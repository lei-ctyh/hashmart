package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysAdminDao;
import com.wangyameng.dao.SysRoleDao;
import com.wangyameng.dto.AddOrEditAdminReq;
import com.wangyameng.entity.SysAdmin;
import com.wangyameng.entity.SysRole;
import com.wangyameng.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName AdminServiceimpl.java
 * @Description TODO
 * @createTime 2024-05-23 20:57:00
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private SysAdminDao sysAdminDao;
    @Autowired
    private SysRoleDao roleDao;
    @Override
    public AjaxResult getList(String username, Integer page, Integer limit) {
        LambdaQueryWrapper<SysAdmin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(SysAdmin::getUsername, username);
        }
        queryWrapper.orderByDesc(SysAdmin::getId);
        Page<SysAdmin> ipage = sysAdminDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (SysAdmin record : ipage.getRecords()) {
            JSONObject row = PubfuncUtil.parseToUnderlineJson(record);
            LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
            roleQueryWrapper.eq(SysRole::getId, record.getRoleId());
            SysRole role = roleDao.selectOne(roleQueryWrapper);
            row.put("role", PubfuncUtil.parseToUnderlineJson(role));
            rows.add(row);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0,"success",rtnJson);
    }

    @Override
    public AjaxResult add(AddOrEditAdminReq addOrEditAdminReq) {
        // 检查唯一
        LambdaQueryWrapper<SysAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAdmin::getUsername, addOrEditAdminReq.getUsername());
        SysAdmin has = sysAdminDao.selectOne(queryWrapper);
        if (has!= null) {
            return AjaxResult.dataReturn(-2, "该登录名已存在");
        }
        // 新增
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setRoleId(addOrEditAdminReq.getRole_id());
        sysAdmin.setUsername(addOrEditAdminReq.getUsername());
        sysAdmin.setNickname(addOrEditAdminReq.getNickname());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,30);
        sysAdmin.setSalt(uuid);
        sysAdmin.setPassword(PubfuncUtil.makePassword(addOrEditAdminReq.getPassword(), uuid));
        sysAdmin.setAvatar(addOrEditAdminReq.getAvatar());
        sysAdmin.setEmail(addOrEditAdminReq.getEmail());
        sysAdmin.setPhone(addOrEditAdminReq.getPhone());
        sysAdmin.setStatus(sysAdmin.getStatus());
        sysAdmin.setCreateTime(new Date());
        sysAdminDao.insert(sysAdmin);
        return AjaxResult.dataReturn(0, "新增成功");
    }

    @Override
    public AjaxResult edit(AddOrEditAdminReq addOrEditAdminReq) {
        // 检查唯一
        LambdaQueryWrapper<SysAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAdmin::getUsername, addOrEditAdminReq.getUsername());
        queryWrapper.ne(SysAdmin::getId, addOrEditAdminReq.getId());
        SysAdmin has = sysAdminDao.selectOne(queryWrapper);
        if (has!= null) {
            return AjaxResult.dataReturn(-2, "该登录名已存在");
        }
        // 编辑
        SysAdmin sysAdmin = sysAdminDao.selectById(addOrEditAdminReq.getId());
        if (sysAdmin == null) {
            return AjaxResult.dataReturn(-1, "该用户不存在");
        }
        sysAdmin.setId(addOrEditAdminReq.getId());
        sysAdmin.setRoleId(addOrEditAdminReq.getRole_id());
        sysAdmin.setUsername(addOrEditAdminReq.getUsername());
        sysAdmin.setNickname(addOrEditAdminReq.getNickname());
        if (StringUtils.isNotBlank(addOrEditAdminReq.getPassword())) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,30);
            sysAdmin.setSalt(uuid);
            sysAdmin.setPassword(PubfuncUtil.makePassword(addOrEditAdminReq.getPassword(), uuid));
        }
        sysAdmin.setAvatar(addOrEditAdminReq.getAvatar());
        sysAdmin.setEmail(addOrEditAdminReq.getEmail());
        sysAdmin.setPhone(addOrEditAdminReq.getPhone());
        sysAdmin.setStatus(sysAdmin.getStatus());
        sysAdmin.setUpdateTime(new Date());
        sysAdminDao.updateById(sysAdmin);
        return AjaxResult.dataReturn(0, "编辑成功");
    }

    @Override
    public AjaxResult del(Integer id) {
        if (id == 1) {
            return AjaxResult.dataReturn(-1, "超级管理员不能删除");
        }
        sysAdminDao.deleteById(id);
        return AjaxResult.dataReturn(0, "删除成功");
    }
}
