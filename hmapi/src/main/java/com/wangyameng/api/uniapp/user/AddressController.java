package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AddressController.java
 * @Description TODO
 * @createTime 2023-11-14 17:37:00
 */
@RestController
public class AddressController {
    @PostMapping("uniapp/user/address/option")
    public AjaxResult option(HttpServletRequest request) {
        System.out.println(request);
        return null;
    }
}
