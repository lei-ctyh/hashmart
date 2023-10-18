package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 运费订单(OrderExpress)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class OrderExpress extends Model<OrderExpress> {
    // id
    private Integer id;
    // 订单号
    private String orderNo;
    // 支付订单号
    private String payNo;
    // 支付金额
    private Double amount;
    // 支付方式 1:微信 2:支付宝 3:哈希币 4:余额
    private Integer payWay;
    // 支付状态 1:待支付 2:已支付 3:已退款 4:部分退款 5:部分支付 6:支付异常
    private Integer payStatus;
    // 支付的盒子类型 1:全部 2:盒子中
    private Integer boxType;
    // 支付人id
    private Integer userId;
    // 支付人名
    private String username;
    // 发货地址id
    private Integer addressId;
    // 发货内容
    private String expressContent;
    // 支付时间
    private Date payTime;
    // 三方单号
    private String thirdNo;
    // 三方返回的信息
    private String thirdReturn;
    // 异常支付信息
    private String payError;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getExpressContent() {
        return expressContent;
    }

    public void setExpressContent(String expressContent) {
        this.expressContent = expressContent;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
    }

    public String getThirdReturn() {
        return thirdReturn;
    }

    public void setThirdReturn(String thirdReturn) {
        this.thirdReturn = thirdReturn;
    }

    public String getPayError() {
        return payError;
    }

    public void setPayError(String payError) {
        this.payError = payError;
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

