<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="700" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="130px"
				 label-position="left">
			<el-form-item label="收件人姓名" prop="receiver">
				<el-input v-model="form.receiver" placeholder="收件人姓名" clearable></el-input>
			</el-form-item>
			<el-form-item label="收件人手机号" prop="phone">
				<el-input type="number"
						  onkeypress="return(/[\d]/.test(String.fromCharCode(event.keyCode)))"
						  oninput="if (isNaN(value)) {value = parseFloat(value)}
						   if (value.includes('-')) {value = '0'}"
						  v-model="form.phone" placeholder="收件人" clearable></el-input>
			</el-form-item>
			<el-form-item label="收件人省市区">
				<el-cascader
					style="width: 100%"
					v-model="selected_areas"
					:options="areas"
					@change="handleChange"></el-cascader>
			</el-form-item>
			<el-form-item label="收件人详细地址" prop="address">
				<el-input v-model="form.address" placeholder="收件人详细地址" clearable type="textarea" :rows="3"></el-input>
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
			selected_areas: [],
			areas: [],
			mode: "edit",
			titleMap: {
				edit: '编辑会员地址',
				show: '查看'
			},
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				id: "",
				receiver: "",
				phone: "",
				province_code: "",
				city_code: "",
				area_code: "",
				address: "",
			},
			//验证规则
			rules: {
				receiver: [
					{required: true, message: '请输入收件人名', trigger: 'blur'}
				],
				phone: [
					{required: true, message: '请输入收件人手机号', trigger: 'blur'}
				],
				address: [
					{required: true, message: '请输入详细地址', trigger: 'blur'}
				],
			},
		}
	},
	mounted() {
		this.getAreas()
	},
	methods: {
		//显示
		open(mode = 'add') {
			this.mode = mode;
			this.visible = true;
			return this
		},
		// 查询省市区数据
		async getAreas() {
			let res = await this.$API.userAddress.areas.get()
			this.areas = res.data
		},
		// 表单提交方法
		submit() {
			this.$refs.dialogForm.validate(async (valid) => {
				if (valid) {
					this.isSaveing = true;
					var res;
					this.form.province_code = this.selected_areas[0]
					this.form.city_code = this.selected_areas[1]
					this.form.area_code = this.selected_areas[2]
					res = await this.$API.userAddress.edit.post(this.form)

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
			this.form.receiver = data.receiver
			this.form.phone = data.phone
			this.form.province_code = data.province_code
			this.form.city_code = data.city_code
			this.form.area_code = data.area_code
			this.form.address = data.address
			// 省市区回显
			this.selected_areas.push(parseInt(data.province_code))
			this.selected_areas.push(parseInt(data.city_code))
			this.selected_areas.push(parseInt(data.area_code))
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

