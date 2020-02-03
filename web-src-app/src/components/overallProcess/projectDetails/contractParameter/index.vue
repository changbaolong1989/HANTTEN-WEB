<template>
  <div class="contractParameter">
    <!-- 合同台账页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>合同编号</span>
          <span>{{ item.contractNum }}</span>
        </li>
        <li>
          <span>合同分类</span>
          <span>{{ item.treatyType | filterTreatyType }}</span>
        </li>
        <li>
          <span>承包方式</span>
          <span>{{ item.undertakeType | filterUndertakeType }}</span>
        </li>
        <li>
          <span>合同名称</span>
          <span>{{ item.contractName }}</span>
        </li>
        <li>
          <span>合作单位</span>
          <span>{{ item.contractUnit }}</span>
        </li>
        <li>
          <span>计划工期</span>
          <span>{{ item.planDate }}</span>
        </li>
        <li>
          <span>合同类型</span>
          <span>{{ item.contractClass | filterContractClass }}</span>
        </li>
        <li>
          <span>重计量状态</span>
          <span>{{ item.measureSts | filterMeasureSts}}</span>
        </li>
        <li>
          <span>目标成本（元）</span>
          <span>{{ item.targetCost }}</span>
        </li>
        <li>
          <span>除税金额（元）</span>
          <span>{{ item.exciseMoney }}</span>
        </li>
        <li>
          <span>税率（%）</span>
          <span>{{ item.taxRatio }}</span>
        </li>
        <li>
          <span>税金（元）</span>
          <span>{{ item.taxMoney }}</span>
        </li>
        <li>
          <span>含税金额（元）</span>
          <span>{{ item.withTaxMoney }}</span>
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
        <img src="../../../../assets/img/noData.png" width="100%" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
    <more :listarr="listarr" :projectId="projectId"></more>
  </div>
</template>

<script>
import ContractCommon from './contract-common';
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
          link: '合同台账'
        }
      ],
    };
  },
  components: {
    more: moreRedact
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
      vm.requestPost('/wholeProcess/contract/query/searchWholeProcessContract', formData)
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
.contractParameter {
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
