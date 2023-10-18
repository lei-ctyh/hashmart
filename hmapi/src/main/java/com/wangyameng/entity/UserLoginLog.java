package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户登录日志表(UserLoginLog)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserLoginLog extends Model<UserLoginLog> {
    // 日志id
    private Integer id;
    // 登录用户id
    private Integer userId;
    // 登录用户昵称
    private String nickname;
    // 登录的ip
    private String loginIp;
    // 登录的设备
    private String userAgent;
    // 登录时间
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

