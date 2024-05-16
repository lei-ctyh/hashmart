package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.UserBoxDeliverDao;
import com.wangyameng.dao.UserBoxDeliverDetailDao;
import com.wangyameng.entity.UserBoxDeliver;
import com.wangyameng.service.admin.UserBoxDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserBoxDeliverServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-16 22:49:00
 */
@Service
public class UserBoxDeliverServiceImpl implements UserBoxDeliverService {
    @Autowired
    private UserBoxDeliverDetailDao userBoxDeliverDetailDao;

    @Autowired
    private UserBoxDeliverDao userBoxDeliverDao;
    @Override
    public AjaxResult getList(String deliverNo, String status, String userId, Integer page, Integer limit) {
        LambdaQueryWrapper<UserBoxDeliver> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(deliverNo)) {
            queryWrapper.eq(UserBoxDeliver::getDeliverNo, deliverNo);
        }
        if (StringUtils.isNotBlank(status)) {
            queryWrapper.eq(UserBoxDeliver::getStatus, status);
        }
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.eq(UserBoxDeliver::getUserId, userId);
        }
        queryWrapper.orderByDesc(UserBoxDeliver::getCreateTime);
        Page<UserBoxDeliver> ipage = userBoxDeliverDao.selectPage(new Page<UserBoxDeliver>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (UserBoxDeliver userBoxDeliver : ipage.getRecords()) {
            rows.add(PubfuncUtil.parseToUnderlineJson(userBoxDeliver));
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }
}
