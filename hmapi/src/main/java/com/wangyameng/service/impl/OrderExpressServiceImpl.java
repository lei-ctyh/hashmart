package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderExpressDao;
import com.wangyameng.entity.OrderExpress;
import com.wangyameng.service.OrderExpressService;
import org.springframework.stereotype.Service;

/**
 * 运费订单(OrderExpress)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderExpressService")
public class OrderExpressServiceImpl extends ServiceImpl<OrderExpressDao, OrderExpress> implements OrderExpressService {

}

