package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderRecordDetailDao;
import com.wangyameng.entity.OrderRecordDetail;
import com.wangyameng.service.OrderRecordDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单获取的奖品详情(OrderRecordDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderRecordDetailService")
public class OrderRecordDetailServiceImpl extends ServiceImpl<OrderRecordDetailDao, OrderRecordDetail> implements OrderRecordDetailService {

}

