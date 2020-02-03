<template>
  <!-- 成果文件主体-->
  <div class="index">
    <preparation v-if="!correctTaskMaterialFlag" :transmittedData="currentTransmitData" :disabled="disabled"></preparation>
    <div :style="{ height: office.officeHeight }" v-if="office.showOnlyOfficeCompFlag"><office :file="office.taskMaterialInfo" @supportOffice="supportOffice"></office></div>
  </div>
</template>

<script>
import preparation from '../../../common/preparation/index.vue'; /* 编制成果文件√ */
import office from '../../../../common/vue/office.vue'; /* 批注 */
export default {
  data() {
    return {};
  },
  props: ['currentTransmitData','office', 'correctTaskMaterialFlag', 'disabled'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
      },
      immeditate: true
    },
    office: {
      handler(newval) {
        let vm = this;
        vm.office = newval;
        console.log('office', vm.office);
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
    },
    disabled: {
      handler(newval) {
        let vm = this;
        vm.disabled = newval;
      },
      immediate: true,
      deep: true
    }
  },
  components: {
    preparation,
    office
  },
  methods: {
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

<style></style>
