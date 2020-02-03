<template>
  <!-- 人员扣分 -->
  <el-dialog title="人员扣分" :visible="show" width="900px" center @open="handleOpen" @close="handleClose"
    :close-on-click-modal="false">
    <el-row>
      <el-col :span="24">
        <span >姓名:{{formData.userName}}</span>
        <span  style="margin-left: 10px;">时间:{{formData.searchDate}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-table :data="tableData" border style="width: 100%;">
          <el-table-column align="center" prop="businessNum" label="合同编号"></el-table-column>
          <el-table-column align="center" prop="businessName" label="扣分数据"></el-table-column>
          <el-table-column align="center" prop="approveTime" label="审核时间"></el-table-column>
          <el-table-column align="center" prop="approveUserName" label="审核人"></el-table-column>
          <el-table-column align="center" prop="questionName" label="错误描述"></el-table-column>
          <el-table-column align="center" prop="questionNum" label="错误数"></el-table-column>
          <el-table-column align="center" prop="grade" label="扣分值">
            <template slot-scope="scope">
              <span>{{ scope.row.grade*scope.row.questionNum }}%</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <!-- 分页 -->
        <div class="block item-fen" v-show="total > 0" style="margin-top: 10px;">
          <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
            :total="total"></el-pagination>
          <el-button class="linear" type="primary" size="small">确定</el-button>
        </div>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
  export default {
    props: ['formData'],
    data() {
      return {
        currentFormFata: {},
        searchProp: this.formData,
        tableData: [],
        show: false,
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      }
    },
    methods: {
      handleSizeChange(val) {
        // 每页条数切换
        this.pageSize = val;
        this.searchList(this.currentPage, this.pageSize)
      },
      handleCurrentChange(val) {
        //页码切换
        this.currentPage = val;
        this.searchList(this.currentPage, this.pageSize)
      },
      searchList(pageNumber, pageSize) {
        const vm = this;
        let formData = {
          userId: vm.searchProp.userId,
          searchDate: vm.searchProp.searchDate,
          pageNumber: pageNumber,
          pageSize: pageSize
        };
        vm.apiAddress('/reportForm/query/searchHTUserQuestionRecordRelationList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            vm.tableData = dataList
            vm.total = res.data.dataCount
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      },
      handleClose(formName) {
        let vm = this;
        vm.show = false;
      },
      handleOpen() {
        let vm = this;
        // vm.currentFormFata 查询条件对象
        // 查询操作
        console.log('handleOpen', vm.currentFormFata);
      }
    }
  }
</script>


<style>
</style>
