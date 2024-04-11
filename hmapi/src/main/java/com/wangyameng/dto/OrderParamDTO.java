package com.wangyameng.dto;

public class OrderParamDTO {
    /**
     *  orderParam.put("id", order.getId());
     *         orderParam.put("total_num", order.getTotalNum());
     *         orderParam.put("unit_price", order.getUnitPrice());
     *         orderParam.put("order_no", order.getOrderNo());
     *         orderParam.put("trace_id", 0);
     *         orderParam.put("blindbox_id", order.getBlindboxId());
     *         orderParam.put("user_id", order.getUserId());
     *         orderParam.put("user_name", order.getUserName());
     *         orderParam.put("pay_price", order.getPayPrice());
     *         orderParam.put("pay_integral", order.getPayIntegral());
     *         orderParam.put("host", host);
     *         orderParam.put("play_id", order.getPlayId());
     *         orderParam.put("pay_order_no", order.getPayOrderNo());
     *         orderParam.put("subject", "盲盒购买" + order.getTotalNum() + "个");
     */

    private Long id;
    private Integer totalNum;
    private Double unitPrice;
    private String orderNo;
    private Integer traceId;
    private Long blindboxId;
    private Long userId;
    private String userName;
    private Double payPrice;
    private Integer payIntegral;
    private String host;
    private Long playId;
    private String payOrderNo;
    private String subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTraceId() {
        return traceId;
    }

    public void setTraceId(Integer traceId) {
        this.traceId = traceId;
    }

    public Long getBlindboxId() {
        return blindboxId;
    }

    public void setBlindboxId(Long blindboxId) {
        this.blindboxId = blindboxId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(Integer payIntegral) {
        this.payIntegral = payIntegral;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getPlayId() {
        return playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
