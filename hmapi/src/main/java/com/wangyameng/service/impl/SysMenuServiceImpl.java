package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.SysMenuDao;
import com.wangyameng.entity.SysMenu;
import com.wangyameng.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单和权限规则表(SysMenu)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

}

