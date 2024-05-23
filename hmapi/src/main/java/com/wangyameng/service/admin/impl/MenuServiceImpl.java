package com.wangyameng.service.admin.impl;
import java.util.Date;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.SysMenuDao;
import com.wangyameng.dto.AddOrEditMenuReq;
import com.wangyameng.entity.SysMenu;
import com.wangyameng.service.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName MenuServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-23 22:10:00
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Override
    public AjaxResult getAllMenu() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getSort);
        List<SysMenu> sysMenus = sysMenuDao.selectList(queryWrapper);
        JSONArray arr = new JSONArray();
        for (SysMenu sysMenu : sysMenus) {
            arr.add(PubfuncUtil.parseToUnderlineJson(sysMenu));
        }
        return AjaxResult.dataReturn(0,"success", PubfuncUtil.makeTree(arr));
    }

    @Override
    public AjaxResult addMenu(AddOrEditMenuReq addOrEditMenuReq) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setPid(addOrEditMenuReq.getPid());
        sysMenu.setType(addOrEditMenuReq.getType());
        sysMenu.setName(addOrEditMenuReq.getName());
        sysMenu.setAuth(addOrEditMenuReq.getAuth());
        sysMenu.setPath(addOrEditMenuReq.getPath());
        sysMenu.setIcon(addOrEditMenuReq.getIcon());
        sysMenu.setComponent(addOrEditMenuReq.getComponent());
        sysMenu.setSort(addOrEditMenuReq.getSort());
        sysMenu.setStatus(addOrEditMenuReq.getStatus());
        sysMenu.setCreateTime(new Date());
        sysMenuDao.insert(sysMenu);
        return AjaxResult.dataReturn(0,"添加成功");
    }
    public AjaxResult editMenu(AddOrEditMenuReq addOrEditMenuReq) {
        SysMenu sysMenu = sysMenuDao.selectById(addOrEditMenuReq.getId());
        sysMenu.setPid(addOrEditMenuReq.getPid());
        sysMenu.setType(addOrEditMenuReq.getType());
        sysMenu.setName(addOrEditMenuReq.getName());
        sysMenu.setAuth(addOrEditMenuReq.getAuth());
        sysMenu.setPath(addOrEditMenuReq.getPath());
        sysMenu.setIcon(addOrEditMenuReq.getIcon());
        sysMenu.setComponent(addOrEditMenuReq.getComponent());
        sysMenu.setSort(addOrEditMenuReq.getSort());
        sysMenu.setStatus(addOrEditMenuReq.getStatus());
        sysMenu.setUpdateTime(new Date());
        sysMenuDao.updateById(sysMenu);
        return AjaxResult.dataReturn(0,"编辑成功");
    }

    @Override
    public AjaxResult delMenu(Integer id) {
        sysMenuDao.deleteById(id);
        return AjaxResult.dataReturn(0,"删除成功");
    }
}
