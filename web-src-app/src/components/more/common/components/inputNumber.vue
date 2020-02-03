<template>
  <!-- 计数器 -->
  <div class="input-number">
    <span class="icon icon-minus" @click="minus">-</span>
    <input type="number" v-model="currentValue" @input="input" disabled="disabled">
    <span class="icon icon-plus" @click="plus">+</span>
  </div>
</template>

<script>
  export default {
    model: {
        prop: 'value',
        event: 'input'
    },
    props: ['max', 'min', 'value'],
    data() {
      return {
        currentValue: this.value
      };
    },
    methods: {
      input(){
        let vm = this;
        vm.check(vm.currentValue);
        vm.$emit('input',vm.currentValue);
      },
      minus() {
        let vm = this;
        if (vm.currentValue <= vm.min) return;
        vm.currentValue--;
         vm.input();
      },
      plus() {
        let vm = this;
        if (vm.currentValue >= vm.max) return;
        vm.currentValue++;
         vm.input();
      },
      check(value) {
        let vm = this;
        if (value >= vm.max)return vm.currentValue = vm.max;
        if (value <= vm.mix)return vm.currentValue = vm.min;
         vm.currentValue = value;
      },
    }
  };
</script>
<style lang="scss">
  .input-number {
    * {
      box-sizing: border-box;
    }
  }
</style>
<style lang="scss" scoped>
  input {
    width: 55px;
    font-size: 26px !important;
    padding: 0 5px;
    marging: 0 5px;
    text-align: center;
  }

  .icon {
    display: inline-block;
    text-align: center;
    width: 36px;
    height: 36px;
    border-radius: 18px;
    line-height: 36px;
    color: white;
    background-color: #35b7f7;
  }
</style>
