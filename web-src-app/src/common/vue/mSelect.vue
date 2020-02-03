<template>
  <div class="m-select">
    <!-- 显示的表单元素 -->
    <mt-cell :title="currentData.title">
      <span @click.stop.prevent="show = true">{{ currentDisplay }}</span>
      <i class="iconfont">&#xe630;</i>
    </mt-cell>
    <!-- 下拉弹窗 -->
    <mt-popup v-model="show" popup-transition="popup-fade" position="bottom">
      <div class="radiolist">
        <p>{{currentData.title}}</p>
        <div class="item" v-for="(o, i) in currentData.options" :key="i">
          <label>
            {{o[display]}}
            <input :type="multiple?'checkbox':'radio'"
                    :name="name"
                    :value="o[unique]"
                    :checked="-1!=fire.indexOf(''+(o[unique]))||-1!=fire.indexOf(parseInt(o[unique]))"
                    @change.prevent="change" hidden/>
            <span></span>
          </label>
        </div>
      </div>
      <div class="ComponentsFooter">
        <mt-button class="linear" @click.prevent="show = false">确定</mt-button>
      </div>
    </mt-popup>
  </div>
</template>

<script>
  export default {
    props: {
      data: { // 传输数据格式
        type: Object,
        default () {
          return {
            title: '', // 标题
            options: [] // 选项对象
          };
        }
      },
      unique: { // 唯一标识的字段(默认为id)
        type: String,
        default: 'id'
      },
      display: { // 用来展示的字段(默认为display)
        type: String,
        default: 'display',
      },
      multiple: { // 是否多选,默认为false
        type: Boolean,
        default: false,
      },
      value: {
        default(){
          return [];
        }
      },
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    data() {
      return {
        name:this.tool.getId(),
        show: false,
        currentData: this.tool.deepCopy(this.data, this.tool.deepCopy),
        fire: this.value?(this.value instanceof Array?this.tool.deepCopy(this.value, this.tool.deepCopy):[this.value]):(0===this.value?[0]:[''])
      };
    },
    watch: {
      data: {
        handler() {
          let vm = this;
          vm.currentData = vm.tool.deepCopy(vm.data, vm.tool.deepCopy);
        },
        immediate: true
      },
      value:{
        handler() {
          let vm = this;
          // 将值转成数组格式
          let arr = vm.value?(vm.value instanceof Array?vm.tool.deepCopy(vm.value, vm.tool.deepCopy):[vm.value]):(0===vm.value?[0]:['']);
          let changeObj = '';
          // 如果相同的赋值则直接返回
          if(vm.fire.toString()==arr.toString())return;
          vm.fire = arr;
          for (let i = 0, len = vm.currentData.options.length; i < len; i++) {
            let key = vm.currentData.options[i][vm.unique];
            if (key == vm.fire[vm.fire.length - 1]) {
              changeObj = vm.currentData.options[i];
            }
          }
          vm.$emit('change', vm.tool.deepCopy(vm.multiple?vm.fire:vm.fire[vm.fire.length - 1], vm.tool.deepCopy),changeObj);
          console.log('vm.fire',vm.fire);
        },
        immediate: true
      }
    },
    computed: {
      currentDisplay() {
        let vm = this;
        let arr = [];
        for (let i = 0, len = vm.currentData.options.length; i < len; i++) {
          if (vm.multiple) {
            for (let j = 0, len = vm.fire.length; j < len; j++) {
              if (vm.fire[j] == vm.currentData.options[i][vm.unique])
                arr.push(vm.currentData.options[i][vm.display]);
            }
          } else {
            if (vm.fire == vm.currentData.options[i][vm.unique])
              arr.push(vm.currentData.options[i][vm.display]);
          }
        }
        return arr.length ? arr.toString() : '请选择';
      }
    },
    methods: {
      /**
       * @method 更改选项时
       */
      change(e) {
        let vm = this;
        let changeObj = '';
        for (let i = 0, len = vm.currentData.options.length; i < len; i++) {
          let key = vm.currentData.options[i][vm.unique];
          if (key == e.target.defaultValue) {
            changeObj = vm.currentData.options[i];
            if (vm.multiple) {
              let index = -1==vm.fire.indexOf(''+key)?vm.fire.indexOf(parseInt(key)):vm.fire.indexOf(''+key);
              // 多选
              // 当前是选中,还是取消选中
              if (e.target.checked) {
                // 选中
                if (-1 == index) vm.fire.push(key);
              } else {
                // 取消选中
                vm.fire.splice(index, 1);
              }
            } else {
              // 单选
              vm.fire = [key];
            }
          }
        }
        if (vm.multiple) {
          vm.$emit('change', vm.tool.deepCopy(vm.fire, vm.tool.deepCopy),changeObj);
        }else{
          vm.$emit('change', vm.tool.deepCopy(vm.fire.toString(), vm.tool.deepCopy),changeObj);
        }

      }
    }
  }
</script>

<style lang="scss" scoped>
  .m-select {
    input[type='checkbox'] {
      display: none;

      &:checked+span {
        border: 2px solid #06a1ee;
        background-color: #06a1ee;
        position: relative;

        &::after {
          content: '';
          width: 60px;
          height: 60px;
          background-color: #fff;
          position: absolute;
          top: 15px;
          left: 15px;
          border-radius: 50%;
          z-index: 10;
          transform: scale(0.5);
          transform-origin: 0% 0%;
        }
      }
    }
  }
</style>
