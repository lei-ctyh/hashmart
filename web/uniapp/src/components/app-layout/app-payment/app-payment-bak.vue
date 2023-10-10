<!-- 全局支付组件 -->
<template>
	<view class="app-payment main-center cross-center" :class="showPayment?'show':''">
		<view class="modal" v-if="payData">
			<view class="title">
				<view>支付方式</view>
				<view class="cancel" @click="cancel">
					<image src="/static/image/icon/close.png"></image>
				</view>
			</view>
			<view class="pay-amount">支付金额 {{payData.amount}} 元</view>
			<view class="pay-type-list">
				<view v-for="(item, index) in payData.list" @click="checkPayType(index)" :key="index"
					class="pay-type-item cross-center" v-if="item.key == 'alipay_h5'">
					<view class="box-grow-0">
						<image class="pay-type-icon" :src="item.icon"></image>
					</view>
					<view class="box-grow-1">
						<view class="pay-type-name">{{item.name}}</view>
						<view class="pay-type-desc" v-if="item.desc">{{item.desc}}</view>
					</view>
					<view class="box-grow-0">
						<view v-if="item.key === 'balance' && item.desc === '账户余额不足'" style="height: 26rpx;width:113rpx"
							@click.prevent.stop="navPay">
							<image style="height: 100%;width:100%" load-lazy="true"
								src="https://cdn.kitego.cn/show/static/pay-td.png">
							</image>
						</view>
						<app-radio v-else-if="item.checked" :theme="getTheme" v-model="item.checked" type="round">
						</app-radio>
					</view>
					<view class="into-modal main-center cross-center" v-if="printPassword && item.key === 'balance'">
						<view class="password-tip" v-if="item.is_pay_password == 0 && !setPassword">
							<view class="password-content dir-top-nowrap main-center cross-center">
								<view>您的账户尚未设置余额支付密码</view>
								<view>是否立即设置？</view>
							</view>
							<view class="password-btn main-center cross-center">
								<view @click="payByBalance">暂不设置</view>
								<view class="line"></view>
								<view @click="setPassword = !setPassword;password=''" :style="{'color':getTheme.color}">
									确认</view>
							</view>
						</view>
						<view class="password-view" v-else-if="printPassword">
							<image class="password-close"
								@click="printPassword = false;setPassword = false;verifyPassword = false;"
								src="/static/image/icon/icon-close.png"></image>
							<view class="password-title">请{{verifyPassword ? '确认':'输入'}}余额支付密码</view>
							<!-- #ifdef MP-ALIPAY -->
							<input type="number" :class="!haveFocus ? 'input' :''" @focus="haveFocus=true"
								@input="passwordInput" @blur="haveFocus=false;getFocus=false" v-model="password">
							<!-- #endif -->
							<!-- #ifdef MP-WEIXIN -->
							<input type="number" style="top:0;right: 400%" @input="passwordInput" :focus="getFocus"
								@blur="getFocus=false" v-model="password">
							<!-- #endif -->
							<!-- #ifndef MP-ALIPAY || MP-WEIXIN -->
							<input type="number" v-show="getFocus" style="top:0;right: 400%" @input="passwordInput"
								:focus="getFocus" @blur="getFocus=false" v-model="password">
							<!-- #endif -->
							<view @click="getInputFocus" class="passoword-input main-center cross-center">
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 0" class="password-placeholder"></view>
								</view>
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 1" class="password-placeholder"></view>
								</view>
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 2" class="password-placeholder"></view>
								</view>
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 3" class="password-placeholder"></view>
								</view>
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 4" class="password-placeholder"></view>
								</view>
								<view class="password-item main-center cross-center">
									<view v-if="password.length > 5" class="password-placeholder"></view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="link-pay">
				{{payData.list[1].pay_url }}&id={{play_id}}

			</view>
			<view class="link-pay-text">
				请复制链接到浏览器粘贴，即可跳转支付宝支付界面
			</view>
			<view class="footer">
				<!--  #ifdef MP -->
				<!-- <app-button v-if="payName == 'wechat'" type="important" :theme="getTheme" @click="confirm" round>提交订单
				</app-button> -->
				<view class="ali-pay" v-if="payName == 'alipay_h5'">
					<view class="aliPay-button" @tap="saveText">复制链接</view>
					<view class="have-pay" @tap="havePay">我已支付</view>
				</view>
				<!--  #endif-->
				<!--  #ifdef H5 -->
				<app-button type="important" :theme="getTheme" @click.native.stop="confirm" round>提交订单</app-button>
				<!--  #endif-->
			</view>
		</view>
	</view>
</template>

<script>
	import Vue from 'vue';
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import AppRadio from '@/components/basic-component/app-radio/app-radio';

	export default {
		name: 'app-payment',
		components: {
			AppRadio
		},
		computed: {
			...mapState({
				mall: state => state.mallConfig.mall,
				userInfo: state => state.user.info,
				showPayment: function(state) {
					return state.payment.showPayment;
				},
				payData: state => state.payment.payData,
				payType: state => state.payment.payType,
				play_id: state => state.payment.id
			}),
			...mapGetters('mallConfig', {
				getTheme: 'getTheme',
			}),
		},
		data() {
			return {
				is_need_pay_password: 0,
				haveFocus: false,
				getFocus: false,
				printPassword: false,
				setPassword: false,
				verifyPassword: false,
				password: '',
				verify_pay_password: '',
				pay_password: '',
				payPassword: '',
				payName: 'alipay_h5',
				hasCopy: false
			}
		},
		created() {
			this.setPayment();
		},
		methods: {
			havePay() {
				if (!this.hasCopy) {
					uni.showToast({
						title: "请复制链接支付",
						icon: "none"
					});
					return
				}
				console.log('111', this.play_id)
				this.$request({
					url: this.$api.recharge.balance_detail,
					data: {
						id: this.play_id,
					},
					method: "get",
				}).then(res => {
					if (res.data.info.is_pay) {
						uni.showToast({
							title: '已支付',
							icon: "none"
						});
						this.$store.commit('payment/showPayment', false);
						this.payName = 'alipay_h5'
						this.hasCopy = false
					} else {
						uni.showToast({
							title: '未支付',
							icon: "none"
						});
					}

				}).catch(err => {
					console.log('2103', err)
				})

			},
			saveText() {
				console.log('this.payData.list[1].pay_url', this.payData.list[1].pay_url)
				this.$utils.uniCopy({
					data: this.payData.list[1].pay_url + '&id=' + this.play_id,
					success: function() {
						uni.showToast({
							title: "复制成功",
							icon: "none"
						});
					}
				});
				this.hasCopy = true
			},
			getInputFocus() {
				this.$nextTick(() => {
					this.getFocus = true;
				})
			},
			passwordInput() {
				if (this.password.length == 6) {
					setTimeout(() => {
						if (this.setPassword) {
							this.setPayPassword();
						} else {
							uni.showLoading({
								mask: true
							});
							uni.hideKeyboard();
							this.verifyPayPassword();
						}
					})
				}
			},
			setPayPassword() {
				if (this.password.length < 6) {
					return false;
				}
				if (!this.verifyPassword) {
					this.pay_password = this.password.toString().substring(0, 6);
					this.verifyPassword = true;
					this.password = '';
				} else {
					this.verify_pay_password = this.password.toString().substring(0, 6);;
					if (this.pay_password === this.verify_pay_password) {
						uni.showLoading({
							mask: true
						});
						let data = {
							pay_password: this.pay_password,
							verify_pay_password: this.verify_pay_password,
						}
						this.$request({
							url: this.$api.member.set_password,
							method: "post",
							data: data
						}).then(response => {
							uni.hideLoading();
							if (response.code === 0) {
								this.payPassword = this.pay_password;
								this.printPassword = false;
								this.setPassword = false;
								this.verifyPassword = false;
								this.password = '';
								this.pay_password = '';
								this.verify_pay_password = '';
								this.$store.commit('payment/showPayment', false);
								this.payName = 'alipay_h5'
								this.payByBalance();
							} else {
								this.password = '';
								this.pay_password = '';
								this.verify_pay_password = '';
								uni.showToast({
									icon: 'none',
									title: response.msg
								});
							}
						});
					} else {
						this.verifyPassword = false;
						this.password = '';
						this.pay_password = '';
						this.verify_pay_password = '';
						uni.showToast({
							icon: 'none',
							title: '两次输入的密码不一致'
						})
					}
				}
			},
			navPay() {
				this.$store.commit('payment/showPayment', false);
				this.payName = 'alipay_h5'
				this.$store.state.payment.reject({
					errMsg: '5b03b6e009796c698d132908cb635fca',
				});
				uni.navigateTo({
					url: "/pages/balance/recharge"
				});
			},
			setPayment() {
				const vm = this;
				Vue.use({
					install(Vue, options) {
						Vue.prototype.$payment = {
							pay: vm.pay,
						};
					},
				});
			},
			pay(id) {
				return new Promise((resolve, reject) => {
					this.$store.commit('payment/setAll', {
						showPayment: false,
						payData: null,
						payType: null,
						id: id,
						resolve: resolve,
						reject: reject,
					});
					console.log('debug payment, setAll ok, id:', this.$store.state.payment.id);
					console.log('debug payment, setAll ok, resolve:', this.$store.state.payment.resolve);
					console.log('debug payment, setAll ok, reject:', this.$store.state.payment.reject);
					uni.showLoading({
						mask: true,
						title: '请求支付...',
					});
					this.$request({
						url: this.$api.payment.get_payments,
						data: {
							id: id,
						}
					}).then(response => {
						uni.hideLoading();
						console.log('debug 1--->', response);
						if (response.code === 0) {
							console.log('debug payment, set resolve 2,', this.$store.state.payment
								.resolve);
							return this.showPaymentModal(response.data);
						} else {
							response.errMsg = response.msg || '';
							return this.$store.state.payment.reject(response.msg);
						}
					}).catch(e => {
						uni.hideLoading();
						e.errMsg = e.msg || '';
						return this.$store.state.payment.reject(e);
					});
				});
			},
			showPaymentModal(data) {
				console.log('debug 2--->', data);
				for (let i in data.list) {
					if (typeof data.list[i].checked === 'undefined') {
						data.list[i].checked = false;
					}
				}
				this.$store.commit('payment/payData', data);
				if (data.amount === 0 || data.amount === 0.00 || data.amount === '0' || data.amount === '0.00') {
					this.$store.commit('payment/payType', 'balance');
					for (let i in this.$store.state.payment.payData.list) {
						if (this.$store.state.payment.payData.list[i].key === 'balance') {
							this.$store.state.payment.payData.list[i].checked = true;
						} else {
							this.$store.state.payment.payData.list[i].checked = false;
						}
					}
					this.confirm();
					return;
				}
				this.$store.commit('payment/showPayment', true);
			},
			confirm() {
				console.log('payment confirm 1:');
				console.log('debug payment, confirm 1,', this.$store.state.payment.resolve);
				for (let i in this.$store.state.payment.payData.list) {
					if (this.$store.state.payment.payData.list[i].checked) {
						this.$store.commit('payment/payType', this.$store.state.payment.payData.list[i].key);
					}
				}
				if (!this.$store.state.payment.payType) {
					uni.showModal({
						title: '提示',
						content: '请选择支付方式',
						showCancel: false,
					});
					return;
				}
				console.log('1523', this.$store.state.payment.payType)
				if (this.$store.state.payment.payType == 'wechat') {
					uni.showToast({
						icon: 'none',
						title: '暂不支持微信支付'
					})
					return
				}
				this.$store.commit('payment/showPayment', false);
				this.payName = 'alipay_h5'
				console.log('payment confirm 2:', this.$store.state.payment.payType);
				console.log('debug payment, confirm 2,', this.$store.state.payment.resolve);
				return this.getPayData();
			},
			cancel() {
				if (this.$store.state.payment.payType == 'alipay_h5' && this.hasCopy) {
					this.havePay()
					return
				}
				this.hasCopy = false
				this.$store.commit('payment/showPayment', false);
				this.payName = 'alipay_h5'
				return this.$store.state.payment.reject({
					errMsg: '支付取消',
				});
			},
			checkPayType(index) {
				if (this.$store.state.payment.payData.list[index].disabled || this.$store.state.payment.payData.list[index]
					.checked) {
					return false;
				}
				const payData = this.$store.state.payment.payData;
				for (let i in payData.list) {
					if (i == index) {
						payData.list[i].checked = true;
						this.payName = payData.list[i].key
						console.log('payName', this.payName)
					} else {
						payData.list[i].checked = false;
					}
				}
				this.$store.commit('payment/payData', payData);
			},
			getPayData() {
				console.log('debug payment, getPayData 1,', this.$store.state.payment.resolve);
				uni.showLoading({
					mask: true,
					title: '请求支付...',
				});
				let _this = this;
				let data = {
					id: this.$store.state.payment.id,
					pay_type: this.$store.state.payment.payType,
				}
				// #ifdef H5
				this.$storage.setStorageSync('WEB_URL', window.location.href + '&pay_id_weChart=' + data.id +
					'&isWechat=true');
				if (window.location.hash.indexOf('/pages/balance/recharge') > -1) {
					data.url = window.location.href.split('#')[0] + '#/pages/balance/recharge?isPay=ture';
				} else {
					if (window.location.hash.indexOf('?') > -1) {
						data.url = window.location.href + '&isPay=ture'
					} else {
						data.url = window.location.href + '?isPay=ture'
					}
				}
				data.url += `&isWechat=true&payType=${this.$store.state.payment.payType}`
				if (!this.$jwx.isWechat()) {
					data.url += '&pay_id_weChart=' + data.id
				}
				// #endif
				this.$request({
					url: this.$api.payment.pay_data,
					data: data
				}).then(response => {
					uni.hideLoading();
					if (response.code === 0) {
						switch (this.$store.state.payment.payType) {
							case 'balance':
								this.callBranch(response.data);
								break;
							case 'huodao':
								this.callHuodao(response.data);
								break;
								// #ifdef H5
							case 'wechat_h5':
								console.log('debug payment, wechat_h5');
								this.$jwx.chooseWXPay({
									timestamp: response.data.timeStamp,
									nonceStr: response.data.nonceStr,
									packAge: response.data.package,
									signType: response.data.signType,
									paySign: response.data.paySign,
									webUrl: response.data.mweb_url,
									success() {
										_this.$store.state.payment.resolve({
											errMsg: '支付成功',
										});
									},
									fail(res) {
										_this.$store.state.payment.reject({
											errMsg: res.msg
										});
									}
								});
								uni.showModal({
									content: '确定已完成支付？',
									confirmText: '确定',
									cancelText: '返回支付',
									success(res) {
										if (res.confirm) {
											_this.weChartPay(_this.$store.state.payment.id);
										} else if (res.cancel) {
											_this.$store.state.payment.reject({
												errMsg: '支付取消'
											});
										}
									}
								});
								break;
							case 'alipay_h5':
								if (this.$jwx.isWechat()) {
									console.log('1754', response.data.url)
									_AP.pay(response.data.url);
								} else {
									window.location.href = response.data.url;
									uni.showModal({
										content: '确定已完成支付？',
										confirmText: '确定',
										cancelText: '返回支付',
										success(res) {
											if (res.confirm) {
												_this.weChartPay(_this.$store.state.payment.id);
												// _this.$store.state.payment.resolve({
												//     errMsg: '支付成功',
												// });
											} else if (res.cancel) {
												_this.$store.state.payment.reject({
													errMsg: '支付取消'
												});
											}
										},
										fail() {}
									});
								}
								console.log('debug payment, alipay_h5');
								break;
								// #endif
							default:
								// #ifdef MP
								console.log('debug payment, getPayData 2,', this.$store.state.payment.resolve);
								this.callPlatformPayment(response.data);
								// #endif
								break;
						}
					} else {
						return this.$store.state.payment.reject(response.msg);
					}
				}).catch(e => {
					uni.hideLoading();
					e.errMsg = e.msg || '';
					return this.$store.state.payment.reject(e);
				});
			},
			callBranch(data) {
				let that = this;
				if (data.order_amount === 0 || data.order_amount === 0.00 || data.order_amount === '0' || data
					.order_amount === '0.00') {
					this.payByBalance();
				} else {
					uni.showModal({
						title: '余额支付确认',
						content: `账户余额：${data.balance_amount}，支付金额：${data.order_amount}`,
						success: (e) => {
							if (e.confirm) {
								for (let item of this.payData.list) {
									if (item.key == 'balance') {
										if (item.is_open_pay_password == 1) {
											this.payPassword = '';
											this.is_need_pay_password = item.is_pay_password;
											this.password = '';
											this.$store.commit('payment/showPayment', true);
											this.printPassword = true;
											setTimeout(() => {
												this.getFocus = true;
											}, 800)
										} else {
											this.payByBalance();
										}
										break
									}
								}
							} else {
								return this.$store.state.payment.reject({
									errMsg: '支付取消.',
								});
							}
						}
					});
				}
			},
			verifyPayPassword() {
				if (this.password.length < 6) {
					return false;
				}
				this.payPassword = this.password.toString().substring(0, 6);
				this.$request({
					url: this.$api.member.verify_password,
					data: {
						pay_password: this.payPassword,
					},
					method: 'post'
				}).then(response => {
					this.password = '';
					uni.hideLoading();
					if (response.code === 0) {
						this.$store.commit('payment/showPayment', false);
						this.payName = 'alipay_h5'
						this.payByBalance();
					} else {
						this.password = '';
						this.payPassword = '';
						uni.showModal({
							title: '提示',
							content: response.msg,
							showCancel: false
						});
					}
				}).catch(e => {
					uni.hideLoading();
					e.errMsg = e.msg || '';
					return this.$store.state.payment.reject(e);
				});
			},
			payByBalance() {
				uni.showLoading({
					mask: true,
					title: '支付中...',
				});
				let para = {
					id: this.$store.state.payment.id,
					pay_password: this.payPassword ? this.payPassword : '',
					is_need_pay_password: this.is_need_pay_password
				}
				this.$request({
					url: this.$api.payment.pay_buy_balance,
					data: para
				}).then(response => {
					uni.hideLoading();
					if (response.code === 0) {
						this.$store.commit('payment/showPayment', false);
						this.payName = 'alipay_h5'
						return this.$store.state.payment.resolve({
							errMsg: '支付成功',
						});
					} else {
						return this.$store.state.payment.reject({
							errMsg: response.msg,
						});
					}
				}).catch(e => {
					e.errMsg = e.msg || '';
					return this.$store.state.payment.reject(e);
				});
			},
			callHuodao() {
				uni.showLoading({
					mask: true,
					title: '提交中...',
				});
				this.$request({
					url: this.$api.payment.pay_buy_huodao,
					data: {
						id: this.$store.state.payment.id,
					},
				}).then(response => {
					uni.hideLoading();
					if (response.code === 0) {
						return this.$store.state.payment.resolve({
							errMsg: '支付成功',
						});
					} else {
						return this.$store.state.payment.reject({
							errMsg: response.msg,
						});
					}
				}).catch(e => {
					uni.hideLoading();
					e.errMsg = e.msg || '';
					return this.$store.state.payment.reject(e);
				});
			},
			// #ifdef MP
			callPlatformPayment(data) {
				console.log('debug payment, callPlatformPayment 1,', this.$store.state.payment.resolve);
				let paymentProvider = null;
				// #ifdef MP-WEIXIN
				paymentProvider = ['wxpay'];
				// #endif
				// #ifdef MP-ALIPAY
				paymentProvider = ['alipay'];
				// #endif
				// #ifdef MP-BAIDU
				paymentProvider = ['baidu'];
				// #endif
				// #ifdef MP-TOUTIAO
				paymentProvider = ['toutiao'];
				// #endif
				uni.requestPayment({
					provider: paymentProvider,
					success: (e) => {
						console.log('debug payment, callPlatformPayment 3,', this.$store.state.payment
							.resolve);
						console.log('success:', e);
						// #ifndef MP-ALIPAY
						return this.$store.state.payment.resolve(e);
						// #endif
						// #ifdef MP-ALIPAY
						if (e.resultCode === 9000 || e.resultCode === '9000') {
							return this.$store.state.payment.resolve(e);
						} else {
							return this.$store.state.payment.reject({
								errMsg: e.memo,
							});
						}
						// #endif
					},
					fail: (e) => {
						const cancelMsgList = [
							'requestPayment:fail cancel',
						];
						if (e.errMsg && cancelMsgList.indexOf(e.errMsg) >= 0) {
							e.errMsg = '取消支付';
						}
						console.log('debug payment, callPlatformPayment 4,', this.$store.state.payment
							.resolve);
						console.log('fail:', e);
						return this.$store.state.payment.reject(e);
					},
					...data
				});
			},
			// #endif
			// #ifdef H5
			alipayH5Pay() {},
			weChartPay(id) {
				this.$request({
					url: this.$api.registered.pay,
					method: 'get',
					data: {
						payment_order_union_id: id
					}
				}).then((res) => {
					if (res.code === 0) {
						if (res.data.status === 1) {
							this.$store.state.payment.resolve({
								errMsg: '支付成功',
							});
							uni.redirectTo({
								url: `/pages/order-submit/pay-result?payment_order_union_id=${id}`,
							});
						} else {
							uni.redirectTo({
								url: '/pages/order/index/index'
							});
						}
					}
				})
			}
			// #endif
		},
	}
</script>

<style scoped lang="scss">
	$bigPadding: #{50rpx};
	$smallPadding: #{25rpx};
	$middlePadding: #{30rpx};
	$smallFont: #{24rpx};
	$lineWidth: #{1rpx};
	$modalWidth: #{600rpx};
	$iconWidth: #{60rpx};

	.link-pay {
		padding: 15rpx;
		overflow: hidden;
		width: 90%;
		margin: 20rpx auto;
		border: 2rpx solid;
		border-radius: 20rpx;
		text-align: center;
	}

	.link-pay-text {
		width: 100%;
		// height: 60rpx;
		font-size: 20rpx;
		text-align: center;
	}

	.ali-pay {
		width: 100%;
		height: 40px;
		display: flex;
		justify-content: space-between;
		align-items: center;

		.aliPay-button,
		.have-pay {
			width: 45%;
			height: 100%;
			font-size: 16px;
			border-radius: 50px;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		.aliPay-button {
			background-color: #FF0000;
			color: #ffffff;
		}

		.have-pay {
			background-color: #ffffff;
			color: #FF0000;
			border: 1rpx solid #FF0000;
		}
	}

	.app-payment {
		background: rgba(0, 0, 0, .5);
		position: fixed;
		z-index: 2000;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		visibility: hidden;
		opacity: 0;
		transition: 150ms;

		.modal {
			background: #fff;
			width: $modalWidth;
			border-radius: #{15rpx};

			.title {
				text-align: center;
				padding: $middlePadding;
				border-bottom: $lineWidth solid #e2e2e2;
				position: relative;
			}

			.cancel {
				position: absolute;
				right: 0;
				top: 0;
				padding: $middlePadding;

				image {
					width: #{36rpx};
					height: #{36rpx};
				}
			}

			.pay-amount {
				text-align: center;
				padding: $bigPadding;
				font-weight: bolder;
			}

			.pay-type-list {
				padding: 0 $bigPadding;
			}

			.pay-type-item {
				border-bottom: $lineWidth solid #e2e2e2;
				padding: $smallPadding 0;

				.pay-type-icon {
					width: $iconWidth;
					height: $iconWidth;
					margin-right: $smallPadding;
				}

				.pay-type-desc {
					color: #909090;
					font-size: $smallFont;
				}
			}

			.pay-type-item:last-child {
				border-bottom: none;
			}

			.footer {
				padding: $bigPadding;
			}
		}
	}

	.app-payment.show {
		visibility: visible;
		opacity: 1;
	}

	.into-modal {
		background: rgba(0, 0, 0, .5);
		position: fixed;
		z-index: 2100;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		opacity: 1;
		transition: 150ms;

		.password-tip {
			width: #{630rpx};
			height: #{340rpx};
			position: relative;
			border-radius: #{16rpx};
			background-color: #fff;

			.password-content {
				height: #{240rpx};
				width: 100%;
				color: #353535;
			}

			.password-btn {
				position: absolute;
				bottom: 0;
				left: 0;
				width: 100%;
				color: #666666;
				height: #{88rpx};
				border-top: #{2rpx} solid #e2e2e2;

				>view {
					width: 50%;
					text-align: center;
					height: #{88rpx};
					line-height: #{88rpx};
				}

				.line {
					width: #{2rpx};
					height: #{32rpx};
					background-color: #e2e2e2;
				}
			}
		}

		.password-view {
			position: relative;
			width: #{560rpx};
			height: #{300rpx};
			border-radius: #{16rpx};
			background-color: #fff;
			margin-top: #{-200rpx};

			.password-close {
				position: absolute;
				top: #{29rpx};
				right: #{28rpx};
				width: #{30rpx};
				height: #{30rpx};
				z-index: 2101;
			}

			.password-title {
				height: #{140rpx};
				line-height: #{140rpx};
				text-align: center;
				margin-bottom: #{128rpx};
			}

			.password-button {
				padding: 0 #{60rpx};
				position: relative;
				z-index: 2101;
			}

			input {
				position: absolute;
				top: -300%;
				width: #{475rpx};
				height: #{78rpx};
				margin: 0 auto;
				z-index: 9999;
			}

			.input {
				top: #{144rpx};
				height: #{70rpx};
				left: #{42.5rpx};
				color: #fff;
				font-size: #{1rpx};
				background-color: transparent;
				opacity: 0;
			}

			.passoword-input {
				position: absolute;
				background-color: #fff;
				top: #{140rpx};
				left: 0;
				width: 100%;
				z-index: 2101;

				.password-item {
					border: #{2rpx} solid #e2e2e2;
					margin-left: #{-2rpx};
					height: #{78rpx};
					width: #{78rpx};

					.password-placeholder {
						width: #{24rpx};
						height: #{24rpx};
						border-radius: 50%;
						background-color: #353535;
					}
				}
			}
		}
	}
</style>
