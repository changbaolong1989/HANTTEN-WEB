<template>
  <!-- 创建请款单 -->
  <el-dialog title="创建请款单" :visible.sync="show" width="600px" center @close="handleClose('form')"
    :close-on-click-modal="false">
    <el-form @submit.native.prevent :model="form" status-icon :rules="rules" ref="form" id="myform" label-width="100px" class="demo-ruleForm qingkuan"
      :inline-message="true">
      <div class="item-serial">
        <el-form-item label="请款单名"  class="name" prop="name">
          <el-input v-model="form.name" placeholder="请输入请款单名"></el-input>
        </el-form-item>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" form="myform" @click="submit('form')" class="linear">确认提交</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        show:false,
        form:{
          name:''
        },
         rules: {
          name: [
          {required: true, message: '请输入请款单名',trigger: 'blur'},
          { max: 50, message: '最长50字', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleClose(formName) {
        this.$refs[formName].resetFields();
      },
      submit(formName) {
        let vm = this;
        vm.$refs[formName].validate((valid) => {
            if (valid) {
                vm.$emit('submit',vm.form);
                vm.show = false;
                vm.handleClose(formName)
            }else{
                return false;
            }
        });
      }
    }
  }
</script>


<style lang="scss">
.qingkuan{
  .el-form-item{
    .el-input__suffix{
      display:none;
    }
    .el-form-item__label{
      min-width:80px !important;
    }
    .el-form-item__content{
      .el-input{
        input{
          width:100%;
        }
      }
    }
    .el-form-item__error--inline{
      position:absolute;
      left:0!important;
      top:100%;
    }
  }
}
</style>
