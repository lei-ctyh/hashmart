<template>
	<el-tabs v-model="activeName" class="demo-tabs">
		<el-tab-pane label="基础信息" name="1">
			<el-form :model="form" label-width="100px" :rules="rules" ref="ruleForm">
				<el-form-item label="商品类型" prop="goods_type" class="goods-type">
					<ul class="goods-type" style="height: 100%">
						<li :class="{'goods-type-active' : form.goods_type == 1}" @click="checkType(1)">
							<div style="margin-top: 5%">普通商品</div>
							<div style="color:#999;font-size: 12px;height: 24px;">
								<el-icon style="font-size: 18px;color:#F56C6C;margin-top: 5px;"><el-icon-Goods /></el-icon>
							</div>
							<span class="icon"></span>
						</li>
						<li :class="{'goods-type-active' : form.goods_type == 2}" @click="checkType(2)" style="margin-left: 10px">
							<div style="margin-top: 5%">盲盒商品</div>
							<div style="color:#999;font-size: 12px;height: 24px;">
								<el-icon style="font-size: 18px;color:#67C23A;margin-top: 5px;"><el-icon-Box /></el-icon>
							</div>
							<span class="icon"></span>
						</li>
					</ul>
				</el-form-item>
				<el-form-item label="所属分类" prop="cateId">
					<el-cascader
						v-model="cateId"
						:options="cate"
						:show-all-levels="false"
						:props="treeProps"
						style="width:500px"
						clearable
					></el-cascader>
				</el-form-item>
				<el-form-item label="商品名称" prop="name">
					<el-input v-model="form.name" style="width:500px" type="textarea" :rows="3"/>
				</el-form-item>
				<el-form-item label="商品副标题">
					<el-input v-model="form.sub_title" style="width:500px" type="textarea" :rows="3"/>
				</el-form-item>
				<el-form-item label="商品主图" prop="image" style="margin-bottom:5px">
					<ul class="img-list">
						<li v-for="(item, index) in goodsImages" :key="index">
							<img :src="item" alt="图片" style="height:58px;width:58px">
							<div class="img-tools">
								<el-icon style="font-size: 14px;color: #fff" @click="removeIcon(index)"><component :is="deleteIcon" /></el-icon>
							</div>
						</li>
						<li>
							<div class="addImg" @click="showImage">
								<el-icon><component :is="plusIcon" /></el-icon>
							</div>
						</li>
					</ul>
				</el-form-item>
				<p style="margin-left:100px;color:#909399">* 建议尺寸：336*336, 最多选择 8 张</p>
				<el-form-item label="状态" style="margin-top:10px">
					<el-radio v-model="form.status" label="1">上架</el-radio>
					<el-radio v-model="form.status" label="2">下架</el-radio>
				</el-form-item>
				<el-form-item label="运费" v-if="form.goods_type == 2">
					<el-input v-model="form.delivery_fee" style="width:500px" type="number"/>
				</el-form-item>
				<el-form-item label="排序">
					<el-input v-model="form.sort" style="width:500px" type="number" placeholder="值越大越靠前"/>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="nextStep">下一步</el-button>
					<el-button type="primary" @click="saveGoods" v-if="mode == 'edit'">保存</el-button>
				</el-form-item>
			</el-form>

		</el-tab-pane>
		<el-tab-pane label="规格库存" name="2">
			<el-form :model="form" label-width="100px" :rules="rules" ref="ruleForm">
				<el-form-item label="商品规格" style="margin-top:10px" v-if="form.goods_type == 1">
					<el-radio v-model="form.type" label="1">单规格</el-radio>
					<el-radio v-model="form.type" label="2">多规格</el-radio>
				</el-form-item>
				<div v-if="form.type == 1">
					<el-form-item label="展示价格">
						<el-input v-model="form.price" style="width:500px" type="number"/>
					</el-form-item>
					<el-form-item label="可兑换哈希币">
						<el-input v-model="form.recovery_price" style="width:370px" type="number"/> 
						<el-input v-model="recover_price" style="width:100px;margin-left: 30px;" type="number"/> 元 
						<el-tooltip class="item" effect="dark" content="(您可以在此处填写金额，系统自动帮您生成哈希币金额)" placement="top-start">
							<el-icon><component :is="QuestionFilled"/></el-icon>
						</el-tooltip>
					</el-form-item>
					<el-form-item label="成本价">
						<el-input v-model="form.cost_price" style="width:500px" type="number"/>
					</el-form-item>
					<el-form-item label="售价(哈希币)">
						<el-input v-model="form.integral_price" style="width:370px" type="number"/>
						<el-input v-model="integral_price" style="width:100px;margin-left: 30px;" type="number"/> 元 
						<el-tooltip class="item" effect="dark" content="(您可以在此处填写金额，系统自动帮您生成哈希币金额)" placement="top-start">
							<el-icon><component :is="QuestionFilled"/></el-icon>
						</el-tooltip>
					</el-form-item>
					<el-form-item label="库存" v-if="form.goods_type == 1">
						<el-input v-model="form.stock" style="width:500px" type="number"/> <span style="font-size: 12px;margin-left: 10px;">(-1 表示无限库存)</span>
					</el-form-item>
				</div>
				<div v-if="form.type == 2" style="margin-bottom:20px">
					<div style="float: right;margin-bottom: 10px;width: 1200px">
						<el-button type="primary" icon="el-icon-plus" size="small" @click="addRule">新的规格</el-button>
						<el-button type="primary" icon="el-icon-cpu" size="small" @click="makeRule">生成属性</el-button>
					</div>
					<el-table
						:data="preItem"
						border
						style="width: 1200px">
						<el-table-column
							label="规格名"
							width="200">
							<template #default="scope">
								<el-input placeholder="规格名" style="width: 150px" v-model="scope.row.title"></el-input>
								<el-icon @click="delTitle(scope.$index)" class="del-btn"><el-icon-delete /></el-icon>
							</template>
						</el-table-column>
						<el-table-column
							label="规格值">
							<template #default="scope">
								<div style="display:flex;flex-flow: row wrap;">
									<div style="width: 180px;float: left;margin-top: 5px;" v-for="(vo,index) in scope.row.item" :key="index">
										<el-input placeholder="规格值" style="width: 150px" v-model="scope.row.item[index]"></el-input>
										<el-icon @click="delItem(scope.$index, index)" class="del-btn"><el-icon-delete /></el-icon>
									</div>
									<div class="item-tool" @click="addNewItem(scope.$index)">
										<el-icon><el-icon-plus /></el-icon> 添加
									</div>
								</div>
							</template>
						</el-table-column>
					</el-table>
				</div>

				<div v-if="final.length > 0 && form.type == 2" style="margin-bottom:20px">
					<el-form-item label="批量设置" style="margin-top: 10px">
						<el-button-group>
							<el-button type="primary" size="small" @click="batchDialogVisible = true">图片</el-button>
							<el-button type="primary" size="small" @click="setBatch('price', '展示价格')">展示价格</el-button>
							<el-button type="primary" size="small" @click="setBatch('recovery_price', '可兑换哈希币')">可兑换哈希币</el-button>
							<el-button type="primary" size="small" @click="setBatch('cost_price', '成本价')">成本价</el-button>
							<el-button type="primary" size="small" @click="setBatch('integral_price', '售价(哈希币)')">售价(哈希币)</el-button>
							<el-button type="primary" size="small" @click="setBatch('stock', '库存')">库存</el-button>
						</el-button-group>
					</el-form-item>
					<el-table
						:data="final"
						border
						style="width: 1200px">
						<el-table-column v-for="item,index in tableHead" :label="item.label" width="150" :key="index">
							<template #default="scope">
								{{ scope.row.sku[item.property] }}
							</template>
						</el-table-column>
						<el-table-column
							label="图片"
							width="100">
							<template #default="scope">
								<div class="up-item-img" v-if="scope.row.image == ''" @click="setOneImg(scope.$index)">
									<el-icon><el-icon-plus /></el-icon>
								</div>
								<img style="width: 40px; height: 40px" :src="scope.row.image" v-if="scope.row.image">
								<div class="img-tools" v-if="scope.row.image" @click="rmImg(scope.$index)">
									<el-icon style="color: #fff;"><el-icon-delete /></el-icon>
								</div>
							</template>
						</el-table-column>
						<el-table-column
							label="展示价格"
							width="150">
							<template #default="scope">
								<el-input v-model="scope.row.price" type="number"></el-input>
							</template>
						</el-table-column>
						<el-table-column
							label="可兑换哈希币"
							width="150">
							<template #default="scope">
								<el-input v-model="scope.row.recovery_price" type="number"></el-input>
							</template>
						</el-table-column>
						<el-table-column
							label="成本价"
							width="150">
							<template #default="scope">
								<el-input v-model="scope.row.cost_price" type="number"></el-input>
							</template>
						</el-table-column>
						<el-table-column
							label="售价(哈希币)"
							width="150">
							<template #default="scope">
								<el-input v-model="scope.row.integral_price" type="number"></el-input>
							</template>
						</el-table-column>
						<el-table-column
							label="库存"
							width="150">
							<template #default="scope">
								<el-input v-model="scope.row.stock" type="number"></el-input>
							</template>
						</el-table-column>
						<el-table-column
							label="操作"
							fixed="right"
							width="150">
							<template #default="scope">
								<el-button @click="handleDel(scope.$index)" type="text" size="small">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
				</div>
				<el-form-item>
					<el-button @click="preStep">上一步</el-button>
					<el-button type="primary" @click="ruleNext">下一步</el-button>
					<el-button type="primary" @click="saveGoods" v-if="mode == 'edit'">保存</el-button>
				</el-form-item>
			</el-form>
		</el-tab-pane>
		<el-tab-pane label="商品详情" name="3">
			<sc-editor v-model="form.content" :height="600"></sc-editor>
			<el-form :model="form" label-width="100px" style="margin-top:20px">
				<el-form-item>
					<el-button @click="preStep">上一步</el-button>
					<el-button type="primary" @click="saveGoods">保存</el-button>
				</el-form-item>
			</el-form>
		</el-tab-pane>
	</el-tabs>

	<el-dialog title="选择资源" v-model="imgResourceVisible" :width="1200" destroy-on-close>
		<Attachment :select-num="8" @selectedFiles="selectedFiles"></Attachment>
	</el-dialog>

	<el-dialog title="选择资源" v-model="batchDialogVisible" :width="1200" destroy-on-close>
		<Attachment :select-num="1" @selectedFiles="selectedBatchImg"></Attachment>
	</el-dialog>

	<el-dialog
		:title="`设置${batchTitle}`"
		v-model="batchVisible"
		width="600px">
		<el-form ref="form" :model="batchForm" label-width="120px">
			<el-form-item :label="batchTitle">
				<el-input v-model="batchForm.field" type="number" style="width: 200px;"></el-input>
				<div v-if="setField == 'recovery_price' || setField == 'integral_price'"><el-input v-model="set_integral_price" style="width:90px;margin-left: 30px;" type="number" /> 元</div> 
				<el-tooltip class="item" effect="dark" content="(您可以在此处填写金额，系统自动帮您生成哈希币金额)" placement="top-start" v-if="setField == 'recovery_price' || setField == 'integral_price'">
					<el-icon><component :is="QuestionFilled"/></el-icon>
				</el-tooltip>
			</el-form-item>
		</el-form>
		<template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="batchFormSubmit">确 定</el-button>
      </span>
		</template>
	</el-dialog>
</template>

<script>

import Attachment from '@/components/attachment'
import { defineAsyncComponent } from 'vue';
const scEditor = defineAsyncComponent(() => import('@/components/scEditor'));

export default {
	name: "goodsAdd",
	components: {
		Attachment,
		scEditor
	},
	data() {
		return {
			QuestionFilled: 'el-icon-QuestionFilled',
			activeName: "1",
			imgResourceVisible: false,
			dialogVisible: false,
			cateId: [],
			goodsImages: [],
			plusIcon: 'el-icon-plus',
			deleteIcon: 'el-icon-delete',
			ruleItem: {
				'title': '',
				'item': ['']
			},
			form: {
				goods_type: 1,
				name: '',
				cate_id: '',
				sub_title: '',
				image: '',
				status: '1',
				delivery_fee: 0,
				sort: 0,
				type: '1',
				price: '0.00',
				recovery_price: '0.00',
				cost_price: '0.00',
				integral_price: '0.00',
				stock: -1
			},
			cate: [],
			treeProps: {
				value: 'id',
				label: 'name',
				children: 'children',
				checkStrictly: true
			},
			rules: {
				goods_type: [
					{ required: true, message: '请选择商品类型', trigger: 'blur' }
				],
				name: [
					{ required: true, message: '请输入商品名', trigger: 'blur' }
				],
				cateId: [
					{ required: true, message: '请选择所属分类', trigger: 'blur' }
				],
				image: [
					{ required: true, message: '请选择商品主图', trigger: 'blur' }
				]
			},
			preItem: [
				{
					'title': '',
					'item': ['']
				}
			],
			final: [],
			tableHead: [],
			ruleTableItem: {
				sku: [],
				image: '',
				recovery_price: '0.00',
				cost_price: '0.00',
				integral_price: '0.00',
				stock: 0
			},
			batchDialogVisible: false,
			batchTitle: '',
			batchVisible: false,
			setField: '',
			batchForm: {
				field: ''
			},
			selectImgIndex: -1,
			mode: 'add',
			recover_price: '0.00',
			integral_price: '0.00',
			set_integral_price: '0.00',
			ratio: 0
		}
	},
	mounted() {
		this.getGoodsCate()
		this.getBaseInfo()
	},
	watch: {
		'recover_price': {
			handler(newVal) {
				this.form.recovery_price = (newVal * this.ratio).toFixed(2) 
			},
			deep: true,
			immediate: true
		},
		'integral_price': {
			handler(newVal) {
				this.form.integral_price = (newVal * this.ratio).toFixed(2) 
			},
			deep: true,
			immediate: true
		},
		'set_integral_price': {
			handler(newVal) {
				this.batchForm.field = (newVal * this.ratio).toFixed(2) 
			},
			deep: true,
			immediate: true
		}
	},
	methods: {
		// 获取基础数据
		async getBaseInfo() {
			let res = await this.$API.system.base.get()
      this.ratio = res.data.change_ratio
      
      this.recover_price = (this.form.recovery_price / this.ratio).toFixed(2)
      this.integral_price = (this.form.integral_price / this.ratio).toFixed(2)

		},
		// 选择商品类型
		checkType(type) {
			this.form.goods_type = type
		},
		// 获取商品分类
		async getGoodsCate() {
			let res = await this.$API.goodsCate.list.get()
			this.cate = res.data
		},
		// 删除商品图
		removeIcon(index) {
			this.goodsImages.splice(index, 1)
		},
		// 选择的资源
		selectedFiles(file) {
			this.goodsImages = []
			file.forEach(item => {
				this.goodsImages.push(item.url)
			})
			this.imgResourceVisible = false
		},
		// 展示资源选择器
		showImage() {
			this.imgResourceVisible = true
		},
		// 下一步
		nextStep() {
			this.activeName = parseInt(this.activeName) + 1 + ''
		},
		// 上一步
		preStep() {
			this.activeName = parseInt(this.activeName) - 1 + ''
		},
		// 添加规格
		addRule() {
			let item = JSON.parse(JSON.stringify(this.ruleItem))
			this.preItem.push(item)
		},
		// 生成属性
		makeRule() {
			let titleMap = [];
			let preList = [];
			this.preItem.forEach((item, index) => {
				let data = item.item.filter(function (s) {
					return s && s.trim();
				})
				if (item.title != '' && data.length > 0) {
					titleMap.push({label: item.title, property: index})
					preList.push(item.item)
				}
			})

			let descartes = this.calcDescartes(preList)
			this.final = [];
			this.ruleTableItem = {
				sku: [],
				image: '',
				price: '0.00',
				recovery_price: '0.00',
				cost_price: '0.00',
				integral_price: '0.00',
				stock: 0
			};
			descartes.forEach(item => {
				if (item instanceof Array) {
					let len = item.length;
					item.forEach((vo, index) => {
						if (!vo) {
							item.splice(index, 1)
						}
					})

					if (len != item.length) {
						return;
					}
				} else if (item == '') {
					return;
				}

				this.ruleTableItem.sku = (item instanceof Array) ? item : [item]
				let tableIem = JSON.parse(JSON.stringify(this.ruleTableItem))
				this.final.push(tableIem)
			})
			this.tableHead = titleMap
		},
		// 删除规格名
		delTitle(index) {
			this.preItem.splice(index, 1)
		},
		// 删除规格
		delItem(tableIndex, index) {
			this.preItem[tableIndex].item.splice(index, 1)
		},
		// 添加规格值
		addNewItem(index) {
			this.preItem[index].item.push('')
		},
		// 计算笛卡尔积
		calcDescartes(array) {
			if (array.length < 2) return array[0] || [];

			return array.reduce((total, currentValue) => {
				let res = [];

				total.forEach(t => {
					currentValue.forEach(cv => {
						if (t instanceof Array) 	// 或者使用 Array.isArray(t)
							res.push([...t, cv]);
						else
							res.push([t, cv]);
					})
				})
				return res;
			})
		},
		// 批量设置
		setBatch(field, title) {
			this.batchTitle = title
			this.batchVisible = true
			this.setField = field
		},
		// 批量设置
		batchFormSubmit() {
			if (this.batchForm.field == '') {
				this.$message.error('请输入正确的值')
				return false
			}

			this.final.map(item => {
				if (this.setField != 'stock' && this.setField != 'spu') {
					item[this.setField] = Number(this.batchForm.field).toFixed(2)
				} else {
					item[this.setField] = this.batchForm.field
				}
			})

			this.batchForm.field = ''
			this.batchVisible = false
			this.set_integral_price = '0.00'
		},
		// 批量设置图片
		selectedBatchImg(img) {

			if (parseInt(this.selectImgIndex) >= 0) {
				this.final[parseInt(this.selectImgIndex)].image = img[0].url
				this.selectImgIndex = -1
			} else {
				this.final.map(item => {
					item.image = img[0].url
				})
			}

			this.batchDialogVisible = false
		},
		// 选择单个图片
		setOneImg(index) {
			this.batchDialogVisible = true
			this.selectImgIndex = index
		},
		// 删过单个图片
		rmImg(index) {
			this.final[parseInt(index)].image = ''
		},
		// 选择规则里面的下一步
		ruleNext() {
			if (this.form.type == 2 && this.final.length == 0) {
				this.$message.error('请生成多规格属性！')
				return false;
			}

			this.activeName = (parseInt(this.activeName) + 1) + ''
		},
		// 保存
		async saveGoods() {
			if (this.cateId instanceof Array) {
				this.form.cate_id = this.cateId[this.cateId.length - 1]
			} else {
				this.form.cate_id = this.cateId
			}

			this.form.preItem = this.preItem
			this.form.final = this.final
			this.form.image = this.goodsImages.join(',')

			let res;
			if (this.mode == 'edit') {
				res = await this.$API.goods.edit.post(this.form)
			} else {
				res = await this.$API.goods.add.post(this.form)
			}

			if (res.code == 0) {
				this.$message.success('操作成功')
				this.$emit('save')
			} else {
				this.$message.error(res.msg)
			}
		},
		// 删除属性
		handleDel(index) {
			this.final.splice(index, 1)
		},
		// 富文本输入
		ueditorContent(content) {
			this.form.content = content
		},
		setMode(mode) {
			this.mode = mode
			return this
		},
		// 设置数据
		setData(row) {
			this.form.id = row.id
			this.form.goods_type = row.goods_type
			this.form.name = row.name
			this.sub_title = row.sub_title
			this.form.image = row.image
			this.form.status = row.status + ''
			this.form.delivery_fee = row.delivery_fee
			this.form.sort = row.sort
			this.form.type = row.type + ''
			this.form.price = row.price
			this.form.recovery_price = row.recovery_price
			this.form.cost_price = row.cost_price
			this.form.integral_price = row.integral_price
			this.form.stock = row.stock
			this.form.content = row.content

			this.cateId = row.cate_id
			this.goodsImages = row.image.split(',')

			if (row.rule) {
				this.preItem = JSON.parse(row.rule.rule)
			}
			
			if (row.ruleExtend) {

				let titleMap = [];
				this.preItem.forEach((item, index) => {
					let data = item.item.filter(function (s) {
						return s && s.trim();
					})
					if (item.title != '' && data.length > 0) {
						titleMap.push({label: item.title, property: index})
					}
				})

				this.tableHead = titleMap
				row.ruleExtend.map(item => {
					if (!(item.sku instanceof Array)) {
						return item.sku = item.sku.split('※')
					}
				})
				this.final = row.ruleExtend
			}
		}
	},
}
</script>

<style scoped>
.goods-type li {
	float: left;
	display: block;
	list-style: none;
	padding: 0;
	width: 120px;
	height: 60px;
	background-color: rgb(255, 255, 255);
	border: 1px solid rgb(226, 226, 226);
	overflow: hidden;
	cursor: pointer;
	position: relative;
	text-align: center;
	line-height: 113px;
}
.goods-type-active {
	border: 1px solid rgb(17, 161, 253) !important;
}
.goods-type-active div:first-child {
	color: rgb(17, 161, 253);
}
.goods-type li div {
	height: 25px;
	text-align: center;
	line-height: 25px;
}
.goods-type-active .icon {
	height: 40px;
	width: 40px;
	display: block;
	background-image: url(/public/img/success.png);
	background-position: 76px 78px;
	position: relative;
	right: -86px;
	top: -31px;
}
.goods-type .el-form-item__content {
	margin-left: 0 !important;
}
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
.del-btn {
	color: #F56C6C;
	cursor: pointer;
	margin-left: 5px;
}
.item-tool {
	width: 50px;
	height: 30px;
	line-height: 30px;
	margin-top: 6px;
	cursor: pointer;
}
.up-item-img {
	width: 40px;
	height: 40px;
	border: 1px
	dashed #c2c2c2;
	text-align: center;
	line-height: 40px;
	cursor: pointer;
}
.cell .img-tools {
	position: absolute;
	width: 40px;
	height: 15px;
	line-height: 15px;
	text-align: center;
	top: 33px;
	background: rgba(0, 0, 0, 0.6);
	cursor: pointer;
}
</style>
