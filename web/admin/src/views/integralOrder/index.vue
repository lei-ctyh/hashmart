<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="支付人ID">
						<el-input v-model="searchForm.user_id" placeholder="支付人ID" clearable></el-input>
					</el-form-item>
					<el-form-item label="订单号">
						<el-input v-model="searchForm.order_no" placeholder="订单号" clearable></el-input>
					</el-form-item>
					<el-form-item label="支付单号">
						<el-input v-model="searchForm.pay_order_no" placeholder="支付单号" clearable></el-input>
					</el-form-item>
					<el-form-item label="订单状态">
						<el-select v-model="searchForm.status" placeholder="请选择" clearable>
							<el-option
							v-for="item in options"
							:key="item.value"
							:label="item.label"
							:value="item.value">
							</el-option>
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
				<el-table-column prop="id" label="ID" width="100px"/>
				<el-table-column prop="order_no" label="订单号" width="180px"/>
				<el-table-column prop="pay_order_no" label="支付单号" width="180px"/>
				<el-table-column prop="user_id" label="支付人id" />
				<el-table-column prop="user_name" label="支付人名" />
				<el-table-column prop="total_num" label="购买数量" />
				<el-table-column label="支付方式">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.pay_way === 1">微信</el-tag>
						<el-tag type="info" v-if="scope.row.pay_way === 2">支付宝</el-tag>
						<el-tag type="warning" v-if="scope.row.pay_way === 3">哈希币</el-tag>
						<el-tag type="danger" v-if="scope.row.pay_way === 4">余额</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="pay_integral" label="支付哈希币" />
				<el-table-column label="订单状态">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.status == 1">待支付</el-tag>
						<el-tag type="warning" v-if="scope.row.status == 2">待发货</el-tag>
						<el-tag type="warning" v-if="scope.row.status == 3">待收货</el-tag>
						<el-tag type="info" v-if="scope.row.status == 4">部分发货</el-tag>
						<el-tag type="success" v-if="scope.row.status == 5">已完成</el-tag>
						<el-tag type="error" v-if="scope.row.status == 6">已取消</el-tag>
						<el-tag type="error" v-if="scope.row.status == 7">已关闭</el-tag>
						<el-tag type="danger" v-if="scope.row.status == 8">库存不足</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="支付状态">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.pay_status == 1">待支付</el-tag>
						<el-tag type="success" v-if="scope.row.pay_status == 2">已支付</el-tag>
						<el-tag type="danger" v-if="scope.row.pay_status == 3">已退款</el-tag>
						<el-tag type="info" v-if="scope.row.pay_status == 4">部分退款</el-tag>
						<el-tag type="success" v-if="scope.row.pay_status == 5">部分支付</el-tag>
						<el-tag type="error" v-if="scope.row.pay_status == 6">支付异常</el-tag>
					</template>
				</el-table-column>
        <el-table-column prop="create_time" label="下单时间" width="150px"/>
				<el-table-column prop="express_no" label="物流单号" width="150px"/>
				<el-table-column 
          width="250"
          fixed="right"
					label="操作">
					<template #default="scope">
						<el-button @click="handleDetail(scope.row)" link type="primary" size="small">订单详情</el-button>
						<el-button @click="handleDelivery(scope.row)" link type="primary" size="small" v-if="scope.row.status == 2">发货</el-button>
						<a v-if="scope.row.status > 2" target="_blank" href="https://www.kuaidi100.com/" style="margin-left: 12px;color:#409eff">物流详情</a>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top:20px"></div>
			<el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
		</el-card>

		<detail-dialog v-if="dialog.detail" ref="detailDialog" @closed="dialog.detail=false"></detail-dialog>
		<delivery-dialog v-if="dialog.delivery" ref="deliveryDialog" @closed="dialog.delivery=false" @success="handleSuccess"></delivery-dialog>
		<express-dialog v-if="dialog.express" ref="expressDialog" @closed="dialog.express=false"></express-dialog>
	</el-main>

</template>

<script>
import detailDialog from './detail'
import deliveryDialog from './delivery'
import expressDialog from './expressDetail'

export default {
	name: 'integralOrderIndex',
	components: {
		detailDialog,
		deliveryDialog,
		expressDialog
	},
	data() {
		return {
			dialog: {
				detail: false,
				delivery:false,
				express:false
			},
			total: 1,
			searchForm: {
				status: '',
				user_name: '',
				order_no: '',
        pay_order_no: '',
        user_id: '',
				page: 1,
				limit: 15
			},
			notExpress: 0,
			tableData: [],
			options: [
				{value: 1, label: '待支付'},
				{value: 2, label: '待发货'},
				{value: 3, label: '待收货'},
				{value: 5, label: '已完成'},
				{value: 6, label: '已取消'},
			],
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
		// 详情弹框
		handleDetail(row) {
			this.dialog.detail = true
			this.getDetail(row.id, row.address_info)
		},
		// 物流详情弹框
		handleExpressDetail(row) {
			this.dialog.express = true
			this.getExpress(row.id,row.user_id)
		},
		// 发货弹框
		handleDelivery(row) {
			this.dialog.delivery = true
			this.$nextTick(() => {
				this.$refs.deliveryDialog.open(row)
			})
		},
		// 获取列表
		async getList() {
			let res = await this.$API.integralOrder.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
			this.notExpress = res.data.not_express
		},
		// 订单详情
		async getDetail(id, address_info) {
			let res = await this.$API.integralOrder.detail.get({order_id:id})
      this.$nextTick(() => {
        let data = {data: res.data, addressInfo: address_info}
				this.$refs.detailDialog.open(data)
			})
		},
		// 物流详情
		async getExpress(id,user_id) {
			let res = await this.$API.integralOrder.express.get({order_id:id,user_id:user_id})
			this.$nextTick(() => {
				this.$refs.expressDialog.open(res.data)
			})
		},
		handleSuccess() {
			this.getList()
		}
	}
}
</script>

<style scoped>
	.warning-tips {
		background: red;color:#fff;height: 20px;padding: 0px 10px;line-height: 20px;text-align: center;margin-left: 5px;margin-right: 5px;
	}
</style>
