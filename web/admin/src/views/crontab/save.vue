<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="1000" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" ref="dialogForm" label-width="100px" label-position="left">
            <el-form-item label="标题" prop="title">
                <el-input v-model="form.title" placeholder="计划任务标题" clearable></el-input>
            </el-form-item>
            <el-form-item label="执行脚本" prop="shell">
                <el-input type="textarea" v-model="form.shell" clearable :rows="3"></el-input>
            </el-form-item>
            <el-form-item prop="frequency">
                <template #label>
                    <div class="frequency-title">定时规则</div>
                </template>
                <el-col :span="4">
                    <el-select v-model="form.frequency.type">
                        <el-option label="每隔N秒" value="1"></el-option>
                        <el-option label="每隔N分钟" value="2"></el-option>
                        <el-option label="每隔N小时" value="3"></el-option>
                        <el-option label="每隔N天" value="4"></el-option>
                        <el-option label="每天" value="5"></el-option>
                        <el-option label="每星期" value="6"></el-option>
                        <el-option label="每月" value="7"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="4" style="margin-left: 10px" v-if="form.frequency.type == 7 || form.frequency.type == 4">
                    <el-input-number v-model="form.frequency.day" controls-position="right" :min="1" :max="31" suffix-icon="el-icon-date"></el-input-number>
                    <span class="suffix">日</span>
                </el-col>
                <el-col :span="4" style="margin-left: 3px" v-if="form.frequency.type == 6">
                    <el-select v-model="form.frequency.week" placeholder="星期">
                        <el-option label="周一" value="1"></el-option>
                        <el-option label="周二" value="2"></el-option>
                        <el-option label="周三" value="3"></el-option>
                        <el-option label="周四" value="4"></el-option>
                        <el-option label="周五" value="5"></el-option>
                        <el-option label="周六" value="6"></el-option>
                        <el-option label="周日" value="7"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="4" style="margin-left: 10px" v-if="form.frequency.type > 2 && form.frequency.type != 4">
                    <el-input-number v-model="form.frequency.hour" controls-position="right" :min="0" :max="23" suffix-icon="el-icon-date"></el-input-number>
                    <span class="suffix">时</span>
                </el-col>
                <el-col :span="4" style="margin-left: 10px" v-if="form.frequency.type == 2 || form.frequency.type > 4">
                    <el-input-number v-model="form.frequency.minute" controls-position="right" :min="0" :max="59" suffix-icon="el-icon-date"></el-input-number>
                    <span class="suffix">分</span>
                </el-col>
                <el-col :span="4" style="margin-left: 10px" v-if="form.frequency.type == 1 || form.frequency.type > 4">
                    <el-input-number v-model="form.frequency.second" controls-position="right" :min="0" :max="59" suffix-icon="el-icon-date"></el-input-number>
                    <span class="suffix">秒</span>
                </el-col>
            </el-form-item>
            <el-form-item label="排序">
                <el-input v-model="form.sort" placeholder="越大越前" clearable type="number"></el-input>
            </el-form-item>
            <el-form-item label="是否启用" prop="status">
                <el-switch v-model="form.status" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="visible=false" >取 消</el-button>
            <el-button type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
        </template>
	</el-dialog>
</template>

<script>
	export default {
		emits: ['success', 'closed'],
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增定时任务',
					edit: '编辑定时任务',
					show: '查看'
				},
                form: {
                    title: '',
                    type: 2,
                    shell: '',
                    frequency: {
                        type: "1",
                        day: "1",
                        week: "1",
                        hour: "1",
                        minute: "1",
                        second: "1"
                    },
                    status: 0,
                    sort: 0
                },
				visible: false,
				isSaveing: false
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
			// 表单提交方法
			submit() {
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
						var res;
                        if (this.mode == 'add') {
                            res = await this.$API.crontab.addTask.post(this.form)
                        } else {
                            res = await this.$API.crontab.editTask.post(this.form)
                        }
						this.isSaveing = false;
						if (res.code == 200) {
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success(res.msg)
						} else {
							this.$message.error(res.msg)
						}
					} else {
						return false;
					}
				})
			},
			// 表单注入数据
			setData(data) {
				console.log('设置数据', data)
			},
		}
	}
</script>

<style scoped>
.el-input-number {
    top: 15px
}
.suffix {
    position: relative;
    top: -17px;
    left: 98px;
}
.frequency-title {
    margin-top: 16px !important;
}
</style>
