<template>
  <div class="dialogTable">
    <div class="top">
      <p>姓名：{{ obj.item.userName }}</p>
      <p>时间：{{ obj.searchDate }}</p>
    </div>
    <div v-infinite-scroll="loadMore" infinite-scroll-disabled="loading" infinite-scroll-distance="10">
      <ul v-for="(item, index) in contentList" :key="index" class="contentlist">
        <li>
          <span>合同编号</span>
          <span>{{ item.businessNum }}</span>
        </li>
        <li>
          <span>扣分数据</span>
          <span>{{ item.businessName }}</span>
        </li>
        <li>
          <span>审核时间</span>
          <span>{{ item.approveTime }}</span>
        </li>
        <li>
          <span>审核人</span>
          <span>{{ item.approveUserId }}</span>
        </li>
        <li>
          <span>错误数</span>
          <span>{{ item.questionNum }}</span>
        </li>
        <li>
          <span>扣分值</span>
          <span>{{ item.grade }}</span>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      obj: '',
      loading: true,
      noMore: false,
      pageNumber:1,
      pageSize:10,
      contentList: [
      ]
    };
  },
  created() {
    this.obj = this.SStorage.getItem('/app/dialogTable');
    this.init();
  },
  methods: {
    init() {
      this.patientInfo = {
        pageNumber:this.pageNumber,
        pageSize: this.pageSize,
        searchDate:this.obj.searchDate,
        userId:this.obj.item.userId
      };
      /* 页面初始化请求数据 */
      this.requestPost('/reportForm/query/searchHTUserQuestionRecordRelationList', this.patientInfo)
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
    loadMore() {
      if (this.contentList.length === this.totle) {
        this.loading = true;
        this.noMore = true;
      } else {
        this.pageNumber += 1;
        this.init();
        this.noMore = false;
      }
    }
  }
};
</script>

<style scoped lang="scss">
.dialogTable {
  width: 100%;
  background-color: #f1f5f8;

  .top {
    width: 100%;
    padding: 20px 0;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.3);
    p {
      font-size: 28px;
      color: #666;
      padding: 10px 30px;
    }
  }
  .contentlist {
    background-color: #fff;
  }
}
</style>
