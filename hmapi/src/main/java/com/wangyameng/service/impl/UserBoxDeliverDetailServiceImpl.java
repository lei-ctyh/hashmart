package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxDeliverDetailDao;
import com.wangyameng.entity.UserBoxDeliverDetail;
import com.wangyameng.service.UserBoxDeliverDetailService;
import org.springframework.stereotype.Service;

/**
 * 用户盒子发货详情表(UserBoxDeliverDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxDeliverDetailService")
public class UserBoxDeliverDetailServiceImpl extends ServiceImpl<UserBoxDeliverDetailDao, UserBoxDeliverDetail> implements UserBoxDeliverDetailService {

}

