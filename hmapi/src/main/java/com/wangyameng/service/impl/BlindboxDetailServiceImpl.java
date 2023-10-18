package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.service.BlindboxDetailService;
import org.springframework.stereotype.Service;

/**
 * 盲盒商品详情(BlindboxDetail)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("blindboxDetailService")
public class BlindboxDetailServiceImpl extends ServiceImpl<BlindboxDetailDao, BlindboxDetail> implements BlindboxDetailService {

}

