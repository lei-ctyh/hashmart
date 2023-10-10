import config from "@/config"
import http from "@/utils/request"

export default {
	list: {
		url: `${config.API_URL}/rule.menu/index`,
		name: "获取菜单列表",
		get: async function(data={}) {
			return await http.get(this.url, data);
		}
	},
    add: {
        url: `${config.API_URL}/rule.menu/add`,
		name: "添加菜单",
		post: async function(data={}) {
			return await http.post(this.url, data);
		}
    },
    edit: {
        url: `${config.API_URL}/rule.menu/edit`,
		name: "编辑菜单",
		post: async function(data={}) {
			return await http.post(this.url, data);
		}
    },
	del: {
        url: `${config.API_URL}/rule.menu/del`,
		name: "删除菜单",
		get: async function(data={}) {
			return await http.get(this.url, data);
		}
    },
	getAllMenu: {
		url: `${config.API_URL}/rule.menu/getAllMenu`,
		name: "获取全部角色",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	}
}
