package com.wangyameng.api.uniapp.shop;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName Shop.java
 * @Description 商品相关接口
 * @createTime 2023-12-04 11:51:00
 */
@RestController
public class Shop {
    @Autowired
    private ShopService shopService;
    @GetMapping("uniapp/goods/shop/cateList")
    public AjaxResult cateList() {
        return shopService.getCateList();
    }

    @GetMapping("uniapp/goods/shop/slider")
    public AjaxResult getSliderList() {
        return shopService.getSliderList();
    }

    @GetMapping("uniapp/goods/shop/goodsList")
    public AjaxResult getGoodsList(int page, int limit) {
        return shopService.getGoodsList(page, limit);
    }

    @GetMapping("uniapp/goods/shop/cateDetail")
    public AjaxResult cateDetail(int page, int limit, int cate_id) {
        return shopService.getGoodsListByCateId(page, limit, cate_id);
    }

    @GetMapping("uniapp/goods/shop/goodsInfo")
    public AjaxResult getGoodsInfo(int id) {
        return shopService.getGoodsInfo(id);
    }


}
