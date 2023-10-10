import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/system.attachment/index`,
		name: "获取资源列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
    del: {
        url: `${config.API_URL}/system.attachment/del`,
		name: "删除资源",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
    }
}