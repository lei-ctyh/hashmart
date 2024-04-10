package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.SysCityDao;
import com.wangyameng.dao.UserAddressDao;
import com.wangyameng.entity.SysCity;
import com.wangyameng.entity.UserAddress;
import com.wangyameng.service.uniapp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName AddressServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-10 12:53:00
 */

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    UserAddressDao userAddressDao;
    @Autowired
    SysCityDao sysCityDao;
    @Override
    public AjaxResult getList() {
        //         $field = ['id', 'receiver', 'phone', 'province_code', 'province_name', 'city_code', 'city_name', 'area_code', 'area_name', 'address', 'default_flag'];
        String id = UserSessionContext.get().getString("id");
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<UserAddress>();
        queryWrapper.eq(UserAddress::getUserId, id);
        List<UserAddress> userAddressList = userAddressDao.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        for (UserAddress userAddress : userAddressList) {
            jsonArray.add(PubfuncUtil.parseToUnderlineJson(userAddress));
        }
        return AjaxResult.dataReturn(0, "success", jsonArray);
    }

    @Override
    public AjaxResult getOption() {
        LambdaQueryWrapper<SysCity> queryWrapper = new LambdaQueryWrapper<SysCity>();
        queryWrapper.eq(SysCity::getIsShow, 1);
        queryWrapper.orderByAsc(SysCity::getId);
        List<SysCity> sysCities = sysCityDao.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        if (sysCities != null && !sysCities.isEmpty()) {
            for (SysCity sysCity : sysCities) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", sysCity.getId());
                jsonObject.put("label", sysCity.getName());
                jsonObject.put("id", sysCity.getId());
                jsonObject.put("pid", sysCity.getPid());
                jsonArray.add(jsonObject);
            }
        }
        return AjaxResult.dataReturn(0, "success", PubfuncUtil.makeTree(jsonArray));
    }


}
