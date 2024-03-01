package com.wangyameng.dto;

import java.util.Date;

/**
 * @author zhanglei
 * @version 1.0
 * @description: TODO
 * @date 2024/2/29 20:52
 */
public class OrderRecordDTO {
    // ID
    private Integer id;
    // 关联的订单id
    private Integer orderId;
    // 所属盲盒的id
    private Integer blindboxId;
    // 用户id
    private Integer userId;
    // 用户名
    private String username;
    // 奖品数量
    private Integer awardNum;
    // 奖品总金额
    private Double totalAmount;
    // 奖品总可兑换哈希币
    private Double totalExchangeIntegral;
    // 带负号的总盈亏
    private Double totalProfit;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    private String goodsImage;
    // 获得商品名
    private String goodsName;
    // 商品的价值
    private Double goodsPrice;
    // 用户当前的hash_key
    private String hashKey;
    // 当前毫秒级时间戳
    private String orderTime;
    // hash算法算出的用户hash值
    private Integer hashNo;
    // 抽中范围
    private String range;

    // 盲盒名称
    private String name;
    // 盲盒图片
    private String pic;
}
