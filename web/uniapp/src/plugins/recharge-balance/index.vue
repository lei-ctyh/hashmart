<template>
	<view class="container">
		<view class="head">
			<u-tabs :list="tabList" :current="currentTab" :itemStyle="tabStyle" :scrollable="false" lineColor="#333333"
				lineWidth="113rpx" lineHeight="4rpx" @change="changeTab"
				:activeStyle="{fontFamily: 'Source Han Sans CN',fontWeight: '500',color:'#000000'}">
			</u-tabs>
		</view>
		<view class="list-container">
			<view class="list-item" v-for="(item,index) in order_list" :key="index">
				<view class="list-head main-space-between">
					<view>
						订单编号：{{item.recharge_no}}
					</view>
					<view :style="{'color': currentTab == 2 ? '#999999' : '#EA6E7A' }">
						{{item.status == 1 ? '待支付' : item.status == 2 ? '支付成功' : item.status == 3 ? '支付失败' : '已过期'}}
					</view>
				</view>
				<view class="list-head main-space-between">
					<view>
						充值时间：{{item.create_time}}
					</view>
					<view>
						充值金额：{{item.amount}}元
					</view>
				</view>
				<view class="list-head main-space-between">
					<view>
						充值方式：{{item.pay_way=='1'?'微信':'支付宝'}}
					</view>
				</view>
				<view class="list-opertaion main-end-flex" v-if="item.status == 1">
					<template v-if="item.status == 1">
						<view class="cancel-order list-button-item main-center-flex"
							@click="cancelOrder(item.recharge_no)">
							取消订单
						</view>
						<view class="pay-order list-button-item main-center-flex" @click="repayment(item.recharge_no)">
							重新支付
						</view>
					</template>
				</view>
			</view>
		</view>
		<defaultIndex v-if="show"></defaultIndex>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import defaultIndex from '@/components/default/index.vue'
	import { balanceInfo, cancel, repay } from '@/api/banlance.js'
	export default {
		components: {
			defaultIndex
		},
		data() {
			return {
				show: false,
				tabList: [{
						name: '全部订单',
						value: '0'
					}, {
						name: '待支付',
						value: '1'
					},
					{
						name: '已完成',
						value: '2'
					},
				],
				tabStyle: {
					width: '25%',
					height: '80rpx',
					fontSize: '28rpx',
					fontFamily: 'Source Han Sans CN',
					fontWeight: '400',
					color: '#999999',
				},
				currentTab: 0,
				order_list: [],
				args: false,
				load: false,
				page: 1,
				type: 0,
			}
		},
		onLoad(parms) {
			balanceInfo({
				page: this.page,
				limit: 10,
				type: this.type
			}).then(res => {
				if (res.code == 0) {
					this.order_list = res.data.rows
					if (this.order_list.length == 0) {
						this.show = true
					}
				} else {
					this.show = true
				}
			})
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;

			balanceInfo({
				limit: '10',
				page: page,
				type: self.type,
			}).then(res => {
				if (res.code === 0) {
					[self.page, self.args, self.order_list] = [page, res.data.rows.length === 0, self
						.order_list.concat(
							res.data.rows)
					];
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
				if (self.order_list.length == 0) {
					this.show = true
				} else {
					this.show = false
				}
				self.load = false;
			});
		},
		methods: {
			repayment(id) {
				let platform = ''
				// #ifdef APP-PLUS
				platform = 'app'
				// #endif
				// #ifdef MP-WEIXIN
				platform = 'miniapp'
				// #endif
				repay({
					recharge_no: id,
					pay_way: '1',
					platform: platform
				}).then(res => {
					// #ifdef MP
					uni.requestPayment({
						provider: 'wxpay', //支付类型-固定值
						timeStamp: res.timeStamp, // 时间戳（单位：秒）
						nonceStr: res.nonceStr, // 随机字符串
						package: res.package, // 固定值
						signType: res.signType, //固定值
						paySign: res.paySign, //签名
						success: function(res) {
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
				})
			},
			changeTab(e) {

				this.args = false
				this.load = false
				this.page = 1
				this.order_list = []
				this.getList(e.value)
				this.currentTab = e.value
			},
			getList(val) {
				balanceInfo({
					page: this.page,
					limit: 10,
					type: val
				}).then(res => {
					if (res.code == 0) {
						this.order_list = res.data.rows
						if (this.order_list.length == 0) {
							this.show = true
						} else {
							this.show = false
						}
					} else {
						this.show = true
					}
				})
			},
			cancelOrder(val) {
				cancel({
					recharge_no: val
				}).then(res => {
					if (res.code == 0) {
						this.getList()
						uni.showToast({
							icon: 'none',
							title: '操作成功'
						})
					}
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
	}

	.head {
		width: 100%;
		height: 80rpx;
		background: #fff;
	}

	.list-container {
		width: 100%;
		height: calc(100% - 80rpx);
		padding: 30rpx;
	}

	.list-item {
		width: 100%;
		background: #fff;
		margin-bottom: 30rpx;
	}

	.list-head {
		height: 75rpx;
		width: 100%;
		padding: 25rpx;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.list-body {
		height: 235rpx;
		width: 100%;
		padding: 30rpx;
	}

	.list-body-left {
		height: 100%;
		width: 175rpx;
	}

	.list-body-right {
		height: 100%;
		width: calc(100% - 175rpx);
		padding: 10rpx;
	}


	.goods-price {
		height: 50%;
		width: 100%;
	}

	.goods-name {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.goods-price {
		display: flex;
		justify-content: space-between;
		align-items: flex-end;
	}

	.goods-price>view:first-child {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.goods-price>view:first-child text {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.goods-price>view:last-child {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.list-foot {
		height: 150rpx;
		width: 100%;
		padding: 30rpx;
	}

	.goods-num {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.goods-total-price {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		align-items: baseline;
	}

	.goods-total-price>text:last-child {
		font-size: 40rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.list-opertaion {
		width: 100%;
		padding-right: 20rpx;
	}

	.list-button-item {
		margin: 20rpx 0 20rpx 30rpx;
		width: 154rpx;
		height: 60rpx;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.cancel-order,
	.confirm-receive {
		border: 2rpx solid #F8F8F8;
	}

	.pay-order {
		background: #3F3F42;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #FFFFFF;
	}

</style>

