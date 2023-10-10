<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px" label-position="left">
			<template v-if="mode==='add'">
				<el-form-item label="角色名称" prop="name">
					<el-input v-model="form.name" clearable></el-input>
				</el-form-item>
			</template>
            <template v-else>
				<el-form-item label="角色名称" prop="name">
					<el-input v-model="form.name" clearable placeholder="输入则为修改"></el-input>
				</el-form-item>
			</template>
			<el-form-item label="状态" prop="status">
				<el-radio v-model="form.status" label="1">正常</el-radio>
				<el-radio v-model="form.status" label="2">禁用</el-radio>
			</el-form-item>
            <el-form-item label="分配权限" prop="rule">
				<el-tree
					:data="treeData"
					node-key="id"
					show-checkbox
					highlight-current
					:default-checked-keys="checkedId"
					ref="menuTree"
					:props="defaultProps">
				</el-tree>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode!=='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
	export default {
		emits: ['success', 'closed'],
		data() {
			return {
				checkedId: [15, 16],
				treeData: [],
				defaultProps: {
					children: 'children',
					label: 'name',
					id: 'id'
				},
				mode: "add",
				titleMap: {
					add: '新增角色',
					edit: '编辑角色',
					show: '查看'
				},
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					id:"",
					name: "",
					rule: "",
          status: '1'
				},
				//验证规则
				rules: {
					name: [
						{required: true, message: '请输入角色名称',  trigger: 'blur'}
					],
					status: [
						{required: true, message: '请选择状态',  trigger: 'blur'}
					],
					rule: [
						{required: true, message: '请分配权限',  trigger: 'blur'}
					],
				},
			}
		},
		mounted() {
			this.getAllMenu()
		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
        this.visible = true;
        this.form.rule = this.checkedId
				return this
			},
			async getAllMenu() {
        let res = await this.$API.menu.getAllMenu.get()
        this.treeData = res.data
				this.getListData();
			},
			//对json的格式的转化
			getListData() {
        function getIds(arr, result = []) {
            arr.forEach(item => {
                if (item.children && item.children.length !== 0) {
                    result.push(item.id)
                    if (item.children.length) {
                        return getIds(item.children, result)
                    }
                }
            })

            return result
        }
       
        let getParId = getIds(this.treeData)
    
        // 筛选不是父级节点的id
        const arrList = this.form.rule.filter(item => {
            if (!getParId.includes(parseInt(item))) {
                return item
            }
        })

        this.checkedId = arrList
			},
		
			// 表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
            let res;
            let checkedNodes = this.$refs.menuTree.getCheckedNodes().concat(this.$refs.menuTree.getHalfCheckedNodes());
            let nodesMap = []
            checkedNodes.forEach(item => {
                nodesMap.push(item.id)
            })
            this.form.rule = nodesMap
						if (this.mode === 'add') {
                res = await this.$API.role.add.post(this.form)
            } else {
                res = await this.$API.role.edit.post(this.form)
            }
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
      setData (data) {
				this.form.id = data.id
				this.form.name = data.name
        this.form.status = data.status.toString()
				this.form.rule = data.rule
			},
		}
	}
</script>

<style>
</style>
