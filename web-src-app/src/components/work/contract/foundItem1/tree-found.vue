<template>
  <!-- tree-->
  <div class="tree">
    <div class="tree-item" v-for="(item, index) in dataVO" :key="index">
      <div class="item-name">
        <span @click.stop="routerLink(item)" :class="{ active: item.isIgnore == 1, active1: item.isFinish == 1 }">{{ item.label }}</span>
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
          if (!!jurisdiction.projectModify && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
            arr.push({
              label: '修改项目',
              link: ''
            });
          }
          if (!!jurisdiction.projectDelete && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
            arr.push({
              label: '删除项目',
              link: ''
            });
          }
          if (!!jurisdiction.contractAdd && '1' != item.isInvalidFlag) {
            arr.push({
              label: '添加合同',
              link: ''
            });
          }
          console.log('项目结束', vm.foundItemData);
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
          } else {
            if (vm.foundItemData.isInvalidFlag === '1') {
              arr.push({
                label: '已作废',
                link: ''
              });
            } else {
              arr.push({
                label: '作废项目',
                link: ''
              });
            }
          }
        } else if (index == 2) {
          if (!!jurisdiction.contractNumModify && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
            arr.push({
              label: '修改合同',
              link: ''
            });
          }
          if (!!jurisdiction.contractDelete && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
            arr.push({
              label: '删除合同',
              link: ''
            });
          }
          if (!!jurisdiction.contractUserModify && '1' != item.isInvalidFlag) {
            arr.push({
              label: '分配合同编撰人',
              link: ''
            });
          }
          if (!!jurisdiction.taskAdd && '1' != item.isInvalidFlag) {
            arr.push({
              label: '创建任务',
              link: ''
            });
          }
          if (!!jurisdiction.requestMenuAdd && '1' != item.isInvalidFlag) {
            arr.push({
              label: '创建请款单',
              link: ''
            });
          }
          if (!!jurisdiction.contractInvalid && '1' != item.isInvalidFlag) {
            arr.push({
              label: '合同作废',
              link: ''
            });
          }
        } else if (index == 3) {
          arr.push({
            label: '下载流程控制表',
            link: ''
          });
          if (!item.verifyState && item.projectSts !== '1' && '1' != item.isInvalidFlag) {
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
        } else if (index == 'requestMent' && '1' != item.isInvalidFlag) {
          if (!!jurisdiction.requestMenuDelete) {
            arr.push({
              label: '删除请款单',
              link: ''
            });
          }
        }
        if (arr.length > 0) {
          return true;
        } else {
          return false;
        }
      } else {
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
        if (!!jurisdiction.projectModify && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '修改项目',
            link: ''
          });
        }
        if (!!jurisdiction.projectDelete && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '删除项目',
            link: ''
          });
        }
        if (!!jurisdiction.contractAdd && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '添加合同',
            link: ''
          });
        }
        if(!!jurisdiction.projectClose && vm.foundItemData.isInvalidFlag === '1') {
          this.listarr.push({
            label: '已作废',
            link: ''
          });
        } else {
          if (!!jurisdiction.projectClose && vm.foundItemData.projectSts === '1') {
            this.listarr.push({
              label: '已完成',
              link: ''
            });
          } else if (!!jurisdiction.projectClose && vm.foundItemData.projectSts != '1') {
            this.listarr.push({
              label: '结束项目',
              link: ''
            });
          }
        }
      } else if (index == 2) {
        if (!!jurisdiction.contractNumModify && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '修改合同',
            link: ''
          });
        }
        if (!!jurisdiction.contractDelete && 1 != item.hideMenuFlg && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '删除合同',
            link: ''
          });
        }
        if (!!jurisdiction.contractUserModify && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '分配合同编撰人',
            link: ''
          });
        }
        if (!!jurisdiction.taskAdd && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '创建任务',
            link: ''
          });
        }
        if (!!jurisdiction.requestMenuAdd && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '创建请款单',
            link: ''
          });
        }
        if (jurisdiction.contractInvalid && '1' != item.isInvalidFlag) {
          this.listarr.push({
            label: '合同作废',
            link: ''
          });
        }
        console.log('listarr', this.listarr);
      } else if (index == 3) {
        console.log('jurisdiction', jurisdiction);
        this.listarr.push({
          label: '下载流程控制表',
          link: ''
        });
        if (!item.verifyState && item.projectSts !== '1' && '1' != item.isInvalidFlag) {
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
      } else if (index == 'requestMent' && '1' != item.isInvalidFlag) {
        if (!!jurisdiction.requestMenuDelete) {
          this.listarr.push({
            label: '删除请款单',
            link: ''
          });
        }
      }
    },
    routerLink(item) {
      const vm = this;
      // 修改定位目标
      let storage = vm.SStorage.getItem('/app/foundItem');
      storage.businessId = item.id;
      vm.SStorage.setItem('/app/foundItem', storage);
      item.projectNum = storage.projectNum;
      this.bus.$emit('treeChange', vm.tool.deepCopy(item, vm.tool.deepCopy));
    },
    routerMore(item) {
      let vm = this;
      vm.popupVisible = false; // 隐藏树的更多选项
      vm.bus.$emit('treeMore', item, vm.activeTree); // 树的更多事件触发
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
      color: #67c23a;
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
