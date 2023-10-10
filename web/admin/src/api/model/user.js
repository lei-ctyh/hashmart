import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.user/index`,
		name: "获取会员列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
  edit: {
        url: `${config.API_URL}/user.user/edit`,
		name: "编辑会员",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
  },
	add: {
		url: `${config.API_URL}/user.user/add`,
		name: "添加会员",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	// 改变哈希币
	integral: {
		url: `${config.API_URL}/user.user/integral`,
		name: "改变哈希币",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	// 改变余额
	balance: {
		url: `${config.API_URL}/user.user/balance`,
		name: "改变余额",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
}
