package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysAdminLogDao;
import com.wangyameng.entity.SysAdminLog;
import com.wangyameng.service.SysAdminLogService;
import org.springframework.stereotype.Service;

/**
 * 管理员日志表(SysAdminLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("sysAdminLogService")
public class SysAdminLogServiceImpl extends ServiceImpl<SysAdminLogDao, SysAdminLog> implements SysAdminLogService {

}

