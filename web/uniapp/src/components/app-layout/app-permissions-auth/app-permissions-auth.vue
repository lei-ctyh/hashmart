<template>
    <view v-if='isShow' class="permissions show bt">
        <view class="permissions-bg"></view>
        <view class="permissions-pic main-center">
            <view class="info-model cross-center dir-top-nowrap">
                <view class="order-title">提示</view>
                <view class="info-box dir-left-nowrap cross-center">
                    {{text}}
                </view>
                <view class="info-end dir-left-nowrap cross-center">
                    <view @click='cancel' class="box-grow-1 main-center cross-center">取消</view>
                    <view class="box-grow-0 info-line"></view>
                    <view class="box-grow-1 red main-center cross-center">
                        <button @click='cancel' open-type="openSetting" class="btn">去设置</button>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        name: 'app-permissions-auth',
        data() {
            return {}
        },
        props: {
            isShow: {
                type: Boolean,
                default: false,
            },
            text: {
                type: String,
                default: '请在设置中打开相应权限',
            }
        },
        methods: {
            cancel() {
                this.$emit('cancel', false);
            },
        }
    }
</script>

<style lang="scss" scoped>
    .permissions {
        position: fixed;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        z-index: 1000;
        transition: 200ms;
    }

    .permissions.show {
        visibility: visible;
        opacity: 1;
    }

    .permissions.bt {
        -webkit-animation-name: fadeIn; /*动画名称*/
        -webkit-animation-duration: 0.25s; /*动画持续时间*/
        -webkit-animation-iteration-count: 1; /*动画次数*/
        -webkit-animation-delay: 0s; /*延迟时间*/
    }

    .permissions .permissions-bg {
        background: rgba(0, 0, 0, 0.8);
        position: fixed;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        z-index: 1;
    }

    .permissions .permissions-pic {
        position: fixed;
        left: 0;
        top: #{188rpx};
        width: 100%;
        height: 100%;
        z-index: 1;
    }

    .permissions .permissions-close image {
        width: #{50rpx};
        height: #{50rpx};
        margin-top: #{50rpx};
    }

    .permissions .info-model {
        height: #{360rpx};
        width: #{620rpx};
        background: #fff;
        border-radius: #{16rpx};
    }

    .permissions .order-title {
        margin: #{48rpx} 0
    }

    .permissions .place {
        color: #cdcdcd
    }

    .permissions .info-box {
        height: #{88rpx};
        margin-bottom: #{48rpx};
        padding: 0 60rpx;
    }

    .permissions .info-input {
        font-size: #{32rpx};
        color: #353535;
    }

    .permissions .info-line {
        height: #{32rpx} !important;
        width: 1px !important;
        background: #e2e2e2;
    }

    .permissions .info-end {
        color: #666666;
        height: #{88rpx};
        font-size: #{32rpx};
        border-top: #{1rpx} solid #e2e2e2;
        width: 100%;
    }

    .permissions .info-end view {
        height: 100%;
        width: 100%;
    }

    button::after {
        border: none;
        content: '';
    }

    .btn {
        border: none;
        padding: 0;
        background-color: #fff;
        font-size: 32#{rpx};
        color: $uni-general-color-one;
    }
</style>