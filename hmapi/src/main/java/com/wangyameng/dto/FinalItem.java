package com.wangyameng.dto;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName aa.java
 * @Description TODO
 * @createTime 2024-05-09 23:07:00
 */
public class FinalItem {
    private List<String> sku;
    private String image;
    private Double price;
    private Double recovery_price;
    private String cost_price;
    private Double integral_price;
    private Integer stock;

    public List<String> getSku() {
        return sku;
    }

    public void setSku(List<String> sku) {
        this.sku = sku;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
