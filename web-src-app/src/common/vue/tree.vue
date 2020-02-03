<template>
  <!-- tree-->
  <div class="tree">
    <div class="tree-item" v-for="(item, index) in dataVO" :key="index">
      <div class="item-name">
        <span @click="routerLink(item.treeNodeLevel, item.identification)">{{ item.label }}</span>
        <span>
          <i class="iconfont icondangqianweizhi linear" v-if="item.state"></i>
          <i class="iconfont icongengduo- dian" v-if="item.showTool" @click="more(item.showTool)"></i>
          <i class="iconfont iconjiantou-1" v-if="item.children" @click="toggle($event)"></i>
        </span>
      </div>
      <div class="children-item" v-show="false"><tree :dataVO="item.children"></tree></div>
    </div>
    <mt-popup v-model="popupVisible" position="bottom" popup-transition="popup-fade">
      <ul class="routerlink">
        <p>更多操作</p>
        <router-link tag="li" :to="item.link" v-for="(item, index) in listarr" :key="index">
          <span>{{ item.label }}</span>
          <i class="iconfont">&#xe630;</i>
        </router-link>
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
      listarr: []
    };
  },
  props: {
    dataVO: Array
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
    more(index) {
      this.popupVisible = true;
      if (index == 1) {
        this.listarr = [
          {
            label: '修改项目',
            link: ''
          },
          {
            label: '删除项目',
            link: ''
          },
          {
            label: '添加合同',
            link: ''
          }
        ];
      } else if (index == 2) {
        this.listarr = [
          {
            label: '创建任务',
            link: ''
          },
          {
            label: '创建请款单',
            link: ''
          },
          {
            label: '删除合同',
            link: ''
          },
          {
            label: '修改合同',
            link: ''
          },
          {
            label: '分配合同编撰人',
            link: ''
          }
        ];
      } else if (index == 3) {
        this.listarr = [
          {
            label: '删除任务',
            link: ''
          },
          {
            label: '修改任务',
            link: ''
          }
        ];
      } else if (index == 'requestMent') {
        this.listarr = [
          {
            label: '删除请款单',
            link: ''
          }
        ];
      }
    },
    routerLink(rout,arg) {
      if (rout == 3) {
        this.$router.push({ path: '/app/customContract' });
      } else if (rout == 6) {
        this.$router.push({ path: '/app/foundItem',query:{ident:arg} });
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
