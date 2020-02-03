<template>
  <div>
    <el-dialog :title="dialogTitle" :visible="isShowDialog" width="500px" transition="fade-in-linear" center
               @close="onClose" @open="onOpen">
      <el-form :model="statementForm" ref="statementForm" label-width="130px" :rules="formRules"
               class="demo-ruleForm">
        <el-form-item label="合同编号-合同名" prop="contractName">
          <common-select :formModel.sync="statementForm.contractName" :data="this.contractNameSelectData"
                         @optionChanged="contractNameChange"
                         :dataFormat="this.contractNameSelectFormat"></common-select>
        </el-form-item>
        <el-form-item label="合同分类">
          <el-input v-model="statementForm.treatyTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="承包方式" v-show="statementForm.undertakeTypeName">
          <el-input v-model="statementForm.undertakeTypeName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="结算金额（元）" prop="statementAmount">
          <el-input v-model="statementForm.statementAmount" placeholder="请输入结算金额"></el-input>
        </el-form-item>
        <el-form-item label="图纸/方案（元）" prop="drawingScheme">
          <el-input v-model="statementForm.drawingScheme" placeholder="请输入图纸/方案"></el-input>
        </el-form-item>
        <el-form-item label="变更洽商（元）" prop="alterDiscuss">
          <el-input v-model="statementForm.alterDiscuss" placeholder="请输入变更洽商"></el-input>
        </el-form-item>
        <el-form-item label="签证（元）" prop="visa">
          <el-input v-model="statementForm.visa" placeholder="请输入签证"></el-input>
        </el-form-item>
        <el-form-item label="材料设备调差（元）" prop="adjustPrice">
          <el-input v-model="statementForm.adjustPrice" placeholder="材料设备调差"></el-input>
        </el-form-item>
        <el-form-item label="物价波动调差（元）" prop="surgePrice">
          <el-input v-model="statementForm.surgePrice" placeholder="请输入物价波动调差"></el-input>
        </el-form-item>
        <el-form-item label="总包服务费（元）" prop="serviceFee">
          <el-input v-model="statementForm.serviceFee" placeholder="请输入总包服务费"></el-input>
        </el-form-item>
        <el-form-item label="税金调差（元）" prop="taxPrice">
          <el-input v-model="statementForm.taxPrice" placeholder="请输入税金调差"></el-input>
        </el-form-item>
        <el-form-item label="其他（元）" prop="otherThing">
          <el-input v-model="statementForm.otherThing" placeholder="请输入其他"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="statementForm.remark" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('statementForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

  import DefaultScript from './default'
  import CommonSelect from '@/components/common/common-select'

  export default {
    name: "close-account-form",
    props: ['isShowDialog', 'dialogTitle', 'formData'],
    components: {CommonSelect},
    data() {
      return {
        statementForm: JSON.parse(JSON.stringify(DefaultScript.statementForm)),
        contractNameSelectFormat: {value: 'contractId', label: 'contractNum,contractName'},
        projectId: this.$parent.$parent.projectId,
        verify: {'moneyCheck': this.moneyCheck, 'checkNumAlphaSymbol': this.checkNumAlphaSymbol},
      }
    },
    computed: {
      contractNameSelectData: {
        get: function () {
          return this.$store.state.wholeProcess.statementObject.contractInfo;
        }
      },
      formRules() {
        return DefaultScript.getFormRules(this.verify);
      }
    },
    methods: {
      contractNameChange(item) {
        let contractInfo = DefaultScript.getContractInfo(this.contractNameSelectData, item.value);
        this.statementForm.contractName = item.label;
        this.statementForm.contractId = item.value;
        this.statementForm.treatyTypeName = DefaultScript.treatyType[contractInfo.treatyType];
        this.statementForm.undertakeTypeName = DefaultScript.undertakeType[contractInfo.undertakeType];
      },
      onClose() {
        if (this.dialogTitle === '新增') {
          this.statementForm = {};
        }
        this.$emit('update:isShowDialog', false);
        this.$refs['statementForm'].resetFields();
      },
      onOpen() {
        if (this.dialogTitle === '修改') {
          let contractInfo = DefaultScript.getContractInfo(this.contractNameSelectData, this.formData.contractId);
          let item = {};
          item.value = this.formData.contractId;
          item.label = contractInfo.contractNum + ' - ' + contractInfo.contractName;
          console.dir(item);
          Object.assign(this.statementForm, this.formData);
          this.contractNameChange(item);
        }
      },
      submitForm(formName) {
        let url = '';
        if (this.dialogTitle === '新增') {
          url = '/wholeProcess/statement/save/saveStatement';
        } else if (this.dialogTitle === '修改') {
          url = '/wholeProcess/statement/modify/updateStatement';
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              const vm = this;
              vm.statementForm.projectId = this.projectId;
              vm.$axios.post(url, JSON.stringify(vm.statementForm), vm.config).then(function (response) {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (response.data.status === "success") {
                  vm.$emit('showDialog', false);
                  // 清空上次添加的表单内容
                  if(vm.dialogTitle !== '修改'){
                    vm.statementForm = {};
                  }
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
