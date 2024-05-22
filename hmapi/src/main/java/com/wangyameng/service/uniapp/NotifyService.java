package com.wangyameng.service.uniapp;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName NotifyService.java
 * @Description TODO
 * @createTime 2024-04-27 17:23:00
 */
public interface NotifyService {
    Map<String, String> wechatNotify(HttpServletRequest title, HttpServletResponse content);
}
