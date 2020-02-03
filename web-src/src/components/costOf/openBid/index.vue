<template>
  <!-- 开标评标 -->
  <div class="cost-of__rxpert-opinion-list">
    <div class="form-block">
      <el-form class="form" ref="form" :model="form" :disabled="disabled" :rules="rules">
        <el-row :gutter="gutter">
          <el-col :span="18" style="padding-left: 10px;">开标、评标</el-col>
          <el-col :span="6" class="text-align-right"><el-button size="mini" type="primary" @click="submit">保存</el-button></el-col>
        </el-row>
        <el-row :gutter="gutter" justify="space-between">
          <el-col :span="12">
            <el-form-item label="开标时间" prop="bidTime">
              <el-date-picker
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm"
                  v-model="form.bidTime"
                  type="datetime"
                  placeholder="选择开标时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="评标时间" prop="evaluationTime">
              <el-date-picker
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm"
                  v-model="form.evaluationTime"
                  type="datetime"
                  placeholder="选择评标时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import dataJs from './data.js';
export default {
  data() {
    return {
      source: this.tool.deepCopy(dataJs, this.tool.deepCopy), // 源数据
      gutter: 24,
      // 表单数据
      form: this.tool.deepCopy(this.data ? this.data.form : dataJs.form, this.tool.deepCopy),
      // 表单验证
      rules: this.tool.deepCopy(dataJs.rules, this.tool.deepCopy),
    };
  },
  props: {
    // 传输数据
    data: {
      type: Object,
      default() {
        return {
          // 默认表单数据
          form: this.tool.deepCopy(dataJs.form, this.tool.deepCopy)
        };
      }
    },
    // 是否禁用表单
    disabled: {
      type: Boolean,
      default: false
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
      vm.$emit('submit', vm.getFormData('form'));
    },
    /**
     * @method 获取表单数据
     * @param {String} formName 表单名称
     */
    getFormData(formName) {
      let vm = this;
      return new Promise(function(resolve, reject) {
        vm.$refs[formName].validate(valid => {
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
      vm.form = vm.tool.deepCopy(data ? data.form : vm.source.form, vm.tool.deepCopy);
    }
  }
};
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
