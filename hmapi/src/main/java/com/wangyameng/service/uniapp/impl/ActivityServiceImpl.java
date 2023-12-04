package com.wangyameng.service.uniapp.impl;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.ActivitySliderDao;
import com.wangyameng.entity.ActivitySlider;
import com.wangyameng.service.uniapp.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ActivityServiceImpl.java
 * @Description TODO
 * @createTime 2023-12-04 13:42:00
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivitySliderDao activitySliderDao;

    @Override
    public JSONArray getSliderData(int type) {
        LambdaQueryWrapper<ActivitySlider> sliderQueryWrapper = new LambdaQueryWrapper<>();
        sliderQueryWrapper.eq(ActivitySlider::getStatus, 1).eq(ActivitySlider::getType, type);
        List<ActivitySlider> activitySliders = activitySliderDao.selectList(sliderQueryWrapper);
        cn.hutool.json.JSONArray sliderList = new cn.hutool.json.JSONArray();
        for (ActivitySlider slider : activitySliders) {
            cn.hutool.json.JSONObject sliderJson = new cn.hutool.json.JSONObject();
            sliderJson.set("id", slider.getId());
            sliderJson.set("title", slider.getTitle());
            sliderJson.set("blindbox_id", slider.getBlindboxId());
            sliderJson.set("goods_id", slider.getGoodsId());
            sliderJson.set("pic", PubfuncUtil.replaceBecomeServerHost(slider.getPic()));
            sliderJson.set("other", slider.getOther());
            sliderList.add(sliderJson);
        }
        return sliderList;
    }
}
