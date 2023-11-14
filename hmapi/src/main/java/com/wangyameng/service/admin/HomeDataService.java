package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeDataService.java
 * @Description TODO
 * @createTime 2023-11-04 14:35:00
 */
public interface HomeDataService {
    /**
     * 获取管理员后台首页数据
     * @return
     */
    AjaxResult getHomeData();
}
