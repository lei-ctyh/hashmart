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
					<el-form-item label="中奖时间">
						<el-date-picker
							v-model="searchForm.create_time"
							type="daterange"
							value-format="YYYY-MM-DD"
							range-separator="至"
							start-placeholder="开始日期"
							end-placeholder="结束日期">
						</el-date-picker>
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
				<el-table-column prop="username" label="用户名"/>
				<el-table-column prop="award_num" label="奖品数量"/>
				<el-table-column prop="total_amount" label="奖品金额"/>
				<el-table-column prop="total_exchange_integral" label="可兑哈希币"/>
				<el-table-column label="总盈亏">
					<template #default="scope">
						<div v-if="scope.row.total_profit > 0" style="color:#F56C6C">{{ scope.row.total_profit }}</div>
						<div v-else style="color:#67C23A">{{ scope.row.total_profit }}</div>
					</template>
				</el-table-column>
				<el-table-column prop="create_time" label="中奖时间"/>
				<el-table-column label="奖品详情">
					<template #default="scope">
						<el-button type="primary" plain size="small" @click="orderRecordDetail(scope.row)"
								   icon="el-icon-trophy">奖品详情
						</el-button>
					</template>
				</el-table-column>
			</el-table>

			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit"
						   @current-change="pageChange"/>
		</el-card>
		<express-info-dialog v-if="dialog.show" ref="expressInfoDialog"
					 @closed="dialog.show=false"></express-info-dialog>
	</el-main>

</template>

<script>
import expressInfoDialog from './expressInfo'

export default {
	name: 'userOrderRecordIndex',
	components: {
		expressInfoDialog
	},
	data() {
		return {
			dialog: {
				show: false
			},
			total: 1,
			searchForm: {
				user_id: '',
				username: '',
				create_time: '',
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
		// 奖品详情
		orderRecordDetail(row) {
			this.$router.push({
				path: '/userOrderRecord/detail',
				query: {
					order_record_id: row.id
				}
			})
		},
		pageChange(page) {
			this.searchForm.page = page
			this.getList()
		},
		// 获取列表
		async getList() {
			let res = await this.$API.userOrderRecord.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		},
		handleSuccess() {
			this.getList()
		},
	}
}
</script>
