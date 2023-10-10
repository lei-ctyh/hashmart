<template>
  <view class="app-mandatory-attention">
      <u-popup v-model="newValue" mode="center" :maskCloseAble="false" border-radius="14" :safeAreaInsetBottom="true" >
          <view class="bd-model" @touchmove.stop.prevent>
<!--               <view class="bd-h5" v-if="!isWechat">-->
<!--                   需要关注公众号才购买-->
<!--               </view>-->
              <view class="bd-wechat cross-center dir-top-nowrap" v-if="isWechat">
                  <view class="bd-title">关注公众号</view>
                  <image class="bd-logo" :src="userInfo && userInfo.wechat_logo"></image>
                  <view class="bd-name">{{userInfo.wechat_name}}</view>
                  <image :src="userInfo && userInfo.qrcode" class="bd-qrcode"></image>
                  <view class="bd-info">长按识别二维码关注公众号</view>
              </view>
              <view class="bd-btn" @click="close">
                  确认关注
              </view>
          </view>
      </u-popup>
  </view>
</template>

<script>
import uPopup from '../../u-popup/u-popup.vue';
import {mapGetters} from "vuex";

export default {
  name: "app-mandatory-attention",
    data() {
        return {
            newValue: true,
            isWechat: false
        }
    },
    computed: {
        ...mapGetters({
            userInfo: 'user/info',
            showAttentionTwo: 'user/showAttentionTwo',
        }),
    },
    methods: {
        close() {
            if (this.showAttentionTwo) {
                this.$user.getInfo({
                    refresh: true
                }).then(() => {
                    this.newValue = false;
                });
                this.$store.dispatch('user/showAttentionTwo', false);
            } else {
                this.$request({
                    url: this.$api.registered.update,
                    method: 'get'
                }).then(response => {
                    if (response.code === 0) {
                        if (response.data.subscribe === 1) {
                            this.$user.getInfo({
                                refresh: true
                            }).then(() => {
                                this.newValue = false;
                            });
                        } else {
                            uni.showToast({
                                icon: 'none',
                                title: '请关注'
                            });
                        }
                    }
                });
            }
        }
    },
    created() {
        this.isWechat = this.$jwx.isWechat();
    },
    components: {
        uPopup
    },
    watch: {
      newValue: {
          handler(newVal) {
              if (newVal === false) {
                  this.$store.dispatch('user/showAttention', false);
              }
          }
      }
    }
}
</script>

<style scoped lang="scss">
.bd-model {
    background-color: #ffffff;
    width: 630upx;
}
.bd-btn {
    font-size: 30upx;
    border-top: 1upx solid #f1f1f1;
    color: #ff4544;
    line-height: 88upx;
    text-align: center;
}
.bd-h5 {
    font-size: 32upx;
    color: #353535;
    text-align: center;
    line-height: 204upx;
}
.bd-wechat {
    .bd-title {
        font-size: 32upx;
        color: #353535;
        text-align: center;
        margin-top: 40upx;
    }
    .bd-logo {
        width: 110upx;
        height: 110upx;
        margin-bottom: 10upx;
        margin-top:30upx;
    }
    .bd-name {
        font-size: 32upx;
        margin-top: 15upx;
        margin-bottom:20upx;
        color: #353535;
    }
    .bd-qrcode {
        width:330upx;
        height:330upx;
        margin: 10upx 0;
    }
    .bd-info {
        font-size:22upx;
        color: #353535;
        margin-bottom: 40upx;
        margin-top:10upx;
    }
}
</style>