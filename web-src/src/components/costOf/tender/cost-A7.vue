<template>
  <div class="cost-A7 matterForm">
    <el-row>
      <el-col :span="18">
        <div>
          <p>招标工作进度计划（勘察、设计）</p>
        </div>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" v-if='transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
        <div @click="redact">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="position: absolute;height: 100%;">
        <el-table :data="[]" style="width: 100%;" class="table--empty" border v-if="form.projectName">
          <el-table-column prop="date" label="项目名称：" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="date" :label="form.projectName" width="200" :resizable="false"></el-table-column>
          <el-table-column prop="name" label="资料最后上传时间：" width="200" :resizable="false"></el-table-column>
          <el-table-column prop="address" :label="(form.startDate || '') + ' - ' + (form.endDate || '')" :resizable="false"></el-table-column>
        </el-table>
        <el-table :data="tableData" style="width: 100%" class="table--empty" border :span-method="spanMethod">
          <el-table-column prop="tenderTypeContent" label="招标类型" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="sortNum" label="序号" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="workflowName" label="工作流程" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="startDate" label="节点" width="200" :resizable="false">
            <template slot-scope="scope">
              {{ scope.row.startDate }} - {{ scope.row.endDate }}
            </template>
          </el-table-column>
          <el-table-column prop="days" label="时间" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="involvedDepartment" label="涉及部门" width="100" :resizable="false"></el-table-column>
          <el-table-column prop="remark" label="备注" :resizable="false"></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 修改弹窗 -->
    <el-dialog title="编辑" :visible.sync="centerDialogVisible" width="700px" top="30vh" transition="fade-in-linear"
      center @close="handClose('ruleForm')" :close-on-click-modal="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm" label-position="top">
        <el-form-item label="资料最后上传时间" prop="timeArr">
          <el-date-picker v-model="ruleForm.timeArr" type="daterange" value-format="yyyy-MM-dd" range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-row class="add">
          <el-col :span="12">
            招标内容
            <el-button type="primary" class="linear" icon="el-icon-plus" @click="addAform"></el-button>
          </el-col>
        </el-row>
        <div v-for="(item,index) in ruleForm.opinionList" :key="index" class="addform">
          <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(item,index)"></el-button>
          <el-form-item label="招标类型" :prop="'opinionList['+index+'].tenderTypeContent'" :rules="[{required: true,message: '请输入招标类型',trigger: 'blur'}]">
            <el-input v-model="item.tenderTypeContent" placeholder="请输入招标类型"></el-input>
          </el-form-item>
          <el-row class="add addtwo">
            <el-col :span="12">
              工作流程及信息
              <el-button type="primary" class="linear" icon="el-icon-plus" @click="addAformTwo(index)"></el-button>
            </el-col>
          </el-row>
          <div v-for="(loop,i) in item.flowList" :key="i" class="addform addformtwo">
            <el-button type="text" icon="el-icon-close" class="colseform" @click="colseFormTwo(loop,i,index)"></el-button>
            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="工作流程" :prop="'opinionList['+index+'].flowList['+i+'].workflow'" :rules="[{required: true,message: '请选择工作流程',trigger: 'change'}]">
                  <el-select v-model="loop.workflow" placeholder="请选择工作流程">
                    <el-option :label="j.name" :value="j.id" v-for="j in flowpathlist" :key="j.id">{{j.name}}</el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="涉及部门" :prop="'opinionList['+index+'].flowList['+i+'].involvedDepartment'" :rules="[{required: true,message: '请输入涉及部门',trigger: 'blur'}]">
                  <el-input v-model="loop.involvedDepartment" placeholder="请输入涉及部门"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="节点" :prop="'opinionList['+index+'].flowList['+i+'].nodeArr'" :rules="[{required: true,message: '请选择节点',trigger: 'blur'}]">
                  <el-date-picker v-model="loop.nodeArr" type="daterange" value-format="yyyy-MM-dd" range-separator="至"
                    start-placeholder="开始日期" end-placeholder="结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="备注" :prop="'opinionList['+index+'].flowList['+i+'].remark'" :rules="[{required: true,message: '请输入备注',trigger: 'blur'}]">
                  <el-input v-model="loop.remark" type="textarea" placeholder="请输入备注"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  var indexa = 1;
  let mergeArr =  [] ;// 合并对象数组(源数据)
  let mergeArrBackup = [];// 合并对象数组备份(会被修改的数据)
  export default {
    props: ['transmittedData'],
    data() {
      return {
        data: {
          relationId: "",
          timeArr: "",
          opinionList: []
        },
        tableData: [],
        form: {},
        centerDialogVisible: false,
        gutter: 40,
        flowpathlist: [{
          name: "发布招标公告/邀请",
          id: "1"
        }, {
          name: "投标报名",
          id: "2"
        }, {
          name: "领取资格预审文件",
          id: "3"
        }, {
          name: "递交资格预审文件",
          id: "4"
        }, {
          name: "资格预审评标抽取专家",
          id: "5"
        }, {
          name: "资格预审评标",
          id: "6"
        }, {
          name: "资格预审评标结果备案",
          id: "7"
        }, {
          name: "招标文件备案",
          id: "8"
        }, {
          name: "领取招标文件",
          id: "9"
        }, {
          name: "编制投标文件",
          id: "10"
        }, {
          name: "抽取评标专家",
          id: "11"
        }, {
          name: "开标、评标",
          id: "12"
        }, {
          name: "评标结果公示",
          id: "13"
        }, {
          name: "发中标通知书",
          id: "14"
        }, {
          name: "签订合同",
          id: "15"
        }, {
          name: "设计出图时间",
          id: "16"
        },
          {
            name: "投标邀请",
            id: "17"
          },
          {
            name: "中标候选人公示",
            id: "18"
          }],
        ruleForm: {
          relationId: "",
          taskId: "",
          timeArr: "",
          opinionList: []
        },
        rules: {
          timeArr: [{
            required: true,
            message: '资料最后上传时间',
            trigger: 'blur'
          }],
        }
      }
    },
    mounted() {
      this.searchList();
    },
    methods: {
      searchList() {
        let vm = this;
        vm.apiAddress("/taskExecute/query/searchA7ZbdlInfo", {
          "relationId": this.transmittedData.relationId,
          "taskId": this.transmittedData.taskId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            if (!vm.tool.isNull(res.data.dataVO)) {
              vm.ruleForm = res.data.dataVO;
              vm.form = Object.assign({}, vm.ruleForm);
              vm.data = Object.assign({}, vm.ruleForm);
              if (!vm.tool.isNull(res.data.dataVO.showList)) {
                vm.tableData = [...res.data.dataVO.showList];
              }
              console.info("tableData", vm.tableData)
            }
            // 合并对象数组每次查询的时候清空
            mergeArr = [];
            // 当前合并对象
            let current = {
              rowSpan: 1,
              /* 合并列数量 */
              id: '' // 合并相同的值
            };
            for (let i = 0, len = vm.tableData.length; i < len; i++) {
              let obj = vm.tableData[i];
              if (current.id == obj.relationId) {
                // 相等的时候,添加合并列个数
                current.rowSpan++;
              } else {
                if (current.rowSpan > 1) mergeArr.push(current);
                // 不相等的时候,重置当前合并对象
                current = {
                  rowSpan: 1,
                  id: obj.relationId
                };
              }
              // 如果是最后一个,则执行添加一次
              if ((i + 1) == len) mergeArr.push(current);
            }
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });

      },
      handClose(index) {
        this.$refs[index].resetFields();
        this.ruleForm.opinionList = [];
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
            this.ruleForm.taskId = this.transmittedData.taskId;
            let formData = this.ruleForm;
            vm.apiAddress('/taskExecute/add/insertA7ZbdlInfo', formData).then(res => {
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
        this.ruleForm.opinionList.push({
          tenderTypeContent: "",
          flowList: []
        })
      },
      colseForm(item, value) {
        this.ruleForm.opinionList.splice(value, 2)
      },
      addAformTwo(i) {
        this.ruleForm.opinionList[i].flowList.push({
          workflow: "",
          nodeArr: "",
          involvedDepartment: "",
          remark: ""
        })
      },
      colseFormTwo(loop, i, index) {
        this.ruleForm.opinionList[index].flowList.splice(i, 1)
      },
      spanMethod({
        row,
        column,
        rowIndex,
        columnIndex
      }) { // 数据表格合并行和列

         let vm = this;
         let returnArr = [1,1];
        if(columnIndex==0){
            // 不知什么原因,导致这个合并会执行两次
            // 所以合并数据开始遍历前,重新赋值
          if(0==rowIndex)mergeArrBackup = vm.tool.deepCopy(mergeArr, vm.tool.deepCopy);
          let arr = vm.tool.deepCopy(mergeArrBackup, vm.tool.deepCopy);
          // 遍历要合并的对象数组
          for (let i = 0,len = arr.length; i < len; i++) {
            let obj = arr[i];
            if(null===obj)returnArr = [0,1];
            // 如果和当前行的relationId相等,则设置当前行的合并行
            if(obj&&obj.id==row.relationId){
               mergeArrBackup.splice(i,1,null);
                return [obj.rowSpan,1];
            }
          }
        }
         return returnArr;
      }
    }
  }
</script>

<style lang="scss">
  @import url('../common/css/form.css');

  .cost-A7 {
    .el-form-item {
      margin-bottom: 0px;
    }

    .add {
      font-size: 12px;
      padding: 10px 0px;

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

    .addtwo {
      border-bottom: 1px solid #dcdfe6;
    }

    .addform {
      position: relative;
      border-bottom: 1px solid #EBEEF5;
      background: #F1F5F8;
      border-radius: 10px;
      padding: 20px 15px;
      margin-bottom: 10px;
    }

    .colseform {
      position: absolute;
      top: 0px;
      right: 10px;
      z-index: 10;
    }

    .el-button--text {
      color: #333333 !important;
    }

    /* 表格数据为空 */
    .table--empty {

      /* 数据为空的提示信息 */
      .el-table__empty-block {
        display: none;
      }

      &.el-table {
        th.is-leaf {
          border-right: 1px solid #EBEEF5 !important;
        }
      }

      &+.table--empty {
        border-top: none;
      }
    }

    .cell{
      font-size: 12px;
      color:#424242;
      line-height: 23px;
    }
  }
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');
  @import url('../common/css/form.css');

  .item-tab-chuang {
    margin: 0;
    text-align: right;
  }
</style>
