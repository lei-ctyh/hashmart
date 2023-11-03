package com.wangyameng.common.util.pubfunc;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName PubfuncUtil.java
 * @Description 公共方法工具类
 * @createTime 2023-11-03 11:53:00
 */
public class PubfuncUtil {
    /**
     * 密码加密 sha1(md5(md5()))
     *
     * @param password 密码
     * @param salt     密码盐
     * @return 加密后的密钥
     */
    public static String makePassword(String password, String salt) {
        String s = SecureUtil.sha1(SecureUtil.md5(SecureUtil.md5(password + salt)));
        return s;
    }

    /**
     * 创建JSON WEB TOKEN (JWT)
     *
     * @param map 参数参考如下
     *            id 用户ID
     *            roleId 角色ID
     *            nickname 昵称
     *            avatar 用户头像
     */
    public static String setJWT(HashMap<String, Object> data) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                // 签发时间
                put("iat", System.currentTimeMillis());
                // 生效时间
                put("nbf", System.currentTimeMillis());
                // 失效时间
                put("exp", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
                putAll(data);
            }

        };
        String jwtKey = "HashMart20221225!@#";
        // todo 可以移植到二开参数中适配
        return JWTUtil.createToken(map, jwtKey.getBytes());
    }

    public static void main(String[] args) {
        setJWT(null);
    }
}
