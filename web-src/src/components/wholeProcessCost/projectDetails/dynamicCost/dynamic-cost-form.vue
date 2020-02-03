<template>
  <div>
    <el-dialog :title="dialogTitle" :visible="isShowDialog" width="500px" transition="fade-in-linear" center
               @close="onClose" @open="onOpen">
      <el-form :model="dynamicForm" ref="dynamicForm" label-width="150px"
               class="demo-ruleForm" :rules="formRules">
        <el-form-item label="合同编号">
          <el-input v-model="dynamicForm.contractNum" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="未定标金额总计（元）" prop="uncalibratedPrice">
          <el-input v-model="dynamicForm.uncalibratedPrice"></el-input>
        </el-form-item>
        <el-form-item label="暂定量/项调整（元）" prop="temporaryAmount">
          <el-input v-model="dynamicForm.temporaryAmount"></el-input>
        </el-form-item>
        <el-form-item label="图差数量调整（元）" prop="drawingAdjust">
          <el-input v-model="dynamicForm.drawingAdjust"></el-input>
        </el-form-item>
        <el-form-item label="暂估单价调整（元）" prop="signelAdjust">
          <el-input v-model="dynamicForm.signelAdjust"></el-input>
        </el-form-item>
        <el-form-item label="材料市场调整（元）" prop="materialAdjust">
          <el-input v-model="dynamicForm.materialAdjust"></el-input>
        </el-form-item>
        <el-form-item label="人工市场调整（元）" prop="peopleAdjust">
          <el-input v-model="dynamicForm.peopleAdjust"></el-input>
        </el-form-item>
        <el-form-item label="其他（元）" prop="otherThing">
          <el-input v-model="dynamicForm.otherThing"></el-input>
        </el-form-item>
        <el-form-item label="税金调整（元）" prop="taxAdjust">
          <el-input v-model="dynamicForm.taxAdjust"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="dynamicRemark">
          <el-input v-model="dynamicForm.dynamicRemark"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('dynamicForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>


  import DefaultScript from './default'

  export default {
    name: "dynamic-cost-form",
    props: ['isShowDialog', 'dialogTitle', 'formData'],
    data() {
      return {
        dynamicForm: JSON.parse(JSON.stringify(DefaultScript.dynamicForm)),
        projectId: this.$parent.$parent.projectId,
        verify: {'moneyCheck': this.moneyCheck}
      }
    },
    watch: {
      formData: function (newVal) {
        Object.assign(this.dynamicForm, newVal);
      }
    },
    computed: {
      formRules() {
        return DefaultScript.getFormRules(this.verify);
      }
    },
    methods: {
      onClose() {
        this.$emit('update:isShowDialog', false)
        if (this.dialogTitle === '修改') {
          this.$refs['dynamicForm'].resetFields();
        }
      },
      onOpen() {
        if (this.dialogTitle === '修改') {
          this.dynamicForm = Object.assign({}, this.formData);
        }
      },
      submitForm(formName) {
        let url = '';
        if (this.dialogTitle === '新增') {
          //url = '/wholeProcess/statement/save/saveStatement';
        } else if (this.dialogTitle === '修改') {
          url = '/wholeProcess/cost/modify/updateCost';
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              const vm = this;
              vm.dynamicForm.projectId = this.projectId;
              vm.$axios.post(url, JSON.stringify(vm.dynamicForm), vm.config).then(function (response) {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (response.data.status === "success") {
                  vm.$emit('showDialog', false);
                  // 清空上次添加的表单内容
                  if (vm.dialogTitle !== '修改') {
                    vm.dynamicForm = {};
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
