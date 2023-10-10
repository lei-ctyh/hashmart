<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="所属角色" prop="role_id">
				<el-select v-model="form.role_id" style="width: 100%">
					<el-option v-for="item in roles" :key="item.id" :label="item.name" :value="item.id"/>
				</el-select>
			</el-form-item>
			<el-form-item label="登录账号" prop="username">
				<el-input v-model="form.username" placeholder="英文+数字,用于登录系统" clearable></el-input>
			</el-form-item>
			<template v-if="mode=='add'">
				<el-form-item label="登录密码" prop="password">
					<el-input type="password" v-model="form.password" clearable show-password></el-input>
				</el-form-item>
			</template>
            <template v-else>
				<el-form-item label="登录密码">
					<el-input type="password" v-model="form.password" clearable show-password placeholder="输入则为修改"></el-input>
				</el-form-item>
			</template>
            <el-form-item label="昵称" prop="name">
				<el-input v-model="form.nickname" placeholder="昵称" clearable></el-input>
			</el-form-item>
            <el-form-item label="邮箱">
				<el-input v-model="form.email" placeholder="" clearable></el-input>
			</el-form-item>
            <el-form-item label="手机号">
				<el-input v-model="form.phone" placeholder="" clearable></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode!='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
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
					add: '新增管理员',
					edit: '编辑管理员',
					show: '查看'
				},
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					id:"",
                    role_id: '',
					username: "",
                    nickname: '',
                    password: '',
					avatar: "",
					email: "",
					phone: "",
                    status: 1
				},
				//验证规则
				rules: {
                    role_id: [
						{required: true, message: '请选择所属角色',  trigger: 'blur'}
					],
					username: [
						{required: true, message: '请输入登录账号',  trigger: 'blur'}
					],
					password: [
						{required: true, message: '请输入登录密码',  trigger: 'blur'}
					]
				},
                // 所有的角色
                roles: []
			}
		},
		mounted() {
			this.getAllRole()
		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this
			},
			async getAllRole() {
                let res = await this.$API.role.getAllRole.get({hashmart_auth_skip: 1})
                this.roles = res.data
            },
			// 表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
						var res;
                        if (this.mode == 'add') {
                            res = await this.$API.admin.add.post(this.form)
                        } else {
                            res = await this.$API.admin.edit.post(this.form)
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
                this.form.username = data.username
                this.form.role_id = data.role_id
                this.form.nickname = data.nickname
                this.form.email = data.email
                this.form.phone = data.phone
                this.form.status = data.status
			}
		}
	}
</script>

<style>
</style>
