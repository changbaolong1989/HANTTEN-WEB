<template>
  <!-- 提交审核-->
  <div class="dialogSubmitAudit--work-contract-compile" @click.stop="(()=>{})">
    <!-- 弹窗 -->
    <el-dialog title="请选择提审文件配套资料" :visible="show" width="50%" center :before-close="beforeClose" :close-on-click-modal="false">
      <el-form ref="form" :status-icon="true" :model="form" style="text-align: left;" class="Remanddata">
        <template v-for="(li,i) in list">
          <el-form-item :label="li.label" :key="i" v-if="li.children&&li.children.length" style="display: flex;">
            <el-checkbox-group v-model="form.checkbox">
              <el-row v-for="(item,j) in li.children" :key="j">
                <el-col :span="24">
                  <el-checkbox :label="item.id">{{item.label}}</el-checkbox>
                </el-col>
              </el-row>
            </el-checkbox-group>
          </el-form-item>
        </template>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit" style="padding: 0 45px;">确定提交</el-button>
      </span>
    </el-dialog>

    <!-- 跳级弹窗 -->
    <el-dialog title="确认" :visible="aboveShow" width="50%" center :before-close="aboveBeforeClose" :close-on-click-modal="false">
      审核本来要提交给<span style="color:#0000FE;">{{currentReviewer.current}}</span>
      ,现在将要跳级提交给<span style="color:orange;">{{currentReviewer.next}}</span>
      <span slot="footer" class="dialog-footer">
         <el-button @click="cancel" style="padding: 0 45px;">取消</el-button>
        <el-button type="primary" @click="aboveShow = false" style="padding: 0 45px;">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    // above  越级
    props: ['above','reviewer'],
    mounted() {},
    methods: {
      upload() {},
      submit() {
        let vm = this;
          vm.$emit('submit', vm.form);
          vm.show = false;
      },
      cancel(){// 跳级取消
        let vm = this;
        vm.aboveShow = false
        vm.$emit('cancel');
      },
      beforeClose() {
        let vm = this;
        vm.show = false;
      },
      aboveBeforeClose() {
        let vm = this;
        vm.aboveShow = false;
        vm.$emit('cancel');
      }
    },
    data() {
      return {
        list: [{
          label: '文件类型1',
          children: [{
            label: 'xx文件',
            id: 'id1'
          }, {
            label: 'xx文件2',
            id: 'id2'
          }]
        }],
        gutter: 20,
        show: false, // 提审弹窗显示
        aboveShow: false, // 越级提审框显示
        form: {
          checkbox: []
        },
        currentReviewer:this.reviewer
      };
    },
    watch:{
      above(){
        let vm = this;
          if(vm.above){
            vm.aboveShow = true;
          }
      },
      reviewer(){
        let vm = this;
        vm.currentReviewer = vm.reviewer;
      }
    }

  };
</script>
<style lang="scss">
  .Remanddata{
    .el-form-item__label{
      width: auto;
    }
  }
</style>
<style lang="scss" scoped></style>
