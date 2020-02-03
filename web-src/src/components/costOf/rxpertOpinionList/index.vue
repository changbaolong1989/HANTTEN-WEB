<template>
  <!-- 专家意见表 -->
  <div class="cost-of__rxpert-opinion-list">
    <div class="form-block">
      <el-form class="form" ref="form" :model="form" :disabled="disabled" :rules="rules">
        <el-row :gutter="gutter">
          <el-col :span="18" style="padding-left: 10px;">专家意见表</el-col>
          <el-col :span="6" class="text-align-right">
            <el-button size="mini" type="primary" @click="submit">保存</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称">
              <el-input v-model="form.projectName" placeholder="请输入项目名称" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号">
              <el-input v-model="form.projectNum" placeholder="请输入项目编号" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="采购代理机构" prop="agentOrg">
              <el-input v-model="form.agentOrg" placeholder="请输入采购代理机构"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购人" prop="purchaser">
              <el-input v-model="form.purchaser" placeholder="请输入采购人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="采购类别" prop="purchaserType">
              <el-input v-model="form.purchaserType" placeholder="请输入采购类别"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="专家论证意见" prop="expertOpinion">
              <el-input type="textarea" v-model="form.expertOpinion" placeholder="请输入专家论证意见"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="论证专家签字" prop="expertSign">
              <el-input v-model="form.expertSign" placeholder="请输入论证专家签字"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="论证时间" prop="proveTime">
              <el-date-picker v-model="form.proveTime" value-format="yyyy-MM-dd" type="date" placeholder="选择论证时间"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="form.remark" placeholder="请输入备注"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
  import dataJs from "./data.js"
  export default {
    props: {
      // 传输数据
      data: {
        type: Object,
        default () {
          return {
            // 默认表单数据
            form: this.tool.deepCopy(dataJs.form, this.tool.deepCopy),
          };
        }
      },
      // 是否禁用表单
      disabled: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        source: this.tool.deepCopy(dataJs, this.tool.deepCopy), // 源数据
        gutter: 20,
        // 表单数据
        form: this.tool.deepCopy(this.data?this.data.form:dataJs.form, this.tool.deepCopy),
        // 表单验证
        rules: this.tool.deepCopy(dataJs.rules, this.tool.deepCopy)
      }
    },
    watch: {
      data: {
        handler() {
          let vm = this;
          // 设置表单数据
          vm.setFormData(this.data);
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 提交表单
       */
      submit() {
        let vm = this;
        vm.$emit('submit',vm.getFormData('form'));
      },
      /**
       * @method 获取表单数据
       * @param {String} formName 表单名称
       */
      getFormData(formName) {
        let vm = this;
        return new Promise(function(resolve, reject) {
          vm.$refs[formName].validate((valid) => {
            if (valid) {
              resolve(vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
            } else {
              reject(null);
            }
          });
        });
      },
      /**
       * @method 设置表单数据
       * @param {Object} data 表单数据
       */
      setFormData(data) {
        let vm = this;
        vm.form = vm.tool.deepCopy(data?data.form:vm.source.form, vm.tool.deepCopy);
      },
      /**
       * @method 重置数据
       */
      reset() {
        let vm = this;
        // 重置表单数据
        vm.form = vm.tool.deepCopy(vm.source.form, vm.tool.deepCopy);
      }
    }
  }
</script>

<style lang="scss">
  @import '../common/scss/exclusive.scss';

  .cost-of__rxpert-opinion-list {
    margin-top: 10px;

    * {
      box-sizing: border-box;
    }
  }
</style>
