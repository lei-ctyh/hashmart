import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/log.adminLog/index`,
		name: "获取管理员日志列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
