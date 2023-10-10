<template>
	<view class="container">
		<logistics :wlInfo="wlInfo" v-if="show"></logistics>
		<view class="img" v-if="!show">
			<!-- #ifdef MP-WEIXIN -->
			<image src="../../static/image/logistics/default.png" mode="" class="image"></image>
			<!-- #endif -->
			<!-- #ifdef APP-PLUS -->
			<img src="../../static/image/logistics/default.png" alt="" class="image">
			<!-- #endif -->

			<view class="content">
				暂无信息
			</view>
		</view>
	</view>
</template>

<script>
</script>

<style scoped>
	.container {
		width: 750rpx;
		/* height: 100vh; */
		/* position: fixed; */
		/* 	top: 0;
		left: 0; */
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.img {
		height: 100%;
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		margin-top: 50%;
	}

	.image {
		width: 196rpx;
		height: 196rpx;
	}

	.content {
		margin-top: 30rpx;
		color: #AFAFAF;
		font-size: 32rpx;
	}

</style>

