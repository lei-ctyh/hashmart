package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.OrderDetailDao;
import com.wangyameng.dao.OrderRecordDetailDao;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.OrderDetail;
import com.wangyameng.entity.OrderRecordDetail;
import com.wangyameng.service.admin.BlindBoxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindBoxOrderServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-17 22:37:00
 */
@Service("adminBlindBoxOrderService")
public class BlindBoxOrderServiceImpl implements BlindBoxOrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderRecordDetailDao orderRecordDetailDao;
    @Override
    public AjaxResult getList(String orderNo, String payOrderNo, String userName, String payStatus, Integer page, Integer limit) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getType, 1);

        if (StringUtils.isNotBlank(orderNo)) {
            queryWrapper.like(Order::getOrderNo, orderNo);
        }

        if (StringUtils.isNotBlank(payOrderNo)) {
            queryWrapper.like(Order::getPayOrderNo, payOrderNo);
        }

        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like(Order::getUserName, userName);
        }

        if (StringUtils.isNotBlank(payStatus)) {
            queryWrapper.eq(Order::getPayStatus, payStatus);
        }

        queryWrapper.orderByDesc(Order::getCreateTime);

        Page<Order> orderPage = orderDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", orderPage.getTotal());

        JSONArray rows = new JSONArray();
        for (Order order : orderPage.getRecords()) {
            rows.add(PubfuncUtil.parseToUnderlineJson(order));
        }
        rtnJson.put("rows", rows);

        return AjaxResult.dataReturn(0, "success", rtnJson);
    }


    @Override
    public AjaxResult detail(String orderId) {
        LambdaQueryWrapper<OrderRecordDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderRecordDetail::getOrderId, orderId);
        List<OrderRecordDetail> orderDetails = orderRecordDetailDao.selectList(queryWrapper);
        JSONArray rtnData = new JSONArray();
        for (OrderRecordDetail orderDetail : orderDetails) {
            orderDetail.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(orderDetail.getGoodsImage()));
            rtnData.add(PubfuncUtil.parseToUnderlineJson(orderDetail));
        }
        return AjaxResult.dataReturn(0, "success", rtnData);
    }
}
