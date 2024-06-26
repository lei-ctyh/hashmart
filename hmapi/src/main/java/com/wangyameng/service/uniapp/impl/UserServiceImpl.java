package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.leheyue.wrapper.MPJLambdaWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.*;
import com.wangyameng.dto.OrderRecordDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangyameng
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

    @Autowired
    OrderRecordDetailDao orderRecordDetailDao;

    @Autowired
    OrderRecordDao orderRecordDao;
    @Autowired
    BlindboxDao blindboxDao;

    @Override
    public AjaxResult getUserInfo() {
        Integer id = UserSessionContext.get().getInteger("id");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        queryWrapper.eq(User::getStatus, 1);
        User userInfo = userDao.selectOne(queryWrapper);
        if (userInfo != null) {

            // 查询待付款订单数
            LambdaQueryWrapper<Order> orderQuery = new LambdaQueryWrapper<>();
            orderQuery.eq(Order::getUserId, id);
            orderQuery.eq(Order::getPayStatus, 1);
            int unpaidOrders = orderDao.selectCount(orderQuery);

            // 待收货订单数
            orderQuery = new LambdaQueryWrapper<>();
            orderQuery.eq(Order::getUserId, id);
            orderQuery.eq(Order::getPayStatus, 3);
            int unshippedOrders = orderDao.selectCount(orderQuery);


            JSONObject respData = new JSONObject();
            respData.put("id", userInfo.getId());
            respData.put("nickname", userInfo.getNickname());
            respData.put("phone", userInfo.getPhone());
            respData.put("avatar", PubfuncUtil.replaceBecomeServerHost(userInfo.getAvatar()));
            respData.put("integral", userInfo.getIntegral());
            respData.put("balance", userInfo.getBalance());
            respData.put("hash_key", userInfo.getHashKey());


            // 查询待付款订单数
            respData.put("unpaid_orders", unpaidOrders);
            // 待收货订单数
            respData.put("unreceived_orders", unshippedOrders);
            return AjaxResult.dataReturn(0, "获取用户信息成功", respData);
        }
        // 'id,nickname,phone,avatar,integral,balance,hash_key'
        return AjaxResult.error();
    }

    @Override
    public AjaxResult setUserInfo(String avatar, String nickname, String phone) {

        Integer id = UserSessionContext.get().getInteger("id");
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

    @Override
    public AjaxResult orderRecordLog(int page, int limit) {
        Integer id = UserSessionContext.get().getInteger("id");

        MPJLambdaWrapper<OrderRecord> wrapper = new MPJLambdaWrapper<OrderRecord>()
                .selectAll(OrderRecord.class)
                .selectAll(OrderRecordDetail.class)
                .selectAll(Blindbox.class)
                .leftJoin(OrderRecordDetail.class, OrderRecordDetail::getOrderRecordId, OrderRecord::getId)
                .leftJoin(Blindbox.class, Blindbox::getId, OrderRecord::getBlindboxId);

        wrapper.eq(OrderRecord::getUserId, id)
                .orderByDesc(OrderRecord::getId);


        Page<OrderRecordDTO> orderRecordDTOPage = orderRecordDao.selectJoinPage(new Page<OrderRecordDTO>(page, limit).setSearchCount(false), OrderRecordDTO.class, wrapper);
        return AjaxResult.dataReturn(0, "获取用户订单成功", PubfuncUtil.paginate(orderRecordDTOPage));
    }

    @Override
    public AjaxResult orderList(String token, int page, int limit, int status) {
        Integer id = UserSessionContext.get().getInteger("id");

        return null;
    }

    @Override
    public AjaxResult updateHash(String hash) {
        Integer id = UserSessionContext.get().getInteger("id");
        // 种子长度应当在0-50
        int minLen = 0;
        int maxLen = 50;
        hash = StringUtils.defaultString(hash, "");
        if (StringUtils.isNotEmpty(hash) && hash.length() > minLen && hash.length() <= maxLen) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getId, id);
            User user = userDao.selectOne(queryWrapper);
            if (user != null) {
                user.setHashKey(hash);
                userDao.updateById(user);
                return AjaxResult.dataReturn(0, "更新成功");
            }else {
                return AjaxResult.error(-1, "该用户不存在");
            }
        }

        return AjaxResult.error(-1, "种子长度应在0~50");

    }
}
