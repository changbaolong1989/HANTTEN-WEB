<template>
  <!-- 请章记录 -->
  <div class="work__table-please-record-chapter" v-infinite-scroll="loadMore">
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
        <span>审批状态</span>
        <span>
          {{ item.verifyState|formatState}}
          <span class="contentlist-li--clickable" v-if="'0'==item.verifyState" @click="approval(item,'2')">拒绝原因</span>
        </span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
    <mt-popup v-model="popupVisible" popup-transition="popup-fade">
      <mt-field label="用户名" placeholder="请输入用户名" v-model="refuse2Reason.approveUserName" :readonly="readonly"></mt-field>
      <mt-field label="自我介绍" placeholder="自我介绍" type="textarea" rows="4" v-model="refuse2Reason.refusalReason" :readonly="readonly"></mt-field>
      <div class="button-group">
        <mt-button type="default" @click="popupHide">取消</mt-button>
        <mt-button type="primary" v-if="false">确定</mt-button>
      </div>
    </mt-popup>

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
        refuse2Reason: { // 拒绝原因form对象
          refusalReason: '',
          approveUserName: ''
        },
        popupVisible: false, // 拒绝原因对话框
        readonly: true, // 拒绝原因是否只读
        noMore: false, // 显示更多提示
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10 //每页显示多少条数据
      };
    },
    filters: {
      formatState: function(input) {
        var result = "";
        if (input == "0") {
          result = "未通过";
        }
        if (input == "1") {
          result = "已通过";
        }
        if (input == "2") {
          result = "审批中";
        }
        return result;
      },
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
      resetData(){
        let vm = this;
        vm.refuse2Reason = { // 拒绝原因form对象
          refusalReason: '',
          approveUserName: ''
        };
      },
      popupHide() { // 关闭对话框
        let vm = this;
        MessageBox.confirm('确定关闭对话框?').then(action => {
          vm.popupVisible = false;
          vm.resetData();
        });
      },
      currentChangePage(currentPage, pageSize) {
        let vm = this;
        if(currentPage>1&& vm.tableData.length<(currentPage-1)*pageSize)return  vm.noMore = true;
        vm.requestPost(
            '/workbench/sealApply/query/querySealApplyRecord',
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
          })
          .catch(function(error) {
            Toast({
              message: error,
              position: 'middle',
              duration: 5000
            });
          });

      },
      loadMore() { // 加载更多
        let vm = this;
        vm.currentChangePage(vm.currentPage++, vm.pageSize);
      },
      approval(row, state) {
        const vm = this;
        // 填写拒绝原因
        if ('2' === state) {
          vm.popupVisible = true;
          vm.readonly = true;
          vm.refuse2Reason.refusalReason = row.rejectReason;
          vm.refuse2Reason.approveUserName = row.approveUserName;
        }
      }
    }
  };
</script>
<style lang="scss">
  @import url(../common/css/work-common.css);
  .work__table-please-record-chapter {
     
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
