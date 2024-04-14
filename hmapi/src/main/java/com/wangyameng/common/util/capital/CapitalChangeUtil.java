package com.wangyameng.common.util.capital;

import com.alibaba.fastjson2.JSONObject;
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

    public AjaxResult decrHash(Double amount,Integer userId) {
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

}
