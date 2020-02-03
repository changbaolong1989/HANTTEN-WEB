<template>
  <!-- 工作台默认内容 -->
  <div class="btnTool--work-defalut">
    <div>
      <el-button class="linear" type="primary" v-if="userPowerInfo && !!userPowerInfo.hoursList" @click="hourslist">工时列表</el-button>
      <el-button class="linear" type="primary" v-if="userPowerInfo && !!userPowerInfo.myHour" @click="searchMyHourListInfo">我的工时</el-button>
    </div>
    <!-- 修改弹窗 -->
    <el-dialog
      title="我的工时"
      class="customManHour"
      :visible.sync="show"
      width="1200px"
      top="10vh"
      transition="fade-in-linear"
      center
      @close="handClose('form')"
      :close-on-click-modal="false"
    >
      <el-form :model="form" ref="form" label-position="top">
        <el-row>
          <el-col :span="12">当前日期：{{ form.newDate }}</el-col>
        </el-row>
        <el-row><el-col :span="12">当日任务：</el-col></el-row>
        <el-table :data="form.tableData" border style="width: 100%" class="tabData">
          <el-table-column prop="taskName" label="任务名称" :show-overflow-tooltip="true" align="center"></el-table-column>
          <el-table-column prop="endDate" label="完成时间" :show-overflow-tooltip="true" align="center"></el-table-column>
          <el-table-column prop="needHour" label="预计占用工时" :show-overflow-tooltip="true" align="center"></el-table-column>
          <el-table-column prop="actualHourSum" label="已分配工时合计" :show-overflow-tooltip="true" align="center"></el-table-column>
          <el-table-column label="今日工时分配" align="center" width="200">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem">
                <el-input v-model="scope.row.actualHour" placeholder="请输入今日工时"></el-input>
                <p style="display: none;">{{ scope.row.taskUserId }}</p>
                <p style="display: none;">{{ scope.row.relationId }}</p>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="customizeHour" label="自定义工时" align="center" width="200">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem"><el-input v-model="scope.row.customizeHour" placeholder="请输入自定义工时"></el-input></el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="hourReason" label="原因" align="center" width="300">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem"><el-input v-model="scope.row.hourReason" placeholder="请输入原因"></el-input></el-form-item>
            </template>
          </el-table-column>
        </el-table>
        <el-button class="linear customBtn" type="primary" @click="addRow(form.customTab)">增加自定义工时</el-button>
        <el-table :data="form.customTab" border style="width: 100%" class="tabData">
          <el-table-column label="任务名称" :show-overflow-tooltip="true" align="center">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].taskName'" :rules="[{ required: true }]">
                <el-input v-model="scope.row.taskName" placeholder="请输入任务名称"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="完成时间" :show-overflow-tooltip="true" align="center" width="200">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].completeTime'" :rules="[{ required: true }]">
                <el-date-picker v-model="scope.row.completeTime" type="date" placeholder="选择完成时间"></el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="预计占用工时" :show-overflow-tooltip="true" align="center"  width="180">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].predictTime'" :rules="[{ required: true },{ type:'number'}]">
                <el-input v-model.number="scope.row.predictTime" placeholder="请输入预计占用工时"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="已分配工时合计" :show-overflow-tooltip="true" align="center" width="180">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].allocatedTime'" :rules="[{ required: true },{ type:'number'}]">
                <el-input v-model.number="scope.row.allocatedTime" placeholder="请输入已分配工时合计"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="今日工时分配" align="center">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].todayTime'" :rules="[{ required: true },{ type:'number'}]">
                <el-input v-model.number="scope.row.todayTime" placeholder="请输入今日工时"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="原因" align="center">
            <template slot-scope="scope">
              <el-form-item class="tab-formItem" :prop="'customTab[' + scope.$index + '].reason'" :rules="[{ required: true }]">
                <el-input v-model="scope.row.reason" placeholder="请输入原因"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column prop="hourReason" label="操作" align="center">
            <template slot-scope="scope">
              <el-button @click.native.prevent="deleteRow(scope.$index, form.customTab)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm('form')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      show: false,
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      form: {
        // 当前日期
        newDate: '',
        // 表格数据
        tableData: [],
        customTab: []
      },
      hourReasonFlag: false,
      customizeHourFlag: false
    };
  },
  created() {},
  methods: {
    /* 删除自定义工时列表*/
    deleteRow(index, rows) {
      rows.splice(index, 1);
    },
    addRow(rows) {
      rows.push({
        taskName:null,
        completeTime:null,
        predictTime: null,
        allocatedTime: null,
        todayTime:null,
        reason:null
      });
    },
    /* 关闭弹窗*/
    handClose(index) {
      this.$refs[index].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗?', {
            center: true
          })
            .then(() => {
              // 查询我的工时列表请求
              this.apiAddress('/task/modify/updateMyHourListInfo', {
                taskList: this.form.tableData,
                nowDate: this.form.newDate
              })
                .then(res => {
                  // 如果返回成功，则提示成功信息，否则提示失败信息
                  if (res.data.status == 'success') {
                    // 隐藏我的工时
                    this.saveTab(res.data.status);
                  } else {
                    this.saveTab(res.data.status);
                  }
                })
                .catch(e => {
                  this.stateDialog('error');
                });
            })
            .catch(function(error) {});
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    saveTab(status) {
      console.log("form",this.form.customTab)
      // 保存自定义工时列表信息
      console.log(this.form.customTab);
      this.apiAddress('/task/save/saveCustomTime ', {
        userCustomTimes: this.form.customTab
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 隐藏我的工时
            if (status == 'success') {
              this.show = false;
              this.stateDialog('success', res.data.message);
            }else{
              this.stateDialog('error', res.data.message);
            }
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    hourslist() {
      this.$router.push('/page/work/hoursList');
    },
    // 查询我的工时列表
    searchMyHourListInfo() {
      // 显示我的工时
      this.show = true;
      // 查询我的工时列表请求
      this.apiAddress('/task/query/searchMyHourListInfo', {})
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 当前日期
            this.form.newDate = res.data.currentDate;
            // 列表数据
            this.form.tableData = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
      // 查询自定义工时请求
      this.apiAddress('/task/query/queryCustomTimeList', {})
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 列表数据
            this.form.customTab = res.data.dataList;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    }
  }
};
</script>
<style lang="scss">
.btnTool--work-defalut {
  .customManHour {
    .el-dialog__body {
      height: 650px;
      overflow: auto;
      .el-input {
        height: 32px;
      }
      .el-date-editor.el-input{width: 165px;}
      .el-form-item.is-success .el-input__inner{
        border-color: #DCDFE6;
      }
    }
  }
  .tabData {
    border-radius: 10px;

    .el-form-item__content {
      line-height: 30px;
    }

    .tab-formItem {
      margin-bottom: 0;
    }
  }
}
</style>
<style lang="scss" scoped>
.customBtn {
  margin: 20px 0;
}
</style>
