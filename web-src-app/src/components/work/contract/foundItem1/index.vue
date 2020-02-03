<template>
  <!-- 合同项目主页面 -->
  <div class="found-item more-sublevel">
    <!-- 左侧边栏 -->
    <transition name="slide-fade">
      <div class="contractAside" v-if="state">
        <div class="treeShow">
          <tree :dataVO="dataVO"></tree>
          <i class="iconfont iconshouqi-" @click="state = false"></i>
        </div>
      </div>
    </transition>
    <!-- 页面默认显示头部内容-->
    <div class="foundItemIndex">
      <ul class="contractList">
        <li @click="state = true">
          <span>全部项目-{{ list.projectNum }}</span>
          <i class="iconfont">&#xe630;</i>
        </li>
      </ul>
    </div>

    <!-- 合同主页面 -->
    <contract v-if="contractState" v-show="showComponents" :transmitData="transmitData"></contract>
    <!-- 任务主页面 -->
    <task v-if="taskState" v-show="showComponents" :transmitData="transmitData"></task>
    <!-- 请款单页面 -->
    <requestPayout v-if="requestPayoutState" v-show="showComponents" :transmitData="transmitData"></requestPayout>
    <!-- 项目详情页面 -->
    <project v-if="projectState" v-show="showComponents" :transmitData="transmitData"></project>
    <!-- 项目基本信息表 -->
    <project-basic-information v-if="projectBasicInformationState" v-show="showComponents" :transmitData="transmitData"></project-basic-information>

    <!-- 功能弹窗 -->
    <transition name="slide-fade-right" class="createProject">
      <!-- 修改项目 -->
      <div class="createProject" v-if="showModifyProject"><modify-project :list="list" :createContract="createContract"></modify-project></div>
      <!-- 新增和修改合同 -->
      <div class="createProject" v-if="showContractAdd"><dialog-contract-add :data="dialogInfo" @cancel="cancelDialog('showContractAdd')"></dialog-contract-add></div>
      <!-- 分配合同编撰人 -->
      <div class="createProject" v-if="showDialogDistributionContractCompiler">
        <dialog-distribution-contract-compiler :data="dialogInfo" @cancel="cancelDialog('showDialogDistributionContractCompiler')"></dialog-distribution-contract-compiler>
      </div>
      <!-- 创建任务/修改任务 -->
      <div class="createProject" v-if="slideRight">
        <createTask ref="create" :createList="createList" @cancelTask="cancelDialog('slideRight')" :createState="createState" :modification="modification"></createTask>
      </div>
      <!-- 创建请款单 -->
      <div class="createProject" v-if="showDialogRequestPayout">
        <dialog-request-payout :data="dialogInfo" @cancel="cancelDialog('showDialogRequestPayout')"></dialog-request-payout>
      </div>
    </transition>
  </div>
</template>

<script>
import tree from './tree-found.vue'; /* 左侧树组件 */
import task from './task/index.vue'; /* 任务主页面 */
import contract from './contract/index.vue'; /* 合同主页面 */
import requestPayout from './requestPayout/index.vue'; /* 请款单页面 */
import project from './project/index.vue'; /* 项目详情页面 */
import projectBasicInformation from './projectBasicInformation/index.vue'; /* 项目基本情况表 */

import modifyProject from './project/modifyProject.vue'; /* 修改项目 */
import dialogContractAdd from './contract/dialogContractAdd.vue'; /* 合同添加 */
import dialogDistributionContractCompiler from './contract/dialogDistributionContractCompiler.vue'; /* 分配合同编撰人 */
import createTask from './task/createTask.vue'; /* 创建任务 */
import dialogRequestPayout from './requestPayout/dialogRequestPayout.vue'; /* 创建请款单 */
export default {
  data() {
    /* 数据 */
    return {
      state: false /* 左侧树显示隐藏 */,
      contractState: false /* 合同显示 */,
      taskState: false /* 任务显示 */,
      requestPayoutState: false /* 请款单显示 */,
      projectState: false /* 项目详情显示 */,
      projectBasicInformationState: false, // 项目基本信息表
      showComponents: true, //是否显示模块组件
      showModifyProject: false, // 是否显示修改项目弹窗
      showContractAdd: false, // 显示添加合同表单弹窗
      slideRight: false, // 显示创建任务
      showDialogRequestPayout: false, // 显示创建请款单
      createList: {} /*, 创建任务数据 */,
      showDialogDistributionContractCompiler: false, // 显示分配合同编撰人表单弹窗
      dataVO: [] /* 左侧树数据 */,
      list: this.SStorage.getItem(this.$route.path) /* 列表数据 */,
      targetTree: {} /* 当前被点击左侧树的数据 */,
      transmitData: {} /* 左边栏的数据 */,
      dialogInfo: {}, // 表单弹窗的数据
      createContract: {} /* 修改项目回显数据 */
    };
  },
  watch: {
    showComponents: {
      handler() {
        let vm = this;
        if (vm.showComponents) {
          vm.resetDialog(); // 重置表单弹窗的显示状态
        }
      },
      immediate: true
    }
  },
  components: {
    /* 子组件 */
    tree /* 左侧树组件 */,
    contract /* 合同主页面 */,
    task /* 任务主页面 */,
    requestPayout /* 请款单页面 */,
    project /* 项目详情页面 */,
    'project-basic-information': projectBasicInformation,
    'modify-project': modifyProject, // 修改项目
    'dialog-contract-add': dialogContractAdd, // 合同添加
    'dialog-distribution-contract-compiler': dialogDistributionContractCompiler, // 分配合同编撰人
    createTask: createTask, // 创建任务
    'dialog-request-payout': dialogRequestPayout // 创建请款单
  },
  created() {
    this.list = this.SStorage.getItem(this.$route.path);
    this.createList.projectNum = this.list.projectNum;
    /* 初始化 */
    this.treeData(); /* 请求左侧树数据 */
  },
  mounted() {
    /* 初始化完成 */
    const vm = this;
    this.bus.$on('treeChange', function(val) {
      vm.treeChange(val);
    });
    // 监听树的<更多事件>
    vm.bus.$on('treeMore', function(data, node) {
      vm.treeMore(data, node);
    });
  },
  methods: {
    /**
     * @method 计算节点
     */
    computeNode(nodeData) {
      let vm = this;
      // 根据跳转菜单目标修改展示组件
      let sessionData = this.SStorage.getItem(vm.$route.path);
      /**
       * @method 根据标识查找目标节点
       * @param {Object} index表示
       * @param {Object} obj 被查找对象
       */
      let findNodeTarget = function(index, obj) {
        if (index == obj.id) {
          // 如果该对象与查询标识一致就返回改对象
          return obj;
        } else if (obj.children && obj.children.length) {
          let target = null;
          // 如果该对象有子集则继续查找
          for (let i = 0, len = obj.children.length; i < len; i++) {
            let t = findNodeTarget(index, obj.children[i]);
            if (t) {
              target = t;
            }
          }
          return target;
        } else {
          // 上述情况都不满足返回null
          return null;
        }
      };
      let targetNode = findNodeTarget(sessionData.businessId, nodeData);
      targetNode.businessId = targetNode.businessId ? targetNode.businessId : targetNode.id;
      targetNode.projectId = targetNode.projectId ? targetNode.projectId : sessionData.projectId;
      targetNode.projectNum = targetNode.projectNum ? targetNode.projectNum : sessionData.projectNum;
      this.SStorage.setItem(vm.$route.path, targetNode);
      vm.bus.$emit('treeChange', targetNode);
    },
    treeData() {
      /* 请求左侧树数据 */
      let vm = this;
      this.requestPost('/project/query/searchProjectTree', {
        projectId: this.list.projectId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.dataVO = [res.data.dataVO];
            // 计算节点
            vm.computeNode(res.data.dataVO);
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    treeChange(val) {
      /* 左侧树被点击 */
      this.resetComponent();
      this.targetTree = val;
      if (this.targetTree.treeNodeLevel == 1) {
        this.transmitData = {
          name: '项目',
          data: val
        };
        this.projectState = true;
        this.state = false;
      } else if (this.targetTree.treeNodeLevel == 2) {
        if ('project' == this.targetTree.objType) {
          this.transmitData = {
            name: '项目基本情况表',
            data: val
          };
          this.projectBasicInformationState = true;
          this.state = false;
        }
      } else if (this.targetTree.treeNodeLevel == 3) {
        this.transmitData = {
          name: '合同提交审核',
          data: val
        };
        this.contractState = true;
        this.state = false;
      } else if (this.targetTree.treeNodeLevel == 5) {
        if (this.targetTree.objType == 'requestMent') {
          this.transmitData = {
            name: '请款单提交审核',
            data: val
          };
          this.requestPayoutState = true;
          this.state = false;
        } else if (this.targetTree.objType == 'task') {
          this.transmitData = val;
          this.taskState = true;
          this.state = false;
        }
      } else if (this.targetTree.treeNodeLevel == 6) {
        this.transmitData = val;
        this.taskState = true;
        this.state = false;
      }
    },
    /**
     * @method 监听树的更多事件
     * @param {Object} data 点击的更多数据
     * @param {Object} node 当前点击节点数据
     */
    treeMore(data, node) {
      let vm = this;
      vm.state = false; // 隐藏树
      vm.showComponents = false; // 隐藏显示的组件
      vm.resetDialog(); // 重置表单弹窗的显示状态
      // 显示对应的表单弹窗
      switch (data.label) {
        case '结束项目':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.showComponents = true; // 删除提示可以显示表单弹窗
            vm.terminateProject(node);
          }).catch(err => {});
          break;
        case '修改项目':
          vm.dialogInfo = { tree: node };
          vm.changeProject(vm.dialogInfo);
          break;
        case '删除项目':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.dialogInfo = { tree: node };
            vm.showComponents = true; // 删除提示可以显示表单弹窗
            vm.removeProject(vm.dialogInfo);
          }).catch(err => {});
          break;
        case '添加合同':
          vm.dialogInfo = { name: '添加合同', data: node };
          vm.showContractAdd = true;
          break;
        case '修改合同':
          vm.dialogInfo = { name: '修改合同', data: node };
          vm.showContractAdd = true;
          break;
        case '删除合同':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.dialogInfo = { name: '删除合同', data: node };
            vm.showComponents = true; // 删除提示可以显示表单弹窗
            vm.removeContract(vm.dialogInfo); // 删除合同
          }).catch(err => {});
          break;
        case '分配合同编撰人':
          vm.dialogInfo = node;
          vm.showDialogDistributionContractCompiler = true; // 删除提示可以显示表单弹窗
          break;
        case '创建任务':
          vm.dialogInfo = { tree: node };
          vm.createRequest(vm.dialogInfo);
          break;
        case '创建请款单':
          vm.dialogInfo = node;
          vm.showDialogRequestPayout = true;
          break;
        case '合同作废':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.dialogInfo = node;
            vm.contractInvalid(vm.dialogInfo);
          }).catch(err => {});;
        case '下载流程控制表':
          vm.showComponents = true; // 删除提示可以显示表单弹窗
          let tempUrl = '/taskExecute/downLoad/downLoadTaskExecuteExecl?taskId=' + node.id;
          vm.downloadFile(tempUrl, '', true); // main.js 中引用的方法
          break;
        case '修改任务':
          vm.dialogInfo = node;
          vm.getTaskInfo({ tree: vm.dialogInfo });
          break;
        case '删除任务':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.showComponents = true; // 删除提示可以显示表单弹窗
            vm.dialogInfo = node;
            vm.removeTask({ tree: vm.dialogInfo }); // 删除请款单
          }).catch(err => {});;
          break;
        case '删除请款单':
          vm.MessageBox.confirm('确定执行此操作?').then(action => {
            vm.showComponents = true; // 删除提示可以显示表单弹窗
            vm.removeRequestMenu(node); // 删除请款单
          }).catch(err => {});;
          break;
        default:
          // 如果都没有匹配到就显示组件(有一些像删除项目操作是没有表单弹窗显示的)
          vm.showComponents = true;
          break;
      }
    },
    resetComponent() {
      /* 重置组件状态 */
      this.projectState = false;
      this.contractState = false;
      this.requestPayoutState = false;
      this.taskState = false;
      this.projectBasicInformationState = false;
    },
    /**
     * @method 重置表单弹窗的显示状态
     */
    resetDialog() {
      let vm = this;
      // 修改项目
      vm.showModifyProject = false;
    },
    /**
     * @method 结束项目
     * @param {Object} node 当前节点信息(项目信息)
     */
    terminateProject(node) {
      let vm = this;
      let foundItemData = this.SStorage.getItem('/app/foundItem');
      vm.requestPost('/project/close/closeProject', {
        projectId: foundItemData.projectId
      })
        .then(res => {
          if (res.data.status == 'success') {
            foundItemData.projectSts = '1';
            vm.SStorage.setItem('/app/foundItem', foundItemData);
            vm.$router.go(0);
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * @method 修改项目
     * @param {Object} val 当前节点信息
     */
    changeProject(val) {
      this.showModifyProject = true;
      // 查询项目详情
      this.requestPost('/project/query/searchProjectInfo', {
        // 项目id
        projectId: this.list.projectId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.createContract = res.data.dataVO;
            // this.$refs['createContract'].projectNumPrefix = res.data.dataVO.projectNumPrefix;
            // this.$refs['createContract'].projectForm.genre = res.data.dataVO.condense;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    removeProject(val) {
      /* 删除项目 */
      let vm = this;
      vm.requestPost('/project/delete/deleteProjectInfo', {
        // 项目id
        projectId: vm.list.projectId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            vm.Toast(res.data.message);
            vm.$router.push('/app/projectList');
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(e => {
          vm.Toast(e);
        });
    },
    /**
     * @method 删除合同
     */
    removeContract(val) {
      let vm = this;
      // 发送删除请求
      vm.requestPost('/project/delete/deleteContractInfo', {
        contractId: val.data.id
      })
        .then(res => {
          vm.Toast(res.data.message);
          // 合同添加或修改合同后修改目标节点,然后刷新页面
          let storage = vm.SStorage.getItem('/app/foundItem');
          storage.businessId = val.data.projectId;
          vm.SStorage.setItem('/app/foundItem', storage);
          vm.$router.go(0);
        })
        .catch(e => {
          vm.Toast(e);
        });
    },
    createRequest(val) {
      /* 创建任务 */
      this.modification = {};
      this.createState = 0;
      /* 请求合同类型*/
      this.createList.contractName = val.tree.label;
      this.createList.contractId = val.tree.id;
      this.createList.departmentId = val.tree.departmentId;
      this.requestPost('/task/query/searchDictContractTypeListByDepartmentId', {
        contractId: val.tree.id
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.createList.contractType = res.data.dataList;
            this.slideRight = true;
            this.state = false;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
      /* 请求编制成果文件人*/
      this.requestPost('/task/query/searchUserListByDepartmentId', {
        contractId: val.tree.id,
        taskId: val.tree.taskId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.createList.contractAchievement = res.data.dataList;
            this.slideRight = true;
            this.$refs.create.init();
            this.state = false;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * @method 合同作废
     */
    contractInvalid(val) {
      let vm = this;
      vm.MessageBox.prompt('', {
        message: '请输入作废原因！',
        title: '提示',
        showCancelButton: true,
        inputType: 'textarea'
      })
        .then(({ value, action }) => {
          vm.requestPost('/contract/update/invalidContract', {
            contractId: val.id,
            projectId: vm.list.projectId,
            invalidReason: value
          })
            .then(res => {
              vm.Toast(res.data.message);
              // 如果返回成功，则绘制数据，否则提示失败信息
              if (res.data.status == 'success') {
                // 左侧树刷新
                vm.$router.go(0);
              }
            })
            .catch(e => {
              vm.Toast(e);
            });
        })
        .catch(err => {});
    },
    /**
     * @method 修改任务----获取任务详情信息
     * @param {Object} val
     */
    getTaskInfo(val) {
      let vm = this;
      // 查询任务详情
      this.requestPost('/task/query/searchTaskInfo', {
        // 任务id
        taskId: val.tree.id
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.slideRight = true;
            vm.createRequest({
              tree: {
                label: res.data.dataVO.contractNum,
                id: res.data.dataVO.contractId
              }
            });
            this.modification = res.data.dataVO;
            this.createState = 1;
            this.createList.departmentId = val.tree.departmentId;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    /**
     * @method 删除任务
     * @param {Object} val 节点数据
     */
    removeTask(val) {
      let vm = this;
      vm.requestPost('/task/delete/deleteTaskInfo', {
        taskId: val.tree.id
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            vm.Toast(res.data.message);
            // 跳到合同位置
            let storage = vm.SStorage.getItem('/app/foundItem');
            storage.businessId = val.tree.contractId;
            vm.SStorage.setItem('/app/foundItem', storage);
            // 触发事件,重新刷新项目树
            vm.$router.go(0);
          } else {
            vm.Toast(res.data.message);
          }
        })
        .catch(e => {
          vm.Toast(e);
        });
    },
    /**
     * @method 删除请款单
     * @param {Object} val 节点数据
     */
    removeRequestMenu(val) {
      let vm = this;
      // 发送删除请求
      vm.requestPost('/requestMenu/delete/deleteRequestMenuInfo', {
        requestMenuId: val.id //(请款单ID)
      })
        .then(res => {
          vm.Toast(res.data.message);
          // 合同添加或修改合同后修改目标节点,然后刷新页面
          let storage = vm.SStorage.getItem('/app/foundItem');
          storage.businessId = val.contractId;
          vm.SStorage.setItem('/app/foundItem', storage);
          vm.$router.go(0);
        })
        .catch(e => {
          vm.Toast(e);
        });
    },
    cancelDialog(name) {
      let vm = this;
      vm.showComponents = true;
      /* 隐藏表单 */
      vm[name] = false;
    }
  }
};
</script>

<style lang="scss">
@import url('../../../more/common/css/fileList.css');
@import url('../../../more/common/css/foldedPanel.css');

.slide-fade-enter-active {
  /* 进入 */
  transition: all 0.8s ease;
}

.slide-fade-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}

.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateY(-900px);
}

/* 进入 */
.slide-fade-right-enter-active {
  transition: all 0.8s ease;
}

.slide-fade-right-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}

.slide-fade-right-enter,
.slide-fade-right-leave-to {
  transform: translateX(100%);
}

.primary {
  width: 100%;
  background-color: #fff;
  padding: 20px 0 0;

  button {
    font-size: 28px;
    color: #fff;
    width: 80%;
    margin: 20px auto;
    display: block;
    text-align: center;
    border-radius: 10px;
    height: 80px;
    line-height: 80px;
  }

  .mint-switch {
    width: 35%;
    margin: 0px auto 20px;
    font-size: 28px;
    color: #333;
  }
}
</style>
<style lang="scss" scoped>
.found-item {
  width: 100%;
  background-color: #f1f5f8;

  .contractAside {
    width: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    height: 100vh;
    position: fixed;
    z-index: 200000;

    .treeShow {
      width: 100%;
      background-color: #fff;
      overflow: hidden;
      min-height: 900px;
      max-height: 100vh;
      overflow: auto;
      position: relative;

      .iconshouqi- {
        position: fixed;
        bottom: 10px;
        color: #fff;
        font-size: 35px;
        left: 50%;
        right: 50%;
      }
    }
  }

  .createProject {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    background-color: #fff;
    width: 100%;
    height: 100vh;
    overflow: auto;
  }

  .contractList {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    background-color: #fff;

    li {
      height: 80px;
      line-height: 80px;
      display: flex;
      padding: 0px 30px;
      justify-content: space-between;
      font-size: 28px;
      color: #333333;

      i {
        font-size: 28px;
      }
    }
  }

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

  .A3 {
    box-shadow: none;
  }
}
</style>
