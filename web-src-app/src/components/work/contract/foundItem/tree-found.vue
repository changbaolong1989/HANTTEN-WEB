<template>
  <!-- tree-->
  <div class="tree">
    <div class="tree-item" v-for="(item, index) in dataVO" :key="index">
      <div class="item-name">
        <span @click.stop="routerLink(item)" :class="{ active: item.isIgnore == 1,active1:item.isFinish==1 }">{{ item.label }}</span>
        <span>
          <i class="iconfont icondangqianweizhi linear" v-if="item.state"></i>
          <i class="iconfont icongengduo- dian" v-if="listarrData(item)" @click.stop="more(item)"></i>
          <i class="iconfont iconjiantou-1" v-if="item.children" @click.stop="toggle($event)"></i>
        </span>
      </div>
      <div class="children-item" v-show="false"><tree :dataVO="item.children"></tree></div>
    </div>
    <mt-popup v-model="popupVisible" position="bottom" popup-transition="popup-fade">
      <ul class="routerlink">
        <p>更多操作</p>
        <li v-for="(item, index) in listarr" :key="index" @click="routerMore(item)">
          <span>{{ item.label }}</span>
          <i class="iconfont">&#xe630;</i>
        </li>
      </ul>
    </mt-popup>
  </div>
</template>

<script>
export default {
  name: 'tree',
  data() {
    return {
      popupVisible: false,
      listarr: [],
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      foundItemData: this.SStorage.getItem('/app/foundItem'),
      dian: false
    };
  },
  props: {
    dataVO: Array
  },
  watch: {
    dataVO: {
      handler(newName, oldName) {
        this.dataVO = newName;
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    toggle(e) {
      var cla = e.target.getAttribute('class');
      if (cla == 'iconfont iconjiantou-1') {
        e.target.setAttribute('class', 'iconfont iconshouqi-');
        e.target.parentNode.parentNode.nextElementSibling.style.display = 'block';
      } else {
        e.target.setAttribute('class', 'iconfont iconjiantou-1');
        e.target.parentNode.parentNode.nextElementSibling.style.display = 'none';
      }
    },
    listarrData(item) {
      const vm = this;
      if (item.showTool) {
        var jurisdiction = Object.assign({}, this.SStorage.getItem('userPowerInfo'), item.powerInfo);
        var index = item.showTool;
        var arr = [];
        if (index == 1) {
          if (!!jurisdiction.projectModify && 1 != item.hideMenuFlg) {
            arr.push({
              label: '修改项目',
              link: ''
            });
          }
          if (!!jurisdiction.projectDelete && 1 != item.hideMenuFlg) {
            arr.push({
              label: '删除项目',
              link: ''
            });
          }
          if (!!jurisdiction.contractAdd) {
            arr.push({
              label: '添加合同',
              link: ''
            });
          }
          if (!!jurisdiction.projectClose && vm.foundItemData.projectSts != '1') {
            arr.push({
              label: '结束项目',
              link: ''
            });
          }
          if (!!jurisdiction.projectClose && vm.foundItemData.projectSts === '1') {
            arr.push({
              label: '已完成',
              link: ''
            });
          }
        } else if (index == 2) {
          if (!!jurisdiction.contractNumModify && 1 != item.hideMenuFlg) {
            arr.push({
              label: '修改合同',
              link: ''
            });
          }
          if (!!jurisdiction.contractDelete && 1 != item.hideMenuFlg) {
            arr.push({
              label: '删除合同',
              link: ''
            });
          }
          if (!!jurisdiction.contractUserModify) {
            arr.push({
              label: '分配合同编撰人',
              link: ''
            });
          }
          if (!!jurisdiction.taskAdd) {
            arr.push({
              label: '创建任务',
              link: ''
            });
          }
          if (!!jurisdiction.requestMenuAdd) {
            arr.push({
              label: '创建请款单',
              link: ''
            });
          }
        } else if (index == 3) {
          arr.push({
            label: '下载流程控制表',
            link: ''
          });
          if (!item.verifyState && item.projectSts !== '1') {
            if (!!jurisdiction.taskModify) {
              arr.push({
                label: '修改任务',
                link: ''
              });
            }
            if (!!jurisdiction.taskDelete) {
              arr.push({
                label: '删除任务',
                link: ''
              });
            }
          }
        } else if (index == 'requestMent') {
          if (!!jurisdiction.requestMenuDelete) {
            arr.push({
              label: '删除请款单',
              link: ''
            });
          }
        }
        if (arr.length > 0) {
         return true;
        }else{
          return false;
        }
      }else{
         return false;
      }
    },
    more(item) {
      let vm = this;
      vm.listarr = [];
      var jurisdiction = Object.assign({}, this.SStorage.getItem('userPowerInfo'), item.powerInfo);
      this.activeTree = item;
      var index = item.showTool;
      this.popupVisible = true;
      if (index == 1) {
        if (!!jurisdiction.projectModify && 1 != item.hideMenuFlg) {
          this.listarr.push({
            label: '修改项目',
            link: ''
          });
        }
        if (!!jurisdiction.projectDelete && 1 != item.hideMenuFlg) {
          this.listarr.push({
            label: '删除项目',
            link: ''
          });
        }
        if (!!jurisdiction.contractAdd) {
          this.listarr.push({
            label: '添加合同',
            link: ''
          });
        }
        if (!!jurisdiction.projectClose && vm.foundItemData.data[0].projectSts != '1') {
          this.listarr.push({
            label: '结束项目',
            link: ''
          });
        }
        if (!!jurisdiction.projectClose && vm.foundItemData.data[0].projectSts === '1') {
          this.listarr.push({
            label: '已完成',
            link: ''
          });
        }
      } else if (index == 2) {
        if (!!jurisdiction.contractNumModify && 1 != item.hideMenuFlg) {
          this.listarr.push({
            label: '修改合同',
            link: ''
          });
        }
        if (!!jurisdiction.contractDelete && 1 != item.hideMenuFlg) {
          this.listarr.push({
            label: '删除合同',
            link: ''
          });
        }
        if (!!jurisdiction.contractUserModify) {
          this.listarr.push({
            label: '分配合同编撰人',
            link: ''
          });
        }
        if (!!jurisdiction.taskAdd) {
          this.listarr.push({
            label: '创建任务',
            link: ''
          });
        }
        if (!!jurisdiction.requestMenuAdd) {
          this.listarr.push({
            label: '创建请款单',
            link: ''
          });
        }
        console.log("listarr",this.listarr)
      } else if (index == 3) {
        console.log('jurisdiction', jurisdiction);
        this.listarr.push({
          label: '下载流程控制表',
          link: ''
        });
        if (!item.verifyState && item.projectSts !== '1') {
          if (!!jurisdiction.taskModify) {
            this.listarr.push({
              label: '修改任务',
              link: ''
            });
          }
          if (!!jurisdiction.taskDelete) {
            this.listarr.push({
              label: '删除任务',
              link: ''
            });
          }
        }
      } else if (index == 'requestMent') {
        if (!!jurisdiction.requestMenuDelete) {
          this.listarr.push({
            label: '删除请款单',
            link: ''
          });
        }
      }
    },
    routerLink(item) {
      let vm = this;
      if(1==item.treeNodeLevel||2==item.treeNodeLevel&&item.objType=='project'||3==item.treeNodeLevel||5==item.treeNodeLevel||6==item.treeNodeLevel){
        vm.bus.$emit(
          'treeClick',
          vm.tool.deepCopy(
            {
              name: '点击树节点',
              data: item
            },
            vm.tool.deepCopy
          )
        );
      }
      if (item.treeNodeLevel == 6) {
        this.bus.$emit(
          'treeChange',
          vm.tool.deepCopy(
            {
              name: '左边栏数据被点击',
              data: item
            },
            vm.tool.deepCopy
          )
        );
      }

      if (item.treeNodeLevel == 5) {
        //请款单
        if (item.objType == 'requestMent') {
          vm.bus.$emit(
            'request-payout',
            vm.tool.deepCopy(
              {
                name: '请款单提交审核',
                data: item
              },
              vm.tool.deepCopy
            )
          );
        }
        if (item.objType == null) {
          this.bus.$emit(
            'treeTask',
            vm.tool.deepCopy(
              {
                name: '任务',
                data: item
              },
              vm.tool.deepCopy
            )
          );
        }
      }
      if (item.treeNodeLevel == 3) {
        //请款单
        vm.bus.$emit(
          'contract',
          vm.tool.deepCopy(
            {
              name: '合同提交审核',
              data: item
            },
            vm.tool.deepCopy
          )
        );
      }
      if (item.treeNodeLevel == 1) {
        vm.bus.$emit(
          'project',
          vm.tool.deepCopy(
            {
              name: '项目',
              data: item
            },
            vm.tool.deepCopy
          )
        );
      }
    },
    routerMore(item) {
      let vm = this;
      if (item.label == '创建任务') {
        this.bus.$emit('establish', { tree: this.activeTree });
      } else if (item.label == '删除任务') {
        this.popupVisible = false;
        this.MessageBox.confirm('', {
          message: '确定执行删除操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.bus.$emit('deleteTask', { tree: this.activeTree });
          })
          .catch(err => {});
      } else if (item.label == '修改任务') {
        this.bus.$emit('modification', { tree: this.activeTree });
      } else if (item.label == '结束项目') {
        this.popupVisible = false;
        this.MessageBox.confirm('', {
          message: '确定执行结束项目操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.requestPost('/project/close/closeProject', {
              projectId: vm.foundItemData.projectId
            })
              .then(res => {
                if (res.data.status == 'success') {
                  vm.foundItemData.data[0].projectSts = '1';
                  this.SStorage.setItem('/app/foundItem', vm.foundItemData);
                  this.$router.go(0);
                } else {
                  this.Toast(res.data.message);
                }
              })
              .catch(err => {
                console.log(err);
              });
          })
          .catch(err => {});
      } else if (item.label == '下载流程控制表') {
        this.bus.$emit('downloadFile', { tree: this.activeTree });
      } else if (item.label == '创建请款单') {
        vm.bus.$emit(
          'request-payout',
          vm.tool.deepCopy(
            {
              name: '创建请款单',
              data: this.activeTree
            },
            vm.tool.deepCopy
          )
        );
      } else if (item.label == '分配合同编撰人') {
        vm.bus.$emit(
          'distribution-contract-compiler',
          vm.tool.deepCopy(
            {
              name: '分配合同编撰人',
              data: this.activeTree
            },
            vm.tool.deepCopy
          )
        );
      } else if (item.label == '修改项目') {
        this.popupVisible = false;
        this.bus.$emit('ModifyProject', { tree: this.activeTree });
      } else if (item.label == '删除项目') {
        this.popupVisible = false;
        this.MessageBox.confirm('', {
          message: '确定执行删除操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.bus.$emit('DeleteProject', { tree: this.activeTree });
          })
          .catch(err => {});
      } else if (item.label == '合同提交审核' || item.label == '添加合同' || item.label == '删除合同' || item.label == '修改合同') {
        vm.bus.$emit('contract', vm.tool.deepCopy({ name: item.label, data: this.activeTree }, vm.tool.deepCopy));
      }
    }
  }
};
</script>
<style scoped="scoped" lang="scss">
.tree {
  width: 100%;
  .routerlink {
    p {
      box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
      height: 90px;
      line-height: 90px;
      font-size: 28px;
      color: #b3b3b3;
      padding: 0 30px;
    }
    li {
      height: 90px;
      line-height: 90px;
      font-size: 28px;
      color: #333333;
      margin: 0 30px;
      border-bottom: 2px solid #e5e5e5;
      display: flex;
      justify-content: space-between;
      .iconfont {
        font-size: 28px;
      }
    }
  }
  .item-name {
    display: flex;
    justify-content: space-between;
    font-size: 28px;
    color: #333333;
    height: 90px;
    line-height: 90px;
    padding: 0 0 0 30px;
    border-bottom: 2px solid #e5e5e5;
    .active {
      color: #999;
    }
    .active1 {
      color: #67C23A;
    }
    .iconfont {
      color: #979797;
      font-size: 32px;
      margin-right: 30px;
    }
    .dian {
      position: absolute;
      right: 60px;
    }
    .icondangqianweizhi {
      position: absolute;
      right: 100px;
      border-radius: 50%;
      width: 80px;
      height: 80px;
      text-align: center;
      transform: scale(0.5);
      transform-origin: 0% 0%;
      color: #fff;
      margin-top: 20px;
      font-size: 40px;
    }
  }
  .children-item {
    padding: 0 0 0 20px;
    background-color: #fff;
    .tree {
      .children-item {
        background-color: #f1f5f8;
        .tree {
          .children-item {
            background-color: #e5ebef;
            .tree {
              .children-item {
                background-color: #d9e2e7;
                .tree {
                  .children-item {
                    background-color: #cdd9df;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
