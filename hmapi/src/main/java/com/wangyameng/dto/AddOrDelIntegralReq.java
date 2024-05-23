package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrEditIntegralReq.java
 * @Description TODO
 * @createTime 2024-05-24 01:42:00
 */
public class AddOrDelIntegralReq {
    /**
     * {"id":5,"name":"年","integral":"10","type":1,"originalIntegral":0}
     */

    private Integer id;
    private String name;
    private Integer integral;
    private Integer type;
    private Integer originalIntegral;

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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOriginalIntegral() {
        return originalIntegral;
    }

    public void setOriginalIntegral(Integer originalIntegral) {
        this.originalIntegral = originalIntegral;
    }
}
