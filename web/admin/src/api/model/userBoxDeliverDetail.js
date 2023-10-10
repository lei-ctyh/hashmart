import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.userBoxDeliverDetail/index`,
		name: "获取发货详情列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
