package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 商品规格值扩展表(GoodsRuleExtend)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class GoodsRuleExtend extends Model<GoodsRuleExtend> {
    // 主键id
    private Integer id;
    // 商品ID
    private Object goodsId;
    // 属性索引
    private String sku;
    // 属性对应的库存
    private Object stock;
    // 销量
    private Object sales;
    // 图片
    private String image;
    // 唯一值
    private String unique;
    // 展示价格
    private Double price;
    // 兑换金额
    private Object recoveryPrice;
    // 成本价
    private Object costPrice;
    // 哈希币价格
    private Double integralPrice;
    // 创建时间
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Object goodsId) {
        this.goodsId = goodsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Object getStock() {
        return stock;
    }

    public void setStock(Object stock) {
        this.stock = stock;
    }

    public Object getSales() {
        return sales;
    }

    public void setSales(Object sales) {
        this.sales = sales;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Object getRecoveryPrice() {
        return recoveryPrice;
    }

    public void setRecoveryPrice(Object recoveryPrice) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

