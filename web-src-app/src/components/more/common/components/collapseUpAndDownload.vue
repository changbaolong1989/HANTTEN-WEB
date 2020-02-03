<template>
  <!-- 面板-上传下载删除 -->
  <el-collapse-item class="invoice collapseUpAndDownload" :title="title" :name="name">
    <template slot="title" v-if="showUploadBtn">
      {{ title }}&nbsp;
      <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;" @click.stop="uploadFile"></i>
    </template>
    <input type="file" ref="file" hidden @change="change" />
    <div class="content hide-upload-container">
      <ul class="file-list">
        <li tabindex="0" class="file-item is-finished" v-for="(f, index) in currentFiles" v-bind:key="index">
          <a style="width: 250px; overflow: hidden;text-overflow: ellipsis; white-space: nowrap; display: block;" @click.stop="browseFile(f)">
            <i class="el-icon-document"></i>
            {{f.materialName }}
          </a>
          <a style="width: 60px;">{{ f.userName }}</a>
          <i class="iconfont iconxiazai-" @click.stop="download(f.materialPath, f.materialName, f.isGeneratedFile)" v-if="f.downloadFlag !== '1'"></i>
          <i class="iconfont iconshanchu" @click.stop="del(f)" v-if="(f.isCanDel == '1' && !currentDelBtnFlag)||showDelBtn"></i>
        </li>
      </ul>
    </div>
    <!-- 浏览文件 -->
    <!-- <dialog-browse-file ref="dialogBrowseFile" :file="file" :type="type" :id="id"></dialog-browse-file> -->
    <previewFile ref="dialogBrowseFile" :file="file" :type="type" :id="id"></previewFile>
  </el-collapse-item>
</template>

<script>
/* 浏览文件 */
// import dialogBrowseFile from './dialogBrowseFile.vue';
import previewFile from './previewFile.vue';
export default {
  props: [
    'name', //标志
    'title', // 标题
    'showUploadBtn', // 显示上传按钮,
    'showDelBtn',// 显示删除按钮
    'delBtnFlag', // 用户类型
    'files',
    'flush', // 直接的(用于直接上传文件,不参与表单录入)
    'type', // 判断类型
    'id' // 主键id
  ],
  components:{
    "previewFile":previewFile
    // "dialog-browse-file":dialogBrowseFile
  },
  data() {
    return {
      currentFiles: this.files,
      currentDelBtnFlag: this.delBtnFlag,
      fileList: [], // 上传文件表单(用于直接上传文件)
      file: '' // 待浏览文件
    };
  },
  watch: {
    delBtnFlag: {
      handler(newName, oldVal) {
        let vm = this;
        vm.currentDelBtnFlag = vm.delBtnFlag;
      },
      immediate: true
    },
    files: {
      handler() {
        let vm = this;
        if (!vm.files) return;
        vm.currentFiles = vm.tool.deepCopy(vm.files, vm.tool.deepCopy);
        console.log("files",vm.currentFiles)
      },
      immediate: true,
      deep:true
    }
  },
  methods: {
    uploadFile() {
      /* 上传文件 */
      let vm = this;
      // 是否直接上传文件
      if (vm.flush) {
        // 直接上传文件(不参与表单录入)
       let elFile = vm.$refs['file'];
       elFile.click();
       elFile.value = '';
      } else {
        // 上传文件(参与表单录入)
        this.$emit('uploadFile', vm.fileList);
      }
      return false;
    },
    del(data) {
      // 删除文件
        this.MessageBox.confirm('', {
          message: '确定执行删除操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.$emit('del', data);
          })
          .catch(err => {});
    },
    download(fileUrl, fileName, isGeneratedFile) {
      // 下载文件
      this.downloadFile(fileUrl, fileName, isGeneratedFile);
    },
    /**
     *
     * @event 选择文件改变
     */
    change(e) {
      let vm = this;
      vm.fileList = e.target.files;
      vm.$emit('uploadFile', vm.fileList);
    },
    /**
     * @method 浏览文件
     */
    browseFile(file) {
      console.log("file",file)
      let vm = this;
      vm.file = file;
      vm.$refs.dialogBrowseFile.show = true;
    }
  }
};
</script>
<style lang="scss">
.collapseUpAndDownload {
}
</style>
<style lang="scss" scoped>
.el-upload-list__item-name {
  margin: 0;
  display: inline-block;
  vertical-align: middle;
}

.file-list {
  .file-item {
    position: relative;

    .iconxiazai-,
    .iconshanchu {
      position: absolute;
      top: 12px;
      font-size: 26px;
    }
    .iconxiazai- {
      right: 70px;
      color: #000;
    }
    .iconshanchu {
      right: 10px;
    }
  }
}
</style>
