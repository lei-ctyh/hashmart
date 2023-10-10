<template>
	<el-main>
		<el-card shadow="never">
			<el-tabs v-model="activeName">
				<el-tab-pane label="用户协议" name="first">
					<sc-editor v-model="userform" :height="600"></sc-editor>
					<el-form  label-width="100px" style="margin-top:20px">
						<el-form-item style="margin-top: 50px;">
							<el-button type="primary" @click="userSubmit" style="width: 200px;">保存</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="隐私协议" name="second">
					<sc-editor v-model="privacyform" :height="600"></sc-editor>
					<el-form  label-width="100px" style="margin-top:20px">
						<el-form-item style="margin-top: 50px;">
							<el-button type="primary" @click="privacySubmit" style="width: 200px;">保存</el-button>
						</el-form-item>
					</el-form>
				</el-tab-pane>
			</el-tabs>
		</el-card>
	</el-main>
</template>

<script>

import { defineAsyncComponent } from 'vue';
const scEditor = defineAsyncComponent(() => import('@/components/scEditor'));

export default {
	name: 'pactSettingIndex',
	components: {
		scEditor
	},
	data() {
		return {
			activeName: 'first',
			labelPosition: 'left',
			userform: '',
			privacyform: ''
		}
	},
	mounted() {
		this.getBaseConf()
	},
	methods: {
		async getBaseConf() {
			let res = await this.$API.pactSetting.pactConfig.get()
			this.userform = res.data.userform
			this.privacyform = res.data.privacyform
		},
		// 用户协议保存
		async userSubmit() {
			let res = await this.$API.pactSetting.save.post({type:1,content:this.userform})
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		},
		// 隐私协议保存
		async privacySubmit() {
			let res = await this.$API.pactSetting.save.post({type:2,content:this.privacyform})
			if (res.code == 0) {
				this.$message.success(res.msg)
			} else {
				this.$message.error(res.msg)
			}
		}
	}
}

</script>
