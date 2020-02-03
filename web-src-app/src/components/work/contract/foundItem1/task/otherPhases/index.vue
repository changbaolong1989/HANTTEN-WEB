<template>
  <!-- 其他阶段主体-->
  <div class="index">
    <ul class="worklist">
      <li>
        <span>工作步骤说明:</span>
        <span>{{ currentTransmitData.periodDesc }}</span>
      </li>
    </ul>
    <!-- 收集资料A4 -->
    <collecting v-if="currentTransmitData.pageContent == 'A4'" :transmittedData="currentTransmitData"></collecting>
    <!-- 拟定工作计划A5 -->
    <work-plan v-if="currentTransmitData.pageContent == 'A5'" :transmittedData="currentTransmitData" :projectName="content.contractNum"></work-plan>
    <!-- 招标部A6 -->
    <modify
      v-if="currentTransmitData.pageContent == 'A6' && currentTransmitData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'"
      :transmittedData="currentTransmitData"
    ></modify>
    <!-- 造价部A6 -->
    <modifyCost
      v-if="currentTransmitData.pageContent == 'A6' && currentTransmitData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'"
      :transmittedData="currentTransmitData"
    ></modifyCost>
    <!-- 造价部A8 -->
    <siteVisit
      v-if="currentTransmitData.pageContent == 'A8' && currentTransmitData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'"
      :transmittedData="currentTransmitData"
    ></siteVisit>
    <!-- 招标部A7 -->
    <tendering v-if="currentTransmitData.pageContent == 'A7'" :transmittedData="currentTransmitData"></tendering>
    <!-- 招标部A8 -->
    <tendering-copy
      v-if="currentTransmitData.pageContent == 'A8' && currentTransmitData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'"
      :transmittedData="currentTransmitData"
    ></tendering-copy>
    <!-- 专家意见表 -->
    <expertOpinion v-if="currentTransmitData.periodType == '6'" :data="rxpertOpinionListForm"></expertOpinion>
    <!-- 磋商文件确认文件意见-->
    <negotiant v-if="currentTransmitData.periodType == '7'" :data="consultationDocumentConfirmationDocumentOpinionForm"></negotiant>
    <!-- 未成交通知书-->
    <unsettled v-if="currentTransmitData.periodType == '8'" :data="unfilledNoticeForm"></unsettled>
  </div>
</template>

<script>
import collecting from '../../../common/collecting/index.vue'; /* 收集资料 √A4*/
import workPlan from '../../../common/workPlan/index.vue'; /* 拟定工作计划√A5 */
import modify from '../../../common/modify/index-call.vue'; /* 招标部A6*/
import modifyCost from '../../../common/modify/index-cost.vue'; /* 造价部A6*/
import siteVisit from '../../../common/siteVisit/index.vue'; /*造价部A8 */
import tenderingA7 from '../../../common/tenderingA7/index.vue'; /*招标部A7 */
import tenderingA8 from '../../../common/tenderingA8/index.vue'; /*招标部A8 */
import expertOpinion from '../../../common/expertOpinion/index.vue'; /*专家意见表 */
import negotiant from '../../../common/negotiant/index.vue'; /*磋商文件确认文件意见 */
import unsettled from '../../../common/unsettled/index.vue'; /*未成交通知书 */
export default {
  data() {
    return {
      consultationDocumentConfirmationDocumentOpinionForm:{} /*磋商文件确认文件意见  */,
      rxpertOpinionListForm:{} /* 专家意见表 */,
      unfilledNoticeForm: {} ,/*, 未成交通知书*/
      content: {
        contractNum: '',
        fileNumber: '',
        projectName: ''
      },
    };
  },
  props: ['currentTransmitData'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
        this.init();
      },
      immeditate: true
    }
  },
  components: {
    collecting: collecting,
    'work-plan': workPlan,
    modify: modify,
    modifyCost: modifyCost,
    siteVisit: siteVisit,
    tendering: tenderingA7,
    'tendering-copy': tenderingA8,
    expertOpinion: expertOpinion,
    negotiant: negotiant,
    unsettled: unsettled
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      /* 请求中间内容数据*/
      this.requestPost('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
        relationId: this.currentTransmitData.relationId
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
      if (this.currentTransmitData.periodType == '6') {
        // 专家论证意见表详情
        this.searchExpertOpinionRelationDetail();
      } else if (this.currentTransmitData.periodType == '7') {
        // 招标文件确认意见表和任务的关联表详情
        this.searchFileConfirmOpinionRelationDetail();
      } else if (this.currentTransmitData.periodType == '8') {
        // 落标通知书和任务关联表详情
        this.searchFailTenderNoticeRelationInfoDetail();
      }else if (this.currentTransmitData.periodType == '9') {
        // 开标、评标信息详情
        this.searchOpenBidInfoDetail();
      }
    },
    /**
     * @method 查询专家论证意见表详情
     */
    searchExpertOpinionRelationDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchExpertOpinionRelationDetail', { taskId: vm.currentTransmitData.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.rxpertOpinionListForm = {
              form: res.data.dataVO
            };
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    /**
     * @method 查询招标文件确认意见表和任务的关联表详情
     */
    searchFileConfirmOpinionRelationDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchFileConfirmOpinionRelationDetail', { taskId: vm.currentTransmitData.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.consultationDocumentConfirmationDocumentOpinionForm = {
              form: res.data.dataVO
            };
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    /**
     * @method 查询落标通知书和任务关联表详情
     */
    searchFailTenderNoticeRelationInfoDetail() {
      let vm = this;
      vm.requestPost('/task/query/searchFailTenderNoticeRelationInfoDetail', { taskId: vm.currentTransmitData.taskId })
        .then(res => {
          if (res.data.status == 'success') {
            // 赋值
            vm.unfilledNoticeForm = {
              form: res.data.dataVO
            };
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          vm.Toast(err);
        });
    },
    /**
     * @method 查询开标、评标详情
     */
    searchOpenBidInfoDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchOpenBidInfoDetail', {"taskId":vm.currentTransmitData.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.openBidForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    }
  }
};
</script>

<style lang="scss" scoped="scoped">

 .worklist{
   background-color: #fff!important;
 }
</style>
