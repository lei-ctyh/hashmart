<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="800" destroy-on-close @closed="$emit('closed')" :close-on-click-modal="false">
		<el-descriptions>
            <el-descriptions-item label="订单号:">{{  info.order_no }}</el-descriptions-item>
            <el-descriptions-item label="支付订单号:">{{  info.pay_no }}</el-descriptions-item>
            <el-descriptions-item label="支付金额:">
                <el-tag size="small" type="danger">{{ info.amount }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="支付时间:">{{ info.pay_time }}</el-descriptions-item>
            <el-descriptions-item label="支付方式:">{{ info.pay_way_txt }}</el-descriptions-item>
            <el-descriptions-item label="支付状态:">
                <el-tag size="small">{{ info.status_txt }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="三方单号:">{{ info.third_no }}</el-descriptions-item>
        </el-descriptions>
	</el-dialog>
</template>

<script>
export default {
	emits: ['success', 'closed'],
	data() {
		return {
			mode: "show",
			titleMap: {
				show: '查看订单支付信息'
			},
			visible: false,
			info: {}
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
			this.getInfo(data.express_order_id)
		},
        async getInfo(id) {
            let res = await this.$API.userBoxDeliver.expressOrder.get({id: id})
            this.info = res.data
        }
	}
}
</script>


