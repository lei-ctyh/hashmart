<template>
	<view class="container">
		<view class="head">
			<u-tabs :list="tabList" :current="currentTab" :itemStyle="tabStyle" :scrollable="false" lineColor="#333333"
				lineWidth="113rpx" lineHeight="4rpx" @change="changeTab"
				:activeStyle="{fontFamily: 'Source Han Sans CN',fontWeight: '500',color:'#000000'}">
			</u-tabs>
		</view>
		<view class="list-container">
			<view class="list-item" :class="{'cannot-use':currentTab == 1}" v-for="(item,index) in 10" :key="index">
				<view class="list-item-left column-center-flex">
					<view class="coupon-price">
						<text>￥</text>
						20
					</view>
					<view class="coupon-condition">
						满200元可用
					</view>
				</view>
				<view class="list-item-center column-center-flex">
					<view class="coupon-name text-ellipsis">
						优惠券名称名称名优惠券名称名称名优惠券名称名称名称
					</view>
					<view class="coupon-range">
						适用于【盲盒名称】
					</view>
					<view class="coupon-time">
						2022.12.01-2022.12.31
					</view>
				</view>
				<view class="list-item-right main-center-flex">
					{{currentTab == 0 ? '立即使用' : '已使用'}}
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
	export default {
		data() {
			return {
				tabList: [{
						name: '可用',
						value: '0'
					},
					{
						name: '不可用',
						value: '1'
					},
				],
				tabStyle: {
					width: '50%',
					height: '80rpx',
					fontSize: '28rpx',
					fontFamily: 'Source Han Sans CN',
					fontWeight: '400',
					color: '#999999',
				},
				currentTab: 0
			}
		},
		methods: {
			changeTab(e) {
				this.currentTab = e.index
			}
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
		padding: 30rpx;
	}

	.list-item {
		width: 100%;
		height: 200rpx;
		background-image: url(https://cdn.kitego.cn/hashmart/my/coupon_bg.png);
		background-size: 100% 100%;
		margin-bottom: 30rpx;
		display: flex;
	}

	.cannot-use {
		filter: grayscale(1);
		opacity: 0.7;
		pointer-events: none;
	}

	.list-item-left {
		width: 220rpx;
		height: 100%;
	}

	.coupon-price {
		font-size: 50rpx;
		font-family: PingFang SC;
		font-weight: 500;
		color: #FFFFFF;
	}

	.coupon-price>text {
		font-size: 30rpx;
	}

	.coupon-condition {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
		margin-top: 20rpx;
	}

	.list-item-center {
		width: 390rpx;
		height: 100%;
		padding: 0 25rpx;
	}

	.list-item-center>view {
		width: 100%;
	}

	.coupon-name {
		font-size: 32rpx;
		font-family: Source Han Sans CN;
		font-weight: bold;
		color: #333333;
	}

	.coupon-range {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.coupon-time {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin-top: 20rpx;
	}

	.list-item-right {
		width: 80rpx;
		height: 100%;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		writing-mode: tb-rl;
		letter-spacing: 8rpx;
		color: #8F0AF0;
	}

</style>

