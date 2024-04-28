<template>
  <view class="container">
    <view class="head">
      <view class="head-content">
        <view class="head-left">
          <view class="">
            <u-icon name="map" color="#2979ff" size="28"></u-icon>
          </view>
          <view class="head-title" v-if="default_address.receiver">
            <view class="head-name">
              {{ default_address.receiver }}
              <text class="number">{{ default_address.phone }}</text>
            </view>
            <view class="number">
              {{ default_address.province_name }}{{ default_address.city_name }}{{ default_address.area_name }}{{ default_address.address }}
            </view>
          </view>
          <view class="head-title" v-else>
            <view class="head-name">
              请先添加地址
            </view>
          </view>
        </view>

        <view class="">
          <u-icon name="arrow-right" color="gray" size="28" @click="choseAddress"></u-icon>
        </view>
      </view>
    </view>
    <view class="list-container">
      <view class="list-content">
        <view class="list-item" v-for="(item,index) in goods_list" :key="index">
          <view class="list-body main-space-between">
            <view class="list-body-left main-center-flex">
              <u--image :showLoading="true" :src="item.goods_image" width="160rpx"
                        height="160rpx"></u--image>
            </view>
            <view class="list-body-right">
              <view class="goods-name text-ellipsis_2">
                {{ item.goods_name }}
              </view>
              <view class="exchange">
                可兑换
                <text>{{ item.recovery_price }}</text>
                哈希币
              </view>
            </view>
          </view>
        </view>
      </view>

      <view class="fare">
        <view class="main-space-between fare-item">
          <view class="">
            运费
          </view>
          <view class="money">
            ￥{{ fare }}
          </view>
        </view>
        <!-- 	<view class="main-space-between fare-item">
          <view class="">
            订单备注
          </view>
          <view class="main-space-between">
            <view class="remarks" @click="addRemarks">
              {{remarks}}
            </view>
            <u-icon name="arrow-right" color="gray" size="28rpx"></u-icon>
          </view>
        </view> -->
        <view class="fare-item fare-bottom">
          共{{ goods_list.length }}件 | 合计：
          <text class="all-money">￥{{ fare }}</text>
        </view>

      </view>
      <view class="pay-type" v-if="fare!=0">
        <view class="pay-item">
          支付方式
        </view>
        <view class="main-space-between pay-item">
          <view class="pay-banlance main-space-between">
            <!-- #ifdef MP-WEIXIN -->
            <image src="../../static/image/pay/wx_pay.png" mode="" class="pay-img"></image>
            <!-- #endif -->
            <!-- #ifdef APP-PLUS -->
            <img class="pay-img" src="../../static/image/pay/wx_pay.png" alt="">
            <!-- #endif -->
            微信
          </view>
          <view class="ban-pay">
            <u-radio-group v-model="payType">
              <u-radio shape="circle" name="1"></u-radio>
            </u-radio-group>
          </view>
        </view>
        <!-- #ifdef APP-PLUS -->
        <view class="main-space-between pay-item">
          <view class="pay-banlance main-space-between">
            <!-- #ifdef MP-WEIXIN -->
            <image src="../../static/image/pay/ali_png.png" mode="" class="pay-img"></image>
            <!-- #endif -->
            <!-- #ifdef APP-PLUS -->
            <img class="pay-img" src="../../static/image/pay/ali_png.png" alt="">
            <!-- #endif -->
            支付宝
          </view>
          <view class="ban-pay">
            <u-radio-group v-model="payType">
              <u-radio shape="circle" name="2"></u-radio>
            </u-radio-group>
          </view>
        </view>
        <!-- #endif -->
        <view class="main-space-between pay-item">
          <view class="pay-banlance main-space-between">

            <!-- #ifdef MP-WEIXIN -->
            <image src="../../static/image/icon/banlance_pay.png" mode="" class="pay-img"></image>
            <!-- #endif -->
            <!-- #ifdef APP-PLUS -->
            <img class="pay-img" src="../../static/image/icon/banlance_pay.png" alt="">
            <!-- #endif -->
            余额
          </view>
          <view class="ban-pay">
            <u-radio-group v-model="payType">
              <u-radio shape="circle" name="4"></u-radio>
            </u-radio-group>
          </view>
        </view>
      </view>
      <view class="tip-info">
        <view>
          若完成交易代表您已同意以下约定：
        </view>
        <view>
          1、港澳台地区及部分偏远地区会无法配送
        </view>
        <view>
          2、受疫情等因素影响，多地物流公司接单和派送受影响，物流时效性有所延长，还请谅解，如有疑问请随时联系在线客服咨询。
        </view>
        <view>
          3、由于显示器，拍照和做图的过程中，产品可能发生颜色偏差，具体请以实物为准！
        </view>
      </view>
    </view>

    <view class="footer main-center-flex">
      <view class="left main-center-flex">

      </view>
      <view class="right main-center-flex">
        <view class="total">

        </view>
        <view class="pickBtn" @click="pickSure">
          确认提货
        </view>
      </view>
    </view>
    <modal ref="modal"></modal>
  </view>
</template>

<script>
import {
  mapGetters,
  mapState
} from 'vuex';
import {
  address_list
} from '@/api/address.js'
import {
  bagBoxDeliver,
  bagBoxTrail,
  // bagBoxCreateOrder
} from '@/api/warehouse.js'

export default {
  data() {
    return {
      remarks: '无备注',
      fare: 0,
      payType: '1',
      default_address: {},
      total: 0,
      choseAll: false,
      goods_list: [],
      refreshIfNeeded: false,
      type: null,
      status: null
    }
  },
  onShow() {
    this.start()

  },

  onLoad(parms) {
    this.goods_list = JSON.parse(parms.list)
    this.type = parms.type
    console.log(this.type)
    this.status = parms.status
    for (var i = 0; i < this.goods_list.length; i++) {
      this.goods_list[i].giveChecked = false
    }


  },
  methods: {
    addRemarks() {
      this.$refs.modal.showModal({
        title: '添加备注',
        isInput: true,
        inputModel: this.remarks,
        confirm: (e) => {
          this.remarks = e

        }
      })
    },
    async start() {
      await address_list().then(res => {
        if (res.code == 0) {
          for (var i = 0; i < res.data.length; i++) {
            if (res.data[i].default_flag == '1') {
              this.default_address = res.data[i]
            }
          }
        }
      }).catch(err => {
      })
      let box_id = this.goods_list.map(item => {
        return item.id
      })
      await bagBoxTrail({
        box_id: box_id.join(','),
        address_id: this.default_address.id,
        type: this.type
      }).then(res => {
        if (res.code == 0) {
          this.fare = res.data
        }
      })
    },
    choseAddress() {
      uni.navigateTo({
        url: '/plugins/address/index'
      })
    },
    pickSure() {
      const that = this
      let box_id = that.goods_list.map(item => {
        return item.id
      })
      let platform = ''
      // #ifdef APP-PLUS
      platform = 'app'
      // #endif
      // #ifdef MP-WEIXIN
      platform = 'miniapp'
      // #endif
      bagBoxDeliver({
        box_id: box_id.join(','),
        address_id: that.default_address.id,
        pay_way: that.payType,
        type: that.type,
        platform: platform
      }).then(res => {
        if (res.code == 0) {
          that.$refs.modal.showModal({
            title: '操作成功，正在为您准备发货',
            confirm: () => {
              uni.switchTab({
                url: '/pages/warehouse/index',
                success: function (result) {
                  console.log(result)
                  getApp().globalData.paramsData = {
                    type: that.type,
                    status: that.status,
                    page: 1
                  };
                }
              })
            }
          })
        } else if (res.code == 201) {
          wx.requestPayment({
            'timeStamp': res.data.timeStamp,
            'nonceStr': res.data.nonceStr,
            'package': res.data.package,
            'signType': res.data.signType,
            'paySign': res.data.paySign,
            'success': function (res) {
              console.log(res, '支付结果')
              that.$refs.modal.showModal({
                title: '支付成功，正在为您准备发货',
                confirm: () => {
                  uni.switchTab({
                    url: '/pages/warehouse/index',
                    success: function (result) {
                      console.log(result)
                      getApp().globalData.paramsData = {
                        type: that.type,
                        status: that.status,
                        page: 1
                      };
                    }
                  })
                }
              })
            },
            'fail': function (res) {
              uni.showToast({
                title: '支付失败',
                icon: 'none'
              })
            }
          })

        } else {
          uni.showToast({
            title: res.msg,
            icon: 'none'
          })
        }
      })
    },
    // pay(val) {
    // 	let that = this
    // 	let platform = ''
    // 	// #ifdef APP-PLUS
    // 	platform = 'app'
    // 	// #endif
    // 	// #ifdef MP-WEIXIN
    // 	platform = 'miniapp'
    // 	// #endif
    // 	bagBoxDeliver({
    // 		pay_no: val,
    // 		platform: platform,
    // 		type: that.type
    // 	}).then(res => {
    // 		console.log(res, '支付结果')
    // 		if (res.code == 0) {
    // 			that.$refs.modal.showModal({
    // 				title: '操作成功，正在为您准备发货',
    // 				confirm: () => {
    // 					uni.switchTab({
    // 						url: '/pages/warehouse/index',
    // 						success: function(res) {
    // 							console.log(res)
    // 							getApp().globalData.paramsData = {
    // 								type: that.type,
    // 								status: that.status,
    // 								page: 1
    // 							};
    // 						}
    // 					})
    // 				}
    // 			})
    // 		} else if (res.code == 201) {
    // 			console.log('支付')
    // 			// #ifdef MP
    // 			uni.requestPayment({
    // 				provider: 'wxpay', //支付类型-固定值
    // 				timeStamp: res.data.timeStamp, // 时间戳（单位：秒）
    // 				nonceStr: res.data.nonceStr, // 随机字符串
    // 				package: res.data.package, // 固定值
    // 				signType: res.data.signType, //固定值
    // 				paySign: res.data.paySign, //签名
    // 				success: function(res) {
    // 					console.log(res, '支付结果')
    // 					that.$refs.modal.showModal({
    // 						title: '支付成功，正在为您准备发货',
    // 						confirm: () => {
    // 							uni.switchTab({
    // 								url: '/pages/warehouse/index',
    // 								success: function(res) {
    // 									console.log(res)
    // 									getApp().globalData
    // 										.paramsData = {
    // 											type: that.type,
    // 											status: that.status,
    // 											page: 1
    // 										};
    // 								}
    // 							})
    // 						}
    // 					})
    // 				},
    // 				fail(res) {
    // 					uni.showToast({
    // 						title: '支付失败',
    // 						icon: 'none'
    // 					})
    // 				}
    // 			})
    // 			// #endif
    // 		} else {
    // 			uni.showToast({
    // 				title: res.msg,
    // 				icon: 'none'
    // 			})
    // 		}
    // 	})
    // },
    choseChange() {
      this.choseAll = !this.choseAll
      for (var i = 0; i < this.goods_list.length; i++) {
        this.goods_list[i].giveChecked = this.choseAll
      }
      if (this.choseAll) {
        this.total = this.goods_list.length
      } else {
        this.total = 0
      }
    },
    checkedChange(item) {
      let choseLength = 0
      for (var i = 0; i < this.goods_list.length; i++) {
        if (this.goods_list[i] == item) {
          this.goods_list[i].giveChecked = !this.goods_list[i].giveChecked
        }
        if (this.goods_list[i].giveChecked) {
          choseLength = choseLength + 1
        }
      }

      if (choseLength == this.goods_list.length) {
        this.choseAll = true
      } else {
        this.choseAll = false
      }
      this.total = choseLength

    },
  }
}

</script>

<style scoped>
.container {
  width: 750rpx;
  min-height: 100vh;
  background: linear-gradient(90deg, #F2F0FF, #EDEBFF, #F3F8FF);
}

.head {
  padding: 30rpx 30rpx 0 30rpx;
}

.head-content {
  background: #fff;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: space-between;
  padding: 0 20rpx;
  height: 150rpx;

}

.head-left {
  display: flex;
  align-items: center;
}

.head-title {
  text-align: left;
}

.head-name {
  font-size: 28rpx;
  font-weight: 600;
}

.number {
  font-weight: 500;
  font-size: 24rpx;
}

.list-container {
  width: 100%;
  height: 70%;
  padding: 30rpx;
  padding-bottom: 200rpx;
}

.list-item {
  width: 100%;
  background: #fff;
}


.list-body {
  height: 235rpx;
  width: 100%;
  padding: 30rpx;
}

.list-body-left {
  height: 100%;
  width: 175rpx;
}

.list-body-right {
  height: 100%;
  width: calc(100% - 175rpx);
  padding: 10rpx;
}

.exchange {
  font-size: 24rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #EC872E;
  margin-top: 30rpx;
}

.total {
  width: 150rpx;
}

.list-content {
  max-height: 500rpx;
  overflow-y: auto;
}

.fare {
  background: #fff;
  border-top: 2rpx solid #e6f1f1;
  padding: 20rpx;
  font-size: 28rpx;
}

.fare-item {
  height: 60rpx;
  line-height: 60rpx;
}

.fare-bottom {
  text-align: right;
}

.all-money {
  font-size: 40rpx;
  color: #333;
  font-weight: 600;
}

.remarks {
  color: darkgray;
}

.money {
  color: red;
}

.pay-type {
  background: #fff;
  margin-top: 30rpx;
  padding: 30rpx;
}

.pay-item {
  line-height: 30px;
  height: 60rpx;
  font-size: 32rpx;
  color: #333;
  font-weight: 600;
  margin-bottom: 30rpx;
}

.pay-banlance {
  font-size: 28rpx;
  font-weight: 500;

}

.pay-img {
  width: 32rpx;
  height: 32rpx;
  margin-right: 20rpx;
}

.tip-info {
  margin-top: 20rpx;
  font-size: 28rpx;
  color: gray;
}

.footer {
  position: fixed;
  bottom: 0;
  /* height: 136rpx; */
  height: 10%;
  display: flex;
  width: 100%;
  justify-content: space-between;
  padding: 20rpx;
  background: #fff;
  font-size: 28rpx;
}

.pickBtn {
  border: 2rpx solid gray;
  text-align: center;
  height: 60rpx;
  line-height: 60rpx;
  font-size: 28rpx;
  background: gray;
  color: #fff;
  width: 160rpx;

}

.goods-price {
  height: 50%;
  width: 100%;
}

.goods-name {
  font-size: 28rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #333333;
}

.goods-price {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.goods-price > view:first-child {
  font-size: 34rpx;
  font-family: Abel;
  font-weight: 400;
  color: #333333;
}

.goods-price > view:first-child text {
  font-size: 20rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #333333;
}

.goods-price > view:last-child {
  font-size: 20rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #999999;
}

.list-foot {
  height: 150rpx;
  width: 100%;
  padding: 30rpx;
}

.goods-num {
  font-size: 24rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #999999;
}

.goods-total-price {
  font-size: 24rpx;
  font-family: Source Han Sans CN;
  font-weight: 400;
  color: #333333;
  align-items: baseline;
}

.goods-total-price > text:last-child {
  font-size: 40rpx;
  font-family: Abel;
  font-weight: 400;
  color: #333333;
}

.cancel-order,
.confirm-receive {
  border: 2rpx solid #F8F8F8;
}

</style>

