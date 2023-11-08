package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysAdminLog;
import org.springframework.stereotype.Repository;

/**
 * 管理员日志表(SysAdminLog)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface SysAdminLogDao extends BaseMapper<SysAdminLog> {

}

