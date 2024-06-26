package com.wangyameng.api.uniapp.order;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.service.uniapp.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName BagController.java
 * @Description 仓库相关
 * @createTime 2024-04-10 11:29:00
 */
@RestController
public class BagController {

    @Autowired
    private BagService bagService;
    @GetMapping("/uniapp/order/getBagBoxList")
    public AjaxResult getBagBoxList(int page,int limit,int status) {
        return bagService.getBagBoxList(page,limit,status);
    }

    @GetMapping("/uniapp/order/getBagGoodsList")
    public AjaxResult getBagGoodsList(int page,int limit,int status) {
        return bagService.getBagGoodsList(page,limit,status);
    }

    @PostMapping("/uniapp/order/bagBoxExchange")
    public AjaxResult bagBoxExchange(@RequestParam("box_id") String boxIdStr, @RequestParam("type") int type) {
        return bagService.bagBoxExchange(boxIdStr,type);
    }

    @PostMapping("/uniapp/order/bagBoxTrail")
    public AjaxResult bagBoxTrail(@RequestParam("box_id") String boxId,@RequestParam("type") int type, @RequestParam("address_id") int addressId) {
        return bagService.bagBoxTrail(boxId, addressId,type);
    }

    @PostMapping("/uniapp/order/bagBoxDeliver")
    public AjaxResult boxDeliver(@RequestParam("box_id") String boxId, @RequestParam("type") int type, @RequestParam("address_id") int addressId,@RequestParam("pay_way") int payWay, @RequestParam("platform") String platform) {
        return bagService.bagBoxDelivery(boxId, addressId,type,platform,payWay);
    }

}
