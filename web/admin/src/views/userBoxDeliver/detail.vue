<template>
    <el-main>
        <el-card shadow="false" style="display:flex">
            <div class="click-bar" style="width:300px;display: flex;">
                <div @click="goBack()" style="cursor: pointer;">
                    <el-icon class="back-icon"><el-icon-arrow-left /></el-icon>返回列表
                </div>
                <span style="font-size: 14px;font-weight: 700;margin-left:20px">发货详情</span>
            </div>
        </el-card>
		    <el-card shadow="never">
          <h3>收件信息</h3>
          <div style="font-size: 14px;margin-top: 10px;">
              <p style="margin-top: 10px;">收件人：{{ addressInfo.receiver }}</p>
              <p style="margin-top: 10px;">手机号：{{ addressInfo.phone }}</p>
              <p style="margin-top: 10px;">收件地址：{{ addressInfo.province_name }}{{ addressInfo.city_name }}{{ addressInfo.area_name }}{{ addressInfo.address }}</p>
          </div>
          <el-divider></el-divider>

            <el-table
                :data="tableData"
                style="width: 100%;margin-top: 20px;">
                <el-table-column
                    prop="id"
                    label="ID">
                </el-table-column>
                <el-table-column
                  prop="reward.goods_id"
                  label="商品id">
                </el-table-column>
                <el-table-column
                  prop="reward.goods_name"
                  label="商品名称">
                </el-table-column>
                <el-table-column
                  label="商品图片">
                  <template #default="scope">
                    <el-avatar shape="square" :size="36" :src="scope.row.reward ? scope.row.reward.goods_image : '' "></el-avatar>
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

    </el-main>
</template>

<script>
  export default {
		name: 'goodsCate',
		data() {
			return {
          dialog: {
              save: false
            },
            tableData: [],
            searchForm: {
                deliver_id: this.$route.query.id,
                // goods_id: '',
                page: 1,
                limit: 15
            },
            page: {
                total: 1
            },
            goodsTag: [],
            addressInfo: {}
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
              let res = await this.$API.userBoxDeliverDetail.list.get(this.searchForm)
              this.tableData = res.data.rows
            this.page.total = res.data.total

            if (res.data.address_info) {
              this.addressInfo = JSON.parse(res.data.address_info)
            }
          },
          pageChangeHandle(page) {
              this.searchForm.page = page
              this.getList()
          },
          goBack() {
              this.$router.push({
                path: '/userBoxDeliver/index'
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
