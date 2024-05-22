package com.wangyameng.strategy.pay.impl;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.PayParamDTO;
import com.wangyameng.strategy.pay.PayStrategy;
import org.springframework.stereotype.Component;

import static com.wangyameng.common.util.pay.WechatPayUtil.generatePayParams;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName WeiCheatPayImpl.java
 * @Description TODO
 * @createTime 2024-04-19 22:22:00
 */
@Component("wechatPayStrategy")
public class WeChatPayStrategyImpl implements PayStrategy {
    @Override
    public AjaxResult pay(PayParamDTO payParamDTO) {
        return AjaxResult.dataReturn(201, payParamDTO.getSubject(), generatePayParams(payParamDTO.getSubject(), payParamDTO.getPay_order_no(), payParamDTO.getOpenid(), 1));
    }
}
