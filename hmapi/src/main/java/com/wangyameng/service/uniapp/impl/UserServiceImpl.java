package com.wangyameng.service.uniapp.impl;

import cn.hutool.jwt.JWT;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2024-02-21 15:00:00
 */
@Service("uniappUserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public AjaxResult getUserInfo(String token) {
        JWT jwt = PubfuncUtil.getJWT(token.substring(7));
        Integer id = ((cn.hutool.json.JSONObject) jwt.getPayload().getClaim("data")).getInt("id");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        queryWrapper.eq(User::getStatus, 1);
        User userInfo = userDao.selectOne(queryWrapper);
        if (userInfo != null) {

            // 查询待付款订单数

            // 待收货订单数
            JSONObject respData = new JSONObject();
            respData.put("id", userInfo.getId());
            respData.put("nickname", userInfo.getNickname());
            respData.put("phone", userInfo.getPhone());
            respData.put("avatar", PubfuncUtil.replaceBecomeServerHost(userInfo.getAvatar()));
            respData.put("integral", userInfo.getIntegral());
            respData.put("balance", userInfo.getBalance());
            respData.put("hash_key", userInfo.getHashKey());

            LambdaQueryWrapper<Order> orderQuery = new LambdaQueryWrapper<>();
            orderQuery.eq(Order::getUserId, id);
            orderQuery.eq(Order::getPayStatus, 1);

            // XXX 待付款订单数和待收货订单数似乎没有用到
            // 查询待付款订单数
            respData.put("unpaid_orders", orderDao.selectCount(orderQuery));
            orderQuery.eq(Order::getPayStatus, 3);
            // 待收货订单数
            respData.put("unreceived_orders", orderDao.selectCount(orderQuery));
            return AjaxResult.dataReturn(0, "获取用户信息成功", respData);
        }
        // 'id,nickname,phone,avatar,integral,balance,hash_key'
        return AjaxResult.error();
    }

    @Override
    public AjaxResult setUserInfo(String avatar, String nickname, String phone, String token) {

        JWT jwt = PubfuncUtil.getJWT(token.substring(7));
        Integer id = ((cn.hutool.json.JSONObject) jwt.getPayload().getClaim("data")).getInt("id");

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userDao.selectOne(queryWrapper);
        if (user == null) {
            return AjaxResult.error(-1, "该用户不存在");
        }
        user.setAvatar(avatar);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setUpdateTime(new Date());
        userDao.updateById(user);
        return AjaxResult.dataReturn(0,"操作成功");
    }
}
