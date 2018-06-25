package com.bao.miaosha.service;

import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.dao.OrderDao;
import com.bao.miaosha.domain.MiaoshaOrder;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.domain.OrderInfo;
import com.bao.miaosha.redis.MiaoshaOrderKey;
import com.bao.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(MiaoshaOrderKey.miaoshaOrderKey, String.valueOf(userId), MiaoshaOrder.class);
    }

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsIdAtDB(long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId((long) user.getId());
        orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId((long) user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        redisService.set(MiaoshaOrderKey.miaoshaOrderKey, String.valueOf(user.getId()), miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderByUserIdAndOrderId(long userId, long orderId) {
        return orderDao.getOrderByUserIdAndOrderId(userId, orderId);
    }
}
