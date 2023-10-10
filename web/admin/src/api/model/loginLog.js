import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/log.loginLog/list`,
		name: "获取会员登录记录",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
