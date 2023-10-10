<?php

namespace app\admin\service\box;

use app\admin\validate\BlindboxDetailValidate;
use app\model\box\BlindboxDetail;
use think\exception\ValidateException;

class BlindboxDetailService
{
    /**
     * 获取盲盒详情列表
     * @param $param
     * @return array
     */
    public function getBlindboxDetailList($param)
    {
        $where[] = ['blindbox_id', '=', $param['blindbox_id']];
        if (!empty($param['goods_name'])) {
            $where[] = ['goods_name', '=', $param['goods_name']];
        }

        if (!empty($param['tag_id'])) {
            $where[] = ['tag_id', '=', $param['tag_id']];
        }

        $blindboxDetailModel = new BlindboxDetail();
        $list = $blindboxDetailModel->with(['blindbox', 'boxTag'])->where($where)->order('probability asc')->paginate($param['limit']);

        return pageReturn(dataReturn(0, 'success', $list));
    }

    /**
     * 添加盲盒商品
     * @param $param
     * @return array
     */
    public function addBlindboxDetail($param)
    {
        try {
            validate(BlindboxDetailValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $blindboxDetailModel = new BlindboxDetail();
        $has = $blindboxDetailModel->checkUnique([
            'goods_id' => $param['goods_id'],
            'blindbox_id' => $param['blindbox_id']
        ])['data'];
        if (!empty($has)) {
            return dataReturn(-2, '该商品已经存在');
        }

        if (empty($param['probability'])) {
            return dataReturn(-4, '请输入正确的概率');
        }

        // 最大概率设置
        $sum = $blindboxDetailModel->where('blindbox_id', $param['blindbox_id'])->sum('probability');
        if ($sum == 100) {
            return dataReturn(-6, '该盲盒的概率已达到100%无法新增了');
        }

        $canSet = round(100 - $sum, 4);
        if (bccomp($param['probability'], $canSet, 4) == 1) {
            return dataReturn(-5, '该盲盒剩下最大的概率只可设置为：' . $canSet . '%');
        }

        $param['create_time'] = now();
        $blindboxDetailModel->insertOne($param);

        return $this->recalculateNoRange($param['blindbox_id']);
    }

    /**
     * 编辑盲盒商品
     * @param $param
     * @return array
     */
    public function editBlindboxDetail($param)
    {
        try {
            validate(BlindboxDetailValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        $blindboxDetailModel = new BlindboxDetail();

        $where[] = ['goods_id', '=', $param['goods_id']];
        $where[] = ['blindbox_id', '=', $param['blindbox_id']];

        $blindboxInfo = $blindboxDetailModel->findOne($where)['data'];
        if (!empty($blindboxInfo) && $blindboxInfo['id'] != $param['id']) {
            return dataReturn(-2, '该商品已经存在');
        }

        if (empty($param['probability'])) {
            return dataReturn(-4, '请输入正确的概率');
        }

        // 最大概率设置
        $sum = $blindboxDetailModel->where('id', '<>', $param['id'])
            ->where('blindbox_id', $param['blindbox_id'])->sum('probability');
        if ($sum == 100) {
            return dataReturn(-6, '该盲盒的概率已达到100%无法新增了');
        }

        $canSet = round(100 - $sum, 4);
        if (bccomp((float)$param['probability'], $canSet, 4) == 1) {
            return dataReturn(-5, '该盲盒剩下最大的概率只可设置为：' . $canSet . '%');
        }

        if (!empty($blindboxInfo) && $blindboxInfo['probability'] == $param['probability']) {
            unset($param['lottery_min_no'], $param['lottery_max_no']);
        }

        $param['update_time'] = now();
        $blindboxDetailModel->updateById($param, $param['id']);

        // 改变了概率则重新计算
        if (!empty($blindboxInfo) && $blindboxInfo['probability'] != $param['probability']) {
            return $this->recalculateNoRange($param['blindbox_id']);
        } else {
            return dataReturn(0, '编辑成功');
        }
    }

    /**
     * 删除盲盒商品
     * @param $id
     * @param $blindboxId
     * @return array
     */
    public function delBlindboxDetail($id, $blindboxId)
    {
        $blindboxDetailModel = new BlindboxDetail();
        $blindboxDetailModel->delById($id);

        return $this->recalculateNoRange($blindboxId);
    }

    /**
     * 获取当前可设置的最大概率
     * @param $blindboxId
     * @param $percent
     * @param $detailId
     * @return array
     */
    public function getLotteryProbability($blindboxId, $percent, $detailId = 0)
    {
        $sum = BlindboxDetail::where('blindbox_id', $blindboxId)->sum('probability');
        if ($detailId > 0) {
            $detailPercent = BlindboxDetail::find($detailId)['probability'];
            $sum = bcsub($sum, $detailPercent, 4);
        }

        return dataReturn(0, 'success', [
            'percent' => bcsub(100, $sum, 4)
        ]);
    }

    /**
     * 获取抽奖数字范围
     * @param $blindboxId
     * @param $percent
     * @return array
     */
    public function getLotteryNumRange($blindboxId, $percent, $detailId)
    {
        $blindboxDetailModel = new BlindboxDetail();
        $leftPercent = $this->getLotteryProbability($blindboxId, $percent, $detailId)['data']['percent'];

        if ($percent > $leftPercent) {
            return dataReturn(-1, '您最多可设置的百分比值是: ' . $leftPercent . '%');
        }

        // 若概率未变
        $blindboxDetailInfo = $blindboxDetailModel->findOne(['id' => $detailId])['data'];
        if (!empty($blindboxDetailInfo) && $blindboxDetailInfo['probability'] == $percent) {
            return dataReturn(0, 'success', [
                'lottery_min_no' => $blindboxDetailInfo['lottery_min_no'],
                'lottery_max_no' => $blindboxDetailInfo['lottery_max_no']
            ]);
        }

        $where[] = ['blindbox_id', '=', $blindboxId];
        $where[] = ['probability', '<=', $percent];
        $list = $blindboxDetailModel->getAllList($where, '*', 'probability asc')['data'];
        $dbMaxNo = (count($list) == 0) ? 0 : $list[count($list) - 1]['lottery_max_no'];

        $sysMaxNo = config('shop.hash_total');
        $min = $dbMaxNo == 0 ? 0 : $dbMaxNo + 1;
        $max = $min + ceil(config('shop.hash_total') * ($percent / 100));
        if ($max > $sysMaxNo) {
            $max = $sysMaxNo;
        }

        return dataReturn(0, 'success', [
            'lottery_min_no' => $min,
            'lottery_max_no' => $max
        ]);
    }

    /**
     * 重新计算中奖数字范围
     * @param $blindboxId
     * @return array
     */
    private function recalculateNoRange($blindboxId)
    {
        $blindboxDetailModel = new BlindboxDetail();
        $list = $blindboxDetailModel->getAllList(['blindbox_id' => $blindboxId], 'id,probability', 'probability asc')['data'];

        $min = 0;
        $sysMaxNo = config('shop.hash_total');
        foreach ($list as $vo) {

            $max = $min + ceil($sysMaxNo * ($vo['probability'] / 100));
            if ($max > $sysMaxNo) {
                $max = $sysMaxNo;
            }

            $blindboxDetailModel->updateById([
                'lottery_min_no' => $min,
                'lottery_max_no' => $max
            ], $vo['id']);

            $min = $max + 1;
        }

        return dataReturn(0, '操作成功');
    }
}