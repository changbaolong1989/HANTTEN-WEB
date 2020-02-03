<template>
  <div class="standing">
    <!-- 结算台账页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>合同编号</span>
          <span>{{ item.contractNum }}</span>
        </li>
        <li>
          <span>合同分类</span>
          <span>{{ item.treatyType | filterTreatyType}}</span>
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
          <span>{{ item.contractClass | filterContractClass}}</span>
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
          <span>结算金额（元）</span>
          <span>{{ item.statementAmount }}</span>
        </li>
        <li>
          <span>图纸/方案（元）</span>
          <span>{{ item.drawingScheme }}</span>
        </li>
        <li>
          <span>变更洽商（元）</span>
          <span>{{ item.alterDiscuss }}</span>
        </li>
        <li>
          <span>签证（元）</span>
          <span>{{ item.visa }}</span>
        </li>
        <li>
          <span>材料设备调差（元）</span>
          <span>{{ item.adjustPrice }}</span>
        </li>
        <li>
          <span>物价波动调差（元）</span>
          <span>{{ item.surgePrice }}</span>
        </li>
        <li>
          <span>总包服务费（元）</span>
          <span>{{ item.serviceFee }}</span>
        </li>
        <li>
          <span>税金调差（元）</span>
          <span>{{ item.taxPrice }}</span>
        </li>
        <li>
          <span>其他（元）</span>
          <span>{{ item.otherThing }}</span>
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
import DefaultScript from './default'; /* 结算台账默认元素  */
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
          link: '结算台账'
        }
      ],
    };
  },
  components: {
    more: moreRedact
  },
  filters: {
    filterTreatyType(val) {
      return DefaultScript.treatyType[val];
    },
    filterUndertakeType(val) {
      return DefaultScript.undertakeType[val];
    },
    filterContractClass(val) {
      return DefaultScript.getText(val, DefaultScript.contractClassSelectData);
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
      vm.requestPost('/wholeProcess/statement/query/searchWholeProcessStatement', formData)
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
