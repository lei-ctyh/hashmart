<template>
	<view class="app-layout" :style="{'position' : isShowLogin ? 'fixed' : ''}">
		<app-user-login v-if="isShowLogin" @closeLoginModal="closeLoginModal"></app-user-login>
		<slot></slot>
	</view>
</template>

<script>
	import {
		mapState,
		mapGetters
	} from 'vuex';
	import AppUserLogin from './app-user-login/app-user-login';

	export default {
		name: "app-layout",
		data() {
			return {
				isShowLogin: false
			};
		},
		components: {
			AppUserLogin
		},
		props: {},
		computed: {},
		watch: {},
		created() {
			if (!this.$user.isLogin()) {
				// uni.navigateTo({
				// 	url: '/plugins/login/phone-number/index'
				// })
				this.isShowLogin = true
			}
		},
		methods: {
			closeLoginModal() {
				this.isShowLogin = false
				uni.switchTab({
					url: '/pages/index/index'
				})
			}
		}
	}

</script>

<style scoped lang="scss">
</style>

