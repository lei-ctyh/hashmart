package com.wangyameng.service.admin;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBlindboxDetailReq;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxDetailService.java
 * @Description TODO
 * @createTime 2024-05-14 22:26:00
 */
public interface BlindboxDetailService {
    AjaxResult getBlindboxDetailList(Integer blindboxId, Integer tagId, String goodsName, Integer page, Integer limit);

    AjaxResult getLotteryProbability(Integer blindboxId, Integer detailId);

    AjaxResult getLotteryNumRange(Integer blindboxId, Double percent, Integer detailId);

    AjaxResult addBlindboxDetail(AddOrEditBlindboxDetailReq req);

    AjaxResult editBlindboxDetail(AddOrEditBlindboxDetailReq req);

    AjaxResult delBlindboxDetail(Integer id, Integer blindboxId);
}
