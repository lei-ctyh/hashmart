package com.wangyameng.dto;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrEditRoleReq.java
 * @Description TODO
 * @createTime 2024-05-23 23:14:00
 */
public class AddOrEditRoleReq {
    private String id;
    private String name;
    private List<String> rule;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getRule() {
        return rule;
    }

    public void setRule(List<String> rule) {
        this.rule = rule;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
