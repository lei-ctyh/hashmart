"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[2669],{9044:function(e,a,t){t.r(a),t.d(a,{default:function(){return c}});var l=t(6252);const i={shadow:"never",style:{border:"none"}},o=(0,l.Uk)("查询"),n=(0,l._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1),r=(0,l.Uk)("兑换详情"),d=(0,l._)("div",{style:{"margin-top":"20px"}},null,-1);function s(e,a,t,s,u,m){const p=(0,l.up)("el-input"),h=(0,l.up)("el-form-item"),c=(0,l.up)("el-button"),g=(0,l.up)("el-form"),b=(0,l.up)("el-table-column"),w=(0,l.up)("el-table"),f=(0,l.up)("el-pagination"),_=(0,l.up)("el-card"),W=(0,l.up)("detail-dialog"),x=(0,l.up)("el-main");return(0,l.wg)(),(0,l.j4)(x,null,{default:(0,l.w5)((()=>[(0,l.Wm)(_,{shadow:"never"},{default:(0,l.w5)((()=>[(0,l._)("div",i,[(0,l.Wm)(g,{inline:!0,model:u.searchForm,class:"demo-form-inline"},{default:(0,l.w5)((()=>[(0,l.Wm)(h,{label:"兑换用户名"},{default:(0,l.w5)((()=>[(0,l.Wm)(p,{modelValue:u.searchForm.username,"onUpdate:modelValue":a[0]||(a[0]=e=>u.searchForm.username=e),placeholder:"兑换用户名",clearable:""},null,8,["modelValue"])])),_:1}),(0,l.Wm)(h,null,{default:(0,l.w5)((()=>[(0,l.Wm)(c,{type:"primary",onClick:m.search},{default:(0,l.w5)((()=>[o])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])]),n,(0,l.Wm)(w,{data:u.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(b,{prop:"id",label:"ID"}),(0,l.Wm)(b,{prop:"exchange_no",label:"兑换单号"}),(0,l.Wm)(b,{prop:"user_id",label:"兑换用户iD"}),(0,l.Wm)(b,{prop:"username",label:"兑换用户名"}),(0,l.Wm)(b,{prop:"exchange_num",label:"兑换数量"}),(0,l.Wm)(b,{prop:"total_amount",label:"兑换总金额"}),(0,l.Wm)(b,{prop:"create_time",label:"兑换时间"}),(0,l.Wm)(b,{width:"250",label:"操作"},{default:(0,l.w5)((e=>[(0,l.Wm)(c,{onClick:a=>m.handleDetail(e.row),link:"",type:"primary",size:"small"},{default:(0,l.w5)((()=>[r])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),d,(0,l.Wm)(f,{background:"",layout:"->, prev, pager, next",total:u.total,"page-size":u.searchForm.limit,onCurrentChange:m.pageChange},null,8,["total","page-size","onCurrentChange"])])),_:1}),u.dialog.detail?((0,l.wg)(),(0,l.j4)(W,{key:0,ref:"detailDialog",onClosed:a[1]||(a[1]=e=>u.dialog.detail=!1)},null,512)):(0,l.kq)("",!0)])),_:1})}var u=t(4199),m={name:"boxExchangeIndex",components:{detailDialog:u["default"]},data(){return{dialog:{detail:!1},total:1,searchForm:{username:"",page:1,limit:15},tableData:[]}},mounted(){this.getList()},methods:{search(){this.getList()},pageChange(e){this.searchForm.page=e,this.getList()},async getList(){let e=await this.$API.boxExchange.list.get(this.searchForm);this.tableData=e.data.rows,this.total=e.data.total},handleDetail(e){this.dialog.detail=!0,this.getDetail(e.id)},async getDetail(e){let a=await this.$API.boxExchange.detail.get({exchange_id:e});this.$nextTick((()=>{this.$refs.detailDialog.open(a.data)}))}}},p=t(3744);const h=(0,p.Z)(m,[["render",s]]);var c=h}}]);