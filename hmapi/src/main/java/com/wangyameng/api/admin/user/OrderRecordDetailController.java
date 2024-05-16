package com.wangyameng.api.admin.user;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.entity.OrderRecordDetail;
import com.wangyameng.service.admin.OrderRecordDetailService;
import com.wangyameng.service.admin.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName OrderRecordDetailController.java
 * @Description TODO
 * @createTime 2024-05-17 00:08:00
 */
@RestController("adminUserOrderRecordDetailController")
public class OrderRecordDetailController {
    @Autowired
    private OrderRecordDetailService orderRecordDetailService;

    @GetMapping("/admin/user.orderRecordDetail/index")
    // order_record_id=60&page=1&limit=1
    public AjaxResult index(Integer order_record_id, Integer page, Integer limit) {
        return orderRecordDetailService.getList(order_record_id, page, limit);
    }
}
