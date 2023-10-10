<template>
    <el-main>
        <el-card shadow="false" style="display:flex">
            <div class="click-bar" style="width:300px;display: flex;">
                <div @click="goBack()" style="cursor: pointer;">
                    <el-icon class="back-icon"><el-icon-arrow-left /></el-icon>返回列表
                </div>
                <span style="font-size: 14px;font-weight: 700;margin-left:20px">奖品详情</span>
            </div>
        </el-card>
		<el-card shadow="never">
            <el-table
                :data="tableData"
                style="width: 100%;margin-top: 20px;">
                <el-table-column
                    prop="id"
                    label="ID">
                </el-table-column>
				<el-table-column
					prop="goods_id"
					label="商品id">
				</el-table-column>
				<el-table-column
					prop="goods_name"
					label="商品名称">
				</el-table-column>
                <el-table-column
					label="中奖范围">
                    <template #default="scope">
                        {{ scope.row.range.split(',').join('~') }}
                    </template>
				</el-table-column>
				<el-table-column
					label="商品图片">
					<template #default="scope">
						<el-image :src="scope.row.goods_image" style="height: 36px;width: 36px;"></el-image>
					</template>
				</el-table-column>
                <el-table-column
					prop="hash_key"
					label="用户种子">
				</el-table-column>
                <el-table-column
					prop="order_time"
					label="下单毫秒">
				</el-table-column>
                <el-table-column
					prop="hash_no"
					label="HASH值">
				</el-table-column>
				<el-table-column
					prop="goods_price"
					label="商品价值">
				</el-table-column>
				<el-table-column
					prop="recovery_price"
					label="可兑哈希币">
				</el-table-column>
                <el-table-column
					label="盈亏">
                    <template #default="scope">
                        <div v-if="scope.row.profit > 0" style="color:#F56C6C">{{ scope.row.profit }}</div>
						<div v-else style="color:#67C23A">{{ scope.row.profit }}</div>
                    </template>
				</el-table-column>
                <el-table-column
					label="状态">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status == 1">盒子中</el-tag>
                        <el-tag v-if="scope.row.status == 2" type="sucess">已兑换</el-tag>
                        <el-tag v-if="scope.row.status == 3" type="danger">已提货</el-tag>
                    </template>
				</el-table-column>
            </el-table>
<!--            <div class="page-div" style="margin-top: 20px">-->
<!--                <el-pagination-->
<!--                    background-->
<!--                    layout="->, prev, pager, next"-->
<!--                    :page-size="searchForm.limit"-->
<!--                    @current-change="pageChangeHandle"-->
<!--                    :total="page.total">-->
<!--                </el-pagination>-->
<!--            </div>-->
        </el-card>

    </el-main>
</template>

<script>
    export default {
		name: 'orderRecordDetail',
		data() {
			return {
                dialog: {
                    save: false
                },
                tableData: [],
                searchForm: {
					order_record_id: this.$route.query.order_record_id,
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
        },
        methods: {
            // 搜索
            search() {
                this.getList()
            },
            // 获取列表
            async getList() {
				let res = await this.$API.userOrderRecordDetail.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
            pageChangeHandle(page) {
                this.searchForm.page = page
                this.getList()
            },
            goBack() {
                this.$router.push({
					path: '/userOrderRecord/index'
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
