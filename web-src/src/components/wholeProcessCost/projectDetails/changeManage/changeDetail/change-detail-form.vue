<template>
  <div>
    <el-dialog :title="dialogTitle" :visible="isShowDialog" width="500px" transition="fade-in-linear" center
               @close="onClose" @open="onOpen">
      <el-form :model="changeDetailForm" ref="changeDetailForm" label-width="130px" :rules="formRules"
               class="demo-ruleForm">
        <el-form-item label="合同编号-合同名" prop="contractName">
          <common-select :formModel.sync="changeDetailForm.contractName" :data="this.contractNameSelectData"
                         @optionChanged="contractNameChange"
                         :dataFormat="this.contractNameSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="合同分类">
          <el-input v-model="changeDetailForm.treatyTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="承包方式" v-show="changeDetailForm.undertakeTypeName">
          <el-input v-model="changeDetailForm.undertakeTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="变更名称" prop="alterName">
          <el-input v-model="changeDetailForm.alterName" placeholder="请输入变更名称"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="professionalName">
          <common-select :formModel.sync="changeDetailForm.professionalName" :data="professionalSelectData"
                         @optionChanged="professionalChange"
                         :dataFormat="this.professionalSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="内容摘要" prop="contentSummary">
          <el-input v-model="changeDetailForm.contentSummary" placeholder="请输入内容摘要"></el-input>
        </el-form-item>
        <el-form-item label="洽商日期" class="du">
          <el-date-picker v-model="changeDetailForm.talkDate" type="date" placeholder="选择洽商日期" format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="接收日期" class="du">
          <el-date-picker v-model="changeDetailForm.receiveDate" type="date" placeholder="选择接收日期"
                          format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <common-select :formModel.sync="changeDetailForm.stsName" :data="staSelectData"
                         @optionChanged="stsChange"
                         :dataFormat="this.stsSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="申报金额（元）" prop="declareAmount">
          <el-input v-model="changeDetailForm.declareAmount" placeholder="申报金额"></el-input>
        </el-form-item>
        <el-form-item label="监理审核金额（元）" prop="auditAmount">
          <el-input v-model="changeDetailForm.auditAmount" placeholder="监理审核金额"></el-input>
        </el-form-item>
        <el-form-item label="汉腾审核金额（元）" prop="hanttenAmount">
          <el-input v-model="changeDetailForm.hanttenAmount" placeholder="汉腾审核金额"></el-input>
        </el-form-item>
        <el-form-item label="业主审批金额（元）" prop="ownerAmount">
          <el-input v-model="changeDetailForm.ownerAmount" placeholder="业主审批金额"></el-input>
        </el-form-item>
        <el-form-item label="批准文号" prop="symbol">
          <el-input v-model="changeDetailForm.symbol" placeholder="批准文号"></el-input>
        </el-form-item>
        <el-form-item label="变更洽商费用分析">
          <common-select :formModel.sync="changeDetailForm.alterAnalyseName" :data="alterAnalyseSelectData"
                         @optionChanged="alterAnalyseChange"
                         :dataFormat="this.alterAnalyseSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="changeDetailForm.remark" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('changeDetailForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

  import DefaultScript from './default'
  import CommonSelect from '@/components/common/common-select'

  export default {
    name: "change-detail-form",
    props: ['isShowDialog', 'dialogTitle', 'formData'],
    components: {CommonSelect},
    data() {
      return {
        //rules: DefaultScript.formRules,
        changeDetailForm: JSON.parse(JSON.stringify(DefaultScript.changeDetailForm)),
        contractNameSelectFormat: {value: 'contractId', label: 'contractNum,contractName'},
        professionalSelectData: DefaultScript.professionalSelectData,
        staSelectData: DefaultScript.staSelectData,
        alterAnalyseSelectData: DefaultScript.alterAnalyseSelectData,
        professionalSelectFormat: {value: 'value', label: 'text'},
        stsSelectFormat: {value: 'value', label: 'text'},
        alterAnalyseSelectFormat: {value: 'value', label: 'text'},
        verify: {'moneyCheck': this.moneyCheck, 'checkNumAlphaSymbol': this.checkNumAlphaSymbol},
        projectId: this.$parent.$parent.projectId
      }
    },
    computed: {
      contractNameSelectData: {
        get: function () {
          return this.$store.state.wholeProcess.changeStandingBookObject.contractInfo;
        }
      },
      formRules() {
        return DefaultScript.getFormRules(this.verify);
      }
    },
    methods: {
      contractNameChange(item) {
        let contractInfo = DefaultScript.getContractInfo(this.contractNameSelectData, item.value);
        this.changeDetailForm.contractName = item.label;
        this.changeDetailForm.contractId = item.value;
        this.changeDetailForm.treatyTypeName = DefaultScript.treatyType[contractInfo.treatyType];
        this.changeDetailForm.undertakeTypeName = DefaultScript.undertakeType[contractInfo.undertakeType];
      },
      professionalChange(item) {
        this.changeDetailForm.professionalId = item.value;
        this.changeDetailForm.professionalName = item.label;
      },
      stsChange(item) {
        this.changeDetailForm.sts = item.value;
        this.changeDetailForm.stsName = item.label;
      },
      alterAnalyseChange(item) {
        this.changeDetailForm.alterAnalyse = item.value;
        this.changeDetailForm.alterAnalyseName = item.label;
      },
      onClose() {
        if (this.dialogTitle === '新增') {
          this.changeDetailForm = {};
        }
        this.$emit('update:isShowDialog', false);
        this.$refs['changeDetailForm'].resetFields();
      },
      onOpen() {
        if (this.dialogTitle === '修改') {
          this.changeDetailForm.professionalName = DefaultScript.getText(this.formData.professionalId, DefaultScript.professionalSelectData);
          this.changeDetailForm.stsName = DefaultScript.getText(this.formData.sts, DefaultScript.staSelectData);
          this.changeDetailForm.alterAnalyseName = DefaultScript.getText(this.formData.alterAnalyse, DefaultScript.alterAnalyseSelectData);
          let contractInfo = DefaultScript.getContractInfo(this.contractNameSelectData, this.formData.contractId);
          let item = {};
          item.value = this.formData.contractId;
          item.label = contractInfo.contractNum + ' - ' + contractInfo.contractName;
          this.contractNameChange(item);
          this.changeDetailForm = Object.assign({}, this.changeDetailForm, this.formData);
        }
      },
      submitForm(formName) {
        let url = '';
        if (this.dialogTitle === '新增') {
          url = '/wholeProcess/changeDetail/save/saveChangeDetail';
        } else if (this.dialogTitle === '修改') {
          url = '/wholeProcess/changeDetail/modify/updateChangeDetail';
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              const vm = this;
              vm.changeDetailForm.projectId = this.projectId;
              vm.$axios.post(url, JSON.stringify(vm.changeDetailForm), vm.config).then(function (response) {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (response.data.status === "success") {
                  vm.$emit('showDialog', false);
                  // 清空上次添加的表单内容
                  vm.changeDetailForm = {};
                  // 查询列表数据
                  vm.bus.$emit('refersPagingData');
                  vm.$message({
                    type: 'success',
                    message: response.data.message,
                    center: true
                  });
                } else {
                  vm.$message({
                    type: 'error',
                    message: response.data.message,
                    center: true
                  });
                }
              }).catch(function (error) {
                vm.$message({
                  type: 'error',
                  message: error,
                  center: true
                });
              });
            }).catch(() => {
            });
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
