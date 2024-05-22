package com.wangyameng.api.admin.box;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditBlindboxDetailReq;
import com.wangyameng.service.admin.BlindboxDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName BlindboxDetailController.java
 * @Description TODO
 * @createTime 2024-05-14 22:24:00
 */
@RestController
public class BlindboxDetailController {
    @Autowired
    private BlindboxDetailService blindboxDetailService;

    @GetMapping("/admin/box.blindboxDetail/index")
    public AjaxResult index(Integer blindbox_id, Integer tag_id, String goods_name, Integer page, Integer limit) {
        return blindboxDetailService.getBlindboxDetailList(blindbox_id, tag_id, goods_name, page, limit);
    }

    @GetMapping("/admin/box.blindboxDetail/getLotteryProbability")
    public AjaxResult getLotteryProbability(Integer blindbox_id, Integer percent) {
        return blindboxDetailService.getLotteryProbability(blindbox_id, percent);
    }

    @GetMapping("/admin/box.blindboxDetail/getLotteryNumRange")
    public AjaxResult getLotteryNumRange(Integer blindbox_id, Double percent, Integer detail_id) {
        return blindboxDetailService.getLotteryNumRange(blindbox_id, percent, detail_id);
    }

    @PostMapping("/admin/box.blindboxDetail/add")
    public AjaxResult add(@RequestBody AddOrEditBlindboxDetailReq req) {
        return blindboxDetailService.addBlindboxDetail(req);
    }
    @PostMapping("/admin/box.blindboxDetail/edit")
    public AjaxResult edit(@RequestBody AddOrEditBlindboxDetailReq req) {
        return blindboxDetailService.editBlindboxDetail(req);
    }
    @GetMapping("/admin/box.blindboxDetail/del")
    public AjaxResult del(Integer id, Integer blindbox_id) {
        return blindboxDetailService.delBlindboxDetail(id, blindbox_id);
    }

}
