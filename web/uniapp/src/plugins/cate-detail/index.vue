<template>
	<view class="container">
		<goods-detail :goodsList="goodsList" :is-padding="false" type="market" v-if="show"></goods-detail>
		<u-loading-page :loading="loadingPage" loading-text="加载中..." v-if="show"></u-loading-page>
		<defaultIndex v-if="!show" class="default"></defaultIndex>
	</view>
</template>

<script>
	import {
		get_cate_detail
	} from '@/api/market.js'
	import GoodsDetail from '@/components/goods-detail/index.vue';
	import defaultIndex from '@/components/default/index.vue'
	export default {
		components: {
			defaultIndex,
			GoodsDetail
		},
		data() {
			return {
				show: true,
				page: 1,
				limit: 10,
				goodsList: [],
				loadingPage: true,
				args: false,
				load: false,
				id: null
			}
		},
		onLoad(parms) {
			uni.setNavigationBarTitle({
				title: parms.title
			})
			this.getCateDetailList(parms.id)
		},
		onReachBottom: function() {
			const self = this;
			if (self.args || self.load)
				return;
			self.load = true;
			let page = self.page + 1;
			get_cate_detail({
				limit: '10',
				page: page,
				cate_id: self.id
			}).then(res => {
				if (res.code === 0) {
					[self.page, self.args, self.goodsList] = [page, res.data.data.length === 0, self
						.goodsList.concat(
							res.data.data)
					];
				}
				if (self.goodsList.length > 0) {
					self.show = true
				} else {
					self.show = false
				}
				self.load = false;
			});
		},
		methods: {
			getCateDetailList(id) {
				this.id = id
				get_cate_detail({
					page: this.page,
					limit: this.limit,
					cate_id: id
				}).then(res => {
					if (res.code == 0) {
						this.goodsList = res.data.data
						if (this.goodsList.length == 0) {
							this.show = false
						} else {
							this.show = true
						}
					} else {
						this.show = false
					}
					this.loadingPage = false
				}).catch(err => {
					this.loadingPage = false
				})
			}
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		min-height: 100vh;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

	.default {
		position: fixed;
		top: 0%;
		left: 0;
	}

</style>

