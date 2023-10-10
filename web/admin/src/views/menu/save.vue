<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="上级菜单">
				<el-input v-model="pname" disabled="true"></el-input>
			</el-form-item>
			<el-form-item label="菜单名称" prop="name">
				<el-input v-model="form.name" clearable></el-input>
			</el-form-item>
			<el-form-item label="图标">
				<el-input v-model="form.icon" clearable placeholder="el-icon-suitcase"></el-input>
			</el-form-item>
			<el-form-item label="是否菜单">
				<el-radio :label="1"  v-model="form.type">菜单</el-radio>
				<el-radio :label="2"  v-model="form.type">功能</el-radio>
			</el-form-item>
            <el-form-item label="权限规则">
				<el-input v-model="form.auth" placeholder="role/index" clearable></el-input>
			</el-form-item>
			<el-form-item label="前端路由">
				<el-input v-model="form.path" placeholder="/role/index" clearable></el-input>
			</el-form-item>
			<el-form-item label="前端组件">
				<el-input v-model="form.component" placeholder="role/index" clearable></el-input>
			</el-form-item>
			<el-form-item label="排序">
				<el-input type="number" v-model="form.sort" clearable></el-input>
			</el-form-item>
			<el-form-item label="是否显示">
				<el-radio :label="1"  v-model="form.status">显示</el-radio>
				<el-radio :label="2"  v-model="form.status">隐藏</el-radio>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
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
					add: '新增菜单',
					edit: '编辑菜单',
					show: '查看'
				},
				visible: false,
				isSaveing: false,
				pname: '顶级菜单',
				//表单数据
				form: {
					id:"",
					pid: 0,
                    type: 1,
					name: "",
                    auth: "",
                    path: "",
					icon: "",
					component: "",
					sort: 1,
                    status: 1
				},
				//验证规则
				rules: {
                    name: [
						{required: true, message: '请输入菜单名称',  trigger: 'blur'}
					]
				}
			}
		},
		mounted() {
			
		},
		methods: {
			// 显示
			open(mode='add') {
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
                            res = await this.$API.menu.add.post(this.form)
                        } else {
                            res = await this.$API.menu.edit.post(this.form)
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
                this.form.pid = data.pid
                this.form.type = data.type
                this.form.name = data.name
                this.form.auth = data.auth
                this.form.path = data.path
				this.form.icon = data.icon
				this.form.component = data.component
				this.form.sort = data.sort
                this.form.status = data.status
			},
			setParentData(row) {
				this.form.pid = row.id
				this.pname = row.name
			}
		}
	}
</script>

<style>
</style>
