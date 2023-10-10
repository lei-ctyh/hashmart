<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-tag>单号：{{ express_no }}</el-tag>
		<el-timeline :reverse="reverse" style="margin-top: 20px;">
			<el-timeline-item
				v-for="(item, index) in express"
				:key="index"
				:timestamp="item.time">
				{{item.status}}
			</el-timeline-item>
		</el-timeline>
	</el-dialog>
</template>

<script>
export default {
	name: 'integralOrderExpressDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "expressDetail",
			titleMap: {
				expressDetail: '物流详情',
			},
			reverse: true,
			visible: false,
			express: [],
			express_no: ''
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(e) {
			this.visible = true;
			let detail = JSON.parse(e.express_detail)
			if (detail) {
				this.express_no = detail.result.number
				this.express = detail.result.list
			}
			
			return this
		},
	}
}
</script>
