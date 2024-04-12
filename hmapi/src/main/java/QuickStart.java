
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.wechat.pay.java.service.payments.jsapi.model.GoodsDetail;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.SceneInfo;
import com.wechat.pay.java.service.payments.jsapi.model.Detail;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest.LimitPayEnum;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.SettleInfo;
import java.util.ArrayList;import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;

/** Native 支付下单为例 */
public class QuickStart {

    /** 商户号 */
    public static String merchantId = "190000****";
    /** 商户API私钥路径 */
    public static String privateKeyPath = "/Users/yourname/your/path/apiclient_key.pem";
    /** 商户证书序列号 */
    public static String merchantSerialNumber = "5157F09EFDC096DE15EBE81A47057A72********";
    /** 商户APIV3密钥 */
    public static String apiV3Key = "...";

    public static void main(String[] args) {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();

        PrepayRequest request = getPrepayRequest();


        PrepayResponse response = service.prepay(request);
        System.out.println(response.getPrepayId());
    }

    private  static PrepayRequest getPrepayRequest() {
        PrepayRequest request = new PrepayRequest();
        // 微信小程序ID
        request.setAppid("");
        // 微信商户号ID
        request.setMchid("");
        // 商品描述
        request.setDescription("测试商品");
        // 商户系统内部订单号，只能是数字大小写字母，同一个商户号需要唯一
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator(1, 1);
        request.setOutTradeNo(snowflakeGenerator.next().toString());

        // 回调地址，该地址需要为可访问的 域名+指定的回写地址
        request.setNotifyUrl("");
        // 需要支付金额对象，单位为分，仅支持人民币
        Amount amount = new Amount();
        amount.setTotal(1);
        request.setAmount(amount);
        // 支付者信息
        Payer payer = new Payer();
        // 用户在商户appid下的唯一标识
        payer.setOpenid("");
        request.setPayer(payer);


        // --------选填字段如下--------
        // 订单失效时间
        request.setTimeExpire("");
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用(选填)
        request.setAttach("");
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
        request.setLimitPay(new ArrayList<LimitPayEnum>());

        return request;
    }
}
