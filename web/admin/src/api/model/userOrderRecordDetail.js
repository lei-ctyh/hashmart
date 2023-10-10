import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.orderRecordDetail/index`,
		name: "获取用户中奖数据奖品详情",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
