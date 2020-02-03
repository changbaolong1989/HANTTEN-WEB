<template>
  <!-- 面板-上传下载 -->
  <el-collapse-item class="invoice" :title="title">
    <template slot="title">
      {{title}}&nbsp;
      <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;" @click.stop="uploadFile" v-if="showUploadBtn"></i>
      <!-- 上传弹窗 -->
      <dialog-upload
      ref="upload"
      :options="options"
      :uploadOptions="uploadOptions"
      @submit="submitUpload"
      :periodTypeBoolean="periodTypeBoolean"
      :periodType="periodType"
      :title="title"></dialog-upload>
      <!-- 浏览文件 -->
      <!--<dialog-browse-file ref="dialogBrowseFile" :file="file" :type="type" :id="id"></dialog-browse-file>-->
      <preview-file ref="dialogBrowseFile" :file="file" :type="type" :id="id"></preview-file>

    </template>
    <div class="content hide-upload-container">
      <el-upload ref="uploadDemo" accept="text" class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" multiple>
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
      <div class="file-list">
        <ul>
          <li tabindex="0" class="el-upload-list__item is-finished" v-for="(f, index) in currentFileList" v-bind:key="index">
            <a class="el-upload-list__item-name" style="min-width: 100px;flex-grow: 2;" @click.stop="browseFile(f)">
              <i class="el-icon-document"></i><span :title="f.materialName">{{ f.materialName }}</span>
            </a>
           <a class="el-upload-list__item-name" style="min-width: 60px;flex-grow: 1;" :title="f.userName" v-if="title!='上传发票'">
              {{ f.userName }}
            </a>
            <label class="el-upload-list__item-status-label"><i class="el-icon-upload-success el-icon-circle-check"></i></label>
            <i class="el-icon-download" @click.stop="download(f.materialPath,f.materialName,f.isGeneratedFile)"></i>
            <i class="el-icon-close" @click.stop="del(f)" v-if="f.isCanDel == '1' && !currentDelBtnFlag"></i>
          </li>
        </ul>
      </div>
    </div>
  </el-collapse-item>

</template>

<script>
  /* upload */
  import dialogUpload from './dialogUpload.vue';
  /* 浏览文件 */
  import dialogBrowseFile from './dialogBrowseFile.vue';

  import previewFile from './previewFile.vue';

  export default {
    components: {
      'dialog-upload': dialogUpload,
      'dialog-browse-file':dialogBrowseFile,
      'previewFile': previewFile
    },
    props: [
      'title', // 标题
      'showUploadBtn', // 显示上传按钮
      'list', // 文件列表
      'options',
      'uploadOptions',// 上传配置
      'delBtnFlag', // 不显示删除按钮
      'type', // 判断类型
      'id', // 主键id
	    'periodTypeBoolean',// 阶段类型
      'periodType',// 阶段类型
    ],
    data() {
      return {
        currentDelBtnFlag:this.delBtnFlag,
        currentFileList:this.tool.deepCopy(this.list, this.tool.deepCopy),// 当期文件列表
        file:''// 待浏览文件
      };
    },
    watch:{
      delBtnFlag(){
        let vm = this;
        vm.currentDelBtnFlag = vm.delBtnFlag;
      },
    list:{
      handler(){
        let vm = this;
        vm.currentFileList = vm.tool.deepCopy(vm.list, vm.tool.deepCopy);
      },
      immediate: true
    }
    },
    methods: {
      /**
       * @method 浏览文件
       */
      browseFile(file){
        let vm = this;
        vm.file = file;
        vm.$refs.dialogBrowseFile.show = true;
      },
      uploadFile() { /* 上传文件 */
        let vm = this;
          vm.$refs.upload.show = true;
      },
      submitUpload(data) { // 提交上传文件
        let vm = this;
        this.$emit('submit', data);
      },
      del(data) { // 删除文件
        this.$emit('del', data);
      },
      download(fileUrl, fileName, isGeneratedFile) { // 下载文件
        this.downloadFile(fileUrl, fileName, isGeneratedFile);
      }
    }
  };
</script>
<style lang="scss"></style>
<style lang="scss" scoped>
  .el-upload-list__item-name {
    margin: 0;
    display: inline-block;
    vertical-align: middle;
  }
</style>
