<template>
  <!-- 审核意见 -->
  <div class="auditOpinion more-sublevel">
    <el-collapse style="border-radius:5px;">
      <!-- 只读组件 -->
      <collapse-review-read name="1" :object="readObject"></collapse-review-read>
      <!-- 可写组件 -->
      <collapse-review-write name="2" :object="writeObject" @selecError="selecError"></collapse-review-write>
      <!-- 上传下载组件 -->
      <collapse-up-and-download
        name="3"
        title="上传下载"
        :showUploadBtn="true"
        :multiple="true"
        :limit="1"
        :files="files"
        @uploadFile="uploadFile"></collapse-up-and-download>
    </el-collapse>
    <!-- 可写组件 - 对话框 -->
    <collapse-review-write-dialog ref="collapseReviewWrite_dialog" :errorTyleList="errorTyleList" :obj="selectErrorObj" @submit="selectErrorSubmit"></collapse-review-write-dialog>
    <!-- 上传下载组件 - 对话框 -->
    <dialog-upload
    ref="dialogUpload"
    :options="options"
    :uploadOptions="uploadOptions"
    :files="files"
    @submit="uploadSubmit"></dialog-upload>
  </div>
</template>

<script>
  /* 只读组件 */
  import collapseReviewRead from '../common/components/collapseReviewRead.vue'
  /* 可写组件 */
  import collapseReviewWrite from '../common/components/collapseReviewWrite.vue'
  /* 可写组件 - 对话框 */
  import collapseReviewWrite_dialog from '../common/components/collapseReviewWrite_dialog'
  /* 上传下载组件 */
  import collapseUpAndDownload from '../common/components/collapseUpAndDownload.vue'
  /* 上传下载组件 - 对话框 */
  import dialogUpload from '../common/components/dialogUpload.vue';

  export default {
    components: {
      'collapse-review-read': collapseReviewRead,
      'collapse-review-write': collapseReviewWrite,
      'collapse-review-write-dialog':collapseReviewWrite_dialog,
      'collapse-up-and-download':collapseUpAndDownload,
      'dialog-upload': dialogUpload
    },
    data() {
      return {
        options:[{
            label: '造价咨询',
            id: '0'
          },
          {
            label: '招标代理',
            id: '1'
          },
          {
            label: '项目管理',
            id: '2'
          }],
        uploadOptions:{
          isInvoice:false// 是否是发票
        },
        activeNames: ['1'],
        readObject: { // 一级面板的对象
          title: '一级复核', // 一级面板的标题
          children: [{
            name: '张佳琪', // 二级面板的审批人
            time: '2015-12-12', // 二级面板的时间
            isPass: 0, // 通过1,未通过0
            opinion: '这里是输入后的审核意见，这里是输入后的审核意见，这里是输入后的审核意见。', // 二级面板的意见
            persions: [ // 二级面板的被审批人数组
              {
                name: '张三丰', // 二级面板的被审批人名字
                errorList: [ // 二级面板的被审批人的错误数组

                ]
              }
            ]
          }]
        },
        writeObject: {
          // 一级面板的对象
          title: '二级复核', // 一级面板的标题
          opinion: '这里是输入后的审核意见，这里是输入后的审核意见，这里是输入后的审核意见。', // 二级面板的意见
          children: [ // 二级面板的被审批人数组
            {
              name: '张三丰', // 二级面板的被审批人名字
              errorList: [ // 二级面板的被审批人的错误数组

              ]
            },{
              name: '小白龙', // 二级面板的被审批人名字
              errorList: [ // 二级面板的被审批人的错误数组

              ]
            }
          ]
        },
        errorTyleList: [
          {
            questionName:'错别字',
            questionNum:0
          },{
            questionName:'加点错误',
            questionNum:0
          },{
            questionName:'技能错误',
            questionNum:0
          }
        ],
        selectErrorObj:null,// 选择错误的对象
        files:[]// 上传文件列表
      };
    },
    methods: {
      /* 可写组件--相关 */
      selecError(data){// 选择错误
        let vm = this;
        // 先赋值再显示对话框
         vm.selectErrorObj = data;
        vm.$refs['collapseReviewWrite_dialog'].show = true;
      },
      selectErrorSubmit(data){
        let vm = this;
         vm.selectErrorObj.errorList = data;
        // 选择错误的表单提交
        console.log('selectErrorSubmit',data);
      },
      /* 上传组件相关 */
      uploadFile(){// 上传文件对话框显示
         let vm = this;
        this.$nextTick(function () {
          vm.$refs['dialogUpload'].show = true;
        })
      },
      uploadSubmit(data){// 上传文件提交
        let vm = this;
        let form = data.get('form');
        vm.invoiceFileList.push(form.file);
      }
    }
  };
</script>
<style>
  @import url('../common/css/fileList.css');
  @import url('../common/css/foldedPanel.css');
</style>
<style lang="scss" scoped>
  .auditOpinion {
    overflow: auto;
    box-sizing: border-box;
    background-color: #edf1f4;
    height: 100vh;
    width: 100vw;
    padding: 30px;
  }
</style>
