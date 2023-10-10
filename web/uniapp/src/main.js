import Vue from 'vue'
import App from './App'
import uView from "uview-ui";
import store from './store/index.js';
import user from './utils/user.js';
import login from './utils/login.js';
import appLayout from '@/components/app-layout/app-layout.vue';
import modal from '@/components/modal/index.vue';

Vue.component('app-layout', appLayout);
Vue.component('modal', modal);
Vue.config.productionTip = false

App.mpType = 'app'
Vue.use({
	install(Vue, options) {
		// Vue.prototype.$sysAppid = sysAppid; //appid
		Vue.prototype.$store = store;
		Vue.prototype.$user = user;
		Vue.prototype.$login = login;
		// Vue.prototype.$platform = platform;
		// Vue.prototype.$jwx = jwx;
		//Vue.prototype.$mallConfig = mallConfig; // 商城配置
	},
});
Vue.use(uView);

const app = new Vue({
	...App
})
app.$mount()

