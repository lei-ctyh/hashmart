package com.wangyameng.api.uniapp.goods;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.BlindboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxController.java
 * @Description 盲盒接口
 * @history v1.0.0, 2023-11-27 14:23:00, init
 * @createTime 2023-11-27 14:23:00
 */
@RestController
public class BlindboxController {
    @Autowired
    private BlindboxService blindboxService;
    @GetMapping("/uniapp/goods/blindboxDetail")

    public AjaxResult detail(@RequestParam String id){
        return blindboxService.getBlindboxDetail(id);
    }

    @GetMapping("/uniapp/goods/blindbox/goodsInfo")
    public AjaxResult getGoodsInfo(@RequestParam String id){
        return blindboxService.getGoodsInfo(id);
    }

}
