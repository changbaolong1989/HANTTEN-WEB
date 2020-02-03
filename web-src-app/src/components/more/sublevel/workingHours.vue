<template>
  <!-- 工时列表-->
  <div class="workingHours">
    <div v-if="!showRemarksForm" v-infinite-scroll="loadMore" style="height: 100%;">
    <form class="search">
      <span class="iconfont">&#xe627;</span>
      <input type="text" class="input" v-model="currentForm.taskName" placeholder="请输入任务名称" @input="search" @change="search"/>
    </form>
    <ul v-for="(item, index) in tableData" class="contentlist" :key="index">
      <li>
        <span>姓名</span>
        <span>{{ item.userName }}</span>
      </li>
      <li>
        <span>任务名称</span>
        <span>{{ item.taskName }}</span>
      </li>
      <li>
        <span>任务完成时间</span>
        <span>{{ item.endDate }}</span>
      </li>
      <li>
        <span>预计占用工时</span>
        <span>{{ item.hour }}</span>
      </li>
      <li>
        <span>已占用工时</span>
        <span>{{ item.needHour }}</span>
      </li>
      <li class="router" v-if="userPowerInfo.hourListRemark">
        <span>操作</span>
        <div>
          <span @click="goToModifyRemarks(item)">备注</span>
        </div>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
    <form class="remarks projectForm" v-if="showRemarksForm" ref="form">
      <div class="form-item">
        <label for="">项目名称</label>
        <textarea
        rows="10" cols="30"
        placeholder="请输入项目名称"
        v-model="currentForm2Remarks.remarks"
        v-validate="'required'" data-vv-name="项目名称">
        </textarea>
      </div>
      <div class="commonFooter">
        <mt-button @click="reset">取消</mt-button>
        <mt-button class="linear" @click="submit">确定</mt-button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [], // 列表数据
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      noMore: false, // 显示更多提示
      currentForm:{// 当前表单
        taskName:''// 任务名称
      },
      currentForm2Remarks:{// 当前表单(备注)
        remarks:''// 备注
      },
      currentItem:'',// 点击备注的当前数据对象
      showRemarksForm:false,// 显示备注组件
      userPowerInfo:this.SStorage.getItem("userPowerInfo")// 登录用户信息
    };
  },
  mounted() {
    let vm = this;
    vm.$nextTick(function() {
      // 本地访问
      vm.search();
    })
  },
  methods: {
    /**
     * @method 重置
     */
    reset() {
      let vm = this;
      vm.currentForm2Remarks = {
        remarks: ''
      };
    },
    submit() {
      let vm = this;
      vm.$validator.validateAll().then(result => {
          if (result) {
            // 查询我的工时列表请求
            vm.requestPost('/task/modify/updateRemarksInfo', {
              // 关联id
              relationId: vm.currentItem.relationId,
              // 备注
              remarks: vm.currentForm2Remarks.remarks
            }).then(res => {
                 vm.Toast(res.data.message);
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (res.data.status == 'success'){
                  vm.reset();
                  vm.centerDialogVisible = false;
                }
              })
              .catch(err => {
                vm.Toast(err);
              });
          } else {
             vm.Toast(vm.errors.all()[0]);
          }
        })
    },
    /**
     * @method 回显备注
     */
    goToModifyRemarks(item){
      let vm = this;
      vm.currentItem = item;
      vm.showRemarksForm = true;
    },
    /**
     * @method 查询数据列表
     */
    search(){
      let vm = this;
      vm.currentChangePage(1, vm.pageSize);
    },
    /**
     * @method 分页方法
     * @param {Number} currentPage 当前页数
     * @param {Number} pageSize 页容量
     */
    currentChangePage(currentPage, pageSize) {
      let vm = this;
      if(currentPage>1&& vm.tableData.length<(currentPage - 1)*pageSize){
        vm.currentPage--
        return  vm.noMore = true;
      }
      let formData = {
        pageNumber: currentPage,// 页码
        pageSize: pageSize,// 页容量
        taskName: vm.currentForm.taskName // 任务名称
      };
      vm.requestPost('/task/query/searchHourListInfo', formData).then(res => {
         vm.Toast(res.data.message);
        if (res.data.status != 'failure') {
          vm.total = res.data.dataCount || 0; // 列表数据总数
          if (1 == currentPage) {
            vm.tableData = res.data.pagedData.dataList; // 列表数据
          } else if (res.data.pagedData.dataList) {
            vm.tableData = vm.tableData.concat(res.data.pagedData.dataList); // 列表数据
          }
          //判断有没有更多数据
          if (res.data.pagedData.dataList && res.data.pagedData.dataList.length) {
            vm.noMore = false;
          } else {
            vm.noMore = true;
          }
        }
      }).catch(err => {
         vm.Toast(err);
      });
    },
    /**
     * @method 加载更多
     */
    loadMore() {
      let vm = this;
      vm.currentChangePage(vm.currentPage++, vm.pageSize);
    }
  }
};
</script>

<style lang="scss" scoped>
.workingHours {
  width: 100%;
  .search {
    width: 100%;
    background-color: #fff;
    padding: 25px 0 15px;
    position: relative;
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    span {
      position: absolute;
      top: 45px;
      left: 44px;
      font-size: 28px;
      color: #b1b1b1;
    }
    .input {
      display: block;
      width: 86%;
      margin: 0 auto;
      border: 2px solid #c9c9c9;
      border-radius: 10px;
      background-color: #f8f9fb;
      height: 60px;
      line-height: 60px;
      padding-left: 65px;
    }
  }

}
</style>
