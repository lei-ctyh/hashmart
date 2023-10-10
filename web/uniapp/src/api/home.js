import {
	request
} from '@/utils/request.js'
//获取转卖列表
export const get_home_list = (data) => {
	return request({
		url: '/home/index',
		data: data,
		method: 'GET'
	})
}
