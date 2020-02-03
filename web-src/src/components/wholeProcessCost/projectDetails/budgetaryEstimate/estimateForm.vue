<template>
  <!-- 项目 > 概算 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible="true" width="500px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="编制日期" prop="compileDate"><el-date-picker v-model="ruleForm.compileDate" type="date" placeholder="选择日期时间"></el-date-picker></el-form-item>
        <el-form-item label="概算名称/版本" prop="estimateName"><el-input v-model="ruleForm.estimateName" placeholder="请输入概算名称/版本"></el-input></el-form-item>
        <el-form-item label="建筑面积" prop="floorSpace"><el-input v-model="ruleForm.floorSpace" placeholder="请输入建筑面积"></el-input></el-form-item>
        <el-form-item label="概算费用" prop="estimatedCost"><el-input v-model="ruleForm.estimatedCost" placeholder="请输入概算费用"></el-input></el-form-item>
        <el-form-item label="概算单方(元/m²)" prop="estimationExParte"><el-input v-model="ruleForm.estimationExParte" placeholder="请输入概算单方"></el-input></el-form-item>
        <el-form-item label="费用范围" prop="costRange" class="tree-error"><el-input v-model="ruleForm.costRange" placeholder="请输入费用范围"></el-input></el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error"><el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>

        <el-form-item label="概算文件" class="upload upload1" prop="materialName">
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
            <el-input placeholder="请上传概算文件" v-model="ruleForm.materialName" :disabled="true"></el-input>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitEstimatefunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    const formdata = new FormData();
    return {
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        estimateId: '', //概算ID
        compileDate: '', //编制日期
        estimateName: '', //概算名称/版本
        floorSpace: '', //建筑面积（m²）
        estimatedCost: '', //概算费用（元）
        estimationExParte: '', //概算单方（元/m²）
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
        //概算费用（元）
        estimatedCost: [
          {
            //必填
            required: true,
            message: '请输入概算费用',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //概算单方（元/m²）
        estimationExParte: [
          {
            //必填
            required: true,
            message: '请输入概算单方',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //概算名称/版本
        estimateName: [{ max: 20, message: '最长20字', trigger: 'blur' }],
        //建筑面积（m²）
        floorSpace: [{ max: 8, message: '最长8字', trigger: 'blur' },{ validator: this.positiveNumber, trigger: 'blur' }],
        //费用范围
        costRange: [{ max: 20, message: '最长20字', trigger: 'blur' }],
        //备注
        remark: [{ max: 100, message: '最长100字', trigger: 'blur' }],
        materialName:[
          {
            //必填
            required: true,
            message: '请上传文件',
            //触发方式:失去焦点
            trigger: 'blur'
          },
        ]
      },
      //初始化上传参数载体(用以附带文件上传)
      formData: formdata,
      //dialog(弹出层)展示头
      formTitle: '',
      //表单提交路径
      url: ''
    };
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
    //新增概算信息先行方法
    addEstimateFunc() {
      this.formTitle = '新增';
      this.url = '/wholeProcess/estimate/insert/insertWholeProcessEstimate';
    },
    //dialog(弹出层)关闭方法
    handleClose(done) {
      this.$refs.ruleForm.resetFields();
      done();
      console.log('------------', this.$parent.centerDialogVisible);
      this.$parent.centerDialogVisible = false;
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

    //查询概算信息(修改概算信息先行方法)
    updateEstimateFunc(row) {
      this.formTitle = '修改';
      this.url = '/wholeProcess/estimate/modify/updateWholeProcessEstimate';
      let formData = {
        estimateId: row.estimateId
      };
      const vm = this;
      vm.apiAddress('/wholeProcess/estimate/query/searchProjectEstimatePeriodInfo', formData)
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
            //向表单赋值
            vm.ruleForm = {
              estimateId: dataVO.estimateId,
              compileDate: dataVO.compileDate,
              estimateName: dataVO.estimateName,
              floorSpace: dataVO.floorSpace,
              estimatedCost: new Number(dataVO.estimatedCost),
              estimationExParte: new Number(dataVO.estimationExParte),
              costRange: dataVO.costRange,
              remark: dataVO.remark,
              materialName: dataVO.estimateFileName
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
    },
    //表单提交
    submitEstimatefunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          const date = new Date(vm.ruleForm.compileDate);
          var year = date.getFullYear();
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          const time = year + '-' + month + '-' + day;
          vm.formData.append('estimateId', vm.ruleForm.estimateId);
          vm.formData.append('compileDate', time);
          vm.formData.append('projectId', vm.projectId);
          vm.formData.append('estimateName', vm.ruleForm.estimateName);
          vm.formData.append('floorSpace', vm.ruleForm.floorSpace);
          vm.formData.append('estimatedCost', vm.ruleForm.estimatedCost);
          vm.formData.append('estimationExParte', vm.ruleForm.estimationExParte);
          vm.formData.append('costRange', vm.ruleForm.costRange); /* 专业 */
          vm.formData.append('remark', vm.ruleForm.remark);
          // 获取列表数据
          vm.$refs.upload.submit();
          vm.apiAddress(this.url, vm.formData)
            .then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.formData = new FormData(); /* 清空formData数据*/
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                vm.$parent.reflashData();
                this.$parent.centerDialogVisible = false; /* 让弹窗隐藏 */
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
        .avatar-uploader {
          .el-upload {
            text-align: left;
          }
        }
        .iconfont {
          font-size: 12px;
        }
        &:last-child {
          border-bottom: none;
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
