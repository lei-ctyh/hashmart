package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysExpressDao;
import com.wangyameng.entity.SysExpress;
import com.wangyameng.service.SysExpressService;
import org.springframework.stereotype.Service;

/**
 * 物流公司表(SysExpress)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("sysExpressService")
public class SysExpressServiceImpl extends ServiceImpl<SysExpressDao, SysExpress> implements SysExpressService {

}

