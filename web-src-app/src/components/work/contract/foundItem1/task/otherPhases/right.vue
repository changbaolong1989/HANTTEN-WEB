<template>
  <!-- 其他阶段右边栏-->
  <div class="right more-sublevel">
    <!-- 上传下载删除-->
    <el-collapse style="border-radius:5px;">
      <!-- 普通阶段文件上传下载删除-->
      <collapseUpAndDownload
        :title="UpAndDownloadTitle"
        :showUploadBtn="showUploadBtnFlg"
        :delBtnFlag="delBtnFlag"
        @uploadFile="uploadFile"
        @del="deleteFileInfo"
        :multiple="true"
        :files="files"
        :type="type"
        :id="currentTransmitData.taskId"
      ></collapseUpAndDownload>
      <!-- 上传文件 -->
      <dialogUpload
        ref="dialogUpload"
        :options="options"
        unique="fileTypeId"
        display="fileTypeName"
        :uploadOptions="uploadOptions"
        :files="files"
        @submit="uploadSubmit"
        :periodTypeBoolean="currentTransmitData.periodType != '1'"
        :periodType="currentTransmitData.periodType"
      ></dialogUpload>
      <div class="primary" v-if="typeFlag == 1">
        <button class="linear" @click="isIgnoreTaskPeriod">{{ currentTransmitData.isIgnore == '0' ? '忽略' : '展示' }}</button>
        <button class="linear" @click="finish">{{ currentTransmitData.isFinish === '1' ? '未完成' : '完成' }}</button>
      </div>
    </el-collapse>
  </div>
</template>

<script>
import collapseUpAndDownload from '../../../../../more/common/components/collapseUpAndDownload.vue'; /* 文件上传下载 */
import dialogUpload from '../../../../../more/common/components/dialogUpload.vue'; /* 文件选择弹窗 */
export default {
  data() {
    return {
      type: 'task' /* 类型 */,
      UpAndDownloadTitle: '显示',
      showUploadBtnFlg: false /*上传按钮是否显示标识 */,
      delBtnFlag: false, // 是否显示删除按钮
      files: [], // 上传文件列表
      options: [] /* 文件上传单选数据 */,
      uploadOptions: {
        isInvoice: false, // 是否是发票
        limit: 1,
        radioFlag: this.periodType == '1' ? false : true
      },
      list: this.SStorage.getItem('/app/foundItem')
    };
  },
  props: ['currentTransmitData', 'typeFlag', 'jurisdiction'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
        this.init();
      },
      immeditate: true
    },
    typeFlag: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.typeFlag = newval;
      },
      immeditate: true
    },
    jurisdiction: {
      /* 显示按钮的相关信息 */
      handler(newval) {
        let vm = this;
        vm.jurisdiction = newval;
        this.init();
      },
      immediate: true,
      deep: true
    }
  },
  components: {
    collapseUpAndDownload,
    dialogUpload
  },
  created() {
    const vm = this;
    // 是否忽略
    vm.isIgnore = vm.currentTransmitData.isIgnore;
    // 是否完成
    vm.isFinish = vm.currentTransmitData.isFinish;
    this.init();
  },
  methods: {
    init() {
      const vm = this;
      // 重新编辑申请
      vm.isSubmitReedit = vm.jurisdiction.isSubmitReedit;
      // 当前状态
      let currentState = vm.jurisdiction.currentState;
      // 部门id
      vm.departmentId = vm.currentTransmitData.departmentId;
      /* 阶段信息*/
      vm.periodType = vm.currentTransmitData.periodType;
      // ==============================各个阶段通用 start=================================

      // 其他阶段
      if (vm.periodType !== '1') {
        vm.showUploadBtnFlg = vm.typeFlag === '1' || (vm.typeFlag === '15' && currentState !== '6'); // 是否显示上传按钮  true:显示  false:不显示
        vm.delBtnFlag = !vm.showUploadBtnFlg; // 是否显示删除按钮  true:不显示  false:显示

        // 收集资料
      } else if (vm.periodType === '1') {
        vm.showUploadBtnFlg =
          ((currentState === '6' && vm.isSubmitReedit !== '1') || vm.typeFlag === '1' || (vm.typeFlag === '15' && vm.isSubmitReedit !== '1')) && vm.typeFlag !== '0';
        vm.delBtnFlag = !vm.showUploadBtnFlg;
      }

      if (vm.periodType === '1' && vm.typeFlag === '6' && vm.isSubmitReedit !== '1') {
        // 如果是收集资料阶段，在归档时机，没提交重新编辑申请显示上传按钮
        vm.showUploadBtnFlg = true; // 是否显示上传按钮  true:显示  false:不显示
        vm.delBtnFlag = false; // 是否显示删除按钮  true:不显示  false:显示
      }

      if (vm.typeFlag === '15' && vm.isSubmitReedit === '1') {
        // 如果是普通执行人，提了重新编辑申请
        vm.showUploadBtnFlg = false; // 是否显示上传按钮  true:显示  false:不显示
        vm.delBtnFlag = true; // 是否显示删除按钮  true:不显示  false:显示
      }

      /* A3请求下载文件列表*/
      this.MaterialList(vm.currentTransmitData.relationId, vm.typeFlag);
    },
    MaterialList(relationId, typeFlag) {
      const vm = this;
      /* A3请求下载文件列表*/
      this.requestPost('/task/query/searchTaskPeriodMaterialList', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));

              // 如果是项目监理部
              if (vm.departmentId === vm.superviseDeptId) {
                // 如果是主提审人或者是普通执行人并且已归档，则不显示删除按钮
                if ((typeFlag === '1' || typeFlag === '15') && dataList[i].isReview === '1') {
                  dataList[i].isCanDel = '0';
                }
                // 如果不是项目监理部
              } else {
                // 如果是主提审人，则显示删除按钮
                if (typeFlag == '1') {
                  dataList[i].isCanDel = '1';

                  // 如果是普通执行人，则随后台显示
                } else if (typeFlag == '15') {
                  continue;

                  // 否则，不显示删除按钮
                } else {
                  dataList[i].isCanDel = '0';
                }

                // 如果是收集资料阶段，在归档时机显示上传按钮
                if (vm.periodType === '1' && vm.typeFlag === '6' && vm.isSubmitReedit !== '1') {
                  dataList[i].isCanDel = '1';
                }
              }
            }
            this.files = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /* 上传组件相关 */
    uploadFile() {
      let vm = this;
      // 上传文件对话框显示
      /* 上传文件类型*/
      this.requestPost('/tenderTask/query/searchMaterialTypeList', {
        departmentId: this.currentTransmitData.departmentId,
        counselTypeId: this.currentTransmitData.counselTypeId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.options = res.data.dataList;
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
      this.$nextTick(function() {
        vm.$refs['dialogUpload'].show = true;
      });
    },
    deleteFileInfo(val) {
      /* 删除文件 */
      console.log("val",val)
      let vm = this;
      let formData = {
        relationId:val.materialId,
        materialPath: val.materialPath,
        taskId: vm.currentTransmitData.taskId
      };
      this.requestPost('/task/delete/deleteTaskPeriodMaterialInfo', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.init();
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // ==============================================================================是否忽略任务阶段 start==============================================================================
    // 是否忽略
    isIgnoreTaskPeriod() {
      let isIgnoreFlag = '';

      if (this.isIgnore === '0') {
        isIgnoreFlag = '1';
      } else {
        isIgnoreFlag = '0';
      }
      this.MessageBox.confirm('', {
        message: '确定执行当前操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/updateIsIgnore', {
            // 任务阶段id
            relationId: this.currentTransmitData.relationId,
            // 是否忽略
            isIgnore: isIgnoreFlag
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                // 刷新页面
                this.list.businessId = this.currentTransmitData.id;
                this.SStorage.setItem('/app/foundItem', this.list);
                this.$router.go(0);
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              this.Toast(e);
            });
        })
        .catch(err => {});
    },
    // ==============================================================================是否忽略任务阶段 end========
    // ==============================================================================是否完成任务阶段 start==============================================================================
    /**
     * @method 完成
     */
    finish() {
      let isFinishFlag = '';

      if (this.isFinish === '0') {
        isFinishFlag = '1';
      } else {
        isFinishFlag = '0';
      }
      this.MessageBox.confirm('', {
        message: '确定执行当前操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/updateIsFinish', {
            // 任务阶段id
            relationId: this.currentTransmitData.relationId,
            // 是否忽略
            isFinish: isFinishFlag
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                // 刷新页面
                this.list.businessId = this.currentTransmitData.id;
                this.SStorage.setItem('/app/foundItem', this.list);
                this.$router.go(0);
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              this.Toast(e);
            });
        })
        .catch(err => {});
    },
    // ==============================================================================是否完成任务阶段 end============
    uploadSubmit(data) {
      /* 上传文件接口 */
      /* 上传文件数据*/
      var formdata = new FormData();
      formdata.append('file', data.form.get('file'));
      formdata.append('taskPeriodId', this.currentTransmitData.relationId);
      formdata.append('fileType', data.fileTypeId);
      if (this.periodType == '1') {
        formdata.append('remark', data.form.get('remark'));
      }
      this.requestPost('/task/add/insertTaskPeriodMaterialInfo', formdata)
        .then(res => {
          if (res.data.status == 'success') {
            this.init();
            this.$router.go(0);
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style></style>
