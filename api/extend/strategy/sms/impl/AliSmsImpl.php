<?php

namespace strategy\sms\impl;

use Darabonba\OpenApi\Models\Config;
use AlibabaCloud\SDK\Dysmsapi\V20170525\Dysmsapi;
use AlibabaCloud\SDK\Dysmsapi\V20170525\Models\SendSmsRequest;
use AlibabaCloud\Tea\Utils\Utils\RuntimeOptions;
use strategy\sms\SmsInterface;

class AliSmsImpl implements SmsInterface
{
    protected function createClient($accessKeyId, $accessKeySecret)
    {
        $config = new Config([
            "accessKeyId" => $accessKeyId,
            "accessKeySecret" => $accessKeySecret
        ]);

        $config->endpoint = "dysmsapi.aliyuncs.com";
        return new Dysmsapi($config);
    }

    public function send($param)
    {
        $client = $this->createClient($param["accessKeyId"], $param["accessKeySecret"]);

        $sendSmsRequest = new SendSmsRequest([
            "signName" => $param['signName'],
            "templateCode" => $param['templateCode'],
            "phoneNumbers" => $param['phone'],
            "templateParam" => $param['code']
        ]);

        $runtime = new RuntimeOptions([]);
        try {

            $client->sendSmsWithOptions($sendSmsRequest, $runtime);
        } catch (\Exception $e) {
            return dataReturn(-1, '发送失败', $e->getMessage());
        }

        return dataReturn(0, '发送成功');
    }
}