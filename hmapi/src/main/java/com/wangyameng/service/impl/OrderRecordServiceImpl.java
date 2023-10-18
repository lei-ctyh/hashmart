package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderRecordDao;
import com.wangyameng.entity.OrderRecord;
import com.wangyameng.service.OrderRecordService;
import org.springframework.stereotype.Service;

/**
 * 用户中奖表(OrderRecord)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderRecordService")
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordDao, OrderRecord> implements OrderRecordService {

}

