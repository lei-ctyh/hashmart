import {
	request
} from '@/utils/request.js'
//余额充值记录
export const balanceInfo = (data) => {
	return request({
		url: '/order/balance',
		data: data,
		method: 'GET'
	})
}
//取消订单
export const cancel = (data) => {
	return request({
		url: '/order/balance/cancel',
		data: data,
		method: 'POST'
	})
}
//重新支付
export const repay = (data) => {
	return request({
		url: '/order/balance/repay',
		data: data,
		method: 'POST'
	})
}

