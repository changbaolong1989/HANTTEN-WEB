<template>
  <!-- 项目详情 -->
  <div class="foundItem__project" v-infinite-scroll="loadMore">
    <p style="padding: 10px;">项目编号:{{currentTransmitData.data?currentTransmitData.data.label:''}}</p>
    <ul v-for="(item, i) in tableData" class="contentlist" :key="i">
      <li class="contentlist-li">
        <span>合同编号</span>
        <span>{{ item.contractNum }}</span>
      </li>
      <li class="contentlist-li">
        <span>合同任务线</span>
        <span>{{ item.departmentName }}</span>
      </li>
      <li class="contentlist-li">
        <span>任务名</span>
        <span>{{ item.taskName }}</span>
      </li>
      <li class="contentlist-li">
        <span>工作类型</span>
        <span>{{ item.jobType }}</span>
      </li>
      <li class="contentlist-li">
        <span>姓名</span>
        <span>{{ item.userName }}</span>
      </li>
      <li class="contentlist-li">
        <span>人员身份</span>
        <span>{{ item.userType }}</span>
      </li>
    </ul>
     <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
 export default{
   props:['transmitData'],
   data(){
     return {
      currentTransmitData:this.tool.deepCopy(this.transmitData, this.tool.deepCopy),// 传输数据(项目信息)
      tableData: [], // 列表数据
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      noMore: false // 显示更多提示
     };
   },
   watch:{
     transmitData:{
       handler(){
         let vm = this;
         vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
         vm.currentChangePage(1, vm.pageSize);
       },
       immediate:true
     }
   },
   methods:{
     loadMore() { // 加载更多
       let vm = this;
       vm.currentChangePage(vm.currentPage++, vm.pageSize);
     },
     currentChangePage(currentPage, pageSize) { //分页方法
       let vm = this;
       if(currentPage>1&& vm.tableData.length<(currentPage-1)*pageSize){
         vm.currentPage--
         return  vm.noMore = true;
       }
       if(!vm.currentTransmitData||!vm.currentTransmitData.data||!vm.currentTransmitData.data.id)return;
       let formData = {
         projectId: vm.currentTransmitData.data.id,
         pageNumber: currentPage,
         pageSize: pageSize
       };
       vm.requestPost('/project/query/searchParticipantsInfoList', formData).then(res => {
         if (res.data.status == 'failure') {
          vm.Toast(res.data.message);
         } else {
           vm.total = res.data.dataCount || 0; // 列表数据总数
           if (1 == currentPage) {
             vm.tableData = res.data.dataList; // 列表数据
           } else if (res.data.dataList) {
             vm.tableData = vm.tableData.concat(res.data.dataList); // 列表数据
           }
           //判断有没有更多数据
           if (res.data.dataList && res.data.dataList.length) {
             vm.noMore = false;
           } else {
             vm.noMore = true;
           }
         }
       }).catch(err => {
         vm.Toast(err);
       });
     }
   }
 }
</script>

<style lang="scss" scoped>
  .foundItem__project {
    height: calc(100vh - 260px);
    overflow: auto;
    p{
      font-size: 28px;
    }
    .contentlist{
      background-color: white;
    }
  }
</style>
