package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxExchangeDetailDao;
import com.wangyameng.entity.UserBoxExchangeDetail;
import com.wangyameng.service.UserBoxExchangeDetailService;
import org.springframework.stereotype.Service;

/**
 * 用户盒子发货详情表(UserBoxExchangeDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxExchangeDetailService")
public class UserBoxExchangeDetailServiceImpl extends ServiceImpl<UserBoxExchangeDetailDao, UserBoxExchangeDetail> implements UserBoxExchangeDetailService {

}

