<template>
	<el-main>
		<el-card shadow="never">
			<el-button type="primary" icon="el-icon-plus" @click="addTop">添加省份</el-button>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>

			<el-table
				:data="tableData"
				style="width: 100%;margin-top: 20px;"
				row-key="id"
				lazy
				:load="load"
				:tree-props="{children: 'children'}">
				<el-table-column
					type=""
					prop="id"
					label="编号">
				</el-table-column>
				<el-table-column
					prop="name"
					label="地区名称">
				</el-table-column>
				<el-table-column
					prop="is_show"
					label="状态">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.is_show == 1">显示</el-tag>
						<el-tag type="danger" v-else>隐藏</el-tag>
					</template>
				</el-table-column>
				<el-table-column
					prop="operation"
					label="操作">
					<template #default="scope">
						<el-button @click="add(scope.row)" type="text" size="small" v-if="scope.row.level <= 2">添加</el-button>
						<el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
						<el-button @click="del(scope.row)" type="text" size="small">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>

		<el-dialog
			:title="title"
			:append-to-body="true"
			:close-on-click-modal="false"
			v-model="dialogVisible"
			width="30%">
			<el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
				<el-form-item label="上级地区" v-if="showParent">
					<el-input v-model="pname" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="地区编码" prop="id">
					<el-input v-model="form.id"></el-input>
				</el-form-item>
				<el-form-item label="省份名称" prop="name">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
				<el-form-item label="是否启用" prop="is_show">
					<el-radio-group v-model="form.is_show">
						<el-radio label="1">启用</el-radio>
						<el-radio label="2">禁用</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
                    </span>
		</el-dialog>
	</el-main>
</template>
<script>

export default {
	name: 'citySettingIndex',
	components: {},
	data() {
		return {
			tableData: [],
			dialogVisible: false,
			title: '添加省份',
			showParent: false,
			mode: 'add',
			total: 1,
			form: {
				id: 0,
				name: "",
				pid: 0,
				is_show: "1",
				level: 1
			},
			pname: "",
			rules: {
				name: [
					{required: true, message: '请输入名称'}
				],
				is_show: [
					{required: true, message: '请选择是否启用'}
				],
				id: [
					{required: true, message: '请输入地区编码'}
				],
			},
		}
	},

	mounted() {
		this.getList()
	},
	methods: {
		// 获取列表
		async getList() {
			let res = await this.$API.citySetting.list.get(this.form)
			this.tableData = res.data
		},
		//添加省份
		addTop() {
			this.title = '添加省份';
			this.form.level = 1;
			this.form.pid = 0;
			this.form.id = '';
			this.form.name = '';
			this.showParent = false;
			this.mode = 'add';
			this.dialogVisible = true;
		},
		//添加
		add(row) {
			this.title = '添加地区';
			this.form.level = row.level + 1;
			this.form.pid = row.id;
			this.pname = row.name;
			this.form.name = '';
			this.showParent = true;
			this.mode = 'add';
			this.dialogVisible = true;
		},
		//编辑
		edit(row) {
			this.title = '编辑省份';
			this.form.id = row.id;
			this.form.name = row.name;
			this.form.pid = row.pid;
			this.form.is_show = String(row.is_show)
			this.form.level = row.level;
			this.showParent = false;
			this.mode = 'edit';
			this.dialogVisible = true;
		},
		//删除
		del(row) {
			this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				let res = await this.$API.citySetting.del.get({id: row.id})
				if (res.code == 0) {
					this.$message.success(res.msg)
					window.location.reload();
				} else {
					this.$message.error(res.msg)
				}
			}).catch(() => {
			});
		},
		//加载下级数据
		async load(tree, treeNode, resolve) {
			this.form.pid = tree.id
			this.form.level = tree.level + 1
			let res = await this.$API.citySetting.list.get(this.form)
			resolve(res.data)
		},
		async submitForm(formName) {
			this.$refs[formName].validate(async (valid) => {
				if (valid) {
					let res; 
					if (this.mode == 'edit') {
						 res = await this.$API.citySetting.edit.post(this.form)
					} else {
						res = await this.$API.citySetting.add.post(this.form)
					}

					if (res.code == 0) {
						this.$message.success(res.msg);
						this.dialogVisible = false
					} else {
						this.$message.error(res.msg);
					}
				}
			});
		}
	}
}
</script>

<style scoped>
.rowClass {
	border-top: 1px solid #efefef;
	border-bottom: 1px solid #efefef;
	background: #fff;
	box-shadow: 0px 0px 20px rgb(0 0 0 / 3%);
}

.headClass {
	color: #22223e;
	font-weight: 600;
	border-top: 1px solid #efefef;
	border-bottom: 1px solid #efefef;
	background: #fff;
	box-shadow: 0px 0px 20px rgb(0 0 0 / 3%)
}
</style>
