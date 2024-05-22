package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrEditBindboxReq.java
 * @Description TODO
 * @createTime 2024-05-14 21:53:00
 */
public class AddOrEditBindboxReq {
    private String id;

    private String desc;
    private Integer hot_tag;
    private String name;
    private String pic;

    private Double price;
    private Integer recommend_tag;
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getHot_tag() {
        return hot_tag;
    }

    public void setHot_tag(Integer hot_tag) {
        this.hot_tag = hot_tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRecommend_tag() {
        return recommend_tag;
    }

    public void setRecommend_tag(Integer recommend_tag) {
        this.recommend_tag = recommend_tag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
