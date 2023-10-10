<template>
	<el-main>
		<el-card shadow="never">
			<el-tabs v-model="activeName">
				<el-tab-pane label="uniapp配置" name="first">
					<el-form ref="form" :model="form" label-width="160px">
						<el-form-item label="appid" style="width:700px">
							<el-input v-model="form.uniapp_appid"></el-input>
						</el-form-item>
						<el-form-item label="apiKey" style="width:700px">
							<el-input v-model="form.uniapp_api_key"></el-input>
						</el-form-item>
						<el-form-item label="apiSecret" style="width:700px">
							<el-input v-model="form.uniapp_api_secret"></el-input>
						</el-form-item>
						<el-form-item label="云函数URL化" style="width:700px">
							<el-input v-model="form.uniapp_cloud_url"></el-input>
						</el-form-item>
						<el-form-item style="margin-top: 50px;">
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
	name: 'uniapp',
	data() {
		return {
			activeName: 'first',
			form: {
				uniapp_appid: '',
				uniapp_api_key: '',
				uniapp_api_secret: '',
				uniapp_cloud_url: ''
			}
		}
	},
	mounted() {
		this.getBaseConf()
	},
	methods: {
		async getBaseConf() {
			let res = await this.$API.appletSetting.getUniapp.get()
			this.form = res.data.uniapp
		},
		// 保存
		async Submit() {
			let res = await this.$API.appletSetting.saveUniapp.post(this.form)
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		},
	}
}

</script>
