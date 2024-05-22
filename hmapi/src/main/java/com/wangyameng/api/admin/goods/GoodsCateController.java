package com.wangyameng.api.admin.goods;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditGoodCateReq;
import com.wangyameng.dto.GoodsListReq;
import com.wangyameng.service.admin.GoodsCatService;
import com.wangyameng.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName GoodsCateController.java
 * @Description TODO
 * @createTime 2024-05-05 23:37:00
 */
@RestController
public class GoodsCateController {
    @Autowired
    private GoodsCatService goodsCatService;
    @GetMapping("/admin/goods.goodsCate/index")
    public AjaxResult index() throws Exception {
        return goodsCatService.getCateList();
    }

    @PostMapping("/admin/goods.goodsCate/add")
    public AjaxResult add(@RequestBody AddOrEditGoodCateReq req) throws Exception {
        return goodsCatService.addCate(req);
    }

    @PostMapping("/admin/goods.goodsCate/edit")
    public AjaxResult edit(@RequestBody AddOrEditGoodCateReq req) throws Exception {
        return goodsCatService.editCate(req);
    }

    @GetMapping("/admin/goods.goodsCate/del")
    public AjaxResult del(Integer id) throws Exception {
        return goodsCatService.delCate(id);
    }

}
