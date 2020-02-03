<template>
  <el-form :inline="true" class="demo-form-inline bazaar-form">
    <el-form-item label="流程名称" prop="processName">
      <common-select :formModel.sync="processConfigForm.processName" :data="processSelectData"
                     @optionChanged="processChange" :dataFormat="this.selectDataFormat"></common-select>
    </el-form-item>
    <el-form-item label="业务线" prop="departmentName">
      <common-select :formModel.sync="processConfigForm.departmentName" :data="departmentSelectData"
                     @optionChanged="departmentChange" :dataFormat="this.selectDataFormat"></common-select>
    </el-form-item>
    <el-form-item class="right-formItem">
      <el-button type="primary" @click="queryData()">查询</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

  import proCfgCommon from './pro-cfg-common'
  import CommonSelect from '../common/common-select'

  export default {
    name: "pro-cfg-query-ctn",
    components: {CommonSelect},
    data() {
      return {
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        processConfigForm: JSON.parse(JSON.stringify(proCfgCommon.processConfigForm)),
        processSelectData: [],
        departmentSelectData: [],
        selectDataFormat: {value: 'value', label: 'text'},
        queryUrl: '/process/config/searchProcessConfigInfo'
      }
    },
    computed: {
      processConfigData: {
        get: function () {
          return this.$store.state.processConfigObject.data;
        },
        set: function (newVal) {
          this.$store.state.processConfigObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.processConfigObject.total;
        },
        set: function (newVal) {
          this.$store.state.processConfigObject.total = newVal;
        }
      },
      queryCondition: {
        get: function () {
          return this.$store.state.processConfigObject.queryCondition;
        },
        set: function (newVal) {
          this.$store.state.processConfigObject.queryCondition = newVal;
        }
      }
    },
    mounted() {
      const vm = this;
      proCfgCommon.loadSelect(vm, '/process/config/searchProcessConfigSelect', {}, 'processSelectData');
      proCfgCommon.loadSelect(vm, '/process/config/searchDepartmentSelect', {}, 'departmentSelectData');
    },
    methods: {
      processChange(item) {
        console.log(item);
        this.processConfigForm.processDictId = item.value;
        this.processConfigForm.processName = item.label;
      },
      departmentChange(item) {
        this.processConfigForm.departmentId = item.value;
        this.processConfigForm.departmentName = item.label;
      },
      queryData() {
        const vm = this;
        let pageParam = {"pageNumber": vm.currentPage, "pageSize": vm.pageSize};
        let formParam = this.processConfigForm;
        let param = Object.assign({}, pageParam, formParam);
        vm.$axios.post(vm.queryUrl, JSON.stringify(param), vm.config).then(function (response) {
          if (response.data.status === "success") {
            vm.processConfigData = response.data.pagedData.dataList;
            //vm.$store.commit('changeProcessConfigData',response.data.pagedData.dataList);
            // 循环数据放入表格数据对象中
            vm.currentChangePage(vm.processConfigData, vm.currentPage, vm.pageSize);
            // 设置数据总条数
            vm.total = response.data.pagedData.dataCount;
            vm.queryCondition = formParam;
          } else {
            vm.$message({
              type: 'error',
              message: response.data.message
            });
          }
        });
        proCfgCommon.loadSelect(vm, '/process/config/searchProcessConfigSelect', {}, 'processSelectData');
        proCfgCommon.loadSelect(vm, '/process/config/searchDepartmentSelect', {}, 'departmentSelectData');
      },
      //分页方法
      currentChangePage(list) {
        //this.$store.commit('changeProcessConfigData', list);
        this.processConfigData = list;
      }
    }
  }
</script>

<style scoped>

</style>
