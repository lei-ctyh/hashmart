<template>
	<el-form ref="loginForm" :model="form" :rules="rules" label-width="0" size="large">
		<el-form-item prop="user">
			<el-input v-model="form.user" prefix-icon="el-icon-user" clearable :placeholder="$t('login.userPlaceholder')"></el-input>
		</el-form-item>
		<el-form-item prop="password">
			<el-input v-model="form.password" prefix-icon="el-icon-lock" clearable show-password :placeholder="$t('login.PWPlaceholder')"></el-input>
		</el-form-item>
		<el-form-item prop="captcha">
			<el-input v-model="form.captcha" prefix-icon="el-icon-lock" clearable style="width: 290px;"></el-input>
			<el-image :src="captchaSrc" style="width:100px;height: 38px;;margin-left: 10px;cursor: pointer;" @click="changeCaptcha"></el-image>
		</el-form-item>
		<el-form-item>
			<el-button type="primary" style="width: 100%;" :loading="islogin" @click="login">{{ $t('login.signIn') }}</el-button>
		</el-form-item>

	</el-form>
</template>

<script>
	export default {
		data() {
			return {
				userType: 'admin',
				form: {
					user: "admin",
					password: "admin123",
					autologin: false,
					captcha: '',
					key: ''
				},
				captchaSrc: '',
				rules: {
					user: [
						{required: true, message: this.$t('login.userError'), trigger: 'blur'}
					],
					password: [
						{required: true, message: this.$t('login.PWError'), trigger: 'blur'}
					],
					captcha: [
						{required: true, message: '请输入验证码', trigger: 'blur'}
					]
				},
				islogin: false,
			}
		},
		mounted() {
			this.changeCaptcha()

			document.onkeydown = (event) => {
                var e = event || window.event;
                if (e && e.keyCode == 13) {
                    this.login();
                }
            }
		},
		methods: {
			// 更换验证码
			async changeCaptcha() {
				let res = await this.$API.auth.captcha.get()
				this.captchaSrc = res.data.img
				this.form.key = res.data.key
			},
			async login() {

				var validate = await this.$refs.loginForm.validate().catch(()=>{})
				if(!validate){ return false }

				this.islogin = true
				var data = {
					username: this.form.user,
					password: this.form.password,
					captcha: this.form.captcha,
					key: this.form.key
				}
				//获取token
				var user = await this.$API.auth.token.post(data)
				if (user.code == 0) {
					this.$TOOL.cookie.set("TOKEN", user.data.token, {
						expires: this.form.autologin? 24*60*60 : 0
					})
					this.$TOOL.data.set("USER_INFO", user.data.userInfo)

					// 获取菜单
					if (user.data.menu.length ==0) {
						this.islogin = false
						this.$alert("当前用户无任何菜单权限，请联系系统管理员", "无权限访问", {
							type: 'error',
							center: true
						})
						return false
					}
					this.$TOOL.data.set("MENU", user.data.menu)
					//this.$TOOL.data.set("PERMISSIONS", menu.data.permissions)

				}else{
					this.islogin = false
					this.$message.warning(user.msg)
					this.changeCaptcha()
					return false
				}


				this.$router.replace({
					path: '/'
				})
				this.$message.success("登录成功")
				this.islogin = false
			},
		}
	}
</script>

<style>
</style>
