<template>
  <!-- 磋商文件确认文件意见 -->
  <div class="cost-of__unfilled-notice">
    <div class="form-block">
      <el-form class="form" ref="form" :model="form" :disabled="disabled" :rules="rules">
        <el-row :gutter="gutter">
          <el-col :span="18" style="padding-left: 10px;">未成交通知书</el-col>
          <el-col :span="6" class="text-align-right">
            <el-button size="mini" type="primary" @click="submit">保存</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称">
              <el-input v-model="form.projectName" placeholder="请输入项目名称" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编号">
              <el-input v-model="form.projectNum" placeholder="请输入项目编号" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="投标单位名称" prop="tenderUnitName">
              <el-input v-model="form.tenderUnitName" placeholder="请输入投标单位名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" v-for="(item,i) in form.bidderList" :key="i">
          <el-col :span="12">
            <el-form-item label="投标人名称" :prop="'bidderList.' + i + '.tenderUserName'" :rules="[{ required: true, message: '请输入投标人名称', trigger: 'blur' }]">
              <el-input v-model="item.tenderUserName" placeholder="请输入投标人名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="排名次序" :prop="'bidderList.' + i + '.sort'" :rules="[{ required: true, message: '请输入排名次序', trigger: 'blur' }]">
              <el-input v-model="item.sort" placeholder="请输入排名次序"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="评标得分" :prop="'bidderList.' + i + '.bidScore'" :rules="[{ required: true, message: '请输入评标得分', trigger: 'blur' }]">
              <el-input v-model="item.bidScore" placeholder="请输入评标得分"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="1">
            <i class="el-icon-close" style="margin-top: 38px;font-size: 16px;" @click="delBidder(i)" v-if="!disabled"></i>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-button size="mini" type="primary" @click="addBidder">+&nbsp;添加投标人</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="采购代理机构" prop="agentOrg">
              <el-input v-model="form.agentOrg" placeholder="请输入采购代理机构"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日期" prop="editDate">
              <el-date-picker v-model="form.editDate" value-format="yyyy-MM-dd" type="date" placeholder="请输入日期"></el-date-picker>
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
       * @method 提交表单
       */
      submit() {
       let vm = this;
       vm.$emit('submit',vm.getFormData('form'));
      },
      /**
       * @method 获取表单数据
       * @param {String} formName 表单名称
       */
      getFormData(formName) {
        let vm = this;
        return new Promise(function(resolve, reject) {
          vm.$refs[formName].validate((valid) => {
            if (valid) {
              resolve(vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
            } else {
              reject(null);
            }
          });
        });
      },
      /**
       * @method 设置表单数据
       * @param {Object} data 表单数据
       */
      setFormData(data) {
        let vm = this;
        vm.form = vm.tool.deepCopy(data?data.form:vm.source.form, vm.tool.deepCopy);
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
  @import '../common/scss/exclusive.scss';
  .cost-of__unfilled-notice {
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
