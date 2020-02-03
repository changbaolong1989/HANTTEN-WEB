<template>
  <div class="task" style="height: 100%;">
    <!-- 工作步骤说明 -->
    <div class="title form-block" v-if='!office.showOnlyOfficeCompFlag'>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">工作步骤说明:</p>
        </el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{currentTransmitData.periodDesc}}</p>
        </el-col>
      </el-row>
    </div>
    <!-- A3组件 -->
    <div class="form-block form-block__row--auto-width" style="height: calc(100% - 91px);padding: 0;" v-if="!correctTaskMaterialFlag">
      <a3 :projectName='projectName' :contractNum='contractNum' :outcomeDocNo="outcomeDocNo" :tableListData='tableListData'></a3>
    </div>
    <!-- onlyOffice -->
    <div class="form-block form-block__row--auto-width" style="height: 700px;" v-if="office.showOnlyOfficeCompFlag">
      <office :file="office.taskMaterialInfo"></office>
    </div>
  </div>
</template>

<script>
  import a3 from '../../../../common/components/a3';  /* a3组件 */
  import office from '../../../../common/components/office';/* onlyOffice组件 */
  export default {
    components: {
      'a3': a3,// A3
      'office':office, //onlyOffice
    },
    props: ['currentTransmitData', 'office', 'correctTaskMaterialFlag'],
    data() {
      return {
        projectName: "",// 项目名称
        contractNum: "",// 合同编号
        outcomeDocNo: "",// 成果文件编号
        tableListData: [], /*,A3列表数据 */
        gutter: 40,// 表单项间距
      }
    },
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          let vm = this;
          vm.currentTransmitData = newval;
        },
        immeditate: true,
        deep: true
      },
      office: {
        handler(newval) {
          let vm = this;
          vm.office = newval;
        },
        immediate: true,
        deep: true
      },
      correctTaskMaterialFlag: {
        handler(newval) {
          let vm = this;
          vm.correctTaskMaterialFlag = newval;
        },
        immediate: true,
        deep: true
      }
    },
    created() {
      let vm = this;
      vm.init(vm.currentTransmitData.relationId);
      vm.searchHTTaskHisTaskInst(vm.currentTransmitData.relationId);
    },
    methods: {
      // =========================================== 初始化信息 start===================================================
      // 初始化信息
      init(relationId){
        let vm = this;
        /* 请求中间内容数据*/
        vm.apiAddress('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
          relationId: relationId
        }).then(res => {
          if (res.data.status == 'success') {
            let dataVO = res.data.dataVO;// 数据对象
            vm.projectName = dataVO.projectName;// 项目名称
            vm.contractNum = dataVO.contractNum;// 合同编号
            vm.outcomeDocNo = dataVO.outcomeDocNo;// 成果文件编号
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });

      },
      // =========================================== 初始化信息 end===================================================
      // ============================================查询审核意见列表 start==================================================
      //查询审核意见列表
      searchHTTaskHisTaskInst(relationId){
        let vm = this;
        //查询审核意见列表
        vm.apiAddress('/task/query/searchHTTaskHisTaskInst', {
          relationId: relationId,
        }).then(res => {
            if (res.data.status == 'failure') {
              this.stateDialog('error', res.data.message)
            } else {

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
              // 把转换后的dataList赋值给opinionList
              vm.tableListData = dataList;

            }
          }).catch(err => {
            this.stateDialog('error', err)
        });
      },
      // ============================================查询审核意见列表 end===============================================
    }
  }
</script>
<style>
  @import url('../../../../common/css/other.css');
</style>
