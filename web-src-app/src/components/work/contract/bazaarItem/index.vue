<template>
  <!-- 市场项目详情页面 -->
  <div class="index">
    <transition name="slide-fade">
      <div class="contractAside" v-if="state">
        <div class="treeShow">
          <tree :dataVO="dataVO" :listarr="listarr" :list="list" :delegateTaskKey="delegateTaskKey" @rightShow="rightShow" @downloadFiles="downloadFiles"></tree>
          <i class="iconfont iconshouqi-" @click="state = false"></i>
        </div>
      </div>
    </transition>
    <transition name="slide-fade-right">
      <div class="createProject" v-if="slideRight"><create-project @colseRight="colseRight"></create-project></div>
    </transition>
    <transition name="slide-fade-right">
      <div class="createProject" v-if="winningRight">
        <form class="mint-popup popup-top">
          <div class="radiolist">
            <p>中标状态</p>
            <div class="item" v-for="(item, index) in winningState">
              <label :for="'radio' + index">
                {{ item.userName }}
                <input type="radio" :id="'radio' + index" v-model="winning" :value="item.userId" name="radio" @change="radioChange" />
                <span></span>
              </label>
            </div>
          </div>
        </form>
        <div class="commonFooter">
          <mt-button @click="rollback">取消</mt-button>
          <mt-button class="linear" form="projectForm" @click="winningForm">确定</mt-button>
        </div>
      </div>
    </transition>
    <div class="foundItemIndex">
      <ul class="contractList">
        <li @click="state = true">
          <span>全部项目-{{ list.tenderTaskName || list.label }}</span>
          <i class="iconfont">&#xe630;</i>
        </li>
      </ul>
      <div class="exhibition">
        <ul v-if="show">
          <li>
            <span>任务概况</span>
            <span>{{ list.tenderTaskContent }}</span>
          </li>
          <li>
            <span>开标时间</span>
            <span>{{ list.startDate }}</span>
          </li>
        </ul>
        <ul v-if="!show">
          <li>
            <span>工作步骤说明</span>
            <span>{{ jurisdiction.periodDesc }}</span>
          </li>
        </ul>
      </div>
	  <div v-if="showOnlyOfficeCompFlag&&periodType == 2 || periodType == 3" style="height: 700px;width: 100%; margin: auto;">
	    <office :file="tenderStageMaterialInfo" @supportOffice="supportOffice"></office>
	  </div>
      <div class="exhibition"><A3 v-if="Ashow" :contentList="contentList" :A3dataList="opinionList"></A3></div>
      <div class="exhibition more-sublevel" v-if="treeNodeLevel == 2">
        <el-collapse style="border-radius:5px;">
          <collapse-download
            :title="aside.label"
            :showUploadBtn="showUploadBtn"
            :delBtnFlag="typeFlag"
            :multiple="true"
            :files="files"
            @uploadFile="uploadFile"
            @del="deleteFileInfo"
          ></collapse-download>
          <collapse-download
            title="相关资料"
            :multiple="true"
            :files="reviewFileList"
            v-if="reviewFileList.length > 0 ? periodType == 2 || periodType == 3 : false"
          ></collapse-download>
          <collapse-download
            title="审核意见文件"
            :multiple="true"
            :files="auditFileList"
            v-if="auditFileList.length > 0 ? periodType == 2 || periodType == 3 : false"
          ></collapse-download>
          <div v-if="periodType == 2 || periodType == 3">
            <template v-for="(item, i) in opinionList">
              <collapse-review-read :object="item"></collapse-review-read>
            </template>
            <!-- 面板-复核可写-->
            <collapse-review-write ref="write" :object="object" v-if="typeFlag == 4 || typeFlag == 3 || typeFlag == 2" @selecError="selecError"></collapse-review-write>
            <div class="primary" v-if="typeFlag == 20 || typeFlag == 1 || typeFlag == 2">
              <mt-switch
                v-model="above"
                v-if="jumpFlag && (typeFlag == 1 || typeFlag == 2 || typeFlag == 3 || typeFlag == 4) && (periodType == 2 || periodType == 3)"
                @change="skipGrade"
              >
                是否跳级审核
              </mt-switch>
              <button class="linear" v-if="typeFlag == 1" @click="submitAudit">提交审核</button>
              <button class="linear" v-if="typeFlag == 20" @click="withdraw">撤回</button>
            </div>
            <div class="primary" v-if="typeFlag == 2 || typeFlag == 3 || typeFlag == 4">
              <button class="linear" @click="auditingHTTenderTask(1)">通过</button>
              <button class="linear" @click="auditingHTTenderTask(0)">驳回</button>
            </div>
          </div>
        </el-collapse>
        <!-- 上传下载组件 - 对话框 -->
        <dialog-upload
          ref="dialogUpload"
          :options="options"
          unique="fileTypeId"
          display="fileTypeName"
          :uploadOptions="uploadOptions"
          :files="files"
          @submit="uploadSubmit"
        ></dialog-upload>
        <!-- 提交审核-对话框 -->
        <submit-audit ref="submitAudit" @confirmSubmit="confirmSubmit"></submit-audit>
        <!-- 可写组件 - 对话框 -->
        <collapse-review-write-dialog
          ref="collapseReviewWrite_dialog"
          :errorTyleList="errorTyleList"
          :obj="selectErrorObj"
          @submit="selectErrorSubmit"
        ></collapse-review-write-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import A3 from '../common/preparation/resultsFile/A3.vue'; /* A3表 */
import tree from './tree-bazaar.vue'; /* 左边栏 */
import createProject from './createProject.vue'; /* 修改项目*/
import collapseUpAndDownload from '../../../more/common/components/collapseUpAndDownload.vue'; /* 文件上传下载删除 */
import collapseReviewRead from '../../../more/common/components/collapseReviewRead.vue'; /* 审核意见只读 */
import collapseReviewWrite from '../../../more/common/components/collapseReviewWrite.vue'; /* 审核意见可写*/
import collapseReviewWrite_dialog from '../../../more/common/components/collapseReviewWrite_dialog.vue'; /* 可写组件 - 对话框*/
import dialogUpload from '../../../more/common/components/dialogUpload.vue'; /* 上传下载组件 - 对话框 */
import submitAudit from '../../../more/common/components/submitAudit.vue'; /* 提交审核-对话框 */
import office from '../../common/vue/office.vue';/* 批注 */
export default {
  data() {
    return {
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      contentList: {
        projectName: '',
        contractNum: '',
        fileNumber: ''
      },
      listarr: [] /* 三点数据 */,
      state: false /* 左侧菜单显示 */,
      slideRight: false /* 右侧修改项目显示 */,
      winningRight: false /* 右侧中标状态显示 */,
      winning: '' /* 中标状态选中值 */,
      winningText: '中标状态',
      winningState: [
        {
          userName: '已中标',
          userId: '2'
        },
        {
          userName: '未中标',
          userId: '1'
        }
      ] /* 中标状态数据 */,
      show: true /* 任务概况显示 */,
      Ashow: false /* A3显示 */,
      showUploadBtn: false /* 上传按钮是否显示 */,
      delBtnFlag: false /* 删除按钮是否显示 */,
      above: false /* 是否跳级审核 */,
      typeFlag: -1 /* 默认权限 */,
      periodType: -1,
      list: '' /* 列表数据 */,
      dataVO: [] /* 左侧菜单数据 */,
      files: [], // 上传文件列表
      reviewFileList: [] /* 相关资料列表 */,
      auditFileList: [] /* 审核意见文件列表 */,
      options: [] /* 文件上传单选数据 */,
      opinionList: [] /* 文件列表数据 */,
      treeNodeLevel: -1,
      errorTyleList: [], // 错误类型列表
      selectErrorObj: {}, // 选择错误的对象
      nextTaskKey: '', //流程下一节点
      taskNum: '', //流程节点序号
      jumpFlag: false /* 是否是初次提审 */,
      approveTime: null,
      delegateTaskKey: null,
      verifyState: null,
      uploadOptions: {
        isInvoice: false, // 是否是发票
        limit: 1,
        radioFlag:false
      },
      tenderStageMaterialInfo:null,/* 批注信息 */
      correctTenderStageMaterialFlag:false,/* 批注权限 */
      showOnlyOfficeCompFlag:false,/* 批注显示 */
      reviewer: {
        // 审核人
        current: '', // 当前
        next: '' // 下一个
      },
      object: {
        title: '', // 一级面板的标题
        opinion: '', // 一级级面板的意见
        children: [
          {
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: [
              // 二级面板的被审批人的错误数组
              {
                num: '', // 错误数量
                type: '' // 错误类型
              }
            ]
          }
        ]
      }
    };
  },
  components: {
    tree: tree,
    A3: A3,
    'collapse-download': collapseUpAndDownload,
    'collapse-review-read': collapseReviewRead,
    'collapse-review-write': collapseReviewWrite,
    'collapse-review-write-dialog': collapseReviewWrite_dialog,
    'dialog-upload': dialogUpload,
    'submit-audit': submitAudit,
    'create-project': createProject,
	office
  },
  created() {
    this.list = this.SStorage.getItem(this.$route.path); /* 请求详情数据 */
    this.selected = this.$route.query.ident;
    this.init(); /* 初始化方法 */
    this.default(); /* 默认重新请求详情数据 */
    this.relatedFileList();
  },
  mounted() {
    const vm = this;
    this.bus.$on('sindex', function(val) {
      vm.echo(val);
      if (vm.typeFlag == 1 || vm.typeFlag ==15 && vm.periodType!=2&&vm.periodType!=3) {
        vm.showUploadBtn = true;
      } else {
        vm.showUploadBtn = false;
      }
    });
    vm.searchQuestionList();
  },
  updated() {
    //跳级显示flag
    this.jumpFlag = this.delegateTaskKey == 'ManagerVerify' || (this.delegateTaskKey == 'SubmitVerify' && this.approveTime == null);
  },
  watch:{
    correctTenderStageMaterialFlag(){
      console.log("correctTenderStageMaterialFlag",this.correctTenderStageMaterialFlag)
      if (this.correctTenderStageMaterialFlag) {
        this.searchTenderStageMaterial(this.SStorage.getItem('/app/bazaarItemIndex').tenderTaskId);
      }else{
        this.showOnlyOfficeCompFlag = correctTenderStageMaterialFlag;
      }
    }
  },
  methods: {
    default() {
      /* 请求详情数据 */
      const vm = this;
      this.requestPost('/tenderTask/query/searchTenderTaskInfoById', { tenderTaskId: this.list.tenderTaskId })
        .then(res => {
          if (res.data.status == 'success') {
            this.list = res.data.dataVO;
            let tenderTaskInfo = res.data.dataVO;
            let userList = tenderTaskInfo.userList;
            for (var i = userList.length - 1; i >= 0; i--) {
              userList[i].errorList = [];
            }
            vm.object.title = vm.getStepName(tenderTaskInfo.delegateTaskKey);
            vm.nextTaskKey = tenderTaskInfo.submitNode;
            vm.delegateTaskKey = tenderTaskInfo.delegateTaskKey;
            vm.delegateTaskId = tenderTaskInfo.delegateTaskId;
            vm.verifyState = tenderTaskInfo.verifyState;
            vm.isCanRecall = tenderTaskInfo.isCanRecall;
            vm.approveTime = tenderTaskInfo.approveTime;
            vm.tasknum = vm.getStepNum(tenderTaskInfo.delegateTaskKey);
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));
            for (var i = userList.length - 1; i >= 0; i--) {
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, 'id'));
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, 'name'));
            }
            vm.object.children = userList;
            for (let i = 0, len = userList.length; i < len; i++) {
              vm.$set(vm.object.children, i, userList[i]);
            }
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    init() {
      this.listarr= []
      this.patientInfo = {
        tenderTaskId: this.list.tenderTaskId
      };
      /* 请求左侧树数据 */
      this.requestPost('/tenderTask/query/searchTaskPeriodByTenderTaskId', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.dataVO = res.data.dataList;
            this.winningText = this.dataVO[0].tenderTaskSts == '1' ? '未中标' : this.dataVO[0].tenderTaskSts == '2' ? '已中标' : '中标状态';
            if (this.dataVO[0].verifyState == '1' && this.dataVO[0].isMainHeadFlag == '1') {
              this.listarr.push({
                label: this.winningText,
                link: ''
              });
            }
            var powerInfo = Object.assign({},this.userPowerInfo, this.dataVO[0].powerInfo);
            if (!this.dataVO[0].verifyState&&!!powerInfo) {
              if (!!powerInfo.tenderTaskModify) {
                this.listarr.push({
                  label: '修改项目',
                  link: '/app/createProject'
                });
              }
              if (!!powerInfo.tenderTaskDelete) {
                this.listarr.push({
                  label: '删除项目',
                  link: ''
                });
              }
            }
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
      this.requiredInfo = {
        tenderTaskId: this.list.tenderTaskId
      };
      /* 请求权限数据*/
      this.requestPost('/tenderTask/query/showBtnType', this.requiredInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.typeFlag = res.data.typeFlag;
            if (this.typeFlag == 1 || this.typeFlag ==15 && this.periodType!=2&&this.periodType!=3) {
              this.showUploadBtn = true;
            } else {
              this.showUploadBtn = false;
            }
            this.listarr.push({
              label: '下载流程控制表',
              link: ''
            })
            this.correctTenderStageMaterialFlag=(this.typeFlag==2||this.typeFlag==3||this.typeFlag==4)
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
      /* 请求审核记录数据(一级审核二级审核)*/
      const vm = this;
      this.requestPost('/tenderTask/query/searchHTTenderTaskHisTaskInst', {
        tenderTaskId: this.list.tenderTaskId
      })
        .then(res => {
          if (res.data.status == 'success') {
            /*------------------------------格式转换START---------------------------------*/
            let dataList = res.data.dataList;
            //for循环遍历更改属性名
            for (var i = 0; i < dataList.length; i++) {
              dataList[i].title = vm.getStepName(dataList[i].taskKey);
              //将属性名httenderTaskHisTaskInstVOList转换成属性名children
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/httenderTaskHisTaskInstVOList/g, 'children'));
              let children = dataList[i].children;
              for (var j = 0; j < children.length; j++) {
                //将属性名approveTime转换成属性名time
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, 'time'));
                //将属性名approveUserName转换成属性名name
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, 'name'));
                //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g, 'persions'));
                //将属性名rejectReason转换成属性名opinion
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, 'opinion'));
                let persions = vm.groupBy(children[j].persions, function(item) {
                  return [item.userId];
                });
                for (var k = 0; k < persions.length; k++) {
                  persions[k].name = persions[k].dataList[0].userName;
                  //将属性名dataList转换成属性名errorList
                  persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, 'errorList'));
                  let errorList = persions[k].errorList;
                  for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, 'type'));
                    //将属性名questionNum转换成属性名num
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, 'num'));
                  }
                  persions[k].errorList = errorList;
                }
                children[j].persions = persions;
              }
            }
            /*---------------------------------格式转换END-------------------------------------*/
            vm.opinionList = dataList;
            console.log('dataList', dataList);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });

      /* 查询审核意见文件*/
      vm.requestPost('/commonModule/query/searchReviewFileRelationList', { businessId: this.list.tenderTaskId })
        .then(res => {
          if (res.data.status == 'success') {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, 'materialId'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, 'materialName'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, 'materialPath'));
            }
            vm.auditFileList = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /* 批注文件信息*/
    searchTenderStageMaterial(tenderTaskId) {
      let vm = this;
      let formData = {
        tenderTaskId: tenderTaskId
      }
      vm.requestPost('/tenderTask/query/searchTenderStageMaterialByTenderTaskId', formData).then(res => {
        if (res.data.status == 'failure') {
          vm.Toast(res.data.message);
        } else {
          let tenderStageMaterialInfo = res.data.dataVO;
          console.log("tenderStageMaterialInfo",tenderStageMaterialInfo)
          vm.tenderStageMaterialInfo = {
            fileName:tenderStageMaterialInfo.reviewFileName,
            key:tenderStageMaterialInfo.onlyOfficeEditKey,
            downloadUrl:tenderStageMaterialInfo.onlyOfficeAbsDownLoadPath,
            saveUrl:tenderStageMaterialInfo.reviewFilePath,
          }
          vm.onlyOfficeEditKey = tenderStageMaterialInfo.onlyOfficeEditKey;
          vm.reviewFileName = tenderStageMaterialInfo.reviewFileName;
          vm.reviewFilePath = tenderStageMaterialInfo.reviewFilePath;
          vm.showOnlyOfficeCompFlag = true;
        }
      }).catch(err => {
      this.Toast(err);
      })
    },
    /* 上传组件相关 */
    uploadFile() {
      // 上传文件对话框显示
      /* 上传文件数据*/
      this.requestPost('/tenderTask/query/searchMaterialTypeList', {
        departmentId: this.list.departmentId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.options = res.data.dataList;
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
      let vm = this;
      this.$nextTick(function() {
        vm.$refs['dialogUpload'].show = true;
      });
    },
    uploadSubmit(data) {
      // 上传文件提交
      let vm = this;
      var formData = new FormData();
      formData.append('file', data.form.get('file'));
      formData.append('tenderTaskPeriodId', this.aside.id);
      formData.append('periodType', this.periodType);
      formData.append('fileTypeId', data.fileTypeId);
      this.requestPost('/tenderTask/insert/saveTenderTaskPeriodMaterialInfo', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.demandFile(this.aside);
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    demandFile(val) {
      /* 请求文件列表 */
      this.requestPost('/tenderTask/query/searchTenderTaskPeriodMaterialList', {
        relationId: val.id
      })
        .then(res => {
          if (res.data.status == 'success') {
            const vm = this;
            let dataList = res.data.dataList;
            //遍历调整结构(变更属性名)
            for (var i = dataList.length - 1; i >= 0; i--) {
              //更改属性名
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));
              if (vm.typeFlag == '1') {
                dataList[i].isCanDel = '1';
              } else if (vm.typeFlag == '15') {
                continue;
              } else {
                dataList[i].isCanDel = '0';
              }
            }
            vm.files = dataList;
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    relatedFileList() {
      /* 请求相关资料列表 */
      let vm = this;
      let formData = {
        tenderTaskId: vm.list.tenderTaskId,
        isReview: '1'
      };
      vm.requestPost('/tenderTask/query/searchIsReviewTenderTaskPeriodMaterialList', formData)
        .then(res => {
          if (res.data.status == 'success') {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));
            }
            vm.reviewFileList = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    echo(val) {
      console.log("val",val)
      /* 左边栏返回值 */
      this.aside = val;
      this.jurisdiction = val;
      this.state = false;
      this.show = false;
      this.treeNodeLevel = val.treeNodeLevel;
      this.periodType = val.periodType;
      if (val.treeNodeLevel == '2') {
        this.demandFile(val);
      }
      /*A3表是否显示判断 */
      if (val.periodType == 2 || val.periodType == 3) {
        this.relatedFileList();
        if (this.typeFlag == 0 || this.typeFlag == 1 || this.typeFlag == 15 || this.typeFlag == 20) {
          this.Ashow = true;
        } else {
          this.Ashow = false;
        }
      } else {
        this.Ashow = false;
      }
    },
    deleteFileInfo(val) {
      /* 删除文件 */
      let vm = this;
      let formData = {
        relationId: val.materialId,
        materialPath: val.materialPath
      };
      this.requestPost('/tenderTask/delete/deleteTenderTaskPeriodMaterialInfo', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.demandFile(this.aside);
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    submitAudit() {
      let vm = this;
      if (this.files.length > 0) {
        /* 提交审核 */
        let formData = {
          tenderTaskPeriodId: vm.aside.id,
          tenderTaskId: vm.list.tenderTaskId,
          periodType: vm.periodType
        };
        //查询以上传的所有文件(非审批文件)
        vm.requestPost('/tenderTask/query/searchAllMaterialTypeAndMaterialList', formData)
          .then(res => {
            if (res.data.status == 'success') {
              let dataList = res.data.dataList;
              let count = 0;
              //校验是否有上传文件
              if (null != dataList && dataList.length > 0) {
                for (var i = dataList.length - 1; i >= 0; i--) {
                  let childDataList = dataList[i];
                  count += childDataList.children.length;
                }
              }
              console.log("count",count)
              //无上传除投标文件其他相关文件时
              if (count == 0) {
                //直接提交
                this.$refs.submitAudit.submit();
              } else {
                this.$refs.submitAudit.list = dataList;
                this.$refs.submitAudit.show = true;
              }
              vm.$router.push('/app/workbench');
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.Toast({
          message: '请选择需要上传得编制投标文件',
          position: 'middle',
          duration: 4000,
          className: 'toast'
        });
      }
    },
    confirmSubmit(val) {
      /* 确认提交审核 */
      const vm = this;
      let nextTaskKey = this.nextTaskKey;
      if (this.above) {
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }
      let formData = {
        tenderTaskId: this.list.tenderTaskId,
        departmentId: this.list.departmentId,
        materialIds: val,
        submitNode: nextTaskKey
      };
      console.log(formData);
      vm.requestPost('/tenderTask/modify/updateTenderTaskPeriodMaterialInfoIsReviewType', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.$refs.submitAudit.show = false;
            // 跳转到工作台
             this.$router.push('/app/workbench')
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    withdraw() {
      /* 撤回 */
      let vm = this;
      let formData = {
        tenderTaskId: this.list.tenderTaskId,
        delegateTaskId: this.delegateTaskId
      };
      vm.requestPost('/tenderTask/modify/recallTask', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.init();
            this.default();
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //查询问题类型列表信息
    searchQuestionList() {
      let vm = this;
      let formData = {
        departmentId: this.marketDeptId
      };
      vm.requestPost('/tenderTask/query/searchQuestionList', formData)
        .then(res => {
          if (res.data.status == 'success') {
            let questionList = res.data.dataList;
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            vm.errorTyleList = questionList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /* 可写组件--相关 */
    selecError(data) {
      // 选择错误
      // 先赋值再显示对话框
      this.selectErrorObj = data;
      this.$refs['collapseReviewWrite_dialog'].show = true;
    },
    selectErrorSubmit(data) {
      this.selectErrorObj.errorList = data;
    },
    //通过或者驳回
    auditingHTTenderTask(isPass) {
      let paramObj = this.object;
      paramObj.isPass = isPass;
      let nextTaskKey = this.nextTaskKey;
      if (this.above) {
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }
      paramObj.submitNode = nextTaskKey;
      paramObj.delegateTaskId = this.delegateTaskId;
      paramObj.onlyOfficeEditKey = this.onlyOfficeEditKey
      paramObj.reviewFileName = this.reviewFileName
      paramObj.reviewFilePath = this.reviewFilePath
      paramObj.userQuestionRecordRelationList = new Array();
      paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, 'delegateTaskKey'));
      if (isPass == '0') {
        paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, 'rejectReason'));
        for (var i = paramObj.children.length - 1; i >= 0; i--) {
          if (paramObj.children[i].errorList) {
            for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
              paramObj.children[i].errorList[j].userId = paramObj.children[i].id;
              paramObj.children[i].errorList[j].userName = paramObj.children[i].name;
              if (paramObj.children[i].errorList[j].questionNum != '0') {
                paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j]);
              }
            }
          }
        }
        delete paramObj.children;
      }
      paramObj.tenderTaskId = this.list.tenderTaskId;
      let vm = this;
      this.requestPost('/tenderTask/insert/auditingHTTenderTask', paramObj)
        .then(res => {
          if (res.data.status == 'success') {
          // 跳转到工作台
           vm.$router.push('/app/workbench');
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    rightShow(val) {
      if (val.label == '修改项目') {
        this.slideRight = true;
      } else if (val.label == '中标状态') {
        this.winningRight = true;
      }
    },
    colseRight() {
      this.slideRight = false;
    },
    rollback() {
      this.winningRight = false;
    },
    radioChange() {
      const vm = this;
      this.winningState.forEach(function(i) {
        if (i.userId == vm.winning) {
          vm.winningText = i.userName;
        }
      });
    },
    winningForm() {
      /* 中标状态提交 */
      let obj = {
        tenderTaskId: this.list.tenderTaskId,
        tenderTaskSts: this.winning
      };
      let vm = this;
      this.requestPost('/tenderTask/modify/updateTenderTaskSts', obj)
        .then(res => {
          if (res.data.status == 'success') {
            this.init();
            this.default();
            vm.winningRight = false;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    downloadFiles(val) {
      let tempUrl = '/taskExecute/downLoad/downLoadTaskExecuteExecl?tenderTaskId=' + val.tenderTaskId;
      this.downloadFile(tempUrl, '', true);
    },
    skipGrade(val) {
      /* 跳级审核 */
      const vm = this;
      if(val==false){
        this.MessageBox.confirm('', {
          message: '审核本来要提交给' + vm.reviewer.current + ',现在将要跳级提交给' + vm.reviewer.next,
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
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
     * @method 查看是否支持onlyOffice信息显示
     */
    supportOffice(val){
      let vm = this;
      vm.officeHeight = val?'700px':'30px';
    }
  }
};
</script>
<style lang="scss">
@import url('../../../more/common/css/fileList.css');
@import url('../../../more/common/css/foldedPanel.css');
.slide-fade-enter-active {
  /* 进入 */
  transition: all 0.8s ease;
}
.slide-fade-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}
.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateY(-900px);
}
.slide-fade-right-enter-active {
  /* 进入 */
  transition: all 0.8s ease;
}
.slide-fade-right-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}
.slide-fade-right-enter,
.slide-fade-right-leave-to {
  transform: translateX(100%);
}
.popup-top {
  top: 15%;
}
.primary {
  width: 100%;
  background-color: #fff;
  padding: 20px 0 0;
  button {
    font-size: 28px;
    color: #fff;
    width: 80%;
    margin: 20px auto;
    display: block;
    text-align: center;
    border-radius: 10px;
    height: 80px;
    line-height: 80px;
  }
  .mint-switch {
    width: 35%;
    margin: 0px auto 20px;
    font-size: 28px;
    color: #333;
  }
}
.radiolist {
  width: 90%;
  margin: 20px auto;
  padding-bottom: 95px;
  max-height: 700px;
  overflow: auto;
  .mint-radiolist-title {
    font-size: 28px;
    color: #b3b3b3;
  }
  .mint-cell {
    border-bottom: 2px solid #e5e5e5;
    .mint-cell-wrapper {
      .mint-radiolist-label {
        display: flex;
        justify-content: space-between;
        flex-direction: row-reverse;
        padding: 0;
        font-size: 28px;
        color: #333333;
        width: 100%;
        height: 90px;
        line-height: 90px;
        .mint-radiolist-core {
          width: 60px;
          height: 60px;
          transform: scale(0.5);
          transform-origin: 0% 0%;
          &::after {
            top: 15px;
            left: 20px;
            position: absolute;
            width: 20px;
            height: 20px;
            border-width: 4px;
          }
        }
      }
    }
  }
}
</style>
<style lang="scss" scoped>
.index {
  width: 100%;
  background-color: #f1f5f8;
  min-height: 100vh;
  .contractAside {
    width: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    height: 100vh;
    position: fixed;
    z-index: 200;
    .treeShow {
      width: 100%;
      background-color: #fff;
      overflow: hidden;
      height: 900px;
      overflow: auto;
      position: relative;
      .iconshouqi- {
        position: absolute;
        bottom: 10px;
        color: #666666;
        font-size: 35px;
        left: 50%;
        right: 50%;
      }
    }
  }
  .createProject {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    background-color: #fff;
    width: 100%;
    height: 100vh;
  }
  .contractRight {
    width: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    height: 100vh;
    position: fixed;
    z-index: 20000;
    bottom: 0;
    .right {
      width: 100%;
      background-color: #fff;
      overflow: hidden;
      height: 1100px;
      overflow: auto;
      position: absolute;
      bottom: 0;
      .iconjiantou-1 {
        position: absolute;
        bottom: 10px;
        color: #666666;
        font-size: 35px;
        left: 50%;
        right: 50%;
      }
    }
  }
  .foundItemIndex {
    overflow: hidden;

    .contractList {
      box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
      background-color: #fff;
      li {
        height: 80px;
        line-height: 80px;
        display: flex;
        padding: 0px 30px;
        justify-content: space-between;
        font-size: 28px;
        color: #333333;
        i {
          font-size: 28px;
        }
      }
    }
    .exhibition {
      box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
      background-color: #fff;
      width: 94%;
      margin: 20px auto;
      border-radius: 10px;
      ul {
        width: 92%;
        margin: 0 auto;
        overflow: hidden;
        li {
          margin: 30px 0;
          display: flex;
          justify-content: space-between;
          font-size: 28px;
        }
      }
    }
    .surface {
      width: 100%;
      overflow: hidden;
    }
  }
}
</style>
