<?php
// 应用公共文件
/**
 * 模型内统一数据返回
 * @param $code
 * @param string $msg
 * @param array $data
 * @return array
 */

use app\model\system\SysSetting;

if (!function_exists('dataReturn')) {

    function dataReturn($code, $msg = 'success', $data = [])
    {
        return ['code' => $code, 'data' => $data, 'msg' => $msg];
    }
}

/**
 * 统一返回json数据
 * @param $code
 * @param string $msg
 * @param array $data
 * @return \think\response\Json
 */
if (!function_exists('jsonReturn')) {

    function jsonReturn($code, $msg = 'success', $data = [])
    {
        return json(['code' => $code, 'data' => $data, 'msg' => $msg]);
    }
}

/**
 * 统一分页返回
 * @param $list
 * @return array
 */
if (!function_exists('pageReturn')) {

    function pageReturn($list) {
        if (0 == $list['code']) {
            return ['code' => 0, 'msg' => 'success', 'data' => [
                'total' => $list['data']->total(),
                'rows' => $list['data']->all()
            ]];
        }

        return ['code' => 0, 'msg' => 'success', [
            'total' => 0,
            'rows' => []
        ]];
    }
}

/**
 * 生成密码
 * @param $password
 * @param $salt
 * @return string
 */
function makePassword($password, $salt = '') {

    return sha1(md5(md5($password . $salt)));
}

/**
 * 设置jwt
 * @param $data
 * @return string
 */
function setJWT($data) {
    $jwt   = new Firebase\JWT\JWT();
    $token = [
        // "iss"  => "http://example.org", // 签发者
        // "aud"  => "http://example.com", // 认证者
        'iat'  => time(), // 签发时间
        'nbf'  => time(), // 生效时间
        'exp'  => (time() + 60 * 60 * 24 * 7), // 过期时间  7天后的时间戳
        'data' => $data
    ];

    return $jwt::encode($token, \config('shop.jwt_key'), 'HS256');
}

/**
 * 获取token中的信息
 * @param $token
 * @return array|null
 */
function getJWT($token) {
    $jwt  = new Firebase\JWT\JWT();
    $data = [];
    try {
        $jwtData = $jwt::decode($token, new Firebase\JWT\Key(\config('shop.jwt_key'), 'HS256'));
        $data     = (array) ($jwtData->data);
    } catch (\Exception $e) {
        \think\facade\Log::write($e->getMessage(), 'error');
        return null;
    }
    return $data;
}

/**
 * 从头部获取token
 * @return bool|string
 */
function getHeaderToken() {
    $header = request()->header();
    if (isset($header['authorization'])) {
        return substr($header['authorization'], 7);
    }

    return '';
}

/**
 * 生成子孙树
 * @param $data
 * @return array
 */
function makeTree($data) {

    $res = [];
    $tree = [];

    // 整理数组
    foreach ($data as $key => $vo) {
        $res[$vo['id']] = $vo;
    }
    unset($data);

    // 查询子孙
    foreach ($res as $key => $vo) {
        if ($vo['pid'] != 0) {
            $res[$vo['pid']]['children'][] = &$res[$key];
        }
    }

    // 去除杂质
    foreach ($res as $key => $vo) {
        if ($vo['pid'] == 0) {
            $tree[] = $vo;
        }
    }
    unset($res);

    return $tree;
}

function crossDomain() {

    header("access-control-allow-headers: Authorization, Content-Type, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, X-Requested-With");
    header("access-control-allow-methods: OPTIONS,GET, POST, PATCH, PUT, DELETE");
    header("access-control-allow-origin: *");
}

function now() {
    return date('Y-m-d H:i:s');
}

/**
 * 获取配置
 * @param $type
 * @return array
 */
function getConfByType($type) {
    try {

        $configModel = new SysSetting();
        $config = $configModel->getAllList(['type' => $type])['data'];
        $formatConfig = [];
        foreach ($config as $vo) {
            $formatConfig[$vo['key']] = $vo['value'];
        }

        return $formatConfig;
    } catch (\Exception $e) {
        return [];
    }
}

/**
 * 生成订单号
 * @param $business
 * @return string
 */
function makeOrderNo($business) {
    return $business . date('YmdHis') . GetNumberCode(6);
}

/**
 * 随机数生成生成
 * @param int $length
 * @return string
 */
function getNumberCode($length = 6) {
    $code = '';
    for ($i = 0; $i < intval($length); $i++) {
        $code .= rand(0, 9);
    }

    return $code;
}

/**
 * 生成随机验证码
 * @param $len
 * @return int
 */
function makeRandNumber($len) {
    $start = pow(10, $len - 1);
    $end = pow(10, $len) - 1;

    return rand($start, $end);
}

/**
 * 格式化短信数据
 * @param $param
 * @return array
 */
function formatSmsData($param) {
    $code = makeRandNumber(6);
    return [
        'accessKeyId' => $param['access_key_id'],
        'accessKeySecret' => $param['access_key_secret'],
        'signName' => $param['sign_name'],
        'templateCode' => $param['templateCode'],
        'phone' => $param['phone'],
        'code' => json_encode(['code' => $code])
    ];
}

/**
 * 删除空目录
 * @param string $path
 */
function removeEmptyDir(string $path) {
    if (!is_dir($path)) {
        return true;
    }
    $path_handle = opendir($path);
    readdir($path_handle);
    readdir($path_handle); // 读取目录两个自带的隐藏目录'.'和'..'

    if (!(bool)readdir($path_handle)) {
        @rmdir($path);
    }
}

/**
 * 获取真实路径
 * @param $path
 * @return array
 */
function getRealPath($path) {
    $delPathMap = [];
    foreach ($path as $vo) {
        $pathMap = explode('/', $vo);
        $pathMap = array_slice($pathMap,3);
        $delPathMap[] = implode('/', $pathMap);
    }

    return $delPathMap;
}

/**
 * 生成毫秒级时间戳
 * @return float
 */
function getMillisecond() {

    list($t1, $t2) = explode(' ', microtime());
    return (float)sprintf('%.0f',(floatval($t1) + floatval($t2)) * 1000);
}

/**
 * 生成唯一id
 * @return string
 */
function uuid() {
    return uniqid(md5( mt_rand()),true);
}

/**
 * post提交
 * @param $url
 * @param $data
 * @return bool|string
 */
function curlPost($url, $data)
{
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_HEADER, 0);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0); // 对认证证书来源的检查
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0); // 从证书中检查SSL加密算法是否存在
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    $output = curl_exec ($ch);
    curl_close ($ch);

    return json_decode($output, true);
}

/**
 * get 提交
 * @param $url
 * @param $data
 * @return mixed
 */
function curlGet($url, $data)
{
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url . '?' . http_build_query($data));
    curl_setopt($ch, CURLOPT_HEADER, 0);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);;
    $output = curl_exec($ch);
    curl_close($ch);

    return json_decode($output,true);
}

/**
 * 随机头像
 * @param $id
 * @return string|array
 */
function getRandAvtar($id = 0)
{
    $arr = [1, 2, 3];
    $api = getConfByType('api_url')['api_url'];
    $api= rtrim($api, '/api');

    if ($id == 0) {
        $num = $arr[array_rand($arr)];
        return $api . '/static/images/avatar-' . $num . '.png';
    }

    $avatar = [];
    foreach ($arr as $vo) {
        $avatar[] = $api . '/static/images/avatar-' . $vo . '.png';
    }

    return $avatar;
}

/**
 * 检测站点是否开启
 * @return bool
 */
function checkOpen()
{
    return getConfByType('sys_base')['web_open'] == 1;
}