"use strict";(self["webpackChunkscui"]=self["webpackChunkscui"]||[]).push([[2623,4431],{3737:function(e,t,a){a.r(t),a.d(t,{default:function(){return w}});var l=a(6252);const s=(0,l.Uk)("添加"),o=(0,l.Uk)("重启服务"),i=(0,l.Uk)("刷新"),n=(0,l._)("div",{style:{width:"100%",height:"0","border-bottom":"#E4E7ED 1px dashed","margin-top":"15px"}},null,-1),c=(0,l.Uk)("执行日志"),r=(0,l.Uk)("删除"),d=(0,l._)("div",{style:{"margin-top":"20px"}},null,-1);function u(e,t,a,u,g,h){const m=(0,l.up)("el-button"),p=(0,l.up)("el-table-column"),f=(0,l.up)("el-switch"),w=(0,l.up)("el-table"),k=(0,l.up)("el-pagination"),b=(0,l.up)("el-card"),v=(0,l.up)("el-main"),y=(0,l.up)("save-dialog"),C=(0,l.up)("log-dialog");return(0,l.wg)(),(0,l.iD)(l.HY,null,[(0,l.Wm)(v,null,{default:(0,l.w5)((()=>[(0,l.Wm)(b,{shadow:"never"},{default:(0,l.w5)((()=>[(0,l.Wm)(m,{type:"primary",icon:"el-icon-plus",onClick:t[0]||(t[0]=e=>h.addTask())},{default:(0,l.w5)((()=>[s])),_:1}),(0,l.Wm)(m,{type:"danger",icon:"el-icon-refresh",onClick:t[1]||(t[1]=e=>h.reload())},{default:(0,l.w5)((()=>[o])),_:1}),(0,l.Wm)(m,{icon:"el-icon-refresh",onClick:t[2]||(t[2]=e=>h.refresh())},{default:(0,l.w5)((()=>[i])),_:1}),n,(0,l.Wm)(w,{data:g.tableData,style:{width:"100%","margin-top":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(p,{prop:"id",label:"ID"}),(0,l.Wm)(p,{prop:"title",label:"标题"}),(0,l.Wm)(p,{prop:"frequency",label:"规则"}),(0,l.Wm)(p,{prop:"running_times",label:"执行次数"}),(0,l.Wm)(p,{label:"状态"},{default:(0,l.w5)((e=>[(0,l.Wm)(f,{modelValue:e.row.status,"onUpdate:modelValue":t=>e.row.status=t,"active-color":"#13ce66","inactive-color":"#ff4949","active-value":1,"inactive-value":0,onChange:t=>h.changeStatus(t,e.row)},null,8,["modelValue","onUpdate:modelValue","onChange"])])),_:1}),(0,l.Wm)(p,{prop:"sort",label:"排序"}),(0,l.Wm)(p,{prop:"create_time",label:"创建时间"}),(0,l.Wm)(p,{label:"操作"},{default:(0,l.w5)((e=>[(0,l.Wm)(m,{onClick:t=>h.handleLog(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[c])),_:2},1032,["onClick"]),(0,l.Wm)(m,{onClick:t=>h.handleDel(e.row),type:"text",size:"small"},{default:(0,l.w5)((()=>[r])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),d,(0,l.Wm)(k,{background:"",layout:"->, prev, pager, next",total:g.total,"page-size":g.searchForm.limit,onCurrentChange:h.pageChange},null,8,["total","page-size","onCurrentChange"])])),_:1})])),_:1}),g.dialog.save?((0,l.wg)(),(0,l.j4)(y,{key:0,ref:"saveDialog",onSuccess:h.handleSuccess,onClosed:t[3]||(t[3]=e=>g.dialog.save=!1),"close-on-click-modal":!1,"close-on-press-escape":!1},null,8,["onSuccess"])):(0,l.kq)("",!0),g.dialog.log?((0,l.wg)(),(0,l.j4)(C,{key:1,ref:"logDialog",onClosed:t[4]||(t[4]=e=>g.dialog.log=!1),"close-on-click-modal":!1,"close-on-press-escape":!1},null,512)):(0,l.kq)("",!0)],64)}var g=a(9864),h=a(9151),m={name:"crontab",components:{saveDialog:g["default"],logDialog:h["default"]},data(){return{total:1,searchForm:{page:1,limit:15},dialog:{save:!1,log:!1},tableData:[]}},mounted(){this.getList()},methods:{search(){this.getList()},pageChange(e){this.searchForm.page=e,this.getList()},async getList(){let e=await this.$API.crontab.getList.get(this.searchForm);200==e.code&&e.data&&(this.tableData=e.data.list,this.total=e.data.count)},refresh(){this.getList()},async handleDel(e){this.$confirm("确定删除该定时任务吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{let t=await this.$API.crontab.delTask.post({id:e.id});200==t.code?(this.$message.success("操作成功"),setTimeout((()=>{this.getList()}),800)):this.$message.error(t.msg)})).catch((()=>{}))},addTask(){this.dialog.save=!0,this.$nextTick((()=>{this.$refs.saveDialog.open("add")}))},handleLog(e){this.dialog.log=!0,this.$nextTick((()=>{this.$refs.logDialog.open("add").setData(e)}))},handleSuccess(){this.getList()},async changeStatus(e,t){let a=await this.$API.crontab.editTask.post({field:"status",value:e,id:t.id});200==a.code?this.$message.success("操作成功"):this.$message.error(a.msg)},reload(){this.$confirm("确定重启定时任务吗,重启定时任务可能导致某些业务中断，请谨慎操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{let e=await this.$API.crontab.reloadServer.post();200==e.code?(this.$message.success("操作成功"),setTimeout((()=>{this.getList()}),800)):this.$message.error(e.msg)})).catch((()=>{}))}}},p=a(3744);const f=(0,p.Z)(m,[["render",u]]);var w=f}}]);