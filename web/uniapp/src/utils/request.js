import Vue from 'vue';
import baseUrl from './siteInfo.js';
import qs from 'qs';
import login from './login.js'
import store from 'vuex'
export const request = (args) => {
	const header = {
		// 'X-Form-Id-List': JSON.stringify(popAll()),
		'X-Form-Id-List': [],

		'X-Requested-With': (args.header && args.header['X-Requested-With']) ? args.header[
			'X-Requested-With'] : 'XMLHttpRequest',
		'content-type': args.header && args.header.contentType ? args.header['content-type'] :
			'application/x-www-form-urlencoded',
	};
	let accessToken = uni.getStorageSync('_USER_ACCESS_TOKEN');
	if (accessToken) {
		header['authorization'] = 'Bearer ' + accessToken;
	}
	return new Promise((resolve, reject) => {
		uni.request({
			url: baseUrl.apiroot + args.url,
			method: args.method,
			data: args.method == 'POST' ? qs.stringify(args.data) : args.data,
			// data: args.data,
			header: header,
			success: (res) => {
				if (res.data.code == 401) {
					// #ifdef MP-WEIXIN
					uni.navigateTo({
						url: '/plugins/login/phone-number/index?type=' + 'phone'
					})
					// #endif
					// #ifdef APP-PLUS
					login.appLogin()
					// #endif
				}
				console.log(JSON.stringify(res))
				resolve(res.data)

			},
			fail: (error) => {
				reject(error)
			}
		})
	})
}

