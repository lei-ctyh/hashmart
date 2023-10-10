<!-- 初版 代和yu分支融合 -->
<template>
    <view class="app-coupon-modal main-center cross-center" v-if="coupon && coupon.list && coupon.list.length > 0">
        <view class="coupon-modal">
            <image class="title-img" :src="img"></image>
            <view class="get-coupon-content">
                <view v-if="coupon.type === `receive` && coupon.list[0].rest >= 0"
                      class="rest dir-left-nowrap main-center cross-center">
                    <view>还剩</view>
                    <view class="rest-count">{{coupon.list[0].rest}}</view>
                    <view>次领取次数</view>
                </view>
                <view class="invite coupon-head-label" v-if="coupon.type === `invite`">
                    <view>成功邀请{{coupon.list[0].id}}位好友，获得奖励</view>
                </view>
                <view v-else class="coupon-head-label">*{{labelText}}</view>
                <scroll-view scroll-y="true" class='coupon-list'>
                    <view class="dir-left-nowrap cross-center coupon-item" v-for="(item, index) in coupon.list"
                          :key="index">
                        <view class="price box-grow-0" >
                            <image v-if="item.share_type === 1" :src='commonImg.hongbao'/>
                            <image v-if="item.share_type === 2" src='/static/image/integral.png'/>
                            <image v-if="item.share_type === 3" :src="item.pic_url" class="card"/>
                            <block v-if="item.share_type === 4">
                                <template v-if="item.type == 2">
                                    <app-price :price="item.sub_price"></app-price>
                                </template>
                                <template v-else>
                                    <view class="discount">{{item.discount}}</view>
                                </template>
                            </block>
                        </view>
                        <view class="right dir-top-nowrap main-center box-grow-1">
                            <view v-if="[1,2,3].includes(item.share_type)"
                                  :class="[`t-omit${item.share_type === 3 ? '-two': ''}`]">{{item.name}}
                            </view>
                            <view v-else class="t-omit">{{item.name}}</view>

                            <view class="t-omit content">{{item.content}}</view>
                            <view class="content" v-if="item.discount_limit">优惠上限:￥{{item.discount_limit}}</view>
                        </view>
                        <view class="box-grow-0 btn"  @click="toGoods(item.page_url)">
                            去使用
                        </view>
                    </view>
                </scroll-view>
            </view>
            <view class='main-center' @click='closeCouponBox'>
                <image src='/static/image/icon/icon-popup-close.png' class="bottom-close"></image>
            </view>
        </view>
    </view>
</template>

<script>
    import {mapState} from "vuex";
    import appPrice from "../../../page-component/goods/app-price.vue";

    export default {
        name: "app-coupon-modal",
        components: {
            'app-price': appPrice,
        },
        data() {
            return {};
        },
        computed: {
            ...mapState({
                mallConfig: state => state.mallConfig,
                coupon: state => state.page.coupon,
				commonImg: state => state.mallConfig.__wxapp_img.common,
            }),
            labelText() {
                if (this.coupon && this.coupon.list && this.coupon.list.length) {
                    const first = this.coupon.list[0];
                    switch (first.share_type) {
                        case 4:
                            return '优惠券已发放到账户，请到我的优惠券查看';
                        case 2:
                            return '积分已发放到账户，请到我的积分查看';
                        case 1:
                            return '余额红包已发放到账户，请到我的余额查看';
                        case 3:
                            return '卡劵已发放到账户，请到我的卡劵查看';
                        default:
                            return '';
                    }
                }
            },
            img() {
                let img = '';
                if (this.coupon.type == 'register') {
                    img = this.mallConfig.__wxapp_img.coupon.get_coupon_title;
                } else if (this.coupon.type == 'share') {
                    img = this.mallConfig.__wxapp_img.coupon.get_coupon_share;
                } else if (this.coupon.type == 'receive') {
                    img = this.mallConfig.__wxapp_img.coupon.get_coupon_receive;
                } else if (this.coupon.type === 'award') {
                    img = this.mallConfig.__wxapp_img.coupon.get_coupon_award;
                }
                return img
            }
        },
        methods: {
            closeCouponBox() {
                let coupon = {
                    list: [],
                    type: ''
                };
                this.$store.dispatch('page/actionSetCoupon', coupon)
            },
            toGoods(page_url) {
                uni.navigateTo({
                    url: page_url
                });
                this.closeCouponBox();
            },
        }
    }
</script>

<style scoped lang="scss">
    .invite {
        text-align: center;
        margin-top: #{20px - 24px};

        view {
            padding: #{16rpx} #{24rpx};
            color: #ffffff;
            background: rgba(0, 0, 0, 0.2);
            font-size: #{26rpx};
            line-height: 1;
            display: inline-block;
            border-radius: #{34rpx};
        }
    }

    .invite-text {
        font-size: #{22rpx};
        line-height: 1;
        padding-top: #{16rpx};

        text:first-child {
            color: #ffee01;
            font-weight: bold;
        }
    }

    .app-coupon-modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        z-index: 1700;

        .coupon-modal {
            width: 100%;
            overflow: visible;
            margin-top: #{-50rpx};

            .title-img {
                width: #{750rpx};
                height: #{360rpx};
                display: block;
            }

            .get-coupon-content {
                width: #{580rpx};
                background: #ef3030;
                border-radius: 0 0 #{16rpx 16rpx};
                padding: #{24rpx 30rpx 30rpx 30rpx};
                margin: 0 auto;
                color: #ffffff;
                font-size: $uni-font-size-weak-two;

                .rest {
                    color: #ffffff;
                    font-size: $uni-font-size-general-one;
                    margin-bottom: #{24rpx};

                    .rest-count {
                        color: #EE3030;
                        font-size: $uni-font-size-import-one;
                        background-color: #ffffff;
                        padding: #{0 10rpx};
                        margin: #{0 10rpx};
                        border-radius: #{4rpx};
                    }
                }

                .coupon-head-label {
                    margin-bottom: #{16rpx};
                }

                .coupon-list {
                    max-height: #{312rpx};

                    view:first-child {
                        margin-top: 0;
                    }

                    .coupon-item {
                        width: 100%;
                        padding: #{0 32rpx};
                        margin-top: #{16rpx};
                        border-radius: #{16rpx};
                        background-color: #ffffff;
                        height: #{144rpx};

                        .price {
                            font-size: #{56rpx};
                            color: #ff4544;
                            .discount:after {
                                content: '折';
                                font-size: 50%;
                            }

                            image {
                                height: #{80rpx};
                                width: #{80rpx};
                                display: block;
                            }

                            .card {
                                border-radius: 50%;
                            }
                        }

                        .right {
                            margin-left: #{26rpx};
                            font-size: $uni-font-size-general-one;
                            color: $uni-important-color-black;

                            .content {
                                font-size: #{$uni-font-size-weak-two};
                                color: $uni-general-color-two;
                            }
                        }

                        .btn {
                            padding: #{12rpx 16rpx};
                            border-radius: #{50rpx};
                            font-size: $uni-font-size-weak-one;
                            margin-left: #{19rpx};
                            background-color: #ff4544;
                        }
                    }
                }
            }

            .bottom-close {
                width: #{30rpx};
                height: #{30rpx};
                margin-top: #{64rpx};
            }
        }
    }
</style>
