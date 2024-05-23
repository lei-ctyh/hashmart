package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.UserDao;
import com.wangyameng.dto.AddOrDelBalenceReq;
import com.wangyameng.dto.AddOrDelIntegralReq;
import com.wangyameng.dto.AddOrEditUserReq;
import com.wangyameng.entity.User;
import com.wangyameng.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.wangyameng.common.util.pubfunc.PubfuncUtil.getRandAvatar;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-24 00:30:00
 */
@Service("adminUserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public AjaxResult getList(String userId, String nickname, String phone, List<String> createTime, String sourceType, Integer page, Integer limit) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.like(User::getId, userId);
        }
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like(User::getNickname, nickname);
        }
        if (StringUtils.isNotBlank(phone)) {
            queryWrapper.like(User::getPhone, phone);
        }
        if (createTime!= null && createTime.size() == 2) {
            queryWrapper.between(User::getCreateTime, createTime.get(0), createTime.get(1));
        }
        if (StringUtils.isNotBlank(sourceType)) {
            queryWrapper.eq(User::getSourceType, sourceType);
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> userPage = userDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson= new JSONObject();
        rtnJson.put("total", userPage.getTotal());

        JSONArray rows = new JSONArray();
        for (User user : userPage.getRecords()) {
            user.setAvatar(PubfuncUtil.replaceBecomeServerHost(user.getAvatar()));
            rows.add(PubfuncUtil.parseToUnderlineJson(user));
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0,"success", rtnJson);
    }

    @Override
    public AjaxResult add(AddOrEditUserReq addOrEditUserReq) {

        User user = new User();
        user.setNickname("后台用户");
        user.setPhone(addOrEditUserReq.getPhone());
        user.setPassword(PubfuncUtil.makePassword(addOrEditUserReq.getPassword(), ""));
        user.setAvatar(getRandAvatar());
        user.setSourceType(2); //后台
        user.setCreateTime(new Date());
        userDao.insert(user);
        return AjaxResult.dataReturn(0,"添加成功");
    }

    @Override
    public AjaxResult edit(AddOrEditUserReq addOrEditUserReq) {
        User user = userDao.selectById(addOrEditUserReq.getId());
        if (user == null) {
            return AjaxResult.dataReturn(-1,"用户不存在");
        }

        user.setNickname(addOrEditUserReq.getNickname());
        user.setPhone(addOrEditUserReq.getPhone());
        user.setStatus(addOrEditUserReq.getStatus());
        user.setUpdateTime(new Date());
        userDao.updateById(user);
        return AjaxResult.dataReturn(0,"修改成功");
    }

    @Override
    public AjaxResult addOrDelIntegral(AddOrDelIntegralReq addOrDelIntegralReq) {
        if (addOrDelIntegralReq.getIntegral() == null || addOrDelIntegralReq.getIntegral() < 0) {
            return AjaxResult.dataReturn(-1, "变动的哈希币必须大于0");

        }
        User user = userDao.selectById(addOrDelIntegralReq.getId());
        if (user == null) {
            return AjaxResult.dataReturn(-2, "该用户不存在");
        }
        if (addOrDelIntegralReq.getType() == 2 && addOrDelIntegralReq.getIntegral() > user.getIntegral()) {
            return AjaxResult.dataReturn(-3, "该用户没有这么多哈希币可以扣");
        }
        //TODO 增加日志记录
        if (addOrDelIntegralReq.getType() == 1) {
            user.setIntegral(user.getIntegral() + addOrDelIntegralReq.getIntegral());
        } else {
            user.setIntegral(user.getIntegral() - addOrDelIntegralReq.getIntegral());
        }
        userDao.updateById(user);
        return AjaxResult.dataReturn(0, "操作成功");
    }

    @Override
    public AjaxResult addOrDelBalance(AddOrDelBalenceReq addOrDelBalenceReq) {
        /**
         *   if (empty($param['balance']) || $param['balance'] < 0) {
         *             return dataReturn(-1, '变动的余额必须大于0');
         *         }
         *
         *         if ($param['type'] == 2 && $param['balance'] > $param['originalBalance']) {
         *             return dataReturn(-2, '该用户没有这么多余额可以扣');
         *         }
         *
         *         (new UserBalanceChangeLog())->insertOne([
         *             'user_id' => $param['id'],
         *             'username' => $param['name'],
         *             'balance' => ($param['type'] == 1) ? $param['balance'] : 0 - $param['balance'],
         *             'type' => ($param['type'] == 1) ? 3 : 4,
         *             'create_time' => now()
         *         ]);
         *
         *         $userModel = new User();
         *         if ($param['type'] == 1) {
         *             return $userModel->incData(['id' => $param['id']], 'balance', $param['balance']);
         *         } else {
         *             return $userModel->decData(['id' => $param['id']], 'balance', $param['balance']);
         *         }
         */
        if (addOrDelBalenceReq.getBalance() == null || addOrDelBalenceReq.getBalance() < 0) {
            return AjaxResult.dataReturn(-1, "变动的余额必须大于0");
        }


        User user = userDao.selectById(addOrDelBalenceReq.getId());
        if (user == null) {
            return AjaxResult.dataReturn(-2, "该用户不存在");
        }
        if (addOrDelBalenceReq.getType() == 2 && addOrDelBalenceReq.getBalance() > user.getBalance()) {
            return AjaxResult.dataReturn(-3, "该用户没有这么多余额可以扣");
        }
        //TODO 增加日志记录
        if (addOrDelBalenceReq.getType() == 1) {
            user.setBalance(user.getBalance() + addOrDelBalenceReq.getBalance());
        } else {
            user.setBalance(user.getBalance() - addOrDelBalenceReq.getBalance());
        }
        userDao.updateById(user);
        return AjaxResult.dataReturn(0, "操作成功");
    }
}
