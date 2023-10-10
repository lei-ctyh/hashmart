<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="120px">
			<el-form-item label="物流公司名称" prop="express_name">
				<el-select v-model="form.express_name" @change="expressChange" style="width:100%">
					<el-option v-for="(item,index) in expressList" :key="index" :label="item.name" :value="item.name"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="物流公司编码" prop="express_code">
				<el-input v-model="form.express_code" disabled clearable></el-input>
			</el-form-item>
			<el-form-item label="物流单号" prop="express_no">
				<el-input v-model="form.express_no" placeholder="物流单号" clearable></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button v-if="mode!=='show'" type="primary" :loading="isSaveing" @click="submit()">确认发货</el-button>
		</template>
	</el-dialog>
</template>

<script>
export default {
	name: 'integralOrderDelivery',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "delivery",
			titleMap: {
				delivery: '发货',
			},
			expressList:[],
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				order_id:'',
				user_id:'',
				express_name: '',
				express_code: '',
				express_no: '',
			},
			deleteIcon: 'el-icon-delete',
			//验证规则
			rules: {
				express_name: [
					{required: true, message: '请选择物流公司名称', trigger: 'blur'}
				],
				express_code: [
					{required: true, message: '请选择物流公司名称', trigger: 'blur'}
				],
				express_no: [
					{required: true, message: '请输入物流单号', trigger: 'blur'}
				],
			}
		}
	},
	mounted() {
		this.getExpressList()
	},
	methods: {
		//显示
		open(e) {
			this.form.order_id = e.id
			this.form.user_id = e.user_id
			this.visible = true;
			return this
		},
		//获取物流公司列表
		async getExpressList() {
			let res = await this.$API.integralOrder.expressList.get()
			this.expressList = res.data.data
		},
		expressChange(name){
			this.expressList.forEach((e) => {
				if (e.name == name) {
					this.form.express_code = e.code;
				}
			});
		},
		// 表单提交方法
		submit() {
			this.$refs.dialogForm.validate(async (valid) => {
				if (valid) {
					this.isSaveing = true;
					var res;
					res = await this.$API.integralOrder.delivery.post(this.form)

					this.isSaveing = false;
					if (res.code === 0) {
						this.$emit('success', this.form, this.mode)
						this.visible = false;
						this.$message.success(res.msg)
					} else {
						this.$message.error(res.msg)
					}
				} else {
					return false;
				}
			})
		},
	}
}
</script>
