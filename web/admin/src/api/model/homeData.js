import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/home.homeData/index`,
		name: "获取首页统计数据",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
