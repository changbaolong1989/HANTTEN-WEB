<template>
  <!-- 项目详情-->
  <div class="m-work-contract-foundItem">
      <div class="given-container">
    <el-collapse v-model="activeNames" accordion>
      <!-- 文件上传-->
      <fileList :title="rowInfoLabel" :showUploadBtn="true" :options="fileTypeList" @del="deleteFileInfo" :list="fileList" :userType='userType'
        @submit="submitForm" v-if="rowInfoLevel==6 && pageContent=='A3'"></fileList>

      <el-collapse-item class="level1" :name="i" v-for="(li1, i) in list" :key="i"  v-if="false">
        <template slot="title">
          <span>{{ li1.title }}</span>
        </template>
        <el-collapse accordion>
          <el-collapse-item class="level2" :name="i + '-' + j" v-for="(li2, j) in li1.list ? li1.list : []" :key="j">
            <template slot="title">
              <span class="leve-name">{{ li2.name }}</span>
              <span class="time">{{ li2.time }}</span>
            </template>
            <div class="content">
              <div class="opinion">{{ li2.content }}</div>
              <ul>
                <li><i class="iconfont point">&#xe61a;</i>勾选未通过意见1</li>
                <li><i class="iconfont point">&#xe61a;</i>勾选未通过意见2</li>
              </ul>
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-collapse-item>
      <el-collapse-item name="5" title="审核反馈" class="review-the-feedback" v-if="false">
        <div class="content" style="padding:0 20px 10px;">
          <div class="file-list">
            <ul>
              <li tabindex="0" class="el-upload-list__item is-finished" @click="checkFile(1)">
                <a class="el-upload-list__item-name">
                  <i class="el-icon-document"></i>
                  food.jpeg
                </a>
                <label class="el-upload-list__item-status-label"><i class="el-icon-upload-success el-icon-circle-check"></i></label>
                <i class="el-icon-download" @click.stop="download(1)"></i>
              </li>
            </ul>
          </div>
        </div>
      </el-collapse-item>
      <el-collapse-item class="invoice" name="6" title="提审文件" v-if="false">
        <template slot="title">
          提审文件&nbsp;
          <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;" @click.stop="uploadFile"></i>
        </template>
        <div class="content hide-upload-container" style="padding: 0 20px 10px;">
          <el-upload accept="text" class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" multiple>
            <el-button size="small" type="primary" id="btn-upload">点击上传</el-button>
          </el-upload>
          <div class="file-list">
            <ul>
              <li tabindex="0" class="el-upload-list__item is-finished" @click="checkFile(2)" v-for="(f, index) in fileList"
                v-bind:key="index">
                <a class="el-upload-list__item-name"><i class="el-icon-document"></i>{{ f.name }}</a>
                <label class="el-upload-list__item-status-label"><i class="el-icon-upload-success el-icon-circle-check"></i></label>
                <i class="el-icon-download" @click.stop="download(2)"></i>
                <i class="el-icon-close"></i>
              </li>
            </ul>
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
    </div>
    <div class="btn-group">
      <el-button type="primary" style="padding: 0 20px;width: 50%;position: absolute;bottom: 20px;left: 25%;" @click="present" v-if="false">提交</el-button>
    </div>

  </div>
</template>

<script>
  import axios from 'axios';
  // 面板--上传下载
  import collapseUpAndDownload from '../../../common/components/collapseUpAndDownload.vue';
  export default {
    components: {
      'fileList': collapseUpAndDownload
    },
    data() {
      return {
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        user: {
          userId: '123',
          userName: '小红'
        },
        activeNames: '',
        list: [],
        contract:false,
        rowInfoLevel: '1',
        rowInfoLabel: '',
        userType:'1',
        pageContent:"",
      };
    },
    mounted() {
      const vm = this;
      // 监听显示合同基本信息
      vm.bus.$on('click1', val => {
        // 合同基本信息显示
        vm.baseContract(val);

        vm.checkRowInfo(val);
        vm.searchMaterialTypeList();

      });
    },
    methods: {
      checkRowInfo(val) {
        this.rowInfo = val
        this.rowInfoLevel = val.treeNodeLevel;
        this.rowInfoLabel = val.label;
        this.pageContent = val.pageContent;

        if (val.treeNodeLevel == '6' && val.pageContent=='A3') {
          let vm = this;
          let formData = {
            relationId: val.id
          }
          vm.apiAddress('/tenderTask/query/searchTenderTaskPeriodMaterialList', formData).then(
            res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                let dataList = res.data.dataList;
                for (var i = dataList.length - 1; i >= 0; i--) {
                  dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
                }
                vm.fileList = dataList;
              }
            }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          });
        }
      },
      searchMaterialTypeList() {
        let vm = this;
        let formData = {}
        vm.apiAddress('/tenderTask/query/searchMaterialTypeList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeId/g, "id"));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeName/g, "label"));
            }
            vm.fileTypeList = dataList
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      },

      deleteFileInfo(val) {
        this.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let vm = this;
          let formData = {
            relationId: val.materialId,
            materialPath: val.materialPath
          }
          this.apiAddress('/tenderTask/delete/deleteTenderTaskPeriodMaterialInfo', formData).then(res => {
            if (res.data.status == 'failure') {
              vm.$message({
                message: res.data.message,
                type: 'error',
                center: true
              });
            } else {
              vm.$message({
                message: res.data.message,
                type: 'success',
                center: true
              });
              vm.checkRowInfo(vm.rowInfo);
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          })
        }).catch(() => {});
      },

      downloadMatrial(item) {
        this.downloadFile(item.url, item.name);
      },
      // 提交上传文件
      submitForm(obj) {
        // this.$confirm('是否提交?', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   center: true
        // }).then(() => {
          // 任务阶段id
          obj.append("taskPeriodId", this.rowInfo.id);
          // 文件类型
          obj.append("fileType", obj.get('select'));

          obj.delete('select')

          let vm = this;
          this.apiAddress('/task/add/insertTaskPeriodMaterialInfo', obj).then(res => {
            if (res.data.status == 'failure') {
             this.stateDialog('success', res.data.message);
            } else {
              this.stateDialog('success', res.data.message);
              vm.checkRowInfo(vm.rowInfo);
            }
          }).catch(err => {
            this.stateDialog('success', res.data.message);
          })
        // }).catch(() => {});
      },

      baseContract(val) {
        if (val.treeNodeLevel == 3) {
          this.contract = true;
        } else {
          this.contract = false;
        }
      },

      download() {
        // 下载文件downloadFile
        let formData = new FormData();
        formData.append('filePathName', '/a.jpg');
        axios.post('/commonModule/downloadFile', formData);
      },
      checkFile(n) {
        let vm = this;
        vm.bus.$emit('checkFile', n);
      },
      present() {
        this.bus.$emit("changeform")
      }
    }
  };
</script>
<style lang="scss">
  @import url('../../../common/css/titleDownLoadTool.css');
  /* 标题下载工具*/
  @import url('../../../common/css/foldedPanel.css');
  /* 折叠面板*/
  @import url('../../../common/css/fileList.css');
  /* 文件列表*/
</style>
<style lang="scss" scoped>
  .m-work-contract-foundItem {
    height: calc(100% - 20px);
    display:flex;
    flex-direction:column;

    .given-container{
      flex-grow: 100;
      overflow: auto;
    }

    .btn-group {
      //按钮组
      width: 80%;
      bottom: 20px;
      margin-left: 10%;
      display: flex;
      flex-direction: column;
      flex-grow: 1;

      .el-button {
        width: 100%;

        &+.el-button {
          margin-left: 0;
          margin-top: 10px;
        }
      }
    }
  }
</style>
