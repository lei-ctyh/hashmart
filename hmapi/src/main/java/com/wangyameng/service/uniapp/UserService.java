package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2024-02-21 14:59:00
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    AjaxResult getUserInfo(String token);

    /**
     * '设置用户信息
     * @param avatar 用户头像
     * @param nickname 昵称
     * @param phone 手机号
     * @return 设置用户信息结果
     */
    AjaxResult setUserInfo(String avatar, String nickname, String phone, String token);


    /**
     * 开盒记录
     * @param token
     * @param page
     * @param limit
     * @return 开盒记录信息
     */
    AjaxResult orderRecordLog(String token, int page, int limit);

    AjaxResult orderList(String token, int page, int limit, int status);
}
