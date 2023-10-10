import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

import phone from './modules/phone.js';
import user from './modules/user.js';
import param from './modules/param.js';
const store = new Vuex.Store({
	modules: {
		phone,
		user,
		param
	}
});

export default store;

