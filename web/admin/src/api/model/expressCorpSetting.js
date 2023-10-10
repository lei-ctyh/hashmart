import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/system.expressCorpSetting/index`,
		name: "获取物流公司列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	add: {
		url: `${config.API_URL}/system.expressCorpSetting/add`,
		name: "添加物流公司",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
    edit: {
        url: `${config.API_URL}/system.expressCorpSetting/edit`,
		name: "编辑物流公司",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
    },
	del: {
		url: `${config.API_URL}/system.expressCorpSetting/del`,
		name: "删除物流公司",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
