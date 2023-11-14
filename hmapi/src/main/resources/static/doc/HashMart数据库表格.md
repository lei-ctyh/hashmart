# 数据库文档

**数据库名：** hashmart

**文档版本：** 0.0.1-SNAPSHOT

**文档描述：** 数据库文档生成

| 表名                  | 说明       |
| :---: | :---: |
| [hashmart_activity_slider](#hashmart_activity_slider) | 导航轮播表 |
| [hashmart_blindbox](#hashmart_blindbox) | 盲盒箱子表 |
| [hashmart_blindbox_detail](#hashmart_blindbox_detail) | 盲盒商品详情 |
| [hashmart_blindbox_tag](#hashmart_blindbox_tag) | 盲盒商品标签 |
| [hashmart_goods](#hashmart_goods) | 商品表 |
| [hashmart_goods_cate](#hashmart_goods_cate) | 商品分类表 |
| [hashmart_goods_rule](#hashmart_goods_rule) | 商品规格表 |
| [hashmart_goods_rule_extend](#hashmart_goods_rule_extend) | 商品规格值扩展表 |
| [hashmart_order](#hashmart_order) | 订单主表 |
| [hashmart_order_deliver_detail](#hashmart_order_deliver_detail) | 哈希币商品发货物流明细表 |
| [hashmart_order_detail](#hashmart_order_detail) | 哈希币商城订单详情 |
| [hashmart_order_express](#hashmart_order_express) | 运费订单 |
| [hashmart_order_record](#hashmart_order_record) | 用户中奖表 |
| [hashmart_order_record_detail](#hashmart_order_record_detail) | 订单获取的奖品详情 |
| [hashmart_order_record_recently](#hashmart_order_record_recently) | 用户最近N个中奖信息，用户保底等场景 |
| [hashmart_sys_admin](#hashmart_sys_admin) | 管理员表 |
| [hashmart_sys_admin_log](#hashmart_sys_admin_log) | 管理员日志表 |
| [hashmart_sys_attachment](#hashmart_sys_attachment) | 附件表 |
| [hashmart_sys_attachment_cate](#hashmart_sys_attachment_cate) | 资源图片分类 |
| [hashmart_sys_city](#hashmart_sys_city) | 省市区表 |
| [hashmart_sys_express](#hashmart_sys_express) | 物流公司表 |
| [hashmart_sys_menu](#hashmart_sys_menu) | 菜单和权限规则表 |
| [hashmart_sys_role](#hashmart_sys_role) | 角色表 |
| [hashmart_sys_setting](#hashmart_sys_setting) | 配置表 |
| [hashmart_user](#hashmart_user) | 用户表 |
| [hashmart_user_address](#hashmart_user_address) | 用户收件地址表 |
| [hashmart_user_agree](#hashmart_user_agree) | 用户协议表 |
| [hashmart_user_balance_change_log](#hashmart_user_balance_change_log) | 用户余额变动记录表 |
| [hashmart_user_balance_recharge_log](#hashmart_user_balance_recharge_log) | 用户余额充值表 |
| [hashmart_user_box_deliver](#hashmart_user_box_deliver) | 订单物流表 |
| [hashmart_user_box_deliver_detail](#hashmart_user_box_deliver_detail) | 用户盒子发货详情表 |
| [hashmart_user_box_exchange](#hashmart_user_box_exchange) | 用户箱子兑换表 |
| [hashmart_user_box_exchange_detail](#hashmart_user_box_exchange_detail) | 用户盒子发货详情表 |
| [hashmart_user_box_hot](#hashmart_user_box_hot) | 用户奖品盒子热点表 |
| [hashmart_user_box_log](#hashmart_user_box_log) | 用户奖品盒子历史表 |
| [hashmart_user_integral_change_log](#hashmart_user_integral_change_log) | 用户哈希币变动记录 |
| [hashmart_user_login_log](#hashmart_user_login_log) | 用户登录日志表 |

**表名：** <a id="hashmart_activity_slider">hashmart_activity_slider</a>

**说明：** 导航轮播表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 轮播id  |
|  2   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1:首页2:哈希币商城  |
|  3   | title |   varchar   | 155 |   0    |    Y     |  N   |       | 轮播描述  |
|  4   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的盲盒的id  |
|  5   | blindbox_name |   varchar   | 155 |   0    |    Y     |  N   |       | 关联的盲盒的名字  |
|  6   | goods_id |   int   | 10 |   0    |    Y     |  N   |       | 关联的商品id  |
|  7   | goods_name |   varchar   | 55 |   0    |    Y     |  N   |       | 商品名  |
|  8   | goods_type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 商品类型1:普通商品2:盲盒商品3:积分4:优惠券商品5:余额商品  |
|  9   | pic |   varchar   | 255 |   0    |    Y     |  N   |       | 导航图片  |
|  10   | status |   tinyint   | 4 |   0    |    Y     |  N   |       | 状态1:启用2:禁用  |
|  11   | other |   varchar   | 255 |   0    |    Y     |  N   |       | 补充的额外参数  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  13   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_blindbox">hashmart_blindbox</a>

**说明：** 盲盒箱子表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 盲盒id  |
|  2   | play_id |   int   | 10 |   0    |    Y     |  N   |   1    | 玩法1:hash玩法  |
|  3   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 盲盒名称  |
|  4   | pic |   varchar   | 255 |   0    |    Y     |  N   |       | 盲盒图片  |
|  5   | price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 单抽价格  |
|  6   | desc |   varchar   | 155 |   0    |    Y     |  N   |       | 盲盒描述  |
|  7   | sales |   int   | 10 |   0    |    Y     |  N   |   0    | 累计销量  |
|  8   | sort |   int   | 10 |   0    |    Y     |  N   |   1    | 排序值越大越靠前  |
|  9   | hot_tag |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 是否热门1:是2:否  |
|  10   | recommend_tag |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 是否推荐1:是2:否  |
|  11   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  12   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_blindbox_detail">hashmart_blindbox_detail</a>

**说明：** 盲盒商品详情

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属盲盒的id  |
|  3   | tag_id |   int   | 10 |   0    |    Y     |  N   |   0    | 商品标签  |
|  4   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的商品的id  |
|  5   | goods_name |   varchar   | 155 |   0    |    Y     |  N   |       | 关联的商品名称  |
|  6   | goods_image |   varchar   | 2000 |   0    |    Y     |  N   |       | 关联的商品图片  |
|  7   | price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 商品售价  |
|  8   | recovery_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 兑换的价格  |
|  9   | stock |   int   | 10 |   0    |    Y     |  N   |   -1    | 库存  |
|  10   | lottery_min_no |   int   | 10 |   0    |    Y     |  N   |   0    | 最小中奖数字  |
|  11   | lottery_max_no |   int   | 10 |   0    |    Y     |  N   |   0    | 最大中间数字  |
|  12   | probability |   decimal   | 8 |   4    |    Y     |  N   |   0.0000    | 中奖概率  |
|  13   | sort |   int   | 10 |   0    |    Y     |  N   |   1    | 排序值  |
|  14   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  15   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_blindbox_tag">hashmart_blindbox_tag</a>

**说明：** 盲盒商品标签

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 分类id  |
|  2   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 分类名称  |
|  3   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1正常2隐藏  |
|  4   | sort |   int   | 10 |   0    |    Y     |  N   |   1    | 排序值  |
|  5   | color |   varchar   | 15 |   0    |    Y     |  N   |       | 色号  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  7   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_goods">hashmart_goods</a>

**说明：** 商品表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | cate_id |   int   | 10 |   0    |    Y     |  N   |       | 所属商品分类  |
|  3   | goods_type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1:普通商品2:盲盒商品3:哈希币4:优惠券商品5:余额商品  |
|  4   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1单规格2多规格  |
|  5   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 商品名称  |
|  6   | sub_title |   varchar   | 255 |   0    |    Y     |  N   |       | 商品副标题  |
|  7   | image |   varchar   | 2000 |   0    |    Y     |  N   |       | 商品图片  |
|  8   | content |   text   | 65535 |   0    |    Y     |  N   |       | 商品详情图片,多个用,分割  |
|  9   | stock |   int   | 10 |   0    |    Y     |  N   |   -1    | 商品库存  |
|  10   | price |   decimal   | 11 |   2    |    Y     |  N   |       | 展示价格  |
|  11   | recovery_price |   decimal   | 11 |   2    |    Y     |  N   |       | 兑换价格  |
|  12   | cost_price |   decimal   | 10 |   2    |    Y     |  N   |   0.00    | 成本价  |
|  13   | integral_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 哈希币价格  |
|  14   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态:1:正常,2:下架  |
|  15   | sales |   int   | 10 |   0    |    Y     |  N   |   1    | 销量  |
|  16   | delivery_fee |   decimal   | 10 |   2    |    Y     |  N   |   0.00    | 运费  |
|  17   | sort |   int   | 10 |   0    |    Y     |  N   |   1    | 排序，值越大越靠谱  |
|  18   | delete_flag |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1正常2删除  |
|  19   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  20   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  21   | delete_time |   datetime   | 19 |   0    |    Y     |  N   |       | 删除时间  |

**表名：** <a id="hashmart_goods_cate">hashmart_goods_cate</a>

**说明：** 商品分类表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 分类id  |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |   0    | 上级分类id  |
|  3   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 分类名称  |
|  4   | icon |   varchar   | 255 |   0    |    Y     |  N   |       | 分类图标  |
|  5   | sort |   int   | 10 |   0    |    Y     |  N   |   1    | 排序，值越大越靠前  |
|  6   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1:正常2:禁用  |
|  7   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  8   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_goods_rule">hashmart_goods_rule</a>

**说明：** 商品规格表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 商品规格id  |
|  2   | goods_id |   int   | 10 |   0    |    Y     |  N   |       | 商品id  |
|  3   | rule |   text   | 65535 |   0    |    Y     |  N   |       | 自定义规格  |

**表名：** <a id="hashmart_goods_rule_extend">hashmart_goods_rule_extend</a>

**说明：** 商品规格值扩展表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键id  |
|  2   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 商品ID  |
|  3   | sku |   varchar   | 191 |   0    |    Y     |  N   |   0    | 属性索引  |
|  4   | stock |   int   | 10 |   0    |    Y     |  N   |   0    | 属性对应的库存  |
|  5   | sales |   int   | 10 |   0    |    Y     |  N   |   0    | 销量  |
|  6   | image |   varchar   | 128 |   0    |    Y     |  N   |       | 图片  |
|  7   | unique |   varchar   | 32 |   0    |    Y     |  N   |       | 唯一值  |
|  8   | price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 展示价格  |
|  9   | recovery_price |   decimal   | 10 |   2    |    Y     |  N   |   0.00    | 兑换金额  |
|  10   | cost_price |   decimal   | 10 |   2    |    Y     |  N   |   0.00    | 成本价  |
|  11   | integral_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 哈希币价格  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |

**表名：** <a id="hashmart_order">hashmart_order</a>

**说明：** 订单主表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 订单id  |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |   0    | 父订单id  |
|  3   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 订单类型1:普通订单2:盲盒订单  |
|  4   | order_no |   varchar   | 32 |   0    |    Y     |  N   |       | 订单号  |
|  5   | pay_order_no |   varchar   | 32 |   0    |    Y     |  N   |       | 支付订单号  |
|  6   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 下单用户  |
|  7   | user_name |   varchar   | 155 |   0    |    Y     |  N   |       | 下单人名  |
|  8   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 购买盲盒的id  |
|  9   | box_id |   int   | 10 |   0    |    Y     |  N   |   1    | 购买的盲盒的箱子id（预留）  |
|  10   | play_id |   int   | 10 |   0    |    Y     |  N   |   1    | 盲盒玩法id  |
|  11   | total_num |   int   | 10 |   0    |    Y     |  N   |   0    | 订单商品数量  |
|  12   | unit_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 单抽价格  |
|  13   | postage |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 邮费  |
|  14   | order_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 订单总金额  |
|  15   | pay_way |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付方式1:微信2:支付宝3:哈希币4:余额  |
|  16   | pay_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 实际支付金额  |
|  17   | pay_integral |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 实际支付哈希币  |
|  18   | pay_postage |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 实际支付邮费  |
|  19   | coupon_amount |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 优惠券累计抵扣金额  |
|  20   | pay_status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付状态1:待支付2:已支付3:已退款4:部分退款5:部分支付6:支付异常7:支付超时  |
|  21   | pay_time |   datetime   | 19 |   0    |    Y     |  N   |       | 支付时间  |
|  22   | delivery_time |   datetime   | 19 |   0    |    Y     |  N   |       | 发货时间  |
|  23   | address_id |   int   | 10 |   0    |    Y     |  N   |   0    | 发货地址id  |
|  24   | address_info |   text   | 65535 |   0    |    Y     |  N   |       | 发货地址详情  |
|  25   | express_name |   varchar   | 155 |   0    |    Y     |  N   |       | 发货物流名  |
|  26   | express_code |   varchar   | 155 |   0    |    Y     |  N   |       | 物流编号  |
|  27   | express_no |   varchar   | 155 |   0    |    Y     |  N   |       | 发货订单号  |
|  28   | cancel_time |   datetime   | 19 |   0    |    Y     |  N   |       | 取消时间  |
|  29   | received_time |   datetime   | 19 |   0    |    Y     |  N   |       | 收货时间  |
|  30   | close_time |   datetime   | 19 |   0    |    Y     |  N   |       | 关闭时间  |
|  31   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 订单状态1:待支付2:待发货3:待收货4:部分发货5:已完成6:已取消7:已关闭8:库存不足  |
|  32   | return_msg |   varchar   | 500 |   0    |    Y     |  N   |       | 第三方返回信息  |
|  33   | third_code |   varchar   | 64 |   0    |    Y     |  N   |       | 第三方支付订单号  |
|  34   | remark |   varchar   | 255 |   0    |    Y     |  N   |       | 用户备注  |
|  35   | integral_ratio |   int   | 10 |   0    |    Y     |  N   |   0    | 哈希币兑换比  |
|  36   | del_flag |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1:正常2:删除  |
|  37   | user_del |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 用户是否删除1:正常2:删除  |
|  38   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  39   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_deliver_detail">hashmart_order_deliver_detail</a>

**说明：** 哈希币商品发货物流明细表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |       | 关联的用户id  |
|  4   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1:进行中2:已签收  |
|  5   | express_detail |   text   | 65535 |   0    |    Y     |  N   |       | 物流详情  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  7   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_detail">hashmart_order_detail</a>

**说明：** 哈希币商城订单详情

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户id  |
|  4   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 商品id  |
|  5   | goods_name |   varchar   | 155 |   0    |    Y     |  N   |       | 商品名称  |
|  6   | goods_image |   varchar   | 155 |   0    |    Y     |  N   |       | 商品图片  |
|  7   | rule_id |   int   | 10 |   0    |    Y     |  N   |   0    | 规格id  |
|  8   | rule |   varchar   | 255 |   0    |    Y     |  N   |       | 规格描述  |
|  9   | num |   int   | 10 |   0    |    Y     |  N   |   0    | 购买数量  |
|  10   | real_pay_amount |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 实际支付金额  |
|  11   | real_pay_integral |   decimal   | 11 |   2    |    Y     |  N   |       | 实际支付积分  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  13   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_express">hashmart_order_express</a>

**说明：** 运费订单

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | order_no |   varchar   | 55 |   0    |    Y     |  N   |       | 订单号  |
|  3   | pay_no |   varchar   | 55 |   0    |    Y     |  N   |       | 支付订单号  |
|  4   | amount |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 支付金额  |
|  5   | pay_way |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付方式1:微信2:支付宝3:哈希币4:余额  |
|  6   | pay_status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付状态1:待支付2:已支付3:已退款4:部分退款5:部分支付6:支付异常  |
|  7   | box_type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付的盒子类型1:全部2:盒子中  |
|  8   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 支付人id  |
|  9   | username |   varchar   | 155 |   0    |    Y     |  N   |       | 支付人名  |
|  10   | address_id |   int   | 10 |   0    |    Y     |  N   |   0    | 发货地址id  |
|  11   | express_content |   text   | 65535 |   0    |    Y     |  N   |       | 发货内容  |
|  12   | pay_time |   datetime   | 19 |   0    |    Y     |  N   |       | 支付时间  |
|  13   | third_no |   varchar   | 155 |   0    |    Y     |  N   |       | 三方单号  |
|  14   | third_return |   text   | 65535 |   0    |    Y     |  N   |       | 三方返回的信息  |
|  15   | pay_error |   varchar   | 255 |   0    |    Y     |  N   |       | 异常支付信息  |
|  16   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  17   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_record">hashmart_order_record</a>

**说明：** 用户中奖表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  3   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属盲盒的id  |
|  4   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户id  |
|  5   | username |   varchar   | 155 |   0    |    Y     |  N   |       | 用户名  |
|  6   | award_num |   int   | 10 |   0    |    Y     |  N   |   0    | 奖品数量  |
|  7   | total_amount |   decimal   | 11 |   2    |    Y     |  N   |       | 奖品总金额  |
|  8   | total_exchange_integral |   decimal   | 11 |   2    |    Y     |  N   |       | 奖品总可兑换哈希币  |
|  9   | total_profit |   decimal   | 11 |   2    |    Y     |  N   |       | 带负号的总盈亏  |
|  10   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  11   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_record_detail">hashmart_order_record_detail</a>

**说明：** 订单获取的奖品详情

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | order_record_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户中奖记录id  |
|  3   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  4   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户id  |
|  5   | user_name |   varchar   | 155 |   0    |    Y     |  N   |       | 中奖用户名  |
|  6   | hash_key |   varchar   | 255 |   0    |    Y     |  N   |       | 用户当前的hash_key  |
|  7   | order_time |   varchar   | 15 |   0    |    Y     |  N   |       | 当前毫秒级时间戳  |
|  8   | hash_no |   int   | 10 |   0    |    Y     |  N   |   0    | hash算法算出的用户hash值  |
|  9   | tag_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属标签  |
|  10   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 获得商品id  |
|  11   | goods_image |   varchar   | 155 |   0    |    Y     |  N   |       | 获得商品图片  |
|  12   | goods_name |   varchar   | 155 |   0    |    Y     |  N   |       | 获得商品名  |
|  13   | unit_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 该商品单抽价格  |
|  14   | goods_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 商品的价值  |
|  15   | recovery_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 兑换价格  |
|  16   | profit |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 带负号的盈亏值  |
|  17   | ratio |   int   | 10 |   0    |    Y     |  N   |   0    | 哈希币兑换比例  |
|  18   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态：1盒子中2已兑换3已提货  |
|  19   | range |   varchar   | 155 |   0    |    Y     |  N   |       | 抽中范围  |
|  20   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 下单时间  |
|  21   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_order_record_recently">hashmart_order_record_recently</a>

**说明：** 用户最近N个中奖信息，用户保底等场景

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |       | 用户id  |
|  4   | hash_key |   varchar   | 255 |   0    |    Y     |  N   |       | 用户当前的hash_key  |
|  5   | order_time |   varchar   | 15 |   0    |    Y     |  N   |       | 当前毫秒级时间戳  |
|  6   | hash_no |   int   | 10 |   0    |    Y     |  N   |   0    | hash算法算出的用户hash值  |
|  7   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 获得商品id  |
|  8   | unit_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 该商品单抽价格  |
|  9   | goods_price |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 商品的价值  |
|  10   | profit |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 带负号的盈亏值  |
|  11   | status |   tinyint   | 4 |   0    |    Y     |  N   |       | 状态：1盒子中2已兑换3已提货  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 下单时间  |
|  13   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_admin">hashmart_sys_admin</a>

**说明：** 管理员表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | role_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属角色id  |
|  3   | username |   varchar   | 55 |   0    |    Y     |  N   |       | 用户名  |
|  4   | nickname |   varchar   | 55 |   0    |    Y     |  N   |       | 昵称  |
|  5   | password |   varchar   | 64 |   0    |    Y     |  N   |       | 密码  |
|  6   | salt |   varchar   | 30 |   0    |    Y     |  N   |       | 密码盐  |
|  7   | avatar |   varchar   | 255 |   0    |    Y     |  N   |       | 头像  |
|  8   | email |   varchar   | 100 |   0    |    Y     |  N   |       | 邮箱  |
|  9   | phone |   varchar   | 11 |   0    |    Y     |  N   |       | 手机  |
|  10   | login_failure |   tinyint   | 3 |   0    |    Y     |  N   |   0    | 登录失败次数  |
|  11   | last_login_time |   datetime   | 19 |   0    |    Y     |  N   |       | 登录时间  |
|  12   | last_login_ip |   varchar   | 50 |   0    |    Y     |  N   |       | 登录IP  |
|  13   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态:2禁用,1=启用  |
|  14   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  15   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_admin_log">hashmart_sys_admin_log</a>

**说明：** 管理员日志表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | admin_id |   int   | 10 |   0    |    Y     |  N   |   0    | 管理员ID  |
|  3   | username |   varchar   | 30 |   0    |    Y     |  N   |       | 管理员用户名  |
|  4   | url |   varchar   | 1500 |   0    |    Y     |  N   |       | 操作Url  |
|  5   | title |   varchar   | 100 |   0    |    Y     |  N   |       | 日志标题  |
|  6   | data |   text   | 65535 |   0    |    Y     |  N   |       | 请求数据  |
|  7   | ip |   varchar   | 50 |   0    |    Y     |  N   |       | IP  |
|  8   | user_agent |   varchar   | 500 |   0    |    Y     |  N   |       | User-Agent  |
|  9   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 操作时间  |

**表名：** <a id="hashmart_sys_attachment">hashmart_sys_attachment</a>

**说明：** 附件表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | cate_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属分类id  |
|  3   | url |   varchar   | 255 |   0    |    Y     |  N   |       | 物理路径  |
|  4   | file_type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 文件类型1:图片2:视频3.文件  |
|  5   | filename |   varchar   | 100 |   0    |    Y     |  N   |       | 文件名称  |
|  6   | file_size |   int   | 10 |   0    |    Y     |  N   |   0    | 文件大小  |
|  7   | mime_type |   varchar   | 100 |   0    |    Y     |  N   |       | mime类型  |
|  8   | storage |   varchar   | 100 |   0    |    Y     |  N   |   local    | 存储位置  |
|  9   | sha1 |   varchar   | 40 |   0    |    Y     |  N   |       | 文件sha1编码  |
|  10   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建日期  |
|  11   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_attachment_cate">hashmart_sys_attachment_cate</a>

**说明：** 资源图片分类

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 图片分类id  |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |   0    | 上级分类id  |
|  3   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 分类名  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  5   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_city">hashmart_sys_city</a>

**说明：** 省市区表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |   0    | 父级  |
|  3   | name |   varchar   | 50 |   0    |    Y     |  N   |       | 名称  |
|  4   | shortname |   varchar   | 30 |   0    |    Y     |  N   |       | 简称  |
|  5   | longitude |   varchar   | 30 |   0    |    Y     |  N   |       | 经度  |
|  6   | latitude |   varchar   | 30 |   0    |    Y     |  N   |       | 纬度  |
|  7   | level |   smallint   | 6 |   0    |    Y     |  N   |   0    | 级别  |
|  8   | sort |   mediumint   | 7 |   0    |    Y     |  N   |   0    | 排序  |
|  9   | is_show |   bit   | 1 |   0    |    Y     |  N   |   1    | 状态1有效  |

**表名：** <a id="hashmart_sys_express">hashmart_sys_express</a>

**说明：** 物流公司表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 物流公司id  |
|  2   | name |   varchar   | 55 |   0    |    Y     |  N   |       | 物流公司名称  |
|  3   | code |   varchar   | 55 |   0    |    Y     |  N   |       | 物流公司编码  |
|  4   | status |   tinyint   | 4 |   0    |    Y     |  N   |       | 状态1:显示2:隐藏  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_menu">hashmart_sys_menu</a>

**说明：** 菜单和权限规则表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | pid |   int   | 10 |   0    |    Y     |  N   |   0    | 上级菜单  |
|  3   | type |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 类型1:菜单2:功能  |
|  4   | name |   varchar   | 50 |   0    |    Y     |  N   |       | 标题  |
|  5   | auth |   varchar   | 100 |   0    |    Y     |  N   |       | 规则名称  |
|  6   | path |   varchar   | 100 |   0    |    Y     |  N   |       | 路由路径  |
|  7   | icon |   varchar   | 50 |   0    |    Y     |  N   |       | 图标  |
|  8   | component |   varchar   | 100 |   0    |    Y     |  N   |       | 组件路径  |
|  9   | sort |   int   | 10 |   0    |    Y     |  N   |   0    | 权重(排序)  |
|  10   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态:1:启用2:禁用  |
|  11   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  12   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_role">hashmart_sys_role</a>

**说明：** 角色表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | name |   varchar   | 155 |   0    |    Y     |  N   |       | 角色名  |
|  3   | rule |   text   | 65535 |   0    |    Y     |  N   |       | 拥有的权限  |
|  4   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1正常2禁用  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_sys_setting">hashmart_sys_setting</a>

**说明：** 配置表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 配置id  |
|  2   | type |   varchar   | 55 |   0    |    Y     |  N   |       | 配置类型  |
|  3   | text |   varchar   | 155 |   0    |    Y     |  N   |       | 配置名  |
|  4   | key |   varchar   | 55 |   0    |    Y     |  N   |       | 编号  |
|  5   | value |   text   | 65535 |   0    |    Y     |  N   |       | 配置的值  |

**表名：** <a id="hashmart_user">hashmart_user</a>

**说明：** 用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 用户id  |
|  2   | nickname |   varchar   | 155 |   0    |    Y     |  N   |       | 用户昵称  |
|  3   | hash_key |   varchar   | 155 |   0    |    Y     |  N   |   我的种子    | 用户hashkey  |
|  4   | phone |   varchar   | 11 |   0    |    Y     |  N   |       | 用户手机号  |
|  5   | openid |   varchar   | 55 |   0    |    Y     |  N   |       | 微信openid  |
|  6   | avatar |   varchar   | 155 |   0    |    Y     |  N   |       | 用户头像  |
|  7   | password |   varchar   | 64 |   0    |    Y     |  N   |       | 密码  |
|  8   | integral |   decimal   | 21 |   2    |    Y     |  N   |   0.00    | 哈希币  |
|  9   | balance |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 余额  |
|  10   | source_type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 注册来源1:微信2:后台3:app  |
|  11   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态1:正常2:禁用  |
|  12   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  13   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_address">hashmart_user_address</a>

**说明：** 用户收件地址表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 用户地址表  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的用户id  |
|  3   | receiver |   varchar   | 155 |   0    |    Y     |  N   |       | 收件人姓名  |
|  4   | phone |   varchar   | 11 |   0    |    Y     |  N   |       | 收件人手机号  |
|  5   | province_code |   varchar   | 55 |   0    |    Y     |  N   |       | 省份编码  |
|  6   | province_name |   varchar   | 55 |   0    |    Y     |  N   |       | 省份名称  |
|  7   | city_code |   varchar   | 55 |   0    |    Y     |  N   |       | 城市编码  |
|  8   | city_name |   varchar   | 55 |   0    |    Y     |  N   |       | 城市名称  |
|  9   | area_code |   varchar   | 55 |   0    |    Y     |  N   |       | 区编码  |
|  10   | area_name |   varchar   | 55 |   0    |    Y     |  N   |       | 区名称  |
|  11   | address |   varchar   | 255 |   0    |    Y     |  N   |       | 详情  |
|  12   | default_flag |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 是否默认1:是2:否  |
|  13   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  14   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_agree">hashmart_user_agree</a>

**说明：** 用户协议表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 1用户协议2隐私协议  |
|  3   | content |   text   | 65535 |   0    |    Y     |  N   |       | 协议内容  |

**表名：** <a id="hashmart_user_balance_change_log">hashmart_user_balance_change_log</a>

**说明：** 用户余额变动记录表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的用户id  |
|  3   | username |   varchar   | 55 |   0    |    Y     |  N   |       | 用户名  |
|  4   | balance |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 变动金额  |
|  5   | type |   tinyint   | 4 |   0    |    Y     |  N   |       | 类型1:抽奖消耗2:充值新增3:后台充值4:后台扣除  |
|  6   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  7   | recharge_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的充值id  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_balance_recharge_log">hashmart_user_balance_recharge_log</a>

**说明：** 用户余额充值表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | recharge_no |   varchar   | 64 |   0    |    Y     |  N   |       | 充值订单号  |
|  3   | pay_no |   varchar   | 64 |   0    |    Y     |  N   |       | 支付单号  |
|  4   | third_no |   varchar   | 155 |   0    |    Y     |  N   |       | 三方返回的订单号  |
|  5   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户id  |
|  6   | username |   varchar   | 155 |   0    |    Y     |  N   |       | 用户名  |
|  7   | amount |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 充值金额  |
|  8   | pay_way |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 支付方式1:微信2:支付宝  |
|  9   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 充值状态1:待支付2:支付成功3:支付失败4:过期5:取消  |
|  10   | third_return |   text   | 65535 |   0    |    Y     |  N   |       | 第三方支付返回原始信息  |
|  11   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  12   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_deliver">hashmart_user_box_deliver</a>

**说明：** 订单物流表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | deliver_no |   varchar   | 64 |   0    |    Y     |  N   |       | 系统内部发货单号  |
|  3   | express_order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 运费订单id  |
|  4   | address_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的地址id  |
|  5   | address_info |   text   | 65535 |   0    |    Y     |  N   |       | 收货地址  |
|  6   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 所属用户的id  |
|  7   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 类型1:发货物流2:退货物流  |
|  8   | status |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 状态：1:待发货2:已发货3:已签收4:异常  |
|  9   | express_name |   varchar   | 155 |   0    |    Y     |  N   |       | 物流公司  |
|  10   | express_code |   varchar   | 155 |   0    |    Y     |  N   |       | 物流标识  |
|  11   | express_no |   varchar   | 155 |   0    |    Y     |  N   |       | 物流单号  |
|  12   | express |   longtext   | 2147483647 |   0    |    Y     |  N   |       | 物流信息  |
|  13   | express_status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 物流状态1:录入2:进行中3:已完成  |
|  14   | postage_fee |   decimal   | 11 |   2    |    Y     |  N   |       | 物流费用  |
|  15   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  16   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_deliver_detail">hashmart_user_box_deliver_detail</a>

**说明：** 用户盒子发货详情表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | deliver_id |   int   | 10 |   0    |    Y     |  N   |   0    | 发货id  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的用户id  |
|  4   | user_box_uuid |   varchar   | 55 |   0    |    Y     |  N   |       | 用户奖品盒子uuid  |
|  5   | record_detail_id |   int   | 10 |   0    |    Y     |  N   |   0    | 中奖记录id  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  7   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_exchange">hashmart_user_box_exchange</a>

**说明：** 用户箱子兑换表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | exchange_no |   varchar   | 64 |   0    |    Y     |  N   |       | 系统内部兑换单号  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 管理的用户id  |
|  4   | username |   varchar   | 55 |   0    |    Y     |  N   |       | 用户名  |
|  5   | exchange_num |   int   | 10 |   0    |    Y     |  N   |   0    | 兑换数  |
|  6   | total_amount |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 总价格  |
|  7   | status |   tinyint   | 4 |   0    |    Y     |  N   |   2    | 状态1:待审批2:已兑换  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 交换时间  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_exchange_detail">hashmart_user_box_exchange_detail</a>

**说明：** 用户盒子发货详情表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | exchange_id |   int   | 10 |   0    |    Y     |  N   |   0    | 发货id  |
|  3   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的用户id  |
|  4   | user_box_uuid |   varchar   | 55 |   0    |    Y     |  N   |       | 用户奖品盒子uuid  |
|  5   | record_detail_id |   int   | 10 |   0    |    Y     |  N   |   0    | 中奖记录id  |
|  6   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 商品的id  |
|  7   | num |   int   | 10 |   0    |    Y     |  N   |   1    | 兑换数量  |
|  8   | amount |   decimal   | 11 |   2    |    Y     |  N   |       | 兑换的等效货币  |
|  9   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  10   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_hot">hashmart_user_box_hot</a>

**说明：** 用户奖品盒子热点表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户ID  |
|  3   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 盲盒ID  |
|  4   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 盲盒购买订单ID  |
|  5   | record_detail_id |   int   | 10 |   0    |    Y     |  N   |       | 中奖记录id  |
|  6   | out_trade_no |   varchar   | 64 |   0    |    Y     |  N   |       | 商户订单号  |
|  7   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 奖品ID  |
|  8   | goods_name |   varchar   | 155 |   0    |    Y     |  N   |       | 奖品名称  |
|  9   | goods_image |   varchar   | 155 |   0    |    Y     |  N   |       | 奖品图片  |
|  10   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态1:盒子中2:已兑换3:已提货  |
|  11   | uuid |   varchar   | 32 |   0    |    Y     |  N   |       | 与用户盒子历史表关联外键  |
|  12   | exchange_time |   datetime   | 19 |   0    |    Y     |  N   |       | 兑换时间  |
|  13   | delivery_time |   datetime   | 19 |   0    |    Y     |  N   |       | 提货时间  |
|  14   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 开箱时间  |
|  15   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_box_log">hashmart_user_box_log</a>

**说明：** 用户奖品盒子历史表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | ID  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 用户ID  |
|  3   | blindbox_id |   int   | 10 |   0    |    Y     |  N   |   0    | 盲盒ID  |
|  4   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 盲盒购买订单ID  |
|  5   | record_detail_id |   int   | 10 |   0    |    Y     |  N   |   0    | 中奖记录id  |
|  6   | out_trade_no |   varchar   | 64 |   0    |    Y     |  N   |       | 商户订单号  |
|  7   | goods_id |   int   | 10 |   0    |    Y     |  N   |   0    | 奖品ID  |
|  8   | goods_name |   varchar   | 155 |   0    |    Y     |  N   |       | 奖品名称  |
|  9   | goods_image |   varchar   | 155 |   0    |    Y     |  N   |       | 奖品图片  |
|  10   | status |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 状态1:盒子中2:已兑换3:已提货  |
|  11   | uuid |   varchar   | 32 |   0    |    Y     |  N   |       | 与用户盒子热点表关联外键  |
|  12   | exchange_time |   datetime   | 19 |   0    |    Y     |  N   |       | 兑换时间  |
|  13   | delivery_time |   datetime   | 19 |   0    |    Y     |  N   |       | 提货时间  |
|  14   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 开箱时间  |
|  15   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_integral_change_log">hashmart_user_integral_change_log</a>

**说明：** 用户哈希币变动记录

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | id  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的用户id  |
|  3   | username |   varchar   | 55 |   0    |    Y     |  N   |       | 用户名  |
|  4   | integral |   decimal   | 11 |   2    |    Y     |  N   |   0.00    | 变动的哈希币  |
|  5   | type |   tinyint   | 4 |   0    |    Y     |  N   |   1    | 类型1:回收新增2:兑换消耗3:后台赠送4:后台减少  |
|  6   | order_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的订单id  |
|  7   | exchange_id |   int   | 10 |   0    |    Y     |  N   |   0    | 关联的回收的id  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="hashmart_user_login_log">hashmart_user_login_log</a>

**说明：** 用户登录日志表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 日志id  |
|  2   | user_id |   int   | 10 |   0    |    Y     |  N   |   0    | 登录用户id  |
|  3   | nickname |   varchar   | 155 |   0    |    Y     |  N   |       | 登录用户昵称  |
|  4   | login_ip |   varchar   | 55 |   0    |    Y     |  N   |       | 登录的ip  |
|  5   | user_agent |   varchar   | 255 |   0    |    Y     |  N   |       | 登录的设备  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 登录时间  |
