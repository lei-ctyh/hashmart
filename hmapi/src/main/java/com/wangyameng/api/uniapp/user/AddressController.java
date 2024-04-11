package com.wangyameng.api.uniapp.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("uniapp/user/address/edit")
    public AjaxResult edit(@RequestParam Map<String, Object> params) {
        return addressService.edit(params);
    }

    @PostMapping("uniapp/user/address/add")
    public AjaxResult add(@RequestParam Map<String, Object> params) {
        return addressService.add(params);
    }

    @PostMapping("uniapp/user/address/setDefault")
    public AjaxResult setDefault(@RequestParam("id") String addressId) {
        return addressService.setDefault(addressId);
    }

    @PostMapping("uniapp/user/address/delete")
    public AjaxResult delete(@RequestParam("id") String addressId) {
        return addressService.delete(addressId);
    }
}
