<template>
  <div class="cost-A7">
    <div class="matterForm">
      <el-row>
        <el-col :span="18">
          <div>工程造价指标分析表</div>
        </el-col>
        <el-col :span="6" class="item-tab-chuang" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
          <div @click="redact">
            <span class="linear el-icon-edit"></span>
            编辑
          </div>
        </el-col>
      </el-row>
      <div class="matterTable">
        <el-row>
          <el-col :span="6">
            <div>咨询合同编号 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.contractNum}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目名称 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.projectName}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>工程类别 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.engineeingTypeName}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>合同价格(元):</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.contractAmount}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>结算价格(元):</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.settlePrice}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>结算价超出合同价的原因（若有） :</div>
          </el-col>
          <el-col :span="18">
            <div>{{form.settleReason}}</div>
          </el-col>
        </el-row>
      </div>
    </div>

    <div class="matterForm">
      <el-row>
        <el-col :span="18">
          <div>单位工程造价指标</div>
        </el-col>
      </el-row>
      <div class="matterTable">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="sortNum" label="序号" align="center"></el-table-column>
          <el-table-column prop="unitName" label="单位工程名称" align="center"></el-table-column>
          <el-table-column prop="constructScale" label="建设规模（㎡/m）" align="center"></el-table-column>
          <el-table-column prop="constructCost" label="造价（元）" align="center"></el-table-column>
          <el-table-column prop="unitCost" label="单位造价（元/㎡）" align="center"></el-table-column>
          <el-table-column prop="costRatio" label="造价比例（%）" align="center"></el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 编辑弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="700px" top="20vh" transition="fade-in-linear" center @close="handClose('ruleForm','unitProjectList')"
      :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="工程类别" prop="engineeingTypeArr">
          <el-checkbox-group v-model="ruleForm.engineeingTypeArr">
            <el-checkbox :label="item.id" v-for="item in checkList" :key="item.id">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="合同价格（元）" prop="contractAmount">
          <el-input type="text" placeholder="请输入合同价格" v-model="ruleForm.contractAmount">
          </el-input>
        </el-form-item>
        <el-form-item label="结算价格（元）" prop="settlePrice">
          <el-input type="text" placeholder="请输入结算价格" v-model="ruleForm.settlePrice">
          </el-input>
        </el-form-item>
        <el-form-item label="结算价超出合同价的原因（若有）" prop="settleReason" class="cause">
          <el-input type="textarea" :rows="3" placeholder="请输入结算价超出合同价的原因（若有）" v-model="ruleForm.settleReason">
          </el-input>
        </el-form-item>
        <el-row class="add">
          <el-col :span="12">
            <span>单位工程造价指标</span>
            <el-button type="primary" class="linear" icon="el-icon-plus" @click="addAform"></el-button>
          </el-col>
        </el-row>
        <div v-for="(item,value) in ruleForm.unitProjectList" :key="value" class="demo-ruleForm addruleForm">
          <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(item,value)"></el-button>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="单位工程名称" :prop="'unitProjectList['+value+'].unitName'" :rules="[{ required: true, message: '请输入单位工程名称', trigger: 'blur' }]">
                <el-input type="text" placeholder="请输入单位工程名称" v-model="item.unitName">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建设规模（㎡/m）" :prop="'unitProjectList['+value+'].constructScale'" :rules="[{ required: true, message: '请输入建设规模（㎡/m）', trigger: 'blur' }]">
                <el-input type="text" placeholder="请输入建设规模（㎡/m）" v-model="item.constructScale">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="造价（元）" :prop="'unitProjectList['+value+'].constructCost'" :rules="[{ required: true, message: '请输入造价（元）', trigger: 'blur' }]">
                <el-input type="text" placeholder="请输入造价（元）" v-model="item.constructCost">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="单位造价（元/㎡）" :prop="'unitProjectList['+value+'].unitCost'" :rules="[{ required: true, message: '请输入单位造价（元/㎡）', trigger: 'blur' }]">
                <el-input type="text" placeholder="请输入单位造价（元/㎡）" v-model="item.unitCost">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="造价比例（%）" :prop="'unitProjectList['+value+'].costRatio'" :rules="[{ required: true, message: '请输入造价比例（%）', trigger: 'blur' }]">
                <el-input type="text" placeholder="请输入造价比例（%）" v-model="item.costRatio">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>

  export default {
    props: ['transmittedData'],
    data() {
      return {
        data: {
          relationId: "",
          engineeingTypeArr: [],
          contractAmount: "",
          settlePrice: "",
          settleReason: "",
          unitProjectList: []
        },
        centerDialogVisible: false,
        form: {},
        ruleForm: {
          relationId: "",
          engineeingTypeArr: [],
          contractAmount: "",
          settlePrice: "",
          settleReason: "",
          unitProjectList: []
        },
        rules: {
          engineeingTypeArr: [{
            required: true,
            message: '请选择工程类别',
            trigger: 'change'
          }],
          contractAmount: [{
            required: true,
            message: '请输入合同价格',
            trigger: 'blur'
          },
          {
            validator: this.moneyCheck,
            trigger: 'blur'
          }],
          settlePrice: [{
            required: true,
            message: '请输入结算价格',
            trigger: 'blur'
          },
            {
              validator: this.moneyCheck,
              trigger: 'blur'
            }],
          settleReason: [{
            required: true,
            message: '请输入结算价超出合同价的原因（若有）',
            trigger: 'blur'
          }],
        },
        tableData: [],
        checkList: [{
          name: "房建",
          id: "0"
        }, {
          name: "工业建筑",
          id: "1"
        }, {
          name: "公共建筑",
          id: "2"
        }, {
          name: "园林绿化",
          id: "3"
        }, {
          name: "安装",
          id: "4"
        }, {
          name: "市政",
          id: "5"
        }, {
          name: "交通",
          id: "6"
        }, ]
      }
    },
    mounted() {
      this.searchList();
    },
    methods: {
      searchList() {
        let vm = this;
        vm.apiAddress("/taskExecute/query/searchA7ZJInfo", {
          "relationId": vm.transmittedData.relationId,
          "taskId": vm.transmittedData.taskId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            if (!vm.tool.isNull(res.data.dataVO)) {
              vm.ruleForm = res.data.dataVO;
              if (vm.tool.isNull(this.ruleForm.unitProjectList)) {
                vm.ruleForm.unitProjectList = [];
              }
              if (vm.tool.isNull(vm.ruleForm.engineeingTypeArr)) {
                vm.ruleForm.engineeingTypeArr = [];
              }
              vm.form = Object.assign({}, vm.ruleForm);
              vm.data = Object.assign({}, vm.ruleForm);
              if (!vm.tool.isNull(res.data.dataVO.unitProjectList)) {
                vm.tableData = [...res.data.dataVO.unitProjectList];
              }
            }
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      handClose(index) {
        this.$refs[index].resetFields();
        this.ruleForm.unitProjectList = []
      },
      redact() {
        this.ruleForm = Object.assign({}, this.data);
        this.centerDialogVisible = true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let vm = this;
            this.ruleForm.relationId = this.transmittedData.relationId;
            let formData = this.ruleForm;
            this.apiAddress('/taskExecute/add/insertA7ZJInfo', formData).then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.centerDialogVisible = false;
                vm.searchList();
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
              }
            }).catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
          } else {
            return false;
          }
        });
      },
      addAform() {
        this.ruleForm.unitProjectList.push({
          unitName: "",
          constructScale: "",
          constructCost: "",
          unitCost: "",
          costRatio: ""
        })
      },
      colseForm(item, value) {
        this.ruleForm.unitProjectList.splice(value, 1)
      }
    }
  }
</script>

<style lang="scss">
  .cost-A7 {
    .matterForm {
      border-radius: 5px;
      padding: 10px;
      background-color: #edf1f4;
      margin-bottom: 10px;

      .item-tab-chuang {
        text-align: right;
        margin: 0;
        padding-right: 5px;
      }
    }

    .matterTable {
      margin: 10px 0;
      border: 1px solid #EBEEF5;
      background-color: #fff;
      box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);

      .el-row {
        &:first-child {
          .el-col {
            border-top: none;
          }
        }

        .el-col {
          border-right: 1px solid #EBEEF5;
          border-top: 1px solid #EBEEF5;
          text-align: center;
          min-height: 50px;
          line-height: 50px;
          &:last-child {
            border-right: none;
          }
        }
      }
    }

    .matter-label {
      margin: 10px;

      & p:not(:first-child) {
        text-indent: 2em;
      }

      p {
        margin: 10px 0;
      }
    }


    .el-dialog--center {
      .el-dialog__body {
        max-height: 560px;
        overflow: auto;
        padding: 0;
        border-bottom: 1px solid #dcdfe6;

        .el-form {
          padding: 18px 30px;

          .cause {
            label {
              width: 100% !important;
            }
          }

          .el-form-item {
            border-bottom: 1px solid #dcdfe6;
            margin: 0;
            padding: 10px 0;

            &:last-child {
              border-bottom: none;
            }

            label {
              float: none;
              text-align: left;
              color: #424242;
              font-size: 12px;
              float: left;
            }

            .el-form-item__content {
              margin-left: 0 !important;
              clear: both;
              text-align: left;

              .el-input__inner {
                height: 27px;
                line-height: 27px;
                color: #424242;
                font-size: 12px;
                background-color: #f8f9fb;
              }

              textarea {
                height: 57px !important;
              }

              .el-textarea+.el-form-item__error {
                top: 110%;
              }
            }

          }

          .itemlist-dialog-bottom {
            .el-form-item__content {
              clear: none;
            }

            & .el-form-item__error {
              left: 290px;
              top: 10px;

              &::before {
                border-bottom: 5px solid transparent !important;
                border-right: 5px solid #F85B48 !important;
                top: 5px !important;
                left: -10px;
                position: absolute;
              }
            }

            p {
              font-size: 12px;
              color: #424242;
              margin: 15px 0 0;
              text-align: left;
            }

            .addUser {
              position: relative;

              .el-form-item__error {
                position: absolute;
                top: 0 !important;
                left: 120px !important;
              }

              .el-button {
                width: 26px;
                height: 16px !important;
                line-height: 16px !important;
                border-radius: 5px;
                padding: 0 !important;
                border: none;
                margin-left: 20px;
              }
            }

            .el-form-item {
              border: none;
              padding: 0;

              label {
                float: left;
                width: 70px !important;
                text-align: left;
              }

              .addOption {
                margin-left: 70px !important;

                span {
                  color: #B0B0B0;
                  cursor: pointer;
                }
              }

            }
          }
        }

        .addruleForm {
          border-bottom: 1px solid #EBEEF5;
          position: relative;

          label {
            width: 100% !important;
          }

          .colseform {
            position: absolute;
            top: 0;
            right: 0px;
            z-index: 10;
          }

          .el-button--text {
            color: #333333 !important;
          }
        }

        .add {
          font-size: 12px;
          padding: 10px 0px;
          div{
            text-align: left;
          }
          span {
            color: #333333;
            margin-right: 5px;
          }

          p {
            color: #B0B0B0;
            text-align: right;
          }

          .el-button {
            width: 26px;
            height: 16px !important;
            line-height: 16px !important;
            border-radius: 5px;
            padding: 0px !important;
            border: none;

            i {
              font-size: 12px;
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

  }
</style>
