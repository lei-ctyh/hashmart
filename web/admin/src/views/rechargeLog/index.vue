<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="用户ID">
						<el-input v-model="searchForm.user_id" placeholder="用户ID" clearable></el-input>
					</el-form-item>
					<!--<el-form-item label="用户名">
						<el-input v-model="searchForm.username" placeholder="用户名" clearable></el-input>
					</el-form-item>-->
					<el-form-item label="充值单号">
						<el-input v-model="searchForm.recharge_no" placeholder="充值单号" clearable></el-input>
					</el-form-item>
					<el-form-item label="支付单号">
						<el-input v-model="searchForm.pay_no" placeholder="支付单号" clearable></el-input>
					</el-form-item>
					<el-form-item label="充值日期">
						<el-date-picker
							v-model="searchForm.create_time"
							type="daterange"
							value-format="YYYY-MM-DD"
							range-separator="至"
							start-placeholder="开始日期"
							end-placeholder="结束日期">
						</el-date-picker>
					</el-form-item>
					<el-form-item label="状态">
						<el-select v-model="searchForm.status" placeholder="请选择状态">
							<el-option label="待支付" value="1"></el-option>
							<el-option label="支付成功" value="2"></el-option>
							<el-option label="支付失败" value="3"></el-option>
							<el-option label="过期" value="4"></el-option>
							<el-option label="取消" value="5"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID" width="80px"/>
				<el-table-column prop="recharge_no" label="充值单号" width="230px"/>
				<el-table-column prop="pay_no" label="支付单号" width="230px"/>
				<el-table-column prop="third_no" label="三方单号" width="230px"/>
				<el-table-column prop="user_id" label="用户iD" />
				<el-table-column prop="username" label="用户名" />
				<el-table-column prop="amount" label="充值金额" />
				<el-table-column label="充值状态">
					<template #default="scope">
						<el-tag type="info" v-if="scope.row.status == 1">待支付</el-tag>
						<el-tag type="success" v-if="scope.row.status == 2">支付成功</el-tag>
						<el-tag type="error" v-if="scope.row.status == 3">支付失败</el-tag>
						<el-tag type="warning" v-if="scope.row.status == 4">过期</el-tag>
            <el-tag type="warning" v-if="scope.row.status == 5">取消</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="create_time" label="充值日期" />
				<el-table-column width="200"
          fixed="right"
					label="操作">
					<template #default="scope">
						<el-button @click="handleDetail(scope.row)" link type="primary" size="small">三方信息</el-button>
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
	name: 'rechargeLogIndex',
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
				recharge_no: '',
				pay_no: '',
				create_time: '',
				status: '',
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
			let res = await this.$API.rechargeLog.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		},
		// 充值详情
		handleDetail(row) {
			this.dialog.detail = true
			this.$nextTick(() => {
				this.$refs.detailDialog.open(row)
			})
		}
	}
}
</script>
