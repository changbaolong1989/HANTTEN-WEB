<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="cost-results matterForm" :disabled="disabled">
    <el-row :gutter="gutter">
      <el-col :span="24">
        <el-form-item label="上传多文件" style="margin-bottom:0;">
          <button type="button" class="el-button btn-upload" @click="clickFileList" :disabled="disabled">
            <span><i class="iconfont" style="font-size: 12px;color: #0099E8;margin-right: 2px;">&#xe614;</i>点击上传</span>
          </button>
          <input ref="fileList" multiple type="file" hidden="hidden" @change="changeFileList">
          <div class="file-list" v-for="(f,index) in form.fileList" :key="index">
            <el-input placeholder="请输入文件名称" v-model="f.name" :disabled="true" style="margin-top:5px;"></el-input>
            <i class="el-icon-close" @click="delFileList(index,f.relationId,f.materialPath)" title="删除该文件"></i>
          </div>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutter">
      <el-col :span="24">
        <el-form-item label="成果文件类型" style="margin-bottom:0;" prop="model">
          <el-radio-group v-model="form.model" @change="changeModel">
            <el-radio :label="0">自定义</el-radio>
            <el-radio :label="1">模板</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutter">
      <el-col :span="24">
        <el-form-item label="成果文件份数" prop="reportCopies">
          <el-input v-model="form.reportCopies" placeholder="请输入成果文件份数"></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutter" v-if="0 == form.model">
      <el-col :span="24">
        <el-form-item label="成果文件编号" prop="reportNum">
          <el-input v-model="form.reportNum" placeholder="请输入成果文件编号"></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutter" v-if="0 == form.model">
      <el-col :span="24">
        <el-form-item label="上传成果文件">
          <el-upload
            name="file"
            class="upload-demo"
            ref="upload"
            action="#"
            :show-file-list="false"
            :on-change="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :auto-upload="false"
            :data="form"
          >
            <button type="button" class="el-button btn-upload">
              <span>
                <i class="iconfont" style="font-size: 12px;color: #0099E8;margin-right: 2px;">&#xe614;</i>
                点击上传
              </span>
            </button>
          </el-upload>
          <el-input placeholder="请输入文件名称" v-model="form.materialName" :disabled="true" style="margin-top:5px;"></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutter" v-if='1 == form.model && currentTransmittedData.departmentId == "db5e76f9-9fbd-11e9-a036-0e411fd5cf91"'>
      <el-col :span="24">
        <el-radio-group v-model="form.template">
          <el-radio style="margin-top: 10px;" :label="item.value" v-for="(item, i) in radioArr" :key="i">{{ item.display }}</el-radio>
        </el-radio-group>
      </el-col>
    </el-row>
    <el-row :gutter="gutter" v-if='1 == form.model && currentTransmittedData.departmentId == "dea3cd5d-9fbd-11e9-a036-0e411fd5cf91"'>
      <el-col :span="24">
        <el-radio-group v-model="form.template">
          <el-radio style="margin-top: 10px;" :label="item.value" v-for="(item, i) in radioArr2" :key="i">{{ item.display }}</el-radio>
        </el-radio-group>
      </el-col>
    </el-row>

    <el-row :gutter="gutter" style="margin-top: 22px;" v-if="1 == form.model">
      <el-col :span="24">
        <component ref="template" v-bind:is="form.template" :transmittedData="currentTransmittedData"
       :data="otherData" :disabled="disabled"></component>
       </el-col>
    </el-row>
  </el-form>
</template>

<script>
import prepareTheBudget from './resultsFile/prepareTheBudget'; /* 编制概算 */
import prepareTheEstimate from './resultsFile/prepareTheEstimate'; /* 编制估算 */
import prepareTheSettlement from './resultsFile/prepareTheSettlement'; /* 编制结算 */
import preparaTheAccounts from './resultsFile/preparationOfFinalAccounts'; /* 编制决算 */
import prepareTenderClearanceReport from './resultsFile/prepareTenderClearanceReport'; /* 编制清标报告 */
import tenderControl from './resultsFile/tenderControl'; /* 招标控制价模版 */
import tenderList from './resultsFile/tenderList'; /* 招标清单模版（分包、承包）*/
import tenderControlTurnkey from './resultsFile/tenderControlTurnkey'; /* 招标控制价模版 （总包）*/
import budgeting from './resultsFile/budgeting'; /* 预算编制模板*/

import auditTheBudget from './auditFile/auditTheBudget'; /* 概算审核*/
import auditTheEstimate from './auditFile/auditTheEstimate'; /* 估算审核*/
import auditTheSettle from './auditFile/auditTheSettle'; /* 结算审核*/
import auditTheBudgeting from './auditFile/auditTheBudgeting'; /* 预算审核*/

import contractNotInvited from './opinionFile/contractNotInvited'; /* 合同审核意见（未招标）*/
import contractNotInvitation from './opinionFile/contractNotInvitation'; /* 合同审核意见（招标）*/
import contractNotSchedule from './opinionFile/contractNotSchedule'; /* 进度款审核意见 */
import contractNotAlteration from './opinionFile/contractNotAlteration'; /* 变更审核意见 */
import contractNotBudget from './opinionFile/contractNotBudget'; /* 预算审核意见 */

import tenderingAgent from './tenderingAgent'; /* 招标代理 */
import bidEvaluationReport from './bidEvaluationReport'; /* 评标书面报告 */

export default {
  props: ['transmittedData', 'disabled'],
  components: {
    t0: prepareTheBudget,
    t1: prepareTheEstimate,
    t2: prepareTheSettlement,
    t3: preparaTheAccounts,
    t4: prepareTenderClearanceReport,
    t5: tenderControl,
    t6: tenderList,
    t7: tenderControlTurnkey,
    t8: budgeting,
    t9: auditTheBudget,
    t10: auditTheEstimate,
    t11: auditTheSettle,
    t12: auditTheBudgeting,
    t13: contractNotInvited,
    t14: contractNotInvitation,
    t15: contractNotSchedule,
    t16: contractNotAlteration,
    t17: contractNotBudget,
    t18: tenderingAgent,/* 招标代理 */
    t19: bidEvaluationReport/* 评标书面报告 */
  },
  data() {
    return {
      currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy), // 传输数据的当前vue的备份
      otherData:{},
      gutter: 20, // 分栏间隔
      form: {
        model: 1,
        template: 't0',
        materialName: '',
        signPathFile: '',
        reportNum: '',
        reportCopies: '',
        fileList:[]// 多文件(新增)
      },
      rules:{
        model: [
          { required: true, message: '请选择成果文件类型', trigger: 'change' }
        ],
        reportNum: [
          { required: true, message: '请输入成果文件编号', trigger: 'blur' }
        ],
        reportCopies: [
          { max: 10, message: '最长10位', trigger: 'blur' },
          { validator: this.integer, trigger: 'blur' },
          { validator: this.positiveNumber, trigger: 'blur' }
        ],
      },
      filedata: '',
      fileList: [],
      radioArr: [
        {
          display: '编制概算',
          value: 't0'
        },
        {
          display: '编制估算',
          value: 't1'
        },
        {
          display: '编制结算',
          value: 't2'
        },
        {
          display: '编制决算',
          value: 't3'
        },
        {
          display: '概算审核',
          value: 't9'
        },
        {
          display: '估算审核',
          value: 't10'
        },
        {
          display: '结算审核',
          value: 't11'
        },
        {
          display: '预算审核',
          value: 't12'
        },
        {
          display: '编制清标报告',
          value: 't4'
        },
        {
          display: '预算编制模板',
          value: 't8'
        },
        {
          display: '招标控制价模版',
          value: 't5'
        },{
          display: '进度款审核意见',
          value: 't15'
        },
        {
          display: '招标清单模版（分包、承包）',
          value: 't6'
        },
        {
          display: '招标控制价模版（总包）',
          value: 't7'
        },
        {
          display: '合同审核意见（未招标）',
          value: 't13'
        },
        {
          display: '合同审核意见（招标）',
          value: 't14'
        }
      ],
      // '编制结算', '编制决算', '编制清标报告', '招标控制价模版', '招标清单模版（分包、承包）', '招标控制价模版（总包）', '预算编制模板'
      radioArr2: [
        {
          display: '招标代理',
          value: 't18'
        },{
          display: '评标书面报告',
          value: 't19'
        }
      ]
    };
  },
  watch: {
    transmittedData: {
      handler() {
        let vm = this;
        vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
       if(vm.currentTransmittedData.departmentId == "db5e76f9-9fbd-11e9-a036-0e411fd5cf91"){
         vm.form.template = 't0';
       }else{
         vm.form.template = 't18';
       }
         vm.otherData.form = this.currentTransmittedData['formworkContent']?JSON.parse(this.currentTransmittedData['formworkContent']):{};
         vm.otherData.projectName = this.currentTransmittedData['projectName']?this.currentTransmittedData['projectName']:'';
         vm.otherData.contractNum = this.currentTransmittedData['contractNum']?this.currentTransmittedData['contractNum']:'';
        // 查询数据
        vm.getDetailInfo();
      },
      immediate: true
    }
  },
  methods: {
    /**
     * @event 点击多文件上传
     */
    clickFileList(){
      let vm = this;
        vm.$nextTick(function () {
          vm.$refs['fileList'].click();
        });
    },
    /**
     * @event 改变多文件上传
     */
    changeFileList(e){
      let vm = this;
      for (let i = 0, len = e.target.files.length; i < len; i++) {
        vm.form.fileList.unshift(e.target.files[i]);
      }
    },
    /**
     * @method 删除多文件
     */
    delFileList(index,relationId,materialPath){
      let vm = this;
      if(vm.disabled)return;
      vm.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {

        vm.form.fileList.splice(index,1);

        // 如果有文件路径，则删除数据库的文件
        if(materialPath){
          vm.apiAddress('/task/delete/deleteTaskPeriodMaterialInfo', {
            taskId:vm.currentTransmittedData.taskId,
            relationId: relationId,
            materialPath: materialPath
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              // 查询文件列表
              // vm.getDetailInfo();
              vm.$router.go(0);
            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e)
          })
        }
      }).catch(() => {});
    },
    getDetailInfo() {
      let vm = this;
      if (!vm.tool.isNull(vm.currentTransmittedData.taskType)) {
        vm.form.model = Number(vm.currentTransmittedData.taskType);
      }
      if (!vm.tool.isNull(vm.currentTransmittedData.formworkType)) {
        vm.form.template = 't' + vm.currentTransmittedData.formworkType;
      }

      // 根据任务阶段ID查询阶段关联文件
      this.apiAddress('/task/query/searchOutcomeDocumentMaterial', {
        taskId: vm.currentTransmittedData.taskId,
        relationId: vm.currentTransmittedData.relationId
      }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            vm.form.fileList = [];
            let tempTaskInfo = res.data.dataVO;
            // 如果任务文件类型为模板
            if (tempTaskInfo.taskType === '0') {
              vm.form.materialName = tempTaskInfo.taskMaterialName;// 任务文件名称
              vm.form.reportNum = tempTaskInfo.reportNum; // 成果文件编号
            }
            let paramObj;
            for(let i = 0; i<res.data.fileList.length; i++){
              paramObj = JSON.parse(JSON.stringify(res.data.fileList[i]).replace(/materialName/g, "name"));// 把materialName替换成name
              vm.form.fileList.push(paramObj);
            }
            vm.form.reportCopies = tempTaskInfo.reportCopies; // 成果文件份数
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          this.stateDialog('error', e);
        });
    },
    changeModel(val) {
      // 切换模式-自定义或者模板
      let vm = this;
      if(1 == vm.form.model && vm.currentTransmittedData.departmentId == "db5e76f9-9fbd-11e9-a036-0e411fd5cf91"){
        // 重置模板radio选项
        vm.form.template = 't0';
      }else{
        // 重置模板radio选项
        vm.form.template = 't18';
      }

    },
    // 新建时存储公告文件
    beforeAvatarUpload(file, fileList,id) {
      this.filedata = file;
      return false;
    },
    // 新建公告文件名称展示
    handleAvatarSuccess(file, fileList) {
      // 修改上传成果文件的文件路径
      this.form.signPathFile = URL.createObjectURL(file.raw);
      // 修改上传成果文件的名称
      this.form.materialName = file.name;
    }
  }
};
</script>

<style lang="scss">
.cost-results {
  .matterForm {
    border-radius: 5px;
    padding: 10px;
    background-color: #edf1f4;
    margin-bottom: 10px;
  }

  /* 文件列表 */
  .file-list{
    position: relative;

    /* 删除文件图标 */
    .el-icon-close{
      position: absolute;
      right: 10px;
      top: 15px;
      cursor: pointer;
    }
  }
}
</style>
