<template>
	<view class="container">
		<view class="head">
			<u-tabs :list="tabList" :current="currentTab" :itemStyle="tabStyle" :scrollable="false" lineColor="#333333"
				lineHeight="4rpx" @click="changeTab"
				:activeStyle="{fontFamily: 'Source Han Sans CN',fontWeight: '500',color:'#000000'}">
			</u-tabs>
		</view>

		<view class="list-container">
			<pickupGoods :goods_list="detailList" :type="currentSort" @renovate="freshen" v-if="currentTab==2">
			</pickupGoods>
			<view class="list-item" v-for="(item,index) in order_list" :key="index" v-else>
				<view class="list-head main-space-between">
					<view>
						订单编号：{{item.order_no}}
					</view>
					<view :style="{'color': currentTab == 3 ? '#999999' : '#EA6E7A' }">
						{{item.status == 1 ? '待支付' : item.status == 2 ? '待发货' : item.status == 3 ? '待收货' : item.status == 4?'部分发货':item.status == 5?'已完成':item.status == 6?'已取消':item.status == 7?'已关闭':'库存不足'}}
					</view>
				</view>
				<view class="list-head">
					盲盒名称：{{item.blindbox.name}}
				</view>
				<u-line color="rgba(0,0,0,0.2)"></u-line>
				<!-- v-for="(item,index) in item.blindbox.orderDetail" :key="index" -->
				<view class="list-body main-space-between" v-for="(item_1,index_1) in item.blindbox.orderDetail"
					:key="index_1">
					<view class="list-body-left main-center-flex">
						<u--image :showLoading="true" :src="item_1.goods_image" width="156rpx"
							height="156rpx"></u--image>
					</view>
					<view class="list-body-right">
						<view class="goods-name text-ellipsis_2">
							{{item_1.goods_name}}
						</view>
						<view class="goods-price">
							<view>
								<text>￥</text>
								{{item_1.price}}
							</view>
						</view>
						<view class="goods-price">
							<view>
								<text>可兑换哈希币</text>
								{{item_1.recovery_price}}
							</view>
						</view>
					</view>
				</view>
				<u-line color="rgba(0,0,0,0.2)"></u-line>
				<view class="list-foot column-align-start-space-flex">
					<view class="goods-num">
						共{{item.blindbox.orderDetail.length}}件商品
					</view>
					<view class="goods-total-price main-start-align-end-flex">
						合计
						<text>￥</text>
						<text>{{item.pay_price}}</text>
					</view>
				</view>
				<view class="list-opertaion main-end-flex" v-if="currentTab != 2">
					<template v-if="currentTab == 1">
						<view class="cancel-order list-button-item main-center-flex"
							@click="cancelOrder(item.order_no)">
							取消订单
						</view>
						<view class="pay-order list-button-item main-center-flex">
							立即付款
						</view>
					</template>
					<template v-if="currentTab == 3">
						<view class="confirm-receive list-button-item main-center-flex" @click="checkLogis(item)">
							查看物流
						</view>
					</template>
				</view>
			</view>
		</view>
		<defaultIndex v-if="show"></defaultIndex>
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
	import { blindbox, cancel } from '@/api/order.js'
	import pickupGoods from '@/components/pickup-goods/index.vue'
	export default {
		components: {
			pickupGoods,
			defaultIndex
		},
		data() {
			return {
				show: false,
				detailList: [],
				tabList: [{
						name: '全部',
						value: 0
					}, {
						name: '待付款',
						value: 1
					},
					{
						name: '待收货',
						value: 3
					},
					{
						name: '已完成',
						value: 2
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
				page: 0,
				type: 0,
			}
		},
		onLoad(parms) {
			if (parms) {
				this.type = parms.currentTab
				this.currentTab = parms.currentTab
			}
			this.getList(this.type)
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;
			if (this.currentTab == 2) {
				get_bag_box_list({
					limit: '10',
					page: page,
					status: 3
				}).then(res => {
					if (res.code === 0) {
						let list = []
						list = res.data.data.filter((item, index) => {
							return item.status == 1 || item.status == 2
						})[self.page, self.args, self.detailList] = [page, list.length === 0, self
							.detailList.concat(list)
						];
					}
					self.load = false;
				});
			} else {
				blindbox({
					limit: '10',
					page: page,
					type: self.type,
				}).then(res => {
					if (res.code === 0) {

						[self.page, self.args, self.order_list] = [page, res.data.rows.length === 0, self
							.order_list.concat(res.data.rows)
						];
					}
					self.load = false;
				});
			}

		},
		methods: {
			changeTab(e) {
				this.order_list = []
				this.currentTab = e.index

				this.getList(e.value)

			},
			getList(val) {
				if (this.currentTab == 2) {
					get_bag_box_list({
						page: 1,
						limit: 10,
						status: 3
					}).then(res => {
						if (res.code == 0) {
							let list = res.data.data.filter((item, index) => {
								return item.status == 1 || item.status == 2
							})
							this.detailList = list
							if (list.length == 0) {
								this.show = true
							} else {
								this.show = false
							}
						} else {
							this.show = true
						}
					})
				} else {
					blindbox({
						page: 1,
						limit: 10,
						type: val
					}).then(res => {
						if (res.code == 0) {
							this.order_list = res.data.rows
							if (res.data.rows.length == 0) {
								this.show = true
							} else {
								this.show = false
							}
						} else {
							this.show = true
						}
					})
				}

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

