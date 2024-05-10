package com.wangyameng.api.admin.system;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName SystemContraller.java
 * @Description TODO
 * @createTime 2024-05-06 22:46:00
 */

@RestController
public class SystemController {
    @Autowired
    private SystemService systemService;

    @GetMapping("/admin/system.system/base")
    public AjaxResult base() throws Exception {
        return systemService.getBase();
    }

}
