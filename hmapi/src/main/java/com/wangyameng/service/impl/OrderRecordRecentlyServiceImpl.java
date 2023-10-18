package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.OrderRecordRecentlyDao;
import com.wangyameng.entity.OrderRecordRecently;
import com.wangyameng.service.OrderRecordRecentlyService;
import org.springframework.stereotype.Service;

/**
 * 用户最近N个中奖信息，用户保底等场景(OrderRecordRecently)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("orderRecordRecentlyService")
public class OrderRecordRecentlyServiceImpl extends ServiceImpl<OrderRecordRecentlyDao, OrderRecordRecently> implements OrderRecordRecentlyService {

}

