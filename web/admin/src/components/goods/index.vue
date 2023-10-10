<template>
    <div class="goods-select">
        <div class="search-box">
			<el-form :inline="true" :model="searchForm" class="demo-form-inline" style="margin-top: 20px;">
				<el-form-item label="商品名称">
					<el-input v-model="searchForm.name" placeholder="" clearable></el-input>
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
					label="商品ID">
			</el-table-column>
			<el-table-column
					label="商品图"
					>
				<template #default="scope">
					<el-image :src="scope.row.image.split(',')[0]" style="width:36px;height: 36px;"></el-image>
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
					label="商品售价">
			</el-table-column>
			<el-table-column
					prop="sales"
					label="销量">
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
          selectedgoods: {
              type: Array,
              default: () => []
          },
          // 一页显示多少资源
          pageSize: {
              type: Number,
              default: 10
          },
          goodsType: {
              type: Number,
              default: 0
          },
      },
      data() {
        return {
            treeProps: {
                value: 'id',
                label: 'name',
                children: 'children',
                checkStrictly: true
            },
            searchForm: {
                goods_type: '',
                cate_id:'',
                status: 1,
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
            this.searchForm.limit = this.pageSize
            this.searchForm.goods_type = this.goodsType
            this.getGoodsList()
			      this.getGoodsCate()
        },
        methods: {
            // 获取商品列表
            async getGoodsList() {
                let res = await this.$API.goods.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.page.total = res.data.total
            },
            // 获取商品分类
            async getGoodsCate() {
              let res = await this.$API.goodsCate.list.get()
              this.goodsCate = res.data
            },
            // 单选
            singleElection(row) {
              this.templateSelection = row.id
              this.selectedGoods = this.tableData.filter((item) => item.id === row.id)
              
              this.$emit('handleSelected', this.selectedGoods)
            },
                  // 翻页
            pageChangeHandle(page) {
              this.searchForm.page = page
                      this.getGoodsList()
            },
            onSubmit() {
              if(this.selectedCate){
                if(this.selectedCate.length == 1){
                  this.searchForm.cate_id = this.selectedCate[0]
                }
                if(this.selectedCate.length == 2){
                  this.searchForm.cate_id = this.selectedCate[1]
                }
              }else{
                this.searchForm.cate_id = ''
              }
              this.getGoodsList()
            },
        }
    }
</script>