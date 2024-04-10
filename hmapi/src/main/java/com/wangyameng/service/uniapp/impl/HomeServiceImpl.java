package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.service.uniapp.ActivityService;
import com.wangyameng.service.uniapp.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wangyameng.service.uniapp.ActivityService.SLIDER_TYPE_INDEX;

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
    private BlindboxDao blindboxDao;
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;
    @Autowired
    private ActivityService activityService;

    @Override
    public AjaxResult getHomeData(int page, int limit) {

        JSONObject rtnData = new JSONObject();
        // 幻灯数据
        rtnData.put("slider", activityService.getSliderData(SLIDER_TYPE_INDEX));
        // 商品列表
        rtnData.put("goods", getGoodsData(page, limit));
        return AjaxResult.dataReturn(0,"success",rtnData);
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
            blindboxJson.put("id", blindbox.getId());
            blindboxJson.put("name", blindbox.getName());
            blindboxJson.put("desc", blindbox.getDesc());
            blindboxJson.put("pic", PubfuncUtil.replaceBecomeServerHost(blindbox.getPic()));
            blindboxJson.put("sales", blindbox.getSales());
            blindboxJson.put("hot_tag", blindbox.getHotTag());
            blindboxJson.put("recommend_tag", blindbox.getRecommendTag());
            blindboxJson.put("price", blindbox.getPrice());
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
                        detailJson.put("goods_image", PubfuncUtil.replaceBecomeServerHost(blindboxDetail.getGoodsImage()));
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
                priceRange.put("min_price", minPrice);
                priceRange.put("max_price", maxPrice);

            }
            blindboxJson.put("price_range", priceRange);
            blindboxJson.put("detail", detail);

            data.add(blindboxJson);
        }

        rtnJson.put("total", ipage.getTotal());
        rtnJson.put("per_page", ipage.getSize());
        rtnJson.put("current_page", ipage.getCurrent());
        rtnJson.put("last_page", ipage.getPages());
        rtnJson.put("data", data);
        return rtnJson;
    }
}
