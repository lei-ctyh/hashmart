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
			<!-- #ifdef APP-PLUS -->
			<template slot="right">
				<!-- #ifdef APP-PLUS -->
				<img src="../../static/image/head-right.png" alt="" class="right-logo">
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<image src="../../static/image/head-right.png" mode="" class="right-logo"></image>
				<!-- #endif -->
			</template>
			<!-- #endif -->
		</u-navbar>
		<scroll-view scroll-y @scrolltolower="getMoreData" class="body"
			:style="{'height': `calc(100vh - ${phoneHieght})`}">
			<view class="padding-container">
				<u-swiper :list="swiperList" keyName="pic" :autoplay="true" indicatorStyle="right: 34rpx;bottom: 17rpx"
					height="294rpx" imgMode="scaleToFill" @change="e => currentSwiper = e.current" @click="goBuy">
					<view slot="indicator" class="indicator-dot-container">
						<view class="indicator-dot" v-for="(item, index) in swiperList" :key="index"
							:class="{'indicator-dot-active' : index === currentSwiper}">
						</view>
					</view>
				</u-swiper>
				<view class="kinds-list">
					<view class="kinds-list-item column-space-between" v-for="(item,index) in cateList" :key="index"
						@click="goCateDetail(item)">
						<view class="kinds-list-item-image">
							<u--image :showLoading="true" :src="item.icon" width="64rpx" height="64rpx" radius="8rpx">
							</u--image>
						</view>
						<view class="kinds-list-item-name">
							{{item.name}}
						</view>
					</view>
				</view>
			</view>
			<goods-detail :goods-list="goodsList" :is-padding="false" type="market"></goods-detail>

			<u-loadmore :status="loadStatus" />
		</scroll-view>
		<u-loading-page :loading="loadingPage" loading-text="加载中..."></u-loading-page>
	</view>
</template>

<script>
	import checkUpdate from '@/uni_modules/uni-upgrade-center-app/utils/check-update.js'
	import {
		mapGetters,
		mapState
	} from 'vuex';
	import {
		get_shop_cate,
		get_goods_list,
		slider
	} from '@/api/market.js'
	import GoodsDetail from '@/components/goods-detail/index.vue';
	export default {
		data() {
			return {
				swiperList: [],
				currentSwiper: 0,
				goodsList: [],
				cateList: [], //分类
				loadingPage: true,
				page: 1,
				limit: 10,
				loadStatus: 'loading',
				hasMore: false,
			}
		},
		components: {
			GoodsDetail
		},
		computed: {
			...mapGetters('phone', {
				phoneHieght: 'phoneHieght',
			}),
		},
		onLoad() {
			checkUpdate()
			this.getCateList()
			this.getGoods()
			this.getSwiper()
		},
		onPullDownRefresh() {
			checkUpdate()
			this.getCateList()
			this.getGoods()
			this.getSwiper()
			uni.stopPullDownRefresh()
		},
		methods: {
			goBuy(e) {
				uni.navigateTo({
					url: '/plugins/market-detail/index?id=' + this.swiperList[e].goods_id
				})
			},
			getSwiper() {
				slider().then(res => {
					if (res.code == 0) {
						this.swiperList = res.data
					}

				})
			},
			getCateList() {
				get_shop_cate().then(res => {
					if (res.code == 0) {
						this.cateList = res.data
					}
				}).catch(err => {})
			},
			getMoreData() {
				if (this.hasMore) {
					this.loadStatus = 'loading'
					this.getGoods()
				}
			},
			getGoods() {
				get_goods_list({
					page: this.page,
					limit: this.limit
				}).then(res => {
					if (res.code == 0) {
						if (this.page < res.data.last_page) {
							this.page++
							this.loadStatus = 'loadmore'
							this.hasMore = true
						} else {
							this.loadStatus = 'nomore'
							this.hasMore = false
						}
						if (res.data.data.length > 0) {
							for (var i = 0; i < res.data.data.length; i++) {
								res.data.data[i].image = res.data.data[i].image.split(',')[0]
							}
							this.goodsList = this.goodsList.concat(res.data.data)

						}
					}
					this.loadingPage = false
				}).catch(err => {
					this.loadingPage = false
				})
			},
			goCateDetail(item) {
				uni.navigateTo({
					url: '/plugins/cate-detail/index?id=' + item.id + '&title=' + item.name
				})
			}
		}
	}

</script>
<style scoped>
	.container {
		width: 750rpx;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.body {
		overflow-y: scroll;
		display: inline-block;
		padding-bottom: 30rpx;
	}

	.padding-container {
		padding: 0 30rpx;
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

	.kinds-list {
		width: 100%;
		/* height: 275rpx; */
		margin-top: 67rpx;
		padding: 0 10rpx;
		display: flex;
		flex-wrap: wrap;
	}

	.kinds-list-item {
		width: 95rpx;
		height: 112rpx;
		margin: 0 19.5rpx;
		margin-bottom: 50rpx;
	}

	.kinds-list-item-name {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #333333;
		white-space: nowrap;
	}

	.kinds-list-item-image {
		width: 64rpx;
		height: 64rpx;
	}

	.image-logo {
		width: 211rpx;
		height: 46rpx;
	}

	.right-logo {
		width: 250rpx;
		height: 46rpx;
	}

</style>

