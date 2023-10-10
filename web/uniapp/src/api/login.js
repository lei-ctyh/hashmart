import {
	request
} from '@/utils/request.js'
//小程序登录
export const mini_wx_login = (data) => {
	return request({
		url: '/user/login/loginByWechat',
		data: data,
		method: 'POST'
	})
}
//获取手机验证码
export const get_login_code = (data) => {
	return request({
		url: '/common/sendSms',
		data: data,
		method: 'POST'
	})
}
//获取短信登录
export const login_by_sms = (data) => {
	return request({
		url: '/user/login/loginBySms',
		data: data,
		method: 'POST'
	})
}
//账号密码登录
export const login_by_account = (data) => {
	return request({
		url: '/user/login/loginByAccount',
		data: data,
		method: 'POST'
	})
}
//忘记密码
export const login_forget = (data) => {
	return request({
		url: '/user/login/forget',
		data: data,
		method: 'POST'
	})
}
//app手机号一键登录
export const login_by_phone = (data) => {
	return request({
		url: '/user/login/loginByPhone',
		data: data,
		method: 'POST'
	})
}
//小程序登录获取手机号
export const getPhone = (data) => {
	return request({
		url: '/user/getPhone',
		data: data,
		method: 'POST'
	})
}
//获取uniapp配置
export const getUniapp = (data) => {
	return request({
		url: '/user/getUniapp',
		data: data,
		method: 'GET'
	})
}

