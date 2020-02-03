<template>
  <!-- 项目 > 索赔台账 -->
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
        <el-form-item label="申报金额(元)" prop="declareAmount"><el-input v-model="ruleForm.declareAmount" placeholder="请输入申报金额"></el-input></el-form-item>
        <el-form-item label="汉腾审核金额(元)" prop="hanttenAmount" class="tree-error">
          <el-input v-model="ruleForm.hanttenAmount" placeholder="请输入汉腾审核金额"></el-input>
        </el-form-item>
        <el-form-item label="已确认金额(元)" prop="confirmAmount" class="tree-error">
          <el-input v-model="ruleForm.confirmAmount" placeholder="请输入已确认金额"></el-input>
        </el-form-item>
        <el-form-item label="预估金额(元)" prop="estimatedAmount" class="tree-error">
          <el-input v-model="ruleForm.estimatedAmount" placeholder="请输入预估金额"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error"><el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitClaimfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
import DefaultProp from './default'; /* 索赔台账默认元素  */
export default {
  data() {
    const formdata = new FormData();
    return {
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        contractId: '',
        declareAmount: '',
        hanttenAmount: '',
        confirmAmount: '',
        estimatedAmount: '',
        remark: ''
      },
      //校验规则
      rules: {
        contractId: [
          {
            //必填
            required: true,
            message: '请选择合同',
            //触发方式:失去焦点
            trigger: 'blur'
          }
        ],
        declareAmount: [
          {
            //必填
            required: true,
            message: '请输入申报金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        hanttenAmount: [
          {
            //必填
            required: true,
            message: '请输入汉腾审核金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        confirmAmount: [
          {
            //必填
            required: true,
            message: '请输入已确认金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        estimatedAmount: [
          {
            //必填
            required: true,
            message: '请输入预估金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        remark: [{ max: 100, message: '最长100字', trigger: 'blur' }]
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
    //新增索赔台账信息先行方法
    addClaimFunc() {
      this.formTitle = '新增';
      this.url = '/wholeProcess/claim/insert/insertWholeProcessClaim';
      this.searchContractList();
    },
    //dialog(弹出层)关闭方法
    handleClose(done) {
      this.$refs.ruleForm.resetFields();
      done();
      console.log('------------', this.$parent.centerDialogVisible);
      this.$parent.centerDialogVisible = false;
    },
    //查询索赔台账信息(修改索赔台账信息先行方法)
    updateClaimFunc(row) {
      this.searchContractList();
      this.formTitle = '修改';
      this.url = '/wholeProcess/claim/modify/updateWholeProcessClaim';
      let formData = {
        claimId: row.claimId
      };
      const vm = this;
      vm.$nextTick(() => {
        vm.apiAddress('/wholeProcess/claim/query/searchProjectClaimPeriodInfo', formData)
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
                claimId: dataVO.claimId,
                contractId: dataVO.contractId,
                declareAmount: new Number(dataVO.declareAmount),
                hanttenAmount: new Number(dataVO.hanttenAmount),
                confirmAmount: new Number(dataVO.confirmAmount),
                estimatedAmount: new Number(dataVO.estimatedAmount),
                remark: dataVO.remark
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
    submitClaimfunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          let formData = {
            claimId: vm.ruleForm.claimId,
            contractId: vm.ruleForm.contractId,
            projectId: vm.projectId,
            declareAmount: vm.ruleForm.declareAmount,
            hanttenAmount: vm.ruleForm.hanttenAmount,
            confirmAmount: vm.ruleForm.confirmAmount,
            estimatedAmount: vm.ruleForm.estimatedAmount,
            remark: vm.ruleForm.remark
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
          this.$refs['ruleForm'].resetFields(); /* 清空表单数据 */
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
          width: 120px !important;
          color: #424242;
          font-size: 12px;
        }

        .el-form-item__content {
          margin-left: 120px !important;

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
