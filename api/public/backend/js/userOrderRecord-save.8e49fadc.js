"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[877],{1010:function(e,s,l){l.r(s),l.d(s,{default:function(){return u}});var o=l(6252);const i=(0,o.Uk)("取 消"),a=(0,o.Uk)("保 存");function r(e,s,l,r,t,d){const m=(0,o.up)("el-input"),u=(0,o.up)("el-form-item"),n=(0,o.up)("el-form"),p=(0,o.up)("el-button"),c=(0,o.up)("el-dialog");return(0,o.wg)(),(0,o.j4)(c,{title:t.titleMap[t.mode],modelValue:t.visible,"onUpdate:modelValue":s[4]||(s[4]=e=>t.visible=e),width:600,"destroy-on-close":"",onClosed:s[5]||(s[5]=s=>e.$emit("closed")),"close-on-click-modal":!1},{footer:(0,o.w5)((()=>[(0,o.Wm)(p,{onClick:s[2]||(s[2]=e=>t.visible=!1)},{default:(0,o.w5)((()=>[i])),_:1}),"show"!==t.mode?((0,o.wg)(),(0,o.j4)(p,{key:0,type:"primary",loading:t.isSaveing,onClick:s[3]||(s[3]=e=>d.submit())},{default:(0,o.w5)((()=>[a])),_:1},8,["loading"])):(0,o.kq)("",!0)])),default:(0,o.w5)((()=>[(0,o.Wm)(n,{model:t.form,rules:t.rules,disabled:"show"===t.mode,ref:"dialogForm","label-width":"100px","label-position":"left"},{default:(0,o.w5)((()=>[(0,o.Wm)(u,{label:"物流公司名称",prop:"express_name"},{default:(0,o.w5)((()=>[(0,o.Wm)(m,{modelValue:t.form.express_name,"onUpdate:modelValue":s[0]||(s[0]=e=>t.form.express_name=e),placeholder:"物流公司名称",clearable:""},null,8,["modelValue"])])),_:1}),(0,o.Wm)(u,{label:"物流单号",prop:"express_no"},{default:(0,o.w5)((()=>[(0,o.Wm)(m,{modelValue:t.form.express_no,"onUpdate:modelValue":s[1]||(s[1]=e=>t.form.express_no=e),placeholder:"物流单号",clearable:""},null,8,["modelValue"])])),_:1})])),_:1},8,["model","rules","disabled"])])),_:1},8,["title","modelValue"])}var t={emits:["success","closed"],data(){return{areas:[],mode:"deliver",titleMap:{deliver:"发货",show:"查看"},visible:!1,isSaveing:!1,form:{id:"",express_name:"",express_no:""},rules:{express_name:[{required:!0,message:"请输入物流公司名称",trigger:"blur"}],express_no:[{required:!0,message:"请输入物流单号",trigger:"blur"}]}}},mounted(){},methods:{open(e="add"){return this.mode=e,this.visible=!0,this},submit(){this.$refs.dialogForm.validate((async e=>{if(!e)return!1;var s;this.isSaveing=!0,s=await this.$API.userBoxDeliver.deliver.post(this.form),this.isSaveing=!1,0===s.code?(this.$emit("success",this.form,this.mode),this.visible=!1,this.$message.success(s.msg)):this.$message.error(s.msg)}))},setData(e){this.form.id=e.id},handleChange(e){console.log(e)}}},d=l(3744);const m=(0,d.Z)(t,[["render",r],["__scopeId","data-v-002aa892"]]);var u=m}}]);