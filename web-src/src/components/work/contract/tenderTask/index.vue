<template>
  <!-- 工作台 市场部详情 -->
  <div class="work-contract-tenderTask">
    <div class="img-failure" v-if="showImg">
      <img v-bind:src="imgSrc" :alt="imgAlt" />
      <p style="text-align: center;">{{ imgAlt }}</p>
    </div>
     <!-- 修改弹窗 -->
    <el-dialog title="修改项目" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center @close="handClose('ruleForm')">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="项目名称" prop="tenderTaskName">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入项目名称" v-model="ruleForm.tenderTaskName">
          </el-input>
        </el-form-item>
        <el-form-item label="项目概况" prop="tenderTaskContent">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入项目概况" v-model="ruleForm.tenderTaskContent">
          </el-input>
        </el-form-item>
        <el-form-item label="开标时间" prop="startDate">
          <div class="block">
            <el-date-picker v-model="ruleForm.startDate" type="date" placeholder="请设置开标时间"></el-date-picker>
          </div>
        </el-form-item>
        <div class="itemlist-dialog-bottom">
          <p>指定人员</p>
          <el-form-item label="主编制人" :prop="'mainUser.userId'" :rules="[{validator:checkmainUser,trigger: 'change'}]">
            <el-select v-model="ruleForm.mainUser.userId" placeholder="请选择主编制人">
              <el-option v-for="item in options" :key="item.userId" :label="item.userName" :value="item.userId">
              </el-option>
            </el-select>
          </el-form-item>
          <div class="addUser"><span>其他组员</span>
            <el-button type="primary" icon="el-icon-plus" class="linear" size="mini" @click="addCard"></el-button>
            <div class="el-form-item__error" v-show="addUser">
              其他组员不可以重复
            </div>
          </div>
          <el-form-item v-for="(i,k) in ruleForm.userList" :key="k" :prop="'userList['+k+'].userId'" :rules="[{validator:checkUserList,trigger: 'change'}]"
            s>
            <div class="addOption">
              <el-select v-model="i.userId" placeholder="请选择其他组员">
                <el-option v-for="item in options" :key="item.userId" :label="item.userName" :value="item.userId">
                </el-option>
              </el-select>
              <span class="el-icon-close" @click="removeCrad(k)"></span>
            </div>
          </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="updateTenderTask('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

    </el-row>
    <div class="form-block" v-if="InfoShowFlag">
      <el-row>
        <el-col :span="24">任务概况</el-col>
      </el-row>
      <el-row>
        <el-col :span="24">{{taskContent}}</el-col>
      </el-row>
    </div>
    <div class="form-block" v-if="InfoShowFlag">
      <el-row>
        <el-col :span="24">开标时间</el-col>
      </el-row>
      <el-row>
        <el-col :span="24">{{startDate}}</el-col>
      </el-row>
    </div>
    <div class="form-block" v-if="!InfoShowFlag">
      <el-row>
        <el-col :span="24">工作步骤说明</el-col>
      </el-row>
      <el-row>
        <el-col :span="24">{{periodDesc}}</el-col>
      </el-row>
    </div>
    <div class="form-block" v-if="periodType==2||periodType==3">
      <cost-A3 :projectName='projectName' :contractNum='contractNum' v-if='!correctTenderStageMaterialFlag' :outcomeDocNo='outcomeDocNo' :tableListData='tableListData' ></cost-A3>
       <div v-if="showOnlyOfficeCompFlag" style="height: 500px;">
         <office  :file="tenderStageMaterialInfo"></office>
       </div>
	</div>
  </div>
</template>

<script>
  import {
    apiAddress,
    stateDialog
  } from '@/request/api';
  import A3 from '../../common/components/a3'
  import office from '../../common/components/office'
  export default {
    components: {
      "cost-A3": A3,
      office
    },
    data() {
      return {
        userType:'',
        showImg: false,
        periodType:'',
        projectName:'',
        contractNum:'',
        outcomeDocNo:'',
        tableListData: [],
        centerStsDialogVisible: false, //弹窗显示
        imgSrc: '',
        imgAlt: '',
        taskContent: "",
        startDate: "",
        periodDesc: "",
        options: [],
        InfoShowFlag: true,
        centerDialogVisible: false, //弹窗显示
        form:{
          tenderTaskId: '',
          tenderTaskSts:'',
        },
        addUser: false,
        ruleForm: {
          tenderTaskId: '',
          tenderTaskName: '',
          tenderTaskContent: '',
          delegateTaskId:'',
          startDate: '',
          mainUser: {
            userId: '',
            isMainDuty: 1
          },
          userList: []
        },
        rules: {
          tenderTaskName: [
            {required: true, message: '请输入项目名称', trigger: 'blur'},
            { max: 20, message: '最长20字', trigger: 'blur' },
          ],
          tenderTaskContent: [
            {required: true, message: '请输入项目概况', trigger: 'blur'},
            { max: 200, message: '最长200字', trigger: 'blur' },
          ],
          startDate: [
            {required: true,message: '请设置开标时间',trigger: 'blur'}
          ],
        },
        tenderStageMaterialInfo:null,
        correctTenderStageMaterialFlag:false,
        showOnlyOfficeCompFlag:false,
      };
    },
    beforeDestroy(){
      let vm = this;
      vm.bus.$off('click');
      vm.bus.$off('aside-tender-task-download');
    },
    mounted() {
      let vm = this;
      //绑定全局事件globalEvent
      vm.bus.$on('click', val => {
        vm.checkFile(val);
        this.searchHTTenderTaskHisTaskInst(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
        this.searchTenderTaskInfo(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
        this.showUserType(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
      });
      vm.bus.$on('update', val => {
        vm.searchTenderTaskInfo(val.id);
        this.finduserList(this.departmentId);
        this.centerDialogVisible = true
      });
      vm.bus.$on('delete', val => {
        vm.deleteTenderTask(val);
      });
      vm.bus.$on('aside-tender-task-download', val => {

          let tempUrl = '/taskExecute/downLoad/downLoadTaskExecuteExecl?tenderTaskId=' + val.id;

          this.downloadFile(tempUrl,'',true);
      });

      vm.bus.$on('reSearchTenderStateFile', val => {
        this.searchTenderTaskInfo(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
        this.showUserType(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
      });
      vm.bus.$on('chengeTenderTaskSts', val => {
        vm.changeTenderTaskSts(val);
      });
      this.searchHTTenderTaskHisTaskInst(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
      this.searchTenderTaskInfo(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
      this.showUserType(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
    },
    updated(){
      console.log("index.vue---------:",this.correctTenderStageMaterialFlag)
    },
    watch:{
      correctTenderStageMaterialFlag(){
        if (this.correctTenderStageMaterialFlag) {
          this.searchTenderStageMaterial(this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId);
        }else{
          this.showOnlyOfficeCompFlag = correctTenderStageMaterialFlag;
        }
      }
    },
    methods: {
      handClose(index) {
        this.$refs[index].resetFields()
      },
      checkmainUser(rule, value, callback) {
        if (value == "") {
          callback(new Error('主编制人不为空'));
        } else {
          var a = 0;
          for (var i = 0; i < this.ruleForm.userList.length; i++) {
            if (this.ruleForm.userList[i].userId == value) {
              a++;
            }
          }
          if (a > 0) {
            callback(new Error('其他组员不可以和主编制人重复'));
          } else {
            callback()
          }
        }
      },
      checkUserList(rule, value, callback) {
        if (!value) {
          callback(new Error('其他组员不为空'));
        } else {
          if (value == this.ruleForm.mainUser.userId) {
            callback(new Error('其他组员不可以和主编制人重复'));
          } else {
            var a = 0;
            for (var i = 0; i < this.ruleForm.userList.length; i++) {
              if (this.ruleForm.userList[i].userId == value) {
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
      searchTenderTaskInfo(tenderTaskId) {
        let vm = this;
        let formData = {
          tenderTaskId: tenderTaskId
        }
        apiAddress('/tenderTask/query/searchTenderTaskInfoById', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let tenderTaskInfo = res.data.dataVO;
            let userList = tenderTaskInfo.userList;
            for (var i = 0; i < userList.length; i++) {
              if (userList[i].isMainDuty == 1) {
                vm.ruleForm.mainUser.userId = userList[i].userId
                userList.splice(i, 1)
              }
            }
            vm.ruleForm.tenderTaskId = tenderTaskInfo.tenderTaskId;
            vm.ruleForm.tenderTaskName = tenderTaskInfo.tenderTaskName;
            vm.ruleForm.tenderTaskId = tenderTaskInfo.tenderTaskId;
            vm.ruleForm.delegateTaskId = tenderTaskInfo.delegateTaskId;
            vm.ruleForm.tenderTaskContent = tenderTaskInfo.tenderTaskContent;
            vm.ruleForm.startDate = tenderTaskInfo.startDate;
            vm.ruleForm.userList = userList;
            vm.$parent.$data.titleName = tenderTaskInfo.tenderTaskName;
            vm.startDate = tenderTaskInfo.startDate;
            vm.taskContent = tenderTaskInfo.tenderTaskContent;
            vm.departmentId = tenderTaskInfo.departmentId;
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      deleteTenderTask(val){
        this.$confirm('此操作将永久删除投标任务, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let vm = this;
          let formData = {
            tenderTaskId: val.id
          }
          apiAddress('/tenderTask/delete/deleteTenderTask', formData).then(res => {
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
              this.$router.push({
                path: '/page/item'
              });
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          })
        }).catch(() => {

        });
      },
      // 查询部门下成员信息
      finduserList(params) {
        const vm = this;
        let formData = {
          departmentId: params,
        };
        vm.$axios.post('/tenderTask/query/searchUsersByDepartmentId', formData, vm.config).then(function(
            response) {
            if (response.data.status == 'failure') {
              vm.$message({
                message: response.data.message,
                type: 'error',
                center: true
              });
            } else {
              let userList = response.data.dataList;
              vm.options = userList;
            }
          })
          .catch(function(error) {
            vm.$message({
              message: error,
              type: 'error',
              center: true
            });
          });
      },
      addCard() {
        this.ruleForm.userList.push({
          userId: '',
          isMainDuty: '0'
        })
      },
      checkFile(data) {
        this.periodType = data.periodType
        this.InfoShowFlag = true
        this.taskContent = data.taskContent;
        this.startDate = data.startDate;
        this.periodDesc = data.periodDesc;
        if (data.treeNodeLevel == '2') {
          this.InfoShowFlag = false
        }
      },
      updateTenderTask(formName) { /* 修改投標任務表单提交 */
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let userList = this.ruleForm.userList.concat();
            const date = new Date(this.ruleForm.startDate)
            let startDate = date.getTime();
            userList.push(this.ruleForm.mainUser)
            let formData = {
              userList: userList,
              startDate: startDate,
              tenderTaskName: this.ruleForm.tenderTaskName,
              delegateTaskId:this.ruleForm.delegateTaskId,
              tenderTaskId: this.ruleForm.tenderTaskId,
              tenderTaskContent: this.ruleForm.tenderTaskContent,
              tenderTaskSts: 0,
              departmentId: this.params
            }
            const vm = this;
            vm.$axios.post("/tenderTask/modify/updateTenderTask", formData, vm.config).then(function(
                response) {
                if (response.data.status == 'failure') {
                  vm.$message({
                    message: response.data.message,
                    type: 'error',
                    center: true
                  });
                } else {
                  vm.$message({
                    message: response.data.message,
                    type: 'success',
                    center: true
                  });
                  vm.searchTenderTaskInfo(formData.tenderTaskId)
                  vm.centerDialogVisible = false

                  // todo
                  // 触发事件,重新刷新项目树
                  // vm.bus.$emit('asideRefresh');
                  vm.$router.go(0)
                }
              })
              .catch(function(error) {
                vm.$message({
                  message: error,
                  type: 'error',
                  center: true
                });
              });
          } else {
            return false;
          }
        });
      },
      removeCrad(k) {
        this.ruleForm.userList.splice(k, 1)
      },

      //查询审核意见列表
      searchHTTenderTaskHisTaskInst(tenderTaskId){
        let vm = this;
        let formData = {
          tenderTaskId: tenderTaskId,
        }
        //查询审核意见列表
        vm.apiAddress('/tenderTask/query/searchHTTenderTaskHisTaskInst', formData).then(
          res => {
            if (res.data.status == 'failure') {
              vm.$message({
                message: res.data.message,
                type: 'error',
                center: true
              });
            } else {
              /*------------------------------格式转换START---------------------------------*/
              let dataList = res.data.dataList;
              //for循环遍历更改属性名
              for (var i = 0 ; i < dataList.length ; i++) {
                dataList[i].title = vm.getStepName(dataList[i].taskKey)
                //将属性名httenderTaskHisTaskInstVOList转换成属性名children
                dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/httenderTaskHisTaskInstVOList/g,"children"));
                let children = dataList[i].children
                for (var j = 0 ; j < children.length ; j++) {
                  //将属性名approveTime转换成属性名time
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g,"time"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g,"name"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,"persions"));
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g,"opinion"));
                  let persions = vm.groupBy(children[j].persions,function(item){
                    return[item.userId]
                  })
                  for (var k = 0 ; k < persions.length ; k++) {
                    persions[k].name = persions[k].dataList[0].userName
                    persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g,"errorList"));
                    let errorList = persions[k].errorList;
                    for (var l = errorList.length - 1; l >= 0; l--) {
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g,"type"));
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g,"num"));
                    }
                    persions[k].errorList = errorList;
                  }
                  children[j].persions = persions;
                }
                dataList[i].children = children
              }
              /*---------------------------------格式转换END-------------------------------------*/
              vm.tableListData = dataList
            }
          }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      },
      searchTenderStageMaterial(tenderTaskId) {
        let vm = this;
        let formData = {
          tenderTaskId: tenderTaskId
        }
        apiAddress('/tenderTask/query/searchTenderStageMaterialByTenderTaskId', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let tenderStageMaterialInfo = res.data.dataVO;
            vm.tenderStageMaterialInfo = {
              fileName:tenderStageMaterialInfo.reviewFileName,
              key:tenderStageMaterialInfo.onlyOfficeEditKey,
              downloadUrl:tenderStageMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl:tenderStageMaterialInfo.reviewFilePath,
            }
            vm.$parent.onlyOfficeEditKey = tenderStageMaterialInfo.onlyOfficeEditKey
            vm.$parent.reviewFilePath = tenderStageMaterialInfo.reviewFilePath
            vm.$parent.reviewFileName = tenderStageMaterialInfo.reviewFileName
            console.log("vm.tenderStageMaterialInfo",vm.tenderStageMaterialInfo)
            vm.showOnlyOfficeCompFlag = true;
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      /*查询当前人类别(用以展示按钮)*/
      showUserType (tenderTaskId){
        let vm = this;
        let formData = {
          tenderTaskId: tenderTaskId
        }
        vm.apiAddress('/tenderTask/query/showBtnType', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.userType = res.data.typeFlag;
            vm.correctTenderStageMaterialFlag=(vm.userType==2||vm.userType==3||vm.userType==4)
            console.log("vm.userType",vm.userType)
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
  .work-contract-tenderTask {
    .classMame {
      padding: 0 !important;
    }

    .item-open {
      position: absolute;
      top: -50px;
    }

    .el-dialog--center {
      .el-dialog__body {
        max-height: 560px;
        overflow: auto;
        padding: 0;
        border-bottom: 1px solid #dcdfe6;

        .el-form {
          padding: 18px 30px;

          .el-form-item {
            border-bottom: 1px solid #dcdfe6;
            margin: 0;
            padding: 10px 0;

            &:last-child {
              border-bottom: none;
            }

            label {
              float: none;
              text-align: left;
              color: #424242;
              font-size: 12px;
              float: left;
            }

            .el-form-item__content {
              margin-left: 0 !important;
              clear: both;
              text-align: left;

              .el-input__inner {
                height: 27px;
                line-height: 27px;
                color: #424242;
                font-size: 12px;
                background-color: #f8f9fb;
              }

              textarea {
                height: 57px !important;
              }

              .el-textarea+.el-form-item__error {
                top: 110%;
              }
            }

          }

          .itemlist-dialog-bottom {
            .el-form-item__content {
              clear: none;
            }

            & .el-form-item__error {
              left: 290px;
              top: 10px;

              &::before {
                border-bottom: 5px solid transparent !important;
                border-right: 5px solid #F85B48 !important;
                top: 5px !important;
                left: -10px;
                position: absolute;
              }
            }

            p {
              font-size: 12px;
              color: #424242;
              margin: 15px 0 0;
              text-align: left;
            }

            .addUser {
              position: relative;

              .el-form-item__error {
                position: absolute;
                top: 0 !important;
                left: 120px !important;
              }

              .el-button {
                width: 26px;
                height: 16px !important;
                line-height: 16px !important;
                border-radius: 5px;
                padding: 0 !important;
                border: none;
                margin-left: 20px;
              }
            }

            .el-form-item {
              border: none;
              padding: 0;

              label {
                float: left;
                width: 70px !important;
                text-align: left;
              }

              .addOption {
                margin-left: 70px !important;

                span {
                  color: #B0B0B0;
                  cursor: pointer;
                }
              }

            }
          }
        }

      }

      .dialog-footer {
        .el-button {
          width: 120px;
          height: 30px;
          line-height: 30px;
          padding: 0;
          font-size: 12px;
        }
      }

    }

  }
</style>
<style lang="scss" scoped>
  .work-contract-tenderTask {
    border-radius: 5px;

    /* 失败时显示图片的容器 */
    .img-failure {
      text-align: center;
      width: 300px;
      margin: auto;

      img {
        width: 150px;
        margin: auto;
        display: inline-block
      }
    }

    .form-block {
      margin-top: 10px;
      background-color: #edf1f4;
      border-radius: 5px;
      padding: 20px 20px 20px 10px;

      .el-row {
        max-width: 860px;
        margin-right: 0 !important;
        margin-left: 0 !important;
        margin-bottom: 5px;

        .el-col {
          padding-right: 0 !important;
        }
      }
    }
  }
</style>
