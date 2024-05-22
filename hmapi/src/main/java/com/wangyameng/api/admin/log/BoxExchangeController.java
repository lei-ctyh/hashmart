package com.wangyameng.api.admin.log;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.BoxExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName BoxExchangeController.java
 * @Description TODO
 * @createTime 2024-05-16 22:16:00
 */
@RestController
public class BoxExchangeController {
    @Autowired
    BoxExchangeService boxExchangeService;
    @GetMapping("/admin/log.boxExchange/index")
    public AjaxResult index(Integer user_id, String username, String exchange_no, Integer page, Integer limit) {

        return boxExchangeService.getList(user_id, username, exchange_no, page, limit);
    }
    @GetMapping("admin/log.boxExchange/detail")
    public AjaxResult detail (Integer exchange_id) {
        return boxExchangeService.getDetail(exchange_id);
    }
}
