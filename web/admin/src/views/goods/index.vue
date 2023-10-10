<template>
    <el-main>
        <el-card v-if="type == 1" shadow="never">
            <div class="search-box">
                <el-form :model="searchForm">
                    <el-form-item label="商品状态">
                        <el-button-group>
                            <el-button :class="{'primary': searchForm.status == 1}" @click="checkTab(1)">已上架</el-button>
                            <el-button :class="{'primary': searchForm.status == 2}" @click="checkTab(2)">已下架</el-button>
                        </el-button-group>
                    </el-form-item>
                </el-form>
                <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="margin-top: 20px;">
                    <el-form-item label="商品名称">
                        <el-input v-model="searchForm.name" placeholder="" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="商品类型">
                        <el-select v-model="searchForm.goods_type" placeholder="请选择" clearable>
                            <el-option label="普通商品" value="1" />
                            <el-option label="盲盒商品" value="2" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="商品分类">
                        <el-cascader
                            v-model="selectedCate"
                            :options="goodsCate"
                            :props="treeProps"
                            clearable
                            ></el-cascader>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit" icon="el-icon-search" >查询</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-bottom: 15px"></div>
            <el-button type="primary" icon="el-icon-plus" @click="addGoods">添加商品</el-button>
            <el-button type="primary" icon="el-icon-shopping-cart-full" @click="shelves(1)" v-if="searchForm.status == 2">批量上架</el-button>
            <el-button type="primary" icon="el-icon-shopping-cart-full" @click="shelves(2)" v-if="searchForm.status == 1">批量下架</el-button>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-table
                :data="tableData"
                @selection-change="handleSelectionChange"
                :row-style="{ height: '57px'}"
                style="width: 100%;font-size: 12px">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="商品ID">
                </el-table-column>
                <el-table-column
                        label="商品图"
                      >
                    <template #default="scope">
                        <el-image :src="scope.row.image.split(',')[0]" style="width: 36px;height: 36px;"></el-image>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="商品名称">
                </el-table-column>
                <el-table-column
                        prop="cate.name"
                        label="商品分类">
                </el-table-column>
                <el-table-column
                    label="商品类型">
                    <template #default="scope">
                        <el-tag type="danger" v-if="scope.row.goods_type == 1">普通商品</el-tag>
                        <el-tag type="success" v-if="scope.row.goods_type == 2">盲盒商品</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="展示价格">
                </el-table-column>
                <el-table-column
                        prop="recovery_price"
                        label="可兑换哈希币">
                </el-table-column>
                <el-table-column
                        prop="integral_price"
                        label="售价(哈希币)">
                </el-table-column>
                <el-table-column
                        label="库存">
                    <template #default="scope">
                        <span v-if="scope.row.stock == -1">无限库存</span>
                        <span v-else>{{  scope.row.stock  }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="状态">
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status == 1">上架</el-tag>
                        <el-tag type="danger" v-if="scope.row.status == 2">下架</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    label="操作"
                    width="140">
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

        <el-card v-if="type == 2" shadow="false" style="display:flex">
            <div class="click-bar">
                <div @click="type = 1">
                    <el-icon class="back-icon"><el-icon-arrow-left /></el-icon>返回
                </div>
                <el-divider direction="vertical" /> <span style="font-size: 14px;font-weight: 700;">{{ title }}</span>
            </div>
        </el-card>
        <el-card v-if="type == 2" shadow="false" style="margin-top: 10px;">
            <save-page @save="saveGoods" ref="saveDialog"></save-page>
        </el-card>
    </el-main>
</template>

<script>

    import savePage from './save'

    export default {
        name: "goods",
        components: {
			savePage
		},
        data() {
            return {
                type: 1,
                selectedCate: '',
                goodsCate: [],
                treeProps: {
                    value: 'id',
                    label: 'name',
                    children: 'children',
                    checkStrictly: true
                },
                searchForm: {
                    goods_type: '',
                    cate_id: '',
                    status: 1,
                    name: '',
                    page: 1,
                    limit: 10
                },
                page: {
                    total: 0
                },
                tableData: [],
                shelfForm: {
                    ids: [],
                    status: 1
                },
                title: '添加商品'
            }
        },
        mounted() {
            this.getList()
            this.getGoodsCate()
        },
        methods: {
            // 查询
            onSubmit() {
                if (this.selectedCate && this.selectedCate.length > 0) {
                  this.searchForm.cate_id = this.selectedCate[this.selectedCate.length - 1]
                } else { 
                  this.searchForm.cate_id = 0
                }
                this.searchForm.page = 1
                this.getList()
            },
            checkTab(type) {
                this.searchForm.status = type
                this.getList()
            },
            // 添加商品
            addGoods() {
                this.type = 2
                this.title = '添加商品'
                this.$nextTick(() => {
                    this.$refs.saveDialog.setMode('add')
                })
            },
            // 批量上下架
            async shelves(type) {

                if (this.shelfForm.ids.length == 0) {
                    this.$message.error('请先勾选要操作的商品')
                    return ;
                }

                this.shelfForm.type = type
                let title = '您确定你要批量上架这批商品?'
                if (type == 2) {
                    title = '您确定你要批量下架这批商品?'
                }

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let res = await this.$API.goods.shelves.post(this.shelfForm)
                    if (res.code == 0) {
                        this.$message.success(res.msg)
                        this.getList()
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(() => {});
            },
            // 翻页
            pageChangeHandle(page) {
                this.searchForm.page = page
                this.getList()
            },
            // 全选反选
            handleSelectionChange(val) {
                this.shelfForm.ids = []
                val.forEach(item => {
                    this.shelfForm.ids.push(item.id)
                })
            },
            // 编辑商品
            handleEdit(row) {
                this.type = 2
                this.title = '编辑商品'
                this.$nextTick(() => {
                    this.$refs.saveDialog.setMode('edit').setData(row)
                })
            },
            // 删除商品
            handleDel(row) {
                this.$confirm('此操作将删除该商品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let res = await this.$API.goods.del.get({id: row.id})
                    if (res.code == 0) {
                        this.$message.success(res.msg)
                        this.getList()
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(() => {});
            },
            // 保存商品
            saveGoods() {
                this.type = 1
                this.getList()
            },
            // 获取商品列表
            async getList() {
                let res = await this.$API.goods.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
            // 获取商品分类
            async getGoodsCate() {
                let res = await this.$API.goodsCate.list.get({hashmart_auth_skip: 1})
                this.goodsCate = res.data
            }
        }
    }
</script>

<style scoped>
.primary {
    color: #FFF;
    background-color: #409EFF;
    border-color: #409EFF;
}
.click-bar {
    width: 150px;
    height: 16px;
    line-height: 16px;
    cursor: pointer;
    display: flex;
}
.click-bar .back-icon {
    position: relative;
    top: 2px;
}
</style>
