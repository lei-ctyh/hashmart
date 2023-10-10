import config from "@/config"
import http from "@/utils/request"

export default {
	config: {
		url: `${config.API_URL}/system.expressInfoSetting/config`,
		name: "获取物流配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	save: {
		url: `${config.API_URL}/system.expressInfoSetting/config`,
		name: "保存配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	}
}
