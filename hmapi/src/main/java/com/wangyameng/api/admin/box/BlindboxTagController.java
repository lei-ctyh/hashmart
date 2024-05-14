package com.wangyameng.api.admin.box;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBlindboxTagReq;
import com.wangyameng.service.admin.BindboxTagService;
import com.wangyameng.service.admin.GoodsCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName blindboxTagController.java
 * @Description TODO
 * @createTime 2024-05-14 00:26:00
 */
@RestController
public class BlindboxTagController {

    @Autowired
    private BindboxTagService bindboxTagService;
    @GetMapping("/admin/box.blindboxTag/index")
    public AjaxResult index(Integer page, Integer limit, String name) throws Exception {
        return bindboxTagService.getBlindboxTagList(page, limit, name);
    }

    @PostMapping("/admin/box.blindboxTag/add")
    public AjaxResult add(@RequestBody  AddOrEditBlindboxTagReq req) throws Exception {
        return bindboxTagService.addBlindboxTag(req);
    }

    @PostMapping("/admin/box.blindboxTag/edit")
    public AjaxResult edit(@RequestBody  AddOrEditBlindboxTagReq req) throws Exception {
        return bindboxTagService.editBlindboxTag(req);
    }
    @GetMapping("/admin/box.blindboxTag/del")
    public AjaxResult delete(Integer id) throws Exception {
        return bindboxTagService.deleteBlindboxTag(id);
    }


}
