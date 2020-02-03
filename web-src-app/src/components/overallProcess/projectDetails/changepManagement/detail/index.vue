<template>
  <div class="standing">
    <!-- 变更明细页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>专业</span>
          <span>{{ item.professionalId }}</span>
        </li>
        <li>
          <span>变更名称</span>
          <span>{{ item.alterName}}</span>
        </li>
        <li>
          <span>合同编号</span>
          <span>{{ item.contractNum}}</span>
        </li>
        <li>
          <span>合同名称</span>
          <span>{{ item.contractName }}</span>
        </li>
        <li>
          <span>内容摘要</span>
          <span>{{ item.contentSummary }}</span>
        </li>
        <li>
          <span>洽商日期</span>
          <span>{{ item.talkDate | filterDate }}</span>
        </li>
        <li>
          <span>接收日期</span>
          <span>{{ item.receiveDate | filterDate }}</span>
        </li>
        <li>
          <span>状态</span>
          <span>{{ item.sts | filterSts }}</span>
        </li>
        <li>
          <span>申报金额（元）</span>
          <span>{{ item.declareAmount }}</span>
        </li>
        <li>
          <span>监理审核金额（元）</span>
          <span>{{ item.auditAmount }}</span>
        </li>
        <li>
          <span>汉腾审核金额（元）</span>
          <span>{{ item.hanttenAmount }}</span>
        </li>
        <li>
          <span>业主审批金额（元）</span>
          <span>{{ item.ownerAmount }}</span>
        </li>
        <li>
          <span>核增/减金额（元）</span>
          <span>{{ item.addSubAmount }}</span>
        </li>
        <li>
          <span>批准文号</span>
          <span>{{ item.symbol }}</span>
        </li>
        <li>
          <span>变更洽商费用分析</span>
          <span>{{ item.alterAnalyse }}</span>
        </li>
        <li>
          <span>备注</span>
          <span>{{ item.remark }}</span>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
    <div v-if="tabdata.length==0" class="NoData">
      <div class="noDataCont">
        <img src="../../../../../assets/img/noData.png" width="100%" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
    <more :listarr="listarr" :projectId="projectId"></more>
  </div>
</template>

<script>
import DefaultScript from './default'; /* 变更明细默认元素  */
  import moreRedact from '../../../../more/moreRedact';/* 导出 */
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
          link: '变更明细'
        }
      ],
    };
  },
  components: {
    more: moreRedact
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
    }
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
        projectId: vm.projectId
      };
      // 获取列表数据
      vm.requestPost('/wholeProcess/changeDetail/query/searchChangeDetail', formData)
        .then(res => {
          if (res.data.status == 'success') {
            vm.totle = res.data.pagedData.dataCount;
            // 设置表格数据
            let dataList = res.data.pagedData.dataList;
            // 循环数据放入表格数据对象中
            dataList.forEach(function(i, k) {
              i.index = k + 1;
              vm.tabdata.push(i);
            });
          }
        })
        .catch(err => {
          this.Toast(err);
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
    }
  }
};
</script>

<style scoped lang="scss">
.standing {
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
