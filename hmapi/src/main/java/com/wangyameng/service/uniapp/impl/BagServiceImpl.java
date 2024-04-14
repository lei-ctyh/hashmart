package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.leheyue.wrapper.MPJLambdaWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.core.UserSessionContext;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.UserBoxLogDao;
import com.wangyameng.dto.BagGoodsDTO;
import com.wangyameng.entity.*;
import com.wangyameng.service.uniapp.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BagServiceImpl.java
 * @Description TODO
 * @createTime 2024-04-10 11:36:00
 */
@Service
public class BagServiceImpl implements BagService {
    @Autowired
    private UserBoxLogDao userBoxLogDao;

    @Override
    public AjaxResult getBagBoxList(int page, int limit, int status) {
        Integer id = UserSessionContext.get().getInteger("id");
        switch (status) {
            case 0:
                return getAllBagGoods(page, limit, status);
            case 1:
                return getAllBagGoods(page, limit, status);
            // return getBoxGoods(id, page, limit, status);

            case 2:
                return getBoxDeliver(id, page, limit, status);
            default:
                return AjaxResult.error("参数错误");
        }

    }

    private AjaxResult getAllBagGoods(int page, int limit, int status) {
        MPJLambdaWrapper<UserBoxLog> wrapper = new MPJLambdaWrapper<UserBoxLog>()
                .selectAll(UserBoxLog.class)
                .selectAll(OrderRecordDetail.class)
                .leftJoin(OrderRecordDetail.class, OrderRecordDetail::getOrderRecordId, UserBoxLog::getId);

        Page<BagGoodsDTO> iPage = userBoxLogDao.selectJoinPage(new Page<>(page, limit), BagGoodsDTO.class, wrapper);
        iPage.getRecords().forEach(bagGoodsDTO -> {
            bagGoodsDTO.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(bagGoodsDTO.getGoodsImage()));
        });

        JSONObject rtnData = new JSONObject();
        rtnData.put("total", iPage.getTotal());
        rtnData.put("per_page", iPage.getSize());
        rtnData.put("current_page", iPage.getCurrent());
        rtnData.put("last_page", iPage.getPages());
        rtnData.put("data", PubfuncUtil.paginate(iPage));
        return AjaxResult.dataReturn(0, "success", rtnData);
    }

    /**
     * 盒子中
     *
     * @param page
     * @param limit
     * @param status
     * @return
     */
    private AjaxResult getBoxGoods(Integer id, int page, int limit, int status) {
        return null;
    }

    /**
     * 盒子兑换
     *
     * @param id
     * @param page
     * @param limit
     * @param status
     * @return
     */
    private AjaxResult getBoxExchange(Integer id, int page, int limit, int status) {
        return null;
    }


    /**
     * 已提货
     */
    private AjaxResult getBoxDeliver(Integer id, int page, int limit, int status) {
        return null;
    }
}
