package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.leheyue.base.MPJBaseMapper;
import com.github.leheyue.wrapper.MPJLambdaWrapper;
import com.wangyameng.common.constant.Constants;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.*;
import com.wangyameng.dto.OrderParamDTO;
import com.wangyameng.dto.OrderRecordDTO;
import com.wangyameng.dto.PayParamDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.OrderService;
import com.wangyameng.strategy.lottery.LotteryProvider;
import com.wangyameng.strategy.pay.PayProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangyameng.common.core.AjaxResult.CODE_TAG;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName OrderServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-11 10:45:00
 */
@Service("uniappOrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private BlindboxDao blindboxDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CapitalChangeUtil capitalChangeUtil;
    @Autowired
    private LotteryProvider lotteryProvider;
    @Autowired
    private OrderRecordDetailDao orderRecordDetailDao;
    @Autowired
    private OrderRecordDao orderRecordDao;
    @Autowired
    private BlindboxTagDao blindboxTagDao;
    @Autowired
    private PayProvider provider;


    @Override
    public AjaxResult trail(Integer blindboxId, Integer num, Integer useIntegral, Integer type) {
        // 查询盲盒信息
        Blindbox blindbox = blindboxDao.selectById(blindboxId);
        if (blindbox == null) {
            return AjaxResult.dataReturn(-1, "盲盒已下架");
        }
        // 查询用户余额
        Integer userId = UserSessionContext.get().getInteger("id");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        queryWrapper.eq(User::getStatus, 1);
        User user = userDao.selectOne(queryWrapper);
        if (user == null) {
            return AjaxResult.dataReturn(-1, "用户信息不存在");
        }

        BigDecimal priceBd = BigDecimal.valueOf(blindbox.getPrice());
        BigDecimal numBigBd = BigDecimal.valueOf(num);
        BigDecimal totalPrice = priceBd.multiply(numBigBd);

        // 可使用的积分
        BigDecimal canUseIntegral = BigDecimal.valueOf(0L);
        // 获取用户可用余额
        BigDecimal canDeductionAmount = BigDecimal.valueOf(0L);
        // 获取兑换比例为 多少ratio兑换成一元人民币，ratio最低为1
        BigDecimal ratio = BigDecimal.valueOf(Long.parseLong(PubfuncUtil.getSdParam("sys_base", "change_ratio")));
        if (ratio.compareTo(BigDecimal.ONE) < 0) {
            return AjaxResult.dataReturn(-1, "Hash币兑换比例异常，请联系管理人员");
        }
        if (user.getIntegral() > 0) {
            // 可抵扣金额
            canDeductionAmount = BigDecimal.valueOf(user.getIntegral()).divide(ratio, 2, RoundingMode.DOWN);

            // 如果可抵扣金额大于订单金额
            if (canDeductionAmount.compareTo(totalPrice) > 0) {
                canUseIntegral = totalPrice.multiply(ratio);
                canDeductionAmount = totalPrice;
                if (useIntegral == 1) {
                    totalPrice = BigDecimal.valueOf(0L);
                }

            } else {
                if (useIntegral == 1) {
                    totalPrice = totalPrice.subtract(canDeductionAmount);
                }
                canUseIntegral = BigDecimal.valueOf(user.getIntegral());
            }

        }
        JSONObject trailData = new JSONObject();
        trailData.put("blindbox_img", PubfuncUtil.replaceBecomeServerHost(blindbox.getPic()));
        trailData.put("price", blindbox.getPrice());
        trailData.put("num", num);
        trailData.put("total_price", totalPrice);
        trailData.put("balance", user.getBalance());
        trailData.put("canUseIntegral", canUseIntegral);
        trailData.put("canDeductionAmount", canDeductionAmount);

        if (type == 2) {
            trailData.put("ratio", ratio);
        }
        return AjaxResult.dataReturn(0, "success", trailData);
    }

    @Override
    public AjaxResult createOrder(Integer blindboxId, Integer num, Integer useIntegral, Integer payWay) {
        AjaxResult trail = trail(blindboxId, num, useIntegral, 2);
        if ((Integer) trail.get(CODE_TAG) != 0) {
            return trail;
        }
        String orderNo = PubfuncUtil.makeOrderNo("O");
        String payOrderNo = PubfuncUtil.makeOrderNo("B");
        double postage = 0;
        Blindbox blindbox = blindboxDao.selectById(blindboxId);
        Integer userId = UserSessionContext.get().getInteger("id");
        String nickName = UserSessionContext.get().getString("nickname");
        JSONObject trailData = (JSONObject) trail.get(AjaxResult.DATA_TAG);

        Order order = new Order();
        order.setPid(0);
        order.setType(2);
        order.setOrderNo(orderNo);
        order.setPayOrderNo(payOrderNo);
        order.setUserId(userId);
        order.setUserName(nickName);
        order.setBlindboxId(blindboxId);
        order.setBoxId(1);
        order.setPlayId(blindbox.getPlayId());
        order.setTotalNum(num);
        order.setUnitPrice(blindbox.getPrice());
        order.setPostage(postage);
        order.setOrderPrice(trailData.getDouble("total_price"));
        order.setPayWay(payWay);
        order.setPayPrice(trailData.getDouble("total_price") + postage);
        order.setPayIntegral(trailData.getDouble("canUseIntegral"));
        order.setPayStatus(1);
        order.setStatus(1);
        order.setIntegralRatio(trailData.getInteger("ratio"));
        order.setCreateTime(new Date());
        orderDao.insert(order);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_no", orderNo);


        return AjaxResult.dataReturn(0, "创建成功", jsonObject);
    }

    @Override
    public AjaxResult payOrder(String orderNo, String platform) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(platform)) {
            return AjaxResult.dataReturn(-1, "参数错误");
        }
        String host = PubfuncUtil.getSdParam("api_url", "api_url");
        // 验证订单信息
        Order order = orderDao.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNo, orderNo)
                .eq(Order::getPayStatus, 1));

        if (order == null) {
            return AjaxResult.dataReturn(-1, "订单信息错误");
        }

        OrderParamDTO orderParamDTO = new OrderParamDTO();
        orderParamDTO.setId(order.getId());
        orderParamDTO.setTotalNum(order.getTotalNum());
        orderParamDTO.setUnitPrice(order.getUnitPrice());
        orderParamDTO.setOrderNo(order.getOrderNo());
        orderParamDTO.setTraceId(0);
        orderParamDTO.setBlindboxId(order.getBlindboxId());
        orderParamDTO.setUserId(order.getUserId());
        orderParamDTO.setUserName(order.getUserName());
        orderParamDTO.setPayPrice(order.getPayPrice());
        orderParamDTO.setPayIntegral(order.getPayIntegral());
        orderParamDTO.setHost(host);
        orderParamDTO.setPlayId(order.getPlayId());
        orderParamDTO.setPayOrderNo(order.getPayOrderNo());
        orderParamDTO.setSubject("盲盒购买" + order.getTotalNum() + "个");


        User user;
        // 如果使用了哈希币
        if (order.getPayIntegral() > 0) {
            // 扣除用户积分
            AjaxResult ajaxResult = capitalChangeUtil.decHash(order.getPayIntegral(), order.getUserId());
            if ((Integer) ajaxResult.get(CODE_TAG) != 0) {
                return ajaxResult;
            }
            JSONObject jsonObject = (JSONObject) ajaxResult.get(AjaxResult.DATA_TAG);
            user = JSONObject.parseObject(jsonObject.toString(), User.class);
            // TODO 哈西币变动记录

        }

        // 订单需支付金额为0，直接完成订单
        if (order.getPayPrice() == 0) {
           return completeOrder(orderParamDTO);
        }
        // 余额支付
        if (order.getPayWay() == 4) {
            // 扣除用户余额
            user = userDao.selectById(order.getUserId());
            if (user.getBalance().compareTo(order.getPayPrice()) < 0) {
                return AjaxResult.dataReturn(-1, "余额不足");
            }
            user.setBalance(user.getBalance()-order.getPayPrice());
            userDao.updateById(user);
            // TODO 余额变动记录
            return completeOrder(orderParamDTO);
        }
        if (StringUtils.equalsIgnoreCase(platform, "miniapp")) {
            String desc = "盲盒支付";
            String openId = UserSessionContext.get().getString("openid");
            String outTradeNo = orderParamDTO.getOrderNo();
            PayParamDTO paramDTO = new PayParamDTO();
            paramDTO.setOpenid(openId);
            paramDTO.setPay_order_no(outTradeNo);
            paramDTO.setSubject(desc);
            paramDTO.setPay_price(orderParamDTO.getPayPrice());

            return provider.pay(paramDTO, Constants.PayWay.WECHAT_PAY);
        }
        return null;
    }

    @Override
    public AjaxResult getResult(String orderNo) {
        JSONObject resultData = new JSONObject();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);
        queryWrapper.or(wq -> wq.eq(Order::getPayOrderNo, orderNo));
        Order order = orderDao.selectOne(queryWrapper);
        if (order == null) {
            return AjaxResult.dataReturn(-1, "订单信息错误");
        }
        if (order.getPayStatus() == 1) {
            return AjaxResult.dataReturn(-1, "订单未支付，请重试");
        }
        if (order.getPayStatus() >2) {
            return AjaxResult.dataReturn(-1, "订单异常");
        }
        LambdaQueryWrapper<OrderRecord> recordQueryWrapper = new LambdaQueryWrapper<>();
        recordQueryWrapper.eq(OrderRecord::getOrderId, order.getId());
        OrderRecord orderRecord = orderRecordDao.selectOne(recordQueryWrapper);
        if (orderRecord == null) {
            return AjaxResult.dataReturn(-1, "订单记录信息错误");
        }

        resultData.put("user_id", order.getUserId());
        resultData.put("username", order.getUserName());


        LambdaQueryWrapper<OrderRecordDetail> detailQueryWrapper = new LambdaQueryWrapper<>();
        detailQueryWrapper.eq(OrderRecordDetail::getOrderRecordId, orderRecord.getId());
        JSONArray detailData = new JSONArray();
        List<OrderRecordDetail> orderRecordDetails = orderRecordDetailDao.selectList(detailQueryWrapper);
        for (OrderRecordDetail orderRecordDetail : orderRecordDetails) {
            orderRecordDetail.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(orderRecordDetail.getGoodsImage()));
            JSONObject detailJson = PubfuncUtil.parseToUnderlineJson(orderRecordDetail);
            Integer tagId = orderRecordDetail.getTagId();
            if (tagId!= null) {
                BlindboxTag blindboxTag = blindboxTagDao.selectById(tagId);
                if (blindboxTag != null) {
                    detailJson.put("tag_name", blindboxTag.getName());
                }
            }
            detailData.add(detailJson);
        }
        resultData.put("detail", detailData);
        return AjaxResult.dataReturn(0, "success", resultData);
    }


    public AjaxResult completeOrder(OrderParamDTO orderParamDTO) {
        lotteryProvider.draw(orderParamDTO);
        // 修改订单状态
        Order order = orderDao.selectById(orderParamDTO.getId());
        order.setPayStatus(2);
        order.setStatus(5);
        order.setUpdateTime(new Date());
        orderDao.updateById(order);
        return AjaxResult.dataReturn(0, "success", order);
    }

}
