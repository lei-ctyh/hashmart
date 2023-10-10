<template>
	<view class="container">
		<logistics :wlInfo="wlInfo" v-if="show"></logistics>
		<defaultIndex v-if="!show"></defaultIndex>
	</view>
</template>

<script>
	import defaultIndex from '@/components/default/index.vue'
	import logistics from '@/components/xinyu-logistics/xinyu-logistics.vue'
	import { getExpress, getShopExpress } from '@/api/warehouse.js'
	export default {
		components: {
			logistics,
			defaultIndex
		},
		data() {
			return {
				show: false,
				wlInfo: {
					delivery_status: 1, //快递状态 1已签收 2配送中
					post_name: '', //快递名称
					logo: '', //快递logo
					exp_phone: '', //快递电话
					post_no: '', //快递单号
					addr: '', //收货地址
					//物流信息
					list: []
				}
			}
		},
		onLoad(parms) {

			var params = parms

			this.getList(params.id, params.type)
		},
		methods: {
			getList(val, type) {
				let getMethods = type == 'goods' ? getShopExpress : getExpress
				let param = {}
				if (type == 'goods') {
					param.order_id = val
				} else {
					param.deliver_no = val
				}
				uni.showLoading({
					title: '加载中'
				})
				getMethods({
					...param
				}).then(res => {
					uni.hideLoading();
					if (res.code == 0) {
						if (res.data == null || res.data == undefined) {
							this.show = true
							uni.$u.toast('暂无物流信息')
							return
						}
						if (!res.data.result) {
							this.show = true
							uni.$u.toast('暂无物流信息')
							return
						}
						if (res.data.status == 0) {
							this.show = true
							if (res.data.result.list) {
								for (var i = 0; i < res.data.result.list.length; i++) {
									res.data.result.list[i].context = res.data.result.list[i].status
									res.data.result.list[i].timeArr = res.data.result.list[i].time.split(' ')
								}
							}
							this.wlInfo.post_no = res.data.result.number
							this.wlInfo.post_name = res.data.result.expName
							this.wlInfo.logo = res.data.result.logo
							this.wlInfo.exp_phone = res.data.result.expPhone
							this.wlInfo.delivery_status = res.data.result.deliverystatus
							this.wlInfo.list = res.data.result.list
						} else {
							uni.$u.toast(res.data.msg)
						}
					} else {
						uni.$u.toast(res.msg)
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
		position: fixed;
		top: 0;
		left: 0;
		background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
	}

</style>

