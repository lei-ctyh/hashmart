<template>
	<view class="container">

		<view class="list-content">
			<view class="" v-for="(item,index) in cellList" :key="index">
				<view class="list-items" v-if="item.label=='avatar'" @click="operateUserInfo(item)">
					<text>{{item.title}}</text>
					<!-- #ifdef APP-PLUS -->
					<img :src="userInfo.avatar" alt="" class="head-img">
					<!-- #endif -->
					<!-- #ifdef MP-WEIXIN -->
					<image :src="userInfo.avatar" mode="" class="head-img"></image>
					<!-- #endif -->
				</view>

				<view class="list-items" v-else-if="item.label=='nickname'" @click="operateUserInfo(item)">
					<text>{{item.title}}</text>
					<text>{{userInfo.nickname}}</text>
				</view>
				<!-- #ifdef APP-PLUS -->
				<view class="list-items" v-else @click="operateUserInfo(item)">
					<!-- #endif -->
					<!-- #ifdef MP-WEIXIN -->
					<view class="list-items" v-else>
						<!-- #endif -->
						<text>{{item.title}}</text>
						<view class="list-button" @click="operateUserInfo(item)">
							<!-- #ifdef APP-PLUS -->

							<text @click="operateUserInfo(item)">{{userInfo.phone}}</text>
							<!-- #endif -->
							<!-- #ifdef MP-WEIXIN -->
							<button open-type="getPhoneNumber" @getphonenumber.stop="getphonenumber"
								class="list-button clear-button">
								<cover-view class="choose-avatar main-end-flex"
									style="font-size: 28rpx;color: #333333;">
									{{userInfo.phone}}
								</cover-view>
							</button>
							<!-- #endif -->
						</view>
					</view>
				</view>
			</view>
			<u-cell-group class="ugroup"
				:customStyle="{'margin-top': '30rpx', 'background': '#fff','fontSize': '28rpx','fontFamily': 'Source Han Sans CN','fontWeight': '400','color': '#333333'}">
				<view class="cell-container" v-for="(item,index) in cellList2">
					<u-cell :title="item.title" :isLink="item.isLink" :border="false" :key="index"
						@click="goDetail(item)">
						<view class="cell-slot" slot="value">
							<view v-if="item.value">
								{{item.value}}
							</view>
						</view>
					</u-cell>
					<view class="line-container">
						<view class="line"></view>
					</view>
				</view>
			</u-cell-group>
			<!-- #ifdef APP-PLUS -->
			<view class="login-out main-center-flex" @click="loginOut">
				退出登录
			</view>
			<!-- #endif -->
			<!-- #ifdef MP-WEIXIN -->
			<u-popup :show="avatar_chose" mode="bottom" @close="avatar_chose = !avatar_chose" ref="avatarPop">
				<!-- #endif -->
				<!-- #ifdef APP-PLUS -->

				<view class="footer" v-show="avatar_chose">
					<!-- #endif -->
					<view class="avatar-chose">
						<view class="">
							推荐使用头像
						</view>
						<view class="avatar-list">
							<view class="avatar-list list-item" v-for="(item,index) in avatarList" :key="index"
								@click="chosedAvatar=item" :style="{'background':item==chosedAvatar?'#EC872E':'gray'}">
								<u-avatar size="68rpx" :src="item">
								</u-avatar>
							</view>
						</view>
						<view class="avatar-sure" @click="sureChose">
							确定
						</view>
					</view>
					<!-- #ifdef APP-PLUS -->

				</view>
				<!-- #endif -->

				<!-- #ifdef MP-WEIXIN -->
			</u-popup>
			<!-- #endif -->
			<modal ref="modal"></modal>

			<view class="dialog" v-if="showVisible">
				<!-- #ifdef APP-PLUS -->

				<view class="containers">
					<view class="main">
						<view class="close" @click="showVisible=false"></view>
						<!-- #ifdef APP-PLUS -->
						<img class="head" src="../../static/image/modal-head.png" alt="" @click="showVisible=false">
						<!-- #endif -->
						<view class="body main-center-flex">
							<view class="content">
								<view class="title main-center-flex">
									{{title}}
								</view>
								<view class="content-detail">
									<u--input v-model="inputModel" border="none" placeholder="请填写昵称" fontSize="28rpx"
										color="#b0b0b0" inputAlign="center">
									</u--input>
								</view>
								<view class="edit-icon">
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
				<!-- #endif -->
			</view>
		</view>
</template>

<script>
	import baseUrl from '@/utils/siteInfo.js';
	import {
		get_user_info,
		set_user_info,
		getAvatar
	} from '@/api/my.js';
	export default {

		data() {
			return {
				currentType: '',
				isInput: false,
				content: '',
				inputModel: '',
				title: '',
				showVisible: false,
				chosedAvatar: null,
				avatarList: [],
				avatar_chose: false,
				userInfo: null,
				cellList: [{
						title: '头像',
						isLink: true,
						label: 'avatar',
					},
					{
						title: '昵称',
						isLink: true,
						label: 'nickname',
					},
					{
						title: '手机号',
						isLink: false,
						label: 'phone',
					}
				],
				cellList2: [{
						title: '隐私协议',
						isLink: true,
						adress: '/plugins/privacy-agreement/index'
					},
					{
						title: '平台交易规则',
						isLink: true,
						adress: '/plugins/transaction-rules/index'
					}
				],
			}
		},
		onLoad() {
			this.getUserInfo()
		},
		methods: {
			sureChose() {
				set_user_info({
					avatar: this.chosedAvatar,
					nickname: this.userInfo.nickname,
					phone: this.userInfo.phone
				}).then(res => {
					if (res.code == 0) {
						this.getUserInfo()
					}
					this.avatar_chose = false
					uni.$u.toast(res.msg)
				}).catch(err => {
					uni.$u.toast(err.msg)
				})
			},

			goDetail(item) {
				if (item.adress) {
					uni.navigateTo({
						url: item.adress
					})
				}
			},
			loginOut() {
				uni.setStorageSync('_USER_ACCESS_TOKEN', '')
				uni.switchTab({
					url: '/pages/index/index'
				})
			},
			getUserInfo() {
				get_user_info().then(res => {
					if (res.code == 0) {
						this.userInfo = res.data
						this.chosedAvatar = res.data.avatar
					}
				}).catch(err => {})
				getAvatar().then(res => {
					if (res.code == 0) {
						this.avatarList = res.data
					}

				})
			},


			changeAvatar(e) {
				console.log('1416-0', e)
			},
			getphonenumber(e) {
				console.log('1416-2', e)
			},
			changeUserInfo() {
				let {
					avatar,
					phone,
					nickname
				} = this.userInfo
				set_user_info({
					avatar,
					nickname,
					phone
				}).then(res => {
					if (res.code == 0) {
						this.getUserInfo()
						this.avatar_chose = false
					}
					uni.$u.toast(res.msg)
				}).catch(err => {
					uni.$u.toast(err.msg)
				})
			},
			operateUserInfo(e) {
				console.log(e, 'dangqian xuanz ')
				const that = this
				// #ifdef APP-PLUS
				that.currentType = e.label
				if (e.label == 'nickname') {
					that.title = '修改昵称'
					that.inputModel = that.userInfo.nickname
					that.showVisible = true
				} else if (e.label == 'avatar') {
					that.avatar_chose = true
				} else if (e.label == 'phone') {
					that.title = '修改手机号'
					that.inputModel = that.userInfo.phone
					that.showVisible = true
				}
				return
				// #endif
				if (e.label == "avatar") {
					that.avatar_chose = true
					return
				}
				if (e.label == 'nickname') {
					that.$refs.modal.showModal({
						title: '修改昵称', // e.name == 'nickname' ? '修改昵称' : '修改手机号',
						isInput: true,
						inputModel: that.userInfo.nickname,
						confirm: (e) => {
							that.userInfo.nickname = e
							that.changeUserInfo()
						}
					})
				}
			},
			confirm() {
				if (this.currentType == "nickname") {
					this.userInfo.nickname = this.inputModel
				} else {
					this.userInfo.phone = this.inputModel
				}
				this.changeUserInfo()
				this.showVisible = false
			},
		}
	}

</script>

<style scoped>
	.container {
		width: 750rpx;
		min-height: 100vh;
		background: #f3f3f3;
		padding: 30rpx;
	}

	.cell-slot {
		font-size: 24rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #999999;
	}

	::v-deep .u-cell__body {
		min-height: 105rpx !important;
	}

	.cell-container {
		position: relative;
		width: 100%;
	}

	.line-container {
		position: absolute;
		bottom: 0;
		width: 100%;
		height: 2rpx;
		padding: 0 30rpx;
	}

	.line {
		height: 100%;
		width: 100%;
		background: #F8F8F8;
	}

	.login-out {
		position: fixed;
		width: 690rpx;
		height: 84rpx;
		background: #3F3F42;
		bottom: calc(30rpx + env(safe-area-inset-bottom));
		left: 30rpx;
		font-size: 28rpx;
		font-family: Source Han Sans CN;
		font-weight: 400;
		color: #FFFFFF;
	}

	.choose-avatar {
		width: 220rpx;
		text-align: right;
		position: relative;
		right: 0;
	}

	.avatar-chose {
		wdith: 100%;
		padding: 20rpx;
		background: #FFFFFF;
		/* #ifdef APP-PLUS */
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;

		/* #endif */
	}

	.avatar-list {
		display: flex;
		justify-content: space-around;
		margin: 20rpx;
	}

	.avatar-sure {
		width: 680rpx;
		height: 86rpx;
		line-height: 86rpx;
		text-align: center;
		left: 0;
		right: 0;
		margin: 20rpx auto;
		background: #333;
		color: #fff;
	}

	.list-item {
		background: gray;
	}

	.head-img {
		width: 68rpx;
		height: 68rpx;
	}

	.list-content {
		background: #fff;
		padding: 24rpx;
	}

	.list-items {
		display: flex;
		justify-content: space-between;
		height: 128rpx;
		line-height: 128rpx;
		align-items: center;
		font-size: 28rpx;
		color: #333;
		background: #fff;
		border-bottom: 2rpx solid #F8F8F8;
	}

	.list-button {
		height: 66rpx;
		width: 230rpx;
		line-height: 66rpx;
		background: #fff;
		/* #ifdef APP-PLUS */
		text-align: right;
		/* #endif */
	}

	.ugroup {
		background: #FFFFFF;
		margin-top: 30rpx;
	}

	.footer {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		margin: auto;
		height: 100vh;
		background: rgba(0, 0, 0, 0.5);
		width: 100%;
	}

	.dialog {
		height: 100%;
		width: 100%;
		background: rgba(0, 0, 0, 0.5);
		position: absolute;
		top: 0;
		left: 0;
	}

	.containers {
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

	.edit-icon {
		width: 32rpx;
		height: 32rpx;
		position: absolute;
		top: 160rpx;
		right: 66rpx;
	}

</style>

