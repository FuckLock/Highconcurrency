package com.bao.miaosha.Vo;

import com.bao.miaosha.domain.Goods;

import java.sql.Timestamp;
import java.util.Date;


public class GoodsVo extends Goods{

    private Double miaoshaPrice;
    private Integer stockCount;
    private Timestamp startDate;
    private Timestamp endDate;

    public GoodsVo(Long id, String goodsName, String goodsTitle, String goodsImg, String goodsDetail, Double goodsPrice, Integer goodsStock, Double miaoshaPrice, Integer stockCount, Timestamp startDate, Timestamp endDate) {
        super(id, goodsName, goodsTitle, goodsImg, goodsDetail, goodsPrice, goodsStock);
        this.miaoshaPrice = miaoshaPrice;
        this.stockCount = stockCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
