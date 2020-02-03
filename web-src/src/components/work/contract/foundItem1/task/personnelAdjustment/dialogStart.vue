<template>
  <!-- 创建请款单 -->
  <el-dialog class="dialogStart--personnelAdjustment" title="入场" :visible.sync="show" width="600px" center @close="handleClose('form')" :close-on-click-modal="false">
    <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px" class="demo-ruleForm"
      :inline-message="true">
      <div class="item-serial">
        <el-row>
          <el-col :span="24">
            <el-form-item label="人员名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入人员名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="入场时间" prop="startDate">
              <el-date-picker v-model="form.startDate" type="date" value-format="yyyy-MM-dd" placeholder="请输入入场时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="工作内容" prop="workContent">
              <el-input type="textarea" v-model="form.workContent" placeholder="请输入工作内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit('form')" class="linear">确认</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        show: false,
        form: {
          userName: '', // 人员名称
          startDate: '', // 入场时间
          workContent: '' // 工作内容
        },
        rules: {
          userName: [{ // 人员名称
            required: true,
            message: '请输入人员名称',
            trigger: 'blur'
          }],
          startDate: [{ // 入场时间
            required: true,
            message: '请输入入场时间',
            trigger: 'blur'
          }],
          workContent: [{ // 工作内容
            required: true,
            message: '请输入工作内容',
            trigger: 'blur'
          }]
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
            vm.$emit('submit',Object.assign({}, vm.form));
            vm.show = false;
            vm.handleClose(formName)
          }
        });
      }
    }
  }
</script>


<style lang="scss">
  .dialogStart--personnelAdjustment{
    .el-date-editor{
      width: 100%;
    }
    .el-date-editor{

    }
    .el-form-item__label,.el-form-item__content{
      line-height: 30px;
    }
    .el-form-item__error--inline,.el-form-item__error{
      position: absolute!important;
      top: calc(100% + 4px)!important;
      left: 0!important;
      display: inline-block;
      white-space: nowrap;
    }
    .item-serial{

      .el-row{
        margin-top: 23px;
      }

      .el-form-item--feedback{
        width:100%;
        .el-form-item__content{
          width:80%;
          .el-input__inner{
            width:100%;
          }
        }
      }
    }
  }
</style>
