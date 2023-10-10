<?php

namespace app\admin\service\home;

use app\model\order\Order;
use app\model\user\User;

class HomeDataService
{
    /**
     * 获取首页统计数据
     * @return array
     */
    public function getHomeData(): array
    {
        $homeData = [];
        // 昨日盲盒订单金额、数量和普通订单的金额和数量
        $begin = date('Y-m-d 00:00:00', strtotime("-1 day"));
        $end = date('Y-m-d 23:59:59', strtotime("-1 day"));
        $where = [
            ['pay_status', '=', 2], // 已支付
            ['create_time', 'between', [$begin, $end]]
        ];
        $field = ['type,SUM(order_price) AS sum_price,COUNT(id) AS counts'];
        $res = $this->getOrdersInfo($where, $field);

        $orderInfos = []; //  订单类型-统计 映射
        if (! empty($res)) {
            foreach ($res as $item) {
                $orderInfos[$item['type']] = $item;
            }
        }
        $normalOrderCounts = $orderInfos[1]['counts'] ?? 0; // 昨日普通订单量
        $normalOrderAmount = $orderInfos[1]['sum_price'] ?? 0; // 昨日普通订单销售额
        $homeData['blind_box_order_counts'] = $orderInfos[2]['counts'] ?? 0; // 昨日盲盒订单量
        $homeData['blind_box_order_amount'] = $orderInfos[2]['sum_price'] ?? 0; // 昨日盲盒订单销售额
        $homeData['all_order_amount'] = bcadd($homeData['blind_box_order_amount'], $normalOrderAmount, 2); // 昨日所有销售额
        $homeData['all_order_counts'] = bcadd($homeData['blind_box_order_counts'], $normalOrderCounts); // 昨日所有订单量

        // 昨日新增用户数
        $userModel = new User();
        $userWhere = [
            ['source_type', 'in', [1, 3]], // 微信和app注册
            ['create_time', 'between', [$begin, $end]]
        ] ;
        $homeData['users'] = $userModel->where($userWhere)->count('id');

        // 改变一次begin和end为月初和现在
        $begin = date('Y-m-01 00:00:00', time());
        $end = date('Y-m-d H:i:s', time());
        $userWhere = [
            ['source_type', 'in', [1, 3]], // 微信和app注册
            ['create_time', 'between', [$begin, $end]]
        ] ;

        /**********本月的盲盒订单金额、数量和普通订单的金额和数量*********/
        $where = [
            ['pay_status', '=', 2], // 已支付
            ['create_time', 'between', [$begin, $end]]
        ];
        $res = $this->getOrdersInfo($where, $field);
        $orderInfos = []; //  订单类型-统计 映射
        if (! empty($res)) {
            foreach ($res as $item) {
                $orderInfos[$item['type']] = $item;
            }
        }
        $monthNormalOrderCounts = $orderInfos[1]['counts'] ?? 0; // 本月普通订单量
        $monthNormalOrderAmount = $orderInfos[1]['sum_price'] ?? 0; // 本月普通订单销售额
        $homeData['month_blind_box_order_counts'] = $orderInfos[2]['counts'] ?? 0; // 本月盲盒订单量
        $homeData['month_blind_box_order_amount'] = $orderInfos[2]['sum_price'] ?? 0; // 本月盲盒订单销售额
        $homeData['month_all_order_amount'] = bcadd($homeData['month_blind_box_order_amount'], $monthNormalOrderAmount, 2); // 本月所有销售额
        $homeData['month_all_order_counts'] = bcadd($homeData['month_blind_box_order_counts'], $monthNormalOrderCounts); // 本月所有订单量
        // 本月新增用户数
        $homeData['month_users'] = $userModel->where($userWhere)->count('id');

        /*********前天的数据*************/
        // 改变一次begin和end为前天
        $begin = date('Y-m-d 00:00:00', strtotime("-2 day"));
        $end = date('Y-m-d 23:59:59', strtotime("-2 day"));
        // 前天盲盒订单金额、数量和普通订单的金额和数量
        $where = [
            ['pay_status', '=', 2], // 已支付
            ['create_time', 'between', [$begin, $end]]
        ];
        $field = ['type,SUM(order_price) AS sum_price,COUNT(id) AS counts'];
        $res = $this->getOrdersInfo($where, $field);
        $orderInfos = []; //  订单类型-统计 映射
        if (! empty($res)) {
            foreach ($res as $item) {
                $orderInfos[$item['type']] = $item;
            }
        }

        $beforeNormalOrderCounts = $orderInfos[1]['counts'] ?? 0; // 前天普通订单量
        $beforeNormalOrderAmount = $orderInfos[1]['sum_price'] ?? 0; // 前天普通订单销售额
        $beforeBlindBoxOrderCounts = $orderInfos[2]['counts'] ?? 0; // 前天盲盒订单量
        $homeData['before_blind_box_order_amount'] = $orderInfos[2]['sum_price'] ?? 0; // 前天盲盒订单销售额
        $beforeOrderAmount = bcadd($homeData['before_blind_box_order_amount'], $beforeNormalOrderAmount, 2); // 前天所有销售额

        $homeData['change_amount'] = $normalOrderAmount-$beforeOrderAmount; // 前天昨天变动销售额
        $homeData['change_blind_box_order_counts'] = $homeData['blind_box_order_counts']-$beforeBlindBoxOrderCounts; // 前天昨天盲盒订单量变动
        $homeData['change_all_order_counts'] = $homeData['all_order_counts'] - ($beforeNormalOrderCounts + $beforeBlindBoxOrderCounts); // 前天昨天所有订单量变动

        $userWhere = [
            ['source_type', 'in', [1, 3]], // 微信和app注册
            ['create_time', 'between', [$begin, $end]]
        ];

        $beforeUsers = $userModel->where($userWhere)->count('id');  // 前天新增用户数
        $homeData['change_users'] = $homeData['users'] - $beforeUsers; // 前天昨天变动用户数

        $days = 15;
        $dates = [];
        $dateOrders = []; // 每日的订单量
        $dateUsers = []; // 每日的新增用户数
        for ($i = $days; $i  > 0; $i--) {
            $date = date('Y-m-d', strtotime('-' . $i . 'day'));
            $dates[] = $date;
            $dateOrders[$date] = 0;// 默认值
            $dateUsers[$date] = 0;// 默认值
        }
        $begin = date('Y-m-d 00:00:00', strtotime($dates[0]));
        $end = date('Y-m-d 23:59:59', strtotime($dates[14]));

        // 查最近15天订单
        $orderModel = new Order();
        $where = [
            ['pay_status', '=', 2], // 已支付
            ['create_time', 'between', [$begin, $end]]
        ];
        $res = $orderModel->where($where)->field(['id','create_time'])->select()->toArray();
        if (! empty($res)) {
            // 给每日订单增加
            foreach ($res as $item) {
                $orderDate = date('Y-m-d', strtotime($item['create_time']));
                $dateOrders[$orderDate]++;
            }
        }
        $homeData['dates'] = array_values($dates);
        $homeData['date_orders'] = array_values($dateOrders);

        // 查最近15天注册用户数
        $userWhere = [
            ['source_type', 'in', [1, 3]], // 微信和app注册
            ['create_time', 'between', [$begin, $end]]
        ] ;
        $res = $userModel->where($userWhere)->field(['id', 'create_time'])->select()->toArray();
        if (! empty($res)) {
            foreach ($res as $item) {
                $dateUsers[date('Y-m-d', strtotime($item['create_time']))]++;
            }
        }
        $homeData['date_users'] = array_values($dateUsers);
        return dataReturn(0, 'success', $homeData);
    }

    /**
     * Notes:
     * Author: LJS
     * @param $where
     * @param $field
     * @return array
     */
    public function getOrdersInfo($where, $field): array
    {
        $orderModel = new Order();
        return $orderModel->where($where)->field($field)->group('type')->select()->toArray();
    }



}