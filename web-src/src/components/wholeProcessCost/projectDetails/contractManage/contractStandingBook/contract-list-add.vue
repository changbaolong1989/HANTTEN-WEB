<template>
  <div>
    <el-dialog :title="dialogTitle" :visible="isShowDialog" width="500px" transition="fade-in-linear" center
               @close="onClose">
      <el-form :model="contractForm" ref="contractForm" label-width="100px" :rules="formRules"
               class="demo-ruleForm">
        <el-form-item label="合同名称" prop="contractName">
          <common-select :formModel.sync="contractForm.contractName" :data="this.contractNameSelectData"
                         @optionChanged="contractNameChange"
                         :dataFormat="this.contractNameSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="合同分类">
          <el-input v-model="contractForm.treatyTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="承包方式" v-show="contractForm.undertakeType">
          <el-input v-model="contractForm.undertakeTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNum">
          <el-input v-model="contractForm.contractNum" placeholder="请输入合同编号"></el-input>
        </el-form-item>
        <el-form-item label="合作单位" prop="contractUnit">
          <el-input v-model="contractForm.contractUnit" placeholder="请输入合作单位"></el-input>
        </el-form-item>
        <el-form-item label="计划工期" prop="planDate">
          <el-input v-model="contractForm.planDate" placeholder="请输入计划工期"></el-input>
        </el-form-item>
        <el-form-item label="合同类型" prop="contractClass">
          <common-select :formModel.sync="contractForm.contractClassName" :data="contractClassSelectData"
                         @optionChanged="contractClassChange"
                         :dataFormat="this.contractClassSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="重计量状态" prop="measureSts">
          <common-select :formModel.sync="contractForm.measureStsName" :data="measureStsSelectData"
                         @optionChanged="measureStsChange" :dataFormat="this.measureStsSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="目标成本（元）" prop="targetCost">
          <el-input v-model="contractForm.targetCost" placeholder="请输入目标成本"></el-input>
        </el-form-item>
        <el-form-item label="除税金额（元）" prop="exciseMoney">
          <el-input v-model="contractForm.exciseMoney" placeholder="请输入除税金额"></el-input>
        </el-form-item>
        <el-form-item label="税率（%）" prop="taxRatio">
          <el-input v-model="contractForm.taxRatio" placeholder="税率"></el-input>
        </el-form-item>
        <el-form-item label="税金（元）" prop="taxMoney">
          <el-input v-model="contractForm.taxMoney" placeholder="请输入税金"></el-input>
        </el-form-item>
        <el-form-item label="含税金额（元）" prop="withTaxMoney">
          <el-input v-model="contractForm.withTaxMoney" placeholder="请输入含税金额"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="contractForm.remark" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('contractForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import ContractCommon from './contract-common'
  import CommonSelect from '@/components/common/common-select'

  export default {
    name: "contract-list-add",
    components: {CommonSelect},
    data() {
      return {
        //rules: ContractCommon.formRules,
        contractForm: JSON.parse(JSON.stringify(ContractCommon.contractForm)),
        contractClassSelectData: ContractCommon.contractClassSelectData,
        measureStsSelectData: ContractCommon.measureStsSelectData,
        contractNameSelectFormat: {value: 'tenderId', label: 'tenderName'},
        contractClassSelectFormat: {value: 'value', label: 'text'},
        measureStsSelectFormat: {value: 'value', label: 'text'},
        verify: {
          'moneyCheck': this.moneyCheck,
          'checkNumAlphaSymbol': this.checkNumAlphaSymbol,
          'checkPercentage': this.checkPercentage
        },
        projectId: this.$parent.$parent.projectId
      }
    },
    computed: {
      contractNameSelectData: {
        get: function () {
          return this.$store.state.wholeProcess.contractObject.tenderInfo;
        }
      },
      formRules() {
        return ContractCommon.getFormRules(this.verify);
      }
    },
    props: ['isShowDialog', 'dialogTitle', 'formData'],
    watch: {
      formData: function (newVal) {
        this.contractForm = JSON.parse(JSON.stringify(newVal));
        this.contractForm.treatyTypeName = ContractCommon.treatyType[newVal.treatyType];
        this.contractForm.undertakeTypeName = ContractCommon.undertakeType[newVal.undertakeType];
        this.contractForm.contractClassName = ContractCommon.getContractClassText(newVal.contractClass);
        this.contractForm.measureStsName = ContractCommon.getMeasureStsText(newVal.measureSts);
      }
    },
    methods: {
      contractNameChange(item) {
        let tenderInfo = ContractCommon.getTenderInfo(this.contractNameSelectData, item.value);
        this.contractForm.contractName = item.label;
        this.contractForm.treatyType = tenderInfo.treatyType;
        this.contractForm.treatyTypeName = ContractCommon.treatyType[tenderInfo.treatyType];
        this.contractForm.tenderId = tenderInfo.tenderId;
        this.contractForm.undertakeType = tenderInfo.undertakeType;
        this.contractForm.undertakeTypeName = ContractCommon.undertakeType[tenderInfo.undertakeType];
      },
      contractClassChange(item) {
        this.contractForm.contractClass = item.value;
        this.contractForm.contractClassName = item.label;
      },
      measureStsChange(item) {
        this.contractForm.measureSts = item.value;
        this.contractForm.measureStsName = item.label;
      },
      onClose() {
        if (this.dialogTitle === '新增') {
          this.contractForm = {};
        }
        this.$emit('update:isShowDialog', false);
        this.$refs['contractForm'].resetFields();
      },
      submitForm(formName) {
        let url = '';
        if (this.dialogTitle === '新增') {
          url = '/wholeProcess/contract/save/saveWholeProcessContract';
        } else if (this.dialogTitle === '修改') {
          url = '/wholeProcess/contract/modify/updateWholeProcessContract';
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              const vm = this;
              vm.contractForm.projectId = this.projectId;
              vm.$axios.post(url, JSON.stringify(vm.contractForm), vm.config).then(function (response) {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (response.data.status === "success") {
                  vm.$emit('showDialog', false);
                  // 清空上次添加的表单内容
                  vm.contractForm = {};
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
