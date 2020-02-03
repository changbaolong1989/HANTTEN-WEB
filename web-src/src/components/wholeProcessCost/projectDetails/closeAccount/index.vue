<template>
  <div style="width: 1550px">
    <common-list-btn-add :addComponentKey="addComponentKey" :addComponentText="addComponentText"
                         style="float: right"></common-list-btn-add>
    <common-export :exportUrl="this.exportUrl"></common-export>

    <div class="item-tab-bottom">
      <el-table :data="statementData" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="@/assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader,index) in dataHeaders"
                         :label="dataHeader.name" :prop="dataHeader.name" :key="index++" width="150" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <label v-if="index===2">{{statementData[scope.$index][dataHeader.id] | filterTreatyType}}</label>
            <label v-else-if="index===3">{{statementData[scope.$index][dataHeader.id] | filterUndertakeType}}</label>
            <label v-else-if="index===7">{{statementData[scope.$index][dataHeader.id] | filterContractClass}}</label>
            <label v-else>{{statementData[scope.$index][dataHeader.id]}}</label>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作">
          <template slot-scope="scope">
            <el-button type="text" class="modify" @click="update(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <common-paging :total="this.total" :queryUrl="queryUrl" @pagingQueryData="pagingQueryData"
                   :queryCondition="queryCondition"></common-paging>
    <statement-form :isShowDialog.sync="isShowEditDialog" @showDialog="showDialog"
                    :formData="rowData" :dialogTitle="dialogTitle"></statement-form>
  </div>
</template>

<script>

  import DefaultScript from './default'
  import CommonPaging from '@/components/common/common-paging'
  import CommonListBtnAdd from '@/components/common/common-list-btn-add'
  import StatementForm from './close-account-form'
  import CommonExport from '@/components/common/common-export'

  export default {
    name: "index",
    data() {
      return {
        addComponentKey: 'WholeProcessStatementAdd',
        addComponentText: '创建结算台账',
        dataHeaders: DefaultScript.listHeaders,
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        queryUrl: '/wholeProcess/statement/query/searchWholeProcessStatement',
        exportUrl: '/wholeProcess/statement/export/exportStatementData?realFileName=' + "结算台账.xlsx" + "&projectId=" + this.$parent.projectId,
        queryCondition: {
          "projectId": this.$parent.projectId
        },
        projectId: this.$parent.projectId,
        isShowEditDialog: false,
        dialogTitle: '修改',
        rowData: {}
      }
    },
    filters: {
      filterTreatyType(val) {
        return DefaultScript.treatyType[val];
      },
      filterUndertakeType(val) {
        return DefaultScript.undertakeType[val];
      },
      filterContractClass(val) {
        return DefaultScript.getText(val, DefaultScript.contractClassSelectData);
      },
    },
    mounted() {
      const vm = this;
      vm.$axios.post(vm.queryUrl, JSON.stringify({
        "pageNumber": vm.currentPage, "pageSize": vm.pageSize, "projectId": vm.projectId
      }), vm.config).then(function (response) {
        if (response.data.status === "success") {
          vm.statementData = response.data.pagedData.dataList;
          //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
          // 循环数据放入表格数据对象中
          vm.currentChangePage(vm.statementData, vm.currentPage, vm.pageSize);
          // 设置数据总条数
          vm.total = response.data.pagedData.dataCount;
        } else {
          vm.$message({
            type: 'error',
            message: response.data.message
          });
        }
      });
      this.initContractInfo(vm);
    },
    components: {CommonPaging, CommonListBtnAdd, StatementForm, CommonExport},
    computed: {
      statementData: {
        get: function () {
          return this.$store.state.wholeProcess.statementObject.data;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.statementObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.wholeProcess.statementObject.total;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.statementObject.total = newVal;
        }
      },
      contractInfo: {
        get: function () {
          return this.$store.state.wholeProcess.statementObject.contractInfo;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.statementObject.contractInfo = newVal;
        }
      }
    },
    methods: {
      pagingQueryData(res) {
        const vm = this;
        // 设置表格数据
        vm.statementData = res.data.pagedData.dataList;
        // 循环数据放入表格数据对象中
        vm.currentChangePage(vm.statementData, vm.currentPage, vm.pageSize);
        // 设置数据总条数
        vm.total = res.data.pagedData.dataCount;
      },
      currentChangePage(list) {
        this.statementData = list;
      },
      initContractInfo(vm) {
        vm.$axios.post('/wholeProcess/changeDetail/query/searchContractName', JSON.stringify({"projectId": vm.projectId}), vm.config).then(function (response) {
          if (response.data.status === "success") {
            vm.contractInfo = response.data.dataList;
          } else {
            vm.$message({
              type: 'error',
              message: response.data.message
            });
          }
        });
      },
      update(data) {
        this.isShowEditDialog = true;
        this.rowData = data;
      },
      showDialog() {
        this.isShowEditDialog = false;
      },
    }
  }
</script>

<style scoped>

</style>
