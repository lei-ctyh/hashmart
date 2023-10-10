<template>
	<u-popup :show="showVisible" :mode="mode" @close="close" bgColor="transparent" :safeAreaInsetBottom="false"
		class="bg">
		<view class="container">
			<view class="main">
				<view class="close" @click="close"></view>
				<!-- #ifdef MP-WEIXIN -->
				<image class="head" src="../../static/image/modal-head.png" mode="scaleToFill" @click="close"></image>
				<!-- #endif -->
				<!-- #ifdef APP-PLUS -->
				<img class="head" src="../../static/image/modal-head.png" alt="" @click="close">
				<!-- #endif -->
				<view class="body main-center-flex">
					<view class="content">
						<view class="title main-center-flex">
							{{title}}
						</view>
						<view class="content-detail">
							<text v-if="!isInput">{{content}}</text>
							<u--input v-else v-model="inputModel" border="none" placeholder="请填写昵称" fontSize="28rpx"
								color="#b0b0b0" inputAlign="center">
							</u--input>
						</view>
						<view class="edit-icon" v-if="isInput">
							<u-icon name="edit-pen"></u-icon>
						</view>
						<view class="button main-center-flex">
							<view class="button_1 main-center-flex" @click="confirm">
								确定
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</u-popup>
</template>

<script>
	export default {
		data() {
			return {
				showVisible: false,
				title: '',
				content: '',
				mode: 'center',
				isInput: false,
				inputModel: '',
				confirmBack: undefined,
				cancleBack: undefined,
				completeBack: undefined
			}
		},
		watch: {
			showVisible: {
				handler(newVal) {
					this.$emit('update:showVisible', newVal)
				},
				immediate: true
			},
		},
		methods: {
			showModal({
				title,
				content,
				isInput,
				inputModel,
				confirm,
				cancel,
				complete
			}) {
				this.title = title
				this.content = content
				this.isInput = isInput
				this.inputModel = inputModel
				this.showVisible = true
				if (confirm) {
					this.confirmBack = confirm
				}
				if (cancel) {
					this.cancleBack = cancel
				}
				if (complete) {
					this.completeBack = complete
				}
			},
			confirm() {
				if (this.confirmBack) {
					this.confirmBack(this.inputModel)
				}
				this.close()

			},
			cancel() {
				if (this.cancleBack) {
					this.cancleBack()
				}
				this.close()
			},
			complete() {
				if (this.completeBack) {
					this.completeBack()
				}
			},
			close() {
				if (this.completeBack) {
					this.complete()
				}
				this.title = ''
				this.content = ''
				this.isInput = false
				this.inputModel = ''
				this.completeBack = undefined
				this.confirmBack = undefined
				this.cancleBack = undefined
				this.showVisible = false
			}
		}
	}

</script>

<style scoped>
	.container {
		height: 100vh;
		width: 750rpx;
		padding: 50rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
		position: fixed;
		left: 0;
		top: 0;
		background: rgba(0, 0, 0, 0.7);
	}

	.main {
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}

	.close {
		height: 85rpx;
		width: 85rpx;
		position: absolute;
		right: 0;
		top: 0;
	}

	.head {
		width: 100%;
		height: 85rpx;
	}

	.body {
		width: 100%;
		background: #ffffff;
		border: 5rpx solid #000000;
		border-radius: 0 0 20rpx 20rpx;
		padding: 20rpx;
		position: relative;
		top: -10rpx;
	}

	.content {
		width: 100%;
		height: 100%;
		background: #FFFFFF;
		border: 3rpx solid #000000;
	}

	.title {
		width: 100%;
		font-size: 34rpx;
		font-family: Source Han Sans CN;
		font-weight: 600;
		color: #333333;
		margin: 54rpx 0 29rpx 0;
		padding: 0 45rpx;
	}

	.content-detail {
		width: 100%;
		padding: 0 45rpx;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		text-align: center;
		margin-bottom: 55rpx;
	}

	.button {
		width: 100%;
		margin-bottom: 30rpx;
		padding: 0 10rpx;
	}

	.button_1 {
		width: 523rpx;
		height: 74rpx;
		background: #3F3F42;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FBF8FF;
	}

	.icon {
		position: absolute;
		top: 41rpx;
		right: 5rpx;
		width: 60rpx;
		height: 40rpx;
	}

	.iconplus {
		position: absolute;
		top: 160rpx;
		right: 92rpx;
		width: 28rpx;
		height: 28rpx;
	}

	.edit-icon {
		width: 32rpx;
		height: 32rpx;
		position: absolute;
		top: 160rpx;
		right: 66rpx;
	}

</style>

