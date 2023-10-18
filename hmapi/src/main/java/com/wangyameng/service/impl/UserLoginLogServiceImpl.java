package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserLoginLogDao;
import com.wangyameng.entity.UserLoginLog;
import com.wangyameng.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 用户登录日志表(UserLoginLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userLoginLogService")
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogDao, UserLoginLog> implements UserLoginLogService {

}

