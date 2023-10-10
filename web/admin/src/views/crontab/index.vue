<template>
	<el-main>
		<el-card shadow="never">
	        <el-button type="primary" icon="el-icon-plus" @click="addTask()">添加</el-button>
			<el-button type="danger" icon="el-icon-refresh" @click="reload()">重启服务</el-button>
			<el-button icon="el-icon-refresh" @click="refresh()">刷新</el-button>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column prop="title" label="标题"/>
				<el-table-column prop="frequency" label="规则" />
				<el-table-column prop="running_times" label="执行次数" />
				<el-table-column label="状态">
					<template #default="scope">
						<el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" @change="changeStatus($event, scope.row)"/>
					</template>
				</el-table-column>
				<el-table-column prop="sort" label="排序" />
				<el-table-column prop="create_time" label="创建时间" />
				<el-table-column label="操作">
					<template #default="scope">
						<el-button @click="handleLog(scope.row)" type="text" size="small">执行日志</el-button>
						<el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>
	</el-main>

	<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false" :close-on-click-modal="false" :close-on-press-escape="false"></save-dialog>
	<log-dialog v-if="dialog.log" ref="logDialog" @closed="dialog.log=false" :close-on-click-modal="false" :close-on-press-escape="false"></log-dialog>
</template>

<script>

import saveDialog from './save'
import logDialog from './log'

export default {
	name: 'crontab',
	components: {
		saveDialog,
		logDialog
	},
	data() {
		return {
			total: 1,
			searchForm: {
				page: 1,
				limit: 15
			},
			dialog: {
				save: false,
				log: false
			},
			tableData: [],
		}
	},
	mounted() {
		this.getList()
	},
	methods: {
		search() {
			this.getList()
		},
		pageChange(page) {
			this.searchForm.page = page
			this.getList()
		},
		// 获取列表
		async getList() {
			let res = await this.$API.crontab.getList.get(this.searchForm)
			if (res.code == 200 && res.data) {
				this.tableData = res.data.list
				this.total = res.data.count
			}
		},
		// 刷新
		refresh() {
			this.getList()
		},
		// 删除
		async handleDel(row) {
			this.$confirm('确定删除该定时任务吗?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				let res = await this.$API.crontab.delTask.post({id: row.id})
				if (res.code == 200) {
					this.$message.success('操作成功')
					setTimeout(() => {
						this.getList()
					}, 800);
				} else {
					this.$message.error(res.msg)
				}
			}).catch(() => {})
		},
		// 添加定时任务
		addTask() {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open('add')
			})
		},
		// 查看执行日志
		handleLog(row) {
			this.dialog.log = true
			this.$nextTick(() => {
				this.$refs.logDialog.open('add').setData(row)
			})
		},
		// 添加成功
		handleSuccess() {
			this.getList()
		},
		// 改变状态
		async changeStatus(val, row) {
			let res = await this.$API.crontab.editTask.post({field: 'status', value: val, id: row.id})
			if (res.code == 200) {
				this.$message.success('操作成功')
			} else {
				this.$message.error(res.msg);
			}
		},
		// 重启服务
		reload() {
			this.$confirm('确定重启定时任务吗,重启定时任务可能导致某些业务中断，请谨慎操作?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				let res = await this.$API.crontab.reloadServer.post()
				if (res.code == 200) {
					this.$message.success('操作成功')
					setTimeout(() => {
						this.getList()
					}, 800);
				} else {
					this.$message.error(res.msg)
				}
			}).catch(() => {})
		}
	}
}
</script>
