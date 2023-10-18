package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxDeliverDao;
import com.wangyameng.entity.UserBoxDeliver;
import com.wangyameng.service.UserBoxDeliverService;
import org.springframework.stereotype.Service;

/**
 * 订单物流表(UserBoxDeliver)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxDeliverService")
public class UserBoxDeliverServiceImpl extends ServiceImpl<UserBoxDeliverDao, UserBoxDeliver> implements UserBoxDeliverService {

}

