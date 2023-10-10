import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/system.citySetting/list`,
		name: "获取城市列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/system.citySetting/add`,
		name: "添加城市",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/system.citySetting/edit`,
		name: "编辑城市",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/system.citySetting/del`,
		name: "删除城市",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
