import config from "@/config"
import http from "@/utils/request"

export default {
	pactConfig: {
		url: `${config.API_URL}/system.pactSetting/pactConfig`,
		name: "获取协议配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	save: {
		url: `${config.API_URL}/system.pactSetting/pactConfig`,
		name: "保存配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	}
}
