<template>
	<view class="container">
		<view class="head">
			<view class="head-title">
				恭喜获得
			</view>
		</view>
		<view class="content">
		</view>
		<view class="contents">
			<view class="goods" v-for="(item,index) in goods_list" :key="index">
				<view class="goods-item"
					:style="{'background-image':item.type=='普通款'?'url('+common+')':'url('+rare+')'}">
					<view class="goods-type">
						{{item.tag_name}}
					</view>
					<view class="goods-img">
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
			<!-- <view class="once-more common">
				再来一发
			</view> -->
		</view>
	</view>
</template>

<script>
	import {
		order_result
	} from '@/api/goods.js';
	export default {
		data() {
			return {
				total: 0,
				order_num: '',
				common: 'https://cdn.kitego.cn/hashmart/goods_detail/border.png',
				rare: 'https://cdn.kitego.cn/hashmart/goods_detail/border-little.png',
				goods_list: [],
			}
		},
		onLoad(param) {
			this.order_num = param.order_num
			order_result({
				order_no: param.order_num
			}).then(res => {
				if (res.code == 0) {
					for (var i = 0; i < res.data.detail.length; i++) {
						res.data.detail[i].goods_image = res.data.detail[i].goods_image.split(',')[0]
					}
					this.goods_list = res.data.detail
				} else if (res.code == 201) {
					this.getOrderRes()
				}
			})
		},
		methods: {
			goToWarehouse() {
				uni.switchTab({
					url: '/pages/warehouse/index'
				})
			},
			getOrderRes() {
				order_result({
					order_no: param.order_num
				}).then(res => {
					if (res.code == 0) {
						this.goods_list = res.data.detail
					} else if (res.code == 201) {
						if (this.total < 20) {
							setTimeout(() => {
								this.total = this.total + 1
								this.getOrderRes()
							}, 800)
						} else {
							uni.navigateTo({
								url: '/plugins/order-detail/index'
							})
						}


					}
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
		padding-bottom: calc(220rpx + env(safe-area-inset-bottom));
		background: black;
	}

	.head {
		width: 100%;
		text-align: center;
		background: url(https://cdn.kitego.cn/hashmart/goods_detail/reward-bg.png) no-repeat;
		background-position: center;
		background-size: 564rpx 84rpx;
		margin-top: 164rpx;
		display: flex;
		justify-content: center;

	}


	.head-title {
		width: 564rpx;
		height: 84rpx;
		text-align: center;
		color: #fff;
		font-size: 66rpx;
		line-height: 84rpx;
		font-weight: 500;
		font-family: BTH;
	}

	.content {
		background: url(https://cdn.kitego.cn/hashmart/goods_detail/goods-bg.png) no-repeat;
		background-size: 730rpx 722rpx;
		margin-top: 74rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		height: 722rpx;
		animation: roate 15s infinite linear;
	}

	.contents {
		background-size: 730rpx 722rpx;
		margin-top: 74rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
		height: 722rpx;
		position: absolute;
		top: 260rpx;
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
		color: #242424;
		font-size: 30rpx;
		font-weight: 600;
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
	}

	.footer {
		display: flex;
		justify-content: center;
		height: 100rpx;
		margin-top: 120rpx;
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

