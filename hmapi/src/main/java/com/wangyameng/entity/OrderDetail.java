package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 哈希币商城订单详情(OrderDetail)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class OrderDetail extends Model<OrderDetail> {
    // id
    private Integer id;
    // 关联的订单id
    private Integer orderId;
    // 用户id
    private Integer userId;
    // 商品id
    private Integer goodsId;
    // 商品名称
    private String goodsName;
    // 商品图片
    private String goodsImage;
    // 规格id
    private Integer ruleId;
    // 规格描述
    private String rule;
    // 购买数量
    private Integer num;
    // 实际支付金额
    private Double realPayAmount;
    // 实际支付积分
    private Double realPayIntegral;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Double getRealPayIntegral() {
        return realPayIntegral;
    }

    public void setRealPayIntegral(Double realPayIntegral) {
        this.realPayIntegral = realPayIntegral;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

