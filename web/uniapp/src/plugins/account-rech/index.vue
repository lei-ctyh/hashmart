<template>
	<app-layout>
		<view class="container">
			<view class="head">
				<view class="text">
					<text class="text-name">账户余额（元）</text>
					<text class="text-num">{{banlan_money}}</text>
				</view>
			</view>
			<view class="content">
				<view class="content-title">
					<view class="span">

					</view>
					<view class="title">
						账户充值
					</view>
				</view>
				<view class="list">
					<view class="list-item" v-for="(item,index) in rech_list" :key="index"
						:class="{active:item.isSelect}" @click="choseItem(item.money,index)">
						<view class="money">
							{{item.money}}元
						</view>

						<view class="give" v-if="item.give!=0">
							赠送：{{item.give}}元
						</view>
					</view>
					<view class="list-item" @click="others">
						<view class="money" v-if="!isOther">
							其他
						</view>
						<view class="" v-if="isOther">
							<u--input placeholder="请输入金额" border="surround" v-model="money_num" type="number"
								@change="change"></u--input>
						</view>
					</view>
				</view>
				<view class="rech-type">
					<view class="content-title type-title">
						<view class="span">
						</view>
						<view class="title">
							充值方式
						</view>
					</view>

					<radio-group @change="payTypeChange" v-model="redioVal">
						<label class="pay-type-item main-space-between" v-for="(item, index) in payList" :key="index">
							<view class="main-start-flex">
								<u--image :showLoading="true" :src="item.pic" width="32rpx" height="32rpx">
								</u--image>
								<view class="pay-type-name">
									{{item.name}}
								</view>
							</view>
							<radio :value="item.pay_way" :checked="item.pay_way === currentPayType" color="#eb5c4a"
								style="transform:scale(0.8)" />
						</label>
					</radio-group>

				</view>
				<view class="rech" @click="rechNow">
					立即充值
				</view>
				<view class="info">
					<view class="info-title">
						充值说明
					</view>
					<view class="info-content">
						1、充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容
					</view>
					<view class="info-content">
						2、充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容充值说明内容
					</view>
				</view>
			</view>
			<modal ref="modal"></modal>
			<!--  -->
		</view>
	</app-layout>
</template>

<script>
	import { createOrder, balance_info } from '@/api/my.js'
	export default {
		data() {
			return {
				redioVal: '1',
				currentPayType: '1',
				payList: [
					// #ifdef APP-PLUS 
					{
						pic: 'https://cdn.kitego.cn/hashmart/pay/ali_pay.png',
						name: '支付宝',
						value: 'ali_pay',
						pay_way: '2'
					},
					// #endif
					{
						pic: 'https://cdn.kitego.cn/hashmart/pay/wx_pay.png',
						name: '微信',
						value: 'wx_pay',
						pay_way: '1'
					}
				],
				pay_way: '1',
				money_num: null,
				isOther: false,
				hash_money: 10,
				banlan_money: 0,
				amount: '10',
				rech_list: [{
						money: '10',
						give: '0',
						isSelect: true,
					},
					{
						money: '20',
						give: '0',
						isSelect: false,
					},
					{
						money: '50',
						give: '0',
						isSelect: false,
					},
					{
						money: '100',
						give: '0',
						isSelect: false,
					},
					{
						money: '200',
						give: '0',
						isSelect: false,
					},
					{
						money: '500',
						give: '0',
						isSelect: false,
					},
				]
			}
		},
		onLoad(params) {
			this.banlan_money = params.banlan_money
		},
		methods: {
			payTypeChange(e) {
				this.pay_way = e.detail.value
			},
			change() {
				this.amount = this.money_num
			},
			others() {
				this.isOther = true
				for (var i = 0; i < this.rech_list.length; i++) {
					this.rech_list[i].isSelect = false
				}
			},
			choseItem(val, index) {
				this.isOther = false
				this.amount = val
				for (var i = 0; i < this.rech_list.length; i++) {
					this.rech_list[i].isSelect = false
					if (i == index) {
						this.rech_list[i].isSelect = true

					}
				}
			},

			getBanlance() {
				balance_info().then(res => {
					if (res.code == 0) {
						this.banlan_money = res.data.balance
					}
				})
			},
			rechNow() {
				let platform = ''
				// #ifdef APP-PLUS
				platform = 'app'
				// #endif
				// #ifdef MP-WEIXIN
				platform = 'miniapp'
				// #endif
				createOrder({
					amount: this.amount,
					pay_way: this.pay_way,
					platform: platform,
				}).then(res => {
					console.log(res, '创建订单')

					// #ifdef MP
					uni.requestPayment({
						provider: 'wxpay', //支付类型-固定值
						timeStamp: res.timeStamp, // 时间戳（单位：秒）
						nonceStr: res.nonceStr, // 随机字符串
						package: res.package, // 固定值
						signType: res.signType, //固定值
						paySign: res.paySign, //签名
						success: function(res) {
							console.log(res, '支付结果')
							uni.showToast({
								title: '支付成功',
								icon: 'none'
							})
						},
						fail(res) {
							uni.showToast({
								title: '支付失败',
								icon: 'none'
							})
						}
					})
					// #endif
					// #ifdef APP-PLUS
					uni.getProvider({ //获取应用服务商
						service: 'payment',
						success: res => {
							if (res.providers.indexOf('wxpay')) { //先判断用户是否有微信支付环境（是否安装了微信app）
								workMission(params).then((res1) => {
									console.log('查看子订单信息', res1)
									//拿到需要进行微信支付的订单信息，必须严格按照微信支付API所需要的支付信息大小写字段进行编码						
									let payInfo = {
										appid: res.appId,
										noncestr: res.nonceStr,
										package: "Sign=WXPay",
										partnerid: res.partnerid,
										prepayid: res1.data.prepay_id,
										timestamp: res.timeStamp,
										sign: res.paySign,
									}
									console.log('生成订单', payInfo)
									// uni.requestPayment({
									// 	provider: 'wxpay',
									// 	orderInfo: payInfo, //微信订单数据（Object类型）
									// 	success: res => {
									// 		uni.showToast({
									// 			title: '支付成功',
									// 			icon: 'none'
									// 		})
									// 	},
									// 	fail: err => {
									// 		console.log('失败得原因', err)
									// 		uni.showToast({
									// 			title: '支付失败',
									// 			icon: 'none'
									// 		})
									// 	}
									// });
								})
							}
						}
					})
					// #endif
				})
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.head {
		width: 100%;
		height: 380rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		background: linear-gradient(to bottom, #4E4E51, #262628);
	}

	.text {
		width: 500rpx;
		text-align: center;
		color: #FFFFFF;
		display: flex;
		flex-direction: column;
	}

	.text-name {
		font-size: 24rpx;
	}

	.text-num {
		font-size: 80rpx;
	}

	.content {
		background: #fff;
		padding: 30rpx;
		width: 100%;
		border-radius: 50rpx 50rpx 0 0;
		position: relative;
		top: -50rpx;
	}

	.content-title {
		display: flex;
	}

	.span {
		width: 9rpx;
		height: 42rpx;
		background: #8D01E6;
	}

	.title {
		color: #333333;
		font-size: 32rpx;
		height: 42rpx;
		line-height: 42rpx;
		border-left: 4rpx solid #8D01E6;
		width: 160rpx;
		text-align: center;
		margin-left: 4rpx;
		margin-bottom: 20rpx;
	}

	.list {
		margin-top: 40rpx;
		display: flex;
		flex-wrap: wrap;


	}


	.list-item {
		background: #F4F4F4;
		border-radius: 25rpx;
		width: 214rpx;
		height: 120rpx;
		text-align: center;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin: 8rpx;


	}

	.active {
		background: #333;
		color: #FFFFFF;
	}

	.money {
		font-size: 34rpx;
		color: #999;
	}

	.give {
		font-size: 24rpx;
		color: #999999;
	}

	.rech-type {
		margin-top: 20rpx;
	}

	.type-title {
		margin-bottom: 20rpx;
	}

	.rech {
		font-size: 28rpx;
		color: #FFFFFF;
		width: 690rpx;
		height: 84rpx;
		line-height: 84rpx;
		text-align: center;
		margin: 50rpx 0;
		background: #333;
	}

	.info-title {
		color: #333;
		font-size: 28rpx;
	}

	.info-content {
		color: #999;
		font-size: 24rpx;
	}

	.pay-type-item {
		height: 80rpx;
	}

	.pay-type-name {
		margin-left: 10rpx;
		font-size: 28rpx;
	}

</style>

