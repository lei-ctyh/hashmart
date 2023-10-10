<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px"
				 label-position="left">
			<el-form-item label="物流信息">
				<el-timeline>
					<el-timeline-item
						v-for="(activity, index) in activities"
						:key="index"
						:timestamp="activity.timestamp">
						{{activity.content}}
					</el-timeline-item>
				</el-timeline>			</el-form-item>

		</el-form>
		<template #footer>
			<el-button @click="visible=false">取 消</el-button>
		</template>
	</el-dialog>
</template>

<script>
export default {
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "show",
			titleMap: {
				show: '查看物流信息'
			},
			visible: false,
			activities: []
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(mode = 'add') {
			this.mode = mode;
			this.visible = true;
			return this
		},
		// 表单注入数据
		setData(data) {
			this.activities = data.express.info
		},
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

