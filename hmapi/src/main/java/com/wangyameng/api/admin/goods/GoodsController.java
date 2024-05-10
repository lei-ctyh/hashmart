package com.wangyameng.api.admin.goods;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditGoodReq;
import com.wangyameng.dto.GoodsListReq;
import com.wangyameng.dto.ShelvesGoodReq;
import com.wangyameng.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName GoodsContraller.java
 * @Description TODO
 * @createTime 2024-05-05 22:56:00
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/admin/goods.goods/index")
    public AjaxResult index(String goods_type, String cate_id, String status, String name, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) throws Exception {
        GoodsListReq goodsListReq = new GoodsListReq();
        goodsListReq.setGoods_type(goods_type);
        goodsListReq.setCate_id(cate_id);
        goodsListReq.setStatus(status);
        goodsListReq.setName(name);
        goodsListReq.setPage(page);
        goodsListReq.setLimit(limit);
        return goodsService.getGoodsList(goodsListReq);
    }

    @PostMapping("/admin/goods.goods/add")
    public AjaxResult add(@RequestBody AddOrEditGoodReq req) throws Exception {
        return goodsService.addGood(req);
    }

    @PostMapping("/admin/goods.goods/edit")
    public AjaxResult edit(@RequestBody AddOrEditGoodReq req) throws Exception {
        return goodsService.editGood(req);
    }

    @PostMapping("/admin/goods.goods/del")
    public AjaxResult delete(Integer id) throws Exception {
        return goodsService.delGood(id);
    }

    @PostMapping("/admin/goods.goods/shelves")
    public AjaxResult shelves(@RequestBody ShelvesGoodReq req) throws Exception {
        return goodsService.shelvesGood(req);
    }
}
