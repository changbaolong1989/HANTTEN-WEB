<template>
  <div class="dialogUpload" @click.stop="() => {}">
    <!-- 弹窗 -->
    <el-dialog
      title="新增"
      :visible="show"
      width="50%"
      center
      :before-close="beforeClose"
      :modal-append-to-body="true"
      @open="open"
      :class="{ zindex1: zindex1, zindex2: zindex2 }"
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :status-icon="true" label-width="80px" style="text-align: left;" :rules="rules">
        <div v-if="(!periodTypeBoolean && !currentUploadOptions) || (currentUploadOptions && !currentUploadOptions.isInvoice && !currentUploadOptions.isPayMoney)">
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="文件类型" prop="select">
                <el-select v-model="form.select" placeholder="请选择文件类型">
                  <el-option v-for="(item, i) in options" :key="i" :label="item.label" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <div v-if="periodType == '1'">
            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="原件/复印件" prop="type">
                  <el-radio-group v-model="form.type">
                    <el-radio label="原件"></el-radio>
                    <el-radio label="复印件"></el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="份数" prop="num"><el-input-number v-model="form.num" :min="1" :max="100000"></el-input-number></el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="接收人" prop="recipient"><el-input v-model="form.recipient" placeholder="请输入接收人"></el-input></el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="接收时间" prop="recipientDate"><el-date-picker value-format="yyyy-MM-dd" v-model="form.recipientDate" type="date" placeholder="选择日期"></el-date-picker></el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="gutter">
              <el-col :span="12">
                <el-form-item label="备注" prop="remark"><el-input v-model="form.remark" type="textarea"></el-input></el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>

        <el-row :gutter="gutter" v-if="currentUploadOptions && currentUploadOptions.isInvoice">
          <el-col :span="24" v-if="title!='上传发票'">
            <el-form-item label="发票金额" prop="money"><el-input v-model="form.money"></el-input></el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter" v-if="currentUploadOptions && currentUploadOptions.isPayMoney">
          <el-col :span="24">
            <el-form-item label="打款金额" prop="payMoney"><el-input v-model="form.payMoney"></el-input></el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item :label="currentUploadOptions && currentUploadOptions.isInvoice ? '发票文件' : '项目文件'">
              <el-upload
                ref="upload1"
                class="upload-demo"
                :limit="1"
                action="#"
                :file-list="fileList"
                :http-request="httpRequest"
                :on-exceed="handleExceed"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" @click="submit" style="padding: 0 45px;">确定提交</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ['options', 'uploadOptions', 'periodTypeBoolean', 'periodType','title'],
  mounted() {},
  data() {
    return {
      zindex1: true, // ie兼容调整
      zindex2: false, // ie兼容调整
      show: false,
      limit: (this.uploadOptions && this.uploadOptions.limit) || 1, //上传文件数量
      gutter: 20,
      formData:new FormData(),
      form: {
        select: '' /* 文件 类型*/,
        money: '', // 发票金额
        payMoney: '', //打款金额
        remark: '', //备注
        num: 1 /*文件份数 */,
        type: '原件' /*原件/复印件 */,
        recipient: '', /* 接收人 */
        recipientDate:"",/* 接收时间 */
      },
      fileList: [], // 文件列表
      fileName: '',
      currentUploadOptions: this.tool.deepCopy(this.uploadOptions, this.tool.deepCopy),
      rules: {
        select: [{ required: true, message: '请选择文件类型', trigger: 'change' }],
        num:[{ required: true, message: '请选择原件/复印件', trigger: 'change' }],
        type:[{ required: true, message: '请选择文件类型', trigger: 'change' }],
        recipient:[{ required: true, message: '请输入接收人', trigger: 'blur' }],
        recipientDate:[{ required: true, message: '请选择接收时间', trigger: 'blur' }],
        money: [{ required: true, message: '请输入发票金额', trigger: 'blur' }, { validator: this.moneyCheck, trigger: 'blur' }],
        payMoney: [{ required: true, message: '请输入打款金额', trigger: 'blur' }, { validator: this.moneyCheck, trigger: 'blur' }],
        remark: [{ max: 300, message: '最长300字', trigger: 'blur' }]
      }
    };
  },
  watch: {
    uploadOptions: {
      handler() {
        let vm = this;
        vm.currentUploadOptions = vm.uploadOptions;
      },
      immediate: true
    }
  },
  methods: {
    handleExceed(files, fileList) {
      // 文件上传设置
      let vm = this;
      this.$message.warning(`当前限制选择 ${vm.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeClose() {
      // 对话框关闭前回调
      let vm = this;
      vm.show = false;
      vm.reset();
    },
    submit() {
      // 提交方法
      let vm = this;
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.$refs.upload1.uploadFiles.length) {
            vm.show = false;
            let form = vm.formData;
            if (vm.currentUploadOptions && vm.currentUploadOptions.isInvoice) {
              form.append('money', vm.form.money);
            } else {
              form.append('select', vm.form.select);
            }
            if (vm.currentUploadOptions && vm.currentUploadOptions.isPayMoney) {
              form.append('payMoney', vm.form.payMoney);
            }
            if (vm.periodType === '1') {
              form.append('remark', vm.form.remark);/* 备注 */
              form.append('num', vm.form.num);/* 份数 */
              form.append('copyFile', vm.form.type);/* 原件/复印件 */
              form.append('recipient', vm.form.recipient);/* 接收人 */
              form.append('recipientDate', vm.form.recipientDate);/* 接收时间 */
            }
            vm.$emit('submit', form);
            vm.reset();
          } else {
            vm.$message.error('请选择上传文件');
          }
        }
      });
    },
    httpRequest(request) {
      // 自定义上传请求
      let vm = this;
      vm.formData.append('file', request.file);
    },
    open() {
      // 打开回调
      let vm = this;
      /* 这个页面 IE浏览器下,对话框弹窗会再显示消失,动态更改el-dialog的z-index,可以显示对话框*/
      let count = 0;
      let interval = setInterval(function() {
        vm.zindex1 = !vm.zindex1;
        vm.zindex2 = !vm.zindex2;
        count++;
        if (100 == count) {
          clearInterval(interval);
        }
      }, 1000);
    },
    reset() {
      // 清空数据
      let vm = this;
      vm.$refs['form'].resetFields(); // 重置表单
      vm.fileList = []; // 清空文件列表
      vm.formData = new FormData();
    }
  }
};
</script>
<style lang="scss"></style>
<style lang="scss" scoped></style>
