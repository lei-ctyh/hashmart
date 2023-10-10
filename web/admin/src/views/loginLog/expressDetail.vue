<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :disabled="mode==='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="物流详情" prop="express_detail">
				<el-input type="textarea" v-model="form.express_detail" disabled></el-input>
			</el-form-item>
			<el-form-item label="更新日期" prop="update_time">
				<el-input v-model="form.update_time" disabled></el-input>
			</el-form-item>
		</el-form>
	</el-dialog>
</template>

<script>
export default {
	name: 'integralOrderExpressDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "expressDetail",
			titleMap: {
				expressDetail: '物流详情',
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
