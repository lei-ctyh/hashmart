<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="900" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="110px" label-position="left">
			<el-form-item label="位置" prop="type">
				<el-radio :label="1"  v-model="form.type">首页</el-radio>
				<el-radio :label="2"  v-model="form.type">哈希币商城</el-radio>
			</el-form-item>
			<el-form-item>
        <template #label>
          <span style="color:#f56c6c;margin-right: 4px;">*</span> 选择商品
        </template>
				<el-button type="primary" icon="el-icon-plus" @click="selectBlindbox" size="small" v-if="form.type == 1">选择盲盒</el-button>
				<el-button type="primary" icon="el-icon-plus" @click="selectGoods" size="small" v-if="form.type == 2">选择商品</el-button>
				<el-table
					v-if="form.type == 1"
					:data="selectedBlindbox"
					style="width: 100%">
					<el-table-column
						prop="id"
						label="ID">
					</el-table-column>
					<el-table-column
						prop="name"
						label="盲盒名称">
					</el-table-column>
					<el-table-column
						label="操作">
						<template #default="scope">
							<el-button @click="handleBoxDel(scope.row)" type="text" size="small">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
				<el-table
					:data="selectedGoods"
					v-if="form.type == 2"
					style="width: 100%">
					<el-table-column
						prop="id"
						label="ID">
					</el-table-column>
					<el-table-column
						prop="name"
						label="商品名">
					</el-table-column>
					<el-table-column
						label="操作">
						<template #default="scope">
							<el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-form-item>
			<el-form-item label="轮播描述" prop="title">
				<el-input v-model="form.title" placeholder="轮播描述" clearable></el-input>
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
						建议尺寸：690*294
					</li>
				</ul>
			</el-form-item>
			<el-form-item label="状态" prop="status">
				<el-radio :label="1"  v-model="form.status">正常</el-radio>
				<el-radio :label="2"  v-model="form.status">禁用</el-radio>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
			<el-button v-if="mode!='show'" type="primary" :loading="isSaveing" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>

	<el-dialog
		title="商品选择"
		v-model="dialogVisible"
		destroy-on-close
		width="50%">
		<Goods :selectedgoods="selectedGoods" @handleSelected="handleSelected" :goods-type="1"></Goods>
	</el-dialog>

	<el-dialog
		title="盲盒选择"
		v-model="blindboxVisible"
		destroy-on-close
		width="50%">
		<Blindbox :selectedBlindbox="selectedBlindbox" @handleSelected="handleBoxSelected"></Blindbox>
	</el-dialog>

	<el-dialog title="选择资源" v-model="resourceVisible" :width="1200" destroy-on-close>
		<Attachment :select-num="1" @selectedFiles="selectedFiles"></Attachment>
	</el-dialog>
</template>

<script>

	import Attachment from '@/components/attachment';
	import Goods from '@/components/goods';
	import Blindbox from '@/components/blindbox';

	export default {
		emits: ['success', 'closed'],
		components: {
			Attachment,
			Goods,
			Blindbox
		},
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '添加轮播配置',
					edit: '编辑轮播配置'
				},
				dialogVisible: false,
				blindboxVisible: false,
				visible: false,
				isSaveing: false,
				//表单数据
				form: {
					type: 1,
					status:1
				},
				options: [],
				plusIcon: 'el-icon-plus',
				deleteIcon: 'el-icon-delete',
				//验证规则
				rules: {
					type: [
						{required: true, message: '请选择位置',  trigger: 'blur'}
					],
					title: [
						{required: true, message: '请填写轮播描述',  trigger: 'blur'}
					],
					pic: [
						{required: true, message: '请选择图片',  trigger: 'blur'}
					]
				},
				resourceVisible: false,
				selectedGoods: [],
				selectedBlindbox: []
			}
		},
		mounted() {
			
		},
		methods: {
			// 显示
			open(e) {
				this.mode = e
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
                res = await this.$API.sliderSetting.add.post(this.form)
            } else {
                res = await this.$API.sliderSetting.edit.post(this.form)
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
			// 选择商品
			selectGoods() {
				this.dialogVisible = true
			},
			// 选择盲盒
			selectBlindbox() {
				this.blindboxVisible = true
			},
			// 删除选择的商品
			handleDel() {
				this.selectedGoods = []

				this.form.goods_id = 0
        this.form.goods_name = ''
        this.form.goods_type = 0
			},
			// 删除盲盒
			handleBoxDel() {
				this.selectedBlindbox = []

				this.form.blindbox_id = 0
				this.form.blindbox_name = ''
			},
			// 表单注入数据
			setData(data) {
				this.form.type = data.type
				this.form.id = data.id
				this.form.title = data.title
				this.form.goods_id = data.goods_id
				this.form.goods_name = data.goods_name
				this.form.goods_type = data.goods_type
				this.form.pic = data.pic
				this.form.status = data.status
				this.form.blindbox_id = data.blindbox_id
				this.form.blindbox_name = data.blindbox_name

				if (data.type == 2) {
					this.selectedGoods = [{
						id: data.goods_id,
						name: data.goods_name,
					}];
				} else {
					this.selectedBlindbox = [{
						id: data.blindbox_id,
						name: data.blindbox_name,
					}];
				}
				
			},
			// 选择了商品
			handleSelected(selectedGoods) {
				this.selectedGoods = selectedGoods
				this.form.goods_id = this.selectedGoods[0].id
				this.form.goods_type = 1
        this.form.goods_name = this.selectedGoods[0].name

				this.dialogVisible = false
			},
			// 选择盲盒
			handleBoxSelected(selectedBlindbox) {
				this.selectedBlindbox = selectedBlindbox
				this.form.blindbox_id = this.selectedBlindbox[0].id
				this.form.blindbox_name = this.selectedBlindbox[0].name
        this.form.other = this.selectedBlindbox[0].price
        this.form.goods_type = 2

				this.blindboxVisible = false
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
