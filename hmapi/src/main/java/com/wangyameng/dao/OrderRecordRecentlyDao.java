package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.OrderRecordRecently;

/**
 * 用户最近N个中奖信息，用户保底等场景(OrderRecordRecently)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
public interface OrderRecordRecentlyDao extends BaseMapper<OrderRecordRecently> {

}

