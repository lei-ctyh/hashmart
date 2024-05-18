package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.entity.Order;
import com.wangyameng.service.admin.IntegralOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName IntegralOrderServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-17 23:09:00
 */
@Service
public class IntegralOrderServiceImpl implements IntegralOrderService {
    @Autowired
    OrderDao orderDao;
    @Override
    public AjaxResult getList(Integer status, String userName, String orderNo, String payOrderNo, Integer userId, Integer page, Integer limit) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getType, 2);

        if (status!= null) {
            wrapper.eq(Order::getStatus, status);
        }
        if (userName!= null) {
            wrapper.like(Order::getUserName, userName);
        }
        if (orderNo!= null) {
            wrapper.like(Order::getOrderNo, orderNo);
        }
        if (payOrderNo!= null) {
            wrapper.like(Order::getPayOrderNo, payOrderNo);
        }
        if (userId!= null) {
            wrapper.eq(Order::getUserId, userId);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        Page<Order> orderPage = orderDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", orderPage.getTotal());

        JSONArray rows = new JSONArray();
        for (Order order : orderPage.getRecords()) {
            rows.add(PubfuncUtil.parseToUnderlineJson(order));
        }
        rtnJson.put("rows", rows);

        wrapper.eq(Order::getStatus, 2);
        rtnJson.put("not_express", orderDao.selectCount(wrapper));
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }
}
