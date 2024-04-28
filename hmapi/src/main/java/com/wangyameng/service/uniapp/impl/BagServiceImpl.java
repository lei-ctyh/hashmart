package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSON;
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
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.*;
import com.wangyameng.dto.BagGoodsDTO;
import com.wangyameng.dto.BoxExchangeDTO;
import com.wangyameng.dto.PayParamDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.BagService;
import com.wangyameng.strategy.pay.PayProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.wangyameng.common.util.pay.WechatPayUtil.generatePayParams;

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
    UserDao userDao;
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
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private UserBoxDeliverDao userBoxDeliverDao;
    @Autowired
    private OrderExpressDao orderExpressDao;
    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private UserBoxDeliverDetailDao userBoxDeliverDetailDao;
    @Autowired
    private PayProvider provider;


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
            case 3: // 已提货
                return getBoxDeliver(page, limit);
            default:
                return AjaxResult.error("参数错误");
        }

    }

    @Override
    public AjaxResult bagBoxExchange(String boxIdStr, int type) {
        List<BoxExchangeDTO> boxExchangeDTOS = new ArrayList<>();
        if (type == 2) {
            MPJLambdaWrapper<UserBoxHot> wrapper = new MPJLambdaWrapper<UserBoxHot>();
            wrapper.selectAll(UserBoxHot.class);
            wrapper.selectAll(OrderRecordDetail.class);
            wrapper.leftJoin(OrderRecordDetail.class, OrderRecordDetail::getId, UserBoxHot::getRecordDetailId);
            wrapper.in(UserBoxHot::getId, Arrays.asList(StringUtils.split(boxIdStr, ",")));
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
        userBoxExchange.setExchangeNo(PubfuncUtil.makeOrderNo("D"));
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
        capitalChangeUtil.addHash(totalAmount, UserSessionContext.get().getInteger("id"));

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

    @Override
    public AjaxResult bagBoxTrail(String boxId, int addressId, int type) {
        if (type == 2) {
            LambdaQueryWrapper<UserBoxHot> wrapper = new LambdaQueryWrapper<>();
            String[] split = StringUtils.split(boxId, ",");
            wrapper.in(UserBoxHot::getId, Arrays.asList(split));
            wrapper.eq(UserBoxHot::getUserId, UserSessionContext.get().getInteger("id"));

            List<UserBoxHot> userBoxHots = userBoxHotDao.selectList(wrapper);
            if (userBoxHots.isEmpty()) {
                return AjaxResult.dataReturn(-3, "该盒子不存在或已兑换");
            }

            List<Integer> goodsList = new ArrayList<>();
            JSONObject bagList = new JSONObject();
            JSONArray idArr = new JSONArray();
            JSONArray uuid = new JSONArray();
            userBoxHots.forEach(userBoxHot -> {
                goodsList.add(userBoxHot.getGoodsId());
                uuid.add(userBoxHot.getUuid());
                idArr.add(userBoxHot.getId());
            });
            bagList.put("id", idArr);
            bagList.put("uuid", uuid);

            // 算最大邮费

            LambdaQueryWrapper<Goods> goodsWrapper = new LambdaQueryWrapper<>();
            goodsWrapper.in(Goods::getId, goodsList);
            goodsWrapper.orderByDesc(Goods::getDeliveryFee);
            Goods goods = goodsDao.selectOne(goodsWrapper);
            if (goods == null) {
                return AjaxResult.dataReturn(-3, "该商品不存在");
            }
            return AjaxResult.dataReturn(0, bagList.toString(), 100.0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult bagBoxDelivery(String boxId, int addressId, int type, String platform, int payWay) {
        AjaxResult trailData = bagBoxTrail(boxId, addressId, type);
        if ((Integer) trailData.get(AjaxResult.CODE_TAG) != 0) {
            return trailData;
        }

        Double deliveryFee = (Double) trailData.get(AjaxResult.DATA_TAG);
        String orderNo = PubfuncUtil.makeOrderNo("F");
        String payNo = PubfuncUtil.makeOrderNo("U");
        OrderExpress orderExpress = new OrderExpress();
        orderExpress.setOrderNo(orderNo);
        orderExpress.setPayNo(payNo);
        orderExpress.setAmount(deliveryFee);
        orderExpress.setPayWay(payWay);
        orderExpress.setBoxType(type);
        orderExpress.setPayStatus(deliveryFee > 0 ? 1 : 2);
        orderExpress.setUserId(UserSessionContext.get().getInteger("id"));
        orderExpress.setUsername(UserSessionContext.get().getString("nickname"));
        orderExpress.setAddressId(addressId);
        orderExpress.setExpressContent((String) trailData.get(AjaxResult.MSG_TAG));
        orderExpress.setCreateTime(new Date());
        if (deliveryFee == 0) {
            orderExpress.setPayTime(new Date());
        }


        orderExpressDao.insert(orderExpress);
        JSONObject orderInfo = JSONObject.from(orderExpress);
        orderInfo.put("express_order_id", orderExpress.getId());

        // 如果已经支付，则直接完成
        if (orderExpress.getPayStatus() == 2) {
            completeExpressOrder(orderExpress, type);
        }

        // 余额支付
        if (payWay == 4) {
            orderExpress.setPayWay(40);
        }

        PayParamDTO payParamDTO = new PayParamDTO();
        payParamDTO.setId(orderExpress.getId());
        payParamDTO.setUser_id(UserSessionContext.get().getInteger("id"));
        payParamDTO.setUsername(UserSessionContext.get().getString("nickname"));
        payParamDTO.setPay_price(orderExpress.getAmount());
        payParamDTO.setHost(PubfuncUtil.getSdParam("api_url", "api_url"));
        payParamDTO.setPay_order_no(orderExpress.getPayNo());
        payParamDTO.setSubject("邮费支付");
        AjaxResult payResult = provider.pay(payParamDTO, orderExpress.getPayWay());

        if (orderExpress.getPayWay() == 40) {
            if ((Integer) payResult.get(AjaxResult.CODE_TAG) != 0) {
                return payResult;
            }
            return completeExpressOrder(orderExpress, type);
        }
        String openId = UserSessionContext.get().getString("openid");
        payParamDTO.setOpenid(openId);
        return provider.pay(payParamDTO, orderExpress.getPayWay());
    }


    /**
     * 完成发货订单
     *
     * @param rderInfo
     * @param type
     * @return
     */
    public AjaxResult completeExpressOrder(OrderExpress rderInfo, int type) {
        JSONObject boxInfo = JSONObject.parseObject(rderInfo.getExpressContent());
        JSONArray boxIds = boxInfo.getJSONArray("id");

        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getId, rderInfo.getUserId());
        User user = userDao.selectOne(userWrapper);

        List<Object> userBagList = new ArrayList<>();
        // 盒子中的奖品数据
        if (type == 1) { // 全部
            LambdaQueryWrapper<UserBoxLog> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(UserBoxLog::getId, boxIds);
            wrapper.eq(UserBoxLog::getUserId, user.getId());
            wrapper.eq(UserBoxLog::getStatus, 1);
            List<UserBoxLog> userBoxLogs = userBoxLogDao.selectList(wrapper);
            if (userBoxLogs.isEmpty()) {
                return AjaxResult.dataReturn(-1, "该奖品不存在");
            }
            userBagList.addAll(userBoxLogs);

        } else { // 盒子中的
            LambdaQueryWrapper<UserBoxHot> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(UserBoxHot::getId, boxIds);
            wrapper.eq(UserBoxHot::getUserId, user.getId());
            List<UserBoxHot> UserBoxHots = userBoxHotDao.selectList(wrapper);
            if (UserBoxHots.isEmpty()) {
                return AjaxResult.dataReturn(-1, "该奖品不存在");
            }
            userBagList.addAll(UserBoxHots);
        }

        // 查询地址信息
        LambdaQueryWrapper<UserAddress> addressWrapper = new LambdaQueryWrapper<>();
        addressWrapper.eq(UserAddress::getId, rderInfo.getAddressId());
        addressWrapper.eq(UserAddress::getUserId, user.getId());
        UserAddress addressInfo = userAddressDao.selectOne(addressWrapper);
        if (addressInfo == null) {
            return AjaxResult.dataReturn(-12, "收货地址信息错误");
        }

        // 1. 提货记录表
        UserBoxDeliver userBoxDeliver = new UserBoxDeliver();

        userBoxDeliver.setDeliverNo(PubfuncUtil.makeOrderNo("T"));
        userBoxDeliver.setExpressOrderId(rderInfo.getId());
        userBoxDeliver.setAddressId(rderInfo.getAddressId());
        userBoxDeliver.setAddressInfo(JSON.toJSONString(addressInfo));
        userBoxDeliver.setUserId(user.getId());
        userBoxDeliver.setType(1);
        userBoxDeliver.setStatus(1);
        userBoxDeliver.setPostageFee(rderInfo.getAmount());
        userBoxDeliver.setCreateTime(new Date());
        userBoxDeliverDao.insert(userBoxDeliver);

        // 2. 记录提货详情
        List<UserBoxDeliverDetail> deliverDetail = new ArrayList();
        List<String> uuids = new ArrayList<>();
        List<Integer> recordDetailIds = new ArrayList<>();
        userBagList.forEach(o -> {
            JSONObject json = PubfuncUtil.parseToUnderlineJson(o);
            uuids.add(json.getString("uuid"));
            recordDetailIds.add(json.getInteger("record_detail_id"));
            UserBoxDeliverDetail userBoxDeliverDetail = new UserBoxDeliverDetail();
            userBoxDeliverDetail.setDeliverId(userBoxDeliver.getId());
            userBoxDeliverDetail.setUserId(user.getId());
            userBoxDeliverDetail.setUserBoxUuid(json.getString("uuid"));
            userBoxDeliverDetail.setRecordDetailId(json.getInteger("record_detail_id"));
            userBoxDeliverDetail.setCreateTime(new Date());
            deliverDetail.add(userBoxDeliverDetail);
        });

        deliverDetail.forEach(userBoxDeliverDetailDao::insert);
        // 3. 删除盒子热点表
        LambdaQueryWrapper<UserBoxHot> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.in(UserBoxHot::getUuid, uuids);
        userBoxHotDao.delete(deleteWrapper);

        // 4. 更新盒子记录表
        LambdaQueryWrapper<UserBoxLog> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in(UserBoxLog::getUuid, uuids);
        userBoxLogDao.selectList(updateWrapper).forEach(userBoxLog -> {
            userBoxLog.setStatus(3);
            userBoxLog.setUpdateTime(new Date());
            userBoxLogDao.updateById(userBoxLog);
        });

        return AjaxResult.dataReturn(0, "支付成功");
    }

    private AjaxResult getAllBagGoods(int page, int limit, int status) {
        MPJLambdaWrapper<UserBoxLog> wrapper = new MPJLambdaWrapper<UserBoxLog>().selectAll(UserBoxLog.class).selectAll(OrderRecordDetail.class).leftJoin(OrderRecordDetail.class, OrderRecordDetail::getId, UserBoxLog::getRecordDetailId);

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
        LambdaQueryWrapper<UserBoxHot> wrapper = new LambdaQueryWrapper<>();
        Integer id = UserSessionContext.get().getInteger("id");
        wrapper.orderByDesc(UserBoxHot::getId);
        wrapper.eq(UserBoxHot::getUserId, id);

        Page<UserBoxHot> iPage = userBoxHotDao.selectPage(new Page<>(page, limit), wrapper);
        JSONArray jsonArr = new JSONArray();
        iPage.getRecords().forEach(userBoxHot -> {
            // 'id','goods_name','goods_image','exchange_time','exchange_time','create_time','recovery_price'
            JSONObject json = new JSONObject();
            json.put("id", userBoxHot.getId());
            json.put("goods_name", userBoxHot.getGoodsName());
            json.put("goods_image", PubfuncUtil.replaceBecomeServerHost(userBoxHot.getGoodsImage()));
            json.put("exchange_time", userBoxHot.getExchangeTime());
            json.put("create_time", userBoxHot.getCreateTime());
            LambdaQueryWrapper<OrderRecordDetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(OrderRecordDetail::getId, userBoxHot.getRecordDetailId());
            OrderRecordDetail orderRecordDetail = orderRecordDetailDao.selectOne(detailWrapper);
            json.put("recovery_price", orderRecordDetail.getGoodsPrice());
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
        LambdaQueryWrapper<UserBoxExchange> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBoxExchange::getUserId, UserSessionContext.get().getInteger("id"));
        wrapper.orderByDesc(UserBoxExchange::getId);
        IPage<UserBoxExchange> iPage = userBoxExchangeDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", iPage.getTotal());
        rtnJson.put("per_page", iPage.getSize());
        rtnJson.put("current_page", iPage.getCurrent());
        rtnJson.put("last_page", iPage.getPages());
        JSONArray data = new JSONArray();
        iPage.getRecords().forEach(userBoxExchange -> {
            JSONObject json = new JSONObject();
            json.put("id", userBoxExchange.getId());
            json.put("exchange_no", userBoxExchange.getExchangeNo());
            json.put("exchange_time", userBoxExchange.getCreateTime());
            List<UserBoxExchangeDetail> userBoxExchangeDetails = userBoxExchangeDetailDao.selectList(new LambdaQueryWrapper<UserBoxExchangeDetail>().eq(UserBoxExchangeDetail::getExchangeId, userBoxExchange.getId()));
            JSONArray detailArr = new JSONArray();
            userBoxExchangeDetails.forEach(userBoxExchangeDetail -> {
                JSONObject detailJson = new JSONObject();
                detailJson.put("id", userBoxExchangeDetail.getId());
                detailJson.put("exchange_id", userBoxExchangeDetail.getExchangeId());

                // 奖品信息
                LambdaQueryWrapper<OrderRecordDetail> recordDetailWrapper = new LambdaQueryWrapper<>();
                recordDetailWrapper.eq(OrderRecordDetail::getId, userBoxExchangeDetail.getRecordDetailId());
                OrderRecordDetail orderRecordDetail = orderRecordDetailDao.selectOne(recordDetailWrapper);
                JSONObject reward = new JSONObject();
                reward.put("id", orderRecordDetail.getId());
                reward.put("goods_id", orderRecordDetail.getGoodsId());
                reward.put("goods_name", orderRecordDetail.getGoodsName());
                reward.put("goods_image", PubfuncUtil.replaceBecomeServerHost(orderRecordDetail.getGoodsImage()));
                reward.put("recovery_price", orderRecordDetail.getGoodsPrice());
                detailJson.put("reward", reward);
                detailArr.add(detailJson);
            });
            json.put("detail", detailArr);
            data.add(json);
        });
        rtnJson.put("data", data);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }


    /**
     * 已提货
     */
    private AjaxResult getBoxDeliver(int page, int limit) {
        LambdaQueryWrapper<UserBoxDeliver> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBoxDeliver::getUserId, UserSessionContext.get().getInteger("id"));
        wrapper.orderByDesc(UserBoxDeliver::getId);
        IPage<UserBoxDeliver> iPage = userBoxDeliverDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", iPage.getTotal());
        rtnJson.put("per_page", iPage.getSize());
        rtnJson.put("current_page", iPage.getCurrent());
        rtnJson.put("last_page", iPage.getPages());
        JSONArray data = new JSONArray();
        iPage.getRecords().forEach(userBoxDeliver -> {
            JSONObject json = new JSONObject();
            json.put("id", userBoxDeliver.getId());
            json.put("deliver_no", userBoxDeliver.getDeliverNo());
            json.put("status", userBoxDeliver.getStatus());
            json.put("deliver_time", userBoxDeliver.getCreateTime());
            List<UserBoxDeliverDetail> userBoxDeliverDetails = userBoxDeliverDetailDao.selectList(new LambdaQueryWrapper<UserBoxDeliverDetail>().eq(UserBoxDeliverDetail::getDeliverId, userBoxDeliver.getId()).orderByDesc(UserBoxDeliverDetail::getId));
            JSONArray detailArr = new JSONArray();
            userBoxDeliverDetails.forEach(userBoxDeliverDetail -> {
                JSONObject detailJson = new JSONObject();
                detailJson.put("user_box_uuid", userBoxDeliverDetail.getUserBoxUuid());
                OrderRecordDetail orderRecordDetail = orderRecordDetailDao.selectById(userBoxDeliverDetail.getRecordDetailId());
                JSONObject rewardSimpleJson = new JSONObject();
                rewardSimpleJson.put("goods_name", orderRecordDetail.getGoodsName());
                rewardSimpleJson.put("goods_image", PubfuncUtil.replaceBecomeServerHost(orderRecordDetail.getGoodsImage()));
                rewardSimpleJson.put("goods_id", orderRecordDetail.getGoodsId());
                detailJson.put("rewardSimple", rewardSimpleJson);
                detailArr.add(detailJson);
            });
            json.put("detail", detailArr);
            data.add(json);
        });
        rtnJson.put("data", data);
        return AjaxResult.dataReturn(0, "success", rtnJson);
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
