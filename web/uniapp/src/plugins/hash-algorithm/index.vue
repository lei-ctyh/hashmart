<template>
	<view class="container">
		<!-- #ifdef  MP-WEIXIN -->
		<image :src="hash_info" mode="" class="img"></image>
		<!-- #endif -->

		<!-- #ifdef  APP-PLUS -->
		<img :src="hash_info" alt="" class="img">
		<!-- #endif -->
	</view>
</template>

<script>
	import baseUrl from '@/utils/siteInfo.js';
	export default {
		data() {
			return {
				hash_info: baseUrl.imgroot + '/front/hash-info.jpg',
			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
		z-index: 1;
		padding-bottom: calc(130rpx + env(safe-area-inset-bottom));
		height: 100vh;
	}

	.img {
		height: 300%;
		width: 750rpx;
	}

</style>

