<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item label="发货人ID" width="80px">
						<el-input v-model="searchForm.user_id" placeholder="发货人ID" clearable></el-input>
					</el-form-item>
					<el-form-item label="发货订单号" width="220px">
						<el-input v-model="searchForm.deliver_no" placeholder="发货订单号" clearable></el-input>
					</el-form-item>
					<el-form-item label="发货状态">
						<el-select v-model="searchForm.status" placeholder="选择发货状态" clearable>
							<el-option label="待发货" value="1"></el-option>
							<el-option label="已发货" value="2"></el-option>
							<el-option label="已签收" value="3"></el-option>
							<el-option label="异常" value="4"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
          <el-form-item v-if="notExpress > 0">
						您有 <div class="warning-tips">{{ notExpress }}</div> 笔订单等待发货，请及时处理！
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID" width="80px"/>
				<el-table-column prop="deliver_no" label="发货订单号" width="220px"/>
				<el-table-column prop="user_id" label="发货人id"/>
				<el-table-column prop="user_name" label="发货人名"/>
				<el-table-column label="状态">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.status === 1">待发货</el-tag>
						<el-tag type="success" v-if="scope.row.status === 2">已发货</el-tag>
						<el-tag type="info" v-if="scope.row.status === 3">已签收</el-tag>
						<el-tag type="error" v-if="scope.row.status === 4">异常</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="express_name" label="物流公司"/>
				<el-table-column prop="express_no" label="物流单号"/>
				<el-table-column prop="create_time" label="申请时间"/>
				<el-table-column label="发货详情">
					<template #default="scope">
						<el-button type="primary" plain size="small" @click="deliverDetail(scope.row)"
								   icon="el-icon-ship">发货详情
						</el-button>
					</template>
				</el-table-column>
				<el-table-column label="操作">
					<template #default="scope">
						<el-button @click="handleDeliver(scope.row)" type="text" size="small" v-if="scope.row.status === 1" style="margin-left: 0px;">发货</el-button>
            <a  v-if="scope.row.status > 1" target="_blank" href="https://www.kuaidi100.com/" style="margin-left: 12px;color:#409eff">物流详情</a>
						<el-button @click="handleOrder(scope.row)" type="text" size="small" style="margin-left: 0px;">运费订单</el-button>
					</template>
				</el-table-column>
			</el-table>

			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit"
						   @current-change="pageChange"/>
		</el-card>
		<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess"
					 @closed="dialog.save=false"></save-dialog>

		<express-info-dialog v-if="dialog.show" ref="expressInfoDialog"
					 @closed="dialog.show=false"></express-info-dialog>

		<express-order-dialog v-if="dialog.order" ref="expressOrderDialog"
					 @closed="dialog.order=false"></express-order-dialog>
	</el-main>

</template>

<script>
import saveDialog from './save'
import expressInfoDialog from './expressInfo'
import expressOrderDialog from './expressOrder'

export default {
	name: 'userBoxDeliverIndex',
	components: {
		saveDialog,
		expressInfoDialog,
		expressOrderDialog
	},
	data() {
		return {
			dialog: {
				save: false,
				show: false,
				order: false
      },
      notExpress: 0,
			total: 1,
			searchForm: {
				deliver_no: '',
        status: '',
        user_id: '',
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
		// 发货详情
		deliverDetail(row) {
			this.$router.push({
				path: '/userBoxDeliver/detail',
				query: {
					id: row.id
				}
			})
		},
		handleDeliver(row) {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open('deliver').setData(row)
			})
		},
		handleExpressInfo(row) {
			this.dialog.show = true
			this.$nextTick(() => {
				this.$refs.expressInfoDialog.open('show').setData(row)
			})
		},
		pageChange(page) {
			this.searchForm.page = page
			this.getList()
		},
		// 获取列表
		async getList() {
			let res = await this.$API.userBoxDeliver.list.get(this.searchForm)
			this.tableData = res.data.rows
      this.total = res.data.total
      this.notExpress = res.data.not_express
		},
		// 运费订单
		handleOrder(row) {
			this.dialog.order = true
			this.$nextTick(() => {
				this.$refs.expressOrderDialog.open('show').setData(row)
			})
		},
		handleSuccess() {
			this.getList()
		},
	}
}
</script>

<style scoped>
.warning-tips {
		background: red;color:#fff;padding: 0px 10px;height: 20px;line-height: 20px;text-align: center;margin-left: 5px;margin-right: 5px;
	}
</style>
