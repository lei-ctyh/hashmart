<script>
	import $store from './store/index.js';
	import {
		get_address_code
	} from '@/api/address.js';
	export default {
		onLaunch: function() {
			console.log('App Launch')
			$store.dispatch('user/USER_CONFIG')
			//获取手机上面安全距离
			$store.dispatch('phone/SET_PHONE_NAME_ACT')
			//获取省市区地址
			let addressCodeList = uni.getStorageSync('_ADDRESS_CODE')
			if (addressCodeList.length == 0 || !addressCodeList) {
				get_address_code().then(res => {
					if (res.code == 0) {
						uni.setStorageSync('_ADDRESS_CODE', res.data)
					}
				}).catch(err => {})
			}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import "uview-ui/index.scss";

	// 全局样式
	:not(not) {
		box-sizing: border-box;
	}
</style>
