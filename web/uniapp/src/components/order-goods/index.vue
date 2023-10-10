<template>
	<view class="container">
		<view class="record-item" v-for="(item,index) in goods_list" :key="index">

			<view class="record-time main-start-flex">
				订单号：{{item.order_no||item.deliver_no}}
			</view>
			<!-- <view class="record-time main-start-flex">
				购买时间：{{item.deliver_time}}
			</view> -->
			<!-- <view class="record-time main-start-flex">
				价格：{{item.pay_price}}
			</view> -->
			<view class="status-box">
				{{type==1?'待付款':type==2?'待发货':'待收货'}}
			</view>
			<view class="" v-if="type==1">
				<view class="record-kinds-series main-start-flex">
					<view class="record-kinds-image main-center-flex">
						<u--image :showLoading="true" :src="item.blindbox.pic" width="156rpx"
							height="156rpx"></u--image>
					</view>
					<view class="record-series-info">
						<view class="text-ellipsis_2">
							{{item.blindbox.name}}
						</view>
					</view>
				</view>
			</view>
			<view class="" v-if="type!=1">
				<view class="" v-for="(item_1,index_1) in item.detail" :key="index_1">
					<view class="record-kinds-series main-start-flex">
						<view class="record-kinds-image main-center-flex">
							<u--image :showLoading="true" :src="item_1.rewardSimple.goods_image" width="156rpx"
								height="156rpx"></u--image>
						</view>
						<view class="record-series-info">
							<view class="text-ellipsis_2">
								{{item_1.rewardSimple.goods_name}}
							</view>
						</view>
					</view>

				</view>
			</view>

			<view class="open-again">
				<view class="main-start-flex again-flex">
					<view class="open-again-button main-center-flex " v-if="type!=2">
						<view class="">

							<view class="main-start-flex">
								<view class="open-again-button main-center-flex">
									<view class="opera-btn" v-if="type==1" @click="cancelOrder(item)">
										取消订单
									</view>
									<view class="opera-btn" v-if="type==1" @click="rePay(item)">
										去支付
									</view>
									<view class="opera-btn" @click="viewLogistics(item)" v-if="type==3">
										查看物流
									</view>
									<view class="opera-btn" @click="confirmReceipt(item)" v-if="type==3">
										确认收货
									</view>
								</view>
							</view>
						</view>

					</view>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	import {
		confirmBoxDeliver
	} from '@/api/warehouse.js'
	import {
		cancel,
		repay
	} from '@/api/order.js'
	export default {
		props: ['goods_list', 'type'],
		data() {
			return {
				limit: 10,
				page: 1,
				openInfoList: []
			}
		},
		methods: {
			viewLogistics(item) {

				uni.navigateTo({
					url: '/plugins/view-logistics/index?id=' + item.deliver_no
				})
			},
			confirmReceipt(item) {
				// 确认收货
				if (this.type != 3) {
					return
				}

				confirmBoxDeliver({
					deliver_no: item.deliver_no
				}).then(res => {
					if (res.code == 0) {
						uni.$u.toast('收货成功')
						this.$emit('renovate', this.type)
					} else {
						uni.$u.toast(res.msg)
					}
				})
			},
			cancelOrder(item) {
				cancel({
					order_no: item.order_no
				}).then(res => {
					if (res.code == 0) {
						uni.$u.toast('操作成功')
						this.$emit('renovate', this.type)
					} else {
						uni.$u.toast(res.msg)
					}
				})

			},
			rePay(item) {
				console.log(item, 'gggg')

				repay({
					order_no: item.order_no,
					platform: 'miniapp'
				}).then(res => {
					console.log(res, 'zhifu ')
					// #ifdef MP-WEIXIN
					uni.requestPayment({
						provider: 'wxpay', //支付类型-固定值
						timeStamp: res.timeStamp, // 时间戳（单位：秒）
						nonceStr: res.nonceStr, // 随机字符串
						package: res.package, // 固定值
						signType: res.signType, //固定值
						paySign: res.paySign, //签名
						success: function(res_pay) {
							console.log(res_pay, '？？？？？？')
							uni.showToast({ title: '支付成功', icon: 'none' })
							// that.$emit('closeOrder', res.data.order_no, that.payParms)
						},
						fail(res_pay) {
							uni.showToast({
								title: '支付失败',
								icon: 'none'

							})
						}
					})
					// #endif
					// #ifdef APP-PLUS
					let orderInfo = {
						appid: res.appid,
						noncestr: res.noncestr,
						package: res.package, // 参数按照官网的来 写死的
						partnerid: res.partnerid,
						prepayid: res.prepayid,
						timestamp: res.timestamp,
						sign: res.sign,
					};
					uni.requestPayment({
						provider: "wxpay", // 这个参数是写死的
						orderInfo: orderInfo, //微信、支付宝订单数据
						success: function(res) {
							console.log("这里是微信支付成功的回调");
						},
						fail: function(err) {
							console.log(res);
						},
					});
					// #endif
				})
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 100%;
		min-height: 100vh;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.record-item {
		width: 100%;
		margin-bottom: 30rpx;
		background: #ffffff;
		padding-bottom: 50rpx;
		padding-top: 30rpx;
		position: relative;
	}

	.status-box {
		position: absolute;
		top: 28rpx;
		right: 48rpx;
		color: #EA6E7A;
		font-size: 24rpx;
	}

	.record-time {
		width: 100%;
		height: 48rpx;
		padding: 0 30rpx;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.record-kinds-info {
		width: 100%;
		height: 200rpx;
		padding: 0 30rpx;
		border-top: 1rpx solid #F8F8F8;
	}

	.record-kinds-image {
		width: 180rpx;
		height: 100%;
	}

	.record-kinds-name,
	.record-series-info {
		width: calc(100% - 220rpx);
		height: 100%;
	}

	.record-kinds-name>view:first-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		padding: 0 20rpx 0 10rpx;
	}

	.record-kinds-name>view:last-child {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	::v-deep .u-divider {
		margin: 0 !important;
	}

	.record-kinds-series {
		width: 100%;
		height: 230rpx;
		padding: 0 30rpx;
	}

	.record-series-info {
		padding: 20rpx 0;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.record-series-info>view:nth-child(2) {
		width: 100%;
		padding-left: 20rpx;
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin: 20rpx 0;
	}

	.record-series-info>view:last-child {
		width: 100%;
	}

	.record-price {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.record-price>text {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.record-num {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.time-seed {
		width: 100%;
		height: 150rpx;
		padding: 25rpx 20rpx;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		padding: 0 30rpx;
	}

	.time-seed>view {
		background: #F8F8F8;
		width: 100%;
		height: 100%;
		padding: 20rpx;
	}

	.open-again {
		width: 100%;
		height: 58rpx;
		padding: 0 30rpx;
		margin-top: 30rpx;
	}

	.open-again>view {
		height: 100%;
		width: 100%;
	}

	.again-flex {
		position: relative;
	}

	.open-again-button {
		height: 100%;
		/* background: #3F3F42; */
		font-size: 26rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333;
		position: absolute;
		right: 0rpx;
	}

	.open-again-image {
		width: 396rpx;
		height: 58rpx;
	}

	.opera-btn {
		margin-left: 20rpx;
		width: 146rpx;
		height: 56rpx;
		line-height: 56rpx;
		border: 2rpx solid #F1F1F1;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		text-align: center;
		margin-right: 30rpx;
	}

</style>

