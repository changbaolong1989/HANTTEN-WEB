<template>
  <!-- 项目 > 付款台账 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible="true" width="500px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="合同编号-合同名" prop="contractId">
          <el-select v-model="ruleForm.contractId" placeholder="请选择" @change="changeContractSelect">
            <el-option v-for="item in contractList" :key="item.contractId" :label="item.contractNum + '-' + item.contractName" :value="item.contractId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同分类" prop="treatyType">
          <span>{{ treatyType }}</span>
        </el-form-item>
        <el-form-item label="承包方式" prop="undertakeType" v-if="treatyTypeFlag">
          <span>{{ undertakeType }}</span>
        </el-form-item>
        <el-form-item label="期数" prop="periodNum">
          <el-select v-model="ruleForm.periodNum" placeholder="请选择">
            <el-option v-for="item in periodNumList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="本期产值(元)" prop="currentValue" class="tree-error"><el-input v-model="ruleForm.currentValue" placeholder="请输入本期产值"></el-input></el-form-item>
        <el-form-item label="本期应付(元)" prop="currentPayment" class="tree-error">
          <el-input v-model="ruleForm.currentPayment" placeholder="请输入本期应付"></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="editDate"><el-date-picker v-model="ruleForm.editDate" type="date" placeholder="选择日期时间"></el-date-picker></el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitPayfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
import DefaultProp from './default'; /* 付款台账默认元素  */
export default {
  data() {
    const formdata = new FormData();
    return {
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        contractId: '',
        periodNum: '',
        currentValue: '',
        currentPayment: '',
        editDate: ''
      },
      //校验规则
      rules: {
        contractId: [
          {
            //必填
            required: true,
            message: '请选择合同',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        periodNum: [
          {
            //必填
            required: true,
            message: '请选择期数',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        currentValue: [
          {
            //必填
            required: true,
            message: '请输入本期产值',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        currentPayment: [
          {
            //必填
            required: true,
            message: '请输入本期应付',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        editDate: [
          {
            //必填
            required: true,
            message: '选择日期时间',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ]
      },
      //初始化上传参数载体(用以附带文件上传)
      formData: formdata,
      //dialog(弹出层)展示头
      formTitle: '',
      //表单提交路径
      url: '',

      /*---------------选项卡start------------------------*/

      //合约分类选项
      treatyTypeList: DefaultProp.treatyTypeList,

      //承包方式选项
      undertakeTypeList: DefaultProp.undertakeTypeList,

      //合同类型选项
      contractTypeList: DefaultProp.contractTypeList,

      //采购方式选项
      purchaserWayList: DefaultProp.purchaserWayList,

      //文件类型选项
      fileTypeList: DefaultProp.fileTypeList,

      //期数列表
      periodNumList: DefaultProp.periodNumList,

      //合同列表
      contractList: [],

      treatyType: '',

      treatyTypeFlag: false,

      undertakeType: '',

      contractInfo: ''

      /*---------------选项卡end------------------------*/
    };
  },
  methods: {
    selectGetName(key, list) {
      return this.$parent.selectGetName(key, list);
    },
    changeContractSelect(item) {
      for (var i = 0; i < this.contractList.length; i++) {
        if (this.contractList[i].contractId == item) {
          this.ruleForm.contractId = this.contractList[i].contractId;
          if (this.contractList[i].treatyType == 1) {
            this.treatyTypeFlag = true;
          } else {
            this.treatyTypeFlag = false;
          }
          this.treatyType = this.selectGetName(this.contractList[i].treatyType, this.treatyTypeList);
          this.undertakeType = this.selectGetName(this.contractList[i].undertakeType, this.undertakeTypeList);
        }
      }
    },
    //新增付款台账信息先行方法
    addPayFunc() {
      this.formTitle = '新增';
      this.url = '/wholeProcess/pay/insert/insertWholeProcessPay';
      this.searchContractList();
    },
    //dialog(弹出层)关闭方法
    handleClose(done) {
      this.$refs.ruleForm.resetFields();
      done();
      console.log('------------', this.$parent.centerDialogVisible);
      this.$parent.centerDialogVisible = false;
    },
    //查询付款台账信息(修改付款台账信息先行方法)
    updatePayFunc(row) {
      this.searchContractList();
      this.formTitle = '修改';
      this.url = '/wholeProcess/pay/modify/updateWholeProcessPay';
      let formData = {
        payId: row.payId
      };
      const vm = this;
      vm.$nextTick(() => {
        vm.apiAddress('/wholeProcess/pay/query/searchProjectPayPeriodInfo', formData)
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
                payId: dataVO.payId,
                contractId: dataVO.contractId,
                periodNum: dataVO.periodNum,
                currentValue: new Number(dataVO.currentValue),
                currentPayment: new Number(dataVO.currentPayment),
                editDate: dataVO.editDate
              };
              for (var i = 0; i < vm.contractList.length; i++) {
                if (dataVO.contractId == vm.contractList[i].contractId) {
                  vm.contractInfo = vm.contractList[i];
                  vm.changeContractSelect(vm.contractList[i].contractId);
                }
              }
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

    // 查询列表数据
    searchContractList() {
      const vm = this;
      let formData = {
        projectId: vm.projectId
      };
      // 获取合同列表数据
      vm.apiAddress('/wholeProcess/claim/query/searchContractList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            vm.contractList = res.data.dataList;
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
      const date = new Date(prop);
      var year = date.getFullYear();
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
      return year + '-' + month + '-' + day;
    },
    //表单提交
    submitPayfunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          let editDate = vm.formatDate(vm.ruleForm.editDate);
          let formData = {
            payId: vm.ruleForm.payId,
            contractId: vm.ruleForm.contractId,
            projectId: vm.projectId,
            periodNum: vm.ruleForm.periodNum,
            currentValue: vm.ruleForm.currentValue,
            currentPayment: vm.ruleForm.currentPayment,
            editDate: editDate
          };
          // 获取列表数据
          vm.apiAddress(this.url, formData)
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
                this.$refs['ruleForm'].resetFields(); /* 清空表单数据 */
                vm.$parent.reflashData();
                this.$parent.centerDialogVisible = false; /* 让弹窗隐藏 */
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        }
      });
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
