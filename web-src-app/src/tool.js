export default {
  formatDate :function (_data,fmt) { //author: meizz
  let data = new Date(_data);
      new Date();
      var o = {
          "M+": data.getMonth() + 1, //月份
          "d+": data.getDate(), //日
          "h+": data.getHours(), //小时
          "m+": data.getMinutes(), //分
          "s+": data.getSeconds(), //秒
          "q+": Math.floor((data.getMonth() + 3) / 3), //季度
          "S": data.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (data.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
          if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
  },

  // 范围日期转换
  formatTosplit: function(date) {
    let retArr = [];
    if (!this.isNull(date)) {
      const arr = date.split("至");
      if (arr.length > 0) {
        for (let i = 0; i < arr.length; i++) {
          retArr.push(this.formatToDate(arr[i]));
        }
      }
    }
    return retArr;
  },

  /**
   * 替换日期格式
   * @param date 需要转换的日期
   * @returns {*}
   */
  formatToDate: function (date) {
    let time = '';
    if (!this.isNull(date) && typeof(date) == 'string') {
      let year = '';
      let month = '';
      let day = '';
      let yearIndex = date.indexOf("年");
      // 如果存在年
      if (yearIndex > -1) {
        year = date.substr(0, yearIndex);
      }
      let monthIndex = date.indexOf("月");
      // 如果存在月
      if (monthIndex > -1) {
        month = '/' + date.substr(yearIndex + 1, monthIndex - yearIndex - 1);
      }
      let dayIndex = date.indexOf("日");
      // 如果存在日
      if (dayIndex > -1) {
        day = '/' + date.substr(monthIndex + 1, dayIndex - monthIndex - 1);
      }
      time = year + month + day;

      return time;
    } else {
      return date;
    }
  },

  // 判断参数是否为空
  isNull: function (paramVar) {
    let paramVarIsNullBool = false;
    if (paramVar == undefined || paramVar == null || paramVar === '' || paramVar.length == 0) {
      paramVarIsNullBool = true;
    }
    return paramVarIsNullBool;
  },
  deepCopy(obj,callback) {
    let clonedObj;
    // 判断直接数据类型
    if (['number', 'string', 'boolean', 'undefined', 'symbol', ].includes(typeof obj) || obj === null) {
      clonedObj = obj;
      return clonedObj;
    }
    const constructor = obj.constructor || Object;
    // 如果是文件类型则原样返回
    if(FileList === constructor)return obj;
    clonedObj = new constructor();
    Object.entries(obj).forEach(([key, value]) => {
      clonedObj[key] = callback(value,callback);
    })
    return clonedObj;
  },
  /**
   * @method 日期格式化
   */
  dateFormat(d,format){
	  d = d?new Date(d):new Date();
    let year = '';
    let mouth = '';
    let date = '';
    // 年
    year = d.getFullYear();
    // 月
    mouth = d.getMonth()+1;// 注意月份+1
    mouth<10?mouth='0'+mouth:'';
    // 日
    date = d.getDate();
    date<10?date='0'+date:'';
    return year+(format[0]||'-')+mouth+(format[1]||'-')+date+(format[2]||'');
  },
  /**
   * @method 获取唯一标识
   */
  getId(){
    return Number(Math.random().toString().substr(3,length) + Date.now()).toString(36);
  },
  /**
   * @method 表单禁用\启用
   * @param {Object} form 表单 $refs[form] 获取到的表单即可
   * @param {Object} isDisabled 是否禁用
   */
  formDisabled(form,isDisabled){
     let i = 0;
    while(form[i]){
      form[i].disabled = !!isDisabled;
      i++;
    }
  }
}
