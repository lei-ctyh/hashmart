package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.GoodsDao;
import com.wangyameng.entity.Goods;
import com.wangyameng.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * 商品表(Goods)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {

}

