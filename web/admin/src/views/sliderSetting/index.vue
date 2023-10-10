<template>
	<el-main>
		<el-card shadow="never">
			<div shadow="never" style="border:none">
				<el-form :inline="true" :model="searchForm" class="demo-form-inline">
					<el-form-item label="商品名称">
						<el-input v-model="searchForm.goods_name" placeholder="商品名称" clearable></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-button type="primary" icon="el-icon-plus" @click="addSlider" style="margin-top: 15px">添加轮播配置</el-button>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>

			<el-table :data="tableData" style="width: 100%;margin-top: 20px;">
				<el-table-column prop="id" label="ID"/>
				<el-table-column label="轮播图">
					<template #default="scope">
						<el-image :src="scope.row.pic" style="width: 60px;height: auto;"></el-image>
					</template>
				</el-table-column>
				<el-table-column prop="goods_name" label="商品名称" />
				<el-table-column label="商品类型">
					<template #default="scope">
						<el-tag type="warning" v-if="scope.row.goods_type == 1">普通商品</el-tag>
						<el-tag type="success" v-if="scope.row.goods_type == 2">盲盒商品</el-tag>
						<el-tag type="info" v-if="scope.row.goods_type == 3">哈希币</el-tag>
						<el-tag type="warning" v-if="scope.row.goods_type == 4">优惠券商品</el-tag>
						<el-tag  v-if="scope.row.goods_type == 5">余额商品</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="位置">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.type == 1">首页</el-tag>
						<el-tag type="warning" v-if="scope.row.type == 2">哈希币商城</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="title" label="轮播描述"/>
				<el-table-column label="状态">
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.status == 1">启用</el-tag>
						<el-tag type="warning" v-if="scope.row.status == 2">禁用</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="create_time" label="创建时间" />
				<el-table-column width="250"
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
import saveDialog from './add'

export default {
	name: 'silderSettingIndex',
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
				goods_name: '',
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
		handleSuccess() {
			this.getList()
		},
		pageChange(page) {
			this.searchForm.page = page
			this.getList()
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
			this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(async () => {
				let res = await this.$API.sliderSetting.del.get({id: row.id})
				if (res.code == 0) {
					this.$message.success(res.msg)
					this.getList()
				} else {
					this.$message.error(res.msg)
				}
			}).catch(() => {});
		},
		// 添加
		addSlider() {
			this.dialog.save = true
			this.$nextTick(() => {
				this.$refs.saveDialog.open('add', this.searchForm)
			})
		},
		// 获取列表
		async getList() {
			let res = await this.$API.sliderSetting.list.get(this.searchForm)
			this.tableData = res.data.rows
			this.total = res.data.total
		}
	}
}
</script>
