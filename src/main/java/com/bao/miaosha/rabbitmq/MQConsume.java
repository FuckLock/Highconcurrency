package com.bao.miaosha.rabbitmq;
import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.domain.MiaoshaOrder;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.service.GoodsService;
import com.bao.miaosha.service.MiaoshaService;
import com.bao.miaosha.service.OrderService;
import com.bao.miaosha.util.ObjectMapperUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MQConsume {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = MQConfiguration.ORDER_QUEUE)
    public void receive(String msg){
        MiaoshaMessage message = ObjectMapperUtil.stringToBean(msg, MiaoshaMessage.class);
        MiaoshaUser user = message.getUser();
        long goodsId = message.getGoodsId();

        // 到数据库中在判断一次库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }

        // 到数据库中判断订单是否存在，存在什么都不做
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsIdAtDB(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        miaoshaService.miaosha(user, goods);
    }

}
