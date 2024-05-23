package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 角色表(SysRole)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@SuppressWarnings("serial")
public class SysRole extends Model<SysRole> {
    @TableId(type = IdType.AUTO)
    // ID
    private Integer id;
    // 角色名
    private String name;
    // 拥有的权限
    private String rule;
    // 1 正常 2 禁用
    private Integer status;
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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

