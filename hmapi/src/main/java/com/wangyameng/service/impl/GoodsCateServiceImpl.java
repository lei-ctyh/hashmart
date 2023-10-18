package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.GoodsCateDao;
import com.wangyameng.entity.GoodsCate;
import com.wangyameng.service.GoodsCateService;
import org.springframework.stereotype.Service;

/**
 * 商品分类表(GoodsCate)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("goodsCateService")
public class GoodsCateServiceImpl extends ServiceImpl<GoodsCateDao, GoodsCate> implements GoodsCateService {

}

