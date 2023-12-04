package com.wangyameng.service.uniapp.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wangyameng.common.core.AjaxResult;
import com.wangyameng.common.util.pubfunc.PubfuncUtil;
import com.wangyameng.dao.BlindboxDetailDao;
import com.wangyameng.dao.BlindboxTagDao;
import com.wangyameng.entity.BlindboxDetail;
import com.wangyameng.entity.BlindboxTag;
import com.wangyameng.service.uniapp.BlindboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhanglei
 * @version 1.0.0
 * @ClassName BlindboxServiceImpl.java
 * @Description TODO
 * @createTime 2023-11-27 14:33:00
 */
@Service
public class BlindboxServiceImpl implements BlindboxService {
    @Autowired
    private BlindboxDetailDao blindboxDetailDao;
    @Autowired
    private BlindboxTagDao blindboxTagDao;

    @Override
    public AjaxResult getBlindboxDetail(String id) {
        LambdaQueryWrapper<BlindboxDetail> detailQueryWrapper = new LambdaQueryWrapper<>();
        detailQueryWrapper.orderByAsc(BlindboxDetail::getProbability).eq(BlindboxDetail::getBlindboxId, id);
        JSONArray list = new JSONArray();
        Map<String, JSONObject> probabilityMap = new HashMap<>(16);

        List<BlindboxTag> blindboxTags = blindboxTagDao.selectList(null);
        for (BlindboxTag blindboxTag : blindboxTags) {
            JSONObject tag = new JSONObject();
            tag.put("tag", blindboxTag.getName());
            tag.put("color", blindboxTag.getColor());
            tag.put("probability", new BigDecimal("0"));
            probabilityMap.put(String.valueOf(blindboxTag.getId()), tag);
        }

        List<BlindboxDetail> blindboxDetails = blindboxDetailDao.selectList(detailQueryWrapper);
        if (blindboxDetails == null) {
            return AjaxResult.dataReturn(-1, "该盲盒不存在");
        }
        for (BlindboxDetail blindboxDetail : blindboxDetails) {
            JSONObject detail = new JSONObject();
            detail.put("id", blindboxDetail.getId());
            detail.put("blindbox_id", blindboxDetail.getBlindboxId());
            detail.put("tag_id", blindboxDetail.getTagId());
            detail.put("goods_id", blindboxDetail.getGoodsId());
            detail.put("goods_name", blindboxDetail.getGoodsName());
            detail.put("goods_image", PubfuncUtil.replaceBecomeServerHost(blindboxDetail.getGoodsImage()));
            detail.put("price", blindboxDetail.getPrice());
            detail.put("recovery_price", blindboxDetail.getRecoveryPrice());
            detail.put("stock", blindboxDetail.getStock());
            detail.put("lottery_min_no", blindboxDetail.getLotteryMinNo());
            detail.put("lottery_max_no", blindboxDetail.getLotteryMaxNo());
            detail.put("probability", blindboxDetail.getProbability());
            detail.put("sort", blindboxDetail.getSort());
            detail.put("create_time", blindboxDetail.getCreateTime());
            detail.put("update_time", blindboxDetail.getUpdateTime());
            if (blindboxDetail.getTagId() != null) {
                LambdaQueryWrapper<BlindboxTag> tagQueryWrapper = new LambdaQueryWrapper<>();
                tagQueryWrapper.eq(BlindboxTag::getId, blindboxDetail.getTagId());
                BlindboxTag blindboxTag = blindboxTagDao.selectOne(tagQueryWrapper);
                if (blindboxTag != null) {
                    detail.put("boxTag", blindboxTag);
                }
                // 计算各个品质的概率
                JSONObject tag = probabilityMap.get(String.valueOf(blindboxDetail.getTagId()));
                tag.put("probability", tag.getBigDecimal("probability") .add(BigDecimal.valueOf(blindboxDetail.getProbability())) );
            }
            list.add(detail);
        }

        JSONObject rtnData = new JSONObject();
        // {"id":7,"blindbox_id":2,"tag_id":1,"goods_id":23,"goods_name":"Apple iPhone 14 Pro Max  暗紫色","goods_image":"http://leiaimeng.mynatapp.cc/storage/local/20230331/95788cee27fad8e659467c6be02838b9.png","price":"8999.00","recovery_price":"3280000.00","stock":-1,"lottery_min_no":0,"lottery_max_no":105,"probability":"0.0100","sort":1,"create_time":"2023-03-31 14:23:58","update_time":null,"boxTag":{"id":1,"name":"稀有款","status":1,"sort":1,"color":"#5DE4E2","create_time":"2023-03-31 11:31:45","update_time":"2023-11-16 21:57:51"}}
        // [{"tag":"稀有款","probability":0.05,"color":"#5DE4E2"},{"tag":"史诗款","probability":0.2,"color":"#E7EA51"},{"tag":"传说款","probability":0.5,"color":"#77E582"},{"tag":"普通款","probability":97.25,"color":"#F3F3F3"}]
        // 盲盒内商品列表
        rtnData.put("list", list);
        // 盲盒概率详情
        List<JSONObject> probability = probabilityMap.values().stream()
                                                     .filter(item -> item.getDouble("probability") > 0)
                                                     .collect(Collectors.toList());
        rtnData.put("probability", probability);
        return AjaxResult.dataReturn(0, "success", rtnData);
    }
}
