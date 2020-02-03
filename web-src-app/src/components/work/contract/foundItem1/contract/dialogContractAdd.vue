<template>
  <!-- 添加合同 -->
  <div class="foundItem__dialog-contract-add">
    <form ref="form" class="projectForm">

      <div class="form-item" style="margin-top: 0;">
        <m-select
          v-if="'添加合同'==data.name"
          v-model="form.contractList[0].departmentId"
          :data="{title: '指定人员',options: designatedPersonnel}"
          unique="departmentId"
          display="userName"
          v-validate="'required'"
          data-vv-name="指定人员"
          @change="getAbbr(0,form.contractList[0].departmentId)">
        </m-select>
        <m-select
         v-else
          v-model="form.contractList[0].jobId"
          :data="{title: '指定人员',options: designatedPersonnel}"
          unique="jobId"
          display="userName"
          v-validate="'required'"
          data-vv-name="指定人员"
          @change="getAbbr(0,form.contractList[0].jobId)">
        </m-select>


      </div>
      <div class="form-item">
        <label>合同编号</label>
        <input placeholder="请输入合同编号" v-model="form.contractNum" v-validate="'required'" data-vv-name="合同编号">
      </div>

      <div class="form-item form-item__flex" v-if="'修改合同'!=data.name">
        <label>跨部门合作</label>
        <mt-switch v-model="form.cooperateContract" @change="cooperateContractChange"></mt-switch>
      </div>

      <div class="form-item form-item__flex" v-if="form.cooperateContract">
        <label>添加业务线</label>
        <button
          style="background-image: linear-gradient(90deg, #06a1ee 0%, #4cc2fc 100%), linear-gradient(#ffffff, #ffffff);
          padding:0 5px;border-radius: 25%;color: white;"
          type="button"
          class="linear addList"
          @click="addPersonnel"
          >+</button>
      </div>
      <div v-if="form.cooperateContract">
      <template v-for="(item,index) in form.contractList">
        <div v-if="index" class="form-item form-item__flex"
          style="padding: 0;margin: 0;"
          :key="index">
          <label>
            <m-select
              v-model="item.departmentId"
              :data="{title: '指定人员',options: designatedPersonnel}"
              unique="departmentId"
              display="userName"
              v-validate="'required'"
              data-vv-name="指定人员"
              @change="getAbbr(index,item.departmentId)">
            </m-select>
          </label>
          <label style="line-height: 1.15rem;padding-left:20px;"><i class="icon-close">x</i></label>
        </div>
      </template>
    </div>

    </form>
    <div class="commonFooter">
      <mt-button @click="reset">取消</mt-button>
      <mt-button class="linear" @click="submit">确定</mt-button>
    </div>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  export default {
    props: ['data'],
    data() {
      return {
        form: {
          contractId: '',// 合同id
          contractNum:'',// 合同编号
          contractList:[{
             userName: '', // 人员显示名称（部门+姓名）
             departmentId:'',// 部门id
             jobId:''// 职位id
           }],
         // 跨部门合作
         cooperateContract:false,
        },
        designatedPersonnel:[],// 指定人员数组
        currentData: this.tool.deepCopy(this.data.data,this.tool.deepCopy),
        storageData:this.SStorage.getItem('/app/foundItem')
      }
    },
    watch: {
      data:{
        handler() {
          let vm = this;
          vm.currentData = vm.tool.deepCopy(vm.data.data,vm.tool.deepCopy);
          if('修改合同'==vm.data.name){
           vm.form.contractNum =  vm.currentData.label.substring(vm.currentData.label.indexOf("-") + 1);
          }
          vm.getPersonList();// 获取指定人员下拉列表
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 跨部门合作改变时重置表单的指定人员数组
       */
      cooperateContractChange(){
        let vm = this;
        while(vm.form.contractList.length>1){
          vm.form.contractList.pop();
        }
      },
      // 获取部门缩写,并且设置数据
      getAbbr(index,id) {
        let vm = this;
        let obj = {};
        let repetition = 0;// 重复次数
        let item_ = vm.form.contractList[index];
        // 获取下拉框的所有值 designatedPersonnel:下拉框
        obj = vm.designatedPersonnel.find(item => {
          if('修改合同'==vm.data.name){
            return item.jobId === id;
          }else{
            return item.departmentId === id;
          }
        });
        for (let i = 0, len = vm.form.contractList.length; i < len; i++) {
          let li = vm.form.contractList[i];
          if(li.departmentId==item_.departmentId&&++repetition>1){
            item_.userName = '';
            item_.departmentId = '';
            item_.jobId = '';
            item_.abbr = '';
            // 计算合同编号
            vm.countContractNum();
            return vm.Toast('不可以选择重复的指定人员!');
          };
        }
        if(!obj)return;
        // 设置用户id
        item_.userName = obj.userName?obj.userName:'';
        item_.departmentId = obj.departmentId?obj.departmentId:'';
        item_.jobId = obj.jobId?obj.jobId:'';
        item_.abbr = obj.abbr?obj.abbr:'';
        // 计算合同编号
       if('添加合同'==vm.data.name)vm.countContractNum();
      },
      /**
       * @method 计算部门缩写
       */
      countContractNum(){
        let vm = this;
        let a = '',b = '',c = '';// 三个指定人员字符串拼接片段
        for (let i = 0, len = vm.form.contractList.length; i < len; i++) {
          let li = vm.form.contractList[i];
          switch (i){
            case 0:
              a = li.abbr?li.abbr:'';
              break;
            case 1:
              b = li.abbr?'/'+li.abbr:'';
              break;
            case 2:
              c = li.abbr?'/'+li.abbr:'';
              break;
          }
        }
        // 通过项目id和部门id查询合同数
        vm.requestPost('/project/query/searchContractCountByProjectIdAndDepartmentId', {
          // 项目id
          projectId: vm.storageData.projectId
        })
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              // 设置合同编号（部门缩写+部门项目下的合同数+1）如果abc为空字符串则设置为空
              vm.form.contractNum = a+b+c?a+b+c+res.data.dataVO.contractCount:'';
            } else {
              this.Toast( res.data.message);
            }
          })
          .catch(e => {
            this.Toast(e);
          });
      },
      /**
       * @method 添加指定人员
       */
      addPersonnel(){
        let vm = this;
        if(vm.form.contractList.length>2)return;
        vm.form.contractList.push({
              userName: '', // 人员显示名称（部门+姓名）
              departmentId:'',// 部门id
              jobId:''// 职位id
            });
      },
      /**
       * @method 获取合同信息
       */
      getContractInfo() {
        const vm = this;
        // 查询合同信息
        this.requestPost('/project/query/excSearchContractInfo', {
          // 合同id
          "contractId": vm.currentData.id
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置指定人员
            vm.form.contractList = [{
                userName: res.data.dataVO.userName, // 人员显示名称（部门+姓名）
                departmentId:res.data.dataVO.departmentId,// 部门id
                jobId:res.data.dataVO.jobId// 职位id
              }];
          } else {
            vm.Toast(res.data.message);
          }
        }).catch(e => {
          vm.Toast(e);
        })

      },
      /**
       * @event 改变指定人员事件
       */
      change(val,option){
        let vm = this;
        vm.form.departmentId = option.departmentId;
      },
      /**
       *@method 获取指定人员下拉列表
       */
      getPersonList() {
        const vm = this;
        // 查询人员
        vm.requestPost('/project/query/excSearchPersonList', {}).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            // 设置指定人员下拉信息
            vm.designatedPersonnel = res.data.dataList;
            if('修改合同'==vm.data.name){
              // 获取合同信息
               vm.getContractInfo();
            }
          } else {
            vm.Toast(res.data.message);
          }
        }).catch(e => {
          vm.Toast(e);
        })

      },
      /**
       * @method 重置
       */
      reset() {
        let vm = this;
        vm.form = {
          contractId: '',// 合同id
           contractNum:'',// 合同编号
           contractList:[{
              userName: '', // 人员显示名称（部门+姓名）
              departmentId:'',// 部门id
              jobId:''// 职位id
            }],
          // 跨部门合作
          cooperateContract:false
        };
        vm.$emit('cancel');
      },
      /**
       * @event 提交表单
       */
      submit() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.$validator
              .validateAll()
              .then(result => {
                if (result) {
                  let vm = this;
                  let formData = {
                    // 项目id
                    "projectId": vm.SStorage.getItem('/app/foundItem').projectId,
                    // 合同编号
                    "contractNum": vm.form.contractNum,
                    // 跨部门合作
                    cooperateContract:vm.form.cooperateContract,
                    // 指定人员数组
                    contractList:vm.form.contractList
                  };
                  let url = '/project/add/saveContractInfo';
                  if('修改合同'==vm.data.name){
                    // 合同标识
                    formData.contractId = vm.currentData.id;
                    url = '/project/modify/updateContractInfo';
                  }
                  vm.requestPost(url, formData).then(res => {
                    vm.Toast(res.data.message);
                    if (res.data.status != 'failure'){
                      // 合同添加或修改合同后修改目标节点,然后刷新页面
                      let storage = vm.SStorage.getItem('/app/foundItem');
                      storage.businessId = formData.contractId||res.data.primaryKey;
                      vm.SStorage.setItem('/app/foundItem',storage);
                      history.go(0);
                    }
                  }).catch(err => {
                    vm.Toast(err);
                  });
                } else {
                  vm.Toast(vm.errors.all()[0]);
                }
              });
        });
      }
    }
  }
</script>


<style lang="scss">
  .foundItem__dialog-contract-add {
    position: absolute;
    height: 100vh;
    width: 100vw;
    top: 0;
    left: 0;
    z-index: 99999;
    background-color: white;


    .form-item{
      .icon-close{
        display: block;
        text-align: right;
        padding-right: 10px;
        font-size: 18px;
      }
      &.form-item__flex{
        display: flex;
        padding-bottom: 15px;

        &>label:first-child{
          flex-grow: 1;
        }
      }
    }


  }
</style>
