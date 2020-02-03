<template>
  <div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">项目编号:{{ transmittedData.projectNumber }}</p>
        </el-col>
      </el-row>
      <div class="img-failure" v-if="showImg">
        <img v-bind:src="imgSrc" :alt="imgAlt" />
        <p style="text-align: center;">{{ imgAlt }}</p>
      </div>
    </div>
    <div class="form-block item" style="padding-bottom:56px;position: relative;">
      <!-- tab页-->
      <el-tabs v-model="active" type="card" @tab-click="handleClick">
        <!-- 合同、任务、请款单 -->
        <el-tab-pane :label="`任务台账`" name="contract-task-request" :lazy="true"></el-tab-pane>
        <!-- 成果文件 -->
        <el-tab-pane :label="`成果文件台账`" name="result-file" :lazy="true"></el-tab-pane>
      </el-tabs>
      <div class="excelBtn">
        <!-- 导出按钮 -->
        <el-button class="linear " type="primary" @click="exportFunc">导出</el-button>
      </div>
      <!-- 蓝色条 -->
      <div class="item-tab-bor linear"></div>
      <!-- 引用组件显示 -->
      <component :ref="active" v-bind:is="'table-'+active"></component>
    </div>
  </div>
</template>
<script>
/* 待办表格 */
import tableContractTaskRequest from './tableContractTaskRequest';
/* 已办表格 */
import tableResultFile from './tableResultFile';
export default {
  components: {
    'table-contract-task-request': tableContractTaskRequest,
    'table-result-file': tableResultFile
  },
  props: ['transmittedData'],
  data() {
    return {
      projectId: this.SStorage.getItem('/page/work/contract/FoundItem').projectId,
      contractNumber: '',
      // 表单项间距
      gutter: 40,
      showImg: false,
      imgSrc: '',
      imgAlt: '',
      active: 'contract-task-request'
    };
  },
  methods: {
    /**
     * 点击tab页面切换
     * @param {Object} tab
     * @param {Object} event
     */
    handleClick(tab, event) {
      let vm = this;
      vm.active = tab.name;
    },
    /**
     * 点击导出按钮
     */
    exportFunc() {
      let vm = this;
      if(vm.active === "contract-task-request"){// 任务台账
        const elemIF = document.createElement('iframe');
        elemIF.src = '/project/export/excExportParticipantsInfoList?projectId=' + this.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      }else{// 成果文件台账
        const elemIF = document.createElement('iframe');
        elemIF.src = '/project/export/excExportResultFileLedgerInfoList?projectId=' + this.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      }
    }
  }
};
</script>
<style lang="scss">
.item {
  /* 导出按钮*/
  .excelBtn{
      display: inline;
      position: absolute;
      right:20px;
      top:40px;
  }
  /* 标签页 */
  .el-tabs--card {
    & > .el-tabs__header {
      border-bottom: none;
      margin: 0;

      .el-tabs__nav-scroll {
        padding: 20px 20px 0;
        // box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.4) inset;

        .el-tabs__nav {
          border: 1px;

          .el-tabs__item {
            border-radius: 10px 10px 0 0;
            border: none;
            box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
            background-color: white;

            &.is-active {
              background-color: #0aa3ef;
              color: white;
            }
          }
        }
      }
    }
  }
}
</style>
<style>
@import url('../../../common/css/other.css');

/* 文件列表*/
.task {
}
</style>
