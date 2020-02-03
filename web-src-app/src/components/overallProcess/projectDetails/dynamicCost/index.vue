<template>
  <div class="standing">
    <!-- 动态成本管理页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>序号</span>
          <span>{{ item.serialNumber | filterNull }}</span>
        </li>
        <li>
          <span>合同分类</span>
          <span>{{ item.treatyType | filterNull | filterTreatyType }}</span>
        </li>
        <li>
          <span>承包方式</span>
          <span>{{ item.undertakeType | filterNull | filterUndertakeType }}</span>
        </li>
        <li>
          <span>合同编号</span>
          <span>{{ item.contractNum | filterNull }}</span>
        </li>
        <li>
          <span>合同名称</span>
          <span>{{ item.contractName | filterNull }}</span>
        </li>
        <li>
          <span>合作单位</span>
          <span>{{ item.contractUnit | filterNull }}</span>
        </li>
        <li>
          <span>计划工期</span>
          <span>{{ item.planDate | filterNull }}</span>
        </li>
        <li>
          <span>目标成本（元）</span>
          <span>{{ item.targetCost | filterNull }}</span>
        </li>
        <li>
          <span>已签订合同总价（元）</span>
          <span>{{ item.withTaxMoney | filterNull }}</span>
        </li>
        <li>
          <span>未定标金额总计（元）</span>
          <span>{{ item.uncalibratedPrice | filterNull }}</span>
        </li>
        <li>
          <span>合同金额总计（元）</span>
          <span>{{ item.withTaxMoney | filterNull }}</span>
        </li>
        <li>
          <span>暂定量/项调整</span>
          <span>{{ item.temporaryAmount | filterNull }}</span>
        </li>
        <li>
          <span>变更估值</span>
          <span>{{ item.alterValue | filterNull }}</span>
        </li>
        <li>
          <span>图差数量调整</span>
          <span>{{ item.drawingAdjust | filterNull }}</span>
        </li>
        <li>
          <span>暂估单价调整</span>
          <span>{{ item.signelAdjust | filterNull }}</span>
        </li>
        <li>
          <span>材料市场调整</span>
          <span>{{ item.materialAdjust | filterNull }}</span>
        </li>
        <li>
          <span>人工市场调整</span>
          <span>{{ item.peopleAdjust | filterNull }}</span>
        </li>
        <li>
          <span>其他</span>
          <span>{{ item.otherThing | filterNull }}</span>
        </li>
        <li>
          <span>税金调整</span>
          <span>{{ item.taxAdjust | filterNull }}</span>
        </li>
        <li>
          <span>预计结算估值</span>
          <span>{{ item.predictCloseValue | filterNull }}</span>
        </li>
        <li>
          <span>备注</span>
          <span>{{ item.remark | filterNull }}</span>
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
import DefaultScript from './default'; /* 动态成本管理默认元素  */
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
          link: '动态成本'
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
    filterNull(val) {
      return val == null ? '-' : val;
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
      vm.requestPost('/wholeProcess/cost/query/searchCost', formData)
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
