<template>
  <div class="wholeProcessCost__projectList">

    <div class="item-from screeningInput">
      <div class="form-goup" style="
      display: inline-block;
      width: calc(100% - 120px);
      overflow: auto;
      padding: 0 10px 14px;
      box-shadow: none;
      white-space: nowrap;">
          <div class="item-input"><label for="i3">项目名称</label>
              <el-input v-model="inquireProjectName" placeholder="请输入项目名称" id="i4"></el-input>
            </div>
            <div class="item-input">
              <label for="i1">所属部门</label>
              <el-select v-model="inquireDepartmentId" placeholder="请选择所属部门" clearable>
                <el-option v-for="item in departmentSelect" :key="item.deptId" :label="item.deptName" :value="item.deptId">
                  {{item.deptName}}
                </el-option>
              </el-select>
            </div>
            <div class="item-input"><label for="i3">项目编号</label>
              <el-input v-model="inquireProjectNum" placeholder="请输入项目编号" id="i3"></el-input>
            </div>
            <div class="item-input"><label for="i2">完成状态</label>
              <el-select v-model="inquireProjectSts" placeholder="请选择完成状态" clearable>
                <el-option v-for="item in projectStsSelect" :key="item.projectSts" :label="item.projectStsLabel" :value="item.projectSts">
                  {{item.projectStsLabel}}
                </el-option>
              </el-select>
            </div>
            <div class="item-input">
              <label>项目创建时间</label>
              <el-date-picker v-model="inquireDateArr" type="daterange" value-format="yyyy-MM-dd" start-placeholder="开始日期"
                end-placeholder="结束日期" :default-value="nowDate">
              </el-date-picker>
            </div>
          </div>
          <div class="item-input search-btn-container">
            <el-button type="primary" icon="el-icon-search" class="linear" @click="clickSearchList">查询</el-button>
          </div>
    </div>

    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%" class="item-tab" :span-method="objectSpanMethod" :cell-style="rowClass">
        <el-table-column align="center" class-name="table-column" label="项目编号" :show-overflow-tooltip="true" :resizable="false">
          <template slot-scope="scope">
            <el-button type="text" @click="goToProjectPeriodPage(scope.row)" class="text-overflow-ellipsis" style="width:100%">{{ scope.row.projectNum }}</el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同数量" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.contractCount}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="项目名称" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.projectName}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同编号" :show-overflow-tooltip="true" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.contractNum==null?"-":scope.row.contractNum}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同所属部门" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.departmentName==null?"-":scope.row.departmentName}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同负责人" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.dutyPeople==null?"-":scope.row.dutyPeople}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同签订时间" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.contractDate==null?"-":scope.row.contractDate}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同已收金额" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.incomeReceived==null?"-":scope.row.incomeReceived}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="合同金额" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.contractAmount==null?"-":scope.row.contractAmount}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="projectSts" label="完成状态" :resizable="false">
          <template slot-scope="scope">
            {{scope.row.projectSts==null?"-":(scope.row.projectSts==0?"未完成":"已完成")}}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="block item-fen" v-show="total>0">
      <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
        :total="total">
      </el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      // 部门下拉列表
      const departmentSelect = [{
          deptId: 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91',
          deptName: '造价部'
        },
        {
          deptId: 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3',
          deptName: '项目部'
        },
        {
          deptId: 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91',
          deptName: '招标代理部'
        }
      ];
      // 项目状态下拉列表
      const projectStsSelect = [{
          projectSts: '0',
          projectStsLabel: '未完成'
        },
        {
          projectSts: '1',
          projectStsLabel: '已完成'
        }
      ];
      return {
        tabdata: [],
        inquireDateArr: "",
        /* 展示查询时间 */
        inquireDepartmentId: "",
        /* 展示查询部门 */
        inquireProjectSts: "",
        /* 展示查询状态 */
        inquireProjectNum: "",
        /* 展示查询项目查询名称 */
        inquireProjectName: "",
        //部门ID
        departmentId:"",
        //项目状态
        projectSts: "",
        //项目编号
        projectNum: "",
        //项目名称
        projectName:"",
        /* 展示查询编号 */
        nowDate: new Date(),
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        departmentSelect: departmentSelect,
        projectStsSelect: projectStsSelect,
        timeList: [],

      }
    },
    mounted() {
      this.searchList(this.currentPage, this.pageSize)
    },
    methods: {
      goToProjectPeriodPage(row) {
        this.SStorage.setItem('/page/wholeProcessCost/projectDetails', {
          rowInfo: row
        })
        this.$router.push('/page/wholeProcessCost/projectDetails');
      },
      clickSearchList() {
        this.currentPage = 1;
        this.pageSize = 10;
        /* 查询时把查询条件赋值*/
        this.departmentId = this.inquireDepartmentId;
        this.projectSts = this.inquireProjectSts;
        this.projectNum = this.inquireProjectNum;
        this.projectName = this.inquireProjectName;
        let timeList = [];
        // 如果时间不为空，则遍历时间，放在集合中
        if (this.inquireDateArr != null) {
          // 遍历时间
          for (var i = 0; i < this.inquireDateArr.length; i++) {
            // 把时间放在集合中
            timeList.push(this.inquireDateArr[i]);
          }
        }
        this.timeList = timeList
        this.searchList(this.currentPage, this.pageSize)
      },
      // 每页条数切换
      handleSizeChange(val) {
        this.pageSize = val;
        this.searchList(this.currentPage, this.pageSize)
      },

      //页码切换
      handleCurrentChange(val) {
        this.currentPage = val;
        this.searchList(this.currentPage, this.pageSize)
      },
      //分页方法
      currentChangePage(list, i, k) {
        this.tabdata = list;
        // 设定一个数组spanArr/contentSpanArr来存放要合并的格数，同时还要设定一个变量pos/position来记录
        this.spanArr = [];
        this.contentSpanArr = [];
        for (var i = 0; i < this.tabdata.length; i++) {
          if (i === 0) {
            this.spanArr.push(1);
            this.pos = 0;
            this.contentSpanArr.push(1);
            this.position = 0;
          } else {
            // 判断当前元素与上一个元素是否相同(第1和第2列)
            if (this.tabdata[i].projectNum === this.tabdata[i - 1].projectNum) {
              this.spanArr[this.pos] += 1;
              this.spanArr.push(0);
            } else {
              this.spanArr.push(1);
              this.pos = i;
            }
          }
        }
      },
      // 查询列表数据
      searchList(currentPage, pageSize) {
        const vm = this;
        let formData = {
          // 页码
          "pageNumber": currentPage,
          // 每页总条数
          "pageSize": pageSize,
          // 项目编号
          "projectNum": vm.projectNum,
          // 项目名称
          "projectName": vm.projectName,
          // 项目状态
          "projectSts": vm.projectSts,
          // 部门id
          "departmentId": vm.departmentId,
          // 时间
          "timeList": vm.timeList
        }
        // 获取列表数据
        vm.apiAddress('/wholeProcess/project/query/searchContractList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let dataList = res.data.dataList;
            // 循环数据放入表格数据对象中
            vm.currentChangePage(dataList, vm.currentPage, vm.pageSize);
            // 设置数据总条数
            vm.total = res.data.dataCount;
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },

      // 点击分页
      refers() {
        const vm = this;
        /* 查询时把查询条件赋值*/
        vm.departmentId = vm.inquireDepartmentId;
        vm.projectSts = vm.inquireProjectSts;
        vm.projectNum = vm.inquireProjectNum;

        let timeList = [];
        // 如果时间不为空，则遍历时间，放在集合中
        if (vm.inquireDateArr != null) {
          // 遍历时间
          for (var i = 0; i < vm.inquireDateArr.length; i++) {
            // 把时间放在集合中
            timeList.push(vm.inquireDateArr[i]);
          }
        }
      },


      // 动态修改项目状态的颜色
      rowClass({
        row,
        column,
        rowIndex,
        columnIndex
      }) {
        if (columnIndex === 8 && row.projectSts == "1") {
          return 'color:#FC4053'
        } else if (columnIndex === 8 && row.projectSts == "0") {
          return 'color:#7FD680'
        }
      },
      objectSpanMethod({
        row,
        column,
        rowIndex,
        columnIndex
      }) {
        if (columnIndex === 0 || columnIndex === 1) {
          const _row = this.spanArr[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          }
        } else if (columnIndex === 8) {
          const _row = this.spanArr[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          return {
            rowspan: _row,
            colspan: _col
          }
        }
      },
    }
  }
</script>

<style lang="scss">
  .wholeProcessCost__projectList {
    .screeningInput {
      /* 表单项 */
      .el-input {
        width: 125px;
        height: 30px;
        border: 1px solid #f8f9fb;
        border-radius: 5px;
        margin-right: 20px;
        font-size: 12px;

        .el-input__inner {
          background-color: #f8f9fb;
          width: 135px;
          height: 28px!important;
          line-height: 28px!important;
          vertical-align: top;

          &.el-range-editor {
            border-radius: 5px;
            width: 233px;
            height: 30px;
          }
        }
      }

      .el-range-input,input{
        height: 28px!important;
        line-height: 28px!important;
        vertical-align: top;
      }

      .el-date-editor {
        margin-right: 20px;
        font-size: 12px;

        .el-range__icon {
          line-height: 30px !important;
        }
      }

      .item-input {
        margin-top:1px;

        .el-input {
          width: auto !important;
        }
      }
    }

  }
</style>
<style lang="scss" scoped>
  .wholeProcessCost__projectList {
    background-color: white;
    border-radius: 10px 10px 0 0;

    /* 筛选输入框 */
    .screeningInput {
      padding: 15px 20px;
      position: relative;
      overflow:hidden;

      .form-goup {
        display: flex;
        flex-wrap: wrap;

        .item-input {
          min-width: 220px;
          display:inline-block;

          .el-input{
            width: auto;
          }

          label {
            font-size: 12px;
            color: #333;
            margin-right: 10px;
          }

        }
      }

      /* 搜索按钮容器 */
      .search-btn-container {
        position: absolute;
        right: 36px;
        top: 16px;
      }


    }
  }
</style>
