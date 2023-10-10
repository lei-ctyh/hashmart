<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="物流公司名称" prop="name">
				<el-input v-model="form.name" placeholder="物流公司名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="物流公司编码">
				<el-input v-model="form.code" placeholder="物流公司编码" clearable></el-input>
			</el-form-item>
			<el-form-item label="状态" prop="status">
				<el-radio :label="1"  v-model="form.status">正常</el-radio>
				<el-radio :label="2"  v-model="form.status">禁用</el-radio>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button v-if="mode!=='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
export default {
	name: 'expressCorpSettingSave',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "add",
			titleMap: {
				edit: '编辑物流公司',
				add: '添加物流公司'
			},
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				id: "",
				name: '',
				code: "",
				status: 1
			},
			//验证规则
			rules: {
				nickname: [
					{required: true, message: '请输入昵称', trigger: 'blur'}
				],
				phone: [
					{required: true, message: '请输入手机号', trigger: 'blur'}
				],
				integral: [
					{required: true, message: '请输入哈希币', trigger: 'blur'}
				],
				balance: [
					{required: true, message: '请输入余额', trigger: 'blur'}
				],
			},
			roles: []
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(mode = 'add') {
			this.mode = mode;
			this.visible = true;
			return this
		},
		// 表单提交方法
		submit() {
			this.$refs.dialogForm.validate(async (valid) => {
				if (valid) {
					this.isSaveing = true;
					var res;
					if (this.mode == 'add') {
						res = await this.$API.expressCorpSetting.add.post(this.form)
					} else {
						res = await this.$API.expressCorpSetting.edit.post(this.form)
					}
					this.isSaveing = false;
					if (res.code == 0) {
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
		// 表单注入数据
		setData(data) {
			this.form.id = data.id
			this.form.name = data.name
			this.form.code = data.code
			this.form.status = data.status
		}
	}
}
</script>

