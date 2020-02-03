<template>
  <!-- 项目 > 发出信函登记表 -->
  <div class="mh-set-role">

    <!-- 表单弹窗 -->
    <el-dialog :title="formTitle" :visible="true" width="500px" transition="fade-in-linear" center
               :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
        <el-form-item label="日期" prop="editDate">
          <el-date-picker
            v-model="ruleForm.editDate"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="ruleForm.fileName" placeholder="请输入文件名称"></el-input>
        </el-form-item>
        <el-form-item label="文件编号" prop="fileNumber">
          <el-input v-model="ruleForm.fileNumber" placeholder="请输入文件编号"></el-input>
        </el-form-item>
        <el-form-item label="发文形式" prop="sendType">
          <el-select v-model="ruleForm.sendType" placeholder="请选择发文形式">
            <el-option
              v-for="item in sendTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收件单位" prop="receiveUnit">
          <el-input v-model="ruleForm.receiveUnit" placeholder="请输入收件单位"></el-input>
        </el-form-item>
        <el-form-item label="收件人" prop="receivePeople">
          <el-input v-model="ruleForm.receivePeople" placeholder="请输入收件人"></el-input>
        </el-form-item>
        <el-form-item label="文件形式" prop="fileType">
          <el-select v-model="ruleForm.fileType" placeholder="请选择文件形式">
            <el-option
              v-for="item in fileTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="份数" prop="copyNum">
          <el-input v-model="ruleForm.copyNum" placeholder="请输入份数"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error">
          <el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input>
        </el-form-item>

        <el-form-item label="信函文件" class="upload upload1" prop="letterFileName">

          <el-upload
            class="avatar-uploader"
            ref="upload"
            action="#"
            :show-file-list="false"
            :on-change="handleAvatarSuccess"
            :auto-upload="false"
            :before-upload="beforeAvatarUpload"
            :data="ruleForm">
            <el-button><span class="iconfont">&#xe614;</span>上传附件</el-button>
            <br>
            <el-input placeholder="请上传信函文件" v-model="ruleForm.letterFileName" :disabled="true"></el-input>
          </el-upload>

        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitLetterfunc('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      const formdata = new FormData();
      return {
        projectId: this.$parent.projectId,//项目ID(从父级页面取值)
        ruleForm: {//所提交form表单(校验表单)
          letterId: '',//信函ID
          editDate: '',//日期
          fileName: '',//文件名称
          fileNumber: '',//文件编号
          sendType: '',//发文形式
          receiveUnit: '',//收件单位
          receivePeople: '',//收件人
          fileType: '',//文件形式
          copyNum: '',//份数
          remark: '',//备注
          file: '',//上传文件
          letterFileName: ''// 信函文件名称
        },
        //校验规则
        rules: {
          editDate: [{
            //必填
            required: true,
            message: '请选择日期',
            //触发方式:失去焦点
            trigger: 'blur'
          }],
          fileName: [{
            //必填
            required: true,
            message: '请输入文件名称',
            //触发方式:失去焦点
            trigger: 'blur'
          }, {
            max: 20,
            message: '最长20字',
            trigger: 'blur'
          }],
          fileNumber: [{
            //必填
            required: true,
            message: '请输入文件编号',
            //触发方式:失去焦点
            trigger: 'blur'
          }, {
            max: 20,
            message: '最长20字',
            trigger: 'blur'
          }],
          receiveUnit:[{
            max: 30,
            message: '最长30字',
            trigger: 'blur'
          }],
          receivePeople:[{
            max: 20,
            message: '最长20字',
            trigger: 'blur'
          }],
          copyNum:[{
            validator: this.integer,
            trigger: 'blur'
          }],
          remark:[{
            max: 300,
            message: '最长300字',
            trigger: 'blur'
          }],
          //上传文件名称
          letterFileName: [
            {
              //必填
              required: true,
              message: '请上传信函文件',
              //触发方式:失去焦点
              trigger: 'change'
            }],
        },
        //初始化上传参数载体(用以附带文件上传)
        formData: formdata,
        //dialog(弹出层)展示头
        formTitle: '',
        //表单提交路径
        url: '',

        // 发文形式选项
        sendTypeList: [{
          value: '1',
          label: '快递'
        }, {
          value: '2',
          label: '手递'
        }, {
          value: '3',
          label: '电邮'
        }, {
          value: '4',
          label: '传真'
        }],
        // 文件形式选项
        fileTypeList: [{
          value: '1',
          label: '纸版'
        }, {
          value: '2',
          label: '电子版'
        }, {
          value: '3',
          label: '纸版和电子版'
        }],
      }
    },
    methods: {
      //文件上传组件提交前钩子方法
      beforeAvatarUpload(file, fileList) { /* 个人签名 */
        this.formData.append("file", file);
        return false;
      },
      //文件上传组件提交成功后钩子方法
      handleAvatarSuccess(file, fileList) { /* 信函展示 */
        this.ruleForm.file = URL.createObjectURL(file.raw);
        this.ruleForm.letterFileName = file.name;
      },
      //新增信息先行方法
      addLetterFunc() {
        this.formTitle = '新增';
        this.url = '/wholeProcess/letter/add/saveWholeProcessLetter'
      },
      //dialog(弹出层)关闭方法
      handleClose(done) {
        this.$refs.ruleForm.resetFields();
        done();
        this.$parent.centerDialogVisible = false
      },
      //查询信息(修改信息先行方法)
      updateLetterFunc(row) {
        this.formTitle = '修改';
        this.url = '/wholeProcess/letter/modify/updateWholeProcessLetter';
        let formData = {
          letterId: row.letterId
        };
        const vm = this;
        vm.apiAddress('/wholeProcess/letter/query/searchProjectLetterPeriodInfo', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let dataVO = res.data.dataVO;
            //向表单赋值
            vm.ruleForm = {
              letterId: dataVO.letterId,
              editDate: dataVO.editDate,
              fileName: dataVO.fileName,
              fileNumber: dataVO.fileNumber,
              sendType: dataVO.sendType,
              receiveUnit: dataVO.receiveUnit,
              receivePeople: dataVO.receivePeople,
              fileType: dataVO.fileType,
              copyNum: dataVO.copyNum,
              remark: dataVO.remark,
              letterFileName: dataVO.letterFileName,
            }
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      formatDate(prop) {
        const date = new Date(prop);
        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return year + "-" + month + "-" + day
      },
      //表单提交
      submitLetterfunc() {
        const vm = this;
        this.$refs['ruleForm'].validate(valid => {
          if (valid) {

            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              vm.formData.append("letterId", vm.ruleForm.letterId);
              vm.formData.append("editDate", vm.formatDate(vm.ruleForm.editDate));
              vm.formData.append("projectId", vm.projectId);
              vm.formData.append("fileName", vm.ruleForm.fileName);
              vm.formData.append("fileNumber", vm.ruleForm.fileNumber);
              vm.formData.append("sendType", vm.ruleForm.sendType);
              vm.formData.append("receiveUnit", vm.ruleForm.receiveUnit);
              vm.formData.append("receivePeople", vm.ruleForm.receivePeople);
              vm.formData.append("fileType", vm.ruleForm.fileType);
              vm.formData.append("copyNum", vm.ruleForm.copyNum);
              vm.formData.append("remark", vm.ruleForm.remark);
              // 获取列表数据
              vm.$refs.upload.submit();
              vm.apiAddress(this.url, vm.formData).then(res => {
                /* 让弹窗隐藏 */
                vm.formData = new FormData();
                /* 清空formData数据*/
                if (res.data.status == 'failure') {
                  vm.$message({
                    message: res.data.message,
                    type: 'error',
                    center: true
                  });
                } else {
                  vm.$message({
                    message: res.data.message,
                    type: 'success',
                    center: true
                  });
                  vm.$parent.reflashData();
                  this.$parent.centerDialogVisible = false
                  /* 让弹窗隐藏 */
                  vm.ruleForm.file = '';
                }
              }).catch(err => {
                vm.$message({
                  message: err,
                  type: 'error',
                  center: true
                });
              });
              this.$refs["ruleForm"].resetFields();
            }).catch(() => {
            });

          }
        });
        /* 清空表单数据 */
      },
      /**
       * @method 文件上传设置
       * @param {Object} files
       * @param {Object} fileList
       */
      handleExceed(files, fileList) {
        let vm = this;
        this.$message.warning(`当前限制选择 ${vm.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
    }
  }
</script>

<style scoped>

</style>
