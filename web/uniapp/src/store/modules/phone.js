const state = {
	phoneNavHeight: '',
};

const getters = {
	phoneHieght(state) {
		return parseInt(state.phoneNavHeight.split('px')[0]) + 44 + 'px'
	},
	topHeight(state) {
		return parseInt(state.phoneNavHeight.split('px')[0]) + 'px'
	}
};
const mutations = {
	SET_PHONE_NAME_MUT(state) {
		state.phoneNavHeight = uni.$u.addUnit(uni.$u.sys().statusBarHeight, 'px')
	}
};

const actions = {
	SET_PHONE_NAME_ACT(context) {
		context.commit('SET_PHONE_NAME_MUT')
	}
};

export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
