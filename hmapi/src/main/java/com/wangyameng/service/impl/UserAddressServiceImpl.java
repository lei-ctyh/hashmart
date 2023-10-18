package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserAddressDao;
import com.wangyameng.entity.UserAddress;
import com.wangyameng.service.UserAddressService;
import org.springframework.stereotype.Service;

/**
 * 用户收件地址表(UserAddress)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddress> implements UserAddressService {

}

