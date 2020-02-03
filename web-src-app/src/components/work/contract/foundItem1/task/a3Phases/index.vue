<template>
  <!-- A3阶段主体内容 -->
  <div class="index">
    <ul class="worklist">
      <li>
        <span>工作步骤说明:</span>
        <span>{{ currentTransmitData.periodDesc }}</span>
      </li>
    </ul>
    <A3 v-if="!correctTaskMaterialFlag" :contentList="content" :A3dataList="A3dataList"></A3>
    <div :style="{ height: office.officeHeight }" v-if="office.showOnlyOfficeCompFlag"><office :file="office.taskMaterialInfo" @supportOffice="supportOffice"></office></div>
  </div>
</template>

<script>
import A3 from '../../../common/preparation/resultsFile/A3.vue'; /* A3 */
import office from '../../../../common/vue/office.vue'; /* 批注 */
export default {
  data() {
    return {
      content: {
        /* A3阶段信息 */
        contractNum: '',
        fileNumber: '',
        projectName: ''
      },
      A3dataList: [] /*A3列表数据 */
    };
  },
  props: ['currentTransmitData', 'office', 'correctTaskMaterialFlag'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
        vm.init(vm.currentTransmitData.relationId);
        vm.searchHTTaskHisTaskInst(vm.currentTransmitData.relationId);
      },
      immeditate: true
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
  components: {
    A3,
    office
  },
  created() {
    const vm = this;
    vm.init(vm.currentTransmitData.relationId);
    vm.searchHTTaskHisTaskInst(vm.currentTransmitData.relationId);
  },
  methods: {
    init(relationId) {
      /* 请求中间内容数据*/
      this.requestPost('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.content = res.data.dataVO;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //查询审核意见列表
    searchHTTaskHisTaskInst(relationId) {
      let vm = this;
      //查询审核意见列表
      vm.requestPost('/task/query/searchHTTaskHisTaskInst', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            /*------------------------------格式转换START---------------------------------*/
            let dataList = res.data.dataList;

            //for循环遍历更改属性名
            for (var i = 0; i < dataList.length; i++) {
              dataList[i].title = vm.getStepName(dataList[i].taskKey);

              //将属性名htTaskPeriodVOList转换成属性名children
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htTaskPeriodVOList/g, 'children'));

              // 获取转换格式后的children
              let children = dataList[i].children;

              // 循环遍历历史列表
              for (var j = 0; j < children.length; j++) {
                //将属性名approveTime转换成属性名time
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, 'time'));
                //将属性名approveUserName转换成属性名name
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, 'name'));
                //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g, 'persions'));
                //将属性名rejectReason转换成属性名opinion
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, 'opinion'));

                let persions = vm.groupBy(children[j].persions, function(item) {
                  return [item.userId];
                });

                for (var k = 0; k < persions.length; k++) {
                  persions[k].name = persions[k].dataList[0].userName;
                  //将属性名dataList转换成属性名errorList
                  persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, 'errorList'));
                  let errorList = persions[k].errorList;
                  for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, 'type'));
                    //将属性名questionNum转换成属性名num
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, 'num'));
                  }
                  persions[k].errorList = errorList;
                }
                children[j].persions = persions;
              }
            }

            /*---------------------------------格式转换END-------------------------------------*/

            // 把转换后的dataList赋值给opinionList
            vm.A3dataList = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * @method 查看是否支持onlyOffice信息显示
     */
    supportOffice(val) {
      let vm = this;
      vm.office.officeHeight = val ? '700px' : '30px';
    }
  }
};
</script>
