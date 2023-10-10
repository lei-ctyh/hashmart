const storageKey = '_USER_ACCESS_TOKEN';

const isLogin = () => {
	let accessToken = uni.getStorageSync(storageKey);
	return accessToken
}

export default {
	isLogin
}
