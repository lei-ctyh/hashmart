package com.wangyameng.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 配置表(SysSetting)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@SuppressWarnings("serial")
public class SysSetting extends Model<SysSetting> {
    // 配置id
    private Integer id;
    // 配置类型
    private String type;
    // 配置名
    private String text;
    // 编号
    private String key;
    // 配置的值
    private String value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

