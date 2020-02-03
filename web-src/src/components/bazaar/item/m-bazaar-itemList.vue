<template>
  <!-- 市场部 > 项目列表 -->
  <div class="bazaar-itemList">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg item-open" justify="end">
      <!-- 新增-->
      <div class="item-tab-chuang" v-if="!!userPowerInfo.tenderTaskAdd" @click="showAddTenderTaskForm">
        <span class="el-icon-plus linear"></span>新增
      </div>
      <!-- 新增弹窗 -->
      <el-dialog title="创建项目" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center @close="handClose('ruleForm')" :close-on-click-modal="false">
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
            <div class="addUser">
              <span>其他组员</span>
              <el-button type="primary" icon="el-icon-plus" class="linear" size="mini" @click="addCard"></el-button>
              <div class="el-form-item__error" v-show="addUser">
                其他组员不可以重复
              </div>
            </div>
            <el-form-item v-for="(i,k) in ruleForm.userList" :key="k" :prop="'userList['+k+'].userId'" :rules="[{validator:checkUserList,trigger: 'change'}]">
              <div class="addOption">
                <el-select v-model="i.userId" placeholder="请选择其他组员">
                  <el-option v-for="item in options" :key="item.userId" :label="item.userName" :value="item.userId">
                  </el-option>
                </el-select>
                <span class="el-icon-close" @click="removeCard(k)"></span>
              </div>
            </el-form-item>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
        </span>
      </el-dialog>
    </el-row>

    <el-form :inline="true" ref="formInline" class="demo-form-inline bazaar-form">
      <el-form-item label="投标任务名" prop="tenderTaskName">
        <el-input v-model="searchTenderTaskName" placeholder="请输入投标任务名"></el-input>
      </el-form-item>
      <el-form-item label="主负责人" prop="userName">
        <el-input v-model="searchUserName" placeholder="请输入主负责人"></el-input>
      </el-form-item>
      <el-form-item class="right-formItem">
        <el-button type="primary" @click="onSubmit()">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%" class="item-tab" :cell-style="rowClass">
        <template slot="empty" class="tabdata-empty">
          <img src="../../../assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" prop="sortNum" label="序号"></el-table-column>
        <el-table-column align="center" class-name="table-column" label="任务名称">
          <template slot-scope="scope">
            <el-button type="text"  @click="routerPush(scope.row)" class="text-overflow-ellipsis width100p">{{scope.row.tenderTaskName}}</el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="tenderTaskContent" :show-overflow-tooltip='true'
          label="任务概况"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="startDate" label="开标时间"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="userName" label="主负责人"></el-table-column>
        <el-table-column align="center" class-name="table-column" label="状态">
          <template slot-scope="scope">
            <span type="text" class="">
              {{scope.row.tenderTaskSts==1 ? "未中标":scope.row.tenderTaskSts==2 ? "已中标":"未开标"}}
            </span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="block item-fen" v-show="total>0">
      <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
        :total="total">
      </el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>

  </div>

</template>

<script>
  export default {
    props: ['params'],
    data() {
      return {
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        tabdata: [], //当前页数据
        centerDialogVisible: false, //弹窗显示
        value1: "",
        searchUserName: "",
        searchTenderTaskName: "",
        userName: "",
        tenderTaskName: "",
        options: [],
        userCheckList: new Set(),
        userPowerInfo:this.SStorage.getItem("userPowerInfo"),
        ruleForm: {
          tenderTaskName: '',
          tenderTaskContent: '',
          startDate: '',
          mainUser: {
            userId: '',
            isMainDuty: 1
          },
          userList: []
        },
        userListFlag: false,
        addUser: false,
        formInline: {
          task: "",
          principal: ""
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
          startDate: [{
            required: true,
            message: '请设置开标时间',
            trigger: 'blur'
          }],
        }
      }
    },
    mounted() {
      // 本地访问
      this.finduserList();
      this.currentChangePage(1, 10);

    },
    methods: {
      handClose(index){
          this.$refs[index].resetFields()
      },
      handleSizeChange(val) { // 每页条数切换
        this.pageSize = val;
        this.currentChangePage(this.currentPage, this.pageSize);
      },
      handleCurrentChange(val) { //页码切换
        this.currentPage = val;
        this.currentChangePage(this.currentPage, this.pageSize)
      },
      showAddTenderTaskForm(){
        this.finduserList()
        this.centerDialogVisible = true
      },
      finduserList() {
        let vm = this;
          let formData = {
            departmentId: this.params,
          };
          vm.apiAddress('/tenderTask/query/searchUsersByDepartmentId', formData).then(res => {
            if (res.data.status == 'failure') {
              vm.$message({
                message: res.data.message,
                type: 'error',
                center: true
              });
            } else {
              let userList = res.data.dataList;
              vm.options = userList;
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          });
      },
      currentChangePage(currentPage, pageSize) { //分页方法
        const vm = this;
        let formData = {
          userName: this.userName,
          tenderTaskName: this.tenderTaskName,
          pageNumber: currentPage,
          pageSize: pageSize
        };
        vm.$axios.post('/tenderTask/query/searchTenderTaskPeriodList', formData, vm.config).then(function(
            response) {
            if (response.data.status == 'failure') {
              vm.$message({
                message: response.data.message,
                type: 'error',
                center: true
              });
            } else {
              let table = response.data.dataList;
              vm.tabdata = table;
              vm.total = response.data.dataCount;
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
      rowClass({
        row,
        column,
        rowIndex,
        columnIndex
      }) { //状态检测
        if (columnIndex === 5) {
          if (row.tenderTaskSts == "2") {
            return 'color:#7fd680'
          } else if (row.tenderTaskSts == "1") {
            return 'color:#b1b1b1'
          } else {
            return 'color:#dcaa3a'
          }

        }
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
      submitForm(formName) { /* 新建项目表单提交 */
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let userList = this.ruleForm.userList.concat();
            let startDate = this.ruleForm.startDate.getTime();
            userList.push(this.ruleForm.mainUser)
            let vm = this;
            let formData = {
              userList: userList,
              startDate: startDate,
              tenderTaskName: this.ruleForm.tenderTaskName,
              tenderTaskContent: this.ruleForm.tenderTaskContent,
              tenderTaskSts: 0,
              departmentId: this.params
            }
            vm.apiAddress('/tenderTask/insert/insertTenderTaskInfo', formData).then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.currentChangePage(vm.currentPage, vm.pageSize);
                vm.centerDialogVisible = false;
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
              }
            }).catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
          } else {
            return false;
          }
        });
      },
      //点击查询方法
      onSubmit() {
        this.userName = this.searchUserName;
        this.tenderTaskName = this.searchTenderTaskName;
        this.currentPage = 1;
        this.pageSize = 10;
        this.currentChangePage(this.currentPage, this.pageSize);
      },
      addCard() {
        this.ruleForm.userList.push({
          userId: '',
          isMainDuty: '0'
        })
      },
      removeCard(k) {
        this.ruleForm.userList.splice(k, 1)
      },
      routerPush(rowInfo) {
        rowInfo["departmentId"] = this.params;
        this.SStorage.setItem('/page/work/contract/tenderTask', {
          rowInfo: rowInfo,
          stateFlag: "tenderTask",
          titleName:rowInfo.tenderTaskName
        })
        // this.$router.push({
        //   path: '/page/work/contract/tenderTask'
        // });
        this.$router.push({
          path: '/page/item/contract/tenderTask'
        });


      }
    }
  }
</script>

<style lang="scss">
    .el-tabs__content{
      overflow:visible!important;
    }
  /* 弹窗 */
  .bazaar-itemList {
    position: relative;

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
                left: -10px!important;
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

    .bazaar-form {
      background-color: #fff;
      min-height: 65px;

      .el-form-item {
        vertical-align: middle;
        margin-top: 10px;
        margin-left: 20px;
        margin-bottom: 0px;
      }

      .right-formItem {
        float: right;
      }
    }
  }
  .width100p{
    width:100%
  }
  /* 文字超出显示...省略号 */
  .text-overflow-ellipsis {
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
</style>
