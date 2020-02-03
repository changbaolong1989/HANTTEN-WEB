<template>
  <div class="index">
    <search @changeMonth="Month"></search>
    <more :listarr="listarr" :searchProp="date"></more>
    <div v-infinite-scroll="loadMore"  infinite-scroll-disabled="loading" infinite-scroll-distance="10">
      <ul v-for="(item, index) in contentList" :key="index" class="contentlist">
        <li class="router">
          <span>姓名</span>
          <div>
            <span @click="routrLink(item)">{{ item.userName }}</span>
          </div>
        </li>
        <li>
          <span>扣分合计</span>
          <span>{{ item.sumNum }}</span>
        </li>
        <li>
          <span>扣分合同编撰数</span>
          <span>{{ item.contractCountNum }}</span>
        </li>
        <li>
          <span>扣分任务数</span>
          <span>{{ item.taskCountNum }}</span>
        </li>
        <li>
          <span>扣分请款单数</span>
          <span>{{ item.requestMenuCountNum }}</span>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
  </div>
</template>

<script>
import search from './search'; /* 搜索功能 */
import moreRedact from '../../more/moreRedact';/* 导出 */
export default {
  data() {
    return {
      pageNumber:1,
      pageSize: 10,
      totle: 0,
      noMore:false,
      date: this.formatting(new Date()).slice(0, 7),
      contentList: [],
      loading:true,
      listarr: [
        {
          name: '导出',
          link: '数据报表'
        }
      ],
    };
  },
  components: {
    search: search,
    more: moreRedact
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.patientInfo = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
        searchDate: this.date
      };
      /* 页面初始化请求数据 */
      this.requestPost('/reportForm/query/searchUserAchievementsReportForm', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            var arr = res.data.dataList;
            const vm = this;
            arr.forEach(function(i) {
              vm.contentList.push(i);
            });
            this.totle = res.data.dataCount;
            this.loading = false;
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    Month(val) {
      this.date = val;
      this.init();
    },
    loadMore() {
      if (this.contentList.length === this.totle) {
        this.loading = true;
        this.noMore = true;
      } else {
        this.pageNumber += 1;
        this.init();
        this.noMore = false;
      }
    },
    routrLink(item) {
      var parameter = {
        item:item,
        searchDate:this.date
      }
      this.SStorage.setItem('/app/dialogTable', parameter);
      this.$router.push({ path: '/app/dialogTable' });
    }
  }
};
</script>

<style lang="scss" scoped>
.index {
  width: 100%;
  .contentlist {
    background-color: #fff;
    .router {
      border-top: none;
      border-bottom: 2px solid #e5e5e5;
      padding-bottom: 20px;
      margin-top: 20px;
    }
  }
}
</style>
