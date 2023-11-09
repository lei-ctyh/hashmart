package com.wangyameng.common.util.pubfunc;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wangyameng.common.util.redis.RedisCacheUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.ArrayList;
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
                put("data",data);
            }

        };
        String jwtKey = "HashMart20221225!@#";
        // todo 可以移植到二开参数中适配
        return JWTUtil.createToken(map, jwtKey.getBytes());
    }

    /**
     * getJWT
     * @param token
     */
    public static JWT getJWT(String token) {
        // 先验证token的有效性
        String jwtKey = "HashMart20221225!@#";
        boolean verify = JWTUtil.verify(token, jwtKey.getBytes());
        if (verify) {
            return JWTUtil.parseToken(token);
        }
        return null;
    }

    /**
     * 生成子孙树
     * @param data 遍历所有数据, 每个数据加到其父节点下
     * @return 子孙树json
     */
    public static JSONArray makeTree(JSONArray data) {
        Map<Integer, JSONObject> res = new HashMap<>();
        JSONArray tree = new JSONArray();

        // 整理数组
        for (Object o : data) {
            JSONObject item = (JSONObject) o;
            res.put((Integer) item.get("id"), item);
        }

        // 查询子孙
        for (Object o : data) {
            JSONObject item = (JSONObject) o;
            if (item.getInteger("pid") != 0) {
                JSONArray children = res.get(item.getInteger("pid")).getJSONArray("children");
                if (children == null) {
                    children = new JSONArray();
                }
                children.add(item);
                res.get(item.getInteger("pid")).put("children", children);
            }
        }

        // 去除杂质
        for (Object o : data) {
            JSONObject item = (JSONObject) o;
            if (item.getInteger("pid") == 0) {
                tree.add(item);
            }
        }
        return tree;
    }

    public static void main(String[] args) {
        setJWT(null);
    }


}
