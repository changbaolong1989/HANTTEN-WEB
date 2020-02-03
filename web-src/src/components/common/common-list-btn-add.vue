<!--通用针对列表新增dialog按钮组件 @author cbl-->
<!--
使用方法：
在父组件中import当前组件，父组件props:addComponentKey和addComponentText
addComponentText：当前新增按钮中所要显示的文字
addComponentKey：新增dialog对话框组件的组件名
子组件相关修改：引用新增dialog组件
<common-list-btn-add :addComponentKey="addComponentKey" :addComponentText="addComponentText" ></common-list-btn-add>
-->
<template>
  <div>
    <div class="item-tab-chuang" @click="isShowAddDialog = true">
      <span class="el-icon-plus linear"></span>{{addComponentText}}
    </div>
    <component :is="addComponentKey" :dialogTitle="dialogTitle" :isShowDialog.sync="isShowAddDialog"
               @showDialog="showDialog"></component>
  </div>
</template>

<script>

  import WholeProcessContractListAdd from '../wholeProcessCost/projectDetails/contractManage/contractStandingBook/contract-list-add'
  import WholeProcessChangeDetailAdd from '../wholeProcessCost/projectDetails/changeManage/changeDetail/change-detail-form'
  import WholeProcessStatementAdd from '../wholeProcessCost/projectDetails/closeAccount/close-account-form'

  export default {
    name: "common-btn-add",
    components: {
      WholeProcessContractListAdd,
      WholeProcessChangeDetailAdd,
      WholeProcessStatementAdd
    },
    data() {
      return {
        isShowAddDialog: false, //弹窗显示
        dialogTitle: '新增'
      }
    },
    props: ['addComponentKey', 'addComponentText'],
    methods: {
      showDialog() {
        this.isShowAddDialog = false
      }
    }
  }
</script>

<style scoped>

</style>
