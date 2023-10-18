package com.wangyameng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.service.BlindboxService;
import org.springframework.stereotype.Service;

/**
 * 盲盒箱子表(Blindbox)表服务实现类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@Service("blindboxService")
public class BlindboxServiceImpl extends ServiceImpl<BlindboxDao, Blindbox> implements BlindboxService {

}

