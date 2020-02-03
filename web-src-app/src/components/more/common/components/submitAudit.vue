<template>
  <div class="submitAudit" v-show="show">
    <form class="projectForm">
      <div class="form-item checkboxlist" v-for="(item,index) in list">
        <mt-checklist align="right" :title="item.label" v-model="value" :options="item.children" v-if="item.children&&item.children.length"></mt-checklist>
      </div>
    </form>
    <footer class="commonFooter">
      <mt-button type="default" class="btn-submit" @click="show = false">取消</mt-button>
      <mt-button type="primary" class="linear btn-submit" @click.prevent="submit">确定</mt-button>
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      show: false,
      value: [],
      list: []
    };
  },
  watch: {
    show: {
      handler() {
        let vm = this;
        if (vm.show) {
          // 清空表单
          vm.reset();
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    submit() {
      var data = [];
      this.value.forEach(function(i){
        data.push(i.id)
      })
      this.$emit('confirmSubmit',data);
    },
    reset() {
      this.value = []
    }
  }
};
</script>
<style lang="scss">
.submitAudit {
  position: fixed;
  top: 0;
  left: 0;
  background-color: white;
  height: 100vh;
  width: 100vw;
  z-index: 2000;
  overflow: auto;
  .form-item{
    border-bottom: none;
  }
  .checkboxlist {
    width: 100%;
    margin: 0px auto;
    overflow: auto;
  }
  .checkboxlist p {
    font-size: 28px;
    color: #b3b3b3;
  }
  .checkboxlist .mint-cell {
    border-bottom: 2px solid #e5e5e5;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label {
    display: flex;
    justify-content: space-between;
    flex-direction: row-reverse;
    font-size: 28px;
    color: #333333;
    width: 100%;
    height: 90px;
    line-height: 90px;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label .mint-checkbox-core {
    width: 60px;
    height: 60px;
    transform: scale(0.5);
    transform-origin: 0% 0%;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label .mint-checkbox-core::after {
    top: 15px;
    left: 20px;
    position: absolute;
    width: 20px;
    height: 20px;
    border-width: 4px;
  }
  .checkboxlist p {
    font-size: 28px;
    color: #b3b3b3;
  }
  .checkboxlist .mint-cell {
    border-bottom: 2px solid #e5e5e5;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label {
    display: flex;
    justify-content: space-between;
    flex-direction: row-reverse;
    font-size: 28px;
    color: #333333;
    width: 100%;
    height: 90px;
    line-height: 90px;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label .mint-checkbox-core {
    width: 60px;
    height: 60px;
    transform: scale(0.5);
    transform-origin: 0% 0%;
  }
  .checkboxlist .mint-cell .mint-cell-wrapper .mint-checklist-label .mint-checkbox-core::after {
    top: 15px;
    left: 20px;
    position: absolute;
    width: 20px;
    height: 20px;
    border-width: 4px;
  }
}
</style>
