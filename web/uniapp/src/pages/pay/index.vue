<template>
	<view class="container" :style="{'padding-top': phoneHieght}">
		<u-navbar title="订单确认" :autoBack="true" :bgColor="'rgba(0,0,0,0)'">
		</u-navbar>
		<template v-if="payInfo">
			<view class="head">
				<view class="head-content">
					<view class="head-left">
						<view class="">
							<!-- #ifdef APP-PLUS -->
							<img src="../../static/image/icon/map.png" alt="" class="img-icon">
							<!-- #endif -->
							<!-- #ifdef MP-WEIXIN -->
							<image src="../../static/image/icon/map.png" mode="" class="img-icon"></image>
							<!-- #endif -->

						</view>
						<view class="head-title" v-if="default_address.receiver">
							<view class="head-name">
								{{default_address.receiver}} <text class="number">{{default_address.phone}}</text>
							</view>
							<view class="number">
								{{default_address.province_name}}{{default_address.city_name}}{{default_address.area_name}}{{default_address.address}}
							</view>
						</view>
						<view class="head-title" v-else>
							<view class="head-name">
								请先添加地址
							</view>
						</view>
					</view>

					<view class="">
						<!-- #ifdef APP-PLUS -->
						<img src="../../static/image/icon/right.png" alt="" class="img-icon" @click="choseAddress">
						<!-- #endif -->
						<!-- #ifdef MP-WEIXIN -->
						<image src="../../static/image/icon/right.png" mode="" class="img-icon" @click="choseAddress">
						</image>
						<!-- #endif -->

					</view>
				</view>
			</view>
			<view class="pay-info">
				<view class="pay-info_1 main-start-flex">
					<!-- #ifdef APP-PLUS -->
					<img :src="payParms.type == 'box' ? payInfo.blindbox_img.split(',')[0] : payInfo.goods_image.split(',')[0]"
						alt="" class="img-goods">
					<!-- #endif -->
					<!-- #ifdef MP-WEIXIN -->
					<image
						:src="payParms.type == 'box' ? payInfo.blindbox_img.split(',')[0] : payInfo.goods_image.split(',')[0]"
						mode="" class="img-goods"></image>
					<!-- #endif -->
					<view class="pay-info-detail_1">
						<view class="main-space-between">
							<view class="pay-info-name text-ellipsis">
								{{payInfo.goods_name ? payInfo.goods_name : ''}}
							</view>
							<view class="pay-info-price">
								<text>￥</text>
								{{payInfo.price}}
							</view>
						</view>
					</view>
				</view>
				<view class="pay-info-item main-space-between">
					<text>数量</text>
					<text>x{{payInfo.num}}</text>
				</view>
				<u-line color="rgba(0,0,0,0.1)"></u-line>
				<view class="pay-info-item main-space-between">
					<text>合计</text>
					<view class="pay-info-item-price">

						{{payInfo.total_price}} <text class="text-color">哈希币</text>
					</view>

				</view>
			</view>
			<view class="pay-type">
				<view class="pay-type-head main-start-flex">
					支付方式
				</view>
				<radio-group @change="payTypeChange">
					<label class="pay-type-item main-space-between" v-for="(item, index) in payList" :key="index">
						<view class="main-start-flex">
							<!-- #ifdef APP-PLUS -->
							<img :src="item.pic" alt="" class="icon-img">
							<!-- #endif -->
							<!-- #ifdef MP-WEIXIN -->
							<image :src="item.pic" mode="" class="icon-img"></image>
							<!-- #endif -->
							<view class="pay-type-name">{{item.name}}</view>
						</view>
						<radio :value="item.pay_way" :checked="item.pay_way === currentPayType" color="#eb5c4a"
							style="transform:scale(0.8)" />
					</label>
				</radio-group>
			</view>
			<view class="pay-remark">
				若完成交易代表您已同意以下约定：<br />
				1、商品的实时价格会因市场波动而产生变化，具体成交价以平台为准。<br />
				2、由于显示器，拍照和做图的过程中，产品可能发生颜色偏差，具体请以实物为准！<br />
			</view>
			<view class="pay-confirm column-align-end-flex">
				<view class="pay-adult main-start-flex">

					<!-- #ifdef MP-WEIXIN -->
					<image src="../../static/image/icon/chose.png" mode="" class="icon-img" v-if="isAdult"
						@click="isAdult = !isAdult"></image>
					<!-- #endif -->
					<!-- #ifdef APP-PLUS -->
					<img src="../../static/image/icon/chose.png" alt="" class="icon-img" v-if="isAdult"
						@click="isAdult = !isAdult">
					<!-- #endif -->
					<view class="icon-img chose-icon" v-if="!isAdult" @click="isAdult = !isAdult">

					</view>
					<view class="">
						我已满18岁，已阅读并同意 <text class="user-rule">《用户协议》</text>
					</view>
				</view>
				<view class="pay-detail main-start-flex">
					<view class="pay-detail-left">
						<view class="">
							合计:￥<text>{{payInfo.total_price}}</text>
						</view>
						<view class="main-end-flex">
							共{{payInfo.num}}件
						</view>
					</view>
					<view class="pay-button main-center-flex" @click="pay">
						确认支付
					</view>
				</view>
			</view>
		</template>
		<modal ref="modal"></modal>
	</view>
</template>

<script>
	import {
		address_list
	} from '@/api/address.js'
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		order_shop_trail,
		create_shop_order,
		pay_shop_order
	} from '@/api/pay.js';
	import {
		get_user_info
	} from '@/api/my.js'
	export default {
		data() {
			return {
				default_address: null,
				payInfo: null,
				payParms: null,
				isAdult: false
			}
		},
		computed: {
			...mapGetters('phone', {
				phoneHieght: 'phoneHieght',
			}),
			currentPayType: {
				get() {
					return this.payList[0].pay_way
				},
				set(v) {}
			},
			payList() {
				let data = [
					// #ifdef APP-PLUS
					{
						pic: require(
							'../../static/image/pay/ali_png.png'),
						name: '支付宝',
						value: 'ali_pay',
						pay_way: '2'
					},
					// #endif
					{
						pic: require(
							'../../static/image/pay/wx_pay.png'),
						name: '微信',
						value: 'wx_pay',
						pay_way: '1'
					},
					{
						pic: require(
							'../../static/image/icon/banlance_pay.png'),
						name: '哈希币',
						value: 'integral_pay',
						pay_way: '3'
					}, {
						pic: require(
							'../../static/image/icon/banlance_pay.png'),
						name: '账户余额',
						value: 'balance_pay',
						pay_way: '4'
					}
				]
				if (this.payParms && this.payParms.type == 'box') {
					return data
				} else {
					return data.filter(item => {
						return item.pay_way == 3
					})
				}
			}
		},
		onShow() {
			this.getAddress()
		},
		onLoad(parms) {
			this.payParms = parms
			if (parms.type == 'box') {
				this.getBoxPayInfo(parms)
			} else {
				this.getAddress()
				this.getGoodsPayInfo(parms)

			}
		},
		methods: {
			choseAddress() {
				uni.navigateTo({
					url: '/plugins/address/index'
				})
			},
			getAddress() {
				address_list().then(res => {
					if (res.code == 0) {
						for (var i = 0; i < res.data.length; i++) {
							if (res.data[i].default_flag == '1') {
								this.default_address = res.data[i]
							}
						}
					}
				})

			},
			getBoxPayInfo({
				blindbox_id,
				num
			}) {
				uni.showLoading({
					title: '加载中',
					mask: true
				})
				order_trail({
					blindbox_id,
					num
				}).then(res => {
					if (res.code == 0) {
						this.payInfo = res.data
						uni.hideLoading()
					} else {

						uni.$u.toast(res.msg)


					}
				}).catch(err => {
					uni.hideLoading()
				})
			},
			getGoodsPayInfo({
				goods_id,
				sku,
				num
			}) {
				uni.showLoading({
					title: '加载中',
					mask: true
				})
				order_shop_trail({
					goods_id,
					sku,
					num,
					address_id: 11,
				}).then(res => {
					if (res.code == 0) {
						this.payInfo = res.data
						uni.hideLoading()
					} else {
						uni.$u.toast(res.msg)
						setTimeout(() => {
							uni.navigateBack({
								delta: 1, //返回层数，2则上上页
							})
						}, 800)

					}
				}).catch(err => {
					uni.hideLoading()
				})
			},
			payTypeChange(e) {
				this.currentPayType = e.detail.value
			},
			pay() {
				const self = this
				if (!self.isAdult) {
					return uni.$u.toast('请同意用户协议')
				}
				if (!self.default_address) {
					return uni.$u.toast('请选择地址')
				}
				get_user_info().then(res => {
					if (res.code == 0) {
						let reaminPrice = self.payParms.type == 'box' ? res.data.balance : res.data.integral
						if (self.payInfo.total_price > reaminPrice) {
							uni.$u.toast('哈希币不足')
						} else {
							self.$refs.modal.showModal({
								title: '支付确认',
								content: `总价为${self.payInfo.total_price}`,
								confirm: () => {
									let data = self.payParms


									uni.showLoading({
										title: '支付中',
										mask: true
									})
									create_shop_order({
										...data,
										pay_way: '4',
										address_id: self.default_address.id,
									}).then(res => {
										if (res.code == 0) {
											let platform = ''
											// #ifdef APP-PLUS
											platform = 'app'
											// #endif
											// #ifdef MP-WEIXIN
											platform = 'miniapp'
											// #endif

											pay_shop_order({
                        pay_order_no: res.data.pay_order_no,
												platform
											}).then(res_order => {
												uni.hideLoading()
												console.log(res_order, '商品支付结果')
												if (res.code == 0) {
													uni.showToast({
														title: '支付成功',
														icon: 'none'
													})
													self.$store
														.commit(
															'param/setType',
															'goods'
														)
													self.$store
														.commit(
															'param/setStatus',
															'2'
														)
													uni.reLaunch({
														url: '/pages/warehouse/index',
														success: function(
															res) {
															console.log(
																res)

															getApp()
																.globalData
																.paramsData = {
																	type: 'goods',
																	status: '2',
																	page: 1
																};
														}
													})
												} else {
													uni.showToast({
														title: res.msg,
														icon: 'none'
													})
												}

											}).catch(err_order => {
												uni.$u.toast(err_order.msg)
											})
										} else {
											uni.$u.toast(res.msg)
										}
									}).catch(err => {
										uni.$u.toast(err.msg)
									})
								}
							})
						}
					}
				}).catch(err => {})
			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		padding: 30rpx;
		padding-bottom: calc(220rpx + env(safe-area-inset-bottom));
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.head {
		margin-bottom: 30rpx;
	}

	.head-content {
		background: #fff;
		display: flex;
		align-items: center;
		text-align: center;
		justify-content: space-between;
		padding: 0 20rpx;
		height: 150rpx;

	}

	.head-left {
		display: flex;
		align-items: center;
	}

	.head-title {
		text-align: left;
	}

	.head-name {
		font-size: 28rpx;
		font-weight: 600;
	}

	.number {
		font-weight: 500;
		font-size: 24rpx;
	}

	.pay-head {
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		background: #D88D54;
		text-align: center;
		padding: 0 30rpx;
		font-size: 40rpx;
		font-family: BTH;
		font-weight: 400;
		color: #FFFFFF;
		margin-top: 30rpx;
	}

	.pay-info {
		background: #FFFFFF;
		padding: 30rpx;
		padding-bottom: 10rpx;
	}

	.pay-info_1 {
		width: 100%;
		height: 156rpx;
	}

	.pay-info-detail_1 {
		width: calc(100% - 156rpx);
		padding-left: 22rpx;
	}

	.pay-info-detail_1>view:first-child {
		width: 100%;
	}

	.pay-info-name {
		width: 70%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.pay-info-price {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.pay-info-price text {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.pay-info-tip {
		width: max-content;
		padding: 0 10rpx;
		height: 32rpx;
		background: #D76474;
		font-size: 26rpx;
		font-family: BTH;
		font-weight: 400;
		color: #FFFFFF;
		margin-top: 12rpx;
	}

	.pay-info-item,
	.pay-type-item {
		width: 100%;
		height: 80rpx;
	}

	.pay-info-item>text:first-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.pay-info-item>text:last-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.pay-info-item-price {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #EC872E;
	}

	.pay-info-item-price text {
		font-size: 34rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #EC872E;
	}

	.pay-type {
		width: 100%;
		background: #FFFFFF;
		padding: 30rpx 30rpx 10rpx 30rpx;
		margin-top: 30rpx;
	}

	.pay-type-head {
		width: 100%;
		font-size: 30rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.pay-type-name {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		margin-left: 10rpx;
		line-height: 8rpx;
	}

	.pay-remark {
		margin-top: 30rpx;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}


	.pay-confirm {
		width: 100%;
		height: calc(190rpx + env(safe-area-inset-bottom));
		padding: 20rpx;
		padding-bottom: env(safe-area-inset-bottom);
		background: #FFFFFF;
		box-shadow: 1rpx -4rpx 16rpx 0 rgba(30, 30, 30, 0.15);
		position: fixed;
		bottom: 0;
		left: 0;
	}

	.pay-adult {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.pay-adult text {
		color: #333333;
	}

	.pay-detail {
		margin-top: 34rpx;
	}

	.pay-detail-left {
		height: 80rpx;
	}

	.pay-detail-left>view:first-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.pay-detail-left>view:first-child text {
		font-size: 42rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.pay-detail-left>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.pay-button {
		width: 210rpx;
		height: 80rpx;
		background: #3F3F42;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
		margin-left: 28rpx;
		margin-top: 10rpx;
	}

	.user-rule {
		color: #8D01E6 !important;
	}

	.img-icon {
		width: 56rpx;
		height: 64rpx;
		margin-right: 10rpx;
	}

	.img-goods {
		width: 156rpx;
		height: 156rpx;
	}

	.icon-img {
		width: 32rpx;
		height: 32rpx;
	}

	.chose-icon {
		border-radius: 50%;
		border: 2rpx solid #999;
	}

</style>

