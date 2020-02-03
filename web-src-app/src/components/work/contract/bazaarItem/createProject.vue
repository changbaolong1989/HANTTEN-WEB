<template>
  <!-- 修改市场项目-->
  <div class="createProject">
    <form class="projectForm" id="projectForm" @submit.prevent="submitForm">
      <div class="form-item">
        <label for="">项目名称</label>
        <input type="text" placeholder="请输入项目名称" v-model="projectForm.projectName" v-validate="{ required: true,max:20 }" name="projectName" data-vv-as="项目名称" />
      </div>
      <div class="form-item">
        <label for="">项目概况</label>
        <input type="text" placeholder="请输入项目概况" v-model="projectForm.projectSurvey" v-validate="{ required: true,max:200 }" name="projectSurvey" data-vv-as="项目概况" />
      </div>
      <div class="form-item" @click="openPicker">
        <mt-cell title="开标时间">
          <span><input type="text" readonly="readonly" placeholder="请选择" v-model="projectForm.Pickertext" v-validate="'required'" name="time" data-vv-as="开标时间" /></span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <mt-datetime-picker ref="picker" type="date" v-model="projectForm.pickerValue" @confirm="handleConfirm"></mt-datetime-picker>
      <div class="form-item" @click="popupVisible = true">
        <mt-cell title="指定主提审人">
          <span>
            <input
              type="text"
              readonly="readonly"
              placeholder="请选择"
              v-model="projectForm.remand"
              v-validate="{ required: true, weight: true }"
              name="person"
              data-vv-as="指定主提审人"
            />
          </span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 指定主提审人弹窗-->
      <mt-popup v-model="popupVisible" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>指定主提审人</p>
          <div class="item" v-for="(item, index) in radiolist">
            <label :for="'radio' + index">
              {{ item.userName }}
              <input type="radio" :id="'radio' + index" v-model="projectForm.remand" :value="item.userId" name="radio" @change="radioChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisible = false">确定</mt-button></div>
      </mt-popup>
      <div class="form-item noborder">
        <mt-cell title="选择其他组员">
          <span @click="popupVisibleMultiple = true">选择</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <ul>
          <li v-for="(item, index) in projectForm.list">
            <span>{{ item.label }}</span>
          </li>
        </ul>
      </div>
      <mt-popup v-model="popupVisibleMultiple" popup-transition="popup-fade" position="bottom">
        <div class="checkboxlist">
          <p>选择其他组员</p>
          <mt-checklist v-model="projectForm.val" :options="checklist" @change="MultipleChange"></mt-checklist>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleMultiple = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="commonFooter">
      <mt-button @click="rollback">取消</mt-button>
      <mt-button class="linear" form="projectForm">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectForm: {
        projectName: '',
        projectSurvey: '',
        pickerValue: new Date(),
        remand: '',
        Pickertext: '',
        list: [],
        val: []
      },
      popupVisible: false,
      popupVisibleMultiple: false,
      radiolist: [],
      checklist: [],
      requiredlist: [],
      radioId: ''
    };
  },
  created() {
    this.$validator.extend('weight', {
      getMessage: field => '指定主提审人不可以和其他组员重复',
      validate: value => {
        return this.verify(value);
      }
    });
    this.list = this.SStorage.getItem(this.$route.path); /* 请求详情数据 */
    this.init(); /* 初始化 */
  },
  methods: {
    init() {
      /* 请求修改数据*/
      this.requestPost('/tenderTask/query/searchTenderTaskInfoById', { tenderTaskId: this.list.tenderTaskId })
        .then(res => {
          var data = res.data.dataVO;
          console.log("市场部回显数据",data)
          this.projectForm.projectName = data.tenderTaskName;
          this.projectForm.projectSurvey = data.tenderTaskContent;
          this.projectForm.Pickertext = data.startDate;
          this.projectForm.pickerValue = new Date(data.startDate);
          this.departmentId = data.departmentId;
          this.tenderTaskId = data.tenderTaskId;
          this.delegateTaskId = data.delegateTaskId;

          for (var i = 0; i < data.userList.length; i++) {
            if (data.userList[i].isMainDuty == 1) {
              this.radioId = data.userList[i].userId;
              this.projectForm.remand = data.userList[i].userName;
            }else{
              this.projectForm.list.push({
                label: data.userList[i].userName,
                value: data.userList[i].userId
              });
            }
          }
        })
        .catch(err => {
          console.log(err);
        });

      /* 请求单选多选数据*/
      this.requiredParameter = {
        departmentId: this.marketDeptId
      };
      this.requestPost('/tenderTask/query/searchUsersByDepartmentId', this.requiredParameter)
        .then(res => {
          this.radiolist = res.data.dataList;
          res.data.dataList.map((value, index, arry) => {
            this.checklist.push({ label: value.userName, value: value.userId });
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    verify(val) {
      /* 自定义验证 */
      var state = true;
      this.projectForm.list.forEach(function(i) {
        if (i.label == val) {
          state = false;
        }
      });
      return state;
    },
    openPicker() {
      /* 打开日期选择插件 */
      this.$refs.picker.open();
    },
    handleConfirm() {
      /* 日期格式化 */
      this.projectForm.Pickertext = this.formatting(this.projectForm.pickerValue);
    },
    removeList(index) {
      /* 删除其他组员列表展示 */
      this.list.splice(index, 1);
    },
    radioChange() {
      const vm = this;
      this.radiolist.forEach(function(i) {
        if (i.userId === vm.projectForm.remand) {
          vm.projectForm.remand = i.userName;
          vm.radioId = i.userId;
        }
      });
    },
    MultipleChange() {
      const vm = this;
      this.projectForm.list = [];
      this.projectForm.val.forEach(function(e) {
        const value = vm.checklist.filter(function(item) {
          return item.value == e;
        });
        vm.projectForm.list.push(value[0]);
      });
    },
    submitForm() {
      this.$validator.validateAll().then(result => {
        if (result) {
          const vm = this;
          this.requiredlist.push({
            userId: this.radioId,
            isMainDuty: 1
          });
          this.projectForm.list.forEach(function(i) {
            vm.requiredlist.push({
              userId: i.value,
              isMainDuty: 0
            });
          });
          this.requiredParameter = {
            departmentId: this.departmentId,
            tenderTaskId: this.tenderTaskId,
            startDate: this.projectForm.pickerValue.getTime(),
            tenderTaskContent: this.projectForm.projectSurvey,
            tenderTaskName: this.projectForm.projectName,
            tenderTaskSts: 0,
            userList: this.requiredlist,
            delegateTaskId:this.delegateTaskId
          };
          this.requestPost('/tenderTask/modify/updateTenderTask', this.requiredParameter)
            .then(res => {
              if (res.data.status == 'success') {
                this.$router.push('/app/projectList');
              } else {
                this.Toast(res.data.message);
              }
              return;
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          this.Toast({
            message: this.errors.all()[0],
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        }
      });
    },
    rollback() {
      this.$emit('colseRight');
    }
  }
};
</script>
<style lang="scss" scoped>
.createProject {
  width: 100%;
  padding-bottom: 110px;
}
</style>
