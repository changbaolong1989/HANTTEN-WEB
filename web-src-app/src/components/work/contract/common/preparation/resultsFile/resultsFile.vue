<template>
  <!-- 成果文件-->
  <div class="resultsFile">
    <form class="projectForm" id="resultsFile">
      <div class="form-item" @click="popupVisibleType = true">
        <mt-cell title="文件类型">
          <span>{{ projectForm.contractType }}</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 合同类型弹窗-->
      <mt-popup v-model="popupVisibleType" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>文件类型</p>
          <div class="item" v-for="(item, i) in resultslist">
            <label :for="'results' + i">
              {{ item.label }}
              <input type="radio" :id="'results' + i" v-model="resultsFile" :value="item.value" name="results" @change="resultschange" :disabled="disabled" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleType = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="surface">
      <custom-contract ref="custom" v-if="resultsFile == 0" :transmittedData="currentTransmittedData" :disabled="disabled"></custom-contract>
      <index ref="index" v-if="resultsFile == 1" :transmittedData="currentTransmittedData" :disabled="disabled"></index>
    </div>
  </div>
</template>

<script>
import customContract from './customContract.vue';
import templateindex from './template/index.vue';
export default {
  data() {
    return {
      popupVisibleType: false,
      projectForm: {
        contractType: '自定义'
      },
      resultsFile: '0',
      resultslist: [
        {
          label: '自定义',
          value: '0'
        },
        {
          label: '模板',
          value: '1'
        }
      ],
      currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy)
    };
  },
  props: ['transmittedData', 'disabled'],
  watch: {
    transmittedData: {
      handler() {
        let vm = this;
        vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
        vm.init();
      },
      immediate: true
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
  components: {
    'custom-contract': customContract,
    index: templateindex
  },
  created() {
    const vm = this;
    vm.bus.$on('MaterialList', function(val) {
      vm.submitForm(val);
    });
  },
  methods: {
    init() {
      const vm = this;
      if (this.currentTransmittedData.taskType !== '') {
        this.resultsFile = this.currentTransmittedData.taskType;
        var item = vm.resultslist.filter(function(i) {
          return i.value == vm.currentTransmittedData.taskType;
        });
        if (item.length > 0) {
          vm.projectForm.contractType = item[0].label;
        }
      } else {
        this.resultsFile = '0';
        vm.projectForm.contractType = '自定义合同';
      }
    },
    resultschange() {
      const vm = this;
      this.projectForm.contractType = this.resultsFile;
      this.resultslist.forEach(function(i) {
        if (i.value === vm.projectForm.contractType) {
          vm.projectForm.contractType = i.label;
        }
      });
    },
    submitForm(type) {
      const vm = this;
      if (vm.resultsFile == '0') {
        if (type == 'BrowseFile') {
          var obj = {
            taskType: vm.resultsFile /* 合同类型 */,
            reportNum: vm.$refs.custom.projectForm.reportNum /* 成果文件编号 */,
            signPathFile: vm.$refs.custom.file /* 表单数据 */
          };
          vm.bus.$emit('echofile', { submit: obj, resultsFile: vm.resultsFile, type: type }); /* 验证通过就让数据提交 */
          vm.bus.$emit('customChange');
        } else {
          vm.$refs.custom.$validator.validateAll().then(result => {
            if (result === false) {
              vm.Toast(vm.errors.items[0].msg);
            } else {
              if (vm.$refs.custom.file === '') {
                vm.Toast('请上传文件');
                return;
              } else {
                var obj = {
                  taskType: vm.resultsFile /* 合同类型 */,
                  reportNum: vm.$refs.custom.projectForm.reportNum /* 成果文件编号 */,
                  signPathFile: vm.$refs.custom.file /* 表单数据 */
                };
                vm.bus.$emit('echofile', { submit: obj, resultsFile: vm.resultsFile, type: type }); /* 验证通过就让数据提交 */
                vm.bus.$emit('customChange');
              }
            }
          });
        }
      } else {
        if (type == 'BrowseFile') {
          var obj = {
            taskType: vm.resultsFile /* 合同类型 */,
            formworkType: vm.$refs.index.Templateindex, // 表单类型
            formworkContent: vm.$refs.index.$refs.template.templateform /* 表单数据 */
          };
          vm.bus.$emit('echofile', { submit: obj, resultsFile: vm.resultsFile, type: type }); /* 验证通过就让数据提交 */
        } else {
          vm.$refs.index.$refs.template.$validator.validateAll().then(result => {
            if (result === false) {
              vm.Toast(vm.errors.items[0].msg);
              return;
            } else {
              var obj = {
                taskType: vm.resultsFile /* 合同类型 */,
                formworkType: vm.$refs.index.Templateindex, // 表单类型
                formworkContent: vm.$refs.index.$refs.template.templateform /* 表单数据 */
              };
              vm.bus.$emit('echofile', { submit: obj, resultsFile: vm.resultsFile, type: type }); /* 验证通过就让数据提交 */
            }
          });
        }
      }
    }
  }
};
</script>
<style scoped lang="scss">
.resultsFile {
  width: 100%;
}
</style>
