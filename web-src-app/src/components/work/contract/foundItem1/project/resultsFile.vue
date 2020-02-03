<template>
  <div v-infinite-scroll="loadMore">
    <more :listarr="listarr" :projectId="projectId"></more>
    <ul v-for="(item, i) in tableData" class="contentlist" :key="i">
      <li class="contentlist-li">
        <span>项目编号</span>
        <span>{{ item.projectNum }}</span>
      </li>
      <li class="contentlist-li">
        <span>项目名称</span>
        <span>{{ item.projectName }}</span>
      </li>
      <li class="contentlist-li">
        <span>合同编号</span>
        <span>{{ item.contractNum }}</span>
      </li>
      <li class="contentlist-li">
        <span>合同名称</span>
        <span>{{ item.contractName }}</span>
      </li>
      <li class="contentlist-li">
        <span>成果文件名称</span>
        <span>{{ item.taskMaterialName }}</span>
      </li>
      <li class="contentlist-li">
        <span>成果文件号</span>
        <span>{{ item.reportNum }}</span>
      </li>
      <li class="contentlist-li">
        <span>成果文件负责人</span>
        <span>{{ item.userName }}</span>
      </li>
      <li class="contentlist-li">
        <span>签发时间</span>
        <span>{{ item.createDate }}</span>
      </li>
      <li class="contentlist-li">
        <span>归档编号</span>
        <span>{{ item.fileNumber }}</span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
import moreRedact from '../../../../more/moreRedact';
export default {
  data() {
    return {
      currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy), // 传输数据(项目信息)
      projectId: this.SStorage.getItem('/app/foundItem').projectId,
      tableData: [], // 列表数据
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      noMore: false, // 显示更多提示
      listarr: [
        {
          name: '导出',
          link: '成果文件台账'
        }
      ]
    };
  },
  props: ['transmitData'],
  components: {
    more: moreRedact
  },
  watch: {
    transmitData: {
      handler() {
        let vm = this;
        vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
        vm.currentChangePage(1, vm.pageSize);
      },
      immediate: true
    }
  },
  methods: {
    loadMore() {
      // 加载更多
      let vm = this;
      vm.currentChangePage(vm.currentPage++, vm.pageSize);
    },
    currentChangePage(currentPage, pageSize) {
      //分页方法
      let vm = this;
      if (currentPage > 1 && vm.tableData.length < (currentPage - 1) * pageSize) {
        vm.currentPage--;
        return (vm.noMore = true);
      }
      if (!vm.currentTransmitData || !vm.currentTransmitData.data || !vm.currentTransmitData.data.id) return;
      let formData = {
        projectId: vm.currentTransmitData.data.id,
        pageNumber: currentPage,
        pageSize: pageSize
      };
      vm.requestPost('/project/query/excSearchResultFileLedgerInfoList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.Toast(res.data.message);
          } else {
            vm.total = res.data.dataCount || 0; // 列表数据总数
            if (1 == currentPage) {
              vm.tableData = res.data.dataList; // 列表数据
            } else if (res.data.dataList) {
              vm.tableData = vm.tableData.concat(res.data.dataList); // 列表数据
            }
            //判断有没有更多数据
            if (res.data.dataList && res.data.dataList.length) {
              vm.noMore = false;
            } else {
              vm.noMore = true;
            }
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    }
  }
};
</script>

<style></style>
