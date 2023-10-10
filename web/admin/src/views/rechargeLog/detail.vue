<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="1000" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-empty description="暂无" v-if="third_return == ''"></el-empty>
		<el-descriptions title="" direction="vertical" :column="4" border v-else>
			<el-descriptions-item :label="key" v-for="item,key in third_return" :key="key">{{ item }}</el-descriptions-item>
		</el-descriptions>
	</el-dialog>
</template>

<script>
export default {
	name: 'rechargeLogDetail',
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "detail",
			titleMap: {
				detail: '三方信息',
			},
			visible: false,
			//表单数据
			third_return: {}
		}
	},
	mounted() {
	},
	methods: {
		//显示
		open(e) {
			this.third_return = e.third_return ? JSON.parse(e.third_return) : ''
			this.visible = true;
			return this
		},
	}
}
</script>
