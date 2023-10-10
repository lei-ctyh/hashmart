<template>
  <el-main>
		<el-card shadow="never">
    <div shadow="never" style="border:none">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
            <el-form-item label="订单号">
                <el-input v-model="searchForm.order_no" placeholder="订单号" clearable></el-input>
            </el-form-item>
            <el-form-item label="支付单号">
              <el-input v-model="searchForm.pay_order_no" placeholder="支付单号" clearable></el-input>
            </el-form-item>
            <!--<el-form-item label="支付人名">
              <el-input v-model="searchForm.user_name" placeholder="支付人名" clearable></el-input>
            </el-form-item>-->
            <el-form-item label="支付状态">
              <el-select v-model="searchForm.pay_status" placeholder="请选择" clearable>
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
        </el-form>
    </div>
    <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
    <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
        <el-table-column prop="id" label="ID" width="80px"/>
        <el-table-column prop="order_no" label="订单号" width="200px"/>
        <el-table-column prop="pay_order_no" label="支付单号" width="200px"/>
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
				<el-table-column label="支付状态">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.pay_status === 1">待支付</el-tag>
						<el-tag type="success" v-if="scope.row.pay_status === 2">已支付</el-tag>
						<el-tag type="danger" v-if="scope.row.pay_status === 3">已退款</el-tag>
						<el-tag type="info" v-if="scope.row.pay_status === 4">部分退款</el-tag>
						<el-tag type="success" v-if="scope.row.pay_status === 5">部分支付</el-tag>
						<el-tag type="error" v-if="scope.row.pay_status === 6">支付异常</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="pay_price" label="支付金额" />
                <el-table-column prop="create_time" label="下单时间" width="200"/>
				<el-table-column
          fixed="right"
          width="100px"
					label="操作">
					<template #default="scope">
						<el-button @click="handleRecord(scope.row)" link type="primary" size="small">中奖记录</el-button>
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
        name: 'blindBoxOrderIndex',
        components: {
          detailDialog
        },
        data() {
          return {
            dialog: {
              detail: false
            },
            total: 1,
            searchForm: {
                order_no: '',
                pay_order_no: '',
                user_name: '',
                pay_status: '',
                page: 1,
                limit: 15
            },
            tableData: [],
            options: [
              {value: 1, label: '待支付'},
              {value: 2, label: '已支付'},
              {value: 3, label: '已退款'}
            ]
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
          // 中奖记录弹框
          handleRecord(row) {
            this.dialog.detail = true
            this.getDetail(row.id)
          },
          // 获取列表
          async getList() {
              let res = await this.$API.blindBoxOrder.list.get(this.searchForm)
              this.tableData = res.data.rows
              this.total = res.data.total
          },
          // 中奖记录
          async getDetail(id) {
            let res = await this.$API.blindBoxOrder.detail.get({order_id:id})
            this.$nextTick(() => {
              this.$refs.detailDialog.open(res.data)
            })
          }
        }
    }
</script>
