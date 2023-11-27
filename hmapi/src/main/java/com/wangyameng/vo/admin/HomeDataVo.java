package com.wangyameng.vo.admin;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeDataVo.java
 * @Description 后台主页数据聚合对象
 * @createTime 2023-11-23 09:57:00
 */
public class HomeDataVo {
    // 昨日盲盒订单数
    private BigDecimal blind_box_order_counts;
    // 昨日盲盒订单销售额
    private BigDecimal blind_box_order_amount;
    // 昨日总订单数")
    private BigDecimal all_order_counts;
    // 昨日总订单销售额
    private BigDecimal all_order_amount;
    // 昨日新增用户数
    private BigDecimal users;
    // 本月盲盒订单量
    private BigDecimal month_blind_box_order_counts;
    // 本月盲盒订单销售额
    private BigDecimal month_blind_box_order_amount;
    // 本月所有销售额
    private BigDecimal month_all_order_amount;
    // 本月所有订单量
    private BigDecimal month_all_order_counts;
    // 本月新增用户数
    private BigDecimal month_users;

    // 前天盲盒订单销售额
    private BigDecimal before_blind_box_order_amount;
    // 前天所有销售额
    private BigDecimal change_amount;
    // 前天昨天盲盒订单量变动
    private BigDecimal change_blind_box_order_counts;
    // 前天昨天所有订单量变动
    private BigDecimal change_all_order_counts;
    // 前天新增用户数
    private BigDecimal change_users;

    // 近15日日期
    private List<String> dates;
    // 每天的订单量
    private List<Integer> date_orders;
    // 每天的新增用户数
    private List<Integer> date_users;

    public BigDecimal getBlind_box_order_counts() {
        return blind_box_order_counts;
    }

    public void setBlind_box_order_counts(BigDecimal blind_box_order_counts) {
        this.blind_box_order_counts = blind_box_order_counts;
    }

    public BigDecimal getBlind_box_order_amount() {
        return blind_box_order_amount;
    }

    public void setBlind_box_order_amount(BigDecimal blind_box_order_amount) {
        this.blind_box_order_amount = blind_box_order_amount;
    }

    public BigDecimal getAll_order_counts() {
        return all_order_counts;
    }

    public void setAll_order_counts(BigDecimal all_order_counts) {
        this.all_order_counts = all_order_counts;
    }

    public BigDecimal getAll_order_amount() {
        return all_order_amount;
    }

    public void setAll_order_amount(BigDecimal all_order_amount) {
        this.all_order_amount = all_order_amount;
    }

    public BigDecimal getUsers() {
        return users;
    }

    public void setUsers(BigDecimal users) {
        this.users = users;
    }

    public BigDecimal getMonth_blind_box_order_counts() {
        return month_blind_box_order_counts;
    }

    public void setMonth_blind_box_order_counts(BigDecimal month_blind_box_order_counts) {
        this.month_blind_box_order_counts = month_blind_box_order_counts;
    }

    public BigDecimal getMonth_blind_box_order_amount() {
        return month_blind_box_order_amount;
    }

    public void setMonth_blind_box_order_amount(BigDecimal month_blind_box_order_amount) {
        this.month_blind_box_order_amount = month_blind_box_order_amount;
    }

    public BigDecimal getMonth_all_order_amount() {
        return month_all_order_amount;
    }

    public void setMonth_all_order_amount(BigDecimal month_all_order_amount) {
        this.month_all_order_amount = month_all_order_amount;
    }

    public BigDecimal getMonth_all_order_counts() {
        return month_all_order_counts;
    }

    public void setMonth_all_order_counts(BigDecimal month_all_order_counts) {
        this.month_all_order_counts = month_all_order_counts;
    }

    public BigDecimal getMonth_users() {
        return month_users;
    }

    public void setMonth_users(BigDecimal month_users) {
        this.month_users = month_users;
    }

    public BigDecimal getBefore_blind_box_order_amount() {
        return before_blind_box_order_amount;
    }

    public void setBefore_blind_box_order_amount(BigDecimal before_blind_box_order_amount) {
        this.before_blind_box_order_amount = before_blind_box_order_amount;
    }

    public BigDecimal getChange_amount() {
        return change_amount;
    }

    public void setChange_amount(BigDecimal change_amount) {
        this.change_amount = change_amount;
    }

    public BigDecimal getChange_blind_box_order_counts() {
        return change_blind_box_order_counts;
    }

    public void setChange_blind_box_order_counts(BigDecimal change_blind_box_order_counts) {
        this.change_blind_box_order_counts = change_blind_box_order_counts;
    }

    public BigDecimal getChange_all_order_counts() {
        return change_all_order_counts;
    }

    public void setChange_all_order_counts(BigDecimal change_all_order_counts) {
        this.change_all_order_counts = change_all_order_counts;
    }

    public BigDecimal getChange_users() {
        return change_users;
    }

    public void setChange_users(BigDecimal change_users) {
        this.change_users = change_users;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Integer> getDate_orders() {
        return date_orders;
    }

    public void setDate_orders(List<Integer> date_orders) {
        this.date_orders = date_orders;
    }

    public List<Integer> getDate_users() {
        return date_users;
    }

    public void setDate_users(List<Integer> date_users) {
        this.date_users = date_users;
    }
}
