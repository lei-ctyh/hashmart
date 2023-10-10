<template>
	<view class="container">
		<view class="news-item" v-for="(item,index) in newList" :key="index">
			<view class="news-item-time main-center-flex">
				2022-11-10 19:54:08
			</view>
			<view class="news-item-content">
				<view class="avatar">
					<u-avatar size="80rpx" :src="item.avatar">
					</u-avatar>
				</view>
				<view class="nickname-content">
					<view class="nickname">
						{{item.nickname}}
					</view>
					<view class="content">
						<view class="title">
							{{item.content.title}}
						</view>
						<view class="content-detail">
							{{item.content.detail}}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newList: [{
						time: '2022-11-10 19:54:08',
						avatar: 'https://thirdwx.qlogo.cn/mmopen/vi_32/CINmVUpAia2wX6ibSuVWWRpHM95xuaPc0gcTeboJIgf5mp3nSbU7LCdenX5rTA2fW6LclpxQuoX0p4Hqy6ibaZ2tg/132',
						nickname: '哈希玛特',
						content: {
							title: '标题内容标题内容标11111111111111111111111111111111111111111题内容标题内容标题内容标题内容标题内容标题内容标题内容标题内容',
							detail: '内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容'
						}

					},
					{
						time: '2022-11-10 19:54:08',
						avatar: 'https://thirdwx.qlogo.cn/mmopen/vi_32/CINmVUpAia2wX6ibSuVWWRpHM95xuaPc0gcTeboJIgf5mp3nSbU7LCdenX5rTA2fW6LclpxQuoX0p4Hqy6ibaZ2tg/132',
						nickname: '哈希玛特',
						content: {
							title: '标题内容标题内容',
							detail: '内容内容内容内容内容内容内容内容内容内容'
						}

					},
					{
						time: '2022-11-10 19:54:08',
						avatar: 'https://thirdwx.qlogo.cn/mmopen/vi_32/CINmVUpAia2wX6ibSuVWWRpHM95xuaPc0gcTeboJIgf5mp3nSbU7LCdenX5rTA2fW6LclpxQuoX0p4Hqy6ibaZ2tg/132',
						nickname: '哈希玛特',
						content: {
							title: '标题内容标题内容',
							detail: '内容内容内容内容内容内容内容内容内容内容'
						}
					}
				]
			}

		}
	}
</script>

<style scoped>
	.container {
		width: 750rpx;
		padding: 30rpx;
	}

	.news-item {
		width: 100%;
	}

	.news-item-time {
		height: 80rpx;
		width: 100%;
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.news-item-content {
		width: 100%;
		min-height: 190rpx;
		display: flex;
	}

	.avatar {
		width: 80rpx;
		height: 100%;
	}

	.nickname-content {
		width: calc(100% - 80rpx);
		height: 100%;
		padding-left: 15rpx;
	}

	.nickname {
		height: 40rpx;
		width: 100%;
		font-size: 20rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	.content {
		width: 100%;
		min-height: 150rpx;
		background: #F2F2F2;
		clip-path: polygon(0 0, 100% 0, 100% 100%, 20rpx 100%, 20rpx 20rpx);
		padding: 20rpx 20rpx 20rpx 40rpx;
		word-break: break-all;
	}

	.title {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 500;
		color: #333333;
	}

	.content-detail {
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
		margin-top: 10rpx;
	}
</style>
