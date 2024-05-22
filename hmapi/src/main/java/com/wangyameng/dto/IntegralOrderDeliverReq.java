package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserBoxDeliverReq.java
 * @Description TODO
 * @createTime 2024-05-19 10:24:00
 */
public class IntegralOrderDeliverReq {
    /**
     * {
     * "order_id": 105,
     * "user_id": 3,
     * "express_name": "顺丰快递",
     * "express_code": "SFEXPRESS",
     * "express_no": "7777777777777777777"
     * }
     */
    private Integer order_id;
    private Integer user_id;
    private String express_name;
    private String express_code;
    private String express_no;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getExpress_no() {
        return express_no;
    }

}
