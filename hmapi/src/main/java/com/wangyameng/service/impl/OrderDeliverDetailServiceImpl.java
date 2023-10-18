package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderDeliverDetailDao;
import com.wangyameng.entity.OrderDeliverDetail;
import com.wangyameng.service.OrderDeliverDetailService;
import org.springframework.stereotype.Service;

/**
 * 哈希币商品发货物流明细表(OrderDeliverDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderDeliverDetailService")
public class OrderDeliverDetailServiceImpl extends ServiceImpl<OrderDeliverDetailDao, OrderDeliverDetail> implements OrderDeliverDetailService {

}

