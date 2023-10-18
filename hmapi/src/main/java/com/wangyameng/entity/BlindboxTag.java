package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 盲盒商品标签(BlindboxTag)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class BlindboxTag extends Model<BlindboxTag> {
    // 分类id
    private Integer id;
    // 分类名称
    private String name;
    // 1正常 2隐藏
    private Integer status;
    // 排序值
    private Integer sort;
    // 色号
    private String color;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

