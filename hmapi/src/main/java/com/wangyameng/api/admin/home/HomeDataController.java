package com.wangyameng.api.admin.home;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.admin.HomeDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeDataController.java
 * @Description 后台首页数据
 * @createTime 2023-11-04 14:28:00
 */
@Api(tags = "后台系统主页")
@RestController
public class HomeDataController {
    @Autowired
    private HomeDataService homeDataService;
    @GetMapping("/admin/home.homeData/index")
    @ApiOperation(value = "获取后台首页数据")
    public AjaxResult index() throws Exception {
        return homeDataService.getHomeData();
    }


}
