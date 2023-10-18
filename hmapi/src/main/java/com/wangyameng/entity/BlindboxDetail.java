package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 盲盒商品详情(BlindboxDetail)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class BlindboxDetail extends Model<BlindboxDetail> {
    // id
    private Integer id;
    // 所属盲盒的id
    private Integer blindboxId;
    // 商品标签
    private Integer tagId;
    // 关联的商品的id
    private Integer goodsId;
    // 关联的商品名称
    private String goodsName;
    // 关联的商品图片
    private String goodsImage;
    // 商品售价
    private Double price;
    // 兑换的价格
    private Double recoveryPrice;
    // 库存
    private Integer stock;
    // 最小中奖数字
    private Integer lotteryMinNo;
    // 最大中间数字
    private Integer lotteryMaxNo;
    // 中奖概率
    private Double probability;
    // 排序值
    private Integer sort;
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

    public Integer getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Integer blindboxId) {
        this.blindboxId = blindboxId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRecoveryPrice() {
        return recoveryPrice;
    }

    public void setRecoveryPrice(Double recoveryPrice) {
        this.recoveryPrice = recoveryPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLotteryMinNo() {
        return lotteryMinNo;
    }

    public void setLotteryMinNo(Integer lotteryMinNo) {
        this.lotteryMinNo = lotteryMinNo;
    }

    public Integer getLotteryMaxNo() {
        return lotteryMaxNo;
    }

    public void setLotteryMaxNo(Integer lotteryMaxNo) {
        this.lotteryMaxNo = lotteryMaxNo;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

