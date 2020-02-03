<template>
  <div class="task">
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
    <!-- 成果文件组件 -->
    <div class="form-block form-block__row--auto-width" style="background-color: white;" v-if="!correctTaskMaterialFlag">
      <prepare ref="prepare" :transmittedData="currentTransmitData" :disabled="disabled"></prepare>
    </div>
    <!-- onlyOffice -->
    <div class="form-block form-block__row--auto-width" v-if='office.showOnlyOfficeCompFlag' style="height: 700px;">
      <office :file="office.taskMaterialInfo"></office>
    </div>
  </div>
</template>

<script>
  import prepare from '../../../../../costOf/Prepare/cost-index';  /* 编制成果文件组件 */
  import office from '../../../../common/components/office';/* onlyOffice组件 */
  export default {
    components: {
      'prepare': prepare,// 成果文件
      'office':office//onlyOffice
    },
    props: ['currentTransmitData','office', 'correctTaskMaterialFlag', 'disabled'],
    data() {
      return {
        // 表单项间距
        gutter: 40
      }
    },
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          const vm = this;
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
    methods: {}
  }
</script>
<style>
  @import url('../../../../common/css/other.css');
</style>
