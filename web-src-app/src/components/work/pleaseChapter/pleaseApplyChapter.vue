<template>
  <div class="pleaseApplyChapter">
    <form class="projectForm">
      <div class="form-item">
        <label for="pleaseChapterReason">请章原因</label>
        <textarea id="pleaseChapterReason" placeholder="请输入请章原因" v-model="projectForm.pleaseChapterReason" v-validate="'required'"
          data-vv-name="请章原因"></textarea>
      </div>
      <div class="form-item">
        <m-select
          v-model="projectForm.chapterWithType.sealTypeId"
          :data="{title: '用章类型',options: this.chapterWithType}"
          unique="sealTypeId"
          display="sealTypeText"
          v-validate="'required'"
          data-vv-name="用章类型">
        </m-select>
      </div>
      <div class="form-item">
        <m-select
          v-model="projectForm.isPagingSeal.id"
          :data="{title: '是否加盖骑缝章',options: this.isPagingSeal}"
          v-validate="'required'"
          data-vv-name="是否加盖骑缝章">
        </m-select>
      </div>
      <div class="form-item file">
        <mt-cell title="用章附件">
          <label for="file">{{ projectForm.resourceFiles[0]?projectForm.resourceFiles[0].name:'上传' }}</label>
          <input type="file" id="file" @change="onUpload" v-validate="'required'" data-vv-name="用章附件"/>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <div class="form-item">
        <m-select
          v-model="projectForm.reviewers.userId"
          :data="{title: '审核人员',options: this.reviewers}"
          unique="userId"
          display="userName">
        </m-select>
      </div>
    </form>
    <p class="alert">如不指定人员，将使用默认审批流程。</p>
    <div class="commonFooter">
      <mt-button @click="cancle">取消</mt-button>
      <mt-button class="linear" @click="submit">确定</mt-button>
    </div>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  export default {
    data() {
      return {
        projectForm: { // 项目表单
          pleaseChapterReason: '', // 请章原因'
          chapterWithType: { // 用章类型
            sealTypeId: '', // 唯一标识
            sealTypeText: '' // 选择的展示内容
          },
          reviewers: {
            userId: '', // 唯一标识
            userName: '' // 选择的展示内容
          }, //审核人员
          isPagingSeal: {//是否加盖骑缝章
            id:'',
            display:''
          },
          resourceFiles: [] // 真实文件
        },
        reviewers: [], // 提审人
        isPagingSeal:[//是否加盖骑缝章的选择项数组
          {
            id:'1',
            display:'是'
          },{
            id:'0',
            display:'否'
          }
        ],
        chapterWithType: [], //用章类型
        config: { // 请求配置
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        }
      };
    },
    mounted() {
      let vm = this;
      // 获取用章类型
      if (!vm.chapterWithType.length) vm.initSealType();
      // 获取审核人员
      if (!vm.reviewers.length) vm.initAssignUsers();
    },
    methods: {
      /**
       * @method 上传
       * @param {Object} e 当前节点对象
       */
      onUpload(e) {
        this.projectForm.resourceFiles = e.currentTarget.files;
      },
      /**
       *@method 提交表单
       */
      submit() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          vm.$validator
            .validateAll()
            .then(result => {
              if (result) {
                let formData = new FormData();
                // 校验成功
                formData.append("applyReason", vm.projectForm.pleaseChapterReason); // 请章原因'
                formData.append("sealTypeId", vm.projectForm.chapterWithType.sealTypeId); // 用章类型
                formData.append("isPagingSeal", vm.projectForm.isPagingSeal.id); //是否骑缝章
                formData.append("delegateUser", vm.projectForm.reviewers.userId); //指定办理人
                formData.append("resourceFiles", vm.projectForm.resourceFiles[0]); // 真实文件
                formData.append('departmentId', sessionStorage.getItem("work__departmentId--default"));
                vm.getVerifyInfo(formData).then(function() {
                  // 清空表单
                  vm.reset();
                  // 后退一页
                   vm.$router.push('/app/pleaseChapter');
                });
              } else {
                vm.Toast({
                  message: vm.errors.all()[0],
                  position: 'middle',
                  className: 'toast',
                  duration: 2000
                });
              }
            });
        });
      },
      /**
       * @method 取消
       */
      cancle() {
        let vm = this;
        // 清空表单
        vm.reset()
        vm.$router.push('/app/pleaseChapter');
      },
      /**
       * @method  获取验证信息
       * @param {Object} row 表格行数据
       */
      getVerifyInfo(data) {
        let vm = this;
        let promise = new Promise(function(resolve, reject) {
          vm.requestPost('/workbench/sealApply/insert/submitSealApply', data, vm.config).then(function(response) {
            if (response.data.status === 'success') {
              resolve();
            } else {
              Toast({
                message: response.data.message,
                position: 'middle',
                duration: 5000
              });
            }
          }).catch(function(error) {
            Toast({
              message: '提交请章异常',
              position: 'middle',
              duration: 5000
            });
          });
        });
        return promise;
      },
      /**
       *@method 获取用章类型
       */
      initSealType() {
        const vm = this;
        vm.requestPost('/workbench/sealApply/query/querySealType', {}, vm.config).then(function(response) {
            vm.chapterWithType = response.data.dataList;
            console.log('vm.chapterWithType', vm.chapterWithType);
          })
          .catch(function(error) {
            console.log(error);
          });
      },
      /**
       * @method 获取审核人员
       */
      initAssignUsers() {
        const vm = this;
        vm.requestPost('/workbench/sealApply/query/queryAssignUsers', {
          departmentId: sessionStorage.getItem("work__departmentId--default")
        }, vm.config).then(function(response) {
          vm.reviewers = response.data.dataList;
        }).catch(function(error) {
          console.log(error);
        });
      },
      /**
       * @method 重置数据
       */
      reset() {
        let vm = this;
        vm.projectForm = { // 项目表单
          pleaseChapterReason: '', // 请章原因'
          chapterWithType: { // 用章类型
            sealTypeId: '', // 唯一标识
            sealTypeText: '' // 选择的展示内容
          },
          reviewers: {
            userId: '', // 唯一标识
            userName: '' // 选择的展示内容
          }, //审核人员
          isPagingSeal: {//是否加盖骑缝章
            id:'',
            display:''
          },
          resourceFiles: [] // 真实文件
        };
      }
    }
  };
</script>
<style scoped lang="scss">
  .pleaseApplyChapter {
    width: 100%;

    .alert {
      position: fixed;
      bottom: 90px;
      left: 0;
      width: 100%;
      height: 70px;
      background-color: rgb(253, 230, 222);
      color: rgb(241, 90, 36);
      text-align: center;
      line-height: 70px;
      font-size: 28px;
    }

  }
</style>
