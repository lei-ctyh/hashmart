<template>
	<view class="container" @touchmove.stop.prevent="moveHandle">
		<view class="head">
			<image src="../../static/image/goods/reward-bg.png" mode="" class="head-image"></image>
			<view class="head-title">
				恭喜获得
			</view>
		</view>
		<view class="content">
		</view>
		<view class="contents">
			<view class="goods" v-for="(item,index) in goods_list" :key="index">
				<view class="goods-item"
					:style="{'background-image':item.tag_name=='普通款'?'url('+common+')':'url('+rare+')'}">
					<view class="goods-type"
						:style="{'color':item.tag_name=='普通款'?colorObj.common_color:item.tag_name=='稀有款'?colorObj.rare_color:item.tag_name=='传说款'?colorObj.lore_color:colorObj.epic_color}">
						{{item.tag_name}}
					</view>
					<view class="goods-img" :style="{'background':item.goods_image.indexOf('.png')!=-1?'#fff':''}">
						<u--image :showLoading="true" :src="item.goods_image" width="170rpx" height="170rpx">
						</u--image>
					</view>
					<view class="goods-detail">
						<view class="goods-name">
							{{item.goods_name}}
						</view>
						<view class="goods-price">
							￥{{item.goods_price}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="footer">
			<view class="receive common" @click="goToWarehouse">
				去仓库查收
			</view>
			<view class="once-more common" @click="onceMore">
				继续抽奖
			</view>
		</view>
	</view>
</template>

<script>
	import baseUrl from '@/utils/siteInfo.js';
	export default {
		props: ['goods_list', 'colorObj'],
		data() {
			return {
				total: 0,
				common_color: '',
				rare_color: '',
				lore_color: '',
				epic_color: '',
				common: baseUrl.imgroot + '/front/border.png',
				rare: baseUrl.imgroot + '/front/border-little.png',
			}
		},
		methods: {
			moveHandle() {
				return false
			},
			goToWarehouse() {
				this.$store
					.commit(
						'param/setType',
						'box'
					)
				this.$store
					.commit(
						'param/setStatus',
						'1'
					)
				uni.switchTab({
					url: '/pages/warehouse/index',
					success: function(res) {
						console.log(res)
						getApp().globalData.paramsData = {
							type: 'box',
							currentIndex: 0
						};
					}
				})
			},

			onceMore() {
				this.$emit('onceMore', '1')
			},
		},


	}

</script>

<style scoped>
	.container {
		width: 100%;
		height: 100vh;
		padding-bottom: calc(220rpx + env(safe-area-inset-bottom));

	}

	.head {
		width: 100%;
		text-align: center;
		/* background: url(https://cdn.kitego.cn/hashmart/goods_detail/reward-bg.png) no-repeat; */
		/* background: url('~@/static/image/goods/reward-bg.png') no-repeat; */
		background-position: center;
		background-size: 564rpx 84rpx;
		/* #ifdef APP-PLUS */
		margin-top: 250rpx;
		/* #endif */
		/* #ifdef MP-WEIXIN */
		margin-top: 200rpx;
		/* #endif */

	}

	.head-image {
		/* #ifdef MP-WEIXIN */
		width: 564rpx;
		height: 84rpx;
		position: relative;
		/* #endif */
		/* #ifdef APP-PLUS */
		display: inline-block;
		width: 564rpx;
		height: 42rpx !important;
		position: relative;
		/* #endif */
	}

	.head-title {
		width: 564rpx;
		height: 90rpx;
		text-align: center;
		color: #fff;
		font-size: 90rpx;
		line-height: 90rpx;
		font-family: BTH;
		position: relative;
		/* #ifdef MP-WEIXIN */
		top: -94rpx;
		/* #endif */
		left: 0;
		right: 0;
		margin: auto;
		/* #ifdef APP-PLUS */
		top: -26rpx;
		/* #endif */
	}

	.content {
		background: url(https://cdn.kitego.cn/hashmart/goods_detail/goods-bg.png) no-repeat;
		background-size: 730rpx 722rpx;
		/* #ifdef MP-WEIXIN */
		margin-top: 54rpx;
		/* #endif */
		/* #ifdef APP-PLUS */
		margin-top: 84rpx;
		/* #endif */
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		height: 722rpx;
		animation: roate 8s infinite linear;
	}

	.contents {
		background-size: 730rpx 722rpx;
		margin-top: 74rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		height: 722rpx;
		overflow-y: auto;
		width: 100%;
		position: fixed;
		left: 0;
		/* #ifdef APP-PLUS */
		width: 100%;
		top: 366rpx;
		/* #endif */
		/* #ifdef MP-WEIXIN */
		top: 336rpx;
		/* #endif */
	}

	@keyframes roate {
		0% {
			transform: rotateZ(0);
			-ms-transform: rotateZ(0);
			-moz-transform: rotateZ(0);
			-webkit-transform: rotateZ(0);
			-o-transform: rotateZ(0);
		}

		100% {
			transform: rotateZ(360deg);
			-ms-transform: rotateZ(360deg);
			-moz-transform: rotateZ(360deg);
			-webkit-transform: rotateZ(360deg);
			-o-transform: rotateZ(360deg);
		}
	}



	.goods-item {

		background-repeat: no-repeat;
		background-size: 230rpx 342rpx;
		width: 230rpx;
		height: 342rpx;
	}

	.goods-type {
		position: relative;
		top: 16rpx;
		left: 40rpx;
		/* color: #242424; */
		font-size: 30rpx;
		font-family: BTH;
	}

	.goods-img {
		width: 170rpx;
		height: 170rpx;
		position: relative;
		left: 30rpx;
		top: 20rpx;
	}

	.goods-detail {
		position: relative;
		top: 56rpx;
		color: #242424;
		font-size: 24rpx;
		left: 32rpx;
		width: 170rpx;
		text-align: center;
	}

	.goods-name {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		text-align: center;
		font-family: Source Han Sans CN;
	}

	.footer {
		display: flex;
		justify-content: space-around;
		height: 100rpx;
		margin-top: 70rpx;
	}

	.receive {
		border: 2rpx solid #fff;
	}

	.once-more {
		background: linear-gradient(to right, #8F09E6, #B546FF);
	}

	.common {
		width: 316rpx;
		height: 100rpx;
		line-height: 100rpx;
		color: #FBF8FF;
		font-size: 28rpx;
		text-align: center;
	}

</style>

