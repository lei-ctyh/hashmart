package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysAdmin;
import org.springframework.stereotype.Repository;

/**
 * 管理员表(SysAdmin)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface SysAdminDao extends BaseMapper<SysAdmin> {

}

