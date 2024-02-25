package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.UserAgree;
import org.springframework.stereotype.Repository;

/**
 * 用户协议表(UserAgree)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:33
 */
@Repository
public interface UserAgreeDao extends BaseMapper<UserAgree> {

}

