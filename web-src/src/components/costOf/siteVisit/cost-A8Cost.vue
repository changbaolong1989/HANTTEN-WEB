<template>
  <div class="cost-A4 matterForm">
    <el-row>
      <el-col :span="18">
        <p>现场踏勘复验记录表</p>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" style="text-align: right;" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
        <div @click="redact" style="display: inline-block;">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>
    <table class="align-center">
      <thead>
        <tr>
          <th>
            <div class="cell">项目名称</div>
          </th>
          <th>
            <div class="cell">{{form.projectName}}</div>
          </th>
          <th>
            <div class="cell">咨询合同编号</div>
          </th>
          <th>
            <div class="cell">{{form.contractNum}}</div>
          </th>
        </tr>
      </thead>

      <tbody>
        <tr>
          <td>
            <div class="cell">发包人</div>
          </td>
          <td colspan="3">
            <div class="cell">{{tableData.employerA8}}</div>
          </td>
        </tr>
         <tr>
          <td>
            <div class="cell">承包人</div>
          </td>
          <td colspan="3">
            <div class="cell">{{tableData.contractor}}</div>
          </td>
        </tr>
         <tr>
          <td>
            <div class="cell">监理人</div>
          </td>
          <td colspan="3">
            <div class="cell">{{tableData.supervisor}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">现场勘查记录</div>
          </td>
          <td colspan="3">
            <div class="cell">{{tableData.prospectRecord}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">发包人（签字）</div>
          </td>
          <td colspan="3">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">承包人（签字）</div>
          </td>
          <td colspan="3">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">监理人（签字）</div>
          </td>
          <td colspan="3">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">咨询人（签字）</div>
          </td>
          <td colspan="3">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">记录人</div>
          </td>
          <td>
            <div class="cell"></div>
          </td>
          <td>
            <div class="cell">时间</div>
          </td>
          <td>
            <div class="cell"></div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 修改弹窗 -->
    <el-dialog :close-on-click-modal="false" title="修改项目" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear"
      center @close="handClose('ruleForm')">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">

        <el-form-item label="发包人" prop="employerA8">
          <el-input type="text" :rows="2" placeholder="请输入发包人" v-model="ruleForm.employerA8">
          </el-input>
        </el-form-item>
        <el-form-item label="承包人" prop="contractor">
          <el-input type="text" :rows="2" placeholder="请输入承包人" v-model="ruleForm.contractor">
          </el-input>
        </el-form-item>
        <el-form-item label="监理人" prop="supervisor">
          <el-input type="text" :rows="2" placeholder="请输入监理人" v-model="ruleForm.supervisor">
          </el-input>
        </el-form-item>
        <el-form-item label="现场勘查记录" prop="prospectRecord">
          <el-input type="textarea" :rows="2" placeholder="请输入现场勘查记录" v-model="ruleForm.prospectRecord">
          </el-input>
        </el-form-item>

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
    mounted() {
      this.searchList();
    },
    methods: {
      searchList() {
        this.apiAddress("/taskExecute/query/searchA8Info", {
          "relationId": this.transmittedData.relationId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            this.form = res.data.dataVO;
            this.ruleForm = res.data.dataVO;
            this.tableData = res.data.dataVO;
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      redact() {
        // 让ruleForm与this.data不同源
        this.ruleForm = Object.assign({}, this.tableData);
        this.centerDialogVisible = true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let vm = this;
            vm.ruleForm.relationId = vm.transmittedData.relationId;
            let formData = vm.ruleForm;
            vm.apiAddress('/taskExecute/add/insertA8Info', formData).then(res => {
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
      handClose(index) {
        this.$refs[index].resetFields();
      }
    },
    data() {
      return {
        centerDialogVisible: false,
        form: {},
        tableData: {
          employerA8: '',
          contractor: '',
          supervisor: '',
          prospectRecord: ''
        },
        ruleForm: {
          relationId:"",
          employerA8: "", // 发包人
          contractor: "", // 承包人
          supervisor: "", // 监理人
          prospectRecord: "", // 现场勘查记录
        },
        rules: {
          employerA8: [{
            required: true,
            message: '请输入发包人',
            trigger: 'blur'
          }],
          contractor: [{
            required: true,
            message: '请输入承包人',
            trigger: 'blur'
          }],
           supervisor: [{
            required: true,
            message: '请输入监理人',
            trigger: 'blur'
          }],
          prospectRecord: [{
            required: true,
            message: '请输入现场勘查记录',
            trigger: 'blur'
          }]
        }
      }
    }
  }
</script>

<style lang="scss">
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');

  .item-tab-chuang {
    margin: 0;
  }
</style>
