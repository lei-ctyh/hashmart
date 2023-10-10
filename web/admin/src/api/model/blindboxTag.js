import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/box.blindboxTag/index`,
		name: "获取盲盒标签列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/box.blindboxTag/add`,
		name: "添加盲盒标签",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/box.blindboxTag/edit`,
		name: "编辑盲盒标签",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/box.blindboxTag/del`,
		name: "删除盲盒标签",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}