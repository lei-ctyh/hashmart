<template>
	<view class="container" :style="{'padding-top': phoneHieght}">
		<u-navbar :fixed="true" bgColor="rgba(0,0,0,0)">
			<template slot="left">
				<!-- #ifdef APP-PLUS -->
				<img src="../../static/image/logo.png" alt="" class="image-logo">
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<image src="../../static/image/logo.png" mode="" class="image-logo"></image>
				<!-- #endif -->
			</template>
		</u-navbar>
		<scroll-view enableFlex scroll-y @scrolltolower="getMoreData" class="body" @refresherpulling="refresherpulling"
			:style="{'height': `calc(100vh - ${phoneHieght})`}">
			<u-swiper :list="swiperList" keyName="pic" :autoplay="true" indicatorStyle="right: 34rpx;bottom: 17rpx"
				height="294rpx" imgMode="scaleToFill" @change="e => currentSwiper = e.current" @click="goDetail">
				<view slot="indicator" class="indicator-dot-container">
					<view class="indicator-dot" v-for="(item, index) in swiperList" :key="index"
						:class="{'indicator-dot-active' : index === currentSwiper}">
					</view>
				</view>
			</u-swiper>
			<goods-list :goods-item="goodsList" :musicSrc="musicSrc"></goods-list>

		</scroll-view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	import checkUpdate from '@/uni_modules/uni-upgrade-center-app/utils/check-update.js'
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		getMusic
	} from '@/api/goods.js';
	import {
		get_home_list
	} from '@/api/home.js';
	import GoodsList from '@/components/goods-list/index.vue';
	export default {
		data() {
			return {
				swiperList: [],
				refreshPage: false,
				currentSwiper: 0,
				loadingPage: true,
				page: 1,
				limit: 10,
				goodsList: [],
				loadStatus: 'loading',
				hasMore: false,
				musicSrc: null,
			}
		},
		components: {
			GoodsList: GoodsList
		},
		computed: {
			...mapGetters('phone', {
				phoneHieght: 'phoneHieght',
			}),
		},
		onLoad() {
			checkUpdate()
			this.getMusicSrc()
			this.refreshPage = true
			this.goodsList = []
			this.page = 1
			this.getHomeList()
		},
		// onShow() {
		// 	this.refreshPage = true
		// 	this.goodsList = []
		// 	this.page = 1
		// 	this.getHomeList()
		// },
		onHide() {
			this.refreshPage = false
		},
		// #ifdef MP
		onShareAppMessage() {
			return this.$shareAppMessage({
				path: "/pages/index/index",
			});
		},
		// #endif
		onPullDownRefresh() {
			checkUpdate()
			this.getHomeList()
		},
		methods: {
			refresherpulling() {
				console.log('444444')
				this.getHomeList()
			},
			getMusicSrc() {
				getMusic().then(res => {
					if (res.code == 0) {
						this.musicSrc = res.data
					}
				})
			},
			goDetail(e) {
				uni.navigateTo({
					url: '/plugins/goods-detail/index?id=' + this.swiperList[e].blindbox_id + '&money=' + this
						.swiperList[e].other + '&src=' + this.musicSrc
				})

			},
			getMoreData() {
				if (this.hasMore) {
					this.loadStatus = 'loading'
					this.getHomeList()
				}
			},
			//获取首页商品列表数据
			getHomeList() {

				get_home_list({
					page: this.page,
					limit: this.limit
				}).then(res => {
					if (res.code == 0) {
						this.swiperList = res.data.slider
						if (this.page < res.data.goods.last_page) {
							this.page++
							this.loadStatus = 'loadmore'
							this.hasMore = true
						} else {
							this.hasMore = false
						}
						if (res.data.goods.data.length > 0) {
							this.goodsList = this.goodsList.concat(res.data.goods.data)
						}
						this.loadingPage = false
					} else {
						this.loadingPage = false
						this.$refs.uToast.show({
							type: 'error',
							icon: false,
							message: res.msg,
						})
					}
				}).catch(err => {
					this.loadingPage = false
					this.$refs.uToast.show({
						type: 'error',
						icon: false,
						message: err.msg,

					})
				})
				uni.stopPullDownRefresh()
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		height: 100vh;
		padding: 0 30rpx;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.body {
		overflow-y: scroll;
	}

	.indicator-dot-container {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.indicator-dot {
		width: 8rpx;
		height: 8rpx;
		background: #FFFFFF;
		opacity: 0.5;
		border-radius: 50%;
		transition: width 0.2s ease;
		margin-right: 5rpx;
	}

	.indicator-dot-active {
		width: 24rpx;
		height: 10rpx;
		background: #FFFFFF;
		border-radius: 5rpx;
		opacity: 1;
	}

	::v-deep .u-swiper {
		margin-top: 20rpx;
	}

	.link-image {
		width: 100%;
		height: 140rpx;
		margin-top: 30rpx;
	}

	.link-image_1 {
		clip-path: polygon(0 0, 100% 0, calc(100% - 30rpx) 100%, 0 100%);
	}

	.link-image_2 {
		clip-path: polygon(30rpx 0, 100% 0, 100% 100%, 0 100%);
	}

	.image-logo {
		width: 211rpx;
		height: 46rpx;
	}

</style>

