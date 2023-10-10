<template>
	<view class="container">
		<view class="head">
			<u-tabs :list="tabList" :current="currentTab" :itemStyle="tabStyle" :scrollable="false" lineColor="#333333"
				lineHeight="4rpx" @click="changeTab"
				:activeStyle="{fontFamily: 'Source Han Sans CN',fontWeight: '500',color:'#000000'}">
			</u-tabs>
		</view>

		<view class="list-container" v-if="!show">
			<orderGoods :goods_list="detailList" :type="status" @renovate="freshen">
			</orderGoods>

		</view>
		<defaultIndex v-if="show" class="default"></defaultIndex>
	</view>
</template>

<script>
	import defaultIndex from '@/components/default/index.vue'
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		get_bag_box_list
	} from '@/api/warehouse.js'
	import { blindbox, cancel, getList } from '@/api/order.js'
	import orderGoods from '@/components/order-goods/index.vue'
	export default {
		components: {
			orderGoods,
			defaultIndex
		},
		data() {
			return {
				status: null,
				show: false,
				detailList: [],
				tabList: [{
						name: '待付款',
						value: 1
					},
					{
						name: '待发货',
						value: 2
					},
					{
						name: '待收货',
						value: 3
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

			if (parms) {
				this.type = parms.currentTab
				this.currentTab = parms.currentTab == 0 ? 3 : parms.currentTab == 1 ? 0 : parms.currentTab == 2 ? 1 : 2
				this.status = parms.currentTab
			}
			this.getList()
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;
			getList({
				limit: '10',
				page: page,
				status: this.status
			}).then(res => {
				if (res.code === 0) {
					[self.page, self.args, self.detailList] = [page, res.data.rows.length === 0, self
						.detailList.concat(
							res.data.rows)
					];
					console.log(self.detailList, 'ssss')
				}
				self.load = false;
			});
		},
		methods: {
			freshen(type) {
				this.status = type
				this.getList()
			},
			changeTab(e) {
				this.args = false,
					this.load = false,
					this.page = 1
				console.log(e, 'hhhh')
				this.order_list = []
				this.currentTab = e.index
				this.status = e.value
				this.getList(e.value)

			},
			getList(val) {
				this.detailList = []
				getList({
					page: 1,
					limit: 10,
					status: this.status
				}).then(res => {
					if (res.code == 0) {
						this.detailList = res.data.rows
						console.log(this.detailList, '数据')
						if (this.detailList.length == 0) {
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
					order_no: val
				}).then(res => {
					if (res.code == 0) {
						this.getList(1)
						uni.showToast({
							icon: 'none',
							title: '操作成功'
						})
					}
				})
			},
			checkLogis(item) {

				uni.navigateTo({
					url: '/plugins/view-logistics/index?id=' + item.order_no
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
		position: relative;
	}

	.head {
		width: 100%;
		height: 80rpx;
		background: #fff;

	}

	.list-container {
		width: 100%;
		height: calc(100% - 120rpx);
		padding: 30rpx;
		position: relative;

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
		height: 34%;
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

