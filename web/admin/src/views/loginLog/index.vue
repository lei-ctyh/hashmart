<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="用户ID">
						<el-input v-model="searchForm.user_id" placeholder="用户ID" clearable></el-input>
					</el-form-item>
					<el-form-item label="用户名">
						<el-input v-model="searchForm.nickname" placeholder="用户名" clearable></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column prop="user_id" label="用户ID"/>
				<el-table-column prop="nickname" label="用户名" />
				<el-table-column prop="login_ip" label="登录ip" />
				<el-table-column prop="user_agent" label="登录设备" />
				<el-table-column prop="create_time" label="登录时间" />
			</el-table>
			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>
	</el-main>

</template>

<script>

export default {
	name: 'loginLogIndex',
	components: {
	},
	data() {
		return {
			total: 1,
			searchForm: {
				user_id: '',
				nickname: '',
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
			let res = await this.$API.loginLog.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		}
	}
}
</script>
