<template>
  <div>
    <pro-cfg-list-btn-add style="float: right"></pro-cfg-list-btn-add>
    <pro-cfg-query-ctn></pro-cfg-query-ctn>
    <pro-cfg-list></pro-cfg-list>
  </div>
</template>

<script>

  import ProCfgListBtnAdd from './pro-cfg-list-btn-add'
  import ProCfgList from './pro-cfg-list'
  import ProCfgQueryCtn from './pro-cfg-query-ctn'

  export default {
    name: "process-config",
    components: {ProCfgList, ProCfgListBtnAdd, ProCfgQueryCtn}
  }

</script>

<style scoped>

</style>

