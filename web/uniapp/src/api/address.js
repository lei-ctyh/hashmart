import {
	request
} from '@/utils/request.js'
//收货地址列表
export const address_list = (data) => {
	return request({
		url: '/user/address/list',
		data: data,
		method: 'GET'
	})
}
//获取省市区地址
export const get_address_code = (data) => {
	return request({
		url: '/user/address/option',
		data: data,
		method: 'GET'
	})
}
//添加收货地址
export const add_address = (data) => {
	return request({
		url: '/user/address/add',
		data: data,
		method: 'POST'
	})
}
//编辑收货地址
export const edit_address = (data) => {
	return request({
		url: '/user/address/edit',
		data: data,
		method: 'POST'
	})
}
//设置默认地址
export const set_default_address = (data) => {
	return request({
		url: '/user/address/setDefault',
		data: data,
		method: 'POST'
	})
}

//删除地址
export const delete_address = (data) => {
	return request({
		url: '/user/address/delete',
		data: data,
		method: 'POST'
	})
}
