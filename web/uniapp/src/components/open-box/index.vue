<template>
	<view class="container">
		<view class="head">

			<view class="head-image img-animation">
				<u--image :showLoading="true" :src="open_action" width="750rpx" height="800rpx">
				</u--image>
			</view>

		</view>

	</view>
</template>

<script>
	import baseUrl from '@/utils/siteInfo.js';
	export default {
		data() {
			return {
				open_action: baseUrl.imgroot + '/front/open-action.gif'
			}
		},


	}

</script>

<style scoped>
	.container {
		width: 750rpx;

		z-index: 1;
		padding-bottom: calc(130rpx + env(safe-area-inset-bottom));
		min-height: 100vh;
	}

	.head {
		width: 750rpx;
		height: 730rpx;
		text-align: center;
		font-size: 34rpx;
		color: #333;
		padding-top: 20rpx;
		font-weight: 600;
	}

	.head-bg {
		position: absolute;
		top: 0;
		left: 0;
	}

	.head-image {
		width: 750rpx;
		height: 800rpx;
		position: relative;
		top: 300rpx;

	}


	.head-images {
		width: 440rpx;
		height: 400rpx;
		position: relative;
		top: 200rpx;
		left: 140rpx;
	}

	.img-animations {
		left: calc(50% - 116px);
		top: calc(100% - 326px);
		animation: box1 cubic-bezier(1, 0, 0.45, 1.4) 1s 1;
	}

	@keyframes box1 {
		from {
			top: -100px;
		}

		to {
			top: calc(100% - 252px);
		}
	}

	.shaking {
		animation: shakeX 1s;
	}

	@keyframes shakeX {

		from,
		to {
			transform: translate3d(0, 0, 0);
		}

		10%,
		30%,
		50%,
		70%,
		90% {
			transform: translate3d(-0.5rem, 0, 0);
		}

		20%,
		40%,
		60%,
		80% {
			transform: translate3d(0.5rem, 0, 0);
		}
	}

	.open-box {
		font-size: 28rpx;
		background: black;
		position: relative;
		top: -16rpx;
		height: 100rpx;
		width: 654rpx;
		left: 47rpx;
		color: #fff;
		text-align: center;
		line-height: 100rpx;
	}

	.open-res {
		margin-top: 36rpx;
		display: flex;
		justify-content: center;
	}

	.res {
		padding-left: 14rpx;
		height: 44rpx;
		width: 186rpx;
		font-size: 24rpx;
		font-weight: 900;
		color: #fff;
		line-height: 44rpx;
		background: #8F09E6;
		background: linear-gradient(-45deg, transparent 16px, #8F09E6 0);

		position: relative;
		left: 20rpx;
		font-style: italic;
	}

	.num {
		padding-left: 46rpx;
		height: 44rpx;
		color: #333;
		font-size: 24rpx;
		width: 200rpx;
		line-height: 44rpx;
		text-align: center;
		background: #fff;
		display: flex;
		align-items: center;
		background: linear-gradient(135deg, transparent 16px, #fff 0);
		position: relative;
		left: -20rpx;
	}

	.icon {
		margin-left: 20rpx;
	}

</style>

