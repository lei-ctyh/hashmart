<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="管理员名">
						<el-input v-model="searchForm.username" placeholder="管理员名" clearable></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column prop="admin_id" label="管理员ID"/>
				<el-table-column prop="username" label="管理员名" />
				<el-table-column prop="title" label="内容"/>
				<el-table-column prop="url" label="路由"/>
				<el-table-column prop="ip" label="ip"/>
				<el-table-column prop="data" :show-overflow-tooltip="true" label="操作内容"/>
				<el-table-column prop="user_agent" label="useragent"/>
				<el-table-column prop="create_time" label="创建时间" />
			</el-table>

			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>
	</el-main>

</template>

<script>

export default {
	name: 'adminLogIndex',
	data() {
		return {
			total: 1,
			searchForm: {
				username: '',
				page: 1,
				limit: 15
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
			let res = await this.$API.adminLog.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		},
	}
}

</script>
