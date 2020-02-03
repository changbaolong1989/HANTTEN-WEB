<template>
  <!-- 分配合同编撰人-->
  <div class="createContract">
    <form class="projectForm">
      <div class="form-item">
        <label>项目编号</label>
        <input placeholder="请输入项目编号" v-model="currentForm.itemNumber" readonly>
      </div>
      <div class="form-item">
        <mt-cell title="分配合同编撰人"></mt-cell>
        <div class="DynamicallyAdd">
          <div class="form-item">
            <label>合同</label>
            <input readonly :value="currentForm.itemNumber+'-'+currentForm.contractNumber">
          </div>
          <div class="form-item">
            <label>合同编号</label>
            <input v-model="currentForm.contractNumber" readonly>
          </div>
          <div class="form-item">
            <m-select
              v-model="currentForm.designatedDerson"
              :data="{title: '指定人员',options: this.designatedDerson}"
              unique="jobId"
              display="userName"
              v-validate="'required'"
              data-vv-name="指定人员">
            </m-select>
          </div>
        </div>
      </div>
    </form>
    <div class="commonFooter">
      <mt-button @click="reset">取消</mt-button>
      <mt-button class="linear" :disabled="btnIsDisabled" @click="submitForm">确定</mt-button>
    </div>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  export default {
    /**
     * @param {Object}  data 传输数据(合同信息)
     * data.id 唯一标识(id)
     */
    props: ['data'],
    data() {
      return {
        currentData: this.tool.deepCopy(this.data, this.tool.deepCopy),
        btnIsDisabled: false, // 禁用确定按钮
        designatedDerson: [] /*, 人员下拉列表 */ ,
        currentForm: {
          itemNumber: '', // 项目编号
          contractNumber: '', // 合同编号
          designatedDerson: '' // 指定人员
        }
      };
    },
    watch: {
      data: {
        handler() {
          let vm = this;
          vm.currentData = vm.tool.deepCopy(vm.data, vm.tool.deepCopy);
          // 拆分label获取项目编号与合同编号
           let strArr = vm.currentData.label.split('-');
           // 设置合同编号
           vm.currentForm.contractNumber = strArr.pop();
           // 设置项目编号
           vm.currentForm.itemNumber = strArr.join('-');
          this.getContractWriter();
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 根据合同标识,获取合同编撰人
       */
      getContractWriter() {
        let vm = this;
        this.requestPost('/project/query/queryContractWriter', {
          "contractId": vm.currentData.id
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          let deptId = res.data.dataVO.departmentId;
          if (res.data.status == "success") {
            // 判断是否已经制定了合同编撰人
            if (res.data.dataVO.applyUserName && res.data.dataVO.applyUserId) {
              // 设置指定人员下拉信息
              vm.currentForm.designatedDerson = res.data.dataVO.applyUserId;
              vm.btnIsDisabled = true;
            } else {
              vm.btnIsDisabled = false;
            }
            //根据合同部门ID获取指定人员下拉列表
            this.getDepartmentPersonList(deptId);
          } else {
            vm.Toast({
              message: res.data.message,
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          }
        }).catch(e => {
          vm.Toast({
            message: e,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        })
      },
      // 获取指定人员下拉列表
      getDepartmentPersonList(deptId) {
        const vm = this;
        // 查询人员
        this.requestPost('/project/query/excSearchPersonListByInvolvedDepartment', {
          // 部门id
          "departmentId": deptId
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            // 设置指定人员下拉信息
            vm.designatedDerson = res.data.dataList;
          } else {
            vm.Toast({
              message: res.data.message,
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          }
        }).catch(e => {
          vm.Toast({
            message: e,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        })
      },
      submitForm(){
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          let params = {
            // 项目id
            projectId: vm.currentData.projectId,
            // 合同id
            "contractId": vm.currentData.id,
            // 职位id
            "userId": vm.currentForm.designatedDerson
          };
          vm.$validator.validate().then(valid => {
            if(!valid){
              vm.Toast(vm.errors.all()[0]);
            }else{
              vm.requestPost('/project/modify/updateContractUserInfo', params).then(res => {
                // 如果返回成功，则绘制数据，否则提示失败信息
                if (res.data.status == "success") {
                vm.Toast({
                    message: res.data.message,
                    position: 'middle',
                    className: 'toast',
                    duration: 2000
                  });
                  // 重置表单以及隐藏表单
                   vm.reset();
                   history.go(0);
                } else {
                  vm.Toast({
                    message: res.data.message,
                    position: 'middle',
                    className: 'toast',
                    duration: 2000
                  });
                }
              }).catch(e => {
               vm.Toast({
                 message: e,
                 position: 'middle',
                 className: 'toast',
                 duration: 2000
               });
              });
            }
          });
        });
      },
      reset(){
        let vm = this;
        vm.currentForm = {
          itemNumber: '', // 项目编号
          contractNumber: '', // 合同编号
          designatedDerson: '' // 指定人员
        };
        vm.$emit('cancel');
      }
    }
  };
</script>
<style lang="scss">
  .createContract {
    .DynamicallyAdd {
      .top {
        .mint-cell {
          span {
            color: #808080;
          }
        }
      }
    }
  }
</style>
<style lang="scss" scoped>
  .createContract {
    width: 100%;

    .DynamicallyAdd {
      background-color: #f1f5f8;
      border-radius: 10px;
      padding: 30px 30px 0;

      .top {
        .mint-cell {
          span {
            color: #808080;
          }
        }
      }
    }
  }
</style>
