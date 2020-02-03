<template>
  <div>
    <common-export :exportUrl="this.exportUrl"></common-export>
    <div class="item-tab-chuang">
      <span></span>
    </div>

    <div class="item-tab-bottom">
      <el-table :data="changeStandingBookData" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="@/assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader, index) in dataHeaders" :label="dataHeader.name" :prop="dataHeader.name" :key="index++" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <label v-if="index === 2">{{ changeStandingBookData[scope.$index][dataHeader.id] | filterTreatyType }}</label>
            <label v-else-if="index === 3">{{ changeStandingBookData[scope.$index][dataHeader.id] | filterUndertakeType }}</label>
            <label v-else-if="index === 5">{{ changeStandingBookData[scope.$index][dataHeader.id] | filterContractClass }}</label>
            <label v-else>{{ changeStandingBookData[scope.$index][dataHeader.id] }}</label>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <common-paging :total="this.total" :queryUrl="queryUrl" @pagingQueryData="pagingQueryData" :queryCondition="queryCondition"></common-paging>
  </div>
</template>

<script>
import DefaultScript from './default';
import CommonPaging from '@/components/common/common-paging';
import CommonExport from '@/components/common/common-export'

export default {
  name: 'index',
  data() {
    return {
      dataHeaders: DefaultScript.listHeaders,
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      queryUrl: '/wholeProcess/changeStandingBook/query/searchChangeStandingBook',
      exportUrl: '/wholeProcess/changeStandingBook/export/exportChangeStandingBookData?realFileName=' + "变更台账.xlsx" + "&projectId=" + this.$parent.projectId,
      queryCondition: {
        projectId: this.$parent.projectId
      },
      projectId: this.$parent.projectId
    };
  },
  filters: {
    filterTreatyType(val) {
      return DefaultScript.treatyType[val];
    },
    filterUndertakeType(val) {
      return DefaultScript.undertakeType[val];
    },
    filterContractClass(val) {
      return DefaultScript.getText(val, DefaultScript.contractClassData);
    }
  },
  mounted() {
    const vm = this;
    vm.$axios
      .post(
        vm.queryUrl,
        JSON.stringify({
          pageNumber: vm.currentPage,
          pageSize: vm.pageSize,
          projectId: vm.projectId
        }),
        vm.config
      )
      .then(function(response) {
        if (response.data.status === 'success') {
          vm.changeStandingBookData = response.data.pagedData.dataList;
          //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
          // 循环数据放入表格数据对象中
          vm.currentChangePage(vm.changeStandingBookData, vm.currentPage, vm.pageSize);
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
  components: { CommonPaging , CommonExport},
  computed: {
    changeStandingBookData: {
      get: function() {
        return this.$store.state.wholeProcess.changeStandingBook.data;
      },
      set: function(newVal) {
        this.$store.state.wholeProcess.changeStandingBook.data = newVal;
      }
    },
    total: {
      get: function() {
        return this.$store.state.wholeProcess.changeStandingBook.total;
      },
      set: function(newVal) {
        this.$store.state.wholeProcess.changeStandingBook.total = newVal;
      }
    }
  },
  methods: {
    pagingQueryData(res) {
      const vm = this;
      // 设置表格数据
      vm.changeStandingBookData = res.data.pagedData.dataList;
      // 循环数据放入表格数据对象中
      vm.currentChangePage(vm.changeStandingBookData, vm.currentPage, vm.pageSize);
      // 设置数据总条数
      vm.total = res.data.pagedData.dataCount;
    },
    currentChangePage(list) {
      this.changeStandingBookData = list;
    }
  }
};
</script>

<style scoped></style>
