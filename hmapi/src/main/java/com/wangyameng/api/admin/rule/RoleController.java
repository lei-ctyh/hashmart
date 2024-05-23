package com.wangyameng.api.admin.rule;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditRoleReq;
import com.wangyameng.service.admin.AdminService;
import com.wangyameng.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName RoleController.java
 * @Description TODO
 * @createTime 2024-05-23 21:47:00
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/rule.role/index")
    public AjaxResult index(String name, Integer page, Integer limit) {
        return roleService.getList(name, page, limit);
    }

    @PostMapping("/admin/rule.role/add")
    public AjaxResult add(@RequestBody AddOrEditRoleReq addOrEditRoleReq) {
        return roleService.add(addOrEditRoleReq);
    }
    @PostMapping("/admin/rule.role/edit")
    public AjaxResult edit(@RequestBody AddOrEditRoleReq addOrEditRoleReq) {
        return roleService.edit(addOrEditRoleReq);
    }
    @PostMapping("/admin/rule.role/del")
    public AjaxResult delete(@RequestBody AddOrEditRoleReq addOrEditRoleReq) {
        return roleService.delete(Integer.valueOf(addOrEditRoleReq.getId()));
    }

    @GetMapping("/admin/rule.role/getAllRole")

    public AjaxResult getAllRole() {
        return roleService.getAllRole();
    }
}
