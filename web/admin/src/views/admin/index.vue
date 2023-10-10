<template>
    <el-main>
		<el-card shadow="never">
            <div shadow="never" style="border:none">
                <el-form :inline="true" :model="searchForm" class="demo-form-inline">
                    <el-form-item label="账号">
                        <el-input v-model="searchForm.username" placeholder="账号" clearable></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-button type="primary" icon="el-icon-plus" @click="add">添加管理员</el-button>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
                <el-table-column prop="id" label="ID"/>
                <el-table-column prop="username" label="账号"/>
                <el-table-column prop="nickname" label="昵称" />
                <!--<el-table-column label="头像">
                    <template #default="scope">
                        <el-avatar :src="scope.row.avatar"></el-avatar>
                    </template>
                </el-table-column>-->
                <el-table-column prop="role.name" label="角色" />
                <el-table-column prop="last_login_time" label="上次登录时间" />
                <el-table-column prop="last_login_ip" label="上次登录IP" />
                <el-table-column label="状态">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
                        <el-tag type="error" v-if="scope.row.status == 2">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleEdit(scope.row)" type="text" size="small" v-if="scope.row.id != 1">编辑</el-button>
                        <el-button @click="handleDel(scope.row)" type="text" size="small" v-if="scope.row.id != 1">删除</el-button>
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
		name: 'adminIndex',
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
                    username: '',
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
                this.$confirm('此操作将永久删除该管理员, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async() => {
                   let res = await this.$API.admin.del.get({id: row.id})
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
                let res = await this.$API.admin.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.total = res.data.total
            },
            handleSuccess() {
                this.getList()
            },
            // 添加管理员
            add() {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open()
				})
            }
        }
    }
</script>
