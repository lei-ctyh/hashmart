<?php

namespace app\api\service\common;

use app\model\user\UserAgree;

class UserAgreementService
{
    /**
     * 获取用户协议
     * @return array
     */
    public function getUserAgreement($type)
    {
        return (new UserAgree())->findOne(['type' => $type]);
    }
}