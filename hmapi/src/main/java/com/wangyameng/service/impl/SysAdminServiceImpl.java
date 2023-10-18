package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysAdminDao;
import com.wangyameng.entity.SysAdmin;
import com.wangyameng.service.SysAdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员表(SysAdmin)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("sysAdminService")
public class SysAdminServiceImpl extends ServiceImpl<SysAdminDao, SysAdmin> implements SysAdminService {

}

