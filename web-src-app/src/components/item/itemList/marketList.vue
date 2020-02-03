<template>
  <!-- 市场项目列表-->
  <div class="marketList">
    <div class="search">
      <span class="iconfont">&#xe627;</span>
      <input type="search" class="input" id="itemNum" v-model="tenderTaskName" @search="init('search')" @change="init('search')" placeholder="请输入搜索投标任务名" />
    </div>
    <more :listarr="listarr" v-if="!!userPowerInfo.tenderTaskAdd"></more>
    <div v-infinite-scroll="loadMarket" infinite-scroll-distance="1">
      <ul class="tabList">
        <li v-for="(item, index) in tabList">
          <div class="top">
            <span>{{ item.sortNum }}</span>
            <span>{{ item.tenderTaskSts == 0 ? '未开标' : item.tenderTaskSts == 1 ? '未中标' : '已中标' }}</span>
          </div>
          <div class="middle" @click="routerLink(item)">
            <span class="state">{{ item.tenderTaskName }}</span>
          </div>
          <div class="bottom">
            <span>{{ item.userName }}</span>
            <span>{{ item.startDate }}</span>
          </div>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
  </div>
</template>

<script>
import moreRedact from '../../more/moreRedact';
export default {
  data() {
    return {
      tabList: [],
      tenderTaskName: '' /* 搜索条件 */,
      pageNumber: 0 /* 当前页码 */,
      pageSize: 10 /* 当前页码 */,
      totle: '' /* 数据总条数 */,
      noMore: false,
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      listarr: [
        {
          name: '新增',
          link: '/app/createProject'
        }
      ]
    };
  },
  components: {
    more: moreRedact
  },
  created() {},
  methods: {
    compare(property) {
      return function(a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value2 - value1;
      };
    },
    init(type) {
      if (type == 'search') {
        this.patientInfo = {
          pageNumber: 1,
          pageSize: this.pageSize,
          tenderTaskName: this.tenderTaskName
        };
        this.tabList = [];
      } else {
        this.patientInfo = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          tenderTaskName: this.tenderTaskName
        };
      }
      /* 页面初始化请求数据 */
      this.requestPost('/tenderTask/query/searchTenderTaskPeriodList', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.totle = res.data.dataCount;
            var arr = res.data.dataList;
            const vm = this;
            arr.forEach(function(i) {
              vm.tabList.push(i);
            });
            vm.tabList.sort(this.compare('sortNum'))
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    loadMarket() {
      this.pageNumber += 1;
      if (this.tabList.length === this.totle) {
        this.noMore = true;
      } else {
        this.init();
        this.noMore = false;
      }
    },
    routerLink(item) {
      console.log(item);
      this.SStorage.setItem('/app/bazaarItemIndex', item);
      this.$router.push({ path: '/app/bazaarItemIndex' });
    }
  }
};
</script>

<style lang="scss" scoped>
.marketList {
  width: 100%;
}
</style>
