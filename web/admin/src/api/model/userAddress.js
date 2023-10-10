import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/user.userAddress/index`,
		name: "获取会员地址列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
  edit: {
    url: `${config.API_URL}/user.userAddress/edit`,
		name: "编辑会员地址",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
  },
	del: {
		url: `${config.API_URL}/user.userAddress/del`,
		name: "删除会员地址",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	areas: {
		url: `${config.API_URL}/user.userAddress/areas`,
		name: "删除会员地址",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}
