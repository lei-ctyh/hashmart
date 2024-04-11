package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 订单主表(Order)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class Order extends Model<Order> {
    @TableId(value = "id",type = IdType.AUTO)
    // 订单id
    private Integer id;
    // 父订单id
    private Integer pid;
    // 订单类型 1:普通订单 2:盲盒订单
    private Integer type;
    // 订单号
    private String orderNo;
    // 支付订单号
    private String payOrderNo;
    // 下单用户
    private Integer userId;
    // 下单人名
    private String userName;
    // 购买盲盒的id
    private Integer blindboxId;
    // 购买的盲盒的箱子id（预留）
    private Integer boxId;
    // 盲盒玩法id
    private Integer playId;
    // 订单商品数量
    private Integer totalNum;
    // 单抽价格
    private Double unitPrice;
    // 邮费
    private Double postage;
    // 订单总金额
    private Double orderPrice;
    // 支付方式 1:微信 2:支付宝 3:哈希币 4:余额
    private Integer payWay;
    // 实际支付金额
    private Double payPrice;
    // 实际支付哈希币
    private Double payIntegral;
    // 实际支付邮费
    private Double payPostage;
    // 优惠券累计抵扣金额
    private Double couponAmount;
    // 支付状态 1:待支付 2:已支付 3:已退款 4:部分退款 5:部分支付 6:支付异常 7:支付超时
    private Integer payStatus;
    // 支付时间
    private Date payTime;
    // 发货时间
    private Date deliveryTime;
    // 发货地址id
    private Integer addressId;
    // 发货地址详情
    private String addressInfo;
    // 发货物流名
    private String expressName;
    // 物流编号
    private String expressCode;
    // 发货订单号
    private String expressNo;
    // 取消时间
    private Date cancelTime;
    // 收货时间
    private Date receivedTime;
    // 关闭时间
    private Date closeTime;
    // 订单状态 1:待支付 2:待发货 3:待收货 4:部分发货 5:已完成 6:已取消 7:已关闭 8:库存不足
    private Integer status;
    // 第三方返回信息
    private String returnMsg;
    // 第三方支付订单号
    private String thirdCode;
    // 用户备注
    private String remark;
    // 哈希币兑换比
    private Integer integralRatio;
    // 1:正常 2:删除
    private Integer delFlag;
    // 用户是否删除 1:正常 2:删除
    private Integer userDel;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
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

    public Integer getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Integer blindboxId) {
        this.blindboxId = blindboxId;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Double getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(Double payIntegral) {
        this.payIntegral = payIntegral;
    }

    public Double getPayPostage() {
        return payPostage;
    }

    public void setPayPostage(Double payPostage) {
        this.payPostage = payPostage;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIntegralRatio() {
        return integralRatio;
    }

    public void setIntegralRatio(Integer integralRatio) {
        this.integralRatio = integralRatio;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getUserDel() {
        return userDel;
    }

    public void setUserDel(Integer userDel) {
        this.userDel = userDel;
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

