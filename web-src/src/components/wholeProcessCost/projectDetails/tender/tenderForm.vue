<template>
  <!-- 项目 > 招标管理 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible="true" width="500px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="招标编号" prop="tenderNum"><el-input v-model="ruleForm.tenderNum" placeholder="请输入招标编号"></el-input></el-form-item>
        <el-form-item label="合约分类" prop="treatyType">
          <el-select v-model="ruleForm.treatyType" placeholder="请选择">
            <el-option v-for="item in treatyTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="承包方式" prop="undertakeType" v-if="ruleForm.treatyType == 1">
          <el-select v-model="ruleForm.undertakeType" placeholder="请选择">
            <el-option v-for="item in undertakeTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="招标名称" prop="tenderName"><el-input v-model="ruleForm.tenderName" placeholder="请输入招标名称"></el-input></el-form-item>
        <el-form-item label="计划起止时间" prop="planTimes" class="tree-error">
          <el-date-picker v-model="ruleForm.planTimes" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="合同类型" prop="contractType" class="tree-error">
          <el-select v-model="ruleForm.contractType" placeholder="请选择">
            <el-option v-for="item in contractTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购方式" prop="purchaserWay" class="tree-error">
          <el-select v-model="ruleForm.purchaserWay" placeholder="请选择">
            <el-option v-for="item in purchaserWayList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="目标成本(元)" prop="targetCost" class="tree-error">
          <el-input v-model="ruleForm.targetCost" type="textarea" placeholder="请输入目标成本"></el-input>
        </el-form-item>
        <el-form-item label="文件类型" prop="fileType" class="tree-error">
          <el-select v-model="ruleForm.fileType" placeholder="请选择">
            <el-option v-for="item in fileTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="除税金额(元)" prop="exciseMoney" class="tree-error">
          <el-input v-model="ruleForm.exciseMoney" type="textarea" placeholder="请输入除税金额"></el-input>
        </el-form-item>
        <el-form-item label="税率(%)" prop="taxRatio" class="tree-error"><el-input v-model="ruleForm.taxRatio" type="textarea" placeholder="请输入税率"></el-input></el-form-item>
        <el-form-item label="税金(元)" prop="taxMoney" class="tree-error"><el-input v-model="ruleForm.taxMoney" type="textarea" placeholder="请输入税金"></el-input></el-form-item>
        <el-form-item label="含税金额(元)" prop="withTaxMoney" class="tree-error">
          <el-input v-model="ruleForm.withTaxMoney" type="textarea" placeholder="请输入含税金额"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error"><el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>

        <el-form-item label="招标文件" class="upload upload1" prop="materialName">
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
              点击上传
            </el-button>
            <br />
            <el-input placeholder="请上传招标管理文件" v-model="ruleForm.materialName" :disabled="true"></el-input>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitTenderfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
import DefaultProp from './default'; /* 招标管理默认元素  */
export default {
  data() {
    const formdata = new FormData();
    return {
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        tenderId: '',
        tenderNum: '', //招标编号
        treatyType: '', //合约分类
        undertakeType: '', //承包方式
        tenderName: '', //招标名称
        planTimes: [],
        planStartTime: '', //计划开始时间
        planEndTime: '', //计划结束时间
        contractType: '', //合同类型
        purchaserWay: '', //采购方式
        targetCost: '', //目标成本
        fileType: '', //文件类型
        exciseMoney: '', //除税金额（%）
        taxRatio: '', //税率（%）
        taxMoney: '', //税金
        withTaxMoney: '', //含税金额
        remark: '', //备注
        file: '', //上传文件
        materialName: '' //上传文件名称
      },
      //校验规则
      rules: {
        //招标编号
        tenderNum: [
          {
            //必填
            required: true,
            message: '请输入招标编号',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { max: 20, message: '最长20字', trigger: 'blur' }
        ],
        //合约分类
        treatyType: [
          {
            //必填
            required: true,
            message: '请选择合约分类',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //承包方式
        undertakeType: [
          {
            //必填
            required: true,
            message: '请选择承包方式',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //招标名称
        tenderName: [
          {
            //必填
            required: true,
            message: '请输入招标名称',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { max: 20, message: '最长20字', trigger: 'blur' }
        ],
        planTimes: [
          {
            //必填
            required: true,
            message: '请选择计划起止时间',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //合同类型
        contractType: [
          {
            //必填
            required: true,
            message: '请选择合同类型',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //采购方式
        purchaserWay: [
          {
            //必填
            required: true,
            message: '请选择采购方式',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //目标成本
        targetCost: [
          {
            //必填
            required: true,
            message: '请输入目标成本',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //文件类型
        fileType: [
          {
            //必填
            required: true,
            message: '请选择文件类型',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        //除税金额（元）
        exciseMoney: [
          {
            //必填
            required: true,
            message: '请输入除税金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //税率（%）
        taxRatio: [
          {
            //必填
            required: true,
            message: '请输入税率',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { max: 20, message: '最长20字', trigger: 'blur' },
          { validator: this.percentage, trigger: 'blur' }
        ],
        //税金
        taxMoney: [
          {
            //必填
            required: true,
            message: '请输入税金',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //含税金额
        withTaxMoney: [
          {
            //必填
            required: true,
            message: '请输入含税金额',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { validator: this.moneyCheck, trigger: 'blur' }
        ],
        //备注
        remark: [{ max: 100, message: '最长100字', trigger: 'blur' }],
        //上传文件名称
        materialName: [
          {
            //必填
            required: true,
            message: '请上传文件',
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
      fileTypeList: DefaultProp.fileTypeList

      /*---------------选项卡end------------------------*/
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
    //新增招标管理信息先行方法
    addTenderFunc() {
      this.formTitle = '新增';
      this.url = '/wholeProcess/tender/insert/insertWholeProcessTender';
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

    //查询招标管理信息(修改招标管理信息先行方法)
    updateTenderFunc(row) {
      this.formTitle = '修改';
      this.url = '/wholeProcess/tender/modify/updateWholeProcessTender';
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
              tenderNum: dataVO.tenderNum, //招标编号
              treatyType: dataVO.treatyType, //合约分类
              undertakeType: dataVO.undertakeType, //承包方式
              tenderName: dataVO.tenderName, //招标名称
              planTimes: [dataVO.planStartTime, dataVO.planEndTime], //时间选择组件载体
              planStartTime: dataVO.planStartTime, //计划开始时间
              planEndTime: dataVO.planEndTime, //计划结束时间
              contractType: dataVO.contractType, //合同类型
              purchaserWay: dataVO.purchaserWay, //采购方式
              targetCost: dataVO.targetCost, //目标成本
              fileType: dataVO.fileType, //文件类型
              exciseMoney: dataVO.exciseMoney, //除税金额（%）
              taxRatio: dataVO.taxRatio, //税率（%）
              taxMoney: dataVO.taxMoney, //税金
              withTaxMoney: dataVO.withTaxMoney, //含税金额
              remark: dataVO.remark, //备注
              file: '', //上传文件
              materialName: dataVO.tenderFileName //上传文件名称
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
      const date = new Date(prop);
      var year = date.getFullYear();
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
      return year + '-' + month + '-' + day;
    },
    //表单提交
    submitTenderfunc() {
      const vm = this;
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          let planStartTime = vm.formatDate(vm.ruleForm.planTimes[0]);
          let planEndTime = vm.formatDate(vm.ruleForm.planTimes[1]);
          if (vm.ruleForm.treatyType != '1') {
            vm.ruleForm.undertakeType = '';
          }
          vm.formData.set('tenderId', vm.ruleForm.tenderId);
          vm.formData.set('tenderNum', vm.ruleForm.tenderNum);
          vm.formData.set('treatyType', vm.ruleForm.treatyType);
          vm.formData.set('undertakeType', vm.ruleForm.undertakeType);
          vm.formData.set('tenderName', vm.ruleForm.tenderName);
          vm.formData.set('planStartTime', planStartTime);
          vm.formData.set('planEndTime', planEndTime);
          vm.formData.set('projectId', vm.projectId);
          vm.formData.set('contractType', vm.ruleForm.contractType);
          vm.formData.set('purchaserWay', vm.ruleForm.purchaserWay);
          vm.formData.set('targetCost', vm.ruleForm.targetCost);
          vm.formData.set('fileType', vm.ruleForm.fileType);
          vm.formData.set('exciseMoney', vm.ruleForm.exciseMoney);
          vm.formData.set('taxRatio', vm.ruleForm.taxRatio);
          vm.formData.set('taxMoney', vm.ruleForm.taxMoney);
          vm.formData.set('withTaxMoney', vm.ruleForm.withTaxMoney);
          vm.formData.set('remark', vm.ruleForm.remark);
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
