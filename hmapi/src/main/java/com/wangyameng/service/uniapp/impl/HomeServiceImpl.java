package com.wangyameng.service.uniapp.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.ActivitySliderDao;
import com.wangyameng.dao.SysSettingDao;
import com.wangyameng.entity.ActivitySlider;
import com.wangyameng.service.uniapp.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-14 18:16:00
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SysSettingDao sysSettingDao;
    @Autowired
    private ActivitySliderDao activitySliderDao;

    @Override
    public AjaxResult getHomeData(int page, int limit) {
        // 检查站点是否正则维护
        String open = PubfuncUtil.getSdParam("sys_base", "web_open");
        String openVal = "1";
        if (!StringUtils.equals(openVal, open)) {
            return AjaxResult.dataReturn(404, "站点正在维护");
        }

        JSONObject rtnData = new JSONObject();
        // 幻灯数据
        rtnData.set("slider", getSliderData());
        // 商品列表
        rtnData.set("goods", null);
        return AjaxResult.dataReturn(0,"success",rtnData);
    }

    /**
     * 获取幻灯片数据
     * @return 幻灯片数据
     */
    private JSONArray getSliderData() {
        LambdaQueryWrapper<ActivitySlider> sliderQueryWrapper = new LambdaQueryWrapper<>();
        sliderQueryWrapper.eq(ActivitySlider::getStatus, 1).eq(ActivitySlider::getType, 1);
        List<ActivitySlider> activitySliders = activitySliderDao.selectList(sliderQueryWrapper);
        JSONArray sliderList = new JSONArray();
        for (ActivitySlider slider : activitySliders) {
            JSONObject sliderJson = new JSONObject();
            sliderJson.set("id", slider.getId());
            sliderJson.set("title", slider.getTitle());
            sliderJson.set("blindbox_id", slider.getBlindboxId());
            sliderJson.set("goods_id", slider.getGoodsId());
            sliderJson.set("pic", slider.getPic());
            sliderJson.set("other", slider.getOther());
            sliderList.add(sliderJson);
        }
        return sliderList;
    }

    /**
     * 获取首页热门商品数据
     * @param page 一般值都是1
     * @param limit 当前页码的最大多少
     * @return 热门商品数据
     */
    private JSONArray getGoodsData(int page, int limit) {
        JSONArray goodsList = new JSONArray();

        return goodsList;
    }
}
