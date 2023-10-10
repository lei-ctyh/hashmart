<template>
    <el-main>
		<el-card shadow="never">
      <div shadow="never" style="border:none">
          <el-form :inline="true" :model="searchForm" class="demo-form-inline">
              <el-form-item label="用户ID">
                  <el-input v-model="searchForm.user_id" placeholder="用户ID" clearable></el-input>
              </el-form-item>
              <el-form-item label="昵称">
                  <el-input v-model="searchForm.nickname" placeholder="昵称" clearable></el-input>
              </el-form-item>
              <el-form-item label="手机号">
                  <el-input v-model="searchForm.phone" placeholder="手机号" clearable></el-input>
              </el-form-item>
              <el-form-item label="注册时间">
              <el-date-picker
                v-model="searchForm.create_time"
                type="daterange"
                value-format="YYYY-MM-DD"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="来源">
              <el-select v-model="searchForm.source_type" placeholder="请选择来源">
                <el-option label="微信" value="1"></el-option>
                <el-option label="后台" value="2"></el-option>
                <el-option label="app" value="3"></el-option>
              </el-select>
            </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="search">查询</el-button>
              </el-form-item>
          </el-form>
      </div>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
			<el-button type="primary" icon="el-icon-plus" @click="addUser()" style="margin-top: 15px">添加会员</el-button>
			<div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <div style="width:100%;height:0;border-bottom:#E4E7ED 1px dashed;margin-top: 15px"></div>
            <el-table :data="tableData" style="width: 100%;margin-top: 20px;">
                <el-table-column prop="id" label="ID"/>
                <el-table-column prop="nickname" label="昵称"/>
                <el-table-column label="头像">
                    <template #default="scope">
                        <el-avatar :src="scope.row.avatar"></el-avatar>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号" />
                <el-table-column prop="integral" label="哈希币" />
                <el-table-column prop="balance" label="余额" />
                <el-table-column label="注册来源">
                  <template #default="scope">
                    <el-tag effect="dark" v-if="scope.row.source_type === 1">微信</el-tag>
                    <el-tag effect="light" v-if="scope.row.source_type === 2">后台</el-tag>
                    <el-tag effect="plain" v-if="scope.row.source_type === 3">app</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="状态">
                  <template #default="scope">
                    <el-tag type="success" v-if="scope.row.status === 1">正常</el-tag>
                    <el-tag type="error" v-if="scope.row.status === 2">禁用</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="create_time" label="注册时间" />
                <el-table-column
                    width="250px"
                    fixed="right"
                    label="操作">
                    <template #default="scope">
                        <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="handleIntegral(scope.row)" type="text" size="small">哈希币</el-button>
                        <el-button @click="handleBalance(scope.row)" type="text" size="small">余额</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div style="margin-top:20px"></div>
            <el-pagination background layout="->, prev, pager, next" :total="total" :page-size="searchForm.limit" @current-change="pageChange"/>
        </el-card>
		    <save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false" :close-on-click-modal="false"></save-dialog>

        <el-dialog
            title="操作哈希币"
            v-model="dialog.integral"
            :close-on-click-modal="false"
            width="500px">
            <el-form ref="form1" :model="integralForm" label-width="100px">
                <el-form-item label="现有哈希币">
                    <el-tag>{{ integralForm.originalIntegral }}</el-tag>
                </el-form-item>
                <el-form-item label="操作">
                    <el-radio-group v-model="integralForm.type">
                    <el-radio :label="1">新增</el-radio>
                    <el-radio :label="2">减少</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="变动的哈希币">
                    <el-input v-model="integralForm.integral" type="number" :min="0"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialog.integral = false">取消</el-button>
                    <el-button type="primary" @click="changeIntegral()">
                    保存
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog
            title="操作余额"
            v-model="dialog.balance"
            :close-on-click-modal="false"
            width="500px">
            <el-form ref="form1" :model="balanceForm" label-width="100px">
                <el-form-item label="现有余额">
                    <el-tag>{{ balanceForm.originalBalance }}</el-tag>
                </el-form-item>
                <el-form-item label="操作">
                    <el-radio-group v-model="balanceForm.type">
                    <el-radio :label="1">新增</el-radio>
                    <el-radio :label="2">减少</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="变动的余额">
                    <el-input v-model="balanceForm.balance" type="number" :min="0"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialog.balance = false">取消</el-button>
                    <el-button type="primary" @click="changeBalance()">
                    保存
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </el-main>

</template>

<script>

import saveDialog from './save'
    export default {
		name: 'userIndex',
		components: {
			saveDialog
		},
		data() {
			return {
				dialog: {
					save: false,
                    integral: false,
                    balance: false
				  },
          total: 1,
          searchForm: {
              user_id: '',
              nickname: '',
              phone: '',
              create_time: '',
              source_type: '',
              page: 1,
              limit: 15
          },
          integralForm: {
              id: 0,
              name: '',
              integral: 0,
              type: 1, // 1增加 2减少
              originalIntegral: 0
          },
          balanceForm: {
              id: 0,
              name: '',
              balance: 0,
              type: 1, // 1增加 2减少
              origralBalance: 0
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
            // 添加会员
            addUser() {
                  this.dialog.save = true
                  this.$nextTick(() => {
                    this.$refs.saveDialog.open()
                  })
            },
            handleEdit(row) {
                this.dialog.save = true
                this.$nextTick(() => {
                  this.$refs.saveDialog.open('edit').setData(row)
                })
            },
            pageChange(page) {
                this.searchForm.page = page
                this.getList()
            },
            // 获取列表
            async getList() {
                let res = await this.$API.user.list.get(this.searchForm)
                this.tableData = res.data.rows
                this.total = res.data.total
            },
            // 操作哈希币
            handleIntegral(row) {
                this.integralForm.originalIntegral = row.integral
                this.integralForm.name = row.nickname
                this.integralForm.id = row.id
                this.dialog.integral = true
            },
            // 改变哈希币
            async changeIntegral() {
                if (this.integralForm.integral <= 0) {
                    this.$message.error('变动的哈希币必须大于0')
                    return ;
                }

                let res = await this.$API.user.integral.post(this.integralForm)
                if (res.code == 0) {
                    this.$message.success(res.msg)
                    setTimeout(() => {
                        this.getList()
                        this.dialog.integral = false
                    }, 800)
                } else {
                    this.$message.error(res.msg)
                }
            },
            // 操作余额
            handleBalance(row) {
                this.balanceForm.originalBalance = row.balance
                this.balanceForm.id = row.id
                this.balanceForm.name = row.nickname
                this.dialog.balance = true
            },
            // 改变余额
            async changeBalance() {
                if (this.integralForm.balance <= 0) {
                    this.$message.error('变动的余额必须大于0')
                    return ;
                }

                let res = await this.$API.user.balance.post(this.balanceForm)
                if (res.code == 0) {
                    this.$message.success(res.msg)
                    setTimeout(() => {
                        this.getList()
                        this.dialog.balance = false
                    }, 800)
                } else {
                    this.$message.error(res.msg)
                }
            },
            handleSuccess() {
                this.getList()
            },
        }
    }
</script>
