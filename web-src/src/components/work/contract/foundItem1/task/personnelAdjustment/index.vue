<template>
  <div class="personnelAdjustment--task">
    <el-form class="form" ref="form" :model="form">
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24" style="text-align: right;" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
            <el-button class="btn-please-chapter" type="primary" @click="addPersonnel">添加人员
            </el-button>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-table :data="tableData" border style="width: 100%" :cell-style="rowClass">
              <el-table-column prop="userName" label="人员姓名" align="center" :resizable="false">
              </el-table-column>
              <el-table-column prop="workContent" label="工作内容" align="center" :resizable="false">
              </el-table-column>
              <el-table-column prop="startDate" label="入场日期" width="100" align="center" :resizable="false">
              </el-table-column>
              <el-table-column prop="endDate" label="离场日期" width="100" align="center" :resizable="false">
              </el-table-column>
              <el-table-column prop="operation" label="操作" width="100" align="center" :resizable="false">
                <template slot-scope="scope" >
                  <span @click="leave(scope.row)" v-if='!scope.row.endDate && transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"' class="leave">人员离场{{ scope.row.name}}</span>
                  <span v-else class="leave2">人员离场{{ scope.row.name}}</span>
                </template>
              </el-table-column>
            </el-table>
            <div class="block item-fen" v-show="total > 0" style="margin-top: 10px;">
              <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
                :total="total"></el-pagination>
                <el-button class="linear" type="primary" size="small">确定</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <!-- 人员入场 -->
    <dialog-start ref="dialogStart" @submit="submitDialogStart"></dialog-start>
    <!-- 人员离场 -->
    <dialog-end ref="dialogEnd" :form="formDialogEnd" @submit="submitDialogEnd"></dialog-end>
  </div>
</template>

<script>
  import dialogStart from './dialogStart';/* 人员入场 */
  import dialogEnd from './dialogEnd';/* 人员离场 */
  export default {
    components: {
      'dialog-start':dialogStart,/* 人员入场 */
      'dialog-end':dialogEnd/* 人员离场 */
    },
    props: ['transmittedData'],
    data() {
      return {
        gutter: 40, // 栅格间距
        form: { // 表单数据
          workContent: '' // 工作内容
        },
        formDialogEnd:{// 人员离场表单对象
          userName: '', // 人员名称
          startDate: '', // 入场时间
          endDate: '' // 离场时间
        },
        tableData: [],
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        relationId:''// 任务阶段id
      };
    },
    mounted(){
      this.searchList();
    },
    methods: {
      searchList(){
        this.apiAddress("/personnelAdjustment/queryAll", {
          "relationId":this.transmittedData.relationId,
          "pageSize":this.pageSize,
          "pageNumber":this.currentPage
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            this.tableData = res.data.pagedData.dataList;
            this.total = res.data.pagedData.dataCount;
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      addPersonnel() { // 添加人员(入场)
        let vm = this;
        vm.$refs['dialogStart'].show = true;
      },

      leave(row) { // 人员离场
        let vm = this;
        console.log("tableData",vm.tableData)
        vm.formDialogEnd.userName = row.userName;
        vm.formDialogEnd.startDate = row.startDate;
        vm.formDialogEnd.relationId = row.relationId;
        // 人员离职判断,已经离职就不需要再弹出对话框了
        // if('王小虎1' == row.staffName || '王小虎2' == row.staffName)return;
        vm.$refs['dialogEnd'].show = true;
      },

      submitDialogStart(data){
        const vm = this;
        vm.apiAddress("/personnelAdjustment/add", {
          "relationId":this.transmittedData.relationId,
          "userName":data.userName,
          "startDate":data.startDate,
          "workContent":data.workContent,
        }).then(response => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status == "success") {
            // 隐藏新增弹窗
            vm.$refs['dialogStart'].show = false;
            // 查询列表数据
            vm.searchList();
            vm.stateDialog('success', response.data.message)
          } else {
            vm.stateDialog('error', response.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        });
      },
       submitDialogEnd(data){
        const vm = this;
        console.log('离场:' + data.endDate);
        vm.apiAddress("/personnelAdjustment/modifyLeaveTime", {
          "relationId":data.relationId,
          "endDate":data.endDate,
        }).then(response => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (response.data.status == "success") {
            // 隐藏新增弹窗
            vm.$refs['dialogEnd'].show = false;
            // 查询列表数据
            vm.searchList();
            vm.stateDialog('success', response.data.message)
          } else {
            vm.stateDialog('error', response.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        });
      },
      rowClass({
        row,
        column,
        rowIndex,
        columnIndex
      }) { //状态检测
        if (columnIndex === 4) {
          if ('王小虎1' == row.staffName || '王小虎2' == row.staffName) {
            return 'color:#C0C4CC';
          }
          return 'color:#06A1EE';
        }
      },
      handleSizeChange(val) { // 每页条数切换
        this.pageSize = val;
        this.searchList()
      },
      handleCurrentChange(val) { //页码切换
        this.currentPage = val;
        this.searchList()
      },
    }
  }
</script>

<style>
  @import url('../../../../common/css/other.css');
</style>
<style lang="scss">
  .personnelAdjustment--task {
    .el-textarea__inner {
      cursor: context-menu !important;
    }
    .form-block {
      padding: 20px !important;

      .el-row .el-col {
        padding-left: 0px !important;
      }
    }
  }
  .m-work-contract-foundItem .item-serial .el-form-item label {
      width: 62px !important;
      font-size: 12px;
      color: #424242;
      padding: 0;
  }


</style>
<style lang="scss" scoped>
  .personnelAdjustment--task {
    .leave{
      cursor: pointer;
    }
    .leave2{
      color: #969696;
    }
  }
</style>
