package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.service.uniapp.BlindboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-27 14:33:00
 */
@Service
public class BlindboxServiceImpl implements BlindboxService {
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;

    @Override
    public AjaxResult getBlindboxDetail(String id) {
        JSONArray rtnDetails = new JSONArray();
        LambdaQueryWrapper<BlindboxDetail> detailQueryWrapper = new LambdaQueryWrapper<>();
        detailQueryWrapper.orderByAsc(BlindboxDetail::getProbability);
        List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(detailQueryWrapper);
        if (blindboxDetails == null) {
            return AjaxResult.dataReturn(-1, "该盲盒不存在");
        }
        for (BlindboxDetail blindboxDetail : blindboxDetails) {
            JSONObject detail = new JSONObject();
            blindboxDetail.getPrice();
        }
        return AjaxResult.dataReturn(0,"success",blindboxDetails);
    }
}
