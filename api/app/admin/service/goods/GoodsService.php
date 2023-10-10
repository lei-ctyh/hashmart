<?php

namespace app\admin\service\goods;

use app\admin\service\log\AdminLogService;
use app\admin\validate\GoodsValidate;
use app\model\goods\Goods;
use app\model\goods\GoodsRule;
use app\model\goods\GoodsRuleExtend;
use Chance\Log\facades\OperationLog;
use think\exception\ValidateException;
use think\facade\Db;

class GoodsService
{
    /**
     * 获取商品列表
     * @param $param
     * @return array
     */
    public function getGoodsList($param)
    {
        $goodsModel = new Goods();

        $where[] = ['delete_flag', '=', 1];
        if (!empty($param['cate_id'])) {
            $where[] = ['cate_id', '=', $param['cate_id']];
        }

        if (!empty($param['status'])) {
            $where[] = ['status', '=', $param['status']];
        }

        if (!empty($param['name'])) {
            $where[] = ['name', 'like', '%' . $param['name'] . '%'];
        }

        if (isset($param['goods_type']) && !empty($param['goods_type'])) {
            $where[] = ['goods_type', '=', $param['goods_type']];
        }

        $list = $goodsModel->with(['cate', 'rule', 'ruleExtend'])->where($where)->order('id desc')->paginate($param['limit'])
            ->each(function ($item) {
            if (!empty($item->ruleExtend->toArray())) {
                $stock = 0;
                foreach ($item->ruleExtend as $v) {
                    $stock += $v['stock'];
                }
                $item->stock = $stock;
            }
        });

        return pageReturn(dataReturn(0, 'success', $list));
    }

    /**
     * 新增商品
     * @param $param
     * @return array
     */
    public function addGoods($param)
    {
        try {
            validate(GoodsValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['type'] == 2 && (empty($param['preItem']) || empty($param['final']))) {
            return dataReturn(-2, '请生成规格属性');
        }

        $goodsModel = new Goods();

        // 检测标题重复
        $where[] = ['name', '=', $param['name']];
        $has = $goodsModel->checkUnique($where)['data'];
        if (!empty($has)) {
            return dataReturn(-3, '该商品标题已经存在');
        }

        Db::startTrans();
        try {

            $goodsId = $goodsModel->insertGetId([
                'cate_id' => $param['cate_id'],
                'goods_type' => $param['goods_type'],
                'type' => $param['type'],
                'name' => $param['name'],
                'sub_title' => $param['sub_title'],
                'image' => $param['image'],
                'content' => $param['content'] ?? '',
                'stock' => $param['stock'],
                'price' => $param['price'],
                'recovery_price' => $param['recovery_price'],
                'cost_price' => $param['cost_price'],
                'integral_price' => $param['integral_price'],
                'status' => $param['status'],
                'sales' => 0,
                'delivery_fee' => $param['delivery_fee'],
                'sort' => $param['sort'],
                'create_time' => now()
            ]);
            (new AdminLogService())->write([], OperationLog::getLog());

            if (!empty($param['preItem'][0]['item'][0])) {
                $goodsRuleModel = new GoodsRule();
                $goodsRuleModel->insert([
                    'goods_id' => $goodsId,
                    'rule' => json_encode($param['preItem'])
                ]);
                (new AdminLogService())->write([], OperationLog::getLog());
            }

            if (!empty($param['final'])) {
                // 规格详情
                $goodsRuleExtendModel = new GoodsRuleExtend();
                $batchParam = [];
                foreach ($param['final'] as $vo) {
                    $batchParam[] = [
                        'goods_id' => $goodsId,
                        'sku' => implode('※', $vo['sku']),
                        'stock' => $vo['stock'],
                        'sales' => 0,
                        'image' => $vo['image'],
                        'unique' => uniqid(),
                        'price' => $vo['price'],
                        'recovery_price' => $vo['recovery_price'],
                        'cost_price' => $vo['cost_price'],
                        'integral_price' => $vo['integral_price'],
                        'create_time' => now()
                    ];
                }
                $goodsRuleExtendModel->insertAll($batchParam);

                // 选取第一个产品的售价当多规格售价
                $goodsModel->updateById([
                    'recovery_price' => $batchParam[0]['recovery_price'],
                    'price' => $batchParam[0]['price'],
                    'integral_price' => $batchParam[0]['integral_price']
                ], $goodsId);
            }

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-4, $e->getMessage());
        }

        return dataReturn(0, '添加成功');
    }

    /**
     * 编辑商品
     * @param $param
     * @return array
     */
    public function editGoods($param)
    {
        try {
            validate(GoodsValidate::class)->check($param);
        } catch (ValidateException $e) {
            return dataReturn(-1, $e->getError());
        }

        if ($param['type'] == 2 && (empty($param['preItem']) || empty($param['final']))) {
            return dataReturn(-2, '请生成规格属性');
        }

        $goodsModel = new Goods();
        // 检测标题重复
        $where[] = ['name', '=', $param['name']];
        $where[] = ['id', '<>', $param['id']];
        $has = $goodsModel->checkUnique($where)['data'];
        if (!empty($has)) {
            return dataReturn(-3, '该商品标题已经存在');
        }

        Db::startTrans();
        try {

            $goodsModel->where('id', $param['id'])->update([
                'cate_id' => $param['cate_id'],
                'goods_type' => $param['goods_type'],
                'type' => $param['type'],
                'name' => $param['name'],
                'sub_title' => $param['sub_title'],
                'image' => $param['image'],
                'content' => $param['content'] ?? '',
                'stock' => $param['stock'],
                'price' => $param['price'],
                'recovery_price' => $param['recovery_price'],
                'cost_price' => $param['cost_price'],
                'integral_price' => $param['integral_price'],
                'status' => $param['status'],
                'sales' => 0,
                'delivery_fee' => $param['delivery_fee'],
                'sort' => $param['sort'],
                'create_time' => now()
            ]);
            (new AdminLogService())->write([], OperationLog::getLog());

            if (!empty($param['preItem'][0]['item'][0])) {
                $goodsRuleModel = new GoodsRule();
                $goodsRuleModel->where('goods_id', $param['id'])->delete();
                $goodsRuleModel->insert([
                    'goods_id' => $param['id'],
                    'rule' => json_encode($param['preItem'])
                ]);
                (new AdminLogService())->write([], OperationLog::getLog());
            }

            // 规格详情
            if (!empty($param['final'])) {
                $goodsRuleExtendModel = new GoodsRuleExtend();
                $goodsRuleExtendModel->where('goods_id', $param['id'])->delete();
                $batchParam = [];
                foreach ($param['final'] as $vo) {
                    $batchParam[] = [
                        'goods_id' => $param['id'],
                        'sku' => implode('※', $vo['sku']),
                        'stock' => $vo['stock'],
                        'sales' => 0,
                        'image' => $vo['image'],
                        'unique' => uniqid(),
                        'price' => $vo['price'],
                        'recovery_price' => $vo['recovery_price'],
                        'cost_price' => $vo['cost_price'],
                        'integral_price' => $vo['integral_price'],
                        'create_time' => now()
                    ];
                }
                $goodsRuleExtendModel->insertAll($batchParam);

                // 选取第一个产品的售价当多规格售价
                $goodsModel->updateById([
                    'recovery_price' => $batchParam[0]['recovery_price'],
                    'price' => $batchParam[0]['price'],
                    'integral_price' => $batchParam[0]['integral_price']
                ], $param['id']);
            }

            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return dataReturn(-4, $e->getTraceAsString());
        }

        return dataReturn(0, '更新成功');
    }

    /**
     * 删除商品
     * @param $id
     * @return array
     */
    public function delGoods($id)
    {
        $goodsModel = new Goods();
        $res = $goodsModel->updateById(['delete_flag' => 2], $id);

        if ($res['code'] == 0) {
            $res['msg'] = '删除成功';
        }

        return $res;
    }

    /**
     * 上下架操作
     * @param $param
     * @return array
     */
    public function shelvesGoods($param)
    {
        $goodsModel = new Goods();
        $res = $goodsModel->updateByIds([
            'status' => $param['type']
        ], $param['ids']);

        if ($res['code'] == 0) {
            $res['msg'] = '操作成功';
        }

        return $res;
    }
}