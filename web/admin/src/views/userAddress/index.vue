<template>
    <el-main>
		<el-card shadow="never">
            <div shadow="never" style="border:none">
                <el-form :inline="true" :model="searchForm" class="demo-form-inline">
                    <el-form-item label="会员id">
                        <el-input v-model="searchForm.user_id" placeholder="会员id" clearable></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
                <el-table-column prop="id" label="ID"/>
                <el-table-column prop="user_id" label="用户id"/>
                <el-table-column prop="receiver" label="收件人名"/>
                <el-table-column prop="phone" label="收件人手机号"/>
                <el-table-column label="收件人地址">
					<template #default="scope">
						{{scope.row.city_name}}{{scope.row.area_name}}{{scope.row.address}}
					</template>
				</el-table-column>
				<el-table-column label="是否默认">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.default_flag == 1">是</el-tag>
						<el-tag type="error" v-if="scope.row.default_flag == 2">否</el-tag>
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
		name: 'userAddressIndex',
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
                    user_id: '',
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
            handleEdit(row) {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open('edit').setData(row)
				})
            },
			handleDel(row) {
				this.$confirm('此操作将永久删除该会员地址, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async() => {
					let res = await this.$API.userAddress.del.post({id: row.id})
					if (res.code === 0) {
						this.$message.success(res.msg)
						await this.getList()
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
                let res = await this.$API.userAddress.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.total = res.data.total
            },
            handleSuccess() {
                this.getList()
            },
        }
    }
</script>
