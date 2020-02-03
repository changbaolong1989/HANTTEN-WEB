<template>
  <!-- 修改项目-->
  <div class="modifyProject">
    <form class="projectForm">
      <div class="form-item" @click="popupVisibleGenre = true">
        <mt-cell title="项目编号">
          <span>{{ projectForm.genre }}</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <!-- 项目编号弹窗-->
      <mt-popup v-model="popupVisibleGenre" popup-transition="popup-fade" position="bottom">
        <div class="radiolist">
          <p>项目编号</p>
          <div class="item" v-for="(item, index) in Genrelist">
            <label :for="'genre' + index">
              {{ item.condense }}
              <input type="radio" :id="'genre' + index" v-model="projectForm.genre" :value="item.companyId" name="genre" @change="GenreChange" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleGenre = false">确定</mt-button></div>
      </mt-popup>
    </form>
    <div class="commonFooter">
      <mt-button>取消</mt-button>
      <mt-button class="linear" @click="routerLink">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      popupVisibleGenre: false,
      projectForm: {
        genre: '请选择'
      },
      Genrelist: [
        {
          companyId: 'b7df4e9d-b360-11e9-a036-0e411fd5cf91',
          condense: 'HHH'
        },
        {
          companyId: '5f6d99be-a538-11e9-a036-0e411fd5cf91',
          condense: '111我们是程序员我们是程序员我们是程序员'
        },
        {
          companyId: '87b99206-a478-11e9-a036-0e411fd5cf91',
          condense: 'ABCD'
        },
        {
          companyId: '0491ef94-a38b-11e9-a036-0e411fd5cf91',
          condense: '11'
        }
      ]
    };
  },
  methods: {
    GenreChange() {
      const vm = this;
      this.Genrelist.forEach(function(i) {
        if (i.companyId === vm.projectForm.genre) {
          vm.projectForm.genre = i.condense;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.modifyProject {
  width: 100%;
}
</style>
