import config from "@/config"
import http from "@/utils/request"

export default {
	getConfig: {
		url: `${config.API_URL}/system.appletSetting/config`,
		name: "获取微信配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	save: {
		url: `${config.API_URL}/system.appletSetting/config`,
		name: "保存配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	getUniapp: {
		url: `${config.API_URL}/system.appletSetting/uniapp`,
		name: "获取uniapp配置",
		get: async function(data={}) {
			return await http.get(this.url, data);
		}
	},
	saveUniapp: {
		url: `${config.API_URL}/system.appletSetting/uniapp`,
		name: "保存uniapp配置",
		post: async function(data={}) {
			return await http.post(this.url, data);
		}
	},
}
