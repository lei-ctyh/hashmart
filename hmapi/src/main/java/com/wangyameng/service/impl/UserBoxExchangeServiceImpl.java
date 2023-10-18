package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxExchangeDao;
import com.wangyameng.entity.UserBoxExchange;
import com.wangyameng.service.UserBoxExchangeService;
import org.springframework.stereotype.Service;

/**
 * 用户箱子兑换表(UserBoxExchange)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxExchangeService")
public class UserBoxExchangeServiceImpl extends ServiceImpl<UserBoxExchangeDao, UserBoxExchange> implements UserBoxExchangeService {

}

