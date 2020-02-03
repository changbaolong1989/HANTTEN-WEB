<template>
  <!-- 编制成果文件-->
  <div class="preparation">
    <form class="projectForm" id="resultsFile">
      <div class="form-item" @click="popupVisibleGenre = true">
        <mt-cell title="编制成果文件">
          <span>{{ projectForm.genre }}</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 编制成果文件弹窗-->
      <mt-popup v-model="popupVisibleGenre" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>编制成果文件</p>
          <div class="item" v-for="(item, i) in personlist">
            <label :for="'person' + i">
              {{ item.label }}
              <input type="radio" :id="'person' + i" v-model="listPerson" :value="item.value" name="person" @change="radioChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleGenre = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="surface">
      <mt-tab-container v-model="listPerson">
        <results-file v-if="listPerson == 0" ref="results" :transmittedData="currentTransmittedData" :disabled="disabled"></results-file>
        <A2 v-if="listPerson == 1" :transmittedData="transmittedData"></A2>
        <A3 v-if="listPerson == 2" :contentList="content"></A3>
        <A7 v-if="listPerson == 3" :transmittedData="transmittedData"></A7>
      </mt-tab-container>
    </div>
  </div>
</template>

<script>
import A2 from './resultsFile/A2.vue';
import A3 from './resultsFile/A3.vue';
import A7 from './resultsFile/A7.vue';
import resultsFile from './resultsFile/resultsFile.vue';
export default {
  data() {
    return {
      popupVisibleGenre: false,
      submitData: null,
      projectForm: {
        genre: '成果文件'
      },
      listPerson: '0',
      personlist: [
        {
          label: '成果文件',
          value: '0'
        },
        {
          label: 'A2',
          value: '1'
        },
        {
          label: 'A3',
          value: '2'
        },
        {
          label: 'A7',
          value: '3'
        }
      ],
      content: {
        contractNum: '',
        fileNumber: '',
        projectName: ''
      },
      currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy)
    };
  },
  components: {
    A2: A2,
    A3: A3,
    A7: A7,
    'results-file': resultsFile
  },
  props: ['transmittedData', 'disabled'],
  watch: {
    transmittedData: {
      handler() {
        let vm = this;
        vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
        vm.init()
        // 获取成果文件信息
        vm.getPreparationOfOutcomeDocumentsDetailInfo();
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
  methods: {
    init(){
      if(this.currentTransmittedData.departmentId=="db5e76f9-9fbd-11e9-a036-0e411fd5cf91"){/* 造价 */
        this.personlist = [
          {
            label: '成果文件',
            value: '0'
          },
          {
            label: 'A2',
            value: '1'
          },
          {
            label: 'A3',
            value: '2'
          },
          {
            label: 'A7',
            value: '3'
          }
        ]
      }else{/* 招标 */
        this.personlist = [
          {
            label: '成果文件',
            value: '0'
          }
        ]
      }
    },
    getPreparationOfOutcomeDocumentsDetailInfo() {
      let vm = this;
      this.requestPost('/task//query/searchTaskInfo', {
        taskId: this.currentTransmittedData.taskId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            let data = res.data.dataVO;
            vm.currentTransmittedData = Object.assign({}, this.currentTransmittedData, {
              taskType: data.taskType, //模板类型
              formworkType: data.formworkType /* 成果文件类型 */,
              formworkContent: data.formworkContent /* 表单内容 */,
              contractNum: data.contractNum /* 合同编号 */,
              projectName: data.projectName /*, 项目名称 */
            });
            var jsonObj = JSON.parse(data.formworkContent);
            if(data.formworkType=='2'){
              vm.currentTransmittedData.startDate = jsonObj.input8.split('至')[0];
              vm.currentTransmittedData.startDate1 = jsonObj.input8.split('至')[1];
              vm.currentTransmittedData.endDate = jsonObj.input9.split('至')[0];
              vm.currentTransmittedData.endDate1 = jsonObj.input9.split('至')[1];
            }
            if(data.formworkType=='11'){
              console.log("16",jsonObj.input16.split('至'))
              console.log("17",jsonObj.input17.split('至'))
              vm.currentTransmittedData.startDate = jsonObj.input16.split('至')[0];
              vm.currentTransmittedData.startDate1 = jsonObj.input16.split('至')[1];
              vm.currentTransmittedData.endDate = jsonObj.input17.split('至')[0];
              vm.currentTransmittedData.endDate1 = jsonObj.input17.split('至')[1];
            }
          }else{
            this.Toast(res.data.message);
          }
        })
        .catch(e => {

        });
    },
    radioChange() {
      const vm = this;
      this.personlist.forEach(function(i) {
        if (i.value === vm.listPerson) {
          vm.projectForm.genre = i.label;
          vm.id = i.value;
        }
      });
      this.bus.$emit('changeAuthorized', vm.id);
    }
  }
};
</script>
<style scoped lang="scss">
.preparation {
  width: 100%;
}
</style>
