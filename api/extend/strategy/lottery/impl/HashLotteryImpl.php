<?php

namespace strategy\lottery\impl;

use app\api\service\goods\BlindboxService;
use app\model\order\OrderRecord;
use app\model\order\OrderRecordDetail;
use app\model\user\UserBoxHot;
use app\model\user\UserBoxLog;
use strategy\lottery\LotteryInterface;

class HashLotteryImpl implements LotteryInterface
{
    public function run($param)
    {
        // 盲盒信息
        $blindboxService = new BlindboxService();
        $goodsList = $blindboxService->getBlindboxDetail(['id' => $param['blindbox_id']]);
        if (empty($goodsList['data'])) {
            return dataReturn(-11, '盲盒数据错误');
        }
        $goodsList = $goodsList['data']['list'];

        $recordModel = new OrderRecord();
        $recordId = $recordModel->insertOne([
            'order_id' => $param['id'],
            'blindbox_id' => $param['blindbox_id'],
            'user_id' => $param['user_id'],
            'username' => $param['user_name'],
            'award_num' => 0, // 后面更新
            'total_amount' => 0, // 后面更新
            'total_exchange_integral' => 0, // 后面更新
            'total_profit' => 0, // 后面更新
            'create_time' => date('Y-m-d H:i:s')
        ]);
        if ($recordId['code'] != 0) {
            return $recordId;
        }
        $recordId = $recordId['data'];

        $awardNum = 0;
        $totalAmount = 0;
        $totalExchangeIntegral = 0;
        $boxData = [];
        $orderRecordDetailModel = new OrderRecordDetail();

        // 当前兑换比例
        $ratio = getConfByType('sys_base')['change_ratio'];
        if (empty($ratio)) {
            $ratio = 1; // 未设置则兑换比例为 1:1
        }

        for ($i = 0; $i < $param['total_num']; $i++) {

            $totalAmount += $param['unit_price']; // 商品单抽价格

            $time = getMillisecond();
            $hashNo = $this->makeHashNo($param['hash_key'], $time);
            $goods = [];
            foreach ($goodsList as $vo) {
                if ($vo['lottery_min_no'] < $hashNo && $vo['lottery_max_no'] > $hashNo) {
                    $goods = $vo;
                    $awardNum += 1;
                    $totalExchangeIntegral += $vo['recovery_price']; // 兑换的哈希币金额
                    break;
                }
            }

            if (empty($goods)) {
                return dataReturn(-15, '盲盒商品配置错误');
            }

            // 中奖详情
            $detailId = $orderRecordDetailModel->insertOne([
                'order_record_id' => $recordId,
                'order_id' => $param['id'],
                'user_id' => $param['user_id'],
                'user_name' => $param['user_name'],
                'hash_key' => $param['hash_key'],
                'order_time' => $time,
                'hash_no' => $hashNo,
                'tag_id' => $goods['tag_id'], // 标签
                'goods_id' => $goods['goods_id'],
                'goods_image' => $goods['goods_image'],
                'goods_name' => $goods['goods_name'],
                'unit_price' => $param['unit_price'],
                'goods_price' => $goods['price'],
                'recovery_price' => $goods['recovery_price'],
                'profit' => round($goods['recovery_price'] / $ratio, 2) - $param['unit_price'], // 换算成真实的RMB盈亏
                'ratio' => $ratio,
                'status' => 1,
                'range' => $goods['lottery_min_no'] . ',' . $goods['lottery_max_no'],
                'create_time' => date('Y-m-d H:i:s')
            ]);
            if ($detailId['code'] != 0) {
                return $detailId;
            }
            $detailId = $detailId['data'];

            // 放入盒子
            $uuid = uniqid();
            $boxData[] = [
                'user_id' => $param['user_id'],
                'blindbox_id' => $param['blindbox_id'],
                'order_id' => $param['id'],
                'record_detail_id' => $detailId,
                'out_trade_no' => $param['order_no'],
                'goods_id' => $goods['goods_id'],
                'goods_image' => $goods['goods_image'],
                'goods_name' => $goods['goods_name'],
                'status' => 1,
                'uuid' => $uuid,
                'create_time' => date('Y-m-d H:i:s')
            ];
        }

        $res = $recordModel->updateById([
            'award_num' => $awardNum,
            'total_amount' => $totalAmount,
            'total_exchange_integral' => $totalExchangeIntegral,
            'total_profit' => round($totalExchangeIntegral / $ratio - $totalAmount, 2),
            'update_time' => date('Y-m-d H:i:s')
        ], $recordId);
        if ($res['code'] != 0) {
            return $res;
        }

        // 盒子表
        $res = (new UserBoxHot())->insertBatch($boxData);
        if ($res['code'] != 0) {
            return $res;
        }

        // 盒子记录表
        $res = (new UserBoxLog())->insertBatch($boxData);
        if ($res['code'] != 0) {
            return $res;
        }

        return dataReturn(0, 'success', ['record_id' => $recordId]);
    }

    /**
     * 生成中奖种子
     * @param $hashKey
     * @param $time
     * @return float|int
     */
    protected function makeHashNo($hashKey, $time)
    {
        return hexdec(substr(hash("sha256", $hashKey . $time), 0, 5));
    }
}