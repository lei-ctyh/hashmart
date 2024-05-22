package com.wangyameng.dto;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserBoxDeliverReq.java
 * @Description TODO
 * @createTime 2024-05-19 10:24:00
 */
public class UserBoxDeliverReq {
    /**
     * {
     *     "id": 75,
     *     "express_name": "顺丰快递",
     *     "express_code": "SFEXPRESS",
     *     "express_no": "12121212",
     *     "splitOrder": false,
     *     "detailIds": []
     * }
     */
    Integer id;
    String express_name;
    String express_code;
    String express_no;
    Boolean splitOrder;
    Integer[] detailIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public Boolean getSplitOrder() {
        return splitOrder;
    }

    public void setSplitOrder(Boolean splitOrder) {
        this.splitOrder = splitOrder;
    }

    public Integer[] getDetailIds() {
        return detailIds;
    }

    public void setDetailIds(Integer[] detailIds) {
        this.detailIds = detailIds;
    }
}
