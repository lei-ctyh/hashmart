package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.SysCityDao;
import com.wangyameng.dao.UserAddressDao;
import com.wangyameng.entity.SysCity;
import com.wangyameng.entity.UserAddress;
import com.wangyameng.service.uniapp.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public AjaxResult edit(Map<String, Object> params) {
        Integer id = UserSessionContext.get().getInteger("id");
        String province_code = params.get("province_code").toString();
        String city_code = params.get("city_code").toString();
        String area_code = params.get("area_code").toString();
        String addressId = (String) params.get("id");
        String is_default = (String) params.get("is_default");

        if (StringUtils.isBlank(addressId)) {
            return AjaxResult.dataReturn(-1, "参数错误");
        }
        LambdaQueryWrapper<UserAddress> userAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userAddressLambdaQueryWrapper.eq(UserAddress::getId, addressId);
        UserAddress userAddress = userAddressDao.selectOne(userAddressLambdaQueryWrapper);
        if (userAddress == null) {
            return AjaxResult.dataReturn(-2, "地址不存在");
        }
        if (!Objects.equals(userAddress.getUserId(), id)) {
            return AjaxResult.dataReturn(-3, "地址不属于当前用户");
        }
        LambdaQueryWrapper<SysCity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysCity::getId, province_code, city_code, area_code);
        List<SysCity> sysCities = sysCityDao.selectList(queryWrapper);
        if (sysCities.size() != 3) {
            return AjaxResult.dataReturn(-2, "省市区数据错误");
        }

        if (is_default.equals("1")) {
            // 先将其他地址的默认标识改为0
            setNotDefault(id);
        }

        userAddress.setProvinceCode(province_code);
        userAddress.setProvinceName(sysCities.get(0).getName());
        userAddress.setCityCode(city_code);
        userAddress.setCityName(sysCities.get(1).getName());
        userAddress.setAreaCode(area_code);
        userAddress.setAreaName(sysCities.get(2).getName());
        userAddress.setAddress(params.get("address").toString());
        userAddress.setDefaultFlag(is_default.equals("1") ? 1 : 0);
        userAddress.setUpdateTime(new Date());
        userAddressDao.updateById(userAddress);
        return AjaxResult.dataReturn(0, "修改成功");
    }


    @Override
    @Transactional
    public AjaxResult add(Map<String, Object> params) {
        Integer id = UserSessionContext.get().getInteger("id");
        String province_code = params.get("province_code").toString();
        String city_code = params.get("city_code").toString();
        String area_code = params.get("area_code").toString();

        LambdaQueryWrapper<SysCity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysCity::getId, province_code, city_code, area_code);
        List<SysCity> sysCities = sysCityDao.selectList(queryWrapper);
        if (sysCities.size() != 3) {
            return AjaxResult.dataReturn(-2, "省市区数据错误");
        }

        UserAddress userAddress = getUserAddress(params, id, sysCities);
        userAddressDao.insert(userAddress);
        return AjaxResult.dataReturn(0, "添加成功");
    }

    @Override
    public AjaxResult setDefault(String addressId) {
        Integer id = UserSessionContext.get().getInteger("id");
        setNotDefault(id);
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddress::getId, addressId);
        UserAddress userAddress = userAddressDao.selectOne(queryWrapper);
        if (userAddress == null) {
            return AjaxResult.dataReturn(-2, "地址不存在");
        }
        if (!Objects.equals(userAddress.getUserId(), id)) {
            return AjaxResult.dataReturn(-3, "地址不属于当前用户");
        }
        userAddress.setDefaultFlag(1);
        userAddress.setUpdateTime(new Date());
        userAddressDao.updateById(userAddress);
        return AjaxResult.dataReturn(0, "设置默认地址成功");
    }


    private UserAddress getUserAddress(Map<String, Object> params, Integer id, List<SysCity> sysCities) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(id);
        userAddress.setReceiver(params.get("receiver").toString());
        userAddress.setPhone(params.get("phone").toString());
        userAddress.setProvinceCode(params.get("province_code").toString());
        userAddress.setProvinceName(sysCities.get(0).getName());
        userAddress.setCityCode(params.get("city_code").toString());
        userAddress.setCityName(sysCities.get(1).getName());
        userAddress.setAreaCode(params.get("area_code").toString());
        userAddress.setAreaName(sysCities.get(2).getName());
        userAddress.setAddress(params.get("address").toString());
        userAddress.setDefaultFlag(params.get("is_default") == null ? 0 : Integer.parseInt(params.get("is_default").toString()));
        userAddress.setCreateTime(new Date());
        userAddress.setUpdateTime(new Date());
        return userAddress;
    }


    private int setNotDefault(Integer userId) {
        LambdaQueryWrapper<UserAddress> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(UserAddress::getDefaultFlag, 1);
        updateWrapper.eq(UserAddress::getUserId, userId);
        List<UserAddress> userAddresses = userAddressDao.selectList(updateWrapper);
        for (UserAddress address : userAddresses) {
            address.setDefaultFlag(0);
            address.setUpdateTime(new Date());
            userAddressDao.updateById(address);
        }
        return userAddresses.size();
    }

}
