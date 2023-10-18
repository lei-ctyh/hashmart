package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysRoleDao;
import com.wangyameng.entity.SysRole;
import com.wangyameng.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

}

