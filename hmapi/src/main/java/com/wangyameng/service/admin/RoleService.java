package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditRoleReq;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName RoleService.java
 * @Description TODO
 * @createTime 2024-05-23 21:49:00
 */
public interface RoleService {
    AjaxResult getList(String name, Integer page, Integer limit);

    AjaxResult add(AddOrEditRoleReq addOrEditRoleReq);

    AjaxResult edit(AddOrEditRoleReq addOrEditRoleReq);

    AjaxResult delete(Integer id);

    AjaxResult getAllRole();
}
