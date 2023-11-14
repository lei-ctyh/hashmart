package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.User;
import com.wangyameng.service.admin.HomeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserDao userDao;

    @Override
    public AjaxResult getHomeData() {
        JSONObject homeData = new JSONObject();
        // 昨天0点0分0秒
        Date beginTime = getTimePoint(1, 0, 0, 0);
        // 昨天23点59分59秒
        Date endTime = getTimePoint(1, 23, 59, 59);
        Map<String, Object> yesterdaySData = getStatisticsData(beginTime, endTime);
        // 昨日盲盒订单数
        homeData.put("blind_box_order_counts", yesterdaySData.get("blindBoxOrderCounts"));
        // 昨日盲盒销售额
        homeData.put("blind_box_order_amount", yesterdaySData.get("blindBoxOrderAmount"));
        // 昨日总订单数
        homeData.put("all_order_counts", yesterdaySData.get("allOrderCounts"));
        // 昨日盲盒订单销售额
        homeData.put("all_order_amount", yesterdaySData.get("allOrderAmount"));
        // 昨日新增用户数
        homeData.put("users", yesterdaySData.get("users"));


        beginTime = getTimePoint(31, 0, 0, 0);
        endTime = getTimePoint(1, 23, 59, 59);
        Map<String, Object> monthData = getStatisticsData(beginTime, endTime);
        // 本月盲盒订单量
        homeData.put("month_blind_box_order_counts", monthData.get("blindBoxOrderCounts"));
        // 本月盲盒订单销售额
        homeData.put("month_blind_box_order_amount", monthData.get("blindBoxOrderAmount"));
        // 本月所有销售额
        homeData.put("month_all_order_amount", monthData.get("allOrderAmount"));
        // 本月所有订单量
        homeData.put("month_all_order_counts", monthData.get("allOrderCounts"));
        // 本月新增用户数
        homeData.put("month_users", monthData.get("users"));

        // 前天天0点0分0秒
        beginTime = getTimePoint(2, 0, 0, 0);
        endTime = getTimePoint(2, 23, 59, 59);
        Map<String, Object> beforeData = getStatisticsData(beginTime, endTime);
        // 前天盲盒订单销售额
        homeData.put("before_blind_box_order_amount", beforeData.get("blindBoxOrderAmount"));
        // 前天所有销售额
        homeData.put("change_amount", beforeData.get("blindBoxOrderAmount"));
        // 前天昨天盲盒订单量变动
        homeData.put("change_blind_box_order_counts", beforeData.get("allOrderAmount"));
        // 前天昨天所有订单量变动
        homeData.put("change_all_order_counts", beforeData.get("allOrderCounts"));
        // 前天新增用户数
        homeData.put("change_users", monthData.get("users"));

        // 15天数据曲线
        JSONArray dates = new JSONArray();
        JSONArray date_orders = new JSONArray();
        JSONArray date_users = new JSONArray();

        beginTime = getTimePoint(16, 0, 0, 0);
        endTime = getTimePoint(1, 23, 59, 59);
        LambdaQueryWrapper<Order> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.between(Order::getCreateTime, beginTime, endTime);
        List<Order> orders = orderDao.selectList(orderWrapper);

        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.between(User::getCreateTime, beginTime, endTime);
        List<User> users = userDao.selectList(userWrapper);
        int days = 15;
        for (int i = days; i >= 0; i--) {
            beginTime = getTimePoint(i, 0, 0, 0);
            endTime = getTimePoint(i, 23, 59, 59);
            dates.add(PubfuncUtil.formatDate(beginTime, "yyyy-MM-dd"));


            Date finalBeginTime = beginTime;
            Date finalEndTime = endTime;
            List<Order> theDayOrders = orders.stream().filter(order -> finalBeginTime.getTime() <= order.getCreateTime()
                                                                                                        .getTime() && order.getCreateTime()
                                                                                                                           .getTime() <= finalEndTime.getTime())
                                             .collect(Collectors.toList());

            List<User> theDayUsers = users.stream().filter(user -> finalBeginTime.getTime() <= user.getCreateTime()
                                                                                                   .getTime() && user.getCreateTime()
                                                                                                                     .getTime() <= finalEndTime.getTime())
                                          .collect(Collectors.toList());

            date_orders.add(theDayOrders.size());
            date_users.add(theDayUsers.size());
        }

        // 横坐标日期
        homeData.put("dates", dates);
        homeData.put("date_orders", date_orders);
        homeData.put("date_users", date_users);


        return AjaxResult.dataReturn(0, "success", homeData);
    }


    /**
     * 获取某段时间范围内的订单的销售额, 订单量, 用户增长数
     *
     * @param beginTime 数据统计的开始时间
     * @param endTime   数据统计的结束时间
     * @return 返回的map格式参照如下
     * {
     * blindBoxOrderCounts: 盲盒订单数,
     * blindBoxOrderAmount: 盲盒订单销售额,
     * allOrderCounts: 总订单数,
     * allOrderAmount: 总订单销售额,
     * users: 新增用户数
     * }
     */
    private Map<String, Object> getStatisticsData(Date beginTime, Date endTime) {
        Map<String, Object> rtnData = new HashMap<>(6);
        // 盲盒订单数
        long blindBoxOrderCounts = 0L;
        // 盲盒订单销售额
        BigDecimal blindBoxOrderAmount = new BigDecimal("0");
        // 普通订单数
        long ordinaryOrderCounts = 0L;
        // 普通订单销售额
        BigDecimal ordinaryOrderAmount = new BigDecimal("0");

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.select("type, SUM(order_price) AS sum_price, COUNT(id) AS counts")
                         .eq("pay_status", 2)
                         .between("create_time", beginTime, endTime)
                         .groupBy("type");
        List<Map<String, Object>> maps = orderDao.selectMaps(orderQueryWrapper);

        for (Map<String, Object> map : maps) {
            Integer type = (Integer) map.get("type");
            // 盲盒类型的数据
            if (type == 2) {
                blindBoxOrderCounts = map.get("counts") == null ? 0L : (long) map.get("counts");
                blindBoxOrderAmount = map.get("sum_price") == null ? new BigDecimal("0") : (BigDecimal) map.get("sum_price");
            } else {
                ordinaryOrderCounts = map.get("counts") == null ? 0L : (long) map.get("counts");
                ordinaryOrderAmount = map.get("sum_price") == null ? new BigDecimal("0") : (BigDecimal) map.get("sum_price");
            }
        }

        // 新增用户数
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        // 1: 代表微信 2: 代表后台新增, 不计入统计 3: 代表app
        userQueryWrapper.in(User::getSourceType, 1, 3)
                        .between(User::getCreateTime, beginTime, endTime);
        List<User> users = userDao.selectList(userQueryWrapper);


        rtnData.put("blindBoxOrderCounts", blindBoxOrderCounts);
        rtnData.put("blindBoxOrderAmount", blindBoxOrderAmount);
        rtnData.put("allOrderCounts", ordinaryOrderCounts + blindBoxOrderCounts);
        rtnData.put("allOrderAmount", ordinaryOrderAmount.add(blindBoxOrderAmount));
        rtnData.put("users", users.size());
        return rtnData;
    }

    /**
     * 获取当前日期 before 天的时间点
     *
     * @param before 往前推多少天
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 当前日期前推before天的 时分秒时间
     */
    private Date getTimePoint(int before, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - before, hour, minute, second);
        return calendar.getTime();
    }


    public static void main(String[] args) {
        System.out.println(PubfuncUtil.formatDate(new Date(), "yyyy-MM-dd"));
    }
}
