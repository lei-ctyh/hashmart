package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.GoodsRuleDao;
import com.wangyameng.entity.GoodsRule;
import com.wangyameng.service.GoodsRuleService;
import org.springframework.stereotype.Service;

/**
 * 商品规格表(GoodsRule)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("goodsRuleService")
public class GoodsRuleServiceImpl extends ServiceImpl<GoodsRuleDao, GoodsRule> implements GoodsRuleService {

}

