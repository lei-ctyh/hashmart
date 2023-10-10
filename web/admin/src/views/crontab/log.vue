<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="1000" destroy-on-close @closed="$emit('closed')">
		<el-button type="primary" icon="el-icon-refresh" size="small" @click="refresh"></el-button>
        <el-table
            class="tableClass"
            :data="tableData"
            v-loading="loading"
            style="width: 100%;margin-top: 20px">
            <el-table-column
                    prop="id"
                    label="ID">
            </el-table-column>
            <el-table-column
                    prop="return_var"
                    label="运行结果">
                <template #default="scope">
                    <el-tag type="success" v-if="scope.row.return_var == 0">成功</el-tag>
                    <el-tag type="danger" v-if="scope.row.return_var == 1">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="command"
                    :show-overflow-tooltip="true"
                    label="任务命令">
            </el-table-column>
            <el-table-column
                    prop="running_time"
                    label="运行耗时">
            </el-table-column>
            <el-table-column
                    prop="output"
                    :show-overflow-tooltip="true"
                    label="输出结果">
            </el-table-column>
            <el-table-column
                    prop="create_time"
                    label="执行时间">
            </el-table-column>
        </el-table>
        <el-pagination
                background
                layout="->, prev, pager, next"
                @current-change="handleLogCurrentChange"
                :page-size="logPage.limit"
                :total="total" style="margin-top: 10px">
        </el-pagination>
	</el-dialog>
</template>

<script>
	export default {
		emits: ['success', 'closed'],
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '执行日志',
					show: '查看'
				},
        tableData: [],
        total: 0,
        logPage: {
          sid: 0,
          page: 1,
          limit: 10
        },
        loading: true,
				visible: false
			}
		},
		mounted() {
			
		},
		methods: {
			// 显示
			open(mode='add') {
				this.mode = mode;
				this.visible = true;
				return this
			},
			// 表单注入数据
			setData(data) {
        this.logPage.sid = data.id
        this.runLog()
			},
      // 拉日志
      async runLog() {
          let res = await this.$API.crontab.log.get(this.logPage)
          this.tableData = res.data.list
          this.total = res.data.count
          this.loading = false
      },
      // 刷新
      refresh() {
          this.loading = true
          this.logPage.page = 1
          this.runLog()
      },
      handleLogCurrentChange(page) {
          this.logPage.page = page
          this.runLog()
      },
		}
	}
</script>

<style scoped>

</style>
