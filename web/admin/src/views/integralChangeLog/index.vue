<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="用户id">
						<el-input v-model="searchForm.user_id" placeholder="用户id" clearable></el-input>
					</el-form-item>
					<!--<el-form-item label="用户名">
						<el-input v-model="searchForm.username" placeholder="用户名" clearable></el-input>
					</el-form-item>-->
					<el-form-item label="变动时间">
						<el-date-picker
							v-model="searchForm.create_time"
							type="daterange"
							value-format="YYYY-MM-DD"
							range-separator="至"
							start-placeholder="开始日期"
							end-placeholder="结束日期">
						</el-date-picker>
					</el-form-item>
					<el-form-item label="来源">
						<el-select v-model="searchForm.type" placeholder="请选择来源">
							<el-option label="兑换新增" value="1"></el-option>
							<el-option label="兑换消耗" value="2"></el-option>
							<el-option label="后台赠送" value="3"></el-option>
							<el-option label="后台减少" value="4"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column prop="user_id" label="用户id"/>
				<el-table-column prop="username" label="用户名" />
				<el-table-column label="来源">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.type == 1">兑换新增</el-tag>
						<el-tag type="info" v-if="scope.row.type == 2">兑换消耗</el-tag>
						<el-tag type="success" v-if="scope.row.type == 3">后台赠送</el-tag>
						<el-tag type="error" v-if="scope.row.type == 4">后台减少</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="integral" label="变动哈希币" />
				<el-table-column prop="create_time" label="变动时间" />
			</el-table>
			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>

	</el-main>

</template>

<script>

export default {
	name: 'integralChangeLogIndex',
	components: {
	},
	data() {
		return {
			total: 1,
			searchForm: {
				user_id: '',
				username: '',
				create_time: '',
				type: '',
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
			let res = await this.$API.integralChangeLog.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		}
	}
}
</script>
