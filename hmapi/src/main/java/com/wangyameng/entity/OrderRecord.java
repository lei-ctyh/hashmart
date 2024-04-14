package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户中奖表(OrderRecord)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class OrderRecord extends Model<OrderRecord> {
    @TableId(type = IdType.AUTO)
    // ID
    private Integer id;
    // 关联的订单id
    private Integer orderId;
    // 所属盲盒的id
    private Integer blindboxId;
    // 用户id
    private Integer userId;
    // 用户名
    private String username;
    // 奖品数量
    private Integer awardNum;
    // 奖品总金额
    private Double totalAmount;
    // 奖品总可兑换哈希币
    private Double totalExchangeIntegral;
    // 带负号的总盈亏
    private Double totalProfit;
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

    public Integer getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Integer blindboxId) {
        this.blindboxId = blindboxId;
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

    public Integer getAwardNum() {
        return awardNum;
    }

    public void setAwardNum(Integer awardNum) {
        this.awardNum = awardNum;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalExchangeIntegral() {
        return totalExchangeIntegral;
    }

    public void setTotalExchangeIntegral(Double totalExchangeIntegral) {
        this.totalExchangeIntegral = totalExchangeIntegral;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
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

