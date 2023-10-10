<template>
	<view class="container" v-if="marketDetail">
		<u-swiper :list="swiperList" :autoplay="true" radius="0" indicatorStyle="right: 30rpx;bottom: 23rpx"
			height="550rpx" @change="e => currentSwiper = e.current">
			<view slot="indicator" class="indicator-dot-container">
				{{currentSwiper + 1}}/{{swiperList.length}}
			</view>
		</u-swiper>
		<view class="market-info">
			<view class="market-price">
				<text>￥</text>
				<text class="price">{{marketDetail.price}}</text>
			</view>
			<view class="market-name text-ellipsis_2">
				{{marketDetail.name}}
			</view>
		</view>
		<view class="market-detail">
			<view class="market-detail-title main-start-flex">
				商品详情
			</view>
			<view>
				<rich-text :nodes="htmlNodes"></rich-text>
			</view>
		</view>


		<u-loading-page :loading="loadingPage" loading-text="加载中..."></u-loading-page>
	</view>
</template>

<script>
	import {
		goodsInfo
	} from '@/api/goods.js';
	export default {
		data() {
			return {
				htmlNodes: null,
				swiperList: [],
				currentSwiper: 0,
				loadingPage: true,
				marketDetail: null,
			}
		},

		onLoad(parms) {
			this.getGoodsDetail(parms.id)
		},
		methods: {
			getGoodsDetail(id) {
				goodsInfo({
					id
				}).then(res => {
					if (res.code == '0') {
						this.htmlNodes = res.data.content
						this.marketDetail = res.data
						this.swiperList = res.data.image.split(',')
					}
					this.loadingPage = false
				}).catch(err => {
					this.loadingPage = false
				})
			},


		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	::v-deep .u-swiper {
		width: 750rpx;
	}

	.indicator-dot-container {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 78rpx;
		height: 43rpx;
		background: rgba(171, 171, 171, 0.6);
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
	}

	.market-info {
		width: 100%;
		background: #FFFFFF;
		padding: 30rpx;
	}

	.market-price {
		width: 100%;

	}

	.market-price>text:first-child {
		font-size: 36rpx;
		font-family: Source Han Sans CN;
		font-weight: 600;
		color: #333333;
	}

	.market-price>text:nth-child(2) {
		font-size: 90rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
	}

	.price {
		font-size: 58rpx !important;
	}

	.market-price>text:nth-child(3) {
		font-size: 34rpx;
		font-family: Abel;
		font-weight: 400;
		color: #EC872E;
		margin-left: 18rpx;
	}

	.market-price>text:last-child {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333;
	}

	.market-name {
		height: 85rpx;
		font-size: 30rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		margin-top: 50rpx;
	}

	.market-detail {
		margin-top: 30rpx;
		width: 100%;
		padding-bottom: calc(130rpx + env(safe-area-inset-bottom));
		background: #fff;
	}

	.market-detail-title {
		width: 100%;
		height: 100rpx;
		font-size: 30rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #0C0D18;
		padding-left: 30rpx;
		background: #FFFFFF;
	}

	.market-footer {
		width: 750rpx;
		height: calc(130rpx + env(safe-area-inset-bottom));
		background: #FFFFFF;
		box-shadow: 1rpx -4rpx 16rpx 0 rgba(30, 30, 30, 0.15);
		padding: 0 30rpx env(safe-area-inset-bottom) 30rpx;
		position: fixed;
		bottom: 0;
		left: 0;

	}

	.market-footer-button {
		width: 315rpx;
		height: 98rpx;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FBF8FF;
	}

	.market-footer>view:first-child {
		background: linear-gradient(99deg, #EB5C4A 0%, #8F09E6 0%, #B546FF 100%);
	}

	.market-footer>view:last-child {
		background: #3F3F42;
	}

	.market-detail-image {
		width: 750rpx;
		background: #fff;
	}

	.buy-container {
		width: 100%;
		background: #FFFFFF;
		padding: 30rpx;
	}

	.buy-info {
		width: 100%;
	}

	.buy-goods-image {
		width: 160rpx;
		height: 160rpx;
	}

	.buy-goods-detail {
		width: calc(100% - 160rpx);
		height: 160rpx;
		padding: 0 100rpx 0 30rpx;
	}

	.buy-goods-detail>view:first-child {
		width: 100%;
		height: 50%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.buy-goods-detail>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #EC872E;
		margin-top: 20rpx;
	}

	.goods-specifications {
		width: 100%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #666666;
		margin-top: 100rpx;
	}

	.specifications-contianer {
		width: 100%;
		display: flex;
		flex-wrap: wrap;
		margin-top: 30rpx;
	}

	.specifications-item {
		padding: 0 30rpx;
		height: 60rpx;
		margin-right: 30rpx;
		font-size: 25rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		margin-bottom: 30rpx;
	}

	.specifications-selected {
		border: 1rpx solid #98b9dc;
		color: #85addd;
		background: rgba(133, 173, 221, 0.2);
	}

	.specifications-unselect {
		border: 1rpx solid #0C0D18;
		color: #0C0D18;
	}

	.buy-num {
		width: 100%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #666666;
		margin-top: 50rpx;
	}

	.confirm-buy {
		width: 690rpx;
		height: 98rpx;
		background: #3F3F42;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FBF8FF;
		margin: 80rpx 0 0 0;
	}

	.rule-container {
		margin-top: 30rpx;
	}

</style>

