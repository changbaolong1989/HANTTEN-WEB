<template>
  <div class="cost-A4 matterForm">
    <el-row>
      <el-col :span="18">
        <div>
          <p>工程咨询业务资料移交记录表</p>
        </div>
      </el-col>
    </el-row>
    <table class="align-center">
      <tbody>
      <tr>
        <td>
          <div class="cell">合同编号</div>
        </td>
        <td>
          <div class="cell">{{form.contractNum}}</div>
        </td>
        <td>
          <div class="cell">项目名称</div>
        </td>
        <td>
          <div class="cell">{{form.projectName}}</div>
        </td>
      </tr>
      <tr>
        <td>
          <div class="cell">业务类别</div>
        </td>
        <td>
          <div class="cell">{{form.counselBusinessType}}</div>
        </td>
        <td>
          <div class="cell"></div>
        </td>
        <td>
          <div class="cell"></div>
        </td>
      </tr>
      <tr>
        <td>
          <div class="cell">委托方</div>
        </td>
        <td>
          <div class="cell">{{form.trusterName}}</div>
        </td>
        <td>
          <div class="cell">经手人</div>
        </td>
        <td>
          <div class="cell">{{form.trusterDutyUser}}</div>
        </td>
      </tr>
      <tr>
        <td>
          <div class="cell">签收方</div>
        </td>
        <td>
          <div class="cell">汉腾公司</div>
        </td>
        <td>
          <div class="cell">经手人</div>
        </td>
        <td>
          <div class="cell">{{form.operatorName}}</div>
        </td>
      </tr>
      </tbody>
    </table>
    <table class="align-center">
      <thead>
      <tr>
        <th>
          <div class="cell">序号</div>
        </th>
        <th>
          <div class="cell">文件名称</div>
        </th>
        <th>
          <div class="cell">文件类型</div>
        </th>
        <th>
          <div class="cell">原件/复印件</div>
        </th>
        <th>
          <div class="cell">份数</div>
        </th>
        <th>
          <div class="cell">提交人</div>
        </th>
        <th>
          <div class="cell">提交时间</div>
        </th>
        <th>
          <div class="cell">接收人</div>
        </th>
        <th>
          <div class="cell">接收时间</div>
        </th>
        <th>
          <div class="cell">备注</div>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item,i) in tableData" :key="i">
        <td>
          <div class="cell">{{i + 1}}</div>
        </td>
        <td>
          <div class="cell">{{item.materialName}}</div>
        </td>
        <td>
          <div class="cell">{{item.fileTypeName}}</div>
        </td>
        <td>
          <div class="cell">{{item.copyFile}}</div>
        </td>
        <td>
          <div class="cell">{{item.num}}</div>
        </td>
        <td>
          <div class="cell">{{item.userName}}</div>
        </td>
        <td>
          <div class="cell">{{item.createDate}}</div>
        </td>
        <td>
          <div class="cell">{{item.recipient}}</div>
        </td>
        <td>
          <div class="cell">{{item.recipientDate}}</div>
        </td>
        <td>
          <div class="cell">{{item.remark}}</div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    props: ['transmittedData'],
    data() {
      return {
        tableData: [],
        form: {},
        currentTransmittedData:this.tool.deepCopy(this.transmittedData,this.tool.deepCopy)
      }
    },
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
    methods: {
      searchList() {
        this.apiAddress("/taskExecute/query/searchA4Info", {
          "relationId": this.currentTransmittedData.relationId,
          "taskId": this.currentTransmittedData.taskId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            this.form = res.data.dataVO;
            this.tableData = res.data.dataVO.taskMaterialList;
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      }
    }
  }
</script>

<style lang="scss">
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');
</style>
