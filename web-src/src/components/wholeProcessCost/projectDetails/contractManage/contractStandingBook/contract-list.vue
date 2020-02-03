<template>
  <div style="width: 1550px">
    <common-export :exportUrl="this.exportUrl"></common-export>
    <div class="item-tab-bottom">
      <el-table :data="standingBookData" border style="width: 100%">
        <template slot="empty" class="tabdata-empty">
          <img src="@/assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader,index) in dataHeaders"
                         :label="dataHeader.name" :prop="dataHeader.name" :key="index++" width="150"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <label v-if="index===2">{{standingBookData[scope.$index][dataHeader.id] | filterTreatyType}}</label>
            <label v-else-if="index===3">{{standingBookData[scope.$index][dataHeader.id] | filterUndertakeType}}</label>
            <label v-else-if="index===7">{{standingBookData[scope.$index][dataHeader.id] | filterContractClass}}</label>
            <label v-else-if="index===8">{{standingBookData[scope.$index][dataHeader.id] | filterMeasureSts}}</label>
            <label v-else>{{standingBookData[scope.$index][dataHeader.id]}}</label>
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
    <contract-list-add :isShowDialog.sync="isShowEditDialog" @showDialog="showDialog"
                       :formData="rowData" :dialogTitle="dialogTitle"></contract-list-add>
  </div>
</template>

<script>

  import CommonPaging from '@/components/common/common-paging'
  import ContractCommon from './contract-common'
  import ContractListAdd from './contract-list-add'
  import CommonExport from '@/components/common/common-export'

  export default {
    name: "contract-list",
    components: {CommonPaging, ContractListAdd, CommonExport},
    data() {
      return {
        dataHeaders: ContractCommon.listHeaders,
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        queryUrl: '/wholeProcess/contract/query/searchWholeProcessContract',
        exportUrl: '/wholeProcess/contract/export/exportContractData?realFileName=' + "合同台账.xlsx" + "&projectId=" + this.$parent.$parent.projectId,
        isShowEditDialog: false,
        rowData: {},
        dialogTitle: '修改',
        projectId: this.$parent.$parent.projectId,
        queryCondition: {
          "projectId": this.$parent.$parent.projectId
        }
      }
    },
    filters: {
      filterTreatyType(val) {
        return ContractCommon.treatyType[val];
      },
      filterUndertakeType(val) {
        return ContractCommon.undertakeType[val];
      },
      filterContractClass(val) {
        return ContractCommon.getContractClassText(val);
      },
      filterMeasureSts(val) {
        return ContractCommon.getMeasureStsText(val);
      }
    },
    mounted() {
      const vm = this;
      vm.$axios.post(vm.queryUrl, JSON.stringify({
        "pageNumber": vm.currentPage,
        "pageSize": vm.pageSize,
        "projectId": vm.projectId
      }), vm.config).then(function (response) {
        if (response.data.status === "success") {
          vm.standingBookData = response.data.pagedData.dataList;
          //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
          // 循环数据放入表格数据对象中
          vm.currentChangePage(vm.standingBookData, vm.currentPage, vm.pageSize);
          // 设置数据总条数
          vm.total = response.data.pagedData.dataCount;
        } else {
          vm.$message({
            type: 'error',
            message: response.data.message
          });
        }
      });
    },
    computed: {
      standingBookData: {
        get: function () {
          return this.$store.state.wholeProcess.contractObject.data;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.contractObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.wholeProcess.contractObject.total;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.contractObject.total = newVal;
        }
      }
    },
    methods: {
      currentChangePage(list) {
        this.standingBookData = list;
      },
      update(data) {
        this.isShowEditDialog = true;
        this.rowData = data;
      },
      pagingQueryData(res) {
        const vm = this;
        // 设置表格数据
        vm.standingBookData = res.data.pagedData.dataList;
        // 循环数据放入表格数据对象中
        vm.currentChangePage(vm.standingBookData, vm.currentPage, vm.pageSize);
        // 设置数据总条数
        vm.total = res.data.pagedData.dataCount;
      },
      showDialog() {
        this.isShowEditDialog = false;
      },
    }
  }
</script>

<style scoped>

</style>
