package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_client;

import java.util.Date;

public class Order {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private double amount;
    private Date createTime;

    public Order(Integer id, Integer userId, Integer productId, double amount, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
