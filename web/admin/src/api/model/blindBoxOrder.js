import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/order.blindBoxOrder/index`,
		name: "获取盲盒订单列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
    detail: {
        url: `${config.API_URL}/order.blindBoxOrder/detail`,
		name: "获取中奖记录",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
    },
	export: {
		url: `${config.API_URL}/order.blindBoxOrder/export`,
		name: "导出",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
