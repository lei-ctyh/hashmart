package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.leheyue.base.MPJBaseMapper;
import com.wangyameng.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * 订单主表(Order)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Repository
public interface OrderDao extends MPJBaseMapper<Order> {

}

