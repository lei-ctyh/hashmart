package com.wangyameng.service.impl;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.HomeDataService;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeDataServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-04 14:36:00
 */
@Service
public class HomeDataServiceImpl implements HomeDataService {
    @Override
    public AjaxResult getHomeData() {
        return AjaxResult.success();
    }
}
