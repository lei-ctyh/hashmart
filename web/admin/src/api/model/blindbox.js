import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/box.blindbox/index`,
		name: "获取盲盒列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/box.blindbox/add`,
		name: "添加盲盒",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/box.blindbox/edit`,
		name: "编辑盲盒",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/box.blindbox/del`,
		name: "删除盲盒",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}