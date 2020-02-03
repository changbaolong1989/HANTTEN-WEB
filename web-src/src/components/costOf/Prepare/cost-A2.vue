<template>
  <div class="cost-A2">
    <div class="matterForm">
      <el-row>
        <el-col :span="18">
          <div>工程咨询业务备案表</div>
        </el-col>
        <el-col :span="6" class="item-tab-chuang" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag != "1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
          <div @click="redact">
            <span class="linear el-icon-edit"></span>
            编辑
          </div>
        </el-col>
      </el-row>
      <div class="matterTable">
        <el-row>
          <el-col :span="6">
            <div>合同编号 :</div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.contractNum}}</div>
          </el-col>
          <el-col :span="6">
            <div>合同签订时间 :</div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.contractDate}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目名称 :</div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.projectName}}</div>
          </el-col>
          <el-col :span="6">
            <div>项目类别 :</div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.counselBusinessType}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div>成果文件编号 :</div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.reportNum}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目委托单位 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.trusterName}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目概况 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.projectCondition}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>发包人 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.employer}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目中标人 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.bidPerson}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>计划完成时间 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.planStartDate}} - {{tableData.planEndDate}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>实际完成时间 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.actualStartDate}} - {{tableData.actualEndDate}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>延迟原因 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.delayReason}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>咨询主要结论 :</div>
          </el-col>
          <el-col :span="18">
            <div>{{tableData.mainResult}}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div>签名</div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div>时间</div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>咨询人员 :</div>
          </el-col>
          <el-col :span="6">
            <div><img slot="reference" :src="tableData.applyUserSignPath" alt="" style="max-height: 50px;max-width: 130px"></div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.applyTime}}</div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div>项目负责人 :</div>
          </el-col>
          <el-col :span="6">
            <div><img slot="reference" :src="tableData.createUserSignPath" alt="" style="max-height: 50px;max-width: 130px"></div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
          <el-col :span="6">
            <div>{{tableData.applyTime}}</div>
          </el-col>
          <el-col :span="3">
            <div></div>
          </el-col>
        </el-row>

      </div>
    </div>

    <!-- 修改弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="500px" top="20vh" transition="fade-in-linear" center @close="handClose('ruleForm')" :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="项目概况" prop="copies">
          <el-input type="textarea" :rows="3" placeholder="请输入项目概况" v-model="ruleForm.projectCondition">
          </el-input>
        </el-form-item>
        <el-form-item label="发包人" prop="contract">
          <el-input type="text" placeholder="请输入发包人" v-model="ruleForm.employer">
          </el-input>
        </el-form-item>
        <el-form-item label="项目中标人" prop="acceptance">
          <el-input type="text" placeholder="请输入项目中标人" v-model="ruleForm.bidPerson">
          </el-input>
        </el-form-item>
        <el-form-item label="计划完成时间" prop="planDateArr">
          <el-date-picker v-model="ruleForm.planDateArr" type="daterange" value-format="yyyy-MM-dd" range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="延迟原因" prop="delay">
          <el-input type="textarea" :rows="3" placeholder="请输入延迟原因" v-model="ruleForm.delayReason">
          </el-input>
        </el-form-item>
        <el-form-item label="咨询主要结论" prop="conclusion">
          <el-input type="textarea" :rows="3" placeholder="请输入咨询主要结论" v-model="ruleForm.mainResult">
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
    props: ['transmittedData'],
    data() {
      return {

        tableData: {},
        centerDialogVisible: false,
        ruleForm: {
          projectCondition:"",
          employer:"",
          bidPerson:"",
          planDateArr: "",
          delayReason:"",
          mainResult:"",
          relationId:"",
          taskId:""
        },
        rules: {
          projectCondition: [{
            required: true,
            message: '项目概况',
            trigger: 'blur'
          }],
          employer: [{
            required: true,
            message: '请输入发包人',
            trigger: 'blur'
          }],
          bidPerson: [{
            required: true,
            message: '请输入项目中标人',
            trigger: 'blur'
          }],
          planEndDate: [{
            required: true,
            message: '请输入计划完成时间',
            trigger: 'blur'
          }],
          delayReason: [{
            required: true,
            message: '请输入延迟原因',
            trigger: 'blur'
          }],
          mainResult: [{
            required: true,
            message: '请输入咨询主要结论',
            trigger: 'blur'
          }],
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
      //查询编制成果文件-A2信息
      getCompileResultsFile(){
        const vm = this;
        console.log(vm.transmittedData.relationId);
        this.apiAddress('/task/query/searchTaskCompileResultsFileA2',{
          "relationId" : vm.transmittedData.relationId
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            vm.tableData = res.data.dataVO;
            //设置建设规模
            vm.ruleForm.projectCondition = res.data.dataVO.projectCondition;
            //设置咨询内容与重点
            vm.ruleForm.employer = res.data.dataVO.employer;
            //设置组长
            vm.ruleForm.bidPerson = res.data.dataVO.bidPerson;
            //设置计划时间数组
            vm.ruleForm.planDateArr = res.data.dataVO.planDateArr;
            //设置咨询业务小组分工
            vm.ruleForm.delayReason = res.data.dataVO.delayReason;
            //设置咨询业务小组分工
            vm.ruleForm.mainResult = res.data.dataVO.mainResult;
            //设置主键
            vm.ruleForm.relationId = res.data.dataVO.relationId;
            //设置任务ID
            vm.ruleForm.taskId = res.data.dataVO.taskId;
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        })
      },
      submitForm(formName) {
        const vm = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							/* 新增数据 */
							vm.$axios.post('/task/add/addTaskCompileResultsFileA2', JSON.stringify({
								// 项目概况
								"projectCondition":vm.ruleForm.projectCondition,
								// 发包人
								"employer":vm.ruleForm.employer,
								// 项目中标人
								"bidPerson":vm.ruleForm.bidPerson,
								// 计划完成时间
								"planDateArr":vm.ruleForm.planDateArr,
                // 延迟原因
								"delayReason":vm.ruleForm.delayReason,
                // 咨询主要结论
								"mainResult":vm.ruleForm.mainResult,
                // 任务ID
								"taskId":vm.ruleForm.taskId,
                // 设置主键
								"relationId":vm.ruleForm.relationId
							}), vm.config)
							.then(function(response) {

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if(response.data.status == "success"){

									// 隐藏编辑弹窗
									vm.centerDialogVisible = false;
									// 清空上次添加的表单内容
									vm.$refs[formName].resetFields();
                  // 查询拟定工作计划信息
                  vm.getCompileResultsFile();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								}else{
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}

							}).catch(function(error) {
								vm.$message({
									type: 'error',
									message: error,
									center: true
								});
							});

						}).catch(() => {});
          } else {
            this.stateDialog("error", "验证失败")
            return false;
          }
        });
      }
    },
    mounted() {
        let vm = this;
        // 查询编制成果文件-A2信息
        this.getCompileResultsFile();
    }
  }
</script>

<style lang="scss">
  .cost-A2 {
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
