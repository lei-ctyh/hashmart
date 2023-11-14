package com.wangyameng.api.uniapp.home;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName HomeController.java
 * @Description TODO
 * @createTime 2023-11-14 18:08:00
 */
@Api(tags = "微信小程序首页")
@RestController
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("uniapp/home/index")
    @ApiOperation("获取首页聚合数据")
    public AjaxResult index(@ApiParam(value = "聚合数据所在页码", required = true) @RequestParam int page, @ApiParam(value = "需要获取的数据条数", required = true) @RequestParam int limit) {
        return homeService.getHomeData(page, limit);
    }
}
