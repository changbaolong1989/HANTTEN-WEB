<template>
  <!-- 创建任务-->
  <div class="createTask">
    <div class="exhibition">
      <ul>
        <li>
          <span>项目编号</span>
          <span>{{ createList.projectNum }}</span>
        </li>
        <li>
          <span>合同编号</span>
          <span>{{ createList.contractName }}</span>
        </li>
      </ul>
    </div>
    <form class="projectForm" id="projectForm" @submit.prevent="submitForm">
      <div class="form-item">
        <label for="">任务名称</label>
        <input type="text" placeholder="请输入任务名称" v-model="projectForm.taskName" v-validate="'required'" name="taskName" data-vv-as="任务名称" />
      </div>
      <div class="form-item" @click="popupVisibleGenre = true" v-if="createList.departmentId === 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'">
        <mt-cell title="合同类型">
          <input type="text" readonly="readonly" class="readonly" v-model="projectForm.genre" placeholder="请选择" v-validate="'required'" name="genre" data-vv-as="合同类型" />
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 合同类型弹窗-->
      <mt-popup v-model="popupVisibleGenre" position="bottom">
        <div class="radiolist">
          <p>合同类型</p>
          <div class="item" v-for="(item, index) in createList.contractType">
            <label :for="'genre' + index">
              {{ item.contractTypeName }}
              <input type="radio" :id="'genre' + index" v-model="projectForm.genre" :value="item.contractTypeId" name="genre" @change="GenreChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleGenre = false">确定</mt-button></div>
      </mt-popup>
      <div class="form-item" @click="popupVisibleCategory = true">
        <mt-cell title="咨询类别">
          <input
            type="text"
            readonly="readonly"
            class="readonly"
            v-model="projectForm.category"
            placeholder="请选择"
            v-validate="'required'"
            name="category"
            data-vv-as="咨询类别"
          />
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 咨询类别弹窗-->
      <mt-popup v-model="popupVisibleCategory" position="bottom">
        <div class="radiolist">
          <p>咨询类别</p>
          <div class="item" v-for="(item, index) in categorylist">
            <label :for="'category' + index">
              {{ item.counselTypeName }}
              <input type="radio" :id="'category' + index" v-model="projectForm.category" :value="item.counselTypeId" name="category" @change="categoryChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleCategory = false">确定</mt-button></div>
      </mt-popup>
      <div class="form-item" @click="achievementChange">
        <mt-cell title="编制成果文件人">
          <input
            type="text"
            readonly="readonly"
            class="readonly"
            v-model="projectForm.authorized"
            placeholder="请选择"
            v-validate="{ required: true, repetition: true }"
            name="authorized"
            data-vv-as="编制成果文件人"
          />
          <i :class="{ iconfont: true, 'iconjiantou-1': tuState, 'iconshouqi-': !tuState }"></i>
        </mt-cell>
      </div>
      <choosePrepare
        ref="choosePrepare"
        v-if="chooseState"
        :radiolist="createList.contractAchievement"
        :userId="projectForm.userId"
        :personList="projectForm.tableData"
        :finishDate="finishDate"
        @returned="returned"
      ></choosePrepare>
      <div class="form-item" @click="openPicker('picker')">
        <mt-cell title="完成时间">
          <input
            type="text"
            readonly="readonly"
            class="readonly"
            v-model="projectForm.Pickertext"
            placeholder="请选择"
            v-validate="'required'"
            name="Pickertext"
            data-vv-as="完成时间"
          />
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <mt-datetime-picker ref="picker" type="date" v-model="pickerValue" @confirm="handleConfirm('Pickertext')"></mt-datetime-picker>
      <div class="form-item">
        <label for="">所需工时</label>
        <input type="text" placeholder="请输入所需工时" v-model="projectForm.manHour" v-validate="{ required: true, numeric: true }" name="manHour" data-vv-as="所需工时" />
      </div>
      <div class="form-item">
        <label for="">任务概况</label>
        <input type="text" placeholder="请输入任务概况" v-model="projectForm.general" v-validate="'required'" name="general" data-vv-as="任务概况" />
      </div>
      <div class="noborder qita">
        <mt-cell title="其他组员">
          <span @click="addlist('其他组员')" class="router">添加</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <div class="DynamicallyAdd" v-for="(item, index) in projectForm.list" :key="index">
          <i class="iconfont iconshanchu" @click="removeSwipe(index)"></i>
          <div class="form-item" @click="operatorchange(index)">
            <mt-cell title="执行人" class="sideslip_cell">
              <input
                type="text"
                readonly="readonly"
                class="readonly"
                v-model="item.userName"
                placeholder="请选择"
                v-validate="{ required: true, oneself: true }"
                name="userName"
                data-vv-as="执行人"
              />
              <i :class="{ iconfont: true, operatorPerson: true, 'iconjiantou-1': item.operatorState, 'iconshouqi-': !item.operatorState }"></i>
            </mt-cell>
          </div>
          <div class="operator">
            <choosePrepare
              ref="choosePrepareList"
              v-if="item.operator"
              :radiolist="createList.contractAchievement"
              :finishDate="item.endDate"
              :index="index"
              @returned="userNameChange"
              :userId="item.userId"
              :personList="item.tableData"
            ></choosePrepare>
          </div>
          <div class="form-item" @click="openPicker('completion', index)">
            <mt-cell title="完成时间" class="sideslip_cell">
              <input type="text" readonly="readonly" class="readonly" v-model="item.endDate" placeholder="请选择" v-validate="'required'" name="endDate" data-vv-as="完成时间" />
              <i class="iconfont">&#xe630;</i>
            </mt-cell>
          </div>
          <mt-datetime-picker ref="completion" type="date" v-model="pickerValue" @confirm="handleConfirm('endDate', signboard)"></mt-datetime-picker>
          <div class="form-item">
            <label for="">所需工时</label>
            <input type="text" placeholder="请输入所需工时" v-model="item.needHour" v-validate="{ required: true }" name="needHour" data-vv-as="所需工时" />
          </div>
          <div class="form-item">
            <label for="">任务概况</label>
            <input type="text" placeholder="请输入任务概况" v-model="item.taskContent" v-validate="'required'" name="taskContent" data-vv-as="任务概况" />
          </div>
        </div>
      </div>
    </form>
    <div class="commonFooter">
      <mt-button @click="cancel">取消</mt-button>
      <mt-button class="linear" form="projectForm">确定</mt-button>
    </div>
  </div>
</template>

<script>
import choosePrepare from './choosePrepare.vue'; /* 编制成果人文件 */
export default {
  props: {
    createList: Object,
    modification: Object,
    createState: Number
  },
  data() {
    return {
      projectForm: {
        // 任务id
        taskId: '',
        taskName: '' /* 任务名称 */,
        genre: '' /* 合同类型 */,
        category: '' /* 咨询类别 */,
        authorized: '' /* 编制成果文件 */,
        Pickertext: '' /* 完成时间 */,
        manHour: '' /* 工时 */,
        general: '' /* 任务概况 */,
        list: [] /* 其他组员 */
      },
      chooseState: false /* 人员信息表状态 */,
      tuState: true /* 编制成果人文件箭头状态 */,
      finishDate: '' /* 编制成果人完成时间 */,
      popupVisibleGenre: false /* 合同类型弹窗显隐 */,
      popupVisibleCategory: false /* 咨询类别弹窗显隐 */,
      signboard: '',
      pickerValue: new Date(),
      Genrelist: [] /* 合同类型数据 */,
      categorylist: [] /* 咨询类别数据 */,
      personlist: [] /* 执行人数据 */
    };
  },
  components: {
    choosePrepare: choosePrepare
  },
  watch: {
    createList: {
      hander: function(newval, oldval) {
        this.createList = newval;
      },
      deep: true
    },
    modification: {
      hander: function(newval, oldval) {
        this.modification = newval;
      },
      deep: true
    },
    createState: {
      hander: function(newval, oldval) {
        this.createState = newval;
      },
      deep: true
    }
  },
  created() {
    this.$validator.extend('repetition', {
      getMessage: field => '编制成果人文件不可以和执行人重复',
      validate: value => {
        return this.verify(value);
      }
    });
    this.$validator.extend('oneself', {
      getMessage: field => '执行人不可以重复',
      validate: value => {
        return this.verifySelf(value);
      }
    });
  },
  mounted() {
    // 当不是造价部的时候，直接查询咨询类别列表
    if (this.createList.departmentId !== 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91') {
      // 查询合同类别
      this.requestPost('/task/query/searchDictCounselTypeListByContractTypeId', {
        // 合同类别id
        contractTypeId: this.createList.departmentId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            // 设置咨询类别列表
            this.categorylist = res.data.dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    }
  },
  methods: {
    init() {
      const vm = this;
      console.log('modification', this.modification);
      var modification = Object.keys(this.modification);
      if (modification.length != 0) {
        this.projectForm = {
          taskName: this.modification.taskName /* 任务名称 */,
          taskId: this.modification.taskId /* 任务名称 id*/,
          genre: this.modification.contractTypeName /* 合同类型 */,
          category: this.modification.counselTypeName /* 咨询类别 */,
          authorized: this.modification.userName /* 编制成果文件 */,
          authorizedId: this.modification.userId /* 主提神人Id */,
          Pickertext: this.modification.endDate /* 完成时间 */,
          manHour: this.modification.needHour /* 工时 */,
          general: this.modification.taskContent /* 任务概况 */,
          userId: this.modification.userId
        };
        this.$set(this.projectForm, 'list', this.modification.smallForm);
        this.$set(this.projectForm, 'tableData', this.modification.tableData);
        this.tuState = false;
        this.chooseState = true;
        if (this.modification.smallForm.length > 0) {
          this.projectForm.list.forEach(function(i) {
            i.operator = true;
          });
        }
      }
    },
    verify(val) {
      /* 自定义验证 */
      var state = true;
      this.projectForm.list.forEach(function(i) {
        if (i.userName == val) {
          state = false;
        }
      });
      return state;
    },
    verifySelf(val) {
      var state = 0;
      this.projectForm.list.forEach(function(i) {
        if (i.userName == val) {
          state++;
        }
      });
      if (state > 1) {
        return false;
      } else {
        return true;
      }
    },
    openPicker(text, index) {
      /* 打开日期选择插件 */
      if (text == 'completion') {
        this.signboard = index;
        this.$refs[text][0].open();
      } else {
        this.$refs[text].open();
      }
    },
    handleConfirm(text, index) {
      /* 日期格式化 */
      if (text == 'endDate') {
        this.projectForm.list[index][text] = this.formatting(this.pickerValue);
      } else {
        this.projectForm[text] = this.formatting(this.pickerValue);
        this.finishDate = this.projectForm[text];
      }
    },
    GenreChange() {
      /* 合同类型改变 */
      const vm = this;
      this.createList.contractType.forEach(function(i) {
        if (i.contractTypeId === vm.projectForm.genre) {
          vm.projectForm.genre = i.contractTypeName;
          vm.contractTypeId = i.contractTypeId; /* 合同类型Id */
          /* 请求咨询类别*/
          vm.requestPost('/task/query/searchDictCounselTypeListByContractTypeId', {
            contractTypeId: i.contractTypeId
          })
            .then(res => {
              if (res.data.status == 'success') {
                vm.categorylist = res.data.dataList;
              }else{
                this.Toast(res.data.message);
              }
            })
            .catch(err => {
              console.log(err);
            });
        }
      });
    },
    categoryChange() {
      const vm = this;
      this.categorylist.forEach(function(i) {
        if (i.counselTypeId === vm.projectForm.category) {
          vm.counselTypeId = i.counselTypeId; /* 咨询类型Id */
          vm.projectForm.category = i.counselTypeName;
        }
      });
    },
    personChange(j) {
      const vm = this;
      this.personlist.forEach(function(i) {
        if (i.value === vm.projectForm.listPerson) {
          vm.projectForm.list[j].person = i.label;
        }
      });
    },
    addlist(text) {
      /* 动态添加 */
      console.log('list', this.projectForm.list);
      this.projectForm.list.push({
        operatorState: true /* 箭头显示 */,
        endDate: '' /* 完成时间 */,
        taskContent: '' /* 任务名称 */,
        needHour: '' /* 工时 */,
        userName: '' /* 执行人 */
      });
    },
    removeSwipe(index) {
      /* 动态删除 */
      this.projectForm.list.splice(index, 1);
    },
    achievementChange() {
      /* 点击编制成果人文件 */
      this.chooseState = !this.chooseState;
      this.tuState = !this.tuState;
    },
    operatorchange(index) {
      /* 点击执行人 */
      this.projectForm.list[index].operatorState = !this.projectForm.list[index].operatorState;
      this.projectForm.list[index].operator = !this.projectForm.list[index].operator;
    },
    cancel() {
      this.$emit('cancelTask');
    },
    returned(val) {
      this.projectForm.authorized = val.item[0].userName;
      this.projectForm.authorizedId = val.item[0].userId;
    },
    userNameChange(val) {
      this.projectForm.list[val.index].userName = val.item[0].userName;
      this.projectForm.list[val.index].userId = val.item[0].userId;
    },
    submitForm() {
      this.$validator
        .validateAll()
        .then(result => {
          if (result) {
            const vm = this;
            var currentTaskform = [
              {
                contractId: this.createList.contractId /* 合同id*/,
                contractTypeId: this.contractTypeId /* 合同类型id */,
                counselTypeId: this.counselTypeId /* 咨询类别id */,
                taskName: this.projectForm.taskName /* 任务名称 */,
                taskId: this.projectForm.taskId,
                userList: [
                  /* 主提审神人 */
                  {
                    userId: this.projectForm.authorizedId /* 编制成果文件id */,
                    taskContent: this.projectForm.general /* 任务概况 */,
                    needHour: this.projectForm.manHour /* 工时 */,
                    isMainDuty: '1',
                    endDate: this.projectForm.Pickertext /* 完成时间 */
                  }
                ]
              }
            ];
            /* 其他组员*/
            vm.projectForm.list.forEach(function(i) {
              currentTaskform[0].userList.push({
                endDate: i.endDate,
                isMainDuty: 0,
                needHour: i.needHour,
                taskContent: i.taskContent,
                userId: i.userId
              });
            });
            // 访问后台的url
            let url = '';
            // 添加到后台的参数
            let params = {};
            if (vm.createState == 0) {
              url = '/task/add/insertTaskInfo';
              params = {
                // 设置合同id
                contractId: this.createList.contractId,
                // 表单数据
                taskList: currentTaskform
              };
            } else {
              // 修改任务地址
              url = '/task/modify/updateTaskInfo';
              // 修改任务参数
              params = {
                // 表单数据
                taskList: currentTaskform
              };
            }
            /* 提交任务*/
            this.MessageBox.confirm('', {
              message: '确定执行创建任务操作?',
              title: '提示',
              showCancelButton: true
            })
              .then(action => {
                vm.requestPost(url, params)
                  .then(res => {
                    if (res.data.status == 'success') {
                      this.Toast(res.data.message);
                      this.$emit('cancelTask');
                    } else {
                      this.Toast(res.data.message);
                    }
                  })
                  .catch(err => {
                    this.Toast(err);
                  });
              })
              .catch(err => {});
          } else {
            this.Toast({
              message: this.errors.all()[0],
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
<style lang="scss">
.createTask {
  .qita {
    .mint-cell-wrapper {
      font-size: 28px;
      .router {
        color: #06a1ee;
      }
    }
  }
  .operatorPerson {
    visibility: visible !important;
    margin-right: 10px;
  }
}
</style>
<style scoped lang="scss">
.createTask {
  width: 100%;
  padding-bottom: 110px;
  .exhibition {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    ul {
      width: 92%;
      margin: 0 auto;
      overflow: hidden;
      li {
        margin: 30px 0;
        display: flex;
        justify-content: space-between;
        font-size: 28px;
      }
    }
  }
  .DynamicallyAdd {
    margin-bottom: 20px;
    height: auto;
    background-color: #f1f5f8 !important;
    overflow: hidden;
    border-radius: 10px;
    .mint-cell-wrapper {
      .mint-cell-value {
        display: block !important;
        .form-item {
          padding: 0;
          margin: 30px auto 0;
          width: 92%;
          display: block;
          &:last-child {
            border-bottom: none;
          }
          .mint-cell {
            width: 100%;
            background-color: transparent;
            .iconfont {
              visibility: visible;
            }
          }
        }
      }
    }
  }
  .operator {
    background-color: #fff;
    border-radius: 10px;
  }
}
</style>
