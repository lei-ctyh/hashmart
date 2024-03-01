package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.leheyue.base.MPJBaseMapper;
import com.wangyameng.entity.OrderRecord;
import org.springframework.stereotype.Repository;

/**
 * 用户中奖表(OrderRecord)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface OrderRecordDao extends MPJBaseMapper<OrderRecord> {

}

