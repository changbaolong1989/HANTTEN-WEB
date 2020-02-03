<template>
  <!-- 合同项目详情页面 -->
  <div class="index">
    <!-- 左侧边栏-->
    <transition name="slide-fade">
      <div class="contractAside" v-if="state">
        <div class="treeShow">
          <tree :dataVO="dataVO"></tree>
          <i class="iconfont iconshouqi-" @click="state = false"></i>
        </div>
      </div>
    </transition>
    <!-- 创建任务-->
    <transition name="slide-fade-right">
      <div class="createProject" v-if="slideRight">
        <createTask ref="create" :createList="createList" @cancelTask="cancelTask" :createState="createState" :modification="modification"></createTask>
      </div>
    </transition>
    <!-- 创建请款单-->
    <transition name="slide-fade-right">
      <div class="createProject" v-if="showDialogRequestPayout">
        <dialog-request-payout :data="contractInfo" @cancel="cancelDialog('showDialogRequestPayout')"></dialog-request-payout>
      </div>
    </transition>
    <!-- 合同添加/修改 -->
    <transition name="slide-fade-right">
      <div class="createProject" v-if="showContractAdd"><dialog-contract-add :data="transmitData" @cancel="cancelDialog('showContractAdd')"></dialog-contract-add></div>
    </transition>
    <!-- 分配合同编撰人 -->
    <transition name="slide-fade-right">
      <div class="createProject" v-if="showDialogDistributionContractCompiler">
        <dialog-distribution-contract-compiler :data="contractInfo" @cancel="cancelDialog('showDialogDistributionContractCompiler')"></dialog-distribution-contract-compiler>
      </div>
    </transition>
    <!-- 修改项目 -->
    <transition name="slide-fade-right">
      <div class="createProject" v-if="contractShow"><MCreateContract ref="createContract" :createContract="createContract" :list="list"></MCreateContract></div>
    </transition>

    <!-- 中间显示内容-->
    <div class="foundItemIndex">
      <ul class="contractList">
        <li @click="state = true">
          <span>全部项目-{{ list.projectNum }}</span>
          <i class="iconfont">&#xe630;</i>
        </li>
      </ul>
      <div class="surface">
        <div v-if="showOnlyOfficeCompFlag && (treeList.periodType === '2' || treeList.periodType === '3')" :style="{ height: officeHeight }">
          <office :file="taskMaterialInfo" @supportOffice="supportOffice"></office>
        </div>

        <!-- A3-->
        <A3 v-if="treeList.periodType == '2' && !correctTaskMaterialFlag" :contentList="content" :A3dataList="A3dataList"></A3>
        <!-- 编制成果文件 -->
        <preparation v-if="treeList.periodType == '3' && !correctTaskMaterialFlag" ref="preparation" :transmittedData="treeList" :disabled="disabled"></preparation>
        <!-- 收集资料 A4-->
        <collecting v-if="treeList.periodType == '1' && treeList.pageContent == 'A4'" :transmittedData="treeList"></collecting>
        <!-- 拟定工作计划 A5-->
        <work-plan v-if="treeList.pageContent == 'A5'" :transmittedData="treeList" :projectName="content.contractNum"></work-plan>
        <!-- A6(造价部)-->
        <modifyCost v-if="treeList.pageContent == 'A6' && treeList.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'" ref="a6" :transmittedData="treeList"></modifyCost>
        <!-- A6(招标部)-->
        <modify v-if="treeList.pageContent == 'A6' && treeList.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'" ref="a6" :transmittedData="treeList"></modify>
        <!-- 招标部A7 -->
        <tendering v-if="treeList.pageContent == 'A7'" :transmittedData="treeList"></tendering>
        <!-- 造价部A8 -->
        <siteVisit v-if="treeList.pageContent == 'A8' && treeList.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'" :transmittedData="treeList"></siteVisit>
        <!-- 招标部A8 -->
        <tendering-copy v-if="treeList.pageContent == 'A8' && treeList.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'" :transmittedData="treeList"></tendering-copy>
        <!-- 专家意见表-->
        <expertOpinion v-if="treeList.periodType == '6'"  :data="rxpertOpinionListForm"></expertOpinion>
        <!-- 磋商文件确认文件意见-->
        <negotiant v-if="treeList.periodType == '7'" :data="consultationDocumentConfirmationDocumentOpinionForm"></negotiant>
        <!-- 未成交通知书-->
        <unsettled v-if="treeList.periodType == '8'" :data="unfilledNoticeForm"></unsettled>
        <!-- 请款单 -->
        <request-payout v-if="showRequestPayoutSubmit" :transmitData="transmitData"></request-payout>
        <!-- 合同 -->
        <contract v-if="showContractSubmit" :transmitData="transmitData"></contract>
        <!-- 项目 -->
        <project v-if="showProject" :transmitData="transmitData"></project>
        <!-- 任务 -->
        <TaskDetails v-if="showDetails" :transmitData="transmitData"></TaskDetails>
      </div>
    </div>
    <!-- 右侧边栏 -->
    <div class="right"><right :treeList="treeList" :typeFlag="typeFlag" :rightJurisdiction="rightJurisdiction" :transmitData="transmitData" @A3data="A3data" :postil="postil"></right></div>
  </div>
</template>

<script>
import preparation from '../common/preparation/index.vue'; /* 编制成果文件√ */
import collecting from '../common/collecting/index.vue'; /* 收集资料 √A4*/
import workPlan from '../common/workPlan/index.vue'; /* 拟定工作计划√A5 */
import modify from '../common/modify/index-call.vue'; /* 招标部A6*/
import modifyCost from '../common/modify/index-cost.vue'; /* 造价部A6*/
import siteVisit from '../common/siteVisit/index.vue'; /*造价部A8 */
import archives from '../common/archives/index.vue'; /*存档 */
import currentLetter from '../common/currentLetter/index.vue'; /*往来函件 */
import tenderingA7 from '../common/tenderingA7/index.vue'; /*招标部A7 */
import tenderingA8 from '../common/tenderingA8/index.vue'; /*招标部A8 */
import expertOpinion from '../common/expertOpinion/index.vue'; /*专家意见表 */
import negotiant from '../common/negotiant/index.vue'; /*磋商文件确认文件意见 */
import unsettled from '../common/unsettled/index.vue'; /*未成交通知书 */
import office from '../../common/vue/office.vue'; /* 批注 */

import tree from './tree-found.vue'; /* 左侧树组件 */

import createTask from './createTask.vue'; /* 创建任务 */
import MCreateContract from './createContract.vue'; /* 修改项目*/
import Right from './right.vue'; /* 右侧边栏 */
import A3 from '../common/preparation/resultsFile/A3.vue'; /* A3 */
import dialogRequestPayout from './dialogRequestPayout.vue'; /* 创建请款单 */
import requestPayout from './requestPayout/index.vue'; /* 请款单 */
import dialogDistributionContractCompiler from './dialogDistributionContractCompiler.vue'; /* 分配合同编撰人 */
import contract from './contract/index.vue'; /* 合同 */
import dialogContractAdd from './contract/dialogContractAdd.vue'; /* 合同添加 */
import project from './project/index.vue'; /* 项目 */
import TaskDetails from './task/details.vue'; /* 任务 */

export default {
  data() {
    return {
      officeHeight: '700px', // onlyOffice文件的高度
      state: false /* 左侧菜单显隐 */,
      dataVO: [] /* 左侧菜单数据 */,
      slideRight: false /*创建任务是否显示  */,
      showDialogRequestPayout: false /* 显示创建请款单 */,
      showRequestPayoutSubmit: false /* 显示请款单提交审核表单 */,
      showContractSubmit: false /* 显示合同表单 */,
      showContractAdd: false /* 显示合同添加 */,
      contractShow: false /* 显示修改项目*/,
      createContract:{},/* 修改项目回显数据 */
      showProject: false /* 查看项目 */,
      showDetails: false /* 查看任务 */,
      showDialogDistributionContractCompiler: false /* 显示分配合同编撰人表单 */,
      transmitData: {} /* 传输的数据 */,
      list: '' /* 列表数据 */,
      createList: {} /*, 创建任务数据 */,
      modification: {} /* 修改任务回显数据 */,
      createState: 0 /* 创建任务还是修改任务 */,
      contractInfo: {} /*, 合同信息 */,
      treeList: {},
      disabled: false /* 编制成果人表单禁用 */,
      // 是否显示onlyOffice
      showOnlyOfficeCompFlag: false,
      // office文件信息
      taskMaterialInfo: null,
      correctTaskMaterialFlag: false,
      content: {
        contractNum: '',
        fileNumber: '',
        projectName: ''
      },
      postil: {} /* 批注信息 */,
      typeFlag: null,
      /* 用户权限 */
      A3dataList: [],
      // 专家论证意见表详情
      rxpertOpinionListForm:null,
      // 招标文件确认意见表和任务的关联表详情
      consultationDocumentConfirmationDocumentOpinionForm:null,
      // 落标通知书和任务关联表详情
      unfilledNoticeForm:null,
      // 开标、评标详情
      openBidForm:null,
      rightJurisdiction:{},/* 用户权限数据 */
    };
  },
  components: {
    preparation: preparation,
    collecting: collecting,
    'work-plan': workPlan,
    modify: modify,
    modifyCost: modifyCost,
    siteVisit: siteVisit,
    tendering: tenderingA7,
    'tendering-copy': tenderingA8,
    expertOpinion: expertOpinion,
    negotiant: negotiant,
    unsettled: unsettled,
    'request-payout': requestPayout, // 请款单
    tree: tree,
    createTask: createTask,
    right: Right,
    A3: A3,
    'dialog-request-payout': dialogRequestPayout, // 创建请款单
    'dialog-distribution-contract-compiler': dialogDistributionContractCompiler, // 分配合同编撰人
    contract, // 合同
    'dialog-contract-add': dialogContractAdd, // 合同添加
    project, // 项目
    MCreateContract,
    TaskDetails /* 任务 */,
    office /* 批注 */
  },
  watch: {
    correctTaskMaterialFlag: {
      handler() {
        if (this.correctTaskMaterialFlag) {
          // A3阶段
          if (this.treeList.periodType === '2') {
            // 查询A3阶段文件信息
            this.searchMaterialByTaskPeriodId();

            // 成果文件阶段
          } else if (this.treeList.periodType === '3') {
            // 查询成果文件阶段文件信息
            this.searchOutcomeDocumentMaterial();
          }
        } else {
          this.showOnlyOfficeCompFlag = this.correctTaskMaterialFlag;
        }
      },
      deep: true,
      immediate: true
    }
  },
  created() {
    this.list = this.SStorage.getItem(this.$route.path);
    this.createList.projectNum = this.list.projectNum;
  },
  mounted() {
    const vm = this;
    this.bus.$on('ModifyProject', function(val) {
      /* 修改项目 */
      vm.changeProject(val);
    });
    this.bus.$on('DeleteProject', function(val) {
      /* 删除项目 */
      vm.removeProject(val);
    });
    this.bus.$on('establish', function(val) {
      /* 创建任务 */
      vm.createRequest(val);
    });
    this.bus.$on('treeChange', function(val) {
      // 重置所有组件显示状态
      vm.resetComponentState();
      vm.treeChange(val.data);
    });
    /* 点击任务*/
    this.bus.$on('treeTask', function(val) {
      // 重置所有组件显示状态
      vm.resetComponentState();
      vm.transmitData = {
        name: '任务',
        data: val.data
      };
      vm.showDetails = true;
      vm.getTaskDetails(val.data);
    });
    /* 删除任务*/
    this.bus.$on('deleteTask', function(val) {
      vm.deleteTask(val);
    });
    /* 修改任务*/
    this.bus.$on('modification', function(val) {
      vm.getTaskInfo(val);
    });
    /* 下载文件*/
    this.bus.$on('downloadFile', function(val) {
      let tempUrl = '/taskExecute/downLoad/downLoadTaskExecuteExecl?taskId=' + val.tree.id;
      vm.downloadFile(tempUrl, '', true);
    });
    // 监听请款单事件
    this.bus.$on('request-payout', function(val) {
      vm.resetComponentState();
      if ('创建请款单' == val.name) {
        vm.showDialogRequestPayout = true; /* 显示创建请款单的表单组件 */
        vm.contractInfo = val.data; /* 赋值合同信息 */
      }
      if ('请款单提交审核' == val.name) {
        vm.transmitData = {
          name: '请款单提交审核',
          data: val.data
        };
        vm.showRequestPayoutSubmit = true;
      }
      vm.state = false; //隐藏左侧菜单
    });
    // 监听合同事件
    vm.bus.$on('contract', function(val) {
      vm.transmitData = val;
      vm.resetComponentState();
      if ('合同提交审核' == val.name) {
        vm.showContractSubmit = true;
      } else if ('添加合同' == val.name) {
        vm.showContractAdd = true;
      } else if ('修改合同' == val.name) {
        vm.showContractAdd = true;
      } else if ('删除合同' == val.name) {
        vm.MessageBox.confirm('确定删除合同：《' + val.data.label + '》?').then(action => {
          // 发送删除请求
          vm.requestPost('/project/delete/deleteContractInfo', {
            contractId: val.data.id
          })
            .then(res => {
              vm.Toast(res.data.message);
              vm.state = false; //隐藏左侧菜单
              vm.init(); // 重新初始化页面
            })
            .catch(e => {
              vm.Toast(e);
            });
        });
        return;
      }
      vm.state = false; //隐藏左侧菜单
    });
    // 监听请款单事件
    this.bus.$on('distribution-contract-compiler', function(val) {
      if ('分配合同编撰人' == val.name) {
        vm.showDialogDistributionContractCompiler = true; /* 显示分配合同编撰人表单组件 */
        vm.contractInfo = val.data; /* 赋值项目信息 */
      }
      vm.state = false; //隐藏左侧菜单
    });
    // 监听项目事件
    this.bus.$on('project', function(val) {
      if ('项目' == val.name && 1 == val.data.treeNodeLevel) {
        vm.transmitData = {
          name: '项目',
          data: val.data
        };
        vm.treeList = val.data;
        vm.showProject = true;
        // 隐藏合同
        vm.showContractSubmit = false;
      } else {
        vm.showProject = false;
      }
      vm.state = false; //隐藏左侧菜单
    });
    // 监听树节点被点击事件
    vm.bus.$on('treeClick', function(val) {
      let targetNode = val.data;
      let sessionData = vm.SStorage.getItem(vm.$route.path);
      targetNode.businessId = targetNode.businessId ? targetNode.businessId : targetNode.id;
      targetNode.projectId = targetNode.projectId ? targetNode.projectId : sessionData.projectId;
      targetNode.projectNum = targetNode.projectNum ? targetNode.projectNum : sessionData.projectNum;
      vm.SStorage.setItem(vm.$route.path, targetNode);
      vm.list = vm.SStorage.getItem(vm.$route.path);
      vm.createList.projectNum = vm.list.projectNum;
      // 重置组件状态
      vm.resetComponentState();
      vm.init();
    });
    vm.init();
  },
  methods: {
    /* 初始化方法 */
    init() {
      let vm = this;
      vm.correctTaskMaterialFlag = false;
      /* 请求左侧树数据*/
      this.requestPost('/project/query/searchProjectTree', {
        projectId: this.list.projectId
      })
        .then(res => {
          this.Toast(res.data.message);
          if (res.data.status == 'success') {
            console.log('请求左侧树数据', res.data);
            this.dataVO = [res.data.dataVO];
            // 计算节点
            vm.computeNode(res.data.dataVO);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * @method 重置组件显示状态
     */
    resetComponentState() {
      let vm = this;
      // 对话框
      // 左侧边栏
      vm.state = false;
      // 创建任务
      vm.slideRight = false;
      // 创建请款单
      vm.showDialogRequestPayout = false;
      // 合同添加/修改
      vm.showContractAdd = false;
      // 分配合同编撰人
      vm.showDialogDistributionContractCompiler = false;
      // 修改项目
      vm.contractShow = false;
      // 组件
      // A3/编制成果文件/收集资料
      vm.treeList.periodType = '';
      // 拟定工作计划 A5/A6(造价部)/A6(招标部)/招标部A7/造价部A8/招标部A8
      vm.treeList.pageContent = '';
      // 请款单
      vm.showRequestPayoutSubmit = false;
      // 合同
      vm.showContractSubmit = false;
      // 项目
      vm.showProject = false;
      // 任务
      vm.showDetails = false;
    },
    /**
     * @method 计算节点
     */
    computeNode(nodeData) {
      let vm = this;
      // 根据跳转菜单目标修改展示组件
      let sessionData = this.SStorage.getItem(vm.$route.path);
      /**
       * @method 根据标识查找目标节点
       * @param {Object} index表示
       * @param {Object} obj 被查找对象
       */
      let findNodeTarget = function(index, obj) {
        if (index == obj.id) {
          // 如果该对象与查询标识一致就返回改对象
          return obj;
        } else if (obj.children && obj.children.length) {
          let target = null;
          // 如果该对象有子集则继续查找
          for (let i = 0, len = obj.children.length; i < len; i++) {
            let t = findNodeTarget(index, obj.children[i]);
            if (t) {
              target = t;
            }
          }
          return target;
        } else {
          // 上述情况都不满足返回null
          return null;
        }
      };
      let targetNode = findNodeTarget(sessionData.businessId, nodeData);
      targetNode.businessId = targetNode.businessId ? targetNode.businessId : targetNode.id;
      targetNode.projectId = targetNode.projectId ? targetNode.projectId : sessionData.projectId;
      targetNode.projectNum = targetNode.projectNum ? targetNode.projectNum : sessionData.projectNum;
      this.SStorage.setItem(vm.$route.path, targetNode);
      switch (targetNode.treeNodeLevel) {
        case '1':
          // 项目
          vm.bus.$emit('project', vm.tool.deepCopy({ name: '项目', data: targetNode }, vm.tool.deepCopy));
          break;
        case '3':
          // 合同
          vm.bus.$emit('contract', vm.tool.deepCopy({ name: '合同提交审核', data: targetNode }, vm.tool.deepCopy));
          break;
        case '5':
          if (targetNode.objType == 'requestMent') {
            // 请款单
            vm.bus.$emit('request-payout', vm.tool.deepCopy({ name: '请款单提交审核', data: targetNode }, vm.tool.deepCopy));
          } else if (targetNode.objType == null) {
            // 任务
            vm.bus.$emit('treeTask', vm.tool.deepCopy({ name: '任务详情', data: targetNode }, vm.tool.deepCopy));
          }
          break;
        case '6':
            // 任务阶段
            vm.bus.$emit('treeChange', vm.tool.deepCopy({ name: '任务', data: targetNode }, vm.tool.deepCopy));
          break;
        default:
          // 项目
          vm.bus.$emit('project', vm.tool.deepCopy({ name: '项目', data: targetNode }, vm.tool.deepCopy));
          break;
      }
    },
    createRequest(val) {
      /* 创建任务 */
      this.modification = {};
      this.createState = 0;
      /* 请求合同类型*/
      this.createList.contractName = val.tree.label;
      this.createList.contractId = val.tree.id;
      this.createList.departmentId = val.tree.departmentId;
      this.requestPost('/task/query/searchDictContractTypeListByDepartmentId', {
        contractId: val.tree.id
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.createList.contractType = res.data.dataList;
            this.slideRight = true;
            this.state = false;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
      /* 请求编制成果文件人*/
      this.requestPost('/task/query/searchUserListByDepartmentId', {
        contractId: val.tree.id,
        taskId: val.tree.taskId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.createList.contractAchievement = res.data.dataList;
            this.$refs.create.init();
            this.slideRight = true;
            this.state = false;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    cancelTask() {
      /* 取消创建任务 */
      this.slideRight = false;
      this.init();
    },
    cancelDialog(name) {
      /* 隐藏表单 */
      this[name] = false;
      this.init();
    },
    treeChange(val) {
      /* 左侧树点击 */
      this.treeList = val;
      this.state = false;
      console.log('当前被点击的左侧树', this.treeList);
      /* 请求中间内容数据*/
      this.requestPost('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
        relationId: this.treeList.relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.content = res.data.dataVO;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
        if (this.treeList.periodType == '6') { // 专家论证意见表详情
          this.searchExpertOpinionRelationDetail();
        }else if (this.treeList.periodType == '7') { // 招标文件确认意见表和任务的关联表详情
          this.searchFileConfirmOpinionRelationDetail();
        }else if (this.treeList.periodType == '8') { // 落标通知书和任务关联表详情
          this.searchFailTenderNoticeRelationInfoDetail();
        }
      /* 请求控制按钮显示的数据typeFlag*/
      this.requestPost('/task/query/showBtnType', {
        periodType: this.treeList.periodType,
        relationId: this.treeList.relationId,
        taskId: this.treeList.taskId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.rightJurisdiction = res.data.dataVO;
            this.typeFlag = res.data.typeFlag;
            if (this.typeFlag == '1') {
              this.disabled = false;
            } else {
              this.disabled = true;
            }
            this.correctTaskMaterialFlag = this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4' || this.typeFlag === '5';
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 删除任务
    deleteTask(val) {
      const vm = this;
      // 发送删除请求
      this.requestPost('/task/delete/deleteTaskInfo', {
        taskId: val.tree.id
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.Toast(res.data.message);
            // 触发事件,重新刷新项目树
            this.$router.go(0);
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // 修改任务----获取任务详情信息
    getTaskInfo(val) {
      let vm = this;
      // 查询任务详情
      this.requestPost('/task/query/searchTaskInfo', {
        // 任务id
        taskId: val.tree.id
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.slideRight = true;
            vm.createRequest({
              tree: {
                label: res.data.dataVO.contractNum,
                id: res.data.dataVO.contractId
              }
            });
            this.modification = res.data.dataVO;
            this.createState = 1;
            this.createList.departmentId = val.tree.departmentId;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    A3data(val) {
      this.A3dataList = val;
    },
    changeProject(val) {
      /* 修改项目 */
      this.contractShow = true;
      // 查询项目详情
      this.requestPost('/project/query/searchProjectInfo', {
        // 项目id
        projectId: this.list.projectId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            console.log("res.data",res.data)
            this.createContract = res.data.dataVO;
            // this.$refs['createContract'].projectNumPrefix = res.data.dataVO.projectNumPrefix;
            // this.$refs['createContract'].projectForm.genre = res.data.dataVO.condense;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    getTaskDetails(val) {
      const vm = this;
      vm.treeList = val;
      // 根据任务id查询合同内容
      this.requestPost('/task/query/searchTaskContent', {
        // 任务id
        taskId: val.id
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            vm.transmitData = Object.assign({}, vm.transmitData.data, {
              contractNumber: res.data.dataVO.contractNum, // 合同编号
              createDate: res.data.dataVO.createDate, // 创建时间
              taskName: res.data.dataVO.taskName, // 任务名称
              showPeriodDescFlg: false, //隐藏工作步骤
              projectNum: vm.list.projectNum
            });

            // 如果有数据，则显示任务描述
            if (res.data.dataVO.taskContent != '') {
              // 显示任务说明
              vm.transmitData.taskContent = true;
              // 设置任务说明
              vm.transmitData.taskDesc = res.data.dataVO.taskContent;
            } else {
              // 隐藏任务说明
              vm.transmitData.taskContent = false;
            }
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    removeProject(val) {
      /* 删除项目 */
      let vm = this;
      this.requestPost('/project/delete/deleteProjectInfo', {
        // 项目id
        projectId: this.list.projectId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.Toast(res.data.message);
            this.$router.push('/app/projectList');
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // ============================================================================== 成果文件 查询office文件信息 end==============================================================================
    // 查询office文件信息
    searchOutcomeDocumentMaterial() {
      this.requestPost('/task/query/searchOutcomeDocumentMaterial', {
        taskId: this.treeList.taskId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };

            this.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 没提交重新编辑申请的时候可以显示批注组件，如果提交了就不能显示批注组件，而显示A3组件
            if (taskMaterialInfo.isSubmitReedit !== '1' && taskMaterialInfo.reviewFileName !== '') {
              // 显示office组件
              this.showOnlyOfficeCompFlag = true;
            } else {
              // 可以显示A3组件
              this.correctTaskMaterialFlag = false;
            }
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ============================================================================== 成果文件 查询office文件信息 end==============================================================================
    // ============================================================================== A3 查询office文件信息 end==============================================================================
    // 查询office文件信息
    searchMaterialByTaskPeriodId() {
      this.requestPost('/task/query/searchMaterialByTaskPeriodId', {
        taskPeriodId: this.treeList.relationId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };
            console.log('taskMaterialInfo', taskMaterialInfo);
            this.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 显示office组件
            this.showOnlyOfficeCompFlag = true;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    /**
     * @method 查看是否支持onlyOffice信息显示
     */
    supportOffice(val) {
      let vm = this;
      vm.officeHeight = val ? '700px' : '30px';
    },
    // ============================================================================== A3 查询office文件信息 end=========================
    /**
     * @method 查询专家论证意见表详情
     */
    searchExpertOpinionRelationDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchExpertOpinionRelationDetail', { taskId: vm.treeList.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.rxpertOpinionListForm = {
              form: res.data.dataVO
            };
            console.log("专家意见表",vm.rxpertOpinionListForm)
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    /**
     * @method 查询招标文件确认意见表和任务的关联表详情
     */
    searchFileConfirmOpinionRelationDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchFileConfirmOpinionRelationDetail', { taskId: vm.treeList.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.consultationDocumentConfirmationDocumentOpinionForm = {
              form: res.data.dataVO
            };
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    /**
     * @method 查询落标通知书和任务关联表详情
     */
    searchFailTenderNoticeRelationInfoDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchFailTenderNoticeRelationInfoDetail', { taskId: vm.treeList.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.unfilledNoticeForm = {
              form: res.data.dataVO
            };
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
           vm.Toast(err);
        });
    },
    /**
     * @method 查询开标、评标详情
     */
    searchOpenBidInfoDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchOpenBidInfoDetail', {"taskId":vm.treeList.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.openBidForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    }
  }
};
</script>
<style lang="scss">
// @import url('../../../more/common/css/fileList.css');
// @import url('../../../more/common/css/foldedPanel.css');

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

/* 进入 */
.slide-fade-right-enter-active {
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
</style>
<style lang="scss" scoped>
.index {
  width: 100%;
  background-color: #f1f5f8;

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
      min-height: 900px;
      max-height: 100vh;
      overflow: auto;
      position: relative;

      .iconshouqi- {
        position: fixed;
        bottom: 10px;
        color: #fff;
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
    overflow: auto;
  }

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

  .A3 {
    box-shadow: none;
  }
}
</style>
