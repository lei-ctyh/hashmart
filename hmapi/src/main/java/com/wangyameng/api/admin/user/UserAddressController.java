package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.EditeUserAddressReq;
import com.wangyameng.entity.UserAddress;
import com.wangyameng.service.admin.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserAddressController.java
 * @Description TODO
 * @createTime 2024-05-24 00:44:00
 */
@RestController
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/admin/user.userAddress/index")
    public AjaxResult index(Integer user_id, Integer page, Integer limit) {
        return userAddressService.getList(user_id, page, limit);
    }

    @GetMapping("/admin/user.userAddress/areas")
    public AjaxResult areas() {
        return userAddressService.areas();
    }

    @PostMapping("/admin/user.userAddress/edit")
    public AjaxResult edit(@RequestBody EditeUserAddressReq editeUserAddressReq) {
        return userAddressService.edit(editeUserAddressReq);
    }

    @PostMapping("/admin/user.userAddress/del")
    public AjaxResult del(@RequestBody EditeUserAddressReq editeUserAddressReq) {
        return userAddressService.del(editeUserAddressReq.getId());
    }

}
