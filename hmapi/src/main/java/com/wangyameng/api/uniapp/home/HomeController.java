package com.wangyameng.api.uniapp.home;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName HomeController.java
 * @Description TODO
 * @createTime 2023-11-14 18:08:00
 */
@RestController
public class HomeController {
    @Autowired
    private HomeService homeService;
    @GetMapping("uniapp/home/index")

    public AjaxResult index( @RequestParam int page,  @RequestParam int limit) {
        return homeService.getHomeData(page, limit);
    }
}
