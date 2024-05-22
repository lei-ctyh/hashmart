package com.wangyameng.service.admin.impl;

import cn.hutool.core.exceptions.ValidateException;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.dto.AddOrEditBindboxReq;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.service.admin.BindboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName BindboxServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-14 21:34:00
 */
@Service("adminBindboxService")
public class BindboxServiceImpl implements BindboxService {
    @Autowired
    private BlindboxDao blindboxDao;
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;

    @Override
    public AjaxResult getBlindboxList(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<Blindbox> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Blindbox::getName, name);
        }
        Page<Blindbox> ipage = blindboxDao.selectPage(new Page<>(page, limit), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (Blindbox blindbox : ipage.getRecords()) {
            blindbox.setPic(PubfuncUtil.replaceBecomeServerHost(blindbox.getPic()));
            JSONObject row = PubfuncUtil.parseToUnderlineJson(blindbox);
            // 获取goods_num
            LambdaQueryWrapper<BlindboxDetail> detailQueryWrapper = new LambdaQueryWrapper<>();
            detailQueryWrapper.eq(BlindboxDetail::getBlindboxId, blindbox.getId());
            int goodsNum = blindboxDetailDao.selectCount(detailQueryWrapper);
            row.put("goods_num", goodsNum);
            rows.add(row);

        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult addBlindbox(AddOrEditBindboxReq req) {
        if (req.getPrice() <= 0 || req.getPic() == null) {
            return AjaxResult.dataReturn(-1, "单抽价格必须大于等于0");
        }
        if (blindboxDao.selectCount(new LambdaQueryWrapper<Blindbox>()
                .eq(Blindbox::getName, req.getName())) > 0) {
            return AjaxResult.dataReturn(-2, "该盲盒已经存在");
        }
        Blindbox blindbox = new Blindbox();
        blindbox.setName(req.getName());
        blindbox.setPic(req.getPic());
        blindbox.setPrice(req.getPrice());
        blindbox.setDesc(req.getDesc());
        blindbox.setSort(req.getSort());
        blindbox.setHotTag(req.getHot_tag());
        blindbox.setRecommendTag(req.getRecommend_tag());
        blindbox.setCreateTime(new Date());
        blindboxDao.insert(blindbox);
        return AjaxResult.dataReturn(0, "添加成功", blindbox.getId());
    }

    @Override
    public AjaxResult editBlindbox(AddOrEditBindboxReq req) {
        if (req.getPrice() <= 0 || req.getPic() == null) {
            return AjaxResult.dataReturn(-1, "单抽价格必须大于等于0");
        }
        Blindbox blindbox = blindboxDao.selectById(req.getId());
        if (blindbox == null) {
            return AjaxResult.dataReturn(-2, "该盲盒不存在");
        }
        if (blindboxDao.selectCount(new LambdaQueryWrapper<Blindbox>()
                .eq(Blindbox::getName, req.getName())
                .ne(Blindbox::getId, req.getId())) > 0) {
            return AjaxResult.dataReturn(-2, "该盲盒已经存在");
        }
        blindbox.setName(req.getName());
        blindbox.setPic(req.getPic());
        blindbox.setPrice(req.getPrice());
        blindbox.setDesc(req.getDesc());
        blindbox.setSort(req.getSort());
        blindbox.setHotTag(req.getHot_tag());
        blindbox.setRecommendTag(req.getRecommend_tag());
        blindbox.setUpdateTime(new Date());
        blindboxDao.updateById(blindbox);
        return AjaxResult.dataReturn(0, "编辑成功", blindbox.getId());
    }

    @Override
    public AjaxResult delBlindbox(Integer id) {
        LambdaQueryWrapper<BlindboxDetail> detailQueryWrapper = new LambdaQueryWrapper<>();
        detailQueryWrapper.eq(BlindboxDetail::getBlindboxId, id);
        int goodsNum = blindboxDetailDao.selectCount(detailQueryWrapper);
        if (goodsNum > 0) {
            return AjaxResult.dataReturn(-1, "该盲盒下有商品不可删除");
        }
        blindboxDao.deleteById(id);
        return AjaxResult.dataReturn(0, "删除成功");
    }
}
