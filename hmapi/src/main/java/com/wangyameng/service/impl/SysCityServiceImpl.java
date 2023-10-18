package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysCityDao;
import com.wangyameng.entity.SysCity;
import com.wangyameng.service.SysCityService;
import org.springframework.stereotype.Service;

/**
 * 省市区表(SysCity)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("sysCityService")
public class SysCityServiceImpl extends ServiceImpl<SysCityDao, SysCity> implements SysCityService {

}

