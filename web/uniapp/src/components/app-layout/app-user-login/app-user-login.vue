<template>
	<view class="container main-center-flex">
		<view class="login-modal main-center-flex"
			:style="{'background-image': `url(https://cdn.kitego.cn/uploads/mall1/20221202/85010779fbef88eca469e0d4f36eb6ac.png)`}">
			<view class="button-container main-center-flex">
				<view class="login-botton" @click="loginCancel"></view>

				<!-- <view class="login-botton" @click="login" v-if="!getNum"></view> -->
				<!-- #ifdef  MP-WEIXIN -->
				<button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber"
					class="login-botton btn">立即登录</button>
				<!-- #endif -->
			</view>
		</view>
	</view>
</template>


<script>
	import Vue from "vue";
	import {
		mapState
	} from "vuex";

	import { getPhone, mini_wx_login } from '@/api/login.js'

	export default {
		name: "app-user-login",

		data() {
			return {
				getNum: true,
				show: false,
				code: null,
			};
		},
		computed: {

		},
		methods: {
			decryptPhoneNumber(e) {
				console.log(e, 'e')
				const storageKey = '_USER_ACCESS_TOKEN';
				const self = this
				uni.login({
					scopes: 'auth_base',
					success(result) {
						console.log(result, 'result')
						if (result.errMsg === 'login:ok') {
							self.code = result.code;
							mini_wx_login({
								code: result.code,
								phone_code: e.detail.code
							}).then(response => {
								console.log(response, 'response')
								if (response.code === 0) {
									uni.setStorageSync(
										storageKey, response
										.data.token);
									self.$store.dispatch('user/USER_CONFIG')
									self.loginCancel()
									uni.showToast({
										icon: 'none',
										title: '登录成功'
									})
									uni.navigateTo({
										url: '/pages/index/index'
									})
								} else {
									uni.showToast({
										icon: 'none',
										title: response
											.msg
									})

								}
							})
						}
					}
				})
			},
			login(phoneNumber) {
				// #ifdef MP-WEIXIN
				this.$login.mpWeixinlogin(phoneNumber).then(res => {
					if (res) {
						this.$store.dispatch('user/USER_CONFIG')
						this.loginCancel()
						uni.showToast({
							icon: 'none',
							title: '登录成功'
						})
					}
				}).catch(err => {})
				// #endif
				// #ifdef APP-PLUS
				this.$login.appLogin()
				// uni.navigateTo({
				// 	url: '/plugins/login/phone-number/index?type=' + 'phone'
				// })
				// #endif
			},
			loginCancel() {
				this.$emit('closeLoginModal', false)
			}
		},
	};

</script>

<style scoped>
	.container {
		position: fixed;
		width: 750rpx;
		height: 100vh;
		top: 0;
		left: 0;
		background: rgba(0, 0, 0, 0.8);
		z-index: 9999;
	}

	.login-modal {
		width: 650rpx;
		height: 700rpx;
		background-size: 100% 100%;
		position: relative;
	}

	.button-container {
		position: absolute;
		bottom: 132rpx;
		width: 100%;
	}

	.button-container :first-child {
		margin-right: 56rpx;
	}

	.login-botton {
		width: 200rpx;
		height: 80rpx;
		border-radius: 40rpx;
	}

	.btn {
		text-align: center;
		font-size: 28rpx;
		line-height: 80rpx;
		color: #fff;
		background: #333;
	}

</style>

