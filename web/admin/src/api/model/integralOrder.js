import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/order.integralOrder/index`,
		name: "获取哈希币商城订单列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
    detail: {
        url: `${config.API_URL}/order.integralOrder/detail`,
		name: "获取订单详情",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
    },
	delivery: {
		url: `${config.API_URL}/order.integralOrder/delivery`,
		name: "发货",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	express: {
		url: `${config.API_URL}/order.integralOrder/express`,
		name: "物流详情",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	expressList: {
		url: `${config.API_URL}/order.integralOrder/expressList`,
		name: "物流公司列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
