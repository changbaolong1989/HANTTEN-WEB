<template>
  <!-- 创建任务弹窗 -->
  <el-dialog class="create-task" :title="title" :visible.sync="show" width="710px" transition="fade-in-linear" center
    @close="handleClose" :close-on-click-modal="false">
    <el-row>
      <el-col :span="12">项目编号：{{projectNum}}</el-col>
      <el-col :span="12">合同编号：{{this.$parent.changeContract.contractNum}}</el-col>
    </el-row>
    <hr color="#dcdfe6" size="1" width="100%">
    <!-- 大表单 -->
    <div class="currentTaskform" v-for="(item,index) in currentTaskform" :key="index">
      <el-form :model="item" ref="currentTaskform" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="item.taskName" placeholder="请输入任务名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if='$parent.changeContract.departmentId === "db5e76f9-9fbd-11e9-a036-0e411fd5cf91"'>
            <el-form-item label="咨询类别" prop="contractTypeName">
              <el-select v-model="item.contractTypeName" :disabled="title=='修改任务'" placeholder="请选择咨询类别" @change="getContractType(item.contractTypeName,index)">
                <el-option v-for="i in contractList" :label="i.contractTypeName" :key="i.contractTypeId" :value="i.contractTypeName"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="二级咨询类别" prop="counselTypeName">
              <el-select v-model="item.counselTypeName" :disabled="title=='修改任务'" placeholder="请选择二级咨询类别" @change="getCounselType(item.counselTypeName,index)">
                <el-option v-for="i in item.consultList" :label="i.counselTypeName" :key="i.counselTypeId" :value="i.counselTypeName"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="所属高管" prop="seniorExecutive">
              <el-radio :disabled="item.disabled" v-model="item.seniorExecutive" label="1">肖总</el-radio>
              <el-radio :disabled="item.disabled" v-model="item.seniorExecutive" label="0">王总</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <hr color="#dcdfe6" size="1" width="100%">
        <p>指定人员</p>
        <el-row>
          <el-col :span="12">
            <el-form-item label="编制成果文件人" class="file" prop="userName" :rules="[{validator:checkmainUser,trigger: 'change'}]">
              <el-select v-model="item.userName" :filterable="true" :disabled="!!currentTaskform[0].verifyState" placeholder="请选择编制成果文件人" @change="getUserId(item.userName,index)">
                <el-option v-for="i in personList" :label="i.userName" :key="i.userId" :value="i.userName"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button type="text" @click="showTab(item,index)" class="personnel">人员任务信息<i :class="{'el-icon--right':true,'el-icon-arrow-down':item.iconDown,'el-icon-arrow-up':item.iconUp}"></i></el-button>
          </el-col>
        </el-row>
        <el-row class="showTable" v-show="item.showTable">
          <el-table :data="item.tableData" border style="width: 100%">
            <el-table-column align="center" prop="taskName" label="已分配任务" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
            <el-table-column align="center" prop="endDate" label="完成时间" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
            <el-table-column align="center" prop="needHour" label="分配工时" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
          </el-table>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="完成时间" prop="endDate">
              <el-date-picker v-model="item.endDate" value-format="yyyy-MM-dd" type="date" placeholder="请输入完成时间"
                @change="isMainUserEndDate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所需工时" prop="needHour">
              <el-input placeholder="请输入所需工时" v-model="item.needHour">
                <template slot="append">时</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="任务概况" prop="taskContent">
              <el-input type="textarea" v-model="item.taskContent" :disabled="!!currentTaskform[0].verifyState" placeholder="请输入任务概况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="else">
          <el-col :span="12">
            其他组员：
            <el-button class="linear add" icon="el-icon-plus" @click="addCrew(index)" :disabled="!!currentTaskform[0].verifyState" ></el-button>
          </el-col>
          <div class="el-form-item__error userInfo" v-show="addUser">
            其他组员不可以重复
          </div>
        </el-row>
        <hr color="#dcdfe6" size="1" width="100%">
        <!-- 小表单 -->
        <div class="smallForm" v-for="(value,xu) in item.smallForm" :key="xu">
          <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm1(value,xu,item,index)"></el-button>

          <el-row>
            <el-col :span="12">
              <el-form-item label="执行人" class="file" :prop="'smallForm['+xu+'].userName'" :rules="[{validator:checkUserList,trigger: 'change'}]">
                <el-select v-model="value.userName" :disabled="!!currentTaskform[0].verifyState" filterable placeholder="请选择执行人" @change="getExecutor(value.userName,xu,index)">
                  <el-option v-for="i in personList" :label="i.userName" :key="i.userId" :value="i.userName"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-button type="text" @click="showTab1(index,xu)" class="personnel">人员任务信息<i :class="{'el-icon--right':true,'el-icon-arrow-down':value.iconDown1,'el-icon-arrow-up':value.iconUp1}"></i></el-button>
            </el-col>
          </el-row>
          <el-row class="showTable" v-show="value.showTable1">
            <el-table :data="value.tableData" border style="width: 100%">
              <el-table-column align="center" prop="taskName" label="已分配任务" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
              <el-table-column align="center" prop="endDate" label="完成时间" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
              <el-table-column align="center" prop="needHour" label="分配工时" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
            </el-table>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="完成时间" :prop="'smallForm['+xu+'].endDate'" :rules="[{type: 'date',required: true,message: '请输入完成时间',trigger: 'blur'}]">
                <el-date-picker v-model="value.endDate" type="date" placeholder="请输入完成时间" @change="isUserEndDate(value.endDate,xu,index)"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所需工时" :prop="'smallForm['+xu+'].needHour'" :rules="[{required: true,message: '请输入所需工时',trigger: 'blur'}]">
                <el-input placeholder="请输入所需工时" v-model="value.needHour">
                  <template slot="append">时</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="任务概况" :prop="'smallForm['+xu+'].taskContent'" :rules="[{required: true,message: '请输入任务概况',trigger: 'blur'}]">
                <el-input type="textarea" :disabled="!!currentTaskform[0].verifyState" v-model="value.taskContent" placeholder="请输入任务概况"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" class="linear" @click="submitForm('currentTaskform')">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  export default {
    data() {
      return {
        show: false,
        storageData: this.SStorage.getItem('/page/work/contract/FoundItem'),
        // 项目编号
        projectNum: "",
        // 大表单
        currentTaskform: [{
          // 任务id
          taskId: "",
          // 任务名称
          taskName: "",
          // 合同类型id
          contractTypeId: "",
          // 咨询类型id
          counselTypeId: "",
          // 指定人名称
          userName: "",
          // 成果文件人id
          userId: "",
          // 完成时间
          endDate: "",
          // 所需工时
          needHour: "",
          // 任务概况
          taskContent: "",
          // 表格是否显示
          showTable: false,
          // 下箭头
          iconDown: true,
          // 上箭头
          iconUp: false,
          // 其他组员表单
          smallForm: [],
          // 其他组员表单(传到后台参数)
          userList: [],
          // 咨询类型数据
          consultList: [],
          // 指定人员信息列表数据
          tableData: [],
          // 审核状态
          verifyState: "",
          // 所属高管
          seniorExecutive: '0',
        }],
        personList: [],
        // 添加其他组员校验
        addUser: false,
        rules: {
          taskName: [{
            required: true,
            message: '请输入任务名称',
            trigger: 'blur'
          }],
          contractTypeName: [{
            required: true,
            message: '请选择合同类型',
            trigger: 'change'
          }],
          counselTypeName: [{
            required: true,
            message: '请选择咨询类别',
            trigger: 'change'
          }],
          seniorExecutive: [{
            required: true,
            message: '请选择所属高管',
            trigger: 'change'
          }],
          needHour: [{
            required: true,
            message: '请输入所需工时',
            trigger: 'blur'
          }],
          taskContent: [{
            required: true,
            message: '请输入任务概况',
            trigger: 'blur'
          }],
          endDate: [{
            required: true,
            message: '请输入完成时间',
            trigger: 'blur'
          }],
        }
      }
    },
    props: ['contractList', "title", 'taskform', 'transmittedData'],
    watch: {
      taskform: {
        handler() {
          let vm = this;
          vm.currentTaskform = [vm.tool.deepCopy(Object.assign({},vm.currentTaskform[0],vm.taskform), vm.tool.deepCopy)];
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      // 查询项目编号
      vm.getProjectNum();

      vm.bus.$off('searchDictCounselTypeListByContractTypeId');
      // 触发提交审核-编制成果文件事件
      vm.bus.$on('searchDictCounselTypeListByContractTypeId', function() {
        vm.$nextTick(function() {
          // 当不是造价部的时候，直接查询咨询类别列表
          if (this.$parent.changeContract.departmentId !== "db5e76f9-9fbd-11e9-a036-0e411fd5cf91") {

            // 查询合同类别
            this.apiAddress("/task/query/searchDictCounselTypeListByContractTypeId", {
              // 合同类别id
              "contractTypeId": this.$parent.changeContract.departmentId
            }).then(res => {
              // 如果返回成功，则绘制数据，否则提示失败信息
              if (res.data.status == "success") {
                // 设置咨询类别列表
                this.currentTaskform[0].consultList = res.data.dataList;
              } else {
                this.stateDialog('error', res.data.message)
              }
            }).catch(e => {
              this.stateDialog('error', e)
            });
          }
        });
      });

    },
    methods: {
      // 点击完普通执行人的结束时间
      isUserEndDate(endDate, xu, index) {

        this.currentTaskform[index].smallForm[xu].endDate = endDate;

        // 参数
        let params = {};

        // 参数设置
        params = {
          // 用户id
          "userId": this.currentTaskform[index].smallForm[xu].userId,
          // 结束时间
          "endDate": this.tool.formatDate(this.currentTaskform[index].smallForm[xu].endDate, 'yyyy-MM-dd'),
          // 部门id
          "departmentId": this.transmittedData.departmentId
        };

        // 查询人员信息
        this.apiAddress("/task/query/searchUserInfo", params).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置人员信息列表
            this.currentTaskform[index].smallForm[xu].tableData = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 点击完主提审人的结束时间
      isMainUserEndDate(endDate) {

        this.currentTaskform[0].endDate = this.tool.formatDate(endDate, 'yyyy-MM-dd');

        // 查询人员信息
        this.apiAddress("/task/query/searchUserInfo", {
          // 用户id
          "userId": this.currentTaskform[0].userId,
          // 结束时间
          "endDate": this.tool.formatDate(endDate, 'yyyy-MM-dd'),
          // 部门id
          "departmentId": this.transmittedData.departmentId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置人员信息列表
            this.currentTaskform[0].tableData = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 校验成果文件人
      checkmainUser(rule, value, callback) {
        // 如果value为空，说明没选择信息，则提示"主提审人不为空",否则校验是否和其他组员重复
        if (value == undefined) {
          callback(new Error('请选择编制成果文件人'));
        } else {
          var a = 0;
          // 循环遍历其他组员信息，看是否和主提审人重复,如果重复，则a不为0
          for (var i = 0; i < this.currentTaskform[0].smallForm.length; i++) {
            if (this.currentTaskform[0].smallForm[i].userName == value) {
              a++;
            }
          }
          // 如果重复，则提示"其他组员不可以和主提审人重复"
          if (a > 0) {
            callback(new Error('其他组员不可以和主提审人重复'));
          } else {
            callback()
          }
        }
      },
      // 校验普通执行人
      checkUserList(rule, value, callback) {
        if (!value) {
          callback(new Error('请选择执行人'));
        } else {
          if (value == this.currentTaskform[0].userName) {
            callback(new Error('其他组员不可以和主提审人重复'));
          } else {
            var a = 0;
            for (var i = 0; i < this.currentTaskform[0].smallForm.length; i++) {
              if (this.currentTaskform[0].smallForm[i].userName == value) {
                a++;
              }
            }
            if (a > 1) {
              this.addUser = true;
              return false;
            } else {
              this.addUser = false;
              callback()
            }
          }
        }
      },
      // 查询项目编号
      getProjectNum() {
        // 查询项目详情
        this.apiAddress("/project/query/searchProjectInfo", {
          // 项目id
          "projectId": this.storageData.projectId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置项目编号
            this.projectNum = res.data.dataVO.projectNum;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 设置合同类型id
      getContractType(val, index) {
        let obj = {};
        // 获取下拉框的所有值 contractList:下拉框
        obj = this.contractList.find((item) => {
          return item.contractTypeName === val;
        });

        // 合同类型id
        this.currentTaskform[index].contractTypeId = obj.contractTypeId;

        // 查询合同类别
        this.apiAddress("/task/query/searchDictCounselTypeListByContractTypeId", {
          // 合同类别id
          "contractTypeId": obj.contractTypeId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置咨询类别列表
            this.currentTaskform[index].consultList = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 动态查询咨询类型
      getCounselType(val, index) {
        let obj = {};
        // 获取下拉框的所有值 consultList:下拉框
        obj = this.currentTaskform[index].consultList.find((item) => {
          return item.counselTypeName === val;
        });
        // 咨询类型id
        this.currentTaskform[index].counselTypeId = obj.counselTypeId;
      },
      // 获取执行人信息
      getExecutor(val, xu, index) {
        let obj = {};
        // 获取下拉框的所有值 personList:下拉框
        obj = this.personList.find((item) => {
          return item.userName === val;
        });
        // 执行人id
        this.currentTaskform[index].smallForm[xu].userId = obj.userId;

        // 参数
        let params = {};

        // 参数设置
        params = {
          // 用户id
          "userId": obj.userId,
          // 结束时间
          "endDate": this.tool.formatDate(this.currentTaskform[index].smallForm[xu].endDate, 'yyyy-MM-dd'),
          // 部门id
          "departmentId": this.transmittedData.departmentId
        };


        // 查询人员信息
        this.apiAddress("/task/query/searchUserInfo", params).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置人员信息列表
            this.currentTaskform[index].smallForm[xu].tableData = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 获取指定编纂人姓名
      getUserId(val, index) {
        let obj = {};
        // 获取下拉框的所有值 personList:下拉框
        obj = this.personList.find((item) => {
          return item.userName === val;
        });
        // 编纂人id
        this.currentTaskform[index].userId = obj.userId;

        // 查询人员信息
        this.apiAddress("/task/query/searchUserInfo", {
          // 用户id
          "userId": obj.userId,
          // 结束时间
          "endDate": this.currentTaskform[0].endDate,
          // 部门id
          "departmentId": this.transmittedData.departmentId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置人员信息列表
            this.currentTaskform[index].tableData = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      // 动态创建表单里面的小表单
      addCrew(index) {
        this.currentTaskform[index].smallForm.push({
          // 执行人id
          userId: "",
          // 成果文件人名称
          userName: "",
          // 是否是主负责人
          isMainDuty: "0",
          // 完成时间
          endDate: "",
          // 所需工时
          needHour: "",
          // 任务概况
          taskContent: "",
          // 表格是否显示
          showTable1: false,
          // 下箭头
          iconDown1: true,
          // 上箭头
          iconUp1: false,
          // 人员信息列表数据
          tableData: []
        });
      },
      // 确认关闭之后清空表单内容
      handleClose() {
        let vm = this;
        this.$refs.currentTaskform[0].resetFields();
        this.currentTaskform[0].smallForm = [];
        this.currentTaskform[0].userList = [];
        // 清空人员信息列表
        this.currentTaskform[0].tableData = [];
        // 清空咨询类型列表
        this.currentTaskform[0].consultList = [];
        // // 关闭弹窗
        vm.show = false;
      },
      // 关闭小表单
      colseForm1(value, xu, item, index) {
        this.currentTaskform[index].smallForm.splice(xu, 1);
      },
      showTab(item, index) { /* 控制表单中的表格显示 */
        if (!this.currentTaskform[index].showTable) {
          this.currentTaskform[index].showTable = true;
          this.currentTaskform[index].iconDown = false;
          this.currentTaskform[index].iconUp = true;
        } else {
          this.currentTaskform[index].showTable = false;
          this.currentTaskform[index].iconDown = true;
          this.currentTaskform[index].iconUp = false;
        }
      },
      showTab1(index, xu) { /* 控制表单中的表格显示 */
        if (!this.currentTaskform[index].smallForm[xu].showTable1) {
          this.currentTaskform[index].smallForm[xu].showTable1 = true;
          this.currentTaskform[index].smallForm[xu].iconDown1 = false;
          this.currentTaskform[index].smallForm[xu].iconUp1 = true;
        } else {
          this.currentTaskform[index].smallForm[xu].showTable1 = false;
          this.currentTaskform[index].smallForm[xu].iconDown1 = true;
          this.currentTaskform[index].smallForm[xu].iconUp1 = false;
        }
      },
      // 提交添加请求
      submitForm(index) {
        let vm = this;
        vm.$refs[index][0].validate((valid) => {
          if (valid) {
            // 二次确认
            vm.$confirm('您确认要提交吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'info ',
              center: true
            }).then(() => {
              vm.currentTaskform[0].userList = [];
              // 循环遍历任务中的指定人员，并放入userList中
              // userList原本不包括指定负责人，所以就需要在提交数据的时候，手动添加到userList中
              for (let i = 0; i < vm.currentTaskform.length; i++) {
                // 定义对象
                let userObj = {
                  // 指定负责人id
                  "userId": vm.currentTaskform[i].userId,
                  // 是指定负责人
                  "isMainDuty": "1",
                  // 完成时间
                  "endDate": vm.currentTaskform[i].endDate,
                  // 所需工时
                  "needHour": vm.currentTaskform[i].needHour,
                  // 任务概述
                  "taskContent": vm.currentTaskform[i].taskContent
                };
                // 把执行人信息放入集合中
                vm.currentTaskform[i].userList = vm.currentTaskform[i].userList.concat(vm.currentTaskform[i].smallForm);

                // 把指定负责人信息添加到userList的头部
                vm.currentTaskform[i].userList.unshift(userObj);
              }

              let fire = vm.tool.deepCopy(vm.currentTaskform, vm.tool.deepCopy);

              // 格式化小表单的时间数据
              for (let n = 0; n < fire[0].userList.length; n++) {
                fire[0].userList[n].endDate = vm.tool.formatDate(fire[0].userList[n].endDate, 'yyyy-MM-dd');
              }

              // 访问后台的url
              let url = "";
              // 添加到后台的参数
              let params = {};
              // 如果是创建任务，则设置创建任务的参数和url
              if (vm.title == "创建任务") {
                // 创建任务地址
                url = "/task/add/insertTaskInfo";
                // 创建任务参数
                params = {
                  // 设置合同id
                  "contractId": vm.$parent.changeContract.contractId,
                  // 表单数据
                  "taskList": fire
                };
                // 如果是修改任务，则设置修改任务的参数和url
              } else if (vm.title == "修改任务") {
                // 修改任务地址
                url = "/task/modify/updateTaskInfo";
                // 修改任务参数
                params = {
                  // 表单数据
                  "taskList": fire
                };
              }
              // 提交请求
              vm.apiAddress(url, params).then(res => {
                // 如果返回成功，则绘制数据，否则提示失败信息
                if (res.data.status == "success") {
                  vm.stateDialog('success', res.data.message);
                  let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
                  sessionData.businessId = res.data.primaryKey || fire[0].taskId;
                  vm.SStorage.setItem('/page/work/contract/FoundItem', sessionData);
                  // 关闭弹窗
                  vm.show = false;
                  // 触发事件,重新刷新项目树
                  vm.bus.$emit('asideRefresh');
                } else {
                  vm.stateDialog('error', res.data.message)
                }
              }).catch(e => {
                vm.stateDialog('error', e)
              });



            }).catch(() => {

            });
          } else {
            return false;
          }
        });
      }
    },
  }
</script>
<style lang="scss">
  .create-task {
    .el-dialog__body {
      font-size: 12px;
      color: #424242;
      max-height: 700px;
      overflow: auto;
    }

    /* 大表单 */
    .currentTaskform {
      background-color: #f1f5f8;
      border-radius: 10px;
      padding: 20px 15px;

      .showTable {
        box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.5) inset;

        .el-table__empty-text {
          margin: 0px auto !important;
        }

        .el-table--border {
          background-color: #f1f5f8;
          border: none;
          margin: 10px 0;
        }

        .el-table {

          tr,
          th,
          td {
            background-color: #f1f5f8 !important;
          }
        }

      }

      .colseform {
        position: absolute;
        right: 10px;
        top: 5px;
        color: #B0B0B0;

        i {
          font-size: 18px;
        }

      }

      .el-form {
        .personnel {
          line-height: 40px !important;
          color: #06A1EE;
        }

        .el-form-item {
          margin-bottom: 0px;


          .el-form-item__label {
            font-size: 12px !important;
            color: #424242;
            display: inline;
          }

          .el-form-item__content {
            .el-select {
              .el-input {
                width: 100%;
              }
            }

            .el-date-editor--date {
              width: 70% !important;
            }

            .el-input-group--append {
              width: 70% !important;
              margin-top: 5px;

              input {
                border-top-right-radius: 0px !important;
                border-bottom-right-radius: 0px !important;
              }
            }

            .el-textarea {
              width: 90%;
              margin-top: 10px;

              .el-textarea__inner {
                min-height: 57px;
              }
            }

            .el-input {
              width: 90%;
            }
          }
        }

        .smallForm {
          position: relative;
          padding: 25px 0;

          .colseform {
            top: -5px;
          }

        }

        .else {
          margin-top: 15px;

          .userInfo {
            margin-top: 10px;
          }
        }
      }
    }
  }
</style>

<style lang="scss" scoped="scoped">
  .create-task {
    .add {
      color: #fff;
      width: 26px;
      height: 16px !important;
      padding: 0;
      font-size: 10px;
      line-height: 16px !important;
      border: none;
    }

    hr {
      margin: 15px 0;
    }

    .cities {
      span {
        margin-right: 20px;
      }

      .el-checkbox-group {
        display: inline;

        .el-checkbox__label {
          color: #808080;
        }
      }
    }

    /* 大表单 */
    .currentTaskform {
      background-color: #f1f5f8;
      border-radius: 10px;
      padding: 35px 15px 20px;
      margin: 10px 0 0;
      position: relative;

      .el-table {
        tr {
          background-color: transparent !important;
        }
      }
    }
  }
</style>
