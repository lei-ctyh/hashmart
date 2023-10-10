<template>
    <div class="goods-select">
        <div class="search-box">
			<el-form :inline="true" :model="searchForm" class="demo-form-inline" style="margin-top: 20px;">
				<el-form-item label="盲盒名称">
					<el-input v-model="searchForm.name" placeholder="" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit" icon="el-icon-search" >查询</el-button>
				</el-form-item>
			</el-form>
		</div>
		<el-table
			:data="tableData"
			:row-style="{ height: '57px'}"
			@row-click="singleElection"
      		highlight-current-row
			style="width: 100%;font-size: 12px">
			<el-table-column width="55" label="选择">
				<template #default="scope">
					<el-radio class="radio" v-model="templateSelection" :label="scope.row.id">&nbsp;</el-radio>
				</template>
			</el-table-column>
			<el-table-column
					prop="id"
					label="盲盒ID">
			</el-table-column>
			<el-table-column
					label="盲盒主图"
					>
				<template #default="scope">
					<el-image :src="scope.row.pic" style="width:36px;height: 36px;"></el-image>
				</template>
			</el-table-column>
			<el-table-column
					prop="name"
					label="盲盒名称">
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
    </div>
</template>

<script>
     export default {
		props: {
            selectedBox: {
                type: Array,
				default: () => []
            },
            // 一页显示多少资源
            pageSize: {
                type: Number,
                default: 10
            }
		},
        data() {
            return {
                searchForm: {
                    name: '',
                    page: 1,
                    limit: 10
                },
                page: {
                    total: 0
                },
                tableData: [],
				templateSelection: "",
            }
        },
        mounted() {
            this.getBlindboxList()
        },
        methods: {
            // 获取盲盒列表
            async getBlindboxList() {
                let res = await this.$API.blindbox.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
			// 单选
			singleElection(row) {
				this.templateSelection = row.id
				let selectedBox = this.tableData.filter((item) => item.id === row.id)
				
				this.$emit('handleSelected', selectedBox)
			},
            // 翻页
			pageChangeHandle(page) {
				this.searchForm.page = page
                this.getBlindboxList()
			},
            onSubmit() {
				this.getBlindboxList()
			},
        }
    }
</script>