package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrDelBalenceReq;
import com.wangyameng.dto.AddOrDelIntegralReq;
import com.wangyameng.dto.AddOrEditUserReq;

import java.util.List;

/**
 * @author wangyameng
 * @date 2024年05月24日 0:30
 */
public interface UserService {
    AjaxResult getList(String userId, String nickname, String phone, List<String> createTime, String sourceType, Integer page, Integer limit);

    AjaxResult add(AddOrEditUserReq addOrEditUserReq);

    AjaxResult edit(AddOrEditUserReq addOrEditUserReq);

    AjaxResult addOrDelIntegral(AddOrDelIntegralReq addOrDelIntegralReq);

    AjaxResult addOrDelBalance(AddOrDelBalenceReq addOrDelBalenceReq);
}
