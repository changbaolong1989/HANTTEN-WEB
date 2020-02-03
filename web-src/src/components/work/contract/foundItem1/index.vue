<template>
  <!-- 项目详情 -->
  <div class="m-work-contract-foundItem">
    <!-- index视图 -->
    <div :class="{'center--work-contract':view_index!='task'}" style="width:100%" v-if="showView">
      <component v-bind:is="view_index" ref="contract" :transmittedData="transmittedData" :theFormData="theFormData" :theFormDisabled="theFormDisabled"></component>
    </div>
    <!-- 右侧视图 -->
    <div width="200px" class="right--work-contract" v-if="showView&&view_index!='task'">
      <component v-bind:is="view_right" :transmittedData="transmittedData"></component>
    </div>
    <!-- 修改项目 -->
    <el-dialog title="修改项目" :visible.sync="dialogVisible" width="600px" center @close="handleClose('changeItem')" :close-on-click-modal="false">
      <el-form :model="changeItem" status-icon :rules="rules" ref="changeItem" label-width="120px" class="demo-ruleForm" :inline-message="true">
          <el-form-item label="公司缩写" class="projectNum" prop="companyId">
            <el-select v-model="changeItem.companyId" placeholder="请选择公司缩写" @change="getCondense">
              <el-option v-for="item in condenseSelect" :key="item.companyId" :label="item.condense" :value="item.companyId">{{ item.condense }}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="合成的项目编号" class="projectNum">
              {{projectNumPrefix}}
              <template v-for="item in condenseSelect">
                <span v-if="item.companyId==changeItem.companyId" :key="item.companyId">{{ item.condense }}</span>
              </template>
          </el-form-item>
          <el-form-item label="项目名称" class="projectNum" prop="projectName" :rules="{ required: true, message: '请输入项目名称', trigger: 'blur' }">
               <el-input style="width:193px;" v-model="changeItem.projectName" placeholder="请输入项目名称"></el-input>
          </el-form-item>
          <el-form-item label="咨询类别" class="projectNum" prop="counselTypePeriod" :rules="{ required: true, message: '请选择咨询类别', trigger: 'change' }">
            <el-select v-model="changeItem.counselTypePeriod" placeholder="请选择咨询类别">
              <el-option v-for="(r, i) in radios" :key="i" :label="r.counselTypeName" :value="r.counselTypeId"></el-option>
            </el-select>
          </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer"><el-button type="primary" @click="submitForm('changeItem', 'dialogVisible')" class="linear">确 定</el-button></span>
    </el-dialog>
    <!-- 创建合同、修改合同 -->
    <el-dialog
      :title="title"
      :visible.sync="contractDialogVisible"
      width="700px"
      transition="fade-in-linear"
      center
      @close="handleClose('changeContract')"
      :close-on-click-modal="false"
    >
      <el-form :model="changeContract" ref="changeContract" label-width="100px" class="demo-ruleForm" :inline-message="true">
        <div class="item-serial">
          <el-form-item label="项目编号" class="projectNum">
            <label for="i1">{{ transmittedData.projectNumber }}</label>
          </el-form-item>
        </div>
        <div class="add-card">
          <span>{{ itemName }}</span>
        </div>
        <div class="card-body">
          <div>
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>合同</span>
                <span class="item-serial-num">{{ transmittedData.projectNumber }}-{{ changeContract.contractNum }}</span>
              </div>
              <div class="text item contrat_error dynamically-add">
                <el-row>
                  <el-col :span="8">
                    <el-form-item
                       v-if="!isCompile"
                      label="指定人员"
                      prop="contractList[0].departmentId"
                      :class="{ top: false, right: false, bottom: true, left: false }"
                      :rules="{ required: true, message: '请选择指定人员', trigger: 'change' }">
                      <el-select v-model="changeContract.contractList[0].departmentId" placeholder="请选择指定人员" @change="getAbbr(changeContract.contractList[0])">
                        <el-option v-for="(i, k) in personSelect" :key="i.jobId" :label="i.userName" :value="i.departmentId"></el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item
                      v-if="isCompile"
                      label="指定人员"
                      prop="contractList[0].jobId"
                      :class="{ top: false, right: false, bottom: true, left: false }"
                      :rules="{ required: true, message: '请选择指定人员', trigger: 'change' }">
                      <el-select :disabled="btnIsDisabled" v-model="changeContract.contractList[0].jobId" placeholder="请选择指定人员" @change="getAbbr(changeContract.contractList[0])">
                        <el-option v-for="(i, k) in personSelect" :key="i.jobId" :label="i.userName" :value="i.jobId"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item
                      v-if="!isCompile"
                      label="合同编号"
                      prop="contractNum"
                      :class="{ top: false, right: false, bottom: true, left: false }"
                      :rules="{ required: true, message: '请输入合同编号', trigger: 'change' }">
                      <el-input v-model="changeContract.contractNum" placeholder="请输入合同编号"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-if="isCompile"
                      label="合同编号"
                      :class="{ top: false, right: false, bottom: true, left: false }">
                      <span>{{ compile }}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="跨部门合作" class="cooperateContract" v-if="!isCompile&&title != '修改合同'">
                        <el-checkbox-group v-model="changeContract.cooperateContract" @change="cooperateContractChange">
                          <el-checkbox :true-label="1" :false-label="0" name="cooperateContract"></el-checkbox>
                        </el-checkbox-group>
                      </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div style="border-bottom: 1px solid #e3e3e3;padding: 10px 0;" v-if="changeContract.cooperateContract">
                <span>添加部门</span>
                <el-button
                      style="width: 26px;
                			height: 16px !important;
                			line-height: 16px !important;
                			border-radius: 5px;
                			padding: 0 !important;
                			border: none;margin-left: 5px;"
                  type="primary" icon="el-icon-plus" class="linear" size="mini" @click="addPersonnel"></el-button>
              </div>
              <div class="text item contrat_error dynamically-add" v-for="(item,index) in changeContract.contractList" :key="index">
                <template v-if="index">
                  <el-form-item
                    label="指定人员"
                    :prop="'contractList.'+index+'.departmentId'"
                    :class="{ top: false, right: false, bottom: true, left: false }"
                    :rules="{ required: true, message: '请选择指定人员', trigger: 'change' }"
                  >
                    <el-select v-model="item.departmentId" placeholder="请选择指定人员" @change="getAbbr(item)">
                      <el-option v-for="(i, k) in personSelect" :key="i.jobId" :label="i.userName" :value="i.departmentId"></el-option>
                    </el-select>
                  </el-form-item>
                  <i data-v-3831ef16="" class="el-icon-close" @click="delPersonnel(index)"></i>
                </template>
              </div>
            </el-card>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" :disabled="btnIsDisabled" @click="submitForm('changeContract', 'contractDialogVisible')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 任务组件 -->
    <create-task ref="createTask" :contractList="contractList" :title="title" :taskform="taskform" :transmittedData="transmittedData"></create-task>
    <!-- 新建请款单 -->
    <dialog-request-payout ref="addRequestMenuForm" @submit="addRequestMenu"></dialog-request-payout>
  </div>
</template>

<script>
/* 创建任务 */
import createTask from './Create-task';
/* a3组件 */
import a3 from '../../common/components/a3';
/* 创建请款单*/
import dialogRequestPayout from './dialogRequestPayout';
/* 项目基本信息表组件 */
import projectBasicInformation from './projectBasicInformation/index';
import projectBasicInformationR from './projectBasicInformation/right';
/* 合同组件 */
import contract from './contract/index';
import contractR from './contract/right';
/* 补充协议组件 */
import agreement from './agreement/index';
import agreementR from './agreement/right';
/* 项目组件 */
import project from './project/index';
import projectR from './project/right';
/* 任务组件 */
import task from './task/index';
/* 请款组件 */
import requestPayout from './requestPayout/index';
import requestPayoutR from './requestPayout/right';
import contractForm from './contractForm.js';
export default {
  data() {
    return {
      itemName: '',
      theFormDisabled: false, // 禁用表单,
      showView: true, // 查看视图
      theFormData: {
        indexContract: {
          // 合同index页
          form: Object.assign({}, contractForm.indexContract)
        },
        baseContract: {
          // 基础合同数据
          form: Object.assign({}, contractForm.baseContract),
          controlObj: {} // 控制对象
        },
        formCostConsulting: {
          // 造价合同
          form: Object.assign({}, contractForm.formCostConsulting)
        },
        formConstructionProjectBiddingAgency: {
          // 建设工程招标代理合同
          form: Object.assign({}, contractForm.formConstructionProjectBiddingAgency)
        },
        formProjectManagementConsultingService: {
          // 工程管理咨询服务合同
          form: Object.assign({}, contractForm.formProjectManagementConsultingService)
        },
        formSupervision: {
          // 工程管理咨询服务合同
          form: Object.assign({}, contractForm.formSupervision)
        }
      }, // 表单数据
      transmittedData: {
        // 项目编号
        projectNumber: '',
        // 阶段说明
        periodDesc: '',
        taskMaterialInfo: '',
        onlyOfficeFlg: false
      },
      btnIsDisabled: false,
      contractList: [],
      view_index: '', // 中间视图
      view_right: '', // 右侧视图
      currentContract: {}, // 合同组件的传值对象
      isCompile: false, // 是否是合同
      compile: '',// 合同编号(分配合同编撰人使用)
      storageData: this.SStorage.getItem('/page/work/contract/FoundItem'), // 项目信息
      dialogVisible: false, // 修改项目弹窗
      contractDialogVisible: false,
      /* 修改合同弹窗 */
      contract: false, // 是否是合同组件
      /* 合同 */
      title: '',
      showImg: false,
      imgSrc: '',
      imgAlt: '',
      changeItem: {
        // 公司id
        companyId: '',
        // 项目id
        projectId: '',
        //项目名称
        projectName:'',
        //咨询类别
        counselTypePeriod:''
      },
      changeContract: {
        // 合同id
        contractId: '',
        // 合同编号
        contractNum: '',
        contractList:[{
            userName: '', // 人员显示名称（部门+姓名）
            departmentId:'',// 部门id
            jobId:''// 职位id
          }],
        // 跨部门合作
        cooperateContract:0
      },
      // 表单验证
      rules: {
        companyId: [
          {
            required: true,
            message: '请输入项目编号',
            trigger: 'blur'
          }
        ]
      },
      // 项目编号前缀
      projectNumPrefix: '',
      // 公司缩写下拉列表
      condenseSelect: [],
      // 公司缩写
      condense: '',
      // 项目编号
      projectNum: '',
      // 人员下拉列表
      personSelect: [],
      // 表单项间距
      gutter: 40,
      taskId: '',
      //请款单ID
      requestMenuId: '',
      currentContractId: '',
      currentContractName: '',
      // 任务的表单
      taskform: {
        // 其他组员表单
        smallForm: [],
        // 其他组员表单(传到后台参数)
        userList: [],
        // 咨询类型数据
        consultList: [],
        // 指定人员信息列表数据
        tableData: [],
        // 表格是否显示
        showTable: false,
        // 下箭头
        iconDown: true,
        // 上箭头
        iconUp: false,
        // 禁用
        disabled: false,
      },
      radios: [],
    };
  },
  watch:{
    contractDialogVisible:{
      handler(){
        let vm = this;
        if(!vm.contractDialogVisible){
          vm.resetChangeContract();// 重置表单信息
          vm.$nextTick(function () {
            // 清空表单校验
            if(vm.$refs['changeContract'])vm.$refs['changeContract'].clearValidate();
          })
        }
      },
      immediate:true
    }
  },
  components: {
    contract: contract,
    'contract-r': contractR,
    agreement: agreement,/* 补充协议 */
    'agreement-r': agreementR,/* 补充协议右 */
    project: project,
    'project-r': projectR,
    'project-basic-information':projectBasicInformation,/* 项目基本信息表index */
    'project-basic-information-r':projectBasicInformationR,/* 项目基本信息表right */
    task: task,
    // 'task-r': taskR,
    'request-payout': requestPayout,
    'request-payout-r': requestPayoutR,
    'create-task': createTask,
    a3: a3,
    'dialog-request-payout': dialogRequestPayout
  },
  // 实例销毁之前调用
  beforeDestroy() {
    let vm = this;
    vm.bus.$off('click');
    vm.bus.$off('aside-task-download');
  },
  mounted() {
    let vm = this;
    // 绑定全局事件globalEvent
    // 提交触发
    vm.bus.$on('allocation', val => {
      vm.changeContract.contractId = val.id;
      vm.editProject('contractDialogVisible', 'compile',val);
    });
    // 合同基本信息显示
    vm.bus.$on('click', val => {
      let vm = this;
      // 补充协议子集
      let supplementAgreementSub = val.treeNodeLevel === "5" && val.objType === "supplementAgreement";
      // 任务、请款单、补充协议
      let taskRequestSupplementAgreement = val.treeNodeLevel === "4" && val.objType !== "baseInfo";
      // 点击"合同"两字、点击"任务"两字、点击"请款单"三个字、点击"补充协议"四个字时，停留在上一个页面，不做任何变动
      // 暂时解决方案（补充协议的子集的时候也停留在上一个页面，不做任何变动，因为补充协议子集暂时还没有页面，等有页面之后再放开校验）
      if (!(val.treeNodeLevel === "2" || taskRequestSupplementAgreement || supplementAgreementSub)) {
        vm.showView = false;
        vm.transmittedData = Object.assign({}, vm.transmittedData, val);
        vm.transmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
        vm.resetContractForm(); // 重置合同表单
        vm.baseContract(vm.transmittedData);
        vm.$nextTick(() => {
          vm.showView = true;
        });
      }
    });
    // 合同基本信息显示
    vm.bus.$on('showAddRequestMenuForm', val => {
      vm.currentContractId = val.id;
      vm.currentContractName = val.label;
      vm.$nextTick(() => {
        vm.$refs.addRequestMenuForm.show = true;
      });
    });
    // 监听选择文件事件
    vm.bus.$on('checkFile', val => {
      vm.checkFile(val);
    });
    // 监听修改项目事件
    vm.bus.$on('editProject', val => {
      // 设置项目id
      vm.changeItem.projectId = val.id;
      vm.editProject('dialogVisible');
    });
    // 监听删除项目事件
    vm.bus.$on('delProject', val => {
      // 设置项目id
      vm.changeItem.projectId = val.id;
      vm.delProject('item');
    });
    // 监听添加合同事件
    vm.bus.$on('addContract', val => {
      // 设置项目id
      vm.changeItem.projectId = vm.storageData.projectId;
      // 设置项目编号
      vm.projectNum = val.label;
      vm.editProject('contractDialogVisible', 'add');
    });
    // 监听删除合同事件
    vm.bus.$on('delContract', val => {
      // 设置合同id
      vm.changeContract.contractId = val.id;
      vm.delProject('contract');
    });
    // 监听修改合同事件
    vm.bus.$on('editContract', val => {
      // 设置项目id
      vm.changeItem.projectId = vm.storageData.projectId;
      // 设置项目编号
      vm.projectNum = vm.storageData.projectNum;
      // 设置合同编号
      vm.changeContract.contractNum = val.label.substring(val.label.indexOf('-') + 1);
      // 设置合同id
      vm.changeContract.contractId = val.id;
      // 调用编辑合同方法
      vm.editProject('contractDialogVisible', 'change');
    });

    // 创建任务、修改任务事件
    vm.bus.$on('createTask', (val, index) => {
      // 弹窗显示之后
      if (vm.$refs.createTask) {
        // 显示弹窗
        vm.$refs.createTask.$data.show = true;

        // 部门id
        vm.changeContract.departmentId = val.departmentId;

        if (index == '0') {
          // 如果是创建任务
          // 弹窗标题
          vm.title = '创建任务';
          // 设置合同编号
          vm.changeContract.contractNum = val.label;
          // 设置合同id
          vm.changeContract.contractId = val.id;
          // 重置表单数据
          vm.taskform = {
            // 其他组员表单
            smallForm: [],
            // 其他组员表单(传到后台参数)
            userList: [],
            // 咨询类型数据
            consultList: [],
            // 指定人员信息列表数据
            tableData: [],
            // 表格是否显示
            showTable: false,
            // 下箭头
            iconDown: true,
            // 上箭头
            iconUp: false,
            // 禁用
            disabled: false,
          };
          // 获取合同类型列表
          vm.getContractTypeList();
        } else if (index == '1') {
          // 如果是修改任务
          // 弹窗标题
          vm.title = '修改任务';
          // 设置任务id
          vm.taskId = val.id;
          // 获取任务详情信息
          vm.getTaskInfo(val);
        }
        // 获取编制成果文件人列表
        vm.getUserList();

        if(vm.$refs.createTask.$data.show){
          // 触发提交审核-编制成果文件事件
          this.bus.$emit('searchDictCounselTypeListByContractTypeId',this.contractList);
        }

      }
    });

    // 监听删除任务事件
    vm.bus.$on('delRequestMenu', val => {
      // 设置任务id
      vm.requestMenuId = val.id;
      // 删除函数
      vm.delRequestMenu();
    });
    // 监听删除任务事件
    vm.bus.$on('delTask', val => {
      // 设置任务id
      vm.taskId = val.id;
      // 删除函数
      vm.delProject('task');
    });

    // 监听下载任务文件事件
    vm.bus.$on('aside-task-download', val => {
      let tempUrl = '/taskExecute/downLoad/downLoadTaskExecuteExecl?taskId=' + val.id;
      this.downloadFile(tempUrl, '', true);
    });

    // 监听禁用表单事件
    vm.bus.$on('theFormDisabled', val => {
      vm.theFormDisabled = val;
    });

    // 监听是否展示onlyOffice事件
    vm.bus.$on('onlyOfficeFlg', val => {
      this.transmittedData.onlyOfficeFlg = val;
    });

    // 查询项目编号
    this.getProjectNum();
    //查询咨询类别下拉列表
    this.searchCounselTypePeriodList();
  },
  methods: {
    // 获取编制成果文件人列表
    getUserList() {
      // 查询编制成果文件人
      this.apiAddress('/task/query/searchUserListByDepartmentId', {
        // 任务id
        taskId: this.taskId,
        // 合同id
        contractId: this.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置执行人
            this.$refs.createTask.$data.personList = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    // 获取合同类型列表
    getContractTypeList() {
      // 查询合同类别
      this.apiAddress('/task/query/searchDictContractTypeListByDepartmentId', {
        // 合同id
        contractId: this.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置合同类别列表
            this.contractList = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    // 获取任务详情信息
    getTaskInfo(val) {
      let vm = this;
      // 查询任务详情
      this.apiAddress('/task/query/searchTaskInfo', {
        // 任务id
        taskId: this.taskId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置合同编号
            this.changeContract.contractNum = res.data.dataVO.contractNum;

            // 添加执行人列表显示样式
            for (let i = 0; i < res.data.dataVO.smallForm.length; i++) {
              // 表格是否显示
              res.data.dataVO.smallForm[i].showTable1 = false;
              // 下箭头
              res.data.dataVO.smallForm[i].iconDown1 = true;
              // 上箭头
              res.data.dataVO.smallForm[i].iconUp1 = false;
              // 结束时间
              res.data.dataVO.smallForm[i].endDate = new Date(res.data.dataVO.smallForm[i].endDate);
            }
            // 设置任务详情
            vm.taskform = vm.tool.deepCopy(
              Object.assign(
                {
                  // 其他组员表单
                  smallForm: [],
                  // 其他组员表单(传到后台参数)
                  userList: [],
                  // 咨询类型数据
                  consultList: [],
                  // 指定人员信息列表数据
                  tableData: [],
                  // 表格是否显示
                  showTable: false,
                  // 下箭头
                  iconDown: true,
                  // 上箭头
                  iconUp: false,
                  // 审核状态
                  verifyState: val.verifyState,
                  // 禁用
                  disabled: true,
                },
                res.data.dataVO
              ),
              vm.tool.deepCopy
            );

          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    // 查询项目编号
    getProjectNum() {
      let vm = this;
      // 提交审核
      this.apiAddress('/project/query/searchProjectInfo', {
        // 项目id
        projectId: this.storageData.projectId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 隐藏基础合同信息
            this.contract = false;
            vm.transmittedData = Object.assign(vm.transmittedData, {
              projectNumber: res.data.dataVO.projectNum,
              contractNumber: ''
            });
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    //计算时间区间
    dateDiff(targetDate) {
      return parseInt(Math.abs(targetDate[1].getTime() - targetDate[0].getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
    },
    // 提交基础合同数据
    SubmitContract(val) {},
    handleClose(index) {
      this.$refs[index].resetFields();
    },
    baseContract(val) {
      let vm = this;
      // 项目
      if (val.treeNodeLevel == 1) {
        // 显示项目index
        this.view_index = 'project';
        // 显示项目right
        this.view_right = 'project-r';

        // 合同
      } else if (val.treeNodeLevel == 3) {
        // 显示合同index
        this.view_index = 'contract';
        // 显示合同right
        this.view_right = 'contract-r';
        // 显示合同基础信息
        this.contract = true;

        // 合同编号
        vm.transmittedData = Object.assign(vm.transmittedData, {
          contractNumber: val.label
        });
        // 合同id
        this.changeContract.contractId = val.id;

        // 检索合同详细信息
        this.getContractDetailInfo();

        // 基本信息、任务、请款单、补充协议
      } else if (val.treeNodeLevel == 4) {

        // 显示项目基本信息表
        if("baseInfo" === val.objType){
          // 显示项目基本信息index
          this.view_index = 'project-basic-information';
          // 显示项目基本信息right
          this.view_right = 'project-basic-information-r';
        }

        // 任务
      } else if (val.treeNodeLevel == 5) {

        // 显示具体任务
        if (val.objType === "task") {
          // 显示任务index
          this.view_index = 'task';
          // // 显示任务right
          this.view_right = '';

          // 显示具体请款单
        } else if (val.objType == 'requestMent') {
          // 显示请款单index
          this.view_index = 'request-payout';
          // 显示请款单right
          this.view_right = 'request-payout-r';
          // 传向子组件参数(请款单ID)
          this.transmittedData = {
            id: val.id,
            level: val.treeNodeLevel,
            isMainHeadFlag: val.isMainHeadFlag
          };

          // 显示具体补充协议
        } else if (val.objType == 'supplementAgreement') {
          // 等补充协议功能添加之后，再用下面的代码引入页面
          // // 显示补充协议index
          // this.view_index = '';
          // // 显示补充协议right
          // this.view_right = '';

        }

        // 任务阶段
      } else if (val.treeNodeLevel == 6) {
        // 显示任务index
        this.view_index = 'task';
        // 显示任务right
        this.view_right = '';
      }
    },
    // 获取部门缩写,并且设置数据
    getAbbr(item_) {
      let vm = this;
      let obj = {};
      let repetition = 0;// 重复次数
      // 获取下拉框的所有值 personSelect:下拉框
      obj = this.personSelect.find(item => {
        if(vm.isCompile){
          return item.jobId === item_.jobId;
        }else{
          return item.departmentId === item_.departmentId;
        }
      });
      for (let i = 0, len = vm.changeContract.contractList.length; i < len; i++) {
        let li = vm.changeContract.contractList[i];
        if(li.departmentId==item_.departmentId&&++repetition>1){
          item_.userName = '';
          item_.departmentId = '';
          item_.jobId = '';
          item_.abbr = '';
          // 计算合同编号
          vm.countContractNum();
          return vm.stateDialog('error','不可以选择重复的指定人员!');
        };
      }
      // 设置用户id
      item_.userName = obj.userName?obj.userName:'';
      item_.departmentId = obj.departmentId?obj.departmentId:'';
      item_.jobId = obj.jobId?obj.jobId:'';
      item_.abbr = obj.abbr?obj.abbr:'';
      // 计算合同编号
      if(!vm.isCompile)vm.countContractNum();
    },
    /**
     * @method 计算部门缩写
     */
    countContractNum(){
      let vm = this;
      let a = '',b = '',c = '';// 三个指定人员字符串拼接片段
      for (let i = 0, len = vm.changeContract.contractList.length; i < len; i++) {
        let li = vm.changeContract.contractList[i];
        switch (i){
          case 0:
            a = li.abbr?li.abbr:'';
            break;
          case 1:
            b = li.abbr?'/'+li.abbr:'';
            break;
          case 2:
            c = li.abbr?'/'+li.abbr:'';
            break;
        }
      }
      // 通过项目id和部门id查询合同数
      this.apiAddress('/project/query/searchContractCountByProjectIdAndDepartmentId', {
        // 项目id
        projectId: vm.changeItem.projectId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置合同编号（部门缩写+部门项目下的合同数+1）如果abc为空字符串则设置为空
            vm.changeContract.contractNum = a+b+c?a+b+c+res.data.dataVO.contractCount:'';
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 获取指定人员下拉列表
    getPersonList() {
      const vm = this;
      // 查询人员
      this.apiAddress('/project/query/excSearchPersonList', {})
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置指定人员下拉信息
            vm.personSelect = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    //获取合同编撰人
    getContractWriter() {
      const vm = this;
      this.apiAddress('/project/query/queryContractWriter', {
        contractId: vm.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          const deptId = res.data.dataVO.departmentId;
          if (res.data.status == 'success') {
            vm.changeContract.contractList[0].jobId = res.data.dataVO.applyUserId?res.data.dataVO.applyUserId:'';
            // 设置指定人员下拉信息
            if (res.data.dataVO.applyUserName && res.data.dataVO.applyUserName != '' && res.data.dataVO.applyUserId && res.data.dataVO.applyUserId != '') {
              vm.btnIsDisabled = true;
            } else {
              vm.btnIsDisabled = false;
            }
            //设置合同编号
            vm.compile = res.data.dataVO.contractNum;
            //根据合同部门ID获取指定人员下拉列表
            this.getDepartmentPersonList(deptId);
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    // 获取指定人员下拉列表
    getDepartmentPersonList(deptId) {
      const vm = this;
      // 查询人员
      this.apiAddress('/project/query/excSearchPersonListByInvolvedDepartment', {
        // 部门id
        departmentId: deptId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置指定人员下拉信息
            vm.personSelect = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    // 获取公司缩写,并且设置数据
    getCondense(val) {
      let obj = {};
      // 获取下拉框的所有值 condenseSelect:下拉框
      obj = this.condenseSelect.find(item => {
        return item.companyId === val;
      });
      // 设置公司缩写
      this.condense = obj.condense;
    },
    // 获取项目信息
    getProjectInfo() {
      const vm = this;
      // 查询项目编号前缀
      this.apiAddress('/project/query/searchProjectInfo', {
        projectId: vm.changeItem.projectId // 项目id
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置项目编号前缀
            vm.projectNumPrefix = res.data.dataVO.projectNumPrefix;
            // 设置公司缩写
            vm.condense = res.data.dataVO.condense;
            // 设置公司id
            if(res.data.dataVO.companyId)vm.changeItem.companyId = res.data.dataVO.companyId;
            // 设置项目名称
            if(res.data.dataVO.projectName)vm.changeItem.projectName = res.data.dataVO.projectName;
            // 咨询类别
            if(res.data.dataVO.counselTypePeriod)vm.changeItem.counselTypePeriod = res.data.dataVO.counselTypePeriod;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 获取公司缩写下拉列表
    getCondenseSelect() {
      const vm = this;
      // 查询公司缩写
      this.apiAddress('/project/query/searchCondenseList', {})
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置公司缩写下拉数据列表
            vm.condenseSelect = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        }).catch(e => {
          this.stateDialog('error');
        });
      },
    // 获取合同详细信息
    getContractDetailInfo() {
      const vm = this;
      // 查询合同信息
      this.apiAddress('/contract/query/excSearchContractDetailInfo', {
        // 合同id
        contractId: vm.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 取得合同详细信息
            let retDataVO = res.data.dataVO;
            if (!vm.transmittedData.taskMaterialInfo) vm.$set(vm.transmittedData, 'taskMaterialInfo', {});
            // office组件需要传入的参数
            vm.transmittedData.taskMaterialInfo = {
              fileName: retDataVO.reviewFileName,
              key: retDataVO.onlyOfficeEditKey,
              downloadUrl: retDataVO.onlyOfficeAbsDownLoadPath,
              saveUrl: retDataVO.reviewFilePath
            };

            // 合同Index页数据
            let indexContract = vm.theFormData.indexContract;
            // 基本合同组件
            let tempBaseContract = vm.theFormData.baseContract;

            let projectTypeList = [];
            // 项目类别及其属性赋值
            if (retDataVO.projectTypeList && retDataVO.projectTypeList.length) {
              let tempProjectFeatureObj = {};
              // 项目类型不为空时
              for (let i = 0; i < retDataVO.projectTypeList.length; i++) {
                let projectType = parseInt(retDataVO.projectTypeList[i].projectType);
                projectTypeList.push(projectType);
                // 项目属性显示
                tempBaseContract.controlObj[projectType] = true;
                // 初始化临时项目属性
                tempProjectFeatureObj = {};
                tempProjectFeatureObj = retDataVO.projectFeatureList[i];

                // 项目属性赋值
                switch (retDataVO.projectTypeList[i].projectType) {
                  case '0':
                    // 住宅-+
                    tempBaseContract.form.residential = tempProjectFeatureObj;
                    break;
                  case '1':
                    // 商业建筑
                    tempBaseContract.form.commercialBuildings = tempProjectFeatureObj;
                    break;
                  case '2':
                    // 酒店及休闲娱乐
                    tempBaseContract.form.hotelAndLeisureEntertainment = tempProjectFeatureObj;
                    break;
                  case '3':
                    // 工业建筑
                    tempBaseContract.form.industrialArchitecture = tempProjectFeatureObj;
                    break;

                  case '4':
                    // 教育
                    tempBaseContract.form.education = tempProjectFeatureObj;
                    break;

                  case '5':
                    // 文化及体育设施
                    tempBaseContract.form.culturalAndSportsFacilities = tempProjectFeatureObj;
                    break;

                  case '6':
                    // 电力
                    tempBaseContract.form.power = tempProjectFeatureObj;
                    break;

                  case '7':
                    // 铁路、道路及桥梁建设
                    tempBaseContract.form.railwayRoadBridgeConstruction = tempProjectFeatureObj;
                    break;

                  case '8':
                    // 水务
                    tempBaseContract.form.water = tempProjectFeatureObj;
                    break;

                  case '9':
                    // 公用事业及城市基础设施
                    tempBaseContract.form.publicUtilitiesUrbanInfrastructure = tempProjectFeatureObj;
                    break;

                  case '10':
                    // 水运、交通
                    tempBaseContract.form.waterTransportationAndTransportation = tempProjectFeatureObj;
                    break;
                  // 其他
                  case '11':
                    // 其他
                    tempBaseContract.form.others = tempProjectFeatureObj;
                    break;
                }
              }
            }

            // 预计收款时间赋值
            if (retDataVO.predictRequestDateList != null && retDataVO.predictRequestDateList.length > 0) {
              // 预计收款时间不为空时
              // 初始化预计收款时间
              tempBaseContract.form.characteristic = [];
              let predictRequestDateItem;
              for (let i = 0; i < retDataVO.predictRequestDateList.length; i++) {
                predictRequestDateItem = {};
                // 预计收款时间
                predictRequestDateItem.startDate = new Date(retDataVO.predictRequestDateList[i].startDate);
                // 预计收款金额
                predictRequestDateItem.receivedAmount = retDataVO.predictRequestDateList[i].receivedAmount;
                // 是否暂定
                predictRequestDateItem.isProvisional = retDataVO.predictRequestDateList[i].isProvisional;
                tempBaseContract.form.characteristic.push(predictRequestDateItem);
              }
            }
            // index页数据
            indexContract.form = Object.assign(indexContract.form, retDataVO);
            // 基本合同数据
            tempBaseContract.form = Object.assign({}, tempBaseContract.form, retDataVO, { projectTypeList: projectTypeList });
            tempBaseContract.controlObj = Object.assign({}, tempBaseContract.controlObj);
            // 合同类型以及模板类型赋值
            if (retDataVO.contractType == '0') {
              // 选择自定义合同时  TODO
            } else if (retDataVO.contractType == '1') {
              // 选择模板合同时
              if(undefined == retDataVO.formworkContent){
                return;
              }
              // 模板内容json转换成对象
              let formworkContentObj = JSON.parse(retDataVO.formworkContent);
              // 判断合同模板类型
              switch (retDataVO.formworkType) {
                case '0':
                  // 造价咨询合同
                  let tempFormCostConsulting = {};
                  // 省份
                  tempFormCostConsulting.province = formworkContentObj.provinceName;
                  // 地区
                  tempFormCostConsulting.region = formworkContentObj.districtName;
                  // 工程规模
                  tempFormCostConsulting.generalize = formworkContentObj.projectScale;
                  // 资金来源
                  tempFormCostConsulting.fund = formworkContentObj.fundSource;
                  // 建设工期或周期
                  tempFormCostConsulting.period = formworkContentObj.buildPeriod;
                  // 双方约定的服务范围及工作内容
                  tempFormCostConsulting.scope = formworkContentObj.workContent;
                  // 服务期限
                  if (formworkContentObj.startYear != '') {
                    // 拼成开始和结束日期
                    let tempStartDate = new Date(parseInt(formworkContentObj.startYear), parseInt(formworkContentObj.startMonth) - 1, parseInt(formworkContentObj.startDay));
                    let tempEndDate = new Date(parseInt(formworkContentObj.endYear), parseInt(formworkContentObj.endMonth) - 1, parseInt(formworkContentObj.endDay));
                    tempFormCostConsulting.deadline = [];
                    // 设置开始和结束日期
                    tempFormCostConsulting.deadline.push(tempStartDate);
                    tempFormCostConsulting.deadline.push(tempEndDate);
                  }

                  // 总合同份数
                  tempFormCostConsulting.copies = formworkContentObj.totalContractCount;
                  // 委托人合同份数
                  tempFormCostConsulting.entrust = formworkContentObj.trusterContractCount;
                  // 咨询人合同份数
                  tempFormCostConsulting.consult = formworkContentObj.consultantContractCount;
                  // 委托人代表
                  tempFormCostConsulting.representative = formworkContentObj.trusterRepresent;
                  // 团队人员
                  tempFormCostConsulting.personnel = formworkContentObj.personCount;

                  // 支付方式
                  if (formworkContentObj.paymentMethod != null && formworkContentObj.paymentMethod != '') {
                    tempFormCostConsulting.pay = parseInt(formworkContentObj.paymentMethod);
                  }

                  if (formworkContentObj.paymentMethod == '0') {
                    // 一次性支付时
                    // 咨询成果报告出具几日内支付
                    tempFormCostConsulting.input = formworkContentObj.paymentInfo;
                  } else if (formworkContentObj.paymentMethod == '1') {
                    // 分期支付时
                    // 分期信息
                    tempFormCostConsulting.desc = formworkContentObj.paymentInfo;
                  }
                  // 争议解决
                  tempFormCostConsulting.dispute = formworkContentObj.courtName;
                  // 送达接收人
                  tempFormCostConsulting.recipient = formworkContentObj.receiver;
                  // 送达地点
                  tempFormCostConsulting.site = formworkContentObj.deliveryPlace;
                  // 酬金及计取方式为
                  tempFormCostConsulting.reward = formworkContentObj.calculationMethod;

                  // 服务范围及工作内容、酬金列表
                  let tempServiceAndWorkContentList = formworkContentObj.serviceAndWorkContentList;
                  // 预计收款时间赋值
                  if (tempServiceAndWorkContentList != null && tempServiceAndWorkContentList.length > 0) {
                    // 服务范围及工作内容、酬金列表不为空时  tempFormCostConsulting
                    //                    let tempAform = this.$refs['contract'].$refs['formCostConsulting'].$refs['serviceAndWorkContent']._data.Aform ;
                    let tempAform = (tempFormCostConsulting.serviceAndWorkContent = {});
                    // 初始化预计收款时间
                    tempAform.data = [];
                    let dataItem;
                    for (let i = 0; i < tempServiceAndWorkContentList.length; i++) {
                      dataItem = {};
                      // 服务阶段
                      dataItem.serve =
                        tempServiceAndWorkContentList[i].servicePhaseId + ',' + tempServiceAndWorkContentList[i].servicePhase + ',' + tempServiceAndWorkContentList[i].serviceRange;
                      // 工作内容
                      dataItem.content = [];
                      if (tempServiceAndWorkContentList[i].workContentList && tempServiceAndWorkContentList[i].workContentList.length > 0) {
                        for (let j = 0; j < tempServiceAndWorkContentList[i].workContentList.length; j++) {
                          if (tempServiceAndWorkContentList[i].workContentList[j].isCheckedFlg == '1') {
                            // 选中状态
                            dataItem.content.push(parseInt(tempServiceAndWorkContentList[i].workContentList[j].workContentType) + 1);
                          }
                        }
                      }

                      // 收费基数
                      dataItem.charge = {};
                      dataItem.charge.money = tempServiceAndWorkContentList[i].chargeRadix;
                      // 收费基数暂定
                      dataItem.charge.state = tempServiceAndWorkContentList[i].isChargeRadixProvisional;
                      // 收费标准
                      dataItem.standard = {};
                      dataItem.standard.money = tempServiceAndWorkContentList[i].chargeStandard;
                      // 收费标准暂定
                      dataItem.standard.state = tempServiceAndWorkContentList[i].isChargeStandardProvisional;
                      dataItem.sum = {};
                      // 酬金金额
                      dataItem.sum.money = tempServiceAndWorkContentList[i].remuneration;
                      // 酬金金额暂定
                      dataItem.sum.state = tempServiceAndWorkContentList[i].isRemunerationProvisional;
                      // 备注
                      dataItem.remark = tempServiceAndWorkContentList[i].remark;
                      let manyCheckBoxReg = /其他|工程实施阶段造价/g;
                      let oneCheckBoxReg = /资金使用计划/g;
                      // 工作内容列表
                      let workContentList = [];
                      // 工作内容元素
                      let workContentItem = {};

                      if (manyCheckBoxReg.test(tempServiceAndWorkContentList[i].serviceRange)) {
                        // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                        dataItem.input = true;
                        // 设置输入文本内容
                        dataItem.details = tempServiceAndWorkContentList[i].workContent;
                        // checkbox隐藏
                        dataItem.checkbox = false;
                        dataItem.checkbox1 = false;
                      } else if (oneCheckBoxReg.test(tempServiceAndWorkContentList[i].serviceRange)) {
                        // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                        dataItem.input = false;
                        // 设置输入文本
                        dataItem.details = '';
                        // 多个checkbox隐藏
                        dataItem.checkbox = false;
                        // 1个checkbox隐藏
                        dataItem.checkbox1 = true;
                      } else {
                        // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                        dataItem.input = false;
                        // 设置输入文本
                        dataItem.details = '';
                        // 多个checkbox隐藏
                        dataItem.checkbox = true;
                        // 1个checkbox隐藏
                        dataItem.checkbox1 = false;
                      }
                      tempAform.data.push(dataItem);
                    }
                  }

                  // 咨询人提交成果列表
                  let tempAchievementFileList = formworkContentObj.achievementFileList;
                  // 预计收款时间赋值
                  if (tempAchievementFileList != null && tempAchievementFileList.length > 0) {
                    // 咨询人提交成果列表不为空时
                    //                    let tempBform = this.$refs['contract'].$refs['formCostConsulting'].$refs['achievementFile']._data.Bform;
                    let tempBform = (tempFormCostConsulting.achievementFile = {});
                    // 初始化列表
                    tempBform.data = [];
                    let dataItem;
                    for (let i = 0; i < tempAchievementFileList.length; i++) {
                      dataItem = {};
                      // 服务范围
                      dataItem.serve = tempAchievementFileList[i].servicePhaseId + ',' + tempAchievementFileList[i].servicePhase;
                      // 成果文件名称
                      dataItem.name = tempAchievementFileList[i].achievementFileName;
                      // 成果文件组成
                      dataItem.constitute = tempAchievementFileList[i].achievementFileCompose;
                      // 提交时间
                      dataItem.date = tempAchievementFileList[i].submitDate;
                      // 份数
                      dataItem.copies = tempAchievementFileList[i].fileCount;
                      // 质量标准
                      dataItem.standard = tempAchievementFileList[i].qualityStandard;

                      tempBform.data.push(dataItem);
                    }
                  }

                  // 委托人提供资料列表
                  let tempProvideDataList = formworkContentObj.provideDataList;
                  if (tempProvideDataList != null && tempProvideDataList.length > 0) {
                    // 委托人提供资料列表不为空时
                    //                    let tempCform = this.$refs['contract'].$refs['formCostConsulting'].$refs['provideData']._data.Cform;
                    let tempCform = (tempFormCostConsulting.provideData = {});
                    // 初始化列表
                    tempCform.data = [];
                    let dataItem;
                    for (let i = 0; i < tempProvideDataList.length; i++) {
                      dataItem = {};
                      // 委托人名称
                      dataItem.name = tempProvideDataList[i].trusterName;
                      // 资料分数
                      dataItem.copies = tempProvideDataList[i].dataCount;
                      // 提供时间
                      let provideYear = '';
                      let provideMonth = '';
                      let provideDay = '';
                      let time = '';
                      if (tempProvideDataList[i].provideDate) {
                        // 提供时间
                        let provideDate = tempProvideDataList[i].provideDate
                          .replace('年', '-')
                          .replace('月', '-')
                          .replace('日', '')
                          .split('-');
                        provideYear = provideDate[0];
                        provideMonth = provideDate[1];
                        provideDay = provideDate[2];
                        time = new Date(provideYear, parseInt(provideMonth) - 1, provideDay);
                      }
                      dataItem.time = time;
                      // 备注
                      dataItem.remark = tempProvideDataList[i].remark;

                      tempCform.data.push(dataItem);
                    }
                  }
                  Object.assign(vm.theFormData.formCostConsulting.form, tempFormCostConsulting);
                  break;
                case '1':
                  // 建设工程招标代理合同
                  Object.assign(vm.theFormData.formConstructionProjectBiddingAgency.form, formworkContentObj);
                  break;
                case '2':
                  // 工程管理咨询服务合同
                  Object.assign(vm.theFormData.formProjectManagementConsultingService.form, formworkContentObj);
                  break;
                case '3':
                  // 监理合同
                  let tempFormSupervision = vm.theFormData.formSupervision.form;
                  Object.assign(tempFormSupervision, formworkContentObj);
                  // 监理期限
                  tempFormSupervision.superviseDate = [];
                  if (formworkContentObj.startYear) {
                    // 开始时间
                    tempFormSupervision.superviseDate.push(
                      new Date(parseInt(formworkContentObj.startYear), parseInt(formworkContentObj.startMonth) - 1, parseInt(formworkContentObj.startDay))
                    );
                    // 结束时间
                    tempFormSupervision.superviseDate.push(
                      new Date(parseInt(formworkContentObj.endYear), parseInt(formworkContentObj.endMonth) - 1, parseInt(formworkContentObj.endDay))
                    );
                  }
                  // 订立时间
                  if (formworkContentObj.signYear) {
                    // 订立时间
                    tempFormSupervision.signDate = new Date(parseInt(formworkContentObj.signYear), parseInt(formworkContentObj.signMonth) - 1, formworkContentObj.signDay);
                  }
                  break;
              }
            }
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },

    // 获取合同信息
    getContractInfo() {
      const vm = this;
      // 查询合同信息
      this.apiAddress('/project/query/excSearchContractInfo', {
        // 合同id
        contractId: vm.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置指定人员
            vm.changeContract.contractList = [{
                userName: res.data.dataVO.userName, // 人员显示名称（部门+姓名）
                departmentId:res.data.dataVO.departmentId,// 部门id
                jobId:res.data.dataVO.jobId// 职位id
              }];
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },

    // 删除项目
    deleteProject() {
      const vm = this;
      // 发送删除请求
      this.apiAddress('/project/delete/deleteProjectInfo', {
        projectId: vm.changeItem.projectId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.stateDialog('success', res.data.message);
            // 跳到项目列表页面
            this.$router.push({
              path: '/page/item'
            });
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 删除合同
    deleteContract() {
      const vm = this;
      // 发送删除请求
      this.apiAddress('/project/delete/deleteContractInfo', {
        contractId: vm.changeContract.contractId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.stateDialog('success', res.data.message);
            // 默认选中到上级项目
            let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
            sessionData.businessId = sessionData.projectId;
            vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
            // 触发事件,重新刷新项目树
            vm.bus.$emit('asideRefresh');
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 删除任务
    deleteTask() {
      const vm = this;
      // 发送删除请求
      this.apiAddress('/task/delete/deleteTaskInfo', {
        taskId: vm.taskId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.stateDialog('success', res.data.message);
            let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
            sessionData.businessId = sessionData.contractId;
            vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
            // 触发事件,重新刷新项目树
            vm.bus.$emit('asideRefresh');
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    /**
     * @method 修改项目 添加合同 修改合同
     * @param {String} dialog 会话框
     * @param {Object} type 类型
     * @param {Object} node 左侧树点击节点信息
     */
    editProject(dialog, type,node) {
      let vm = this;
      this[dialog] = true;
      if (type == 'add') {
        this.title = '新增合同';
        this.isCompile = false;

        this.itemName = '指定业务部门副总';

        // 获取指定人员下拉列表
        this.getPersonList();
      } else if (type == 'compile') {
        this.title = '分配合同编撰人';
        this.isCompile = true;
        this.projectNum = this.storageData.projectNum;
        this.itemName = '分配合同编撰人';
        let index = node.label.indexOf('-');
        vm.changeContract.contractNum = node.label.slice(index+1);
        //查询合同编撰人
        this.getContractWriter();
      } else {
        // 如果是修改项目
        if (dialog == 'dialogVisible') {
          // 设置弹窗头部题目
          this.title = '修改项目';

          // 获取项目信息
          this.getProjectInfo();

          // 获取公司缩写下拉列表
          this.getCondenseSelect();
        }
        // 如果是修改合同
        else if (dialog == 'contractDialogVisible') {
          // 设置弹窗头部题目
          this.title = '修改合同';
          this.isCompile = false;

          this.itemName = '指定业务部门副总';

          // 获取指定人员下拉列表
          this.getPersonList();

          // 获取合同信息
          this.getContractInfo();
        }
      }
    },

    // 删除操作
    delProject(type) {
      const vm = this;
      vm.$confirm('确认删除吗?', {
        center: true
      })
        .then(() => {
          // 如果是item则表示是项目，则删除项目信息，否则删除合同信息
          if (type == 'item') {
            // 删除项目
            vm.deleteProject();
          } else if (type == 'contract') {
            // 删除合同
            vm.deleteContract();
            // 查询项目编号
            vm.getProjectNum();
          } else if (type == 'task') {
            // 删除任务
            vm.deleteTask();
          }
        })
        .catch(function(error) {});
    },
    // 提交请求事件
    submitForm(formName, dialog) {
      const vm = this;
      vm.$refs[formName].validate(valid => {
        if (valid) {
          vm.$confirm('确认提交吗?', {
            center: true
          })
            .then(() => {
              // 传递参数
              var params = {};
              // 请求地址
              var url = '';

              if (formName == 'changeItem') {
                var params = {
                  // 项目id
                  projectId: vm.changeItem.projectId,
                  // 项目编号
                  projectNum: vm.projectNumPrefix + vm.condense,
                  // 设置公司id
                  companyId: vm.changeItem.companyId,
                  //项目名称
                  projectName:vm.changeItem.projectName,
                  //咨询类别
                  counselTypePeriod:vm.changeItem.counselTypePeriod,
                };
                var url = '/project/modify/updateProjectInfo';
              } else if (formName == 'changeContract' && vm.title == '新增合同') {
                var params = {
                  // 项目id
                  projectId: vm.storageData.projectId,
                  // 合同编号
                  contractNum: vm.changeContract.contractNum,
                  // 跨部门合作
                  cooperateContract:vm.changeContract.cooperateContract,
                  // 指定人员数组
                  contractList:vm.changeContract.contractList
                };
                var url = '/project/add/saveContractInfo';
              } else if (formName == 'changeContract' && vm.title == '修改合同') {
                var params = {
                  // 项目id
                  projectId: vm.storageData.projectId,
                  // 合同id
                  contractId: vm.changeContract.contractId,
                  // 合同编号
                  contractNum: vm.changeContract.contractNum,
                  // 职位id
                  jobId: vm.changeContract.contractList[0].jobId,
                  // 部门id
                  departmentId: vm.changeContract.contractList[0].departmentId
                };
                var url = '/project/modify/updateContractInfo';
              } else if (formName == 'changeContract' && vm.title == '分配合同编撰人') {
                var params = {
                  // 合同id
                  projectId: vm.storageData.projectId,
                  // 合同id
                  contractId: vm.changeContract.contractId,
                  // 职位id
                  userId: vm.changeContract.contractList[0].jobId
                };
                var url = '/project/modify/updateContractUserInfo';
              }
              this.apiAddress(url, params)
                .then(res => {
                  // 如果返回成功，则绘制数据，否则提示失败信息
                  if (res.data.status == 'success') {
                    this.stateDialog('success', res.data.message);
                    let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
                    if (formName == 'changeItem') {
                      //修改项目
                      sessionData.businessId = params.projectId;
                    } else if (formName == 'changeContract' && vm.title == '新增合同') {
                      // 新增合同
                      sessionData.businessId = res.data.primaryKey;
                    } else if (formName == 'changeContract' && vm.title == '修改合同') {
                      // 修改合同
                      sessionData.businessId = sessionData.id;
                    }
                    vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
                    // 触发事件,重新刷新项目树
                    this.bus.$emit('asideRefresh');
                    this[dialog] = false;
                    this.$refs[formName].resetFields();

                    // 查询项目编号
                    this.getProjectNum();
                  } else {
                    this.stateDialog('error', res.data.message);
                  }
                })
                .catch(e => {
                  this.stateDialog('error', e);
                });
            })
            .catch(() => {});
        } else {
          vm[dialog] = true;
          return false;
        }
      });
    },
    checkFile(n) {
      let vm = this;
      if (1 == n) {
        vm.imgSrc = require('../../../../assets/image/noFile.png');
        vm.imgAlt = '暂无文件';
      } else if (2 == n) {
        vm.imgSrc = require('../../../../assets/image/unable2preview.png');
        vm.imgAlt = '文件暂时无法预览，请下载查看！';
      }
    },
    addRequestMenu(val) {
      let vm = this;
      let formData = {
        contractId: vm.currentContractId,
        contractName: vm.currentContractName,
        requestMenuName: val.name
      };
      //新增请款单
      vm.apiAddress('/requestMenu/add/insertRequestMenuInfo', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
            sessionData.businessId = res.data.primaryKey;
            vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
            // 触发事件,重新刷新项目树
            this.bus.$emit('asideRefresh');
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    resetContractForm() {
      // 重置合同表单
      let vm = this;
      vm.theFormData = {
        // Object.assign({},formSupervision)
        indexContract: {
          // 合同index页
          form: Object.assign({}, contractForm.indexContract)
        },
        baseContract: {
          // 基础合同数据
          form: Object.assign({}, contractForm.baseContract),
          controlObj: {} // 控制对象
        },
        formCostConsulting: {
          // 造价合同
          form: Object.assign({}, contractForm.formCostConsulting)
        },
        formConstructionProjectBiddingAgency: {
          // 建设工程招标代理合同
          form: Object.assign({}, contractForm.formConstructionProjectBiddingAgency)
        },
        formProjectManagementConsultingService: {
          // 工程管理咨询服务合同
          form: Object.assign({}, contractForm.formProjectManagementConsultingService)
        },
        formSupervision: {
          // 工程管理咨询服务合同
          form: Object.assign({}, contractForm.formSupervision)
        }
      };
    },
    delRequestMenu() {
      let vm = this;
      let formData = {
        requestMenuId: vm.requestMenuId
      };
      //删除请款单
      vm.apiAddress('/requestMenu/delete/deleteRequestMenuInfo', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
            sessionData.businessId = sessionData.contractId;
            vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
            // 触发事件,重新刷新项目树
            this.bus.$emit('asideRefresh');
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    /**
     * @method 添加指定人员
     */
    addPersonnel(){
      let vm = this;
      if(vm.changeContract.contractList.length>2)return;
      vm.changeContract.contractList.push({
            userName: '', // 人员显示名称（部门+姓名）
            departmentId:'',// 部门id
            jobId:''// 职位id
          });
    },
    /**
     * @method 删除指定人员
     */
    delPersonnel(i){
      let vm = this;
      if(vm.changeContract.contractList.length>1)vm.changeContract.contractList.splice(i, 1);
      // 计算合同编号
      vm.countContractNum();
    },
    /**
     * @method 跨部门合作改变
     */
    cooperateContractChange(val){
      let vm = this;
      if(!val)vm.changeContract.contractList
      while(vm.changeContract.contractList.length>1){
        vm.changeContract.contractList.pop();
        // 计算合同编号
        vm.countContractNum();
      }
    },
    resetChangeContract(){
      let vm = this;
      vm.compile = '';// 合同编号(分配合同编撰人使用)
      vm.isCompile = false;
      vm.changeContract = {
        // 合同id
        contractId: '',
        // 合同编号
        contractNum: '',
        contractList:[{
            userName: '', // 人员显示名称（部门+姓名）
            departmentId:'',// 部门id
            jobId:''// 职位id
          }],
        // 跨部门合作
        cooperateContract:0
      };
    },
    // 查询列表数据
    searchCounselTypePeriodList() {
      const vm = this;
      let formData={
      }
      // 查询咨询类别下拉列表
      vm.apiAddress('/project/query/searchCounselTypePeriodList', formData).then(res => {
      if (res.data.status == 'failure') {
        vm.$message({
        message: res.data.message,
        type: 'error',
        center: true
        });
      } else {
        // 设置表格数据
        let dataList = res.data.dataList;
        this.radios = dataList
      }
      }).catch(err => {
      vm.$message({
        message: err,
        type: 'error',
        center: true
      });
      })
    },
  }
};
</script>
<style lang="scss">
.m-work-contract-foundItem {
  .el-form-item__error--inline {
    position: absolute!important;
    top: 100%;
    left: 0 !important;
  }
  .item-serial-num {
    float: right;
    color: #b3b3b3;
  }
  .card-right {
    float: right;
  }

  .card-right .el-select {
    width: 120px;
    height: 24px;
  }
  .box-card {
    margin: 10px 0;
    padding: 15px 20px;
    background-color: #f1f5f8;
    border: none;
    width: 100%;
    display: inline-block;
    vertical-align: middle;
    box-shadow: none !important;
    overflow: visible;
  }

  .box-card .el-card__header {
    border-bottom: 1px solid #e3e3e3;
    padding: 10px 0;
  }

  .box-card .el-card__header .clearfix span:first-child {
    float: left;
  }

  .box-card .el-card__body {
    padding: 10px 0;
  }

  .box-card .el-card__body .el-form-item,
  .item-serial .el-form-item {
    display: inline-block;
    margin-bottom: 0px;
  }

  .box-card .el-card__body .el-form-item label {
    width: 58px !important;
    font-size: 12px;
    color: #424242;
    padding: 0;
  }
  .item-serial .el-form-item label {
    width: 50px !important;
    font-size: 12px;
    color: #424242;
    padding: 0;
  }

  .box-card .el-card__body .el-form-item .el-form-item__content,
  .item-serial .el-form-item .el-form-item__content {
    margin-left: 10px !important;
    float: left;
  }

  .box-card .el-card__body .el-form-item .el-form-item__content input,
  .item-serial .el-form-item .el-form-item__content input {
    width: 120px;
    height: 24px;
    line-height: 24px;
  }

  .box-card{
    .el-card__body{
      .el-form-item{
        /* 跨部门合作 */
        &.cooperateContract{

          label{
            width: auto!important;
          }
        }
      }
       .el-form-item__error--inline{
          top: 100%!important;
       }
    }
  }
}
</style>
<style lang="scss" scoped>
.m-work-contract-foundItem {
  display: flex;
  height: 100%;
  width: 100%;
  background-color: #f7fafc;
  border-radius: 5px;

  /* 动态添加指定人员 */
  .dynamically-add{
    position:relative;

    /* 删除图标 */
    .el-icon-close{
      position: absolute;
      top: 15px;
      left: 195px;
    }
  }
}
</style>
