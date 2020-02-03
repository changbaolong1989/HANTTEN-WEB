<template>
  <div>
    <div>
      <el-table :data="tableData" border style="width: 100%;">
        <el-table-column align="center" prop="contractNum" label="合同编号" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" prop="taskName" label="任务名" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="nodeJump(scope.row)">{{ scope.row.taskName }}</el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="jobType" label="工作类型" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" prop="userName" label="姓名" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="showProcessPic(scope.row)">{{ scope.row.userName }}</el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="userType" label="人员身份" :show-overflow-tooltip="true"></el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="block item-fen" v-show="total > 0">
      <el-pagination
        background
        class="fen-left"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        layout="total, prev, pager,next,sizes,jumper"
        :total="total"
      ></el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>
    <el-dialog title="查看流程图" :visible.sync="dialogImageVisible" width="80%"><el-image style="width: 100%; height: 100%" :src="processPicUrl"></el-image></el-dialog>
  </div>
</template>
<script>
export default {
  props: ['transmittedData'],
  mounted() {
    this.searchList(1, 10);
  },
  data() {
    return {
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      projectId: this.SStorage.getItem('/page/work/contract/FoundItem').projectId,
      tableData: [], //当前页数据
      contractNumber: '',
      // 表单项间距
      gutter: 40,
      showImg: false,
      imgSrc: '',
      imgAlt: '',
      dialogImageVisible: false,
      processPicUrl: ''
    };
  },
  methods: {
    nodeJump(obj) {
      obj.businessId = obj.taskId;
      this.SStorage.setItem('/page/work/contract/FoundItem',obj);
      this.bus.$emit('asideRefresh');
    },
    // 展示流程图
    showProcessPic(data) {
      let vm = this;
      vm.apiAddress('/process/getFlowDiagramUrl', {})
        .then(res => {
          vm.dialogImageVisible = true;
          vm.processPicUrl = res.data + '/process/getFlowDiagram?processInstId=' + data.processInstId;
        })
        .catch(err => {
          vm.stateDialog('error', err);
        });
    },
    handleSizeChange(val) {
      // 每页条数切换
      this.pageSize = val;
      this.searchList(this.currentPage, this.pageSize);
    },
    handleCurrentChange(val) {
      //页码切换
      this.currentPage = val;
      this.searchList(this.currentPage, this.pageSize);
    },
    searchList(pageNumber, pageSize) {
      const vm = this;
      let formData = {
        projectId: vm.projectId,
        pageNumber: pageNumber,
        pageSize: pageSize
      };
      vm.apiAddress('/project/query/searchParticipantsInfoList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message);
          } else {
            let dataList = res.data.dataList;
            vm.tableData = dataList;
            vm.total = res.data.dataCount;
          }
        })
        .catch(err => {
          vm.stateDialog('error', err);
        });
    }
  }
};
</script>
<style lang="scss">
.item {
  /* 标签页 */
  .el-tabs--card {
    & > .el-tabs__header {
      border-bottom: none;
      margin: 0;

      .el-tabs__nav-scroll {
        padding: 20px 20px 0;
        // box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.4) inset;

        .el-tabs__nav {
          border: 1px;

          .el-tabs__item {
            border-radius: 10px 10px 0 0;
            border: none;
            box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
            background-color: white;

            &.is-active {
              background-color: #0aa3ef;
              color: white;
            }
          }
        }
      }
    }
  }
}
</style>
<style>
@import url('../../../common/css/other.css');

/* 文件列表*/
.task {
}
</style>
