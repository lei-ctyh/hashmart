<?php

return [

    'jwt_key' => 'HashMart20221225!@#',

    // password salt
    'salt' => 'HashMart20230113@##$$%$',

    // 存储映射
    'store_config' => [
        'local' => '',
        'aliyun' => 'store_oss',
        'qiniu' => 'store_qiniu',
        'qcloud' => 'store_tencent'
    ],

    // 域名key和存储位置映射
    'store_domain' => [
        'aliyun' => 'oss_domain',
        'qiniu' => 'qiniu_domain',
        'qcloud' => 'tencent_domain'
    ],

    // hash累计值
    'hash_total' => 1048575,

    // 最小概率值
    'min_probability' => 0.0001
];