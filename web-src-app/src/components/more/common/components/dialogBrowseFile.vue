<template>
  <!-- 浏览文件 -->
  <div class="dialog__browse-file" @click.stop="() => {}">
    <!-- 弹窗 -->
    <mt-popup v-model="show2" popup-transition="popup-fade" position="bottom" modal="true">
      <i @click.stop="beforeClose" class="iconfont" style="float: right; margin-top: 8%;font-size: 14px; margin-right: 2%;color: #06A1EE;">关闭预览</i>
      <div v-if="1 == fileType" style="text-align: center;height: 100vh;">
        <img preview="0" :src="srcReviewHref" alt="" style="position: absolute;left: 0;right: 0;top: 0;bottom: 0;margin: auto;max-width:75%;" />
      </div>
      <div v-else style="text-align: center;height: 100vh;"><iframe :src="reviewHref" width="100%" height="82%"></iframe></div>
    </mt-popup>
  </div>
</template>

<script>
export default {
  props: ['file', 'type', 'id', 'formData'],
  data() {
    return {
      show: false, // 对话框
      show2: false, // 对话框（正式）
      reviewHref: '', // 文档文件展示的地址
      srcReviewHref: '', // 图片文件展示的地址
      fileType: 1, // 浏览文件类型
      path: '' // 访问文件路径
    };
  },
  watch: {
    show: {
      handler() {
        let vm = this;
        // 获取文件路径
        let materialPath = vm.file.materialPath;

        // 模板
        if (vm.file.isGeneratedFile) {
          if (vm.show) {
            vm.show = false;
            // 类型为文件
            vm.fileType = 2;
            // 如果是未提审之前的预览
            if (vm.file.noSubmitPreview) {
              // 模板验证文件
              vm.noSubmitPreview();
              // 提审之后的预览
            } else {
              // 模板验证文件
              vm.byFileStream();
            }
          }
          // 自定义
        } else {
          // 如果文件不为空
          if (vm.show && vm.file && materialPath) {
            vm.show = false;
            // 获取文件路径的后缀名，判断是那种类型的文件
            let suffix = materialPath.substring(materialPath.lastIndexOf('.'), materialPath.length);
            // 如果是图片文件
            if ('.jpg' == suffix || '.jpeg' == suffix || '.png' == suffix) {
              // 类型为图片
              vm.fileType = 1;
              // 自定义验证文件
              vm.preview(materialPath);
              // 如果是文档文件
            } else if ('.doc' == suffix || '.docx' == suffix || '.pdf' == suffix) {
              // 类型为文件
              vm.fileType = 2;
              // 自定义验证文件
              vm.preview(materialPath);
            } else {
              vm.Toast('只能支持.pdf、.docx、.doc、.png、.jpg、.jpeg格式');
            }
          }
        }
      },
      immediate: true
    }
  },
  methods: {
    noSubmitPreview() {
      let vm = this;
      vm.requestPost('/preview/checkFile/byFileStreamNoSubmitPreview?appType=App', vm.formData)
        .then(response => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status === 'success') {
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
            vm.Toast(response.data.message);
          }
        })
        .catch(e => {
          vm.Toast(e);
        });
    },
    // 模板验证文件
    byFileStream() {
      const vm = this;
      vm.requestPost('/preview/checkFile/byFileStream?appType=App').then(function(response) {
        // 如果返回成功，则绘制数据，否则提示失败信息
        if (response.data.status === 'success') {
          // 前缀
          let prefixViewer = response.data.prefixViewer;
          // 服务器地址
          let openOfficeServerIp = response.data.openOfficeServerIp;
          // 展示的地址
          vm.reviewHref = prefixViewer + encodeURIComponent(openOfficeServerIp + '?type=' + vm.type + '&id=' + vm.id);
          // 显示弹窗
          vm.show2 = true;
        } else {
          vm.show = false;
        }
      });
    },
    // 自定义验证文件
    preview(path) {
      const vm = this;
      vm.requestPost('/preview/checkFile?appType=App&filePath=' + path).then(function(response) {
        // 如果返回成功，则绘制数据，否则提示失败信息
        if (response.data.status === 'success') {
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
        }
      });
    },
    beforeClose() {
      // 对话框关闭前回调
      let vm = this;
      vm.show = false;
      vm.show2 = false;
      vm.reviewHref = '';
      vm.srcReviewHref = '';
    }
  }
};
</script>
<style lang="scss" scoped></style>
