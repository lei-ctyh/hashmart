import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/rule.role/index`,
		name: "获取角色列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/rule.role/add`,
		name: "添加角色",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/rule.role/edit`,
		name: "编辑角色",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/rule.role/del`,
		name: "删除角色",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	getAllRole: {
		url: `${config.API_URL}/rule.role/getAllRole`,
		name: "获取全部角色",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
