<template>
  <div>
    <!-- 不跨域路径 -->
    <!--return '../../../../../../PC/preview/pdf/web/viewer.html?file=/preview/fileViewer';-->
    <!-- 跨域路径 -->
    <!--return '../../../../../../static/preview/pdf/web/viewer.html?file=http://127.0.0.1:18866/preview/fileViewer'; -->
    <p>&nbsp;</p>
    <el-button @click="preview('pdf')" type="primary" style="margin-left: 16px;">
      预览PDF
    </el-button>
    <el-button @click="preview('word')" type="primary" style="margin-left: 16px;">
      预览WORD
    </el-button>
    <el-button @click="preview('txt')" type="primary" style="margin-left: 16px;">
      预览TXT
    </el-button>
    <el-button @click="preview('zip')" type="primary" style="margin-left: 16px;">
      预览ZIP
    </el-button>
    <el-button @click="preview('excel')" type="primary" style="margin-left: 16px;">
      预览EXCEL
    </el-button>
    <el-drawer :visible.sync="drawer" direction="ttb" height="100%" style="background: #f2f8fe">
      <el-tree v-if="fileTreeData" :data="fileTreeData" :props="defaultProps"></el-tree>
      <iframe v-else :src="reviewHref" width="99%" height="900"></iframe>
    </el-drawer>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        drawer: false,
        zipDrawer: false,
        reviewHref: '',
        prefixViewer: '',
        prefixPath: '',
        openOfficeServerIp: '',
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        fileTreeData: null,
        pdfPath: '/pdf/compressed.pdf',
        wordPath: '/word/word_test.docx',
        txtPath: '/txt/logfile.txt',
        zipPath: '/zip/apache-maven-3.6.1-bin.zip',
        excelPath: '/excel/20190903.xls',
/*                pdfPath: '/home/changbaolong/hantten/preview/pdf/compressed.pdf',
                wordPath: '/home/changbaolong/hantten/preview/word/word_test.docx',
                txtPath: '/home/changbaolong/hantten/preview/txt/logfile.txt',
                zipPath: '/home/changbaolong/hantten/preview/zip/apache-maven-3.6.1-bin.zip',
                excelPath: '/home/changbaolong/hantten/preview/excel/20190903.xls',*/
      }
    },
    computed: {
      fileType: {
        set: function (val) {
          let url;
          switch (val) {
            case 'pdf':
              url = this.openOfficeServerIp + this.prefixPath + this.pdfPath;
              //url = 'http://192.168.133.128:18866/preview/fileViewer?filePath=' + this.pdfPath;
              this.reviewHref = this.prefixViewer + encodeURIComponent(url);
              break;
            case 'word':
              url = this.openOfficeServerIp + this.prefixPath + this.wordPath;
              //url = 'http://192.168.133.128:18866/preview/fileViewer?filePath=' + this.wordPath;
              this.reviewHref = this.prefixViewer + encodeURIComponent(url);
              break;
            case 'txt':
              this.reviewHref = this.openOfficeServerIp + this.prefixPath + this.txtPath;
              //this.reviewHref = 'http://192.168.133.128:18866/preview/fileViewer?filePath=' + this.txtPath;
              break;
            case 'zip':
              const vm = this;
              vm.$axios.post('/preview/zipViewer?filePath=' + this.prefixPath + vm.zipPath, vm.config).then(function (response) {
                if (response.data.status === "success") {
                  vm.fileTreeData = JSON.parse(response.data.message);
                } else {
                  vm.$message({
                    type: 'error',
                    message: response.data.message
                  });
                }
              });
              break;
            case 'excel':
              alert('正在研发中……');
              break;
          }
        },
        get: function (val) {
          alert(JSON.stringify(val));
        }
      }
    },
    methods: {
      preview(type) {
        const vm = this;
        vm.fileTreeData = null;
        vm.$axios.post('/preview/checkFile?filePath=' + vm.pdfPath, vm.config).then(function (response) {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status === "success") {
            vm.prefixViewer = response.data.prefixViewer;
            vm.openOfficeServerIp = response.data.openOfficeServerIp;
            vm.prefixPath = response.data.prefixPath;
            vm.fileType = type;
            vm.drawer = true;
          } else {
            vm.$message({
              type: 'error',
              message: response.data.message
            });
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
