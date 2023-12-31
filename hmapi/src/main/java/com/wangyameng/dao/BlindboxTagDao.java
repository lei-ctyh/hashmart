package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.BlindboxTag;
import org.springframework.stereotype.Repository;

/**
 * 盲盒商品标签(BlindboxTag)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface BlindboxTagDao extends BaseMapper<BlindboxTag> {

}

