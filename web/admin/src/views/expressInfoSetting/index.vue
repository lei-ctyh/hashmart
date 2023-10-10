<template>
	<el-main>
		<el-card shadow="never">
			<el-tabs v-model="activeName">
				<el-tab-pane label="物流信息查询配置" name="first">
					<el-form ref="form" :model="form" label-width="160px">
						<el-form-item label="AppKey" style="width:700px">
							<el-input v-model="form.app_key"></el-input>
						</el-form-item>
						<el-form-item label="AppSecret" style="width:700px">
							<el-input v-model="form.app_secret"></el-input>
						</el-form-item>
						<el-form-item label="AppCode" style="width:700px">
							<el-input v-model="form.app_code"></el-input>
						</el-form-item>
						<el-form-item>
							<div style="font-size:12px;">
								本系统集成该接口查询物流信息，可点击<a href="https://market.aliyun.com/products/57126001/cmapi021863.html?spm=5176.730005.result.2.32ab3524s45loN&innerSource=search_%E5%BF%AB%E9%80%92%E6%9F%A5%E8%AF%A2#sku=yuncode1586300000" target="
								" style="color:#409EFF"> 阿里云市场物流查询接口 </a> 开通
							</div>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="Submit" style="width: 200px;">保存</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>
			</el-tabs>
		</el-card>
	</el-main>
</template>

<script>

export default {
	name: 'expressInfoSettingIndex',
	data() {
		return {
			activeName: 'first',
			form: {
				app_key: '',
				app_secret: '',
				app_code: ''
			}
		}
	},
	mounted() {
		this.getBaseConf()
	},
	methods: {
		async getBaseConf() {
			let res = await this.$API.expressInfoSetting.config.get()
			this.form = res.data.express
		},
		// 保存
		async Submit() {
			let res = await this.$API.expressInfoSetting.save.post(this.form)
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		},
	}
}

</script>
