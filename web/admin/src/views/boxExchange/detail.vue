<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="800" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-table
			:data="tableData"
			style="width: 100%">
			<el-table-column
				prop="reward.goods_id"
				label="商品ID">
			</el-table-column>
			<el-table-column
				prop="reward.goods_name"
				label="商品名称">
			</el-table-column>
			<el-table-column
				label="商品图片">
				<template #default="scope">
					<el-image :src="scope.row.reward.goods_image" style="width:40px;height: 40px"></el-image>
				</template>
			</el-table-column>
			<el-table-column
				prop="amount"
				label="可兑换哈希币">
			</el-table-column>
		</el-table>
	</el-dialog>
</template>

<script>
export default {
	name: 'boxExchangeDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "detail",
			titleMap: {
				detail: '兑换详情',
			},
			visible: false,
			tableData: []
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(e) {
			this.tableData = e
			this.visible = true;
			return this
		},
	}
}
</script>
