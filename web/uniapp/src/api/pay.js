import {
	request
} from '@/utils/request.js'
//盲盒订单试算
export const order_trail = (data) => {
	return request({
		url: '/order/trail',
		data: data,
		method: 'POST'
	})
}
//盲盒创建订单
export const create_order = (data) => {
	return request({
		url: '/order/createOrder',
		data: data,
		method: 'POST'
	})
}
//盲盒支付订单
export const pay_order = (data) => {
	return request({
		url: '/order/payOrder',
		data: data,
		method: 'POST'
	})
}


//商品订单试算
export const order_shop_trail = (data) => {
	return request({
		url: '/order/shop/trail',
		data: data,
		method: 'POST'
	})
}
//商品创建订单
export const create_shop_order = (data) => {
	return request({
		url: '/order/shop/createOrder',
		data: data,
		method: 'POST'
	})
}

//商品支付订单
export const pay_shop_order = (data) => {
	return request({
		url: '/order/shop/payOrder',
		data: data,
		method: 'POST'
	})
}

