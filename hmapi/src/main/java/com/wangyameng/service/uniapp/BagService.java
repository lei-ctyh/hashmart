package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @date 2024年04月10日 11:31
 */
public interface BagService {
    /**
     * 获取仓库详情
     * @param page 第几页
     * @param limit 每页多少条
     * @param status 状态
     * @return AjaxResult
     */
    AjaxResult getBagBoxList(int page, int limit, int status);
}
