<template>
  <!-- 创建合同项目-->
  <div class="createContract">
    <form class="projectForm formBottom" @submit.prevent="submitForm" id="nameform">
      <div class="form-item short" @click="changeNum">
        <mt-cell :title="'项目编号' + ' ' + projectNumPrefix">
          <span><input type="text" readonly="readonly" placeholder="请选择" v-model="projectForm.genre" v-validate="'required'" name="num" data-vv-as="项目编号" /></span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
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
      <div class="form-item">
        <mt-cell title="项目名称">
          <span><input type="text" placeholder="请输入" v-model="projectForm.projectName" v-validate="'required'" name="projectName" data-vv-as="项目名称" /></span>
          <i class="iconfont" style="visibility: hidden;">&#xe630;</i>
        </mt-cell>
      </div>
      <div class="form-item">
        <m-select
          v-model="projectForm.counselTypePeriod"
          :data="{ title: '咨询类别', options: this.types }"
          unique="counselTypeId"
          display="counselTypeName"
          v-validate="'required'"
          data-vv-name="咨询类别"
          @change="change"
        ></m-select>
      </div>

      <div class="form-item" style="border: none;">
        <mt-cell title="指定业务部门副总"></mt-cell>
        <div class="DynamicallyAdd">
          <div class="form-item top short">
            <mt-cell title="合同" :value="projectNumPrefix + (projectForm.genre != '请选择' ? projectForm.genre : '') + '-' + projectForm.contractNum"></mt-cell>
          </div>
          <div class="form-item">
            <m-select
              v-model="projectForm.category"
              :data="{ title: '指定人员', options: personlist }"
              unique="jobId"
              display="userName"
              v-validate="{ required: true }"
              data-vv-name="指定人员"
              @change="contractListChange1"
            ></m-select>
          </div>
          <div class="form-item">
            <mt-cell title="合同编号">
              <span><input type="text" placeholder="请输入合同编号" v-model="projectForm.contractNum" v-validate="'required'" name="contractNum" data-vv-as="合同编号" /></span>
              <i class="iconfont" style="visibility: hidden;">&#xe630;</i>
            </mt-cell>
          </div>
          <div class="form-item">
            <mt-cell title="跨部门合作"><mt-switch v-model="projectForm.transDepartment"></mt-switch></mt-cell>
          </div>
          <div v-if="projectForm.transDepartment">
            <div class="form-item">
              <mt-cell title="添加业务线"><mt-button size="small" class="linear addList" @click.stop.prevent="addList">+</mt-button></mt-cell>
            </div>
            <div class="form-item" v-if="i > 0">
              <i class="close" @click="removeList(1)">×</i>
              <m-select
                v-model="person"
                :data="{ title: '指定人员', options: personlist }"
                unique="jobId"
                display="userName"
                v-validate="{ required: true }"
                data-vv-name="指定人员"
                @change="contractListChange2"
              ></m-select>
            </div>
            <div class="form-item" v-if="i > 1">
              <i class="close" @click="removeList(2)">×</i>
              <m-select
                v-model="person1"
                :data="{ title: '指定人员', options: personlist }"
                unique="jobId"
                display="userName"
                v-validate="{ required: true }"
                data-vv-name="指定人员"
                @change="contractListChange3"
              ></m-select>
            </div>
          </div>
        </div>
      </div>
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
        projectName: '',
        counselTypePeriod: '',
        contractNum: '',
        category: '',
        transDepartment: false,
        contractList: []
      },
      list: [],
      i: 0,
      person: '',
      person1: '',
      projectNumPrefix: '' /* 初始化项目前缀 */,
      popupVisible: false,
      popupVisiblePerson: false,
      radiolist: [] /* 项目编号列表 */,
      personlist: [] /*, 指定人员列表 */,
      pitchList: '' /*选中得数据联动  */,
      // 公司缩写
      condense: '',
      types: []
    };
  },
  created() {
    /* 页面初始化请求数据 */
    this.init();
    /* 指定人员改变 */
    this.changePerson();
    //查询咨询类别下拉列表
    this.searchCounselTypePeriodList();
  },
  methods: {
    init() {
      const vm = this;
      /* 页面初始化请求数据 */
      this.requestPost('/project/query/excSearchMaxProjectNum', {})
        .then(res => {
          if (res.data.status == 'success') {
            vm.projectNumPrefix = res.data.dataVO.projectNumPrefix;
          }
          return;
        })
        .catch(err => {
          console.log(err);
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
        }
      });
      vm.condense = vm.projectForm.genre;
    },
    changePerson(type) {
      /* 指定人员改变 */
      this.requestPost('/project/query/excSearchPersonList', {})
        .then(res => {
          if (res.data.status == 'success') {
            this.personlist = res.data.dataList;
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
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
    addList() {
      /* 添加业务线 */
      if (this.i < 2) {
        this.i += 1;
      }
    },
    submitForm() {
      /* 提交表单 */
      const vm =this;
      vm.MessageBox.confirm('确定执行此操作?').then(action => {
        vm.$validator.validateAll().then(result => {
          if (result) {
            vm.requiredParameter = {
              // 项目编号前缀
              projectNum: vm.projectNumPrefix + vm.condense,
              // 公司id
              companyId: vm.projectNum.companyId,
              /* 项目名称 */
              projectName: vm.projectForm.projectName,
              /* 咨询类别 */
              counselTypePeriod: vm.counselTypePeriodId,
              // 合同编号
              contractNum: vm.projectForm.contractNum,
              // 部门id
              departmentId: vm.pitchList.departmentId,
              // 职位id
              jobId: vm.pitchList.jobId,
              contractList: vm.projectForm.contractList
            };
            vm.requestPost('/project/add/saveProjectInfo', vm.requiredParameter)
              .then(res => {
                if (res.data.status == 'success') {
                  vm.$router.push('/app/projectList');
                } else {
                  vm.Toast({
                    message: res.data.message,
                    position: 'middle',
                    className: 'toast',
                    duration: 3000
                  });
                }
              })
              .catch(err => {
                console.log(err);
              });
          } else {
            vm.Toast({
              message: vm.errors.all()[0],
              position: 'middle',
              className: 'toast',
              duration: 3000
            });
          }
        });
      });
    },
    cancel() {
      this.$router.go(-1);
    },
    change(val) {
      this.counselTypePeriodId = val;
    },
    verify(val) {
      var vm = this;
      // /* 自定义验证 */
      var state = true;
      var j = 0;
      this.projectForm.contractList.forEach(function(i) {
        if (i.jobId === val) {
          vm.Toast('不可以选择重复的指定人员');
          state = false;
        }
      });
      return state;
    },
    contractListChange1(val, index) {
      var state = this.verify(val);
      if (state) {
        this.projectForm.contractList[0] = index;
        this.countContractNum();
      } else {
        this.projectForm.category = '';
      }
    },
    contractListChange2(val, index) {
      var state = this.verify(val);
      if (state) {
        this.projectForm.contractList[1] = index;
        this.countContractNum();
      } else {
        this.person = '';
      }
    },
    contractListChange3(val, index) {
      var state = this.verify(val);
      if (state) {
        this.projectForm.contractList[2] = index;
        this.countContractNum();
      } else {
        this.person1 = '';
      }
    },
    removeList(index) {
      this.projectForm.contractList.splice(index, 1);
      this.i--;
      this.countContractNum();
    },
    /**
     * @method 计算部门缩写
     */
    countContractNum() {
      let vm = this;
      let a = '',
        b = '',
        c = ''; // 三个指定人员字符串拼接片段
      for (let i = 0, len = vm.projectForm.contractList.length; i < len; i++) {
        let li = vm.projectForm.contractList[i];
        if (li) {
          switch (i) {
            case 0:
              a = li.abbr ? li.abbr : '';
              break;
            case 1:
              b = li.abbr ? '/' + li.abbr : '';
              break;
            case 2:
              c = li.abbr ? '/' + li.abbr : '';
              break;
          }
        }
      }
      // 设置部门缩写,如果abc为空字符串则设置为空
      vm.projectForm.contractNum = a + b + c ? a + b + c + '01' : '';
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
  .close {
    display: block;
    text-align: right;
    font-size: 28px;
    padding: 0 20px;
    color: #666;
    font-size: 28px;
  }
}
</style>
