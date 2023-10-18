package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.UserAgreeDao;
import com.wangyameng.entity.UserAgree;
import com.wangyameng.service.UserAgreeService;
import org.springframework.stereotype.Service;

/**
 * 用户协议表(UserAgree)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Service("userAgreeService")
public class UserAgreeServiceImpl extends ServiceImpl<UserAgreeDao, UserAgree> implements UserAgreeService {

}

