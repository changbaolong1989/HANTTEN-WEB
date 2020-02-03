<template>
  <!-- 评标书面报告-->
  <div class="prepareTenderClearanceReport">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <mt-cell title="项目名称 :">
            <span>{{templateform.projectName }}</span>
          </mt-cell>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>采购人</label>
          <input type="text" placeholder="请输入采购人" v-model="templateform.purchaser" v-validate="'required'" name="purchaser" data-vv-as="采购人" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>采购代理机构</label>
          <input type="text" placeholder="请输入采购代理机构" v-model="templateform.purchasingAgency" v-validate="'required'" name="purchasingAgency" data-vv-as="采购代理机构"  />
        </div>
        <div class="form-item">
          <label>招标描述</label>
          <textarea name="" id="area" placeholder="请输入招标描述" v-model="templateform.biddingDescription" v-validate="'required'" name="biddingDescription" data-vv-as="招标描述" ></textarea>
        </div>
      </div>
      <div class="block">
        <p>一、第一项</p>
        <div class="form-item">
          <label>内容</label>
          <textarea name="" id="area" placeholder="请输入内容" v-model="templateform.firstItemContent" v-validate="'required'" name="firstItemContent" data-vv-as="内容"></textarea>
        </div>
      </div>
      <div class="block">
        <p>二、第二项</p>
        <div class="form-item">
          <label>内容</label>
          <textarea name="" id="area" placeholder="请输入内容" v-model="templateform.secondSectionContent" v-validate="'required'" name="secondSectionContent" data-vv-as="内容"></textarea>
        </div>
      </div>
      <div class="block">
        <p>三、评标程序及情况</p>
        <div class="form-item">
          <label>组建评标委员会</label>
          <textarea name="" id="area" placeholder="请输入组建评标委员会" v-model="templateform.establishingABidEvaluationCommittee" v-validate="'required'" name="establishingABidEvaluationCommittee" data-vv-as="组建评标委员会"></textarea>
        </div>
        <div class="form-item">
          <label>熟悉招标文件</label>
          <textarea name="" id="area" placeholder="请输入熟悉招标文件" v-model="templateform.familiarWithBiddingDocuments" v-validate="'required'" name="familiarWithBiddingDocuments" data-vv-as="熟悉招标文件"></textarea>
        </div>
        <div class="form-item">
          <label>评审</label>
          <textarea name="" id="area" placeholder="请输入评审" v-model="templateform.review" v-validate="'required'" name="review" data-vv-as="评审"></textarea>
        </div>
        <div class="form-item">
          <label>评标结果</label>
          <textarea name="" id="area" placeholder="请输入评标结果" v-model="templateform.bidEvaluationResult" v-validate="'required'" name="bidEvaluationResult" data-vv-as="评标结果"></textarea>
        </div>
        <div class="form-item">
          <mt-cell title="添加投标人">
            <span @click="addlist">添加</span>
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
          <div class="DynamicallyAdd" v-for="(item, index) in templateform.bidderList" :key="index">
            <i class="iconfont iconshanchu" @click="removeSwipe(index)"></i>
            <div class="form-item">
              <label for="">投标人名称</label>
              <input type="text" placeholder="请输入投标人名称" v-model="item.bidderName"  v-validate="'required'" name="bidderName" data-vv-as="投标人名称"/>
            </div>
            <div class="form-item">
              <label for="">排名次序</label>
              <input type="text" placeholder="请输入排名次序" v-model="item.rankingOrder"  v-validate="'required'" name="rankingOrder" data-vv-as="排名次序"/>
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
          <label>推荐公司名称</label>
          <input type="text" placeholder="请输入推荐公司名称" v-model="templateform.recommendedCompanyName" v-validate="'required'" name="recommendedCompanyName" data-vv-as="推荐公司名称" />
        </div>
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
      templateform: {
        projectName:this.projectName,
        purchaser: "", // 采购人
        purchasingAgency: "", // 采购代理机构
        biddingDescription: "", // 招标描述
        firstItemContent: "", // 内容(第一项)
        secondSectionContent: "", // 内容(第二项)
        establishingABidEvaluationCommittee: "", // 组建评标委员会
        familiarWithBiddingDocuments: "", // 熟悉招标文件
        review: "", // 评审
        bidEvaluationResult: "", // 评标结果
        bidderList: [],
        recommendedCompanyName: "", // 推荐公司名称
        companyName: "", // 公司名称
        date: "" // 日期
      },
      pickerVisible: new Date()
    };
  },
  props: ['contractNum','projectName'],
  methods: {
    openPicker(text) {
      this.$refs[text].open();
    },
    handleConfirm(text) {
      this.templateform[text] = this.formatting(this.pickerVisible);
    },
    addlist(text) {
      /* 动态添加 */
      this.templateform.bidderList.push({ // 投标人列表
          bidderName: "", // 投标人名称
          rankingOrder: "", // 排名次序
          bidEvaluation: "" // 评标得分
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
