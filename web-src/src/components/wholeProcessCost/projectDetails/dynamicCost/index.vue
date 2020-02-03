<template>
  <div style="width: 1550px">
    <common-export :exportUrl="this.exportUrl"></common-export>
    <div class="item-tab-bottom">

      <div class="item-tab-chuang">
        <span></span>
      </div>

      <el-table :data="costData" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="@/assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader,index) in dataHeaders"
                         :label="dataHeader.name" :prop="dataHeader.name" :key="index++" width="150" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <label v-if="index===2">{{costData[scope.$index][dataHeader.id] | filterNull | filterTreatyType}}</label>
            <label v-else-if="index===3"> {{costData[scope.$index][dataHeader.id] | filterNull |
              filterUndertakeType}}</label>
            <label v-else>{{costData[scope.$index][dataHeader.id] | filterNull}}</label>
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
    <dynamic-cost :isShowDialog.sync="isShowEditDialog" @showDialog="showDialog" :formData="rowData"
                  :dialogTitle="dialogTitle"></dynamic-cost>
  </div>
</template>

<script>

  import DefaultScript from './default'
  import CommonPaging from '@/components/common/common-paging'
  import CommonListBtnAdd from '@/components/common/common-list-btn-add'
  import DynamicCost from './dynamic-cost-form'
  import CommonExport from '@/components/common/common-export'


  export default {
    name: "index",
    data() {
      return {
        dataHeaders: DefaultScript.listHeaders,
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        queryUrl: '/wholeProcess/cost/query/searchCost',
        exportUrl: '/wholeProcess/cost/export/exportDynamicCostData?realFileName=' + "动态成本.xlsx" + "&projectId=" + this.$parent.projectId,
/*        queryCondition: {
          "projectId": projectId
        },*/
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
      filterNull(val) {
        return val == null ? '-' : val;
      }
    },
    mounted() {
      const vm = this;
      vm.$axios.post(vm.queryUrl, JSON.stringify({
        "pageNumber": vm.currentPage, "pageSize": vm.pageSize, "projectId": vm.projectId
      }), vm.config).then(function (response) {
        if (response.data.status === "success") {
          vm.costData = response.data.pagedData.dataList;
          //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
          // 循环数据放入表格数据对象中
          vm.currentChangePage(vm.costData, vm.currentPage, vm.pageSize);
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
    components: {CommonPaging, CommonListBtnAdd, DynamicCost, CommonExport},
    computed: {
      costData: {
        get: function () {
          return this.$store.state.wholeProcess.costObject.data;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.costObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.wholeProcess.costObject.total;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.costObject.total = newVal;
        }
      },
      queryCondition() {
        return {
          "projectId": this.projectId
        };
      }
    },
    methods: {
      pagingQueryData(res) {
        const vm = this;
        // 设置表格数据
        vm.costData = res.data.pagedData.dataList;
        // 循环数据放入表格数据对象中
        vm.currentChangePage(vm.costData, vm.currentPage, vm.pageSize);
        // 设置数据总条数
        vm.total = res.data.pagedData.dataCount;
      },
      currentChangePage(list) {
        this.costData = list;
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
