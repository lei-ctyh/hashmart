<template>
	<view class="container column-start-flex" :style="{'padding-top': phoneHieght}">
		<u-navbar :fixed="true" bgColor="rgba(0,0,0,0)" @leftClick="back"></u-navbar>
		<view class="" v-show="!weixin">
			<view class="head">
				<view class="head-title_1">
					{{pageType == 'password' ? '密码登录' : pageType == 'retrieve' ? '找回密码' : '手机号快捷登录' }}
				</view>
				<view v-if="pageType == 'phone'" class="head-title_2">未注册过的手机号将自动创建账号</view>
			</view>
			<view class="body">
				<u--form labelPosition="left" :model="loginModel" :rules="rules" ref="form">
					<u-form-item prop="phone" borderBottom>
						<view class="form-item main-start-flex">
							<u--image src="https://cdn.kitego.cn/hashmart/login/phone.png" width="34rpx" height="34rpx">
							</u--image>
							<u--input v-model="loginModel.phone" border="none" placeholder="请输入手机号"></u--input>
						</view>
					</u-form-item>
					<u-form-item prop="code" borderBottom>
						<view class="form-item main-start-flex">
							<u--image src="https://cdn.kitego.cn/hashmart/login/code.png" width="34rpx" height="34rpx">
							</u--image>
							<u--input v-model="loginModel.code" border="none" placeholder="请输入验证码"></u--input>
							<view class="get-code main-center-flex">
								<text v-if="!isGetCode" @click="getCode">获取验证码</text>
								<text v-else>{{codeTime}}s</text>
							</view>
						</view>
					</u-form-item>
					<u-form-item prop="password" borderBottom v-if="pageType != 'phone'">
						<view class="form-item main-start-flex">
							<u--image src="https://cdn.kitego.cn/hashmart/login/password.png" width="34rpx"
								height="34rpx">
							</u--image>
							<u--input v-model="loginModel.password" border="none" placeholder="请输入密码"
								password></u--input>
						</view>
					</u-form-item>
					<u-form-item prop="checked" borderBottom v-if="pageType != 'retrieve'">
						<view class="form-item main-start-flex">
							<u-checkbox-group placement="column" @change="loginModel.checked = !loginModel.checked">
								<u-checkbox shape="circle" :checked="loginModel.checked"></u-checkbox>
							</u-checkbox-group>
							<view class="agreement">
								我已阅读并同意哈希玛特<text>《用户协议》</text>
							</view>
						</view>
					</u-form-item>
				</u--form>
			</view>
			<view class="login-button main-center-flex" @click="login">
				{{pageType == 'password' ? '立即登录' : pageType == 'retrieve' ? '提交并确认' : '立即登录/注册' }}
			</view>
			<view class="password-login">
				<view @click="changeLoginPage('password')" v-if="pageType == 'phone'">
					使用密码登录
				</view>
				<view class="main-center-flex" style="position: relative;" v-if="pageType == 'password'">
					<view class="" @click="questLogin">快捷登录</view>
					<view class="line"></view>
					<view class="" style="margin-left: 80rpx;" @click="changeLoginPage('retrieve')">忘记密码</view>
				</view>
			</view>
			<!-- #ifdef MP-WEIXIN -->
			<view class="other-login column-center-flex" v-if="pageType != 'retrieve'">
				<view class="">
					其他方式登录
				</view>
				<view class="main-center-flex">
					<view class="login-image" v-for="(item,index) in 1" :key="index">
						<u--image src="https://cdn.kitego.cn/hashmart/login/login-wx.png" width="64rpx" height="64rpx"
							@click="weixin=!weixin">
						</u--image>

						<!-- 	<button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber"
						class="login-botton">
						<u--image src="https://cdn.kitego.cn/hashmart/login/login-wx.png" width="64rpx" height="64rpx">
						</u--image>
					</button> -->

					</view>
				</view>
			</view>
			<!-- #endif -->
		</view>
		<!-- #ifdef  MP-WEIXIN -->
		<view class="weixin" v-show="weixin">
			<view class="head-icon icon-img">
				<image src="../../../static/image/logo.png" mode="" class="icon-img"></image>
			</view>
			<view class="rules">
				<u-checkbox-group placement="column" @change="checkedChange(item)">
					<u-checkbox shape="circle" :checked="checked" activeColor="#EB5C4A"></u-checkbox>
				</u-checkbox-group>
				<view class="">
					阅读并同意哈希玛特 <text @click="goToRule('0')">《用户协议》</text>、<text @click="goToRule('1')">《隐私政策》</text>
				</view>

			</view>
			<view class="wlogin">
				<view class="others-login  login-botton" v-if="!checked" @click="clickInfo">
					微信一键登录
				</view>
				<button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber"
					class="login-botton others-login" v-if="checked">
					微信一键登录
				</button>
				<view class="others-login" @click="weixin=!weixin">
					其他方式登录
				</view>
			</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		get_login_code,
		login_by_sms,
		login_by_account,
		login_forget,
		mini_wx_login,
		getPhone
	} from '@/api/login.js';
	export default {
		data() {
			return {
				checked: false,
				// #ifdef  MP-WEIXIN
				weixin: true,
				// #endif
				// #ifdef APP-PLUS
				weixin: false,
				// #endif
				pageType: 'phone',
				loginModel: {
					phone: '',
					code: '',
					checked: true,
					password: ''
				},
				rules: {
					phone: [{
							type: 'number',
							required: true,
							message: '请填写手机号',
							trigger: ['blur', 'change']
						},
						{
							// 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
							validator: (rule, value, callback) => {
								// 调用uView自带的js验证规则，详见：https://www.uviewui.com/js/test.html
								return uni.$u.test.mobile(value);
							},
							message: "请填写正确的手机号",
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ["change", "blur"],
						}
					],
					code: [{
							type: 'number',
							required: true,
							message: '请填写验证码',
							trigger: ['blur', 'change']
						},
						{
							// 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
							validator: (rule, value, callback) => {
								// 调用uView自带的js验证规则，详见：https://www.uviewui.com/js/test.html
								return uni.$u.test.code(value, 6);
							},
							message: "请填写正确的验证码",
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ["change", "blur"],
						}
					],
					password: {
						type: 'string',
						required: true,
						message: '请填写密码',
						trigger: ['blur', 'change']
					},
					checked: [{
							type: 'boolean',
							required: true,
							message: '请同意协议',
							trigger: ['change']
						},
						{
							// 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
							validator: (rule, value, callback) => {
								// 调用uView自带的js验证规则，详见：https://www.uviewui.com/js/test.html
								return value
							},
							message: "请同意协议",
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ["change"],
						}
					]
				},
				isGetCode: false,
				codeTime: 60,
				codeInterval: null
			}
		},
		computed: {
			...mapGetters('phone', {
				phoneHieght: 'phoneHieght',
			}),
		},
		onReady() {
			// 如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则
			this.$refs.form.setRules(this.rules)
		},
		onLoad(parms) {
			this.pageType = parms.type
		},
		methods: {
			goToRule(val) {
				if (val == 0) {
					uni.navigateTo({
						url: '/plugins/user-agreement/index'
					})
				} else {
					uni.navigateTo({
						url: '/plugins/privacy-agreement/index'
					})
				}
			},
			clickInfo() {
				uni.$u.toast('请阅读并勾选上方协议')
			},
			checkedChange(item) {
				this.checked = !this.checked
			},
			decryptPhoneNumber(e) {
				const storageKey = '_USER_ACCESS_TOKEN';
				const self = this
				uni.login({
					scopes: 'auth_base',
					success(result) {
						if (result.errMsg === 'login:ok') {
							self.code = result.code;
							mini_wx_login({
								code: result.code,
								phone_code: e.detail.code
							}).then(response => {
								if (response.code === 0) {
									uni.setStorageSync(
										storageKey, response
										.data.token);
									self.$store.dispatch('user/USER_CONFIG')
									uni.switchTab({
										url: '/pages/index/index'
									})
									uni.showToast({
										icon: 'none',
										title: '登录成功'
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
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			login() {
				this.$refs.form.validate().then(res => {
					let {
						phone,
						code,
						password
					} = this.loginModel
					let loginMethod = this.pageType == 'phone' ? login_by_sms : this.pageType == 'password' ?
						login_by_account : login_forget
					loginMethod({
						phone,
						code,
						password
					}).then(res => {
						if (res.code == 0) {
							uni.setStorageSync('_USER_ACCESS_TOKEN', res.data.token);
							uni.switchTab({
								url: '/pages/my/index'
							})
						}
						uni.$u.toast(res.msg)
					}).catch(err => {
						uni.$u.toast(err.msg)
					})
				}).catch(errors => {})
			},
			changeLoginPage(type) {
				this.loginModel = {
					phone: '',
					code: '',
					checked: true,
					password: ''
				}

				uni.navigateTo({
					url: '/plugins/login/phone-number/index?type=' + type
				})
			},
			questLogin() {
				uni.navigateTo({
					url: '/plugins/login/phone-number/index'
				})
			},
			getCode() {
				console.log('发送验证码')
				clearInterval(this.codeInterval)
				this.$refs.form.validateField('phone')
				get_login_code({
					phone: this.loginModel.phone,
					type: 'login_sms_code'
				}).then(res => {
					console.log('发送验证码', res)
					uni.$u.toast(res.msg)
					if (res.code == 0) {
						this.isGetCode = true
						this.codeInterval = setInterval(() => {
							if (this.codeTime > 0) {
								this.codeTime--
							} else {
								clearInterval(this.codeInterval)
								this.isGetCode = false
							}
						}, 1000)
					}
				}).catch(err => {
					uni.$u.toast(err.msg)
				})

			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
		position: relative;
	}

	.head {
		width: 100%;
		padding-left: 50rpx;
		margin-top: 80rpx;
	}

	.head-title_1 {
		font-size: 42rpx;
		font-family: PingFangSC;
		font-weight: 500;
		color: #333333;
	}

	.head-title_2 {
		font-size: 24rpx;
		font-family: PingFang SC;
		font-weight: 500;
		color: #999999;
		margin-top: 30rpx;
	}

	::v-deep .u-form {
		margin-top: 100rpx;
	}

	.form-item {
		width: 640rpx;
		height: 100rpx;
	}

	::v-deep .u-form-item__body {
		padding: 0 !important;
	}

	::v-deep input {
		padding-left: 30rpx;
	}

	.get-code {
		height: 100%;
		font-size: 28rpx;
		font-family: PingFang SC;
		font-weight: 400;
		color: #6565E7;
	}

	::v-deep .u-checkbox-group {
		width: 10%;
	}

	.agreement {
		font-size: 24rpx;
		font-family: PingFang SC;
		font-weight: 400;
		color: #999999;
	}

	.agreement text {
		color: #6565E7;
	}

	.login-button {
		width: 590rpx;
		height: 92rpx;
		background: #4F4F52;
		font-size: 34rpx;
		font-family: PingFang SC;
		font-weight: 400;
		color: #FFFFFF;
		margin-top: 100rpx;
	}

	.password-login {
		font-size: 26rpx;
		font-family: PingFang SC;
		font-weight: 400;
		color: #999999;
		margin-top: 28rpx;
	}

	.other-login {
		position: absolute;
		bottom: calc(30rpx + env(safe-area-inset-bottom));
		font-size: 24rpx;
		font-family: PingFang SC;
		font-weight: 400;
		color: #999999;
		width: 100%;
		left: 0;
		right: 0;
		margin: auto;
	}

	.other-login>view:last-child {
		margin-top: 30rpx;
	}

	.login-image {
		margin: 0 5rpx;
	}

	.line {
		height: 100%;
		width: 2rpx;
		background: #E1DFDF;
		position: absolute;
	}

	.login-botton {
		background-color: none;
	}

	.icon-img {
		width: 354rpx;
		height: 78rpx;
	}

	.weixin {
		padding: 50rpx;

	}

	.head-icon {
		left: 0;
		right: 0;
		margin: auto;
		margin-top: 30%;
		margin-bottom: 20%;
	}

	.rules {
		color: #999;
		font-size: 24rpx;
		text-align: center;
		display: flex;
	}

	.wlogin {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.login-botton {
		margin-bottom: 50rpx;
		background: #333 !important;
		color: #fff !important;
		margin-top: 10%;


	}

	.others-login {
		font-size: 34rpx;
		width: 590rpx;
		height: 92rpx;
		background: #fff;
		text-align: center;
		line-height: 92rpx;
		border-radius: 5px;
	}

</style>

