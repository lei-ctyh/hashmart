<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="1200" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-table :data="form"  style="width: 100%">
			<el-table-column prop="id" label="ID"/>
			<el-table-column label="用户名">
				<template #default="scope">
					{{ scope.row.user_name }} <br/> ID: {{ scope.row.user_id }}
				</template>
			</el-table-column>
			<el-table-column prop="order_time" label="下单时间戳" ></el-table-column>
			<el-table-column prop="hash_key" label="hash_key" />
			<el-table-column prop="hash_no" label="中奖数字" />
			<el-table-column
				label="获得商品图片">
				<template #default="scope">
					<el-image :src="scope.row.goods_image" style="height: 36px;width: 36px;"></el-image>
				</template>
			</el-table-column>
			<el-table-column label="获得商品">
				<template #default="scope">
					{{ scope.row.goods_name }} <br/> ID: {{ scope.row.goods_id }}
				</template>
			</el-table-column>
			<el-table-column prop="profit" label="盈亏" />
		</el-table>
	</el-dialog>
</template>

<script>
export default {
	name: 'blindBoxOrderDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "detail",
			titleMap: {
				detail: '中奖记录',
			},
			visible: false,
			//表单数据
			form: {
			}
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(e) {
			this.form = e
			this.visible = true;
			return this
		},
	}
}
</script>
