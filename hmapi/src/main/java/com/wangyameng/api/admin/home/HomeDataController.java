package com.wangyameng.api.admin.home;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.HomeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName HomeDataController.java
 * @Description 后台首页数据
 * @createTime 2023-11-04 14:28:00
 */
@RestController
public class HomeDataController {
    @Autowired
    private HomeDataService homeDataService;
    @GetMapping("/admin/home.homeData/index")

    public AjaxResult index() throws Exception {
        return homeDataService.getHomeData();
    }
}
