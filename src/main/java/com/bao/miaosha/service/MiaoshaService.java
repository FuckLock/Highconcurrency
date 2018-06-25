package com.bao.miaosha.service;

import com.bao.miaosha.Vo.GoodsVo;
import com.bao.miaosha.domain.MiaoshaOrder;
import com.bao.miaosha.domain.MiaoshaUser;
import com.bao.miaosha.domain.OrderInfo;
import com.bao.miaosha.redis.*;
import com.bao.miaosha.result.CodeMsg;
import com.bao.miaosha.result.Result;
import com.bao.miaosha.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.font.Script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.CollationElementIterator;
import java.util.Random;

@Service
public class MiaoshaService {

    private Logger logger = LoggerFactory.getLogger(MiaoshaService.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        boolean record = goodsService.reduceStock(goods);
        if (record){
            redisService.set(MiaoshaOrderKey.miaoshaOrderKey, String.valueOf(user.getId()), goods.getStockCount());
            return orderService.createOrder(user, goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public Result<Long> getMiaoshaResult(long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsIdAtDB(userId, goodsId);
        if (order != null){
            return Result.success(order.getId(), "秒杀成功");
        } else {
            boolean flag = getGoodsOver(goodsId);
            if (flag){
                return Result.error("库存不足，秒杀失败");
            }else {
                return Result.success(0L, "秒杀进行中");
            }
        }

    }

    public boolean getGoodsOver(long goodsId){
        return redisService.exists(GoodsOver.goodsOver, String.valueOf(goodsId));
    }

    public void setGoodsOver(Long goodsId) {
        redisService.set(GoodsOver.goodsOver, String.valueOf(goodsId), true);
    }

    public String getMiaoshaPath(MiaoshaUser user, long goodsId) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String path = MD5Util.formPassToDBPass(sb1.append(user.getId()).append(goodsId).toString(), "123456");
        redisService.set(MiaoshaPathKey.miaoshaPathKey, sb2.append(user.getId()).append("_").append(goodsId).toString(), path);
        return path;
    }

    public boolean checkRequest(int userId, long goodsId, String path) {
        StringBuilder sb = new StringBuilder();
        if (path == null) {
            return false;
        }
        String redisPath = redisService.get(MiaoshaPathKey.miaoshaPathKey, sb.append(userId).append("_").append(goodsId).toString(), String.class);
        return path.equals(redisPath);
    }

    public BufferedImage getImage(int userId, long goodsId) {
        int width = 80;
        int height = 32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0,  width, height);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width -1, height - 1);
        graphics.setColor(new Color(006400));
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            graphics.drawOval(random.nextInt(width), random.nextInt(height), 0, 0);
        }
        String code = generateVerifyCode(userId, goodsId);
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(code, 8, 24);
        graphics.dispose();
        writeVerifyCodeToRedis(userId, goodsId, code);
        return image;
    }

    public String generateVerifyCode(long userId, long goodsId){
        StringBuilder sb = new StringBuilder();
        char[] chars = new char[]{'+', '-', '*'};
        Random random = new Random();
        int number1 = random.nextInt(10);
        int number2 = random.nextInt(10);
        int number3 = random.nextInt(10);
        return sb.append(number1).append(chars[random.nextInt(chars.length)]).
                append(number2).append(chars[random.nextInt(chars.length)]).
                append(number3).toString();
    }

    private void writeVerifyCodeToRedis(long userId, long goodsId, String code) {
        StringBuilder sb = new StringBuilder();
        redisService.setEx(MiaoshaVeriCode.miaoshaVeriCode, sb.append(userId).append("_").append(goodsId).toString(), calculate(code), 60);
    }

    public int calculate(String code){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("js");
        try {
            return (int) engine.eval(code);
        } catch (ScriptException e) {
            logger.error("javascript eval error", e);
            return 0;
        }
    }

    public boolean checkVerifyCode(long userId, long goodsId, int verifyCode) {
        StringBuilder sb = new StringBuilder();
        int code = redisService.get(MiaoshaVeriCode.miaoshaVeriCode, sb.append(userId).append("_").append(goodsId).toString(), int.class);
        redisService.delete(MiaoshaVeriCode.miaoshaVeriCode, sb.append(userId).append("_").append(goodsId).toString());
        return Integer.valueOf(verifyCode).equals(code);
    }
}
