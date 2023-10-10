import {
	request
} from '@/utils/request.js';
//获取用户协议
export const userAgreement = (data) => {
	return request({
		url: '/common/userAgreement',
		data: data,
		method: 'GET'
	})
}

