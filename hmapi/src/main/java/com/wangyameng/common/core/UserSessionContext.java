package com.wangyameng.common.core;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName UserSessionContext.java
 * @Description 用户会话上下文
 * @createTime 2024-04-10 11:43:00
 */
public class UserSessionContext {
    private static final ThreadLocal<JSONObject> USER_SESSION_THREADLOCAL = new ThreadLocal<>();

    public static void set(JSONObject userSessionVO) {
        USER_SESSION_THREADLOCAL.set(userSessionVO);
    }

    public static JSONObject get() {
        if (USER_SESSION_THREADLOCAL.get() == null) {
            return new JSONObject();
        }
        return USER_SESSION_THREADLOCAL.get();
    }

    public static void remove() {
        USER_SESSION_THREADLOCAL.remove();
    }
}
