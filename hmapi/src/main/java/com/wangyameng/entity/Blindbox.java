package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 盲盒箱子表(Blindbox)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class Blindbox extends Model<Blindbox> {
    // 盲盒id
    private Integer id;
    // 玩法 1:hash玩法
    private Integer playId;
    // 盲盒名称
    private String name;
    // 盲盒图片
    private String pic;
    // 单抽价格
    private Double price;
    // 盲盒描述
    private String desc;
    // 累计销量
    private Integer sales;
    // 排序 值越大越靠前
    private Integer sort;
    // 是否热门 1:是 2:否
    private Integer hotTag;
    // 是否推荐 1:是 2:否
    private Integer recommendTag;
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

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getHotTag() {
        return hotTag;
    }

    public void setHotTag(Integer hotTag) {
        this.hotTag = hotTag;
    }

    public Integer getRecommendTag() {
        return recommendTag;
    }

    public void setRecommendTag(Integer recommendTag) {
        this.recommendTag = recommendTag;
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

