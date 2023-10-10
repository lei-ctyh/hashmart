<template>
	<view class="container" :style="{'padding': padding}">
		<view class="goods-item" v-for="(item,index) in goodsList" :key="index"
			@click="goDetail(type=='box'?item.goods_id:item.id)">
			<view class="goods-item-image">
				<!-- <u--image :showLoading="true" :src=" type == 'market' ? item.image.split(',')[0] :item.pic"
					width="335rpx" height="335rpx">
				</u--image> -->
				<!-- #ifdef MP-WEIXIN -->
				<img :src=" type == 'market' ? item.image.split(',')[0] :item.pic" alt="" class="goods-img">
				<!-- #endif -->
				<!-- #ifdef APP-PLUS -->
				<image :src=" type == 'market' ? item.image.split(',')[0] :item.pic" mode="" class="goods-img"></image>
				<!-- #endif -->
			</view>
			<view class="goods-type" v-if="item.boxTag!=null" :style="{'background':item.boxTag.color}">
				{{item.boxTag.name}}
			</view>
			<view class="goods-item-name text-ellipsis_2">
				{{type == 'market' ? item.name :item.goods_name}}
			</view>
			<template v-if="type == 'detail'||type == 'box'">
				<view class="goods-item-price main-start-flex">
					<text class="text-style">参考价</text>
					<text class="money">￥ <text class="money-num">{{item.price}}</text> </text>

				</view>
				<view class="goods-item-rank text-style">
					抽中范围：{{item.lottery_min_no + '~' + item.lottery_max_no}}
				</view>
			</template>
			<template v-if="type == 'market'">
				<view class="market-price">
					<text>￥</text>
					{{item.price}}
				</view>
				<view class="market-hash-coin">
					{{item.integral_price}}
					<text>哈希币</text>
				</view>
			</template>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			goodsList: {
				type: Array
			},
			type: {
				type: String,
				default: 'detail'
			},
			padding: {
				type: String,
				default: '0 30rpx 30rpx 30rpx'
			}
		},
		onLoad() {
			console.log(this.goodsList, '9999999')
		},
		methods: {
			goDetail(id) {
				if (this.type == 'box') {
					uni.navigateTo({
						url: '/plugins/box-goods-detail/index?id=' + id
					})
				} else {
					uni.navigateTo({
						url: '/plugins/market-detail/index?id=' + id
					})
				}


			}
		}
	}

</script>

<style scoped>
	.container {
		width: 100%;
		padding: 30rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		align-content: flex-start;
	}

	.goods-item {
		width: 335rpx;
		height: 544rpx;
		background: #FFFFFF;
		/* margin: 0 5rpx; */
		margin-top: 20rpx;
	}

	.goods-item-image {
		width: 100%;
		height: 335rpx;
	}

	.goods-type {
		width: 94rpx;
		position: relative;
		top: -330rpx;
		font-size: 20rpx;
		/* background: linear-gradient(-72deg, transparent 9px, #EB5C4A 0); */
		color: #fff;
		border-radius: 0 0 100rpx 0;
		padding-left: 8rpx;
	}

	.goods-item-name {
		width: 100%;
		height: 80rpx;
		padding: 0 20rpx;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		margin: 10rpx 0 0 0;
	}

	.goods-item-price {
		display: flex;
		padding-left: 20rpx;
		font-size: 40rpx;
		font-family: Abel;
		font-weight: 400;
		color: #EA6E7A;
	}

	.goods-item-price text:nth-child(2) {
		font-size: 26rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #EA6E7A;
	}

	.text-style {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.goods-item-rank {
		padding-left: 20rpx;
	}

	.market-price {
		font-size: 40rpx;
		font-family: Abel;
		font-weight: 400;
		color: #333333;
		padding-left: 20rpx;
	}

	.market-price text {
		font-size: 26rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
	}

	.market-hash-coin {
		padding-left: 20rpx;
		font-size: 28rpx;
		font-family: Abel;
		font-weight: 400;
		color: #EC872E;
		display: flex;
		align-items: center;
		margin-top: 10rpx;
	}

	.market-hash-coin text {
		font-size: 20rpx;
	}

	.money {
		font-size: 24rpx !important;
	}

	.money-num {
		font-size: 26rpx !important;
	}

	.goods-img {
		width: 335rpx;
		height: 335rpx;
	}

</style>

