<template>
  <!-- 项目 > 估算 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg" justify="end">
      <el-col :span="24" class="text-align-right">
        <!-- 新增/修改-->
        <div class="item-tab-chuang" @click="addAssessBtn()" style="float: right;">
          <span class="el-icon-plus linear"></span>
          创建估算
        </div>
        <div class="item-tab-chuang"style="float: right;" @click="exportAssessBtn()">
          <span class="el-icon-upload2 linear"></span>
          导出
        </div>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="../../../../assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" type="index" label="序号"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="compileDate" label="编制日期" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.compileDate ? '-' : scope.row.compileDate }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="assessName" label="估算名称/版本" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.assessName ? '-' : scope.row.assessName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="floorSpace" label="建筑面积（m²）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.floorSpace ? '-' : new Number(scope.row.floorSpace) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="estimatedCost" label="估算费用（元）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.estimatedCost ? '-' : new Number(scope.row.estimatedCost) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="estimationExParte" label="估算单方（元/m²）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.estimationExParte ? '-' : new Number(scope.row.estimationExParte) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="costRange" label="费用范围" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.costRange ? '-' : scope.row.costRange }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="remark" label="备注" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.remark ? '-' : scope.row.remark }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="delete" @click="downLoadFile(scope.row)">下载</el-button>
            <el-button type="text" class="modify" @click="updateAssessBtn(scope.row)">修改</el-button>
          </template>
        </el-table-column>
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
    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="编制日期" prop="compileDate"><el-date-picker v-model="ruleForm.compileDate" type="date" placeholder="选择日期时间"></el-date-picker></el-form-item>
        <el-form-item label="估算名称/版本" prop="assessName"><el-input v-model="ruleForm.assessName" placeholder="请输入估算名称/版本"></el-input></el-form-item>
        <el-form-item label="建筑面积" prop="floorSpace"><el-input v-model="ruleForm.floorSpace" placeholder="请输入建筑面积"></el-input></el-form-item>
        <el-form-item label="估算费用" prop="estimatedCost"><el-input v-model="ruleForm.estimatedCost" placeholder="请输入估算费用"></el-input></el-form-item>
        <el-form-item label="估算单方(元/m²)" prop="estimationExParte"><el-input v-model="ruleForm.estimationExParte" placeholder="请输入估算单方"></el-input></el-form-item>
        <el-form-item label="费用范围" prop="costRange" class="tree-error"><el-input v-model="ruleForm.costRange" placeholder="请输入费用范围"></el-input></el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error"><el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>

        <el-form-item label="估算文件" class="upload upload1" prop="assessFilePath">
          <el-upload
            class="avatar-uploader"
            ref="upload"
            action="#"
            :show-file-list="false"
            :on-change="handleAvatarSuccess"
            :auto-upload="false"
            :before-upload="beforeAvatarUpload"
            :data="ruleForm"
          >
            <el-button>
              <span class="iconfont">&#xe614;</span>
              上传附件
            </el-button>
            <br />
            <el-input placeholder="请上传估算文件" v-model="ruleForm.materialName" :disabled="true"></el-input>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitAssessfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    const formdata = new FormData();
    return {
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      tabdata: [], //当前页数据
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)

      ruleForm: {
        //所提交form表单(校验表单)
        assessId: '', //估算ID
        compileDate: '', //编制日期
        assessName: '', //估算名称/版本
        floorSpace: '', //建筑面积（m²）
        estimatedCost: '', //估算费用（元）
        estimationExParte: '', //估算单方（元/m²）
        costRange: '', //费用范围
        remark: '', //备注
        file: '', //上传文件
        materialName: '' //上传文件名称
      },
      //校验规则
      rules: {
        compileDate: [
          {
            //必填
            required: true,
            message: '请选择编制日期',
            //触发方式:失去焦点
            trigger: 'blur'
          }
        ],
        //估算名称/版本
        assessName: [{ max: 20, message: '最长20字', trigger: 'blur' }],
        //建筑面积（m²）
        floorSpace: [{ max: 20, message: '最长20字', trigger: 'blur' }, { validator: this.positiveNumber, trigger: 'blur' }],
        //估算费用（元）
        estimatedCost: [
          {
            //必填
            required: true,
            message: '请输入估算费用',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //估算单方（元/m²）
        estimationExParte: [
          {
            //必填
            required: true,
            message: '请输入估算单方',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],

        //费用范围
        costRange: [{ max: 20, message: '最长20字', trigger: 'blur' }],
        //备注
        remark: [{ max: 100, message: '最长100字', trigger: 'blur' }]
      },
      //初始化上传参数载体(用以附带文件上传)
      formData: formdata,
      //dialog(弹出层)展示头
      formTitle: '',
      //弹窗显示Flag
      centerDialogVisible: false,
      //表单提交路径
      url: ''
    };
  },
  //页面初始化加载钩子函数
  mounted() {
    this.currentPage = 1;
    this.pageSize = 10;
    this.searchList(this.currentPage, this.pageSize);
  },
  methods: {
    //文件上传组件提交前钩子方法
    beforeAvatarUpload(file, fileList) {
      /* 个人签名 */
      this.formData.append('file', file);
      return false;
    },
    //文件上传组件提交成功后钩子方法
    handleAvatarSuccess(file, fileList) {
      /* 估算展示 */
      this.ruleForm.file = URL.createObjectURL(file.raw);
      this.ruleForm.materialName = file.name;
    },
    //新增估算信息先行方法
    addAssessBtn() {
      //弹出层展示Flag
      this.centerDialogVisible = true;
      this.formTitle = '新增';
      this.url = '/wholeProcess/assess/insert/insertWholeProcessAssess';
    },
    //dialog(弹出层)关闭方法
    handleClose(done) {
      this.$refs.ruleForm.resetFields();
      done();
    },
    // 每页条数切换
    handleSizeChange(val) {
      this.pageSize = val;
      this.searchList(this.currentPage, this.pageSize);
    },
    //页码切换
    handleCurrentChange(val) {
      this.currentPage = val;
      this.searchList(this.currentPage, this.pageSize);
    },

    //查询估算信息(修改估算信息先行方法)
    updateAssessBtn(row) {
      this.formTitle = '修改';
      this.url = '/wholeProcess/assess/modify/updateWholeProcessAssess';
      let formData = {
        assessId: row.assessId
      };
      const vm = this;
      vm.apiAddress('/wholeProcess/assess/query/searchProjectAssessPeriodInfo', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let dataVO = res.data.dataVO;
            console.log('dataVO', dataVO);
            //向表单赋值
            vm.ruleForm = {
              assessId: dataVO.assessId,
              compileDate: dataVO.compileDate,
              assessName: dataVO.assessName,
              floorSpace:dataVO.floorSpace,
              estimatedCost: new Number(dataVO.estimatedCost),
              estimationExParte: new Number(dataVO.estimationExParte),
              costRange: dataVO.costRange,
              remark: dataVO.remark,
              materialName: dataVO.assessFileName
            };
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      //显示弹出层
      this.centerDialogVisible = true;
    },
    // 查询列表数据
    searchList(currentPage, pageSize) {
      const vm = this;
      let formData = {
        // 页码
        pageNumber: currentPage,
        // 每页总条数
        pageSize: pageSize,
        // 项目编号
        projectId: vm.projectId
      };
      // 获取列表数据
      vm.apiAddress('/wholeProcess/assess/query/searchProjectAssessPeriodList', formData)
        .then(res => {
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
            vm.tabdata = dataList;
            // 设置数据总条数
            vm.total = res.data.dataCount;
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
    // 导出列表数据
    exportAssessBtn() {
      const vm = this;
      // 导出列表数据
      const elemIF = document.createElement('iframe');
      elemIF.src = '/wholeProcess/assess/exprot/exportAssessData?realFileName=' + "估算台账.xls" + "&projectId=" +  vm.projectId;
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
    },
    //下载文件
    downLoadFile(row) {
      this.downloadFile(row.assessFilePath, row.assessFileName);
    },
    //表单提交
    submitAssessfunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          const date = new Date(vm.ruleForm.compileDate);
          var year = date.getFullYear();
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          const time = year + '-' + month + '-' + day;
          vm.formData.append('assessId', vm.ruleForm.assessId);
          vm.formData.append('compileDate', time);
          vm.formData.append('projectId', vm.projectId);
          vm.formData.append('assessName', vm.ruleForm.assessName);
          vm.formData.append('floorSpace', vm.ruleForm.floorSpace);
          vm.formData.append('estimatedCost', vm.ruleForm.estimatedCost);
          vm.formData.append('estimationExParte', vm.ruleForm.estimationExParte);
          vm.formData.append('costRange', vm.ruleForm.costRange); /* 专业 */
          vm.formData.append('remark', vm.ruleForm.remark);
          // 获取列表数据
          vm.$refs.upload.submit();
          vm.apiAddress(this.url, vm.formData)
            .then(res => {
              vm.centerDialogVisible = false; /* 让弹窗隐藏 */
              vm.formData = new FormData(); /* 清空formData数据*/
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
                vm.searchList(vm.currentPage, vm.pageSize);
                vm.ruleForm.file = '';
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
          this.$refs['ruleForm'].resetFields(); /* 清空表单数据 */
        }
      });
    },
    /**
     * @method 文件上传设置
     * @param {Object} files
     * @param {Object} fileList
     */
    handleExceed(files, fileList) {
      let vm = this;
      this.$message.warning(`当前限制选择 ${vm.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
  }
};
</script>

<style lang="scss">
.mh-set-role .el-table .table-column:last-child .cell {
  cursor: pointer;
}

/* 弹窗 */
.mh-set-role .el-dialog--center {
  .el-dialog__body {
    padding: 0;

    .el-form {
      padding: 18px 30px 0;
      border-bottom: 1px solid #dcdfe6;

      .el-form-item {
        border-bottom: 1px solid #dcdfe6;
        margin: 0;
        padding: 10px 0;

        &:last-child {
          border-bottom: none;
        }
        .avatar-uploader {
          .el-upload {
            text-align: left;
          }
        }
        .iconfont {
          font-size: 12px;
        }
        label {
          width: 110px !important;
          color: #424242;
          font-size: 12px;
        }

        .el-form-item__content {
          margin-left: 110px !important;

          .el-input__inner {
            height: 27px;
            line-height: 27px;
            color: #424242;
            font-size: 12px;
            background-color: #f8f9fb;
          }
        }

        .el-date-editor {
          width: 100%;
        }
      }
    }
  }

  .dialog-footer {
    .el-button {
      width: 120px;
      height: 30px;
      line-height: 30px;
      padding: 0;
      font-size: 12px;
    }
  }
}

/* 表单 */
.mh-set-role .item-from {
  padding: 14px 20px;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
  margin-bottom: 20px;
}

.mh-set-role .item-from > div {
  display: inline-block;
}

.mh-set-role .el-input__inner {
  background-color: #f8f9fb;
}

.mh-set-role .item-serial .el-form-item {
  display: block !important;
}

.mh-set-role .item-from .el-input {
  width: 125px;
  height: 32px;
  border: 1px solid #f8f9fb;
  border-radius: 5px;
  margin-right: 20px;
  font-size: 12px;
}

.mh-set-role .item-input label {
  font-size: 12px;
  color: #333;
  margin-right: 10px;
}

.mh-set-role .item-date {
  margin-right: 30px;
}

.mh-set-role .item-date span {
  font-size: 12px;
  color: #333;
  margin-right: 10px;
}

.mh-set-role .itemfrom-right {
  float: right;
}

.mh-set-role .itemfrom-right button {
  margin: 0 10px;
  font-size: 12px;
  width: 90px;
  height: 30px;
  line-height: 30px;
  border-radius: 5px;
  padding: 0;
}

.mh-set-role .item-from .el-input .el-input__inner {
  color: #333;
  width: 135px;
  height: 30px;
  line-height: 30px;
}

.mh-set-role .item-from .el-range-editor.el-input__inner {
  border-radius: 5px;
  width: 233px;
  height: 30px;
}

.mh-set-role .el-date-editor .el-range-input {
  font-size: 12px;
  color: #333333;
  background-color: #f8f9fb;
  height: 28px !important;
}

.mh-set-role .item-serial {
  margin: 25px 0;
}

.mh-set-role .el-date-editor .el-range__icon,
.el-date-editor .el-range-separator {
  line-height: 20px;
  color: #b1b1b1 !important;
}

.delete {
  color: #fc4053;
}

.modify {
  color: #06a1ee;
}
</style>
