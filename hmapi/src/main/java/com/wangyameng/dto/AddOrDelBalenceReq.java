package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AddOrDelBalenceReq.java
 * @Description TODO
 * @createTime 2024-05-24 02:17:00
 */
public class AddOrDelBalenceReq {
    /**
     * {id: 5, name: "年", balance: "50", type: 2, origralBalance: 0, originalBalance: 0}
     */
    private Integer id;
    private String name;
    private Double balance;
    private Integer type;
    private Integer origralBalance;
    private Integer originalBalance;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrigralBalance() {
        return origralBalance;
    }

    public void setOrigralBalance(Integer origralBalance) {
        this.origralBalance = origralBalance;
    }

    public Integer getOriginalBalance() {
        return originalBalance;
    }

    public void setOriginalBalance(Integer originalBalance) {
        this.originalBalance = originalBalance;
    }
}
