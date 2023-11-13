package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:32
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}

