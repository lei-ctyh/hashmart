<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="editRules" :disabled="mode==='show'" ref="editForm" label-width="100px"
				 label-position="left" v-if="mode==='edit'">
			<el-form-item label="昵称" prop="nickname">
				<el-input v-model="form.nickname" placeholder="昵称" clearable></el-input>
			</el-form-item>
			<el-form-item label="手机号">
				<el-input type="number" v-model="form.phone" placeholder="" clearable></el-input>
			</el-form-item>
			<el-form-item label="状态" prop="status">
				<el-radio :label="1"  v-model="form.status">正常</el-radio>
				<el-radio :label="2"  v-model="form.status">禁用</el-radio>
			</el-form-item>
		</el-form>

		<el-form :model="form" :rules="addRules" :disabled="mode==='show'" ref="addForm" label-width="100px"
				 label-position="left" v-else-if="mode==='add'">
			<el-form-item label="手机号" prop="phone">
				<el-input type="number" v-model="form.phone" placeholder="请输入手机号" clearable></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input  v-model="form.password" placeholder="请输入密码" clearable></el-input>
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
			mode: "add",
			titleMap: {
				add: '新增会员',
				edit: '编辑会员',
				show: '查看'
			},
			visible: false,
			isSaveing: false,
			//表单数据
			form: {
				id: "",
				nickname: '',
				avatar: "",
				phone: "",
				password: "",
				integral: 0,
				balance: 0.00,
				status: 1
			},
			deleteIcon: 'el-icon-delete',
			//验证规则
			editRules: {
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
			addRules: {
				phone: [
					{required: true, message: '请输入手机号', trigger: 'blur'}
				],
				password: [
					{required: true, message: '请输入密码', trigger: 'blur'}
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
			if (this.mode==='edit') {
				this.$refs.editForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
						var res;
						res = await this.$API.user.edit.post(this.form)

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
			}else{
				this.$refs.addForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
						var res;
						res = await this.$API.user.add.post(this.form)

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
			}
		},
		// 表单注入数据
		setData(data) {
			this.form.id = data.id
			this.form.nickname = data.nickname
			this.form.avatar = data.avatar
			this.form.phone = data.phone
			this.form.integral = data.integral
			this.form.balance = data.balance
			this.form.status = data.status
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

