import {
	request
} from '@/utils/request.js';
//获取订单信息
export const blindbox = (data) => {
	return request({
		url: '/order/blindbox',
		data: data,
		method: 'GET'
	})
}
//取消订单
export const cancel = (data) => {
	return request({
		url: '/order/blindbox/cancel',
		data: data,
		method: 'POST'
	})
}
//获取订单信息新
export const getList = (data) => {
	return request({
		url: '/user/order/list',
		data: data,
		method: 'GET'
	})
}
//重新支付
export const repay = (data) => {
	return request({
		url: '/order/blindbox/repay',
		data: data,
		method: 'POST'
	})
}

