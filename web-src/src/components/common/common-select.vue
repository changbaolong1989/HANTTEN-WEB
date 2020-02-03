<!--通用下拉列表组件 @editor cbl-->
<!--
使用方法：
在父组件中import当前组件，父组件props:data,holder,model，data数据结构至少包含value和text
如果需要下拉列表联动，监听a下拉框的model值变化时，改变b下拉框的值
父组件接收optionChanged回调
demo：
<select :formModel.sync="processConfigForm.departmentName" :data="departmentSelectData" @optionChanged="departmentChange"></select>
V2.0修改：
父组件传入dataFormat，格式为:dataFormat="{'value':'valueKey','label':'labelKey'}"
实现动态key,label
V2.1修改：
select回调函数将返回整个数据对象，实现动态label，可以显示多个属性作为label，格式为：{value: 'contractId', label: 'contractNum,contractName'}
-->
<template>
  <el-select v-model="selectFormModel" @change="valueChanged">
    <el-option
      v-for="(item,index) in dataConverted"
      :label="item.label" :key="index"
      :value="item"
    ></el-option>
  </el-select>
</template>

<script>
  export default {
    name: "common-combo-box",
    props: ['formModel', 'data', 'dataFormat'],
    data() {
      return {
        selectFormModel: this.formModel,
        dataConverted: this.data
      }
    },
    created() {
      this.dataConverted = this.dataTrans(this.data);
    },
    methods: {
      valueChanged(item) {
        this.$emit('optionChanged', item);
      },
      dataTrans(data) {
        let _data = [];
        if (this.dataFormat.label.indexOf(',') > 0) {//多属性label
          for (let i = 0; i < data.length; i++) {
            _data[i] = {};
            _data[i] = JSON.parse(JSON.stringify(data[i]));
            let labelArray = this.dataFormat.label.split(',');
            let labelText = '';
            for (let j = 0; j < labelArray.length; j++) {
              labelText += data[i][labelArray[j]] + ' - ';
            }
            if (labelText.endsWith(' - ')) {
              labelText = labelText.substring(0, labelText.length - 3);
            }
            _data[i].label = labelText;
            _data[i].value = data[i][this.dataFormat.value];
          }
        } else {
          for (let i = 0; i < data.length; i++) {
            _data[i] = {};
            _data[i] = JSON.parse(JSON.stringify(data[i]));
            _data[i].label = data[i][this.dataFormat.label];
            _data[i].value = data[i][this.dataFormat.value];
          }
        }
        return _data;
      }
    },
    watch: {
      formModel(newVal) {
        this.selectFormModel = newVal;
        this.$emit('update:formModel', newVal);
      },
      data: {
        handler: function (val, oldVal) {
          if (val !== oldVal) {
            this.$nextTick(() => {
              this.dataConverted = this.dataTrans(val);
            });
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>

