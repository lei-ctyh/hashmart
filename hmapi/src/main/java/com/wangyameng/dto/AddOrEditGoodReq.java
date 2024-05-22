package com.wangyameng.dto;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddGoodReq.java
 * @Description TODO
 * @createTime 2024-05-09 22:36:00
 */
public class AddOrEditGoodReq {
    private Integer id;
    private Integer goods_type;
    private String name;
    private Integer cate_id;
    private String sub_title;
    private String image;


    private Integer status;
    private Integer delivery_fee;
    private Integer sort;

    private Integer type;
    private Double price;
    private Double recovery_price;
    private String cost_price;
    private Double integral_price;
    private Integer stock;
    private String content;
    private List<preItem> preItem;
    @com.fasterxml.jackson.annotation.JsonProperty("final")
    private List<FinalItem> finalItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(Integer goods_type) {
        this.goods_type = goods_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCate_id() {
        return cate_id;
    }

    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Integer delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRecovery_price() {
        return recovery_price;
    }

    public void setRecovery_price(Double recovery_price) {
        this.recovery_price = recovery_price;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public Double getIntegral_price() {
        return integral_price;
    }

    public void setIntegral_price(Double integral_price) {
        this.integral_price = integral_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<com.wangyameng.dto.preItem> getPreItem() {
        return preItem;
    }

    public void setPreItem(List<com.wangyameng.dto.preItem> preItem) {
        this.preItem = preItem;
    }

    public List<com.wangyameng.dto.FinalItem> getFinalItem() {
        return finalItem;
    }

    public void setFinalItem(List<com.wangyameng.dto.FinalItem> finalItem) {
        this.finalItem = finalItem;
    }
}

class preItem {
    private String title;
    private List<String> item;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
}

