<template>
  <div class="contractPlanning">
    <!-- 合约规划页面 -->
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="contentlist" v-for="(item, index) in tabdata" :key="index">
        <li>
          <span>合约编号</span>
          <span>{{ item.treatyNum }}</span>
        </li>
        <li>
          <span>合约分类</span>
          <span>{{ item.treatyType=='1'?'工程类合约':item.treatyType=='2'?'采购类合约':item.treatyType=='3'?'服务类合约':item.treatyType=='4'?'土地类合约':item.treatyType=='5'?'其他类合约':'' }}</span>
        </li>
        <li>
          <span>承包方式</span>
          <span>{{item.undertakeType=='1'?'施工总承包':item.undertakeType=='2'?'发包人发包专业合同':item.undertakeType=='3'?'发包人自行发包专业':'' }}</span>
        </li>
        <li>
          <span>合约名称</span>
          <span>{{ item.treatyName }}</span>
        </li>
        <li>
          <span>合约内容及范围</span>
          <span>{{ item.treatyContent }}</span>
        </li>
        <li>
          <span>目标成本（元）</span>
          <span>{{ item.targetCost }}</span>
        </li>
        <li>
          <span>合同类型</span>
          <span>{{ item.contractType=='1'?'单价合同':item.contractType=='2'?'总价合同':item.contractType=='3'?'其他':'' }}</span>
        </li>
        <li>
          <span>合同确定方式</span>
          <span>{{ item.contractWay=='1'?'总承包':item.contractWay=='2'?'指定供货':item.contractWay=='3'?'指定分包':item.contractWay=='4'?'甲供':item.contractWay=='5'?'直接发包':item.contractWay=='6'?'三方协议':'' }}</span>
        </li>
        <li>
          <span>采购方式</span>
          <span>{{item.purchaserWay=='1'?'公开招标':item.purchaserWay=='2'?'内部组织招标':item.purchaserWay=='3'?'联合招标':item.purchaserWay=='4'?'直接委托':item.purchaserWay=='5'?'竞争性谈判':''}}</span>
        </li>
        <li>
          <span>备注</span>
          <span>{{item.remark}}</span>
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
          link: '合约规划'
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
      vm.requestPost('/wholeProcess/treaty/query/searchProjectTreatyPeriodList', formData)
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
  }
};
</script>

<style scoped lang="scss">
.contractPlanning {
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
