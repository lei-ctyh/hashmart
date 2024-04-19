package com.wangyameng.strategy.pay;

import com.wangyameng.common.constant.Constants;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.OrderParamDTO;
import com.wangyameng.dto.PayParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName PayProvider.java
 * @Description TODO
 * @createTime 2024-04-19 22:25:00
 */
@Component
public class PayProvider {

    private PayStrategy payStrategy;
    @Autowired
    private Map<String, PayStrategy> payStrategyMap = new HashMap<>();

    public AjaxResult pay(PayParamDTO payParamDTO, int payWay) {
        if (payWay == Constants.PayWay.BALANCE) {
            this.payStrategy = payStrategyMap.get("balancePayStrategy");
        } else if (payWay == Constants.PayWay.WECHAT_PAY) {
            this.payStrategy = payStrategyMap.get("wechatPayStrategy");
        } else if (payWay == 40) {
            this.payStrategy = payStrategyMap.get("expressBalancePay");
        }
        return payStrategy.pay(payParamDTO);
    }
}
