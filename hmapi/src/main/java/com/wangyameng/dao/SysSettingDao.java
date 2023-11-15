package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysSetting;
import org.springframework.stereotype.Repository;

/**
 * 配置表(SysSetting)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Repository
public interface SysSettingDao extends BaseMapper<SysSetting> {

}

