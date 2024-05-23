package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrEditAdminReq.java
 * @Description TODO
 * @createTime 2024-05-23 23:39:00
 */
public class AddOrEditAdminReq {
    /**
     * {"id":"","role_id":5,"username":"dingdan","nickname":"订单专员","password":"123456","avatar":"","email":"2468341590@qq.com","phone":"13256610521","status":1}
     */
    private String id;
    private Integer role_id;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String email;
    private String phone;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
