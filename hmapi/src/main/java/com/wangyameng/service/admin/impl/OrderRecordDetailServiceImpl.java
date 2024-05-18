package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderRecordDetailDao;
import com.wangyameng.entity.OrderRecordDetail;
import com.wangyameng.service.admin.OrderRecordDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName OrderRecordDetailServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-17 00:11:00
 */
@Service
public class OrderRecordDetailServiceImpl implements OrderRecordDetailService {
    @Autowired
    private OrderRecordDetailDao orderRecordDetailDao;
    @Override
    public AjaxResult getList(Integer orderRecordId, Integer page, Integer limit) {

        LambdaQueryWrapper<OrderRecordDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderRecordDetail::getOrderRecordId, orderRecordId);
        wrapper.orderByAsc(OrderRecordDetail::getId);
        Page<OrderRecordDetail> ipage = orderRecordDetailDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (OrderRecordDetail orderRecordDetail : ipage.getRecords()) {
            orderRecordDetail.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(orderRecordDetail.getGoodsImage()));
            rows.add(PubfuncUtil.parseToUnderlineJson(orderRecordDetail));
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }
}
