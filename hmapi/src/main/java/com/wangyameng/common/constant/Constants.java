package com.wangyameng.common.constant;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName Constants.java
 * @Description 通用常量类
 * @createTime 2023-10-20 13:01:00
 */
public class Constants {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";

    public static final class PayWay {
        // 1. 微信支付 2. 支付宝 3. 哈希币  4. 余额
        public static final Integer WECHAT_PAY = 1;
        public static final Integer ALIPAY = 2;
        public static final Integer HASH_COIN = 3;
        public static final Integer BALANCE = 4;

    }
}
