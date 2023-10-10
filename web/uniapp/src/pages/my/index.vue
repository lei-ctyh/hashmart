<template>
	<!-- #ifdef APP-PLUS -->
	<view class="container">
		<view class="empty" :style="{'height': topHeight}"></view>
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<view class="container" :style="{'padding-top': phoneHieght}">
			<!-- #endif -->
			<!-- #ifdef MP-WEIXIN -->
			<u-navbar :fixed="true" leftIconSize="0" title="我的" bgColor="rgba(0,0,0,0)"></u-navbar>
			<!-- #endif -->
			<view class="user-info main-start-flex">
				<template v-if="isUserLogin">
					<u-avatar size="98rpx" :src="userInfo.avatar" v-if="userInfo.avatar">
					</u-avatar>
					<view class="user-info-text">
						<view v-if="userInfo.nickname!=null&&userInfo.nickname!=undefined">
							{{userInfo.nickname}}
						</view>
						<view class="nick-id" v-if="userInfo.nickname!=null&&userInfo.nickname!=undefined">
							ID:{{userInfo.id}}
						</view>
					</view>
				</template>
				<view class="login main-center-flex" @click="goLogin" v-else>
					立即登录
				</view>
			</view>
			<view class="account-price-container main-space-between" v-if="isUserLogin">
				<view class="account-price-item  my-banlance" @click="lookBanlance"
					:style="{'font-size':userInfo.integral>10000000000 ?'42rpx': '52rpx' }">
					<view>{{userInfo.balance}}</view>
					<view>账户余额(元)</view>
				</view>
				<view class="account-price-item my-banlance"
					:style="{'font-size':userInfo.integral>10000000000 ?'42rpx': '52rpx' }">
					<view>{{userInfo.integral}}</view>
					<view>哈希币</view>
				</view>
			</view>
			<view class="order-container">
				<view class="order-head main-start-flex my-order">
					我的订单
				</view>
				<view class="order-detail">
					<view class="order-item column-center-flex" v-for="(item,index) in orderList" :key="index"
						@tap="goOrderDetail(item.val)">
						<view class="order-item-image">
							<!-- #ifdef  MP-WEIXIN -->
							<image :src="item.pic" mode="" class="order-icon" v-if="item.val!=2"></image>
							<image src=" ../../static/image/my/finish.png" mode="" class="order-icon" v-else></image>
							<!-- #endif -->
							<!-- #ifdef  APP-PLUS -->
							<img :src="item.pic" alt="" class="order-icon">
							<!-- #endif -->
						</view>
						<view class="order-name">
							{{item.name}}
						</view>

					</view>
				</view>
			</view>
			<view class="service-container">
				<view class="service-head main-start-flex">
					我的服务
				</view>
				<view class="service-detail main-start-flex">
					<view class="service-item  my-service" v-for="(item,index) in serviceList" :key="index"
						@click="goDetail(item.url)">
						<view class="service-item-image">
							<!-- #ifdef  MP-WEIXIN -->
							<u--image :showLoading="true" :src="item.pic" width="48rpx" height="48rpx">
							</u--image>
							<!-- #endif -->
							<!-- #ifdef  APP-PLUS -->
							<img :src="item.pic" alt="" class="img-icon">
							<!-- #endif -->
						</view>
						<view class="service-name">
							{{item.name}}
						</view>
					</view>
				</view>
			</view>
		</view>
</template>

<script>
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		get_user_info
	} from '@/api/my.js'
	export default {
		data() {
			return {
				refreshPage: false,
				userInfo: null,
				isUserLogin: false,
				orderList: [{
						pic: require('../../static/image/my/un_pay.png'),
						name: '待付款',
						val: '1'
					},
					{
						pic: require('../../static/image/my/finish.png'),
						name: '待发货',
						val: '2'
					},
					{
						pic: require('../../static/image/my/un_receive.png'),
						name: '待收货',
						val: '3'
					},
				],
				serviceList: [{
						pic: require('../../static/image/my/record.png'),
						name: '开盒记录',
						url: '/plugins/open-record/index'
					},
					{
						pic: require('../../static/image/my/address.png'),
						name: '地址管理',
						url: '/plugins/address/index'
					},
					{
						pic: require('../../static/image/my/setting.png'),
						name: '联系客服',
						url: '/plugins/customer/index'
					},
					{
						pic: require('../../static/image/my/setting.png'),
						name: '设置',
						url: '/plugins/setting/index'
					},
					{
						pic: require('../../static/image/my/order-icon.png'),
						name: '余额充值订单',
						url: '/plugins/recharge-balance/index'
					}
				]
			}
		},
		computed: {
			...mapGetters('phone', {
				topHeight: 'topHeight',
				phoneHieght: 'phoneHieght',
			}),
		},
		onShow() {
			uni.showLoading({
				title: '加载中'
			})

			this.isUserLogin = this.$user.isLogin()
			if (this.isUserLogin) {
				this.getUserInfo()
			} else {
				uni.hideLoading();
			}
		},
		onHide() {
			this.refreshPage = false
		},
		methods: {
			lookBanlance() {
				uni.navigateTo({
					url: '/plugins/account-balance/index'
				})
			},
			getUserInfo() {
				get_user_info().then(res => {
					uni.hideLoading();
					if (res.code == 0) {
						this.userInfo = res.data
						this.userInfo.integral = parseFloat(this.userInfo.integral).toFixed(2)
					}
				}).catch(err => {})
			},
			goOrderDetail(index) {
				uni.navigateTo({
					url: '/plugins/order-detail/index?currentTab=' + index
				})
			},
			goDetail(url) {
				if (url) {
					uni.navigateTo({
						url: url
					})
				}
			},
			goLogin() {
				uni.navigateTo({
					url: '/plugins/login/phone-number/index?type=' + 'phone'
				})
			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		padding: 30rpx;
		padding-top: 60rpx;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.empty {
		width: 100%;
	}

	.user-info {
		height: 98rpx;
		width: 100%;
		position: relative;
	}

	.user-info-text {
		margin-left: 20rpx;
	}

	.nick-id {
		font-size: 24rpx !important;
	}

	.user-info-text>view:first-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: bold;
		color: #333333;
	}

	.user-info-text>view:last-child {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin-top: 20rpx;
	}

	.account-price-container {
		width: 100%;
		height: 150rpx;
		margin-top: 30rpx;
	}

	.account-price-item {
		width: 335rpx;
		height: 150rpx;
		background: #FFFFFF;
	}

	.account-price-item>view:first-child {
		/* font-size: 52rpx; */
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.account-price-item>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.order-container,
	.service-container {
		width: 100%;
		height: 250rpx;
		background: #FFFFFF;
		margin-top: 30rpx;
		/* padding: 0 30rpx; */
	}

	.my-order {
		margin-left: 30rpx;
	}

	.order-head,
	.service-head {
		width: 100%;
		height: 100rpx;
		font-size: 32rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.service-head {
		padding: 0 30rpx;
	}

	.order-detail {
		height: 130rpx;
		width: 100%;
		background: #FFFFFF;
		padding: 0 18rpx;
		display: flex;
		justify-content: space-between;
		align-items: flex-start;
	}

	.order-item {
		width: 25%;
	}

	.order-item,
	.service-item {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		margin-top: 10rpx;
	}

	.order-item-image {
		width: 74rpx;
		height: 68rpx;
	}

	.order-name {
		margin-top: 18rpx;
	}

	.service-item-image {
		width: 48rpx;
		height: 48rpx;
	}

	.img-icon {
		width: 48rpx;
		height: 48rpx;
	}

	.service-container {
		min-height: 360rpx;
		padding: 0;
	}

	.service-detail {
		width: 100%;
		background: #FFFFFF;
		flex-wrap: wrap;
	}

	.service-item {
		width: 25%;
		margin-bottom: 40rpx;
	}

	.login {
		position: absolute;
		height: 100%;
		right: 0;
	}

	.image-size {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
	}

	.service-name {
		margin-top: 20rpx;
	}

	.my-service {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.my-banlance {
		display: flex;
		flex-direction: column;
		padding-left: 32rpx;
		justify-content: center;
	}

	.order-icon {
		width: 74rpx;
		height: 68rpx;
	}

</style>

