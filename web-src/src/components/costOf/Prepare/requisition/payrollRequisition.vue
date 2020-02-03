<template>
  <!-- 请款单表单-->
  <el-form class="form" ref="form" :model="form" :rules="rule">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="档案编号" prop="input1">
            <el-input v-model="form.input1" placeholder="请输入档案编号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="公司名称" prop="input2">
            <el-input v-model="form.input2" placeholder="请输入公司名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="项目名称" prop="input3">
            <el-input v-model="form.input3" placeholder="请输入项目名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="费用名称" prop="input4">
            <el-input v-model="form.input4" placeholder="请输入费用名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程节点请款时间" prop="input5">
            <el-date-picker v-model="form.input5" type="date" placeholder="选择工程节点请款时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="编制工作内容" prop="input6">
            <el-input v-model="form.input6" placeholder="请输入编制工作内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="双方签订的合同" prop="input7">
            <el-input v-model="form.input7" placeholder="请输入双方签订的合同"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合同类型" prop="input8">
            <el-input v-model="form.input8" placeholder="请输入合同类型"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="收费说明" prop="input9">
            <el-input v-model="form.input9" type="textarea" placeholder="请输入收费说明"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
  </el-form>
</template>

<script>
  export default {
    data() {
      return {
        gutter: 40,
        form: {
          input1: "",
          input2: "",
          input3: "",
          input4: "",
          input5: "",
          input6: "",
          input7: "",
          input8: "",
          input9: "",
        },
        rule: {
          input5: [{
            required: true,
            message: '选择工程节点请款时间',
            trigger: 'blur'
          }],
        }
      }
    }
  }
</script>

<style>
</style>
