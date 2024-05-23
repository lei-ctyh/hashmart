package com.wangyameng.api.admin.rule;

import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dto.AddOrEditMenuReq;
import com.wangyameng.service.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName MenuController.java
 * @Description TODO
 * @createTime 2024-05-23 22:07:00
 */

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/admin/rule.menu/index")
    public AjaxResult index() {
        return menuService.getAllMenu();
    }

    @PostMapping("/admin/rule.menu/add")
    public AjaxResult addMenu(@RequestBody AddOrEditMenuReq addOrEditMenuReq) {
        return menuService.addMenu(addOrEditMenuReq);
    }
    @PostMapping("/admin/rule.menu/edit")
    public AjaxResult editMenu(@RequestBody AddOrEditMenuReq addOrEditMenuReq) {
        return menuService.editMenu(addOrEditMenuReq);
    }
    @GetMapping("/admin/rule.menu/del")
    public AjaxResult delMenu(Integer id) {
        return menuService.delMenu(id);
    }

    @GetMapping("/admin/rule.menu/getAllMenu")
    public AjaxResult getAllMenu(Integer id) {
        return menuService.getAllMenu();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Random random = new Random();
            System.out.println(random.nextInt(2) );
        }
    }
}
