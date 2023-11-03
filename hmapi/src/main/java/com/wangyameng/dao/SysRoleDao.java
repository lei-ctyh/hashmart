package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysRole;
import org.springframework.stereotype.Repository;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Repository
public interface SysRoleDao extends BaseMapper<SysRole> {

}

