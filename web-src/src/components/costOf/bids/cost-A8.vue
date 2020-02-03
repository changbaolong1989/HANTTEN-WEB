<template>
  <div class="cost-A8 matterForm">
    <el-row>
      <el-col :span="18">
        <div>
          <p>咨询工作记录</p>
        </div>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
        <div @click="redact">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>
    <table class="align-center">
      <tbody>
        <tr>
          <td colspan="2">
            <div class="cell">项目名称：</div>
          </td>
          <td colspan="3">
            <div class="cell">{{form.projectName}}</div>
          </td>
          <td colspan="2">
            <div class="cell">达成意见一致时间：</div>
          </td>
          <td colspan="3">
            <div class="cell">{{form.consensusTime}}</div>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <div class="cell">序号</div>
          </td>
          <td colspan="4">
            <div class="cell">咨询意见</div>
          </td>
          <td colspan="4">
            <div class="cell">委托方回复意见</div>
          </td>
        </tr>
        <tr v-for="(item,i) in tableData" :key="i">
          <td colspan="2">
            <div class="cell">{{item.sortNum}}</div>
          </td>
          <td colspan="4">
            <div class="cell">{{item.counselAdvise}}</div>
          </td>
          <td colspan="4">
            <div class="cell">{{item.answerAdvise}}</div>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- 修改弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" top="30vh" center @close="handClose('ruleForm')"
      :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item label="达成意见一致时间" prop="consensusTime">
          <el-date-picker v-model="ruleForm.consensusTime" value-format="yyyy-MM-dd" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-row class="add">
          <el-col :span="12">
            <span>咨询工作记录</span>
            <el-button type="primary" class="linear" icon="el-icon-plus" @click="addAform"></el-button>
          </el-col>
        </el-row>
        <div v-for="(item,index) in ruleForm.opinionList" :key="index" class="addform">
          <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(item,index)"></el-button>
          <el-form-item label="咨询意见" :prop="'opinionList['+index+'].counselAdvise'" :rules="[{required: true,message: '请输入咨询意见',trigger: 'blur'}]">
            <el-input v-model="item.counselAdvise" placeholder="请输入咨询意见"></el-input>
          </el-form-item>
          <el-form-item label="委托方回复意见" :prop="'opinionList['+index+'].answerAdvise'" :rules="[{required: true,message: '请输入委托方回复意见',trigger: 'blur'}]">
            <el-input v-model="item.answerAdvise" placeholder="请输入委托方回复意见"></el-input>
          </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    props: ['transmittedData'],
    data() {
      return {
        data: {
          consensusTime: "",
          opinionList: []
        },
        tableData: [],
        form: {},
        centerDialogVisible: false,
        ruleForm: {
          relationId:"",
          consensusTime: "",
          opinionList: []
        },
        rules: {
          consensusTime: [{
            required: true,
            message: '请选择达成意见一致时间',
            trigger: 'blur'
          }],
        }

      }
    },
    mounted() {
      this.searchList();
    },
    methods: {
      searchList() {
        let vm = this;
        this.apiAddress("/taskExecute/query/searchA8Info", {
          "relationId": this.transmittedData.relationId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            this.form = vm.tool.deepCopy(res.data.dataVO,vm.tool.deepCopy);
            this.ruleForm = vm.tool.deepCopy(res.data.dataVO,vm.tool.deepCopy);
            this.tableData = vm.tool.deepCopy(res.data.dataVO.opinionList,vm.tool.deepCopy);
            this.data = vm.tool.deepCopy(res.data.dataVO,vm.tool.deepCopy);
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      handClose(index) {
        this.$refs[index].resetFields();
        this.ruleForm.opinionList = [];
      },
      redact() {
        let vm = this;
        // 让ruleForm与this.data不同源
        this.ruleForm = vm.tool.deepCopy(this.data,vm.tool.deepCopy);
        this.centerDialogVisible = true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let vm = this;
            vm.ruleForm.relationId = vm.transmittedData.relationId;
            let formData = vm.ruleForm;
            vm.apiAddress('/taskExecute/add/insertA8ZbdlInfo', formData).then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.centerDialogVisible = false;
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                this.searchList();
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
      addAform() {
        this.ruleForm.opinionList.push({
          counselAdvise: "",
          answerAdvise: ""
        })

      },
      colseForm(item, value) {
        this.ruleForm.opinionList.splice(value, 1)
      }
    }
  }
</script>

<style lang="scss">
  .cost-A8 {
    .add {
      font-size: 12px;
      padding: 10px 0px;
      border-bottom: 1px solid #dcdfe6;
      div{
        text-align: left;
      }
      span {
        color: #333333;
        margin-right: 5px;
      }

      p {
        color: #B0B0B0;
        text-align: right;
      }

      .el-button {
        width: 26px;
        height: 16px !important;
        line-height: 16px !important;
        border-radius: 5px;
        padding: 0px !important;
        border: none;

        i {
          font-size: 12px;
        }
      }
    }

    .addform {
      position: relative;
      border-bottom: 1px solid #EBEEF5;
      background: #F1F5F8;
      border-radius: 10px;
      padding: 20px 15px;
      margin: 10px 0;
    }

    .colseform {
      position: absolute;
      top: 0;
      right: 10px;
      z-index: 10;
    }

    .el-button--text {
      color: #333333 !important;
    }
  }
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');

  .item-tab-chuang {
    margin: 0;
    text-align: right;
  }
</style>
