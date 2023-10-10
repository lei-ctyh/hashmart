import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/log.balanceChangeLog/index`,
		name: "获取余额变动记录列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
