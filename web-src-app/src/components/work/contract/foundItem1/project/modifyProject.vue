<template>
  <!-- 修改合同项目-->
  <div class="createContract">
    <form class="projectForm" @submit.prevent="submitForm" id="nameform">
      <div class="form-item short" @click="changeNum">
        <mt-cell :title="'项目编号' + ' ' + projectNumPrefix">
          <span><input type="text" readonly="readonly" placeholder="请选择" v-model="projectForm.genre" v-validate="'required'" name="num" data-vv-as="项目编号" /></span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <div class="form-item short">
        <mt-cell :title="'项目名称'">
          <span><input type="text" placeholder="请输入" v-model="projectForm.projectName" v-validate="'required'" name="num" data-vv-as="项目编号" /></span>
          <i class="iconfont" style="visibility: hidden;">&#xe630;</i>
        </mt-cell>
      </div>
      <div class="form-item short">
        <m-select
          v-model="projectForm.counselTypePeriod"
          :data="{title: '咨询类别',options: this.types}"
          unique="counselTypeId"
          display="counselTypeName"
          v-validate="'required'"
          data-vv-name="咨询类别"
          @change="change">
        </m-select>
      </div>
      <mt-popup v-model="popupVisible" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>项目编号</p>
          <div class="item" v-for="(item, index) in radiolist">
            <label :for="'radio' + index">
              {{ item.condense }}
              <input type="radio" :id="'radio' + index" v-model="projectForm.genre" :value="item.companyId" name="radio" @change="radioChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisible = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="commonFooter">
      <mt-button @click="cancel">取消</mt-button>
      <mt-button class="linear" form="nameform">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectForm: {
        /* 表单输入项 */
        genre: '',
        projectName:'',
        counselTypePeriod:''
      },
      counselTypePeriodId:'',
      projectNumPrefix: '' /* 初始化项目前缀 */,
      popupVisible: false /* 项目编号选择框 */,
      radiolist: [], /*, 项目编号列表 */
      types:[]
    };
  },
  props: ['list','createContract'],
  watch: {
    list(newval, oldval) {
      this.list = newval;
    },
    createContract(newval,oldval){
      this.createContract = newval;
      this.init()
    }
  },
  created() {
    //查询咨询类别下拉列表
    this.searchCounselTypePeriodList();
  },
  methods: {
    init(){
      this.projectNumPrefix = this.createContract.projectNumPrefix;
      this.projectForm.genre = this.createContract.condense;
      this.projectForm.projectName = this.createContract.projectName;
      this.projectForm.counselTypePeriod = this.createContract.counselTypePeriod;
    },
    // 查询列表数据
    searchCounselTypePeriodList() {
      const vm = this;
      let formData = {};
      // 查询咨询类别下拉列表
      vm.requestPost('/project/query/searchCounselTypePeriodList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.Toast({
              message: res.data.message
            });
          } else {
            // 设置数据
            vm.types = res.data.dataList;
          }
        })
        .catch(err => {
          vm.Toast({
            message: err
          });
        });
    },
    changeNum() {
      /* 项目编号选择 */
      this.popupVisible = true;
      this.requestPost('/project/query/searchCondenseList', {})
        .then(res => {
          if (res.data.status == 'success') {
            this.radiolist = res.data.dataList;
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    radioChange() {
      /* 项目编号回显 */
      const vm = this;
      this.radiolist.forEach(function(i) {
        if (i.companyId === vm.projectForm.genre) {
          vm.projectNum = i;
          vm.projectForm.genre = i.condense;
          vm.companyId = i.companyId;
        }
      });
    },
    submitForm() {
      /* 提交表单 */
      const vm = this;
      vm.MessageBox.confirm('确定执行此操作?').then(action => {
        vm.requiredParameter = {
          // 项目id
          projectId: vm.list.projectId,
          // 项目编号
          projectNum: vm.projectNumPrefix + vm.projectForm.genre,
          // 设置公司id
          companyId: vm.companyId,
          /* 项目名称 */
          projectName:vm.projectForm.projectName,
          /* 咨询类别 */
          counselTypePeriod:vm.counselTypePeriodId,
        };
        vm.requestPost('/project/modify/updateProjectInfo', vm.requiredParameter)
          .then(res => {
            vm.Toast(res.data.message);
            vm.$router.go(-1);
          })
          .catch(err => {
            vm.Toast(res.data.message);
          });
      });
    },
    cancel() {
      this.$parent.showComponents = true;
    },
    change(val){
      this.counselTypePeriodId = val;
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
