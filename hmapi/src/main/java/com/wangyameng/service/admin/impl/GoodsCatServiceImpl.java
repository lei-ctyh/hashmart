package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.GoodsCateDao;
import com.wangyameng.dto.AddOrEditGoodCateReq;
import com.wangyameng.entity.GoodsCate;
import com.wangyameng.service.admin.GoodsCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName GoodsCatServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-05 23:39:00
 */

@Service("adminGoodsCatService")
public class GoodsCatServiceImpl implements GoodsCatService {
    @Autowired
    private GoodsCateDao goodsCateDao;

    @Override
    public AjaxResult getCateList() {
        List<GoodsCate> goodsCates = goodsCateDao.selectList(null);
        JSONArray rtnDataArr = new JSONArray();
        for (GoodsCate goodsCate : goodsCates) {
            goodsCate.setIcon(PubfuncUtil.replaceBecomeServerHost(goodsCate.getIcon()));
            JSONObject json = JSONObject.from(goodsCate);
            rtnDataArr.add(PubfuncUtil.parseToUnderlineJson(json));
        }

        return AjaxResult.dataReturn(0, "success", PubfuncUtil.makeTree(rtnDataArr));
    }

    @Override
    public AjaxResult addCate(AddOrEditGoodCateReq req) {
        // 判断是否存在同名分类
        LambdaQueryWrapper<GoodsCate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsCate::getName, req.getName());
        List<GoodsCate> goodsCates = goodsCateDao.selectList(wrapper);
        if (!goodsCates.isEmpty()) {
            return AjaxResult.error(-2, "分类名称已存在");
        }

        GoodsCate goodsCate = new GoodsCate();
        goodsCate.setName(req.getName());
        goodsCate.setPid(req.getPid());
        goodsCate.setIcon(req.getIcon());
        goodsCate.setSort(req.getSort());
        goodsCate.setCreateTime(new Date());
        goodsCateDao.insert(goodsCate);
        return AjaxResult.dataReturn(0, "操作成功");
    }

    @Override
    public AjaxResult delCate(Integer id) {
        List<GoodsCate> goodsCates = goodsCateDao.selectList(new LambdaQueryWrapper<GoodsCate>().eq(GoodsCate::getPid, id));
        if (!goodsCates.isEmpty()) {
            return AjaxResult.error(-1, "该分类下有子分类不可删除");
        }
        goodsCateDao.deleteById(id);
        return AjaxResult.dataReturn(0, "操作成功");
    }

    @Override
    public AjaxResult editCate(AddOrEditGoodCateReq req) {
        // 判断是否存在同名分类
        LambdaQueryWrapper<GoodsCate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsCate::getName, req.getName());
        wrapper.ne(GoodsCate::getId, req.getId());
        int count = goodsCateDao.selectCount(wrapper);
        if (count > 0) {
            return AjaxResult.error(-2, "分类名称已存在");
        }
        GoodsCate goodsCate = goodsCateDao.selectById(req.getId());
        goodsCate.setName(req.getName());
        goodsCate.setPid(req.getPid());
        goodsCate.setIcon(req.getIcon());
        goodsCate.setSort(req.getSort());
        goodsCate.setUpdateTime(new Date());
        goodsCateDao.updateById(goodsCate);
        return AjaxResult.dataReturn(0, "操作成功");
    }


}
