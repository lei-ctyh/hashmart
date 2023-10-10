<template>
    <u-popup v-model="show" mode="center" border-radius="16" :length="630" :maskCloseAble="false">
        <view class="u-content" >
            <view class='u-header'>授权获取手机号</view>
            <view class="u-body dir-top-nowrap cross-center">
                <image class="u-img" :src="img"></image>
                <text class="u-text">申请获取您绑定的手机号</text>
                <!--#ifndef MP-ALIPAY-->
                <button
                    hover-class="u-hover-class"
                    class="u-btn"
                    open-type="getPhoneNumber"
                    @getphonenumber="getPhoneNumber"
                >确认</button>
                <!--#endif-->
                <!--#ifdef MP-ALIPAY-->
                <button
                    class="u-btn"
                    hover-class="u-hover-class"
                    open-type="getAuthorize"
                    scope='phoneNumber'
                    @getAuthorize="onGetAuthorize"
                >点击授权</button>
                <!--#endif-->
            </view>
        </view>
    </u-popup>
</template>

<script>
    import {mapState} from "vuex";
    import uPopup from '../../u-popup/u-popup.vue';

    export default {
        name: "u-authorized-iphone",
        computed: {
            ...mapState({
                _app_config: state => state.mallConfig
            }),
            showPhone() {
                return this.$store.state.user.info;
            },
            img() {
                let img = '';
                // #ifdef MP-WEIXIN
                img = this._app_config.__wxapp_img.mall.icon_wechat;
                // #endif
                // #ifdef MP-ALIPAY
                img = this._app_config.__wxapp_img.mall.icon_alipay;
                // #endif
                // #ifdef MP-TOUTIAO
                img = this._app_config.__wxapp_img.mall.icon_ttapp;
                // #endif
                return img;
            }
        },
        data() {
            return {
                // #ifndef MP-ALIPAY
                code: null,
                // #endif
                show: false
            };
        },
        watch: {
            showPhone: {
                handler(newVal) {
                    newVal && this.$validation.isEmpty(newVal.mobile) ? this.show = true : this.show = false;
                },
                immediate: true
            }
        },
        created() {
            // #ifndef MP-ALIPAY
            let _this= this;
            uni.login({
                scopes: 'auth_base',
                success(res) {
                    if (res.errMsg === 'login:ok') {
                        _this.code = res.code;
                    }
                }
            })
            // #endif
        },
        destroyed() {
            this.show = false;
        },
        methods: {
            // #ifndef MP-ALIPAY
            getPhoneNumber(e) {
                if (e.detail.errMsg === 'getPhoneNumber:fail user deny') return;
                this.$request({
                    method: 'post',
                    url: this.$api.phone.binding,
                    data: {
                        encryptedData: e.detail.encryptedData,
                        iv: e.detail.iv,
                        code: this.code
                    }
                }).then(() => {
                    this.show = false;
                    this.$store.dispatch('user/refresh');
                });
            },
            // #endif
            // #ifdef MP-ALIPAY
            onGetAuthorize() {
                let _this = this;
                my.getPhoneNumber({
                    success: (res) => {
                        this.$request({
                            method: 'post',
                            url: _this.$api.phone.binding,
                            data: {
                                data: JSON.parse(res.response).response,
                            }
                        }).then(() => {
                            _this.show = false;
                            _this.$store.dispatch('user/refresh');
                        });
                    },
                    fail: () => {
                    }
                });
            }
            // #endif
        },
        components: {
            uPopup
        }
    }
</script>

<style scoped lang="scss">
    .u-content {
        background-color: #ffffff;
    }

    .u-header {
        text-align: center;
        padding: 30upx 0;
        line-height: 60upx;
        border-bottom: 1upx solid #eeeeee;
        color: #353535;
        font-size: 35upx;
    }
    .u-img {
        width: 88upx;
        height: 88upx;
        text-align: center;
        margin: 32upx 0 40upx;
    }
    .u-body {
        padding: 0 24upx;

    }
    .u-text {
        margin-bottom: 40upx;
        font-size: 26upx;
        color: #666666;
    }
    .u-btn {
        background-color: #04be01;
        width: 500upx;
        height: 80upx;
        line-height: 80upx;
        border-radius: 80upx;
        color: #ffffff;
        font-size: 30upx;
        margin-bottom: 40upx;
        padding: 0;
    }
</style>