package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserIntegralChangeLogDao;
import com.wangyameng.entity.UserIntegralChangeLog;
import com.wangyameng.service.UserIntegralChangeLogService;
import org.springframework.stereotype.Service;

/**
 * 用户哈希币变动记录(UserIntegralChangeLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userIntegralChangeLogService")
public class UserIntegralChangeLogServiceImpl extends ServiceImpl<UserIntegralChangeLogDao, UserIntegralChangeLog> implements UserIntegralChangeLogService {

}

