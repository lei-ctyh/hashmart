package com.wangyameng.common.enums;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName PayWay.java
 * @Description TODO
 * @createTime 2024-05-19 10:54:00
 */
public enum PayWay {
    WECHAT(1, "微信"),
    ALIPAY(2, "支付宝"),
    INTEGRAL(3, "积分"),
    BALANCE(4, "余额");

    private Integer code;
    private String desc;

    PayWay(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static PayWay getPayWayEnum(Integer code) {
        for (PayWay payWay : PayWay.values()) {
            if (payWay.getCode().equals(code)) {
                return payWay;
            }
        }
        throw new IllegalArgumentException("没有对应的支付方式:" + code);
    }

}
