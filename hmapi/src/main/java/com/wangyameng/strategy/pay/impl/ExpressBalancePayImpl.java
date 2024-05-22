package com.wangyameng.strategy.pay.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.dao.OrderExpressDao;
import com.wangyameng.dao.UserBalanceChangeLogDao;
import com.wangyameng.dto.PayParamDTO;
import com.wangyameng.entity.OrderExpress;
import com.wangyameng.entity.UserBalanceChangeLog;
import com.wangyameng.strategy.pay.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName ExpressBalancePayImpl.java
 * @Description TODO
 * @createTime 2024-04-19 23:10:00
 */
@Component("expressBalancePay")
public class ExpressBalancePayImpl implements PayStrategy {
    @Autowired
    private CapitalChangeUtil capitalChangeUtil;
    @Autowired
    private UserBalanceChangeLogDao userBalanceChangeLogDao;
    @Autowired
    private OrderExpressDao orderExpressDao;

    @Override
    public AjaxResult pay(PayParamDTO payParamDTO) {
        // 减少余额
        AjaxResult result = capitalChangeUtil.decBalance(payParamDTO.getPay_price(), payParamDTO.getUser_id());
        if ((Integer) result.get(AjaxResult.CODE_TAG) != 0) {
            return result;
        }

        // 记录余额变动
        UserBalanceChangeLog userBalanceChangeLog = new UserBalanceChangeLog();
        userBalanceChangeLog.setUserId(payParamDTO.getUser_id());
        userBalanceChangeLog.setUsername(payParamDTO.getUsername());
        userBalanceChangeLog.setBalance(0 - payParamDTO.getPay_price());
        userBalanceChangeLog.setType(1);
        userBalanceChangeLog.setOrderId(payParamDTO.getId());
        userBalanceChangeLog.setCreateTime(new Date());
        userBalanceChangeLogDao.insert(userBalanceChangeLog);

        // 修改订单状态
        LambdaQueryWrapper<OrderExpress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderExpress::getId, payParamDTO.getId());
        OrderExpress orderExpress = orderExpressDao.selectOne(lambdaQueryWrapper);
        orderExpress.setPayStatus(2);
        orderExpress.setUpdateTime(new Date());
        orderExpressDao.update(orderExpress, lambdaQueryWrapper);
        return AjaxResult.dataReturn(0, "支付成功");

    }
}
