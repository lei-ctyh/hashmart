import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/box.blindboxDetail/index`,
		name: "获取盲盒商品列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/box.blindboxDetail/add`,
		name: "添加盲盒商品",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/box.blindboxDetail/edit`,
		name: "编辑盲盒商品",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/box.blindboxDetail/del`,
		name: "删除盲盒商品",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	getMaxPercent: {
		url: `${config.API_URL}/box.blindboxDetail/getLotteryProbability`,
		name: "删除盲盒商品中奖值",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	getLotteryNumRange: {
		url: `${config.API_URL}/box.blindboxDetail/getLotteryNumRange`,
		name: "删除盲盒商品中奖值",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}