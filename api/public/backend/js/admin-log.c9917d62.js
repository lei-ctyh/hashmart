"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[2912],{5239:function(e,a,t){t.r(a),t.d(a,{default:function(){return d}});var l=t(6252);const r={shadow:"never",style:{border:"none"}},n=(0,l.Uk)("查询"),o=(0,l._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1),i=(0,l._)("div",{style:{"margin-top":"20px"}},null,-1);function m(e,a,t,m,p,u){const s=(0,l.up)("el-input"),d=(0,l.up)("el-form-item"),h=(0,l.up)("el-button"),c=(0,l.up)("el-form"),g=(0,l.up)("el-table-column"),b=(0,l.up)("el-table"),W=(0,l.up)("el-pagination"),f=(0,l.up)("el-card"),w=(0,l.up)("el-main");return(0,l.wg)(),(0,l.j4)(w,null,{default:(0,l.w5)((()=>[(0,l.Wm)(f,{shadow:"never"},{default:(0,l.w5)((()=>[(0,l._)("div",r,[(0,l.Wm)(c,{inline:!0,model:p.searchForm,class:"demo-form-inline"},{default:(0,l.w5)((()=>[(0,l.Wm)(d,{label:"管理员名"},{default:(0,l.w5)((()=>[(0,l.Wm)(s,{modelValue:p.searchForm.username,"onUpdate:modelValue":a[0]||(a[0]=e=>p.searchForm.username=e),placeholder:"管理员名",clearable:""},null,8,["modelValue"])])),_:1}),(0,l.Wm)(d,null,{default:(0,l.w5)((()=>[(0,l.Wm)(h,{type:"primary",onClick:u.search},{default:(0,l.w5)((()=>[n])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])]),o,(0,l.Wm)(b,{data:p.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(g,{prop:"id",label:"ID"}),(0,l.Wm)(g,{prop:"admin_id",label:"管理员ID"}),(0,l.Wm)(g,{prop:"username",label:"管理员名"}),(0,l.Wm)(g,{prop:"title",label:"内容"}),(0,l.Wm)(g,{prop:"url",label:"路由"}),(0,l.Wm)(g,{prop:"ip",label:"ip"}),(0,l.Wm)(g,{prop:"data",label:"操作内容"}),(0,l.Wm)(g,{prop:"user_agent",label:"useragent"}),(0,l.Wm)(g,{prop:"create_time",label:"创建时间"})])),_:1},8,["data"]),i,(0,l.Wm)(W,{background:"",layout:"->, prev, pager, next",total:p.total,"page-size":p.searchForm.limit,onCurrentChange:u.pageChange},null,8,["total","page-size","onCurrentChange"])])),_:1})])),_:1})}var p={name:"adminLogIndex",data(){return{total:1,searchForm:{username:"",page:1,limit:15},tableData:[]}},mounted(){this.getList()},methods:{search(){this.getList()},pageChange(e){this.searchForm.page=e,this.getList()},async getList(){let e=await this.$API.adminLog.list.get(this.searchForm);this.tableData=e.data.rows,this.total=e.data.total}}},u=t(3744);const s=(0,u.Z)(p,[["render",m]]);var d=s}}]);