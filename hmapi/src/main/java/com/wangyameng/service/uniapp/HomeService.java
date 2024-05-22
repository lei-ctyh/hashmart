package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author wangyameng
 * @date 2023年11月14日 18:14
 */
public interface HomeService {
    /**
     * 获取首页聚合数据
     *
     * @param page 当前页
     * @param limit 每页限制数
     * @return ajax结果
     */
    AjaxResult getHomeData(int page, int limit);
}
