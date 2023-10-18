package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxLogDao;
import com.wangyameng.entity.UserBoxLog;
import com.wangyameng.service.UserBoxLogService;
import org.springframework.stereotype.Service;

/**
 * 用户奖品盒子历史表(UserBoxLog)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxLogService")
public class UserBoxLogServiceImpl extends ServiceImpl<UserBoxLogDao, UserBoxLog> implements UserBoxLogService {

}

