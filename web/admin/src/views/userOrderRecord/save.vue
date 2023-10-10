<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="物流公司名称" prop="express_name">
				<el-input v-model="form.express_name" placeholder="物流公司名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="物流单号" prop="express_no">
				<el-input v-model="form.express_no" placeholder="物流单号" clearable></el-input>
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
	emits: ['success', 'closed'],
	data() {
		return {
			areas: [],
			mode: "deliver",
			titleMap: {
				deliver: '发货',
				show: '查看'
			},
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				id: "",
				express_name: "",
				express_no: "",
			},
			//验证规则
			rules: {
				express_name: [
					{required: true, message: '请输入物流公司名称', trigger: 'blur'}
				],
				express_no: [
					{required: true, message: '请输入物流单号', trigger: 'blur'}
				],
			},
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
					res = await this.$API.userBoxDeliver.deliver.post(this.form)

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
		// 表单注入数据
		setData(data) {
			this.form.id = data.id
		},
		handleChange(value) {
			console.log(value);
		}

	}
}
</script>

<style scoped>
.img-list {
	height: 60px;
	padding-left: 0;
	margin-top: 0;
}
.img-list li:first-child {
	margin-left: 0;
}
.img-list li {
	width: 58px;
	height: 58px;
	float: left;
	margin-left: 5px;
	cursor: pointer;
	position: relative;
}
.addImg {
	height: 56px;
	width: 56px;
	line-height: 56px;
	text-align: center;
	border: 1px dashed rgb(221, 221, 221);
}
ul li {list-style: none}
.image-check-dialog .el-dialog__header {display: none}
.image-check-dialog .el-dialog__body {padding: 0;}
.img-list .img-tools {
	position: absolute;
	width: 58px;
	height: 15px;
	line-height: 15px;
	text-align: center;
	top: 43px;
	background: rgba(0, 0, 0, 0.6);
	cursor: pointer;
}
</style>

