<template>
  <!-- A3-->
  <div class="A3">
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ contentList.projectName }}</span>
      </li>
      <li>
        <span>咨询合同编号</span>
        <span>{{ contentList.contractNum }}</span>
      </li>
      <li>
        <span>成果文件编号</span>
        <span>{{ contentList.fileNumber }}</span>
      </li>
    </ul>
    <ul v-for="(item, index) in showTableListData(A3dataList)" class="contentlist" :key="'list' + index">
      <p>{{ item.title }}</p>
      <li>
        <span>复核意见</span>
        <span>{{ showOpinions(item.children) }}</span>
      </li>
      <li>
        <span>初审时间</span>
        <span>{{ showMaxDate(item.children) }}</span>
      </li>
      <li>
        <span>执行结果</span>
        <span>{{ showResult(item.children) }}</span>
      </li>
      <li>
        <span>执行日期</span>
        <span>{{ showPassDate(item.children) }}</span>
      </li>
      <li>
        <span>复核人</span>
        <span><img :src="showSignPath(item.children)" v-if="showSignPath(item.children)!=''" /></span>
      </li>
      <li>
        <span>通过日期</span>
        <span>{{ showPassDate(item.children) }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      resultList: [
        {
          // 一级面板的对象
          title: '一级审核', // 一级面板的标题
          taskKey: 'ManagerVerify',
          children: [
            {
              name: '', // 二级面板的审批人
              time: '', // 二级面板的时间
              isPass: null, // 通过1,未通过0
              signPath: '', //签名路径
              opinion: '' // 二级面板的意见
            }
          ]
        },
        {
          // 一级面板的对象
          title: '二级审核', // 一级面板的标题
          taskKey: 'QualityVerify',
          children: [
            {
              name: 'XXX', // 二级面板的审批人
              time: '', // 二级面板的时间
              isPass: null, // 通过1,未通过0
              signPath: '', //签名路径
              opinion: '' // 二级面板的意见
            }
          ]
        },
        {
          // 一级面板的对象
          title: '三级审核', // 一级面板的标题
          taskKey: 'GenerManagerVerify',
          children: [
            {
              name: '', // 二级面板的审批人
              time: '', // 二级面板的时间
              isPass: null, // 通过1,未通过0
              signPath: '', //签名路径
              opinion: '' // 二级面板的意见
            }
          ]
        }
      ]
    };
  },
  props: {
    contentList: Object,
    A3dataList: Array
  },
  watch: {
    contentList(newval) {
      this.contentList = newval;
    },
    A3dataList(newval) {
      this.A3dataList = newval;
    }
  },
  methods: {
    showTableListData(tableListData) {
      let resultList = this.resultList;
      for (var i = resultList.length - 1; i >= 0; i--) {
        if (tableListData) {
          for (var j = tableListData.length - 1; j >= 0; j--) {
            if (resultList[i].taskKey == tableListData[j].taskKey) {
              resultList[i] = tableListData[j];
            }
          }
        }
      }
      return resultList;
    },
    showOpinions(children) {
      let resultOpinion = '';
      for (var i = 0; i < children.length; i++) {
        if (children[i].isPass == null) {
          return '';
        }
        if (children[i].isPass == '0') {
          resultOpinion += ' ' + (i + 1) + '. ' + children[i].opinion;
          resultOpinion;
        }
        if (children[i].isPass == '1') {
          resultOpinion += ' ' + (i + 1) + '、通过;';
        }
      }
      return resultOpinion;
    },
    showMaxDate(children) {
      let maxDate = children[0].time;
      for (var i = children.length - 1; i >= 0; i--) {
        if (new Date(maxDate) > new Date(children[i].time)) {
          maxDate = children[i].time;
        }
      }
      return maxDate;
    },
    showResult(children) {
      let result = '-';
      let returnFlag = false;
      for (var i = children.length - 1; i >= 0; i--) {
        if (children[i].isPass == '0') {
          result = '已修改';
        }
        if (children[i].isPass == '1') {
          returnFlag = true;
        }
      }
      if (returnFlag) {
        return result;
      }
      return '';
    },
    showPassDate(children) {
      let passDate = '';
      for (var i = children.length - 1; i >= 0; i--) {
        if (children[i].isPass == '1') {
          passDate = children[i].time;
        }
      }
      return passDate;
    },
    showSignPath(children) {
      for (var i = children.length - 1; i >= 0; i--) {
        if (children[i].isPass == '1') {
          return children[i].signPath;
        }
      }
      return '';
    }
  }
};
</script>

<style lang="scss">
.A3 {
  width: 100%;
  .contentlist {
    background-color: #fff !important;
    li {
      span:first-child {
        display: block;
        white-space: nowrap;
      }
      span{
        text-align:right;
        img{
          width: 271px;
          height: 120px;
        }
      }
    }
    p {
      border-bottom: 2px solid #e5e5e5;
      padding: 30px 30px;
      font-size: 28px;
      color: #666;
    }
  }
}
</style>
