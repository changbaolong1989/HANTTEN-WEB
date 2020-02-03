<template>
  <!-- 创建请款单 -->
  <el-dialog class="dialogEnd--personnelAdjustment" title="离场" :visible.sync="show" width="600px" center @close="handleClose('currentForm')"
    :close-on-click-modal="false">
    <el-form :model="currentForm" status-icon :rules="rules" ref="currentForm" label-width="100px" class="demo-ruleForm"
      :inline-message="true">
      <div class="item-serial">
        <el-row>
          <el-col :span="24">
            <el-form-item label="人员名称" prop="userName">
              <el-input v-model="currentForm.userName" placeholder="请输入人员名称" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="入场时间" prop="startDate">
              <el-date-picker v-model="currentForm.startDate" type="date" placeholder="请输入入场时间" disabled>
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="离场时间" prop="endDate">
              <el-date-picker v-model="currentForm.endDate" type="date" value-format="yyyy-MM-dd" placeholder="请输入离场时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit('currentForm')" class="linear">确认</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    props: ['form'], // 配置对象
    data() {
      return {
        show: false,
        currentForm: this.form|| {
          relationId: '', // 人员主键
          userName: '', // 人员名称
          startDate: '', // 入场时间
          endDate: '' // 离场时间
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
          endDate: [{ // 离场时间
            required: true,
            message: '请输入离场时间',
            trigger: 'blur'
          }]
        }
      }
    },
    watch:{
      form(){// 监听表单对象
        let vm = this;
        vm.currentForm = vm.form;
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
            vm.$emit('submit',Object.assign({}, vm.currentForm));
            vm.show = false;
            vm.handleClose(formName)
          }
        });
      }
    }
  }
</script>


<style lang="scss">
  .dialogEnd--personnelAdjustment {
    .el-date-editor {
      width: 100%;
    }

    .el-date-editor {}

    .el-form-item__label,
    .el-form-item__content {
      line-height: 30px;
    }

    .el-form-item__error--inline,
    .el-form-item__error {
      position: absolute !important;
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
