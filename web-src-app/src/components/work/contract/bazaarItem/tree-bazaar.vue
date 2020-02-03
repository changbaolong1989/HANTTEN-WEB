<template>
  <!-- tree-->
  <div class="tree">
    <div class="tree-item" @click="routerLink(item.treeNodeLevel, item)" v-for="(item, index) in dataVO" :key="index">
      <div class="item-name">
        <span>{{ item.label }}</span>
        <span>
          <i class="iconfont icondangqianweizhi linear" v-if="item.state"></i>
          <i class="iconfont icongengduo- dian" @click="more" v-if="item.showTool != 0 && listarr.length > 0"></i>
          <i class="iconfont iconjiantou-1" v-if="item.children" @click="toggle($event)"></i>
        </span>
      </div>
      <div class="children-item" v-show="false"><tree :dataVO="item.children"></tree></div>
    </div>
    <mt-popup v-model="popupVisible" position="bottom" popup-transition="popup-fade">
      <ul class="routerlink">
        <p>更多操作</p>
        <li @click="routerMore(item)" v-for="(item, index) in listarr" :key="index">
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
      popupVisible: false
    };
  },
  props: {
    dataVO: Array,
    list: Object,
    listarr: Array
  },
  watch: {
    dataVO: function(newval, oldval) {
      this.dataVO = newval;
    },
    list: function(newval, oldval) {
      this.list = newval;
    },
    listarr: {
      handler(newName, oldName) {
        this.listarr = newName;
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
    more() {
      this.popupVisible = true;
    },
    routerLink(rout, type) {
      if (rout == 2) {
        this.bus.$emit('sindex', type);
      }
    },
    routerMore(item) {
      if (item.label == '删除项目') {
        this.popupVisible = false;
        this.MessageBox.confirm('', {
          message: '确定执行删除操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            if (action == 'confirm') {
              this.requestPost('/tenderTask/delete/deleteTenderTask', { tenderTaskId: this.list.tenderTaskId })
                .then(res => {
                  if (res.data.status == 'success') {
                    this.SStorage.setItem('/app/projectList',{selected:"2"})
                    this.$router.push({
                      path: '/app/projectList'
                    });
                  } else {
                    this.Toast(res.data.message);
                  }
                  return;
                })
                .catch(err => {
                  console.log(err);
                });
            }
          })
          .catch(err => {
            if (err == 'cancel') {
              //取消的回调
              console.log(2);
            }
          });
      } else if (item.label == '修改项目') {
        this.$emit('rightShow', item);
        this.popupVisible = false;
      } else if (item.label == '中标状态') {
        this.$emit('rightShow', item);
      } else if (item.label == '已中标' || item.label == '未中标') {
        this.Toast({
          message: '当前状态不可以选中中标状态啦',
          position: 'middle',
          duration: 3000
        });
      } else if (item.label == '下载流程控制表') {
        this.$emit('downloadFiles', this.list);
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
      .disabled {
        color: #a7a7a7;
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
