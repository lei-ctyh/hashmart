<template>
	<view class="container">
		<view class="head main-start-flex">
			<view class="head_decorate_1">
			</view>
			<view class="head_decorate_2">
			</view>
			<view class="head_title">
				<text class="head_title_1">热卖盲盒</text>
				<text class="head_title_2">hot sale box</text>
			</view>
		</view>
		<view class="body">
			<view class="body-item" v-for="(item,index) in goodsItem" :key="index" @tap="goGoodsDetail(item)">
				<view class="body-item-image">
					<u--image :src="item.pic" width="250rpx" height="250rpx" mode="scaleToFill">
					</u--image>
				</view>
				<view class="body-item-right">
					<view class="body-item-title">
						{{item.name}}
					</view>
					<view class="body-item-remark text-ellipsis">
						{{item.desc}}
					</view>
					<view class="body-item-type_2">
						<view class="type_2_price">
							<text>￥</text>{{item.price}}
						</view>
						<view class="type_2_price_rank main-start-flex">
							<view class="main-center-flex type_2_price_rank_title">商品价值</view>
							<view class="main-center-flex type_2_price_rank_detail"
								v-if="item.price_range.min_price && item.price_range.max_price">
								{{item.price_range.min_price}} - {{item.price_range.max_price}}元
							</view>
						</view>
						<view class="type_2_image">
							<view v-for="(item_1,index_1) in item.detail" :key="index_1" class="type_2_image_item">
								<u--image :showLoading="true" :src="item_1.goods_image" width="58rpx" height="58rpx"
									mode="scaleToFill">
								</u--image>
							</view>

						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			goodsItem: {
				type: Array,
				require: true
			},
			musicSrc: {
				type: String,
				require: true
			}
		},
		data() {
			return {
				musicSrc: null,
			}
		},
		onLoad() {

		},
		methods: {
			goGoodsDetail(item) {
				console.log(item, '详情')

				uni.navigateTo({
					url: '/plugins/goods-detail/index?id=' + item.id + '&money=' + item.price + '&src=' + this
						.musicSrc + '&goods_name=' + item.name
				})
			},

		}
	}

</script>

<style scoped>
	.container {
		width: 100%;
		margin-top: 25rpx;
		/* #ifdef APP-PLUS */
		transform: translateX(-30rpx);
		/* #endif */

	}

	.head {
		width: 100%;
	}

	.head_decorate_1 {
		width: 9rpx;
		height: 48rpx;
		background: #8D01E6;
	}

	.head_decorate_2 {
		width: 4rpx;
		height: 48rpx;
		background: #8D01E6;
		margin-left: 3rpx;
	}

	.head_title {
		display: flex;
		align-items: flex-end;
		justify-content: flex-start;
	}

	.head_title_1 {
		font-size: 36rpx;
		font-family: Source Han Sans CN;
		font-weight: bold;
		color: #333333;
		margin-left: 8rpx;
	}

	.head_title_2 {
		font-size: 15rpx;
		font-family: Roboto;
		font-weight: 400;
		color: #B0B0B0;
		margin-left: 8rpx;
		margin-bottom: 4rpx;
	}

	.body {
		width: 100%;
	}

	.body-item {
		width: 100%;
		height: 300rpx;
		margin-top: 30rpx;
		/* background: linear-gradient(0deg, #FAE2DE 0%, #FDF8F2 100%); */
		background: #fff;
		padding: 25rpx;
		display: flex;
		align-items: center;
	}

	.body-item-image {
		width: 250rpx;
		height: 250rpx;
	}

	.body-item-right {
		width: 350rpx;
		margin-left: 25rpx;
	}

	.body-item-title {
		font-size: 32rpx;
		font-family: Source Han Sans CN;
		font-weight: bold;
		color: #202020;
	}

	.body-item-remark {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin-top: 12rpx;
		width: 100%;
	}

	.body-item-type_1 {
		width: 100%;
		height: 100rpx;
		margin-top: 50rpx;
		background-size: 100% 100%;
		background-image: url(https://cdn.kitego.cn/hashmart/index/buy_red.png);
		padding-left: 30rpx;
	}

	.type_1_price {
		font-size: 40rpx;
		font-family: Abel;
		font-weight: 400;
		color: #FFFFFF;
	}

	.type_1_price text {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
	}

	.type_1_buynum {
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
		opacity: 0.6;
	}

	.type_2_price {
		font-size: 40rpx;
		font-family: Abel;
		font-weight: 400;
		color: #A12EEB;
	}

	.type_2_price text {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #A12EEB;
	}

	.type_2_price_rank {
		width: 286rpx;
		height: 32rpx;
		background: linear-gradient(99deg, #8F09E6, #B546FF);
		padding: 0 2rpx;
		display: flex;
	}

	.type_2_price_rank_title {
		width: 35%;
		height: 80%;
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
	}

	.type_2_price_rank_detail {
		width: 65%;
		background: #FFFFFF;
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #9516EB;
	}

	.type_2_image {
		width: 100%;
		display: flex;
		margin-top: 15rpx;
	}

	.type_2_image_item {
		margin-right: 20rpx;
		height: 58rpx;
		width: 58rpx;
	}

</style>

