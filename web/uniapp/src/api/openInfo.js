import {
	request
} from '@/utils/request.js'
//开盒记录
export const orderRecordLog = (data) => {
	return request({
		url: '/user/orderRecordLog',
		data: data,
		method: 'GET'
	})
}

