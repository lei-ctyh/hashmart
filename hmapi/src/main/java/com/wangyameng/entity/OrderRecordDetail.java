package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 订单获取的奖品详情(OrderRecordDetail)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class OrderRecordDetail extends Model<OrderRecordDetail> {
    // ID
    private Integer id;
    // 用户中奖记录id
    private Integer orderRecordId;
    // 关联的订单id
    private Integer orderId;
    // 用户id
    private Integer userId;
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
    // 获得商品id
    private Integer goodsId;
    // 获得商品图片
    private String goodsImage;
    // 获得商品名
    private String goodsName;
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
    private Integer status;
    @TableField("`RANGE`")
    // 抽中范围
    private String range;
    // 下单时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderRecordId() {
        return orderRecordId;
    }

    public void setOrderRecordId(Integer orderRecordId) {
        this.orderRecordId = orderRecordId;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
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

