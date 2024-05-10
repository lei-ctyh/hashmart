package com.wangyameng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 商品规格表(GoodsRule)表实体类
 *
 * @author wangyameng
 * @since 2023-10-18 22:32:31
 */
@SuppressWarnings("serial")
public class GoodsRule extends Model<GoodsRule> {
    @TableId(type = IdType.AUTO)
    // 商品规格id
    private Integer id;
    // 商品id
    private Integer goodsId;
    // 自定义规格
    private String rule;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}

