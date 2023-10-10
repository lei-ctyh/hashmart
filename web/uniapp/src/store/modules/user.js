import $user from '@/utils/user.js'

// 这里存放vuex属性
const state = {
	accessToken: '',

};

const getters = {};

const mutations = {
	TOKEN_ACCESS_MUT(state, data) {
		state.accessToken = data
	}
};

const actions = {
	USER_CONFIG(context, data) {
		if ($user.isLogin()) {
			context.commit('TOKEN_ACCESS_MUT', $user.isLogin())
		}
	},
};

export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
}

