package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.ApplicationContextHelper;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.SysSettingDao;
import com.wangyameng.entity.SysSetting;
import com.wangyameng.service.admin.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName SystemServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-06 22:51:00
 */
@Service("adminSystemService")
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SysSettingDao sysSettingDao;
    @Override
    public AjaxResult getBase() {
        LambdaQueryWrapper<SysSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSetting::getType, "sys_base");
        List<SysSetting> sysSettings = sysSettingDao.selectList(queryWrapper);
        JSONObject rtnJSON = new JSONObject();
        for (SysSetting sysSetting : sysSettings) {
            String key = sysSetting.getKey();
            String value = sysSetting.getValue();
            rtnJSON.put(key, value);
        }
        return AjaxResult.dataReturn(0,"success", rtnJSON);
    }
}
