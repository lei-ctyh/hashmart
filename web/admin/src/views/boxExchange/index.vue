<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="兑换人id">
						<el-input v-model="searchForm.user_id" placeholder="兑换人id" clearable></el-input>
					</el-form-item>
					<!--<el-form-item label="兑换用户名">
						<el-input v-model="searchForm.username" placeholder="兑换用户名" clearable></el-input>
					</el-form-item>-->
					<el-form-item label="兑换单号">
						<el-input v-model="searchForm.exchange_no" placeholder="兑换单号" clearable></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column prop="exchange_no" label="兑换单号"/>
				<el-table-column prop="user_id" label="兑换用户iD" />
				<el-table-column prop="username" label="兑换用户名" />
				<el-table-column prop="exchange_num" label="兑换数量" />
				<el-table-column prop="total_amount" label="兑换总金额" />
				<el-table-column prop="create_time" label="兑换时间" />
				<el-table-column width="250"
					label="操作">
					<template #default="scope">
						<el-button @click="handleDetail(scope.row)" link type="primary" size="small">兑换详情</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>

		<detail-dialog v-if="dialog.detail" ref="detailDialog" @closed="dialog.detail=false"></detail-dialog>
	</el-main>

</template>

<script>
import detailDialog from './detail'

export default {
	name: 'boxExchangeIndex',
	components: {
		detailDialog,
	},
	data() {
		return {
			dialog: {
				detail: false,
			},
			total: 1,
			searchForm: {
				user_id: '',
				username: '',
				exchange_no: '',
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
			let res = await this.$API.boxExchange.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		},
		// 兑换详情
		handleDetail(row) {
			this.dialog.detail = true
			this.getDetail(row.id)
		},
		// 兑换详情接口
		async getDetail(id) {
			let res = await this.$API.boxExchange.detail.get({exchange_id:id})
			this.$nextTick(() => {
				this.$refs.detailDialog.open(res.data)
			})
		}
	}
}
</script>
