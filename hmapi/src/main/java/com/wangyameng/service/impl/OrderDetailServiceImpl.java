package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderDetailDao;
import com.wangyameng.entity.OrderDetail;
import com.wangyameng.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 哈希币商城订单详情(OrderDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderDetailService")
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {

}

