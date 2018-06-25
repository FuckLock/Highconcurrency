package com.bao.miaosha.controller;

import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.dao.GoodsDao;
import com.bao.miaosha.domain.MiaoshaOrder;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.domain.OrderInfo;
import com.bao.miaosha.interceptor.UserContext;
import com.bao.miaosha.rabbitmq.MQSender;
import com.bao.miaosha.rabbitmq.MiaoshaMessage;
import com.bao.miaosha.redis.GoodStockKey;
import com.bao.miaosha.redis.KeyPrefix;
import com.bao.miaosha.redis.RedisService;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.service.GoodsService;
import com.bao.miaosha.service.MiaoshaService;
import com.bao.miaosha.service.OrderService;
import com.bao.miaosha.validator.AccessLimit;
import com.sun.tools.javac.jvm.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.runtime.ResultTypeFinder;
import sun.plugin2.message.Message;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean{

    private Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> goodOver = new HashMap<>();

    /**
     * 这个方法会在spring bean初始化进行，先把每件商品的库存加载到redis中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList =  goodsService.getGoodsVoList();
        if (CollectionUtils.isEmpty(goodsVoList)){
            return;
        }
        for (GoodsVo goodsVo: goodsVoList){
            redisService.set(GoodStockKey.goodStockKey, String.valueOf(goodsVo.getId()), goodsVo.getStockCount());
            goodOver.put(goodsVo.getId(), false);
        }
    }

    /**
     * 秒杀接口优化
     * 1. 现在redis里面判断库存和重复订单， 这个方法里面不要有任何查询数据库到操作，要不然高并发数据库扛不住都是白搭
     * 2. 这一步把数据库查询到动作都放到rabbitmq 的队列中，这样会一个一个消费，不会对数据库就行挤压
     *
     *
     */
    @PostMapping("/{path}/miaoshaOrder")
    @ResponseBody
    public Result<Integer> miaoshaOrder(@RequestParam("goodsId") long goodsId, @PathVariable("path") String path){
        MiaoshaUser user = UserContext.get();
        System.out.println("==================================path" + path);

        boolean check = miaoshaService.checkRequest(user.getId(), goodsId, path);

        if (!check) {
            return Result.error(CodeMsg.ILLEGAL_REQUEST);
        }

        // 减少对redis的访问
        if (goodOver.get(goodsId)){
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        // 到redis 判断库存, 符合条件才能秒杀，否则直接返回
        Integer redisStock = redisService.get(GoodStockKey.goodStockKey, String.valueOf(goodsId), Integer.class);
        if (redisStock <= 0) {
            goodOver.put(goodsId, true);
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        // 到redis里面查找订单是否存在，存在返回，不可重复秒杀
        MiaoshaOrder orderInfo = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (orderInfo != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入队列
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setGoodsId(goodsId);
        miaoshaMessage.setUser(user);
        mqSender.sendMessage(miaoshaMessage);

        return Result.success(0);//排队中
    }

    /**
     * data = orderID 秒杀成功
     * data = -1 秒杀失败
     * data = 0 还在秒杀
     *
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshsOrderResult(@RequestParam("goodsId") long goodsId){
        MiaoshaUser user = UserContext.get();
        return miaoshaService.getMiaoshaResult(user.getId(), goodsId);
    }

    /**
     *  防止非法用户刷请求，通过请求服务器端获取连接来防止这种情况
     */
    @AccessLimit(seconds=5, maxCount=5)
    @GetMapping("/path")
    @ResponseBody
    public Result<String> getMiaoshaPath(@RequestParam("goodsId") long goodsId, @RequestParam("verifyCode") int verifyCode){
        MiaoshaUser user = UserContext.get();
        boolean check = miaoshaService.checkVerifyCode(user.getId(), goodsId, verifyCode);
        if (!check){
           return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }

        String path = miaoshaService.getMiaoshaPath(user, goodsId);
        if (path == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success(path);
    }

    @GetMapping("/verifyCode")
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, @RequestParam("goodsId")long goodsId) {
        MiaoshaUser user = UserContext.get();

        BufferedImage image = miaoshaService.getImage(user.getId(), goodsId);
        try {
            ImageIO.write(image, "png", response.getOutputStream());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    /**
     * 秒杀订单需要做的事
     * 1.判断库存，查看商品是否空闲，空闲则可以秒杀，否则不能返回错误
     * 2.查看用户是否已经秒杀(判断订单是否存在),如果已经秒杀返回，不可重复秒杀
     * 3.可以下订单了，下订单我们要 减库存，创建订单。(存在线程安全兴问题： 第一：库存共享，第二有写操作，高并发情况下库存会存在负数的可能)
     *
     * 线程安全性问题解决
     * 分两种情况
     * 1，不同用户到高并发，库存会存在负值，修改sql语句, 在原先到sql添加 and stock > 0, 保证库存大于0才减库存
     * 2. 相同用户到高并发, 可能同一个用户会下两次订单，可以在miaoshaorder添加 userid orderid 唯一性索引来修改
     *
     */
    /*
    @PostMapping("/miaoshaOrder")
    @ResponseBody
    public Result<OrderInfo> miaoshaOrder(MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0){
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        return Result.success(orderInfo);
    }

    // 不是异步的，性能不太好
    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.OUT_OF_STOCK.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(Long.valueOf(user.getId()), goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
    */

}
