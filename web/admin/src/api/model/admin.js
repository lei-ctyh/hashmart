import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/rule.admin/index`,
		name: "获取管理员列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
  add: {
    url: `${config.API_URL}/rule.admin/add`,
		name: "添加管理员",
		post: async function(data={}){
			return await http.post(this.url, data);
	  }
  },
  edit: {
    url: `${config.API_URL}/rule.admin/edit`,
		name: "编辑管理员",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
  },
	del: {
    url: `${config.API_URL}/rule.admin/del`,
    name: "删除管理员",
    get: async function(data={}){
      return await http.get(this.url, data);
    }
  },
}