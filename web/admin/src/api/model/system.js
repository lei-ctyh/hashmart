import config from "@/config"
import http from "@/utils/request"

export default {
	store: {
		url: `${config.API_URL}/system.system/store`,
		name: "获取存储配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
    storeSave: {
		url: `${config.API_URL}/system.system/store`,
		name: "更新存储配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	// 获取基础配置
	base: {
		url: `${config.API_URL}/system.system/base`,
		name: "获取基础配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	// 保存基础配置
	baseSave: {
		url: `${config.API_URL}/system.system/base`,
		name: "保存基础配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	}
}