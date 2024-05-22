package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrEditBlindboxDetailReq.java
 * @Description TODO
 * @createTime 2024-05-15 00:14:00
 */
public class AddOrEditBlindboxDetailReq {


    private Integer blindbox_id;
    @com.fasterxml.jackson.annotation.JsonProperty("goods_id")

    private Integer goods_id;
    private String goods_image;
    private String goods_name;
    private Integer id;
    private Integer lottery_max_no;
    private Integer lottery_min_no;
    private Double price;
    private Double probability;
    private Double recovery_price;
    private int stock;
    private int tag_id;

    public Integer getBlindbox_id() {
        return blindbox_id;
    }

    public void setBlindbox_id(Integer blindbox_id) {
        this.blindbox_id = blindbox_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLottery_max_no() {
        return lottery_max_no;
    }

    public void setLottery_max_no(Integer lottery_max_no) {
        this.lottery_max_no = lottery_max_no;
    }

    public Integer getLottery_min_no() {
        return lottery_min_no;
    }

    public void setLottery_min_no(Integer lottery_min_no) {
        this.lottery_min_no = lottery_min_no;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getRecovery_price() {
        return recovery_price;
    }

    public void setRecovery_price(Double recovery_price) {
        this.recovery_price = recovery_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }
}
