package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 订单物流表(UserBoxDeliver)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserBoxDeliver extends Model<UserBoxDeliver> {
    // id
    private Integer id;
    // 系统内部发货单号
    private String deliverNo;
    // 运费订单id
    private Integer expressOrderId;
    // 关联的地址id
    private Integer addressId;
    // 收货地址
    private String addressInfo;
    // 所属用户的id
    private Integer userId;
    // 类型1:发货物流 2:退货物流
    private Integer type;
    // 状态：1:待发货  2:已发货 3:已签收  4:异常
    private Integer status;
    // 物流公司
    private String expressName;
    // 物流标识
    private String expressCode;
    // 物流单号
    private String expressNo;
    // 物流信息
    private String express;
    // 物流状态 1:录入 2:进行中 3:已完成
    private Integer expressStatus;
    // 物流费用
    private Double postageFee;
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

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public Integer getExpressOrderId() {
        return expressOrderId;
    }

    public void setExpressOrderId(Integer expressOrderId) {
        this.expressOrderId = expressOrderId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Double getPostageFee() {
        return postageFee;
    }

    public void setPostageFee(Double postageFee) {
        this.postageFee = postageFee;
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

