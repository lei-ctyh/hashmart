package com.wangyameng.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户协议表(UserAgree)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@SuppressWarnings("serial")
public class UserAgree extends Model<UserAgree> {

    private Integer id;
    // 1用户协议 2隐私协议
    private Integer type;
    // 协议内容
    private String content;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

