package com.wangyameng.service.admin.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.GoodsCateDao;
import com.wangyameng.dao.GoodsDao;
import com.wangyameng.dao.GoodsRuleDao;
import com.wangyameng.dao.GoodsRuleExtendDao;
import com.wangyameng.dto.AddOrEditGoodReq;
import com.wangyameng.dto.FinalItem;
import com.wangyameng.dto.GoodsListReq;
import com.wangyameng.dto.ShelvesGoodReq;
import com.wangyameng.entity.Goods;
import com.wangyameng.entity.GoodsCate;
import com.wangyameng.entity.GoodsRule;
import com.wangyameng.entity.GoodsRuleExtend;
import com.wangyameng.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wangyameng
 * @version 1.0.0
 * @ClassName GoodsServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-05 22:58:00
 */
@Service("adminGoodsService")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsCateDao goodsCateDao;
    @Autowired
    private GoodsRuleDao goodsRuleDao;
    @Autowired
    private GoodsRuleExtendDao goodsRuleExtendDao;

    @Override
    public AjaxResult getGoodsList(GoodsListReq goodsListReq) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(goodsListReq.getCate_id()) && !StringUtils.equals("0", goodsListReq.getCate_id())) {
            queryWrapper.eq(Goods::getCateId, goodsListReq.getCate_id());
        }
        if (StringUtils.isNotBlank(goodsListReq.getStatus())) {
            queryWrapper.eq(Goods::getStatus, goodsListReq.getStatus());
        }
        if (StringUtils.isNotBlank(goodsListReq.getName())) {
            queryWrapper.like(Goods::getName, goodsListReq.getName());
        }
        if (StringUtils.isNotBlank(goodsListReq.getGoods_type())) {
            queryWrapper.eq(Goods::getGoodsType, goodsListReq.getGoods_type());
        }
        queryWrapper.orderByDesc(Goods::getId);

        IPage<Goods> iPage = goodsDao.selectPage(new Page<>(goodsListReq.getPage(), goodsListReq.getLimit()), queryWrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", iPage.getTotal());
        rtnJson.put("per_page", iPage.getSize());
        rtnJson.put("current_page", iPage.getCurrent());
        rtnJson.put("last_page", iPage.getPages());

        JSONArray rows = new JSONArray();
        List<Goods> goodsList = iPage.getRecords();
        for (Goods good : goodsList) {
            good.setImage(PubfuncUtil.replaceBecomeServerHost(good.getImage()));
            JSONObject row = JSONObject.parseObject(JSON.toJSONString(good));
            // cate
            LambdaQueryWrapper<GoodsCate> cateQueryWrapper = new LambdaQueryWrapper<>();
            cateQueryWrapper.eq(GoodsCate::getId, good.getCateId());
            GoodsCate goodsCate = goodsCateDao.selectOne(cateQueryWrapper);
            row.put("cate", PubfuncUtil.parseToUnderlineJson(goodsCate));
            // rule
            LambdaQueryWrapper<GoodsRule> ruleQueryWrapper = new LambdaQueryWrapper<>();
            ruleQueryWrapper.eq(GoodsRule::getGoodsId, good.getId());
            GoodsRule goodsRule = goodsRuleDao.selectOne(ruleQueryWrapper);
            row.put("rule", PubfuncUtil.parseToUnderlineJson(goodsRule));

            // ruleExtend
            LambdaQueryWrapper<GoodsRuleExtend> ruleExtendQueryWrapper = new LambdaQueryWrapper<>();
            ruleExtendQueryWrapper.eq(GoodsRuleExtend::getGoodsId, good.getId());
            List<GoodsRuleExtend> goodsRuleExtends = goodsRuleExtendDao.selectList(ruleExtendQueryWrapper);
            JSONArray ruleExtends = new JSONArray();
            for (GoodsRuleExtend goodsRuleExtend : goodsRuleExtends) {
                goodsRuleExtend.setImage(PubfuncUtil.replaceBecomeServerHost(goodsRuleExtend.getImage()));
                ruleExtends.add(PubfuncUtil.parseToUnderlineJson(goodsRuleExtend));
            }
            JSONObject rowJson = PubfuncUtil.parseToUnderlineJson(row);
            rowJson.put("ruleExtend", ruleExtends);
            rows.add(rowJson);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult addGood(AddOrEditGoodReq req) {
        if (Objects.equals(req.getType(), "2") && (req.getPreItem().isEmpty() || req.getFinalItem().isEmpty())) {
            return AjaxResult.dataReturn(-2, "请生成规格属性");
        }
        // 检测标题重复
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getName, req.getName());
        Integer count = goodsDao.selectCount(queryWrapper);
        if (count > 0) {
            return AjaxResult.dataReturn(-3, "商品标题重复");
        }

        Goods goods = new Goods();
        goods.setCateId(req.getCate_id());
        goods.setGoodsType(req.getGoods_type());
        goods.setType(req.getType());
        goods.setName(req.getName());
        goods.setSubTitle(req.getSub_title());
        goods.setImage(req.getImage());
        goods.setContent(req.getContent());
        goods.setStock(req.getStock());
        goods.setPrice(req.getPrice());
        goods.setRecoveryPrice(req.getRecovery_price());
        goods.setCostPrice(req.getCost_price());
        goods.setIntegralPrice(req.getIntegral_price());
        goods.setStatus(req.getStatus());
        goods.setSales(0);
        goods.setDeliveryFee(req.getDelivery_fee());
        goods.setSort(req.getSort());
        goods.setCreateTime(new Date());
        goodsDao.insert(goods);

        if (req.getType() == 2 && !req.getPreItem().isEmpty() && !req.getFinalItem().isEmpty()) {
            GoodsRule goodsRule = new GoodsRule();
            goodsRule.setGoodsId(goods.getId());
            goodsRule.setRule(JSONArray.from(req.getPreItem()).toString());
            goodsRuleDao.insert(goodsRule);

            GoodsRuleExtend firstFinalItem = null;


            for (FinalItem finalItem : req.getFinalItem()) {
                GoodsRuleExtend goodsRuleExtend = new GoodsRuleExtend();
                goodsRuleExtend.setGoodsId(goods.getId());
                goodsRuleExtend.setSku(JSONArray.from(finalItem.getSku()).toString());
                goodsRuleExtend.setStock(finalItem.getStock());
                goodsRuleExtend.setSales(0);
                goodsRuleExtend.setImage(finalItem.getImage());
                goodsRuleExtend.setUnique(UUID.randomUUID().toString().replace("-", ""));
                goodsRuleExtend.setPrice(finalItem.getPrice());
                goodsRuleExtend.setRecoveryPrice(finalItem.getRecovery_price());
                goodsRuleExtend.setCostPrice(finalItem.getCost_price());
                goodsRuleExtend.setIntegralPrice(finalItem.getIntegral_price());
                goodsRuleExtend.setCreateTime(new Date());
                goodsRuleExtendDao.insert(goodsRuleExtend);
                if (firstFinalItem == null) {
                    firstFinalItem = goodsRuleExtend;
                }
            }
            if (firstFinalItem != null) {
                goods.setRecoveryPrice(firstFinalItem.getRecoveryPrice());
                goods.setPrice(firstFinalItem.getPrice());
                goods.setIntegralPrice(firstFinalItem.getIntegralPrice());
                goodsDao.updateById(goods);
            }
        }

        return AjaxResult.dataReturn(0, "success");
    }

    @Override
    public AjaxResult editGood(AddOrEditGoodReq req) {

        if (Objects.equals(req.getType(), "2") && (req.getPreItem().isEmpty() || req.getFinalItem().isEmpty())) {
            return AjaxResult.dataReturn(-2, "请生成规格属性");
        }
        // 检测标题重复
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getName, req.getName());
        queryWrapper.ne(Goods::getId, req.getId());
        Integer count = goodsDao.selectCount(queryWrapper);
        if (count > 0) {
            return AjaxResult.dataReturn(-3, "商品标题重复");
        }
        Goods goods = goodsDao.selectById(req.getId());
        goods.setCateId(req.getCate_id());
        goods.setGoodsType(req.getGoods_type());
        goods.setType(req.getType());
        goods.setName(req.getName());
        goods.setSubTitle(req.getSub_title());
        goods.setImage(req.getImage());
        goods.setContent(req.getContent());
        goods.setStock(req.getStock());
        goods.setPrice(req.getPrice());
        goods.setRecoveryPrice(req.getRecovery_price());
        goods.setCostPrice(req.getCost_price());
        goods.setIntegralPrice(req.getIntegral_price());
        goods.setStatus(req.getStatus());
        goods.setSales(0);
        goods.setDeliveryFee(req.getDelivery_fee());
        goods.setSort(req.getSort());
        goods.setCreateTime(new Date());
        goodsDao.updateById(goods);
        if (req.getType() == 2 && !req.getPreItem().isEmpty() && !req.getFinalItem().isEmpty()) {
            // 删除原有规格属性
            goodsRuleDao.delete(new LambdaQueryWrapper<GoodsRule>().eq(GoodsRule::getGoodsId, goods.getId()));
            goodsRuleExtendDao.delete(new LambdaQueryWrapper<GoodsRuleExtend>().eq(GoodsRuleExtend::getGoodsId, goods.getId()));
            GoodsRule goodsRule = new GoodsRule();
            goodsRule.setGoodsId(goods.getId());
            goodsRule.setRule(JSONArray.from(req.getPreItem()).toString());
            goodsRuleDao.insert(goodsRule);

            GoodsRuleExtend firstFinalItem = null;

            for (FinalItem finalItem : req.getFinalItem()) {
                GoodsRuleExtend goodsRuleExtend = new GoodsRuleExtend();
                goodsRuleExtend.setGoodsId(goods.getId());
                goodsRuleExtend.setSku(JSONArray.from(finalItem.getSku()).toString());
                goodsRuleExtend.setStock(finalItem.getStock());
                goodsRuleExtend.setSales(0);
                goodsRuleExtend.setImage(finalItem.getImage());
                goodsRuleExtend.setUnique(UUID.randomUUID().toString().replace("-", ""));
                goodsRuleExtend.setPrice(finalItem.getPrice());
                goodsRuleExtend.setRecoveryPrice(finalItem.getRecovery_price());
                goodsRuleExtend.setCostPrice(finalItem.getCost_price());
                goodsRuleExtend.setIntegralPrice(finalItem.getIntegral_price());
                goodsRuleExtend.setCreateTime(new Date());
                goodsRuleExtendDao.insert(goodsRuleExtend);
                if (firstFinalItem == null) {
                    firstFinalItem = goodsRuleExtend;
                }
            }
            if (firstFinalItem != null) {
                goods.setRecoveryPrice(firstFinalItem.getRecoveryPrice());
                goods.setPrice(firstFinalItem.getPrice());
                goods.setIntegralPrice(firstFinalItem.getIntegralPrice());
                goodsDao.updateById(goods);
            }

        }

        return AjaxResult.dataReturn(0, "success");

    }

    @Override
    public AjaxResult delGood(Integer id) {
        Goods goods = goodsDao.selectById(id);
        goods.setDeleteFlag(2);
        return AjaxResult.dataReturn(0, "success");
    }

    @Override
    public AjaxResult shelvesGood(ShelvesGoodReq shelvesGoodReq) {
        for (Integer id : shelvesGoodReq.getIds()) {
            Goods goods = goodsDao.selectById(id);
            goods.setStatus(shelvesGoodReq.getType());
            goodsDao.updateById(goods);
        }
        return AjaxResult.dataReturn(0, "操作成功");
    }

}
