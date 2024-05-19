package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.OrderDeliverDetailDao;
import com.wangyameng.dao.OrderDetailDao;
import com.wangyameng.dao.SysExpressDao;
import com.wangyameng.dto.IntegralOrderDeliverReq;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.OrderDeliverDetail;
import com.wangyameng.entity.OrderDetail;
import com.wangyameng.entity.SysExpress;
import com.wangyameng.service.admin.IntegralOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    OrderDetailDao orderDetailDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SysExpressDao sysExpressDao;
    @Autowired
    OrderDeliverDetailDao orderDeliverDetailDao;
    @Override
    public AjaxResult getList(Integer status, String userName, String orderNo, String payOrderNo, Integer userId, Integer page, Integer limit) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getType, 1);

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

    @Override
    public AjaxResult getExpressList() {
        LambdaQueryWrapper<SysExpress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysExpress::getStatus, 1);
        List<SysExpress> sysExpresses = sysExpressDao.selectList(wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("code", 0);
        rtnJson.put("msg", "success");

        JSONArray data = new JSONArray();
        for (SysExpress sysExpress : sysExpresses) {
            data.add(PubfuncUtil.parseToUnderlineJson(sysExpress));
        }
        rtnJson.put("data", data);

        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult getDetail(String orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        OrderDetail orderDetail = orderDetailDao.selectOne(queryWrapper);
        orderDetail.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(orderDetail.getGoodsImage()));
        return AjaxResult.dataReturn(0, "success", PubfuncUtil.parseToUnderlineJson(orderDetail));
    }

    @Override
    public AjaxResult deliver(IntegralOrderDeliverReq integralOrderDeliverReq) {
        // 更新订单表
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getId, integralOrderDeliverReq.getOrder_id());
        Order order = orderDao.selectOne(queryWrapper);
        order.setStatus(3);
        order.setExpressCode(integralOrderDeliverReq.getExpress_code());
        order.setExpressName(integralOrderDeliverReq.getExpress_name());
        order.setExpressNo(integralOrderDeliverReq.getExpress_no());
        order.setDeliveryTime(new Date());
        orderDao.updateById(order);

        // 更新物流表
        OrderDeliverDetail orderDeliverDetail = new OrderDeliverDetail();
        orderDeliverDetail.setOrderId(integralOrderDeliverReq.getOrder_id());
        orderDeliverDetail.setUserId(integralOrderDeliverReq.getUser_id());
        orderDeliverDetail.setStatus(1);
        orderDeliverDetail.setCreateTime(new Date());
        orderDeliverDetailDao.insert(orderDeliverDetail);

        return AjaxResult.dataReturn(0, "发货成功");
    }
}
