<template>
  <span class="m-checkbox" @click="clickCurCheckbox">
    <input :disabled="disabled" type="checkbox" :name="name" class="m-checkbox-input" @change="change" :checked="!!value">
    <span class="m-checkbox-core"></span>
  </span>
</template>

<script>
  export default{
    props:['name','value','disabled'],
    model: {
      prop: 'value',
      event: 'change'
    },
    data(){
      return {};
    },
    methods:{
      /**
       * @method 点击
       */
      clickCurCheckbox(e){
        let vm = this;
        if(vm.disabled)return;
        e.currentTarget.children[0].click();
      },
      /**
       * @event 当checkbox改变时触发
       */
      change(e){
        let vm = this;
        vm.$emit('change',!!e.currentTarget.checked);
      }
    }
  }
</script>

<style lang="scss" scoped>
  /* 自定义checkbox */
  .m-checkbox{

    .m-checkbox-input{
      display: none;

      &:checked + .m-checkbox-core{
        background-color: #26a2ff;
        border-color: #26a2ff;

        &::after{
          top: 5px;
          left: 10px;
          border-color: #fff;
          transform: rotate(45deg) scale(1);
        }
      }
      &:disabled{
        &+.m-checkbox-core{
          background-color: #d9d9d9;
        }
      }
    }

    .m-checkbox-core{
      position: relative;
      display: inline-block;
      width: 28px;
      height: 28px;
      border:1px solid #ccc;
      border-radius: 50%;
      background-color: #fff;

        &::after{
          border: 1px solid transparent;
          border-left: 0;
          border-top: 0;
          content: " ";
          position: absolute;
          width: 6px;
          height: 13px;

        }
    }
  }

</style>
