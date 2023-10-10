<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :disabled="mode==='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="id" prop="ID">
				<el-input v-model="form.id" disabled></el-input>
			</el-form-item>
			<el-form-item label="商品的id" prop="user_id">
				<el-input v-model="form.goods_id" disabled></el-input>
			</el-form-item>
			<el-form-item label="商品名" prop="user_name">
				<el-input v-model="form.goods_name" disabled></el-input>
			</el-form-item>
			<el-form-item label="商品图片" prop="goods_image">
				<el-image :src="form.goods_image" style="height: 96px;width: 96px;"></el-image>
			</el-form-item>
			<el-form-item label="商品规格" prop="hash_key">
				<el-input v-model="form.rule" disabled></el-input>
			</el-form-item>
			<el-form-item label="数量" prop="order_time">
				<el-input v-model="form.num" disabled></el-input>
			</el-form-item>
			<el-form-item label="单价" prop="order_id">
				<el-input v-model="form.price" disabled></el-input>
			</el-form-item>
			<el-form-item label="实际支付金额" prop="goods_id">
				<el-input v-model="form.real_pay_amount" disabled></el-input>
			</el-form-item>
			<el-form-item label="实际支付哈希币" prop="goods_name">
				<el-input v-model="form.real_pay_integral" disabled></el-input>
			</el-form-item>
		</el-form>
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
			//表单数据
			form: {
			},
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
