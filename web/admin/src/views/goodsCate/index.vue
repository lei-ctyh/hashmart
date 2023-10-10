<template>
    <el-main>
		<el-card shadow="never">
            <el-button type="primary" icon="el-icon-plus" @click="addTop">添加顶级分类</el-button>
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
                    label="分类名称">
                </el-table-column>
                <el-table-column
                    label="图标">
                    <template #default="scope">
                        <el-image :src="scope.row.icon" style="height: 36px;width: 36px;"></el-image>
                    </template>
                </el-table-column>
                <el-table-column
                    label="状态">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status == 1">正常</el-tag>
                        <el-tag type="danger" v-if="scope.row.status == 2">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="sort"
                    label="排序">
                </el-table-column>
                <el-table-column
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleAddSub(scope.row)" type="text" size="small" v-if="scope.row.pid==0">子分类</el-button>
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
        name: 'goodsCate',
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
                this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let res = await this.$API.goodsCate.del.get({id: row.id})
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
                let res = await this.$API.goodsCate.list.get()
                this.tableData = res.data
            },
            handleSuccess() {
                this.getList()
            }
        }
      }
</script>