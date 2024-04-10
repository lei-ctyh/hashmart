package com.wangyameng.service.uniapp;

import com.wangyameng.common.core.AjaxResult;

import java.util.Map;

/**
 * @author zhanglei
 * @date 2024年04月10日 12:51
 */
public interface AddressService {
    /**
     * 获取地址列表
     * @return all
     */
    AjaxResult getList();

    /**
     * 获取地址选项
     * @return
     */
    AjaxResult getOption();

    AjaxResult edit(Map<String, Object> params);

    AjaxResult add(Map<String, Object> params);

    AjaxResult setDefault(String addressId);
}
