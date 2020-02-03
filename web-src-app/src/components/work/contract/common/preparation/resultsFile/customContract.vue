<template>
  <!-- 自定义文件-->
  <div class="customContract">
    <form class="projectForm" >
      <div class="form-item">
        <label>成果文件编号</label>
        <input type="text" placeholder="请输入成果文件编号" v-model="projectForm.reportNum" :disabled="disabled"  v-validate="'required'" name="reportNum" data-vv-as="成果文件编号"/>
      </div>
      <div class="form-item file">
        <mt-cell title="上传文件">
          <label for="file">{{ projectForm.accessory }}</label>
          <input type="file" id="file" @change="onUpload($event)"  :disabled="disabled"/>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectForm: {
        reportNum:"",
        accessory: '上传',
      },
      file:'',
      currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy)
    };
  },
  props: ['transmittedData', 'disabled'],
  watch: {
    transmittedData: {
      handler() {
        let vm = this;
        vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
        this.$nextTick(function() {
          vm.init();
        });
      },
      immediate: true
    }
  },
  methods:{
    init(){
      this.requestPost('/task/query/searchOutcomeDocumentMaterial', {
        taskId: this.currentTransmittedData.taskId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            if(res.data.dataVO.taskMaterialName==""){
              this.projectForm.accessory = "上传";
            }else{
              console.log("文件", res.data.dataVO)
              this.projectForm.accessory = res.data.dataVO.taskMaterialName;
              this.projectForm.reportNum = res.data.dataVO.reportNum;
              this.file = res.data.dataVO.taskMaterialPath;
            }
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    onUpload(e) {
      this.file = e.target.files[0];
      this.projectForm.accessory = e.target.files[0].name;
    }
  }
};
</script>

<style scoped lang="scss">
.customContract {
}
</style>
