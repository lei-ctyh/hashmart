import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/goods.goods/index`,
		name: "获取商品列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/goods.goods/add`,
		name: "添加商品",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/goods.goods/edit`,
		name: "编辑商品",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/goods.goods/del`,
		name: "删除商品",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	shelves: {
		url: `${config.API_URL}/goods.goods/shelves`,
		name: "上下架",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
}