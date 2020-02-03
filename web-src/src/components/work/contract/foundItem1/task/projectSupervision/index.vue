<template>

  <div class="task">

    <div class="img-failure" v-if="showImg">
      <img v-bind:src="imgSrc" :alt="imgAlt" />
      <p style="text-align: center;">{{ imgAlt }}</p>
    </div>

    <div class="title form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">工作步骤说明:</p>
        </el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{currentTransmitData.periodDesc}}</p>
        </el-col>
      </el-row>
    </div>

    <!-- 项目监理部列表 -->
    <div class="form-block" style="padding-bottom:56px;">
      <div>
          <el-table :data="tableData" border style="width: 100%;">
            <el-table-column align="center" prop="orderNumber" label="序号"></el-table-column>
            <el-table-column align="center" prop="fileNumber" label="档案编号"></el-table-column>
            <el-table-column align="center" prop="approveTime" label="归档时间"></el-table-column>
            <el-table-column align="center" prop="approveUserName" label="归档人"></el-table-column>
          </el-table>
      </div>
      <!-- 分页 -->
      <div class="block item-fen" v-show="total > 0">
        <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
         :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
         :total="total"></el-pagination>
        <el-button class="linear" type="primary" size="small">确定</el-button>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    props: ['currentTransmitData'],
    data() {
      return {
        showImg: false,
        // 表单项间距
        gutter: 40,
        //当前页数据
        tableData: [],
        //分页总数据
        total: 0,
        //当前页码
        currentPage: 1,
        //每页显示多少条数据
        pageSize: 10,
        //条目列表
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
      }
    },
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          const vm = this;
          vm.currentTransmitData = newval;
          vm.taskId = vm.currentTransmitData.taskId;// 任务id
          vm.searchArchiveListInfo(vm.taskId, 1, 10);// 项目监理归档信息列表
        },
        immeditate: true
      },
    },
    created() {
      let vm = this;
      vm.taskId = vm.currentTransmitData.taskId;// 任务id
      vm.searchArchiveListInfo(vm.taskId, 1, 10);// 项目监理归档信息列表
    },
    methods: {
      // ============================================================================== 项目监理归档信息列表 start==============================================================================
      // 项目监理归档信息列表
      searchArchiveListInfo(taskId,pageNumber, pageSize) {
        let vm = this;
        vm.apiAddress('/task/query/searchArchiveListInfo', {
          // 任务id
          "taskId": taskId,
          "pageNumber": pageNumber,
          "pageSize": pageSize
        }).then(res => {
          if (res.data.status == 'success') {
            // 列表数据
            vm.tableData = res.data.pagedData.dataList;
            // 列表总条数
            vm.total = res.data.pagedData.dataCount;
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        })
      },
      handleSizeChange(val) {
        this.pageSize = val; // 每页条数切换
        this.searchArchiveListInfo(this.taskId, this.currentPage, this.pageSize)
      },
      handleCurrentChange(val) {
        this.currentPage = val; //页码切换
        this.searchArchiveListInfo(this.taskId, this.currentPage, this.pageSize)
      },
      // ============================================================================== 项目监理归档信息列表 end==============================================================================
    }
  }
</script>
<style>
  @import url('../../../../common/css/other.css');
</style>
