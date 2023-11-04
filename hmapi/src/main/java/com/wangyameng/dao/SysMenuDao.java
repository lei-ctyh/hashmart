package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysMenu;
import org.springframework.stereotype.Repository;

/**
 * 菜单和权限规则表(SysMenu)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Repository
public interface SysMenuDao extends BaseMapper<SysMenu> {

}

