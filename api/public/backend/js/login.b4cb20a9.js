"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[4535],{3645:function(t,a,e){e.r(a),e.d(a,{default:function(){return p}});var i=e(6252),s=e(3577);const o=t=>((0,i.dD)("data-v-7c1c82f6"),t=t(),(0,i.Cn)(),t),n={class:"login_bg"},c={class:"login_adv",style:{"background-image":"url(img/auth_banner.jpg)"}},l={class:"login_adv__title"},d=o((()=>(0,i._)("h2",null,"HashMart",-1))),r=o((()=>(0,i._)("div",{class:"login_adv__mask"},null,-1))),g=o((()=>(0,i._)("div",{class:"login_adv__bottom"}," © V1.0.1 ",-1))),h={class:"login_main"},m={class:"login-form"},u={class:"login-header"},_={class:"logo"},v=["alt"],L=o((()=>(0,i._)("label",null,"HashMart",-1)));function O(t,a,e,o,O,f){const $=(0,i.up)("password-form");return(0,i.wg)(),(0,i.iD)("div",n,[(0,i._)("div",c,[(0,i._)("div",l,[d,(0,i._)("h4",null,(0,s.zw)(t.$t("login.slogan")),1)]),r,g]),(0,i._)("div",h,[(0,i._)("div",m,[(0,i._)("div",u,[(0,i._)("div",_,[(0,i._)("img",{alt:t.$CONFIG.APP_NAME,src:"img/logo.png"},null,8,v),L])]),(0,i.Wm)($)])])])}var f=e(6471),$={components:{passwordForm:f["default"]},data(){return{config:{lang:this.$TOOL.data.get("APP_LANG")||this.$CONFIG.LANG,dark:this.$TOOL.data.get("APP_DARK")||!1},lang:[{name:"简体中文",value:"zh-cn"},{name:"English",value:"en"}],WechatLoginCode:"",showWechatLogin:!1,isWechatLoginResult:!1}},watch:{"config.dark"(t){t?(document.documentElement.classList.add("dark"),this.$TOOL.data.set("APP_DARK",t)):(document.documentElement.classList.remove("dark"),this.$TOOL.data.remove("APP_DARK"))},"config.lang"(t){this.$i18n.locale=t,this.$TOOL.data.set("APP_LANG",t)}},created:function(){this.$TOOL.cookie.remove("TOKEN"),this.$TOOL.data.remove("USER_INFO"),this.$TOOL.data.remove("MENU"),this.$TOOL.data.remove("PERMISSIONS"),this.$TOOL.data.remove("grid"),this.$store.commit("clearViewTags"),this.$store.commit("clearKeepLive"),this.$store.commit("clearIframeList"),console.log("%c SCUI %c Gitee: https://gitee.com/lolicode/scui","background:#666;color:#fff;border-radius:3px;","")},methods:{configDark(){this.config.dark=!this.config.dark},configLang(t){this.config.lang=t.value},wechatLogin(){this.showWechatLogin=!0,this.WechatLoginCode="SCUI-823677237287236-"+(new Date).getTime(),this.isWechatLoginResult=!1,setTimeout((()=>{this.isWechatLoginResult=!0}),3e3)}}},k=e(3744);const T=(0,k.Z)($,[["render",O],["__scopeId","data-v-7c1c82f6"]]);var p=T}}]);