import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.orderRecord/index`,
		name: "获取用户中奖数据列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
