<template>
  <el-row :gutter="gutter">
    <el-col :span="18">
      <el-form-item :prop="field"><el-input type="textarea" :autosize="{ minRows: 3, maxRows: 4 }" v-model="model" @change="textChange"></el-input></el-form-item>
    </el-col>
    <el-col :span="6">
      <el-form-item>
        <el-button type="primary" size="mini" @click="clear">清除</el-button>
        <el-button type="primary" size="mini" @click="restore">还原</el-button>
      </el-form-item>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      gutter: 40,
      model: this.obj.model,
      field: this.obj.field
    };
  },
  props: ['obj', 'text'],
  watch: {
    obj: {
      handler(newval, oldval) {
        this.obj = newval;
        this.$set(this,'model',this.obj.model)
      },
      immediate: true,
      deep:true
    }
  },
  methods: {
    textChange(val){
      this.$emit('childByValue',{label:this.obj.field,value:val})
    },
    clear() {
      this.$emit('childByValue',{label:this.obj.field,value:''})
    },
    restore() {
      this.$emit('childByValue',{label:this.obj.field,value:this.text})
    }
  }
};
</script>

<style></style>
