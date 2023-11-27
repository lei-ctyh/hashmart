package com.wangyameng.vo.admin;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName CaptchaVo.java
 * @Description 登录验证码信息
 * @createTime 2023-11-27 11:03:00
 */
public class CaptchaVo {
    // 验证码存在于Redis的key
    private String key;
    // 验证码的Base64图片
    private String img;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
