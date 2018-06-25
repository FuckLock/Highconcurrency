package com.bao.miaosha.controller;

import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.Vo.OrderDetailVo;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.domain.OrderInfo;
import com.bao.miaosha.interceptor.UserContext;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.service.GoodsService;
import com.bao.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> getOrderDetail(@RequestParam("orderId") long orderId){
        MiaoshaUser user = UserContext.get();
        OrderInfo orderInfo = orderService.getOrderByUserIdAndOrderId(user.getId(), orderId);

        if (orderInfo == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        System.out.println("========orderInfo.getGoodsId()=========" + orderInfo.toString());
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(orderInfo.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrderInfo(orderInfo);
        orderDetailVo.setGoodsVo(goodsVo);
        return Result.success(orderDetailVo);
    }
}
