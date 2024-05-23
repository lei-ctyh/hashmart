package com.wangyameng.strategy.lottery.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.*;
import com.wangyameng.dto.OrderParamDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.BlindboxService;
import com.wangyameng.strategy.lottery.LotteryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("hashLotteryStrategy")
public class HashLotteryStrategyImpl implements LotteryStrategy {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BlindboxService blindboxService;
    @Autowired
    private OrderRecordDao orderRecordDao;
    @Autowired
    private OrderRecordDetailDao orderRecordDetailDao;
    @Autowired
    private UserBoxLogDao userBoxLogDao;
    @Autowired
    private UserBoxHotDao userBoxHotDao;
    @Override
    public AjaxResult draw(OrderParamDTO orderParamDTO) {
        /* String nickName = UserSessionContext.get().getString("nickname");
        String hashKey = UserSessionContext.get().getString("hashKey"); */
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, orderParamDTO.getUserId());
        User user = userDao.selectOne(queryWrapper);
        if (user == null) {
            return AjaxResult.dataReturn(-11, "用户不存在");
        }
        String nickName = user.getNickname();
        String hashKey = user.getHashKey();

        AjaxResult BlindboxDetailR = blindboxService.getBlindboxDetail(String.valueOf(orderParamDTO.getBlindboxId()));
        JSONObject BlindboxDetail= (JSONObject) BlindboxDetailR.get(AjaxResult.DATA_TAG);
        if (BlindboxDetail == null) {
            return AjaxResult.dataReturn(-11, "盲盒数据错误");
        }
        JSONArray goodsList = BlindboxDetail.getJSONArray("list");
        OrderRecord orderRecord = new OrderRecord();

        orderRecord.setOrderId(orderParamDTO.getId());
        orderRecord.setBlindboxId(orderParamDTO.getBlindboxId());
        orderRecord.setUserId(orderParamDTO.getUserId());
        orderRecord.setUsername(nickName);
        // 后面更新
        orderRecord.setAwardNum(0);
        // 后面更新
        orderRecord.setTotalAmount(0.00);
        // 后面更新
        orderRecord.setTotalExchangeIntegral(0.00);
        // 后面更新
        orderRecord.setTotalProfit(0.00);
        orderRecord.setCreateTime(new Date());


        orderRecordDao.insert(orderRecord);

        Integer awardNum = 0;
        Double totalAmount = 0.00;
        Double totalExchangeIntegral = 0.00;
        Double totalProfit = 0.00;
        int ratio = 1;
        String ratioStr = PubfuncUtil.getSdParam("sys_base", "change_ratio");
        try {
            ratio = Integer.parseInt(ratioStr);
            if (ratio <= 0) {
                ratio = 1;
            }
        } catch (Exception e) {
            return AjaxResult.dataReturn(-11, "哈希币兑换比例配置错误");
        }


        List<UserBoxHot> userBoxHotList = new ArrayList<>();
        List<UserBoxLog> userBoxLogList = new ArrayList<>();


        for (int i = 0; i < orderParamDTO.getTotalNum(); i++) {
            long timeMillis = System.currentTimeMillis();
            int hashNo = makeHashNo(hashKey,timeMillis);

            totalAmount += orderParamDTO.getUnitPrice();
            int lottery_min_no =-1;
            int lottery_max_no = -1;
            JSONObject winGood = null;
            for (Object o : goodsList) {
                JSONObject good = (JSONObject) o;
                 lottery_min_no = good.getInteger("lottery_min_no");
                 lottery_max_no = good.getInteger("lottery_max_no");
                int recovery_price = good.getInteger("recovery_price");
                if (lottery_min_no < hashNo && lottery_max_no > hashNo) {
                    winGood = good;
                    awardNum += 1;
                    totalExchangeIntegral += recovery_price; // 兑换的哈希币金额
                    break;
                }
            }
            if (winGood == null) {
                return AjaxResult.dataReturn(-15, "盲盒商品配置错误");
            }

            OrderRecordDetail orderRecordDetail = new OrderRecordDetail();
            orderRecordDetail.setOrderRecordId(orderRecord.getId());
            orderRecordDetail.setOrderId(orderParamDTO.getId());
            orderRecordDetail.setUserId(orderParamDTO.getUserId());
            orderRecordDetail.setUserName(nickName);
            orderRecordDetail.setHashKey(hashKey);
            orderRecordDetail.setOrderTime(String.valueOf(timeMillis));
            orderRecordDetail.setHashNo(hashNo);
            orderRecordDetail.setTagId(winGood.getInteger("tag_id"));
            orderRecordDetail.setGoodsId(winGood.getInteger("goods_id"));
            orderRecordDetail.setGoodsImage(winGood.getString("goods_image"));
            orderRecordDetail.setGoodsName(winGood.getString("goods_name"));
            orderRecordDetail.setUnitPrice(orderParamDTO.getUnitPrice());
            orderRecordDetail.setGoodsPrice(winGood.getDouble("price"));
            orderRecordDetail.setRecoveryPrice(winGood.getDouble("recovery_price"));
            orderRecordDetail.setProfit(winGood.getDouble("recovery_price") / ratio - orderParamDTO.getUnitPrice());
            orderRecordDetail.setRatio(ratio);
            orderRecordDetail.setStatus(1);
            orderRecordDetail.setRange(lottery_min_no + "," + lottery_max_no);
            orderRecordDetail.setCreateTime(new Date());
            orderRecordDetailDao.insert(orderRecordDetail);

            Integer detailId = orderRecordDetail.getId();

            // 放入盒子
            String uuid = UUID.randomUUID().toString().replace("-", "");
            UserBoxLog userBoxLog = new UserBoxLog();
            userBoxLog.setUserId(orderParamDTO.getUserId());
            userBoxLog.setBlindboxId(orderParamDTO.getBlindboxId());
            userBoxLog.setRecordDetailId(detailId);
            userBoxLog.setOutTradeNo(orderParamDTO.getOrderNo());
            userBoxLog.setGoodsId(winGood.getInteger("goods_id"));
            userBoxLog.setGoodsImage(winGood.getString("goods_image"));
            userBoxLog.setGoodsName(winGood.getString("goods_name"));
            userBoxLog.setStatus(1);
            userBoxLog.setUuid(uuid);
            userBoxLog.setCreateTime(new Date());
            userBoxLogList.add(userBoxLog);
            // 放入热门榜
            UserBoxHot userBoxHot = new UserBoxHot();
            userBoxHot.setUserId(orderParamDTO.getUserId());
            userBoxHot.setBlindboxId(orderParamDTO.getBlindboxId());
            userBoxHot.setRecordDetailId(detailId);
            userBoxHot.setGoodsId(winGood.getInteger("goods_id"));
            userBoxHot.setGoodsImage(winGood.getString("goods_image"));
            userBoxHot.setGoodsName(winGood.getString("goods_name"));
            userBoxHot.setStatus(1);
            userBoxHot.setUuid(uuid);
            userBoxHot.setCreateTime(new Date());
            userBoxHotList.add(userBoxHot);
        }

        // 更新抽奖记录的盈亏
        orderRecord.setAwardNum(awardNum);
        orderRecord.setTotalAmount(totalAmount);
        orderRecord.setTotalExchangeIntegral(totalExchangeIntegral);
        orderRecord.setTotalProfit(totalExchangeIntegral / ratio - totalAmount);
        orderRecordDao.updateById(orderRecord);

        // 批量插入盒子日志
        userBoxLogList.forEach(userBoxLogDao::insert);
        // 批量插入热门榜
        userBoxHotList.forEach(userBoxHotDao::insert);


        return AjaxResult.dataReturn(0, "抽奖成功", orderRecord.getId());
    }

    public static int makeHashNo( String hashKey, long tillTime) {
        long seed = tillTime + hashKey.hashCode();
        System.out.println(seed+"当前种子");
        Random random = new Random(seed);
        return random.nextInt(1048575);
    }
}
