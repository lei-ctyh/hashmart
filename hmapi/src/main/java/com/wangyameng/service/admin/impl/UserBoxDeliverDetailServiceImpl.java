package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.UserBoxDeliverDao;
import com.wangyameng.dao.UserBoxDeliverDetailDao;
import com.wangyameng.dao.UserBoxLogDao;
import com.wangyameng.entity.UserBoxDeliver;
import com.wangyameng.entity.UserBoxDeliverDetail;
import com.wangyameng.entity.UserBoxLog;
import com.wangyameng.service.admin.UserBoxDeliverDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserBoxDeliverDetailServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-16 23:02:00
 */
@Service
public class UserBoxDeliverDetailServiceImpl  implements UserBoxDeliverDetailService {
    @Autowired
    private UserBoxDeliverDetailDao userBoxDeliverDetailDao;
    @Autowired
    private UserBoxLogDao userBoxLogDao;
    @Autowired
    private UserBoxDeliverDao userBoxDeliverDao;
    @Override
    public AjaxResult getList(String deliverId, Integer page, Integer limit) {
        LambdaQueryWrapper<UserBoxDeliverDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBoxDeliverDetail::getDeliverId, deliverId);
        queryWrapper.orderByDesc(UserBoxDeliverDetail::getCreateTime);
        Page<UserBoxDeliverDetail> ipage = userBoxDeliverDetailDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());

        JSONArray rows = new JSONArray();
        for (UserBoxDeliverDetail userBoxDeliverDetail : ipage.getRecords()) {
            JSONObject row = PubfuncUtil.parseToUnderlineJson(userBoxDeliverDetail);
            LambdaQueryWrapper<UserBoxLog> rewardQueryWrapper = new LambdaQueryWrapper<>();
            rewardQueryWrapper.eq(UserBoxLog::getUuid, userBoxDeliverDetail.getUserBoxUuid());

            UserBoxLog userBoxLog = userBoxLogDao.selectOne(rewardQueryWrapper);
            if (userBoxLog!= null) {
                userBoxLog.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(userBoxLog.getGoodsImage()));
                row.put("reward", PubfuncUtil.parseToUnderlineJson(userBoxLog));
            }
            rows.add(row);

        }
        rtnJson.put("rows", rows);

        String address_info = "";
        LambdaQueryWrapper<UserBoxDeliver> deliverQueryWrapper = new LambdaQueryWrapper<>();
        deliverQueryWrapper.eq(UserBoxDeliver::getId, deliverId);
        UserBoxDeliver userBoxDeliver = userBoxDeliverDao.selectOne(deliverQueryWrapper);
        if (userBoxDeliver!= null) {
            address_info = userBoxDeliver.getAddressInfo();
        }
        rtnJson.put("address_info", address_info);

        return AjaxResult.dataReturn(0,"success", rtnJson);
    }
}
