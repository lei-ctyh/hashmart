import {
	request
} from '@/utils/request.js'
//商品详情页
export const get_blind_detail = (data) => {
	return request({
		url: '/goods/blindboxDetail',
		data: data,
		method: 'GET'
	})
}
//根据订单号查询中奖结果
export const order_result = (data) => {
	return request({
		url: '/order/result',
		data: data,
		method: 'POST'
	})
}
//系列商品详情
export const goodsInfo = (data) => {
	return request({
		url: '/goods/blindbox/goodsInfo',
		data: data,
		method: 'GET'
	})
}
//获取用户信息
export const getInfo = (data) => {
	return request({
		url: '/user/getInfo',
		data: data,
		method: 'POST'
	})
}
//更换种子
export const updatehash = (data) => {
	return request({
		url: '/user/updatehash',
		data: data,
		method: 'POST'
	})
}
//获取背景音乐
export const getMusic = (data) => {
	return request({
		url: '/common/getMusic',
		data: data,
		method: 'GET'
	})
}

