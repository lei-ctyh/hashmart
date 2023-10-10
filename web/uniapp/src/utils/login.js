import {
	mini_wx_login,
	login_by_phone,
	getUniapp
} from '../api/login.js';
const storageKey = '_USER_ACCESS_TOKEN';
// #ifdef MP-WEIXIN
const mpWeixinlogin = () => {
	return new Promise((resolve, reject) => {
		wx.getUserProfile({
			desc: "正在登录", // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
			success: (res) => {
				wx.getUserInfo({
					success: (res2) => {
						res2.rawData = res.rawData;
						let userInfoResult = {
							detail: res2,
						};
						let accessToken = uni.getStorageSync(storageKey);
						uni.showLoading({
							mask: true,
							title: '正在登录',
						});
						uni.login({
							scopes: 'auth_base',
							success(loginResult) {
								// 获取缓存信息
								mini_wx_login({
									code: loginResult.code
								}).then(response => {

									uni.hideLoading();
									if (response.code === 0) {

										uni.setStorageSync(
											storageKey, response
											.data.token);
										return resolve(response.data
											.token);
									} else {
										uni.showToast({
											icon: 'none',
											title: response
												.msg
										})

									}
								}).catch(e => {
									uni.hideLoading();
									reject(e);
								});
							},
							fail(error) {
								reject(error);
							}
						});
					},
					fail: (res2) => {},
				});
			},
			fail: (res) => {
				console.log("fail", res);
			},
		})

	})
}
// #endif

// #ifdef APP-PLUS
const appLogin = () => {
	uni.showLoading({
		title: '登录中'
	});
	uni.login({
		provider: 'univerify',
		univerifyStyle: {
			"fullScreen": true,
			"icon": {
				"path": "/static/image/logo.png", // 自定义显示在授权框中的logo，仅支持本地图片 默认显示App logo   
				"width": "176px",
				"height": "39px",
			},
			"authButton": {
				"normalColor": "#4F4F52", // 授权按钮正常状态背景颜色 默认值：#3479f5  
				"highlightColor": "#b70d0d", // 授权按钮按下状态背景颜色 默认值：#2861c5（仅ios支持）  
				"disabledColor": "#999999", // 授权按钮不可点击时背景颜色 默认值：#73aaf5（仅ios支持）  
				"textColor": "#ffffff", // 授权按钮文字颜色 默认值：#ffffff  
				"title": "本机号码一键登录", // 授权按钮文案 默认值：“本机号码一键登录”  
				"borderRadius": "10px" // 授权按钮圆角 默认值："24px" （按钮高度的一半）
			},

			"otherLoginButton": {
				"visible": "true", // 是否显示其他登录按钮，默认值：true
				"normalColor": "#FFFFFF", // 其他登录按钮正常状态背景颜色 默认值：#f8f8f8
				"highlightColor": "#dedede", // 其他登录按钮按下状态背景颜色 默认值：#dedede
				"textColor": "#999999", // 其他登录按钮文字颜色 默认值：#000000
				"title": "其他手机号码登录", // 其他登录方式按钮文字 默认值：“其他登录方式”
				"borderWidth": "1px", // 边框宽度 默认值：1px（仅ios支持）
				"borderColor": "#c5c5c5", //边框颜色 默认值： #c5c5c5（仅ios支持）
				"borderRadius": "10px" // 授权按钮圆角 默认值："24px" （按钮高度的一半）
			},


			"privacyTerms": {
				"defaultCheckBoxState": true, // 条款勾选框初始状态 默认值： true
				"uncheckedImage": "", // 可选 条款勾选框未选中状态图片（仅支持本地图片 建议尺寸 24x24px）(3.2.0+ 版本支持)   
				"checkedImage": "", // 可选 条款勾选框选中状态图片（仅支持本地图片 建议尺寸24x24px）(3.2.0+ 版本支持)   
				"checkBoxSize": 12, // 可选 条款勾选框大小，仅android支持
				"textColor": "#BBBBBB", // 文字颜色 默认值：#BBBBBB  
				"termsColor": "#5496E3", //  协议文字颜色 默认值： #5496E3  
				"prefix": "我已阅读并同意", // 条款前的文案 默认值：“我已阅读并同意”  
				"suffix": "并使用本机号码登录", // 条款后的文案 默认值：“并使用本机号码登录”  
				"privacyItems": [ // 自定义协议条款，最大支持2个，需要同时设置url和title. 否则不生效  
					{
						"url": "http://showapp.mstshop.cn/protocol/index.html", // 点击跳转的协议详情页面  
						"title": "用户协议" // 协议名称  
					}
				]
			},
			"buttons": { // 自定义页面下方按钮仅全屏模式生效（3.1.14+ 版本支持）
				"iconWidth": "45px", // 图标宽度（高度等比例缩放） 默认值：45px
				"list": [{
					"provider": "weixin",
					"iconPath": "/static/image/login-wx.png" // 图标路径仅支持本地图片
				}]
			}
		},
		success: res => {
			uni.hideLoading();
			if (res.errMsg == 'login:ok') {
				getUniapp().then(result => {
					//执行云函数
					uniCloud.callFunction({
						name: 'getPhoneNumberHash',
						data: {
							queryStringParameters: {
								appid: result.data
									.uniapp_appid, // 替换成自己开通一键登录的应用的DCloud appid
								provider: 'univerify',
								apiKey: result.data
									.uniapp_api_key, // 在开发者中心开通服务并获取apiKey
								apiSecret: result.data
									.uniapp_api_secret, // 在开发者中心开通服务并获取apiSecret
								// 客户端一键登录接口返回的access_token
								access_token: res.authResult.access_token,
								// 客户端一键登录接口返回的openid
								openid: res.authResult.openid
							}
						}
					}).then(res_num => {
						//关闭弹框事件
						if (res_num.result.code == 0) {
							//获取的手机号
							//do somthing...
							login_by_phone({
								// 客户端一键登录接口返回的access_token
								access_token: res.authResult.access_token,
								// 客户端一键登录接口返回的openid
								openid: res.authResult.openid
							}).then(res_login => {
								console.log('res_login', res_login, )
								if (res_login.code == 0) {
									uni.setStorageSync('_USER_ACCESS_TOKEN',
										res_login.data.token);
								}

								uni.$u.toast(res_login.msg)
								uni.switchTab({
									url: '/pages/index/index'
								})
								uni.closeAuthView();

							}).catch(err_login => {
								uni.$u.toast(err_login.msg)
								uni.closeAuthView();
							})
						} else {
							uni.showToast({
								icon: 'none',
								title: '授权失败~'
							});
						}
						//if end
					}).catch(msg => {
						//关闭弹框事件
						uni.closeAuthView();
						uni.hideLoading();
						console.error(msg);
					});
				})
			} else {
				//关闭弹框事件
				uni.closeAuthView();
				uni.hideLoading();
				uni.showToast({
					icon: 'none',
					title: res['errMsg']
				});
			}
		},
		fail: res => {
			uni.hideLoading();
			if (res.errCode == '30002') {}
			if (res.errCode == '30008') {}
			uni.navigateTo({
				url: '/plugins/login/phone-number/index?type=' + 'phone'
			})
		}
	});

}
// #endif

export default {
	// #ifdef MP-WEIXIN
	mpWeixinlogin,
	// #endif
	// #ifdef APP-PLUS
	appLogin,
	// #endif
}

