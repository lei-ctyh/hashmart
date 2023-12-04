package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.GoodsCate;
import org.springframework.stereotype.Repository;

/**
 * 商品分类表(GoodsCate)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface GoodsCateDao extends BaseMapper<GoodsCate> {

}

