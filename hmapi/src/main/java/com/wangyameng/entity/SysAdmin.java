package com.wangyameng.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 管理员表(SysAdmin)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class SysAdmin extends Model<SysAdmin> {
    // ID
    private Object id;
    // 所属角色id
    private Integer roleId;
    // 用户名
    private String username;
    // 昵称
    private String nickname;
    // 密码
    private String password;
    // 密码盐
    private String salt;
    // 头像
    private String avatar;
    // 邮箱
    private String email;
    // 手机
    private String phone;
    // 登录失败次数
    private Object loginFailure;
    // 登录时间
    private Date lastLoginTime;
    // 登录IP
    private String lastLoginIp;
    // 状态:2 禁用,1=启用
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getLoginFailure() {
        return loginFailure;
    }

    public void setLoginFailure(Object loginFailure) {
        this.loginFailure = loginFailure;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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

