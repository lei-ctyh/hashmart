package com.wangyameng.api.uniapp.order;

import com.alibaba.fastjson.JSONObject;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.service.uniapp.NotifyService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @RequestMapping("/uniapp/notify/wechat")
    @ResponseBody
    public Map<String, String> notify(HttpServletRequest request, HttpServletResponse response) {
        return notifyService.wechatNotify(request, response);
    }
}