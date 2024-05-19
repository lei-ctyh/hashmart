package com.wangyameng.common.enums;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName PayStatusEnum.java
 * @Description TODO
 * @createTime 2024-05-19 10:53:00
 */
public enum PayStatusEnum {

    PAY_WAIT(1, "待支付"),
    PAY_SUCCESS(2, "已支付"),
    PAY_REFUND(3, "已退款"),
    PAY_PART_REFUND(4, "部分退款"),
    PAY_PART_SUCCESS(5, "部分支付"),
    PAY_EXCEPTION(6, "支付异常");

    private Integer code;
    private String desc;

    PayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PayStatusEnum getEnum(Integer code) {
        for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
            if (payStatusEnum.getCode().equals(code)) {
                return payStatusEnum;
            }
        }
        throw new IllegalArgumentException("没有找到对应的支付状态的枚举类型 [" + code + "]");
    }
}
