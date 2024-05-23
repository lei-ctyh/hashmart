package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.EditeUserAddressReq;

/**
 * @author wangyameng
 * @date 2024年05月24日 0:45
 */
public interface UserAddressService {
    AjaxResult getList(Integer userId, Integer page, Integer limit);

    AjaxResult areas();

    AjaxResult edit(EditeUserAddressReq editeUserAddressReq);

    AjaxResult del(Integer id);
}
