"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[75,2736],{435:function(e,t,l){l.r(t),l.d(t,{default:function(){return _}});var a=l(6252),o=l(3577);const m=(0,a.Uk)("开启"),u=(0,a.Uk)("关闭"),n=(0,a.Uk)(),s={style:{color:"#F56C6C","font-size":"12px","margin-left":"10px"}},d=(0,a.Uk)(),i=(0,a.Uk)("更改"),r=(0,a.Uk)("保存");function c(e,t,l,c,f,p){const h=(0,a.up)("el-radio"),w=(0,a.up)("el-form-item"),_=(0,a.up)("el-input"),g=(0,a.up)("el-image"),b=(0,a.up)("el-button"),k=(0,a.up)("el-form"),y=(0,a.up)("el-tab-pane"),W=(0,a.up)("el-tabs"),V=(0,a.up)("el-card"),x=(0,a.up)("Attachment"),S=(0,a.up)("el-dialog"),U=(0,a.up)("el-main");return(0,a.wg)(),(0,a.j4)(U,null,{default:(0,a.w5)((()=>[(0,a.Wm)(V,{shadow:"never"},{default:(0,a.w5)((()=>[(0,a.Wm)(W,{modelValue:f.activeName,"onUpdate:modelValue":t[3]||(t[3]=e=>f.activeName=e)},{default:(0,a.w5)((()=>[(0,a.Wm)(y,{label:"基础配置",name:"first"},{default:(0,a.w5)((()=>[(0,a.Wm)(k,{ref:"form",model:f.form,"label-width":"160px"},{default:(0,a.w5)((()=>[(0,a.Wm)(w,{label:"站点是否开启",style:{width:"500px"}},{default:(0,a.w5)((()=>[(0,a.Wm)(h,{label:"1",modelValue:f.form.web_open,"onUpdate:modelValue":t[0]||(t[0]=e=>f.form.web_open=e)},{default:(0,a.w5)((()=>[m])),_:1},8,["modelValue"]),(0,a.Wm)(h,{label:"2",modelValue:f.form.web_open,"onUpdate:modelValue":t[1]||(t[1]=e=>f.form.web_open=e)},{default:(0,a.w5)((()=>[u])),_:1},8,["modelValue"])])),_:1}),(0,a.Wm)(w,{label:"哈希币比例",style:{width:"500px"}},{default:(0,a.w5)((()=>[(0,a.Wm)(_,{modelValue:f.form.change_ratio,"onUpdate:modelValue":t[2]||(t[2]=e=>f.form.change_ratio=e),type:"number"},null,8,["modelValue"]),n,(0,a._)("span",s,"1 元 = "+(0,o.zw)(f.form.change_ratio)+" 哈希币",1)])),_:1}),(0,a.Wm)(w,{label:"客服微信",style:{width:"500px"}},{default:(0,a.w5)((()=>[(0,a.Wm)(g,{src:f.form.kefu_wechat,style:{width:"100px",height:"100px"}},null,8,["src"]),d,(0,a.Wm)(b,{style:{"margin-left":"50px"},onClick:p.showAttachment},{default:(0,a.w5)((()=>[i])),_:1},8,["onClick"])])),_:1}),(0,a.Wm)(w,{style:{"margin-top":"50px"}},{default:(0,a.w5)((()=>[(0,a.Wm)(b,{type:"primary",onClick:p.Submit,style:{width:"200px"}},{default:(0,a.w5)((()=>[r])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])])),_:1})])),_:1},8,["modelValue"])])),_:1}),(0,a.Wm)(S,{title:"选择资源",modelValue:f.dialogShow,"onUpdate:modelValue":t[4]||(t[4]=e=>f.dialogShow=e),width:1200,"destroy-on-close":""},{default:(0,a.w5)((()=>[(0,a.Wm)(x,{"select-num":1,onSelectedFiles:p.selectedBatchImg},null,8,["onSelectedFiles"])])),_:1},8,["modelValue"])])),_:1})}var f=l(3874),p={name:"appletSettingIndex",components:{Attachment:f.Z},data(){return{activeName:"first",form:{change_ratio:"",kefu_wechat:"",web_open:""},dialogShow:!1}},mounted(){this.getBaseConf()},methods:{async getBaseConf(){let e=await this.$API.system.base.get();this.form=e.data},async Submit(){let e=await this.$API.system.baseSave.post(this.form);0==e.code?this.$message.success(e.msg):this.$message.error(e.msg)},showAttachment(){this.dialogShow=!0},selectedBatchImg(e){this.form.kefu_wechat=e[0].url,this.dialogShow=!1}}},h=l(3744);const w=(0,h.Z)(p,[["render",c]]);var _=w}}]);