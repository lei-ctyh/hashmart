package com.wangyameng.api.uniapp.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.BagService;
import com.wangyameng.service.uniapp.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BagController.java
 * @Description 仓库相关
 * @createTime 2024-04-10 11:29:00
 */
@RestController
public class BagController {

    @Autowired
    private BagService bagService;
    @GetMapping("uniapp/getBagBoxList")
    public AjaxResult cateList() {
        return bagService.getBagBoxList(1,1,"1");
    }
}
