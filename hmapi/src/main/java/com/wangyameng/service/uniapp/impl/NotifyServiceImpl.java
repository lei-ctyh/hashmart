package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.dao.OrderExpressDao;
import com.wangyameng.dao.UserDao;
import com.wangyameng.dto.OrderParamDTO;
import com.wangyameng.entity.Order;
import com.wangyameng.entity.OrderExpress;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.BagService;
import com.wangyameng.service.uniapp.NotifyService;
import com.wangyameng.strategy.lottery.LotteryProvider;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.java.service.retailstore.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName NotifyServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-27 17:29:00
 */
@Service("uniappNotifyService")
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    private LotteryProvider lotteryProvider;
    @Autowired
    private OrderExpressDao orderExpressDao;
    @Autowired
    BagServiceImpl bagService;

    @Override
    public Map<String, String> wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        // 获取报文
        String body = getRequestBody(request);
        // 随机串
        String nonceStr = request.getHeader("Wechatpay-Nonce");
        // 微信传递过来的签名
        String signature = request.getHeader("Wechatpay-Signature");
        // 证书序列号（微信平台）
        String serialNo = request.getHeader("Wechatpay-Serial");
        // 时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        // 构造签名串
        // 应答时间戳\n
        // 应答随机串\n
        // 应答报文主体\n
        String signStr = Stream.of(timestamp, nonceStr, body).collect(Collectors.joining("\n", "", "\n"));

        Map<String, String> map = new HashMap<>(2);
        try {
            // 验证签名是否通过
            boolean result = verifiedSign(serialNo, signStr, signature);

            if (result) {
                // 解密数据
                String plainBody = decryptBody(body);

                JSONObject respJson = convertWechatPayMsgToJSON(plainBody);

                dealOrder(respJson);
                // 响应微信
                map.put("code", "SUCCESS");
                map.put("message", "成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "FAIL");
            map.put("message", e.getMessage());
        }
        return map;
    }



    private void dealOrder(JSONObject respJson) {
        String outTradeNo = respJson.getString("out_trade_no");
        if (outTradeNo.startsWith("O")) {
            // 处理盲盒支付订单
            dealBlindBoxOrder(respJson);
        }

        if (outTradeNo.startsWith("U")) {
            // 处理邮费支付订单
            dealExpressOrder(respJson);
        }

    }

    private void dealExpressOrder(JSONObject respJson) {
        String outTradeNo = respJson.getString("out_trade_no");
        LambdaQueryWrapper<OrderExpress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderExpress::getPayNo, outTradeNo);
        // TODO 此处最好再判断一些支付状态
        OrderExpress orderExpress = orderExpressDao.selectOne(wrapper);
        if (orderExpress == null) {
            throw new RuntimeException("订单不存在");
        }
        // 完成订单
        AjaxResult result = bagService.completeExpressOrder(orderExpress, orderExpress.getBoxType());
        orderExpress.setPayStatus((Integer) result.get(AjaxResult.CODE_TAG) != 0 ? 6 : 2);
        orderExpress.setPayError((Integer) result.get(AjaxResult.CODE_TAG) != 0 ? result.get(AjaxResult.MSG_TAG).toString() : "");
        orderExpress.setPayTime(new Date());
        orderExpress.setThirdNo(respJson.getString("transaction_id"));
        orderExpress.setThirdReturn(respJson.toJSONString());
        orderExpress.setUpdateTime(new Date());
        orderExpressDao.updateById(orderExpress);
    }

    private void dealBlindBoxOrder(JSONObject respJson) {
        String outTradeNo = respJson.getString("out_trade_no");
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        Double totalFee = respJson.getJSONObject("amount").getDouble("payer_total");
        String transactionId = respJson.getString("transaction_id");

        wrapper.eq(Order::getPayOrderNo, outTradeNo);
        wrapper.or( wq->wq.eq(Order::getOrderNo, outTradeNo));

        Order order = orderDao.selectOne(wrapper);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        // TODO 此处应该校验支付金额

        order.setPayStatus(2);
        order.setStatus(2);
        order.setThirdCode(transactionId);
        order.setReturnMsg(respJson.toJSONString());
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        orderDao.updateById(order);

        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getId, order.getUserId());
        User user = userDao.selectOne(userWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
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
        String host = PubfuncUtil.getSdParam("api_url", "api_url");
        orderParamDTO.setHost(host);
        orderParamDTO.setPlayId(order.getPlayId());
        orderParamDTO.setPayOrderNo(order.getPayOrderNo());
        lotteryProvider.draw(orderParamDTO);
    }

    /**
     * 转换body为map
     *
     * @param plainBody
     * @return
     */
    private JSONObject convertWechatPayMsgToJSON(String plainBody) {
        Map<String, String> paramsMap = new HashMap<>(2);
        return  JSONObject.parseObject(plainBody);
    }


    /**
     * 解密body的密文
     * <p>
     * "resource": {
     * "original_type": "transaction",
     * "algorithm": "AEAD_AES_256_GCM",
     * "ciphertext": "",
     * "associated_data": "",
     * "nonce": ""
     * }
     *
     * @param body
     * @return
     */
    private String decryptBody(String body) throws UnsupportedEncodingException, GeneralSecurityException {
        String apiv3Key = PubfuncUtil.getSdParam("wechat_pay", "apiV3Key");
        AesUtil aesUtil = new AesUtil(apiv3Key.getBytes());
        JSONObject object = JSONObject.parseObject(body);
        JSONObject resource = object.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String associatedData = resource.getString("associated_data");
        String nonce = resource.getString("nonce");
        return aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes("utf-8"), ciphertext);
    }


    /**
     * 验证签名
     *
     * @param serialNo  微信平台-证书序列号
     * @param signStr   自己组装的签名串
     * @param signature 微信返回的签名
     * @return
     * @throws UnsupportedEncodingException
     */
    private boolean verifiedSign(String serialNo, String signStr, String signature) throws UnsupportedEncodingException {
        return true;
    }

    /**
     * 读取请求数据流
     *
     * @param request
     * @return
     */
    private String getRequestBody(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        try (ServletInputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        }
        return sb.toString();

    }

}
