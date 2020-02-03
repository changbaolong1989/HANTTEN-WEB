<template>
  <!-- 项目 > 清标管理 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible="true" width="500px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="招标名称">
          <span>{{ ruleForm.tenderName }}</span>
        </el-form-item>
        <el-form-item label="合同类型">
          <span>{{ selectGetName(ruleForm.contractType, contractTypeList) }}</span>
        </el-form-item>
        <el-form-item label="接收资料时间" prop="acceptFileTime">
          <el-date-picker v-model="ruleForm.acceptFileTime" type="date" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="发出报告时间" prop="sendReportTime">
          <el-date-picker v-model="ruleForm.sendReportTime" type="date" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>

        <el-form-item label="清标文件" class="upload upload1" prop="materialName">
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
            <el-input placeholder="请上传清标文件" v-model="ruleForm.materialName" :disabled="true"></el-input>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitCleanTenderfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
import DefaultProp from './default'; /* 清标管理默认元素  */
export default {
  data() {
    const formdata = new FormData();
    return {
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        tenderId: '', //清标管理ID
        tenderName: '', //招标名称
        contractType: '', //合同类型
        acceptFileTime: '', //接收资料时间
        sendReportTime: '', //发出报告时间
        file: '', //上传文件
        materialName: '' //上传文件名称
      },
      contractTypeList: DefaultProp.contractTypeList,
      //校验规则
      rules: {
        materialName: [
          {
            //必填
            required: true,
            message: '请上传文件',
            //触发方式:失去焦点
            trigger: 'blur'
          }
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
    selectGetName(key, list) {
      return this.$parent.selectGetName(key, list);
    },
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

    //查询清标管理信息(修改清标管理信息先行方法)
    updateCleanTenderFunc(row) {
      this.formTitle = '修改';
      this.url = '/wholeProcess/tender/modify/updateWholeProcessCleanTender';
      let formData = {
        tenderId: row.tenderId
      };
      const vm = this;
      vm.apiAddress('/wholeProcess/tender/query/searchProjectTenderPeriodInfo', formData)
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
              tenderId: dataVO.tenderId, //招标ID
              tenderName: dataVO.tenderName, //招标名称
              contractType: dataVO.contractType, //合同类型
              acceptFileTime: dataVO.acceptFileTime, //接收资料时间
              sendReportTime: dataVO.sendReportTime, //发出报告时间
              materialName: dataVO.reportName
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
    formatDate(prop) {
      if (prop) {
        const date = new Date(prop);
        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
      }
      return year + '-' + month + '-' + day;
    },
    //表单提交
    submitCleanTenderfunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          console.log("acceptFileTime",vm.ruleForm.acceptFileTime)
          console.log("sendReportTime",vm.ruleForm.sendReportTime)
          let acceptFileTime = vm.formatDate(vm.ruleForm.acceptFileTime);
          let sendReportTime = vm.formatDate(vm.ruleForm.sendReportTime);
          vm.formData.append('tenderId', vm.ruleForm.tenderId);
          vm.formData.append('acceptFileTime', acceptFileTime);
          vm.formData.append('sendReportTime', sendReportTime);
          // 获取列表数据
          vm.$refs.upload.submit();
          vm.apiAddress(this.url, vm.formData)
            .then(res => {
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

        &:last-child {
          border-bottom: none;
        }

        label {
          width: 110px !important;
          color: #424242;
          font-size: 12px;
        }
        .avatar-uploader {
          .el-upload {
            text-align: left;
          }
        }
        .iconfont {
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
