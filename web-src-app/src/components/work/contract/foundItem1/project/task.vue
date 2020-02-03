<template>
  <div>
    <div v-infinite-scroll="loadMore">
      <more :listarr="listarr" :projectId="projectId"></more>
      <ul v-for="(item, i) in tableData" class="contentlist" :key="i">
        <li class="contentlist-li">
          <span>合同编号</span>
          <span>{{ item.contractNum }}</span>
        </li>
        <li class="contentlist-li">
          <span>任务名</span>
          <span>{{ item.taskName }}</span>
        </li>
        <li class="contentlist-li">
          <span>工作类型</span>
          <span>{{ item.jobType }}</span>
        </li>
        <li class="contentlist-li">
          <span>姓名</span>
          <span class="link" @click="showProcessPic(item)">{{ item.userName }}</span>
        </li>
        <li class="contentlist-li">
          <span>人员身份</span>
          <span>{{ item.userType }}</span>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
    <transition name="slide-fade-right" class="createProject">
      <div class="createProject" v-if="dialogImageVisible">
        <i @click.stop="beforeClose" class="iconfont" style="float: left; margin-top: 8%;font-size: 14px; margin-left: 10px;color: #06A1EE;">关闭预览</i>
        <img class="img" :src="processPicUrl" />
      </div>
    </transition>
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
          link: '任务台账'
        }
      ],
      dialogImageVisible: false,
      processPicUrl: ''
    };
  },
  props: ['transmitData'],
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
  components: {
    more: moreRedact
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
      vm.requestPost('/project/query/searchParticipantsInfoList', formData)
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
    },
    // 展示流程图
    showProcessPic(data) {
      let vm = this;
      vm.requestPost('/process/getFlowDiagramUrl', {})
        .then(res => {
          vm.processPicUrl = res.data + '/process/getFlowDiagram?processInstId=' + data.processInstId;
          vm.dialogImageVisible = true;
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    beforeClose(){
      this.dialogImageVisible = false;
    }
  }
};
</script>

<style lang="scss">
/* 进入 */
.slide-fade-right-enter-active {
  transition: all 0.8s ease;
}

.slide-fade-right-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}

.slide-fade-right-enter,
.slide-fade-right-leave-to {
  transform: translateX(100%);
}
.contentlist-li {
  .link {
    color: #06a1ee !important;
  }
}

.createProject {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 30000;
  background-color: #fff;
  width: 100vw;
  height: 100vh;
  overflow: auto;
  .img {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom:0;
    margin: auto;
  }
}
</style>
