package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysCityDao;
import com.wangyameng.dao.UserAddressDao;
import com.wangyameng.dto.EditeUserAddressReq;
import com.wangyameng.entity.SysCity;
import com.wangyameng.entity.UserAddress;
import com.wangyameng.service.admin.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName UserAddressServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-24 00:45:00
 */
@Service("adminUserAddressService")
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private SysCityDao sysCityDao;
    @Override
    public AjaxResult getList(Integer userId, Integer page, Integer limit) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        if (userId!= null) {
            wrapper.eq(UserAddress::getUserId, userId);
        }
        wrapper.orderByDesc(UserAddress::getId);
        Page<UserAddress> ipage = userAddressDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (UserAddress userAddress : ipage.getRecords()) {
            JSONObject row = PubfuncUtil.parseToUnderlineJson(userAddress);
            rows.add(row);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult areas() {

        LambdaQueryWrapper<SysCity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCity::getIsShow, 1);
        wrapper.select(SysCity::getId, SysCity::getPid, SysCity::getName, SysCity::getLevel);
        wrapper.orderByAsc(SysCity::getId);
        List<SysCity> sysCities = sysCityDao.selectList(wrapper);
        JSONArray rows = new JSONArray();
        for (SysCity sysCity : sysCities) {
            JSONObject row = PubfuncUtil.parseToUnderlineJson(sysCity);
            row.put("value", sysCity.getId());
            row.put("label", sysCity.getName());
            rows.add(row);
        }
        return AjaxResult.dataReturn(0, "success", PubfuncUtil.makeTree(rows));
    }

    @Override
    public AjaxResult edit(EditeUserAddressReq editeUserAddressReq) {
        LambdaQueryWrapper<SysCity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysCity::getId, editeUserAddressReq.getProvince_code(), editeUserAddressReq.getCity_code(), editeUserAddressReq.getArea_code());
        List<SysCity> sysCities = sysCityDao.selectList(wrapper);
        if (sysCities.size() != 3) {
            return AjaxResult.dataReturn(-2, "省市区数据错误");
        }
        List<Map<String, Object>> codeNames = new ArrayList<>();
        for (SysCity sysCity : sysCities) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sysCity.getId());
            map.put("name", sysCity.getName());
            codeNames.add(map);
        }

        LambdaQueryWrapper<UserAddress> userAddressWrapper = new LambdaQueryWrapper<>();
        userAddressWrapper.eq(UserAddress::getId, editeUserAddressReq.getId());
        UserAddress userAddress = userAddressDao.selectOne(userAddressWrapper);
        if (userAddress == null) {
            return AjaxResult.dataReturn(-1, "用户地址不存在");
        }
        userAddress.setProvinceCode(editeUserAddressReq.getProvince_code());
        userAddress.setProvinceName((String) codeNames.get(0).get("name"));
        userAddress.setCityCode(editeUserAddressReq.getCity_code());
        userAddress.setCityName((String) codeNames.get(1).get("name"));
        userAddress.setAreaCode(editeUserAddressReq.getArea_code());
        userAddress.setAreaName((String) codeNames.get(2).get("name"));
        userAddress.setAddress(editeUserAddressReq.getAddress());
        userAddress.setPhone(editeUserAddressReq.getPhone());
        userAddress.setReceiver(editeUserAddressReq.getReceiver());
        userAddress.setUpdateTime(new Date());
        userAddressDao.updateById(userAddress);
        return AjaxResult.dataReturn(0, "编辑成功");
    }

    @Override
    public AjaxResult del(Integer id) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getId, id);
        UserAddress userAddress = userAddressDao.selectOne(wrapper);
        if (userAddress == null) {
            return AjaxResult.dataReturn(-1, "用户地址不存在");
        }
        userAddressDao.deleteById(id);
        return AjaxResult.dataReturn(0, "删除成功");
    }

}
