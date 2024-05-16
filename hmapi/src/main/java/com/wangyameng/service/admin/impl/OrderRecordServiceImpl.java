package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderRecordDao;
import com.wangyameng.entity.OrderRecord;
import com.wangyameng.service.admin.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName OrderRecordServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-16 23:29:00
 */
@Service
public class OrderRecordServiceImpl  implements OrderRecordService {
    @Autowired
    private OrderRecordDao orderRecordDao;

    @Override
    public AjaxResult getList(Integer userId, String username, List<String> createTime, Integer page, Integer limit) {
        LambdaQueryWrapper<OrderRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq(OrderRecord::getUserId, userId);
        }
        if (username != null) {
            queryWrapper.like(OrderRecord::getUsername, username);
        }
        if (createTime != null) {
            queryWrapper.between(OrderRecord::getCreateTime, createTime.get(0), createTime.get(1));
        }
        queryWrapper.orderByDesc(OrderRecord::getCreateTime);

        Page<OrderRecord> ipage = orderRecordDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        ipage.getRecords().forEach(orderRecord -> {
            rows.add(PubfuncUtil.parseToUnderlineJson(orderRecord));
        });
        rtnJson.put("rows", rows);

        return AjaxResult.dataReturn(0, "success", rtnJson);
    }
}
