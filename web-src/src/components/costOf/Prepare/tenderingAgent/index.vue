<template>
  <!-- 招标代理 -->
  <div class="cost-of__tendering-agent">
    <div class="form-block">
      <el-form class="form" ref="form" :model="form" :disabled="disabled" :rules="rules">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称">
              <span>{{form.projectName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同编号">
               <span>{{form.contractNumber}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="报告编号" prop="reportNo">
              <el-input v-model="form.reportNo" placeholder="请输入报告编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" class="row--title">
          <el-col :span="24">
            一、招标项目概况
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="招标项目名称" prop="biddingProjectName">
              <el-input v-model="form.biddingProjectName" placeholder="请输入招标项目名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标人名称" prop="tendereeName">
              <el-input v-model="form.tendereeName" placeholder="请输入招标人名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="招标代理机构名称" prop="tenderAgencyName">
              <el-input v-model="form.tenderAgencyName" placeholder="请输入招标代理机构名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标代理机构地址" prop="tenderAgencyAddress">
              <el-input v-model="form.tenderAgencyAddress" placeholder="请输入招标代理机构地址"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="招标范围" prop="tenderScope">
              <el-input type="textarea" v-model="form.tenderScope" placeholder="请输入招标范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" class="row--title">
          <el-col :span="24">
            二、招标项目概况
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="内容" prop="tenderOrganizationContent">
              <el-input type="textarea" v-model="form.tenderOrganizationContent" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" class="row--title">
          <el-col :span="24">
            三、招标方式
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="内容" prop="tenderMethodContent">
              <el-input type="textarea" v-model="form.tenderMethodContent" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" class="row--title">
          <el-col :span="24">
            四、招标工作综述
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="招标公告" prop="tenderNotice">
              <el-input type="textarea" v-model="form.tenderNotice" placeholder="请输入招标公告"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="投标报名" prop="biddingRegistration">
              <el-input type="textarea" v-model="form.biddingRegistration" placeholder="请输入投标报名"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="招标文件发售" prop="biddingDocumentsForSale">
              <el-input type="textarea" v-model="form.biddingDocumentsForSale" placeholder="请输入招标文件发售"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="现场踏勘" prop="siteSurvey">
              <el-input type="textarea" v-model="form.siteSurvey" placeholder="请输入现场踏勘"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="评标委员会组建" prop="establishmentOfTheBidEvaluationCommittee">
              <el-input type="textarea" v-model="form.establishmentOfTheBidEvaluationCommittee" placeholder="请输入评标委员会组建"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="开标组织文件" prop="bidOpeningDocument">
              <el-input type="textarea" v-model="form.bidOpeningDocument" placeholder="请输入开标组织文件"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="评标过程" prop="bidEvaluationProcess">
              <el-input type="textarea" v-model="form.bidEvaluationProcess" placeholder="请输入评标过程"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter" v-for="(item,i) in form.bidderList" :key="i">
          <el-col :span="6">
            <el-form-item label="排名次序" :prop="'bidderList.' + i + '.rankingOrder'" :rules="{ required: true, message: '请输入排名次序', trigger: 'blur' }">
              <el-input v-model="item.rankingOrder" placeholder="请输入排名次序"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="投标人名称" :prop="'bidderList.' + i + '.bidderName'" :rules="{ required: true, message: '请输入投标人名称', trigger: 'blur' }">
              <el-input v-model="item.bidderName" placeholder="请输入投标人名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="评标得分" :prop="'bidderList.' + i + '.bidEvaluation'" :rules="{ required: true, message: '请输入评标得分', trigger: 'blur' }">
              <el-input v-model="item.bidEvaluation" placeholder="请输入评标得分"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <i class="el-icon-close" style="margin-top: 38px;font-size: 16px;" @click="delBidder(i)"></i>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-button size="mini" type="primary" @click="addBidder">+&nbsp;添加投标人</el-button>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="中标公示" prop="successfulBidding">
              <el-input type="textarea" v-model="form.successfulBidding" placeholder="请输入中标公示"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="中标结果" prop="winningResult">
              <el-input type="textarea" v-model="form.winningResult" placeholder="请输入中标结果"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="合同签订" prop="signAContract">
              <el-input type="textarea" v-model="form.signAContract" placeholder="请输入合同签订"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="公司名称" prop="companyName">
              <el-input v-model="form.companyName" placeholder="请输入公司名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日期" prop="date">
              <el-date-picker v-model="form.date" type="date" placeholder="请输入日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </div>
  </div>
</template>

<script>
  import dataJs from "./data.js"
  export default {
    props: {
      // 传输数据
      data: {
        type: Object,
        default () {
          return {
            // 默认表单数据
            form: this.tool.deepCopy(dataJs.form, this.tool.deepCopy),
          };
        }
      },
      // 是否禁用表单
      disabled: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        source: dataJs, // 源数据
        gutter: 20,
        // 表单数据
        form: this.tool.deepCopy(this.data?this.data.form:dataJs.form, this.tool.deepCopy),
        // 表单验证
        rules: this.tool.deepCopy(dataJs.rules, this.tool.deepCopy)
      }
    },
    watch: {
      data: {
        handler() {
          let vm = this;
          // 设置表单数据
          vm.setFormData(vm.data);
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 获取表单数据
       * @param {String} formName 表单名称
       * @param {Boolean} noVerify 不验证表单(默认有表单校验)
       */
      getFormData(formName,noVerify) {
        let vm = this;
        return new Promise(function(resolve, reject) {
         if(noVerify){
            resolve(vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
         }else{
           vm.$refs[formName].validate((valid) => {
             if (valid) {
               resolve(vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
             } else {
               reject(null);
             }
           });
         }
        });
      },
      /**
       * @method 设置表单数据
       * @param {Object} data 表单数据
       */
      setFormData(data) {
        let vm = this;
        vm.form = vm.tool.deepCopy(data?data.form:vm.source.form, vm.tool.deepCopy);
        vm.form.projectName =data?data.projectName:'';
        vm.form.contractNumber = data?data.contractNum:'';
      },
      /**
       * @method 重置数据
       */
      reset() {
        let vm = this;
        // 重置表单数据
        vm.form = vm.tool.deepCopy(vm.source.form, vm.tool.deepCopy);
      },
      /**
       * @event 添加投标人
       */
      addBidder(){
        let vm = this;
        // 投标人
        let bidder = vm.tool.deepCopy(vm.source.form.bidderList[0], vm.tool.deepCopy);
        if(!vm.form.bidderList)vm.$set( vm.form,'bidderList',[]);
        // 添加投标人
        vm.form.bidderList.push(bidder);
      },
      /**
       * @event 刪除投标人
       */
      delBidder(index){
        let vm = this;
        // 刪除投标人
        vm.form.bidderList.splice(index,1);
      }
    }
  }
</script>

<style lang="scss">
  @import '../../common/scss/exclusive.scss';
  .cost-of__tendering-agent {
    margin-top: 10px;

    * {
      box-sizing: border-box;
    }

    .el-icon-close{

      &:hover{
        color: #F56C6C;
      }
    }
  }
</style>
