package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserBoxHotDao;
import com.wangyameng.entity.UserBoxHot;
import com.wangyameng.service.UserBoxHotService;
import org.springframework.stereotype.Service;

/**
 * 用户奖品盒子热点表(UserBoxHot)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userBoxHotService")
public class UserBoxHotServiceImpl extends ServiceImpl<UserBoxHotDao, UserBoxHot> implements UserBoxHotService {

}

