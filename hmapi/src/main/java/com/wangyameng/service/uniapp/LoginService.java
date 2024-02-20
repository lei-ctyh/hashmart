package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @version 1.0
 * @description: TODO
 * @date 2024/2/20 21:19
 */
public interface LoginService {
    /**
     * 微信一键登录
     * @param code 前端获取的code
     * @param phone_code 前端获取的手机验证码
     */
    public AjaxResult doLoginByWechat(String code, String phone_code);
}
