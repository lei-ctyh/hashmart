package com.wangyameng.common.util.pubfunc;

import cn.hutool.Hutool;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.ApplicationContextHelper;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysSettingDao;
import com.wangyameng.entity.SysSetting;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wangyameng
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
     * @param dataArray 遍历所有数据, 每个数据加到其父节点下
     * @return 子孙树json
     */
    public static JSONArray makeTree(JSONArray dataArray) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject jsonObject = dataArray.getJSONObject(i);
            Map<String, Object> map = new HashMap<>(jsonObject);
            data.add(map);
        }

        List<Map<String, Object>> res = new ArrayList<>();
        Map<Integer, Map<String, Object>> map = new HashMap<>();

        // 整理数组
        for (Map<String, Object> vo : data) {
            map.put((Integer) vo.get("id"), vo);
        }

        // 查询子孙
        for (Map<String, Object> vo : data) {
            Integer pid = (Integer) vo.get("pid");
            if (pid != 0) {
                Map<String, Object> parent = map.get(pid);
                if (parent != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parent.get("children");
                    if (children == null) {
                        children = new ArrayList<>();
                        parent.put("children", children);
                    }
                    children.add(vo);
                }
            }
        }

        // 去除杂质
        for (Map<String, Object> vo : data) {
            int pid = (Integer) vo.get("pid");
            if (pid == 0) {
                res.add(vo);
            }
        }

        return new JSONArray(res);
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
        if (date == null) {
            return "";
        }
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
    private static String replaceDomainName(String url, String domainName) {
        // (\w+):\/\/([^/:]+)(:\d*)?
        // 转成java正则
        // String regex = "(\\w+):\\/\\/(\\w+)(:\\d*)?";
        String regex = "(\\w+):\\/\\/(\\w+)(:\\d*)?";
        return url.replaceAll(regex, domainName);
    }

    public static String replaceBecomeServerHost(String url) {
        // todo 后期需要处理成可替换的 String serverHost = getSdParam("server", "host");
        return   replaceDomainName(url, "HASHMART_URL").replaceAll("HASHMART_URL", getPublicIPAddress());
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

    /**
     * 获取私有IP地址
     * @return 服务的私有IP地址
     */
    public static String getPrivateIPAddress() {
        return "http://localhost:8888";
    }

    /**
     * 获取公网IP地址
     * @return 服务的公网IP地址
     */
    private static String getPublicIPAddress() {
        return getPrivateIPAddress();
    }


    /**
     * 获取随机头像
     * @param flag 0: 获取随机头像; 3: 获取全部头像列表
     */
    public static String getRandAvatar(int flag) {
        int[] arr = {1, 2, 3};
        if (flag == 3) {
            JSONArray array = new JSONArray();
            for (int i = 1; i <= arr.length; i++) {
                array.add(PubfuncUtil.replaceBecomeServerHost("HASHMART_URL" + "/images/avatar-" + i + ".png"));
            }
            return array.toString();
        }else {
            int num = arr[new Random().nextInt(arr.length)];
            return PubfuncUtil.replaceBecomeServerHost("HASHMART_URL" + "/images/avatar-" + num + ".png");
        }

    }

    /**
     * 获取随机头像
     * @return 随机头像地址
     */
    public static String getRandAvatar() {
        return getRandAvatar(0);
    }


    public static void main(String[] args) {
        setJWT(null);
    }


    public static <T> JSONArray paginate(IPage<T> ipage) {
        List<T> records = ipage.getRecords();

        JSONArray jsonArray = new JSONArray();
        for (T record : records) {
            JSONObject json = new JSONObject();
            // 获取字段名
            Field[] fields = record.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String fieldName = underscoreName(field.getName());
                    if (field.getType() == Date.class) {
                        json.put(fieldName, formatDate((Date) field.get(record)));
                    }else {
                        json.put(fieldName, field.get(record));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            jsonArray.add(json);
        }
        return jsonArray;
    }

    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (!StringUtils.isAllLowerCase(s) && Character.isUpperCase(s.charAt(0))) {
                    result.append("_").append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }

    /**
     * 对象序列化成JSON，但是key值变成帕斯卡
     */
    public static JSONObject parseToUnderlineJson(Object object) {
        if (object != null) {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
            JSONObject jsonObject = new JSONObject();
            for (String key : json.keySet()) {
                String camelName = underscoreName(key);
                jsonObject.put(camelName, json.get(key));
            }
            return jsonObject;
        }
        return null;
    }

    /**
     * 生成订单号
     * @param b 盲盒支付订单  U 邮费支付单号 D盲盒兑换记录 O盲盒订单 F 邮费订单号 T 提货订单 S 商品购买订单号 G 商品订单支付号
     * @return 订单号
     */
    public static String makeOrderNo(String b) {
        // 业务前缀+时间戳+雪花算法
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator(0, 0);
        return b+PubfuncUtil.formatDate(new Date(), "yyyyMMddHHmm")+ snowflakeGenerator.next();
    }
}
