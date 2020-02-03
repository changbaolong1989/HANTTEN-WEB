<template>
  <!-- 成果模板-->
  <div class="index">
    <form class="projectForm">
      <div class="form-item" @click="changeTemplate">
        <mt-cell title="模板类型">
          <span>{{ Form.contractTemplate }}</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 模板类型弹窗-->
      <mt-popup v-model="popupVisibleTemplate" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>模板类型</p>
          <div class="item" v-for="(item, i) in resultsTemplate">
            <label :for="'template' + i">
              {{ item.label }}
              <input type="radio" :id="'template' + i" v-model="Templateindex" :value="item.value" name="template" @change="Templatechange" :disabled="disabled" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleTemplate = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="surface">
      <prepare-budget v-if="Templateindex == 0" ref="template" :contractNum="contractNum"></prepare-budget>
      <prepare-estimate v-if="Templateindex == 1" ref="template" :contractNum="contractNum"></prepare-estimate>
      <prepare-settlement ref="template" v-if="Templateindex == 2" :contractNum="contractNum"></prepare-settlement>
      <prepare-accounts ref="template" v-if="Templateindex == 3" :contractNum="contractNum"></prepare-accounts>
      <prepare-tenderClearanceReport ref="template" v-if="Templateindex == 4" :contractNum="contractNum"></prepare-tenderClearanceReport>
      <bidding-controlPriceTemplate ref="template" v-if="Templateindex == 5" :contractNum="contractNum"></bidding-controlPriceTemplate>
      <subpackage ref="template" v-if="Templateindex == 6" :contractNum="contractNum"></subpackage>
      <mainContractor ref="template" v-if="Templateindex == 7" :contractNum="contractNum"></mainContractor>
      <budgeting ref="template" v-if="Templateindex == 8" :contractNum="contractNum"></budgeting>
      <budgetAudit ref="template" v-if="Templateindex == 9" :contractNum="contractNum"></budgetAudit>
      <estimateAudit ref="template" v-if="Templateindex == 10" :contractNum="contractNum"></estimateAudit>
      <settlementAudit ref="template" v-if="Templateindex == 11" :contractNum="contractNum"></settlementAudit>
      <budgetAuditing ref="template" v-if="Templateindex == 12" :contractNum="contractNum"></budgetAuditing>
      <reviewTender ref="template" v-if="Templateindex == 13" :contractNum="contractNum"></reviewTender>
      <successfulTenderReview ref="template" v-if="Templateindex == 14" :contractNum="contractNum"></successfulTenderReview>
      <commentsProgressPayment ref="template" v-if="Templateindex == 15" :contractNum="contractNum"></commentsProgressPayment>
      <changeAuditOpinion ref="template" v-if="Templateindex == 16" :contractNum="contractNum"></changeAuditOpinion>
      <budgetReviewComments ref="template" v-if="Templateindex == 17" :contractNum="contractNum"></budgetReviewComments>
      <invitationAgency ref="template" v-if="Templateindex == 18" :contractNum="contractNum" :projectName="projectName"></invitationAgency>
      <writtenBidEvaluationReport ref="template" v-if="Templateindex == 19" :contractNum="contractNum" :projectName="projectName"></writtenBidEvaluationReport>
    </div>
  </div>
</template>

<script>
import prepareBudget from './prepareBudget.vue'; /* 编制概算 */
import prepareEstimate from './prepareEstimate.vue'; /* 编制估算 */
import prepareSettlement from './prepareSettlement.vue'; /* 编制结算 */
import prepareAccounts from './prepareAccounts.vue'; /* 编制决算 */
import prepareTenderClearanceReport from './prepareTenderClearanceReport.vue'; /* 编制清标报告 */
import biddingControlPriceTemplate from './biddingControlPriceTemplate.vue'; /* 招标控制价模板 */
import subpackage from './subpackage.vue'; /* 招标控制价模板 (分包承包)*/
import mainContractor from './mainContractor.vue'; /* 招标控制价模板 (总包)*/
import budgeting from './budgeting.vue'; /* 预算编制模板*/
import budgetAudit from './budgetAudit.vue'; /* 概算审核模板*/
import estimateAudit from './estimateAudit.vue'; /* 估算审核模板*/
import settlementAudit from './settlementAudit.vue'; /* 结算审核模板*/
import budgetAuditing from './budgetAuditing.vue'; /* 预算审核模板*/
import reviewTender from './reviewTender.vue'; /* 合同审核意见（未招标）*/
import successfulTenderReview from './successfulTenderReview.vue'; /* 合同审核意见（招标）*/
import commentsProgressPayment from './commentsProgressPayment.vue'; /* 进度款审核意见--*/
import changeAuditOpinion from './changeAuditOpinion.vue'; /* 变更审核意见*/
import budgetReviewComments from './budgetReviewComments.vue'; /* 预算审核意见*/
import invitationAgency from './invitationAgency.vue'; /* 招标代理*/
import writtenBidEvaluationReport from './writtenBidEvaluationReport.vue'; /* 评标书面报告*/

export default {
  data() {
    return {
      popupVisibleTemplate: false,
      Form: {
        contractTemplate: '编制概算'
      },
      Templateindex: '0',
      currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy),
      contractNum: '',
      resultsTemplate: [
        {
          label: '编制概算',
          value: '0'
        },
        {
          label: '编制估算',
          value: '1'
        },
        {
          label: '编制结算',
          value: '2'
        },
        {
          label: '编制决算',
          value: '3'
        },
        {
          label: '编制清标报告',
          value: '4'
        },
        {
          label: '招标控制价模板',
          value: '5'
        },
        {
          label: '招标清单模板(分包，承包)',
          value: '6'
        },
        {
          label: '招标清单模板(总包)',
          value: '7'
        },
        {
          label: '预算编制模板',
          value: '8'
        },
        {
          label: '概算审核',
          value: '9'
        },
        {
          label: '估算审核',
          value: '10'
        },
        {
          label: '结算审核',
          value: '11'
        },
        {
          label: '预算审核',
          value: '12'
        },
        {
          label: '合同审核意见(未招标)',
          value: '13'
        },
        {
          label: '合同审核意见(招标)',
          value: '14'
        },
        {
          label: '进度款审核意见',
          value: '15'
        },
        {
          label: '变更审核意见',
          value: '16'
        },
        {
          label: '预算审核意见',
          value: '17'
        }
      ]
    };
  },
  components: {
    'prepare-budget': prepareBudget,
    'prepare-estimate': prepareEstimate,
    'prepare-settlement': prepareSettlement,
    'prepare-accounts': prepareAccounts,
    'prepare-tenderClearanceReport': prepareTenderClearanceReport,
    'bidding-controlPriceTemplate': biddingControlPriceTemplate,
    subpackage: subpackage,
    mainContractor: mainContractor,
    budgeting: budgeting,
    budgetAudit: budgetAudit,
    estimateAudit: estimateAudit,
    settlementAudit: settlementAudit,
    budgetAuditing: budgetAuditing,
    reviewTender: reviewTender,
    successfulTenderReview: successfulTenderReview,
    commentsProgressPayment: commentsProgressPayment,
    changeAuditOpinion: changeAuditOpinion,
    budgetReviewComments: budgetReviewComments,
    invitationAgency: invitationAgency,
    writtenBidEvaluationReport: writtenBidEvaluationReport
  },
  props: ['transmittedData', 'disabled'],
  watch: {
    transmittedData: {
      handler() {
        let vm = this;
        vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
        vm.contractNum = vm.currentTransmittedData.contractNum;
        vm.projectName = vm.currentTransmittedData.projectName;
        vm.init();
      },
      immediate: true
    },
    disabled: {
      handler(newval) {
        let vm = this;
        vm.disabled = newval;
        this.initInfo()
      },
      deep: true
    }
  },
  created() {
    const vm = this;
    if (vm.currentTransmittedData.departmentId != 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91') {
      /* 招标 */
      vm.resultsTemplate = [
        {
          label: '招标代理',
          value: '18'
        },
        {
          label: '评标书面报告',
          value: '19'
        }
      ];
      vm.init();
    } else {
      vm.init();
    }
  },
  mounted() {
    const vm = this;
    vm.initInfo();
  },
  methods: {
    init() {
      const vm = this;
      if (this.currentTransmittedData.formworkType !== '') {
        this.Templateindex = this.currentTransmittedData.formworkType;
        console.log('Templateindex', this.Templateindex);
        var item = vm.resultsTemplate.filter(function(i) {
          return i.value == vm.currentTransmittedData.formworkType;
        });
        if (item.length > 0) {
          vm.$set(vm.Form, 'contractTemplate', item[0].label);
        }
      } else {
        if (vm.currentTransmittedData.departmentId != 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91') {
          vm.$set(vm.Form, 'contractTemplate', '招标代理');
          vm.$set(vm, 'Templateindex', '18');
        } else {
          vm.Form.contractTemplate = '编制概算';
          vm.Templateindex = '0';
        }
      }
      if (vm.currentTransmittedData.formworkContent == '') {
        vm.formcontent = {};
      } else {
        vm.formcontent = JSON.parse(vm.currentTransmittedData.formworkContent);
      }
    },
    initInfo() {
      const vm = this;
      if (JSON.stringify(vm.formcontent) !== '{}') {
        vm.$refs.template.templateform = vm.formcontent;
      }
      vm.$refs.template.contractNum = vm.currentTransmittedData.contractNum;
      if (vm.currentTransmittedData.formworkType == '2' || vm.currentTransmittedData.formworkType == '11') {
        vm.$refs.template.startDate = vm.currentTransmittedData.startDate;
        vm.$refs.template.startDate1 = vm.currentTransmittedData.startDate1;
        vm.$refs.template.endDate = vm.currentTransmittedData.endDate;
        vm.$refs.template.endDate1 = vm.currentTransmittedData.endDate1;
      }

      var form = vm.$refs.template.$el.getElementsByClassName('projectForm')[0].elements;
      for (var i = 0; i < form.length; i++) {
        form[i].disabled = vm.disabled;
      }
    },
    Templatechange() {
      const vm = this;
      this.Form.contractTemplate = this.Templateindex;
      this.resultsTemplate.forEach(function(i) {
        if (i.value === vm.Form.contractTemplate) {
          vm.Form.contractTemplate = i.label;
        }
      });
    },
    changeTemplate() {
      this.popupVisibleTemplate = true;
    }
  }
};
</script>
