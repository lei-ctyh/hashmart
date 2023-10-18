package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysSettingDao;
import com.wangyameng.entity.SysSetting;
import com.wangyameng.service.SysSettingService;
import org.springframework.stereotype.Service;

/**
 * 配置表(SysSetting)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Service("sysSettingService")
public class SysSettingServiceImpl extends ServiceImpl<SysSettingDao, SysSetting> implements SysSettingService {

}

