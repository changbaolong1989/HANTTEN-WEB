<template>
  <!-- 项目监理主体-->
  <div class="index">
    <ul class="worklist">
      <li>
        <span>工作步骤说明:</span>
        <span>{{ currentTransmitData.periodDesc }}</span>
      </li>
    </ul>
    <supervisor :transmittedData="currentTransmitData"></supervisor>
  </div>
</template>

<script>
import supervisor from '../../../common/supervisor/index.vue'; /* 项目监理表*/
  export default{
    data(){
      return{

      }
    },
    props: ['currentTransmitData'],
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          const vm = this;
          vm.currentTransmitData = newval;
        },
        immeditate: true
      },
    },
    components: {
      supervisor,
    },
    methods:{

    }
  }
</script>

<style>
</style>
