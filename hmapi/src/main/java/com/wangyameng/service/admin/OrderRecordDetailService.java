package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName OrderRecordDetailService.java
 * @Description TODO
 * @createTime 2024-05-17 00:11:00
 */
public interface OrderRecordDetailService {
    AjaxResult getList(Integer orderRecordId, Integer page, Integer limit);
}
