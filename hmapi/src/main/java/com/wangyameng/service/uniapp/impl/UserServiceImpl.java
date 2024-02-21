package com.wangyameng.service.uniapp.impl;

import cn.hutool.jwt.JWT;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Override
    public AjaxResult getUserInfo(String token) {
        // 检查站点是否正则维护
        if (!PubfuncUtil.checkOpen()) {
            return AjaxResult.dataReturn(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }

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
            return AjaxResult.dataReturn(0,"获取用户信息成功",respData);
        }


        // 'id,nickname,phone,avatar,integral,balance,hash_key'
        return AjaxResult.error();
    }
}
