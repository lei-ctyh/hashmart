package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.leheyue.base.MPJBaseMapper;
import com.wangyameng.entity.UserAddress;
import org.springframework.stereotype.Repository;

/**
 * 用户收件地址表(UserAddress)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Repository
public interface UserAddressDao extends MPJBaseMapper<UserAddress> {

}

