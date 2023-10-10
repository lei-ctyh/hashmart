const state = {
	status: '1',
	type: 'box',
	settingInfo: null

};

const getters = {
	getStatus(state) {
		return state.status
	},
	getType(state) {
		return state.type
	},
	settingInfo(state) {
		return state.settingInfo;
	},
};

const mutations = {
	setStatus(state, data) {
		state.status = data
	},
	setType(state, data) {
		state.type = data
	},
	settingInfo(state, data) {
		state.settingInfo = data;
	},
};

const actions = {
	setStatusAct(context) {
		context.commit('setStatus')
	},
	setTypeAct(context) {
		context.commit('setType')
	},
	settingInfo(context, data) {
		context.commit('settingInfo', data);
	},
};

export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
}

