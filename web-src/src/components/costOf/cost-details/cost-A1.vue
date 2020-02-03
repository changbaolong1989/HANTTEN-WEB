<template>
  <div class="cost-A1">
    <div class="matterForm">
      <el-row>
        <el-col :span="18">
          <div>成果文件流程控制表</div>
        </el-col>
        <el-col :span="6" class="item-tab-chuang">
          <div @click="redact">
            <span class="linear el-icon-edit"></span>
            编辑
          </div>
        </el-col>
      </el-row>
      <div class="matterTable">
        <table class="align-center">
          <tbody>
            <tr>
              <td>
                <div class="cell">合同编号：</div>
              </td>
              <td colspan="5">
                <div class="cell"></div>
              </td>
              <td>
                <div class="cell">项目类别：</div>
              </td>
              <td colspan="2">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell">项目名称：</div>
              </td>
              <td colspan="5">
                <div class="cell"></div>
              </td>
              <td>
                <div class="cell">成果文件编号：</div>
              </td>
              <td colspan="2">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell"></div>
              </td>
              <td colspan="5">
                <div class="cell"></div>
              </td>
              <td>
                <div class="cell">成果文件份数：</div>
              </td>
              <td colspan="2">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="cell"></div>
              </td>
              <td colspan="5">
                <div class="cell"></div>
              </td>
              <td>
                <div class="cell"></div>
              </td>
              <td colspan="2">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell"></div>
              </td>
              <td colspan="2">
                <div class="cell">签    名</div>
              </td>
              <td>
                <div class="cell"></div>
              </td>
              <td colspan="2">
                <div class="cell">时   间</div>
              </td>
              <td>
                <div class="cell"></div>
              </td>
              <td colspan="2">
                <div class="cell">备    注</div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">编 制 人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">审 核 人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">质量控制人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">审 定 人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">总经理签发:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">存 档 人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">存档审核人:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="cell">存档延迟原因（部门经理签字确认）:</div>
              </td>
              <td colspan="8">
                <div class="cell"></div>
              </td>
            </tr>
          </tbody>
        </table>

      </div>
      <div class="matter-label">
        <p>注：1.本表与工作底稿一并流转，逐级复核并签字。</p>
        <p>2.必须随本表流转的工作底稿包括工程造价预算审核意见、结算审核报告、报审资料、工程清单控制价、合同及进度款审核意见、招标代理成果文件。</p>
        <p>3.每一步骤执行人必须在认真执行各项程序后签字。</p>
        <p> 4.存档时间规定：造价部为项目完成后10个工作日；招标代理部为项目完成后15个工作日；项目管理部为项目完成后20个工作日。</p>
        <p>5.全部流转完毕后，随表附工作底稿交质量控制部审核，由行政部归档。</p>
      </div>
    </div>

    <!-- 修改弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="500px" top="20vh" transition="fade-in-linear" center @close="handClose('ruleForm')"
      :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="成果文件编号" prop="tenderTaskName">
          <el-input type="text" placeholder="请输入成果文件编号" v-model="ruleForm.tenderTaskName">
          </el-input>
        </el-form-item>
        <el-form-item label="成果文件份数" prop="copies">
          <el-input type="text" placeholder="请输入成果文件份数" v-model="ruleForm.copies">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        centerDialogVisible: false,
        ruleForm: {
          tenderTaskName: "",
          copies: "",
        },
        rules: {
          tenderTaskName: [{
            required: true,
            message: '请输入成果文件编号',
            trigger: 'blur'
          }],
          copies: [{
            required: true,
            message: '请输入成果文件份数',
            trigger: 'blur'
          }]
        }

      }
    },
    methods: {
      handClose(index) {
        this.$refs[index].resetFields();
      },
      redact() {
        this.centerDialogVisible = true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.centerDialogVisible = false;
            this.stateDialog("success", "提交成功")
          } else {
            this.stateDialog("error", "验证失败")
            return false;
          }
        });
      }
    }
  }
</script>

<style lang="scss">
  .cost-A1 {
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
<style scoped="scoped" lang="scss">
  @import url('../common/css/tableScoped.css');
</style>
