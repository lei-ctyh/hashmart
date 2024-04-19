package com.wangyameng.common.util.capital;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangyameng
 * @date 2020-03-13
 * @desc 资金变动工具类
 */
@Component
public class CapitalChangeUtil {
    @Autowired
    UserDao userDao;


    /**
     * 扣减用户的哈希币
     * @param amount
     * @param userId
     * @return
     */
    public AjaxResult decHash(Double amount,Integer userId) {
        if (amount == null || amount <= 0) {
            return AjaxResult.dataReturn(-1,"哈希币金额错误");
        }
        User user = null;
        synchronized (CapitalChangeUtil.class) {
            user = userDao.selectById(userId);
            if (user == null) {
                return AjaxResult.dataReturn(-1,"用户不存在");
            }
            Double currentHash = user.getIntegral();
            if (currentHash < amount) {
                return AjaxResult.dataReturn(-3,"哈希币余额不足");
            }

            user.setIntegral(currentHash - amount);
            userDao.updateById(user);
        }
        return AjaxResult.dataReturn(0,"哈希币减少成功", JSONObject.from(user));
    }


    /**
     * 增加用户的哈希币
     * @param amount
     * @param userId
     * @return
     */
    public AjaxResult addHash(Double amount,Integer userId) {
        if (amount == null || amount <= 0) {
            return AjaxResult.dataReturn(-2,"哈希币金额错误");
        }
        User user = null;
        synchronized (CapitalChangeUtil.class) {
            user = userDao.selectById(userId);
            if (user == null) {
                return AjaxResult.dataReturn(-2,"用户不存在");
            }
            Double currentHash = user.getIntegral();
            user.setIntegral(currentHash + amount);
            userDao.updateById(user);
        }
        return AjaxResult.dataReturn(0,"哈希币增加成功", JSONObject.from(user));
    }

    public AjaxResult decBalance( Double amount,Integer userId) {
        if (amount == null || amount <= 0) {
            return AjaxResult.dataReturn(-3,"余额信息错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        User user = userDao.selectOne(queryWrapper);

        if (user == null) {
            return AjaxResult.dataReturn(-5,"用户不存在");
        }
        Double currentBalance = user.getBalance();
        if (currentBalance < amount) {
            return AjaxResult.dataReturn(-5,"余额不足");
        }
        user.setBalance(currentBalance - amount);
        userDao.updateById(user);
        return AjaxResult.dataReturn(0,"余额减少成功", JSONObject.from(user));
    }

}
