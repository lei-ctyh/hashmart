<template>
    <el-main>
        <el-card shadow="false" style="display:flex">
            <div class="click-bar" style="width:300px;display: flex;">
                <div @click="goBack()" style="cursor: pointer;">
                    <el-icon class="back-icon"><el-icon-arrow-left /></el-icon>返回列表
                </div>
                <span style="font-size: 14px;font-weight: 700;margin-left:20px">盲盒商品详情</span>
            </div>
        </el-card>
		<el-card shadow="never">
            <div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="商品名称">
						<el-input v-model="searchForm.goods_name" placeholder="商品名称" clearable></el-input>
					</el-form-item>
          <el-form-item label="标签">
            <el-select v-model="searchForm.tag_id" placeholder="请选择" clearable>
                  <el-option
                      v-for="item in goodsTag"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
              </el-select>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-button type="primary" icon="el-icon-plus" @click="addBlindbox" style="margin-top: 15px">添加盲盒商品</el-button>
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
                    label="盲盒名称">
                    <template #default="scope">
                        {{ scope.row.blindbox.name }}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="goods_name"
                    label="商品名称">
                </el-table-column>
                <el-table-column
                    prop="boxTag.name"
                    label="标签"
                    width="100px">
                </el-table-column>
                <el-table-column
                    label="商品图片"
                    width="100px">
                    <template #default="scope">
                        <el-image :src="scope.row.goods_image.split(',')[0]" style="height:36px;width: 36px;"></el-image>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="price"
                    label="售价"
                    width="100px">
                </el-table-column>
                <el-table-column
                    prop="recovery_price"
                    label="可兑换哈希币"
                    width="100px">
                </el-table-column>
                <el-table-column
                    label="中奖范围">
                    <template #default="scope">
                        <el-tag>{{ scope.row.lottery_min_no }} ~ {{ scope.row.lottery_max_no }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    label="中奖概率">
                    <template #default="scope">
                        <el-tag type="danger">{{ scope.row.probability }} %</el-tag>
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
    import saveDialog from './add'

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
                    blindbox_id: this.$route.query.id,
                    tag_id: '',
                    goods_name: '',
                    page: 1,
                    limit: 15
                },
                page: {
                    total: 1
                },
                goodsTag: []
			}
		},
        mounted() {
            this.getList()
            this.getBlindboxTag()
        },
        methods: {
            // 搜索
            search() {
                this.getList()
            },
            // 添加盲盒商品
            addBlindbox() {
                this.dialog.save = true
                this.$nextTick(() => {
					this.$refs.saveDialog.open('add', this.searchForm.blindbox_id)
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
                this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let res = await this.$API.blindboxDetail.del.get({id: row.id, blindbox_id: row.blindbox_id})
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
                let res = await this.$API.blindboxDetail.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
            handleSuccess() {
                this.getList()
            },
            pageChangeHandle(page) {
                this.searchForm.page = page
                this.getList()
            },
            // 获取盲盒标签
			async getBlindboxTag() {
				let res = await this.$API.blindboxTag.list.get({limit: 9999999, page: 1, status: 1, hashmart_auth_skip: 1})
				this.goodsTag = res.data.rows
			},
            goBack() {
                this.$router.push({
					path: '/blindbox/index'
				})
            }
        }
    }
</script>

<style scoped>
.back-icon {
    position: relative;
    top: 2px;
    margin-top: 3px;
}
</style>
