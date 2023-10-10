<?php

namespace app\api\service\user;

use app\api\validate\user\LoginValidate;
use app\model\user\User;
use app\model\user\UserLoginLog;
use EasyWeChat\Factory;
use think\facade\Cache;
use think\facade\Config;

class LoginService
{
    /**
     * 微信授权登录
     * @param $param
     * @return array|mixed
     */
    public function doLoginByWechat($param)
    {
        if (!isset($param['phone_code']) || empty($param['phone_code'])) {
            return dataReturn(-1, '参数错误');
        }

        if (!isset($param['code']) || empty($param['code'])) {
            return dataReturn(-2, '参数错误');
        }

        $dbConfig = getConfByType('miniapp');
        $config = [
            'app_id' => $dbConfig['miniapp_app_id'],
            'secret' => $dbConfig['miniapp_app_secret']
        ];

        try {

            $app = Factory::miniProgram($config);
            // 拿手机号
            $accessToken = $app->access_token;
            $token = $accessToken->getToken(); // token 数组  token['access_token'] 字符串
            $token = $token['access_token'];
            $url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=$token";
            $res = $this->httpRequest($url, json_encode(['code' => $param['phone_code']]));
            $res = json_decode($res, true);
            if (isset($res['errcode']) && $res['errcode'] == 40001) {
                $token = $accessToken->getToken(true); // 强刷token
                $token = $token['access_token'];
                $url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=$token";
                $res = $this->httpRequest($url, json_encode(['code' => $param['phone_code']]));
                $res = json_decode($res, true);
            }
            $phone = $res['phone_info']['phoneNumber'];

            $userModel = new User();
            $userInfo = $userModel->findOne([
                'phone' => $phone
            ])['data'];

            if (empty($userInfo)) {
                $regParam = $userInfo = [
                    'nickname' => '微信用户',
                    'phone' => $phone,
                    'avatar' => getRandAvtar()
                ];

                // 拿openid
                $result = $app->auth->session($param['code']);
                if (!isset($result['openid'])) {
                    return dataReturn(-1, '系统错误');
                }

                $regParam['openid'] = $result['openid'];
                $regParam['password'] = makePassword('123456');
                $regParam['source_type'] = 1;
                $regParam['create_time'] = date('Y-m-d H:i:s');

                $userIdInfo = $this->register($regParam);
                $userInfo['id'] = $userIdInfo['data'];
            } else {

                if ($userInfo['status'] == 2) {
                    return dataReturn(-3, '该账号已被封禁');
                }

                // 补全openid
                if (empty($userInfo['openid'])) {
                    $result = $app->auth->session($param['code']);
                    if (!isset($result['openid'])) {
                        return dataReturn(-1, '系统错误');
                    }

                    $userModel->updateById(['openid' => $result['openid']], $userInfo['id']);
                }
            }

            return $this->makeReturnData($userInfo);
        } catch (\Exception $e) {
            return dataReturn(-4, $e->getMessage());
        }
    }

    /**
     * 根据账号密码登录
     * @param $param
     * @return array|mixed
     */
    public function doLoginByAccount($param)
    {
        $loginValidate = new LoginValidate();
        if (!$loginValidate->scene('account')->check($param)) {
            return dataReturn(-1, $loginValidate->getError());
        }

        $userModel = new User();
        $userInfo = $userModel->findOne([
            'phone' => $param['phone']
        ])['data'];

        if (empty($userInfo)) {
            return dataReturn(-1, '用户名密码错误');
        }

        // 密码检测
        $salt = Config::get('user.salt');
        if (makePassword($param['password'], $salt) != $userInfo['password']) {
            return dataReturn(-2, '用户名密码错误');
        }

        if ($userInfo['status'] == 2) {
            return dataReturn(-3, '您已被禁用');
        }

        return $this->makeReturnData($userInfo);
    }

    /**
     * 短信密码登录
     * @param $param
     * @return array|mixed
     */
    public function doLoginBySms($param)
    {
        $loginValidate = new LoginValidate();
        if (!$loginValidate->scene('code')->check($param)) {
            return dataReturn(-1, $loginValidate->getError());
        }

        $userModel = new User();
        $userInfo = $userModel->findOne([
            'phone' => $param['phone']
        ])['data'];

        if (empty($userInfo)) {
            $regParam = $userInfo = [
                'nickname' => $param['phone'],
                'phone' => $param['phone'],
                'avatar' => getRandAvtar()
            ];

            $regParam['openid'] = '';
            $regParam['password'] = makePassword('123456');
            $regParam['source_type'] = 3;
            $regParam['create_time'] = date('Y-m-d H:i:s');

            $userIdInfo = $this->register($regParam);
            $userInfo['id'] = $userIdInfo['data'];
        }

        // 校验验证码
        $param['type'] = 'login_sms_code';
        $code = Cache::get($param['phone'] . '_' . $param['type']);
        if (empty($code)) {
            return dataReturn(-2, '验证码过期');
        }

        if ($code != $param['code']) {
            return dataReturn(-3, '验证码不正确');
        }

        return $this->makeReturnData($userInfo);
    }

    /**
     * 手机号一键登录
     * @param $param
     * @return array|mixed
     */
    public function doLoginByPhone($param)
    {
        $loginValidate = new LoginValidate();
        if (!$loginValidate->scene('phone')->check($param)) {
            return dataReturn(-1, $loginValidate->getError());
        }

        $config = getConfByType('uniapp');
        $param['appid'] = $config['uniapp_appid'];
        $param['apiKey'] = $config['uniapp_api_key'];
        $param['apiSecret'] = $config['uniapp_api_secret'];
        $sign = $this->getSignature($param, $config['uniapp_api_secret']);
        $requestUrl = $config['uniapp_cloud_url'] . "?sign=" . $sign . "&" . http_build_query($param);
        $result = json_decode(file_get_contents($requestUrl), true);
        if (isset($result['error'])) {
            return dataReturn(-2, $result['error']['message']);
        }

        $phone = $result['data']['phoneNumber'];
        $userModel = new User();
        $userInfo = $userModel->findOne([
            'phone' => $phone
        ])['data'];

        if (empty($userInfo)) {
            $regParam = $userInfo = [
                'nickname' => $phone,
                'phone' => $phone,
                'avatar' => getRandAvtar()
            ];

            $regParam['openid'] = '';
            $regParam['password'] = makePassword('123456');
            $regParam['source_type'] = 3;
            $regParam['create_time'] = date('Y-m-d H:i:s');

            $userIdInfo = $this->register($regParam);
            $userInfo['id'] = $userIdInfo['data'];
        }

        return $this->makeReturnData($userInfo);
    }

    /**
     * 忘记密码
     * @param $param
     * @return array|mixed
     */
    public function forgetPassword($param)
    {
        $loginValidate = new LoginValidate();
        if (!$loginValidate->check($param)) {
            return dataReturn(-1, $loginValidate->getError());
        }

        $userModel = new User();
        $userInfo = $userModel->findOne([
            'phone' => $param['phone']
        ])['data'];

        if (empty($userInfo)) {
            return dataReturn(-1, '您尚未注册');
        }

        // 校验验证码
        $param['type'] = 'forget_sms_code';
        $code = Cache::get($param['phone'] . '_' . $param['type']);
        if (empty($code)) {
            return dataReturn(-2, '验证码过期');
        }

        if ($code != $param['code']) {
            return dataReturn(-3, '验证码不正确');
        }

        $userModel->updateById([
            'password' => makePassword($param['password'], Config::get('user.salt')),
            'update_time' => date('Y-m-d H:i:s')
        ], $userInfo['id']);

        return dataReturn(0, '操作成功');
    }

    /**
     * 获取手机号
     * @param $param
     * @return array
     */
    public function getUserPhone($param)
    {
        $formatConfig = getConfByType('miniapp');
        $config = [
            'app_id' => $formatConfig['miniapp_app_id'],
            'secret' => $formatConfig['miniapp_app_secret'],
            'response_type' => 'array',
        ];

        $app = Factory::miniProgram($config);
        $accessToken = $app->access_token;
        $token = $accessToken->getToken(); // token 数组  token['access_token'] 字符串
        $token = $token['access_token'];
        $url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=$token";
        $res = $this->httpRequest($url, json_encode(['code' => $param['code']]));

        return dataReturn(0, 'success', json_decode($res, true));
    }

    /**
     * 获取uniapp配置
     * @return array
     */
    public function getUniapp()
    {
        return dataReturn(0, 'success', getConfByType('uniapp'));
    }

    protected function httpRequest($url, $data = null)
    {
        $curl = curl_init();
        curl_setopt($curl, CURLOPT_URL, $url);
        curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, FALSE);

        if (!empty($data)) {
            curl_setopt($curl, CURLOPT_POST, TRUE);
            curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json'
            ));
        }
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, TRUE);
        $output = curl_exec($curl);
        curl_close($curl);

        return $output;
    }

    /**
     * 统一返回数据
     * @param $userInfo
     * @return array
     */
    protected function makeReturnData($userInfo)
    {
        $token = setJWT([
            'id' => $userInfo['id'],
            'nickname' => $userInfo['nickname'],
            'phone' => $userInfo['phone'],
            'avatar' => $userInfo['avatar']
        ]);

        $header = request()->header();
        (new UserLoginLog())->insertOne([
            'user_id' => $userInfo['id'],
            'nickname' => $userInfo['nickname'],
            'login_ip' => request()->ip(),
            'user_agent' => $header['user-agent'] ?? '',
            'create_time' => date('Y-m-d H:i:s')
        ]);

        return dataReturn(0, '登录成功', [
            'token' => $token,
            'userInfo' => [
                'id' => $userInfo['id'],
                'nickname' => $userInfo['nickname'],
                'phone' => $userInfo['phone'],
                'avatar' => $userInfo['avatar']
            ]
        ]);
    }

    protected function register($param)
    {
        return (new User())->insertOne($param);
    }

    /**
     * @param $arrData
     * @param $key
     * @return false|string
     */
    protected function getSignature($arrData, $key)
    {
        ksort($arrData);
        $paramsString = "";
        unset($arrData['mall_id']);
        foreach ($arrData as $k => $value) {
            if (strlen($paramsString) == 0)
                $paramsString .= $k . "=" . $value;
            else
                $paramsString .= "&" . $k . "=" . $value;
        }
        $stringSignTemp = rtrim($paramsString, '&');
        return hash_hmac('sha256', $stringSignTemp, $key);
    }
}