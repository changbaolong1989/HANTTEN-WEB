export default {
  deepCopy(obj,callback) {
    let clonedObj;
    // 判断直接数据类型
    if (['number', 'string', 'boolean', 'undefined', 'symbol', ].includes(typeof obj) || obj === null) {
      clonedObj = obj;
      return clonedObj;
    }
    const constructor = obj.constructor || Object;
    clonedObj = new constructor();
    Object.entries(obj).forEach(([key, value]) => {
      clonedObj[key] = callback(value,callback);
    })
    return clonedObj;
  }
};
