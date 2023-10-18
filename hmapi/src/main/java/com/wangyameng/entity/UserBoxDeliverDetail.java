package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户盒子发货详情表(UserBoxDeliverDetail)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserBoxDeliverDetail extends Model<UserBoxDeliverDetail> {
    // id
    private Integer id;
    // 发货id
    private Integer deliverId;
    // 关联的用户id
    private Integer userId;
    // 用户奖品盒子uuid
    private String userBoxUuid;
    // 中奖记录id
    private Integer recordDetailId;
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

    public Integer getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Integer deliverId) {
        this.deliverId = deliverId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserBoxUuid() {
        return userBoxUuid;
    }

    public void setUserBoxUuid(String userBoxUuid) {
        this.userBoxUuid = userBoxUuid;
    }

    public Integer getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(Integer recordDetailId) {
        this.recordDetailId = recordDetailId;
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

