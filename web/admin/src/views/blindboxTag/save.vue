<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="标签名称" prop="name">
				<el-input v-model="form.name" clearable></el-input>
			</el-form-item>
			<el-form-item label="标签颜色" prop="color">
				<el-color-picker v-model="form.color"></el-color-picker>
			</el-form-item>
			<el-form-item label="排序" prop="sort">
				<el-input type="number" v-model="form.sort" clearable></el-input>
			</el-form-item>
			<el-form-item label="是否显示" prop="status">
				<el-radio :label="1"  v-model="form.status">显示</el-radio>
				<el-radio :label="2"  v-model="form.status">隐藏</el-radio>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button v-if="mode!='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
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
					add: '新增标签',
					edit: '编辑标签',
					show: '查看'
				},
				resourceVisible: false,
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					id:"",
					name: "",
					color: '',
					sort: 1,
                    status: 1
				},
				plusIcon: 'el-icon-plus',
				deleteIcon: 'el-icon-delete',
				//验证规则
				rules: {
                    name: [
						{required: true, message: '请输入分类名称',  trigger: 'blur'}
					],
					color: [
						{required: true, message: '请选择颜色',  trigger: 'blur'}
					],
					sort: [
						{required: true, message: '请输入排序',  trigger: 'blur'}
					],
					status: [
						{required: true, message: '请选择状态',  trigger: 'blur'}
					],
				}
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
                            res = await this.$API.blindboxTag.add.post(this.form)
                        } else {
                            res = await this.$API.blindboxTag.edit.post(this.form)
                        }
						this.isSaveing = false;
						if (res.code == 0) {
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
				this.form.id = data.id
                this.form.name = data.name
				this.form.sort = data.sort
				this.form.color = data.color
                this.form.status = data.status
			}
		}
	}
</script>

<style scoped>
	.img-list {
        height: 60px;
        padding-left: 0;
        margin-top: 0;
    }
    .img-list li:first-child {
        margin-left: 0;
    }
    .img-list li {
        width: 58px;
        height: 58px;
        float: left;
        margin-left: 5px;
        cursor: pointer;
        position: relative;
    }
    .addImg {
        height: 56px;
        width: 56px;
        line-height: 56px;
        text-align: center;
        border: 1px dashed rgb(221, 221, 221);
    }
    ul li {list-style: none}
    .image-check-dialog .el-dialog__header {display: none}
    .image-check-dialog .el-dialog__body {padding: 0;}
    .img-list .img-tools {
        position: absolute;
        width: 58px;
        height: 15px;
        line-height: 15px;
        text-align: center;
        top: 43px;
        background: rgba(0, 0, 0, 0.6);
        cursor: pointer;
    }
</style>
