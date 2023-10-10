import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/system.attachmentCate/index`,
		name: "获取分类列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/system.attachmentCate/add`,
		name: "添加分类",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	edit: {
		url: `${config.API_URL}/system.attachmentCate/edit`,
		name: "编辑分类",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	del: {
		url: `${config.API_URL}/system.attachmentCate/del`,
		name: "删除分类",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}