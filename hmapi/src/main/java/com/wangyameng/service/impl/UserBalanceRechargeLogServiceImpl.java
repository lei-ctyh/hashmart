package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBalanceRechargeLogDao;
import com.wangyameng.entity.UserBalanceRechargeLog;
import com.wangyameng.service.UserBalanceRechargeLogService;
import org.springframework.stereotype.Service;

/**
 * 用户余额充值表(UserBalanceRechargeLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBalanceRechargeLogService")
public class UserBalanceRechargeLogServiceImpl extends ServiceImpl<UserBalanceRechargeLogDao, UserBalanceRechargeLog> implements UserBalanceRechargeLogService {

}

