"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[1129,9720],{2247:function(e,a,t){t.r(a),t.d(a,{default:function(){return _}});var l=t(6252),s=t(3577);const i={shadow:"never",style:{border:"none"}},o=(0,l.Uk)("查询"),r=(0,l._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1),n=(0,l.Uk)("是"),d=(0,l.Uk)("否"),u=(0,l.Uk)("编辑"),m=(0,l.Uk)("删除"),c=(0,l._)("div",{style:{"margin-top":"20px"}},null,-1);function p(e,a,t,p,h,g){const w=(0,l.up)("el-input"),f=(0,l.up)("el-form-item"),_=(0,l.up)("el-button"),k=(0,l.up)("el-form"),b=(0,l.up)("el-table-column"),v=(0,l.up)("el-tag"),y=(0,l.up)("el-table"),W=(0,l.up)("el-pagination"),C=(0,l.up)("el-card"),x=(0,l.up)("save-dialog"),D=(0,l.up)("el-main");return(0,l.wg)(),(0,l.j4)(D,null,{default:(0,l.w5)((()=>[(0,l.Wm)(C,{shadow:"never"},{default:(0,l.w5)((()=>[(0,l._)("div",i,[(0,l.Wm)(k,{inline:!0,model:h.searchForm,class:"demo-form-inline"},{default:(0,l.w5)((()=>[(0,l.Wm)(f,{label:"会员id"},{default:(0,l.w5)((()=>[(0,l.Wm)(w,{modelValue:h.searchForm.user_id,"onUpdate:modelValue":a[0]||(a[0]=e=>h.searchForm.user_id=e),placeholder:"会员id",clearable:""},null,8,["modelValue"])])),_:1}),(0,l.Wm)(f,null,{default:(0,l.w5)((()=>[(0,l.Wm)(_,{type:"primary",onClick:g.search},{default:(0,l.w5)((()=>[o])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])]),r,(0,l.Wm)(y,{data:h.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(b,{prop:"id",label:"ID"}),(0,l.Wm)(b,{prop:"user_id",label:"用户id"}),(0,l.Wm)(b,{prop:"receiver",label:"收件人名"}),(0,l.Wm)(b,{prop:"phone",label:"收件人手机号"}),(0,l.Wm)(b,{label:"收件人地址"},{default:(0,l.w5)((e=>[(0,l.Uk)((0,s.zw)(e.row.city_name)+(0,s.zw)(e.row.area_name)+(0,s.zw)(e.row.address),1)])),_:1}),(0,l.Wm)(b,{label:"是否默认"},{default:(0,l.w5)((e=>[1==e.row.default_flag?((0,l.wg)(),(0,l.j4)(v,{key:0,type:"success"},{default:(0,l.w5)((()=>[n])),_:1})):(0,l.kq)("",!0),2==e.row.default_flag?((0,l.wg)(),(0,l.j4)(v,{key:1,type:"error"},{default:(0,l.w5)((()=>[d])),_:1})):(0,l.kq)("",!0)])),_:1}),(0,l.Wm)(b,{prop:"create_time",label:"创建时间"}),(0,l.Wm)(b,{label:"操作"},{default:(0,l.w5)((e=>[(0,l.Wm)(_,{onClick:a=>g.handleEdit(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[u])),_:2},1032,["onClick"]),(0,l.Wm)(_,{onClick:a=>g.handleDel(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[m])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),c,(0,l.Wm)(W,{background:"",layout:"->, prev, pager, next",total:h.total,"page-size":h.searchForm.limit,onCurrentChange:g.pageChange},null,8,["total","page-size","onCurrentChange"])])),_:1}),h.dialog.save?((0,l.wg)(),(0,l.j4)(x,{key:0,ref:"saveDialog",onSuccess:g.handleSuccess,onClosed:a[1]||(a[1]=e=>h.dialog.save=!1)},null,8,["onSuccess"])):(0,l.kq)("",!0)])),_:1})}var h=t(9507),g={name:"userAddressIndex",components:{saveDialog:h["default"]},data(){return{dialog:{save:!1},total:1,searchForm:{user_id:"",page:1,limit:15},tableData:[]}},mounted(){this.getList()},methods:{search(){this.getList()},handleEdit(e){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open("edit").setData(e)}))},handleDel(e){this.$confirm("此操作将永久删除该会员地址, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{let a=await this.$API.userAddress.del.post({id:e.id});0===a.code?(this.$message.success(a.msg),await this.getList()):this.$message.error(a.msg)})).catch((()=>{}))},pageChange(e){this.searchForm.page=e,this.getList()},async getList(){let e=await this.$API.userAddress.list.get(this.searchForm);this.tableData=e.data.rows,this.total=e.data.total},handleSuccess(){this.getList()}}},w=t(3744);const f=(0,w.Z)(g,[["render",p]]);var _=f}}]);