<template>
  <!-- 浏览文件 -->
  <div class="dialog__browse-file" @click.stop="(()=>{})">
    <!-- 弹窗 -->
    <el-dialog :visible="show2" width="100%" height="100%" center :before-close="beforeClose" :modal-append-to-body="true"
      @open="open" :class="{'zindex1':zindex1,'zindex2':zindex2}" :close-on-click-modal="false">
      <div v-if=" 1 == fileType" style="text-align: center;">
        <img preview="0" :src="srcReviewHref" alt="" />
      </div>
      <div v-else style="text-align: center;">
        <iframe :src="reviewHref" width="99%" height="900"></iframe>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    props: ['file','type','id','formData'],
    mounted() {
    },
    data() {
      return {
        zindex1: true, // ie兼容调整
        zindex2: false, // ie兼容调整
        show: false,// 对话框
        show2:false,// 对话框（正式）
        reviewHref: "",// 文档文件展示的地址
        srcReviewHref:"",// 图片文件展示的地址
        fileType:1,// 浏览文件类型
        path:"",// 访问文件路径
      };
    },
    watch:{
      show:{
        handler(){
          let vm = this;
          // 获取文件路径
          let materialPath = vm.file.materialPath;

          // 模板
          if(vm.file.isGeneratedFile){
            if(vm.show){
              vm.show = false;
              // 类型为文件
              vm.fileType = 2;
              // 如果是未提审之前的预览
              if(vm.file.noSubmitPreview){
                // 模板验证文件
                vm.noSubmitPreview();
                // 提审之后的预览
              } else {
                // 模板验证文件
                vm.byFileStream();
              }

            }
            // 自定义
          }else{
            // 如果文件不为空
            if(vm.show&&vm.file&&materialPath){
              vm.show = false;
              // 获取文件路径的后缀名，判断是那种类型的文件
              let suffix = materialPath.substring(materialPath.lastIndexOf("."),materialPath.length);
              // 如果是图片文件
              if(".jpg" == suffix || ".jpeg" == suffix || ".png" == suffix || ".JPG" == suffix || ".JPEG" == suffix || ".PNG" == suffix){
                // 类型为图片
                vm.fileType = 1;
                // 自定义验证文件
                vm.preview(materialPath);
                // 如果是文档文件
              } else if(".doc" == suffix || ".docx" == suffix || ".pdf" == suffix || ".DOC" == suffix || ".DOCX" == suffix || ".PDF" == suffix) {
                // 类型为文件
                vm.fileType = 2;
                // 自定义验证文件
                vm.preview(materialPath);
              } else {
                vm.stateDialog('error', "只能支持.pdf、.docx、.doc、.png、.jpg、.jpeg、.JPG、.JPEG、.PNG、.DOC、.DOCX、.PDF格式")
              }

            }
          }

        },
        immediate: true
      }
    },
    methods: {
      noSubmitPreview(){
        let vm = this;
        vm.apiAddress('/preview/checkFile/byFileStreamNoSubmitPreview', vm.formData).then(response => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status === "success") {
            // 前缀
            let prefixViewer = response.data.prefixViewer;
            // 服务器地址
            let openOfficeServerIp = response.data.openOfficeServerIp;
            // 获取文件地址前缀
            let prefixPath = response.data.prefixPath;
            // 获取文件地址前缀
            let filePath = response.data.filePath;
            filePath = filePath.replace(/\\/g,'/');
            // 文档文件展示的地址
            vm.reviewHref = prefixViewer + encodeURIComponent(openOfficeServerIp + filePath);
            // 显示弹窗
            vm.show2 = true;
          } else {
            vm.show = false;
            vm.stateDialog('error', response.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        })
      },
      // 模板验证文件
      byFileStream() {
        let vm = this;
        vm.$axios.post('/preview/checkFile/byFileStream', vm.config).then(function (response) {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status === "success") {
            // 前缀
            let prefixViewer = response.data.prefixViewer;
            // 服务器地址
            let openOfficeServerIp = response.data.openOfficeServerIp;
            // 展示的地址
            vm.reviewHref = prefixViewer + encodeURIComponent(openOfficeServerIp+"?type="+vm.type+"&id="+vm.id);
            // 显示弹窗
            vm.show2 = true;
          } else {
            vm.show = false;
            vm.stateDialog('error', response.data.message)
          }
        })
      },
      // 自定义验证文件
      preview(path) {
        let vm = this;
        vm.$axios.post('/preview/checkFile?filePath=' + path, vm.config).then(function (response) {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status === "success") {
            // 前缀
            let prefixViewer = response.data.prefixViewer;
            // 服务器地址
            let openOfficeServerIp = response.data.openOfficeServerIp;
            // 获取文件地址前缀
            let prefixPath = response.data.prefixPath;
            // 文档文件展示的地址
            vm.reviewHref = prefixViewer + encodeURIComponent(openOfficeServerIp + prefixPath + path);
            // 图片文件展示的地址
            vm.srcReviewHref = openOfficeServerIp + prefixPath + path;
            // 显示弹窗
            vm.show2 = true;
          } else {
            vm.show = false;
            vm.stateDialog('error', response.data.message)
          }
        })
      },
      beforeClose() { // 对话框关闭前回调
        let vm = this;
        vm.show = false;
        vm.show2 = false;
        vm.reviewHref = "";
        vm.srcReviewHref = "";
      },
      open() { // 打开回调
        let vm = this;
        /* 这个页面 IE浏览器下,对话框弹窗会再显示消失,动态更改el-dialog的z-index,可以显示对话框*/
        let count = 0;
        let interval = setInterval(function() {
          vm.zindex1 = !vm.zindex1;
          vm.zindex2 = !vm.zindex2;
          count++;
          if (100 == count) {
            clearInterval(interval);
          }
        }, 1000);
        vm.$previewRefresh();
      }
    }
  };
</script>
<style lang="scss">
  .dialog__browse-file{

    .el-dialog{
       background: rgba(0,0,0,0)!important;
       background-color: rgba(0,0,0,0)!important;
       margin:0!important;
       height:100vh;

      .el-dialog__header{
        padding-top:20px;
        .el-dialog__headerbtn{
          top: 0;
        }
      }
      .el-dialog__header,.el-dialog__body{
        background: rgba(0,0,0,0)!important;
        background-color: rgba(0,0,0,0)!important;
      }
      .el-dialog__body{
        padding: 0;
        height: calc(100% - 30px);
        overflow: auto;
      }
    }
  }
</style>
<style lang="scss" scoped>
</style>
