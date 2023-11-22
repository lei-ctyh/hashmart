package com.wangyameng.service.uniapp.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.*;
import com.wangyameng.entity.ActivitySlider;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.entity.Goods;
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
    private ActivitySliderDao activitySliderDao;
    @Autowired
    private BlindboxDao blindboxDao;
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;

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
        rtnData.set("goods", getGoodsData(page, limit));
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
            sliderJson.set("pic", PubfuncUtil.replaceBecomeServerHost(slider.getPic()));
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
    private JSONObject getGoodsData(int page, int limit) {
        JSONObject rtnJson = new JSONObject();
        LambdaQueryWrapper<Blindbox> bolindboxQueryWrapper = new LambdaQueryWrapper<>();
        bolindboxQueryWrapper.orderByDesc(Blindbox::getSort);
        Page<Blindbox> ipage = new Page<>();
        ipage.setPages(page);
        ipage.setSize(limit);
        ipage = blindboxDao.selectPage(ipage, bolindboxQueryWrapper);
        List<Blindbox> blindboxes = ipage.getRecords();

        JSONArray data = new JSONArray();
        for (Blindbox blindbox : blindboxes) {
            JSONObject blindboxJson = new JSONObject();
            blindboxJson.set("id", blindbox.getId());
            blindboxJson.set("name", blindbox.getName());
            blindboxJson.set("desc", blindbox.getDesc());
            blindboxJson.set("pic", blindbox.getPic());
            blindboxJson.set("sales", blindbox.getSales());
            blindboxJson.set("hot_tag", blindbox.getHotTag());
            blindboxJson.set("recommend_tag", blindbox.getRecommendTag());
            blindboxJson.set("price", blindbox.getPrice());
            // 每个盲盒都要有价格范围, 和盲盒下物品的图片
            JSONArray detail = new JSONArray();
            JSONObject priceRange = new JSONObject();
            if (blindbox.getPrice()!= null) {
                LambdaQueryWrapper<BlindboxDetail> blindboxDetailQueryWrapper = new LambdaQueryWrapper<>();
                blindboxDetailQueryWrapper.eq(BlindboxDetail::getBlindboxId, blindbox.getId());
                blindboxDetailQueryWrapper.orderByDesc(BlindboxDetail::getSort);
                List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(blindboxDetailQueryWrapper);
                String minPrice = "";
                String maxPrice = "";
                for (BlindboxDetail blindboxDetail : blindboxDetails) {
                    if (blindboxDetail.getPrice()!= null) {
                        if (StringUtils.isEmpty(minPrice)) {
                            minPrice = blindboxDetail.getPrice().toString();
                        } else {
                            if (Double.parseDouble(blindboxDetail.getPrice().toString()) < Double.parseDouble(minPrice)) {
                                minPrice = blindboxDetail.getPrice().toString();
                            }
                        }
                        if (StringUtils.isEmpty(maxPrice)) {
                            maxPrice = blindboxDetail.getPrice().toString();
                        } else {
                            if (Double.parseDouble(blindboxDetail.getPrice().toString()) > Double.parseDouble(maxPrice)) {
                                maxPrice = blindboxDetail.getPrice().toString();
                            }
                        }
                    }

                    if (blindboxDetail.getGoodsId()!= null) {
                        JSONObject detailJson = new JSONObject();
                        detailJson.set("goods_image", PubfuncUtil.replaceBecomeServerHost(blindboxDetail.getGoodsImage()));
                        detail.add(detailJson);
                    }

                }
                // 预防某些盲盒没有价格, 默认价格为99999999
                if (StringUtils.isBlank(minPrice)) {
                    minPrice = "99999999";
                }
                if (StringUtils.isBlank(maxPrice)) {
                    maxPrice = "99999999";
                }
                priceRange.set("min_price", minPrice);
                priceRange.set("max_price", maxPrice);

            }
            blindboxJson.set("price_range", priceRange);
            blindboxJson.set("detail", detail);

            data.add(blindboxJson);
        }

        rtnJson.set("total", ipage.getTotal());
        rtnJson.set("per_page", ipage.getSize());
        rtnJson.set("current_page", ipage.getCurrent());
        rtnJson.set("last_page", ipage.getPages());
        rtnJson.set("data", data);
        return rtnJson;
    }
}
