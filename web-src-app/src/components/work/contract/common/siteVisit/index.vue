<template>
  <div class="siteVisit">
    <p class="Atitle">现场踏勘复验记录表</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ form.projectName }}</span>
      </li>
      <li>
        <span>咨询合同编号</span>
        <span>{{ form.contractNum }}</span>
      </li>
      <li>
        <span>发包人</span>
        <span>{{ tableData.employerA8 }}</span>
      </li>
      <li>
        <span>承包人</span>
        <span>{{ tableData.contractor }}</span>
      </li>
      <li>
        <span>监理人</span>
        <span>{{ tableData.supervisor }}</span>
      </li>
      <li>
        <span>现场勘查记录</span>
        <span>{{ tableData.prospectRecord }}</span>
      </li>
      <li>
        <span>发包人（签字）</span>
        <span></span>
      </li>
      <li>
        <span>承包人（签字）</span>
        <span></span>
      </li>
      <li>
        <span>监理人（签字）</span>
        <span></span>
      </li>
      <li>
        <span>咨询人（签字）</span>
        <span></span>
      </li>
      <li>
        <span>记录人</span>
        <span></span>
      </li>
      <li>
        <span>时间</span>
        <span></span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {},
      tableData: {
        employerA8: '',
        contractor: '',
        supervisor: '',
        prospectRecord: ''
      }
    };
  },
  props: ['transmittedData'],
  mounted() {
    this.searchList();
  },
  methods: {
    searchList() {
      this.requestPost('/taskExecute/query/searchA8Info', {
        relationId: this.transmittedData.relationId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.form = res.data.dataVO;
            this.tableData = res.data.dataVO;
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    }
  }
};
</script>
