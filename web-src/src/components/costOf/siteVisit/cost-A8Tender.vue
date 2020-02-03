<template>
  <div class="cost-A4 matterForm">
    <el-row>
      <el-col :span="18">
          <p>咨询工作记录</p>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" style="text-align: right;">
        <div @click="redact" style="display: inline-block;">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>
    <table class="align-center">
      <tbody>
        <tr>
          <td>
            <div class="cell">项目名称</div>
          </td>
          <td>
            <div class="cell"></div>
          </td>
          <td>
            <div class="cell"></div>
          </td>
        </tr>
      </tbody>
    </table>

    <table class="align-center">
      <thead>
        <tr>
          <td>
            <div class="cell">序号</div>
          </td>
          <td>
            <div class="cell">咨询意见</div>
          </td>
          <td>
            <div class="cell">委托方回复意见</div>
          </td>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>

    <!-- 修改弹窗 -->
    <el-dialog :close-on-click-modal="false" title="修改项目" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear"
      center @close="handClose('ruleForm')">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="咨询业务简述" prop="consultationBrief">
          <el-input type="textarea" :rows="2" placeholder="请输入咨询业务简述" v-model="ruleForm.consultationBrief">
          </el-input>
        </el-form-item>
        <el-form-item label="建设规模" prop="constructionScale">
          <el-input type="text" placeholder="请输入建设规模" v-model="ruleForm.constructionScale">
          </el-input>
        </el-form-item>
        <el-form-item label="咨询内容与重点" prop="consultationContentAndFocus">
          <el-input type="text" placeholder="请输入咨询内容与重点" v-model="ruleForm.consultationContentAndFocus">
          </el-input>
        </el-form-item>
        <el-form-item label="组长" prop="groupLeader">
          <el-input type="text" placeholder="请输入组长" v-model="ruleForm.groupLeader">
          </el-input>
        </el-form-item>
        <el-form-item label="专业负责人" prop="professionalResponsiblePerson">
          <el-input type="text" placeholder="请输入专业负责人" v-model="ruleForm.professionalResponsiblePerson">
          </el-input>
        </el-form-item>
        <el-form-item label="咨询业务小组分工" prop="consultingTeamIsDivided">
          <el-input type="text" placeholder="请输入咨询业务小组分工" v-model="ruleForm.consultingTeamIsDivided">
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
    methods: {
      redact() {
        this.centerDialogVisible = true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.centerDialogVisible = false;
            this.stateDialog("success", "提交成功")
          } else {
            this.stateDialog("error", "验证失败")
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
        tableData: {
          consultationBrief: '',
          constructionScale: '',
          consultationContentAndFocus: '',
          groupLeader: '',
          professionalResponsiblePerson: '',
          consultingTeamIsDivided: '',
        },
        centerDialogVisible: false,
        ruleForm: {
          consultationBrief: "", //咨询业务简述
          constructionScale: "", // 建设规模
          consultationContentAndFocus: "", // 咨询内容与重点
          groupLeader: "", // 组长
          professionalResponsiblePerson: "", // 专业负责人
          consultingTeamIsDivided: "" // 咨询业务小组分工
        },
        rules: {
          consultationBrief: [{
            required: true,
            message: '请输入咨询业务简述',
            trigger: 'blur'
          }],
          constructionScale: [{
            required: true,
            message: '请输入建设规模',
            trigger: 'blur'
          }],
          consultationContentAndFocus: [{
            required: true,
            message: '请输入咨询内容与重点',
            trigger: 'blur'
          }],
          groupLeader: [{
            required: true,
            message: '请输入组长',
            trigger: 'blur'
          }],
          professionalResponsiblePerson: [{
            required: true,
            message: '请输入专业负责人',
            trigger: 'blur'
          }],
          consultingTeamIsDivided: [{
            required: true,
            message: '请输入咨询业务小组分工',
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
