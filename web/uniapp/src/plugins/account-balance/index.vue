<template>
	<app-layout>
		<view class="container">
			<view class="head" :style="{'background-image':'url('+bgUrl+')'}">
				<view class="head-title">
					<view class="banlance">
						账户余额（元）
					</view>
					<view class="recharge" @click="recharge">
						充值
					</view>
				</view>
				<view class="head-content">
					<view class="banlan-num">
						{{banlan_money}}
					</view>
				</view>
				<view class="head-title">
					<view class="banlance total-rech">
						<view class="">
							累计充值（元 ）
						</view>
						<view class="banlan-num">
							{{total_money}}
						</view>
					</view>
					<view class="banlance total-rech">
						<view class="">
							累计消费(元)
						</view>
						<view class="banlan-num">
							{{consume}}
						</view>
					</view>
				</view>
			</view>
			<view class="history common">
				<view class="his-log">
					历史记录
				</view>
				<view class="common date" @click="show =true">
					<text>{{date_times}}</text>
					<u-datetime-picker :show="show" v-model="date_time" mode="year-month" @confirm="confirm"
						@cancel="show=false"></u-datetime-picker>
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>
			<view class="tab">
				<view class="tab-list">
					<view class="tab-all com" v-for="(item,index) in list" :key="index"
						:class="{activeAll:item.isSelect}" @click="tabChange(item.val,index)">
						{{item.name}}
					</view>
				</view>
			</view>
			<view class="list">
				<view class="list-item" v-for="(item,index) in banlance_list" :key="index">
					<view class="list-item common">
						<view class="item-left">
							<view class="opera">
								{{item.username}}
							</view>
							<view class="time">
								{{item.create_time}}
							</view>
						</view>
						<view class="item-right">
							<text class="del-num" v-if="item.balance.indexOf('-')!=-1">{{item.balance}}</text>
							<text class="add-num" v-if="item.balance.indexOf('-')==-1">+{{item.balance}}</text>
						</view>
					</view>
					<u-line color="#D8D8D8"></u-line>
				</view>
			</view>
			<!-- <u-loadmore :status="loadStatus" /> -->
		</view>
	</app-layout>
</template>

<script>
	import { balance_info, balance_list } from '@/api/my.js'
	import baseUrl from '@/utils/siteInfo.js';
	export default {
		data() {
			return {
				bgUrl: baseUrl.imgroot + '/front/banlance-bg.png',
				isSelectAll: true,
				date_time: Number(new Date()),
				date_times: '',
				show: false,
				mode: 'single',
				banlan_money: 0,
				total_money: 0,
				consume: 0,
				list: [{
					name: '全部',
					val: '0',
					isSelect: true
				}, {
					name: '充值',
					val: '2',
					isSelect: false
				}, {
					name: '消费',
					val: '1',
					isSelect: false
				}],
				banlance_list: [],
				args: false,
				load: false,
				page: 1,
				type: 0,
			}
		},
		onShow() {
			this.getCurrentTime()
			this.initData()
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;
			balance_list({
				limit: '10',
				page: page,
				type: self.type,
				month: this.date_times
			}).then(res => {
				if (res.code === 0) {
					[self.page, self.args, self.banlance_list] = [page, res.data.rows.length === 0, self
						.banlance_list.concat(
							res.data.rows)
					];
				}
				self.load = false;
			});
		},
		methods: {
			getCurrentTime() {
				let date = new Date()
				let year = date.getFullYear()
				let month = date.getMonth() + 1
				this.date_times = year + '-' + (month < 10 ? '0' + month : month)
			},
			confirm(e) {
				this.date_times = uni.$u.timeFormat(e.value, 'yyyy-mm')
				this.show = false
				this.getList()
			},
			tabChange(val, index) {
				this.page = 1
				this.banlance_list = []
				this.args = false
				this.load = false
				for (var i = 0; i < this.list.length; i++) {
					this.list[i].isSelect = false
					if (i == index) {
						this.list[i].isSelect = true
					}
				}
				this.type = val
				this.getList()
			},
			getList() {
				this.banlance_list = []
				this.page = 1
				balance_list({
					limit: '10',
					page: this.page,
					type: this.type,
					month: this.date_times
				}).then(res => {
					if (res.code == 0) {
						this.banlance_list = res.data.rows
					}
				})
			},
			recharge() {
				uni.navigateTo({
					url: '/plugins/account-rech/index?banlan_money=' + this.banlan_money
				})
			},
			initData() {
				balance_info().then(res => {
					if (res.code == 0) {
						this.banlan_money = res.data.balance
						this.total_money = res.data.totalRecharge
						this.consume = Math.abs(res.data.totalSpend)
					}
				})
				this.getList()
			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		min-height: 100vh;
		padding: 30rpx;
		background: #fff;
	}

	.head {
		width: 690rpx;
		height: 352rpx;
		padding: 30rpx;
		background: linear-gradient(#4E4E51, #262628);
		border-radius: 20rpx;
		/* background-image: url(https://cdn.kitego.cn/hashmart/my/banlance-bg.png); */
		background-repeat: no-repeat;
		background-size: 690rpx 352rpx;
		color: #fff;
	}

	.head-title {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.head-left {
		color: #fff;
		width: 456rpx;
	}

	.banlance {
		font-size: 24rpx;
	}

	.banlan-num {
		font-size: 70rpx;
		margin-top: 6rpx;
	}

	.total-rech {
		margin-top: 24rpx;
	}

	.all-con {
		position: absolute;
		/* #ifdef MP-WEIXIN */
		top: 72rpx;
		/* #endif */
		/* #ifdef APP-PLUS */
		top: 50rpx;
		/* #endif */
		left: -98rpx;

	}

	.recharge {
		width: 152rpx;
		height: 54rpx;
		background-color: #FFF0CD;
		text-align: center;
		line-height: 54rpx;
		font-size: 24rpx;
		color: #2A2A2C;
		border-radius: 26rpx;
		font-weight: 600;
	}



	.total-con {
		margin-top: 92rpx;
	}

	.total-cons {
		margin-top: 110rpx;
	}

	.history {
		margin-top: 48rpx;
	}

	.his-log {
		font-size: 32rpx;
		color: #333333;
	}

	.date {
		color: #999;
		font-size: 24rpx;
	}

	.common {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.head-right {
		color: #fff;
		position: relative;
		right: 14rpx;
		width: 234rpx;
	}

	.tab {
		margin-top: 24rpx;
	}

	.tab-list {
		width: 678rpx;
		height: 60rpx;
		background: #f3f3f3;
		padding: 8rpx;
		color: gray;
		display: flex;
		border-radius: 8rpx;
	}

	.com {
		width: 220rpx;
		height: 44rpx;
		text-align: center;
		line-height: 44rpx;
		font-size: 24rpx;
	}

	.activeAll {
		background: #FFFFFF;
		color: #333;
		border-radius: 4rpx;
	}


	.list-item {
		height: 120rpx;
	}

	.opera {
		font-size: 28rpx;
		color: #333333;
		height: 60rpx;
		line-height: 60rpx;
	}

	.time {
		font-size: 24rpx;
		color: #999999;
	}

	.item-right {
		font-size: 32rpx;
	}

	.del-num {
		color: #22AC38;
	}

	.add-num {
		color: #E60012;
	}

</style>

