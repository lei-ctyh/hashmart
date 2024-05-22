package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditGoodCateReq;

/**
 * @author wangyameng
 * @date 2024年05月05日 23:39
 */
public interface GoodsCatService {
    AjaxResult getCateList();

    AjaxResult addCate(AddOrEditGoodCateReq req);

    AjaxResult delCate(Integer id);

    AjaxResult editCate(AddOrEditGoodCateReq req);
}
