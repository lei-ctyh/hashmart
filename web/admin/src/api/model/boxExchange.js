import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/log.boxExchange/index`,
		name: "获取奖品兑换记录",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	detail: {
		url: `${config.API_URL}/log.boxExchange/detail`,
		name: "兑换详情",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
