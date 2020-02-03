<template>
  <!-- 弹窗-咨询工作记录 -->
  <el-dialog title="编辑" :visible.sync="show" width="50%" transition="fade-in-linear" center @close="handClose('form','unitProjectList')"
    :close-on-click-modal="false">
    <el-form :model="form" :rules="rules" ref="form" label-width="110px" class="demo-form">
      <el-row class="add">
        <el-col :span="12">
          <span>咨询工作记录</span>
          <i class="el-icon-plus btn-plus" @click="addAform"></i>
        </el-col>
      </el-row>

      <div v-for="(item,value) in form.unitProjectList" :key="value" class="demo-form">
        <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(item,value)"></el-button>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="咨询意见" :prop="'unitProjectList['+value+'].counselAdvise'" :rules="[{ required: true, message: '请输入咨询意见', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入咨询意见" v-model="item.counselAdvise">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="承包人回复意见" :prop="'unitProjectList['+value+'].responseAdvise'" :rules="[{ required: true, message: '请输入承包人回复意见', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入承包人回复意见" v-model="item.responseAdvise">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" class="linear" @click="submitForm('form')">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
   import tool from '@/assets/js/tool.js'// 工具箱
  export default {
    data() {
      return {
        show: false,
        form: {
          relationId:"",
          unitProjectList: []
        },
        rules: {}
      }
    },
    methods: {
      handClose(index, index1) {
        this.$refs[index].resetFields();
        this.form.unitProjectList = []
      },
      submitForm(formName) {
        let vm = this;
        vm.$refs[formName].validate((valid) => {
          if (valid) {
            vm.$emit('submit', vm.tool.deepCopy(vm.form,vm.tool.deepCopy));
          } else {
            return false;
          }
        });
      },
      addAform() {
        this.form.unitProjectList.push({
          counselAdvise: "", // 咨询意见
          responseAdvise: "" // 回复意见
        })

      },
      colseForm(item, value) {
        this.form.unitProjectList.splice(value, 1)
      }
    }
  }
</script>

<style lang="scss"></style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');

  .demo-form {
    position: relative;
    padding-top: 20px;

    .colseform {
      position: absolute;
      top: -10px;
      right: 0px;
    }
  }
</style>
