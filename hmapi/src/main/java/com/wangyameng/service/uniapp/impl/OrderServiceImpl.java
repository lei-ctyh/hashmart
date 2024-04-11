package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static com.wangyameng.common.core.AjaxResult.CODE_TAG;

/**
 * @author zhanglei
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
        String orderNo = PubfuncUtil.makeOrderNo("B");
        String payOrderNo = PubfuncUtil.makeOrderNo("B");
        double postage = 0;
        Blindbox blindbox = blindboxDao.selectById(blindboxId);
        Integer userId = UserSessionContext.get().getInteger("id");
        String nickName = UserSessionContext.get().getString("nickName");
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

        JSONObject orderParam = new JSONObject();
        orderParam.put("id", order.getId());
        orderParam.put("total_num", order.getTotalNum());
        orderParam.put("unit_price", order.getUnitPrice());
        orderParam.put("order_no", order.getOrderNo());
        orderParam.put("trace_id", 0);
        orderParam.put("blindbox_id", order.getBlindboxId());
        orderParam.put("user_id", order.getUserId());
        orderParam.put("user_name", order.getUserName());
        orderParam.put("pay_price", order.getPayPrice());
        orderParam.put("pay_integral", order.getPayIntegral());
        orderParam.put("host", host);
        orderParam.put("play_id", order.getPlayId());
        orderParam.put("pay_order_no", order.getPayOrderNo());
        orderParam.put("subject", "盲盒购买" + order.getTotalNum() + "个");

        // 如果使用了哈希币
        if (order.getPayIntegral() > 0) {
            // 扣除用户积分
            AjaxResult ajaxResult = capitalChangeUtil.decrHash(order.getPayIntegral(), order.getUserId());
            if ((Integer) ajaxResult.get(CODE_TAG) != 0) {
                return ajaxResult;
            }
        }
        // TODO 哈西币变动记录

        // 订单需支付金额为0，直接完成订单
        if (order.getPayPrice() == 0) {
            completeOrder(order);

        }

        return null;
    }

    private void completeOrder(Order order) {
        Integer userId = UserSessionContext.get().getInteger("id");
        String hashKey = UserSessionContext.get().getString("hashKey");
        order.getPlayId();
    }

}
