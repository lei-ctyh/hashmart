package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author wangyameng
 * @date 2024年04月10日 11:31
 */
public interface BagService {
    /**
     * 获取仓库详情
     * @param page 第几页
     * @param limit 每页多少条
     * @param status 状态
     * @return AjaxResult
     */
    AjaxResult getBagBoxList(int page, int limit, int status);

    AjaxResult bagBoxExchange(String boxId, int type);


    /**
     * 仓库盲盒试运算
     * @param boxId
     * @param addressId
     * @param type
     * @return
     */
    AjaxResult bagBoxTrail(String boxId, int addressId, int type);

    /**
     * 盲盒提货订单支付
     *
     * @param boxId     盲盒id
     * @param addressId 地址id
     * @param type      类型
     * @param platform
     * @param payWay
     * @return
     */
    AjaxResult bagBoxDelivery(String boxId, int addressId, int type, String platform, int payWay);

    AjaxResult getBagGoodsList(int page, int limit, int status);
}
