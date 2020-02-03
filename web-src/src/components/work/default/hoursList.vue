<template>
  <div class="hoursList">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="任务名称"><el-input v-model="formInline.taskName" placeholder="请输入任务名称"></el-input></el-form-item>
      <el-form-item label="姓名"><el-input v-model="formInline.userName" placeholder="请输入姓名"></el-input></el-form-item>
<!--      <el-form-item label="任务分配年月">
        <el-date-picker v-model="formInline.date" type="month" value-format="yyyy-MM" placeholder="选择任务分配年月"></el-date-picker>
      </el-form-item> -->
      <el-form-item class="form-right"><el-button type="primary" class="linear" icon="el-icon-search" @click="onSubmit">搜索</el-button></el-form-item>
    </el-form>
    <el-table :data="tableData" :span-method="objectSpanMethod" border style="width: 100%;">
      <el-table-column prop="userName" label="姓名" align="center"></el-table-column>
      <el-table-column prop="taskName" label="任务名称" align="center">
        <template slot-scope="scope">
          <div :class="{ liang: scope.row.highlight=='0' }">{{ scope.row.taskName }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="endDate" label="任务完成时间" align="center">
        <template slot-scope="scope">
          <div :class="{ liang: scope.row.highlight=='0' }">{{ scope.row.endDate }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="needHour" label="预计占用工时" align="center">
        <template slot-scope="scope">
          <div :class="{ liang: scope.row.highlight=='0' }">{{ scope.row.needHour }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="actualHourSum" label="已占用工时" align="center">
        <template slot-scope="scope">
          <div :class="{ liang: scope.row.highlight=='0' }">{{ scope.row.actualHourSum }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" v-if="userPowerInfo.hourListRemark">
        <template slot-scope="scope">
          <el-button type="text" @click="goToModifyRemarks(scope.$index, scope.row)">备注</el-button>
          <p style="display: none;">{{ scope.row.relationId }}</p>
        </template>
      </el-table-column>
    </el-table>
    <!-- 修改弹窗 -->
    <el-dialog
      title="工时备注"
      :visible.sync="centerDialogVisible"
      width="700px"
      top="30vh"
      transition="fade-in-linear"
      center
      @close="handClose('ruleForm')"
      :close-on-click-modal="false"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm" label-position="top">
        <el-form-item prop="desc"><el-input type="textarea" :rows="8" v-model="ruleForm.desc"></el-input></el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button></span>
    </el-dialog>
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
        layout="total, prev, pager, next, sizes, jumper"
        :total="total"
      ></el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        taskName: '',
        userName: '',
        date: ''
      },
      tableData: [],
      spanArr: [], // 一个空的数组，用于存放每一行记录的合并数
      pos: 0, // spanArr 的索引
      total: 0,
      /* 总条目数 */
      currentPage: 1,
      /* 当前页码 */
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90],
      /* 每页显示个数选择器的选项设置 */
      pageSize: 10,
      /* 每页显示条目个数 */
      centerDialogVisible: false,
      userPowerInfo:this.SStorage.getItem("userPowerInfo"),
      ruleForm: {
        desc: ''
      },
      rules: {
        desc: [
          {
            required: true,
            message: '请输入工时备注',
            trigger: 'blur'
          }
        ]
      },
      relationId: ''
    };
  },
  created() {
    // 页面初始化时、点击查询时查询列表数据
    this.refer();
  },
  methods: {
    handClose(index) {
      /* 关闭弹窗清空表单 */
      this.$refs[index].resetFields();
    },
    // 页面初始化时、点击查询时查询列表数据
    refer() {
      // 查询我的工时列表请求
      this.apiAddress('/task/query/searchHourListInfo', {
        // 页码
        pageNumber: 1,
        // 每页总条数
        pageSize: this.pageSize,
        // 任务名称
        taskName: this.formInline.taskName,
        // 用户姓名
        userName: this.formInline.userName,
        // 任务分配年月
        createDate: this.formInline.date
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置表格数据
            this.tableData = res.data.pagedData.dataList;
            // 设置数据总条数
            this.total = res.data.pagedData.dataCount;
            // 合并单元格
            this.currentChangePage(this.tableData, this.currentPage, this.pageSize);
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 点击分页
    refers() {
      // 查询我的工时列表请求
      this.apiAddress('/task/query/searchHourListInfo', {
        // 页码
        pageNumber: this.currentPage,
        // 每页总条数
        pageSize: this.pageSize,
        // 任务名称
        taskName: this.formInline.taskName,
        // 用户姓名
        userName: this.formInline.userName,
        // 任务分配年月
        createDate: this.formInline.date
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置表格数据
            this.tableData = res.data.pagedData.dataList;
            // 设置数据总条数
            this.total = res.data.pagedData.dataCount;
            // 合并单元格
            this.currentChangePage(this.tableData, this.currentPage, this.pageSize);
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 点击搜索
    onSubmit() {
      this.refer();
    },
    //分页方法
    currentChangePage(list, i, k) {
      this.tableData = list;
      // 设定一个数组spanArr/contentSpanArr来存放要合并的格数，同时还要设定一个变量pos/position来记录
      this.spanArr = [];
      this.contentSpanArr = [];
      for (var i = 0; i < this.tableData.length; i++) {
        if (i === 0) {
          this.spanArr.push(1);
          this.pos = 0;
          this.contentSpanArr.push(1);
          this.position = 0;
        } else {
          // 判断当前元素与上一个元素是否相同(第1和第2列)
          if (this.tableData[i].userId === this.tableData[i - 1].userId) {
            this.spanArr[this.pos] += 1;
            this.spanArr.push(0);
          } else {
            this.spanArr.push(1);
            this.pos = i;
          }
        }
      }
    },
    objectSpanMethod({
      /* 合并表格 */
      row,
      column,
      rowIndex,
      columnIndex
    }) {
      if (columnIndex === 0) {
        const _row = this.spanArr[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
    handleSizeChange(val) {
      /* 每页条目数切换 */
      this.pageSize = val;
      this.refers();
    },
    handleCurrentChange(val) {
      /* 页码切换 */
      this.currentPage = val;
      this.refers();
    },
    // 回显备注
    goToModifyRemarks(a, b) {
      // 显示修改弹窗
      this.centerDialogVisible = true;
      // 设置关联id
      this.relationId = b.relationId;
      // 回显备注请求
      this.apiAddress('/task/query/searchRemarksByRelationId', {
        // 关联id
        relationId: this.relationId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.ruleForm.desc = res.data.dataVO.remarks;
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error');
        });
    },
    // 修改备注
    submitForm(formName) {
      this.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          // 查询我的工时列表请求
          this.apiAddress('/task/modify/updateRemarksInfo', {
            // 关联id
            relationId: this.relationId,
            // 备注
            remarks: this.ruleForm.desc
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                this.stateDialog('success', res.data.message);
                this.centerDialogVisible = false;
              } else {
                this.stateDialog('error', res.data.message);
              }
            })
            .catch(e => {
              this.stateDialog('error');
            });
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss">
.hoursList {
  .el-form {
    background-color: #fff;
    border-radius: 10px;
    padding: 10px;

    .el-form-item {
      margin-bottom: 0px;

      .el-form-item__label {
        font-size: 12px;
      }
    }
  }

  .el-table {
    margin: 20px 0;
    border-radius: 10px;
  }
}
</style>
<style lang="scss" scoped>
.hoursList {
  .form-right {
    float: right;
  }
  .liang {
    color:rgb(252, 64, 83);
  }
}
</style>
