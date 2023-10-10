import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.userBoxDeliver/index`,
		name: "获取发货列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	deliver: {
        url: `${config.API_URL}/user.userBoxDeliver/deliver`,
		name: "发货",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
    },
	// 获取订单支付信息
	expressOrder: {
		url: `${config.API_URL}/user.userBoxDeliver/expressOrder`,
		name: "订单信息",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
