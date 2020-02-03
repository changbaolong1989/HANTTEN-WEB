<template>
  <div class="cost-A3">
    <cost-A3 v-if="A3Flg" :projectName='projectName' :contractNum='contractNum' :outcomeDocNo="outcomeDocNo" :tableListData='tableListData'></cost-A3>
    <!-- 修改弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="500px" top="20vh" transition="fade-in-linear"
      center @close="handClose('ruleForm')" :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="成果文件编号" prop="tenderTaskName">
          <el-input type="text" placeholder="请输入成果文件编号" v-model="ruleForm.tenderTaskName">
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
  import A3 from '../../work/common/components/a3'
  export default {
    props: ['transmittedData'],
    data() {
      return {
        centerDialogVisible: false,
        ruleForm: {
          tenderTaskName: "",
        },
        rules: {
          tenderTaskName: [{
            required: true,
            message: '请输入成果文件编号',
            trigger: 'blur'
          }],
        },
        // 默认不显示A3组件
        A3Flg:false,
        // 审核历史记录
        tableListData: [],
        // 项目名称
        projectName:"",
        // 合同编号
        contractNum:"",
        // 成果文件编号
        outcomeDocNo:"",
        // 传过来的参数
        currentTransmittedData:this.transmittedData,
      }
    },
    components: {
      "cost-A3": A3
    },
    mounted() {
      // 如果是A3阶段，则查询审核历史记录列表，并显示A3组件
      if(this.currentTransmittedData.pageContent.indexOf('A3') > -1){
        // 隐藏A3组件
        this.A3Flg = false;
        // 根据任务阶段关联id查询审核意见列表
        this.searchHTTaskHisTaskInst(this.currentTransmittedData.relationId);
        // 根据任务阶段关联id查询项目编号合同编号成果文件编号
        this.searchProjectNumContractNumFileNumberByTaskPeriodId(this.currentTransmittedData.relationId);
      }else {
        // 隐藏A3组件
        this.A3Flg = false;
      }
    },
    methods: {
      // 根据任务阶段关联id查询项目编号合同编号成果文件编号
      searchProjectNumContractNumFileNumberByTaskPeriodId(relationId){
        this.apiAddress('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
          relationId: relationId,
        }).then(
          res => {
          if (res.data.status == 'failure') {
          this.stateDialog('error', res.data.message)
        } else {
          // 项目名称
          this.projectName = res.data.dataVO.projectName;
          // 合同编号
          this.contractNum = res.data.dataVO.contractNum;
          // 成果文件编号
          this.outcomeDocNo = res.data.dataVO.reportNum;
        }
      }).catch(err => {
          this.stateDialog('error', err)
      });
      },
      //查询审核意见列表
      searchHTTaskHisTaskInst(relationId){

        let vm = this;
        //查询审核意见列表
        vm.apiAddress('/task/query/searchHTTaskHisTaskInst', {
          relationId: relationId,
        }).then(
          res => {
          if (res.data.status == 'failure') {
          this.stateDialog('error', res.data.message)
        } else {

          /*------------------------------格式转换START---------------------------------*/

          let dataList = res.data.dataList;

          //for循环遍历更改属性名
          for (var i = 0 ; i < dataList.length ; i++) {

            dataList[i].title = vm.getStepName(dataList[i].taskKey)

            //将属性名htTaskPeriodVOList转换成属性名children
            dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htTaskPeriodVOList/g,"children"));

            // 获取转换格式后的children
            let children = dataList[i].children;

            // 循环遍历历史列表
            for (var j = 0 ; j < children.length ; j++) {
              //将属性名approveTime转换成属性名time
              children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g,"time"));
              //将属性名approveUserName转换成属性名name
              children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g,"name"));
              //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
              children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,"persions"));
              //将属性名rejectReason转换成属性名opinion
              children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g,"opinion"));

              let persions = vm.groupBy(children[j].persions,function(item){
                return[item.userId]
              })

              for (var k = 0 ; k < persions.length ; k++) {
                persions[k].name = persions[k].dataList[0].userName
                //将属性名dataList转换成属性名errorList
                persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g,"errorList"));
                let errorList = persions[k].errorList;
                for (var l = errorList.length - 1; l >= 0; l--) {
                  //将属性名questionName转换成属性名type
                  errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g,"type"));
                  //将属性名questionNum转换成属性名num
                  errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g,"num"));
                }
                persions[k].errorList = errorList;
              }
              children[j].persions = persions;
            }
          }

          /*---------------------------------格式转换END-------------------------------------*/

          // 把转换后的dataList赋值给opinionList
          vm.tableListData = dataList;

          // 如果是A3阶段，则显示A3组件
          if(this.currentTransmittedData.pageContent.indexOf('A3') > -1){
            this.A3Flg = true;
          }

        }
      }).catch(err => {
          this.stateDialog('error', err)
      });
      },
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
  .cost-A3 {
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
