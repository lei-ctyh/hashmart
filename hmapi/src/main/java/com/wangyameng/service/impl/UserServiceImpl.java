package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserDao;
import com.wangyameng.entity.User;
import com.wangyameng.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

