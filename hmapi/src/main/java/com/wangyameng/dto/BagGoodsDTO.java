package com.wangyameng.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class BagGoodsDTO {
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
    // 与用户盒子热点表关联外键
    private String uuid;
    // 兑换时间
    private Date exchangeTime;
    // 提货时间
    private Date deliveryTime;
    // 开箱时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


    // 用户中奖记录id
    private Integer orderRecordId;
    // 用户id
    // 中奖用户名
    private String userName;
    // 用户当前的hash_key
    private String hashKey;
    // 当前毫秒级时间戳
    private String orderTime;
    // hash算法算出的用户hash值
    private Integer hashNo;
    // 所属标签
    private Integer tagId;
    // 该商品单抽价格
    private Double unitPrice;
    // 商品的价值
    private Double goodsPrice;
    // 兑换价格
    private Double recoveryPrice;
    // 带负号的盈亏值
    private Double profit;
    // 哈希币兑换比例
    private Integer ratio;
    // 状态：1 盒子中 2 已兑换 3 已提货
    @TableField("`RANGE`")
    // 抽中范围
    private String range;

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

    public Integer getOrderRecordId() {
        return orderRecordId;
    }

    public void setOrderRecordId(Integer orderRecordId) {
        this.orderRecordId = orderRecordId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getHashNo() {
        return hashNo;
    }

    public void setHashNo(Integer hashNo) {
        this.hashNo = hashNo;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getRecoveryPrice() {
        return recoveryPrice;
    }

    public void setRecoveryPrice(Double recoveryPrice) {
        this.recoveryPrice = recoveryPrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
