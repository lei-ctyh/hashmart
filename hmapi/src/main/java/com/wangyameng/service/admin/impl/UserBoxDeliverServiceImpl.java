package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.enums.PayStatusEnum;
import com.wangyameng.common.enums.PayWay;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.OrderExpressDao;
import com.wangyameng.dao.UserBoxDeliverDao;
import com.wangyameng.dao.UserBoxDeliverDetailDao;
import com.wangyameng.dto.UserBoxDeliverReq;
import com.wangyameng.entity.OrderExpress;
import com.wangyameng.entity.UserBoxDeliver;
import com.wangyameng.service.admin.UserBoxDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserBoxDeliverServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-16 22:49:00
 */
@Service
public class UserBoxDeliverServiceImpl implements UserBoxDeliverService {
    @Autowired
    private UserBoxDeliverDetailDao userBoxDeliverDetailDao;

    @Autowired
    private OrderExpressDao orderExpressDao;
    @Autowired
    private UserBoxDeliverDao userBoxDeliverDao;

    @Override
    public AjaxResult getList(String deliverNo, String status, String userId, Integer page, Integer limit) {
        LambdaQueryWrapper<UserBoxDeliver> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(deliverNo)) {
            queryWrapper.eq(UserBoxDeliver::getDeliverNo, deliverNo);
        }
        if (StringUtils.isNotBlank(status)) {
            queryWrapper.eq(UserBoxDeliver::getStatus, status);
        }
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.eq(UserBoxDeliver::getUserId, userId);
        }
        queryWrapper.orderByDesc(UserBoxDeliver::getCreateTime);
        Page<UserBoxDeliver> ipage = userBoxDeliverDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (UserBoxDeliver userBoxDeliver : ipage.getRecords()) {
            rows.add(PubfuncUtil.parseToUnderlineJson(userBoxDeliver));
        }
        rtnJson.put("rows", rows);
        queryWrapper.eq(UserBoxDeliver::getStatus, 1);

        rtnJson.put("not_express", userBoxDeliverDao.selectCount(queryWrapper));

        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult deliver(UserBoxDeliverReq userBoxDeliverReq) {
        LambdaQueryWrapper<UserBoxDeliver> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBoxDeliver::getId, userBoxDeliverReq.getId());
        UserBoxDeliver userBoxDeliver = userBoxDeliverDao.selectOne(queryWrapper);
        if (userBoxDeliver == null) {
            return AjaxResult.error("该订单不存在");
        }
        userBoxDeliver.setStatus(2);
        userBoxDeliver.setUpdateTime(new Date());

        // 更新信息
        userBoxDeliver.setExpressName(userBoxDeliverReq.getExpress_name());
        userBoxDeliver.setExpressCode(userBoxDeliverReq.getExpress_code());
        userBoxDeliver.setExpressNo(userBoxDeliverReq.getExpress_no());
        userBoxDeliverDao.updateById(userBoxDeliver);
        return AjaxResult.dataReturn(0, "发货成功");
    }

    @Override
    public AjaxResult getOrderInfo(Integer id) {
        LambdaQueryWrapper<OrderExpress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderExpress::getId, id);
        OrderExpress orderExpress = orderExpressDao.selectOne(queryWrapper);
        if (orderExpress == null) {
            return AjaxResult.error("该订单不存在");
        }
        JSONObject info = PubfuncUtil.parseToUnderlineJson(orderExpress);
        info.put("status_txt", PayStatusEnum.getEnum(orderExpress.getPayStatus()).getDesc());
        info.put("pay_way_txt", PayWay.getPayWayEnum(orderExpress.getPayWay()).getDesc());
        return AjaxResult.dataReturn(0, "success", info);
    }
}
