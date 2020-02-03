<template>
  <div class="contractInformation__default" v-infinite-scroll="loadMore">
    <search @submit="submit" placeholder="合同编号" @change="submit"></search>
    <!-- 法务意见 -->
    <transition name="slide-fade-right">
      <div class="contractInfo" v-if="showDialogRequestPayout">
        <dialog-opinion
        :data="contractInfo"
        @cancel="cancelDialog('showDialogRequestPayout')"></dialog-opinion>
      </div>
    </transition>

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
        <span>操作</span>
        <span>
          <span class="contentlist-li--clickable" @click="download(item)">合同下载</span>
          <span class="contentlist-li--clickable" @click="opinion(item)">{{item.legalOpinion?'查看':'添加'}}法务意见</span>
        </span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
  import search from '../../../common/vue/search.vue';// 搜索组件
  import dialogOpinion from './dialogOpinion.vue';// 法务意见表单
  export default {
    components: {
      search,
      'dialog-opinion':dialogOpinion
    },
    data() {
      return {
        showDialogRequestPayout:false,
        contractInfo:{},// 合同信息
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        noMore: false, // 显示更多提示
        form: {
          input: ''
        }
      };
    },
    mounted() {
      let vm = this;
      vm.currentChangePage(1, vm.pageSize);
    },
    methods: {
      /**
       * @method 下载
       */
      download(item){
        this.downloadFile(item.contractFilePath, item.contractFileName, item.contractType=='1');
      },
      /**
       * method 取消
       */
      cancelDialog(){
        let vm = this;
        vm.showDialogRequestPayout = false;
      },
      /**
       * @method 法务意见
       */
      opinion(item) {
        let vm = this;
        vm.contractInfo = item;
        vm.showDialogRequestPayout = true;
      },
      /**
       * @method 搜索条件提交查询
       * @param {Object} val 搜索条件表单
       */
      submit(val) {
        let vm = this;
        vm.form = val;
        vm.currentChangePage(1, vm.pageSize);
      },
      loadMore() { // 加载更多
        let vm = this;
        vm.currentChangePage(vm.currentPage++, vm.pageSize);
      },
      currentChangePage(currentPage, pageSize) { //分页方法
        let vm = this;
        if (currentPage > 1 && vm.tableData.length < (currentPage - 1) * pageSize) {
          vm.currentPage--
          return vm.noMore = true;
        }
        let formData = {
          contractNum: vm.form.input,
          pageNumber: currentPage,
          pageSize: pageSize,

        };
        vm.requestPost('/legalAffair/query/searchContractList', formData).then(res => {
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
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
  .contractInformation__default {
    height: 100%;
    width: 100%;
    overflow: auto;

    .contentlist-li--clickable {
      color: #06a1ee !important;

      &+.contentlist-li--clickable{
        margin-left: 10px;
      }
    }
    .contractInfo{
      position: fixed;
      left: 0;
      top: 0;
      z-index: 10001;
    }
  }
</style>
