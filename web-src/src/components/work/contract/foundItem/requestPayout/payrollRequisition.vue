<template>
  <!-- 请款单表单-->
  <el-form class="form payrollRequisition" ref="form" :model="currentForm" :rules="rules">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="公司名称" prop="input2">
            <el-input v-model="currentForm.input2" placeholder="请输入公司名称" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="项目名称" prop="input3">
            <el-input v-model="currentForm.input3" placeholder="请输入项目名称" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="费用名称" prop="input4">
            <el-input v-model="currentForm.input4" placeholder="请输入费用名称" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程节点请款时间" prop="input5">
            <el-date-picker v-model="currentForm.input5" type="date" placeholder="选择工程节点请款时间" :disabled="childDisabledFlag">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="编制工作内容" prop="input6">
            <el-input v-model="currentForm.input6" placeholder="请输入编制工作内容" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="双方签订的合同" prop="input7">
            <el-input v-model="currentForm.input7" placeholder="请输入双方签订的合同" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合同类型" prop="input8">
            <el-input v-model="currentForm.input8" placeholder="请输入合同类型" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="收费说明" prop="input9">
            <el-input v-model="currentForm.input9" type="textarea" :rows="8" placeholder="请输入收费说明" :disabled="childDisabledFlag"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
  </el-form>
</template>

<script>
  export default {
    props:['form','disabledFlag'],
    data() {
      return {
        gutter: 40,
        childDisabledFlag:true,
        currentForm: {
          input2: "",
          input3: "",
          input4: "",
          input5: "",
          input6: "",
          input7: "",
          input8: "",
          input9: ""
        },
        rules: {
          input2: [
            {required: true, message: '请输入公司名称',trigger: 'blur'},
            {max: 30, message: '最长30字',trigger: 'blur'}
          ],
          input3: [
            {required: true,message: '请输入项目名称',trigger: 'blur'},
            {max: 30, message: '最长30字',trigger: 'blur'}
          ],
          input4: [
            {required: true,message: '请输入费用名称',trigger: 'blur'},
            {max: 30, message: '最长30字',trigger: 'blur'}
            ],
          input5: [
            {required: true, message: '选择工程节点请款时间',trigger: 'blur'}
          ],
          input6: [
            {required: true,message: '请输入编制工作内容',trigger: 'blur'},
            {max: 100, message: '最长100字',trigger: 'blur'}],
          input7: [
            {required: true,message: '请输入双方签订的合同',trigger: 'blur'},
            {max: 50, message: '最长50字',trigger: 'blur'}],
          input8: [
            {required: true,message: '请输入合同类型',trigger: 'blur'},
            {max: 20, message: '最长20字',trigger: 'blur'}],
          input9: [
            {required: true,message: '请输入收费说明',trigger: 'blur'},
            {max: 200, message: '最长200字',trigger: 'blur'}],
        }
      }
    },
    watch:{
      form:{
        handler(){
          let vm = this;
          vm.currentForm = vm.form;
        },
        deep:true,
        immediate: true
      },

      disabledFlag:{
        handler(){
          let vm = this;
          console.log("vm.disabledFlag",vm.disabledFlag)
          vm.childDisabledFlag = vm.disabledFlag;
        },
        deep:true,
        immediate: true
      }
    }
  }
</script>

<style lang="scss">

  .form.payrollRequisition {
    .el-form-item {
      .el-form-item__label {
        text-align: left;
        line-height: 30px;
        width: auto !important;
      }

      .el-form-item__content {
        margin-left: 0 !important;
        line-height: 30px;

        .el-input-group {
          vertical-align: middle;
        }
      }

      .el-input__inner {
        padding: 0 5px;
      }

      .el-date-editor {
        width: 100%;

        .el-input__inner {
          padding: 0 30px;
        }
      }

      /* 标准的表单布局 */
      &.normal {
        .el-form-item {
          display: block;
          margin-bottom: 10px;

          .el-form-item__label {
            text-align: right;
            width: 130px !important;
          }

          .el-form-item__content {
            margin-left: 130px !important;
          }
        }
      }
    }
  }
</style>
