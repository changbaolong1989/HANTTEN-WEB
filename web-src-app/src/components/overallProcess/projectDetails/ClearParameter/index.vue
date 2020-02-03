<template>
  <div class="ClearParameter">
    <!-- 清标台账页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>招标名称</span>
          <span>{{ item.tenderName }}</span>
        </li>
        <li>
          <span>合同类型</span>
          <span>{{selectGetName(item.contractType,contractTypeList)}}</span>
        </li>
        <li>
          <span>接收资料时间</span>
          <span>{{item.acceptFileTime}}</span>
        </li>
        <li>
          <span>发出报告时间</span>
          <span>{{ item.sendReportTime }}</span>
        </li>
        <li>
          <span>操作</span>
          <span class="blue" @click="download(item)">下载</span>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
    <div v-if="tabdata.length==0" class="NoData">
      <div class="noDataCont">
        <img src="../../../../assets/img/noData.png" width="100%" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
    <more :listarr="listarr" :projectId="projectId"></more>
  </div>
</template>

<script>
  import DefaultProp from './default' /* 清标管理默认元素  */
  import moreRedact from '../../../more/moreRedact';/* 导出 */
export default {
  data() {
    return {
      total: 0, //分页总数据
      pageNumber: 0, //当前页码
      pageSize: 10, //每页显示多少条数据
      tabdata: [], //当前页数据
      noMore: false, // 是否还有更多
      projectId: this.$parent.projectId,
      contractTypeList:DefaultProp.contractTypeList,
      listarr: [
        {
          name: '导出',
          link: '清标台账'
        }
      ],
    };
  },
  components: {
    more: moreRedact
  },
  methods: {
    init() {
      const vm = this;
      let formData = {
        // 页码
        pageNumber: vm.pageNumber,
        // 每页总条数
        pageSize: vm.pageSize,
        // 项目编号
        projectId: vm.projectId,
      };
      // 获取列表数据
      vm.requestPost('/wholeProcess/tender/query/searchProjectTenderPeriodList', formData)
        .then(res => {
          if (res.data.status == 'success') {
            vm.totle = res.data.dataCount;
            // 设置表格数据
            let dataList = res.data.dataList;
            // 循环数据放入表格数据对象中
            dataList.forEach(function(i, k) {
              i.index = k + 1;
              vm.tabdata.push(i);
            });
          }
        })
        .catch(err => {
          this.Toast(res.data.message);
        });
    },
    loadMore() {
      this.pageNumber += 1;
      console.log('totle', this.totle);
      console.log('tabdata', this.tabdata);
      if (this.tabdata.length == this.totle) {
        this.noMore = true;
      } else {
        this.init();
        this.noMore = false;
      }
    },
    download(item) {
      this.downloadFile(item.reportPath, item.reportName);
    },
    selectGetName(key,list) {
      return this.$parent.selectGetName(key,list);
    },
  }
};
</script>

<style scoped lang="scss">
.ClearParameter {
  background-color: #eff3f5;
  .contentlist {
    width: 100%;
    background-color: #fff !important;
    overflow: hidden;
    .blue {
      color: #06a1ee;
    }
  }
}
</style>
