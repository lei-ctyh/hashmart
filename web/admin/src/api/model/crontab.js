import config from "@/config"
import http from "@/utils/request"

export default {
	getList: {
		url: `${config.API_URL}/system.crontabApi/index`,
		name: "获取定时任务列表",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
	addTask: {
		url: `${config.API_URL}/system.crontabApi/add`,
		name: "添加定时任务",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
    editTask: {
		url: `${config.API_URL}/system.crontabApi/edit`,
		name: "编辑定时任务",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
    delTask: {
		url: `${config.API_URL}/system.crontabApi/del`,
		name: "删除定时任务",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
    reloadServer: {
		url: `${config.API_URL}/system.crontabApi/reload`,
		name: "重启服务",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
    log: {
		url: `${config.API_URL}/system.crontabApi/flow`,
		name: "获取执行日志",
		get: async function(data={}){
			return await http.get(this.url, data);
		}
	},
}