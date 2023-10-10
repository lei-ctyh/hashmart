<template>
	<page-meta :page-style="`overflow: ${showVisible ? 'hidden':''}`">
	</page-meta>
	<view class="container">
		<radio-group @change="setDefaultAddress">
			<view v-for="(item,index) in addressList" :key="index" @click.prevent>
				<view class="address-item">
					<view class="name-phone main-start-flex">
						<text>{{item.receiver}}</text>
						{{item.phone}}
					</view>
					<view class="address-detail main-start-flex">
						{{item.province_name + ' ' + item.city_name + ' ' + item.area_name  + ' ' +  item.address}}
					</view>
					<u-line color="#F8F8F8" :hairline="false" margin="10rpx 0"></u-line>
					<view class="address-operation main-space-between">
						<view class="main-start-flex">
							<radio :value="index" :checked="item.default_flag == 1" color="#eb5c4a"
								style="transform:scale(0.8)" />
							设为默认
						</view>
						<view class="main-start-flex">
							<view @click.stop="goEditAddress(item)">编辑</view>
							<view @click.stop="deleteAddress(item.id)">删除</view>
						</view>
					</view>
				</view>
			</view>
		</radio-group>
		<defaultIndex v-if="show"></defaultIndex>
		<view class="footer main-center-flex">
			<view class="add-address main-center-flex" @click="goAddressOperation">
				+ 添加收货地址
			</view>
		</view>
		<modal ref="modal" :showVisible.sync="showVisible"></modal>
	</view>
</template>

<script>
	import {
		address_list,
		set_default_address,
		delete_address
	} from '@/api/address.js'
	import defaultIndex from '@/components/default/index.vue'
	export default {
		components: {
			defaultIndex
		},
		data() {
			return {
				show: false,
				addressList: [],
				showVisible: false
			}
		},
		onShow() {
			this.getAddressList()
		},

		methods: {
			getAddressList() {
				address_list().then(res => {
					if (res.code == 0) {
						this.addressList = res.data
						if (this.addressList.length == 0) {
							this.show = true
						} else {
							this.show = false
						}
					} else {
						this.show = true
					}
				}).catch(err => {})
			},
			goAddressOperation() {
				uni.navigateTo({
					url: '/plugins/address/address-operation/index'
				})
			},
			setDefaultAddress(e) {
				console.log('920', e)
				set_default_address({
					id: this.addressList[e.detail.value].id
				}).then(res => {
					if (res.code == 0) {
						this.getAddressList()
					}
					uni.$u.toast(res.msg)
				}).catch(err => {
					uni.$u.toast(err.msg)
				})
			},
			goEditAddress(item) {
				item.addressArea = [item.province_name, item.city_name, item.area_name]
				item.addressCode = [item.province_code, item.city_code, item.area_code]
				uni.navigateTo({
					url: '/plugins/address/address-operation/index?editData=' + encodeURIComponent(JSON.stringify(
						item)) + '&type=edit'
				})
			},
			deleteAddress(id) {
				this.$refs.modal.showModal({
					title: '是否删除',
					content: '将删除此地址',
					confirm: () => {
						delete_address({
							id
						}).then(res => {
							if (res.code == 0) {
								this.getAddressList()
							}
							uni.$u.toast(res.msg)
						}).catch(err => {
							uni.$u.toast(err.msg)
						})
					}
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
		padding: 30rpx 30rpx calc(180rpx + env(safe-area-inset-bottom)) 30rpx;
	}

	.footer {
		width: 100%;
		height: calc(150rpx + env(safe-area-inset-bottom));
		background: #ffffff;
		padding-bottom: env(safe-area-inset-bottom);
		box-shadow: 1rpx -4rpx 16rpx 0 rgba(30, 30, 30, 0.15);
		position: fixed;
		bottom: 0;
		left: 0;
	}

	.add-address {
		width: 690rpx;
		height: 84rpx;
		background: #3F3F42;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
	}

	.radio-group-container {
		width: 100%;
	}

	.address-item {
		width: 100%;
		min-height: 223rpx;
		background: #FFFFFF;
		padding: 30rpx 30rpx 20rpx 30rpx;
		margin-bottom: 30rpx;
	}

	.name-phone {
		width: 100%;
		height: 50rpx;
		font-size: 26rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #BABABA;
	}

	.name-phone>text {
		font-size: 30rpx;
		font-family: Source Han Sans CN;
		font-weight: bold;
		color: #333333;
		margin-right: 10rpx;
	}

	.address-detail {
		width: 100%;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #666666;
	}

	.address-operation {
		width: 100%;
		height: 60rpx;
	}

	.address-operation>view {
		height: 100%;
	}

	.address-operation>view:first-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #BABABA;
	}

	.address-operation>view:last-child {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #666666;
	}

	.address-operation>view:last-child>view:last-child {
		margin-left: 33rpx;
	}

</style>

