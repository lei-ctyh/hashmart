package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditAdminReq;

/**
 * @author wangyameng
 * @date 2024年05月23日 20:56
 */
public interface AdminService {
    AjaxResult getList(String username, Integer page, Integer limit);

    AjaxResult add(AddOrEditAdminReq addOrEditAdminReq);

    AjaxResult edit(AddOrEditAdminReq addOrEditAdminReq);

    AjaxResult del(Integer id);
}
