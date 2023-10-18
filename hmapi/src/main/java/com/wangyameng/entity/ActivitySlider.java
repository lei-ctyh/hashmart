package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 导航轮播表(ActivitySlider)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:30
 */
@SuppressWarnings("serial")
public class ActivitySlider extends Model<ActivitySlider> {
    // 轮播id
    private Integer id;
    // 1:首页 2:哈希币商城
    private Integer type;
    // 轮播描述
    private String title;
    // 关联的盲盒的id
    private Integer blindboxId;
    // 关联的盲盒的名字
    private String blindboxName;
    // 关联的商品id
    private Integer goodsId;
    // 商品名
    private String goodsName;
    // 商品类型 1:普通商品  2:盲盒商品 3:积分 4:优惠券商品 5:余额商品
    private Integer goodsType;
    // 导航图片
    private String pic;
    // 状态 1:启用 2:禁用
    private Integer status;
    // 补充的额外参数
    private String other;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Integer blindboxId) {
        this.blindboxId = blindboxId;
    }

    public String getBlindboxName() {
        return blindboxName;
    }

    public void setBlindboxName(String blindboxName) {
        this.blindboxName = blindboxName;
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

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

