<template>
  <!-- 工作台 请章 -->
  <div class="work-pleaseChapter">
    <el-button class="btn-please-chapter btn-please-export" type="primary" v-if="!!userPowerInfo.chapterExport" @click="exportData">
      <i class="iconfont" style="font-size: 12px;">&#xe642;</i>
      &nbsp;导出
    </el-button>
    <el-button class="btn-please-chapter" type="primary" @click="initDialog">
      <i class="iconfont" style="font-size: 12px;">&#xe613;</i>
      &nbsp;请章申请
    </el-button>
    <el-tabs v-model="active" type="card" @tab-click="toogleTab">
      <el-tab-pane label="用章审核" name="b" v-if="isApproveRole==='1'">
        <table-in-chapter-review :departmentId = "departmentId"></table-in-chapter-review>
      </el-tab-pane>
      <el-tab-pane label="用章记录" name="c" v-if="isSignSealRole==='1'">
        <table-recorded-in-chapter></table-recorded-in-chapter>
      </el-tab-pane>
      <el-tab-pane label="请章记录" name="a">
        <table-please-record-in-chapter></table-please-record-in-chapter>
      </el-tab-pane>
    </el-tabs>
    <!-- 请章申请弹窗 -->
    <el-dialog title="请章申请" :visible.sync="dialogVisible" width="550px" center :before-close="beforeClose" :close-on-click-modal="false">
      <el-form ref="form" :status-icon="true" :model="form" label-width="80px" :rules="rules" style="text-align: left;">
        <el-form-item label="请章原因" prop="applyReason">
          <el-input type="textarea" placeholder="请输入用章原因" v-model="form.applyReason"></el-input>
        </el-form-item>

        <el-form-item label="用章类型" prop="sealTypeId">
          <el-checkbox-group v-model="form.sealTypeId">
            <el-checkbox :label="item.sealTypeId" v-for="item in radioList" :key="item.sealTypeId">{{item.sealTypeText}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="是否加盖骑缝章" prop="isPagingSeal">
          <el-radio-group v-model="form.isPagingSeal">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="用章附件" class="upload upload1" prop="resourceFiles">
          <el-input v-model="form.resourceFiles" v-if="false" ></el-input>
          <el-upload class="avatar-uploader" ref="upload" action="#"
                     :show-file-list="false" :on-change="handleAvatarSuccess" :auto-upload="false" :data="form" :on-success="fileSuccess" >
              <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;"></i>
          </el-upload>
           <el-input placeholder="请输入文件名称" v-model="form.resourceFileName" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="选择审核人员">
          <el-row>
            <el-col :span="12">
              <el-form-item label="选择人员" class="default" prop="delegateUser">
                <el-select v-model="form.delegateUser" placeholder="请选择人员">
                  <el-option label="请选择" value=""></el-option>
                  <el-option v-for="i in assignUsersList" :label="i.userName" :key="i.userId" :value="i.userId"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <p
        style="text-align: center;
				background-color: #FDE6DE;
				color: #F15A24;
				margin-left: -25px;
				margin-right: -25px;
				padding: 6px;"
      >
        如不指定人员，将使用默认审批流程。
      </p>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="onSubmit('form')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
	// 请章记录
  import tablePleaseRecordInChapter from './tablePleaseRecordInChapter';
	// 用章审核
  import tableInChapterReview from './tableInChapterReview';
	// 用章记录
  import tableRecordedInChapter from './tableRecordedInChapter';


  export default {
    data() {
      const formdata = new FormData();
      return {
        tabdata: [],
        biaodata: [], //表格数据
        active: 'b',
        input: 1,
        dialogVisible: false,//提交请章弹窗显示
        fileList: [],
        total: 100, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        formData: formdata,
        userPowerInfo:this.SStorage.getItem("userPowerInfo"),
        form: {
          applyReason: '',
          sealTypeId: [],
          sealTypeRadio: '',
          delegateUser: '',
          isPagingSeal:'',
          resourceFiles:'',
          resourceFileName:'',
          select1: '',
          select2: ''
        },
        departmentId: '',
        resourceFiles:'',
        delegateUser: '',//指定办理人
        sealTypeId: [],
        sealTypeRadio: '',
        radioList: '',
        isApproveRole: '',
        isSignSealRole: '',
        assignUsersList: '',
        isPagingSeal:'',
        config: {
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          }
        },
        rules: {
          applyReason: [{required: true, message: '请输入用章原因', trigger: 'blur'}],
          sealTypeId: [{required: true, message: '请选择用章类型', trigger: 'change'}],
          isPagingSeal: [{required: true, message: '请选择是否加盖骑缝章', trigger: 'change'}],
          resourceFiles: [{required: true, message: '请选择用章附件', trigger: 'change'}]
        }
      };
    },
    created() {
      this.getParams();
      const vm = this;
      vm.$axios.post(
        '/workbench/sealApply/query/sealApplyInit',{},vm.config
      ).then(
        function (response) {
          const authData = response.data.dataList;
          vm.isSignSealRole = authData.isSignSealRole;
          vm.isApproveRole = authData.isApproveRole;
          //2019.9.19 需求变更由于tab页更换顺序，所以在没有审批权限tab页时，将活动页改成记录页
          //2019.9.29 需求再次变更tab页更换顺序，所以在没有审批权限tab页时，将活动页改成用章记录页
          if(authData.isApproveRole=='0' && authData.isSignSealRole=="0"){
            vm.active = 'a';
          }else if(authData.isApproveRole=='1'){
            vm.active = 'b';
          }else if(authData.isSignSealRole=='1'){
            vm.active = 'c';
          }
        }
      ).catch(
        function (error) {
          vm.$message({
            type: 'error',
            message: error,
            center: true
          });
        }
      );
    },
    components: {
      'table-in-chapter-review': tableInChapterReview,
      'table-please-record-in-chapter': tablePleaseRecordInChapter,
      'table-recorded-in-chapter': tableRecordedInChapter
    },
    methods: {
      toogleTab(a){//tab页切换事件
        // 出发事件，父页面发，子页面接
        this.bus.$emit(a.name);
      },
      exportData(){
        const elemIF = document.createElement('iframe');
        elemIF.src = '/workbench/sealApply/exprot/exportSealApplySecord?realFileName=' + "请章记录.xls";
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      },
      onSubmit(formName) {
        // 提交请章申请
        this.$refs[formName].validate(valid => {
          if (valid) {
            const vm = this;
            vm.formData.append("applyReason", vm.form.applyReason);//请章原因
            vm.formData.append("sealTypeId", vm.form.sealTypeId);//章类型
            vm.formData.append("isPagingSeal", vm.form.isPagingSeal);//是否骑缝章
            vm.formData.append("delegateUser", vm.form.delegateUser);//指定办理人
            vm.formData.append('departmentId',this.departmentId);
            //vm.formData.append("resourceFiles", vm.form.resourceFiles);//附件
            vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              vm.$axios.post('/workbench/sealApply/insert/submitSealApply', vm.formData, {
                headers: {
                  'Content-Type': 'multipart/form-data',
                }
              }).then(function (response) {
                vm.dialogVisible = false;//隐藏弹窗
                vm.formData = new FormData();/* 清空formData数据*/
                if (response.data.status === "success") {
                  // 出发事件，父页面发，子页面接
                  vm.bus.$emit('reLoadData');
                  vm.$message({
                    type: 'success',
                    message: response.data.message,
                    center: true
                  });
                } else {
                  vm.$message({
                    type: 'error',
                    message: response.data.message,
                    center: true
                  });
                }
                // 清空上次修改的表单内容
                vm.$refs[formName].resetFields();
                vm.$refs.upload.clearFiles();
                vm.form.resourceFileName = '';
              })
                .catch(function (error) {
                  vm.formData = new FormData();
                  vm.$message({
                    type: 'error',
                    message: error,
                    center: true
                  });
                });

            }).catch(() => {
              vm.formData = new FormData();
            });
          }
        });
      },
      // 修改上传文件动态改名
      handleAvatarSuccess(file) {
        this.formData.delete("resourceFiles");
        this.formData.append("resourceFiles", file.raw);
        this.form.resourceFiles = URL.createObjectURL(file.raw);
        this.form.resourceFileName = file.name;
      },
      // 上传文件成功回调
      fileSuccess(response, file, fileList) {
      },
      initDialog() {
        /* 点击请章请求数据 */
        this.dialogVisible = true;//显示弹窗
        this.initSealType();
        this.initAssignUsers();
      },
      initSealType() {
        const vm = this;
        vm.$axios.post('/workbench/sealApply/query/querySealType', {}, vm.config).then(function (response) {
          vm.radioList = response.data.dataList;
        })
          .catch(function (error) {
            console.log(error);
          });
      },
      initAssignUsers(){
        const vm = this;
        vm.$axios.post('/workbench/sealApply/query/queryAssignUsers', {
          departmentId : this.departmentId
        }, vm.config).then(function (response) {
          vm.assignUsersList = response.data.dataList;
        }).catch(function (error) {
          console.log(error);
        });
      },
      beforeClose(DialogDone) {
        this.$refs.form.resetFields();
        this.$refs.upload.clearFiles();
        this.form.resourceFileName = '';
        this.dialogVisible = false;
      },
      getParams(){
        this.departmentId = this.$router.history.current.query.departmentId;
      },
      currentChangePage(list, i, k) { //分页方法
        this.tabdata = list;
      }
    }
  };
</script>
<style lang="scss">
  .work-pleaseChapter {
    /* 标签页 */
    .el-tabs--card {
      & > .el-tabs__header {
        border-bottom: none;
        margin: 0;

        .el-tabs__nav-scroll {
          padding: 20px 20px 0;

          .el-tabs__nav {
            border: 1px;
            .el-tabs__item {
              border-radius: 10px 10px 0 0;
              border: none;
              box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
              background-color: white;
              color: #b7b7b7;

              &.is-active {
                background-color: #0aa3ef;
                color: white;
              }
            }
          }
        }
      }
    }

    .el-button {
      &.btn-please-chapter {
        width: 90px;
        height: 26px;
        line-height: 26px;
        padding: 0;
        margin: 0;
        position: absolute;
        top: 25px;
        right: 45px;
        color: #0099e8;
        border: 1px solid #0099e8;
        background-color: white;
        font-size: 12px;
        border-radius: 5px;
        z-index: 10;

        .icon-pleaseChapter {
          width: 15px;
          height: 15px;
          background-position: -104px -103px;
          vertical-align: middle;
          position: relative;
          top: -1px;
        }
      }
      &.btn-please-export{
        right: 150px!important;
      }
    }

    .el-dialog {
      .el-dialog__body {
        padding-bottom: 0;
        .el-form {
          /* 表单项 */
          .el-form-item {
            padding-bottom: 15px;
            border-bottom: 1px solid #e7e7e7;

            &:last-child {
              padding-bottom: 0;
              border-bottom: none;
            }

            label.el-form-item__label {
              line-height: 30px;
              display: block;
              float: none;
              width: 100% !important;
              text-align: left;
              color: #424242;
            }

            .el-radio-group {
              label {
                display: inline-block;
                width: auto !important;
                margin-top: 10px;
              }
            }
            .el-form-item__content {
              margin: 0 !important;
            }

            &.default {
              padding-bottom: 0;
              border-bottom: none;

              label {
                display: inline-block;
                width: auto !important;
                height: 30px;
                line-height: 30px;
              }

              .el-form-item__content {
                display: inline-block;
                width: 160px;
              }
            }

            .el-input__inner {
              height: 30px;
            }
          }
        }
      }

      .el-dialog__footer {
        button {
          height: 30px;
          padding: 0 30px;
          line-height: 30px;
        }
      }
    }

    .up-load {
      .upload-demo {
        line-height: 30px;

        .el-upload {
          float: left;
          .el-button {
            width: 90px;
            height: 25px;
            color: #0099e8;
            border: 1px solid #0099e8;
            font-size: 12px;
            padding: 0;
            line-height: 12px;
            .icon-commit {
              width: 12px;
              height: 12px;
              background-position: -106px -148px;
              margin-right: 3px;
            }
          }
        }

        .el-upload--text {
          margin-bottom: 10px;
        }

        ul {
          clear: both;
          li {
            background-color: #f1f5f8;
            text-align: left;
            border-radius: 5px;

            i {
              display: none;
            }

            a {
              color: #06a1ee;
              height: 30px;
              line-height: 30px;
              padding-left: 10px;
            }

            .el-progress {
              top: 30px;
            }

            .el-icon-close {
              top: 11px;
              display: none;
            }
          }
        }
      }
    }
    .el-upload{
      text-align: left;
    }
  }
</style>
<style lang="scss" scoped>
  .work-pleaseChapter {
    position: relative;
    padding-bottom: 25px;
    flex-direction: column;
    display: flex;
    margin-left: 0px;
  }
</style>
