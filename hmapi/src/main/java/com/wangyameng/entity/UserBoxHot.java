package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户奖品盒子热点表(UserBoxHot)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserBoxHot extends Model<UserBoxHot> {
    // ID
    private Object id;
    // 用户ID
    private Object userId;
    // 盲盒ID
    private Object blindboxId;
    // 盲盒购买订单ID
    private Object orderId;
    // 中奖记录id
    private Integer recordDetailId;
    // 商户订单号
    private String outTradeNo;
    // 奖品ID
    private Object goodsId;
    // 奖品名称
    private String goodsName;
    // 奖品图片
    private String goodsImage;
    // 状态 1:盒子中 2:已兑换 3:已提货
    private Integer status;
    // 与用户盒子历史表关联外键
    private String uuid;
    // 兑换时间
    private Date exchangeTime;
    // 提货时间
    private Date deliveryTime;
    // 开箱时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Object blindboxId) {
        this.blindboxId = blindboxId;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Integer getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(Integer recordDetailId) {
        this.recordDetailId = recordDetailId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Object getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Object goodsId) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
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

