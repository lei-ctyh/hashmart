import {
	request
} from '@/utils/request.js'
//分类
export const get_shop_cate = (data) => {
	return request({
		url: '/goods/shop/cateList',
		data: data,
		method: 'GET'
	})
}
//商品
export const get_goods_list = (data) => {
	return request({
		url: '/goods/shop/goodsList',
		data: data,
		method: 'GET'
	})
}
//商品详情
export const get_market_detail = (data) => {
	return request({
		url: '/goods/shop/goodsInfo',
		data: data,
		method: 'GET'
	})
}

//分类详情
export const get_cate_detail = (data) => {
	return request({
		url: '/goods/shop/cateDetail',
		data: data,
		method: 'GET'
	})
}
//积分商城幻灯
export const slider = (data) => {
	return request({
		url: '/goods/shop/slider',
		data: data,
		method: 'GET'
	})
}

