package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditGoodReq;
import com.wangyameng.dto.GoodsListReq;
import com.wangyameng.dto.ShelvesGoodReq;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName GoodsService.java
 * @Description TODO
 * @createTime 2024-05-05 22:58:00
 */
public interface GoodsService {
    AjaxResult getGoodsList(GoodsListReq goodsListReq);

    AjaxResult addGood(AddOrEditGoodReq req);

    AjaxResult editGood(AddOrEditGoodReq req);

    AjaxResult delGood(Integer id);

    AjaxResult shelvesGood(ShelvesGoodReq shelvesGoodReq);
}
