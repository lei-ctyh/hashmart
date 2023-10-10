import {
	request
} from '@/utils/request.js';
//盲盒列表
export const get_bag_box_list = (data) => {
	return request({
		url: '/order/getBagBoxList',
		data: data,
		method: 'GET'
	})
}
//商品列表
export const get_bag_goods_list = (data) => {
	return request({
		url: '/order/getBagGoodsList',
		data: data,
		method: 'GET'
	})
}
//盲盒兑换
export const bagBoxExchange = (data) => {
	return request({
		url: '/order/bagBoxExchange',
		data: data,
		method: 'POST'
	})
}

//盲盒提货试算邮费
export const bagBoxTrail = (data) => {
	return request({
		url: '/order/bagBoxTrail',
		data: data,
		method: 'POST'
	})
}
//盲盒提货创建订单
// export const bagBoxCreateOrder = (data) => {
// 	return request({
// 		url: '/order/bagBoxCreateOrder',
// 		data: data,
// 		method: 'POST'
// 	})
// }
//盲盒提货创建订单
export const bagBoxDeliver = (data) => {
	return request({
		url: '/order/bagBoxDeliver',
		data: data,
		method: 'POST'
	})
}
//盲盒提货支付
// export const bagBoxDeliver = (data) => {
// 	return request({
// 		url: '/order/bagBoxDeliver',
// 		data: data,
// 		method: 'POST'
// 	})
// }
//盲盒奖品确认收货
export const confirmBoxDeliver = (data) => {
	return request({
		url: '/order/confirmBoxDeliver',
		data: data,
		method: 'POST'
	})
}
//积分商场奖品确认收货
export const bagGoodsConfirm = (data) => {
	return request({
		url: '/order/bagGoodsConfirm',
		data: data,
		method: 'POST'
	})
}
//盲盒提货物流
export const getExpress = (data) => {
	return request({
		url: '/order/express',
		data: data,
		method: 'GET'
	})
}
//商品提货物流
export const getShopExpress = (data) => {
	return request({
		url: '/order/shop/express',
		data: data,
		method: 'GET'
	})
}

