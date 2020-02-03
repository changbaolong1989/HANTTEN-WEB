<template>
  <!-- 用章审核 -->
  <div class="work__table-chapter-review" v-infinite-scroll="loadMore">
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
          <span class="contentlist-li--clickable" @click="approval(item,'1')" v-if="0==item.isHistory">通过</span>
          <span class="contentlist-li--clickable--refuse" @click="approval(item,'0')" v-if="0==item.isHistory">拒绝</span>
          <span class="contentlist-li--clickable" @click="approval(item,'2')" v-if="1==item.isHistory&&1==item.isViewReason">拒绝原因</span>
        </span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>

    <div v-if="dialogVisible" class="float-form">
      <div class="form-content">
        <mt-field v-if="!isRead" label="拒绝人" placeholder="请输入拒绝人" v-model="form.approveUserName" :disabled="!isRead"></mt-field>
        <mt-field placeholder="请输入拒绝原因" label="拒绝原因" v-validate="'required'" data-vv-name="拒绝原因" type="textarea" rows="3"
          v-model="form.refusalReason" name="refusalReason" :disabled="!isRead"></mt-field>
      </div>
      <div class="button-group">
        <mt-button type="default" @click="popupHide">取 消</mt-button>
        <mt-button type="primary" class="linear" @click="noApproval" v-if="isRead">确 定</mt-button>
      </div>
    </div>

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
        form: { // 拒绝原因form对象
          refusalReason: '',
          approveUserName: ''
        },
        dialogVisible: false, // 拒绝原因对话框
        isRead: true, // 拒绝原因是否只读
        noMore: false, // 显示更多提示
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        isLoading: false // 正在加载,防止重复加载
      };
    },
    watch: {
      dialogVisible: {
        handler() {
          let vm = this;
          if (vm.dialogVisible) {
            // 屏蔽点击
          } else {
            //恢复默认
          }
        },
        immediate: true
      }
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
      /**
       *不批准(拒绝)
       */
      noApproval() {
        let vm = this;
        vm.$validator
          .validateAll()
          .then(result => {
            if (result) {
              // 校验成功
              vm.getVerifyInfo({
                pageNumber: vm.currentPage,
                pageSize: vm.pageSize,
                rejectReason: vm.form.refusalReason,
                departmentId: sessionStorage.getItem("work__departmentId--default"),
                delegateTaskId: vm.form.delegateTaskId
              }).then(function() {
                vm.currentPage = 1;
                vm.dialogVisible = false;
                vm.currentChangePage(vm.currentPage, vm.pageSize);
              });
            } else {
              vm.Toast({
                message: vm.errors.all()[0],
                position: 'middle',
                className: 'toast',
                duration: 2000
              });
            }

          })
      },
      resetData() {
        let vm = this;
        // 拒绝原因form对象
        vm.form = {
          refusalReason: '',
          approveUserName: ''
        };
      },
      popupHide() { // 关闭对话框
        let vm = this;
        MessageBox.confirm('确定关闭对话框?').then(action => {
          vm.dialogVisible = false;
          vm.resetData();
        });
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
        console.log('loadMore');
        if (!vm.isLoading) vm.currentChangePage(vm.currentPage++, vm.pageSize);
      },
      /**
       * @method 批准
       * @param {Object} row 表格行数据
       * @param {Object} state 状态
       */
      approval(row, state) {
        const vm = this;
        // 填写拒绝原因
        if ('0' === state) {
          vm.dialogVisible = true;
          vm.isRead = true;
          vm.form.delegateTaskId = row.delegateTaskId;
          return;
        } else if ('2' === state) {
          vm.dialogVisible = true;
          vm.isRead = false;
          vm.form.refusalReason = row.rejectReason;
          vm.form.approveUserName = row.approveUserName;
          return;
        }
        MessageBox.confirm('是否提交?').then(action => {
          vm.getVerifyInfo({
            pageNumber: vm.currentPage,
            /* 页码   1*/
            pageSize: vm.pageSize,
            delegateTaskId: row.delegateTaskId,
            departmentId: sessionStorage.getItem("work__departmentId--default"),
            recordId: row.recordId
          }).then(function() {
            vm.currentPage = 1;
            vm.currentChangePage(vm.currentPage, vm.pageSize);
          });
        }, cancel => {});
      },
      /**
       * @method  获取验证信息
       * @param {Object} row 表格行数据
       */
      getVerifyInfo(data) {
        let vm = this;
        let promise = new Promise(function(resolve, reject) {
          vm.requestPost('/workbench/sealApply/modify/verify', JSON.stringify(data), vm.config).then(function(
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
              '/workbench/sealApply/query/queryApproveList',
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

  .work__table-chapter-review {
    * {
      box-sizing: border-box;
    }

    /* 表单 */
    .float-form {
      box-sizing: border-box;
      position: fixed;
      top: 0;
      left: 0;
      z-index: 999;
      background-color: white;
      width: 100%;
      height: 100%;
      padding: 30px 3% 30px;

      /* 表单行*/
      .mint-cell {
        border-bottom: 2px solid #e5e5e5;

        &:last-child {
          border-bottom: none;
        }

        /* 行内容的主体容器 */
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

            /* label */
            .mint-cell-text {
              font-size: 28px;
              color: #666;
            }
          }
          .mint-cell-value {
            textarea {
              resize: none !important;
            }
            input,
            textarea {
              font-size: 28px;
              color: #333;
            }
          }
        }
      }

      .button-group {
        position: absolute;
        left: 0;
        bottom: 0;
        height: 95px;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
        display: flex;
        justify-content: space-around;
        margin-top: 50px;

        .mint-button {
          width: 40%;
          height: 70px;
          line-height: 70px;
          margin: 12px 0;
          font-size: 28px;
          background-color: #bfcad5;
          color: white;
        }
      }
    }
  }
</style>
<style scoped lang="scss">
  * {
    box-sizing: border-box;
  }
</style>
