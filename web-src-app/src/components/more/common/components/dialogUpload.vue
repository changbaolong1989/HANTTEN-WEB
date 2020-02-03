<template>
  <div class="dialogUpload" v-show="show">
    <!-- 弹窗 -->
    <form class="projectForm" style="margin-top: 15px;">
      <div class="form-item" v-if="(!periodTypeBoolean &&!currentUploadOptions)
      ||(currentUploadOptions&&!currentUploadOptions.isInvoice&&!currentUploadOptions.isPayMoney&&!currentUploadOptions.radioFlag)||1==periodType
      ">
        <m-select
          v-model="form.select"
          :data="{title:'文件类型',options: this.options}"
          :unique="unique"
          :display="display"
          v-validate="'required'"
          data-vv-name="文件类型">
        </m-select>
      </div>
      <div class="form-item" v-if="currentUploadOptions && currentUploadOptions.isInvoice" >
        <label>发票金额</label>
        <input type="text" name="fieldName" placeholder="请输入发票金额" v-model="form.money" v-validate="'required'"
          data-vv-name="发票金额"/>
      </div>
      <div class="form-item" v-if="currentUploadOptions&&currentUploadOptions.isPayMoney" >
        <label>打款金额</label>
        <input type="text" placeholder="请输入打款金额" v-model="form.payMoney" v-validate="'required'"
          data-vv-name="打款金额"/>
      </div>
      <div class="form-item" v-if="1==periodType">
        <label>备注</label>
        <input type="text" placeholder="请输入备注" v-model="form.remark" v-validate="'required'"
          data-vv-name="备注"/>
      </div>
      <div class="form-item">
        <input ref="file" type="file" @change="tirggerFile($event)" style="display: none;"/>
        <span type="primary" class="linear btn-upload" @click.stop="uploadFile">上传{{ currentUploadOptions && currentUploadOptions.isInvoice ? '发票文件' : '项目文件' }}</span>
      </div>
      <ul class="file-list">
        <li class="file-item" v-for="(file, i) in currentFiles" :key="i">{{ file.materialName }}</li>
      </ul>
    </form>
    <footer class="commonFooter">
      <mt-button type="default" class="btn-submit" @click="show = false">取消</mt-button>
      <mt-button type="primary" class="linear btn-submit" @click="submit">确定</mt-button>
    </footer>
  </div>
</template>

<script>
export default {
  props: {
    // 文件类型的下拉数据(数组)
    options:{
      default(){
        return [];
      }
    },
    // 文件类型的下拉数据的唯一标识
    unique:{
      default(){
        return 'fileTypeId';
      }
    },
    // 文件类型的下拉数据的展示文字属性
    display:{
      default(){
        return 'fileTypeName';
      }
    },
    // 上传组件的一些配置项
    uploadOptions:{
      default(){
        return {
          isInvoice:false,// 是否是发票金额
          isPayMoney:false,// 是否是付款金额
          limit:10,// 上传文件数量
          radioFlag:true// 上传资料阶段
        };
      }
    },
    // 文件列表
     files:{
       default(){
         return [];
       }
     },
     periodTypeBoolean:{
       default(){
         return false;
       }
     },
     periodType:{
       default(){
         return '';
       }
     },
  },
  data() {
    return {
      form: {
        select: '',
        money: '' ,// 发票金额
        payMoney:'',// 打款金额
        remark:''// 备注
      },
      currentUploadOptions:this.tool.deepCopy(this.uploadOptions, this.tool.deepCopy),
      show: false,// 是否显示
      limit: (this.currentUploadOptions && this.uploadOptions.limit) || 10, //上传文件数量
      currentFiles: []// 文件列表
    };
  },
  watch: {
    uploadOptions:{
      handler() {
        let vm = this;
        vm.currentUploadOptions = vm.tool.deepCopy(vm.uploadOptions, vm.tool.deepCopy);
        vm.limit = (vm.currentUploadOptions && vm.uploadOptions.limit) || 10;
      },
      immediate: true,
      deep:true
    },
    files: {
      handler() {
        let vm = this;
        vm.currentFiles = [...vm.files];
      },
      immediate: true
    },
    show: {
      handler() {
        let vm = this;
        if (vm.show) {
          // 清空表单
          vm.reset();
        }
      },
      deep: true,
      immediate: true
    }
  },
  created() {
    console.log("periodType",this.periodType)
  },
  methods: {
    handleExceed(files, fileList) {
      // 文件上传设置
      let vm = this;
      this.$message.warning(`当前限制选择 ${vm.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submit() {
      // 提交方法
      let vm = this;
      vm.$validator.validate().then(valid => {
        if (!valid){
          vm.Toast(vm.errors.all()[0]);
        }else{
          let form = new FormData();
          if (vm.currentUploadOptions && vm.currentUploadOptions.isInvoice) {
            // 发票金额
            form.append('money', vm.form.money);
          } else {
            form.append('select', vm.form.select);
          }
          // 付款金额
          if(vm.currentUploadOptions&&vm.currentUploadOptions.isPayMoney){
            form.append('payMoney', vm.form.payMoney);
          }
          if(vm.periodType == "1"){
            form.append('remark', vm.form.remark);
          }
          form.append('file', vm.currentFiles[0]);
          if(!vm.currentFiles.length)return vm.Toast('请选择上传文件!');
          this.$emit('submit',{"form":form,"fileTypeId":vm.form.select});
          vm.show = false;
        }
      });
    },
    reset() {
      // 清空数据
      let vm = this;
      vm.form = {
        select: '', // 文件类型
        money: '' ,// 发票金额
        payMoney:''// 打款金额
      };
      vm.currentFiles = []; // 清空文件列表
    },
    uploadFile() {
      /* 上传文件 */
      let vm = this;
      this.$nextTick(function() {
        let elFile = vm.$refs['file'];
        elFile.click();
        elFile.value = '';
      });
    },
    tirggerFile(target) {
      let vm = this;
      let currentFiles = vm.currentFiles;
      let files = target.target.files;
      for (let i = 0, len = files.length; i < len; i++) {
        // 如果规定了最大上传数量
        if (vm.limit) {
          files[i].materialName = files[i].name;
          // 如果当前上传的文件列表没超过最大数
          if (currentFiles.length < vm.limit) {
            currentFiles.unshift(files[i]);
          } else if (currentFiles.length == vm.limit) {
            currentFiles.unshift(files[i]);
            currentFiles.pop();
          }
        } else {
          currentFiles.unshift(files[i]);
        }
      }
    }
  }
};
</script>
<style lang="scss">
@import url('../../../../common/vue/element-ui/packages/theme-chalk/lib/index.css');

.dialogUpload {
  * {
    box-sizing: border-box;
  }

  .btn-upload {
    padding: 10px;
    display: inline-block;
    margin-bottom: 30px;
    color: white;
  }
}
</style>
<style lang="scss" scoped>
.dialogUpload {
  position: fixed;
  top: 0;
  left: 0;
  background-color: white;
  height: 100vh;
  width: 100vw;
  z-index: 2000;

  .el-icon-plus {
    color: white;
    padding: 2px 6px;
    border-radius: 5px;
    margin: 10px 0;
  }

  .form-item {
    &:first-child {
      margin-top: 0;
    }
  }

  /* 文件列表 */
  .file-list {
    .file-item {
      padding: 10px 0;
    }
  }

  .projectForm {
    height: calc(100% - 100px);
    overflow: auto;
  }

  .btn-submit {
    margin: 15px 3%;
    width: 94%;
    height: 70px;
    font-size: 28px;
    color: white;
  }
}
</style>
