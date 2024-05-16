package com.wangyameng.service.admin.impl;

import java.util.Date;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.common.util.text.StringUtils;
import com.wangyameng.dao.BlindboxDao;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.dao.BlindboxTagDao;
import com.wangyameng.dto.AddOrEditBlindboxDetailReq;
import com.wangyameng.entity.Blindbox;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.entity.BlindboxTag;
import com.wangyameng.service.admin.BlindboxDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxDetailServiceImpl.java
 * @Description TODO
 * @createTime 2024-05-14 22:26:00
 */
@Service
public class BlindboxDetailServiceImpl implements BlindboxDetailService {
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;
    @Autowired
    BlindboxTagDao blindboxTagDao;
    @Autowired
    BlindboxDao blindboxDao;


    @Override
    public AjaxResult getBlindboxDetailList(Integer blindboxId, Integer tagId, String goodsName, Integer page, Integer limit) {

        LambdaQueryWrapper<BlindboxDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxDetail::getBlindboxId, blindboxId);
        if (StringUtils.isNotBlank(goodsName)) {
            wrapper.like(BlindboxDetail::getGoodsName, goodsName);

        }
        if (tagId != null) {
            wrapper.eq(BlindboxDetail::getTagId, tagId);
        }

        wrapper.orderByAsc(BlindboxDetail::getProbability);
        Page<BlindboxDetail> ipage = blindboxDetailDao.selectPage(new Page<>(page, limit), wrapper);
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("total", ipage.getTotal());
        JSONArray rows = new JSONArray();


        for (BlindboxDetail blindboxDetail : ipage.getRecords()) {
            blindboxDetail.setGoodsImage(PubfuncUtil.replaceBecomeServerHost(blindboxDetail.getGoodsImage()));
            JSONObject row = PubfuncUtil.parseToUnderlineJson(blindboxDetail);

            LambdaQueryWrapper<BlindboxTag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.eq(BlindboxTag::getId, blindboxDetail.getTagId());
            BlindboxTag tag = blindboxTagDao.selectOne(tagWrapper);
            row.put("boxTag", PubfuncUtil.parseToUnderlineJson(tag));

            LambdaQueryWrapper<Blindbox> blindboxWrapper = new LambdaQueryWrapper<>();
            blindboxWrapper.eq(Blindbox::getId, blindboxDetail.getBlindboxId());
            Blindbox blindbox = blindboxDao.selectOne(blindboxWrapper);
            blindbox.setPic(PubfuncUtil.replaceBecomeServerHost(blindbox.getPic()));
            row.put("blindbox", PubfuncUtil.parseToUnderlineJson(blindbox));
            rows.add(row);
        }
        rtnJson.put("rows", rows);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult getLotteryProbability(Integer blindboxId, Integer detailId) {
        LambdaQueryWrapper<BlindboxDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxDetail::getBlindboxId, blindboxId);
        List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(wrapper);
        double sum = 0;
        for (BlindboxDetail blindboxDetail : blindboxDetails) {
            sum += blindboxDetail.getProbability();
        }

        if (null != detailId && detailId > 0) {
            BlindboxDetail detail = blindboxDetailDao.selectById(detailId);
            if (detail != null) {
                sum -= detail.getProbability();
            }
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("percent", String.format("%.4f", 100 - sum));
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult getLotteryNumRange(Integer blindboxId, Double percent, Integer detailId) {

        AjaxResult leftPercentResp = getLotteryProbability(blindboxId, detailId);
        Double leftPercent = 0D;
        if (0 == (Integer) (leftPercentResp.get(AjaxResult.CODE_TAG))) {
            JSONObject leftPercentData = PubfuncUtil.parseToUnderlineJson(leftPercentResp.get(AjaxResult.DATA_TAG));
            leftPercent = Double.parseDouble(leftPercentData.getString("percent"));
        }
        if (percent > leftPercent) {

            return AjaxResult.dataReturn(-1, "您最多可设置的百分比值是: " + leftPercent + "%");
        }

        // 若概率未变
        if (detailId != null && detailId > 0) {
            BlindboxDetail detail = blindboxDetailDao.selectById(detailId);
            if (detail != null && Objects.equals(detail.getProbability(), percent)) {
                JSONObject rtnJson = new JSONObject();
                rtnJson.put("lottery_min_no", detail.getLotteryMinNo());
                rtnJson.put("lottery_max_no", detail.getLotteryMaxNo());
                return AjaxResult.dataReturn(0, "success", rtnJson);
            }
        }
        List<BlindboxDetail> list = blindboxDetailDao.selectList(new LambdaQueryWrapper<BlindboxDetail>()
                .eq(BlindboxDetail::getBlindboxId, blindboxId)
                .le(BlindboxDetail::getProbability, percent)
                .orderByAsc(BlindboxDetail::getProbability));
        Integer dbMaxNo = (list.size() == 0) ? 0 : list.get(list.size() - 1).getLotteryMaxNo();
        Integer sysMaxNo = 1048575;
        Integer min = dbMaxNo == 0 ? 0 : dbMaxNo + 1;
        Integer max = min + (int) Math.ceil(sysMaxNo * (percent / 100));
        if (max > sysMaxNo) {
            max = sysMaxNo;
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("lottery_min_no", min);
        rtnJson.put("lottery_max_no", max);
        return AjaxResult.dataReturn(0, "success", rtnJson);
    }

    @Override
    public AjaxResult addBlindboxDetail(AddOrEditBlindboxDetailReq req) {

        LambdaQueryWrapper<BlindboxDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxDetail::getBlindboxId, req.getBlindbox_id());
        wrapper.eq(BlindboxDetail::getGoodsId, req.getGoods_id());
        List<BlindboxDetail> has = blindboxDetailDao.selectList(wrapper);
        if (!has.isEmpty()) {
            return AjaxResult.dataReturn(-2, "该商品已经存在");
        }
        if (req.getProbability() == null || req.getProbability() < 0 || req.getProbability() > 100) {
            return AjaxResult.dataReturn(-4, "请输入正确的概率");
        }
        // 最大概率设置
        LambdaQueryWrapper<BlindboxDetail> sumWrapper = new LambdaQueryWrapper<>();
        sumWrapper.eq(BlindboxDetail::getBlindboxId, req.getBlindbox_id());
        List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(sumWrapper);

        Double sum = 0D;
        for (BlindboxDetail blindboxDetail : blindboxDetails) {
            sum += blindboxDetail.getProbability();
        }
        if (sum == 100) {
            return AjaxResult.dataReturn(-6, "该盲盒的概率已达到100%无法新增了");
        }
        Double canSet = 100 - sum;
        if (req.getProbability() > canSet) {
            return AjaxResult.dataReturn(-5, "该盲盒剩下最大的概率只可设置为：" + canSet + "%");
        }

        BlindboxDetail blindboxDetail = new BlindboxDetail();
        blindboxDetail.setBlindboxId(req.getBlindbox_id());
        blindboxDetail.setTagId(req.getTag_id());
        blindboxDetail.setGoodsId(req.getGoods_id());
        blindboxDetail.setGoodsName(req.getGoods_name());
        blindboxDetail.setGoodsImage(req.getGoods_image());
        blindboxDetail.setPrice(req.getPrice());
        blindboxDetail.setRecoveryPrice(req.getRecovery_price());
        blindboxDetail.setStock(req.getStock());
        blindboxDetail.setLotteryMinNo(req.getLottery_min_no());
        blindboxDetail.setLotteryMaxNo(req.getLottery_max_no());
        blindboxDetail.setProbability(req.getProbability());
        blindboxDetail.setSort(0);
        blindboxDetail.setCreateTime(new Date());
        blindboxDetailDao.insert(blindboxDetail);
        return recalculateNoRange(blindboxDetail.getBlindboxId());
    }

    @Override
    public AjaxResult editBlindboxDetail(AddOrEditBlindboxDetailReq req) {
        // 判断商品是否存在
        LambdaQueryWrapper<BlindboxDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxDetail::getBlindboxId, req.getBlindbox_id());
        wrapper.eq(BlindboxDetail::getGoodsId, req.getGoods_id());
        BlindboxDetail blindboxInfo = blindboxDetailDao.selectOne(wrapper);

        if (blindboxInfo != null && blindboxInfo.getId()!=req.getId()) {
            return AjaxResult.dataReturn(-2, "该商品已经存在");
        }
        if (req.getProbability() == null || req.getProbability() < 0 || req.getProbability() > 100) {
            return AjaxResult.dataReturn(-4, "请输入正确的概率");
        }
        // 最大概率设置
        LambdaQueryWrapper<BlindboxDetail> sumWrapper = new LambdaQueryWrapper<>();
        sumWrapper.eq(BlindboxDetail::getBlindboxId, req.getBlindbox_id());
        List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(sumWrapper);

        Double sum = 0D;
        for (BlindboxDetail blindboxDetail : blindboxDetails) {
            sum += blindboxDetail.getProbability();
        }
        if (sum == 100) {
            return AjaxResult.dataReturn(-6, "该盲盒的概率已达到100%无法新增了");
        }
        Double canSet = 100 - sum;
        if (req.getProbability() > canSet) {
            return AjaxResult.dataReturn(-5, "该盲盒剩下最大的概率只可设置为：" + canSet + "%");
        }
        LambdaQueryWrapper<BlindboxDetail> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(BlindboxDetail::getId, req.getId());
        BlindboxDetail updateInfo = blindboxDetailDao.selectOne(updateWrapper);
        updateInfo.setTagId(req.getTag_id());
        updateInfo.setGoodsName(req.getGoods_name());
        updateInfo.setGoodsImage(req.getGoods_image());
        updateInfo.setPrice(req.getPrice());
        updateInfo.setRecoveryPrice(req.getRecovery_price());
        updateInfo.setStock(req.getStock());
        updateInfo.setLotteryMinNo(req.getLottery_min_no());
        updateInfo.setLotteryMaxNo(req.getLottery_max_no());
        updateInfo.setProbability(req.getProbability());
        updateInfo.setSort(0);
        updateInfo.setUpdateTime(new Date());
        blindboxDetailDao.updateById(updateInfo);
        return recalculateNoRange(blindboxInfo.getBlindboxId());
    }

    @Override
    public AjaxResult delBlindboxDetail(Integer id, Integer blindboxId) {
        BlindboxDetail blindboxDetail = blindboxDetailDao.selectById(id);
        if (blindboxDetail == null) {
            return AjaxResult.dataReturn(-1, "该商品不存在");
        }
        blindboxDetailDao.deleteById(id);
        return recalculateNoRange(blindboxDetail.getBlindboxId());
    }

    /**
     * 重新计算盲盒商品的区间
     *
     * @param blindboxId
     * @return
     */
    private AjaxResult recalculateNoRange(Integer blindboxId) {
        LambdaQueryWrapper<BlindboxDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlindboxDetail::getBlindboxId, blindboxId);
        wrapper.orderByAsc(BlindboxDetail::getProbability);
        List<BlindboxDetail> list = blindboxDetailDao.selectList(wrapper);
        Integer min = 0;
        Integer sysMaxNo = 1048575;
        for (BlindboxDetail blindboxDetail : list) {
            Integer max = min + (int) Math.ceil(sysMaxNo * (blindboxDetail.getProbability() / 100));
            if (max > sysMaxNo) {
                max = sysMaxNo;
            }
            blindboxDetail.setLotteryMinNo(min);
            blindboxDetail.setLotteryMaxNo(max);
            blindboxDetailDao.updateById(blindboxDetail);
            min = max + 1;
        }
        return AjaxResult.dataReturn(0, "操作成功");

    }
}
