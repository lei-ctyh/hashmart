package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderDao;
import com.wangyameng.entity.Order;
import com.wangyameng.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单主表(Order)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

}

