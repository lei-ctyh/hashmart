<template>
	<el-card class="box-card" shadow="false">
		<template #header>
			<div class="card-header">
				<span>{{ title }}</span>
				<el-tag type="success">今日</el-tag>
			</div>
		</template>
		<el-divider style="margin: 2px"/>
		<div class="data-content">
			<div class="number" v-if="title==='销售额'">{{ all_order_amount }}</div>
			<div class="number" v-else-if="title==='盲盒订单量'">{{ blind_box_order_counts }}</div>
			<div class="number" v-else-if="title==='订单量'">{{ all_order_counts }}</div>
			<div class="number" v-else-if="title==='新增用户'">{{ users }}</div>

			<div class="grow-percent" v-if="title==='销售额'">
				今日环比：{{ change_amount }}元
				<el-icon style="color:#67C23A" v-if="change_amount < 0">
					<component :is="iconDown"/>
				</el-icon> &nbsp;
				<el-icon style="color:#F56C6C" v-else-if="change_amount > 0">
					<component :is="iconUp"/>
				</el-icon>
			</div>
			<div class="grow-percent" v-else-if="title==='盲盒订单量'">
				今日环比：{{ change_blind_box_order_counts }}
				<el-icon style="color:#67C23A" v-if="change_blind_box_order_counts < 0">
					<component :is="iconDown"/>
				</el-icon> &nbsp;
				<el-icon style="color:#F56C6C" v-else-if="change_blind_box_order_counts > 0">
					<component :is="iconUp"/>
				</el-icon>
			</div>
			<div class="grow-percent" v-else-if="title==='订单量'">
				今日环比：{{ change_all_order_counts }}
				<el-icon style="color:#67C23A" v-if="change_all_order_counts < 0">
					<component :is="iconDown"/>
				</el-icon> &nbsp;
				<el-icon style="color:#F56C6C" v-else-if="change_all_order_counts > 0">
					<component :is="iconUp"/>
				</el-icon>
			</div>
			<div class="grow-percent" v-else-if="title==='新增用户'">
				今日环比：{{ change_users }}
				<el-icon style="color:#67C23A" v-if="change_users < 0">
					<component :is="iconDown"/>
				</el-icon> &nbsp;
				<el-icon style="color:#F56C6C" v-else-if="change_users > 0">
					<component :is="iconUp"/>
				</el-icon>
			</div>

		</div>
		<el-divider style="margin: 2px"/>
		<div class="card-footer" v-if="title==='销售额'">
			本月销售额：<span style="float: right;">{{ month_all_order_amount }}元</span>
		</div>
		<div class="card-footer" v-else-if="title==='盲盒订单量'">
			本月盲盒订单量：<span style="float: right;">{{ month_blind_box_order_counts }}</span>
		</div>
		<div class="card-footer" v-else-if="title==='订单量'">
			本月订单量：<span style="float: right;">{{ month_all_order_counts }}</span>
		</div>
		<div class="card-footer" v-else-if="title==='新增用户'">
			本月新增用户：<span style="float: right;">{{ month_users }}人</span>
		</div>
	</el-card>
</template>

<script>
export default {
	props: {
		title: {
			type: String,
			default: ''
		},
		all_order_amount: { // 昨日总销售额
			type: Number,
			default: 0
		},
		blind_box_order_counts: { // 昨日盲盒订单量
			type: Number,
			default: 0
		},
		all_order_counts: { // 昨日总订单量
			type: Number,
			default: 0
		},
		users: { // 昨日新增用户数
			type: Number,
			default: 0
		},
		month_all_order_amount: { // 本月销售额
			type: Number,
			default: 0
		},
		month_blind_box_order_counts: { // 本月盲盒订单量
			type: Number,
			default: 0
		},
		month_all_order_counts: { // 本月订单量
			type: Number,
			default: 0
		},
		month_users: { // 本月新增用户数
			type: Number,
			default: 0
		},
		change_amount: { // 前天昨天变动销售额
			type: Number,
			default: 0
		},
		change_blind_box_order_counts: { // 前天昨天盲盒订单量变动
			type: Number,
			default: 0
		},
		change_all_order_counts: { // 前天昨天订单量变动
			type: Number,
			default: 0
		},
		change_users: { // 前天昨天变动用户数
			type: Number,
			default: 0
		},
	},
	data() {
		return {
			iconUp: 'el-icon-CaretTop',
			iconDown: 'el-icon-CaretBottom'
		}
	}
}
</script>

<style scoped>
.card-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.text {
	font-size: 14px;
}

.item {
	margin-bottom: 18px;
}

.data-content {
	height: 90px;
	width: 100%;
}

.data-content .number {
	font-size: 30px;
	margin-top: 10px;
}

.grow-percent {
	height: 42px;
	font-size: 14px;
	margin-top: 5px;
}

.card-footer {
	margin-top: 5px;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
}
</style>
