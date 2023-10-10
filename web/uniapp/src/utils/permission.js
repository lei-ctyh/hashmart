import Vue from 'vue';
import $store from '../store/index.js';
//只要是未登录状态，想要跳转到名单内的路径时，直接跳到登录页
// 页面白名单，不受拦截
const whiteListRequest = [
	'api/passport/login',
	'api/user/config',
	'plugin/market_place/api/setting/config'
]
const whiteListUrl = [
	'/pages/my/my',
	'/pages/index/index',
]
const hasPermission = (url) => {
	// 在白名单中或有登录判断条件可以直接跳转
	let accessToken = uni.getStorageSync('_USER_ACCESS_TOKEN');
	let api = url.split('&r=')[1]
	let isHaveWhite = whiteListRequest.some(item => {
		return api === item
	})
	if (isHaveWhite || accessToken) {
		return true
	}

	return false
}
uni.addInterceptor('request', {
	// 页面跳转前进行拦截, invoke根据返回值进行判断是否继续执行跳转
	invoke(e) {
		// hasPermission(e.url)
		// if (!hasPermission(e.url)) {
		// 	uni.reLaunch({
		// 		url: '/pages/user-center/index'
		// 	})
		// 	return false
		// }
		// return true
	},
	success(e) {
		if (e.data.code == -1) {
			$store.commit('user/LOGIN_MODAL_MUT', true)
		}
	}
})
uni.addInterceptor('navigateTo', {
	// 页面跳转前进行拦截, invoke根据返回值进行判断是否继续执行跳转
	invoke(e) {},
	success(e) {
		$store.commit('user/LOGIN_MODAL_CLOSE_MUT');
	}
})
