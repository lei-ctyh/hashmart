<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="盲盒名称" prop="name">
				<el-input v-model="form.name"></el-input>
			</el-form-item>
			<el-form-item label="封面图" prop="pic">
				<ul class="img-list">
            <li v-if="form.pic">
                <img :src="form.pic" alt="封面图" style="height:58px;width:58px">
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
                建议尺寸：250*250
            </li>
        </ul>
			</el-form-item>
			<el-form-item label="单抽价格" prop="price">
				<el-input v-model="form.price" type="number" :min="0"></el-input>
			</el-form-item>
			<el-form-item label="描述">
				<el-input v-model="form.desc" rows="3" type="textarea"></el-input>
			</el-form-item>
			<el-form-item label="排序">
				<el-input v-model="form.sort" clearable type="number"></el-input>
			</el-form-item>
			<el-form-item label="是否热门">
				<el-radio :label="1"  v-model="form.hot_tag">是</el-radio>
				<el-radio :label="2"  v-model="form.hot_tag">否</el-radio>
			</el-form-item>
			<el-form-item label="是否推荐">
				<el-radio :label="1"  v-model="form.recommend_tag">是</el-radio>
				<el-radio :label="2"  v-model="form.recommend_tag">否</el-radio>
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
					add: '新增盲盒',
					edit: '编辑盲盒',
					show: '查看'
				},
				resourceVisible: false,
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					id:"",
					name: "",
					price: 0,
					pic: "",
					desc: "",
					sort: 1,
                    hot_tag: 2,
					recommend_tag: 2
				},
				plusIcon: 'el-icon-plus',
				deleteIcon: 'el-icon-delete',
				//验证规则
				rules: {
                    name: [
						{required: true, message: '请输入名称',  trigger: 'blur'}
					],
					price: [
						{required: true, message: '请输入单抽价格',  trigger: 'blur'}
					],
					pic: [
						{required: true, message: '请选择封面',  trigger: 'blur'}
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
                            res = await this.$API.blindbox.add.post(this.form)
                        } else {
                            res = await this.$API.blindbox.edit.post(this.form)
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
				this.form.pic = ''
			},
			// 资源选择器
			showImage() {
				this.resourceVisible = true
			},
			// 选择器返回的图片数据
			selectedFiles(file) {
				this.form.pic = file[0].url
				this.resourceVisible = false
			},
			// 表单注入数据
			setData(data) {
				this.form.id = data.id
                this.form.name = data.name
				this.form.price = data.price
				this.form.pic = data.pic
				this.form.desc = data.desc
				this.form.sort = data.sort
                this.form.hot_tag = data.hot_tag
				this.form.recommend_tag = data.recommend_tag
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
