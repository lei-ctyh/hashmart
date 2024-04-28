package com.wangyameng.service.uniapp.impl;

import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.ApplicationContextHelper;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.redis.RedisCacheUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.User;
import com.wangyameng.service.uniapp.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanglei
 * @version 1.0
 * @description: TODO
 * @date 2024/2/20 21:21
 */

@Service("uniappLoginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    @Override
    public AjaxResult doLoginByWechat(String code, String phone_code) {

        String appId = PubfuncUtil.getSdParam("miniapp", "miniapp_app_id");
        String appSecret = PubfuncUtil.getSdParam("miniapp", "miniapp_app_secret");
        String token = getToken(appId, appSecret);
        String phoneNumber = getPhoneNumber(phone_code, token);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phoneNumber);
        User userInfo = userDao.selectOne(queryWrapper);
        // 用户信息为空需要注册
        if (userInfo == null) {
            userInfo = new User();
            userInfo.setNickname("微信用户");
            userInfo.setPhone(phoneNumber);
            userInfo.setAvatar(PubfuncUtil.getRandAvatar());

            userInfo.setOpenid(getOpenId(code));
            userInfo.setPassword(PubfuncUtil.makePassword("123456", ""));
            userInfo.setSourceType(1);
            userInfo.setCreateTime(new Date());
            userDao.insert(userInfo);
            userInfo= userDao.selectOne(queryWrapper);

        }else {
            if (userInfo.getStatus() == 2) {
                return AjaxResult.dataReturn(-3, "该账号已被封禁");
            }

            // 补全openid
            if (StringUtils.isBlank(userInfo.getOpenid())) {
                String openId = getOpenId(code);
                if (StringUtils.isBlank(openId)) {
                    return AjaxResult.dataReturn(-1, "系统错误");
                }

                userInfo.setOpenid(openId);
                userDao.updateById(userInfo);
            }
        }
        return makeReturnData(userInfo);
    }

    /**
     * 获取手机号
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html">接口相关文档</a>
     * @param phoneCode 手机号码加密串
     * @param token token
     * @return 手机号码
     */
    private String getPhoneNumber(String phoneCode, String token) {
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + token ;
        JSONObject reqMsg = new JSONObject();
        reqMsg.put("code", phoneCode);
        String respMsg = HttpUtil.post(url, reqMsg.toString());
        JSONObject respJson = JSONObject.parseObject(respMsg);
        if (respJson.get("errcode") != null && StringUtils.equals("0", respJson.get("errcode").toString())) {
            return respJson.getJSONObject("phone_info").getString("phoneNumber");
        }
        throw new RuntimeException("获取手机号失败");
    }

    /**
     * 获取token
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html">微信小程序获取接口调用凭据</a>
     * @param appId 微信小程序的appID
     * @param appSecret 微信小程序的appSecret
     * @return 该token的有效期为7200秒, Redis中设置的失效事件为7000秒
     */

    private String getToken(String appId, String appSecret) {

        RedisCacheUtil redisCacheUtil = ApplicationContextHelper.popBean(RedisCacheUtil.class);
        // redis缓存中获取token
        if (redisCacheUtil != null) {
            Object token = redisCacheUtil.getCacheObject("token");
            if (token != null) {
                return token.toString();
            }
        }

        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRE}";
        String respMsg = HttpUtil.get(getTokenUrl.replace("{APPID}", appId).replace("{APPSECRE}", appSecret));
        JSONObject respJson = JSONObject.parseObject(respMsg);
        if (respJson.get("access_token") != null) {
            redisCacheUtil.setCacheObject("token", respJson.get("access_token"), 7000, TimeUnit.SECONDS);
            return respJson.get("access_token").toString();
        }
       throw new RuntimeException("获取token失败");
    }

    /**
     * 获取openid
     * <a href="https://developers.weixin.qq.com/minigame/dev/api-backend/open-api/login/auth.code2Session.html">获取OpenId的相关文档</a>
     * @param code code
     * @return openid
     */
    private String  getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={APPSECRET}&js_code={CODE}&grant_type=authorization_code";
        String appId = PubfuncUtil.getSdParam("miniapp", "miniapp_app_id");
        String appSecret = PubfuncUtil.getSdParam("miniapp", "miniapp_app_secret");
        String respMsg = HttpUtil.get(url.replace("{APPID}", appId).replace("{APPSECRET}", appSecret).replace("{CODE}", code));
        JSONObject respJson = JSONObject.parseObject(respMsg);
        if (respJson.get("openid") != null) {
            return respJson.get("openid").toString();
        }
        return "";
    }

    private AjaxResult makeReturnData(User userInfo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", userInfo.getId());
        map.put("nickname", userInfo.getNickname());
        map.put("phone", userInfo.getPhone());
        map.put("avatar", userInfo.getAvatar());
        map.put("hashKey", userInfo.getHashKey());
        map.put("openid", userInfo.getOpenid());
        map.put("sourceType", userInfo.getSourceType());
        map.put("createTime", userInfo.getCreateTime());
        map.put("updateTime", userInfo.getUpdateTime());
        map.put("status", userInfo.getStatus());
        String token = PubfuncUtil.setJWT(map);

        // todo 记录登录日志
        JSONObject respJson = new JSONObject();
        respJson.put("token", token);
        respJson.put("userInfo", map);
        return AjaxResult.dataReturn(0, "登录成功", respJson);
    }


}
