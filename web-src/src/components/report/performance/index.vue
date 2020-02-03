<template>
  <!-- 绩效 -->
  <div class="report-performance">
    <search ref="search" :searchProp="this.searchProp" searchType="month" @click="searchData()"></search>
    <el-row>
      <el-col :span="24">
        <el-table :data="tableData" border style="width: 100%;">
          <el-table-column align="center" prop="name" label="姓名">
            <template slot-scope="scope">
              <span class="click-entry" @click="click(scope.row)">{{ scope.row.userName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="sumNum" label="扣分合计">
            <template slot-scope="scope">
              <span>{{ scope.row.sumNum>100?100:scope.row.sumNum }}%</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="contractCountNum" label="扣分合同编撰数"></el-table-column>
          <el-table-column align="center" prop="taskCountNum" label="扣分任务数"></el-table-column>
          <el-table-column align="center" prop="requestMenuCountNum" label="扣分请款单数"></el-table-column>
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
    <!-- 对话框-表格 -->
    <dialog-table ref="dialogTable" :formData="userFormData"></dialog-table>
  </div>

</template>

<script>
  /* 会话框 */
  import dialogTable from './dialogTable';
  /* 搜索 */
  import search from '../common/search';
  export default {
    components: {
      'dialog-table': dialogTable,
      search,
      /* 搜索 */
    },
    data() {
      return {
        userFormData: {
          userId:'',
          userName:'',
          searchDate:'',
        }, // 对话框的表格数据
        activeTab: 'newContractQuantity', // 默认选中的tab
        searchProp:'',
        tableData: [],
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      }
    },
    mounted(){
      this.searchData()
    },
    methods: {
      searchData(){
        this.currentPage = 1;
        this.pageSize = 10;
        this.searchProp = this.$refs['search'].form.date;
        this.searchList(this.currentPage, this.pageSize)
      },
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
      searchList(currentPage,pageSize) {
        let vm = this;
        let formData = {
          searchDate: vm.searchProp,
          pageNumber: currentPage,
          pageSize: pageSize
        };
        vm.apiAddress('/reportForm/query/searchUserAchievementsReportForm', formData).then(res => {
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
      click(row) {
        let vm = this;
        console.log("row",row)
        vm.userFormData.searchDate = vm.searchProp;
        vm.userFormData.userId = row.userId;
        vm.userFormData.userName = row.userName;
        vm.$refs['dialogTable'].show = true;
        vm.$refs['dialogTable'].currentPage = 1;
        vm.$refs['dialogTable'].pageSize = 10;
        vm.$refs['dialogTable'].searchList(1, 10);
      }
    }
  }
</script>

<style lang="scss">
  .report-performance {
    .click-entry {
      margin-left: 10px;
      color: #409EFF;
      cursor: pointer;
    }
  }
</style>
