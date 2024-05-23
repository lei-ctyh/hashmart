package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysRoleDao;
import com.wangyameng.dto.AddOrEditRoleReq;
import com.wangyameng.entity.SysRole;
import com.wangyameng.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName RoleServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-23 21:49:00
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Override
    public AjaxResult getList(String name, Integer page, Integer limit) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(SysRole::getName, name);
        }

        queryWrapper.gt(SysRole::getId, 1);
        queryWrapper.orderByDesc(SysRole::getId);

        Page<SysRole> ipage = sysRoleDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (SysRole role : ipage.getRecords()) {
            JSONObject row = PubfuncUtil.parseToUnderlineJson(role);
            row.put("rule", JSONArray.parseArray(role.getRule()));
            rows.add(row);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0,"success", rtnJson);
    }

    @Override
    public AjaxResult add(AddOrEditRoleReq addOrEditRoleReq) {
        SysRole sysRole = new SysRole();
        sysRole.setName(addOrEditRoleReq.getName());
        sysRole.setRule(JSONArray.from(addOrEditRoleReq.getRule()).toString());
        sysRole.setCreateTime(new Date());
        sysRole.setStatus(addOrEditRoleReq.getStatus());
        sysRoleDao.insert(sysRole);
        return AjaxResult.dataReturn(0, "success");
    }

    @Override
    public AjaxResult edit(AddOrEditRoleReq addOrEditRoleReq) {
        SysRole sysRole = sysRoleDao.selectById(addOrEditRoleReq.getId());
        if (sysRole == null) {
            return AjaxResult.dataReturn(-1, "角色不存在");
        }
        sysRole.setName(addOrEditRoleReq.getName());
        sysRole.setRule(JSONArray.from(addOrEditRoleReq.getRule()).toString());
        sysRole.setUpdateTime(new Date());
        sysRole.setStatus(addOrEditRoleReq.getStatus());
        sysRoleDao.updateById(sysRole);
        return AjaxResult.dataReturn(0, "编辑成功");
    }

    @Override
    public AjaxResult delete(Integer id) {
        SysRole sysRole = sysRoleDao.selectById(id);
        if (sysRole == null) {
            return AjaxResult.dataReturn(-1, "角色不存在");
        }
        sysRoleDao.deleteById(id);
        return AjaxResult.dataReturn(0, "删除成功");
    }

    @Override
    public AjaxResult getAllRole() {
        /**
         *  $sysRoleModel = new SysRole();
         *         return $sysRoleModel->getAllList([['id', '>', 1]]);
         */
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(SysRole::getId, 1);
        queryWrapper.orderByDesc(SysRole::getId);
        JSONArray roleList = new JSONArray();
        for (SysRole role : sysRoleDao.selectList(queryWrapper)) {
            roleList.add(PubfuncUtil.parseToUnderlineJson(role));
        }
        return AjaxResult.dataReturn(0, "success", roleList);
    }
}
