package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户余额充值表(UserBalanceRechargeLog)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserBalanceRechargeLog extends Model<UserBalanceRechargeLog> {
    // id
    private Integer id;
    // 充值订单号
    private String rechargeNo;
    // 支付单号
    private String payNo;
    // 三方返回的订单号
    private String thirdNo;
    // 用户id
    private Integer userId;
    // 用户名
    private String username;
    // 充值金额
    private Double amount;
    // 支付方式 1:微信 2:支付宝
    private Integer payWay;
    // 充值状态 1:待支付 2:支付成功 3:支付失败 4:过期 5:取消
    private Integer status;
    // 第三方支付返回原始信息
    private String thirdReturn;
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

    public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getThirdReturn() {
        return thirdReturn;
    }

    public void setThirdReturn(String thirdReturn) {
        this.thirdReturn = thirdReturn;
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

