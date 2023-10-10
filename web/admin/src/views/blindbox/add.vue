<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="900" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode=='show'" ref="dialogForm" label-width="110px" label-position="left">
			<el-form-item label="选择商品" prop="goods_name">
				<el-button type="primary" icon="el-icon-plus" @click="selectGoods" size="small">选择商品</el-button>
				<el-table
					:data="selectedGoods"
					style="width: 100%">
					<el-table-column
						prop="id"
						label="ID"
						width="70px">
					</el-table-column>
					<el-table-column
            label="商品图"
						width="60px">
						<template #default="scope">
							<el-image :src="scope.row.image.split(',')[0]" style="width: 36px;height: 36px;"></el-image>
						</template>
					</el-table-column>
					<el-table-column
						prop="name"
						:show-overflow-tooltip="true"
						label="商品名">
					</el-table-column>
					<el-table-column
						prop="price"
						label="价格">
					</el-table-column>
					<el-table-column
						prop="recovery_price"
						label="可兑换哈希币">
					</el-table-column>
					<!--<el-table-column
						label="库存">
						<template #default="scope">
							<el-input v-model="scope.row.stock" type="number"></el-input>
						</template>
					</el-table-column>-->
					<el-table-column
						label="操作">
						<template #default="scope">
							<el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-form-item>
			<el-form-item label="选择商品标签" prop="tag_id">
				<el-select v-model="form.tag_id" placeholder="请选择" style="width: 300px;">
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<template #label>
					中奖概率(%)
					<el-tooltip class="item" effect="dark" placement="top" content="如：5%，填5">
						<el-icon style="margin-top: 10px;margin-left: 5px;"><el-icon-QuestionFilled /></el-icon>
					</el-tooltip>
				</template>
				<template #default>
					<el-input v-model="form.probability" style="width: 300px;" @blur="probabilityChange"></el-input>
					<span style="margin-left:10px;font-size: 12px;color:#F56C6C"> （当前概率最大可增加：{{ maxProbability }}）</span>
				</template>
			</el-form-item>
			<el-form-item label="中奖数字范围">
				{{  form.lottery_min_no }} ~ {{ form.lottery_max_no }}
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
		<Goods :selectedgoods="selectedGoods" @handleSelected="handleSelected" :goods-type="2"></Goods>
	</el-dialog>
</template>

<script>
	import Goods from '@/components/goods';
	export default {
		emits: ['success', 'closed'],
		components: {
			Goods
		},
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增盲盒商品',
					edit: '编辑盲盒商品',
					show: '查看'
				},
				dialogVisible: false,
				visible: false,
				isSaveing: false,
				maxNum: 1048575,
				//表单数据
				form: {
					id:"",
					blindbox_id: "",
					tag_id: "",
					goods_id: "",
					goods_name: "",
					goods_image: "",
					price: "",
					recovery_price: "",
					stock: 0,
          lottery_min_no: 0,
					lottery_max_no: 0,
					probability: 0.00
				},
				options: [],
				plusIcon: 'el-icon-plus',
				deleteIcon: 'el-icon-delete',
				//验证规则
				rules: {
					goods_name: [
						{required: true, message: '请选择商品',  trigger: 'blur'}
					],
					tag_id: [
						{required: true, message: '请选择标签',  trigger: 'blur'}
					]
				},
				selectedGoods: [],
        maxProbability: ''
			}
		},
		mounted() {
			this.getBlindboxTag()
		},
		methods: {
			// 显示
			open(mode='add', id) {
				this.mode = mode;
				this.visible = true;
				this.form.blindbox_id = id
				if (mode == 'add') {
					this.getMaxPercent(id, 0)
				}

				return this
			},
			// 表单提交方法
			submit() {
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaveing = true;
						var res;
            if (this.mode == 'add') {
                res = await this.$API.blindboxDetail.add.post(this.form)
            } else {
                res = await this.$API.blindboxDetail.edit.post(this.form)
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
			// 概率修改
			probabilityChange() {
				this.getLotteryNoRange(this.form.blindbox_id, this.form.probability, this.form.id)
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
        this.form.blindbox_id = data.blindbox_id
				this.form.tag_id = data.tag_id
				this.form.goods_id = data.goods_id
				this.form.goods_name = data.goods_name
        this.form.goods_image = data.goods_image
				this.form.price = data.price
				this.form.recovery_price = data.recovery_price
				this.form.stock = data.stock
				this.form.lottery_min_no = data.lottery_min_no
				this.form.lottery_max_no = data.lottery_max_no
				this.form.probability = data.probability

				this.selectedGoods = [{
					id: data.goods_id,
					image: data.goods_image,
					name: data.goods_name,
					price: data.price,
					recovery_price: data.recovery_price,
					stock: data.stock
				}];

				this.getMaxPercent(data.blindbox_id, data.probability)
			},
			// 选择商品
			selectGoods() {
				this.dialogVisible = true
			},
			// 删除选择的商品
			handleDel() {
				this.selectedGoods = []

				this.form.goods_id = 0
				this.form.goods_name = ''
        this.form.goods_image = ''
				this.form.price = 0
				this.form.stock = -1
			},
			// 获取盲盒标签
			async getBlindboxTag() {
				let res = await this.$API.blindboxTag.list.get({limit: 9999999, page: 1, status: 1})
				this.options = res.data.rows
			},
			// 获取可设置的最大概率
			async getMaxPercent(id, percent) {
				let res = await this.$API.blindboxDetail.getMaxPercent.get({blindbox_id: id, percent: percent})
				if (res.code == 0) {
					this.maxProbability = res.data.percent
				} else {
					this.$message.success(res.msg)
				}
			},
			// 单选
			handleSelected(selectedGoods) {

				this.selectedGoods = selectedGoods

				this.form.goods_id = selectedGoods[0].id
				this.form.goods_image = selectedGoods[0].image
				this.form.goods_name = selectedGoods[0].name
				this.form.price = selectedGoods[0].price
				this.form.recovery_price = selectedGoods[0].recovery_price
				this.form.stock = selectedGoods[0].stock

				this.dialogVisible = false
			},
			// 获取数字范围
			async getLotteryNoRange(id, percent, detailId) {
				if (!percent) {
					this.$message.error('请输入正确的概率')
					return
				}
				let res = await this.$API.blindboxDetail.getLotteryNumRange.get({blindbox_id: id, percent: percent, detail_id: detailId})
				if (res.code == 0) {
					this.form.lottery_min_no = res.data.lottery_min_no
					this.form.lottery_max_no = res.data.lottery_max_no
				} else {
					this.$message.error(res.msg)
				}
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
