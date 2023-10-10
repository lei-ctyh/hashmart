<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="上级分类">
				<el-input v-model="pname" disabled="true"></el-input>
			</el-form-item>
			<el-form-item label="分类名称" prop="name">
				<el-input v-model="form.name" clearable></el-input>
			</el-form-item>
			<el-form-item label="图标" prop="icon">
				<ul class="img-list">
                    <li v-if="form.icon">
                        <img :src="form.icon" alt="图片" style="height:58px;width:58px">
                        <div class="img-tools">
							<el-icon style="font-size: 14px;color: #fff" @click="removeIcon()"><component :is="deleteIcon" /></el-icon>
                        </div>
                    </li>
                    <li>
                        <div class="addImg" @click="showImage">
                            <el-icon><component :is="plusIcon" /></el-icon>
                        </div>
                    </li>
                    <li style="color: #999;font-size: 12px;width: 250px;line-height: 58px;margin-left: 30px">
                        建议尺寸：64*64
                    </li>
                </ul>
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

	<el-dialog title="选择资源" v-model="resourceVisible" :width="1200" destroy-on-close>
		<Attachment :select-num="1" @selectedFiles="selectedFiles"></Attachment>
	</el-dialog>
</template>

<script>

	import Attachment from '@/components/attachment';

	export default {
		emits: ['success', 'closed'],
		components: {
            Attachment
        },
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增分类',
					edit: '编辑分类',
					show: '查看'
				},
				resourceVisible: false,
				visible: false,
				isSaveing: false,
				pname: '顶级分类',
				//表单数据
				form: {
					id:"",
					pid: 0,
					name: "",
					icon: "",
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
					icon: [
						{required: true, message: '请输选择图标',  trigger: 'blur'}
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
                            res = await this.$API.goodsCate.add.post(this.form)
                        } else {
                            res = await this.$API.goodsCate.edit.post(this.form)
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
			// 移除图标
			removeIcon() {
				this.form.icon = ''
			},
			// 资源选择器
			showImage() {
				this.resourceVisible = true
			},
			// 选择器返回的图片数据
			selectedFiles(file) {
				this.form.icon = file[0].url
				this.resourceVisible = false
			},
			// 表单注入数据
			setData(data) {
				this.form.id = data.id
                this.form.pid = data.pid
                this.form.name = data.name
				this.form.icon = data.icon
				this.form.sort = data.sort
                this.form.status = data.status
			},
			setParentData(row) {
				this.form.pid = row.id
				this.pname = row.name
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
