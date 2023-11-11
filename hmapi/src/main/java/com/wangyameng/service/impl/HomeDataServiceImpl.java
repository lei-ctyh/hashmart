package com.wangyameng.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.entity.Order;
import com.wangyameng.service.HomeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeDataServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-04 14:36:00
 */
@Service
public class HomeDataServiceImpl implements HomeDataService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public AjaxResult getHomeData() {
        JSONArray homeData = new JSONArray();
        Date beginTime = new Date();
        Date endTime = new Date();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("type, SUM(order_price) AS sum_price, COUNT(id) AS counts")
                    .eq("pay_status", 2)
                    // .between("create_time", beginTime, endTime)
                    .groupBy("type");
        List<Map<String, Object>> maps = orderDao.selectMaps(queryWrapper);


        return AjaxResult.success();
    }
}
