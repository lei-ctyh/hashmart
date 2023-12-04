package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.GoodsCateDao;
import com.wangyameng.dao.GoodsDao;
import com.wangyameng.entity.Goods;
import com.wangyameng.entity.GoodsCate;
import com.wangyameng.service.uniapp.ActivityService;
import com.wangyameng.service.uniapp.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName ShopServiceImpl.java
 * @Description TODO
 * @createTime 2023-12-04 11:58:00
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private GoodsCateDao goodsCateDao;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public AjaxResult getCateList() {
        JSONArray rtnData = new JSONArray();
        LambdaQueryWrapper<GoodsCate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCate::getStatus, 1)
                    .orderByDesc(GoodsCate::getSort);
        List<GoodsCate> cateList = goodsCateDao.selectList(queryWrapper);
        for (GoodsCate goodsCate : cateList) {
            JSONObject data = new JSONObject();
            data.put("id", goodsCate.getId());
            data.put("name", goodsCate.getName());
            data.put("icon", PubfuncUtil.replaceBecomeServerHost(goodsCate.getIcon()));
            rtnData.add(data);
        }
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    @Override
    public AjaxResult getSliderList() {
        // {"code":0,"data":[{"id":2,"title":"最新款iphone、智能手机等你来拿！","blindbox_id":0,"goods_id":7,"pic":"http://leiaimeng.mynatapp.cc/storage/local/20230331/9e391e2412340e67afa3c6c8013df2c1.jpg","other":""}],"msg":"success"}
        return AjaxResult.dataReturn(0, "success", activityService.getSliderData(ActivityService.SLIDER_TYPE_SHOP));
    }

    @Override
    public AjaxResult getGoodsList(int page, int limit) {
        return getGoodsListByCateId(page, limit, null);
    }

    @Override
    public AjaxResult getGoodsListByCateId(int page, int limit, Integer cateId) {
        JSONObject rtnData = new JSONObject();
        if (!PubfuncUtil.checkOpen()) {
            return AjaxResult.dataReturn(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }

        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getStatus, 1)
                    .eq(Goods::getGoodsType, 1)
                    .eq(Goods::getDeleteFlag, 1)
                    .orderByDesc(Goods::getSort);
        if (cateId != null) {
            queryWrapper.eq(Goods::getCateId, cateId);
        }
        Page<Goods> ipage = new Page<>();
        ipage.setPages(page);
        ipage.setSize(limit);
        ipage = goodsDao.selectPage(ipage, queryWrapper);
        rtnData.put("total", ipage.getTotal());
        rtnData.put("per_page", ipage.getSize());
        rtnData.put("current_page", ipage.getCurrent());
        rtnData.put("last_page", ipage.getPages());
        JSONArray datas = new JSONArray();
        for (Goods good : ipage.getRecords()) {
            JSONObject data = new JSONObject();
            data.put("id", good.getId());
            data.put("type", good.getType());
            data.put("name", good.getName());
            data.put("sub_title", good.getSubTitle());
            data.put("image", PubfuncUtil.replaceBecomeServerHost(good.getImage()));
            data.put("stock", good.getStock());
            data.put("price", good.getPrice());
            data.put("integral_price", good.getIntegralPrice());
            data.put("sales", good.getSales());
            datas.add(data);
        }
        rtnData.put("data", datas);

        // {"code":0,"data":{"total":2,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":36,"type":1,"name":"111","sub_title":"","image":"http://leiaimeng.mynatapp.cc/storage/local/20230331/27407a64b20a489320bea1790810d7fb.png","stock":-1,"price":"0.00","integral_price":"0.00","sales":0},{"id":38,"type":1,"name":"充电器","sub_title":"快来抢购","image":"http://leiaimeng.mynatapp.cc/storage/local/20230331/3d13884adf3d42404636db6aea0ccd39.png","stock":200,"price":"169.00","integral_price":"159.00","sales":0}]},"msg":"success"}
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    @Override
    public AjaxResult getGoodsInfo(int id) {
        return null;
    }


}
