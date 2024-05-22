package com.wangyameng.service.admin;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.LoginDTO;

/**
 * @author wangyameng
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
    AjaxResult doLogin(LoginDTO loginDto) throws  Exception;

    /**
     * 生成验证码相关信息
     */

    AjaxResult createCaptcha();
}
