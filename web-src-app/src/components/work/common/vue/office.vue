<template>
  <div class="preview-container">
    <div ref="preview-div-container" style="height: 100%;" v-if="showOffice">
      <div id="preview-div" class="preview-div" v-if="showOffice"></div>
    </div>
    <div v-show="!showOffice" style="text-align: center;margin: 20px;font-size: 12px;font-weight: 800;">上传的自定义文件格式不支持，系统只支持扩展名为.doc、.docx的文件
</div>
  </div>
</template>

<script>
  export default {
    props: ['file'],
    data() {
      return {
        currentFile: this.file,
        showOffice: true
      }
    },
    watch: {
      file: {
        handler(newval) {
          let vm = this;
          vm.currentFile = newval;
          console.log("currentFile",vm.currentFile)
          vm.getOffice();
        },
        immediate: true
      },
      showOffice: {
        handler() {
          let vm = this;
          // 触发onlyOffice文件支持事件(是否支持显示onlyOffice文件)
          vm.$emit('supportOffice',vm.showOffice);
        },
        immediate: true
      },
    },
    mounted() {
      let vm = this;
      vm.getOffice();
      console.log("file",this.file)
    },
    methods: {
      getOffice() {
        let vm = this;
        if(!vm.currentFile)return;
        // 文件名称
        let fileName = vm.currentFile.fileName;
        if(!fileName)return;
        // 文件类型
        let fileArr = fileName.split('.');
        let fileType = fileArr[fileArr.length - 1];
        fileArr.pop();
        // 文件title
        let title = fileArr.toString();
        // 文件key
        let key = vm.currentFile.key;
        // 文件的下载路径
        let downloadUrl = vm.currentFile.downloadUrl;
        console.log('fileName',fileName);
        console.log('downloadUrl',downloadUrl);
        // 文件的保存路径
        let saveUrl = vm.currentFile.saveUrl;
        let patt = /(doc|docx)/i;
        vm.showOffice = patt.test(fileType);
        if (!vm.showOffice) {
          return
        }else{
           this.$nextTick(function () {
              vm.$refs['preview-div-container'].innerHTML = '<div id="preview-div" class="preview-div" v-if="showOffice"></div>';
              new DocsAPI.DocEditor('preview-div', {
                "document": {
                  "fileType": fileType, // 获取 文件类型
                  "key": key,
                  "title": title, // 获取- 标题
                  // todo 文件获取路径
                  //"url": "http://192.168.1.19:8866/test-office/detail/" + $("#officeFileId").val() + "/getFile",
                  "url": downloadUrl
                },
                "type": "desktop",
                "documentType": vm.getDocumentTypeByExpandedName(fileType),
                "editorConfig": {
                  "callbackUrl": saveUrl, // 保存文件路径
                  "lang": "zh_CN",
                  "mode": "edit",
                  "customization": {
                    "autosave": true,
                    "chat": false,
                    "commentAuthorOnly": false,
                    "comments": true,
                    "compactHeader": true,
                    "compactToolbar": true,
                    "feedback": false,
                    "forcesave": false,
                    "goback": false,
                    "help": false,
                    "hideRightMenu": false,
                    "showReviewChanges": false,
                    "toolbarNoTabs": false,
                    "zoom": 100,
                    "hideRightMenu":true
                  }
                }
              });
           })
        }
      },
      getDocumentTypeByExpandedName(paramExpandedNameStr) {
        var resultDocType;
        var tmpTextTypeArr = ["doc","docx"];
        var tmpSpreadSheetTypeArr = ["xls","xlsx"];
        var tmpPptTypeArr = ["ppt", "pptx"];
        // 如果是 Word
        if (tmpTextTypeArr.indexOf(paramExpandedNameStr) > 0) {
          resultDocType = "text";
        } else if (tmpSpreadSheetTypeArr.indexOf(paramExpandedNameStr) > 0) {
          resultDocType = "spreadsheet";
        } else if (tmpPptTypeArr.indexOf(paramExpandedNameStr) > 0) {
          resultDocType = "presentation";
        }
        return resultDocType;
      }
    }
  }
</script>
<style lang="scss">
  .preview-container {
    height: 100%;
  }
</style>
