<template>
  <!-- 补充协议中间主体内容-->
  <div class="index">
    <div class="form-block">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="form">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="金额" prop="money"><el-input v-model="ruleForm.money"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="备注" prop="remark"><el-input type="textarea" v-model="ruleForm.remark"></el-input></el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    var validate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入金额'));
      } else {
        var reg = /^[0-9\u4e00-\u9fa5]+$/g;
        // if(){

        //   }
        callback();
      }
    };
    return {
      ruleForm: {
        money: '',
        remark: ''
      },
      gutter: 20,
      rules: {
        money: [{ validator: validate, trigger: 'blur' }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style></style>
