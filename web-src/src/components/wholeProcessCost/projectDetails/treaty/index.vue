<template>
  <!-- 项目 > 合约规划 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg" justify="end">
      <el-col :span="24" class="text-align-right">
        <!-- 新增/修改-->
        <div class="item-tab-chuang" @click="addTreatyBtn()" style="float: right;">
          <span class="el-icon-plus linear"></span>
          创建合约
        </div>
        <div class="item-tab-chuang" style="float: right;" @click="exportBtn()">
          <span class="el-icon-upload2 linear"></span>
          导出
        </div>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%">
        <template slot="empty" class="tabdata-empty">
          <img src="../../../../assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" prop="treatyNum" label="合约编号" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="treatyType" label="合约分类" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{selectGetName(scope.row.treatyType,treatyTypeList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="undertakeType" label="承包方式" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{scope.row.treatyType=='1'?selectGetName(scope.row.undertakeType,undertakeTypeList):'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="treatyName" label="合约名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="treatyContent" label="合约内容及范围" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.treatyContent?'-':scope.row.treatyContent}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="targetCost" label="目标成本（元）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ new Number(scope.row.targetCost) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="contractType" label="合同类型" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{selectGetName(scope.row.contractType,contractTypeList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="contractWay" label="合同确定方式" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{selectGetName(scope.row.contractWay,contractWayList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="purchaserWay" label="采购方式" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{selectGetName(scope.row.purchaserWay,purchaserWayList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="remark" label="备注" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.remark?'-':scope.row.remark}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="modify" @click="updateTreatyBtn(scope.row)">修改</el-button>
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
        <el-form-item label="合约编号" prop="treatyNum"><el-input v-model="ruleForm.treatyNum" placeholder="请输入合约编号"></el-input></el-form-item>
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
        <el-form-item label="合约名称" prop="treatyName"><el-input v-model="ruleForm.treatyName" placeholder="请输入合约名称"></el-input></el-form-item>
        <el-form-item label="目标成本（元）" prop="targetCost" class="tree-error">
          <el-input v-model="ruleForm.targetCost" type="textarea" placeholder="请输入目标成本"></el-input>
        </el-form-item>
        <el-form-item label="合同类型" prop="contractType" class="tree-error">
          <el-select v-model="ruleForm.contractType" placeholder="请选择">
            <el-option v-for="item in contractTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同确定方式" prop="contractWay" class="tree-error">
          <el-select v-model="ruleForm.contractWay" placeholder="请选择">
            <el-option v-for="item in contractWayList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购方式" prop="purchaserWay" class="tree-error">
          <el-select v-model="ruleForm.purchaserWay" placeholder="请选择">
            <el-option v-for="item in purchaserWayList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合约内容及范围" prop="treatyContent" class="tree-error">
          <el-input v-model="ruleForm.treatyContent" type="textarea" placeholder="请输入合约内容及范围"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error"><el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input></el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitTreatyfunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      total: 0, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      tabdata: [], //当前页数据
      projectId: this.$parent.projectId, //项目ID(从父级页面取值)
      ruleForm: {
        //所提交form表单(校验表单)
        treatyId: '', //合约规划ID
        treatyNum: '', //合约编号
        treatyType: '', //合约分类
        undertakeType: '', //承包方式
        treatyName: '', //合约名称
        treatyContent: '', //合约内容及范围
        targetCost: '', //目标成本
        contractType: '', //合同类型
        contractWay: '', //合同确定方式
        purchaserWay: '', //采购方式
        remark: '' //备注
      },
      //校验规则
      rules: {
        treatyNum: [
          {
            //必填
            required: true,
            message: '请选择合约编号',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { max: 20, message: '最长20字', trigger: 'blur' }
        ],
        treatyType: [
          {
            //必填
            required: true,
            message: '请选择合约分类',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        undertakeType: [
          {
            //必填
            required: true,
            message: '请选择承包方式',
            //触发方式:失去焦点
            trigger: 'blur'
          }
        ],
        treatyName: [
          {
            //必填
            required: true,
            message: '请输入合约名称',
            //触发方式:失去焦点
            trigger: 'blur'
          },
          { max: 20, message: '最长20字', trigger: 'blur' }
        ],
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
        contractType: [
          {
            //必填
            required: true,
            message: '请选择合同类型',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        contractWay: [
          {
            //必填
            required: true,
            message: '请选择合同确定方式',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        purchaserWay: [
          {
            //必填
            required: true,
            message: '请选择采购方式',
            //触发方式:失去焦点
            trigger: 'change'
          }
        ],
        treatyContent: [{ max: 100, message: '最长100字', trigger: 'blur' }],
        remark: [{ max: 100, message: '最长100字', trigger: 'blur' }]
      },
      //dialog(弹出层)展示头
      formTitle: '',
      //弹窗显示Flag
      centerDialogVisible: false,
      //表单提交路径
      url: '',

      /*---------------选项卡start------------------------*/
      //合约分类选项
      treatyTypeList: [
        {
          value: '1',
          label: '工程类合约'
        },
        {
          value: '2',
          label: '采购类合约'
        },
        {
          value: '3',
          label: '服务类合约'
        },
        {
          value: '4',
          label: '土地类合约'
        },
        {
          value: '5',
          label: '其他类合约'
        }
      ],

      //承包方式选项
      undertakeTypeList: [
        {
          value: '1',
          label: '施工总承包'
        },
        {
          value: '2',
          label: '发包人发包专业合同'
        },
        {
          value: '3',
          label: '发包人自行发包专业'
        }
      ],

      //合同类型选项
      contractTypeList: [
        {
          value: '1',
          label: '单价合同'
        },
        {
          value: '2',
          label: '总价合同'
        },
        {
          value: '3',
          label: '其他'
        }
      ],

      //合同确定方式选项
      contractWayList: [
        {
          value: '1',
          label: '总承包'
        },
        {
          value: '2',
          label: '指定供货'
        },
        {
          value: '3',
          label: '指定分包'
        },
        {
          value: '4',
          label: '甲供'
        },
        {
          value: '5',
          label: '直接发包'
        },
        {
          value: '6',
          label: '三方协议'
        }
      ],

      //采购方式选项
      purchaserWayList: [
        {
          value: '1',
          label: '公开招标'
        },
        {
          value: '2',
          label: '内部组织招标'
        },
        {
          value: '3',
          label: '联合招标'
        },
        {
          value: '4',
          label: '直接委托'
        },
        {
          value: '5',
          label: '竞争性谈判'
        }
      ]
    };
    /*---------------选项卡end------------------------*/
  },
  //页面初始化加载钩子函数
  mounted() {
    this.currentPage = 1;
    this.pageSize = 10;
    this.searchList(this.currentPage, this.pageSize);
  },
  methods: {
    selectGetName(key,list) {
      return this.$parent.selectGetName(key,list);
    },
    //新增合约规划信息先行方法
    addTreatyBtn() {
      //弹出层展示Flag
      this.centerDialogVisible = true;
      this.formTitle = '新增';
      this.url = '/wholeProcess/treaty/insert/insertWholeProcessTreaty';
    },
    // 导出列表数据
    exportBtn() {
      const vm = this;
      // 导出列表数据
      const elemIF = document.createElement('iframe');
      elemIF.src = '/wholeProcess/treaty/export/exportTreatyData?realFileName=' + "合约规划.xls" + "&projectId=" +  vm.projectId;
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
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

    //查询合约规划信息(修改合约规划信息先行方法)
    updateTreatyBtn(row) {
      this.formTitle = '修改';
      this.url = '/wholeProcess/treaty/modify/updateWholeProcessTreaty';
      let formData = {
        treatyId: row.treatyId
      };
      const vm = this;
      vm.apiAddress('/wholeProcess/treaty/query/searchProjectTreatyPeriodInfo', formData)
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
              //所提交form表单(校验表单)
              treatyId: dataVO.treatyId, //合约规划ID
              treatyNum: dataVO.treatyNum, //合约编号
              treatyType: dataVO.treatyType, //合约分类
              undertakeType: dataVO.undertakeType, //承包方式
              treatyName: dataVO.treatyName, //合约名称
              treatyContent: dataVO.treatyContent, //合约内容及范围
              targetCost: new Number(dataVO.targetCost), //目标成本
              contractType: dataVO.contractType, //合同类型
              contractWay: dataVO.contractWay, //合同确定方式
              purchaserWay: dataVO.purchaserWay, //采购方式
              remark: dataVO.remark //备注
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
      vm.apiAddress('/wholeProcess/treaty/query/searchProjectTreatyPeriodList', formData)
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
    //表单提交
    submitTreatyfunc() {
      this.$refs['ruleForm'].validate(valid => {
        if (valid) {
          const vm = this;
          let formdata = vm.ruleForm;
          formdata.projectId = vm.projectId;
          // 获取列表数据
          vm.apiAddress(this.url, formdata)
            .then(res => {
              vm.centerDialogVisible = false; /* 让弹窗隐藏 */
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
              }
              this.$refs['ruleForm'].resetFields(); /* 清空表单数据 */
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
              this.$refs['ruleForm'].resetFields(); /* 清空表单数据 */
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
