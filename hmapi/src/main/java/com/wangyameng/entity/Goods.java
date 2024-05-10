package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 商品表(Goods)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class Goods extends Model<Goods> {
    @TableId(type = IdType.AUTO)

    // ID
    private Integer id;
    // 所属商品分类
    private Integer cateId;
    // 1:普通商品  2:盲盒商品 3:哈希币 4:优惠券商品 5:余额商品
    private Integer goodsType;
    // 1 单规格 2 多规格
    private Integer type;
    // 商品名称
    private String name;
    // 商品副标题
    private String subTitle;
    // 商品图片
    private String image;
    // 商品详情图片,多个用,分割
    private String content;
    // 商品库存
    private Integer stock;
    // 展示价格
    private Double price;
    // 兑换价格
    private Double recoveryPrice;
    // 成本价
    private Object costPrice;
    // 哈希币价格
    private Double integralPrice;
    // 状态:1:正常,2:下架
    private Integer status;
    // 销量
    private Integer sales;
    // 运费
    private Object deliveryFee;
    // 排序，值越大越靠谱
    private Integer sort;
    // 1 正常 2 删除
    private Integer deleteFlag;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 删除时间
    private Date deleteTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public Object getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Object costPrice) {
        this.costPrice = costPrice;
    }

    public Double getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Double integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Object getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Object deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

}

