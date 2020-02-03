<template>
  <div class="supervisor" v-infinite-scroll="loadMore" infinite-scroll-disabled="true" infinite-scroll-distance="1">
    <ul class="contentlist" v-for="(item,index) in tableData">
      <li>
        <span>序号</span>
        <span>{{ item.orderNumber }}</span>
      </li>
      <li>
        <span>档案编号</span>
        <span>{{ item.fileNumber }}</span>
      </li>
      <li>
        <span>归档时间</span>
        <span>{{ item.approveTime }}</span>
      </li>
      <li>
        <span>归档人</span>
        <span>{{ item.approveUserName }}</span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      pageNumber: 0,
      pageSize: 10,
      loading: true,
      noMore: false
    };
  },
  props: ['transmittedData'],
  mounted() {},
  methods: {
    searchList() {
      this.requestPost('/task/query/searchArchiveListInfo', {
        // 任务id
        taskId: this.transmittedData.taskId,
        pageNumber: this.pageNumber,
        pageSize: this.pageSize
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            for(var i = 0;i<res.data.pagedData.dataList.length;i++){
              this.tableData.push(res.data.pagedData.dataList[i]);
            }
            this.dataCount = res.data.pagedData.dataCount;
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    loadMore() {
      this.pageNumber += 1;
      if (this.tableData.length == this.dataCount) {
        this.noMore = true;
      } else {
        this.searchList();
        this.noMore = false;
      }
    },
  }
};
</script>
