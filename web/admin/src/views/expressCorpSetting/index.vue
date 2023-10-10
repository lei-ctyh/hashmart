<template>
    <el-main>
		<el-card shadow="never">
            <div shadow="never" style="border:none">
                <el-form :inline="true" :model="searchForm" class="demo-form-inline">
                    <el-form-item label="物流公司编码">
                        <el-input v-model="searchForm.code" placeholder="物流公司编码" clearable></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
			<el-button type="primary" icon="el-icon-plus" @click="add">添加物流公司</el-button>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
                <el-table-column prop="id" label="ID"/>
                <el-table-column prop="name" label="物流公司名称"/>
				<el-table-column prop="code" label="物流公司编码"/>
				<el-table-column label="状态">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
						<el-tag type="error" v-if="scope.row.status == 2">禁用</el-tag>
					</template>
				</el-table-column>
                <el-table-column prop="create_time" label="创建时间" />
                <el-table-column
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
						<el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div style="margin-top:20px"></div>
            <el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
        </el-card>
		<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false"></save-dialog>
    </el-main>

</template>

<script>
    import saveDialog from './save'
    export default {
		name: 'expressCorpSettingIndex',
		components: {
			saveDialog
		},
		data() {
			return {
				dialog: {
					save: false
				},
                total: 1,
                searchForm: {
					code: '',
                    page: 1,
                    limit: 15
                },
				tableData: [],
			}
		},
        mounted() {
            this.getList()
        },
        methods: {
            search() {
                this.getList()
            },
			// 添加物流
			add() {
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('add')
				})
			},
			//编辑
            handleEdit(row) {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open('edit').setData(row)
				})
            },
			// 删除
			handleDel(row) {
				this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async () => {
					let res = await this.$API.expressCorpSetting.del.get({id: row.id})
					if (res.code == 0) {
						this.$message.success(res.msg)
						this.getList()
					} else {
						this.$message.error(res.msg)
					}
				}).catch(() => {});
			},
            pageChange(page) {
                this.searchForm.page = page
                this.getList()
            },
            // 获取列表
            async getList() {
                let res = await this.$API.expressCorpSetting.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.total = res.data.total
            },
            handleSuccess() {
                this.getList()
            },
        }
    }
</script>
