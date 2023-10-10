<template>
	<view class="container">
		<view class="record-item" v-for="(item,index) in openInfoList" :key="index">
			<view class="record-time main-start-flex">
				开盒时间：{{item.create_time}}
			</view>
			<view class="" v-for="(item_1,index_1) in item.detail" :key="index_1">
				<view class="record-kinds-series main-start-flex">
					<view class="record-kinds-image main-center-flex">
						<u--image :showLoading="true" :src="item_1.goods_image" width="156rpx"
							height="156rpx"></u--image>
					</view>
					<view class="record-series-info">
						<view class="text-ellipsis_2">
							{{item_1.goods_name}}
						</view>
						<view class="">
							抽中范围：{{item_1.range.split(',')[0]}}~{{item_1.range.split(',')[1]}}
						</view>
						<view class="main-space-between">
							<view class="record-price">
								<text>￥</text>
								{{item_1.goods_price}}
							</view>
							<view class="record-num">
								x1
							</view>
						</view>
					</view>
				</view>
				<view class="time-seed">
					<view class="column-align-start-space-flex">
						<view class="">
							时间种子：{{item_1.order_time}}
						</view>
						<view class="">
							用户种子：{{item_1.hash_key}}
						</view>
						<view class="">
							结果种子hash：{{item_1.hash_no}}
						</view>
					</view>

				</view>
			</view>
			<view class="open-again">
				<view class="main-start-flex">
					<view class="open-again-image">
						<u--image :showLoading="true" src="https://cdn.kitego.cn/hashmart/my/open_again.png"
							width="472rpx" height="58rpx"></u--image>
					</view>
					<view class="open-again-button main-center-flex" @click="onceAgain(item)">
						再来一发
					</view>
				</view>
			</view>
		</view>
		<defaultIndex v-if="show"></defaultIndex>
	</view>
</template>

<script>
	import { orderRecordLog } from '@/api/openInfo.js'
	import defaultIndex from '@/components/default/index.vue'
	export default {
		components: {
			defaultIndex
		},
		data() {
			return {
				show: false,
				limit: 10,
				page: 1,
				openInfoList: []
			}
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;

			orderRecordLog({
				limit: '10',
				page: page,
			}).then(res => {

				[self.page, self.args, self.openInfoList] = [page, res.data.length === 0, self
					.openInfoList.concat(
						res.data)
				];
				if (this.openInfoList.length == 0) {
					this.show = true
				} else {
					this.show = false
				}

				self.load = false;
			});
		},
		onShow() {
			this.show = false
			orderRecordLog({
				limit: this.limit,
				page: this.page
			}).then(res => {
				this.openInfoList = res.data
				if (this.openInfoList.length == 0) {
					this.show = true
				}
			})
		},
		methods: {
			onceAgain(item) {
				console.log(item, 'item')
				uni.navigateTo({
					url: '/plugins/goods-detail/index?id=' + item.blindbox_id + '&money=' + item.box.price +
						'&goods_name=' + item.box.name
				})
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		min-height: 100vh;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
		padding: 30rpx;
	}

	.record-item {
		width: 100%;
		margin-bottom: 30rpx;
		background: #ffffff;
		padding-bottom: 50rpx;
	}

	.record-time {
		width: 100%;
		height: 75rpx;
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
		width: calc(100% - 180rpx);
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

	.open-again-button {
		width: calc(100% - 472rpx);
		height: 100%;
		background: #3F3F42;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #FFFFFF;
	}

	.open-again-image {
		width: 472rpx;
		height: 58rpx;
	}

</style>

