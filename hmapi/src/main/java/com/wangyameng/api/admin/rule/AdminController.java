package com.wangyameng.api.admin.rule;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditAdminReq;
import com.wangyameng.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AdminController.java
 * @Description TODO
 * @createTime 2024-05-23 20:54:00
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/rule.admin/index")
    public AjaxResult index(String username, Integer page, Integer limit) {
        return adminService.getList(username, page, limit);
    }

    @PostMapping("/admin/rule.admin/add")
    public AjaxResult add(@RequestBody AddOrEditAdminReq addOrEditAdminReq) {
        return adminService.add(addOrEditAdminReq);
    }

    @PostMapping("/admin/rule.admin/edit")
    public AjaxResult edit(@RequestBody AddOrEditAdminReq addOrEditAdminReq) {
        return adminService.edit(addOrEditAdminReq);
    }

    @GetMapping("/admin/rule.admin/del")
    public AjaxResult delete(Integer id) {
        return adminService.del(id);
    }
}
