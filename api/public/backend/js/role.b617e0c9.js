"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[2647],{2792:function(e,t,a){a.r(t),a.d(t,{default:function(){return f}});var l=a(6252);const s={shadow:"never",style:{border:"none"}},i=(0,l.Uk)("查询"),o=(0,l.Uk)("添加角色"),n=(0,l._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1),r=(0,l.Uk)("正常"),d=(0,l.Uk)("禁用"),u=(0,l.Uk)("编辑"),c=(0,l.Uk)("删除"),m=(0,l._)("div",{style:{"margin-top":"20px"}},null,-1);function p(e,t,a,p,h,g){const w=(0,l.up)("el-input"),k=(0,l.up)("el-form-item"),f=(0,l.up)("el-button"),y=(0,l.up)("el-form"),b=(0,l.up)("el-table-column"),v=(0,l.up)("el-tag"),_=(0,l.up)("el-table"),C=(0,l.up)("el-pagination"),W=(0,l.up)("el-card"),x=(0,l.up)("save-dialog"),D=(0,l.up)("el-main");return(0,l.wg)(),(0,l.j4)(D,null,{default:(0,l.w5)((()=>[(0,l.Wm)(W,{shadow:"never"},{default:(0,l.w5)((()=>[(0,l._)("div",s,[(0,l.Wm)(y,{inline:!0,model:h.searchForm,class:"demo-form-inline"},{default:(0,l.w5)((()=>[(0,l.Wm)(k,{label:"角色名称"},{default:(0,l.w5)((()=>[(0,l.Wm)(w,{modelValue:h.searchForm.name,"onUpdate:modelValue":t[0]||(t[0]=e=>h.searchForm.name=e),placeholder:"角色名称",clearable:""},null,8,["modelValue"])])),_:1}),(0,l.Wm)(k,null,{default:(0,l.w5)((()=>[(0,l.Wm)(f,{type:"primary",onClick:g.search},{default:(0,l.w5)((()=>[i])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])]),(0,l.Wm)(f,{type:"primary",icon:"el-icon-plus",onClick:g.add},{default:(0,l.w5)((()=>[o])),_:1},8,["onClick"]),n,(0,l.Wm)(_,{data:h.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(b,{prop:"id",label:"ID"}),(0,l.Wm)(b,{prop:"name",label:"角色名称"}),(0,l.Wm)(b,{label:"状态"},{default:(0,l.w5)((e=>[1===e.row.status?((0,l.wg)(),(0,l.j4)(v,{key:0,type:"success"},{default:(0,l.w5)((()=>[r])),_:1})):(0,l.kq)("",!0),2===e.row.status?((0,l.wg)(),(0,l.j4)(v,{key:1,type:"error"},{default:(0,l.w5)((()=>[d])),_:1})):(0,l.kq)("",!0)])),_:1}),(0,l.Wm)(b,{prop:"create_time",label:"创建时间"}),(0,l.Wm)(b,{prop:"update_time",label:"修改时间"}),(0,l.Wm)(b,{label:"操作"},{default:(0,l.w5)((e=>[1!=e.row.id?((0,l.wg)(),(0,l.j4)(f,{key:0,onClick:t=>g.handleEdit(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[u])),_:2},1032,["onClick"])):(0,l.kq)("",!0),1!=e.row.id?((0,l.wg)(),(0,l.j4)(f,{key:1,onClick:t=>g.handleDel(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[c])),_:2},1032,["onClick"])):(0,l.kq)("",!0)])),_:1})])),_:1},8,["data"]),m,(0,l.Wm)(C,{background:"",layout:"->, prev, pager, next",total:h.total,"page-size":h.searchForm.limit,onCurrentChange:t[1]||(t[1]=t=>g.pageChange(e.page))},null,8,["total","page-size"])])),_:1}),h.dialog.save?((0,l.wg)(),(0,l.j4)(x,{key:0,ref:"saveDialog",onSuccess:g.handleSuccess,onClosed:t[2]||(t[2]=e=>h.dialog.save=!1)},null,8,["onSuccess"])):(0,l.kq)("",!0)])),_:1})}var h=a(9693),g={name:"roleIndex",components:{saveDialog:h["default"]},data(){return{dialog:{save:!1},total:1,searchForm:{name:"",page:1,limit:15},tableData:[]}},mounted(){this.getList()},methods:{search(){this.getList()},handleEdit(e){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open("edit").setData(e)}))},handleDel(e){this.$confirm("此操作将永久删除该角色, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{let t=await this.$API.role.del.post({id:e.id});0===t.code?(this.$message.success(t.msg),await this.getList()):this.$message.error(t.msg)})).catch((()=>{}))},pageChange(e){this.searchForm.page=e,this.getList()},async getList(){let e=await this.$API.role.list.get(this.searchForm);this.tableData=e.data.rows,this.total=e.data.total},handleSuccess(){this.getList()},add(){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open()}))}}},w=a(3744);const k=(0,w.Z)(g,[["render",p]]);var f=k}}]);