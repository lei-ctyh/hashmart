package com.wangyameng.service;

import com.alibaba.fastjson2.JSONObject;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.LoginDto;
import com.wangyameng.entity.SysAdmin;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName LoginService.java
 * @Description TODO
 * @createTime 2023-11-03 13:45:00
 */
public interface LoginService {
    /**
     * 实现后台用户登录
     * @param loginDto
     * @return
     * @throws Exception
     */
    AjaxResult doLogin(LoginDto loginDto) throws  Exception;

    /**
     * 生成验证码相关信息
     */

    AjaxResult createCaptcha();
}
