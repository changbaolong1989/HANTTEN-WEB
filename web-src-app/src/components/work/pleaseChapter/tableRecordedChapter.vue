<template>
  <!-- 用章记录 -->
  <div class="work__table-recorded-chapter" v-infinite-scroll="loadMore">
    <ul v-for="(item, i) in tableData" class="contentlist" :key="i">
      <li class="contentlist-li">
        <span>创建时间</span>
        <span>{{ item.applyTime }}</span>
      </li>
      <li class="contentlist-li">
        <span>用章原因</span>
        <span>{{ item.applyReason }}</span>
      </li>
      <li class="contentlist-li">
        <span>用章类型</span>
        <span>{{ item.sealTypeText }}</span>
      </li>
      <li class="contentlist-li">
        <span>是否骑缝</span>
        <span>{{ item.isPagingSeal|formatIsPagingSeal }}</span>
      </li>
      <li class="contentlist-li">
        <span>申请人</span>
        <span>{{ item.applyUserName }}</span>
      </li>
      <li class="contentlist-li">
        <span>审批时间</span>
        <span>{{ item.approveTime }}</span>
      </li>
      <li class="contentlist-li">
        <span>审批人</span>
        <span>{{ item.approveUserName }}</span>
      </li>
      <li class="contentlist-li">
        <span>操作</span>
        <span>
          <span class="contentlist-li--clickable" @click="download(item)">下载</span>
          <span class="contentlist-li--clickable" @click="singSeal(item)" v-show="item.isSignatured!=='1'">标记盖章</span>
          <span v-show="item.isSignatured==='1'">已盖章</span>
        </span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
  import {
    Toast,
    Popup,
    Field,
    MessageBox
  } from 'mint-ui';
  export default {
    data() {
      return {
        noMore: false, // 显示更多提示
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        isLoading: false // 正在加载,防止重复加载
      };
    },
    filters: {
      formatIsPagingSeal(input) {
        let result = "-";
        if (input == "0") {
          result = "否";
        }
        if (input == "1") {
          result = "是";
        }
        return result;
      }
    },
    methods: {
      /**
       * @method 标记盖章
       * @param {Object} row 表格行数据
       */
      singSeal(row){
        const vm = this;
        MessageBox.confirm('是否提交?').then(action => {
          vm.getTagStamp({
            pageNumber: vm.currentPage,
            /* 页码   1*/
            pageSize: vm.pageSize,
            recordId: row.recordId
          }).then(function() {
            vm.currentPage = 1;
            vm.currentChangePage(vm.currentPage, vm.pageSize);
          });
        }, cancel => {});
      },
      currentChangePage(currentPage, pageSize) {
        let vm = this;
        if (currentPage > 1 && vm.total <= vm.tableData.length) {
          let vm = this;
          vm.currentPage--
          return vm.noMore = true;
        }
        // 正在加载...
        vm.isLoading = true;
        vm.getApproveList(currentPage, pageSize).then(function(response) {
          vm.isLoading = false;
          if (response.data.status == 'success') {
            vm.total = response.data.pagedData.dataCount || 0; /* 总条数 */
            let list = response.data.pagedData.dataList || [];
            if (1 == currentPage) {
              vm.tableData = list; // 列表数据
            } else {
              vm.tableData = vm.tableData.concat(list); // 列表数据
            }
            //判断有没有更多数据
            if (list && list.length) {
              vm.noMore = false;
            } else {
              vm.noMore = true;
            }
          } else {
            Toast({
              message: response.data.message,
              position: 'middle',
              duration: 5000
            });
          }
        });
      },
      loadMore() { // 加载更多
        let vm = this;
        if (!vm.isLoading) vm.currentChangePage(vm.currentPage++, vm.pageSize);
      },
      /**
       * @method  获取标记盖章信息
       * @param {Object} row 表格行数据
       */
      getTagStamp(data) {
        let vm = this;
        let promise = new Promise(function(resolve, reject) {
          vm.requestPost('/workbench/sealApply/modify/signSeal', JSON.stringify(data), vm.config).then(function(
            response) {
            if (response.data.status === 'success') {
              resolve();
            } else {
              Toast({
                message: response.data.message,
                position: 'middle',
                duration: 5000
              });
            }
          }).catch(function(error) {
            Toast({
              message: error,
              position: 'middle',
              duration: 5000
            });
          });
        });
        return promise;
      },
      /**
       * @method 获取批准列表
       * @param {Object} currentPage 当前页
       * @param {Object} pageSize 页容量
       */
      getApproveList(currentPage, pageSize) {
        let vm = this;
        let promise = new Promise(function(resolve, reject) {
          vm.requestPost(
              '/workbench/sealApply/query/queryApprovedRecord',
              JSON.stringify({
                pageNumber: currentPage,
                /* 页码*/
                pageSize: pageSize /* 每页总条数 */
              }), {
                headers: {
                  'Content-Type': 'application/json',
                  Accept: 'application/json'
                }
              }
            )
            .then(function(response) {
              resolve(response);
            })
            .catch(function(error) {
              Toast({
                message: error,
                position: 'middle',
                duration: 5000
              });
            });
        });
        return promise;
      },
      download(item) {
        let elemIF = document.createElement('iframe');
        elemIF.src = '/commonModule/downloadFile?filePathName=' + item.attachmentPath + '&realFileName=' + item.attachmentName;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      }
    }
  };
</script>
<style lang="scss">
  @import url(../common/css/work-common.css);
  .work__table-recorded-chapter {
    * {
      box-sizing: border-box;
    }

    .mint-cell {
      .mint-cell-wrapper {
        flex-direction: column;
        align-items: stretch;

        .mint-cell-title,
        .mint-cell-value {
          min-height: 60px;
          line-height: 60px;
          padding: 5px;

        }

        .mint-cell-title {
          width: 200px;
        }

        .mint-cell-value {
          textarea {
            resize: none !important;
            line-height: 60px;
          }

          input {
            line-height: 60px;
          }
        }
      }
    }
  }
</style>
<style scoped lang="scss">
  .mint-popup {
    width: 80%;
    margin: auto;
    border-radius: 10px;
    padding: 10px;
  }

  .mint-button {
    height: 50px;
    line-height: 50px;
    padding: 0 30px;
  }

  .button-group {
    padding: 10px;
    text-align: center;
  }
</style>
