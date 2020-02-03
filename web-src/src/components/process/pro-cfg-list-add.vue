<template>
  <div>
    <el-dialog title="新增" :visible="isShowAddDialog" width="500px" transition="fade-in-linear" center @close="onClose">
      <el-form :model="processConfigForm" ref="processConfigForm" label-width="100px" :rules="rules"
               class="demo-ruleForm">
        <el-form-item label="流程名称" prop="processName">
          <common-select :formModel.sync="processConfigForm.processName" :data="processSelectData"
                         @optionChanged="processChange" :dataFormat="this.selectDataFormat"></common-select>
        </el-form-item>
        <el-form-item label="业务线" prop="departmentName">
          <common-select :formModel.sync="processConfigForm.departmentName" :data="departmentSelectData"
                         @optionChanged="departmentChange" :dataFormat="this.selectDataFormat"></common-select>
        </el-form-item>
        <el-form-item label="职位" prop="jobName">
          <common-select :formModel.sync="processConfigForm.jobName" :data="jobSelectData"
                         @optionChanged="jobChange" :dataFormat="this.selectDataFormat"></common-select>
        </el-form-item>
        <el-form-item label="节点" prop="nodeName">
          <common-select :formModel.sync="processConfigForm.nodeName" :data="nodeSelectData"
                         @optionChanged="nodeChange" :dataFormat="this.selectDataFormat"></common-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('processConfigForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

  import CommonSelect from '../common/common-select'
  import proCfgCommon from './pro-cfg-common'

  export default {
    name: "pro-cfg-list-add",
    components: {CommonSelect},
    props: ['isShowAddDialog'],
    data() {
      return {
        rules: proCfgCommon.formRules,
        processConfigForm: JSON.parse(JSON.stringify(proCfgCommon.processConfigForm)),
        processName: '',
        departmentName: '',
        processSelectData: [],
        selectDataFormat: {value: 'value', label: 'text'},
        departmentSelectData: [],
        jobSelectData: [],
        nodeSelectData: []
      }
    },
    watch: {
      processName: function (newVal) {
        this.processName = newVal;
        this.processConfigForm.nodeName = '';
      },
      departmentName: function (newVal) {
        this.departmentName = newVal;
        this.processConfigForm.jobName = '';
      }
    },
    mounted() {
      const vm = this;
      proCfgCommon.loadSelect(vm, '/process/config/searchProcessConfigSelect', {}, 'processSelectData');
      proCfgCommon.loadSelect(vm, '/process/config/searchDepartmentSelect', {}, 'departmentSelectData');
    },
    methods: {
      onClose() {
        this.processConfigForm = {};
        this.$emit('update:isShowAddDialog', false);
      },
      processChange(item) {
        let processDictId = item.value;
        this.processName = item.label;
        proCfgCommon.loadSelect(this, '/process/config/searchNodeSelect', {"processDictId": processDictId}, 'nodeSelectData');
        this.processConfigForm.processDictId = processDictId;
        this.processConfigForm.processName = item.label;
      },
      departmentChange(item) {
        let departmentId = item.value;
        this.departmentName = item.label;
        proCfgCommon.loadSelect(this, '/process/config/searchJobSelect', {"departmentId": departmentId}, 'jobSelectData');
        this.processConfigForm.departmentId = departmentId;
        this.processConfigForm.departmentName = item.label;
      },
      jobChange(item) {
        this.processConfigForm.jobId = item.value;
        this.processConfigForm.jobName = item.label;
      },
      nodeChange(item) {
        this.processConfigForm.nodeId = item.value;
        this.processConfigForm.nodeName = item.label;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              const vm = this;
              vm.$axios.post('/process/config/saveProcessNodeRelation', JSON.stringify(vm.processConfigForm), vm.config).then(function (response) {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (response.data.status === "success") {
                  vm.$emit('showDialog', false);
                  // 清空上次添加的表单内容
                  vm.processConfigForm = {};
                  // 查询列表数据
                  vm.bus.$emit('refersPagingData');
                  vm.$message({
                    type: 'success',
                    message: response.data.message,
                    center: true
                  });
                } else {
                  vm.$message({
                    type: 'error',
                    message: response.data.message,
                    center: true
                  });
                }
              }).catch(function (error) {
                vm.$message({
                  type: 'error',
                  message: error,
                  center: true
                });
              });
            }).catch(() => {
            });
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
