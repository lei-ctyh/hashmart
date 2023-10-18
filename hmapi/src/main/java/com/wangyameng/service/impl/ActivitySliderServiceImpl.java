package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.ActivitySliderDao;
import com.wangyameng.entity.ActivitySlider;
import com.wangyameng.service.ActivitySliderService;
import org.springframework.stereotype.Service;

/**
 * 导航轮播表(ActivitySlider)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("activitySliderService")
public class ActivitySliderServiceImpl extends ServiceImpl<ActivitySliderDao, ActivitySlider> implements ActivitySliderService {

}

