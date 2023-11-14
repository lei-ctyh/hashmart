package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AddressController.java
 * @Description TODO
 * @createTime 2023-11-14 17:37:00
 */
@Api(tags = "微信用户地址信息")
@RestController
public class AddressController {
    @PostMapping("uniapp/user/address/option")
    @ApiOperation(value = "发起登录")
    public AjaxResult option(HttpServletRequest request) {
        System.out.println(request);
        return null;
    }
}
