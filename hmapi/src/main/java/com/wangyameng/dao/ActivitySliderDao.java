package com.wangyameng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangyameng.entity.ActivitySlider;
import org.springframework.stereotype.Repository;

/**
 * 导航轮播表(ActivitySlider)表数据库访问层
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:30
 */
@Repository
public interface ActivitySliderDao extends BaseMapper<ActivitySlider> {

}

