import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/log.rechargeLog/index`,
		name: "获取充值记录列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
