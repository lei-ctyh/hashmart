package com.wangyameng.common.util.pay;

import com.alibaba.fastjson2.JSONObject;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;

/**
 * 微信小程序 支付下单为例
 */
public class WechatPayUtil {

    public static JSONObject generatePayParams(String desc, String outTradeNo, String openId, int amount) {
        JSONObject rtnJSON = new JSONObject();
        try {
            String prepayId = getPrepayId(desc, outTradeNo, openId, amount);
            rtnJSON = createPayParams(prepayId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnJSON;

    }

    private static String getPrepayId(String desc, String outTradeNo, String openId, int amount) {
        String merchantId = PubfuncUtil.getSdParam("wechat_pay", "merchantId");
        String privateKeyPath = PubfuncUtil.getSdParam("wechat_pay", "privateKeyPath");
        String merchantSerialNumber = PubfuncUtil.getSdParam("wechat_pay", "merchantSerialNumber");
        String apiV3Key = PubfuncUtil.getSdParam("wechat_pay", "apiV3Key");
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();
        PrepayRequest request = getPrepayRequest(desc, outTradeNo, openId, amount);
        PrepayResponse response = service.prepay(request);
        return "prepay_id="+response.getPrepayId();
    }

    private static PrepayRequest getPrepayRequest(String desc, String outTradeNo, String openId, int amount) {
        String appId = PubfuncUtil.getSdParam("wechat_pay", "appId");
        String merchantId = PubfuncUtil.getSdParam("wechat_pay", "merchantId");
        String notifyUrl = PubfuncUtil.getSdParam("wechat_pay", "notifyUrl");

        PrepayRequest request = new PrepayRequest();
        // 微信小程序ID
        request.setAppid(appId);
        // 微信商户号ID
        request.setMchid(merchantId);
        // 商品描述
        request.setDescription(desc);
        // 商户系统内部订单号，只能是数字大小写字母，同一个商户号需要唯一
        request.setOutTradeNo(outTradeNo);

        // 回调地址，该地址需要为可访问的 域名+指定的回写地址 https://leiaimeng.mynatapp.cc/uniapp/notify/wechat
        request.setNotifyUrl(notifyUrl);
        // 需要支付金额对象，单位为分，仅支持人民币
        Amount am = new Amount();
        am.setTotal(1);
        request.setAmount(am);
        // 支付者信息
        Payer payer = new Payer();
        // 用户在商户appid下的唯一标识
        payer.setOpenid(openId);
        request.setPayer(payer);


        // --------选填字段如下--------
        // 订单失效时间
        request.setTimeExpire(null);
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用(选填)
        request.setAttach(null);
        // 暂且不开放支付优惠功能
        request.setDetail(null);
        // 不填写场景信息
        request.setSceneInfo(null);
        // 结算信息
        request.setSettleInfo(null);
        // 是否需要电子发票
        request.setSupportFapiao(false);
        // 订单优惠标记，(选填)
        request.setGoodsTag("");
        // 限定支付方式，不需要做限制
        request.setLimitPay(new ArrayList<>());

        return request;
    }


    /**
     * 微信支付-前端唤起支付参数
     * prepay_id=wx201410272009395522657a690389285100
     *
     * @param packageStr 预下单接口返回数据 预支付交易会话标识	prepay_id
     * @return
     */
    private static JSONObject createPayParams(String packageStr) throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        JSONObject rtnJSON = new JSONObject();
        String nonceStr = PubfuncUtil.makeOrderNo("");
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(timestamp, nonceStr, packageStr);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8));
        String appId = PubfuncUtil.getSdParam("wechat_pay", "appId");
        rtnJSON.put("appId", appId);
        rtnJSON.put("timeStamp", timestamp + "");
        rtnJSON.put("nonceStr", nonceStr);
        rtnJSON.put("package", packageStr);
        rtnJSON.put("signType", "RSA");
        rtnJSON.put("paySign", signature);
        return rtnJSON;
    }

    /**
     * 微信支付-前端唤起支付参数-签名
     *
     * @param message 签名数据
     * @return
     */
    private static String sign(byte[] message) throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        String privateKeyPath = PubfuncUtil.getSdParam("wechat_pay", "privateKeyPath");
        sign.initSign(getPrivateKey(privateKeyPath));
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 微信支付-前端唤起支付参数-构建签名参数
     *
     * @param nonceStr 签名数据
     * @return
     */
    public static String buildMessage(long timestamp, String nonceStr, String packageStr) {
        return PubfuncUtil.getSdParam("wechat_pay", "appId") + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + packageStr + "\n";
    }


    /**
     * 微信支付-前端唤起支付参数-获取商户私钥
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    private static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

}
