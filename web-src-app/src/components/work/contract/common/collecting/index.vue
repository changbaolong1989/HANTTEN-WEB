<template>
  <div class="collecting">
    <p class="Atitle">工程咨询业务资料移交记录表</p>
    <ul class="contentlist">
      <li>
        <span>合同编号</span>
        <span>{{ form.contractNum }}</span>
      </li>
      <li>
        <span>项目名称</span>
        <span>{{ form.projectName }}</span>
      </li>
      <li>
        <span>业务类别</span>
        <span>{{ form.counselBusinessType }}</span>
      </li>
      <li>
        <span>委托方</span>
        <span>{{ form.trusterName }}</span>
      </li>
      <li>
        <span>经手人</span>
        <span>{{ form.trusterDutyUser }}</span>
      </li>
      <li>
        <span>签收方</span>
        <span>汉腾公司</span>
      </li>
      <li>
        <span>经手人</span>
        <span>{{ form.operatorName }}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in tableData" class="contentlist":key="'list'+j">
      <li>
        <span>序号</span>
        <span>{{j + 1}}</span>
      </li>
      <li>
        <span>文件名称</span>
        <span>{{i.materialName}}</span>
      </li>
      <li>
        <span>份数</span>
        <span>1份</span>
      </li>
      <li>
        <span>备注</span>
        <span>{{i.remark}}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      form: {},
      currentTransmittedData:this.tool.deepCopy(this.transmittedData,this.tool.deepCopy)
    }
  },
  props: ['transmittedData'],
  watch:{
      transmittedData:{
        handler(){
          let vm = this;
          vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData,vm.tool.deepCopy);
        },
        immediate: true
      }
  },
  mounted() {
    this.searchList();
    this.bus.$on('searchList', val => {
      this.searchList();
    });
  },
  methods:{
    searchList() {
      this.requestPost("/taskExecute/query/searchA4Info", {
        "relationId": this.currentTransmittedData.relationId,
        "taskId": this.currentTransmittedData.taskId
      }).then(res => {
        // 如果返回成功，则绘制数据，否则提示失败信息
        if (res.data.status == "success") {
          console.log(res)
          this.form = res.data.dataVO;
          this.tableData = res.data.dataVO.taskMaterialList;
          console.log("备注",this.tableData)
        }
      }).catch(e => {
        this.Toast(e);
      });
    }
  }
};
</script>