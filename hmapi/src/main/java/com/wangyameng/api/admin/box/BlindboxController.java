package com.wangyameng.api.admin.box;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBindboxReq;
import com.wangyameng.service.admin.BindboxService;
import com.wangyameng.service.admin.BindboxTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxController.java
 * @Description TODO
 * @createTime 2024-05-14 21:32:00
 */
@RestController("adminBlindboxController")
public class BlindboxController {
    @Autowired
    private BindboxService bindboxService;

    @GetMapping("/admin/box.blindbox/index")
    public AjaxResult index(Integer page, Integer limit, String name) throws Exception {
        return bindboxService.getBlindboxList(page, limit, name);
    }

    @PostMapping("/admin/box.blindbox/add")
    public AjaxResult add(@RequestBody AddOrEditBindboxReq req) throws Exception {
        return bindboxService.addBlindbox(req);
    }

    @PostMapping("/admin/box.blindbox/edit")
    public AjaxResult edit(@RequestBody AddOrEditBindboxReq req) throws Exception {
        return bindboxService.editBlindbox(req);
    }

    @GetMapping("/admin/box.blindbox/del")
    public AjaxResult del(Integer id) throws Exception {
        return bindboxService.delBlindbox(id);
    }
}
