<template>
    <el-container style="height:100%">
      <el-aside :width="needSelect ? '300px' : '400px'" style="height:100%">
        <ul style="padding:20px">
            <li>
                <div class="tree-item" @click="itemData(0)">
                    <span class="cate-title">全部资源</span>
                    <el-dropdown @command="handleCommand($event, {id: 0})" style="float: right;">
                        <span class="el-dropdown-link">
                            <el-icon><component :is="iconMore" /></el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="addTop">添加分类</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </li>
            <div id="cate-tree">
                <li v-for="item in cateTree" :key="item.id">
                    <el-icon v-if="item.children && item.children.length != 0"><component :is="((showSub == 'block') && (item.id == nowSubId)) ? icon : iconRight" @click="toggleSub(item)" /></el-icon>
                    <div class="tree-item" @click="itemData(item.id)">
                        <span class="cate-title" :class="{'active' : (cate_id == item.id)}">{{ item.name }}</span>
                        <el-dropdown @command="handleCommand($event, item)" style="float: right;" :class="{'menu-right': (item.children && item.children.length != 0)}">
                            <span class="el-dropdown-link">
                                <el-icon><component :is="iconMore" /></el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item command="add">添加分类</el-dropdown-item>
                                    <el-dropdown-item command="edit">编辑分类</el-dropdown-item>
                                    <el-dropdown-item command="del">删除分类</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                    <ul class="child-sub" v-if="item.children && item.children.length != 0"
                        :style="{'display': (item.id == nowSubId) ? showSub : 'none'}">
                        <li v-for="vo in item.children" :key="vo.id">
                            <div class="tree-item" @click="itemData(vo.id)">
                                <span class="cate-title sub-name" :class="{'active' : (cate_id == vo.id)}" style="width: 100px !important;">{{ vo.name }}</span>
                                <el-dropdown @command="handleCommand($event, vo)" style="float: right;top: -16px;left:2px" >
                                    <span class="el-dropdown-link">
                                        <el-icon><component :is="iconMore" /></el-icon>
                                    </span>
                                    <template #dropdown>
                                        <el-dropdown-menu>
                                            <el-dropdown-item command="edit">编辑分类</el-dropdown-item>
                                            <el-dropdown-item command="del">删除分类</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                            </div>
                        </li>
                    </ul>
                </li>
            </div>
        </ul>
      </el-aside>
      <el-main style="height:100%">
        <el-container>
            <el-header>
                <div style="width:400px">
                    <el-badge :value="selectedIds.length" class="item" v-if="needSelect" style="margin-right: 15px;">
                        <el-button type="success" icon="el-icon-Finished" size="small" style="font-size:12px;" :disabled="!selectedIds.length > 0" @click="confirmToUse">使用资源</el-button>
                    </el-badge>
                    <el-upload
                        class="upload-demo"
                        :headers="header" 
                        :action="uploadImgUrl"
                        :data="{'cate_id': cate_id}"
                        :show-file-list="false"
                        :before-upload="handleBefore"
                        :on-success="handleSuccess"
                        :on-progress="handleProgress"
                        :limit="1">
                        <el-button type="primary" icon="el-icon-UploadFilled" size="small" style="font-size:12px;">上传图片</el-button>
                    </el-upload>
                    <el-upload
                        class="upload-demo"
                        :headers="header"
                        :action="uploadVideoUrl"
                        :data="{'cate_id': cate_id}"
                        :show-file-list="false"
                        :before-upload="handleBefore"
                        :on-success="handleSuccess"
                        :on-progress="handleProgress"
                        :limit="1">
                        <el-button type="primary" icon="el-icon-UploadFilled" size="small" style="font-size:12px">上传视频</el-button>
                    </el-upload>
                    <el-button type="danger" icon="el-icon-DeleteFilled" size="small" style="font-size:12px" :disabled="!selectedIds.length > 0" @click="delFile">删除文件</el-button>
                </div>
            </el-header>
            <el-main>
                <div class="imgList" :style="needSelect ? 'width:710px': 'width:1190px'">
                    <div class="img-box" style="margin-right:20px" v-if="uploading">
                        <el-progress type="circle" :percentage="uplodPercent">上传中</el-progress>
                    </div>
                    <div class="img-box" :class="{'clear-margin': (index % oneLineNum == 1)}" v-for="(item, index) in fileList" :key="item.id">
                        <div class="item-box" @click="selectFile(item)">
                            <el-image
                            v-if="item.file_type == 1"
                            style="width: 100px; height: 100px"
                            :preview-src-list="srcList"
                            :src="item.url">
                            </el-image>
                            <video width="100" height="100" controls v-if="item.file_type == 2">
                                <source :src="item.url" :type="item.mine">
                                您的浏览器不支持 video 标签。
                            </video>
                            <div class="user-selected" v-if="selectedIds.indexOf(item.id) != -1">选中</div>
                        </div>
                        <div class="image-name">{{ item.filename }}</div>
                    </div>
                </div>
                <el-pagination
                    background
                    layout="->, prev, pager, next"
                    :page-size="pageSize"
                    @current-change="handlePageChange"
                    :total="total">
                </el-pagination>
            </el-main>
        </el-container>
      </el-main>
    </el-container>

    <el-dialog v-model="dialogFormVisible" title="分类管理" width="500px">
        <el-form :model="cateForm">
            <el-form-item label="分类名称" :label-width="formLabelWidth">
                <el-input v-model="cateForm.name" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="submitCate" :loading="loading">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
    import sysConfig from "@/config";
    import tool from '@/utils/tool';

    export default {
        props: {
                // 需要选择使用资源
          needSelect: {
              type: Boolean,
              default: true
          },
          // 最大选择数量
          selectNum: {
              type: Number,
              default: 0
          },
          // 选择资源类型 1:图片  2:视频
          fileType: {
              type: Number,
              default: 1
          },
          // 一页显示多少资源
          pageSize: {
              type: Number,
              default: 18
          },
        },
        data() {
          return {
            cate_id: 0,
            cateForm: {
                pid: 0,
                name: ''
            },
            uploadImgUrl: sysConfig.API_URL + '/system.attachment/uploadPicture',
            uploadVideoUrl: sysConfig.API_URL + '/system.attachment/uploadVideo',
            header: {
                Authorization: sysConfig.TOKEN_PREFIX + tool.cookie.get("TOKEN"),
            },
            srcList: [],
            fileList: [],
            oneLineNum: 10,
            page: 1,
            total: 1,
            uploading: false,
            uplodPercent: 0,
            selectedFile: [],
            selectedIds: [],
            formLabelWidth: 80,
            dialogFormVisible: false,
            loading: false,
            cateTree: [],
            nowSubId: 0,
            showSub: 'none',
            icon: 'el-icon-CaretBottom',
            iconRight: 'el-icon-CaretRight',
            iconMore: 'el-icon-MoreFilled'
          }
        },
        mounted() {
            this.getAttachmentCate()
            if (this.needSelect) {
                this.oneLineNum = 6
            }

            this.getResource()
        },
        methods: {
            handleCommand(event, val) {
                if (event == 'add') {
                    this.cateForm.pid = val.id
                    this.dialogFormVisible = true
                } else if (event == 'addTop') {
                    this.cateForm.pid = 0
                    this.dialogFormVisible = true
                } else if (event == 'edit') {
                    this.cateForm.id = val.id
                    this.cateForm.pid = val.pid
                    this.cateForm.name = val.name
                    this.dialogFormVisible = true
                } else if (event == 'del') {
                    this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(async () => {
                        let res = await this.$API.attachmentCate.del.get({id: val.id})
                        if (res.code == 0) {
                            this.$message.success(res.msg)
                            this.getAttachmentCate()
                        } else {
                            this.$message.error(res.msg)
                        }
                    }).catch(() => {})
                }
            },
            // 显示子菜单内容
            itemData(id) {
                this.cate_id = id
                this.getResource()
            },
            // 显示隐藏sub
            toggleSub(item) {
                this.nowSubId = item.id
                this.showSub = (this.showSub === 'none') ? 'block' : 'none';
            },
            // 获取分类列表树
            async getAttachmentCate() {
                let res = await this.$API.attachmentCate.list.get()
                this.cateTree = res.data
            },
            // 处理分类
            async submitCate() {
                if (this.cateForm.name == '') {
                    this.$message.error('请输入分类名称')
                    return ;
                }

                this.loading = true
                let res;
                if (this.cateForm.id) {
                    res = await this.$API.attachmentCate.edit.post(this.cateForm)
                } else {
                    res = await this.$API.attachmentCate.add.post(this.cateForm)
                }
                
                this.loading = false
                if (res.code == 0) {
                    this.$message.success(res.msg)
                    this.getAttachmentCate()
                } else {
                    this.$message.error(res.msg)
                }
                this.dialogFormVisible = false
            },
            // 获取资源
            async getResource() {
                let res = await this.$API.attachment.list.get({
                    cate_id: this.cate_id,
                    page: this.page,
                    limit: this.pageSize
                })

                if (res.code == 0) {
                    this.fileList = res.data.rows
                    this.total = res.data.total
                }
            },
            // 资源翻页
            handlePageChange(page) {
                this.page = page
                this.getResource()
            },
            // 上传前
            handleBefore() {
                this.uploading = true
            },
            // 上传成功
            handleSuccess() {
                this.uploading = false
                this.$message.success('上传成功')
                this.getResource()
            },
            // 上传进度
            handleProgress(percent) {
                this.uplodPercent = percent.percent
            },
            // 显示大图
            previewPic(val) {
                this.srcList = []
                this.srcList.push(val)
            },
            // 选中资源
            selectFile(file) {
                if (this.needSelect && this.fileType != parseInt(file.file_type)) {
                    let type = '图片'
                    if (this.fileType == 2) {
                        type = '视频'
                    }

                    this.$message.error('只可以选择' + type)
                    return ;
                }

                if (this.selectedIds.indexOf(file.id) != -1) {
                    this.selectedIds.splice(this.selectedIds.indexOf(file.id), 1)
                    this.selectedFile.splice(this.selectedIds.indexOf(file.id), 1)
                } else {

                    if (this.needSelect && (this.selectedIds.length + 1) > this.selectNum) {
                        this.$message.error('最多选则' + this.selectNum + '个')
                        return ;
                    }

                    this.selectedIds.push(file.id)
                    this.selectedFile.push(file)
                }
            },
            // 确定使用
            confirmToUse() {
                let selectedFiles = JSON.parse(JSON.stringify(this.selectedFile))
                this.$emit('selectedFiles', selectedFiles)
                this.selectedIds = []
                this.selectedFile = []
            },
            // 选择删除的文件
            async delFile() {
                if (this.selectedIds.length == 0) {
                    this.$message.error('请先选择要删除的资源')
                    return ;
                }

                let res = await this.$API.attachment.del.get({ids: this.selectedIds})
                if (res.code == 0) {
                    this.$message.success('删除成功')
                    this.getResource()
                    this.selectedFile = []
                    this.selectedIds = []
                } else {
                    this.$message.error(res.msg)
                }
            }
        }
    }
</script>

<style scoped>
    ul li {
        list-style: none;
        padding: 0px;
        white-space: nowrap;
        outline: none;
        margin-top: 10px;
    }

    .tree-item:hover {
        background: rgb(238, 238, 238);
    }

    .tree-item {
        display: inline-block;
        margin: 0px;
        padding: 0px 5px;
        border-radius: 3px;
        cursor: pointer;
        vertical-align: top;
        color: rgb(81, 90, 110);
        transition: all 0.2s ease-in-out 0s;
        width: 100%;
        height: 20px;
    }

    .tool-more {
        float: right;
        margin-top: -15px;
    }

    .menu-box {
        position: absolute;
        width: 80px;
        height: 88px;
        background-color: rgb(255, 255, 255);
        background-clip: padding-box;
        border-radius: 4px;
        box-shadow: rgba(0, 0, 0, 0.2) 0px 1px 6px;
        display: none;
        right: 10px;
    }

    .tree-dropdown-item {
        padding: 4px 10px;
    }

    .tree-dropdown-item:hover {
        background: rgb(238, 238, 238);
    }

    .r-triangle {
        margin-left: -20px;
        cursor: pointer;
    }

    .child-sub {
        padding: 0px;
        display: none;
    }

    .sub-name {
        margin-left: 35px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        width: 120px !important;
        display: block !important;
    }
    .el-icon {cursor: pointer;}
    .cate-title {cursor: pointer;}
    .menu-right {right: 11px}
    .active {
        color: rgb(45, 140, 240);
    }
    .imgList {
        width: 1190px;
        height: 500px;
        overflow: hidden;
    }
    .imgList .img-box {
        float: left;
    }
    .img-box {
        width: 110px;
        height: 120px;
        margin-left: 10px;
        margin-top: 10px;
        cursor: pointer;
        text-align: center;
    }
    .clear-margin {margin-left: 0 !important;}
    .image-name {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        height: 20px;
        width: 100px;
        text-align: center;
    }
    .item-box {width: 110px;height: 110px;position: relative;}
    .upload-demo {float: left;width: 88px;margin-right: 10px;}
    .user-selected {
        text-align: center;
        transform: rotate(45deg);
        right: -9px;
        top: 7px;
        width: 51px;
        background: #67C23A;
        color: white;
        box-shadow: 0 5px 5px rgba(0, 0, 0, 0.1);
        letter-spacing: 1px;
        position: absolute;
        font-size: 10px;
    }
</style>