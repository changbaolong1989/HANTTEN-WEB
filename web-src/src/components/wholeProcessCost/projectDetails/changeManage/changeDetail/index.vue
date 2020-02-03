<template>
  <div style="width: 1550px">
    <common-list-btn-add :addComponentKey="addComponentKey" :addComponentText="addComponentText"
                         style="float: right"></common-list-btn-add>
    <common-export :exportUrl="this.exportUrl"></common-export>

    <div class="item-tab-bottom">
      <el-table :data="changeDetailData" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="@/assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader,index) in dataHeaders"
                         :label="dataHeader.name" :prop="dataHeader.name" :key="index++" width="150" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <label v-if="index===1">{{changeDetailData[scope.$index][dataHeader.id] | filterProfession}}</label>
            <label v-else-if="index===6">{{changeDetailData[scope.$index][dataHeader.id] | filterDate}}</label>
            <label v-else-if="index===7">{{changeDetailData[scope.$index][dataHeader.id] | filterDate}}</label>
            <label v-else-if="index===8">{{changeDetailData[scope.$index][dataHeader.id] | filterSts}}</label>
            <label v-else-if="index===15">{{changeDetailData[scope.$index][dataHeader.id] | filterAlterAnaly}}</label>
            <label v-else>{{changeDetailData[scope.$index][dataHeader.id]}}</label>
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
    <change-detail-form :isShowDialog.sync="isShowEditDialog" @showDialog="showDialog"
                        :formData="rowData" :dialogTitle="dialogTitle"></change-detail-form>
  </div>
</template>

<script>

  import DefaultScript from './default'
  import CommonPaging from '@/components/common/common-paging'
  import CommonListBtnAdd from '@/components/common/common-list-btn-add'
  import ChangeDetailForm from './change-detail-form'
  import CommonExport from '@/components/common/common-export'

  export default {
    name: "index",
    data() {
      return {
        addComponentKey: 'WholeProcessChangeDetailAdd',
        addComponentText: '创建变更明细',
        dataHeaders: DefaultScript.listHeaders,
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        queryUrl: '/wholeProcess/changeDetail/query/searchChangeDetail',
        exportUrl: '/wholeProcess/changeDetail/export/exportChangeDetailData?realFileName=' + "变更洽商签证台帐.xlsx" + "&projectId=" + this.$parent.projectId,
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
      filterDate(val) {
        if (!val) return '-';
        let date = new Date(val);
        let year = date.getFullYear();
        let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        let day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        return year + '-' + month + '-' + day;
      },
      filterSts(val) {
        return DefaultScript.getText(val, DefaultScript.staSelectData);
      },
      filterProfession(val){
        return DefaultScript.getText(val, DefaultScript.professionalSelectData);
      },
      filterAlterAnaly(val){
        return DefaultScript.getText(val, DefaultScript.alterAnalyseSelectData);
      }
    },
    mounted() {
      const vm = this;
      vm.$axios.post(vm.queryUrl, JSON.stringify({
        "pageNumber": vm.currentPage, "pageSize": vm.pageSize, "projectId": vm.projectId
      }), vm.config).then(function (response) {
        if (response.data.status === "success") {
          vm.changeDetailData = response.data.pagedData.dataList;
          //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
          // 循环数据放入表格数据对象中
          vm.currentChangePage(vm.changeDetailData, vm.currentPage, vm.pageSize);
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
    components: {CommonPaging, CommonListBtnAdd, ChangeDetailForm, CommonExport},
    computed: {
      changeDetailData: {
        get: function () {
          return this.$store.state.wholeProcess.changeStandingBookObject.data;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.changeStandingBookObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.wholeProcess.changeStandingBookObject.total;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.changeStandingBookObject.total = newVal;
        }
      },
      contractInfo: {
        get: function () {
          return this.$store.state.wholeProcess.changeStandingBookObject.contractInfo;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.changeStandingBookObject.contractInfo = newVal;
        }
      }
    },
    methods: {
      pagingQueryData(res) {
        const vm = this;
        // 设置表格数据
        vm.changeDetailData = res.data.pagedData.dataList;
        // 循环数据放入表格数据对象中
        vm.currentChangePage(vm.changeDetailData, vm.currentPage, vm.pageSize);
        // 设置数据总条数
        vm.total = res.data.pagedData.dataCount;
      },
      currentChangePage(list) {
        this.changeDetailData = list;
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
