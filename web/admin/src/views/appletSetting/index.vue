<template>
	<el-main>
		<el-card shadow="never">
			<el-tabs v-model="activeName">
				<el-tab-pane label="小程序设置" name="first">
					<el-form ref="form" :model="form" label-width="160px">
						<el-form-item label="appId" style="width:700px">
							<el-input v-model="form.miniapp_app_id"></el-input>
						</el-form-item>
						<el-form-item label="AppSecret" style="width:700px">
							<el-input v-model="form.miniapp_app_secret"></el-input>
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
	name: 'appletSettingIndex',
	data() {
		return {
			activeName: 'first',
			form: {
				miniapp_app_id: '',
				miniapp_app_secret: '',
			}
		}
	},
	mounted() {
		this.getBaseConf()
	},
	methods: {
		async getBaseConf() {
			let res = await this.$API.appletSetting.getConfig.get()
			this.form = res.data.miniapp
		},
		// 保存
		async Submit() {
			let res = await this.$API.appletSetting.save.post(this.form)
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		},
	}
}

</script>
