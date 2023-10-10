<?php

return [

    // 可直接跳过的权限
    'skip_rule' => [
        'user.Login/*' => 1,
        'system.AttachmentCate/api' => 1,
        'system.AttachmentCate/index' => 1,
        'system.Attachment/api' => 1,
        'system.Attachment/index' => '1',
        'system.Attachment/uploadPicture' => 1,
        'system.Attachment/uploadVideo' => 1,
        'box.BlindboxDetail/minLotteryNo' => 1,
        'box.BlindboxDetail/getLotteryNumRange' => 1,
        'box.Blindbox/index' => 1,
        'system.System/base' => 1,
        'order.IntegralOrder/expressList' => 1,
        'box.BlindboxDetail/getLotteryProbability' => 1,
        'goods.GoodsCate/index' => 1,
        'goods.Goods/index' => 1,
        'rule.Menu/getAllMenu' => 1,
        'user.UserAddress/areas' => 1,
        'system.PaySetting/uploadTxtFile'
    ]
];