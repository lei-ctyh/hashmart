"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[3266,4618],{3556:function(e,a,l){l.r(a),l.d(a,{default:function(){return y}});var t=l(6252),i=l(3577);const o=e=>((0,t.dD)("data-v-3effedac"),e=e(),(0,t.Cn)(),e),s={class:"click-bar",style:{width:"300px",display:"flex"}},d=(0,t.Uk)("返回列表 "),n=o((()=>(0,t._)("span",{style:{"font-size":"14px","font-weight":"700","margin-left":"20px"}},"盲盒商品详情",-1))),r={shadow:"never",style:{border:"none"}},p=(0,t.Uk)("查询"),u=o((()=>(0,t._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1))),m=(0,t.Uk)("添加盲盒商品"),c=o((()=>(0,t._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1))),g=(0,t.Uk)("编辑"),h=(0,t.Uk)("删除"),w={class:"page-div",style:{"margin-top":"20px"}};function b(e,a,l,o,b,_){const f=(0,t.up)("el-icon-arrow-left"),x=(0,t.up)("el-icon"),k=(0,t.up)("el-card"),y=(0,t.up)("el-input"),W=(0,t.up)("el-form-item"),v=(0,t.up)("el-option"),C=(0,t.up)("el-select"),D=(0,t.up)("el-button"),$=(0,t.up)("el-form"),T=(0,t.up)("el-table-column"),F=(0,t.up)("el-image"),U=(0,t.up)("el-tag"),z=(0,t.up)("el-table"),B=(0,t.up)("el-pagination"),E=(0,t.up)("save-dialog"),L=(0,t.up)("el-main");return(0,t.wg)(),(0,t.j4)(L,null,{default:(0,t.w5)((()=>[(0,t.Wm)(k,{shadow:"false",style:{display:"flex"}},{default:(0,t.w5)((()=>[(0,t._)("div",s,[(0,t._)("div",{onClick:a[0]||(a[0]=e=>_.goBack()),style:{cursor:"pointer"}},[(0,t.Wm)(x,{class:"back-icon"},{default:(0,t.w5)((()=>[(0,t.Wm)(f)])),_:1}),d]),n])])),_:1}),(0,t.Wm)(k,{shadow:"never"},{default:(0,t.w5)((()=>[(0,t._)("div",r,[(0,t.Wm)($,{inline:!0,model:b.searchForm,class:"demo-form-inline"},{default:(0,t.w5)((()=>[(0,t.Wm)(W,{label:"商品名称"},{default:(0,t.w5)((()=>[(0,t.Wm)(y,{modelValue:b.searchForm.goods_name,"onUpdate:modelValue":a[1]||(a[1]=e=>b.searchForm.goods_name=e),placeholder:"商品名称",clearable:""},null,8,["modelValue"])])),_:1}),(0,t.Wm)(W,{label:"标签"},{default:(0,t.w5)((()=>[(0,t.Wm)(C,{modelValue:b.searchForm.tag_id,"onUpdate:modelValue":a[2]||(a[2]=e=>b.searchForm.tag_id=e),placeholder:"请选择",clearable:""},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(b.goodsTag,(e=>((0,t.wg)(),(0,t.j4)(v,{key:e.id,label:e.name,value:e.id},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1}),(0,t.Wm)(W,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{type:"primary",onClick:_.search},{default:(0,t.w5)((()=>[p])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])]),u,(0,t.Wm)(D,{type:"primary",icon:"el-icon-plus",onClick:_.addBlindbox,style:{"margin-top":"15px"}},{default:(0,t.w5)((()=>[m])),_:1},8,["onClick"]),c,(0,t.Wm)(z,{data:b.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,t.w5)((()=>[(0,t.Wm)(T,{prop:"id",label:"ID",width:"100px"}),(0,t.Wm)(T,{label:"盲盒名称"},{default:(0,t.w5)((e=>[(0,t.Uk)((0,i.zw)(e.row.blindbox.name),1)])),_:1}),(0,t.Wm)(T,{prop:"goods_name",label:"商品名称"}),(0,t.Wm)(T,{prop:"boxTag.name",label:"标签",width:"100px"}),(0,t.Wm)(T,{label:"商品图片",width:"100px"},{default:(0,t.w5)((e=>[(0,t.Wm)(F,{src:e.row.goods_image.split(",")[0],style:{height:"36px",width:"36px"}},null,8,["src"])])),_:1}),(0,t.Wm)(T,{prop:"price",label:"售价",width:"100px"}),(0,t.Wm)(T,{prop:"recovery_price",label:"可兑换哈希币",width:"100px"}),(0,t.Wm)(T,{label:"中奖范围"},{default:(0,t.w5)((e=>[(0,t.Wm)(U,null,{default:(0,t.w5)((()=>[(0,t.Uk)((0,i.zw)(e.row.lottery_min_no)+" ~ "+(0,i.zw)(e.row.lottery_max_no),1)])),_:2},1024)])),_:1}),(0,t.Wm)(T,{label:"中奖概率"},{default:(0,t.w5)((e=>[(0,t.Wm)(U,{type:"danger"},{default:(0,t.w5)((()=>[(0,t.Uk)((0,i.zw)(e.row.probability)+" %",1)])),_:2},1024)])),_:1}),(0,t.Wm)(T,{label:"操作"},{default:(0,t.w5)((e=>[(0,t.Wm)(D,{onClick:a=>_.handleEdit(e.row),type:"text",size:"small"},{default:(0,t.w5)((()=>[g])),_:2},1032,["onClick"]),(0,t.Wm)(D,{onClick:a=>_.handleDel(e.row),type:"text",size:"small"},{default:(0,t.w5)((()=>[h])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),(0,t._)("div",w,[(0,t.Wm)(B,{background:"",layout:"->, prev, pager, next","page-size":b.searchForm.limit,onCurrentChange:_.pageChangeHandle,total:b.page.total},null,8,["page-size","onCurrentChange","total"])])])),_:1}),b.dialog.save?((0,t.wg)(),(0,t.j4)(E,{key:0,ref:"saveDialog",onSuccess:_.handleSuccess,onClosed:a[3]||(a[3]=e=>b.dialog.save=!1)},null,8,["onSuccess"])):(0,t.kq)("",!0)])),_:1})}l(7658);var _=l(3600),f={name:"goodsCate",components:{saveDialog:_["default"]},data(){return{dialog:{save:!1},tableData:[],searchForm:{blindbox_id:this.$route.query.id,tag_id:"",goods_name:"",page:1,limit:15},page:{total:1},goodsTag:[]}},mounted(){this.getList(),this.getBlindboxTag()},methods:{search(){this.getList()},addBlindbox(){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open("add",this.searchForm.blindbox_id)}))},handleEdit(e){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open("edit").setData(e)}))},handleDel(e){this.$confirm("此操作将永久删除该商品, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{let a=await this.$API.blindboxDetail.del.get({id:e.id,blindbox_id:e.blindbox_id});0==a.code?(this.$message.success(a.msg),this.getList()):this.$message.error(a.msg)})).catch((()=>{}))},async getList(){let e=await this.$API.blindboxDetail.list.get(this.searchForm);this.tableData=e.data.rows,this.page.total=e.data.total},handleSuccess(){this.getList()},pageChangeHandle(e){this.searchForm.page=e,this.getList()},async getBlindboxTag(){let e=await this.$API.blindboxTag.list.get({limit:9999999,page:1,status:1,hashmart_auth_skip:1});this.goodsTag=e.data.rows},goBack(){this.$router.push({path:"/blindbox/index"})}}},x=l(3744);const k=(0,x.Z)(f,[["render",b],["__scopeId","data-v-3effedac"]]);var y=k}}]);