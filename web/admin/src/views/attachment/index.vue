<template>
    <el-main style="height:100%">
        <el-card style="height:100%" class="el-card-define" shadow="never">
            <Attachment :need-select="false" :page-size="40"></Attachment>
        </el-card>
    </el-main>
</template>

<script>

    import Attachment from '@/components/attachment';

    export default {
		name: 'attachmentMG',
        components: {
            Attachment
        },
		    data() {
          return {
            
          }
        },
    }
</script>

<style scoped>
.el-card-define {
  min-height: 100%;
  height: 100%;
}
.el-card-define >>> .el-card__body {
  height: 100%;
}
</style>