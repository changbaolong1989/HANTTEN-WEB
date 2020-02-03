<template>
  <div class="foundItem__contract--right-default">
    <el-collapse v-model="activeNames" accordion style="margin: 0;">

      <!-- 面板-上传下载(合同) -->
      <collapse-up-and-download res="contractTemplate" name="download1" title="合同" :multiple="true" :limit="1"
        :showUploadBtn="false" :files="contractList" @uploadFile="uploadFile" v-if="rowInfoLevel==3&&loadChild&&contractList.length>0"></collapse-up-and-download>

      <!--审核意见-->
      <template v-for="(item,i) in opinionList">
        <collapse-review-read :name="item.taskKey" :object="item" :key="i"></collapse-review-read>
      </template>

      <el-collapse-item title="法务意见" name="99" v-if="!!legalOpinion">
        <div class="content" style="width: 100%;">
          <textarea v-model="legalOpinion" :disabled="true" placeholder="请输入法务意见" style="width: 100%;background-color: #f2f3f4;border:none!important;"></textarea>
        </div>
      </el-collapse-item>

      <!-- 面板-复核可写-->
      <collapse-review-write name="write1" :object="object" v-if='typeFlag==2||typeFlag==3||typeFlag==4||typeFlag==5'
        @selecError="selecError"></collapse-review-write>

      <!-- 面板-上传下载(归档) -->
      <collapse-up-and-download res="contractTemplate" name="download2" title="上传归档附件" :multiple="true" :limit="1"
        :showUploadBtn="typeFlag == 7" :files="archiveList" @uploadFile="uploadFileArchive" :flush="true" v-if="rowInfoLevel==3&&loadChild&&contractList.length>0"></collapse-up-and-download>
    </el-collapse>

    <form class="projectForm">
      <div class="form-item" v-if='typeFlag==7' style="margin-top: 15px;">
        <label>档案编号</label>
        <input type="text" placeholder="请输入档案编号" v-model="fileNumber" />
      </div>
    </form>

    <div class="btn-group primary">
      <p>
        <!-- <mt-switch v-model="above" v-if="jumpFlag&&(typeFlag==1||typeFlag==2||typeFlag==3||typeFlag==4)" @change="changeAbove">
          是否跳级审核
        </mt-switch> -->
        <mt-switch v-model="above" v-if="(this.jumpFlag
                                          &&(typeFlag==1||typeFlag==2||typeFlag==3||typeFlag==4)
                                          &&(this.currentTransmitData.data.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'
                                          || this.currentTransmitData.data.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'))
                                          ||(this.jumpFlag&&(typeFlag==1||typeFlag==2)
                                          &&(this.currentTransmitData.data.departmentId == 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3'))" @change="changeAbove">
          是否跳级审核
        </mt-switch>
      </p>
      <button class="linear" @click="present(e)" v-if="typeFlag==1">提交审核</button>
      <button class="linear" v-if='typeFlag==20' @click="revokeHTContract">撤回</button>
      <button class="linear" v-if='typeFlag==2||typeFlag==3||typeFlag==4' @click="reviewContract('1')">通过</button>
      <button class="linear" v-if='typeFlag==5' @click="reviewContract('1')">签发</button>
      <button class="linear" v-if='typeFlag==2||typeFlag==3||typeFlag==4||typeFlag==5' @click="reviewContract('0')">驳回</button>
      <button class="linear" v-if='typeFlag==6' @click="sealButton">确认盖章</button>
      <button class="linear" v-if='typeFlag==7' @click="submitArchiveButton">提交归档</button>
      <button class="linear" v-if='typeFlag==1' @click="present(e,true)">预览</button>
    </div>
    <!-- 可写组件 - 对话框 -->
    <collapse-review-write-dialog ref="collapseReviewWrite_dialog" :errorTyleList="errorTyleList" :obj="selectErrorObj"
      @submit="selectErrorSubmit"></collapse-review-write-dialog>
    <!-- 面板-上传下载-表单 -->
    <dialog-upload ref="dialogUpload" :options="options" :uploadOptions="uploadOptions" :files="contractList" @submit="uploadSubmit"></dialog-upload>
  </div>
</template>

<script>
  import collapseReviewWrite from '../../../../more/common/components/collapseReviewWrite.vue'; // 面板-可写
  import collapseReviewWrite_dialog from '../../../../more/common/components/collapseReviewWrite_dialog.vue'; // 面板-可写-选错
  import collapseReviewRead from '../../../../more/common/components/collapseReviewRead.vue'; // 面板-只读
  import collapseUpAndDownload from '../../../../more/common/components/collapseUpAndDownload.vue'; // 面板-上传下载
  import dialogUpload from '../../../../more/common/components/dialogUpload.vue'; // 面板-上传下载-表单

  import {
    Toast
  } from 'mint-ui';
  export default {
    components: {
      'collapse-review-write': collapseReviewWrite, // 面板-可写
      'collapse-review-write-dialog': collapseReviewWrite_dialog, // 面板-可写-选错
      'collapse-review-read': collapseReviewRead, // 面板-只读
      'collapse-up-and-download': collapseUpAndDownload, // 面板-上传下载
      'dialog-upload': dialogUpload // 面板-上传下载-表单
    },
    props: ['transmitData', 'typeFlag'],
    data() {
      return {
        fileNumber: '', // 档案编号
        // 面案-上传下载-表单配置
        uploadOptions: {
          isInvoice: false // 是否是发票
        },
        // 选择文件类型
        options: [{
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
          }
        ],
        rowInfoLevel: '', //节点等级
        readObject: { // 一级面板的对象(只读)
          title: '一级复核', // 一级面板的标题
          children: [{
            name: '张佳琪', // 二级面板的审批人
            time: '2015-12-12', // 二级面板的时间
            isPass: 0, // 通过1,未通过0
            opinion: '这里是输入后的审核意见，这里是输入后的审核意见，这里是输入后的审核意见。', // 二级面板的意见
            persions: [ // 二级面板的被审批人数组
              {
                name: '张三丰', // 二级面板的被审批人名字
                errorList: []// 二级面板的被审批人的错误数组
              }
            ]
          }]
        },
        selectErrorObj: null, // 选择错误的对象
        activeNames: 'write1', // 默认激活的折叠面板
        currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy),
        above: false, //是否跳级(绑定的表单值)
        jumpFlag: true, //是否跳级(表单的显示隐藏)
        nextTaskKey: '', //流程下一节点
        contractId: "", // 合同编号
        delegateTaskKey: null, // 代理任务key
        opinionList: [], // 选项列表
        contractList: [], // 合同列表
        archiveList:[],// 归档列表
        errorTyleList: [], // 错误类型列表
        object: { // 一级面板的对象
          title: '', // 一级面板的标题
          opinion: '', // 一级级面板的意见
          children: [{
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: []// 二级面板的被审批人的错误数组
          }]
        },
        legalOpinion: '', //法务意见
        verifyState: null, // 验证状态
        isCanRecall: null, // 是否可以召回
        taskNum: '', //流程节点序号
        reviewer: { // 审核人
          current: '', // 当前
          next: '' // 下一个
        },
        auditOpinionList: [], // 审计意见清单
        loadChild: true ,// 加载子元素
        updateDate:''
      };
    },
    watch: {
      transmitData: {
        handler() {
          let vm = this;
          vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
        },
        immediate: true
      },
      delegateTaskKey() {
        this.jumpFlagUpdate();
      },
      verifyState() {
        this.jumpFlagUpdate();
      }
    },
    mounted() {
      let vm = this;
      vm.searchAuditOpinionInfo();
      vm.bus.$on('updateDateContract',function(val){
        vm.updateDate = val.updateDate;
      });
    },
    methods: {
      /**
       * @method 跳级审核提示
       */
      changeAbove(){
        let vm = this;
        if(vm.above){
          vm.MessageBox.confirm('', {
            message: '审核本来要提交给' + vm.reviewer.current + ',现在将要跳级提交给' + vm.reviewer.next,
            title: '提示',
            showCancelButton: true
          }).then(action => {
              if (action == 'confirm') {
                vm.above = true;
              }
            })
            .catch(err => {
              if (err == 'cancel') {
                vm.above = false;
              }
            });
        }
      },
      /**
       * @method 查询归档文件列表
       */
      searchContractArchiveFileInfo() {
        let vm = this;
        let formData = {
          contractId: vm.currentTransmitData.data.id
        };
        vm.requestPost('/contract/query/searchContractArchiveFileList', formData).then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
            }
            // 设置转换之后的list
            this.archiveList = dataList;
            vm.toggleTab();
          }
        }).catch(err => {
          this.Toast(err);
        });
      },
      /**
       * @event 归档的上传文件
       */
      uploadFileArchive(files) {
        let vm = this;
        let form = new FormData();
        vm.archiveList = files;
        form.append('file', vm.archiveList[0]);
        form.append('updateDate', vm.updateDate);
        form.append('contractId',vm.currentTransmitData.data.id);
        // 提交文件请求
        this.requestPost('/contract/add/insertContractArchiveFileInfo', form).then(res => {
          this.Toast(res.data.message);
          if (res.data.status == "success") vm.searchContractArchiveFileInfo(); // 查询归档文件列表
        }).catch(err => {
          this.Toast(err);
        })
      },
      /**
       * @method 提交归档
       */
      submitArchiveButton() {
        let vm = this;
        if (null == vm.archiveList || vm.archiveList.length == 0) {
          return this.Toast('请上传归档附件');
        }
        let formData = {
          contractId: vm.currentTransmitData.data.id,
          fileNumber: vm.fileNumber,
          updateDate:vm.updateDate
        };
        // 走提交归档接口
        this.requestPost('/contract/modify/submitArchiveHTContract', formData).then(res => {
          this.Toast(res.data.message);
          if (res.data.status != 'failure') history.go(-1);
        }).catch(err => {
          this.Toast(err);
        })
      },
      /**
       * @method 确认盖章
       */
      sealButton() {
        let vm = this;
        let formData = {
          contractId: vm.currentTransmitData.data.id,
          updateDate:vm.updateDate
        };
        // 走盖章接口
        this.requestPost('/contract/modify/confirmSealHTContract', formData).then(res => {
          this.Toast(res.data.message);
          if (res.data.status != 'failure') history.go(-1);
        }).catch(err => {
          this.Toast(err);
        })
      },
      /**
       * @method 上传文件提交
       * @param {Object} data
       */
      uploadSubmit(data) {
        let vm = this;
        let form = data.get('form');
        vm.invoiceFileList.push(form.file);
      },
      /* 上传组件相关 */
      uploadFile() { // 上传文件对话框显示
        let vm = this;
        this.$nextTick(function() {
          vm.$refs['dialogUpload'].show = true;
        })
      },
      /**
       * @method 审核
       * @param {Object} isPass 是否通过
       */
      reviewContract(isPass) {
        this.bus.$emit('reviewContract', isPass, this.tool.deepCopy(this.object, this.tool.deepCopy));
      },
      /**
       * @method 撤回
       */
      revokeHTContract() {
        let vm = this;
        let formData = {
          contractId: vm.currentTransmitData.data.id,
          updateDate:vm.updateDate
        };
        this.requestPost('/contract/revoke/revokeHTContract', formData).then(res => {
          this.Toast(res.data.message);
          if (res.data.status != 'failure')history.go(0);
        }).catch(err => {
          this.Toast(err);
        })
      },
      /* 可写组件--相关 */
      selecError(data) { // 选择错误
        let vm = this;
        // 先赋值再显示对话框
        vm.selectErrorObj = data;
        vm.$refs['collapseReviewWrite_dialog'].show = true;
      },
      selectErrorSubmit(data) {
        let vm = this;
        // 将选择好的错误,赋值给选择错误的对象(selectErrorObj)
        vm.selectErrorObj.errorList = data;
      },
      /**
       * @method 是否跳级视图更新
       *
       */
      jumpFlagUpdate() {
        let vm = this;
        //跳级显示flag
        vm.jumpFlag = vm.delegateTaskKey == 'QualityVerify'|| vm.delegateTaskKey == 'ManagerVerify'||(vm.delegateTaskKey == 'SubmitVerify'  && vm.approveTime == null);
      },
      /**
       * @method 搜索审计意见信息
       */
      searchAuditOpinionInfo() {
        let vm = this;
        vm.rowInfoLevel = vm.currentTransmitData.data.treeNodeLevel;
        // 合同层级时 查询审核意见
        if (vm.currentTransmitData.data.treeNodeLevel == 3) {
          // 搜索审计意见列表
          vm.searchAuditOpinionListFunc();
          //查询问题类型列表信息
          vm.searchQuestionList();
          /*查询合同信息*/
          vm.searchContractInfo();
          /*查询合同归档文件信息*/
          vm.searchContractArchiveFileInfo();
        }
      },
      /**
       * @method 搜索审计意见列表
       */
      searchAuditOpinionListFunc() {
        let vm = this;
        let formData = {
          contractTaskId: vm.currentTransmitData.data.id,
        };
        //查询审核意见列表
        vm.requestPost('/contract/query/searchHTContractTaskHisTaskInst', formData).then(
          res => {
            if (res.data.status == 'failure') {
              vm.Toast({
                message: res.data.message,
                position: 'middle',
                className: 'toast',
                duration: 2000
              });
            } else {
              /*------------------------------格式转换START---------------------------------*/
              let dataList = res.data.dataList;
              //for循环遍历更改属性名
              for (var i = 0; i < dataList.length; i++) {
                dataList[i].title = vm.getStepName(dataList[i].taskKey);
                //将属性名httenderTaskHisTaskInstVOList转换成属性名children
                dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htcontractTaskHisTaskInstVOList/g,
                  "children"));
                let children = dataList[i].children;
                for (var j = 0; j < children.length; j++) {
                  //将属性名approveTime转换成属性名time
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, "time"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, "name"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,
                    "persions"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, "opinion"));
                  let persions = vm.groupBy(children[j].persions, function(item) {
                    return [item.userId]
                  })
                  for (var k = 0; k < persions.length; k++) {
                    persions[k].name = persions[k].dataList[0].userName;
                    persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, "errorList"));
                    let errorList = persions[k].errorList;
                    for (var l = errorList.length - 1; l >= 0; l--) {
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, "type"));
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, "num"));
                    }
                    persions[k].errorList = errorList;
                  }
                  children[j].persions = persions;
                }
              }
              /*---------------------------------格式转换END-------------------------------------*/
              vm.opinionList = dataList
            }
          }).catch(err => {
          vm.Toast({
            message: err,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        });
      },
      /**
       * @methods 查询问题类型列表信息
       */
      searchQuestionList() {
        let vm = this;
        let formData = {
          departmentId: vm.currentTransmitData.data.departmentId
        }
        vm.requestPost('/tenderTask/query/searchQuestionList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.Toast({
              message: res.data.message,
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          } else {
            let questionList = res.data.dataList;
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            vm.errorTyleList = questionList;
          }
        }).catch(err => {
          vm.Toast({
            message: err,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        })
      },
      /**
       *  @method 查询合同信息
       */
      searchContractInfo() {
        let vm = this;
        let formData = {
          contractId: vm.currentTransmitData.data.id
        };
        vm.requestPost('/contract/query/searchContractInfoById', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.Toast({
              message: res.data.message,
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          } else {
            let tempContractInfo = res.data.dataVO;
            let userList = tempContractInfo.userList;

            for (let i = userList.length - 1; i >= 0; i--) {
              userList[i].errorList = [];
            }

            vm.object.title = vm.getStepName(tempContractInfo.delegateTaskKey);
            vm.object.delegateTaskKey = tempContractInfo.delegateTaskKey;
            vm.object.nextTaskKey = tempContractInfo.submitNode;
            vm.nextTaskKey = tempContractInfo.submitNode;
            vm.delegateTaskKey = tempContractInfo.delegateTaskKey;
            vm.legalOpinion = tempContractInfo.legalOpinion; //法务意见
            vm.verifyState = tempContractInfo.verifyState;
            vm.isCanRecall = tempContractInfo.isCanRecall;
            vm.tasknum = vm.getStepNum(tempContractInfo.delegateTaskKey);
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));
            for (let i = userList.length - 1; i >= 0; i--) {
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, "id"));
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, "name"));
            }
            vm.contractId = tempContractInfo.contractId;
            vm.object.children = userList;

            // 合同路径
            let contractFilePath = tempContractInfo.contractFilePath;

            // 合同类型
            let contractType = tempContractInfo.contractType;


            // 是否调用合同模板生成接口 默认走正常的下载接口
            let isGeneratedFile = false;

            // 如果合同类型为模板合同
            if (contractType == '1') {

              // 合同路径 后面拼接合同id和项目编号
              contractFilePath = contractFilePath + '?contractId=' + tempContractInfo.contractId + '&projectNum=' +
                tempContractInfo.projectNum;

              // 调用同模板生成接口 生成合同模板
              isGeneratedFile = true;
            }
            if (tempContractInfo.contractFileName != null && tempContractInfo.contractFileName != "") {
              vm.contractList[0] = {
                "materialName": tempContractInfo.contractFileName,
                "materialPath": contractFilePath,
                "isGeneratedFile": isGeneratedFile
              };
            }


            if (tempContractInfo.reviewFileName != null && tempContractInfo.reviewFileName != "") {

              vm.auditOpinionList[0] = {
                // "materialName": tempContractInfo.reviewFileName,
                // "materialPath": tempContractInfo.reviewFilePath
              };
            }
            vm.toggleTab();
          }
        }).catch(err => {
          vm.Toast({
            message: err,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        })
      },
      toggleTab() {
        let vm = this;
        vm.loadChild = false;
        vm.$nextTick(() => {
          vm.loadChild = true;
        })
      },
      /**
       * @method 提交
       *@param {Boolean} boo
       */
      present(e,boo) {
        this.bus.$emit("changeform", this._data,!!boo);
      }
    }
  }
</script>

<style lang="scss" scoped>
  .foundItem__contract--right-default {
    *{
      box-sizing: border-box;
    }
    .primary {
      padding-bottom: 20px;
    }
  }
</style>
