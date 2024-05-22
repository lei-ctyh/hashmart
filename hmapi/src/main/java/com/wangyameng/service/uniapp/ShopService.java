package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName ShopService.java
 * @Description TODO
 * @createTime 2023-12-04 11:57:00
 */
public interface ShopService {
    AjaxResult getCateList();

    AjaxResult getSliderList();

    AjaxResult getGoodsList(int page, int limit);

    AjaxResult getGoodsListByCateId(int page, int limit, Integer cateId);

    AjaxResult getGoodsInfo(int id);
}
