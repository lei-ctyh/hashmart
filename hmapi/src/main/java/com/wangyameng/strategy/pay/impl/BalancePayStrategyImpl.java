package com.wangyameng.strategy.pay.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.dto.PayParamDTO;
import com.wangyameng.strategy.pay.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName A.java
 * @Description TODO
 * @createTime 2024-04-19 22:24:00
 */
@Component("balancePayStrategy")
public class BalancePayStrategyImpl implements PayStrategy {
    @Autowired
    private CapitalChangeUtil capitalChangeUtil;

    @Override
    public AjaxResult pay(PayParamDTO payParamDTO) {
        // 减少余额
        AjaxResult userRes = capitalChangeUtil.decHash(payParamDTO.getPay_price(), payParamDTO.getUser_id());
        if ((Integer)userRes.get(AjaxResult.CODE_TAG) != 0) {
            return userRes;
        }
        JSONObject userInfo = (JSONObject) userRes.get(AjaxResult.DATA_TAG);

        return null;
    }
}
