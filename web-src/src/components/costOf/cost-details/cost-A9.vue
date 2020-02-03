<template>
  <div class="cost-A1">
    <div class="matterForm">
      <el-row>
        <el-col :span="18">
          <div>回访记录表</div>
        </el-col>
      </el-row>
      <div class="matterTable">
        <el-row>
          <el-col :span="6">
            <div>项目委托单位 :</div>
          </el-col>
          <el-col :span="6">
            <div>164576466576</div>
          </el-col>
          <el-col :span="6">
            <div>合同编号 :</div>
          </el-col>
          <el-col :span="6">
            <div>我的迷你版项目</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目名称 :</div>
          </el-col>
          <el-col :span="6">
            <div>哈哈哈哈我哪里知道</div>
          </el-col>
          <el-col :span="6">
            <div>服务内容 :</div>
          </el-col>
          <el-col :span="6">
            <div>110120119</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <div>序号</div>
          </el-col>
          <el-col :span="6">
            <div>评价事项</div>
          </el-col>
          <el-col :span="16">
            <div>委托单位意见</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <div>1</div>
          </el-col>
          <el-col :span="6">
            <div>业务水平</div>
          </el-col>
          <el-col :span="16">
            <div class="checkbox">
              <span></span>很满意
            </div>
            <div class="checkbox">
              <span></span>满意
            </div>
            <div class="checkbox">
              <span></span>一般
            </div>
            <div class="checkbox">
              <span></span>较差
            </div>
            <div class="checkbox">
              <span></span>很差
            </div>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="2">
            <div>2</div>
          </el-col>
          <el-col :span="6">
            <div>管理能力</div>
          </el-col>
          <el-col :span="16">
            <div class="checkbox">
              <span></span>很满意
            </div>
            <div class="checkbox">
              <span></span>满意
            </div>
            <div class="checkbox">
              <span></span>一般
            </div>
            <div class="checkbox">
              <span></span>较差
            </div>
            <div class="checkbox">
              <span></span>很差
            </div>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="2">
            <div>3</div>
          </el-col>
          <el-col :span="6">
            <div>管理能力</div>
          </el-col>
          <el-col :span="16">
            <div class="checkbox">
              <span></span>很满意
            </div>
            <div class="checkbox">
              <span></span>满意
            </div>
            <div class="checkbox">
              <span></span>一般
            </div>
            <div class="checkbox">
              <span></span>较差
            </div>
            <div class="checkbox">
              <span></span>很差
            </div>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="2">
            <div>4</div>
          </el-col>
          <el-col :span="6">
            <div>职业道德</div>
          </el-col>
          <el-col :span="16">
            <div class="checkbox">
              <span></span>很满意
            </div>
            <div class="checkbox">
              <span></span>满意
            </div>
            <div class="checkbox">
              <span></span>一般
            </div>
            <div class="checkbox">
              <span></span>较差
            </div>
            <div class="checkbox">
              <span></span>很差
            </div>
          </el-col>
        </el-row>

      </div>
      <div class="matter-label">
        <p>委托方建议：</p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {}
    },
    methods: {}
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

          .checkbox {
            display: inline-block;
            width: 15%;

            span {
              width: 12px;
              height: 12px;
              border: 1px solid #424242;
              display: inline-block;
              margin: 0px 5px;
            }
          }

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
