package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.UserBoxExchangeDao;
import com.wangyameng.dao.UserBoxExchangeDetailDao;
import com.wangyameng.dao.UserBoxLogDao;
import com.wangyameng.entity.UserBoxExchange;
import com.wangyameng.entity.UserBoxExchangeDetail;
import com.wangyameng.entity.UserBoxLog;
import com.wangyameng.service.admin.BoxExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BoxExchangeServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-16 22:19:00
 */
@Service
public class BoxExchangeServiceImpl implements BoxExchangeService {
    @Autowired
    UserBoxExchangeDao userBoxExchangeDao;
    @Autowired
    UserBoxExchangeDetailDao userBoxExchangeDetailDao;
    @Autowired
    UserBoxLogDao userBoxLogDao;


    @Override
    public AjaxResult getList(Integer userId, String username, String exchangeNo, Integer page, Integer limit) {
        LambdaQueryWrapper<UserBoxExchange> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(UserBoxExchange::getUsername, username);
        }
        if (userId != null) {
            queryWrapper.eq(UserBoxExchange::getUserId, userId);
        }
        if (StringUtils.isNotBlank(exchangeNo)) {
            queryWrapper.like(UserBoxExchange::getExchangeNo, exchangeNo);
        }

        Page<UserBoxExchange> pageInfo = userBoxExchangeDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", pageInfo.getTotal());
        JSONArray rows = new JSONArray();
        for (UserBoxExchange userBoxExchange : pageInfo.getRecords()) {
            rows.add(PubfuncUtil.parseToUnderlineJson(userBoxExchange));
        }
        rtnJson.put("rows", rows);

        return AjaxResult.dataReturn(0,"success", rtnJson);
    }

    @Override
    public AjaxResult getDetail(Integer exchangeId) {
        LambdaQueryWrapper<UserBoxExchangeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBoxExchangeDetail::getExchangeId, exchangeId);
        List<UserBoxExchangeDetail> userBoxExchangeDetailList = userBoxExchangeDetailDao.selectList(queryWrapper);
        if (!userBoxExchangeDetailList.isEmpty()) {
            JSONArray data = new JSONArray();
            userBoxExchangeDetailList.forEach(userBoxExchangeDetail -> {
                JSONObject detailJson = PubfuncUtil.parseToUnderlineJson(userBoxExchangeDetail);

                LambdaQueryWrapper<UserBoxLog> rewardQueryWrapper = new LambdaQueryWrapper<>();
                rewardQueryWrapper.eq(UserBoxLog::getUuid, userBoxExchangeDetail.getUserBoxUuid());
                UserBoxLog userBoxLog = userBoxLogDao.selectOne(rewardQueryWrapper);
                if (userBoxLog!= null) {
                    userBoxLog.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(userBoxLog.getGoodsImage()));
                    detailJson.put("reward", PubfuncUtil.parseToUnderlineJson(userBoxLog));
                }
                data.add(detailJson);

            });
            return AjaxResult.dataReturn(0,"success", data);
        }
        return AjaxResult.dataReturn(1,"未查询到数据", null);
    }
}
