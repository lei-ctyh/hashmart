package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxService.java
 * @Description TODO
 * @createTime 2023-11-27 14:33:00
 */
public interface BlindboxService {
    /**
     * 获取盲盒详情
     * @param id 盲盒ID
     * @return 盲盒信息
     */
    AjaxResult getBlindboxDetail(String id);

    AjaxResult getGoodsInfo(String id);
}
