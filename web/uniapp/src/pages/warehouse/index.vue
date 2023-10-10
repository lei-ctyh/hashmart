<template>
	<view class="container">
		<!-- #ifdef APP-PLUS -->
		<view class="empty"></view>
		<!-- #endif -->
		<view class="head">
			<u-tabs :list="tabList" :itemStyle="tabStyle" :scrollable="false" :current="currentIndex"
				lineColor="#333333" lineWidth="172rpx" lineHeight="4rpx"
				:activeStyle="{fontFamily: 'Source Han Sans CN',fontWeight: 'bold',color:'#333333'}"
				@change="changeTab">
			</u-tabs>
		</view>
		<view class="sort-container main-space-between">
			<view class="sort-item main-center-flex" :class="{'sort-item-active': currentSort == item.status}"
				v-for="(item,index) in sortList" :key="index" @click="changeStatus(item)">
				{{item.name}}
			</view>
		</view>
		<!-- #ifdef APP-PLUS -->
		<!-- ${topHeight} -->
		<!-- <scroll-view enableFlex scroll-y class="list"
			:style="{'height':pickGoods?`calc(100vh - 80rpx - 262rpx -${topHeight} )`:`calc(100vh - 80rpx - 130rpx - ${topHeight} )`}"> -->
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<!-- #endif -->
		<scroll-view class="list" scroll-y @scrolltolower="getMoreData"
			:style="{'height':currentSort==1&&listType=='box' ? `calc(100vh - 280rpx)`:`calc(100vh - 210rpx)`}">
			<pickupGoods :goods_list="detailList" :type="currentSort" @renovate="freshen"
				v-if="(currentSort == 2||currentSort == 3)&&listType=='box'" class="goods-list">
			</pickupGoods>

			<view class="list-item-container" v-for="(item,index) in detailList" :key="index" v-else>
				<view class="tipInfo">
					ID:{{listType=='box'?item.id:item.order_no }}
				</view>
				<view class="list-item">
					<view class="check-box" v-if="pickGoods">
						<u-checkbox-group placement="column" @change="checkedChange(item)">
							<u-checkbox shape="circle" :checked="item.giveChecked" activeColor="#EB5C4A"></u-checkbox>
						</u-checkbox-group>
					</view>

					<view class="list-item-image main-center-flex" v-if="item.goods_image">
						<u--image :showLoading="true" :src="item.goods_image.split(',')[0]" width="160rpx"
							height="160rpx">
						</u--image>
					</view>
					<view class="list-item-info">
						<view class="list-item-info-name text-ellipsis_2">
							{{item.goods_name}}
						</view>
						<view class="list-item_0" v-if="listType=='box'&&(currentSort == 0||currentSort == 1)">
							可兑换<text>{{item.recovery_price}}</text>哈希币
						</view>
						<view class="list-item-style" v-if="(currentSort!=0&&listType=='box')||listType=='goods'"
							:style="{'margin-top': currentSort == 1|| currentSort == 3 ? '24rpx' : ''}">
							购买时间：{{item.create_time}}
						</view>
						<view class="opera-status" @click="goToPay(item)" v-if="listType=='goods'">
							{{item.status==2?'待发货':item.status==3?'待收货': item.status==4?'部分发货':item.status==5?"已完成":item.status==6?'已取消':item.status==7?'已关闭':''}}
						</view>
						<view class="main-center-flex view-logistics" v-if="listType=='goods'">

							<view class="opera-change first-opera" v-if="item.status=='3'||item.status==5"
								@click="viewLogistics(item)">
								查看物流
							</view>
							<view class="opera-change" @click="confirmReceipt(item)" v-if="currentSort == 3">
								确认收货
							</view>
						</view>
						<view class="main-center-flex opera-status"
							v-if="currentSort != 3 &&(item.status==2||item.status==3)&&listType=='box'">
							{{item.status==2?'已兑换':item.status==3?'已提货':''}}
						</view>
						<view class="status-box" v-if="listType=='box'">


							<view class="status-opera" v-if="item.status==1">
								<view class="opera-change first-opera" @click="changeLonely(item,0)">
									兑换
								</view>
								<view class="opera-change" @click="changeLonely(item,1)">
									提货
								</view>
							</view>
						</view>

					</view>
				</view>
				<view class="list-operation" v-if="currentSort == 1&&listType=='box'">
					<view class="main-center-flex" @click="changeLonely(item,0)">
						兑换
					</view>
					<view class="main-center-flex" @click="changeLonely(item,1)">
						提货
					</view>
				</view>
			</view>

			<u-loadmore :status="loadStatus" />

		</scroll-view>
		<view class="footers" v-if="listType=='box'&&currentSort=='1'">
			<view class="footer-item item1" @click="operation(item,'exchange')">
				批量兑换
			</view>
			<view class="footer-item" @click="operation(item,'pickUp')">
				批量提货
			</view>
		</view>
		<view class="footer main-center-flex" v-if="pickGoods">
			<view class="left main-center-flex">
				<u-checkbox-group placement="column" @change="choseChange">
					<u-checkbox shape="circle" :checked="choseAll"></u-checkbox>
				</u-checkbox-group>
				<view class="" @click="choseChange">
					全选
				</view>
				<view class="total">
					<view class="">
						已选{{total}}件
					</view>
					<view class="change-num" v-if="operaTypa=='exchange'">
						可兑换{{all_num}}哈希币
					</view>
				</view>

			</view>
			<view class="right main-center-flex">
				<view class="cancel " @click="cancelOpera">
					{{operaTypa=='exchange'?'取消兑换':'取消提货'}}
				</view>
				<view class="pickBtn sure-opera btn-common" v-if="total==0">
					{{operaTypa=='exchange'?'立即兑换':'立即提货'}}
				</view>
				<view class="pickBtn sure-operas btn-common" @click="surePickGoods(0)" v-if="total>0">
					{{operaTypa=='exchange'?'立即兑换':'立即提货'}}
				</view>
			</view>
		</view>
		<u-popup :show="operationVisible" @close="operationVisible = !operationVisible" :safeAreaInsetBottom="false"
			closeable>
			<view class="operation-container" v-if="currentItem">
				<view class="operation-head give-head" v-if="operationType == 'give'">
					<view class="give-title">
						我要赠送
					</view>
					<view class="give-image">
						<u--image :showLoading="true" :src="currentItem.goods_image" width="210rpx" height="210rpx">
						</u--image>
					</view>
					<view class="give-name">
						{{currentItem.goods_name}}
					</view>
				</view>
				<view class="operation-head main-start-flex" v-if="operationType == 'exchange'">
					<view class="operation-image">
						<u--image :showLoading="true" :src="currentItem.goods_image" width="160rpx" height="160rpx">
						</u--image>
					</view>
					<view class="operation-info column-align-start-flex">
						<view class="text-ellipsis_2">
							{{currentItem.goods_name}}
						</view>
						<view class="" v-if="operationType == 'exchange'">
							兑换哈希币 <text>{{currentItem.recovery_price}}</text>
						</view>
					</view>
				</view>
				<view class="operation-body" :style="{'background':operationType == 'exchange' ? '#FAF9F9' : '#fff'}">
					<view class="">
						{{operationType == 'exchange' ? '兑换说明' : '赠送说明' }}
					</view>
					<view class="">
						1、不喜欢的商品可兑换平台哈希币 (哈希币是作为平台兑换商品的媒介)</br>
						2、哈希币一旦兑换，不可转让，不可提现</br>
						3、哈希币的所属解释权归平台所有，有疑问请联系客服
						4、哈希币的兑换数量根据商品市场成本实时变化
					</view>
				</view>
				<view class="main-start-flex agreement" v-if="operationType == 'give'">
					<u-checkbox-group placement="column" @change="giveChecked = !giveChecked">
						<u-checkbox shape="circle" :checked="giveChecked"></u-checkbox>
					</u-checkbox-group>
					<view class="">
						同意上述《商品赠送说明》
					</view>

				</view>
				<view class="operation-foot main-center-flex" @click="confirmOpera">
					{{operationType == 'exchange' ? '确认兑换' : '确认赠送' }}
				</view>
			</view>
		</u-popup>
		<modal ref="modal"></modal>
	</view>

</template>

<script>
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		get_bag_box_list,
		get_bag_goods_list,
		bagBoxExchange,
		bagGoodsConfirm
	} from '@/api/warehouse.js'
	import pickupGoods from '@/components/pickup-goods/index.vue'
	export default {
		components: {
			pickupGoods
		},
		data() {
			return {

				operaTypa: null,
				all_num: 0,
				choseAll: false,
				total: 0,
				pickGoods: false,
				currentIndex: '0',
				giveChecked: true,
				tabList: [{
					name: '盒子',
					value: 'box'
				}, {
					name: '商品',
					value: 'goods'
				}],
				sortBoxList: [{
						name: '盒子中',
						status: '1'
					},
					{
						name: '已兑换',
						status: '2'
					},
					{
						name: '已提货',
						status: '3'
					}, {
						name: '全部',
						status: '0'
					},
				],
				sortGoodsList: [{
						name: '待发货',
						status: '2'
					},
					{
						name: '待收货',
						status: '3'
					},
					{
						name: '已完成',
						status: '5'
					}, {
						name: '全部',
						status: '0'
					},
				],
				sortList: [],
				currentSort: '0',
				tabStyle: {
					width: '50%',
					height: '80rpx',
					fontSize: '36rpx',
					fontFamily: 'Source Han Sans CN',
					fontWeight: '400',
					color: '#B0B0B0',
				},
				detailList: [],
				page: 1,
				limit: 10,
				loadStatus: 'loading',
				hasMore: false,
				operationVisible: false,
				currentItem: null,
				operationType: 'exchange',
				listType: 'box',
				args: false,
				load: false,
			}
		},
		computed: {
			...mapGetters('phone', {
				topHeight: 'topHeight',
			}),
		},
		onShow() {
			this.pickGoods = false
			this.listType = this.$store.getters['param/getType']
			this.sortList = this.listType == 'box' ? this.sortBoxList : this.sortGoodsList
			console.log(this.$store.getters['param/getType'], this.$store.getters['param/getStatus'], '获取参数')
			this.currentSort = this.$store.getters['param/getStatus']
			if (this.listType == 'goods') {
				this.currentIndex = '1'
			} else {
				this.currentIndex = '0'
			}

			console.log(this.listType, this.currentIndex, 'jjajaj')
			this.page = 1
			this.getList()
		},
		onLoad() {

		},

		methods: {
			viewLogistics(item) {
				let id = this.listType == 'goods' ? item.order_id : item.deliver_no
				uni.navigateTo({
					url: '/plugins/view-logistics/index?id=' + id + '&type=' + this
						.listType
				})
			},
			freshen() {
				this.getList()
			},
			confirmReceipt(item) {
				uni.showLoading({
					mask: true
				});
				// 确认收货
				bagGoodsConfirm({
					order_id: item.order_id
				}).then(res => {
					uni.hideLoading();
					if (res.code == 0) {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
						this.getList()
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}
				})
			},
			changeLonely(item, type) {
				if (type == 0) {
					this.operationVisible = true
					this.operationType = 'exchange'
					this.currentItem = item
				} else {
					this.operaTypa = ''
					this.surePickGoods(item)
				}

			},
			choseChange() {
				this.all_num = 0
				this.choseAll = !this.choseAll

				for (var i = 0; i < this.detailList.length; i++) {
					this.detailList[i].giveChecked = this.choseAll
				}
				if (this.choseAll) {
					this.total = this.detailList.length
				} else {
					this.total = 0
				}
				if (this.operaTypa == 'exchange') {
					for (var i = 0; i < this.detailList.length; i++) {
						if (this.detailList[i].giveChecked) {
							this.all_num = this.all_num + Number(this.detailList[i].recovery_price)
						}
					}
				}
			},
			checkedChange(item) {

				let choseLength = 0
				for (var i = 0; i < this.detailList.length; i++) {
					if (this.detailList[i] == item) {
						this.detailList[i].giveChecked = !this.detailList[i].giveChecked
					}
					if (this.detailList[i].giveChecked) {
						choseLength = choseLength + 1
					}
				}

				if (choseLength == this.detailList.length) {
					this.choseAll = true
				} else {
					this.choseAll = false
				}
				this.total = choseLength
				let choseNum = 0
				if (this.operaTypa == 'exchange') {
					for (var i = 0; i < this.detailList.length; i++) {
						if (this.detailList[i].giveChecked) {
							choseNum = choseNum + Number(this.detailList[i].recovery_price)
						}
					}
					this.all_num = choseNum
				}

			},
			surePickGoods(val) {
				if (this.operaTypa == 'exchange') {
					this.boxExchange()
					return
				}
				if (val != 0 && this.currentSort != 1) {
					let arrNew = [val]
					uni.navigateTo({
						url: '/plugins/pickup-goods/index?list=' + JSON.stringify(arrNew) + '&type=1&status=0'
					})
					return
				}
				let arr = []
				for (var i = 0; i < this.detailList.length; i++) {
					if (this.detailList[i].giveChecked) {
						arr.push(this.detailList[i])
					}
				}
				if (val != 0) {
					arr.push(val)
				}
				if (arr.length > 0) {
					uni.navigateTo({
						url: '/plugins/pickup-goods/index?list=' + JSON.stringify(arr) + '&type=2&status=1'
					})
				} else {
					this.$refs.modal.showModal({
						title: '请选择要提货的商品',
						complete: () => {

						}
					})
				}

			},
			boxExchange() {
				let arr = []
				for (var i = 0; i < this.detailList.length; i++) {
					if (this.detailList[i].giveChecked) {
						arr.push(this.detailList[i])
					}
				}
				let box_id = arr.map(item => {
					return item.id
				}).join(',')
				bagBoxExchange({
					box_id: box_id,
					type: this.currentSort == 0 ? '1' : '2'
				}).then(res => {
					if (res.code == 0) {
						this.$refs.modal.showModal({
							title: res.msg,
							confirm: () => {
								this.cancelOpera()
								uni.switchTab({
									url: '/pages/warehouse/index',
									success: function(res) {
										getApp().globalData.paramsData = {
											type: 'box',
											status: 1,
											page: 1
										};
									}
								})
							}
						})
					} else {
						this.$refs.modal.showModal({
							title: res.msg,
							confirm: () => {
								this.cancelOpera()
								uni.switchTab({
									url: '/pages/warehouse/index',
									success: function(res) {
										getApp().globalData.paramsData = {
											type: 'box',
											status: 1,
											page: 1
										};
									}
								})
							}
						})

					}
				}).catch(err => {
					this.$refs.modal.showModal({
						title: err.msg,
						confirm: () => {
							this.cancelOpera()
							uni.switchTab({
								url: '/pages/warehouse/index',
								success: function(res) {
									getApp().globalData.paramsData = {
										type: 'box',
										status: 1,
										page: 1
									};
								}
							})
						}
					})
				})
			},
			confirmOpera() {
				let sureOpera = this.operationType == 'exchange' ? bagBoxExchange : ''
				this.operationVisible = !this.operationVisible
				if (sureOpera == bagBoxExchange) {
					sureOpera({
						box_id: this.currentItem.id,
						type: this.currentSort == 0 ? '1' : '2'
					}).then(res => {
						if (res.code == 0) {
							this.page = 1
							this.getList()
							this.$refs.modal.showModal({
								title: res.msg,
								complete: () => {
									uni.switchTab({
										url: '/pages/warehouse/index',
										success: function(res) {
											getApp().globalData.paramsData = {
												type: 'box',
												status: 0,
												page: 1
											};
										}
									})
								}
							})
						} else {
							uni.showToast({
								title: res.msg,
								icon: 'none'
							})
						}

					})
				}
			},
			changeTab(e) {
				this.pickGoods = false
				this.detailList = []
				// this.clearPage()
				this.page = 1
				this.loadStatus = 'loading'
				this.listType = e.value
				this.sortList = this.listType == 'box' ? this.sortBoxList : this.sortGoodsList
				this.currentSort = this.sortList[0].status
				this.getList()
			},
			getList(val) {
				let getListMrthod = this.listType == 'box' ? get_bag_box_list : get_bag_goods_list
				uni.showLoading({
					title: '加载中'
				});
				let limit = this.limit
				if (this.listType == 'box' && this.currentSort == 1) {
					limit = 999
				}
				getListMrthod({
					page: this.page,
					limit: limit, //this.limit
					status: this.currentSort
				}).then(res => {
					if (res.code == 0) {
						if (res.data.data.length >= 0) {
							if (this.page == 1) {
								this.detailList = []
							}
							for (var i = 0; i < res.data.data.length; i++) {
								res.data.data[i].giveChecked = false
							}
							this.detailList = this.detailList.concat(res.data.data)
						}
						if (this.page < res.data.last_page) {
							this.page++
							this.loadStatus = 'loadmore'
							this.hasMore = true
						} else {
							this.loadStatus = 'nomore'
							this.hasMore = false
						}
						uni.hideLoading();
					} else {
						uni.hideLoading();
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}
				}).catch(err => {})
			},
			getMoreData() {
				if (this.hasMore) {
					this.loadStatus = 'loading'
					this.getList()
				}
			},
			changeStatus(item) {
				this.detailList = []
				this.page = 1
				this.pickGoods = false
				if (this.currentSort == item.status) return
				this.currentSort = item.status
				this.$store
					.commit(
						'param/setType',
						this.listType
					)
				this.$store
					.commit(
						'param/setStatus',
						this.currentSort
					)
				this.getList()
			},
			clearPage() {
				this.loadStatus = 'loading'
				this.detailList = []
				this.page = 1
			},
			operation(item, type) {
				this.operationType = type
				this.currentItem = item
				this.pickGoods = true
				this.operaTypa = type
			},
			cancelOpera() {
				this.total = 0
				this.page = 1
				this.status = 1
				this.all_num = 0
				this.getList()
				this.pickGoods = false
				for (var i = 0; i < this.detailList.length; i++) {
					this.detailList[i].giveChecked = false
					this.choseAll = false
				}

			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		position: relative;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
		/* overflow-y: hidden; */
	}

	.empty {
		width: 100%;
		background: #FBF8FF;
		height: 80rpx;

	}

	.head {
		width: 100%;
		height: 100rpx;
		background: #FBF8FF;
		/* #ifdef APP-PLUS */
		position: fixed;
		top: 80rpx;
		left: 0;
		/* #endif */
	}

	.sort-container {
		width: 100%;
		padding: 0 30rpx;
		margin: 30rpx 0 50rpx 0;
		/* #ifdef APP-PLUS */
		position: fixed;
		top: 160rpx;
		left: 0;
		/* #endif */
	}

	.sort-item {
		width: 131rpx;
		height: 51rpx;
		background: #2E2E31;
		border: 1rpx solid #D4D4D4;
		opacity: 0.3;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FBF8FF;
	}

	.sort-item-active {
		opacity: 1;
	}

	.list {
		width: 100%;
		padding: 0 30rpx 30rpx 30rpx;
		/* height: calc(100vh - 80rpx - 130rpx); */
		overflow-y: scroll;
		/* #ifdef APP-PLUS */
		position: fixed;
		top: 260rpx;
		left: 0;
		/* #endif */
	}

	.list-item-container {
		margin-bottom: 30rpx;
		position: relative;
	}

	.list-item {
		width: 100%;
		height: 280rpx;
		background: #FFFFFF;
		display: flex;
		align-items: center;
		text-align: center;
	}

	.check-box {
		margin-left: 20rpx;
	}

	.list-item-image {
		width: 220rpx;
		height: 240rpx;
	}

	.list-item-info {
		text-align: left;
		width: calc(100% - 220rpx);
		height: 280rpx;
		padding: 40rpx 30rpx 40rpx 0;
		position: relative;
	}

	.view-logistics {
		position: absolute;
		bottom: 10rpx;
		right: 36rpx;
	}

	.list-item-info-name {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
		margin-top: 20rpx;
	}

	.list-item-style {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #B0B0B0;
		margin-top: 10rpx;
	}

	.status-box {
		margin-top: 14rpx;
		font-size: 24rpx;
		text-align: right;
		display: flex;
		justify-content: flex-end;
		position: absolute;
		bottom: 20rpx;
		right: 30rpx;
	}

	.status-opera {
		display: flex;
		justify-content: flex-end;
	}

	.opera-status {
		font-size: 24rpx;
		position: absolute;
		top: 12rpx;
		right: 32rpx;
		color: #EA6E7A;
	}

	.opera-change {
		width: 146rpx;
		height: 56rpx;
		border: 2rpx solid #F1F1F1;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		text-align: center;
		line-height: 56rpx;

	}

	.first-opera {
		margin: 0 20rpx;
	}

	.list-item_0 {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #EC872E;
		margin-top: 18rpx;
	}

	.list-item_0 text {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #EC872E;
	}

	.list-operation {
		height: 80rpx;
		width: 100%;
		background: #FFFFFF;
		display: flex;
		justify-content: flex-end;
		align-items: flex-start;
	}

	.list-operation>view {
		width: 146rpx;
		height: 56rpx;
		border: 2rpx solid #F1F1F1;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		margin-right: 30rpx;
	}

	.operation-container {
		width: 100%;
		padding: 30rpx;
		background: #FFFFFF;
	}

	.operation-head {
		height: 160rpx;
	}

	.give-head {
		height: 410rpx;
		text-align: center;

	}

	.give-title {
		font-size: 30rpx;
		color: #333;
		margin-top: 40rpx;
		font-weight: 600;
	}

	.give-image {
		display: flex;
		justify-content: center;
		margin-top: 90rpx;
	}

	.give-name {
		margin-top: 70rpx;
		font-size: 28rpx;
		color: #333;
		font-weight: 600;
	}

	.agreement {
		color: #999;
		font-size: 24rpx;
		margin-top: 40rpx;
	}

	.operation-image {
		width: 160rpx;
		height: 160rpx;
	}

	.operation-info {
		width: calc(100% - 160rpx);
		height: 160rpx;
		padding: 0 100rpx 0 30rpx;
	}

	.operation-info>view:first-child {
		width: 100%;
		height: 50%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.operation-info>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #EC872E;
	}

	.operation-info>view:last-child text {
		font-size: 34rpx;
	}

	.operation-body {
		width: 690rpx;
		padding: 30rpx 20rpx;
		//background: #FAF9F9;
		margin-top: 30rpx;
	}

	.operation-body>view:first-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #666666;
	}

	.operation-body>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin-top: 27rpx;
	}

	.operation-foot {
		width: 100%;
		height: 98rpx;
		background: #3F3F42;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FBF8FF;
		margin: 62rpx 0 14rpx 0;
	}

	.footer {
		position: fixed;
		bottom: 0;
		height: 120rpx;
		display: flex;
		width: 100%;
		justify-content: space-between;
		padding: 20rpx;
		background: #fff;
		font-size: 28rpx;
		border-bottom: 2rpx solid #f3f3f3;
	}

	.footers {
		position: fixed;
		bottom: 0;
		height: 120rpx;
		display: flex;
		width: 100%;
		align-items: center;
		padding: 20rpx;
		background: #fff;
		font-size: 28rpx;
		border-bottom: 2rpx solid #f3f3f3;
		justify-content: center;
		box-shadow: 3px 2px 5px 5px #e2e2e2;
		border-top: 2rpx solid #f3f3f3;
	}

	.footer-item {
		width: 240rpx;
		height: 68rpx;
		background: #333;
		color: #fff;
		line-height: 68rpx;
		text-align: center;
		font-size: 28rpx;
	}

	.item1 {
		margin-right: 90rpx;
	}

	.total {
		color: #999999;
		margin-left: 20rpx;
		font-size: 22rpx;
	}

	.change-num {
		color: #EC872E;
	}

	.pickBtn {
		text-align: center;
		height: 60rpx;
		line-height: 60rpx;
		font-size: 28rpx;
		background: gray;
		color: #fff;
		width: 160rpx;

	}

	.sure-opera {
		background: #c0c0c0;
	}

	.sure-operas {
		background: #ec872e;
	}

	.btn-common {
		text-align: center;
		width: 180rpx;
		height: 68rpx;
		line-height: 68rpx;
		margin-left: 20rpx;
	}

	.cancel {
		color: #999;
		font-size: 28rpx;
	}

	.goods-list {
		height: 100%;
	}

	.tipInfo {
		position: absolute;
		top: 10rpx;
		left: 20rpx;
		color: #B0B0B0;
		font-size: 24rpx;
	}

</style>

