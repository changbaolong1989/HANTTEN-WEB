<template>
  <!-- 招标代理-->
  <div class="prepareTenderClearanceReport">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <mt-cell title="项目名称 :">
            <span>{{templateform.projectName }}</span>
          </mt-cell>
        </div>
        <div class="form-item">
          <mt-cell title="合同编号 :">
            <span>{{contractNum }}</span>
          </mt-cell>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>报告编号</label>
          <input type="text" placeholder="请输入报告编号" v-model="templateform.reportNo"  v-validate="'required'" name="reportNo" data-vv-as="报告编号" />
        </div>
      </div>
      <div class="block">
        <p>一、招标项目概况</p>
        <div class="form-item">
          <label>招标项目名称</label>
          <input type="text" placeholder="请输入招标项目名称" v-model="templateform.biddingProjectName"  v-validate="'required'" name="biddingProjectName" data-vv-as="招标项目名称" />
        </div>
        <div class="form-item">
          <label>招标人名称</label>
          <input type="text" placeholder="请输入招标人名称" v-model="templateform.tendereeName"  v-validate="'required'" name="tendereeName" data-vv-as="招标人名称" />
        </div>
        <div class="form-item">
          <label>招标代理机构名称</label>
          <input type="text" placeholder="请输入招标代理机构名称" v-model="templateform.tenderAgencyName"  v-validate="'required'" name="tenderAgencyName" data-vv-as="招标代理机构名称" />
        </div>
        <div class="form-item">
          <label>招标代理机构地址</label>
          <input type="text" placeholder="请输入招标代理机构地址" v-model="templateform.tenderAgencyAddress"  v-validate="'required'" name="tenderAgencyAddress" data-vv-as="招标代理机构地址" />
        </div>
        <div class="form-item">
          <label>招标范围</label>
          <textarea name="" id="area" placeholder="请输入招标范围" v-model="templateform.tenderScope" v-validate="'required'" name="tenderScope" data-vv-as="招标范围" ></textarea>
        </div>
      </div>
      <div class="block">
        <p>二、招标项目概况</p>
        <div class="form-item">
          <label>内容</label>
          <textarea name="" id="area" placeholder="请输入内容" v-model="templateform.tenderOrganizationContent" v-validate="'required'" name="tenderOrganizationContent" data-vv-as="内容"></textarea>
        </div>
      </div>
      <div class="block">
        <p>三、招标方式</p>
        <div class="form-item">
          <label>内容</label>
          <textarea name="" id="area" placeholder="请输入内容" v-model="templateform.tenderMethodContent" v-validate="'required'" name="tenderMethodContent" data-vv-as="内容"></textarea>
        </div>
      </div>
      <div class="block">
        <p>四、招标工作综述</p>
        <div class="form-item">
          <label>招标公告</label>
          <textarea name="" id="area" placeholder="请输入招标公告" v-model="templateform.tenderNotice" v-validate="'required'" name="tenderNotice" data-vv-as="招标公告"></textarea>
        </div>
        <div class="form-item">
          <label>投标报名</label>
          <textarea name="" id="area" placeholder="请输入投标报名" v-model="templateform.biddingRegistration" v-validate="'required'" name="biddingRegistration" data-vv-as="投标报名"></textarea>
        </div>
        <div class="form-item">
          <label>招标文件发售</label>
          <textarea name="" id="area" placeholder="请输入招标文件发售" v-model="templateform.biddingDocumentsForSale" v-validate="'required'" name="biddingDocumentsForSale" data-vv-as="招标文件发售"></textarea>
        </div>
        <div class="form-item">
          <label>现场踏勘</label>
          <textarea name="" id="area" placeholder="请输入现场踏勘" v-model="templateform.siteSurvey" v-validate="'required'" name="siteSurvey" data-vv-as="现场踏勘"></textarea>
        </div>
        <div class="form-item">
          <label>评标委员会组建</label>
          <textarea name="" id="area" placeholder="请输入评标委员会组建" v-model="templateform.establishmentOfTheBidEvaluationCommittee" v-validate="'required'" name="establishmentOfTheBidEvaluationCommittee" data-vv-as="评标委员会组建"></textarea>
        </div>
        <div class="form-item">
          <label>开标组织文件</label>
          <textarea name="" id="area" placeholder="请输入开标组织文件" v-model="templateform.bidOpeningDocument" v-validate="'required'" name="bidOpeningDocument" data-vv-as="开标组织文件"></textarea>
        </div>
        <div class="form-item">
          <label>评标过程</label>
          <textarea name="" id="area" placeholder="请输入评标过程" v-model="templateform.bidEvaluationProcess" v-validate="'required'" name="bidEvaluationProcess" data-vv-as="评标过程"></textarea>
        </div>
        <div class="form-item">
          <mt-cell title="添加投标人 ">
            <span @click="addlist()">添加</span>
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
          <div class="DynamicallyAdd" v-for="(item, index) in templateform.bidderList" :key="index">
            <i class="iconfont iconshanchu" @click="removeSwipe(index)"></i>
            <div class="form-item">
              <label for="">排名次序</label>
              <input type="text" placeholder="请输入排名次序" v-model="item.rankingOrder"  v-validate="'required'" name="rankingOrder" data-vv-as="排名次序"/>
            </div>
            <div class="form-item">
              <label for="">投标人名称</label>
              <input type="text" placeholder="请输入投标人名称" v-model="item.bidderName"  v-validate="'required'" name="bidderName" data-vv-as="投标人名称"/>
            </div>
            <div class="form-item">
              <label for="">评标得分</label>
              <input type="text" placeholder="请输入评标得分" v-model="item.bidEvaluation"  v-validate="'required'" name="bidEvaluation" data-vv-as="评标得分"/>
            </div>
          </div>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>中标公示</label>
          <textarea name="" id="area" placeholder="请输入中标公示" v-model="templateform.successfulBidding" v-validate="'required'" name="successfulBidding" data-vv-as="中标公示"></textarea>
        </div>
        <div class="form-item">
          <label>中标结果</label>
          <textarea name="" id="area" placeholder="请输入中标结果" v-model="templateform.winningResult" v-validate="'required'" name="winningResult" data-vv-as="中标结果"></textarea>
        </div>
        <div class="form-item">
          <label>合同签订</label>
          <textarea name="" id="area" placeholder="请输入合同签订" v-model="templateform.signAContract" v-validate="'required'" name="signAContract" data-vv-as="合同签订"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>公司名称</label>
          <input type="text" placeholder="请输入公司名称" v-model="templateform.companyName"  v-validate="'required'" name="companyName" data-vv-as="公司名称"/>
        </div>
        <div class="form-item">
          <mt-cell title="日期">
            <input type="text" @click="openPicker('picker')" v-model="templateform.date" :disabled="false"  v-validate="'required'" name="date" data-vv-as="日期">
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('date')"></mt-datetime-picker>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      templateform:{
        projectName:this.projectName,
        reportNo: '', // 报告编号
        biddingProjectName: '', // 招标项目名称
        tendereeName: '', // 招标人名称
        tenderAgencyName: '', // 招标代理机构名称
        tenderAgencyAddress: '', // 招标代理机构地址
        tenderScope: '', // 招标范围
        tenderOrganizationContent: '', // 内容(招标项目概况)
        tenderMethodContent: '', // 内容(招标方式)
        tenderNotice: '', // 招标公告
        biddingRegistration: '', // 投标报名
        biddingDocumentsForSale: '', // 招标文件发售
        siteSurvey: '', // 现场踏勘
        establishmentOfTheBidEvaluationCommittee: '', // 评标委员会组建
        bidOpeningDocument: '', // 开标组织文件
        bidEvaluationProcess: '', // 评标过程
        bidderList:[],
        successfulBidding: '', // 中标公示
        winningResult: '', // 中标结果
        signAContract: '', // 合同签订
        companyName: '', // 公司名称
        date: '' // 日期
      },
      pickerVisible: new Date()
    };
  },
  watch:{
    templateform:{
      handler(val){
        this.templateform = val;
      },
      immediate: true,
      deep:true
    }
  },
  props: ['contractNum','projectName'],
  methods: {
    openPicker(text) {
      this.$refs[text].open();
    },
    handleConfirm(text) {
      this.$set(this.templateform,text,this.formatting(this.pickerVisible))
    },
    addlist() {
      /* 动态添加 */
      console.log("bidderList",this.templateform.bidderList)
      this.templateform.bidderList.push({
        // 投标人列表
        bidderName: '', // 投标人名称
        rankingOrder: '', // 排名次序
        bidEvaluation: '' // 评标得分
      });
    },
    removeSwipe(index) {
      /* 动态删除 */
      this.templateform.bidderList.splice(index, 1);
    }
  }
};
</script>

<style scoped lang="scss"></style>
