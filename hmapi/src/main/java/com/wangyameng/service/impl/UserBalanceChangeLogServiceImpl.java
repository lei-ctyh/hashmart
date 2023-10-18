package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBalanceChangeLogDao;
import com.wangyameng.entity.UserBalanceChangeLog;
import com.wangyameng.service.UserBalanceChangeLogService;
import org.springframework.stereotype.Service;

/**
 * 用户余额变动记录表(UserBalanceChangeLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBalanceChangeLogService")
public class UserBalanceChangeLogServiceImpl extends ServiceImpl<UserBalanceChangeLogDao, UserBalanceChangeLog> implements UserBalanceChangeLogService {

}

