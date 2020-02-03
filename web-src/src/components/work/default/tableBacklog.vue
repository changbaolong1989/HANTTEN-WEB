<template>
  <!-- 待办表格 -->
  <el-container class="tableBacklog--work-default">
    <form-search @click="search" :btns="btns" :searchFlagObj="{projectNameFlag:dataVOParamKeyMap.projectName,isBacklog:true,currentDeptId:departmentId}"></form-search>
    <div class="item-tab-bottom">
      <el-table :data="tableData" ref="multipleTable" border style="width: 100%" class="item-tab" :cell-style="rowClass" v-if="tableShow" @selection-change="selectionChange">
        <template slot="empty" class="tabdata-empty">
          <img src="../../../assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column type="selection" width="50" v-if="true" :selectable="selectable"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" width="50" class-name="table-column" prop="sortNum" label="序号" v-if="dataVOParamKeyMap.sortNum"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" min-width="50"class-name="table-column" prop="projectNum" label="项目编号" v-if="dataVOParamKeyMap.projectNum">
          <template slot-scope="scope">
            {{ scope.row.projectNum == null||scope.row.projectNum == ''?"-":scope.row.projectNum }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" min-width="60" class-name="table-column" prop="projectName" label="项目名称" v-if="dataVOParamKeyMap.projectName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="contractNum" label="合同编号" v-if="dataVOParamKeyMap.contractNum">
          <template slot-scope="scope">
            {{ scope.row.contractNum == null||scope.row.contractNum == ''?"-":scope.row.contractNum }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" min-width="60" class-name="table-column" prop="contractName" label="合同名称" v-if="dataVOParamKeyMap.contractName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="counselTypePeriod" label="业务类型" v-if="dataVOParamKeyMap.counselTypePeriod">
          <template slot-scope="scope">
            {{  scope.row.counselTypePeriod == null|| scope.row.counselTypePeriod ==""? '-' : selectGetName(scope.row.counselTypePeriod,radios) }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="counselType" label="咨询类别" v-if="dataVOParamKeyMap.counselType">
          <template slot-scope="scope">
            {{  scope.row.counselType == null|| scope.row.counselType ==""? '-' : scope.row.counselType }}
          </template>
        </el-table-column>

        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="cooperateCompany" label="合作公司" v-if="dataVOParamKeyMap.cooperateCompany">
          <template slot-scope="scope">
            {{ scope.row.cooperateCompany == null|| scope.row.cooperateCompany =="" ?"-":scope.row.cooperateCompany }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="seniorManagerName" label="所属高管" v-if="dataVOParamKeyMap.seniorManagerName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="tenderTaskName" label="任务名称" v-if="dataVOParamKeyMap.tenderTaskName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="departmentName" label="所属部门" v-if="dataVOParamKeyMap.departmentName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="startDate" label="开标时间" v-if="dataVOParamKeyMap.startDate"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="userName" label="提审人" v-if="dataVOParamKeyMap.userName"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="compilationUserName" label="流转人" v-if="dataVOParamKeyMap.createUserName">
          <template slot-scope="scope">
            <span>{{ !!scope.row.compilationUserName?scope.row.compilationUserName:"历史数据，暂无流转人 " }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" class-name="table-column" prop="createDate" label="任务数据创建时间" v-if="false"></el-table-column>
        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="handleDate" label="流转时间" v-if="dataVOParamKeyMap.handleDate"></el-table-column>
        <el-table-column :show-overflow-tooltip="false"  align="center" class-name="table-column line-breaking" prop="pending" label="任务" v-if="dataVOParamKeyMap.pending">
          <template slot-scope="scope" >
            <el-button width="50" @click="toContract(scope.row)" type="text" size="small">{{ scope.row.delegateTaskKey }}</el-button>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="currentStateName" label="状态" v-if="dataVOParamKeyMap.currentStateName">
          <template slot-scope="scope">
            <el-button @click="toContract(scope.row)" type="text" size="small">{{ scope.row.currentStateName }}</el-button>
          </template>
        </el-table-column>
        <!--操作-->
        <!-- <el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="" v-if="false" label="操作"> -->
            <!--<el-button type="text" size="small" @click="approval(scope.row)" v-if="1==scope.row.isPass">通过</el-button>-->
          <!-- <template slot-scope="scope" v-if="true" >
            <el-button type="text" size="small" @click="showProcessPic(scope.row)">流程图</el-button>
          </template> -->
        <!-- </el-table-column> -->
      </el-table>
    </div>
    <div style="margin-top: 30px;">
      <el-button :disabled="btnFlag" v-if="true" class="linear" type="primary" @click="batchPass()">
        批量通过
      </el-button>
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
    </div>
    <el-dialog title="查看流程图" :visible.sync="dialogImageVisible" width="80%">
      <el-image style="width: 100%; height: 100%" :src="processPicUrl"></el-image>
    </el-dialog>

  </el-container>

</template>

<script>
/* 搜索表单 */
import formSearch from './formSearch';

/* 市场部 */
// userName:主要负责人名称
// createDate:数据创建时间
// isUnTouch:状态待审核
// sortNum:排序
// startDate:开始时间
// tenderTaskId:投标任务标识
// tenderTaskName:投标任务名称
export default {
  components: {
    'form-search': formSearch
  },
  created() {
    // 本地访问

    this.currentChangePage(this.currentPage, this.pageSize);
    this.searchDepartmentList();
  },
  data() {
    const types=[{
        display: '造价咨询-编制',
        val: '0-0'
      }, {
        display: '造价咨询-审核',
        val: '0-1'
      }, {
        display: '造价咨询-全过程跟踪审计',
        val: '0-2'
      }, {
        display: '造价咨询-全过程工程造价咨询',
        val: '0-3'
      },{
        display: '招标代理-施工',
        val: '1-0'
      },{
        display: '招标代理-监理',
        val: '1-1'
      }, {
        display: '招标代理-设计',
        val: '1-2'
      },{
        display: '招标代理-勘察',
        val: '1-3'
      },{
        display: '招标代理-服务类',
        val: '1-4'
      },{
        display: '招标代理-采购',
        val: '1-5'
      },{
        display: '招标代理-其他',
        val: '1-6'
      }]
    return {
      tableShow: true, // 显示表格数据
      btns: [], // 搜索按钮数组
      departmentId: '', //当前活动的部门标识
      tenderTaskName: '',
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      tableData: [], //表格数据
      dataVOParamKeyMap: {}, // 表格显示列
      projectNum: '', // 项目编号
      projectName: '', // 项目名称
      userName: '' ,// 用户名
      checkList:[],
      btnFlag:true,
      dialogImageVisible: false,
      processPicUrl: '',
      radios: types,
    };
  },
  methods: {
    search(data) {
      // 表单查询
      let vm = this;
      //设置当前活动部门
      vm.departmentId = data.id;
      vm.tenderTaskName = data.tenderTaskName;
      vm.projectNum = data.projectNum;
      vm.projectName = data.projectName;
      vm.userName = data.userName;
      vm.currentChangePage(1, this.pageSize);
    },
    selectable(row, index){/* 多选框禁用 */
        if(row.isPass=="0"){
          return false;
        }else{
          this.btnFlag = false;
          return true;
        }
    },
    selectGetName(key,list){
      for (var i = 0; i < list.length; i++) {
        if (list[i].val==key) {
          return list[i].display
        }
      }
    },
    toContract(rowInfo) {
      // 跳转到详情页面
      // 如果是市场部，则跳转到投标任务详情页
      if ('593df92d-fb21-442a-b279-a8b821bbc0d4' == rowInfo.departmentId) {
        this.SStorage.setItem('/page/work/contract/tenderTask', {
          rowInfo: rowInfo,
          stateFlag: 'tenderTask',
          titleName: rowInfo.tenderTaskName
        });
        this.$router.push('/page/work/contract/tenderTask');
        // 否则，跳转到项目详情页面
      } else {
        this.SStorage.setItem('/page/work/contract/FoundItem', rowInfo);
        this.$router.push('/page/work/contract/FoundItem');
      }
    },
    handleSizeChange(val) {
      // 每页条数切换
      this.pageSize = val;
      this.currentChangePage(this.currentPage, this.pageSize);
    },
    handleCurrentChange(val) {
      //页码切换
      this.currentPage = val;
      this.currentChangePage(this.currentPage, this.pageSize);
    },
    currentChangePage(currentPage, pageSize) {
      //分页方法
      let vm = this;
      let formData = {
        tenderTaskName: vm.tenderTaskName,
        departmentId: vm.departmentId,
        pageNumber: currentPage,
        pageSize: pageSize,
        projectNum: vm.projectNum,
        projectName: vm.projectName,
        userName: vm.userName
      };
      vm.tableShow = false;
      vm.apiAddress('/work/query/searchPendingList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.dataVOParamKeyMap = res.data.dataVOParamKeyMap; // 表格显示列
            vm.tableData = res.data.dataList;
            vm.total = res.data.dataCount;
            this.btnFlag = true;
            // if (res.data.dataCount) {
            //   vm.$parent.label = '待办('+vm.total+')'
            //   vm.$emit('changeLabel',{
            //     name:'backlogLabel',
            //     count:vm.total
            //   });
            // }
            vm.tableShow = true;
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },

    searchDepartmentList() {
      //分页方法
      let vm = this;
      let formData = {
        colseLoadingFlag:true
      };
      vm.apiAddress('/work/query/searchAllDepartmentList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            if (!vm.departmentId) {
              vm.departmentId = res.data.dataList[0].departmentId; // 默认显示部门标识
            }
            let dataList = res.data.dataList;
            let dataCount = res.data.dataCount;
            vm.$parent.backlogLabel = dataCount
            for (var i = dataList.length - 1; i >= 0; i--) {
              // 设置部门id
              dataList[i].uniqueId = dataList[i].departmentId;
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentId/g, 'id'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentName/g, 'name'));
              if (dataList.length==1) {
                dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentBtnFlag/g, 'flag'));
              }
            }
            vm.btns = dataList; // 显示部门按钮
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    rowClass({ row, column, rowIndex, columnIndex }) {
      //状态检测
      if (columnIndex === 10) {
        return 'color:#06A1EE';
      }
    },
    // 展示流程图
    showProcessPic(data){
      let vm = this;
      vm.dialogImageVisible = true;
      vm.processPicUrl = 'http://localhost:18866/process/getFlowDiagram?processInstId='+data.processInstId;
      console.log("rowInfo",data)
    },
    // 审核通过
    approval(data){
      let vm = this;
      let paramObj = {
        isPass:'1',
        delegateTaskId:data.delegateTaskId,
        submitNode :data.submitNode,
        updateDate :data.updateDate
      };

      var url = '';
      // 合同
      if('0' == data.sourceType){// 合同

        paramObj.contractId = data.contractId;

        url = '/contract/audit/auditingHTContract';

      }else if('1-1' == data.sourceType){// 成果文件

        paramObj.relationId = data.businessId;

        url = '/task/add/auditingTaskOutcomeDocumentPeriod';

      }else if('1-2' == data.sourceType){// A3阶段

        paramObj.relationId = data.businessId;

        url = '/task/add/auditingTaskA3Period';

      }else if('1-3' == data.sourceType){// 重新编辑

        paramObj.relationId = data.businessId;

        // 当重新编辑的流程的节点不是‘签发’时，执行改接口
        if('任务重新编辑-待签发' != data.delegateTaskKey){

          url = '/task/add/auditingOutcomeDocumentPeriodReeditApplication';

        }else{

          url = '/task/add/reeditApplicationIssue';
        }

      }else if('2' == data.sourceType){// 投标任务

        paramObj.tenderTaskId = data.tenderTaskId;

        url = '/tenderTask/insert/auditingHTTenderTask';

      }else if('3' == data.sourceType){// 请款单

        paramObj.requestMenuId = data.businessId;

        url = '/requestMenu/modify/auditingQuestionMenu';

      }

      this.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {
          this.apiAddress(url, paramObj).then(res => {
            if (res.data.status == 'failure') {
              // 错误提示
              this.stateDialog('error', res.data.message);
            } else {
              // 正确提示
              this.stateDialog('success', res.data.message);
              // 刷新页面
              vm.currentChangePage(1, this.pageSize);
            }
          }).catch(err => {
            this.stateDialog('error', err);
          });
      }).catch(() => {

      });
    },
    //批量通过
    batchPass(){
      let paramVOList=this.checkList
      const vm = this;
      if (null==paramVOList||paramVOList.length==0) {
        vm.$message({
          message: "请至少选取一条数据",
          type: 'error',
          center: true
        });
        return
      }
      vm.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
        vm.apiAddress('/work/update/batchPassCheckBusinessList', paramVOList  )
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            this.checkList = [];
            // 查询部门列表
            this.searchDepartmentList();
            // 刷新页面
            vm.currentPage = 1;
            vm.currentChangePage(1, this.pageSize);
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      });
    },
    selectionChange(val){
      this.checkList = val;
    }
  }
};
</script>
<style lang="scss">
.tableBacklog--work-default {
  /* 总容器 */
  &.el-container {
    padding-bottom: 25px;
    display: flex;
    flex-direction: column;
  }
  .table-column{
    div.el-tooltip{
      color: #424242;
      word-break:break-all;
    }
  }

  .line-breaking  {
    .el-button{
      white-space: normal;
      line-height: 14px!important;
    }
  }
}
.el-table .cell.el-tooltip {
    white-space: normal;
    min-width: 50px;
}
.el-table .cell, .el-table th div {
    padding-right: 10px;
    overflow: hidden;
    text-overflow: clip;
}
.el-table .el-button {
    padding: 0;
    word-break:break-all;
}
</style>
<style lang="scss" scoped>
.tableBacklog--work-default {
  margin-left: 0px;

  .item-tab-bottom {
    position: relative;
  }
  /* 筛选内容 */
  .screening {
    box-shadow: 0 0 10px rgba(176, 192, 202, 0.5) inset;
    background-color: white;
    border-radius: 10px 10px 0 0;
    padding: 15px 40px;
    height: 62px;

    button {
      background-color: white;
      border: 1px solid #dcdfe6;
      border-radius: 5px;
      color: #989898;
      padding: 8px;

      & + button {
        margin-left: 20px;
      }

      &.active {
        border: 1px solid #0099e8;
        color: #0099e8;
      }
    }
  }

  .el-table {
    margin: 0;
  }
}
</style>
