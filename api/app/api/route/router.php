<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2018 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

use think\facade\Route;

// 活动相关
Route::group('/activity', function () {

});

// 商品相关
Route::group('/goods', function () {

    Route::get('/blindboxDetail', 'api/v1.goods.Blindbox/detail'); // 盲盒详情

    Route::get('/shop/cateList', 'api/v1.shop.Shop/cateList'); // 商品分类
    Route::get('/shop/cateDetail', 'api/v1.shop.Shop/cateDetail'); // 分类详情
    Route::get('/shop/goodsList', 'api/v1.shop.Shop/goodsList'); // 商品列表
    Route::get('/shop/goodsInfo', 'api/v1.shop.Shop/goodsInfo'); // 商品详情
    Route::get('/shop/slider', 'api/v1.shop.Shop/getSlider'); // 商品幻灯

    Route::get('/blindbox/goodsInfo', 'api/v1.goods.Blindbox/info'); // 盲盒商品内容
});

// 订单相关
Route::group('/order', function () {

    Route::post('/trail', 'api/v1.order.Order/trail'); // 盲盒购买试算
    Route::post('/createOrder', 'api/v1.order.Order/createOrder'); // 盲盒订单创建
    Route::post('/payOrder', 'api/v1.order.Order/payOrder'); // 盲盒订单支付

    Route::post('/shop/trail', 'api/v1.shop.ShopOrder/trail'); // 普通商品试算
    Route::post('/shop/createOrder', 'api/v1.shop.ShopOrder/createOrder'); // 普通商品订单创建
    Route::post('/shop/payOrder', 'api/v1.shop.ShopOrder/payOrder'); // 普通商品订单支付

    Route::any('/notify/alipay', 'api/v1.order.Notify/alipay'); // 支付宝支付
    Route::any('/notify/wechat', 'api/v1.order.Notify/wechat'); // 微信支付

    Route::get('/getBagBoxList', 'api/v1.order.Bag/getBagBoxList');//仓库=>盲盒列表
    Route::get('/getBagBoxDetail', 'api/v1.order.Bag/getBagBoxDetail');//仓库=>单个盲盒详情
    Route::get('/getBagGoodsList', 'api/v1.order.Bag/getBagGoodsList');//仓库=>商品列表
    Route::get('/getBagGoodsDetail', 'api/v1.order.Bag/getBagGoodsDetail');//仓库=>单个商品详情
    Route::post('/bagBoxExchange', 'api/v1.order.Bag/bagBoxExchange');//仓库=>盲盒兑换
    Route::get('/express', 'api/v1.order.order/express'); // 提货物流
    Route::post('/bagBoxTrail', 'api/v1.order.Bag/boxTrail');// 仓库=>盲盒发货试算
    Route::post('/bagBoxDeliver', 'api/v1.order.Bag/boxDeliver'); // 盲盒提货订单支付
    Route::post('/confirmBoxDeliver', 'api/v1.order.Bag/confirmBoxDeliver'); //仓库=>盲盒提货确认
    Route::post('/bagGoodsConfirm', 'api/v1.order.Bag/bagGoodsConfirm');//仓库=>商品确认发货
    Route::get('/shop/express', 'api/v1.shop.ShopOrder/express'); // 积分商城发货物流

    Route::post('/result', 'api/v1.order.order/result'); // 中奖结果

    Route::get('/blindbox', 'api/v1.order.order/orderList'); // 盲盒订单
    Route::post('/blindbox/cancel', 'api/v1.order.order/cancel'); // 盲盒订单取消
    Route::post('/blindbox/repay', 'api/v1.order.order/repay'); // 重新支付

    Route::get('/balance', 'api/v1.order.BalanceOrder/index'); // 余额充值订单
    Route::post('/balance/cancel', 'api/v1.order.BalanceOrder/cancel'); // 余额充值订单取消
    Route::post('/balance/repay', 'api/v1.order.BalanceOrder/repay'); // 重新支付
});

// 用户相关
Route::group('/user', function () {

    Route::post('/login/loginByWechat', 'api/controller/v1.user.Login/loginByWechat'); // 微信授权登录
    Route::post('/login/loginByAccount', 'api/controller/v1.user.Login/loginByAccount'); // 账号密码登录
    Route::post('/login/loginBySms', 'api/controller/v1.user.Login/loginBySms'); // 短信登录
    Route::post('/login/loginByPhone', 'api/v1.user.Login/loginByPhone'); // 手机号一键登录
    Route::post('/getPhone', 'api/v1.user.Login/getUserPhone'); // 获取手机号
    Route::get('/getUniapp', 'api/v1.user.Login/getUniapp'); // 获取uniapp配置
    Route::post('/login/forget', 'api/controller/v1.user.Login/forget'); // 忘记密码
    Route::post('/getInfo', 'api/v1.user.User/getUserInfo'); // 用户基础信息
    Route::post('/setInfo', 'api/v1.user.User/setUserInfo'); // 设置用户信息
    Route::get('/orderRecordLog', 'api/v1.user.User/orderRecordLog'); // 开盒记录列表
    Route::get('/order/list', 'api/v1.user.User/orderList'); // 开盒记录列表
    Route::get('/address/option', 'api/v1.user.Address/option'); // 省市区数据
    Route::get('/address/list', 'api/v1.user.Address/list'); // 用户收货地址=>列表
    Route::post('/address/add', 'api/v1.user.Address/add'); // 用户收货地址=>添加
    Route::post('/address/edit', 'api/v1.user.Address/edit'); // 用户收货地址=>编辑
    Route::post('/address/setDefault', 'api/v1.user.Address/setDefault'); // 用户收货地址=>设置默认值
    Route::post('/address/delete', 'api/v1.user.Address/delete'); // 用户收货地址=>删除
    Route::post('/updatehash', 'api/v1.user.User/updateToken'); // 用户收货地址=>删除

    Route::get('/balance/list', 'api/v1.user.UserBalance/index'); // 用户余额记录
    Route::get('/balance/info', 'api/v1.user.UserBalance/info'); // 用户余额基础信息
    Route::post('/balance/createOrder', 'api/v1.user.UserBalance/createOrder'); // 用户余额充值订单
});

// 公用的方法
Route::group('/common', function () {

    Route::post('/sendSms', 'api/v1.common.Common/sendSms'); // 发短信
    Route::get('/getAvatar', 'api/v1.common.Common/getAvatar'); // 获取头像
    Route::get('/getMusic', 'api/v1.common.Common/getMusic'); // 获取音乐
    Route::get('/userAgreement', 'api/v1.common.Common/userAgreement'); // 获取用户协议
    Route::get('/getKeFuCode', 'api/v1.common.Common/getKeFuCode'); // 获取客服二维码
});

// 首页接口
Route::group('/home', function () {

    Route::get('/index', 'api/v1.home.Home/index');
});
