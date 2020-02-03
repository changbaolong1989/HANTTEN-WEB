<template>
  <el-form :model="form" ref="form" class="search">
    <div class="item">
      <label for="">{{ 'year' == currentsearchType ? '年份' : '月份' }}</label>
      <el-date-picker
        v-model="form.date" :clearable="false" :editable="false"
        :type="'year' == currentsearchType ? 'year' : 'month'"
        :placeholder="'year' == currentsearchType ? '选择年份' : '选择月份'"
        :value-format="'year' == currentsearchType ? 'yyyy' : 'yyyy-MM'"
      ></el-date-picker>
    </div>
    <el-button @click="exportData"
      type="primary"
      class="linear"
      v-if="userPowerInfo.reportJXFormExport && tabName == undefined"
      style="position: absolute;right: 120px;top: 15px;"
    >
      <i class="iconfont" style="font-size: 12px;">&#xe642;</i>
      &nbsp;导出
    </el-button>
    <el-button type="primary" icon="el-icon-search" class="linear" @click="search" style="position: absolute;right: 20px;top: 15px;">搜索</el-button>
  </el-form>
</template>

<script>
export default {
  props: ['searchType', 'tabName', 'searchProp'],
  data() {
    return {
      form: {
        date: this.tool.formatDate(new Date(), 'year' == this.searchType ? 'yyyy' : 'yyyy-MM')
      },
      currentsearchType: this.searchType || 'year',
      userPowerInfo: this.SStorage.getItem('userPowerInfo')
    };
  },
  watch: {
    searchType: {
      handler() {
        let vm = this;
        vm.currentsearchType = vm.searchType;
      },
      immediate: true
    },
    tabName: {
      handler() {
        let vm = this;
        // 重置搜索日期
        vm.form.date = vm.tool.formatDate(new Date(), 'year' == this.searchType ? 'yyyy' : 'yyyy-MM');
      },
      immediate: true
    }
  },
  methods: {
    search() {
      let vm = this;
      vm.$emit('click', vm.form);
    },
    exportData(){
      const elemIF = document.createElement('iframe');
      elemIF.src = '/reportForm/exprot/exportPerformanceReport?realFileName=' + "绩效报表.xls" + "&month=" + this.searchProp;
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
    }
  }
};
</script>

<style lang="scss">
.search {
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
  padding: 15px 20px;
  position: relative;
  box-shadow: 0 0 15px #e7e8e9 inset;
  height: 62px;
  background-color: white;

  .item {
    display: flex;

    & + .item {
      margin-left: 15px;
    }

    label,
    .el-input {
      height: 30px;
      line-height: 30px;
      font-size: 12px;
    }

    label {
      color: #333;
      margin-right: 10px;
    }

    .el-input {
      position: relative;
      display: inline-block;
      width: auto !important;
    }
  }
}
</style>
