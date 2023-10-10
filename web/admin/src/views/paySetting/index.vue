<template>
  <el-main>
    <el-card shadow="never">
      <el-tabs v-model="activeName">
        <el-tab-pane label="API地址" name="first">
          <el-form ref="form" :model="apiform" label-width="120px">
            <el-form-item label="API地址" style="width: 700px">
              <el-input v-model="apiform.api_url"></el-input>
            </el-form-item>
            <el-form-item style="margin-top: 50px">
              <el-button type="primary" @click="apiSubmit" style="width: 200px"
                >保存</el-button
              >
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="微信支付" name="second">
          <el-form ref="form" :model="wxform" label-width="120px">
            <el-form-item label="是否开启">
              <el-radio label="1" v-model="wxform.wechat_pay_open"
                >开启</el-radio
              >
              <el-radio label="0" v-model="wxform.wechat_pay_open"
                >关闭</el-radio
              >
            </el-form-item>
            <el-form-item label="小程序appId" style="width: 700px">
              <el-input v-model="wxform.wechat_miniapp_id"></el-input>
            </el-form-item>
            <el-form-item label="APP的appId" style="width: 700px">
              <el-input v-model="wxform.wechat_pay_appid"></el-input>
            </el-form-item>
            <el-form-item label="商户ID(Mchid)" style="width: 700px">
              <el-input v-model="wxform.wechat_pay_mchid"></el-input>
            </el-form-item>
            <el-form-item label="密钥(Key)" style="width: 700px">
              <el-input v-model="wxform.wechat_pay_key"></el-input>
            </el-form-item>
            <el-form-item label="支付证书" style="width: 700px">
              <el-upload
                :action="uploadUrl"
                :limit="1"
                :on-success="handleOnSuccessCert"
                :headers="headers"
                :file-list="wechat_pay_cert"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="支付证书秘钥" style="width: 700px">
              <el-upload
                :action="uploadUrl"
                :limit="1"
                :headers="headers"
                :on-success="handleOnSuccessPem"
                :file-list="wechat_pay_pem"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item style="margin-top: 50px">
              <el-button type="primary" @click="wxSubmit" style="width: 200px"
                >保存</el-button
              >
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="支付宝支付" name="third">
          <el-form ref="form" :model="aliform" label-width="120px">
            <el-form-item label="是否开启">
              <el-radio label="1" v-model="aliform.alipay_open">开启</el-radio>
              <el-radio label="0" v-model="aliform.alipay_open">关闭</el-radio>
            </el-form-item>
            <el-form-item label="APPID" style="width: 700px">
              <el-input v-model="aliform.alipay_app_id"></el-input>
            </el-form-item>
            <el-form-item label="支付宝应用私钥" style="width: 700px">
              <el-input
                v-model="aliform.alipay_private_key"
                type="textarea"
                :rows="9"
              ></el-input>
            </el-form-item>
            <el-form-item label="支付宝公钥" style="width: 700px">
              <el-input
                v-model="aliform.alipay_public_key"
                type="textarea"
                :rows="8"
              ></el-input>
            </el-form-item>
            <el-form-item style="margin-top: 50px">
              <el-button type="primary" @click="aliSubmit" style="width: 200px"
                >保存</el-button
              >
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </el-main>
</template>

<script>
import sysConfig from "@/config";
import tool from "@/utils/tool";
export default {
  name: "paySettingIndex",
  data() {
    return {
      uploadUrl: sysConfig.API_URL + "/system.paySetting/uploadTxtFile",
      labelPosition: "left",
      activeName: "first",
      wechat_pay_cert: [],
      wechat_pay_pem: [],
      apiform: {
        api_url: "",
      },
      wxform: {
        wechat_pay_open: "",
        wechat_miniapp_id: "",
        wechat_pay_mchid: "",
        wechat_pay_key: "",
        wechat_pay_cert: "",
        wechat_pay_pem: "",
      },
      aliform: {
        alipay_open: "",
        alipay_app_id: "",
        alipay_private_key: "",
        alipay_public_key: "",
      },
      headers: {
        Authorization: "Bearer " + tool.cookie.get("TOKEN"),
      },
    };
  },
  mounted() {
    this.getBaseConf();
  },
  methods: {
    handleOnSuccessCert(e) {
      if (e.code == 0) {
        this.wxform.wechat_pay_cert = e.data.url;
        this.wechat_pay_cert[0] = e.data;
      }
    },
    handleOnSuccessPem(e) {
      if (e.code == 0) {
        this.wxform.wechat_pay_pem = e.data.url;
        this.wechat_pay_pem[0] = e.data;
      }
    },
    async getBaseConf() {
      let res = await this.$API.paySetting.payConfig.get();
      this.wxform = res.data.wechat_pay;
      this.aliform = res.data.alipay;
      this.apiform = res.data.api_url;
      this.wechat_pay_cert = [
        {
          name: res.data.wechat_pay.wechat_pay_cert,
          url: res.data.wechat_pay.wechat_pay_cert,
        },
      ];
      this.wechat_pay_pem = [
        {
          name: res.data.wechat_pay.wechat_pay_pem,
          url: res.data.wechat_pay.wechat_pay_pem,
        },
      ];
    },
    // api地址
    async apiSubmit() {
      let res = await this.$API.paySetting.save.post(this.apiform);
      if (res.code == 0) {
        this.$message.success(res.msg);
      } else {
        this.$message.error(res.msg);
      }
    },
    // wx保存
    async wxSubmit() {
      let res = await this.$API.paySetting.save.post(this.wxform);
      if (res.code == 0) {
        this.$message.success(res.msg);
      } else {
        this.$message.error(res.msg);
      }
    },
    // ali保存
    async aliSubmit() {
      let res = await this.$API.paySetting.save.post(this.aliform);
      if (res.code == 0) {
        this.$message.success(res.msg);
      } else {
        this.$message.error(res.msg);
      }
    },
  },
};
</script>
