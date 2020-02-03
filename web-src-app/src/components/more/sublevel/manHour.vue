<template>
  <!-- 我的工时-->
  <div class="manHour">
    <div class="headline">
      <span>当前日期</span>
      <span>{{ newDate }}</span>
    </div>
   <form class="projectForm" v-infinite-scroll="loadMore">
    <ul v-for="(item, index) in tableData" class="contentlist" :key="index">
      <li>
        <span>任务名称</span>
        <span>{{ item.taskName }}</span>
      </li>
      <li>
        <span>完成时间</span>
        <span>{{ item.endDate }}</span>
      </li>
      <li>
        <span>预计占用工时</span>
        <span>{{ item.needHour }}</span>
      </li>
      <li>
        <span>已分配工时合计</span>
        <span>{{ item.actualHourSum }}</span>
      </li>
      <li class="input">
          <div class="form-item">
            <label for="">今日工时分配</label>
            <div class="row">
              <input type="text" placeholder="请输入今日工时" v-model="item.actualHour" />
              <span>小时</span>
            </div>
          </div>
      </li>
      <li>
          <div class="form-item">
            <label for="">自定义工时</label>
            <div class="row">
              <input type="text" placeholder="请输入自定义工时" v-model="item.customizeHour" />
              <span>小时</span>
            </div>
          </div>
      </li>
      <li>
          <div class="form-item">
            <label for="">原因</label>
            <div class="row">
              <input type="text" placeholder="请输入原因" v-model="item.hourReason" />
            </div>
          </div>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
    </form>
    <div class="commonFooter">
      <mt-button @click="reset">取消</mt-button>
      <mt-button class="linear" @click="submitForm()">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newDate:"", // 当前日期
      tableData: [],// 列表数据
      total: 0, //分页总数据
      currentPage: 1, //当前页码 
      pageSize: 10, //每页显示多少条数据
      noMore: false, // 显示更多提示
    };
  },
  mounted() {
    let vm = this;
    vm.$nextTick(function() {
      // 本地访问
      vm.currentChangePage(1, vm.pageSize);
    })
  },
  methods:{
    reset(){
       history.go(0);
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
      let formData = {};
      vm.requestPost('/task/query/searchMyHourListInfo', formData).then(res => {
         vm.Toast(res.data.message);
        if (res.data.status != 'failure') {
          vm.total = res.data.dataCount || 0; // 列表数据总数
          vm.newDate = res.data.currentDate;// 当前日期
          let tableData = res.data.dataList;
          if (1 == currentPage) {
            vm.tableData = tableData; // 列表数据
          } else if (tableData) {
            vm.tableData = vm.tool.deepCopy(vm.tableData.concat(tableData), vm.tool.deepCopy);; // 列表数据
          }
          //判断有没有更多数据
          if (tableData && tableData.length) {
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
    },
    /**
     * @methods 提交我的工时数据信息
     */
    submitForm() {
      let vm = this;
      vm.MessageBox.confirm('确认提交吗?').then(action => {
        // 查询我的工时列表请求
        vm.requestPost('/task/modify/updateMyHourListInfo', {
          "taskList":vm.tableData,
          "nowDate":vm.newDate
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          vm.Toast(res.data.message);
        }).catch(err => {
          vm.Toast(err);
        })
      });
    },
  }
};
</script>

<style lang="scss" scoped>
.manHour {
  width: 100%;
  padding-bottom: 110px;
  .headline {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    background-color: #fff;
    height: 76px;
    line-height: 76px;
    display: flex;
    padding: 0px 30px;
    justify-content: space-between;
    font-size: 28px;
    color: #333333;
  }
  .projectForm {
    width: 100%;
    .form-item {
      border: none;
      margin-top:0;
      .row{
        width: 100%;
        display: flex;
        justify-content: space-between;
        input{
          width: 90%;
        }
        span{
          line-height: 85px;
        }
      }
    }
  }
  .contentlist {
    background-color: #f1f5f8;
    width: 94%;
    margin: 20px auto;
    border-radius: 10px;
    overflow: hidden;
    li {
      margin: 30px;
      display: flex;
      justify-content: space-between;
      span {
        font-size: 28px;
        &:first-child {
          color: #808080;
        }
        &:last-child {
          color: #666666;
        }
      }
      &.input {
        border-top: 2px solid #e5e5e5;
        padding-top: 20px;
      }
    }
  }

}
</style>
