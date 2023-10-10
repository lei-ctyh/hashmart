<template>
    <el-main>
		<el-card shadow="never">
            <div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="盲盒名称">
						<el-input v-model="searchForm.name" placeholder="盲盒名称" clearable></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-button type="primary" icon="el-icon-plus" @click="addBlindbox" style="margin-top: 15px">添加盲盒</el-button>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>

            <el-table
                :data="tableData"
                style="width: 100%;margin-top: 20px;">
                <el-table-column
                    prop="id"
                    label="ID"
                    width="100px">
                </el-table-column>
                <el-table-column
                    prop="name"
                    label="盲盒名称">
                </el-table-column>
                <el-table-column
                    label="图标"
                    width="100px">
                    <template #default="scope">
                        <el-image :src="scope.row.pic" style="height: 36px;width: 36px;"></el-image>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="price"
                    label="单抽价格"
                    width="100px">
                </el-table-column>
                <el-table-column
                    prop="sales"
                    label="累计销量"
                    width="100px">
                </el-table-column>
                <el-table-column
                    prop="goods_num"
                    label="商品数量"
                    width="100px">
                </el-table-column>
                <el-table-column
                    label="是否热门"
                    width="100px">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.hot_tag == 1">是</el-tag>
                        <el-tag type="danger" v-if="scope.row.hot_tag == 2">否</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    label="是否推荐"
                    width="100px">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.recommend_tag == 1">是</el-tag>
                        <el-tag type="danger" v-if="scope.row.recommend_tag == 2">否</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="sort"
                    label="排序"
                    width="100px">
                </el-table-column>
                <el-table-column
                    label="商品详情">
                    <template #default="scope">
                        <el-button type="primary" plain size="small" @click="goodsDetail(scope.row)" icon="el-icon-Goods">商品详情</el-button>
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="page-div" style="margin-top: 20px">
                <el-pagination
                    background
                    layout="->, prev, pager, next"
                    :page-size="searchForm.limit"
                    @current-change="pageChangeHandle"
                    :total="page.total">
                </el-pagination>
            </div>
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
                tableData: [],
                searchForm: {
                    name: '',
                    page: 1,
                    limit: 15
                },
                page: {
                    total: 1
                }
			}
		},
        mounted() {
            this.getList()
        },
        methods: {
            // 查询
            search() {
                this.getList()
            },
            // 添加
            addBlindbox() {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open()
				})
            },
            // 商品详情
			goodsDetail(row) {
				this.$router.push({
					path: '/blindbox/detail',
                    query: {
						id: row.id
					}
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
                    let res = await this.$API.blindbox.del.get({id: row.id})
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
                let res = await this.$API.blindbox.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
            handleSuccess() {
                this.getList()
            },
            pageChangeHandle(page) {
                this.searchForm.page = page
                this.getList()
            }
        }
    }
</script>
