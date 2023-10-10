<template>

	<el-main>
		<el-row :gutter="15">
			<el-col :lg="6">
				<dataCard title="销售额"
						  :all_order_amount="homeData.all_order_amount"
						  :month_all_order_amount="homeData.month_all_order_amount"
						  :change_amount="homeData.change_amount"
				></dataCard>
			</el-col>
			<el-col :lg="6">
				<dataCard title="盲盒订单量"
						  :blind_box_order_counts="homeData.blind_box_order_counts"
						  :month_blind_box_order_counts="homeData.month_blind_box_order_counts"
						  :change_blind_box_order_counts="homeData.change_blind_box_order_counts"
				></dataCard>
			</el-col>
			<el-col :lg="6">
				<dataCard title="订单量"
						  :all_order_counts="homeData.all_order_counts"
						  :month_all_order_counts="homeData.month_all_order_counts"
						  :change_all_order_counts="homeData.change_all_order_counts"
				></dataCard>
			</el-col>
			<el-col :lg="6">
				<dataCard title="新增用户"
						  :users="homeData.users"
						  :month_users="homeData.month_users"
						  :change_users="homeData.change_users"
				></dataCard>
			</el-col>
		</el-row>

		<el-row :gutter="15">
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/user/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#66B1FF"><component :is="iconData.userIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">用户管理</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/sotre/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#67C23A"><component :is="iconData.setIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">存储引擎</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/goods/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#F56C6C"><component :is="iconData.shoppingIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">商品管理</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/blindBoxOrder/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#F56C6C"><component :is="iconData.orderIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">订单管理</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/blindbox/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#E6A23C"><component :is="iconData.boxIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">盲盒管理</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/userOrderRecord/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#67C23A"><component :is="iconData.recordIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">中奖记录</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/paySetting/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#F56C6C"><component :is="iconData.payIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">支付设置</p>
					</a>
				</div>
			</el-col>
			<el-col :lg="3">
				<div class="function-card">
					<a href="#/messageSetting/index">
						<el-icon style="font-size:28px;margin-top: 20px;color:#F56C6C"><component :is="iconData.smsIcon" /></el-icon>
						<p style="margin-top:8px;font-size:14px">短信设置</p>
					</a>
				</div>
			</el-col>
		</el-row>

		<el-row :gutter="15" style="margin-top:20px">
			<el-col :lg="24">
				<el-card shadow="never" style="width:100%">
					<scEcharts height="300px" :option="orderOption"></scEcharts>
				</el-card>
			</el-col>
		</el-row>

		<el-row :gutter="15" style="margin-top:20px">
			<el-col :lg="24">
				<el-card shadow="never" style="width:100%">
					<scEcharts height="300px" :option="userOption"></scEcharts>
				</el-card>
			</el-col>
<!--			<el-col :lg="8">-->
<!--				<el-card shadow="never" style="width:100%">-->
<!--					<scEcharts height="300px" :option="option4"></scEcharts>-->
<!--				</el-card>-->
<!--			</el-col>-->
		</el-row>
	</el-main>

</template>

<script>
	import dataCard from '@/components/datacard'
	import scEcharts from '@/components/scEcharts';

    export default {
		name: 'home',
		components: {
			dataCard,
			scEcharts
		},
		data() {
			return {
				iconData: {
					userIcon: 'el-icon-UserFilled',
					setIcon: 'el-icon-Cloudy',
					shoppingIcon: 'el-icon-shopping-cart',
					orderIcon: 'el-icon-List',
					boxIcon: 'el-icon-Box',
					recordIcon: 'el-icon-Collection',
					payIcon: 'el-icon-money',
					smsIcon: 'el-icon-ChatLineSquare'
				},
				orderOption: {},
				userOption: {},
				option4: {
					title: {
						text: '购买用户统计',
						subtext: '基础饼图',
					},
					tooltip: {
						trigger: 'item'
					},
					series: [
						{
							name: '访问来源',
							type: 'pie',
							radius: ['40%', '70%'],
							center: ['50%', '60%'],
							label: false,
							data: [
								{value: 1048, name: '搜索引擎'},
								{value: 735, name: '直接访问'},
								{value: 300, name: '视频广告'}
							]
						}
					]
				},
				//默认统计数据
				homeData: {
					all_order_amount: 0, //昨日总销售额
					blind_box_order_counts: 0, // 昨日盲盒订单量
					all_order_counts: 0, // 昨日总订单量
					users: 0, // 昨日新增注册用户数
					month_all_order_amount: 0, // 本月销售额
					month_blind_box_order_counts: 0, // 本月盲盒订单量
					month_all_order_counts: 0, // 本月所有订单量
					month_users: 0, // 本月新增用户数

					change_amount: 0, // 前天昨天变动销售额
					change_blind_box_order_counts:0, // 前天昨天盲盒订单量变动
					change_all_order_counts: 0, // 前天昨天所有订单量变动
					change_users: 0, // 前天昨天变动用户数
				}
			}
		},
		mounted() {
			this.getHomeData()
		},
		methods: {
		// 获取首页统计数据
			async getHomeData() {
				let res = await this.$API.homeData.list.get(this.searchForm)
				this.homeData.all_order_amount = res.data.all_order_amount // 昨日销售额
				this.homeData.blind_box_order_counts = res.data.blind_box_order_counts // 昨日盲盒订单量
				this.homeData.all_order_counts = res.data.all_order_counts // 昨日所有订单量
				this.homeData.users = res.data.users // 昨日新增用户数
				this.homeData.month_all_order_amount = res.data.month_all_order_amount // 本月总销售额
				this.homeData.month_blind_box_order_counts = res.data.month_blind_box_order_counts // 本月盲盒订单量
				this.homeData.month_all_order_counts = res.data.month_all_order_counts // 本月所有订单量
				this.homeData.month_users = res.data.month_users // 本月新增用户数
				this.homeData.change_amount = res.data.change_amount // 前天昨天变动销售额
				this.homeData.change_blind_box_order_counts = res.data.change_blind_box_order_counts // 前天昨天盲盒订单量变动
				this.homeData.change_all_order_counts = res.data.change_all_order_counts // 前天昨天所有订单量变动
				this.homeData.change_users = res.data.change_users // 前天昨天变动用户数

				this.orderOption = {
					title: {
						text: '订单',
						subtext: '最近15日曲线',
					},
					grid: {
						top: '80px'
					},
					tooltip: {
						trigger: 'axis'
					},
					xAxis: {
						type: 'category',
						data: res.data.dates
					},
					yAxis: {
						type: 'value'
					},
					series: [{
						data: res.data.date_orders,
						type: 'line',
						smooth:true
					}]
				}
				this.userOption = {
					title: {
						text: '用户',
						subtext: '近15日注册用户数',
					},
					grid: {
						top: '80px'
					},
					tooltip: {
						trigger: 'axis'
					},
					xAxis: {
						type: 'category',
						data: res.data.dates
					},
					yAxis: {
						type: 'value'
					},
					series: [{
						data: res.data.date_users,
						type: 'line',
						smooth:true,
						areaStyle: {}
					}]
				}
			},
		}
    }
</script>

<style scoped>
	.function-card {height: 93px;text-align: center;background: #fff;}
</style>
