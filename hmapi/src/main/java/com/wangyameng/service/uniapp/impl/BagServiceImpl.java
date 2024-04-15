package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.leheyue.wrapper.MPJLambdaWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.*;
import com.wangyameng.dto.BagGoodsDTO;
import com.wangyameng.dto.BoxExchangeDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BagServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-10 11:36:00
 */
@Service
public class BagServiceImpl implements BagService {
    @Autowired
    private UserBoxLogDao userBoxLogDao;
    @Autowired
    private UserBoxHotDao userBoxHotDao;
    @Autowired
    private UserBoxExchangeDao userBoxExchangeDao;
    @Autowired
    private UserBoxExchangeDetailDao userBoxExchangeDetailDao;
    @Autowired
    private UserIntegralChangeLogDao userIntegralChangeLogDao;
    @Autowired
    private CapitalChangeUtil capitalChangeUtil;
    @Autowired
    private OrderRecordDetailDao orderRecordDetailDao;


    @Override
    public AjaxResult getBagBoxList(int page, int limit, int status) {
        Integer id = UserSessionContext.get().getInteger("id");
        switch (status) {
            case 0:
                return getAllBagGoods(page, limit, status);
            case 1: // 盒子中//
                return getBoxGoods(page, limit);
            case 2: // 已兑换
                return getBoxExchange(page, limit);
            default:
                return AjaxResult.error("参数错误");
        }

    }

    @Override
    public AjaxResult bagBoxExchange(int boxId, int type) {
        List<BoxExchangeDTO> boxExchangeDTOS = new ArrayList<>();
        if (type == 2) {
            MPJLambdaWrapper<UserBoxHot> wrapper = new MPJLambdaWrapper<UserBoxHot>();
            wrapper.selectAll(UserBoxHot.class);
            wrapper.selectAll(OrderRecordDetail.class);
            wrapper.leftJoin(OrderRecordDetail.class, OrderRecordDetail::getId, UserBoxHot::getRecordDetailId);
            wrapper.in(UserBoxHot::getId, boxId);
            wrapper.eq(UserBoxHot::getUserId, UserSessionContext.get().getInteger("id"));

            boxExchangeDTOS = userBoxHotDao.selectJoinList(BoxExchangeDTO.class, wrapper);
            if (boxExchangeDTOS.isEmpty()) {
                return AjaxResult.dataReturn(-1, "该盒子不存在或已兑换");
            }


        }


        Double totalAmount = 0.0;
        List<String> uuids = new ArrayList<>();
        List<Integer> recordDetailIds = new ArrayList<>();
        for (BoxExchangeDTO boxExchangeDTO : boxExchangeDTOS) {
            totalAmount += boxExchangeDTO.getRecoveryPrice();
            uuids.add(boxExchangeDTO.getUuid());
            recordDetailIds.add(boxExchangeDTO.getRecordDetailId());
        }

        // 1. 记录兑换表
        UserBoxExchange userBoxExchange = new UserBoxExchange();
        userBoxExchange.setExchangeNo(PubfuncUtil.makeOrderNo("E"));
        userBoxExchange.setUserId(UserSessionContext.get().getInteger("id"));
        userBoxExchange.setUsername(UserSessionContext.get().getString("nickname"));
        userBoxExchange.setExchangeNum(boxExchangeDTOS.size());
        userBoxExchange.setTotalAmount(totalAmount);
        userBoxExchange.setStatus(2);
        userBoxExchange.setCreateTime(new Date());
        userBoxExchangeDao.insert(userBoxExchange);

        // 2. 记录对象记录详情表
        for (BoxExchangeDTO boxExchangeDTO : boxExchangeDTOS) {
            UserBoxExchangeDetail userBoxExchangeDetail = new UserBoxExchangeDetail();
            userBoxExchangeDetail.setExchangeId(userBoxExchange.getId());
            userBoxExchangeDetail.setUserId(UserSessionContext.get().getInteger("id"));
            userBoxExchangeDetail.setUserBoxUuid(boxExchangeDTO.getUuid());
            userBoxExchangeDetail.setRecordDetailId(boxExchangeDTO.getRecordDetailId());
            userBoxExchangeDetail.setGoodsId(boxExchangeDTO.getGoodsId());
            userBoxExchangeDetail.setNum(1);
            userBoxExchangeDetail.setAmount(boxExchangeDTO.getRecoveryPrice());
            userBoxExchangeDetail.setCreateTime(new Date());
            userBoxExchangeDetailDao.insert(userBoxExchangeDetail);
        }

        // 3、记录用户哈希币变动
        UserIntegralChangeLog userIntegralChangeLog = new UserIntegralChangeLog();
        userIntegralChangeLog.setUserId(UserSessionContext.get().getInteger("id"));
        userIntegralChangeLog.setUsername(UserSessionContext.get().getString("nickname"));
        userIntegralChangeLog.setIntegral(totalAmount);
        userIntegralChangeLog.setType(1);
        userIntegralChangeLog.setExchangeId(userBoxExchange.getId());
        userIntegralChangeLog.setCreateTime(new Date());
        userIntegralChangeLogDao.insert(userIntegralChangeLog);

        // 4. 删除热点盒子记录
        LambdaQueryWrapper<UserBoxHot> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.in(UserBoxHot::getUuid, uuids);
        userBoxHotDao.delete(deleteWrapper);

        // 5. 更新用户盒子记录表
        LambdaQueryWrapper<UserBoxLog> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in(UserBoxLog::getUuid, uuids);
        userBoxLogDao.selectList(updateWrapper).forEach(userBoxLog -> {
            userBoxLog.setStatus(2);
            userBoxLog.setUpdateTime(new Date());
            userBoxLogDao.updateById(userBoxLog);
        });
        // 6. 增加用户积分
        capitalChangeUtil.addHash( totalAmount,UserSessionContext.get().getInteger("id"));

        // 7、标记中奖详情状态
        LambdaQueryWrapper<OrderRecordDetail> updateRecordDetailWrapper = new LambdaQueryWrapper<>();
        updateRecordDetailWrapper.in(OrderRecordDetail::getId, recordDetailIds);
        orderRecordDetailDao.selectList(updateRecordDetailWrapper).forEach(orderRecordDetail -> {
            orderRecordDetail.setStatus(2);
            orderRecordDetail.setUpdateTime(new Date());
            orderRecordDetailDao.updateById(orderRecordDetail);
        });
        return AjaxResult.dataReturn(0, "兑换成功");
    }

    private AjaxResult getAllBagGoods(int page, int limit, int status) {
        MPJLambdaWrapper<UserBoxLog> wrapper = new MPJLambdaWrapper<UserBoxLog>()
                .selectAll(UserBoxLog.class)
                .selectAll(OrderRecordDetail.class)
                .leftJoin(OrderRecordDetail.class, OrderRecordDetail::getId, UserBoxLog::getRecordDetailId);

        Integer id = UserSessionContext.get().getInteger("id");
        wrapper.orderByDesc(UserBoxLog::getId);
        wrapper.eq(UserBoxLog::getUserId, id);

        Page<BagGoodsDTO> iPage = userBoxLogDao.selectJoinPage(new Page<>(page, limit), BagGoodsDTO.class, wrapper);
        iPage.getRecords().forEach(bagGoodsDTO -> {
            bagGoodsDTO.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(bagGoodsDTO.getGoodsImage()));
        });

        JSONObject rtnData = getIpgData(iPage, null);
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    /**
     * 盒子中
     *
     * @param page
     * @param limit
     * @return
     */
    private AjaxResult getBoxGoods(int page, int limit) {
        MPJLambdaWrapper<UserBoxHot> wrapper = new MPJLambdaWrapper<UserBoxHot>()
                .selectAll(UserBoxHot.class)
                .selectAll(OrderRecordDetail.class)
                .leftJoin(OrderRecordDetail.class, OrderRecordDetail::getId, UserBoxHot::getRecordDetailId);

        Integer id = UserSessionContext.get().getInteger("id");
        wrapper.orderByDesc(UserBoxHot::getId);
        wrapper.eq(UserBoxHot::getUserId, id);

        Page<BagGoodsDTO> iPage = userBoxHotDao.selectJoinPage(new Page<>(page, limit), BagGoodsDTO.class, wrapper);
        JSONArray jsonArr = new JSONArray();
        iPage.getRecords().forEach(bagGoodsDTO -> {
            // 'id','goods_name','goods_image','exchange_time','exchange_time','create_time','recovery_price'
            JSONObject json = new JSONObject();
            json.put("id", bagGoodsDTO.getId());
            json.put("goods_name", bagGoodsDTO.getGoodsName());
            json.put("goods_image", PubfuncUtil.replaceBecomeServerHost(bagGoodsDTO.getGoodsImage()));
            json.put("exchange_time", bagGoodsDTO.getExchangeTime());
            json.put("create_time", bagGoodsDTO.getCreateTime());
            json.put("recovery_price", bagGoodsDTO.getRecoveryPrice());
            jsonArr.add(json);
        });

        JSONObject rtnData = getIpgData(iPage, jsonArr);
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    /**
     * 盒子兑换
     *
     * @param page
     * @param limit
     */
    private AjaxResult getBoxExchange(int page, int limit) {
        JSONObject json = JSONObject.parseObject("{\n" +
                "        \"total\": 3,\n" +
                "        \"per_page\": 10,\n" +
                "        \"current_page\": 1,\n" +
                "        \"last_page\": 1,\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"exchange_no\": \"EXCH-001\",\n" +
                "                \"exchange_time\": \"2024-04-15 10:00:00\",\n" +
                "                \"detail\": [\n" +
                "                    {\n" +
                "                        \"id\": 1,\n" +
                "                        \"exchange_id\": 1,\n" +
                "                        \"reward\": {\n" +
                "                            \"goods_id\":1111,\n" +
                "                            \"goods_name\": 1111,\n" +
                "                            \"goods_image\": 1111\n" +
                "                        }\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 2,\n" +
                "                        \"exchange_id\": 1,\n" +
                "                        \"reward\": {}\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"exchange_no\": \"EXCH-002\",\n" +
                "                \"exchange_time\": \"2024-04-14 15:30:00\",\n" +
                "                \"detail\": [\n" +
                "                    {\n" +
                "                        \"id\": 3,\n" +
                "                        \"exchange_id\": 2,\n" +
                "                        \"reward\": {}\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }");
        return AjaxResult.dataReturn(0, "success", json);
    }


    /**
     * 已提货
     */
    private AjaxResult getBoxDeliver(Integer id, int page, int limit, int status) {

        JSONObject json = JSONObject.parseObject("{\n" +
                "        \"total\": 3,\n" +
                "        \"per_page\": 10,\n" +
                "        \"current_page\": 1,\n" +
                "        \"last_page\": 1,\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"exchange_no\": \"EXCH-001\",\n" +
                "                \"exchange_time\": \"2024-04-15 10:00:00\",\n" +
                "                \"detail\": [\n" +
                "                    {\n" +
                "                        \"id\": 1,\n" +
                "                        \"exchange_id\": 1,\n" +
                "                        \"reward\": {}\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 2,\n" +
                "                        \"exchange_id\": 1,\n" +
                "                        \"reward\": {}\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"exchange_no\": \"EXCH-002\",\n" +
                "                \"exchange_time\": \"2024-04-14 15:30:00\",\n" +
                "                \"detail\": [\n" +
                "                    {\n" +
                "                        \"id\": 3,\n" +
                "                        \"exchange_id\": 2,\n" +
                "                        \"reward\": {}\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }");
        return AjaxResult.dataReturn(0, "success", json);
    }

    private <T> JSONObject getIpgData(IPage<T> iPage, JSONArray data) {
        JSONObject rtnData = new JSONObject();
        rtnData.put("total", iPage.getTotal());
        rtnData.put("per_page", iPage.getSize());
        rtnData.put("current_page", iPage.getCurrent());
        rtnData.put("last_page", iPage.getPages());
        if (data == null || data.isEmpty()) {
            rtnData.put("data", PubfuncUtil.paginate(iPage));
        } else {
            rtnData.put("data", data);
        }
        return rtnData;
    }
}
