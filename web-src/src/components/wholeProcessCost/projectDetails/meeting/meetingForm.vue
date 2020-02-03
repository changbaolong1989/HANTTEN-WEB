<template>
  <!-- 项目 > 招标管理 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <!-- 表单弹窗 -->
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
        <el-form-item label="会议主题" prop="meetingTheme">
          <el-input v-model="ruleForm.meetingTheme" placeholder="请输入会议主题"></el-input>
        </el-form-item>
        <el-form-item label="会议地点" prop="address">
          <el-input v-model="ruleForm.address" placeholder="请输入会议地点"></el-input>
        </el-form-item>
        <el-form-item label="参与单位" prop="participatoryUnit">
          <el-input v-model="ruleForm.participatoryUnit" placeholder="请输入参与单位"></el-input>
        </el-form-item>
        <el-form-item label="内容摘要" prop="contentSummary" class="tree-error">
          <el-input v-model="ruleForm.contentSummary" type="textarea" placeholder="请输入内容摘要"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="tree-error">
          <el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注"></el-input>
        </el-form-item>

        <el-form-item label="会议文件" class="upload upload1" prop="meetingFileName">

          <el-upload
              class="avatar-uploader"
              ref="upload"
              action="#"
              :show-file-list="false"
              :on-change="handleAvatarSuccess"
              :auto-upload="false"
              :before-upload="beforeAvatarUpload"
              :data="ruleForm" >
            <el-button><span class="iconfont">&#xe614;</span>上传附件</el-button>
            <br>
            <el-input placeholder="请上传会议文件" v-model="ruleForm.meetingFileName" :disabled="true"></el-input>
          </el-upload>

        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitMeetingFunc('ruleForm')">确 定</el-button>
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
          meetingId: '',//会议纪要ID
          editDate: '',//日期
          meetingTheme: '',//会议文件
          address: '',//地点
          contentSummary: '',//内容摘要
          participatoryUnit: '',//参与单位
          remark: '',//备注
          file: '',//上传文件
          meetingFileName: ''// 会议文件名称
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
          meetingTheme: [{
            //必填
            required: true,
            message: '请输入会议主题',
            //触发方式:失去焦点
            trigger: 'blur'
          }, {
            max: 30,
            message: '最长30字',
            trigger: 'blur'
          }],
          address:[{
            max: 30,
            message: '最长30字',
            trigger: 'blur'
          }],
          contentSummary:[{
            max: 300,
            message: '最长300字',
            trigger: 'blur'
          }],
          participatoryUnit:[{
            max: 100,
            message: '最长100字',
            trigger: 'blur'
          }],
          remark:[{
            max: 300,
            message: '最长300字',
            trigger: 'blur'
          }],
          meetingFileName:[{
            //必填
            required: true,
            message: '请上传会议文件',
            //触发方式:失去焦点
            trigger: 'blur'
          }]
        },
        //初始化上传参数载体(用以附带文件上传)
        formData: formdata,
        //dialog(弹出层)展示头
        formTitle: '',
        //表单提交路径
        url: '',
      }
    },
    methods: {
      //文件上传组件提交前钩子方法
      beforeAvatarUpload(file, fileList) {
        this.formData.append("file", file);
        return false;
      },
      //文件上传组件提交成功后钩子方法
      handleAvatarSuccess(file, fileList) { /* 会议文件展示 */
        this.ruleForm.file = URL.createObjectURL(file.raw);
        this.ruleForm.meetingFileName = file.name;
      },
      //新增信息先行方法
      addMeetingFunc() {
        this.formTitle = '新增';
        this.url = '/wholeProcess/meeting/add/saveWholeProcessMeeting'
      },
      //dialog(弹出层)关闭方法
      handleClose(done) {
        this.$refs.ruleForm.resetFields();
        done();
        this.$parent.centerDialogVisible = false
      },
      //查询信息(修改信息先行方法)
      updateMeetingFunc(row) {
        this.formTitle = '修改';
        this.url = '/wholeProcess/meeting/modify/updateWholeProcessMeeting';
        let formData = {
          meetingId: row.meetingId
        }
        const vm = this;
        vm.apiAddress('/wholeProcess/meeting/query/searchProjectMeetingPeriodInfo', formData).then(res => {
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
              meetingId: dataVO.meetingId,
              editDate: dataVO.editDate,
              meetingTheme: dataVO.meetingTheme,
              address: dataVO.address,
              participatoryUnit: dataVO.participatoryUnit,
              contentSummary: dataVO.contentSummary,
              remark: dataVO.remark,
              meetingFileName: dataVO.meetingFileName
            }
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
        //显示弹出层
        this.centerDialogVisible = true;
      }
    ,
    formatDate(prop){
      const date = new Date(prop);
      var year = date.getFullYear();
      var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return year + "-" + month + "-" + day
    },
      //表单提交
      submitMeetingFunc() {
        const vm = this;
        this.$refs['ruleForm'].validate(valid => {
          if (valid) {

            this.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              vm.formData.append("meetingId", vm.ruleForm.meetingId);
              vm.formData.append("editDate", vm.formatDate(vm.ruleForm.editDate));
              vm.formData.append("projectId", vm.projectId);
              vm.formData.append("meetingTheme", vm.ruleForm.meetingTheme);
              vm.formData.append("address", vm.ruleForm.address);
              vm.formData.append("participatoryUnit", vm.ruleForm.participatoryUnit);
              vm.formData.append("contentSummary", vm.ruleForm.contentSummary);
              vm.formData.append("remark", vm.ruleForm.remark);
              // 获取列表数据
              vm.$refs.upload.submit();
              vm.apiAddress(this.url, vm.formData).then(res => {
                vm.centerDialogVisible = false;
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
                  this.$parent.centerDialogVisible = false/* 让弹窗隐藏 */
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

