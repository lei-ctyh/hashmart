package com.wangyameng.service.uniapp.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.dao.UserAgreeDao;
import com.wangyameng.entity.UserAgree;
import com.wangyameng.service.uniapp.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0
 * @description: TODO
 * @date 2024/2/25 23:24
 */
@Service("uniappCommonService")
public class CommonServiceImpl implements CommonService {
    @Autowired
    UserAgreeDao userAgreeDao;
    @Override
    public AjaxResult getUserAgreement(String type) {
        LambdaQueryWrapper<UserAgree> query = new LambdaQueryWrapper<>();
        query.eq(UserAgree::getType, type);
        UserAgree userAgree = userAgreeDao.selectOne(query);
        return AjaxResult.dataReturn(0,"success",userAgree);

    }
}
