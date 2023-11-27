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
import com.wangyameng.vo.admin.HomeDataVo;
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
        HomeDataVo homeDataVo = new HomeDataVo();
        // 昨天0点0分0秒
        Date beginTime = getTimePoint(1, 0, 0, 0);
        // 昨天23点59分59秒
        Date endTime = getTimePoint(1, 23, 59, 59);
        Map<String, Object> yesterdaySData = getStatisticsData(beginTime, endTime);
        homeDataVo.setBlind_box_order_counts(new BigDecimal(yesterdaySData.get("blindBoxOrderCounts").toString()));
        homeDataVo.setBlind_box_order_amount(new BigDecimal(yesterdaySData.get("blindBoxOrderAmount").toString()));
        homeDataVo.setAll_order_counts(new BigDecimal(yesterdaySData.get("allOrderCounts").toString()));
        homeDataVo.setAll_order_amount(new BigDecimal(yesterdaySData.get("allOrderAmount").toString()));
        homeDataVo.setUsers(new BigDecimal(yesterdaySData.get("users").toString()));


        beginTime = getTimePoint(31, 0, 0, 0);
        endTime = getTimePoint(1, 23, 59, 59);
        Map<String, Object> monthData = getStatisticsData(beginTime, endTime);
        homeDataVo.setMonth_blind_box_order_counts(new BigDecimal(monthData.get("blindBoxOrderCounts").toString()));
        homeDataVo.setMonth_blind_box_order_amount(new BigDecimal(monthData.get("blindBoxOrderAmount").toString()));
        homeDataVo.setMonth_all_order_amount(new BigDecimal(monthData.get("allOrderAmount").toString()));
        homeDataVo.setMonth_all_order_counts(new BigDecimal(monthData.get("allOrderCounts").toString()));
        homeDataVo.setMonth_users(new BigDecimal(monthData.get("users").toString()));

        // 前天天0点0分0秒
        beginTime = getTimePoint(2, 0, 0, 0);
        endTime = getTimePoint(2, 23, 59, 59);
        Map<String, Object> beforeData = getStatisticsData(beginTime, endTime);
        homeDataVo.setBefore_blind_box_order_amount(new BigDecimal(beforeData.get("blindBoxOrderAmount").toString()));
        homeDataVo.setChange_amount(new BigDecimal(beforeData.get("blindBoxOrderAmount").toString()));
        homeDataVo.setChange_blind_box_order_counts(new BigDecimal(beforeData.get("allOrderAmount").toString()));
        homeDataVo.setChange_all_order_counts(new BigDecimal(beforeData.get("allOrderCounts").toString()));
        homeDataVo.setChange_users(new BigDecimal(beforeData.get("users").toString()));

        // 15天数据曲线
        List<String> dates = new ArrayList<>();
        List<Integer> date_orders = new ArrayList<>();
        List<Integer> date_users = new ArrayList<>();

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
        homeDataVo.setDates(dates);
        homeDataVo.setDate_orders(date_orders);
        homeDataVo.setDate_users(date_users);


        return AjaxResult.dataReturn(0, "success", homeDataVo);
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
