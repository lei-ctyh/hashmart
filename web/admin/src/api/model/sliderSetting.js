import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/system.sliderSetting/index`,
		name: "获取轮播配置列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/system.sliderSetting/add`,
		name: "新增",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/system.sliderSetting/edit`,
		name: "编辑",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/system.sliderSetting/del`,
		name: "删除",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
