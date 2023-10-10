import config from "@/config"
import http from "@/utils/request"

export default {
	messageConfig: {
		url: `${config.API_URL}/system.MessageSetting/messageConfig`,
		name: "获取支付配置",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	save: {
		url: `${config.API_URL}/system.MessageSetting/messageConfig`,
		name: "保存配置",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	}
}
