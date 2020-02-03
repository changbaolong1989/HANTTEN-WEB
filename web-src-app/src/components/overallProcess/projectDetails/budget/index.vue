<template>
  <div class="budget">
    <!-- 概算页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>序号</span>
          <span>{{ item.index }}</span>
        </li>
        <li>
          <span>编制日期</span>
          <span>{{ item.compileDate }}</span>
        </li>
        <li>
          <span>概算名称/版本</span>
          <span>{{ item.estimateName }}</span>
        </li>
        <li>
          <span>建筑面积（m²）</span>
          <span>{{ item.floorSpace }}</span>
        </li>
        <li>
          <span>概算费用（元）</span>
          <span>{{ item.estimatedCost }}</span>
        </li>
        <li>
          <span>概算单方（元/m²）</span>
          <span>{{ item.estimationExParte }}</span>
        </li>
        <li>
          <span>费用范围</span>
          <span>{{ item.costRange }}</span>
        </li>
        <li>
          <span>备注</span>
          <span>{{ item.remark }}</span>
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
      listarr: [
        {
          name: '导出',
          link: '概算'
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
      vm.requestPost('/wholeProcess/estimate/query/searchProjectEstimatePeriodList', formData)
        .then(res => {
          if (res.data.status == 'success') {
            vm.totle = res.data.dataCount;
            console.log('表格数据', res);
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
      this.downloadFile(item.estimateFilePath, item.estimateFileName);
    }
  }
};
</script>

<style scoped lang="scss">
.budget {
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
