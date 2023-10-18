package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.BlindboxTagDao;
import com.wangyameng.entity.BlindboxTag;
import com.wangyameng.service.BlindboxTagService;
import org.springframework.stereotype.Service;

/**
 * 盲盒商品标签(BlindboxTag)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("blindboxTagService")
public class BlindboxTagServiceImpl extends ServiceImpl<BlindboxTagDao, BlindboxTag> implements BlindboxTagService {

}

