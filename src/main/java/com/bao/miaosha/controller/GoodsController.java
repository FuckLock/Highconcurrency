package com.bao.miaosha.controller;

import com.bao.miaosha.Vo.GoodsDetailVo;
import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.interceptor.UserContext;
import com.bao.miaosha.redis.RedisService;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.service.GoodsService;
import com.bao.miaosha.service.MiaoshaUserService;
import com.bao.miaosha.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model) {
        MiaoshaUser user = UserContext.get();
        List<GoodsVo> goodsList = goodsService.getGoodsVoList();
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/detail/{goodId}")
    @ResponseBody
    public Result<GoodsDetailVo> goodDetail(@PathVariable("goodId")long goodId){
        MiaoshaUser user = UserContext.get();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodId);
        long goodStartTime = goodsVo.getStartDate().getTime();
        long goodEndTime = goodsVo.getEndDate().getTime();
        long nowTime = new Date().getTime();

        long remainSeconds = 0;
        int miaoshaStatus = 0;

        // 商品的开始时间大于现在时间说明还没开始
        if (goodStartTime > nowTime) {
            remainSeconds = (goodStartTime - nowTime)/1000;
            miaoshaStatus = -1;
        }

        // 商品处于秒杀进行当中
        if (goodStartTime <= nowTime && nowTime <= goodEndTime){
            miaoshaStatus = 1;
        }

        // 商品结束了
        if (nowTime > goodEndTime){
            remainSeconds = -1;
            miaoshaStatus = 2;
        }

        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setUser(user);

        Result<GoodsDetailVo> result = new Result<>();
        result.setData(goodsDetailVo);
        return result;
    }
}
