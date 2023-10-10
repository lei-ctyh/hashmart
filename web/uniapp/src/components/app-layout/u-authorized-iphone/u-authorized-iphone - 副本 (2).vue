<template>
   <real-name-modal :realNameModal="realNameModal" :goPhone="true" @getPhoneNumber="getPhoneNumber" @displayRealNameModal="displayRealNameModal"></real-name-modal>
</template>

<script>
    import {mapState} from "vuex";
    import	RealNameModal from '../../../page-component/real-name-modal/index.vue'

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
                show: false,
				realNameModal:true
            };
        },
        watch: {
            showPhone: {
                handler(newVal) {
                    newVal && this.$validation.isEmpty(newVal.mobile) ? this.realNameModal = true : this.realNameModal = false;
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
            this.realNameModal = false;
        },
        methods: {
			displayRealNameModal(){
				this.$emit('displayPhone')
			},
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
                    this.realNameModal = false;
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
            RealNameModal
        }
    }
</script>

<style scoped lang="scss">
</style>