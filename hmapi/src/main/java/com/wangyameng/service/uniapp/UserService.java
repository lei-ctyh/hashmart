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
    AjaxResult getUserInfo(String token);
}
