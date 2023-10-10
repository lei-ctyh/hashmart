<template>
	<el-main>
		<el-card shadow="never">
			<el-tabs v-model="activeName">
				<el-tab-pane label="阿里云短信设置" name="first">
					<el-form ref="form" :model="messageSetting" :label-position="labelPosition" label-width="160px">
						<el-form-item label="短信KeyID" style="width:700px">
							<el-input v-model="messageSetting.access_key_id"></el-input>
						</el-form-item>
						<el-form-item label="短信KeySecret" style="width:700px">
							<el-input v-model="messageSetting.access_key_secret"></el-input>
						</el-form-item>
						<el-form-item label="短信签名" style="width:700px">
							<el-input v-model="messageSetting.sign_name"></el-input>
						</el-form-item>
						<el-form-item style="margin-top: 50px;">
							<el-button type="primary" @click="SettingSubmit" style="width: 200px;">保存</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="阿里云短信模板" name="second">
					<el-form ref="form" :model="messageTpl" :label-position="labelPosition" label-width="160px">
						<el-form-item label="通用模板ID" style="width:700px">
							<el-input v-model="messageTpl.com_sms_code"></el-input>
						</el-form-item>
						<el-form-item label="用户登录模板ID" style="width:700px">
							<el-input v-model="messageTpl.login_sms_code"></el-input>
						</el-form-item>
						<el-form-item label="用户注册模板ID" style="width:700px">
							<el-input v-model="messageTpl.reg_sms_code"></el-input>
						</el-form-item>
						<el-form-item label="密码找回模板ID" style="width:700px">
							<el-input v-model="messageTpl.forget_sms_code"></el-input>
						</el-form-item>
						<el-form-item label="手机号码绑定模板ID" style="width:700px">
							<el-input v-model="messageTpl.bind_sms_code"></el-input>
						</el-form-item>
						<el-form-item style="margin-top: 50px;">
							<el-button type="primary" @click="tplSubmit" style="width: 200px;">保存</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>
			</el-tabs>
		</el-card>
	</el-main>
</template>

<script>

export default {
	name: 'messageSettingIndex',
	data() {
		return {
			labelPosition: 'left',
			activeName: 'first',
			messageSetting: {
				access_key_id: '',
				access_key_secret: '',
				sign_name: '',
			},
			messageTpl: {
				com_sms_code: '',
				login_sms_code: '',
				reg_sms_code: '',
				forget_sms_code: '',
				bind_sms_code: ''
			}
		}
	},
	mounted() {
		this.getBaseConf()
	},
	methods: {
		handleOnSuccess(e){
			console.log(e)
		},
		async getBaseConf() {
			let res = await this.$API.messageSetting.messageConfig.get()
			this.messageSetting = res.data.sms
			this.messageTpl = res.data.sms
		},
		// setting保存
		async SettingSubmit() {
			let res = await this.$API.messageSetting.save.post(this.messageSetting)
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		},
		// tpl保存
		async tplSubmit() {
			let res = await this.$API.messageSetting.save.post(this.messageTpl)
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		}
	}
}

</script>
