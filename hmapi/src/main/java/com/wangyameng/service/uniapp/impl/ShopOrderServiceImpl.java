package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.capital.CapitalChangeUtil;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.*;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName ShopOrderServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-29 00:28:00
 */

@Service("uniappShopOrderService")
public class ShopOrderServiceImpl implements ShopOrderService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsRuleExtendDao goodsRuleExtendDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private CapitalChangeUtil capitalChangeUtil;

    @Override
    public AjaxResult trail(int goodsId, String sku, int num, String address) {
        LambdaQueryWrapper<Goods> goodsWarpper = new LambdaQueryWrapper<>();
        goodsWarpper.eq(Goods::getId, goodsId);
        Goods goodsInfo = goodsDao.selectOne(goodsWarpper);
        if (goodsInfo == null) {
            return AjaxResult.dataReturn(-1, "商品不存在");
        }
        GoodsRuleExtend ruleInfo = null;
        if (goodsInfo.getType() == 2) {
            if (StringUtils.isBlank(sku)) {
                return AjaxResult.dataReturn(-2, "请选择规格");
            }
            boolean hasRule = false;
            LambdaQueryWrapper<GoodsRuleExtend> ruleExtendWarpper = new LambdaQueryWrapper<>();
            ruleExtendWarpper.eq(GoodsRuleExtend::getGoodsId, goodsId);
            List<GoodsRuleExtend> ruleExtendList = goodsRuleExtendDao.selectList(ruleExtendWarpper);
            for (GoodsRuleExtend ruleExtend : ruleExtendList) {
                if (ruleExtend.getSku().equals(sku)) {
                    hasRule = true;
                    ruleInfo = ruleExtend;
                    break;
                }

            }
            if (!hasRule) {
                return AjaxResult.dataReturn(-3, "规格不存在");
            }
        }

        if (goodsInfo.getType() == 1) {
            if (goodsInfo.getStock() != -1 && goodsInfo.getStock() < num) {
                return AjaxResult.dataReturn(-4, "库存不足");
            }
        } else {
            if (ruleInfo != null && ruleInfo.getStock() != -1 && ruleInfo.getStock() < num) {
                return AjaxResult.dataReturn(-5, "库存不足");
            }

        }
        Double integralPrice = goodsInfo.getType() == 2 ? ruleInfo.getIntegralPrice() : goodsInfo.getIntegralPrice();
        JSONObject trailData = new JSONObject();
        trailData.put("goods_id", goodsInfo.getId());
        trailData.put("goods_name", goodsInfo.getName());
        trailData.put("goods_image", goodsInfo.getType() == 2 ? PubfuncUtil.replaceBecomeServerHost(ruleInfo.getImage()) : PubfuncUtil.replaceBecomeServerHost(goodsInfo.getImage()));
        trailData.put("rule_id", goodsInfo.getType() == 2 ? ruleInfo.getId() : 0);
        trailData.put("sku", sku);
        trailData.put("num", num);
        trailData.put("price", goodsInfo.getType() == 2 ? ruleInfo.getPrice() : goodsInfo.getPrice());
        trailData.put("delivery_fee", goodsInfo.getDeliveryFee());
        trailData.put("integral_price", integralPrice);
        trailData.put("total_price", integralPrice * num);
        return AjaxResult.dataReturn(0, "success", trailData);
    }

    @Override
    public AjaxResult createOrder(int goodsId, String sku, int num, String address, int payWay) {
        AjaxResult trailData = trail(goodsId, sku, num, address);
        if ((Integer) trailData.get(AjaxResult.CODE_TAG) != 0) {
            return trailData;
        }
        JSONObject trailDataJson = (JSONObject) trailData.get(AjaxResult.DATA_TAG);
        // 查询当前用户哈西币余额
        String userId = UserSessionContext.get().getString("id");
        LambdaQueryWrapper<User> userWarpper = new LambdaQueryWrapper<>();
        userWarpper.eq(User::getId, userId);
        User user = userDao.selectOne(userWarpper);
        if (user == null) {
            return AjaxResult.dataReturn(-1, "用户不存在");
        }
        if (user.getIntegral() < trailDataJson.getFloatValue("total_price")) {
            return AjaxResult.dataReturn(-2, "余额不足");
        }
        String orderNo = PubfuncUtil.makeOrderNo("S");
        String payOrderNo = PubfuncUtil.makeOrderNo("G");
        Double postage = trailDataJson.getDoubleValue("delivery_fee");
        // 查询地址信息
        LambdaQueryWrapper<UserAddress> addressWarpper = new LambdaQueryWrapper<>();
        addressWarpper.eq(UserAddress::getId, address);
        addressWarpper.eq(UserAddress::getUserId, userId);
        UserAddress addressInfo = userAddressDao.selectOne(addressWarpper);
        if (addressInfo == null) {
            return AjaxResult.dataReturn(-12, "收货地址错误");
        }
        Order order = new Order();
        order.setPid(0);
        order.setType(1);
        order.setOrderNo(orderNo);
        order.setPayOrderNo(payOrderNo);
        order.setUserId(Integer.valueOf(userId));
        order.setUserName(UserSessionContext.get().getString("nickname"));
        order.setBlindboxId(0);
        order.setBoxId(0);
        order.setPlayId(0);
        order.setTotalNum(num);
        order.setUnitPrice(trailDataJson.getDoubleValue("price"));
        order.setPostage(postage);
        order.setOrderPrice(trailDataJson.getDoubleValue("total_price"));
        order.setPayWay(payWay);
        order.setPayPrice(0.0);
        order.setPayIntegral(trailDataJson.getDoubleValue("total_price") + postage);
        order.setPayStatus(1);
        order.setAddressId(Integer.valueOf(address));
        order.setAddressInfo(JSONObject.from(addressInfo).toString());
        order.setStatus(1);
        order.setCreateTime(new Date());
        orderDao.insert(order);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId());
        orderDetail.setUserId(Integer.valueOf(userId));
        orderDetail.setGoodsId(goodsId);
        orderDetail.setGoodsName(trailDataJson.getString("goods_name"));
        orderDetail.setGoodsImage(trailDataJson.getString("goods_image"));
        orderDetail.setRuleId(trailDataJson.getInteger("rule_id"));
        orderDetail.setRule(sku);
        orderDetail.setNum(num);
        orderDetail.setRealPayAmount(0.0);
        orderDetail.setRealPayIntegral(trailDataJson.getDoubleValue("total_price") + postage);
        orderDetail.setCreateTime(new Date());
        orderDetailDao.insert(orderDetail);
        JSONObject rtnData = new JSONObject();
        rtnData.put("pay_order_no", payOrderNo);
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    @Override
    public AjaxResult payOrder(String platform, String payOrderNo) {
        // 验证订单信息

        LambdaQueryWrapper<Order> orderWarper = new LambdaQueryWrapper<>();
        orderWarper.eq(Order::getPayOrderNo, payOrderNo);
        orderWarper.eq(Order::getPayStatus, 1);
        Order order = orderDao.selectOne(orderWarper);
        if (order == null) {
            return AjaxResult.dataReturn(-1, "订单信息错误");
        }
        // 库存处理
        OrderDetail orderDetail = orderDetailDao.selectOne(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, order.getId()));
        GoodsRuleExtend goodsExtendInfo = null;
        // 单规格商品
        if (orderDetail.getRuleId() == 0) {

        } else { // 多规格商品
            LambdaQueryWrapper<GoodsRuleExtend> ruleWrapper = new LambdaQueryWrapper<>();
            ruleWrapper.eq(GoodsRuleExtend::getId, orderDetail.getRuleId());
            goodsExtendInfo = goodsRuleExtendDao.selectOne(ruleWrapper);
            if (goodsExtendInfo.getStock() != -1 && goodsExtendInfo.getStock() < orderDetail.getNum()) {
                return AjaxResult.dataReturn(-2, "库存不足");
            }
            int stock = -1;
            if (goodsExtendInfo.getStock() != -1) {
                stock = goodsExtendInfo.getStock() - orderDetail.getNum();
            }
            goodsExtendInfo.setStock(stock);
            goodsRuleExtendDao.updateById(goodsExtendInfo);

            Integer userId = UserSessionContext.get().getInteger("id");
            // 扣除哈西币
            AjaxResult decHashResult = capitalChangeUtil.decHash(order.getPayIntegral(), userId);
            if (!((Integer) decHashResult.get(AjaxResult.CODE_TAG) == 0)) {
                return decHashResult;
            }

            // 修改订单状态
            order.setPayStatus(2);
            order.setStatus(2);
            order.setPayTime(new Date());
            order.setUpdateTime(new Date());
            orderDao.updateById(order);
        }


        return null;
    }
}
