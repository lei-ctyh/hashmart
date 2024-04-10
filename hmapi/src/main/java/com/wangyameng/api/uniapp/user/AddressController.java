package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    private AddressService addressService;
    @GetMapping("uniapp/user/address/option")
    public AjaxResult option() {
        return addressService.getOption();
    }

    @GetMapping("uniapp/user/address/list")
    public AjaxResult list() {
        return addressService.getList();
    }
}
