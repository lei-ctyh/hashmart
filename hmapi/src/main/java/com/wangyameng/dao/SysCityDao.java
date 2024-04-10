package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.SysCity;
import org.springframework.stereotype.Repository;

/**
 * 省市区表(SysCity)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface SysCityDao extends BaseMapper<SysCity> {

}

