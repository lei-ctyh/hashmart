package com.wangyameng.common.util.pubfunc;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.ApplicationContextHelper;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysSettingDao;
import com.wangyameng.entity.SysSetting;

import java.util.Date;
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
     * @param data 参数参考如下
     *             id 用户ID
     *             roleId 角色ID
     *             nickname 昵称
     *             avatar 用户头像
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
                put("data", data);
            }

        };
        String jwtKey = "HashMart20221225!@#";
        // todo 可以移植到二开参数中适配
        return JWTUtil.createToken(map, jwtKey.getBytes());
    }

    /**
     * getJWT
     *
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
     *
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


    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        return new java.text.SimpleDateFormat(format).format(date);
    }

    /**
     * 简化日期格式的使用
     *
     * @param date 需要格式化的日期
     * @return 格式化的字符串 如2023-11-13 17:42:00
     * @see com.wangyameng.common.util.pubfunc.PubfuncUtil#formatDate(java.util.Date, java.lang.String)
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取系统参数
     *
     * @param type 类型
     * @param key  当前类型的键值
     * @return 参数值
     */
    public static String getSdParam(String type, String key) {
        String rtnValue = "";
        SysSettingDao sysSettingDao = ApplicationContextHelper.popBean(SysSettingDao.class);
        LambdaQueryWrapper<SysSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSetting::getType, type).eq(SysSetting::getKey, key);
        if (sysSettingDao != null) {
            SysSetting sysSetting = sysSettingDao.selectOne(queryWrapper);
            rtnValue = sysSetting.getValue();
        }
        return rtnValue;
    }

    /**
     * 正则表达式替换域名
     *
     * @param url        资源链接
     * @param domainName 目标域名
     * @return 替换后的链接
     */
    public static String replaceDomainName(String url, String domainName) {
        // (\w+):\/\/([^/:]+)(:\d*)?
        // 转成java正则
        // String regex = "(\\w+):\\/\\/(\\w+)(:\\d*)?";
        String regex = "(\\w+):\\/\\/(\\w+)(:\\d*)?";
        return url.replaceAll(regex, domainName);
    }

    public static String replaceBecomeServerHost(String url) {
        // todo 后期需要处理成可替换的 String serverHost = getSdParam("server", "host");
        String serverHost = "http://localhost:8888";
        return   replaceDomainName(url, "HASHMART_URL").replaceAll("HASHMART_URL", serverHost);
    }

    /**
     * 检查是否客户端开启
     * @return true 开启 false 关闭
     */
    public static boolean checkOpen() {
        String open = PubfuncUtil.getSdParam("sys_base", "web_open");
        String openVal = "1";
        return StringUtils.equals(openVal, open);
    }

    public static void main(String[] args) {
        setJWT(null);
    }


}
