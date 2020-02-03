<template>
  <!-- 弹窗-招标规划 -->
  <el-dialog title="编辑" :visible="show" width="50%" transition="fade-in-linear" center @close="handClose('form','unitProjectList')"
    :close-on-click-modal="false">
    <el-form :model="form" :rules="rules" ref="form" label-width="110px" class="demo-form">
      <el-row class="add">
        <el-col :span="12">
          <span>招标规划</span>
          <i class="el-icon-plus btn-plus" @click="addAform"></i>
        </el-col>
      </el-row>

      <div v-for="(item,value) in form.unitProjectList" :key="value" class="demo-form">
        <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(item,value)"></el-button>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="工程名称" :prop="'unitProjectList['+value+'].engineeringName'" :rules="[{ required: true, message: '请输入工程名称', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入工程名称" v-model="item.engineeringName">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标方式" :prop="'unitProjectList['+value+'].tenderMethod'" :rules="[{ required: true, message: '请输入招标方式', trigger: 'change' }]">
              <el-select v-model="item.tenderMethod" placeholder="请选择招标方式">
                <el-option v-for="(opt,i) in options" :key="i" :label="opt.display" :value="opt.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="招标形式" :prop="'unitProjectList['+value+'].tenderType'" :rules="[{ required: true, message: '请输入招标形式', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入招标形式" v-model="item.tenderType">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标平台" :prop="'unitProjectList['+value+'].tenderPlatform'" :rules="[{ required: true, message: '请输入招标平台', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入招标平台" v-model="item.tenderPlatform">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="投标人资格要求" :prop="'unitProjectList['+value+'].investorRequire'" :rules="[{ required: true, message: '请输入投标人资格要求', trigger: 'blur' }]">
              <el-input type="text" placeholder="请输入投标人资格要求" v-model="item.investorRequire">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
    methods: {
      handClose(index, index1) {
        this.$refs[index].resetFields();
        this.form.unitProjectList = []
        this.show = false;
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
          engineeringName: "", // 项目名称
          tenderMethod: "", // 招标方式
          tenderType: "", // 招标形式
          investorRequire: "", // 投标人资格要求
          tenderPlatform: "" // 招标平台
        })

      },
      colseForm(item, value) {
        this.form.unitProjectList.splice(value, 1)
      }
    },
    data() {
      return {
        show: false, // 是否显示dialog
        form: {
          relationId:"",
          unitProjectList: []
        },
        rules: {},
        options: [{
            display: '公开招标',
            id: '公开招标'
          },
          {
            display: '邀请招标',
            id: '邀请招标'
          },
          {
            display: '比选',
            id: '比选'
          },
          {
            display: '竞争性谈判',
            id: '竞争性谈判'
          },
          {
            display: '政府采购',
            id: '政府采购'
          },
          {
            display: '询价',
            id: '询价'
          },
          {
            display: '单一来源',
            id: '单一来源'
          }
        ]
      }
    }
  }
</script>

<style lang="scss">
  @import url('../common/css/form.css');
</style>
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
