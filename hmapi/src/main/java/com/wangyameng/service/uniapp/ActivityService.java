package com.wangyameng.service.uniapp;


import com.alibaba.fastjson2.JSONArray;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ActivityService.java
 * @Description TODO
 * @createTime 2023-12-04 13:40:00
 */
public interface ActivityService {
    public static int SLIDER_TYPE_INDEX = 1;
    public static int SLIDER_TYPE_SHOP = 2;
    JSONArray getSliderData(int type);
}
