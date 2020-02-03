<template>
  <div class="claimParameter">
    <!-- 索赔台账页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>合同编号</span>
          <span>{{ item.contractNum }}</span>
        </li>
        <li>
          <span>合同分类</span>
          <span>{{ selectGetName(item.treatyType, treatyTypeList) }}</span>
        </li>
        <li>
          <span>承包方式</span>
          <span>{{ item.treatyType == '1' ? selectGetName(item.undertakeType, undertakeTypeList) : '-' }}</span>
        </li>
        <li>
          <span>合同名称</span>
          <span>{{ item.contractName }}</span>
        </li>
        <li>
          <span>合同类型</span>
          <span>{{ selectGetName(item.contractClass, contractClassList) }}</span>
        </li>
        <li>
          <span>合同签订金额(元)</span>
          <span>{{ item.withTaxMoney }}</span>
        </li>
        <li>
          <span>申报金额(元)</span>
          <span>{{ item.declareAmount }}</span>
        </li>
        <li>
          <span>汉腾审核金额(元)</span>
          <span>{{ item.hanttenAmount }}</span>
        </li>
        <li>
          <span>汉腾审核金额(%)</span>
          <span>{{ (item.hanttenAmount / item.withTaxMoney) * 100 }}%</span>
        </li>
        <li>
          <span>已确认(元)</span>
          <span>{{ item.confirmAmount }}</span>
        </li>
        <li>
          <span>预估金额(元)</span>
          <span>{{ item.estimatedAmount }}</span>
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
import DefaultProp from './default'; /* 索赔台账默认元素  */
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
      treatyTypeList: DefaultProp.treatyTypeList,
      undertakeTypeList: DefaultProp.undertakeTypeList,
      contractClassList: DefaultProp.contractClassList,
      listarr: [
        {
          name: '导出',
          link: '索赔台账'
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
        projectId: vm.projectId
      };
      // 获取列表数据
      vm.requestPost('/wholeProcess/claim/query/searchProjectClaimPeriodList', formData)
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
    selectGetName(key, list) {
      return this.$parent.selectGetName(key, list);
    }
  }
};
</script>

<style scoped lang="scss">
.claimParameter {
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
