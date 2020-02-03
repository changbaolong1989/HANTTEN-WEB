<template>
  <!-- 附录A -->
  <div class="Aform">
    <div class="form-block">
      <el-row :gutter="gutter" class="add">
        <el-col :span="12">
          <span>服务范围及工作内容、酬金</span>
          <el-button type="primary" class="linear" icon="el-icon-plus" @click="addAform"></el-button>
        </el-col>
        <el-col :span="12">
          <p>注：未选择的服务内容将在文档中以“/”体现</p>
        </el-col>
      </el-row>
      <el-form class="form" ref="Aform" :model="Aform" :disabled="disabled">
        <div class="addform" v-for="(item,index) in Aform.data" :key="index">
          <el-button type="text" icon="el-icon-close" class="colse-icon" @click="colseForm(index)"></el-button>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="服务阶段" :prop="'data.'+index+'.serve'" :rules="[{ required: true, message: '请选择服务阶段', trigger: 'change' }]">
                <el-select v-model="item.serve" placeholder="请选择服务阶段" @change="select(item,index)">
                  <el-option-group v-for="i in servelist" :key="i.id" :label="i.name">
                    <el-option v-for="k in i.list" :key="k.id" :label="k.name" :value="i.id+','+i.name+','+k.name">
                    </el-option>
                  </el-option-group>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="工作内容" :prop="'data.'+index+'.content'" :rules="[{ required: true, message: '请选择工作内容', trigger: 'blur' }]"
                v-show="item.checkbox">
                <el-checkbox-group v-model="item.content">
                  <el-checkbox v-for="i in contentlist" :label="i.id" :key="i.id">{{i.name}}</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
              <el-form-item label="工作内容" :prop="'data.'+index+'.content'" :rules="[{ required: true, message: '请选择工作内容', trigger: 'blur' }]"
                v-show="item.checkbox1">
                <el-checkbox-group v-model="item.content">
                  <el-checkbox v-for="i in contentlist1" :label="i.id" :key="i.id">{{i.name}}</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
              <el-form-item label="工作内容" :prop="'data.'+index+'.details'" :rules="[{ required: true, message: '请输入工作内容', trigger: 'blur' }]"
                v-show="item.input">
                <el-input type="text" v-model="item.details"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="8">
              <el-form-item label="收费基数" class="tentative" :prop="'data.'+index+'.charge.money'" :rules="[{ required: true, message: '收费基数', trigger: 'blur' }]">
                <el-input v-model="item.charge.money" placeholder="请输入">
                  <template slot="append">
                    万元
                  </template>
                </el-input>
                <el-checkbox-group v-model="item.charge.state">
                  <el-checkbox label="1" name="type" true-label="1" false-label="0" value="0">暂定</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="收费标准" class="tentative" :prop="'data.'+index+'.standard.money'" :rules="[{ required: true, message: '收费标准', trigger: 'blur' }]">
                <el-input v-model="item.standard.money" placeholder="请输入">
                  <template slot="append">
                    %
                  </template>
                </el-input>
                <el-checkbox-group v-model="item.standard.state">
                  <el-checkbox label="1" name="type" true-label="1" false-label="0"  value="0">暂定</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="酬金金额" class="tentative" :prop="'data.'+index+'.sum.money'" :rules="[{ required: true, message: '酬金金额', trigger: 'blur' }]">
                <el-input v-model="item.sum.money" placeholder="请输入">
                  <template slot="append">
                    万元
                  </template>
                </el-input>
                <el-checkbox-group v-model="item.sum.state">
                  <el-checkbox label="1" name="type" true-label="1" false-label="0" value="0">暂定</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="24">
              <el-form-item label="备注" :prop="'data.'+index+'.remark'" :rules="[{ required: true, message: '备注', trigger: 'blur' }]">
                <el-input type="textarea" v-model="item.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>
  </div>

</template>

<script>
  export default {
    props:['theFormData',"disabled"],
    data() {
      return {
        gutter: 40, // 表单相间距
        servelist: [{
            name: '决策阶段',
            list: [{
                name: "投资估算",
                id: 1
              },
              {
                name: "经济评价",
                id: 2
              },
              {
                name: "其他",
                id: 0,
              }
            ],
            id: 0
          },
          {
            name: '设计阶段',
            list: [{
                name: "设计概算",
                id: 1
              },
              {
                name: "施工图预算",
                id: 2
              },
              {
                name: "其他",
                id: 0,
              }
            ],
            id: 1
          },
          {
            name: '发承包阶段',
            list: [{
                name: "工程量清单",
                id: 1
              },
              {
                name: "最高投标限价",
                id: 2
              },
              {
                name: "投标报价分析",
                id: 3
              }, {
                name: "清标报告",
                id: 4
              },
              {
                name: "其他",
                id: 0,
              }
            ],
            id: 2
          },
          {
            name: '实施阶段',
            list: [{
                name: "资金使用计划",
                id: 1
              },
              {
                name: "工程计量与工程款审核",
                id: 2
              },
              {
                name: "合同价款调整",
                id: 3
              }, {
                name: "工程变更、索赔、签证",
                id: 4
              },
              {
                name: "工程实施阶段造价控制",
                id: 5
              },
              {
                name: "其他",
                id: 0,
              },
            ],
            id: 3
          }, {
            name: '竣工阶段',
            list: [{
                name: "竣工结算",
                id: 1
              },
              {
                name: "竣工决算",
                id: 2
              },
              {
                name: "其他",
                id: 0,
              },
            ],
            id: 4
          }, {
            name: "其他服务",
            list: [{
              name: "工程造价鉴定",
              id: 0,
            }],
            id: 5
          }
        ],
        contentlist: [{
            name: "编制",
            id: 1
          },
          {
            name: "审核",
            id: 2
          },
          {
            name: "调整",
            id: 3
          },
        ],
        contentlist1: [{
          name: "编制",
          id: 1
        }, ],
        Aform: this.theFormData||{data:[]},
      };
    },
    watch:{
      theFormData(){
        this.Aform = this.theFormData;
      }
    },
    methods: {
      resetForm(){
        let vm = this;
          vm.$refs['Aform'].resetFields();
      },
      addAform() {
        this.Aform.data.push({
          serve: "",
          hide: "",
          content: [],
          details: "",
          charge: {
            money: "",
            state: ""
          },
          standard: {
            money: "",
            state: ""
          },
          sum: {
            money: "",
            state: ""
          },
          remark: "",
          checkbox: true,
          checkbox1: false,
          input: false
        })
      },
      colseForm(index) {
        this.Aform.data.splice(index, 1)
      },
      select(item, index) {
        var reg = /其他|工程实施阶段造价/g;
        var reg1 = /资金使用计划/g;
        if (reg.test(item.serve)) {
          this.Aform.data[index].input = true;
          this.Aform.data[index].checkbox = false;
          this.Aform.data[index].checkbox1 = false;
        } else if (reg1.test(item.serve)) {
          this.Aform.data[index].input = false;
          this.Aform.data[index].checkbox = false;
          this.Aform.data[index].checkbox1 = true;
        } else {
          this.Aform.data[index].input = false;
          this.Aform.data[index].checkbox = true;
          this.Aform.data[index].checkbox1 = false;
        }
      }
    }
  };
</script>
<style lang="scss">
  .Aform {
    .el-checkbox__label {
      font-size: 12px;
    }

    .tentative {
      position: relative;

      .el-checkbox-group {
        position: absolute;
        top: -30px;
        right: 0;
      }
    }

    .addform {
      .colse-icon {
        i {
          font-size: 18px;
        }
      }
    }

    .el-form-item__label{
      float: none;
      text-align: left;
      line-height: 30px;
      width: auto !important;
    }
    .el-form-item__content{
      margin-left: 0 !important;
      line-height: 30px;
      position: relative;
      font-size: 14px;
      width: 100%;

      .el-select{
          width: 100%;
      }
    }
  }

  .el-select-group {
    .el-select-dropdown__item {
      padding-left: 50px !important;
    }
  }
</style>
<style lang="scss" scoped>
  .add {
    font-size: 12px;

    span {
      color: #333333;
      margin-right: 5px;
    }

    p {
      color: #B0B0B0;
      text-align: right;
    }

    .el-button {
      width: 26px;
      height: 16px !important;
      line-height: 16px !important;
      border-radius: 5px;
      padding: 0px !important;
      border: none;

      i {
        font-size: 12px;
      }
    }
  }

  .addform {
    border-top: 1px solid #e3e3e3;
    padding-top: 15px;
    position: relative;
    margin-top: 15px;

    .colse-icon {
      color: #808080;
      position: absolute;
      top: 0;
      right: 0;

      i {
        font-size: 18px;
      }
    }
  }
</style>
