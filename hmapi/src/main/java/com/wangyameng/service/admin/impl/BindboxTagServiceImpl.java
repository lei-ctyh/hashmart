package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.dao.BlindboxTagDao;
import com.wangyameng.dto.AddOrEditBlindboxTagReq;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.entity.BlindboxTag;
import com.wangyameng.service.admin.BindboxTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName BindboxTagServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-14 00:28:00
 */
@Service
public class BindboxTagServiceImpl implements BindboxTagService {
    @Autowired
    BlindboxTagDao blindboxTagDao;
    @Autowired
    BlindboxDetailDao blindboxDetailDao;

    @Override
    public AjaxResult getBlindboxTagList(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<BlindboxTag> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(BlindboxTag::getName, name);
        }
        wrapper.orderByDesc(BlindboxTag::getSort);
        Page<BlindboxTag> ipage = blindboxTagDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();
        for (BlindboxTag blindboxTag : ipage.getRecords()) {
            JSONObject row;
            row = PubfuncUtil.parseToUnderlineJson(blindboxTag);
            rows.add(row);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult addBlindboxTag(AddOrEditBlindboxTagReq req) {
        if (StringUtils.isBlank(req.getName())) {
            return AjaxResult.dataReturn(-1, "标签名称不能为空");
        }
        LambdaQueryWrapper<BlindboxTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxTag::getName, req.getName());
        int count = blindboxTagDao.selectCount(wrapper);
        if (count > 0) {
            return AjaxResult.dataReturn(-2, "标签名称已存在");
        }
        BlindboxTag blindboxTag = new BlindboxTag();
        blindboxTag.setName(req.getName());
        blindboxTag.setSort(req.getSort());
        blindboxTag.setColor(req.getColor());
        blindboxTag.setStatus(req.getStatus());
        blindboxTag.setCreateTime(new Date());
        blindboxTagDao.insert(blindboxTag);
        return AjaxResult.dataReturn(0, "操作成功");
    }

    @Override
    public AjaxResult editBlindboxTag(AddOrEditBlindboxTagReq req) {
        if (StringUtils.isBlank(req.getName())) {
            return AjaxResult.dataReturn(-1, "标签名称不能为空");
        }
        LambdaQueryWrapper<BlindboxTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxTag::getName, req.getName());
        wrapper.ne(BlindboxTag::getId, req.getId());
        int count = blindboxTagDao.selectCount(wrapper);
        if (count > 0) {
            return AjaxResult.dataReturn(-2, "标签名称已存在");
        }
        BlindboxTag blindboxTag = new BlindboxTag();
        blindboxTag.setId(req.getId());
        blindboxTag.setName(req.getName());
        blindboxTag.setSort(req.getSort());
        blindboxTag.setColor(req.getColor());
        blindboxTag.setStatus(req.getStatus());
        blindboxTag.setUpdateTime(new Date());
        blindboxTagDao.updateById(blindboxTag);
        return AjaxResult.dataReturn(0, "操作成功");
    }

    @Override
    public AjaxResult deleteBlindboxTag(Integer id) {
        LambdaQueryWrapper<BlindboxDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(BlindboxDetail::getTagId, id);
        Integer count = blindboxDetailDao.selectCount(detailWrapper);
        if (count > 0) {
            return AjaxResult.dataReturn(-1, "该标签正被使用，无法删除");
        }
        blindboxTagDao.deleteById(id);
        return AjaxResult.dataReturn(0, "删除成功");
    }
}
