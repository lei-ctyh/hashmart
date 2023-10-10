import {
	request
} from '@/utils/request.js';
//获取用户信息
export const get_user_info = (data) => {
	return request({
		url: '/user/getInfo',
		data: data,
		method: 'POST'
	})
}
//获取用户信息
export const getAvatar = (data) => {
	return request({
		url: '/common/getAvatar',
		data: data,
		method: 'GET'
	})
}
//修改用户信息
export const set_user_info = (data) => {
	return request({
		url: '/user/setInfo',
		data: data,
		method: 'POST'
	})
}
//用户余额基础信息
export const balance_info = (data) => {
	return request({
		url: '/user/balance/info',
		data: data,
		method: 'GET'
	})
}
//用户余额基础信息
export const balance_list = (data) => {
	return request({
		url: '/user/balance/list',
		data: data,
		method: 'GET'
	})
}
//用户余额充值订单
export const createOrder = (data) => {
	return request({
		url: '/user/balance/createOrder',
		data: data,
		method: 'POST'
	})
}
//获取客服二维码
export const getKeFuCode = (data) => {
	return request({
		url: '/common/getKeFuCode',
		data: data,
		method: 'GET'
	})
}

