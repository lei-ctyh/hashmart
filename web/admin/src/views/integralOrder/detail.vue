<template>
  
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="1000" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
    <h4>收件信息</h4>
    <div style="font-size: 14px;margin-top: 10px;">
        <p style="margin-top: 10px;">收件人：{{ addressInfo.receiver }}</p>
        <p style="margin-top: 10px;">手机号：{{ addressInfo.phone }}</p>
        <p style="margin-top: 10px;">收件地址：{{ addressInfo.province_name }}{{ addressInfo.city_name }}{{ addressInfo.area_name }}{{ addressInfo.address }}</p>
    </div>
    <el-divider></el-divider>
		<el-table
			:data="tableData"
			style="width: 100%;margin-top: 20px;">
			<el-table-column
				prop="goods_id"
				label="商品ID">
			</el-table-column>
			<el-table-column
				label="商品图片">
				<template #default="scope">
					<el-avatar shape="square" :size="36" :src="scope.row.goods_image"></el-avatar>
				</template>
			</el-table-column>
			<el-table-column
				prop="goods_name"
				label="商品名称">
			</el-table-column>
			<el-table-column
				label="商品规格">
				<template #default="scope">
					{{ scope.row.rule.split('※').join(',') }}
				</template>
			</el-table-column>
			<el-table-column
				prop="num"
				label="数量">
			</el-table-column>
			<el-table-column
				prop="real_pay_amount"
				label="实际支付金额">
			</el-table-column>
			<el-table-column
				prop="real_pay_integral"
				label="实际支付哈希币">
			</el-table-column>
		</el-table>
	</el-dialog>
</template>

<script>
export default {
	name: 'integralOrderDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "detail",
			titleMap: {
				detail: '订单详情',
			},
			visible: false,
      tableData: [],
      addressInfo: {}
		}
	},
	mounted() {
	},
	methods: {
		//显示
    open (res) {
      console.log(res)
			this.visible = true;
      this.tableData.push(res.data)
      if (res.addressInfo) {
        this.addressInfo = JSON.parse(res.addressInfo)
      }
			return this
		},
	}
}
</script>
