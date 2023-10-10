<template>
    <el-main>
		<el-card shadow="never">
            <el-button type="primary" icon="el-icon-plus" @click="addTop">添加顶级菜单</el-button>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>

            <el-table
                :data="tableData"
                row-key="id"
                style="width: 100%;margin-top: 20px;">
                <el-table-column
                    type=""
                    prop="id"
                    label="ID">
                </el-table-column>
                <el-table-column
                    prop="name"
                    label="菜单">
                </el-table-column>
                <el-table-column
                    label="图标">
                    <template #default="scope">
                        <el-icon><component :is="scope.row.icon" /></el-icon>
                    </template>
                </el-table-column>
                <el-table-column
                    label="类型">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.type == 1">菜单</el-tag>
                        <el-tag v-if="scope.row.type == 2">功能</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="path"
                    label="路径">
                </el-table-column>
                <el-table-column
                    prop="sort"
                    label="排序">
                </el-table-column>
                <el-table-column
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleAddSub(scope.row)" type="text" size="small" v-if="scope.row.type==1">子菜单</el-button>
                        <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false"></save-dialog>
    </el-main>
</template>

<script>
    import saveDialog from './save'

    export default {
		name: 'menuOpt',
        components: {
			saveDialog
		},
		data() {
			return {
                dialog: {
                    save: false
                },
                tableData: []
			}
		},
        mounted() {
            this.getList()
        },
        methods: {
            // 添加顶级菜单
            addTop() {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open()
				})
            },
            // 添加子菜单
            handleAddSub(row) {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open().setParentData(row)
				})
            },
            // 编辑
            handleEdit(row) {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open('edit').setData(row)
				})
            },
            // 删除
            handleDel(row) {
                this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let res = await this.$API.menu.del.get({id: row.id})
                    if (res.code == 0) {
                        this.$message.success(res.msg)
                        this.getList()
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(() => {});
            },
            // 获取列表
            async getList() {
                let res = await this.$API.menu.list.get()
                this.tableData = res.data
            },
            handleSuccess() {
                this.getList()
            }
        }
    }
</script>