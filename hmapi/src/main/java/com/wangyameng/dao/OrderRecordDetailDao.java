package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.leheyue.base.MPJBaseMapper;
import com.wangyameng.entity.OrderRecordDetail;
import org.springframework.stereotype.Repository;

/**
 * 订单获取的奖品详情(OrderRecordDetail)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface OrderRecordDetailDao extends MPJBaseMapper<OrderRecordDetail> {

}

