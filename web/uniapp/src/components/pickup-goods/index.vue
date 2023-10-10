<template>
	<view class="container">
		<view class="record-item" v-for="(item,index) in goods_list" :key="index">
			<view class="record-time main-start-flex">
				{{type==2?'兑换单号:':'提货单号：'}}{{ type==2?item.exchange_no:item.deliver_no}}
			</view>
			<view class="record-time main-start-flex">
				{{type==2?'兑换时间:':'提货时间：'}}{{type==2?item.exchange_time:item.deliver_time}}
			</view>
			<view class="status-box" v-if="type==2">
				已兑换
			</view>
			<view class="status-box" v-if="type!=2">
				{{item.status==1?'待发货':item.status==2?'待收货':item.status==3?'已签收':''}}
			</view>
			<view class="" v-for="(item_1,index_1) in item.detail" :key="index_1">
				<view class="record-kinds-series main-start-flex">
					<view class="record-kinds-image main-center-flex">
						<u--image :showLoading="true"
							:src="type==2?item_1.reward.goods_image:item_1.rewardSimple.goods_image" width="156rpx"
							height="156rpx"></u--image>
					</view>
					<view class="record-series-info">
						<view class="text-ellipsis_2">
							{{type==2?item_1.reward.goods_name:item_1.rewardSimple.goods_name}}
						</view>
					</view>
				</view>

			</view>
			<view class="open-again">

				<view class="main-start-flex again-flex">

					<view class="open-again-button main-center-flex " v-if="type!=2">
						<view class="" v-if="item.status!=1">
							<!-- @click="viewLogistics(item)" -->
							<view class="main-start-flex">

								<view class="open-again-button main-center-flex" v-if="type==2">
									已兑换
								</view>
								<view class="open-again-button main-center-flex" v-else>
									<view class="opera-btn" @click="viewLogistics(item)">
										查看物流
									</view>
									<view class="opera-btn" @click="confirmReceipt(item)" v-if="item.status==2">
										{{item.status==1?'待发货':item.status==2?'确认收货':item.status==3?'已签收':'异常'}}
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
				if (item.status != 2) {
					return
				}

				confirmBoxDeliver({
					deliver_no: item.deliver_no
				}).then(res => {

					if (res.code == 0) {
						uni.$u.toast('收货成功')
						this.$emit('renovate')
					} else {
						uni.$u.toast(res.msg)
					}
				})
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		/* #ifdef APP-PLUS */
		height: 100%;
		/* #endif */
		/* #ifdef MP-WEIXIN */
		height: 100vh;
		/* #endif */
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
		right: 92rpx;
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
		right: 20rpx;
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

