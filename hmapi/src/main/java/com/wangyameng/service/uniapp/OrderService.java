package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @date 2024年04月11日 10:45
 */
public interface OrderService {
    /**
     * 订单试算
     * @param blindboxId 购买的盲盒id
     * @param num 购买数量
     * @param useIntegral 是否使用hash币
     * @param type
     * @return
     */
    AjaxResult trail(Integer blindboxId, Integer num, Integer useIntegral, Integer type);

    AjaxResult createOrder(Integer blindboxId, Integer num, Integer useIntegral, Integer payWay);

    AjaxResult payOrder(String orderNo, String platform);

    AjaxResult getResult(String orderNo);

}
